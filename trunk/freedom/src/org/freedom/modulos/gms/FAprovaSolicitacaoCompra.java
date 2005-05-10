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
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
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
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.modulos.std.DLRPedido;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLInputText;
import org.freedom.telas.FDetalhe;

public class FAprovaSolicitacaoCompra extends FDetalhe implements PostListener,
		CarregaListener, FocusListener, ActionListener, InsertListener {

	private int casasDec = Aplicativo.casasDec;
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
	private ImageIcon imgOk = Icone.novo("btOk.gif");
	private ImageIcon imgCancel = Icone.novo("btExcluir.gif");
	private JButton btCancelaCompra = new JButton("Solicitação pendente", null);
	private JButton btAprovaItem = new JButton("Item pendente", null);
	private JButton btCancelaItem = new JButton("Cancelar", imgCancel);
	private JTextFieldPad txtCodSolicitacao = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtStatusSolicitacao = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituaçãoItAprov = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituaçãoItComp = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituaçãoIt = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtDtEmitSolicitacao = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtDtAptovItSol = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtCodItSolicitacao = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtQtdItSolicitado = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtQtdItAprovado = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtCodCancUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldPad txtCodAprovUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldPad txtUsuCanc = new JTextFieldPad(JTextFieldPad.TP_STRING,8, 0);
	private JTextFieldPad txtUsuCancIt = new JTextFieldPad(	JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,8, 0);
	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,	19, 0);
	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10, 0);
	private JTextFieldPad txtOrigSolicitacao = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextAreaPad txaMotivoSolicitacao = new JTextAreaPad();
	private JTextFieldPad txaMotivoCanc = new JTextFieldPad(JTextFieldPad.TP_STRING, 10000, 0);
	private JTextFieldPad txaMotivoAprovIt = new JTextFieldPad(JTextFieldPad.TP_STRING, 10000, 0);
	private JTextFieldPad txaMotivoCancIt = new JTextFieldPad(JTextFieldPad.TP_STRING, 10000, 0);
	private JScrollPane spnMotivo = new JScrollPane(txaMotivoSolicitacao);
	private ListaCampos lcAlmox = new ListaCampos(this, "AM");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcUsuario = new ListaCampos(this, "");
	private ListaCampos lcUsuario2 = new ListaCampos(this, "");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	String sOrdNota = "";
	Integer anoCC = null;
	String codCC = null;
	Integer codAlmox = null;
	String aprovSolicitacaoCompra = "";

	public FAprovaSolicitacaoCompra() {
		setTitulo("Aprova Solicitação de Compra");
		setAtribos(15, 10, 760, 580);

		pnMaster.remove(2);
		pnGImp.removeAll();
		pnGImp.setLayout(new GridLayout(1, 3));
		pnGImp.setPreferredSize(new Dimension(220, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);

		pnMaster.add(spTab, BorderLayout.CENTER);

		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd", "Descrição do produto",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",ListaCampos.DB_SI, false));
		lcProd.setWhereAdic("ATIVOPROD='S'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);

		txtCodProd.setTabelaExterna(lcProd);

		lcProd2.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",	ListaCampos.DB_PK, false));
		lcProd2.add(new GuardaCampo(txtDescProd, "DescProd","Descrição do produto", ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",ListaCampos.DB_SI, false));
		lcProd2.setWhereAdic("ATIVOPROD='S'");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);

		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		txtRefProd.setTabelaExterna(lcProd2);

		lcAlmox.add(new GuardaCampo(txtCodAlmoxarife, "CodAlmox", "Cod.almox.",ListaCampos.DB_PK, false));
		lcAlmox.add(new GuardaCampo(txtDescAlmoxarife, "DescAlmox", "Descrição do almoxarifado;",ListaCampos.DB_SI, false));
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);

		txtDescAlmoxarife.setSoLeitura(true);
		txtCodAlmoxarife.setTabelaExterna(lcAlmox);

		txtCodUsu.setNomeCampo("IdUsuItSol");

		lcUsuario.add(new GuardaCampo(txtCodUsu, "IDUSU", "ID usuário",ListaCampos.DB_PK, false));
		lcUsuario.add(new GuardaCampo(txtNomeUsu, "NOMEUSU", "Nome",ListaCampos.DB_SI, false));
		lcUsuario.montaSql(false, "USUARIO", "SG");
		lcUsuario.setQueryCommit(false);
		lcUsuario.setReadOnly(true);

		txtCodUsu.setTabelaExterna(lcUsuario);

		txtCodCancUsu.setNomeCampo("IdUsuCancItSol");
		txtCodAprovUsu.setNomeCampo("IdUsuAprovItSol");

		lcUsuario2.add(new GuardaCampo(txtCodUsu, "IDUSU", "ID usuário",ListaCampos.DB_PK, false));
		lcUsuario2.add(new GuardaCampo(txtNomeUsu, "NOMEUSU", "Nome do usuário",ListaCampos.DB_SI, false));
		lcUsuario2.montaSql(false, "USUARIO", "SG");
		lcUsuario2.setQueryCommit(false);
		lcUsuario2.setReadOnly(true);

		txtCodCancUsu.setTabelaExterna(lcUsuario2);
		txtCodAprovUsu.setTabelaExterna(lcUsuario2);

		lcCC.add(new GuardaCampo(txtCodCC, "codCC", "Cód.c.c.", ListaCampos.DB_PK,false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC", "Descriçao do centro de custo",ListaCampos.DB_SI, false));
		lcCC.montaSql(false, "CC", "FN");
		lcCC.setQueryCommit(false);
		lcCC.setReadOnly(true);

		txtCodCC.setTabelaExterna(lcCC);

		pinCab = new JPanelPad(740, 180);
		setListaCampos(lcCampos);
		setAltCab(180);
		setPainel(pinCab, pnCliCab);

		txtDtEmitSolicitacao.setSoLeitura(true);
		txaMotivoSolicitacao.setEditable(false);
		txaMotivoSolicitacao.setEnabled(false);
		adicCampo(txtCodSolicitacao, 7, 20, 100, 20, "CodSol", "Nº solicitação", ListaCampos.DB_PK, true);
		adicCampo(txtDtEmitSolicitacao, 110, 20, 100, 20, "DtEmitSol","Data solicitação.", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtStatusSolicitacao, "SitSol", "Descrição da situação", ListaCampos.DB_SI, false);
		adic(btCancelaCompra, 519, 15, 217, 30);
		btCancelaCompra.setEnabled(false);
		adicDBLiv(txaMotivoSolicitacao, "MotivoSol", "Motivo", false);
		adicCampoInvisivel(txaMotivoCanc, "MotivoCancSol", "Motivo de cancelamento", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtUsuCanc, "IDUsuCancSol", "Usuário que cancelou", ListaCampos.DB_SI, false);
		adic(new JLabelPad("Motivo"), 7, 40, 100, 20);
		adic(spnMotivo, 7, 60, 727, 77);
		adicCampoInvisivel(txtOrigSolicitacao, "OrigSol", "Origem", ListaCampos.DB_SI, false);
		setListaCampos(true, "SOLICITACAO", "CP");
		lcCampos.setQueryInsert(false);

		txtQtdItSolicitado.addFocusListener(this);
		lcCampos.addPostListener(this);
		lcCampos.addCarregaListener(this);
		lcProd.addCarregaListener(this);
		lcDet.addPostListener(this);
		lcDet.addCarregaListener(this);
		lcDet.addInsertListener(this);
		lcCampos.addInsertListener(this);
		btCancelaCompra.addActionListener(this);
		btAprovaItem.addActionListener(this);
		btCancelaItem.addActionListener(this);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
	    setImprimir(true);
	}

	private void montaDetalhe() {
		setAltDet(100);
		pinDet = new JPanelPad(740, 100);
		setPainel(pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

		txtCodProd.setSoLeitura(true);
		adicCampo(txtCodItSolicitacao, 7, 20, 30, 20, "CodItSol", "Item", ListaCampos.DB_PK, true);
		if (comRef()) {
			adicCampoInvisivel(txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, false);
			adicCampoInvisivel(txtRefProd, "RefProd", "Referência", ListaCampos.DB_FK, false);
			adic(new JLabelPad("Referência"), 40, 0, 67, 20);
			txtRefProd.setSoLeitura(true);
			adic(txtRefProd, 40, 20, 67, 20);
			txtRefProd.setFK(true);
		  	txtRefProd.setBuscaAdic(new DLBuscaProd(con,"REFPROD",lcProd2.getWhereAdic()));
		} else {
			adicCampo(txtCodProd, 40, 20, 87, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, false);
		  	txtCodProd.setBuscaAdic(new DLBuscaProd(con,"CODPROD",lcProd.getWhereAdic()));
		}

		txtCodUsu.setSoLeitura(true);
		txtCodCC.setSoLeitura(true);
		adicCampo(txtCodCC, 7, 60, 70, 20, "CodCC", "Cód.c.c.", ListaCampos.DB_FK, txtDescCC, false);
		adicDescFK(txtDescCC, 80, 60, 180, 20, "DescCC","Descrição do centro de custo");
		adicCampoInvisivel(txtAnoCC, "anoCC", "Ano c.c.", ListaCampos.DB_SI, false);
		adicCampo(txtCodUsu, 263, 60, 70, 20, "IdUsuItSol", "Cód.usu.", ListaCampos.DB_FK, txtNomeUsu, false);
		adicDescFK(txtNomeUsu, 336, 60, 144, 20, "NomeUsu","Nome do solicitante");
		adic(btAprovaItem, 485, 55, 217, 30);
		btAprovaItem.setEnabled(false);
		adic(btCancelaItem, 615, 55, 125, 30);
		btCancelaItem.setVisible(false);
		btCancelaItem.setEnabled(false);
		txtCodAlmoxarife.setSoLeitura(true);
		adicCampo(txtCodAlmoxarife, 480, 20, 67, 20, "CodAlmox", "Cód.almox.", ListaCampos.DB_FK, txtDescAlmoxarife, false);
		adicDescFK(txtDescAlmoxarife, 550, 20, 182, 20, "DescAlmox","Descrição do almoxarifado");
		txtQtdItSolicitado.setSoLeitura(true);
		adicDescFK(txtDescProd, 130, 20, 197, 20, "DescProd","Descrição do produto");
		adicCampo(txtQtdItSolicitado, 330, 20, 67, 20, "QtdItSol", "Qtd.solic.", ListaCampos.DB_SI, false);
		adicCampo(txtQtdItAprovado, 400, 20, 77, 20, "QtdAprovItSol", "Qtd.aprov.", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituaçãoIt, "SitItSol", "Sit. do item", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituaçãoItComp, "SitCompItSol", "Sit.compra", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituaçãoItAprov, "SitAprovItSol","Sit.aprovação", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodAprovUsu, "IdUsuAprovItSol", "Usuário que aprovou", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtDtAptovItSol, "DtAprovItSol", "Data aprovação", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txaMotivoAprovIt, "MotivoItSol", "Motivo da aprovação parcial do item", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txaMotivoCancIt, "MotivoItSol", "Motivo de cancelamento do item", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtUsuCancIt, "IDUsuCancItSol", "Usuário que cancelou o item", ListaCampos.DB_SI, false);
		setListaCampos(true, "ITSOLICITACAO", "CP");
		lcDet.setQueryInsert(false);
		montaTab();

		tab.setTamColuna(30, 0);
		tab.setTamColuna(80, 1);
		tab.setTamColuna(230, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(230, 4);
		tab.setTamColuna(70, 5);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(230, 7);
		tab.setTamColuna(70, 8);
		tab.setTamColuna(230, 9);
		tab.setTamColuna(70, 10);
	}

	public void focusGained(FocusEvent fevt) {}

	public void focusLost(FocusEvent fevt) {}

	public void beforeCarrega(CarregaEvent cevt) {

	}

	public void afterPost(PostEvent pevt) {
		CarregaEvent cevt = new CarregaEvent(pevt.getListaCampos());

		afterCarrega(cevt);
	}

	public void afterCarrega(CarregaEvent cevt) {
		boolean allow = aprovSolicitacaoCompra.equalsIgnoreCase("TD");
		boolean allowItems = (aprovSolicitacaoCompra.equalsIgnoreCase("CC") && txtCodCC
				.getVlrString().equals(codCC))
				|| allow;
		boolean block = txtStatusSolicitacao.getVlrString().equalsIgnoreCase("PE")
				|| allow;
		boolean blockItems = (block && txtSituaçãoIt.getVlrString()
				.equalsIgnoreCase("PE"))
				|| allowItems;

		if (cevt.getListaCampos() == lcDet) {
			if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("PE")) {
				if (allow || allowItems) {
					btAprovaItem.setEnabled(true);
					btAprovaItem.setIcon(imgOk);
					btAprovaItem.setText("Aprovar");
					btAprovaItem.setSize(125, 30);
					btCancelaItem.setEnabled(true);
					btCancelaItem.setVisible(true);
				} else {
					btCancelaItem.setEnabled(false);
					btCancelaItem.setVisible(false);
					btAprovaItem.setSize(253, 30);
					btAprovaItem.setEnabled(false);
					btAprovaItem.setIcon(null);
					btAprovaItem.setText("Item Pendente");
				}
				if (txtQtdItAprovado.getVlrDouble().doubleValue() == 0) {
					txtQtdItAprovado.setVlrDouble(txtQtdItSolicitado.getVlrDouble());
				}
			} else if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("CA")) {
				btCancelaItem.setEnabled(false);
				btCancelaItem.setVisible(false);
				btAprovaItem.setSize(253, 30);
				btAprovaItem.setEnabled(false);
				btAprovaItem.setIcon(null);
				btAprovaItem.setText("Item cancelado");
			} else if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("SC")) {
				btCancelaItem.setEnabled(false);
				btCancelaItem.setVisible(false);
				btAprovaItem.setSize(253, 30);
				btAprovaItem.setEnabled(false);
				btAprovaItem.setIcon(null);
				if (txtSituaçãoItAprov.getVlrString().equalsIgnoreCase("AT"))
					btAprovaItem.setText("Aprovado Totalmente");
				else if (txtSituaçãoItAprov.getVlrString().equalsIgnoreCase("AP"))
					btAprovaItem.setText("Aprovado Parcialmente");
				else
					btAprovaItem.setText("Item concluído");
			}
		} else if (cevt.getListaCampos() == lcCampos) {
			if (aprovSolicitacaoCompra.equalsIgnoreCase("PE")) {
				if (aprovSolicitacaoCompra.equalsIgnoreCase("TD")) {
					btCancelaCompra.setEnabled(true);
					btCancelaCompra.setIcon(imgCancel);
					btCancelaCompra.setText("Cancelar solicitação");
				} else {
					btCancelaCompra.setEnabled(false);
					btCancelaCompra.setIcon(null);
					btCancelaCompra.setText("Solicitação pendente");
				}
				lcDet.setReadOnly(false);
			} else if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("CA")) {
				btCancelaCompra.setEnabled(false);
				btCancelaCompra.setIcon(null);
				btCancelaCompra.setText("Solicitação cancelada");
				lcDet.setReadOnly(true);
			} else if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("SC")) {
				btCancelaCompra.setEnabled(false);
				btCancelaCompra.setIcon(null);
				btCancelaCompra.setText("Solicitação concluída");
				lcDet.setReadOnly(true);
			} else if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("TM")) {
				btCancelaCompra.setEnabled(false);
				btCancelaCompra.setIcon(null);
				btCancelaCompra.setText("Cotação de preços");
				lcDet.setReadOnly(true);
			}
		}
	}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (kevt.getSource() == txtQtdItAprovado) {
				//Talvez este possa ser
				// o ultimo campo do
				// itvenda.
				btAprovaItem.doClick();
				if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
					lcDet.post();
					txtCodItSolicitacao.requestFocus();
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
		else if (evt.getSource() == btCancelaCompra) {
			if (lcCampos.getStatus() != ListaCampos.LCS_EDIT)
				lcCampos.edit();
			txtStatusSolicitacao.setVlrString("CA");
			btCancelaCompra.setEnabled(false);
			btCancelaCompra.setIcon(null);
			btCancelaCompra.setText("Solicitação cancelada");

			DLInputText dl = null;
			dl = new DLInputText(this, "Motivo do cancelamento da solicitação", true);
			dl.setTexto("");
			dl.setVisible(true);
			if ((dl.OK ) && (!dl.getTexto().trim().equals(""))) {
				txaMotivoCanc.setVlrString(dl.getTexto().trim());
				txtUsuCanc.setVlrString(Aplicativo.strUsuario);
			}

		} else if (evt.getSource() == btAprovaItem) {
			if (lcDet.getStatus() != ListaCampos.LCS_EDIT)
				lcDet.edit();
			btCancelaItem.setEnabled(false);
			btCancelaItem.setVisible(false);
			btAprovaItem.setSize(253, 30);
			btAprovaItem.setEnabled(false);
			btAprovaItem.setIcon(null);
			if (txtQtdItAprovado.getVlrDouble().doubleValue() == txtQtdItSolicitado
					.getVlrDouble().doubleValue()) {
				txtSituaçãoItAprov.setVlrString("AT");
				txtSituaçãoItComp.setVlrString("PE");
				txtSituaçãoIt.setVlrString("SC");
				btAprovaItem.setText("Aprovação total");
				txtCodAprovUsu.setVlrString(Aplicativo.strUsuario);
			} else if (txtQtdItAprovado.getVlrDouble().doubleValue() > 0) {
				txtSituaçãoItAprov.setVlrString("AP");
				txtSituaçãoItComp.setVlrString("PE");
				txtSituaçãoIt.setVlrString("SC");
				btAprovaItem.setText("Aprovação parcial");
				txtCodAprovUsu.setVlrString(Aplicativo.strUsuario);

				DLInputText dl = null;
				dl = new DLInputText(this, "Motivo da aprovação parcial", true);
				dl.setTexto("");
				dl.setVisible(true);
				if ((dl.OK ) && (!dl.getTexto().trim().equals(""))) {
					txaMotivoAprovIt.setVlrString(dl.getTexto().trim());
				}

			} else if (txtQtdItAprovado.getVlrDouble().doubleValue() == 0) {
				txtSituaçãoItAprov.setVlrString("NA");
				txtSituaçãoItComp.setVlrString("PE");
				txtSituaçãoIt.setVlrString("CA");
				btAprovaItem.setText("Solicitação cancelada");
				txtCodCancUsu.setVlrString(Aplicativo.strUsuario);

				DLInputText dl = null;
				dl = new DLInputText(this, "Motivo do cancelamento", true);
				dl.setTexto("");
				dl.setVisible(true);
				if ((dl.OK ) && (!dl.getTexto().trim().equals(""))) {
					txaMotivoCancIt.setVlrString(dl.getTexto().trim());
					txtUsuCancIt.setVlrString(Aplicativo.strUsuario);
				}
			}
			txtDtAptovItSol.setVlrDate(new Date());
		} else if (evt.getSource() == btCancelaItem) {
			if (lcDet.getStatus() != ListaCampos.LCS_EDIT)
				lcDet.edit();
			txtSituaçãoItAprov.setVlrString("NA");
			txtSituaçãoItComp.setVlrString("PE");
			txtSituaçãoIt.setVlrString("CA");
			btCancelaItem.setEnabled(false);
			btCancelaItem.setVisible(false);
			btAprovaItem.setSize(253, 30);
			btCancelaCompra.setEnabled(false);
			btCancelaCompra.setIcon(null);
			btCancelaCompra.setText("Solicitação cancelada");
			txtQtdItAprovado.setVlrDouble(new Double(0));
			txtCodCancUsu.setVlrString(Aplicativo.strUsuario);
			txtDtAptovItSol.setVlrDate(new Date());

			DLInputText dl = null;
			dl = new DLInputText(this, "Motivo do cancelamento", true);
			dl.setTexto("");
			dl.setVisible(true);
			if ((dl.OK ) && (!dl.getTexto().trim().equals(""))) {
				txaMotivoCancIt.setVlrString(dl.getTexto().trim());
				txtUsuCancIt.setVlrString(Aplicativo.strUsuario);
			}
		}
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
		if (pevt.getListaCampos() == lcDet) {}
	}

	public void beforeInsert(InsertEvent ievt) {}

	public void afterInsert(InsertEvent ievt) {}

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

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		montaDetalhe();
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcAlmox.setConexao(cn);
		lcUsuario.setConexao(cn);
		lcUsuario2.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC=" + buscaAnoBaseCC());

		lcCampos.setPodeExc(false);
		lcCampos.setPodeIns(false);
		lcDet.setPodeExc(false);
		lcDet.setPodeIns(false);

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
					+ err.getMessage(),true,con,err);
		}

	}
}