/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLRTipoCli.java <BR>
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

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

import java.util.Vector;

public class DLRTipoCli extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JRadioGroup rgOrdem = null;
	
	private JRadioGroup rgTipo = null;

	private JLabelPad lbOrdem = new JLabelPad( "Ordenar por:" );


	public DLRTipoCli() {

		setTitulo( "Ordem do Relatório" );
		setAtribos( 300, 190 );
		
		Vector vLabs = new Vector();
		Vector vVals = new Vector();
		
		vLabs.addElement( "Código" );
		vLabs.addElement( "Descrição" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		rgOrdem = new JRadioGroup( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );
		adic( lbOrdem, 7, 0, 80, 15 );
		adic( rgOrdem, 7, 20, 270, 30 );
		
		
		Vector vLabs1 = new Vector();
		Vector vVals1 = new Vector();
		
		vVals1.addElement( "G" );
		vVals1.addElement( "T" );
		vLabs1.addElement( "Grafico" );
		vLabs1.addElement( "Texto" );
		rgTipo = new JRadioGroup( 1, 2, vLabs1, vVals1 );
		rgTipo.setVlrString( "T" );
		
		adic( rgTipo, 7, 60, 270, 30 );
	}

	public String getValor() {

		String sRetorno = "DESCTIPOCLI";

		if ( rgOrdem.getVlrString().compareTo( "C" ) == 0 ) {
			sRetorno = "CODTIPOCLI";
		}
		
		return sRetorno;
	}
	
	public String getTipo(){
		
		return rgTipo.getVlrString();
	}
}
