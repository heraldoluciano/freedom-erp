/**
 * @version 08/2007 <BR>
 * @author Setpoint Informática Ltda./ Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FCartCob.java <BR>
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
 * Formulário de cadastro de Carteiras de Cobrança.<br>
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.event.ActionListener;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FCartCob extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtDescCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCartCobCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	protected final ListaCampos lcBanco = new ListaCampos( this, "BO" );
	

	public FCartCob() {

		super();
		setTitulo( "Cadastro de tipos de clientes" );
		setAtribos( 50, 50, 420, 180 );
		
		montaListaCampos();
		
		montaTela();
		
		setListaCampos( false, "CARTCOB", "FN" );
				
	}
	
	private void montaTela() {
		
		adicCampo( txtCodBanco, 7, 30, 80, 20, "CodBanco", "Cód. banco", ListaCampos.DB_PF, txtNomeBanco, true );
		adicDescFK( txtNomeBanco, 90, 30, 300, 20, "NomeBanco", "Nome do banco" );

		adicCampo( txtCodCartCob, 7, 70, 80, 20, "CodCartCob", "Cód.cart.cob.", ListaCampos.DB_PK, true );
		adicCampo( txtDescCartCob, 90, 70, 237, 20, "DescCartCob", "Descrição da carteira de cobraça", ListaCampos.DB_SI, true );
		adicCampo( txtCartCobCnab, 330, 70, 60, 20, "CartCobCnab", "Cód. cnab", ListaCampos.DB_SI, true );
		
	}
	
	private void montaListaCampos() {
		
		/***************
		 *   FNBANCO   *
		 ***************/

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setListaCampos( lcBanco );
		txtCodBanco.setFK( true );
		txtNomeBanco.setListaCampos( lcBanco );
	}

	@ Override
	public synchronized void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}
}
