/**
 * @version 8/02/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FPrefereFBB.java <BR>
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
 * Tela de preferencias para Febraban
 * 
 */

package org.freedom.modulos.fnc;

import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FTabDados;

public class FPrefereFBB extends FTabDados {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelGeral = new JPanelPad();
	
	private final JPanelPad panelSiacc = new JPanelPad();
	
	private final JPanelPad panelCnab = new JPanelPad();

	private final JTextFieldFK txtNomeEmp = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );
	
	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldFK txtTipo = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldFK txtCodConv = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );
	
	private final JTextFieldFK txtVersao = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldFK txtIdentServ = new JTextFieldFK( JTextFieldPad.TP_STRING, 17, 0 );
	
	private final JTextFieldFK txtContaCompr = new JTextFieldFK( JTextFieldPad.TP_STRING, 16, 0 );
	
	private final JTextFieldFK txtIdentAmbCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );
	
	private final JTextFieldFK txtIdentAmbBco = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );
		
	private final JTextFieldFK txtNroSeq = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private ListaCampos lcBanco = new ListaCampos( this, "BO" );

	public FPrefereFBB() {

		setTitulo( "Preferências Gerais" );
		setAtribos( 50, 50, 400, 400 );
		
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		
		montaTela();

		/*lcCampos.setMensInserir( false );
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );

		setListaCampos( false, "PREFERE6", "SG" );*/

	}
	
	private void montaTela() {

		/*****************
		 *     GERAL     *
		 *****************/

		setPainel( panelGeral );
		adicTab( "Geral", panelGeral );
		adicCampo( txtNomeEmp, 7, 25, 250, 20, "NomeEmp", "Nome da empresa", ListaCampos.DB_SI, true );

		/*****************
		 *     SIACC     *
		 *****************/

		setPainel( panelSiacc );
		adicTab( "SIACC", panelSiacc );

		adicCampo( txtCodBanco, 7, 20, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_FK, txtNomeBanco, true );
		adicDescFK( txtNomeBanco, 110, 20, 260, 20, "NomeBanco", "Nome do banco" );
		//adicDB( txtTipo, 7, 70, 300, 30, "TipoFebraban", "Tipo :", false );
		
		adicCampo( txtCodConv, 7, 20, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_SI, false );
		adicCampo( txtVersao, 7, 20, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_SI, false );
		adicCampo( txtIdentServ, 7, 20, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_SI, false );
		adicCampo( txtContaCompr, 7, 20, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_SI, false );
		adicCampo( txtIdentAmbCli, 7, 20, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_SI, false );
		adicCampo( txtIdentAmbBco, 7, 20, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_SI, false );
		
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
		//lcCampos.carregaDados();
	}
}
