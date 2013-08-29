/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRInadimplentes.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class FRRecebAberto extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private Vector<String> vVals1 = new Vector<String>();

	private Vector<String> vLabs1 = new Vector<String>();

	public FRRecebAberto() {

		setTitulo( "Recebimentos em aberto" );
		setAtribos( 80, 80, 330, 160 );

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );

		montaListaCampos();
		montaTela();

	}

	public void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Referência:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 300, 45 );

		adic( new JLabelPad( "Recebimentos em aberto até:" ), 10, 25, 200, 20 );
		adic( txtDatafim, 200, 25, 90, 20 );
	}

	public void montaListaCampos() {

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		String sWhere = "";
		String sCab = "";
		String sFiltro = "'R1'";

		String sSQL = "select ir.dtitrec, r.docrec, r.codvenda, ir.vlrparcitrec, ir.vlrpagoitrec, ir.vlrapagitrec, ir.dtpagoitrec" 
			+ " from fnreceber r, fnitreceber ir" 
			+ " where ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec " //+ AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) 
			+ " and statusitrec in ('R1','RL')"
			+ sWhere + " order by ir.dtitrec";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( sSQL );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rs = ps.executeQuery();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta ao relatório de recebimentos em aberto!\n" + err.getMessage(), true, con, err );
		}


	}

	private void imprimiGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRRecebAberto.jasper", "Relatório de Recebimentos em aberto", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de Recebimentos em aberto!" + err.getMessage(), true, con, err );
			}
		}
	}

	private void imprimiTexto( final ResultSet rs, final TYPE_PRINT bVisualizar, final String sCab ) {

		ImprimeOS imp = null;
		int linPag = 0;
		BigDecimal bTotalDev = new BigDecimal( "0" );
		int iNumLanca = 0;

		try {

			boolean hasData = false;

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relatório de Compras" );
			imp.addSubTitulo( sCab.toString() );
			imp.limpaPags();

			while ( rs.next() ) {

				if ( imp.pRow() == linPag ) {

					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
					imp.eject();
					imp.incPags();

				}

				if ( imp.pRow() == 0 ) {

					imp.montaCab();
					imp.setTitulo( "Relatório de Inadimplentes" );

					if ( sCab.length() > 0 ) {

						imp.addSubTitulo( sCab );
					}

					imp.impCab( 136, true );

					imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "| Vencto." );
					imp.say( imp.pRow() + 0, 13, "|Vlr. da Parc." );
					imp.say( imp.pRow() + 0, 27, "|Doc.    " );
					imp.say( imp.pRow() + 0, 39, "|N.Lancto" );
					imp.say( imp.pRow() + 0, 48, "|N.Pedido" );
					imp.say( imp.pRow() + 0, 57, "|Data Emis." );
					imp.say( imp.pRow() + 0, 68, "|Devedor" );
					imp.say( imp.pRow() + 0, 119, "|Telefone" );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );

				}

				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "|" );
				imp.say( imp.pRow() + 0, 2, StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItRec" ) ) + "" );
				imp.say( imp.pRow() + 0, 13, "|" + Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrparcItRec" ) ) );
				imp.say( imp.pRow() + 0, 27, "|" + ( Funcoes.copy( rs.getString( 9 ), 0, 1 ).equals( "P" ) ? Funcoes.copy( rs.getString( "CodVenda" ), 0, 8 ) : Funcoes.copy( rs.getString( "DocRec" ), 0, 8 ) ) + "/" + Funcoes.copy( rs.getString( "NParcItRec" ), 0, 2 ) );
				imp.say( imp.pRow() + 0, 39, "|" + Funcoes.copy( rs.getString( "Codrec" ), 0, 8 ) );
				imp.say( imp.pRow() + 0, 48, "|" + Funcoes.copy( rs.getString( "Codvenda" ), 0, 8 ) );
				imp.say( imp.pRow() + 0, 57, "|" + StringFunctions.sqlDateToStrDate( rs.getDate( "DtItRec" ) ) );
				imp.say( imp.pRow() + 0, 68, "|" + Funcoes.copy( rs.getString( "CodCli" ), 0, 8 ) + "-" + Funcoes.copy( rs.getString( "RazCli" ), 0, 40 ) );
				imp.say( imp.pRow() + 0, 119, "|" + ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ).trim() );
				imp.say( imp.pRow() + 0, 135, "|" );

				bTotalDev = bTotalDev.add( new BigDecimal( rs.getString( "VlrParcItRec" ) ) );
				iNumLanca++;

			}

			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow(), 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );
			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, "|" );
			imp.say( imp.pRow() + 0, 40, "Totais Gerais->    Lançamentos: " + StringFunctions.strZero( "" + iNumLanca, 5 ) + "     Total a Receber: " + Funcoes.strDecimalToStrCurrency( 13, 2, "" + bTotalDev ) );
			imp.say( imp.pRow(), 135, "|" );

			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );

			imp.eject();
			imp.fechaGravacao();

			con.commit();

			if ( bVisualizar==TYPE_PRINT.VIEW ) {
				imp.preview( this );
			}
			else {
				imp.print();

			}

		} catch ( Exception err ) {
			err.printStackTrace();
		}
	}
}
