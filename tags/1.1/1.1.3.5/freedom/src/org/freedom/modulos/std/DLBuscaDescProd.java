package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.Tabela;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;

public class DLBuscaDescProd extends FDialogo {

	private static final long serialVersionUID = 1l;

	private JPanelPad pn = new JPanelPad();

	private JTextAreaPad txa = new JTextAreaPad();

	private Tabela tabDesc = new Tabela();

	private JButton btbusca = new JButton( Icone.novo( "btObs.gif" ) );

	public DLBuscaDescProd( final String sDesc ) {

		super();

		setTitle( "Descrição completa" );
		setAtribos( 400, 200 );

		txa.setVlrString( sDesc );

		tabDesc.adicColuna( "pedido" );
		tabDesc.adicColuna( "item" );
		tabDesc.adicColuna( "produto" );
		tabDesc.adicColuna( "descrição" );

		tabDesc.setTamColuna( 50, 0 );
		tabDesc.setTamColuna( 40, 1 );
		tabDesc.setTamColuna( 50, 2 );
		tabDesc.setTamColuna( 500, 3 );
		
		tabDesc.addMouseListener( new MouseAdapter() {
			public void mouseClicked( MouseEvent e ) {
				if( e.getClickCount() == 2 ) {
					setText();	
				}
			}
		});

		pn.adic( new JScrollPane( txa ), 0, 0, 385, 133 );
		pn.adic( new JScrollPane( tabDesc ), 0, 134, 385, 120 );

		c.add( pn );

		pnRodape.add( btbusca, BorderLayout.WEST );

		btbusca.addActionListener( this );
	}

	private void buscaDescricao() {
		
		try {
			
			tabDesc.limpa();
			
			int count = 0;
			
			String sSQL = "SELECT I.CODVENDA, I.CODITVENDA, I.CODPROD, I.OBSITVENDA " +
						  "FROM VDITVENDA I " +
						  "WHERE I.CODEMP=? AND I.CODFILIAL=? AND I.TIPOVENDA='V' " +
						  "AND I.OBSITVENDA LIKE ?";
			
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, "%" + txa.getVlrString().trim() + "%" );
			
			ResultSet rs = ps.executeQuery();
			
			Vector data = new Vector();
			
			while ( rs.next() ) {
				
				for ( int i = 0; i < tabDesc.getNumColunas(); i++ ) {
					data.add( rs.getString( 1 + i ) );
				}
				
				tabDesc.adicLinha( data );
				
				count++;
			}
			
			if ( count > 0 ) {
				
				setSize( 400, 320 );
				setVisible( true );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}		
	}
	
	private void setText() {
		
		if ( tabDesc.getLinhaSel() > -1 ) {
			
			txa.setVlrString( (String) tabDesc.getValor( tabDesc.getLinhaSel(), 3 ) );
			setSize( 400, 200 );
			setVisible( true );
		}
	}

	public String getTexto() {

		return txa.getVlrString();
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btbusca ) {

			buscaDescricao();
		}

		super.actionPerformed( e );
	}
}
