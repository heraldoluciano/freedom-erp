/**
 * @version 06/01/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)DLNovoAgend.java <BR>
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
 * Novo agendamento
 * 
 */

package org.freedom.modulos.atd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLNovoAgen extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,
			new GridLayout(1, 1));

	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldPad txtAssunto = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodAge = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtTipoAge = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 5, 0);

	private JTextFieldFK txtDescAge = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);

	private JTextFieldPad txtIdUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,
			8, 0);

	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);

	private JTextFieldPad txtIdUsuEmit = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 8, 0);

	private JTextFieldFK txtNomeUsuEmit = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 50, 0);

	private JSpinner txtHoraini = new JSpinner();

	private JSpinner txtHorafim = new JSpinner();

	private JComboBoxPad cbPrioridade = null;

	private JComboBoxPad cbTipo = null;

	private JRadioGroup rgCAAGD = null;

	private JTextAreaPad txaDescAtend = new JTextAreaPad();

	private JScrollPane spnDesc = new JScrollPane(txaDescAtend);

	private JLabelPad lbImg = new JLabelPad(Icone
			.novo("bannerTMKagendamento.jpg"));

	private JButtonPad btTipoAGD = new JButtonPad(Icone.novo("btExecuta.gif"));

	private ListaCampos lcAgente = new ListaCampos(this);

	private ListaCampos lcUsuEmit = new ListaCampos(this);

	private ListaCampos lcUsu = new ListaCampos(this);

	private Vector vCodTipoAGD = new Vector();

	private Vector vDescTipoAGD = new Vector();

	public DLNovoAgen(Component cOrig) {
		this("", null, cOrig);
	}

	public DLNovoAgen(String sIdUsu, Date data, Component cOrig) {
		super(cOrig);
		setTitulo("Novo agendamento");
		setAtribos(510, 475);

		// Acertando o spinner
		GregorianCalendar agora = new GregorianCalendar();
		GregorianCalendar cal = new GregorianCalendar();
		GregorianCalendar cal1 = new GregorianCalendar();
		GregorianCalendar cal2 = new GregorianCalendar();

		if (data != null) {
			agora.setTime(data);
			agora.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
			agora.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));

			cal.setTime(agora.getTime());
			cal1.setTime(agora.getTime());
			cal2.setTime(agora.getTime());
		}

		cal.add(Calendar.DATE, 0);
		cal1.add(Calendar.YEAR, -100);
		cal2.add(Calendar.YEAR, 100);

		txtDataini.setVlrDate(cal.getTime());
		txtHoraini.setModel(new SpinnerDateModel(cal.getTime(), cal1.getTime(),
				cal2.getTime(), Calendar.HOUR_OF_DAY));
		txtHoraini.setEditor(new JSpinner.DateEditor(txtHoraini, "kk:mm"));
		txtDatafim.setVlrDate(cal.getTime());
		txtHorafim.setModel(new SpinnerDateModel(cal.getTime(), cal1.getTime(),
				cal2.getTime(), Calendar.HOUR_OF_DAY));
		txtHorafim.setEditor(new JSpinner.DateEditor(txtHorafim, "kk:mm"));

		// Construindo o combobox de tipo.

		cbTipo = new JComboBoxPad(vDescTipoAGD, vCodTipoAGD,
				JComboBoxPad.TP_STRING, 2, 0);

		Vector vVals1 = new Vector();
		vVals1.addElement("PU");
		vVals1.addElement("PR");
		Vector vLabs1 = new Vector();
		vLabs1.addElement("publico");
		vLabs1.addElement("privado");
		rgCAAGD = new JRadioGroup(1, 2, vLabs1, vVals1);
		rgCAAGD.setVlrString("PR");

		Vector vVals2 = new Vector();
		vVals2.addElement("1");
		vVals2.addElement("2");
		vVals2.addElement("3");
		vVals2.addElement("4");
		Vector vLabs2 = new Vector();
		vLabs2.addElement("nenhuma");
		vLabs2.addElement("baixa");
		vLabs2.addElement("média");
		vLabs2.addElement("alta");
		cbPrioridade = new JComboBoxPad(vLabs2, vVals2, JComboBoxPad.TP_STRING,
				2, 0);

		lcUsuEmit.add(new GuardaCampo(txtIdUsuEmit, "IdUsu", "ID Usuario",
				ListaCampos.DB_PK, false));
		lcUsuEmit.add(new GuardaCampo(txtNomeUsuEmit, "NomeUsu", "Nome",
				ListaCampos.DB_SI, false));
		lcUsuEmit.add(new GuardaCampo(txtCodAge, "CodAge", "Cód.age.",
				ListaCampos.DB_FK, true));
		lcUsuEmit.montaSql(false, "USUARIO", "SG");
		lcUsuEmit.setReadOnly(true);
		txtIdUsuEmit.setTabelaExterna(lcUsuEmit);
		txtIdUsuEmit.setFK(true);
		txtIdUsuEmit.setNomeCampo("IdUsu");

		lcUsu.add(new GuardaCampo(txtIdUsu, "IdUsu", "ID Usuario",
				ListaCampos.DB_PK, false));
		lcUsu.add(new GuardaCampo(txtNomeUsu, "NomeUsu", "Nome",
				ListaCampos.DB_SI, false));
		lcUsu.add(new GuardaCampo(txtCodAge, "CodAge", "Cód.age.",
				ListaCampos.DB_FK, true));
		lcUsu.montaSql(false, "USUARIO", "SG");
		lcUsu.setReadOnly(true);
		txtIdUsu.setTabelaExterna(lcUsu);
		txtIdUsu.setFK(true);
		txtIdUsu.setNomeCampo("IdUsu");

		lcAgente.add(new GuardaCampo(txtCodAge, "CodAge", "Cód.age.",
				ListaCampos.DB_PK, true));
		lcAgente.add(new GuardaCampo(txtDescAge, "DescAge",
				"Descrição do agente", ListaCampos.DB_SI, false));
		lcAgente.add(new GuardaCampo(txtTipoAge, "TipoAge", "Tipo",
				ListaCampos.DB_SI, false));
		lcAgente.montaSql(false, "AGENTE", "SG");
		lcAgente.setReadOnly(true);
		txtCodAge.setTabelaExterna(lcAgente);
		txtCodAge.setFK(true);
		txtCodAge.setNomeCampo("CodAge");

		pnCab.setPreferredSize(new Dimension(500, 60));
		pnCab.add(lbImg);
		c.add(pnCab, BorderLayout.NORTH);

		adic(new JLabelPad("Usuário"), 10, 5, 60, 20);
		adic(txtIdUsuEmit, 10, 25, 70, 20);
		adic(new JLabelPad("ID usu"), 83, 5, 60, 20);
		adic(txtIdUsu, 83, 25, 70, 20);
		adic(new JLabelPad("Nome do usuario"), 156, 5, 155, 20);
		adic(txtNomeUsu, 156, 25, 155, 20);
		adic(rgCAAGD, 315, 20, 170, 30);

		adic(new JLabelPad("prioridade"), 10, 45, 100, 20);
		adic(cbPrioridade, 10, 65, 234, 20);
		adic(new JLabelPad("Tipo"), 249, 45, 100, 20);
		adic(cbTipo, 249, 65, 200, 20);
		adic(btTipoAGD, 452, 60, 30, 30);

		adic(new JLabelPad("Data inicio:"), 10, 95, 115, 20);
		adic(txtDataini, 10, 115, 115, 20);
		adic(new JLabelPad("hora"), 128, 95, 115, 20);
		adic(txtHoraini, 128, 115, 115, 20);
		adic(new JLabelPad("Data fim:"), 249, 95, 115, 20);
		adic(txtDatafim, 249, 115, 115, 20);
		adic(new JLabelPad("hora"), 367, 95, 115, 20);
		adic(txtHorafim, 367, 115, 115, 20);
		adic(new JLabelPad("Assunto"), 10, 135, 100, 20);
		adic(txtAssunto, 10, 155, 475, 20);

		JLabelPad lbChamada = new JLabelPad("    Ação:");
		lbChamada.setOpaque(true);
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());

		adic(lbChamada, 20, 180, 60, 20);
		adic(lbLinha, 10, 190, 475, 2);
		adic(spnDesc, 10, 205, 475, 115);

		if (!"".equals(sIdUsu)) {
			txtIdUsuEmit.setVlrString(sIdUsu);
			txtIdUsuEmit.setAtivo(false);
			txtIdUsu.setVlrString(sIdUsu);
		} else {
			Funcoes.mensagemErro(this, "Usuario invalido!");
			cancel();
		}

		btTipoAGD.addActionListener(this);

		lcUsu.addCarregaListener(this);

	}

	public String[] getValores() {

		final String[] sVal = new String[12];

		sVal[0] = txtDataini.getVlrString();
		sVal[1] = ((JSpinner.DateEditor) txtHoraini.getEditor()).getTextField()
				.getText();
		sVal[2] = txtDatafim.getVlrString();
		sVal[3] = ((JSpinner.DateEditor) txtHorafim.getEditor()).getTextField()
				.getText();
		sVal[4] = txtAssunto.getVlrString();
		sVal[5] = txaDescAtend.getVlrString();
		sVal[6] = String.valueOf(Aplicativo.iCodFilial);
		sVal[7] = (String) vCodTipoAGD.elementAt(cbTipo.getSelectedIndex());
		sVal[8] = cbPrioridade.getVlrString();
		sVal[9] = txtCodAge.getVlrString();
		sVal[10] = txtTipoAge.getVlrString();
		sVal[11] = rgCAAGD.getVlrString();

		return sVal;

	}

	private void carregaTipoAgenda() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;

		try {

			vCodTipoAGD.clear();
			vDescTipoAGD.clear();

			sSQL = "SELECT CODTIPOAGD, DESCTIPOAGD FROM SGTIPOAGENDA WHERE CODEMP=? AND CODFILIAL=?";

			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);

			rs = ps.executeQuery();

			while (rs.next()) {

				vCodTipoAGD.addElement(rs.getString("CODTIPOAGD"));
				vDescTipoAGD.addElement(rs.getString("DESCTIPOAGD"));

			}

			rs.close();
			ps.close();

			if (!con.getAutoCommit())
				con.commit();

			if (vDescTipoAGD.size() <= 0) {
				Funcoes.mensagemInforma(this,
						"Nenhum tipo de agendamento foi encontrado!");
			}

			cbTipo.setItens(vDescTipoAGD, vCodTipoAGD);

		} catch (Exception e) {
			e.printStackTrace();
			Funcoes.mensagemErro(this, "Erro ao buscar tipos de agendamento!\n"
					+ e.getMessage());
		}

	}

	public void setValores(String[] sVal) {

		txtCodAge.setVlrString(sVal[0]);
		txtDataini.setVlrString(sVal[1]);
		((JSpinner.DateEditor) txtHoraini.getEditor()).getTextField().setText(
				sVal[2]);
		txtDatafim.setVlrString(sVal[3]);
		((JSpinner.DateEditor) txtHorafim.getEditor()).getTextField().setText(
				sVal[4]);
		txtAssunto.setVlrString(sVal[5]);
		txaDescAtend.setVlrString(sVal[6]);
		rgCAAGD.setVlrString(sVal[7]);
		cbPrioridade.setVlrString(sVal[8]);
		cbTipo.setVlrString(sVal[9]);

		lcUsuEmit.carregaDados();
		lcAgente.carregaDados();

	}

	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == btOK) {

			if (txtCodAge.getVlrString().equals("")) {

				Funcoes.mensagemInforma(this, "Código do usuario inválido!");
				return;

			} else if (txaDescAtend.getVlrString().equals("")) {

				Funcoes.mensagemInforma(this, "Não foi digitado nenhuma ação!");
				return;

			}

		} else if (evt.getSource() == btTipoAGD) {

			FTipoAgenda tipoAgd = new FTipoAgenda();
			tipoAgd.setConexao(con);
			tipoAgd.setVisible(true);
			tipoAgd.toFront();
			carregaTipoAgenda();

		}

		super.actionPerformed(evt);

	}

	public void beforeCarrega(CarregaEvent e) {
		if (e.getListaCampos() == lcUsu) {
			lcAgente.carregaDados();
		}
	}

	public void afterCarrega(CarregaEvent e) {
		if (e.getListaCampos() == lcUsu) {
			lcAgente.carregaDados();
		}
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcUsu.setConexao(cn);
		lcUsuEmit.setConexao(cn);
		lcAgente.setConexao(cn);

		lcUsu.carregaDados();
		lcUsuEmit.carregaDados();
		lcAgente.carregaDados();

		carregaTipoAgenda();
	}

}
