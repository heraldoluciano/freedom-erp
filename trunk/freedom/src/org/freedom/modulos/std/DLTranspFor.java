package org.freedom.modulos.std;

import java.util.Vector;

import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.telas.FFDialogo;


public class DLTranspFor extends FFDialogo{

	private static final long serialVersionUID = 1L;
	
	private JRadioGroup<?, ?> rgOrdem = null;

	private JLabelPad lbOrdem = new JLabelPad( "Ordenar por:" );

	public DLTranspFor() {

		setTitulo( "Ordem do Relatório" );
		setAtribos( 300, 150 );
		
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		
		vLabs.addElement( "Código" );
		vLabs.addElement( "Descrição" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "C" );
		adic( lbOrdem, 7, 0, 80, 15 );
		adic( rgOrdem, 7, 20, 270, 30 );
		
		
		
		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();
		
	}

	public String getValor() {

		String sRetorno = "";

		if( "C".equals( rgOrdem.getVlrString() )){
			
			sRetorno = "T.CODTRAN";
			
		}else if( "D".equals( rgOrdem.getVlrString() )){
			
			sRetorno = "T.NOMETRAN";
		}
		
		return sRetorno;
	}
	
}
