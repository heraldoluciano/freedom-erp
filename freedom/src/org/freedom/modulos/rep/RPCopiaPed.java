package org.freedom.modulos.rep;

import java.awt.Component;
import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.JLabelPad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class RPCopiaPed extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcCli = new ListaCampos( this, "" );

	public RPCopiaPed( Component cOrig ) {

		super( cOrig );
		setTitulo( "Cópia de orçamento" );
		setAtribos( 320, 150 );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, true ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "RP" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		adic( new JLabelPad( "Cód.cli." ), 7, 5, 200, 20 );
		adic( txtCodCli, 7, 25, 80, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 90, 5, 200, 20 );
		adic( txtRazCli, 90, 25, 200, 20 );

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( txtCodCli.getText().trim().length() == 0 ) {
				Funcoes.mensagemInforma( this, "O campo cliente está em branco! ! !" );
				txtCodCli.requestFocus();
				return;
			}
		}
		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		lcCli.setConexao( cn );
	}

	public int[] getValores() {

		int iRet[] = { txtCodCli.getVlrInteger().intValue(), lcCli.getCodFilial() };
		return iRet;
	}
}
