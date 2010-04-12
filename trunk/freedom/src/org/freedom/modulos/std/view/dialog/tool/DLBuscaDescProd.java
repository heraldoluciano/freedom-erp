package org.freedom.modulos.std.view.dialog.tool;


import javax.swing.JScrollPane;

import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.dialog.FDialogo;

public class DLBuscaDescProd extends FDialogo {

	private static final long serialVersionUID = 1l;

	private JPanelPad pn = new JPanelPad();

	private JTextAreaPad txa = new JTextAreaPad();

	public DLBuscaDescProd( final String sDesc ) {

		super();

		setTitle( "Descrição completa" );
		setAtribos( 400, 200 );

		txa.setVlrString( sDesc );
		pn.adic( new JScrollPane( txa ), 0, 0, 385, 133 );
		c.add( pn );
	}
	
	public String getTexto() {

		return txa.getVlrString();
	}
}
