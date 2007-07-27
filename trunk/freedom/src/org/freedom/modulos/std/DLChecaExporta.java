/**
 * @version 07/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLChecaExporta.java <BR>
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
 * Dialogo que mostra os registros com erros da exportação Contabil/Livros Fiscais.
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JScrollPane;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.telas.DLRelatorio;

public class DLChecaExporta extends DLRelatorio {

	private static final long serialVersionUID = 1L;
	
	private final String FREEDOM_CONTABIL = "01";
	
	private final String SAFE_CONTABIL = "02";

	public Tabela tab = new Tabela();

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final String tipo;
	
	private final String sistema;

	public DLChecaExporta( final String tipo, final String sistema ) {

		super();
		setModal( true );		
		setTitulo( "Inconsistências de Vendas" );
		setAtribos( 600, 400 );
		
		this.tipo = tipo;
		this.sistema = sistema;

		c.add( panelGeral, BorderLayout.CENTER );		
		
		montaTela();
	}
	
	private void montaTela() {
		
		if ( SAFE_CONTABIL.equals( sistema ) ) {
			
			montaTelaSafeContabil();
		}
	}
	
	private void montaTelaSafeContabil() {
		
		panelGeral.add( new JScrollPane( tab ), BorderLayout.CENTER );

		tab.adicColuna( "Tipo" );
		tab.adicColuna( "Conta Deb." );
		tab.adicColuna( "Conta Cred." );
		tab.adicColuna( "Documento" );
		tab.adicColuna( "Data" );
		tab.adicColuna( "Valor" );
		tab.adicColuna( "Historico" );
		tab.adicColuna( "Filial" );

		tab.setTamColuna( 100, 0 );
		tab.setTamColuna( 100, 1 );
		tab.setTamColuna( 100, 2 );
		tab.setTamColuna( 80, 3 );
		tab.setTamColuna( 90, 4 );
		tab.setTamColuna( 100, 5 );
		tab.setTamColuna( 300, 6 );
		tab.setTamColuna( 50, 7 );
	}

	public void carregaDados( Object dados ) {
		
		if ( SAFE_CONTABIL.equals( sistema ) ) {
			
			try {
				carregaDadosSafeContabil( (List<FExporta.SafeBean>) dados );
			} catch ( ClassCastException e ) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void carregaDadosSafeContabil( List<FExporta.SafeBean> args ) {
		
		tab.limpa();
		int row = 0;
		
		for ( FExporta.SafeBean sb : args ) {
			
			tab.adicLinha();
			
			tab.setValor( sb.getStrTipo(), row, 0 );
			tab.setValor( sb.getContadeb(), row, 1 );
			tab.setValor( sb.getContacred(), row, 2 );
			tab.setValor( sb.getDocumento(), row, 3 );
			tab.setValor( sb.getData(), row, 4 );
			tab.setValor( sb.getValor().setScale( 2, BigDecimal.ROUND_HALF_UP ), row, 5 );
			tab.setValor( sb.getHistorico(), row, 6 );
			tab.setValor( sb.getFilial(), row, 7);
			
			row++;
		}
		
	}
	
	public void imprimir( boolean bVal ) {

		if ( bVal ) {
			System.out.println( "imprimiu" );
		}
	}

};
