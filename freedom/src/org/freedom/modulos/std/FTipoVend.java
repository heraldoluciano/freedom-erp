/**
 * @version 13/05/2008 <BR>
 * @author Setpoint Informática Ltda./Felipe Daniel Elias <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: FTipoVend
 * @(#)FClasCli.java <BR>
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

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;

public class FTipoVend extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTipoVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	public FTipoVend() {

		super();
		setAtribos( 50, 50, 350, 165 );
		setTitulo( "Cadastro de Tipos de Comissionados" );
		adicCampo( txtCodTipoVenda, 7, 20, 70, 20, "CODTIPOVEND", "Cód.tp.comis.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoVenda, 80, 20, 250, 20, "DESCTIPOVEND", "Descrição do tipo de comissionado", ListaCampos.DB_SI, true );
		setListaCampos( true, "TIPOVEND", "VD" );
		setImprimir( true );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

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
		DLRTipoVend dl = new DLRTipoVend( this );

		try {
			dl.setVisible( true );
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}

			sSQL = "SELECT CODTIPOVEND, DESCTIPOVEND FROM VDTIPOVEND WHERE CODEMP=? AND CODFILIAL=? ORDER BY " + dl.getOrdem();
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTIPOVEND" ) );
			rs = ps.executeQuery();

			if ( "T".equals( dl.getTipo() ) ) {
				imprimirTexto( bVisualizar, rs );
			}
			else if ( "G".equals( dl.getTipo() ) ) {
				imprimirGrafico( bVisualizar, rs );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			dl.dispose();
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		}
	}

	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();
		imp.setTitulo( "Relatório de Tipos de Comissionados" );

		try {

			imp.limpaPags();

			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( imp.pRow() + 0, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 0, 2, "Código" );
					imp.say( imp.pRow() + 0, 30, "Descrição" );
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, Funcoes.replicate( "-", 80 ) );
					
				}
				imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
				imp.say( imp.pRow() + 0, 2, rs.getString( "CODTIPOVEND" ) );
				imp.say( imp.pRow() + 0, 30, rs.getString( "DESCTIPOVEND" ) );
				imp.say( imp.pRow() + 0, 82, "|" + imp.normal() );
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
			imp.say( imp.pRow() + 0, 0, Funcoes.replicate( "=", 80 ) );

			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		}
	}
	
	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/TipoVend.jasper", "Classificação tipo de comissionado ", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {

				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
			}
		}
	}
}
