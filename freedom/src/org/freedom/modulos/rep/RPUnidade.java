/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPUnidade.java <BR>
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
 * Tela para cadastro de unidades.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPUnidade extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDescUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	public RPUnidade() {

		super();
		setTitulo( "Cadastro de unidades" );		
		setAtribos( 50, 50, 410, 140 );
		
		montaTela();
		setListaCampos( true, "UNIDADE", "RP" );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodUnid, 7, 30, 70, 20, "CodUnid", "Cód.unid.", ListaCampos.DB_PK, true );
		adicCampo( txtDescUnid, 80, 30, 300, 20, "DescUnid", "Descrição da unidade", ListaCampos.DB_SI, true );
	}
}
