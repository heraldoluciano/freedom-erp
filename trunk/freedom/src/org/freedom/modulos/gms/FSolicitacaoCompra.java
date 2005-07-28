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
import java.awt.Color;
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
import java.util.Vector;

import javax.swing.JButton;

import org.freedom.bmps.Icone;
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
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLBuscaEstoq;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.modulos.std.DLRPedido;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FObservacao;

public class FSolicitacaoCompra extends FDetalhe implements PostListener,
		CarregaListener, FocusListener, ActionListener, InsertListener {
	private static final long serialVersionUID = 1L;
	private int casasDec = Aplicativo.casasDec;
	private JPanelPad pinCab = new JPanelPad(740, 242);
	private JPanelPad pinBotCab = new JPanelPad(104, 92);
	private JPanelPad pinBotDet = new JPanelPad(104, 63);
	private JPanelPad pinDet = new JPanelPad();
	private JPanelPad pinLb = new JPanelPad();
	private JLabelPad lSitItSol = null;
	private JButton btAprovaSol = new JButton("Aprovar", Icone.novo("btTudo.gif"));
	private JButton btFinAprovSol = new JButton("Finaliz. aprov.", Icone
			.novo("btFechaVenda.gif"));
	private JButton btCancelaSol = new JButton("Cancelar", Icone
			.novo("btRetorno.gif"));
	private JButton btCancelaItem = new JButton("Cancelar", Icone
			.novo("btRetorno.gif"));
	private JButton btMotivoCancelaSol = new JButton("Mot.Can", Icone
			.novo("btObs.gif"));
	private JButton btMotivoCancelaItem = new JButton("Mot.Can", Icone
			.novo("btObs.gif"));
	private JButton btMotivoPrior = new JButton("Mot.Prior", Icone
			.novo("btObs.gif"));

	private JTextFieldPad txtCodSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtDtEmitSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtCodItSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtQtdItSolicitado = new JTextFieldPad(
			JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtQtdItAprovado = new JTextFieldPad(
			JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtIDUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,
			13, 0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,
			13, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,
			19, 0);
	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,
			10, 0);
	private JTextFieldPad txtOrigSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);
	private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 8, 0);
//	private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodAlmox = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);
	private JTextFieldPad txtNomeUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,
			40, 0);
	private JTextFieldPad txtCodCCUsu = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 19, 0);

	private JTextAreaPad txaMotivoSol = new JTextAreaPad();
	private JTextAreaPad txaMotivoCancSol = new JTextAreaPad();
	private JTextAreaPad txaMotivoCancItem = new JTextAreaPad();
	private JTextAreaPad txaMotivoPrior = new JTextAreaPad();

	private JTextFieldPad txtStatusSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoItAprov = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoItComp = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoIt = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);

	private JRadioGroup rgPriod = null;
	private Vector vLabsTipo = new Vector();
	private Vector vValsTipo = new Vector();
	private JScrollPane spnMotivo = new JScrollPane(txaMotivoSol);

	private ListaCampos lcAlmox = new ListaCampos(this, "AM");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	private ListaCampos lcUsu = new ListaCampos(this, "UU");
//	private ListaCampos lcUsuAtual = new ListaCampos(this, "UA");

	String sOrdNota = "";
	String sOrdSol = "";
	Integer anoCC = null;
	Integer iCodTpMov = null;
	String codCC = null;
	boolean bAprovaParcial = false;
	String SitSol = "";
	boolean[] bPrefs = null;
	boolean bAprovaCab = false;
	boolean bCotacao = false;
	int cont = 0;
	Vector vItem = new Vector();
	Vector vProdCan = new Vector();
	Vector vMotivoCan = new Vector();
	Integer codAlmox = null;
	String sSitIt = txtSituacaoIt.getVlrString();

	public FSolicitacaoCompra() {
		setTitulo("Solicitação de Compra");
		setAtribos(15, 10, 763, 580);

		pnMaster.remove(2);
		pnGImp.removeAll();
		pnGImp.setLayout(new GridLayout(1, 3));
		pnGImp.setPreferredSize(new Dimension(220, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);

		pnMaster.add(spTab, BorderLayout.CENTER);

		String sWhereAdicProd = "ATIVOPROD='S' AND RMAPROD='S' AND ((SELECT ANOCCUSU||CODCCUSU FROM sgretinfousu('"
				+ Aplicativo.strUsuario
				+ "')) IN "
				+ "(SELECT ANOCC||CODCC FROM EQPRODACESSO PA WHERE TIPOPA='RMA' AND PA.codemp=EQPRODUTO.CODEMP AND "
				+ "PA.CODFILIAL=EQPRODUTO.CODFILIAL AND PA.CODPROD=EQPRODUTO.CODPROD) "
				+ "OR "
				+ "((SELECT coalesce(COUNT(1),0) FROM EQPRODACESSO PA WHERE TIPOPA='RMA' AND PA.codemp=EQPRODUTO.CODEMP AND "
				+ "PA.CODFILIAL=EQPRODUTO.CODFILIAL AND PA.CODPROD=EQPRODUTO.CODPROD)=0) "
				+ "OR "
				+ "((SELECT ALMOXARIFE FROM sgretinfousu('"
				+ Aplicativo.strUsuario
				+ "'))='S') "
				+ "OR "
				+ "((SELECT APROVARMA FROM sgretinfousu('"
				+ Aplicativo.strUsuario
				+ "'))='TD') " + ") ";

		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",
				ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd", "Descrição do produto",
				ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",
				ListaCampos.DB_SI, false));
		lcProd.setWhereAdic(sWhereAdicProd);
		//	lcProd.setWhereAdic("ATIVOPROD='S' AND RMAPROD='S'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd);

		lcProd2.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",
				ListaCampos.DB_PK, false));
		lcProd2.add(new GuardaCampo(txtDescProd, "DescProd", "Descrição",
				ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.rod.",
				ListaCampos.DB_SI, false));

		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		lcProd2.setWhereAdic(sWhereAdicProd);
		//		lcProd2.setWhereAdic("ATIVOPROD='S' AND RMAPROD='S'");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2);

		lcAlmox.add(new GuardaCampo(txtCodAlmox, "CodAlmox", "Cod.almox.",
				ListaCampos.DB_PK, txtDescAlmox, false));
		lcAlmox.add(new GuardaCampo(txtDescAlmox, "DescAlmox",
				"Descrição do almoxarifado;", ListaCampos.DB_SI, false));
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);
		txtDescAlmox.setSoLeitura(true);
		txtCodAlmox.setTabelaExterna(lcAlmox);

		lcCC.add(new GuardaCampo(txtCodCC, "CodCC", "Cód.c.c.", ListaCampos.DB_PK,
				false));
		lcCC.add(new GuardaCampo(txtAnoCC, "AnoCC", "Ano c.c.", ListaCampos.DB_PK,
				false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC",
				"Descrição do centro de custo", ListaCampos.DB_SI, false));
		lcCC.montaSql(false, "CC", "FN");
		lcCC.setQueryCommit(false);
		lcCC.setReadOnly(true);
		txtCodCC.setTabelaExterna(lcCC);
		txtAnoCC.setTabelaExterna(lcCC);

		lcUsu.add(new GuardaCampo(txtIDUsu, "idusu", "Id.Usu.", ListaCampos.DB_PK,
				false));
		lcUsu.add(new GuardaCampo(txtNomeUsu, "nomeusu", "Nome do usuário",
				ListaCampos.DB_SI, false));
		lcUsu.add(new GuardaCampo(txtCodCCUsu, "codcc", "C.Custo Usuário",
				ListaCampos.DB_SI, false));
		lcUsu.montaSql(false, "USUARIO", "SG");
		lcUsu.setQueryCommit(false);
		lcUsu.setReadOnly(true);
		txtIDUsu.setTabelaExterna(lcUsu);
		//		txtIDUsu.setEnabled(false);

		vValsTipo.addElement("B");
		vValsTipo.addElement("M");
		vValsTipo.addElement("A");
		vLabsTipo.addElement("Baixa");
		vLabsTipo.addElement("Média");
		vLabsTipo.addElement("Alta");
		rgPriod = new JRadioGroup(3, 1, vLabsTipo, vValsTipo);
		rgPriod.setVlrString("B");

		setListaCampos(lcCampos);
		setAltCab(190);
		setPainel(pinCab, pnCliCab);

		adicCampo(txtCodSolicitacao, 7, 20, 70, 20, "CodSol", "Cód.Sol",
				ListaCampos.DB_PK, true);
		adicCampo(txtIDUsu, 451, 20, 80, 20, "IdUsu", "Id do usuário",
				ListaCampos.DB_FK, true);
		adicCampo(txtDtEmitSolicitacao, 539, 20, 86, 20, "DtEmitSol",
				"Data da Sol.", ListaCampos.DB_SI, true);

		adicDescFKInvisivel(txtDescCC, "DescCC", "Descrição do centro de custos");
		adicCampo(txtCodCC, 80, 20, 130, 20, "CodCC", "Cód.CC.",
				ListaCampos.DB_FK, txtDescCC, true);
		adicCampo(txtAnoCC, 213, 20, 70, 20, "AnoCC", "Ano CC.", ListaCampos.DB_FK,
				true);
		adicDescFK(txtDescCC, 286, 20, 162, 20, "DescCC",
				"Descrição do centro de custos");

		adicCampoInvisivel(txtStatusSolicitacao, "SitSol", "Sit.Sol.",
				ListaCampos.DB_SI, false);
		adicDBLiv(txaMotivoCancSol, "MotivoSol", "Motivo do cancelamento", false);
		adicCampoInvisivel(txtOrigSolicitacao, "OrigSol", "Origem",
				ListaCampos.DB_SI, false);

		adicDBLiv(txaMotivoSol, "MotivoSol", "Observações", false);
		adic(new JLabelPad("Observações"), 7, 40, 100, 20);
		adic(spnMotivo, 7, 60, 617, 90);

		txtIDUsu.setNaoEditavel(true);
		txtDtEmitSolicitacao.setNaoEditavel(true);
		txtCodCC.setNaoEditavel(true);
		txtAnoCC.setNaoEditavel(true);

		setListaCampos(true, "SOLICITACAO", "CP");
		lcCampos.setQueryInsert(false);

		txtQtdItSolicitado.addFocusListener(this);
		lcCampos.addPostListener(this);
		lcCampos.addCarregaListener(this);
		lcProd.addCarregaListener(this);
		lcProd2.addCarregaListener(this);
		lcDet.addPostListener(this);
		lcDet.addCarregaListener(this);
		lcDet.addInsertListener(this);
		lcCampos.addInsertListener(this);
		lcUsu.addCarregaListener(this);

		btAprovaSol.setToolTipText("Aprovar todos os ítens.");
		btFinAprovSol.setToolTipText("Finaliza Aprovação.");
		btCancelaSol.setToolTipText("Cancelar todos os ítens.");
		btCancelaItem.setToolTipText("Cancelar ítem.");
		btMotivoCancelaSol.setToolTipText("Motivo do cancelamento da Solicitação.");
		btMotivoCancelaItem.setToolTipText("Motivo do cancelamento do ítem.");
		btMotivoPrior.setToolTipText("Motivo da prioridade do ítem.");

		pinCab.adic(pinBotCab, 630, 1, 114, 150);
		pinBotCab.adic(btAprovaSol, 0, 0, 110, 30);
		pinBotCab.adic(btFinAprovSol, 0, 31, 110, 30);
		pinBotCab.adic(btCancelaSol, 0, 62, 110, 30);
		pinBotCab.adic(btMotivoCancelaSol, 0, 93, 110, 30);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		btAprovaSol.addActionListener(this);
		btCancelaSol.addActionListener(this);
		btCancelaItem.addActionListener(this);
		btMotivoCancelaSol.addActionListener(this);
		btMotivoCancelaItem.addActionListener(this);
		btMotivoPrior.addActionListener(this);
		btFinAprovSol.addActionListener(this);

		setImprimir(true);

		desabAprov(true);
	}

	private void desabAprov(boolean bHab) {
		if (txtStatusSolicitacao.getVlrString().equals("AT")) {
			btAprovaSol.setEnabled(false);
			if (!txtStatusSolicitacao.getVlrString().equals("AF"))
				btFinAprovSol.setEnabled(true);
			else {
				btFinAprovSol.setEnabled(false);
			}
		} else {
			btAprovaSol.setEnabled(!bHab);
		}
		if (txtStatusSolicitacao.getVlrString().equals("CA")) {
			btMotivoCancelaSol.setEnabled(true);
		}
		if (txtSituacaoItAprov.getVlrString().equals("CA")) {
			btMotivoCancelaItem.setEnabled(true);
		} else {
			btMotivoCancelaSol.setEnabled(!bHab);
			btMotivoCancelaItem.setEnabled(!bHab);
		}

		btFinAprovSol.setEnabled(!bHab);
		btCancelaSol.setEnabled(!bHab);
		btCancelaItem.setEnabled(!bHab);
		txtQtdItAprovado.setNaoEditavel(bHab);
	}

	private void montaDetalhe() {
		setAltDet(125);
		pinDet = new JPanelPad(740, 122);
		setPainel(pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

		adicCampo(txtCodItSolicitacao, 7, 20, 30, 20, "CodItSol", "Item",
				ListaCampos.DB_PK, true);
		if (comRef()) {
			adicCampo(txtRefProd, 40, 20, 87, 20, "RefProd", "Referência",
					ListaCampos.DB_FK, txtDescProd, true);
			adicCampoInvisivel(txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_SI,
					false);
			txtRefProd.setBuscaAdic(new DLBuscaProd(con, "REFPROD", lcProd2
					.getWhereAdic()));
			txtQtdItSolicitado.setBuscaAdic(new DLBuscaEstoq(lcDet, lcAlmox, lcProd2,
					con, "qtditsol"));
		} else {
			adicCampo(txtCodProd, 40, 20, 87, 20, "CodProd", "Cód.prod.",
					ListaCampos.DB_FK, txtDescProd, true);
			adicCampoInvisivel(txtRefProd, "RefProd", "Referência",
					ListaCampos.DB_SI, false);
			txtCodProd.setBuscaAdic(new DLBuscaProd(con, "CODPROD", lcProd
					.getWhereAdic()));
			txtQtdItSolicitado.setBuscaAdic(new DLBuscaEstoq(lcDet, lcAlmox, lcProd,
					con, "QtdItSol"));
		}

		adicDescFK(txtDescProd, 130, 20, 297, 20, "DescProd",
				"Descrição do produto");
		adicDB(rgPriod, 513, 20, 100, 65, "PriorItRma", "Prioridade:", true);
		adicCampo(txtQtdItSolicitado, 430, 20, 80, 20, "QtdItSol", "Qtd.solic.",
				ListaCampos.DB_SI, true);

		adicCampo(txtQtdItAprovado, 260, 60, 80, 20, "QtdAprovItSol", "Qtd.aprov.",
				ListaCampos.DB_SI, false);

		txtCodAlmox.setNaoEditavel(true);

		adicCampoInvisivel(txtCodAlmox, "CodAlmox", "Cód.Almox.",
				ListaCampos.DB_FK, txtDescAlmox, false);
		adicDescFK(txtDescAlmox, 7, 60, 250, 20, "DescAlmox",
				"Descrição do almoxarifado");

		adicCampoInvisivel(txtSituacaoIt, "SitItSol", "Sit.It.Sol.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituacaoItAprov, "SitAprovItSol", "Sit.Ap.It.Sol.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituacaoItComp, "SitCompItSol", "Sit.Cot.It.Sol.",
				ListaCampos.DB_SI, false);
		adicDBLiv(txaMotivoCancItem, "motivocancitsol", "Motivo do cancelamento",
				false);
		adicDBLiv(txaMotivoPrior, "MotivoPriorItSol", "Motivo da Prioridade", false);

		txtRefProd.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent kevt) {
				lcDet.edit();
			}
		});

		setListaCampos(true, "ITSOLICITACAO", "CP");
		lcDet.setQueryInsert(false);
		montaTab();

		tab.setTamColuna(30, 0);
		tab.setTamColuna(70, 1);
		tab.setTamColuna(250, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(70, 4);
		tab.setTamColuna(70, 5);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(70, 7);
		tab.setTamColuna(250, 8);

		btMotivoPrior.setEnabled(false);

		pinBotDet.adic(btCancelaItem, 0, 0, 110, 28);
		pinBotDet.adic(btMotivoCancelaItem, 0, 29, 110, 28);
		pinBotDet.adic(btMotivoPrior, 0, 58, 110, 28);
		pinDet.adic(pinBotDet, 630, 1, 114, 90);
		lSitItSol = new JLabelPad();
		lSitItSol.setForeground(Color.WHITE);
		//pinLb.add(new JLabelPad("",imgStatus,SwingConstants.CENTER));
		pinLb.adic(lSitItSol, 31, 0, 110, 20);
		pinDet.adic(pinLb, 630, 91, 114, 24);
	}
/*
	private void testaCodSol() { //Traz o verdadeiro número do codCompra
		// através do generator do banco
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			ps.setString(3, "CP");
			rs = ps.executeQuery();
			rs.next();
			txtCodSolicitacao.setVlrString(rs.getString(1));
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao confirmar código da Compra!\n"
					+ err.getMessage(), true, con, err);
		}
	}
*/
	public void focusGained(FocusEvent fevt) {}

	public void focusLost(FocusEvent fevt) {}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (kevt.getSource() == txtCodAlmoxarife) {//Talvez este possa ser
				// o ultimo campo do
				// itvenda.
				txtCodAlmoxarife.atualizaFK();
				if (lcDet.getStatus() == ListaCampos.LCS_INSERT) {
					lcDet.post();
					lcDet.limpaCampos(true);
					lcDet.setState(ListaCampos.LCS_NONE);
					if (comRef())
						txtRefProd.requestFocus();
					else
						txtCodProd.requestFocus();
				} else if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
					lcDet.post();
					txtCodItSolicitacao.requestFocus();
				}
			}
		}
		super.keyPressed(kevt);
	}

	public boolean[] prefs() {
		boolean[] bRet = { false };
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRet[0] = true;
				//sOrdNota = rs.getString("OrdNota");
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage(), true, con, err);
		} finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
		return bRet;
	}

	public void actionPerformed(ActionEvent evt) {
//		String[] sValores = null;
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
		imp.montaCab();
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
					imp.say(imp.pRow() + 0, 96, "[ Data de emissão ]");
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
					imp.say(imp.pRow() + 0, 18, "Descrição dos produtos");
					imp.say(imp.pRow() + 0, 60, "Qtd.sol.");
					imp.say(imp.pRow() + 0, 75, "Qtd.aprov.");
					imp.say(imp.pRow() + 0, 90, "Sit.item");
					imp.say(imp.pRow() + 0, 110, "Sit.compra");
					imp.say(imp.pRow() + 0, 130, "Sit.aprov.");
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
					+ err.getMessage(), true, con, err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private boolean comRef() {
		return bPrefs[0];
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		super.keyReleased(kevt);
	}

	private boolean dialogObsPrior() {
		boolean bRet = false;
		FObservacao obs = new FObservacao(txaMotivoPrior.getVlrString());
		if (obs != null) {
			if ((rgPriod.getVlrString().equals("A"))
					&& (txtIDUsu.getVlrString().equals(Aplicativo.strUsuario))) {
				obs.txa.setEnabled(true);
			} else
				obs.txa.setEnabled(false);
			obs.setVisible(true);
			if (obs.OK) {
				txaMotivoPrior.setVlrString(obs.getTexto());
				bRet = true;
			}
		}
		obs.dispose();
		return bRet;
	}

	public void beforeCarrega(CarregaEvent cevt) {}

	public void afterCarrega(CarregaEvent cevt) {

		buscaInfoUsuAtual();

		String sSitRma = txtStatusSolicitacao.getVlrString();
		String sSitItAprov = txtSituacaoItAprov.getVlrString();
		String sSitItExp = txtSituacaoItComp.getVlrString();
		sSitIt = txtSituacaoIt.getVlrString();

		boolean bStatusTravaTudo = ((sSitIt.equals("AF"))
				|| (sSitIt.equals("EF")) || (sSitIt.equals("CA")));
//		boolean bStatusTravaExp = (!(sSitIt.equals("AF")));

		if (cevt.getListaCampos() == lcDet) {
			if (sSitIt.equals("CA")) {
				desabCampos(true);
				btMotivoCancelaItem.setEnabled(true);
			}
		}

		if (rgPriod.getVlrString().equals("A") && sSitRma.equals("PE")) {
			btMotivoPrior.setEnabled(true);
		} else
			btMotivoPrior.setEnabled(false);

		if (sSitRma.equals("CA"))
			btMotivoCancelaSol.setEnabled(true);
		else
			btMotivoCancelaSol.setEnabled(false);

		if (!(txtIDUsu.getVlrString().equals(Aplicativo.strUsuario))
				|| (bStatusTravaTudo))
			desabCampos(true);
		else
			desabCampos(false);

		if (!bAprovaCab || bStatusTravaTudo) {
			desabAprov(true);
			txaMotivoCancSol.setEnabled(false);
		} else {
			if (!bStatusTravaTudo)
				txaMotivoCancSol.setEnabled(true);
			desabAprov(false);
		}

		if (((cevt.getListaCampos() == lcProd) || (cevt.getListaCampos() == lcProd2))
				&& ((lcDet.getStatus() == ListaCampos.LCS_EDIT) || ((lcDet.getStatus() == ListaCampos.LCS_INSERT)))) {
		}

		if (sSitIt.equals("CA")) {
			//imgStatus = imgCancelado;
			SitSol = "Cancelado";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(250, 50, 50));
		} else if (sSitIt.equals("PE")) {
			//imgStatus = imgPendento;
			SitSol = "Pendente";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(255, 204, 51));
		} else if (sSitItExp.equals("ET") || sSitItExp.equals("EP")) {
			//imgStatus = imgExpedido;
			SitSol = "Expedido";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(0, 170, 30));
		} else if (sSitItAprov.equals("AT") || sSitItAprov.equals("AP")) {
			//imgStatus = imgAprovado;
			SitSol = "Aprovado";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(26, 140, 255));
		}

		if (cevt.getListaCampos() == lcDet) {
			if (txtQtdItAprovado.isEditable()) {
				if (txtQtdItAprovado.getVlrDouble().compareTo(new Double(0)) <= 0)
					txtQtdItAprovado.setVlrDouble(txtQtdItAprovado.getVlrDouble());
			}
			if (txtQtdItSolicitado.isEditable()) {
				if (txtQtdItSolicitado.getVlrDouble().compareTo(new Double(0)) <= 0)
					txtQtdItSolicitado.setVlrDouble(txtQtdItAprovado.getVlrDouble());
			}
		}
		if (cevt.getListaCampos() == lcUsu) {
			/*
			 * String sWhereAdicProd = "ATIVOPROD='S' AND RMAPROD='S' AND ((SELECT
			 * ANOCCUSU||CODCCUSU FROM
			 * sgretinfousu('"+txtIDUsu.getVlrString().trim()+"')) IN "+ "(SELECT
			 * ANOCC||CODCC FROM EQPRODACESSO PA WHERE TIPOPA='RMA' AND
			 * PA.codemp=EQPRODUTO.CODEMP AND "+ "PA.CODFILIAL=EQPRODUTO.CODFILIAL AND
			 * PA.CODPROD=EQPRODUTO.CODPROD) "+ "OR "+ "((SELECT coalesce(COUNT(1),0)
			 * FROM EQPRODACESSO PA WHERE TIPOPA='RMA' AND PA.codemp=EQPRODUTO.CODEMP
			 * AND "+ "PA.CODFILIAL=EQPRODUTO.CODFILIAL AND
			 * PA.CODPROD=EQPRODUTO.CODPROD)=0) "+ "OR " + "((SELECT ALMOXARIFE FROM
			 * sgretinfousu('"+txtIDUsu.getVlrString().trim()+"'))='S')) ";
			 */

			//lcLote.setDinWhereAdic("CODPROD=#N AND VENCTOLOTE >= #D ",txtCodProd)

			//	carregaWhereAdic();
		}
	}
	public Color cor(int r, int g, int b){
		Color color = new Color(r, g, b);
		return color;
	}
	private void desabCampos(boolean bHab) {
		txtCodProd.setNaoEditavel(bHab);
		txtRefProd.setNaoEditavel(bHab);
		txtQtdItAprovado.setNaoEditavel(bHab);
		txaMotivoSol.setEnabled(!bHab);
		rgPriod.setAtivo(!bHab);
	}
	public void beforePost(PostEvent pevt) {
		String sMotvProir = rgPriod.getVlrString();
		if (pevt.getListaCampos() == lcCampos) {
			txtOrigSolicitacao.setVlrString("AX");
			if (txtSituacaoIt.getVlrString().equals("")) {
				txtStatusSolicitacao.setVlrString("PE");
			}
		} else if (pevt.getListaCampos() == lcDet) {
			if (txtQtdItAprovado.getVlrDouble().doubleValue() > txtQtdItSolicitado
					.getVlrDouble().doubleValue()) {
				Funcoes.mensagemInforma(null,
						"Quantidade aprovada maior que a requerida!");
				pevt.getListaCampos().cancelPost();
			}
			if (txtSituacaoIt.getVlrString().equals("")) {
				txtSituacaoIt.setVlrString("PE");
			}
			if (txtSituacaoItAprov.getVlrString().equals("")) {
				txtSituacaoItAprov.setVlrString("PE");
			}
			if (txtSituacaoItComp.getVlrString().equals("")) {
				txtSituacaoItComp.setVlrString("PE");
			}
			if (txtQtdItAprovado.getVlrString().equals("")) {
				txtQtdItAprovado.setVlrDouble(new Double(0));
			}
			if (txtQtdItSolicitado.getVlrString().equals("")) {
				txtQtdItSolicitado.setVlrDouble(new Double(0));
			}
			if (sMotvProir.equals("A")) {
				dialogObsPrior();
			}
		} else if (pevt.getListaCampos() == lcCampos) {
			if (txtStatusSolicitacao.getVlrString().equals("")) {
				txtStatusSolicitacao.setVlrString("PE");
			}
			if (txtSituacaoItAprov.getVlrString().equals("")) {
				txtSituacaoItAprov.setVlrString("PE");
			}
			if (txtSituacaoItComp.getVlrString().equals("")) {
				txtSituacaoItComp.setVlrString("PE");
			}

		}

	}

	public void beforeInsert(InsertEvent ievt) {}

	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcCampos) {
			txtAnoCC.setVlrInteger(anoCC);
			txtCodCC.setVlrString(codCC);
			lcCC.carregaDados();
			txtIDUsu.setVlrString(Aplicativo.strUsuario);
			txtDtEmitSolicitacao.setVlrDate(new Date());
			lcCampos.carregaDados();
		}
	}

	public void exec(int iCodCompra) {
		txtCodSolicitacao.setVlrString(iCodCompra + "");
		lcCampos.carregaDados();
	}

	public void execDev(int iCodFor, int iCodTipoMov, String sSerie, int iDoc) {
		lcCampos.insert(true);
	}
/*
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
							+ err.getMessage(), true, con, err);
		}
		return iRet;
	}
*/
	public void carregaWhereAdic() {
		buscaInfoUsuAtual();
		if ((bAprovaCab) || (bCotacao)) {
			if (bAprovaParcial) {
				lcCampos.setWhereAdic("CODCC='" + Aplicativo.strCodCCUsu
						+ "' AND ANOCC=" + Aplicativo.strAnoCCUsu);
			}
		} else {
			lcCampos.setWhereAdic("IDUSU='" + Aplicativo.strUsuario + "'");
		}
	}

	private void buscaInfoUsuAtual() {
		String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVCPSOLICITACAOUSU,ALMOXARIFEUSU "
				+ "FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? " + "AND IDUSU=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGUSUARIO"));
			ps.setString(3, Aplicativo.strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				String sAprova = rs.getString("APROVCPSOLICITACAOUSU");
				String sCotacao = rs.getString("ALMOXARIFEUSU");
				if (sAprova != null) {
					if (!sAprova.equals("ND")) {
						if (sAprova.equals("TD"))
							bAprovaCab = true;
						else if ((txtCodCC.getVlrString().trim().equals(rs.getString(
								"CODCC").trim()))
								&& (lcCC.getCodEmp() == rs.getInt("CODEMPCC"))
								&& (lcCC.getCodFilial() == rs.getInt("CODFILIALCC"))
								&& (sAprova.equals("CC"))) {
							bAprovaCab = true;
						} else {
							bAprovaCab = false;
						}

					}
				}
				if (sCotacao != null) {
					if (sCotacao.equals("S"))
						bCotacao = true;
					else
						bCotacao = false;
				}
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage());
		}
	}

	private int buscaVlrPadrao() {
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
		super.setConexao(cn); // tem que setar a conexão principal para verificar
		// preferências
		bPrefs = prefs();
		montaDetalhe();

		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC=" + buscaVlrPadrao());
		lcAlmox.setConexao(cn);
		lcUsu.setConexao(cn);

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
					anoCC = new Integer(buscaVlrPadrao());
				codCC = rs.getString("codCC");
			}

			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage());
		}

		/*
		 * lcProd.setWhereAdic(sWhereAdicProd);
		 * lcProd2.setWhereAdic(sWhereAdicProd);
		 */

		carregaWhereAdic();
	}
}