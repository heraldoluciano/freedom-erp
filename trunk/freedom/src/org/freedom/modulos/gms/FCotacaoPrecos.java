/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FCompra.java <BR>
 *                  Este programa é licenciado de acordo com a LPG-PC (Licença
 *                  Pública Geral para Programas de Computador), <BR>
 *                  versão 2.1.0 ou qualquer versão posterior. <BR>
 *                  A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e
 *                  REPRODUÇÕES deste Programa. <BR>
 *                  Caso uma cópia da LPG-PC não esteja disponível junto com
 *                  este Programa, você pode contatar <BR>
 *                  o LICENCIADOR ou então pegar uma cópia em: <BR>
 *                  Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                  Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR
 *                  este Programa é preciso estar <BR>
 *                  de acordo com os termos da LPG-PC <BR>
 *                  <BR>
 *                  Tela para cadastro de notas fiscais de compra.
 */

package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.modulos.std.DLRPedido;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FCotacaoPrecos extends FDetalhe implements PostListener,
		CarregaListener, FocusListener, ActionListener, InsertListener {

	private int casasDec = Aplicativo.casasDec;
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
	private JButton btStatusSol = new JButton("Solicitação Pendente", null);
	private JButton btStatusItem = new JButton("Item Pendente", null);
	private JButton btStatusCompra = new JButton("Cotação Pendente", null);
	private JTextFieldPad txtCodSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtDtEmitSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtCodItSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtQtdItAprovado = new JTextFieldPad(
			JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,
			13, 0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,
			13, 0);
	private JTextFieldPad txtCodProd2 = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd2 = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,
			19, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,
			10, 0);
	private JTextFieldPad txtOrigSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);
	private JTextFieldPad txtStatusSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituaçãoItAprov = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituaçãoItComp = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituaçãoIt = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtCodSol = new JTextFieldPad(JTextFieldPad.TP_INTEGER,
			8, 0);
	private JTextFieldPad txtCodCot = new JTextFieldPad(JTextFieldPad.TP_INTEGER,
			5, 0);
	private JTextFieldPad txtDtCot = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,
			0);
	private JTextFieldPad txtIdUsuCot = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,
			8, 0);
	private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);
	private JTextFieldPad txtQtdCot = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,
			15, casasDec);
	private JTextFieldPad txtQtdAprovCot = new JTextFieldPad(
			JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtPrecoCot = new JTextFieldPad(
			JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private Tabela tabCot = new Tabela();
	private JScrollPane spTabCot = new JScrollPane(tabCot);
	private Navegador navCot = new Navegador(true);
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	private ListaCampos lcFor = new ListaCampos(this, "FR");
	private ListaCampos lcCotacao = new ListaCampos(this, "");

	String sOrdNota = "";
	Integer anoCC = null;
	String codCC = null;
	Integer codAlmox = null;
	String aprovSolicitacaoCompra = "";

	public FCotacaoPrecos() {
		setTitulo("Cotação de Preços");
		setAtribos(15, 10, 760, 580);

		pnMaster.remove(2);
		pnGImp.removeAll();
		pnGImp.setLayout(new GridLayout(1, 3));
		pnGImp.setPreferredSize(new Dimension(220, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);

		pnMaster.add(spTab, BorderLayout.CENTER);

		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",
				ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd", "Descrição do produto",
				ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",
				ListaCampos.DB_SI, false));

		lcProd.setWhereAdic("ATIVOPROD='S'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);

		txtCodProd.setTabelaExterna(lcProd);
		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		txtRefProd.setTabelaExterna(lcProd);

		lcProd2.add(new GuardaCampo(txtCodProd2, "CodProd", "Cód.prod.",
				ListaCampos.DB_PK, false));
		lcProd2.add(new GuardaCampo(txtRefProd2, "RefProd", "Referência",
				ListaCampos.DB_SI, false));

		lcProd2.setWhereAdic("ATIVOPROD='S'");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);

		txtCodProd2.setTabelaExterna(lcProd2);
		txtRefProd2.setNomeCampo("RefProd");
		txtRefProd2.setListaCampos(lcCotacao);
		txtRefProd2.setTabelaExterna(lcProd2);

		lcCC.add(new GuardaCampo(txtCodCC, "CodCC", "Código", ListaCampos.DB_PK,
				false));
		lcCC.add(new GuardaCampo(txtAnoCC, "AnoCC", "Ano do Centro de Custos",
				ListaCampos.DB_SI, false));
		lcCC.montaSql(false, "CC", "FN");
		lcCC.setQueryCommit(false);
		lcCC.setReadOnly(true);
		txtCodCC.setTabelaExterna(lcCC);
		txtAnoCC.setTabelaExterna(lcCC);

		lcFor.add(new GuardaCampo(txtCodFor, "CodFor", "Cód.for.",
				ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo(txtDescFor, "RazFor",
				"Razão social do fornecedor", ListaCampos.DB_SI, false));
		lcFor.montaSql(false, "FORNECED", "CP");
		lcFor.setQueryCommit(false);
		lcFor.setReadOnly(true);
		txtCodFor.setTabelaExterna(lcFor);

		pinCab = new JPanelPad(740, 90);
		setListaCampos(lcCampos);
		setAltCab(90);
		setPainel(pinCab, pnCliCab);

		txtDtEmitSolicitacao.setEditable(false);
		lcCampos.setPodeExc(false);
		lcCampos.setPodeIns(false);

		adicCampo(txtCodSolicitacao, 7, 20, 100, 20, "CodSol", "Nºsolicit.",
				ListaCampos.DB_PK, true);
		adicCampo(txtDtEmitSolicitacao, 110, 20, 100, 20, "DtEmitSol",
				"Data solicitação", ListaCampos.DB_SI, true);
		adicCampoInvisivel(txtStatusSolicitacao, "SitSol", "Situação",
				ListaCampos.DB_SI, false);
		adic(btStatusSol, 500, 15, 230, 30);
		btStatusSol.setEnabled(false);
		adicCampoInvisivel(txtOrigSolicitacao, "OrigSol", "Origem",
				ListaCampos.DB_SI, false);
		setListaCampos(true, "SOLICITACAO", "CP");
		lcCampos.setQueryInsert(false);

		lcCotacao.setMaster(lcDet);
		lcDet.adicDetalhe(lcCotacao);
		lcCotacao.setTabela(tabCot);

		navCot.setListaCampos(lcCotacao);
		lcCotacao.setNavegador(navCot);
		pinDet = new JPanelPad(740, 287);
		setPainel(pinDet, pnDet);

		adic(navCot, 7, 250, 280, 27);
		adic(spTabCot, 7, 47, 730, 140);

		lcCampos.addPostListener(this);
		lcCampos.addCarregaListener(this);
		lcProd.addCarregaListener(this);
		lcDet.addPostListener(this);
		lcDet.addCarregaListener(this);
		lcDet.addInsertListener(this);
		lcCampos.addInsertListener(this);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);

	}

	private void montaDetalhe() {
		setAltDet(287);
		setPainel(pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

		navRod.setAtivo(4, false);
		navRod.setAtivo(5, false);

		lcDet.setPodeExc(false);
		lcDet.setPodeIns(false);
		txtCodItSolicitacao.setEditable(false);
		txtCodProd.setEditable(false);
		txtRefProd.setEditable(false);
		txtQtdItAprovado.setEditable(false);

		adicCampo(txtCodItSolicitacao, 7, 20, 30, 20, "CodItSol", "Item",
				ListaCampos.DB_PK, true);
		if (comRef()) {
			adicCampoInvisivel(txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_FK,
					txtDescProd, false);
			adicCampoInvisivel(txtRefProd, "RefProd", "Referência",
					ListaCampos.DB_FK, false);
			adic(new JLabelPad("Referência"), 40, 0, 67, 20);
			adic(txtRefProd, 40, 20, 67, 20);
			txtRefProd.setFK(true);
			txtRefProd.setBuscaAdic(new DLBuscaProd(Aplicativo.telaPrincipal, con, "REFPROD"));
		} else {
			adicCampo(txtCodProd, 40, 20, 87, 20, "CodProd", "Cód.prod.",
					ListaCampos.DB_FK, txtDescProd, false);
			txtCodProd.setBuscaAdic(new DLBuscaProd(Aplicativo.telaPrincipal, con, "CODPROD"));
		}

		adicCampoInvisivel(txtCodCC, "CodCC", "Cód.c.c.", ListaCampos.DB_FK, false);
		adicCampoInvisivel(txtAnoCC, "AnoCC", "ano c.c.", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodUsu, "IdUsuItSol", "Cód.usu.", ListaCampos.DB_SI,
				false);

		adicDescFK(txtDescProd, 130, 20, 267, 20, "DescProd",
				"Descrição do produto");
		adicCampo(txtQtdItAprovado, 400, 20, 77, 20, "QtdAprovItSol", "Qtd.aprov.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituaçãoIt, "SitItSol", "Sit.item",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituaçãoItComp, "SitCompItSol", "Sit.compra",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituaçãoItAprov, "SitAprovItSol", "Sit.aprovação",
				ListaCampos.DB_SI, false);
		adic(btStatusItem, 500, 10, 230, 30);
		btStatusItem.setEnabled(false);

		txtRefProd.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent kevt) {
				lcDet.edit();
			}
		});

		setListaCampos(true, "ITSOLICITACAO", "CP");
		lcDet.setWhereAdic("SitAprovItSol <> 'NA' AND SitItSol <> 'CA'");
		lcDet.setQueryInsert(false);
		montaTab();

		tab.setTamColuna(30, 0);
		tab.setTamColuna(80, 1);
		tab.setTamColuna(230, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(70, 4);
		tab.setTamColuna(70, 5);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(70, 7);
		tab.setTamColuna(70, 8);
		tab.setTamColuna(70, 9);
		tab.setTamColuna(70, 10);

		setListaCampos(lcCotacao);
		setNavegador(navCot);

		txtQtdAprovCot.setSoLeitura(true);
		txtDtCot.setSoLeitura(true);

		adicCampo(txtCodCot, 7, 210, 77, 20, "CodCot", "Cód.Cot.",
				ListaCampos.DB_PK, true);
		adicCampoInvisivel(txtCodSol, "CodSol", "Cód.Sol.", ListaCampos.DB_PK, true);
		adicCampo(txtDtCot, 87, 210, 97, 20, "DtCot", "Dt.Cot.", ListaCampos.DB_SI,
				false);
		adicCampoInvisivel(txtIdUsuCot, "IdUsuCot", "Usu.Cot.", ListaCampos.DB_SI,
				false);
		adicCampo(txtCodFor, 187, 210, 77, 20, "CodFor", "Cod.For.",
				ListaCampos.DB_FK, txtDescFor, false);
		adicDescFK(txtDescFor, 267, 210, 197, 20, "RazFor",
				"Razão social do fornecedor");
		adicCampo(txtQtdCot, 467, 210, 87, 20, "QtdCot", "Qtd.Cot.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodProd2, "CodProd", "Cód.prod.", ListaCampos.DB_FK,
				false);
		adicCampoInvisivel(txtRefProd2, "RefProd", "Referência", ListaCampos.DB_FK,
				false);
		adicCampo(txtQtdAprovCot, 557, 210, 87, 20, "QtdAprovCot",
				"Qtd.Aprov.Cot.", ListaCampos.DB_SI, false);
		adicCampo(txtPrecoCot, 647, 210, 87, 20, "PrecoCot", "Preco.Cot.",
				ListaCampos.DB_SI, false);

		adic(btStatusCompra, 500, 235, 233, 30);
		btStatusCompra.setEnabled(false);

		lcCotacao.montaSql(true, "COTACAO", "CP");
		lcCotacao.montaTab();
		lcCotacao.addInsertListener(this);
		lcCotacao.addCarregaListener(this);

		tabCot.setTamColuna(30, 0);
		tabCot.setTamColuna(80, 1);
		tabCot.setTamColuna(230, 2);
		tabCot.setTamColuna(70, 3);
		tabCot.setTamColuna(70, 4);
		tabCot.setTamColuna(70, 5);
		tabCot.setTamColuna(70, 6);
		tabCot.setTamColuna(70, 7);
		tabCot.setTamColuna(70, 8);

	}

	public void focusGained(FocusEvent fevt) {}

	public void focusLost(FocusEvent fevt) {}

	public void beforeCarrega(CarregaEvent cevt) {}

	public void afterPost(PostEvent pevt) {}

	public void afterCarrega(CarregaEvent cevt) {
		boolean allow = aprovSolicitacaoCompra.equalsIgnoreCase("TD");
		boolean allowItems = (aprovSolicitacaoCompra.equalsIgnoreCase("CC") && txtCodCC
				.getVlrString().equals(codCC))
				|| allow;
		boolean block = txtStatusSolicitacao.getVlrString().equalsIgnoreCase("PE")
				|| allow;
		boolean blockItems = (block
				&& txtSituaçãoIt.getVlrString().equalsIgnoreCase("PE") && txtCodUsu
				.getVlrString().equals(Aplicativo.strUsuario))
				|| allowItems;
		boolean blockCot = (blockItems
				&& txtSituaçãoItComp.getVlrString().equalsIgnoreCase("PE") && txtCodUsu
				.getVlrString().equals(Aplicativo.strUsuario));

		if (cevt.getListaCampos() == lcCampos) {
			lcCampos.setReadOnly(!block);

			if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("PE")) {
				btStatusSol.setText("Solicitação Pendente");
				lcDet.setReadOnly(false);
			} else if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("CA")) {
				btStatusSol.setText("Solicitação Cancelada");
				lcDet.setReadOnly(true);
			} else if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("SC")) {
				btStatusSol.setText("Solicitação Concluída");
				lcDet.setReadOnly(true);
			} else if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("TM")) {
				btStatusSol.setText("Cotação de Preços");
				lcDet.setReadOnly(true);
			}

		} else if (cevt.getListaCampos() == lcDet) {
			lcCampos.setReadOnly(!blockItems);

			if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("PE")) {
				if (allow || allowItems) {
					btStatusItem.setText("Item Pendente");
				}
			} else if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("CA")) {
				btStatusItem.setText("Item Cancelado");
			} else if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("SC")) {
				if (txtSituaçãoItAprov.getVlrString().equalsIgnoreCase("AT"))
					btStatusItem.setText("Aprovado Totalmente");
				else if (txtSituaçãoItAprov.getVlrString().equalsIgnoreCase("AP"))
					btStatusItem.setText("Aprovado Parcialmente");
				else
					btStatusItem.setText("Item Concluído");
			}
		} else if (cevt.getListaCampos() == lcCotacao) {
			lcCotacao.setReadOnly(!blockItems);

			if (txtSituaçãoItComp.getVlrString().equalsIgnoreCase("PE")) {
				if (allow || allowItems) {
					btStatusItem.setText("Cotação Pendente");
				}
			} else if (txtSituaçãoItComp.getVlrString().equalsIgnoreCase("CC")) {
				btStatusItem.setText("Cotação Cancelada");
			} else if (txtSituaçãoItComp.getVlrString().equalsIgnoreCase("CT")) {
				btStatusItem.setText("Compra Total");
			} else if (txtSituaçãoItComp.getVlrString().equalsIgnoreCase("CP")) {
				btStatusItem.setText("Compra Parcial");
			} else {
				btStatusItem.setText("Cotação Concluída");
			}

		}
	}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (kevt.getSource() == txtPrecoCot) {//Talvez este possa ser
				// o ultimo campo do
				// itvenda.
				if (lcCotacao.getStatus() == ListaCampos.LCS_INSERT) {
					lcCotacao.post();
					lcCotacao.limpaCampos(true);
					lcCotacao.setState(ListaCampos.LCS_NONE);
					txtCodFor.requestFocus();
				} else if (lcCotacao.getStatus() == ListaCampos.LCS_EDIT) {
					lcCotacao.post();
					txtCodFor.requestFocus();
				}
			}
		}
		super.keyPressed(kevt);
	}

	public void actionPerformed(ActionEvent evt) {
		String[] sValores = null;
		if (evt.getSource() == btPrevimp)
			imprimir(true, txtCodSolicitacao.getVlrInteger().intValue());
		else if (evt.getSource() == btImp)
			imprimir(false, txtCodSolicitacao.getVlrInteger().intValue());
		super.actionPerformed(evt);
	}

	private void imprimir(boolean bVisualizar, int iCodSol) {
		ImprimeOS imp = new ImprimeOS("", con);
		DLRPedido dl = new DLRPedido(sOrdNota);
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		imp.verifLinPag();
		imp.setTitulo("Relatório de Solicitação de Compras");
		String sSQL = "SELECT (SELECT COUNT(IC.CODITSOL) FROM CPITSOLICITACAO IC WHERE IC.CODSOL=S.CODSOL),"
				+ "S.CODSOL,S.DTEMITSOL,S.SITSOL,S.MOTIVOSOL,"
				+ "I.CODPROD, I.QTDITSOL, I.QTDAPROVITSOL,I.SITAPROVITSOL, I.SITCOMPITSOL, I.SITITSOL,"
				+ "P.REFPROD,P.DESCPROD, P.CODUNID,"
				+ "A.CODALMOX, A.DESCALMOX, CC.CODCC, CC.ANOCC"
				+ " FROM CPSOLICITACAO S, CPITSOLICITACAO I, EQALMOX A, FNCC CC, EQPRODUTO P"
				+ " WHERE S.CODSOL="
				+ iCodSol
				+ " AND I.CODSOL=S.CODSOL"
				+ " AND P.CODPROD=I.CODPROD"
				+ " AND I.CODALMOX=I.CODALMOX"
				+ " AND CC.CODCC=I.CODCC"
				+ " ORDER BY S.CODSOL,P."
				+ dl.getValor()
				+ ";";

		PreparedStatement ps = null;
		ResultSet rs = null;
		int iItImp = 0;
		int iMaxItem = 0;
		try {
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			imp.limpaPags();
			iMaxItem = imp.verifLinPag() - 23;
			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.impCab(136, false);
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 4, "SOLICITAÇÂO DE COMPRA No.: ");
					imp.say(imp.pRow() + 0, 25, rs.getString("CODSOL"));
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 96, "[ Data de Emissão ]");
					imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs
							.getDate("DTEMITSOL")));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 57, "DADOS DO(S) PRODUTO(S)");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "Referencia");
					imp.say(imp.pRow() + 0, 18, "Descrição dos Produtos");
					imp.say(imp.pRow() + 0, 60, "Qtd. Sol.");
					imp.say(imp.pRow() + 0, 75, "Qtd. Aprov.");
					imp.say(imp.pRow() + 0, 90, "Sit. Item");
					imp.say(imp.pRow() + 0, 110, "Sit. Compra");
					imp.say(imp.pRow() + 0, 130, "Sit. Aprov.");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
				}
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 4, rs.getString("RefProd"));
				imp.say(imp.pRow() + 0, 18, rs.getString("DescProd").substring(0, 39));
				imp.say(imp.pRow() + 0, 60, "" + rs.getDouble("QTDITSOL"));
				imp.say(imp.pRow() + 0, 75, "" + rs.getDouble("QTDAPROVITSOL"));
				if (rs.getString("SITITSOL").equalsIgnoreCase("PE"))
					imp.say(imp.pRow() + 0, 90, "PENDENTE");
				if (rs.getString("SITITSOL").equalsIgnoreCase("SC"))
					imp.say(imp.pRow() + 0, 90, "CONCLUÍDO");
				if (rs.getString("SITITSOL").equalsIgnoreCase("SA"))
					imp.say(imp.pRow() + 0, 90, "CANCELADO");
				if (rs.getString("SITCOMPITSOL").equalsIgnoreCase("PE"))
					imp.say(imp.pRow() + 0, 110, "PENDENTE");
				if (rs.getString("SITCOMPITSOL").equalsIgnoreCase("CP"))
					imp.say(imp.pRow() + 0, 110, "COMPRA PARCIAL");
				if (rs.getString("SITCOMPITSOL").equalsIgnoreCase("CT"))
					imp.say(imp.pRow() + 0, 110, "COMPRA TOTAL");
				if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("PE"))
					imp.say(imp.pRow() + 0, 130, "PENDENTE");
				if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("AP"))
					imp.say(imp.pRow() + 0, 130, "APROVAÇÂO PARCIAL");
				if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("AT"))
					imp.say(imp.pRow() + 0, 130, "APROVAÇÂO TOTAL");
				if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("NA"))
					imp.say(imp.pRow() + 0, 130, "NÃO APROVADA");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				iItImp++;
				if ((imp.pRow() >= iMaxItem) | (iItImp == rs.getInt(1))) {
					if ((iItImp == rs.getInt(1))) {
						int iRow = imp.pRow();
						for (int i = 0; i < (iMaxItem - iRow); i++) {
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "");
						}
					}
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 60, "DADOS ADICIONAIS");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					if (rs.getString("SITSOL").equalsIgnoreCase("PE"))
						imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2,
								"SITUAÇÂO : PENDENTE");
					if (rs.getString("SITSOL").equalsIgnoreCase("SC"))
						imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2,
								"SITUAÇÂO : CONCLUÍDA");
					if (rs.getString("SITSOL").equalsIgnoreCase("SA"))
						imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2,
								"SITUAÇÂO : CANCELADA");
					imp.say(imp.pRow() + 2, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 3, "MOTIVO : " + rs.getString("MOTIVOSOL"));
					imp.eject();
				}
			}
			imp.fechaGravacao();

			if (!con.getAutoCommit())
				con.commit();
			dl.dispose();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao consultar a tabela de Compra!"
					+ err.getMessage());
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private boolean comRef() {
		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD, ORDNOTA FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRetorno = true;
				sOrdNota = rs.getString("OrdNota");
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage());
		}
		return bRetorno;
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		super.keyReleased(kevt);
	}

	public void beforePost(PostEvent pevt) {}

	public void beforeInsert(InsertEvent ievt) {}

	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcCotacao) {
			txtIdUsuCot.setVlrString(Aplicativo.strUsuario);
			txtDtCot.setVlrDate(new Date());
			txtCodSol.setVlrInteger(txtCodSolicitacao.getVlrInteger());
			txtCodProd2.setVlrInteger(txtCodProd.getVlrInteger());
			txtCodProd2.atualizaFK();
			txtQtdCot.setVlrDouble(txtQtdItAprovado.getVlrDouble());
			txtCodFor.requestFocus();
		}
	}

	public void exec(int iCodCompra) {
		txtCodSolicitacao.setVlrString(iCodCompra + "");
		lcCampos.carregaDados();
	}

	public void execDev(int iCodFor, int iCodTipoMov, String sSerie, int iDoc) {
		lcCampos.insert(true);
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
		montaDetalhe();
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcCotacao.setConexao(cn);
		lcFor.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC=" + buscaAnoBaseCC());

		String sSQL = "SELECT anoCC, codCC, codAlmox, aprovCPSolicitacaoUsu FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? AND IDUsu=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ps.setString(3, Aplicativo.strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				anoCC = new Integer(rs.getInt("anoCC"));
				if (anoCC.intValue() == 0)
					anoCC = new Integer(buscaAnoBaseCC());
				codCC = rs.getString("codCC");
				codAlmox = new Integer(rs.getInt("codAlmox"));
				aprovSolicitacaoCompra = rs.getString("aprovCPSolicitacaoUsu");
			}

			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage());
		}

	}
}