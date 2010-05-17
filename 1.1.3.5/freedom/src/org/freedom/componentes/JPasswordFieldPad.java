/**
 * @version 08/01/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JPasswordFieldPad.java <BR>
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
 * Comentários da classe.....
 */

package org.freedom.componentes;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JPasswordField;

import org.freedom.componentes.ListaCampos;
public class JPasswordFieldPad extends JPasswordField {

	private static final long serialVersionUID = 1L;
	private int iTam = 0;
	private ListaCampos lcTxt = null;
    private boolean bEnterPula = true;
  
	/**
	*  Construtor da classe mesmo que JPasswordFieldPad(0). <BR>
	*
	*/
  
	public JPasswordFieldPad () {
		this(0);
	}

	/**
	*  Construtor da classe com tamanho do campo pré-estabelecido. <BR>
	*  Carrega as heranças e adiciona keyListener e focusListener.
	*
	*/
  
	public JPasswordFieldPad (int iTamanho) {
		iTam = iTamanho;
		addKeyListener(
			new KeyAdapter() {
				public void keyTyped(KeyEvent kevt) {
					if (kevt.getKeyChar() == KeyEvent.VK_BACK_SPACE)
					  return;
					  
					if (kevt.getKeyChar() == KeyEvent.VK_ENTER && bEnterPula) {
						transferFocus();
						return;
					}
					if (getPassword().length+1 > iTam && (getSelectedText() == null)) { //+1 pq o keyTyped vem depois que o char foi impresso.
						kevt.setKeyChar((char)KeyEvent.VK_UNDEFINED);
					}
					if (validaChar(kevt.getKeyChar()) && (lcTxt != null)) {
						lcTxt.edit();
					}
				}
			}

		);
		addFocusListener(
			new FocusAdapter() {
				public void focusGained(FocusEvent fevt) {
					selectAll();
				}
			}

		);
	}

	/**
	*  Ajusta o tamanho maximo do campo. <BR>
	*  @param iTamanho - Tamanho do campo.
	*  @see #getTamanho
	*
	*/

	public void setTamanho(int iTamanho) {
		iTam = iTamanho;
	}

	/**
	*  Retorna o tamanho maximo do campo. <BR>
	*  @return Tamanho do campo ou zero se o tamanho não foi definido.
	*  @see #setTamanho
	*
	*/


	public int getTamanho() {
		return iTam;
	}

	/**
	*  Adiciona um ListaCampos a este campo. <BR>
	*  O ListaCampos aqui adionado será informado sobre os status de edição.
	*  @param lcCampos - ListaCampos a ser adicionado.
	*  @see #getListaCampos
	*
	*/

	public void setListaCampos(ListaCampos lcCampos) {
		lcTxt = lcCampos;
	}

	/**
	*  Retorna o ListaCampos que foi adicionado neste campo. <BR>
	*  @return ListaCampos do password ou null.
	*  @see #setListaCampos
	*
	*/

	public ListaCampos getListaCampos() {
		return lcTxt;
	}

	/**
	*  Ajusta o campo para pular automaticamente para o proximo foco<BR>
	*  quando a tecla enter é pressionada.
	*  @param bValEnterPula - Se verdadeiro o enter troca de foco, se falso não troca.
	*  @see #getEnterPula
	*
	*/

	public void setEnterPula(boolean bValEnterPula) {
		bEnterPula = bValEnterPula;
	}

	/**
	*  Retorna se o campo troca de foco quando a tecla enter é pressionada.
	*  @return Se verdadeiro o enter troca de foco, se falso não troca.
	*  @see #setEnterPula
	*
	*/

	public boolean getEnterPula() {
		return bEnterPula;
	}

	/**
	*  Insere um valor string no campo.<BR>
	*  @param sVal - String a ser inserida.
	*  @see #getVlrString
	*
	*/

	public void setVlrString(String sVal) {
		if (sVal != null)
		  setText(sVal.trim());
		else
		  setText("");
	}

	/**
	*  Retorna um valor string com a senha.
	*  @return String com a senha.
	*  @see #setVlrString
	*
	*/

	public String getVlrString() {
		return new String(getPassword());
	}

	private boolean validaChar(char cVal) {
		boolean bValido = false;
		
		if (((cVal >= KeyEvent.VK_COMMA) && (cVal <= KeyEvent.VK_DIVIDE)) ||
			((cVal >= KeyEvent.VK_AMPERSAND) &&	(cVal <= KeyEvent.VK_BRACERIGHT)) ||
			((cVal >= KeyEvent.VK_AT) && (cVal <= KeyEvent.VK_UNDERSCORE))) {
				bValido = true;
		}  // Campos imprimíveis!
		
		return bValido;
	}
}
