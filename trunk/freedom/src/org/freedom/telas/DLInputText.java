/**
 * @version 22/10/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLInputText.java <BR>
 *                      Este programa é licenciado de acordo com a LPG-PC
 *                      (Licença Pública Geral para Programas de Computador),
 *                      <BR>
 *                      versão 2.1.0 ou qualquer versão posterior. <BR>
 *                      A LPG-PC deve acompanhar todas PUBLICAÇÕES,
 *                      DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 *                      Caso uma cópia da LPG-PC não esteja disponível junto com
 *                      este Programa, você pode contatar <BR>
 *                      o LICENCIADOR ou então pegar uma cópia em: <BR>
 *                      Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                      Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou
 *                      ALTERAR este Programa é preciso estar <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 *                      <BR>
 *                      Dialog de edição e inserção de observações por data nos
 *                      clientes
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.funcoes.Funcoes;

/**
 * @author robson
 */
public class DLInputText extends FFDialogo {

	private JTextAreaPad txaMemo = new JTextAreaPad();
	private JScrollPane spnMemo = new JScrollPane(txaMemo);
	private boolean required = false;

	public DLInputText(Component cOrig, String title, boolean required) {
		super(cOrig);
		setAtribos(350, 250);
		c.add(spnMemo, BorderLayout.CENTER);
		c.add(new JLabel(title), BorderLayout.NORTH);

		this.required = required;
	}

	public String getTexto() {
		return txaMemo.getText();
	}

	public void setTexto(String sTexto) {
		txaMemo.setText(sTexto);
	}

	public void ok() {
		if (txaMemo.getText().trim().equals("") && required) {
			Funcoes.mensagemInforma(this, "O campo de texto deve ser preenchido !");
		} else {
			OK = true;
			setVisible(false);
		}
	}

	public void cancel() {
		if (txaMemo.getText().trim().equals("") && required) {
			Funcoes.mensagemInforma(this, "O campo de texto deve ser preenchido !");
		} else {
			OK = false;
			setVisible(false);
		}

	}

}

