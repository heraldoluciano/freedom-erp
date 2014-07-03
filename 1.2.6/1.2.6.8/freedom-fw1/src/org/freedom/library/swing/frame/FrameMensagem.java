package org.freedom.library.swing.frame;

import java.awt.ScrollPane;
//import java.awt.Scrollbar;

import javax.swing.JDialog;
import javax.swing.JTextArea;

public class FrameMensagem extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea txaMemo = new JTextArea();
	private ScrollPane painel = new ScrollPane();
	public FrameMensagem() {
		super();
		setSize(400, 400);
		painel.add(txaMemo);
		getContentPane().add(painel);
		setModal(true);
	}
	public void setTexto(String texto) {
		txaMemo.setText(texto);
	}
}