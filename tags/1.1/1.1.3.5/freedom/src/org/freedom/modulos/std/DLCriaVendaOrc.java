package org.freedom.modulos.std;

import java.awt.event.KeyEvent;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.telas.FDialogo;

public class DLCriaVendaOrc extends FDialogo {
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtNewCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	public DLCriaVendaOrc( boolean arg0, String arg1 ) {
		
		setTitulo("Confirmação");
		
		if(arg1.equals("E"))
			setAtribos(235, 120);
		else
			setAtribos(235, 140);
		
		adic(new JLabelPad("DEJEJA CRIAR UMA VENDA AGORA?"), 7, 15, 220, 20);
		
		if(arg1.equals("V")) {
			adic(new JLabelPad("Nº Pedido"), 7, 40, 80, 20);
			adic(txtNewCodVenda, 87, 40, 120, 20);
		}
		
		btOK.addKeyListener( this );
				
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
