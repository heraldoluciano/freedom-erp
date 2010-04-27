package org.freedom.modulos.std.view.dialog.utility;

import java.awt.event.KeyEvent;

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;

public class DLCriaVendaCompra extends FDialogo {
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtNewCod = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	public DLCriaVendaCompra( boolean nrpedido, String tipo ) {
		
		setTitulo("Confirmação", this.getClass().getName() );
		
		String labeltipo = "";
		
		if( "E".equals( tipo )) { // Venda PDV/ECF
			setAtribos(235, 120);
			labeltipo = "um Cupom";
		}
		else if ("V".equals( tipo )) { // Venda STD
			setAtribos(235, 140);
			labeltipo = "uma Venda";
		}
		else if ("C".equals( tipo )) { // Compra GMS 
			setAtribos(235, 140);
			labeltipo = "uma Compra";
		}
		
		adic(new JLabelPad("Deseja criar uma " + labeltipo + " agora?"), 7, 15, 220, 20);
		
		if(tipo.equals("V") && nrpedido) {
			adic(new JLabelPad("Nº Pedido"), 7, 40, 80, 20);
			adic(txtNewCod, 87, 40, 120, 20);
		}
		
		btOK.addKeyListener( this );
				
	}
	
	public void setNewCodigo( int arg ) {
		txtNewCod.setVlrInteger( arg );
	}
	
	public int getNewCodigo() {
		return txtNewCod.getVlrInteger().intValue();
	}
	
	public void keyPressed(KeyEvent kevt) {
		
		if(kevt.getSource() == btOK) {
			btOK.doClick();
		}
		
		super.keyPressed(kevt);
		
	}

}
