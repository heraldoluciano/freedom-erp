package org.freedom.modulos.pcp;

import javax.swing.ImageIcon;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FFDialogo;


public class DLAcoesCorretivas extends FFDialogo {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pnImg = new JPanelPad( 635, 371 );
	
	private ImageIcon imgACorretiva = Icone.novo( "ishikawa.jpg" );
	
	private JLabelPad lbImg = new JLabelPad( imgACorretiva );

	public DLAcoesCorretivas(){
		
		setTitulo( "Acões corretivas" );
		setAtribos( 690, 500 );
		
		adic( pnImg, 20, 20, 635, 371 );
		pnImg.tiraBorda();
		pnImg.adic( lbImg, 0, 0, 635, 371 );
	}
}
