/**
 * @version 15/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FGravaMoeda.java <BR>
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
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.ecf.app.ControllerECF;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FGravaMoeda extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtSingMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldFK txtPlurMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final ControllerECF ecf;

	private ListaCampos lcMoeda = new ListaCampos( this, "" );
	

	public FGravaMoeda() {

		setTitulo( "Ajusta moeda na impressora." );
		setAtribos( 385, 180 );

		ecf = new ControllerECF( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo );
		
		montaListaCampos();
		montaTela();
	}
		
	private void montaListaCampos() {
		
		txtCodMoeda.setTipo( JTextFieldPad.TP_STRING, 4, 0 );
		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtSingMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.add( new GuardaCampo( txtPlurMoeda, "PlurMoeda", "Descrição do plural da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "FN" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setFK( true );
		txtCodMoeda.setNomeCampo( "CodMoeda" );
		txtCodMoeda.setTabelaExterna( lcMoeda );
	}
	
	private void montaTela() {

		adic( new JLabelPad( "Sigla" ), 7, 5, 50, 15 );
		adic( txtCodMoeda, 7, 20, 50, 20 );
		adic( new JLabelPad( "Nome sing." ), 60, 5, 147, 15 );
		adic( txtSingMoeda, 60, 20, 147, 20 );
		adic( new JLabelPad( "Nome slur." ), 210, 5, 150, 15 );
		adic( txtPlurMoeda, 210, 20, 150, 20 );
		adic( new JLabelPad( 
				"<HTML>" +
				"Este comando so será executado<BR>" +
				"se não tiver havido movimentação no dia." +
				"</HTML>" ), 7, 45, 400, 40 );
	}

	private void gravaMoeda() {

		if ( ! ecf.programaMoeda( txtCodMoeda.getVlrString(), txtSingMoeda.getVlrString(), txtPlurMoeda.getVlrString() ) ) {
			Funcoes.mensagemErro( this, ecf.getMessageLog() );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			gravaMoeda();
		}
		super.actionPerformed( evt );
	}

	@ Override
	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == btOK && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btOK.doClick();
		}
		else if ( e.getSource() == btCancel && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btCancel.doClick();
		}
		else {
			super.keyPressed( e );
		}
	}

	public void setConexao( Connection cn ) {

		lcMoeda.setConexao( cn );
	}
}
