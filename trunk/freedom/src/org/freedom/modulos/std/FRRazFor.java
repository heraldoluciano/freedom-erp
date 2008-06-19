/**
 * @version 24/10/2007 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRRazFor.java <BR>
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
 * 
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRRazFor extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcFor = new ListaCampos( this );

	public FRRazFor() {

		setTitulo( "Compras por Razão" );
		setAtribos( 50, 50, 340, 195 );

		montaTela();
		montaListaCampos();
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
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
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );

	}

	@ Override
	public void imprimir( boolean bVisualizar ) {

		int param = 1;
		int codfor = 0;

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sCab = new StringBuffer();

		try {

			codfor = txtCodFor.getVlrInteger().intValue();
			if ( codfor != 0 ) {
				sCab.append( "FORNECEDOR - " + txtDescFor.getVlrString() );
			}

			
			sSQL.append( "SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "CAST( '" );
			sSQL.append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) );
			/**
			 * Tipo A = Saldo anterior
			 * Busca na FNPAGAR todas as compras com valor financeiro a pagar (VLRPAG)
			 */
			sSQL.append( "' AS DATE) DATA, 'A' TIPO, 0 DOC, 0.00 VLRDEB, " );
			sSQL.append( "(COALESCE( ( SELECT SUM(P.VLRPAG) " );
			sSQL.append( "FROM FNPAGAR P " );
			sSQL.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? AND " );
			sSQL.append( "P.CODEMPFR=F.CODEMP AND P.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "P.CODFOR=F.CODFOR AND " );
			sSQL.append( "P.DATAPAG < ? ),0) + " );
			/**
			 *  Subtrai o valor pago do saldo anterior
			 *  O sinal do lanca é invertido, então o sinal + está subtraindo
			 */
			sSQL.append( "COALESCE( ( SELECT SUM(L.VLRLANCA) " );
			sSQL.append( "FROM FNLANCA L " );
			sSQL.append( "WHERE L.CODEMPFR=F.CODEMP AND L.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "L.CODFOR=F.CODFOR AND " );
			sSQL.append( "L.CODEMP=? AND L.CODFILIAL=? AND " );
			sSQL.append( "L.DATALANCA < ? ), 0) " );
			sSQL.append( ") VLRCRED " );
			
			
			sSQL.append( "FROM CPFORNECED F " );
			sSQL.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "( EXISTS (SELECT * FROM FNPAGAR P2 " );
			sSQL.append( "WHERE P2.CODEMP=? AND P2.CODFILIAL=? AND " );
			sSQL.append( "P2.CODEMPFR=F.CODEMP AND P2.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "P2.CODFOR=F.CODFOR AND DATAPAG BETWEEN ? AND ? )" );
			sSQL.append( "OR EXISTS (SELECT * FROM FNLANCA L2 " );
			sSQL.append( "WHERE F.CODEMP=L2.CODEMPFR AND " );
			sSQL.append( "F.CODFILIAL=L2.CODFILIALFR AND F.CODFOR=L2.CODFOR AND " );
			sSQL.append( "L2.CODEMP=? AND L2.CODFILIAL=? AND " );
			sSQL.append( "L2.DATALANCA BETWEEN ? AND ?" );
			sSQL.append( ") ) " );
			/**
			 * Fim da query do saldo anterior
			 */
			
			/**
			 * Query das compras 
			 */
			sSQL.append( "UNION " );
			sSQL.append( "SELECT P.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "P.DATAPAG DATA, 'C' TIPO, P.DOCPAG DOC, (P.VLRPARCPAG-P.VLRPAG)*-1 VLRDEB, P.VLRPAG VLRCRED " );
			sSQL.append( "FROM FNPAGAR P, CPFORNECED F " );
			sSQL.append( "WHERE F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
			sSQL.append( "F.CODFOR=P.CODFOR AND " );
			sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "P.DATAPAG BETWEEN ? AND ? " );
			
			/**
			 * Query dos pagamentos 
			 */
			sSQL.append( "UNION " );
			sSQL.append( "SELECT L.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "L.DATALANCA DATA, 'P' TIPO, P.DOCPAG DOC, L.VLRLANCA VLRDEB, 0.00 VLRCRED " ); 
			sSQL.append( "FROM FNLANCA L, CPFORNECED F, FNPAGAR P " );
			sSQL.append( "WHERE F.CODEMP=L.CODEMPFR AND F.CODFILIAL=L.CODFILIALFR AND " );
			sSQL.append(  "P.CODEMP=L.CODEMPPG AND P.CODFILIAL=L.CODFILIALPG AND P.CODPAG=L.CODPAG AND " );
			sSQL.append( "F.CODFOR=L.CODFOR AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "L.CODEMP=? AND L.CODFILIAL=? AND " );
			sSQL.append( "L.DATALANCA BETWEEN ? AND ? " );
			sSQL.append( "ORDER BY 1, 2, 3, 4, 5" );

			ps = con.prepareStatement( sSQL.toString() );
			// ps.setDate( param++ , Funcoes.strDateToSqlDate( txtDataini.getVlrString() )); // 1
			ps.setInt( param++, Aplicativo.iCodEmp ); // 2
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 3
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 4
			ps.setInt( param++, Aplicativo.iCodEmp ); // 5
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 6
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 7
			ps.setInt( param++, Aplicativo.iCodEmp ); // 8
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPFORNECED" ) ); // 9
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 10
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 11
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 12
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 13
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 14
			ps.setInt( param++, Aplicativo.iCodEmp ); // 15
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 16
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 17
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 18
			ps.setInt( param++, Aplicativo.iCodEmp ); // 19
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 20
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 21
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 22
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 23
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 24
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 25
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 26
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 27
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 28
			rs = ps.executeQuery();

			imprimiGrafico( bVisualizar, rs, sCab.toString() );

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( Exception err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados na tabela!\n" + err.getMessage(), true, con, err );
		}
	}

	private void imprimiGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRRazFor.jasper", "Relatório de Razão por fornecedor", sCab, rs, hParam, this );

		if ( bVisualizar ) {
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

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );

	}
}
