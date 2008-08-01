/**
 * @version 01/08/2008 <BR>
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FMetodoAnalitico.java <BR>
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
 * Tela para cadastro de tipos de clientes.
 * 
 */

package org.freedom.modulos.pcp;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;


public class FMetodoAnalitico extends FDados {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodMtAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtDescMtAnalise = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextAreaPad txaObsMtAnalise = new JTextAreaPad();

	public FMetodoAnalitico(){
		
		setTitulo( "Métodos Analíticos" );
		setAtribos( 50, 50, 380, 220 );
		montaTela();
	}
	
	private void montaTela(){
		
		adicCampo( txtCodMtAnalise, 7, 20, 70, 20, "CodMtAnalise", "Cód.Método", ListaCampos.DB_PK, true );
		adicCampo( txtDescMtAnalise, 80, 20, 260, 20, "DescMtAnalise", "Descrição do método analítico", ListaCampos.DB_SI, true );
		adicDB( txaObsMtAnalise, 7, 60, 335, 80, "ObsMtAnalise", "Observação do método", false );
		
		setListaCampos( true, "METODOANALISE", "PP" );
	
	}
}
