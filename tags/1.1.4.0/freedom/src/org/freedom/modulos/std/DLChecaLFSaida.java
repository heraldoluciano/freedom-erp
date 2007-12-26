/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLChecaLFSaida.java <BR>
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
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;

import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.componentes.Tabela;
import org.freedom.telas.DLRelatorio;

public class DLChecaLFSaida extends DLRelatorio {

	private static final long serialVersionUID = 1L;

	public Tabela tab = new Tabela();

	private JScrollPane spnTab = new JScrollPane( tab );

	private JPanelPad pnCliente = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	public DLChecaLFSaida() {

		setTitulo( "Inconsistências de Vendas" );
		setAtribos( 600, 320 );

		c.add( pnCliente, BorderLayout.CENTER );
		pnCliente.add( spnTab, BorderLayout.CENTER );
		tab.adicColuna( "Pedido" );
		tab.adicColuna( "Série" );
		tab.adicColuna( "Nota" );
		tab.adicColuna( "Emissão" );
		tab.adicColuna( "Inconsistência" );

		tab.setTamColuna( 80, 0 );
		tab.setTamColuna( 40, 1 );
		tab.setTamColuna( 80, 2 );
		tab.setTamColuna( 100, 3 );
		tab.setTamColuna( 200, 4 );

	}

	public void imprimir( boolean bVal ) {

		if ( bVal ) {
			System.out.println( "imprimiu" );
		}
	}

};
