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

			sql.append( "SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sql.append( "CAST( '" );
			sql.append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) );
			/**
			 * Tipo A = Saldo anterior Busca na FNPAGAR todas as compras com valor financeiro a pagar (VLRPAG)
			 */
			sql.append( "' AS DATE) DATA, 'A' TIPO, ");
			sql.append( "'A' TIPOSUBLANCA, ");
			sql.append( "0 DOC, 0.00 VLRDEB, " );
			sql.append( "(COALESCE( ( SELECT SUM(P.VLRPARCPAG) " );
			sql.append( "FROM FNPAGAR P " );
			sql.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? AND " );
			sql.append( "P.CODEMPFR=F.CODEMP AND P.CODFILIALFR=F.CODFILIAL AND " );
			sql.append( "P.CODFOR=F.CODFOR AND " );
			sql.append( "P.DATAPAG < ? ),0) + " );
			/**
			 * Subtrai o valor pago do saldo anterior O sinal do sublanca é invertido, então o sinal - está subtraindo
			 */
			sql.append( "COALESCE( ( SELECT SUM(SL.VLRSUBLANCA*-1) " );
			sql.append( "FROM FNSUBLANCA SL " );
			sql.append( "WHERE SL.CODEMPFR=F.CODEMP AND SL.CODFILIALFR=F.CODFILIAL AND " );
			sql.append( "SL.CODFOR=F.CODFOR AND SL.CODSUBLANCA<>0 AND " );
			sql.append( "SL.CODEMP=? AND SL.CODFILIAL=? AND " );
			sql.append( "SL.DATASUBLANCA < ? ), 0) +  " );

			/**
			 * Subtrai o valor do desconto na data do lançamento financeiro
			 */

			/*sql.append( "COALESCE( ( SELECT SUM(SL.VLRSUBLANCA) FROM FNSUBLANCA SL, SGPREFERE1 PF WHERE  " );
			sql.append( " SL.CODEMPFR=F.CODEMP AND SL.CODFILIALFR=F.CODFILIAL AND SL.CODFOR=F.CODFOR AND " );
			//sql.append( " SL.CODEMPPN=PF.CODEMPDR AND SL.CODFILIALPN=PF.CODFILIALDR AND SL.CODPLAN=PF.CODPLANDR AND " );
			sql.append( " SL.TIPOSUBLANCA=? AND " );
			sql.append( " SL.CODEMP=? AND SL.CODFILIAL=? AND SL.DATASUBLANCA < ? ), 0) - " );
*/
			/**
			 * Subtrai o valor das devoluções do saldo anterior
			 */
			sql.append( "( COALESCE( ( SELECT SUM(VD.VLRLIQVENDA) " );
			sql.append( "FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF " );
			sql.append( "WHERE VD.CODEMP=? AND VD.CODFILIAL=? AND  TM.CODEMP=VD.CODEMPTM AND " );
			sql.append( "TM.CODFILIAL=VD.CODFILIALTM AND TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND " );
			sql.append( "TM.CODTIPOMOV=VD.CODTIPOMOV AND CF.CODEMPFR=F.CODEMP AND " );
			sql.append( "CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND VD.CODEMPCL=CF.CODEMP AND " );
			sql.append( "VD.CODFILIALCL=CF.CODFILIAL AND VD.CODCLI=CF.CODCLI AND VD.DTEMITVENDA < ? ),0) ) ) " );
			sql.append( " VLRCRED " );
			/**
			 * Filtro do fornecedor
			 */
			sql.append( "FROM CPFORNECED F " );
			sql.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND " );
			if ( codfor != 0 ) {
				sql.append( "F.CODFOR=? AND " );
			}
			/**
			 * Verifica a existência dos valores
			 */
			sql.append( "( EXISTS (SELECT * FROM FNPAGAR P2 " );
			sql.append( "WHERE P2.CODEMP=? AND P2.CODFILIAL=? AND " );
			sql.append( "P2.CODEMPFR=F.CODEMP AND P2.CODFILIALFR=F.CODFILIAL AND " );
			sql.append( "P2.CODFOR=F.CODFOR AND DATAPAG BETWEEN ? AND ? )" );
			sql.append( "OR EXISTS (SELECT * FROM FNSUBLANCA SL2 " );
			sql.append( "WHERE F.CODEMP=SL2.CODEMPFR AND " );
			sql.append( "F.CODFILIAL=SL2.CODFILIALFR AND F.CODFOR=SL2.CODFOR AND " );
			sql.append( "SL2.CODEMP=? AND SL2.CODFILIAL=? AND " );
			sql.append( "SL2.DATASUBLANCA BETWEEN ? AND ? ) OR " );
			sql.append( " EXISTS (SELECT * FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF " );
			sql.append( "WHERE VD.CODEMP=? AND VD.CODFILIAL=? AND TM.CODEMP=VD.CODEMPTM AND " );
			sql.append( "TM.CODFILIAL=VD.CODFILIALTM AND TM.CODTIPOMOV=VD.CODTIPOMOV AND " );
			sql.append( "TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND " );
			sql.append( "CF.CODEMPFR=F.CODEMP AND CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND " );
			sql.append( "VD.CODEMPCL=CF.CODEMP AND VD.CODFILIALCL=CF.CODFILIAL AND " );
			sql.append( "VD.CODCLI=CF.CODCLI AND VD.DTEMITVENDA BETWEEN ? AND ? ) ) " );
			/**
			 * Fim da query do saldo anterior
			 */

			/**
			 * Query das compras
			 */
			sql.append( "UNION ALL " );
			sql.append( "SELECT P.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sql.append( "P.DATAPAG DATA, 'C' TIPO, ");
			sql.append( "'C' TIPOSUBLANCA, ");

			sql.append( "P.DOCPAG DOC, " );
			sql.append( "0.00 VLRDEB, P.VLRPARCPAG VLRCRED " );
			sql.append( "FROM FNPAGAR P, CPFORNECED F " );
			sql.append( "WHERE F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
			sql.append( "F.CODFOR=P.CODFOR AND " );
			sql.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
			if ( codfor != 0 ) {
				sql.append( "F.CODFOR=? AND " );
			}
			sql.append( "P.DATAPAG BETWEEN ? AND ? " );

			/**
			 * Query dos pagamentos
			 */
			sql.append( "UNION ALL " );
			sql.append( "SELECT SL.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sql.append( "SL.DATASUBLANCA DATA, ");
			sql.append( " (CASE WHEN SL.TIPOSUBLANCA='P' THEN 'P' ELSE 'X' END) TIPO, ");
			sql.append( "SL.TIPOSUBLANCA, ");
			sql.append( "P.DOCPAG DOC, SL.VLRSUBLANCA VLRDEB, ");
			sql.append( "(CASE WHEN SL.TIPOSUBLANCA IN ('J','D','M') THEN SL.VLRSUBLANCA ELSE 0.00 END) VLRCRED " );
			sql.append( "FROM FNSUBLANCA SL, CPFORNECED F, FNPAGAR P " );
			sql.append( "WHERE F.CODEMP=SL.CODEMPFR AND F.CODFILIAL=SL.CODFILIALFR AND " );
			sql.append( "P.CODEMP=SL.CODEMPPG AND P.CODFILIAL=SL.CODFILIALPG AND P.CODPAG=SL.CODPAG AND " );
			// Incluido no tiposublanca P - Padrão, M - Multa e J-Juros
			//sql.append( "SL.TIPOSUBLANCA IN ('P','M','J') AND ");
			sql.append( "F.CODFOR=SL.CODFOR AND SL.CODSUBLANCA<>0 AND " );
			if ( codfor != 0 ) {
				sql.append( "F.CODFOR=? AND " );
			}
			sql.append( "SL.CODEMP=? AND SL.CODFILIAL=? AND " );
			sql.append( "SL.DATASUBLANCA BETWEEN ? AND ? " );
			/**
			 * Query dos cancelamentos
			 */
			/*sql.append( "UNION ALL ");
			sql.append( " SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sql.append( " IP.DTVENCITPAG DATA, 'X' TIPO, " );
			sql.append( "'X' TIPOSUBLANCA, ");
			sql.append( "P.DOCPAG DOC, COALESCE(SUM(IP.VLRCANCITPAG),0) VLRCRED, 0.00 VLRDEB " );
			sql.append( "FROM FNPAGAR P, FNITPAGAR IP, CPFORNECED F WHERE P.CODEMP=? AND P.CODFILIAL=? AND P.CODEMPFR=F.CODEMP AND " );
			sql.append( "P.CODFILIALFR=F.CODFILIAL AND P.CODFOR=F.CODFOR ");
			sql.append( "AND IP.CODEMP=P.CODEMP AND IP.CODFILIAL=P.CODFILIAL AND IP.CODPAG=P.CODPAG AND IP.STATUSITPAG IN ('CP') ");
			if ( codfor != 0 ) {
				sql.append( "AND F.CODFOR=? " );
			}
			//sql.append( "AND R.CODEMP=? AND R.CODFILIAL=? AND " );
			sql.append( "AND P.DATAPAG BETWEEN ? AND ? " );
			sql.append( "GROUP BY 1, 2, 3, 4, 5, 6 ");
				*/		
			/**
			 * Query das devoluções
			 */
			sql.append( "UNION ALL SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, VD.DTEMITVENDA DATA, " );
			sql.append( " 'Z' TIPO, ");
			sql.append( "'Z' TIPOSUBLANCA, " );
			sql.append( "VD.DOCVENDA DOC, VD.VLRLIQVENDA VLRCRED, 0.00 VLRDEB " );
			sql.append( "FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF, CPFORNECED F " );
			sql.append( "WHERE TM.CODEMP=VD.CODEMPTM AND TM.CODFILIAL=VD.CODFILIALTM AND " );
			sql.append( "TM.CODTIPOMOV=VD.CODTIPOMOV AND TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND " );
			sql.append( "CF.CODEMPFR=F.CODEMP AND CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND " );
			sql.append( "VD.CODEMPCL=CF.CODEMP AND VD.CODFILIALCL=CF.CODFILIAL AND " );
			sql.append( "VD.CODCLI=CF.CODCLI AND " );
			if ( codfor != 0 ) {
				sql.append( "F.CODFOR=? AND " );
			}
			sql.append( "VD.CODEMP=? AND VD.CODFILIAL=? AND " );
			sql.append( "VD.DTEMITVENDA BETWEEN ? AND ? " );

			/**
			 * Query dos descontos
			 */
/*			sql.append( "UNION ALL SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, SL.DATASUBLANCA DATA, " );
			sql.append( " 'X' TIPO, P.DOCPAG DOC, (SL.VLRSUBLANCA * -1) VLRDEB,  0.00 VLRCRED " );
			sql.append( "FROM FNSUBLANCA SL, FNPAGAR P, CPFORNECED F " );
			sql.append( "WHERE SL.CODEMPPG=P.CODEMP AND SL.CODFILIALPG=P.CODFILIAL AND " );
			sql.append( " SL.TIPOSUBLANCA=? AND " );
			sql.append( " SL.CODPAG=P.CODPAG AND F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
			sql.append( "F.CODFOR=P.CODFOR AND " );
			if ( codfor != 0 ) {
				sql.append( "F.CODFOR=? AND " );
			}
			sql.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
			sql.append( "SL.DATASUBLANCA BETWEEN ? AND ?  " );
*/
			sql.append( "ORDER BY 1, 2, 3, 4, 6, 5" );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp ); // 1
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 2
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 3
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 5
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 6

			// Paramatro do saldo de descontos

			// Tipo sublanca = "D" Desconto
			/*ps.setString( param++, "D" ); // 7
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 5
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 6
*/
			// Parametros do saldo de devoluções
			ps.setInt( param++, Aplicativo.iCodEmp ); // 7
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) ); // 8
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 9
			//
			ps.setInt( param++, Aplicativo.iCodEmp ); // 10
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPFORNECED" ) ); // 11
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 12
			}
			// Parametros do exists
			ps.setInt( param++, Aplicativo.iCodEmp ); // 13
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 14
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 15
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 16
			ps.setInt( param++, Aplicativo.iCodEmp ); // 17
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 18
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 19
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 20
			// Parametros do exists referente ao saldo de devoluções
			ps.setInt( param++, Aplicativo.iCodEmp ); // 21
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) ); // 22
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 23
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 24
			//
			ps.setInt( param++, Aplicativo.iCodEmp ); // 25
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 26
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 27
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 28
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 29
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 30
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 31
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 32
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 33
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 34
			// Parâmetros cancelamentos
			/*ps.setInt( param++, Aplicativo.iCodEmp ); // 35
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 36
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 37
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 38
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 39
	        */
			// Parâmetros das devoluções
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 40
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 41
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) ); // 42
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 43
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 44

			// Parametros dos descontos
			
            // Tipo sublanca = D - Descontos
			/*ps.setString( param++, "D" ); // 4

			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 41
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 42
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 43
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 44
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 45
*/
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
