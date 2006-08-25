package org.freedom.layout.telas;

import javax.swing.JLabel;

import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.telas.FDialogo;


public class DLConveniadoPDV extends FDialogo {
	
	private static final long serialVersionUID = 1;
	
	private JTextFieldPad txtCodBarras = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtMatricula = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	public DLConveniadoPDV() {

		super();
		
		setTitulo( "Conveniado" );
		setAtribos( 383, 400 );
		
		
		montaTela();
		
	}
	
	private void montaTela() {
		
		adic( new JLabel( "Cód.barras" ), 7, 10, 200, 20 );
		adic( txtCodBarras, 7, 30, 200, 20 );
		adic( new JLabel( "Matricula" ), 210, 10, 100, 20 );
		adic( txtMatricula, 210, 30, 150, 20 );
		
		JLabel linha = new JLabel();
		
	}
	
	public static void main( String arg[] ) {
		
		DLConveniadoPDV teste = new DLConveniadoPDV();
		teste.setVisible(true);
		
	}

}
