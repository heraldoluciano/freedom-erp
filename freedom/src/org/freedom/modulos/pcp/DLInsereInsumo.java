package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FFDialogo;


public class DLInsereInsumo extends FFDialogo {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pnControl = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCab = new JPanelPad( 400, 45 );
	
	private Tabela tabInsumos = new Tabela();

	private JScrollPane spnTabInsumos = new JScrollPane( tabInsumos );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	 
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcProd = new ListaCampos( this, ""  );
	
	private enum eInsert {
	
		CODPROD, DESCPROD, QTD, LOTE
	};
	
	
	public DLInsereInsumo(){
		
		setTitulo( "" );
		setAtribos( 670, 480 );
		
		montaListaCampos();
		montaTela();
	}
	
	private void montaTela(){
		
		pinCab.setPreferredSize( new Dimension( 400, 100 ) );
		pnControl.add( pinCab, BorderLayout.NORTH );
		pnControl.add( spnTabInsumos, BorderLayout.CENTER );
		c.add( pnControl, BorderLayout.CENTER );
		
		tabInsumos.adicColuna( "Cód.Prod" );
		tabInsumos.adicColuna( "Descrição do produto" );
		tabInsumos.adicColuna( "Qtd." );
		tabInsumos.adicColuna( "Lote" );
		
		tabInsumos.setTamColuna( 400, eInsert.DESCPROD.ordinal() );
		
		adic( txtCodProd, 7, 25, 70, 20 );
		
	}
	
	private void montaListaCampos(){
		  
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
	}

}
