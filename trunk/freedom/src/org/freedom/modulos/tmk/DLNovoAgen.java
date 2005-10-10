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

package org.freedom.modulos.tmk;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLNovoAgen extends FFDialogo {
	private static final long serialVersionUID = 1L;

	private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JSpinner txtHoraini = new JSpinner();
	private JSpinner txtHorafim = new JSpinner();
	private JTextFieldPad txtAssunto = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtIdUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextAreaPad txaDescAtend = new JTextAreaPad();
	private ListaCampos lcUsuario = new ListaCampos(this);
	private JScrollPane spnDesc = new JScrollPane(txaDescAtend);
	private JLabelPad lbImg = new JLabelPad(Icone.novo("bannerTMKagendamento.jpg"));
	Vector vVals = new Vector();
	Vector vLabs = new Vector();
	private JComboBoxPad cbTipo = null;
	public DLNovoAgen(Component cOrig) {
		this("",cOrig);
	}
	public DLNovoAgen(String sIdUsu, Component cOrig) {
		super(cOrig);
		setTitulo("Novo agendamento");
		setAtribos(504,460);
		
//Acertando o spinner		
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE,1);
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.add(Calendar.YEAR,-100);
		GregorianCalendar cal2 = new GregorianCalendar();
		cal2.add(Calendar.YEAR,100);
		txtDataini.setVlrDate(cal.getTime());
		txtHoraini.setModel(new SpinnerDateModel(cal.getTime(),cal1.getTime(),cal2.getTime(),Calendar.HOUR_OF_DAY));
		txtHoraini.setEditor(new JSpinner.DateEditor(txtHoraini,"kk:mm"));
		txtDatafim.setVlrDate(cal.getTime());
		txtHorafim.setModel(new SpinnerDateModel(cal.getTime(),cal1.getTime(),cal2.getTime(),Calendar.HOUR_OF_DAY));
		txtHorafim.setEditor(new JSpinner.DateEditor(txtHorafim,"kk:mm"));
		
//Construindo o combobox de tipo.		

		vVals.addElement("");
		vVals.addElement("RE");
		vVals.addElement("VI");
		vVals.addElement("LI");
		vVals.addElement("TA");
		vVals.addElement("CO");
		vLabs.addElement("<--Selecione-->");
		vLabs.addElement("Reunião");
		vLabs.addElement("Visita");
		vLabs.addElement("Ligação");
		vLabs.addElement("Tarefa");
		vLabs.addElement("Compromisso");
		cbTipo = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0);
		
		lcUsuario.add(new GuardaCampo( txtIdUsu, "IdUsu", "ID", ListaCampos.DB_PK, txtNomeUsu, true));
		lcUsuario.add(new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome", ListaCampos.DB_SI, false));
		lcUsuario.montaSql(false, "USUARIO", "SG");    
		lcUsuario.setReadOnly(true);
		txtIdUsu.setTabelaExterna(lcUsuario);
		txtIdUsu.setFK(true);
		txtIdUsu.setNomeCampo("IdUsu");
		
	    pnCab.setPreferredSize(new Dimension(500,60));
		pnCab.add(lbImg);
	    c.add(pnCab,BorderLayout.NORTH);
	    
		adic(new JLabelPad("Cód.usu."),7,5,80,20);
		adic(txtIdUsu,7,25,80,20);
		adic(new JLabelPad("Nome do usuario"),90,5,200,20);
		adic(txtNomeUsu,90,25,217,20);
		adic(new JLabelPad("Tipo"),310,5,150,20);
		adic(cbTipo,310,25,160,20);
		adic(new JLabelPad("Data inicio:"),7,45,100,20);
		adic(txtDataini,7,65,100,20);
		adic(new JLabelPad("hora"),110,45,87,20);
		adic(txtHoraini,110,65,87,20);
		adic(new JLabelPad("Data fim:"),200,45,97,20);
		adic(txtDatafim,200,65,100,20);
		adic(new JLabelPad("hora"),300,45,87,20);
		adic(txtHorafim,300,65,87,20);
		adic(new JLabelPad("Assunto"),7,85,380,20);
		adic(txtAssunto,7,105,380,20);
		
		JLabelPad lbChamada = new JLabelPad(" Ação: ");
		lbChamada.setOpaque(true);
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		
		adic(lbChamada,20,130,60,20);
		adic(lbLinha,7,140,470,2);
		adic(spnDesc,7,155,470,140);
		
		if (!sIdUsu.equals("")) {
		  txtIdUsu.setVlrString(sIdUsu);
		  txtIdUsu.setAtivo(false);
		}
	
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtIdUsu.getVlrString().equals("")) { 
				Funcoes.mensagemInforma(this,"Código do leitor inválido!");
				return;
			}
			else if (txaDescAtend.getVlrString().equals("")) {
				Funcoes.mensagemInforma(this,"Não foi digitado nenhuma ação!");
				return;
			}
		}
		super.actionPerformed(evt);
	}
	public String[] getValores() {
		String[] sVal = new String[9];
		sVal[0] = txtDataini.getVlrString();
		sVal[1] = ((JSpinner.DateEditor)txtHoraini.getEditor()).getTextField().getText();
		sVal[2] = txtDatafim.getVlrString();
		sVal[3] = ((JSpinner.DateEditor)txtHorafim.getEditor()).getTextField().getText();
		sVal[4] = txtAssunto.getVlrString();
		sVal[5] = txaDescAtend.getVlrString();
		sVal[6] = cbTipo.getVlrString();
		sVal[7] = ""+lcUsuario.getCodFilial();
		sVal[8] = txtIdUsu.getVlrString();
		return sVal;
	}
	public void setValores(String[] sVal) {
		txtDataini.setVlrString(sVal[0]);
		((JSpinner.DateEditor)txtHoraini.getEditor()).getTextField().setText(sVal[1]);
		txtDatafim.setVlrString(sVal[2]);
		((JSpinner.DateEditor)txtHorafim.getEditor()).getTextField().setText(sVal[3]);
		txtAssunto.setVlrString(sVal[4]);
		txaDescAtend.setVlrString(sVal[5]);
		cbTipo.setVlrString(sVal[6]);
		lcUsuario.carregaDados();
	}
	public void setConexao(Connection cn) {
		lcUsuario.setConexao(cn);
	}
}
