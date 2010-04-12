/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FTipoCob.java <BR>
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

package org.freedom.modulos.std;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.ImprimeOS;
import org.freedom.library.JCheckBoxPad;
import org.freedom.library.JRadioGroup;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FTipoCob extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescTipoCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtDuplCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private JRadioGroup<?, ?> rgTipoFebraban = null;
	
	private final JCheckBoxPad cbObriCartCob = new JCheckBoxPad( "Obrigar carteira de cobraça?", "S", "N" );
	

	public FTipoCob() {

		super();
		setTitulo( "Cadastro de Tipo de Cobrança" );
		setAtribos( 50, 50, 480, 250 );

		montaRadioGrupos();
		
		montaTela();
		
		setListaCampos( true, "TIPOCOB", "FN" );
		lcCampos.setQueryInsert( false );
		
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		
		rgTipoFebraban.setVlrString( "00" );
	}
	
	private void montaRadioGrupos() {

		/*********************
		 *   TIPO FEBRABAN   *
		 *********************/

		Vector<String> vLabsTipoFebraban = new Vector<String>();
		Vector<String> vValsTipoFebraban = new Vector<String>();
		
		vLabsTipoFebraban.addElement( "Nenhum" );
		vLabsTipoFebraban.addElement( "SIACC" );
		vLabsTipoFebraban.addElement( "CNAB" );
		vValsTipoFebraban.addElement( "00" );
		vValsTipoFebraban.addElement( "01" );
		vValsTipoFebraban.addElement( "02" );
		
		rgTipoFebraban = new JRadioGroup<String, String>( 1, 2, vLabsTipoFebraban, vValsTipoFebraban );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodTipoCob, 7, 20, 80, 20, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoCob, 90, 20, 250, 20, "DescTipoCob", "Descrição do tipo de cobrança", ListaCampos.DB_SI, true );
		adicCampo( txtDuplCob, 343, 20, 80, 20, "DuplCob", "Duplicata", ListaCampos.DB_SI, false );
		adicDB( rgTipoFebraban, 7, 70, 416, 30, "TipoFebraban", "Tipo cobrança FEBRABAN", false );
		adicDB( cbObriCartCob, 7, 115, 416, 20, "ObrigCartCob", "", true );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRTipoCob dl = null;
		ImprimeOS imp = null;
		int linPag = 0;

		dl = new DLRTipoCob();
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relatório de Tipos de Cobrança" );
			imp.limpaPags();

			sSQL = "SELECT CODTIPOCOB,DESCTIPOCOB,DUPLCOB " + "FROM FNTIPOCOB " + "WHERE CODEMP=? AND CODFILIAL=? " + "ORDER BY " + dl.getValor();

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNTIPOCOB" ) );
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( imp.pRow(), 0, imp.normal() );
					imp.say( imp.pRow(), 2, "Código" );
					imp.say( imp.pRow(), 20, "Descrição" );
					imp.say( imp.pRow(), 60, "Duplicata" );
					imp.say( imp.pRow() + 1, 0, imp.normal() );
					imp.say( imp.pRow(), 0, StringFunctions.replicate( "-", 79 ) );
				}
				imp.say( imp.pRow() + 1, 0, imp.normal() );
				imp.say( imp.pRow(), 2, rs.getString( "CodTipoCob" ) );
				imp.say( imp.pRow(), 20, rs.getString( "DescTipoCob" ) );
				imp.say( imp.pRow(), 60, rs.getString( "DuplCob" ) != null ? rs.getString( "DuplCob" ) : "" );
				if ( imp.pRow() >= linPag ) {
					imp.say( imp.pRow() + 1, 0, imp.normal() );
					imp.say( imp.pRow(), 0, StringFunctions.replicate( "-", 79 ) );
					imp.incPags();
					imp.eject();
				}
			}
			imp.say( imp.pRow() + 1, 0, imp.normal() );
			imp.say( imp.pRow(), 0, StringFunctions.replicate( "=", 79 ) );
			imp.eject();
			imp.fechaGravacao();
			con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de tipos de cobrança!" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			dl = null;
		}

		if ( bVisualizar )
			imp.preview( this );
		else
			imp.print();
	}
}
