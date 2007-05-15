/**
 * @version 17/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FAliquota.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Tabela;
import org.freedom.drivers.ECFDriver;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FAliquota extends FFDialogo implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 400, 60 );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtAliquota = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 4, 2 );

	private JButton btInsere = new JButton( Icone.novo( "btExecuta.gif" ) );

	private Tabela tab = new Tabela();

	private JScrollPane spnTab = new JScrollPane( tab );

	private ECFDriver ecf = new ECFDriver( !AplicativoPDV.usaEcfDriver() );

	//private String sAliquotas = "";
	
	private Vector vAliquotas = new Vector();

	public FAliquota() {

		setTitulo( "Ajusta aliquotas" );
		setAtribos( 100, 150, 402, 270 );

		btInsere.setPreferredSize( new Dimension( 30, 30 ) );
		btInsere.setToolTipText( "Insere alíquota" );
		btInsere.addActionListener( this );

		setPanel( pnCli );
		pnCli.add( spnTab, BorderLayout.CENTER );
		pnCli.add( pinCab, BorderLayout.NORTH );

		pinCab.adic( new JLabelPad( "Inserir aliquota" ), 7, 5, 87, 20 );
		pinCab.adic( txtAliquota, 7, 25, 87, 20 );
		pinCab.adic( btInsere, 150, 15, 30, 30 );

		tab.adicColuna( "" );
		tab.adicColuna( "" );
		tab.adicColuna( "" );

		tab.setTamColuna( 130, 0 );
		tab.setTamColuna( 130, 1 );
		tab.setTamColuna( 130, 2 );
		
		tab.setFont( new Font( "Arial", Font.PLAIN, 12 ) );
		
		setToFrameLayout();
		
		carregaTabela();
		
	}

	private void insereAliquota() {

		if ( ( txtAliquota.floatValue() <= 0 ) || ( txtAliquota.floatValue() > 99.99 ) ) {
			Funcoes.mensagemErro( this, "Alíquota inválida" );
			txtAliquota.requestFocus();
		}
		else {
			
			String sAliq = txtAliquota.getText().trim();
			String sVal = Funcoes.strZero( txtAliquota.getVlrBigDecimal().intValue() + "", 2 ) + sAliq.substring( sAliq.length() - 2 );
			
			if ( vAliquotas.contains( sVal ) ) {
				Funcoes.mensagemErro( this, "Alíquota já foi cadastrada!" );
			}
			else {
				if ( vAliquotas.size() > 15 ) {
					Funcoes.mensagemErro( this, "Quantidade maxima de aliquotas já foi atingida!" );
				}
				else {
					if ( AplicativoPDV.bECFTerm ) {
						ecf.programaAliquotas( sVal, 0 );
					}
					carregaTabela();
				}

			}
		}

	}

	private void carregaTabela() {
		
		if ( !AplicativoPDV.bECFTerm ) {
			return;
		}
		
		vAliquotas = getAliquotas( ecf );
		
		tab.limpa();
		tab.adicLinha();
		
		String aliq = null;
		DecimalFormat df = new DecimalFormat( "00.00" );
		
		int iRow = 0;
		int iCol = 0;
		int tamanho = vAliquotas.size();
		
		for ( int i=0; i < tamanho; i++ ) {
			
			aliq = "T" + Funcoes.strZero( String.valueOf( i + 1 ), 2 ) + " = " + df.format( new Float( (String)vAliquotas.elementAt( i ) ).floatValue() / 100 ) + " %";
			
			tab.setValor( aliq, iRow, iCol++ );
			
			if ( iCol == 3 ) {
				
				iCol = 0;
				iRow++;
				
				if ( i < tamanho - 1 ) {
					tab.adicLinha();
				}
				
			}
			
		}

	}
	
	public static Vector getAliquotas( final ECFDriver ecf ) {
		
		final Vector aliquotas = new Vector();
		
		String sAliquotas = ( ecf.retornoAliquotas() ).trim();
		
		int tamanho = 0;
		
		if ( AplicativoPDV.usaEcfDriver() ) {
			
			tamanho = sAliquotas.length() / 4;
			
			for ( int i=0; i < tamanho; i++ ) {
				
				aliquotas.addElement( sAliquotas.substring( i * 4, ( i * 4 ) + 4 ) );
				
			}
			
		} else {
			
			tamanho = ( ( ( sAliquotas.length() ) + 1 ) / 5 );
			
			for ( int i=0; i < tamanho; i++ ) {
				
				aliquotas.addElement( i == 1 ? sAliquotas.substring( 0, 4 ) : sAliquotas.substring( ( i * 5 ) - 5, ( i * 5 ) - 1 ) );

			}
			
			
		}
		
		return aliquotas;
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btInsere ) {
			insereAliquota();
		}
	}

}
