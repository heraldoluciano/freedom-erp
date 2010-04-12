/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FBanco.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários sobre a classe...
 */

package org.freedom.modulos.fnc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.PainelImagem;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.std.view.dialog.report.DLRBanco;

public class FBanco extends FDados implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtNomeBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodModBol = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSiteBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldFK txtDescModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDigito = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JButtonPad btFirefox = new JButtonPad( Icone.novo( "firefox.gif" ) );

	private PainelImagem imgBolBanco = new PainelImagem( 65000 );
	
	private PainelImagem imgBolBanco2 = new PainelImagem( 65000 );

	private ListaCampos lcModBol = new ListaCampos( this, "MB" );

	private String sURLBanco = null;

	public FBanco() {

		super();
		setTitulo( "Cadastro de Banco" );
		setAtribos( 50, 50, 450, 310 );

		lcModBol.add( new GuardaCampo( txtCodModBol, "CodModBol", "Cód.mod.bol.", ListaCampos.DB_PK, txtDescModBol, false ) );
		lcModBol.add( new GuardaCampo( txtDescModBol, "DescModBol", "Descriçao do modelo de boleto", ListaCampos.DB_SI, null, false ) );
		lcModBol.montaSql( false, "MODBOLETO", "FN" );
		lcModBol.setQueryCommit( false );
		lcModBol.setReadOnly( true );
		txtCodModBol.setTabelaExterna( lcModBol );

		adicCampo( txtCodBanco, 7, 20, 70, 20, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true );
		adicCampo( txtNomeBanco, 80, 20, 280, 20, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, true );
		adicCampo( txtCodModBol, 7, 60, 70, 20, "CodModBol", "Cód.mod.", ListaCampos.DB_FK, txtDescModBol, false );
		adicDescFK( txtDescModBol, 80, 60, 230, 20, "DescModBol", "Descrição do modelo de boleto" );
		adicCampo( txtDigito, 313, 60, 50, 20, "DvBanco", "Dígito", ListaCampos.DB_SI, true );
		adicCampo( txtSiteBanco, 7, 100, 330, 20, "SiteBanco", "Site ", ListaCampos.DB_SI, false );
		adicDB( imgBolBanco, 7, 140, 200, 30, "ImgBolBanco", "Primeira logo para boleto ", false );
		adicDB( imgBolBanco2, 7, 200, 200, 30, "ImgBolBanco2", "Segunda logo boleto ", false );

		adic( btFirefox, 340, 100, 20, 20 );
		setListaCampos( false, "BANCO", "FN" );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btFirefox.addActionListener( this );
		lcCampos.setQueryInsert( false );
		setImprimir( true );
		btFirefox.setToolTipText( "Acessar Site" );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );

		}
		super.actionPerformed( evt );

		if ( evt.getSource() == btFirefox ) {

			if ( !txtSiteBanco.getVlrString().equals( "" ) ) {

				sURLBanco = txtSiteBanco.getVlrString();
				Funcoes.executeURL( Aplicativo.strOS, Aplicativo.strBrowser, sURLBanco );
			}
			else
				Funcoes.mensagemInforma( this, "Informe o Site do banco! " );
		}
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();
		imp.setTitulo( "Relatório de Bancos" );
		DLRBanco dl = new DLRBanco( this );
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}
		String sSQL = "SELECT CODBANCO,NOMEBANCO FROM FNBANCO ORDER BY " + dl.getValor();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			rs = ps.executeQuery();
			imp.limpaPags();
			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 2, "Código" );
					imp.say( 30, "Nome" );
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, StringFunctions.replicate( "-", 79 ) );
				}
				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodBanco" ) );
				imp.say( 30, rs.getString( "NomeBanco" ) );
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, StringFunctions.replicate( "=", 79 ) );
			imp.eject();

			imp.fechaGravacao();

			//      rs.close();
			//      ps.close();
			con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de bancos!\n" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcModBol.setConexao( cn );
	}
}
