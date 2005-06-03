/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda. Robson Sanchez e Fernando Oliveira da
 *         Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FTipoMov.java <BR>
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
 * Comentários sobre a classe...
 *  
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FTabDados;

public class FTipoMov extends FTabDados implements RadioGroupListener,
		CheckBoxListener {
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov2 = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtDescTipoMov = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodModNota = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodSerie = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 4, 0);

	private JTextFieldPad txtCodTab = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtEspecieTipomov = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 4, 9);

	private JTextFieldFK txtDescModNota = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 30, 0);

	private JTextFieldFK txtDescSerie = new JTextFieldFK(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescTab = new JTextFieldFK(JTextFieldPad.TP_STRING,
			40, 0);

	private JTextFieldFK txtDescTipoMov2 = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtIDUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,
			8, 0);

	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);

	private ListaCampos lcModNota = new ListaCampos(this, "MN");

	private ListaCampos lcSerie = new ListaCampos(this, "SE");

	private ListaCampos lcTab = new ListaCampos(this, "TB");

	private ListaCampos lcTipoMov = new ListaCampos(this, "TM");

	private JPanelPad pinGeral = new JPanelPad(JPanelPad.TP_JPANEL,
			new BorderLayout());

	private JPanelPad pnGeral = new JPanelPad(JPanelPad.TP_JPANEL,
			new BorderLayout());

	private JPanelPad pinCamposGeral = new JPanelPad(430, 520);

	private JPanelPad pnRestricoes = new JPanelPad(JPanelPad.TP_JPANEL,
			new BorderLayout());

	private JPanelPad pinDetRestricoes = new JPanelPad(JPanelPad.TP_JPANEL,
			new BorderLayout());

	private Tabela tbRestricoes = new Tabela();

	private JPanelPad pinNavRestricoes = new JPanelPad(680, 30);

	private Navegador navRestricoes = new Navegador(true);

	private ListaCampos lcRestricoes = new ListaCampos(this, "");

	private ListaCampos lcUsu = new ListaCampos(this, "US");

	private JScrollPane spnRestricoes = new JScrollPane(tbRestricoes);

	private JPanelPad pinCamposRestricoes = new JPanelPad(430, 520);

	private Vector vVals = new Vector();

	private Vector vLabs = new Vector();

	private Vector vValsES = new Vector();

	private Vector vLabsES = new Vector();

	private JComboBoxPad cbTipoMov = null;

	private JRadioGroup rgESTipoMov = null;

	private JCheckBoxPad chbRestritoTipoMov = new JCheckBoxPad(
			"Permitir todos os usuários?", "S", "N");

	private JCheckBoxPad chbFiscalTipoMov = new JCheckBoxPad("Lanc.fiscal?",
			"S", "N");

	private JCheckBoxPad chbEstoqTipoMov = new JCheckBoxPad("Cont.estoque?",
			"S", "N");

	private JCheckBoxPad chbSomaTipoMov = new JCheckBoxPad("Financeiro?",
			"S", "N");

	private JCheckBoxPad chbImpPedTipoMov = new JCheckBoxPad("Imp.pedido?",
			"S", "N");

	private JCheckBoxPad chbImpNfTipoMov = new JCheckBoxPad("Imp.NF?", "S", "N");

	private JCheckBoxPad chbImpBolTipoMov = new JCheckBoxPad("Imp.bol.?", "S",
			"N");

	private JCheckBoxPad chbReImpNfTipoMov = new JCheckBoxPad("Reimp.NF?", "S",
			"N");

	private JPanelPad pinInfoPadImp = new JPanelPad(300, 150);

	private JLabelPad lbInfoPadImp = new JLabelPad(
			"  Opções para fechamento de venda");

	private JPanelPad pinLbPadImp = new JPanelPad(53, 15);

	public FTipoMov() {

		setTitulo("Cadastro de Tipos de Movimento");
		setAtribos(10, 10, 430, 560);

		lcRestricoes.setMaster(lcCampos);
		lcCampos.adicDetalhe(lcRestricoes);
		lcRestricoes.setTabela(tbRestricoes);

		lcModNota.add(new GuardaCampo(txtCodModNota, "CodModNota",
				"Cód.mod.nota", ListaCampos.DB_PK, false));
		lcModNota.add(new GuardaCampo(txtDescModNota, "DescModNota",
				"Descrição do modelo de nota", ListaCampos.DB_SI, false));
		lcModNota.montaSql(false, "MODNOTA", "LF");
		lcModNota.setQueryCommit(false);
		lcModNota.setReadOnly(true);
		txtCodModNota.setTabelaExterna(lcModNota);

		lcSerie.add(new GuardaCampo(txtCodSerie, "Serie", "Cód.serie",
				ListaCampos.DB_PK, false));
		lcSerie.add(new GuardaCampo(txtDescSerie, "DocSerie", "Nº. doc",
				ListaCampos.DB_SI, false));
		lcSerie.montaSql(false, "SERIE", "LF");
		lcSerie.setQueryCommit(false);
		lcSerie.setReadOnly(true);
		txtCodSerie.setTabelaExterna(lcSerie);

		lcTab.add(new GuardaCampo(txtCodTab, "CodTab", "Cód.tb.pc.",
				ListaCampos.DB_PK, false));
		lcTab.add(new GuardaCampo(txtDescTab, "DescTab",
				"Descrição da tabela de preço", ListaCampos.DB_SI, false));
		lcTab.montaSql(false, "TABPRECO", "VD");
		lcTab.setQueryCommit(false);
		lcTab.setReadOnly(true);
		txtCodTab.setTabelaExterna(lcTab);

		lcTipoMov.add(new GuardaCampo(txtCodTipoMov2, "CodTipoMov",
				"Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov.add(new GuardaCampo(txtDescTipoMov2, "DescTipoMov",
				"Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov.setQueryCommit(false);
		lcTipoMov.setReadOnly(true);
		txtCodTipoMov2.setTabelaExterna(lcTipoMov);

		lcUsu.add(new GuardaCampo(txtIDUsu, "IDUsu", "ID", ListaCampos.DB_PK,
				txtNomeUsu, false));
		lcUsu.add(new GuardaCampo(txtNomeUsu, "NomeUsu",
				"Nome nome do usuário", ListaCampos.DB_SI, false));
		lcUsu.montaSql(false, "USUARIO", "SG");
		lcUsu.setQueryCommit(false);
		lcUsu.setReadOnly(true);
		txtIDUsu.setFK(true);
		txtIDUsu.setNomeCampo("IDUsu");
		txtIDUsu.setTabelaExterna(lcUsu);

		cbTipoMov = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0);

		vLabsES.addElement("Entrada");
		vLabsES.addElement("Saída");
		vLabsES.addElement("Inventário");
		vValsES.addElement("E");
		vValsES.addElement("S");
		vValsES.addElement("I");

		rgESTipoMov = new JRadioGroup(1, 2, vLabsES, vValsES);
		rgESTipoMov.addRadioGroupListener(this);

		montaCbTipoMov("E");

		txtCodTipoMov2.setNomeCampo("CodTipoMov");

		pinGeral.setPreferredSize(new Dimension(430, 560));
		pinGeral.add(pinCamposGeral, BorderLayout.CENTER);

		pnGeral.add(pinGeral, BorderLayout.SOUTH);

		setPainel(pinCamposGeral);

		adicTab("Geral", pnGeral);

		adicCampo(txtCodTipoMov, 7, 120, 80, 20, "CodTipoMov", "Cód.tp.mov.",
				ListaCampos.DB_PK, true);
		adicCampo(txtDescTipoMov, 90, 120, 300, 20, "DescTipoMov",
				"Descrição do tipo de movimento", ListaCampos.DB_SI, true);
		adicCampo(txtCodModNota, 7, 160, 80, 20, "CodModNota", "Cód.mod.nota",
				ListaCampos.DB_FK, true);
		adicDescFK(txtDescModNota, 90, 160, 300, 20, "DescModNota",
				"Descrição do modelo de nota");
		adicCampo(txtCodSerie, 7, 200, 80, 20, "Serie", "Série",
				ListaCampos.DB_FK, txtDescSerie, true);
		adicDescFK(txtDescSerie, 90, 200, 300, 20, "DocSerie",
				"Documento atual");
		adicCampo(txtCodTab, 7, 240, 80, 20, "CodTab", "Cód.tp.pc.",
				ListaCampos.DB_FK, txtDescTab, false);
		adicDescFK(txtDescTab, 90, 240, 300, 20, "DescTab",
				"Descrição da tab. de preços");
		adicCampo(txtCodTipoMov2, 7, 280, 80, 20, "CodTipoMovTM",
				"Cód.mov.nf.", ListaCampos.DB_FK, txtDescTipoMov2, false);
		adicDescFK(txtDescTipoMov2, 90, 280, 300, 20, "DescTipoMov",
				"Descrição do movimento para nota.");
		adicDB(rgESTipoMov, 7, 320, 300, 30, "ESTipoMov", "Fluxo", true);

		adicDB(chbFiscalTipoMov, 7, 370, 107, 20, "FiscalTipoMov",
				"Lançamento", true);
		adicDB(chbEstoqTipoMov, 140, 370, 110, 20, "EstoqTipoMov", "Estoque",
				true);
		adicDB(chbSomaTipoMov, 260, 370, 200, 20, "SomaVdTipoMov",
				"Soma venda", true);

		adicDB(cbTipoMov, 7, 410, 250, 30, "TipoMov", "Tipo de movimento", true);
		adicCampo(txtEspecieTipomov, 280, 415, 80, 20, "EspecieTipomov",
				"Espécie", ListaCampos.DB_SI, true);
		adicDB(chbImpPedTipoMov, 13, 480, 97, 20, "ImpPedTipoMov",
				"Pad.imp.ped.", true);
		adicDB(chbImpNfTipoMov, 113, 480, 94, 20, "ImpNfTipoMov", "Pad.imp.NF",
				true);
		adicDB(chbImpBolTipoMov, 210, 480, 97, 20, "ImpBolTipoMov",
				"Pad.imp.boleto", true);
		adicDB(chbReImpNfTipoMov, 310, 480, 90, 20, "ReImpNfTipoMov",
				"Pad.reimp.NF", true);
		adicDB(chbRestritoTipoMov, 13, 520, 240, 20, "TUSUTIPOMOV", "", true);
		chbRestritoTipoMov.addCheckBoxListener(this);

		pinLbPadImp.adic(lbInfoPadImp, 0, 0, 230, 15);
		pinLbPadImp.tiraBorda();

		adic(pinLbPadImp, 10, 445, 230, 15);
		adic(pinInfoPadImp, 7, 450, 400, 60);
		setListaCampos(true, "TIPOMOV", "EQ");
		lcCampos.setQueryInsert(false);
		
		
		setPainel(pinDetRestricoes, pnRestricoes);

		pinDetRestricoes.setPreferredSize(new Dimension(430, 80));
		pinDetRestricoes.add(pinNavRestricoes, BorderLayout.SOUTH);
		pinDetRestricoes.add(pinCamposRestricoes, BorderLayout.CENTER);

		setListaCampos(lcRestricoes);
		setNavegador(navRestricoes);

		pnRestricoes.add(pinDetRestricoes, BorderLayout.SOUTH);
		pnRestricoes.add(spnRestricoes, BorderLayout.CENTER);
		pinNavRestricoes.adic(navRestricoes, 0, 0, 270, 25);

		setPainel(pinCamposRestricoes);

		//adicCampoInvisivel(txtCodTipoMov, "CodTipoMov", "Tipo Mov.",
			//	ListaCampos.DB_PK, null, true);
		adicCampo(txtIDUsu, 7, 20, 80, 20, "IdUsu", "Id",
				ListaCampos.DB_PF, txtNomeUsu, true);
		adicDescFK(txtNomeUsu, 90, 20, 250, 20, "NomeUsu", " e nome do usuário");

		setListaCampos(true, "TIPOMOVUSU", "EQ");
		lcRestricoes.setQueryInsert(false);
		lcRestricoes.setQueryCommit(false);
		lcRestricoes.montaTab();

		txtCodTipoMov.setTabelaExterna(lcRestricoes);

		tbRestricoes.setTamColuna(80, 0);
		tbRestricoes.setTamColuna(280, 1);
		
	}

	private void montaCbTipoMov(String ES) {
		cbTipoMov.limpa();
		vLabs.clear();
		vVals.clear();
		if (ES.equals("E")) {
			vLabs.addElement("Orçamento (compra)");
			vVals.addElement("OC");
			vLabs.addElement("Pedido (compra)");
			vVals.addElement("PC");
			vLabs.addElement("Compra");
			vVals.addElement("CP");
			vLabs.addElement("Cancelamento");
			vVals.addElement("CC");
			vLabs.addElement("Ordem de produção");
			vVals.addElement("OP");
		} else if (ES.equals("S")) {
			vLabs.addElement("Orçamento (venda)");
			vVals.addElement("OV");
			vLabs.addElement("Pedido (venda)");
			vVals.addElement("PV");
			vLabs.addElement("Venda");
			vVals.addElement("VD");
			vLabs.addElement("Serviço");
			vVals.addElement("SE");
			vLabs.addElement("Venda - ECF");
			vVals.addElement("VE");
			vLabs.addElement("Venda - televendas");
			vVals.addElement("VT");
			vLabs.addElement("Bonificação");
			vVals.addElement("BN");
			vLabs.addElement("Devolução");
			vVals.addElement("DV");
			vLabs.addElement("Transferência");
			vVals.addElement("TR");
			vLabs.addElement("Perda");
			vVals.addElement("PE");
			vLabs.addElement("Consignação - saída");
			vVals.addElement("CS");
			vLabs.addElement("Consignação - devolução");
			vVals.addElement("CE");
			vLabs.addElement("Cancelamento");
			vVals.addElement("CC");
			vLabs.addElement("Requisição de material");
			vVals.addElement("RM");
		} else if (ES.equals("I")) {
			vLabs.addElement("Inventário");
			vVals.addElement("IV");
		}
		cbTipoMov.setItens(vLabs, vVals);

	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcTipoMov.setConexao(cn);
		lcModNota.setConexao(cn);
		lcSerie.setConexao(cn);
		lcTab.setConexao(cn);
		lcRestricoes.setConexao(cn);
		lcUsu.setConexao(cn);
	}

	public void valorAlterado(RadioGroupEvent evt) {
		montaCbTipoMov(rgESTipoMov.getVlrString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freedom.acao.CheckBoxListener#valorAlterado(org.freedom.acao.CheckBoxEvent)
	 */
	public void valorAlterado(CheckBoxEvent evt) {
		if (evt.getCheckBox() == chbRestritoTipoMov) {
			if (evt.getCheckBox().isSelected()) {
				removeTab("Restrições de Usuário", pnRestricoes);
			} else {
				adicTab("Restrições de Usuário", pnRestricoes);
			}
		}

	}
}