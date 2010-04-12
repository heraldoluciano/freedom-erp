package org.freedom.modulos.crm;

import javax.swing.JScrollPane;

import org.freedom.library.swing.JTextAreaPad;
import org.freedom.telas.FDialogo;


public class DLMinutaContr extends FDialogo{
	
	private static final long serialVersionUID = 1L;

	private JTextAreaPad txaMinuta =  new JTextAreaPad( 32000 );
	
	private JScrollPane scrol = new JScrollPane( txaMinuta );
	
	public DLMinutaContr( String minuta ){
		
		super();
		setAtribos( 825, 845 );
		setTitulo( "Minuta do contrato" );
		
		adic( scrol, 0, 0, 810, 780 );
		txaMinuta.setVlrString( minuta );
	}
	
	public String getValores(){
		
		return txaMinuta.getVlrString();
	}
}
