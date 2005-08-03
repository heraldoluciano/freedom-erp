/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Alexandre Rocha Lima e Marcondes <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.gms <BR>
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
 *                  Tela para cadastro de cotações de preço para compra.
 */

package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.modulos.std.DLRPedido;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FObservacao;

public class FCotacaoPrecos extends FDetalhe implements PostListener,
		CarregaListener, FocusListener, ActionListener, InsertListener {

	private static final long serialVersionUID = 1L;
	private int casasDec = Aplicativo.casasDec;
	private JPanelPad pinCab = new JPanelPad(740, 242);
	private JPanelPad pinBotCab = new JPanelPad(104, 92);
	private JPanelPad pinBotDet = new JPanelPad(104, 63);
	private JPanelPad pinLb = new JPanelPad();

	private JLabelPad lSitItSol = null;
	private JPanelPad pinDet = new JPanelPad();
	private JButton btAprovaSol = new JButton("Aprovar", Icone.novo("btTudo.gif"));
	private JButton btFinAprovSol = new JButton("Finaliz. aprov.", Icone
			.novo("btFechaVenda.gif"));
	private JButton btCompra = new JButton("Comprar", Icone.novo("btMedida.gif"));
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
	private JTextFieldPad txtQtdItAprovado = new JTextFieldPad(
			JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtIDUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,
			13, 0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,
			13, 0);
	private JTextFieldPad txtCodProd2 = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd2 = new JTextFieldPad(JTextFieldPad.TP_STRING,
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
	private JTextFieldPad txtStatusSolicitacao = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoItAprov = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoItComp = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoIt = new JTextFieldPad(
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

	private JTextFieldPad txtNomeUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,
			40, 0);
	private JTextFieldPad txtCodCCUsu = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 19, 0);

	private JTextAreaPad txaMotivoSol = new JTextAreaPad();
	private JTextAreaPad txaMotivoCancSol = new JTextAreaPad();
	private JTextAreaPad txaMotivoCancItem = new JTextAreaPad();
	private JTextAreaPad txaMotivoPrior = new JTextAreaPad();

	private Tabela tabCot = new Tabela();
	private JScrollPane spTabCot = new JScrollPane(tabCot);
	private Navegador navCot = new Navegador(true);
	
	private JRadioGroup rgPriod = null;
	private Vector vLabsTipo = new Vector();
	private Vector vValsTipo = new Vector();
	private JScrollPane spnMotivo = new JScrollPane(txaMotivoSol);

	private ListaCampos lcUsu = new ListaCampos(this, "UU");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	private ListaCampos lcFor = new ListaCampos(this, "FR");
	private ListaCampos lcCotacao = new ListaCampos(this, "");

	String sSitItSol = txtSituacaoIt.getVlrString();
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

	public FCotacaoPrecos() {
		setTitulo("Cotação de Preços");
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
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2);

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

		lcFor.add(new GuardaCampo(txtCodFor, "CodFor", "Cód.for.",
				ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo(txtDescFor, "RazFor",
				"Razão social do fornecedor", ListaCampos.DB_SI, false));
		lcFor.montaSql(false, "FORNECED", "CP");
		lcFor.setQueryCommit(false);
		lcFor.setReadOnly(true);
		txtCodFor.setTabelaExterna(lcFor);

		vValsTipo.addElement("B");
		vValsTipo.addElement("M");
		vValsTipo.addElement("A");
		vLabsTipo.addElement("Baixa");
		vLabsTipo.addElement("Média");
		vLabsTipo.addElement("Alta");
		rgPriod = new JRadioGroup(3, 1, vLabsTipo, vValsTipo);
		rgPriod.setVlrString("B");

		pinCab = new JPanelPad(740, 267);
		setListaCampos(lcCampos);
		setAltCab(267);
		setPainel(pinCab, pnCliCab);

		txtDtEmitSolicitacao.setEditable(false);
		lcCampos.setPodeExc(false);
		lcCampos.setPodeIns(false);
		nav.setAtivo(0, false);
		nav.setAtivo(1, false);
		nav.setAtivo(2, false);
		nav.setAtivo(3, false);
		nav.setAtivo(4, false);

		adicCampo(txtCodSolicitacao, 7, 20, 70, 20, "CodSol", "Cód.Sol",
				ListaCampos.DB_PK, true);
		adicCampo(txtIDUsu, 451, 20, 80, 20, "IdUsu", "Id do usuário",
				ListaCampos.DB_FK, true);
		adicCampo(txtDtEmitSolicitacao, 539, 20, 86, 20, "DtEmitSol",
				"Data da Sol.", ListaCampos.DB_SI, true);

		adicDescFKInvisivel(txtDescCC, "DescCC", "Descrição do centro de custos");
		adicCampo(txtCodCC, 80, 20, 130, 20, "CodCC", "Cód.CC.", ListaCampos.DB_FK,
				txtDescCC, true);
		adicCampo(txtAnoCC, 213, 20, 70, 20, "AnoCC", "Ano CC.", ListaCampos.DB_FK,
				true);
		adicDescFK(txtDescCC, 286, 20, 162, 20, "DescCC",
				"Descrição do centro de custos");

		adicCampoInvisivel(txtStatusSolicitacao, "SitSol", "Sit.Sol.",
				ListaCampos.DB_SI, false);
		adicDBLiv(txaMotivoCancSol, "MotivoSol", "Motivo do cancelamento", false);
		adicCampoInvisivel(txtOrigSolicitacao, "OrigSol", "Origem",
				ListaCampos.DB_SI, false);

		txtIDUsu.setNaoEditavel(true);
		txtDtEmitSolicitacao.setNaoEditavel(true);
		txtCodCC.setNaoEditavel(true);
		txtAnoCC.setNaoEditavel(true);

		setListaCampos(true, "SOLICITACAO", "CP");
		lcCampos.setQueryInsert(false);

		lcCotacao.setMaster(lcDet);
		lcDet.adicDetalhe(lcCotacao);

		txtQtdItAprovado.addFocusListener(this);
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
		btCompra.setToolTipText("Comprar todos os ítens.");
		btCancelaItem.setToolTipText("Cancelar ítem.");
		btMotivoCancelaSol.setToolTipText("Motivo do cancelamento da Compra.");
		btMotivoCancelaItem.setToolTipText("Motivo do cancelamento do ítem.");
		btMotivoPrior.setToolTipText("Motivo da prioridade do ítem.");

		pinCab.adic(pinBotCab, 630, 1, 114, 150);
		pinBotCab.adic(btAprovaSol, 0, 0, 110, 30);
		pinBotCab.adic(btFinAprovSol, 0, 31, 110, 30);
		pinBotCab.adic(btCancelaSol, 0, 62, 110, 30);
		pinBotCab.adic(btMotivoCancelaSol, 0, 93, 110, 30);
		pinBotCab.adic(btCompra, 0, 124, 110, 30);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		btAprovaSol.addActionListener(this);
		btCancelaSol.addActionListener(this);
		btCancelaItem.addActionListener(this);
		btCompra.addActionListener(this);
		btMotivoCancelaSol.addActionListener(this);
		btMotivoCancelaItem.addActionListener(this);
		btMotivoPrior.addActionListener(this);
		btFinAprovSol.addActionListener(this);

		pinDet = new JPanelPad(740, 100);
		setPainel(pinDet, pnDet);

		setImprimir(true);

		desabAprov(true);
		desabCot(true);
	}

	private void montaDetalhe() {
		setAltDet(100);
		setListaCampos(lcDet);
		setPainel(pinCab, pnCliCab);
		setNavegador(navCot);
		lcDet.setTabela(tabCot);
		lcDet.setNavegador(navCot);
		navCot.setListaCampos(lcDet);
	
		lcDet.setPodeExc(false);
		lcDet.setPodeIns(false);
		txtCodItSolicitacao.setEditable(false);
		txtCodProd.setEditable(false);
		txtRefProd.setEditable(false);
		txtQtdItAprovado.setEditable(false);
	
		adicCampo(txtCodItSolicitacao, 7, 60, 30, 20, "CodItSol", "Item",
				ListaCampos.DB_PK, true);
		if (comRef()) {
			adicCampo(txtRefProd, 40, 60, 87, 20, "RefProd", "Referência",
					ListaCampos.DB_FK, txtDescProd, true);
			adicCampoInvisivel(txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_SI,
					false);
			txtRefProd.setBuscaAdic(new DLBuscaProd(con, "REFPROD", lcProd2
					.getWhereAdic()));
		} else {
			adicCampo(txtCodProd, 40, 60, 87, 20, "CodProd", "Cód.prod.",
					ListaCampos.DB_FK, txtDescProd, true);
			adicCampoInvisivel(txtRefProd, "RefProd", "Referência",
					ListaCampos.DB_SI, false);
			txtCodProd.setBuscaAdic(new DLBuscaProd(con, "CODPROD", lcProd
					.getWhereAdic()));
		}
	
		adicDescFK(txtDescProd, 130, 60, 297, 20, "DescProd",
				"Descrição do produto");
		adicDB(rgPriod, 635, 156, 100, 65, "PriorItSol", "Prioridade:", true);
		rgPriod.setEnabled(false);
	
		adicCampo(txtQtdItAprovado, 430, 60, 80, 20, "QtdAprovItSol", "Qtd.aprov.",
				ListaCampos.DB_SI, false);
	
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
		lcDet.setWhereAdic("SitAprovItSol <> 'NA' AND SitItSol <> 'CA'");
		lcDet.setQueryInsert(false);
		montaTab();
	
		tabCot.setTamColuna(30, 0);
		tabCot.setTamColuna(80, 1);
		tabCot.setTamColuna(230, 2);
		tabCot.setTamColuna(70, 3);
		tabCot.setTamColuna(70, 4);
		tabCot.setTamColuna(70, 5);
		tabCot.setTamColuna(70, 6);
		tabCot.setTamColuna(70, 7);
		tabCot.setTamColuna(70, 8);
		tabCot.setTamColuna(70, 9);
		tabCot.setTamColuna(70, 10);
	
		nav.setName("Mestre");
		navCot.setNavigationOnly();
		navCot.setName("Detalhe 1");
		navRod.setName("Detalhe 2");
		FlowLayout flNavCot = new FlowLayout(FlowLayout.LEFT, 0, 0);
		JPanelPad pnNavCot = new JPanelPad(JPanelPad.TP_JPANEL, flNavCot);
		pnNavCot.setBorder(null);
		pnNavCot.add(navCot);
		pnNavCot.add(nav);
		pnNavCab.add(pnNavCot, BorderLayout.WEST);
		pinCab.adic(spTabCot, 7, 87, 620, 140);
	
		setListaCampos(lcCotacao);
		setPainel(pinDet, pnDet);
		setNavegador(navRod);
		navRod.setListaCampos(lcCotacao);
		lcCotacao.setNavegador(navRod);
		lcCotacao.setTabela(tab);
	
		txtQtdAprovCot.setSoLeitura(true);
		txtDtCot.setSoLeitura(true);
	
		adicCampo(txtCodCot, 7, 20, 77, 20, "CodCot", "Cód.Cot.",
				ListaCampos.DB_PK, true);
		adicCampoInvisivel(txtCodSol, "CodSol", "Cód.Sol.", ListaCampos.DB_PK, true);
		adicCampo(txtDtCot, 87, 20, 97, 20, "DtCot", "Dt.Cot.", ListaCampos.DB_SI,
				false);
		adicCampoInvisivel(txtIdUsuCot, "IdUsuCot", "Usu.Cot.", ListaCampos.DB_SI,
				false);
		adicCampo(txtCodFor, 187, 20, 77, 20, "CodFor", "Cod.For.",
				ListaCampos.DB_FK, txtDescFor, false);
		adicDescFK(txtDescFor, 267, 20, 197, 20, "RazFor",
				"Razão social do fornecedor");
		adicCampo(txtQtdCot, 467, 20, 87, 20, "QtdCot", "Qtd.Cot.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodProd2, "CodProd", "Cód.prod.", ListaCampos.DB_FK,
				false);
		adicCampoInvisivel(txtRefProd2, "RefProd", "Referência", ListaCampos.DB_FK,
				false);
		adicCampo(txtQtdAprovCot, 7, 60, 87, 20, "QtdAprovCot", "Qtd.Aprov.Cot.",
				ListaCampos.DB_SI, false);
		adicCampo(txtPrecoCot, 97, 60, 87, 20, "PrecoCot", "Preco.Cot.",
				ListaCampos.DB_SI, false);
	
		lcCotacao.montaSql(true, "COTACAO", "CP");
		lcCotacao.montaTab();
		lcCotacao.addInsertListener(this);
		lcCotacao.addCarregaListener(this);
	
		tab.setTamColuna(30, 0);
		tab.setTamColuna(80, 1);
		tab.setTamColuna(230, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(70, 4);
		tab.setTamColuna(70, 5);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(70, 7);
		tab.setTamColuna(70, 8);

		btMotivoPrior.setEnabled(false);

		pinBotDet.adic(btCancelaItem, 0, 0, 110, 28);
		pinBotDet.adic(btMotivoCancelaItem, 0, 29, 110, 28);
		//pinBotDet.adic(btMotivoPrior, 0, 58, 110, 28);
		pinDet.adic(pinBotDet, 630, 1, 114, 90);
		lSitItSol = new JLabelPad();
		lSitItSol.setForeground(Color.WHITE);
		pinLb.adic(lSitItSol, 31, 0, 110, 20);
		pinDet.adic(pinLb, 630, 91, 114, 24);
	}

	private void buscaInfoUsuAtual() {
		String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVCPSOLICITACAOUSU,COMPRASUSU "
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
				String sCotacao = rs.getString("COMPRASUSU");
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

	public void focusGained(FocusEvent fevt) {}

	public void focusLost(FocusEvent fevt) {}

	public void beforeCarrega(CarregaEvent cevt) {}

	public void afterPost(PostEvent pevt) {
		if (pevt.getListaCampos() == lcCampos) {
			lcCampos.carregaDados();
		}
		if (pevt.getListaCampos() == lcDet) {
			lcCampos.carregaDados();
		}
	}

	private void desabCampos(boolean bHab) {
		txtCodProd.setNaoEditavel(bHab);
		txtRefProd.setNaoEditavel(bHab);
		txtQtdItAprovado.setNaoEditavel(bHab);
		txaMotivoSol.setEnabled(!bHab);
		rgPriod.setAtivo(!bHab);
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

	private void desabCot(boolean bHab) {
		btCompra.setEnabled(!bHab);
		txtQtdItAprovado.setNaoEditavel(bHab);
	}

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

	public void afterCarrega(CarregaEvent cevt) {
		buscaInfoUsuAtual();

		String sSitSol = txtStatusSolicitacao.getVlrString();
		String sSitItAprov = txtSituacaoItAprov.getVlrString();
		String sSitItExp = txtSituacaoItComp.getVlrString();
		sSitItSol = txtSituacaoIt.getVlrString();

		boolean bStatusTravaTudo = ((sSitItSol.equals("AF"))
				|| (sSitItSol.equals("EF")) || (sSitItSol.equals("CA")));
		boolean bStatusTravaCot = (!(sSitItSol.equals("AF")));

		if (cevt.getListaCampos() == lcDet) {
			if (sSitItSol.equals("CA")) {
				desabCampos(true);
				btMotivoCancelaItem.setEnabled(true);
			}
		}

		if (rgPriod.getVlrString().equals("A") && sSitSol.equals("PE")) {
			btMotivoPrior.setEnabled(true);
		} else
			btMotivoPrior.setEnabled(false);

		if (sSitSol.equals("CA"))
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

		if (!bCotacao || bStatusTravaCot)
			desabCot(true);
		else {
			desabCot(false);
		}

		if (((cevt.getListaCampos() == lcProd) || (cevt.getListaCampos() == lcProd2))
				&& ((lcDet.getStatus() == ListaCampos.LCS_EDIT) || ((lcDet.getStatus() == ListaCampos.LCS_INSERT)))) {}

		if (sSitItSol.equals("CA")) {
			SitSol = "Cancelado";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(250, 50, 50));
		} else if (sSitItSol.equals("PE")) {
			SitSol = "Pendente";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(255, 204, 51));
		} else if (sSitItExp.equals("ET") || sSitItExp.equals("EP")) {
			SitSol = "Expedido";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(0, 170, 30));
		} else if (sSitItAprov.equals("AT") || sSitItAprov.equals("AP")) {
			SitSol = "Aprovado";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(26, 140, 255));
		}

		if (cevt.getListaCampos() == lcDet) {
			if (txtQtdItAprovado.isEditable()) {
				if (txtQtdAprovCot.getVlrDouble().compareTo(new Double(0)) <= 0)
					txtQtdAprovCot.setVlrDouble(txtQtdItAprovado.getVlrDouble());
			}
		}
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

	private boolean dialogObsCab() {
		boolean bRet = false;
		FObservacao obs = new FObservacao(txaMotivoCancSol.getVlrString());
		if (obs != null) {
			if ((!bAprovaCab) || (sSitItSol.equals("CA")))
				obs.txa.setEnabled(false);
			obs.setVisible(true);
			if (obs.OK) {
				txaMotivoCancSol.setVlrString(obs.getTexto());
				bRet = true;
			}
		}
		obs.dispose();
		return bRet;
	}

	private boolean dialogObsDet() {
		boolean bRet = false;
		FObservacao obs = new FObservacao(txaMotivoCancItem.getVlrString());
		if (obs != null) {
			if ((!bAprovaCab) || (sSitItSol.equals("CA")))
				obs.txa.setEnabled(false);
			obs.setVisible(true);
			if (obs.OK) {
				txaMotivoCancItem.setVlrString(obs.getTexto());
				bRet = true;
			}
		}
		obs.dispose();
		return bRet;
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

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp)
			imprimir(true, txtCodSolicitacao.getVlrInteger().intValue());
		else if (evt.getSource() == btImp)
			imprimir(false, txtCodSolicitacao.getVlrInteger().intValue());
		else if (evt.getSource() == btMotivoCancelaSol) {
			dialogObsCab();
		} else if (evt.getSource() == btMotivoCancelaItem) {
			dialogObsDet();
		} else if (evt.getSource() == btMotivoPrior) {
			dialogObsPrior();
		} else if (evt.getSource() == btCancelaSol) {
			lcCampos.setState(ListaCampos.LCS_EDIT);
			if (Funcoes.mensagemConfirma(null,
					"Deseja cancelar a compra de todos os ítens?") == JOptionPane.YES_OPTION) {
				if (dialogObsCab()) {
					txtStatusSolicitacao.setVlrString("CA");
					lcCampos.post();
				}
			}
		} else if (evt.getSource() == btCancelaItem) {
			lcDet.setState(ListaCampos.LCS_EDIT);
			if (Funcoes.mensagemConfirma(null,
					"Deseja cancelar ítem da compra?") == JOptionPane.YES_OPTION) {
				if (dialogObsDet()) {
					txtSituacaoIt.setVlrString("CA");
					lcDet.post();
				}
			}
		}

		else if (evt.getSource() == btAprovaSol) {
			lcCampos.setState(ListaCampos.LCS_EDIT);
			if (Funcoes
					.mensagemConfirma(
							null,
							"Deseja Aprovar todos os ítens da compra?\n Caso você não tenha informado as quantidades\n a serem aprovadas"
									+ " estará aprovando as quantidades requeridas!") == JOptionPane.OK_OPTION) {
				;
				txtStatusSolicitacao.setVlrString("AT");
				nav.btSalvar.doClick();
			}
		} else if (evt.getSource() == btFinAprovSol) {
			lcCampos.setState(ListaCampos.LCS_EDIT);
			if (Funcoes
					.mensagemConfirma(
							null,
							"Deseja finalizar o processo de aprovação da compra?\n Após este procedimento a compra não poderá mais ser alterada\n"
									+ "e estará disponível para expedição da nota fiscal!") == JOptionPane.OK_OPTION) {
				;
				txtStatusSolicitacao.setVlrString("AF");
				nav.btSalvar.doClick();
			}
		} else if (evt.getSource() == btCompra) {
			lcCampos.setState(ListaCampos.LCS_EDIT);
			if (Funcoes
					.mensagemConfirma(
							null,
							"Deseja cotar todos os ítens da solicitação de compra?\n Caso você não tenha informado as quantidades\n a serem cotadas"
									+ " estará cotando as quantidades aprovadas!") == JOptionPane.OK_OPTION) {
				;
				txtSituacaoItComp.setVlrString("ET");
				nav.btSalvar.doClick();
			}
		}

		super.actionPerformed(evt);
	}

	private void imprimir(boolean bVisualizar, int iCodSol) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		DLRPedido dl = new DLRPedido(sOrdSol);
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		imp.verifLinPag();
		imp.montaCab();
		/*
		 * String sSQL = "SELECT (SELECT COUNT(IC.CODITSOL) FROM CPITSOLICITACAO IC
		 * WHERE IC.CODSOL=S.CODSOL)," +
		 * "S.CODSOL,S.DTEMITSOL,S.SITSOL,S.MOTIVOSOL," + "I.CODPROD, I.QTDITSOL,
		 * I.QTDAPROVITSOL,I.SITAPROVITSOL, I.SITCOMPITSOL, I.SITITSOL," +
		 * "P.REFPROD,P.DESCPROD, P.CODUNID," + "A.CODALMOX, A.DESCALMOX, CC.CODCC,
		 * CC.ANOCC" + " FROM CPSOLICITACAO S, CPITSOLICITACAO I, EQALMOX A, FNCC
		 * CC, EQPRODUTO P" + " WHERE S.CODSOL=" + iCodSol + " AND
		 * I.CODSOL=S.CODSOL" + " AND P.CODPROD=I.CODPROD" + " AND
		 * I.CODALMOX=I.CODALMOX" + " AND CC.CODCC=I.CODCC" + " ORDER BY
		 * S.CODSOL,P." + dl.getValor() + ";";
		 */
		imp.setTitulo("Relatório de Solicitação de Compras");
/*
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
*/		
		String sSQL = "SELECT  (SELECT COUNT(IT.CODITRMA) FROM EQITRMA IT "
				+ " WHERE IT.CODEMP=R.CODEMP AND IT.CODFILIAL = R.CODFILIAL AND IT.CODRMA=R.CODRMA),"
				+ "R.CODRMA,R.DTINS,R.SITRMA,R.MOTIVORMA,R.IDUSU,R.IDUSUAPROV,R.IDUSUEXP,R.DTAAPROVRMA,R.DTAEXPRMA,R.MOTIVOCANCRMA,"
				+ "I.CODPROD, I.QTDITRMA, I.QTDAPROVITRMA, I.QTDEXPITRMA, I.SITITRMA,"
				+ "I.SITITRMA,I.SITAPROVITRMA,I.SITEXPITRMA,I.CODITRMA,"
				+ "P.REFPROD,P.DESCPROD, P.CODUNID,"
				+ "A.CODALMOX, A.DESCALMOX, CC.CODCC, CC.ANOCC, CC.DESCCC,"
				+ "(SELECT U.CODCC FROM SGUSUARIO U WHERE U.IDUSU=R.IDUSUAPROV),"
				+ "(SELECT C.DESCCC FROM FNCC C, SGUSUARIO U "
				+ "WHERE C.CODEMP=U.CODEMPCC AND C.CODFILIAL=U.CODEMPCC AND C.ANOCC=U.ANOCC "
				+ " AND C.CODCC=U.CODCC AND U.IDUSU=R.IDUSUAPROV),"
				+ "(SELECT C.DESCCC FROM FNCC C, SGUSUARIO U "
				+ "WHERE C.CODEMP=U.CODEMPCC AND C.CODFILIAL=U.CODEMPCC AND C.ANOCC=U.ANOCC "
				+ " AND C.CODCC=U.CODCC AND U.IDUSU=R.IDUSUEXP),"
				+ "(SELECT U.CODCC FROM SGUSUARIO U WHERE U.IDUSU=R.IDUSUEXP),"
				+ " I.MOTIVOCANCITRMA, I.CODPROD , R.CODOP, R.SEQOP"
				+ " FROM EQRMA R, EQITRMA I, EQALMOX A, FNCC CC, EQPRODUTO P"
				+ " WHERE R.CODEMP=? AND R.CODFILIAL=? AND R.CODRMA=?"
				+ " AND I.CODEMP=R.CODEMP AND I.CODFILIAL=R.CODFILIAL AND I.CODRMA=R.CODRMA"
				+ " AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD"
				+ " AND I.CODEMP=R.CODEMP AND I.CODFILIAL=R.CODFILIAL "
				+ " AND CC.CODEMP=R.CODEMPCC AND CC.CODFILIAL=R.CODFILIALCC AND CC.CODCC=R.CODCC"
				+ " AND A.CODEMP=I.CODEMPAX AND A.CODFILIAL=I.CODFILIALAX AND A.CODALMOX=I.CODALMOX "
				+ " ORDER BY R.CODRMA,P." + dl.getValor() + ";";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(sSQL);

			ps.setInt(1, lcCampos.getCodEmp());
			ps.setInt(2, lcCampos.getCodFilial());
			ps.setInt(3, txtCodSolicitacao.getVlrInteger().intValue());

			rs = ps.executeQuery();

			imp.limpaPags();

			while (rs.next()) {
				if (imp.pRow() >= (linPag - 1)) {
					imp.incPags();
					imp.eject();
				}
				if (imp.pRow() == 0) {
					imp.impCab(136, true);
					imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("=", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "R.M.A.   No.: ");
					imp.say(imp.pRow() + 0, 19, rs.getString("CODRMA"));
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Requisitante: ");
					imp.say(imp.pRow() + 0, 19, rs.getString("IDUSU"));
					imp.say(imp.pRow() + 0, 30, "- C.C.: ");
					imp.say(imp.pRow() + 0, 38, (rs.getString("CODCC") != null ? rs
							.getString("CODCC").trim() : ""));
					imp.say(imp.pRow() + 0, 62, "-"
							+ (rs.getString("DESCCC") != null ? rs.getString("DESCCC").trim()
									: ""));
					imp.say(imp.pRow() + 0, 113, "- Data : ");
					imp.say(imp.pRow() + 0, 123, Funcoes.sqlDateToStrDate(rs
							.getDate("DTINS")));
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Aprovação   : ");
					imp.say(imp.pRow() + 0, 19, rs.getString("IDUSUAPROV"));
					imp.say(imp.pRow() + 0, 30, "- C.C.: ");
					imp.say(imp.pRow() + 0, 38, (rs.getString(29) != null ? rs.getString(
							29).trim() : ""));
					imp.say(imp.pRow() + 0, 62, "-"
							+ (rs.getString(30) != null ? rs.getString(30).trim() : ""));
					imp.say(imp.pRow() + 0, 113, "- Data : ");
					imp.say(imp.pRow() + 0, 123, Funcoes.sqlDateToStrDate(rs
							.getDate("DTAAPROVRMA")));
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Expedição   : ");
					imp.say(imp.pRow() + 0, 19, rs.getString("IDUSUEXP"));
					imp.say(imp.pRow() + 0, 30, "- C.C.: ");
					imp.say(imp.pRow() + 0, 38, (rs.getString(31) != null ? rs.getString(
							31).trim() : ""));
					imp.say(imp.pRow() + 0, 62, "-"
							+ (rs.getString(32) != null ? rs.getString(32).trim() : ""));
					imp.say(imp.pRow() + 0, 113, "- Data : ");
					imp.say(imp.pRow() + 0, 123, Funcoes.sqlDateToStrDate(rs
							.getDate("DTAEXPRMA")));
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "O.P/OS.:");
					imp.say(imp.pRow() + 0, 13, rs.getString("CodOP") != null ? rs
							.getString("CodOP").trim() : "");
					imp.say(imp.pRow() + 0, 25, "Seq. O.P.:");
					imp.say(imp.pRow() + 0, 37, rs.getString("SeqOP") != null ? rs
							.getString("SeqOP").trim() : "");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("=", 133) + "|");

					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 57, "DADOS DO(S) PRODUTO(S)");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 2, "Item");
					imp.say(imp.pRow() + 0, 8, "Referencia");
					imp.say(imp.pRow() + 0, 22, "Descrição dos produtos");
					imp.say(imp.pRow() + 0, 60, "Qtd.req.");
					imp.say(imp.pRow() + 0, 75, "Qtd.aprov.");
					imp.say(imp.pRow() + 0, 90, "Qtd.exp.");
					imp.say(imp.pRow() + 0, 100, "Sit.item");
					imp.say(imp.pRow() + 0, 110, "Sit.aprov.");
					imp.say(imp.pRow() + 0, 122, "Sit.exp.");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 136, "|");
				}
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "|");
				imp.say(imp.pRow() + 0, 2, rs.getString("CODITRMA"));
				imp.say(imp.pRow() + 0, 8, rs.getString("REFPROD"));
				imp.say(imp.pRow() + 0, 22, rs.getString("DESCPROD").substring(0, 37));
				imp.say(imp.pRow() + 0, 60, "" + rs.getDouble("QTDITRMA"));
				imp.say(imp.pRow() + 0, 75, "" + rs.getDouble("QTDAPROVITRMA"));
				imp.say(imp.pRow() + 0, 90, "" + rs.getDouble("QTDEXPITRMA"));
				if (!rs.getString("SITITRMA").equals("CA"))
					imp.say(imp.pRow() + 0, 105, "" + rs.getString("SITITRMA"));
				if (!rs.getString("SITAPROVITRMA").equals("NA"))
					imp.say(imp.pRow() + 0, 115, "" + rs.getString("SITAPROVITRMA"));
				if (!rs.getString("SITEXPITRMA").equals("NE"))
					imp.say(imp.pRow() + 0, 125, "" + rs.getString("SITEXPITRMA"));
				imp.say(imp.pRow() + 0, 136, "|");

				if ((rs.getString("SITITRMA").equals("CA"))
						|| (rs.getString("SITAPROVITRMA").equals("NA"))
						|| (rs.getString("SITEXPITRMA").equals("NE"))) {
					if (comRef())
						vProdCan.addElement(rs.getString("REFPROD"));
					else
						vProdCan.addElement(rs.getString("CODPROD"));
					vItem.addElement(rs.getString("CODITRMA"));
					vMotivoCan.addElement(rs.getString("MOTIVOCANCRMA") != null ? rs
							.getString("MOTIVOCANCRMA") : "");
					cont++;
				}

			}
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("=", 133) + "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 1, "|");
			imp.say(imp.pRow() + 0, 57, "INFORMAÇÕES ADICIONAIS");
			imp.say(imp.pRow() + 0, 136, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 1, "|");
			imp.say(imp.pRow() + 0, 136, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 1, "|");
			imp.say(imp.pRow() + 0, 2, "MOTIVO DA REQUISIÇÃO: ");
			String sMotivoRMA = (rs.getString("MOTIVORMA") != null ? rs
					.getString("MOTIVORMA") : "").trim();
			imp.say(imp.pRow() + 0, 26, sMotivoRMA.substring(0,
					sMotivoRMA.length() > 109 ? 109 : sMotivoRMA.length()));
			imp.say(imp.pRow() + 0, 136, "|");
			if (cont > 0) {
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "|");
				imp.say(imp.pRow() + 0, 4, "ITENS NÃO EXPEDIDOS:");
				imp.say(imp.pRow() + 0, 136, "|");
				for (int i = 0; vProdCan.size() > i; i++) {
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, vItem.elementAt(i).toString());
					imp.say(imp.pRow() + 0, 9, vProdCan.elementAt(i).toString());
					String sMotivoCanc = vMotivoCan.elementAt(i).toString();

					imp.say(imp.pRow() + 0, 25, "- "
							+ sMotivoCanc.substring(0, sMotivoCanc.length() > 108 ? 108
									: sMotivoCanc.length()));
					imp.say(imp.pRow() + 0, 136, "|");
				}
			}

			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133) + "+");
			imp.say(imp.pRow() + 2, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 52, Funcoes.replicate("_", 41));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 62, "Ass. do requisitante");

			imp.eject();

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

	public void beforePost(PostEvent pevt) {
		String sMotvProir = rgPriod.getVlrString();
		if (pevt.getListaCampos() == lcDet) {
			if (txtQtdAprovCot.getVlrDouble().doubleValue() > txtQtdCot
					.getVlrDouble().doubleValue()) {
				Funcoes.mensagemInforma(null,
						"Quantidade aprovada maior que a cotada!");
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
			if (sMotvProir.equals("A")) {
				dialogObsPrior();
			}
		} else if (pevt.getListaCampos() == lcCampos) {
			txtOrigSolicitacao.setVlrString("AX");
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
		super.setConexao(cn);
		bPrefs = prefs();
		montaDetalhe();

		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC=" + buscaVlrPadrao());
		lcUsu.setConexao(cn);
		lcFor.setConexao(cn);
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

		carregaWhereAdic();
	}

	public Color cor(int r, int g, int b) {
		Color color = new Color(r, g, b);
		return color;
	}
}