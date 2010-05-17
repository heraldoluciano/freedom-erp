/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPEstacao.java <BR>
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
 * Tela de cadastros de estações de trabalho.
 * 
 */

package org.freedom.modulos.rep;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPEstacao extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	

	public RPEstacao() {

		super( false );
		setTitulo( "Estação de trabalho" );
		setAtribos( 50, 50, 420, 140 );
		
		montaTela();		
		setListaCampos( true, "ESTACAO", "SG" );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodEst, 7, 30, 80, 20, "CodEst", "Cód.est.", ListaCampos.DB_PK, true );
		adicCampo( txtDescEst, 90, 30, 305, 20, "DescEst", "Descrição da estação de trabalho", ListaCampos.DB_SI, true );
	}
}
