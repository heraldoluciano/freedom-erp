/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCCAnal.java <BR>
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLCCAnal extends FFDialogo {
	private JTextFieldPad txtCodPai = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtDescPai = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
	private JTextFieldPad txtDescAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtSiglaAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
	private JLabelPad lbCodPai = new JLabelPad("Código");
	private JLabelPad lbDescPai = new JLabelPad("e descrição da origem");
	private JLabelPad lbCodAnal = new JLabelPad("Código");
	private JLabelPad lbDescAnal = new JLabelPad("Descrição");
	private JLabelPad lbSiglaAnal = new JLabelPad("Sig.");
	public DLCCAnal(
	    Component cOrig,
		String sCodPai,
		String sDescPai,
		String sCod,
		String sDesc,
		String sSigla) {
		super(cOrig);
		setTitulo("Nova Conta Analítica");
		setAtribos(450, 170);
		cancText(txtCodPai);
		cancText(txtDescPai);
		cancText(txtCodAnal);
		Funcoes.setBordReq(txtDescAnal);
		txtCodPai.setText(sCodPai);
		txtDescPai.setText(sDescPai);
		txtCodAnal.setText(sCod);
		adic(lbCodPai, 7, 0, 80, 20);
		adic(txtCodPai, 7, 20, 80, 20);
		adic(lbDescPai, 90, 0, 200, 20);
		adic(txtDescPai, 90, 20, 240, 20);
		adic(lbCodAnal, 7, 40, 100, 20);
		adic(txtCodAnal, 7, 60, 110, 20);
		adic(lbDescAnal, 120, 40, 110, 20);
		adic(txtDescAnal, 120, 60, 207, 20);
		adic(lbSiglaAnal, 330, 40, 100, 20);
		adic(txtSiglaAnal, 330, 60, 80, 20);
		if (sDesc != null) {
			setTitulo("Edição de Conta Analítica");
			txtDescAnal.setText(sDesc);
			txtSiglaAnal.setText(sSigla);
			txtDescAnal.selectAll();
		}
		txtDescAnal.requestFocus();
	}
	private void cancText(JTextFieldPad txt) {
		txt.setBackground(Color.lightGray);
		txt.setFont(new Font("Dialog", Font.BOLD, 12));
		txt.setEditable(false);
		txt.setForeground(new Color(118, 89, 170));
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtDescAnal.getText().trim().length() == 0) {
				Funcoes.mensagemInforma(
					this,
					"O campo descrição está em branco! ! !");
				txtDescAnal.requestFocus();
				return;
			} else if (txtSiglaAnal.getText().trim().length() == 0) {
				Funcoes.mensagemInforma(
					this,
					"O campo sigla está em branco! ! !");
				txtSiglaAnal.requestFocus();
				return;
			}
		}
		super.actionPerformed(evt);
	}
	public String[] getValores() {
		String[] sRet = { txtDescAnal.getVlrString(), txtSiglaAnal.getVlrString()};
		return sRet;
	}
}
