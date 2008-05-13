/**
 * @version 13/05/2008 <BR>
 * @author Setpoint Informática Ltda./Felipe Daniel Elias <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: FTipoVend
 * @(#)FClasCli.java <BR>
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

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;



public class FTipoVend extends FDados{
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodTipoVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	
	public FTipoVend(){
		
		super();
		setAtribos( 50, 50, 350, 165 );
		setTitulo( "Cadastro de tipos de comissionado" );
		adicCampo( txtCodTipoVenda, 7, 20, 70, 20, "CODTIPOVEND", "Cód.tp.vend", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoVenda, 80, 20, 250, 20, "DESCTIPOVEND", "Descrição do tipo da venda", ListaCampos.DB_PK, true );
		setListaCampos( true, "TIPOVEND", "VD" );
		
	}

}
