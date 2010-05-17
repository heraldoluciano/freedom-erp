/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPMoeda.java <BR>
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
 * Tela de cadastros de moedas.
 * 
 */

package org.freedom.modulos.rep;

import java.util.Vector;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPMoeda extends FDados {

	private static final long serialVersionUID = 1L;

	private final JRadioGroup rgTipo;

	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldPad txtSingMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtPlurMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtDecsMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtDecpMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	public RPMoeda() {

		super( false );
		setTitulo( "Cadastro de Moedas" );
		setAtribos( 50, 50, 425, 270 );

		Vector<String> vValsTipo = new Vector<String>();
		Vector<String> vLabsTipo = new Vector<String>();

		vValsTipo.addElement( "C" );
		vValsTipo.addElement( "I" );
		vLabsTipo.addElement( "Moeda corrente" );
		vLabsTipo.addElement( "Indice de valores" );
		rgTipo = new JRadioGroup( 2, 1, vLabsTipo, vValsTipo );
		rgTipo.setVlrString( "C" );
		
		montaTela();
		
		setListaCampos( false, "MOEDA", "RP" );
		lcCampos.setQueryInsert( false );

	}
	
	private void montaTela() {
		
		adicCampo( txtCodMoeda, 7, 30, 80, 20, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK, true );
		adicCampo( txtSingMoeda, 90, 30, 305, 20, "SingMoeda", "Nome no singular", ListaCampos.DB_SI, true );
		
		adicCampo( txtPlurMoeda, 7, 80, 200, 20, "PlurMoeda", "Nome no plural", ListaCampos.DB_SI, true );
		adicCampo( txtDecsMoeda, 7, 120, 200, 20, "DecsMoeda", "Decimal no singular", ListaCampos.DB_SI, true );
		adicCampo( txtDecpMoeda, 7, 160, 200, 20, "DecpMoeda", "Decimal no plural", ListaCampos.DB_SI, true );
		adicDB( rgTipo, 220, 80, 175, 50, "TipoMoeda", "Tipo", false );
	}
}
