/**
 * @version 29/10/2007 <BR>
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

public class FRRazCli extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCnpjCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private ListaCampos lcCli = new ListaCampos( this );

	public FRRazCli() {

		setTitulo( "Razão Clientes" );
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
		adic( new JLabelPad( "Cód.Cli" ), 7, 60, 80, 20 );
		adic( txtCodCli, 7, 80, 80, 20 );
		adic( new JLabelPad( "Descrição do Cliente" ), 90, 60, 220, 20 );
		adic( txtDescCli, 90, 80, 220, 20 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	private void montaListaCampos() {

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód. Cliente", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtDescCli, "RazCli", "Razão social  do Cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCnpjCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );

		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

	}

	@ Override
	public void imprimir( TYPE_PRINT bVisualizar ) {

		int param = 1;
		int codcli = 0;

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer cab = new StringBuffer();

		try {

			codcli = txtCodCli.getVlrInteger().intValue();
			if ( codcli != 0 ) {
				cab.append( "Cli: " );
				cab.append( codcli );
				cab.append( " - " );
				cab.append( txtDescCli.getVlrString().trim() );
			}
			cab.append( "\nPeríodo de: " );
			cab.append( txtDataini.getVlrString() );
			cab.append( " até: " );
			cab.append( txtDatafim.getVlrString() );
			
			sql.append( "select v.codcli codemit, v.razcli razemit ");
			sql.append( ", cast( '" );
			sql.append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) );
			/**
			 * Tipo A = Saldo anteiror Busca na FNRECEBER todos as vendas com valor financeiro a receber (VLRREC)
			 */
			sql.append( "' as date) data, 'A' tipo, 'A' tiposublanca ");
			sql.append( ", 0 doc, sum(v.vlrdeb+v.vlrcred) vlrdeb ");
			sql.append( ", 0 vlrcred ");
			sql.append( "from fnrazclivw01 v ");
			sql.append( "where v.codempcl=? and v.codfilialcl=? ");
			if (codcli!=0) {
				sql.append( " and v.codcli=? " );
			}
			sql.append( "and v.data < ? " );
			sql.append( "and ( ");
			sql.append( "(v.codemprc is null or v.codemprc=? ) ");
			sql.append( "and (v.codfilialrc is null or v.codfilialrc=? ) ");
			sql.append( "and (v.codempsl is null or v.codempsl=? ) ");
			sql.append( "and (v.codfilialsl is null or v.codfilialsl=? ) ");
			sql.append( "and (v.codempcp is null or v.codempcp=? ) ");
			sql.append( "and (v.codfilialcp is null or v.codfilialcp=? ) ");
			sql.append( ")");
			sql.append( " group by 1, 2 ");
			/**
			 * Query dos lancamentos
			 */
			sql.append( "union all " );
			sql.append( "select v.codcli codemit, v.razcli razemit " );
			sql.append( ", v.data, v.tipo, v.tiposublanca ");
			sql.append( ", v.doc, v.vlrdeb, v.vlrcred " );
			sql.append( "from fnrazclivw01 v " );
			sql.append( "where codempcl=? and codfilialcl=? " );
			sql.append( "and  " );
			if ( codcli != 0 ) {
				sql.append( "v.codcli=? " );
			}
			sql.append( "and v.data between ? and ? " );
			sql.append( "and ( ");
			sql.append( "(v.codemprc is null or v.codemprc=? ) ");
			sql.append( "and (v.codfilialrc is null or v.codfilialrc=? ) ");
			sql.append( "and (v.codempsl is null or v.codempsl=? ) ");
			sql.append( "and (v.codfilialsl is null or v.codfilialsl=? ) ");
			sql.append( "and (v.codempcp is null or v.codempcp=? ) ");
			sql.append( "and (v.codfilialcp is null or v.codfilialcp=? ) ");
			sql.append( ") ");
			sql.append( "order by 1, 2, 3, 4, 6, 5" );

			ps = con.prepareStatement( sql.toString() );
			System.out.println( "QUERY" + sql.toString() );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDCLIENTE" ) ); 
			if (codcli!=0) {
				ps.setInt( param++, codcli );
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setInt( param++, Aplicativo.iCodEmp ); 
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( param++, Aplicativo.iCodEmp ); 
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); 
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDCLIENTE" ) ); 
			if (codcli!=0) {
				ps.setInt( param++, codcli );
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); 
			ps.setInt( param++, Aplicativo.iCodEmp ); 
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( param++, Aplicativo.iCodEmp ); 
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); 
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );

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
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNRECEBER" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", cab );

		dlGr = new FPrinterJob( "relatorios/FRRazCli.jasper", "Relatório de Razão por Cliente", cab, rs, hParam, this );

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
		lcCli.setConexao( cn );

	}
}
