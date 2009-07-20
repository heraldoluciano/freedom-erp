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
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
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
