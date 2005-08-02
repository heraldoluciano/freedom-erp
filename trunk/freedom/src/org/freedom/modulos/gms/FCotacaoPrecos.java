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
	private JTextFieldPad txtRefProd2 = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 13, 0);
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

		pinDet = new JPanelPad(740, 100);
		setPainel(pinDet, pnDet);
	
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
	    JPanelPad pnNavCot = new JPanelPad(JPanelPad.TP_JPANEL,flNavCot);
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
		adicCampo(txtQtdAprovCot, 557, 20, 87, 20, "QtdAprovCot",
				"Qtd.Aprov.Cot.", ListaCampos.DB_SI, false);
		adicCampo(txtPrecoCot, 647, 20, 87, 20, "PrecoCot", "Preco.Cot.",
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
	}

	public void focusGained(FocusEvent fevt) {}

	public void focusLost(FocusEvent fevt) {}

	public void beforeCarrega(CarregaEvent cevt) {}

	public void afterPost(PostEvent pevt) {}

	public void afterCarrega(CarregaEvent cevt) {}

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
					+ err.getMessage(),true,con,err);
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
					+ err.getMessage(),true,con,err);
		}
		return bRetorno;
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		super.keyReleased(kevt);
	}

	public void beforePost(PostEvent pevt) {
		if (lcCotacao.getStatus() == ListaCampos.LCS_INSERT) {
			txtSituacaoItComp.setVlrString("PE");
			txtSituacaoItAprov.setVlrString("PE");
		}
	}

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
							+ err.getMessage(),true,con,err);
		}
		return iRet;
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
		super.setConexao(cn);
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

		/*
		 * lcProd.setWhereAdic(sWhereAdicProd);
		 * lcProd2.setWhereAdic(sWhereAdicProd);
		 */

		carregaWhereAdic();
	}
}