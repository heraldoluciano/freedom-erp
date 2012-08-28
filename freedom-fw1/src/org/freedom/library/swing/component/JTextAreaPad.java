/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JTextFieldPad.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários da classe.....
 */

package org.freedom.library.swing.component;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import org.freedom.library.persistence.Campo;
import org.freedom.library.persistence.ListaCampos;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

public class JTextAreaPad extends JTextArea implements KeyListener, Campo {

	private static final long serialVersionUID = 1L;
	private ListaCampos lcTxa = null;
	public int iTamanho = 10000;
	public int iDecimal = 0;
	private int iMascara = -1;
	private int tipoCampo = JTextFieldPad.TP_STRING;
	//private int tipoCampo = TP_NONE;

	boolean bAtivo = true;

	/**
	 * Construtor da classe (sem tamanho). <BR>
	 * Coloca o tamanho padrão: 10000.
	 * 
	 */
	public JTextAreaPad() {
		this(0);
		this.setBorder(BorderFactory.createEtchedBorder());
	}

	/**
	 * Construtor da classe (com tamanho). <BR>
	 * Número de caracteres que o txa pode aceitar.
	 * 
	 */
	public JTextAreaPad(int iTam) {
		if (iTam > 0)
			iTamanho = iTam;
		addKeyListener(this);
		this.setLineWrap(true);
	}

	public String getVlrString() {
		return getText();
	}

	public void setListaCampos(ListaCampos lc) {
		lcTxa = lc;
	}

	public void setVlrString(String val) {
		setText(val);
		setCaretPosition(0);
	}

	public void setAtivo(boolean b) {
		if (b) {
			setEditable(true);
			bAtivo = true;
			this.setBackground(Color.WHITE);
			this.setBorder(BorderFactory.createEtchedBorder());
		}
		else {
			setEditable(false);
			bAtivo = false;
			this.setBackground(new Color(238, 238, 238));
			this.setBorder(BorderFactory.createLineBorder(new Color(184, 207, 229)));
		}
	}

	public void keyTyped(KeyEvent kevt) {
		if (( kevt.getKeyChar() != KeyEvent.CHAR_UNDEFINED ) && ( kevt.getKeyChar() != ( char ) 8 ) && ( kevt.getKeyChar() != ( char ) 10 ) && ( kevt.getKeyChar() != ( char ) 9 )) {
			if (getText().length() > iTamanho)
				kevt.setKeyChar(( char ) 0);
			else if (lcTxa != null)
				lcTxa.edit();
		}
	}

	public void setNaoEditavel(boolean b) {
		// bSoLeitura = b;
		if (b) {
			setEditable(false);
			bAtivo = false;
		}
		else {
			setEditable(true);
			bAtivo = true;
		}
	}

	public void keyPressed(KeyEvent kevt) {
	}

	public void keyReleased(KeyEvent kevt) {
	}
	
	public ListaCampos getTabelaExterna() {
		return null;
	}
	
	public String getStrMascara() {
		return "";
	}
	
	public int getTamanho() {
		return iTamanho;
	}

	public void setTamanho(int tam) {
		this.iTamanho = tam;
	}
	
	public int getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(int tipoCampo) {
		this.tipoCampo = tipoCampo;
	}
	
	public void setDecimal(int dec) {
		this.iDecimal = dec;
	}
	
	public int getDecimal() {
		return this.iDecimal;
	}

	public int getIMascara() {
		return iMascara;
	}

	public void setIMascara(int iMascara) {
		this.iMascara = iMascara;
	}

	public ListaCampos getListaCampos() {
		return this.lcTxa;
	}

	public int getMascara() {
		return iMascara;
	}
	
	public Integer getVlrInteger() {
		return new Integer(0);
	}
	
	public void setVlrBigDecimal(BigDecimal vlr) {
		
	}

	public void setVlrInteger(Integer vlr) {
		
	}
	
	public void cancelaDLF2() {
		//runDLF2 = false;
	}
	
}
