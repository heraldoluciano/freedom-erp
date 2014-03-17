/**
 * @version 24/10/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRRazFor.java <BR>
 * 
 *                   Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                   na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                   Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                   Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                   Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

public class FRRazFor extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcFor = new ListaCampos( this );

	private JTextFieldPad txtCnpjFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	public FRRazFor() {

		super( false );
		setTitulo( "Compras por Razão" );
		setAtribos( 50, 50, 340, 195 );

		montaTela();
		montaListaCampos();
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Período:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 15, 5, 80, 20 );
		adic( lbLinha, 7, 15, 303, 40 );

		adic( new JLabelPad( "De:", SwingConstants.CENTER ), 10, 25, 40, 20 );
		adic( txtDataini, 50, 25, 100, 20 );
		adic( new JLabelPad( "Até:", SwingConstants.CENTER ), 150, 25, 45, 20 );
		adic( txtDatafim, 195, 25, 100, 20 );
		adic( new JLabelPad( "Cód.For" ), 7, 60, 80, 20 );
		adic( txtCodFor, 7, 80, 80, 20 );
		adic( new JLabelPad( "Descrição do fornecedor" ), 90, 60, 220, 20 );
		adic( txtDescFor, 90, 80, 220, 20 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	private void montaListaCampos() {

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCnpjFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );

		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );
	}

	@ Override
	public void imprimir( TYPE_PRINT bVisualizar ) {

		int param = 1;
		int codfor = 0;

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer cab = new StringBuffer();

		try {

			codfor = txtCodFor.getVlrInteger().intValue();
			if ( codfor != 0 ) {
				cab.append( "Forn.: " );
				cab.append( codfor );
				cab.append( " - " );
				cab.append( txtDescFor.getVlrString().trim());
				cab.append( " / ");
			}
			cab.append( "Período de: " );
			cab.append( txtDataini.getVlrString() );
			cab.append( " até: " );
			cab.append( txtDatafim.getVlrString() );

			sql.append( "select v.codfor codemit, v.razfor razemit, " );
			sql.append( "cast( '" );
			sql.append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) );
			/**
			 * Tipo A = Saldo anterior Busca na FNPAGAR todas as compras com valor financeiro a pagar (VLRPAG)
			 */
			sql.append( "' as date) data, 'A' tipo, ");
			sql.append( "'A' tiposublanca, ");
			sql.append( "0 DOC, 0.00 vlrdeb, " );
			sql.append( "sum(v.vlrcred-v.vlrdeb) vlrcred " );
			sql.append( "from fnrazforvw01 v " );
			sql.append( "where v.codempfr=? and v.codfilialfr=? ");
			if (codfor!=0) {
				sql.append( " and v.codfor=? ");
			}
			sql.append( "and v.data < ? " );
			sql.append( "and ( ");
			sql.append( "(v.codemppg is null or v.codemppg=? ) ");
			sql.append( "and (v.codfilialpg is null or v.codfilialpg=? ) ");
			sql.append( "and (v.codempsl is null or v.codempsl=? ) ");
			sql.append( "and (v.codfilialsl is null or v.codfilialsl=? ) ");
			sql.append( "and (v.codempvd is null or v.codempvd=? ) ");
			sql.append( "and (v.codfilialvd is null or v.codfilialvd=? ) ");
			sql.append( ")");
			sql.append( " group by 1, 2 ");
			/**
			 * Query dos lancamentos
			 */
			sql.append( "union all " );
			sql.append( "select v.codfor codemit, v.razfor razemit, " );
			sql.append( "v.data, v.tipo, v.tiposublanca ");
			sql.append( ",v.doc, v.vlrdeb, v.vlrcred " );
			sql.append( "from fnrazforvw01 v " );
			sql.append( "where codempfr=? and codfilialfr=? " );
			sql.append( "and  " );
			if ( codfor != 0 ) {
				sql.append( "v.codfor=? " );
			}
			sql.append( "and v.data between ? and ? " );
			sql.append( "and ( ");
			sql.append( "(v.codemppg is null or v.codemppg=? ) ");
			sql.append( "and (v.codfilialpg is null or v.codfilialpg=? ) ");
			sql.append( "and (v.codempsl is null or v.codempsl=? ) ");
			sql.append( "and (v.codfilialsl is null or v.codfilialsl=? ) ");
			sql.append( "and (v.codempvd is null or v.codempvd=? ) ");
			sql.append( "and (v.codfilialvd is null or v.codfilialvd=? ) ");
			sql.append( ") ");
			sql.append( "order by 1, 2, 3, 4, 6, 5" );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPFORNECED" ) ); 
			if (codfor!=0) {
				ps.setInt( param++, codfor );
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setInt( param++, Aplicativo.iCodEmp ); 
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			ps.setInt( param++, Aplicativo.iCodEmp ); 
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); 
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPFORNECED" ) ); 
			if (codfor!=0) {
				ps.setInt( param++, codfor );
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); 
			ps.setInt( param++, Aplicativo.iCodEmp ); 
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			ps.setInt( param++, Aplicativo.iCodEmp ); 
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); 
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			System.out.println( "QUERY" + sql.toString() );

			rs = ps.executeQuery();

			imprimiGrafico( bVisualizar, rs, cab.toString() );

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados na tabela!\n" + err.getMessage(), true, con, err );
		}
	}

	private void imprimiGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String cab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", cab );

		dlGr = new FPrinterJob( "relatorios/FRRazFor.jasper", "Relatório de Razão por fornecedor", cab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório por razão!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );

	}
}
