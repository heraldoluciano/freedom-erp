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
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Tabela;
import org.freedom.ecf.app.ControllerECF;
import org.freedom.ecf.driver.AbstractECFDriver;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FAliquota extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JPanelPad pinCab = new JPanelPad( 400, 60 );

	private final JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JTextFieldPad txtAliquota = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 4, 2 );

	private final JButton btInsere = new JButton( Icone.novo( "btExecuta.gif" ) );

	private final Tabela tab = new Tabela();

	private final JScrollPane spnTab = new JScrollPane( tab );

	private final ControllerECF ecf;
	
	private List<String> aliquotas;
	

	public FAliquota() {

		setTitulo( "Ajusta aliquotas" );
		setAtribos( 100, 150, 402, 270 );
		
		ecf = new ControllerECF( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo );
		
		montaTela();
		
		setToFrameLayout();
		
		carregaTabela();
	}
		
	private void montaTela() {
		
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
	}

	private void insereAliquota() {

		DecimalFormat df = new DecimalFormat( "00.00" );
		String strAliquota = df.format( txtAliquota.getVlrBigDecimal().doubleValue() ).replaceAll( ",", "" );
		
		if ( aliquotas.contains( strAliquota ) ) {
			Funcoes.mensagemErro( this, "Alíquota já foi cadastrada!" );
		}
		else {
			if ( aliquotas.size() > 15 ) {
				Funcoes.mensagemErro( this, "Quantidade maxima de aliquotas já foi atingida!" );
			}
			else {
				if ( ! ecf.programaAliquota( txtAliquota.getVlrBigDecimal(), AbstractECFDriver.ICMS ) ) {
					Funcoes.mensagemErro( this, ecf.getMessageLog() );
				}					
				carregaTabela();
			}
		}
	}

	private void carregaTabela() {
		
		aliquotas = ecf.getAllAliquotas();
		
		tab.limpa();
		tab.adicLinha();
		
		String aliq = null;
		DecimalFormat df = new DecimalFormat( "00.00" );
		
		int iRow = 0;
		int iCol = 0;
		int size = aliquotas.size();
		
		for ( int i=0; i < size; i++ ) {
			
			aliq = 
				"T" + 
				Funcoes.strZero( String.valueOf( i + 1 ), 2 ) + 
				" = " + 
				df.format( new Float( aliquotas.get( i ) ).floatValue() / 100 ) + " %";
			
			tab.setValor( aliq, iRow, iCol++ );
			
			if ( iCol == 3 ) {
				
				iCol = 0;
				iRow++;
				
				if ( i < size - 1 ) {
					tab.adicLinha();
				}				
			}			
		}
	}

	@Override
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btInsere ) {
			insereAliquota();
		}
	}

}
