/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FPrefereGeral.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.fnc.view.frame.crud.tabbed;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FTabDados;


public class FPrefereFNC extends FTabDados {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinGeral = new JPanelPad( 330, 350 );

	private JPanelPad pinFin = new JPanelPad();

	private JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDescMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private ListaCampos lcMoeda = new ListaCampos( this, "MO" );

	public FPrefereFNC() {

		setTitulo( "Preferências Gerais" );
		setAtribos( 50, 50, 355, 200 );

		lcCampos.setMensInserir( false );

		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtDescMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "FN" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setTabelaExterna( lcMoeda );

		// Geral

		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );
		adicCampo( txtAnoCC, 7, 25, 100, 20, "AnoCentroCusto", "Ano Base C.C.", ListaCampos.DB_SI, true );

		// Financeiro

		setPainel( pinFin );
		adicTab( "Financeiro", pinFin );

		adicCampo( txtCodMoeda, 7, 20, 70, 20, "CodMoeda", "Cód.moeda", ListaCampos.DB_FK, txtDescMoeda, true );
		adicDescFK( txtDescMoeda, 80, 20, 230, 20, "SingMoeda", "Descrição da moeda corrente." );

		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );

		setListaCampos( false, "PREFERE1", "SG" );

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcMoeda.setConexao( cn );
		lcCampos.carregaDados();
	}
}
