package org.freedom.modulos.std.view.dialog.utility;

import java.awt.event.KeyEvent;

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;

public class DLCriaVendaOrc extends FDialogo {
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtNewCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	public DLCriaVendaOrc( boolean nrpedido, String tipo ) {
		
		setTitulo("Confirmação", this.getClass().getName() );
		
		if(tipo.equals("E"))
			setAtribos(235, 120);
		else
			setAtribos(235, 140);
		
		adic(new JLabelPad("DEJEJA CRIAR UMA VENDA AGORA?"), 7, 15, 220, 20);
		
		if(tipo.equals("V") && nrpedido) {
			adic(new JLabelPad("Nº Pedido"), 7, 40, 80, 20);
			adic(txtNewCodVenda, 87, 40, 120, 20);
		}
		
		btOK.addKeyListener( this );
				
	}
	
	public void setNewCodVenda( int arg ) {
		txtNewCodVenda.setVlrInteger( arg );
	}
	
	public int getNewCodVenda() {
		return txtNewCodVenda.getVlrInteger().intValue();
	}
	
	public void keyPressed(KeyEvent kevt) {
		if(kevt.getSource() == btOK)
			btOK.doClick();
		super.keyPressed(kevt);
	}

}
