package org.freedom.modulos.std;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;



public class FTipoVend extends FDados{
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodTipoVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	
	public FTipoVend(){
		
		super();
		setAtribos( 50, 50, 350, 165 );
		setTitulo( "Cadastro de tipos de comissionado" );
		adicCampo( txtCodTipoVenda, 7, 20, 70, 20, "CODTIPOVEND", "Cód.tp.vend", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoVenda, 80, 20, 250, 20, "DESCTIPOVEND", "Descrição do tipo da venda", ListaCampos.DB_PK, true );
		setListaCampos( true, "TIPOVEND", "VD" );
		
	}

}
