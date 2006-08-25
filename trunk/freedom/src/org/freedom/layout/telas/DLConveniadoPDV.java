package org.freedom.layout.telas;

import java.sql.Connection;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDialogo;


public class DLConveniadoPDV extends FDialogo {
	
	private static final long serialVersionUID = 1;
	
	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeConv = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private ListaCampos lcConv = new ListaCampos( this, "" );

	public DLConveniadoPDV() {

		super();
		
		setTitulo( "Conveniado" );
		setAtribos( 300, 400 );
		
//			FK Conveniado
		lcConv.add(new GuardaCampo( txtCodConv, "CodConv", "Cód.conv.", ListaCampos.DB_PK, false));
		lcConv.add(new GuardaCampo( txtNomeConv, "NomeConv", "Nome do coveniado", ListaCampos.DB_SI, false));
		lcConv.montaSql( false, "CONVENIADO", "AT" );    
		lcConv.setQueryCommit(false);
		lcConv.setReadOnly(true);
		txtCodConv.setTabelaExterna(lcConv);
		
		montaTela();
		
	}
	
	private void montaTela() {
		
		adic( new JLabel( "Cód.conv." ), 7, 10, 100, 20 );
		adic( txtCodConv, 7, 30, 100, 20 );
		adic( new JLabel( "Nome do conveniado" ), 110, 10, 250, 20 );
		adic( txtNomeConv, 110, 30, 250, 20 );
		
	}
	
	public void setConexao( Connection cn ) {

		super.setConexao(cn);
		
		lcConv.setConexao( cn );
		
	}
	
	public static void main( String arg[] ) {
		
		DLConveniadoPDV teste = new DLConveniadoPDV();
		teste.setVisible(true);
		
	}

}
