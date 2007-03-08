/**
 * @version 07/03/2007 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLIdentCli.java <BR>
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
 */

package org.freedom.modulos.fnc;

import java.awt.Component;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLIdentCli extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private final JTextFieldPad txtRazCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtAgenciaCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 9, 0 );

	private final JTextFieldPad txtIdentCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private static boolean MRET = false;

	public DLIdentCli( final Component cOrig ) {

		super( cOrig );
		setTitulo( "Identificação do cliente" );
		setAtribos( 420, 200 );
		
		txtCodCli.setAtivo( false );
		txtRazCli.setAtivo( false );
		
		montaTela();
	}
	
	private void montaTela() {
		
		txtAgenciaCli.setRequerido( true );
		txtIdentCli.setRequerido( true );
		
		adic( new JLabelPad( "Cód.cli." ), 7, 5, 80, 20 );
		adic( txtCodCli, 7, 25, 80, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 90, 5, 280, 20 );
		adic( txtRazCli, 90, 25, 280, 20 );

		adic( new JLabelPad( "Agência" ), 7, 50, 80, 20 );
		adic( txtAgenciaCli, 7, 70, 80, 20 );
		adic( new JLabelPad( "Identificação" ), 90, 50, 100, 20 );
		adic( txtIdentCli, 90, 70, 100, 20 );
	}

	public static Object[] execIdentCli( final Component cOrig, final Integer codCli, final String razCli, final String agenciaCli, final String identCli ) {

		Object[] retorno = { new Boolean( false ), "", "" };
		DLIdentCli dl = new DLIdentCli( cOrig );
		
		dl.txtCodCli.setVlrInteger( codCli );
		dl.txtRazCli.setVlrString( razCli );
		dl.txtAgenciaCli.setVlrString( agenciaCli );
		dl.txtIdentCli.setVlrString( identCli );
		dl.execShow();
		
		if ( MRET ) {
			retorno[ 0 ] = new Boolean( MRET );
			retorno[ 1 ] = dl.txtAgenciaCli.getVlrString();
			retorno[ 2 ] = dl.txtIdentCli.getVlrString();
		}
		
		return retorno;
	}

	@ Override
	public void ok() {

		if ( txtAgenciaCli.getVlrString().trim().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Campo agência é obrigatório!" );
			txtAgenciaCli.requestFocus();
			return;
		}
		else if ( txtIdentCli.getVlrString().trim().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Campo identificação é obrigatório!" );
			txtIdentCli.requestFocus();
			return;
		}
		
		MRET = true;
		
		super.ok();
	}

	@ Override
	public void cancel() {

		MRET = false;
		
		super.cancel();
	}

}
