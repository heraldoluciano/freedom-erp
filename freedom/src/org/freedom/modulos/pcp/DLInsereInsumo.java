package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLInsereInsumo extends FFDialogo implements ActionListener{


	private static final long serialVersionUID = 1L;
	
	private JPanelPad pnControl = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCab = new JPanelPad( 400, 45 );
	
	private Tabela tabInsumos = new Tabela();

	private JScrollPane spnTabInsumos = new JScrollPane( tabInsumos );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	 
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtQtd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	public JButton btInserir = new JButton( Icone.novo("btGerar.gif"));

	private ListaCampos lcProd = new ListaCampos( this, ""  );
	
	int iLinha = 0;
	
	private enum eInsert {
	
		CODPROD, DESCPROD, QTD, LOTE
	};
	
	
	public DLInsereInsumo( Connection con ){
		
		setTitulo( "" );
		setAtribos( 630, 430 );
		setConexao( con );
		
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
		
		tabInsumos.setTamColuna( 350, eInsert.DESCPROD.ordinal() );
		
		setPainel( pinCab );
		adic( new JLabelPad("Cód.Prod"), 7, 5, 70, 20 );
		adic( txtCodProd, 7, 25, 70, 20 );
		adic( new JLabelPad("Descrição do produto"), 80, 5, 250, 20 );
		adic( txtDescProd, 80, 25, 300, 20 );
		adic( new JLabelPad("Qtd."), 383, 5, 50, 20 );
		adic( txtQtd, 383, 25, 50, 20 );
		adic( new JLabelPad("Lote"), 440, 5, 50, 20 );
		adic( txtLote, 440, 25, 100, 20 );
		adic( btInserir, 550, 25, 35, 35 );
		
		txtCodProd.setRequerido( true );
		txtQtd.setRequerido( true );
		
		btInserir.addActionListener( this );
		
	}
	
	private void insertGrid( int codprod, String descprod, BigDecimal qtd ){
		
		int pos = -1;

		if ( codprod == 0 ) {

			Funcoes.mensagemInforma( this, "Produto não encontrado!" );
			txtCodProd.requestFocus();
			return;
		}

		for ( int i = 0; i < tabInsumos.getNumLinhas(); i++ ) {

			if ( codprod == ( (Integer) tabInsumos.getValor( i, eInsert.CODPROD.ordinal() ) ).intValue() ) {
				pos = i;
				qtd = qtd.add( (BigDecimal) tabInsumos.getValor( i, eInsert.QTD.ordinal() ) );
				
				break;
			}
		}

		if ( pos == -1 ) {

			tabInsumos.adicLinha();
			pos = tabInsumos.getNumLinhas() - 1;
		}

		tabInsumos.setValor( codprod, pos, eInsert.CODPROD.ordinal() );
		tabInsumos.setValor( descprod, pos, eInsert.DESCPROD.ordinal() );
		tabInsumos.setValor( qtd, pos, eInsert.QTD.ordinal() );
	}
	
	private void limpaCampo(){
		
		txtCodProd.setVlrString( "" );
		txtDescProd.setVlrString( "" );
		txtQtd.setVlrString( "" );
		
	}
	
	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed(evt);
		
		if( evt.getSource() == btInserir ){
			
			insertGrid( txtCodProd.getVlrInteger(), txtDescProd.getVlrString(), txtQtd.getVlrBigDecimal() );
		}
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
	
	public void setConexao( Connection con ){
		
		super.setConexao( con );
		lcProd.setConexao( con );
	}
}
