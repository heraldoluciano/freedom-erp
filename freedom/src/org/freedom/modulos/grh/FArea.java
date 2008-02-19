/**
 * @version 28/01/2005 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FArea.java <BR>
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
 * Formulário para cadastro das áreas de conhecimento para uso nas funções de recrutamento e seleção.
 * 
 */

package org.freedom.modulos.grh;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FArea extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodArea = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescArea = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	public FArea() {

		super();
		setTitulo( "Cadastro de Areas" );
		setAtribos( 50, 50, 380, 135 );

		montaTela();
		
		setImprimir( false );
	}

	private void montaTela() {

		adicCampo( txtCodArea, 7, 20, 70, 20, "CodArea", "Cód.area", ListaCampos.DB_PK, true );
		adicCampo( txtDescArea, 80, 20, 250, 20, "DescArea", "Descrição da area", ListaCampos.DB_SI, true );
		setListaCampos( true, "AREA", "RH" );
		lcCampos.setQueryInsert( false );
	}
}
