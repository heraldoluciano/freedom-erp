/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Felipe Daniel Elias <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLGuiaTraf.java <BR>
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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLGuiaTraf extends FFDialogo implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodEmp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodFilial = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodItCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 3, 0 );

	private JTextFieldPad txtDtEmissGuiaTraf = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtNumGuiaTraf = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtSeloGuiaTraf = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JLabelPad lbCodCompra = new JLabelPad( "Cód.compra" );

	private JLabelPad lbCodEmp = new JLabelPad( "Cód.emp" );

	private JLabelPad lbCodFilial = new JLabelPad( "Cód.filial" );

	private JLabelPad lbCodItCompra = new JLabelPad( "Cód.item" );

	private JLabelPad lbNumGuiaTraf = new JLabelPad( "Número da guia" );

	private JLabelPad lbDataEmissGuia = new JLabelPad( "Data de emissão" );

	private JLabelPad lbSeloGuiaTraf = new JLabelPad( "Selo da Guia" );
	
	//int codcompra;
	
	//int coditcompra;
	
	

	public DLGuiaTraf() {

		super();
		setTitulo( "Guia de tráfego" );
		setAtribos( 350, 150 );

		txtDtEmissGuiaTraf.setRequerido( true );
		txtNumGuiaTraf.setRequerido( true );
		txtSeloGuiaTraf.setRequerido( true );

		adic( lbDataEmissGuia, 7, 0, 100, 20 );
		adic( txtDtEmissGuiaTraf, 7, 20, 100, 20 );
		adic( lbNumGuiaTraf, 110, 0, 100, 20 );
		adic( txtNumGuiaTraf, 110, 20, 100, 20 );
		adic( lbSeloGuiaTraf, 213, 0, 100, 20 );
		adic( txtSeloGuiaTraf, 213, 20, 100, 20 );
		
		//this.codcompra = codcompra;
		//this.coditcompra = coditcompra;

	}

	private void gravaGuiaTraf() {

		if ( txtDtEmissGuiaTraf.getVlrString().equals( "" ) ) {

			Funcoes.mensagemInforma( this, "A data de emissão é requerida!" );
		}
		else if ( txtNumGuiaTraf.getVlrString().equals( "" ) ) {

			Funcoes.mensagemInforma( this, "O número da guia é requerido!" );
		}
		else if ( txtSeloGuiaTraf.getVlrString().equals( "" ) ) {

			Funcoes.mensagemInforma( this, "O selo da guia é requerido" );

		}

		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;

		sSQL.append( "INSERT INTO EQGUIATRAF( CODEMP, CODFILIAL, CODCOMPRA, CODITCOMPRA, DTEMISGUIATRAF, " );
		sSQL.append( "NROGUIATRAF, NROGUIASELOTRAF ) VALUES (?,?,?,?,?,?,?)" );

		try {

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQGUIATRAF" ) );
			//ps.setInt( 3, codcompra );
			//ps.setInt( 4, coditcompra );
			

		} catch ( Exception e ) {

		}

	}

}


