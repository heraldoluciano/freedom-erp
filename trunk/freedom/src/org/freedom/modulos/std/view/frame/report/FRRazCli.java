/**
 * @version 29/10/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRRazFor.java <BR>
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

package org.freedom.modulos.std.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;

public class FRRazCli extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCnpjCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private ListaCampos lcCli = new ListaCampos( this );

	public FRRazCli() {

		setTitulo( "Compras por Razão/Cliente" );
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
		
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

	}

	@ Override
	public void imprimir( boolean bVisualizar ) {

		int param = 1;
		int codcli = 0;

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sCab = new StringBuffer();

		try {

			codcli = txtCodCli.getVlrInteger().intValue();
			if ( codcli != 0 ) {
				sCab.append( "CLIENTE - " + txtDescCli.getVlrString() );
			}
			
			sSQL.append( " SELECT C.CODCLI CODEMIT, C.RAZCLI RAZEMIT, " );
			sSQL.append( " CAST( ' " );
			sSQL.append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) );
			/**
			 * Tipo A = Saldo anteiror
			 * Busca na FNRECEBER todos as vendas com valor financeiro a receber (VLRREC)
			 */
			sSQL.append( "' AS DATE) DATA, 'A' TIPO, " );
			sSQL.append( "0 DOC, (COALESCE( ( SELECT SUM(R.VLRPARCREC+VLRMULTAREC+VLRJUROSREC) " );
			sSQL.append( "FROM FNRECEBER R WHERE R.CODEMP=? AND R.CODFILIAL=? AND R.CODEMPCL=C.CODEMP AND " );
			sSQL.append( "R.CODFILIALCL=C.CODFILIAL AND R.CODCLI=C.CODCLI AND R.DATAREC < ? ),0) - " );
			
			  /**

             *  Subtrai o valor recebido do saldo anterior

             */

            sSQL.append( "COALESCE( ( SELECT SUM(L.VLRLANCA) FROM FNLANCA L WHERE  " );
            sSQL.append( " L.CODEMPCL=C.CODEMP AND L.CODFILIALCL=C.CODFILIAL AND L.CODCLI=C.CODCLI AND ");
            sSQL.append( " L.CODEMP=? AND L.CODFILIAL=? AND L.DATALANCA < ? ), 0) - " );
			
            /**
			 *  Subtrai o valor do desconto na data do lançamento financeiro 
			 */
			
			sSQL.append( "COALESCE( ( SELECT SUM(SL.VLRSUBLANCA) FROM FNLANCA L, FNSUBLANCA SL, SGPREFERE1 PF WHERE  " );
			sSQL.append( " L.CODEMPCL=C.CODEMP AND L.CODFILIALCL=C.CODFILIAL AND L.CODCLI=C.CODCLI AND ");
			sSQL.append( " SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND SL.CODLANCA=L.CODLANCA AND ");
			sSQL.append( " SL.CODEMPPN=PF.CODEMPDC AND SL.CODFILIALPN=PF.CODFILIALDC AND SL.CODPLAN=PF.CODPLANDC AND ");
			sSQL.append( " PF.CODEMP=? AND PF.CODFILIAL=? AND " );
			sSQL.append( " L.CODEMP=? AND L.CODFILIAL=? AND L.DATALANCA < ? ), 0) - " );
						
			/**
			 * Subtrai as devoluções do saldo anterior
			 */
			sSQL.append( "( COALESCE( ( SELECT SUM(CP.VLRLIQCOMPRA) ");
            sSQL.append( "FROM CPCOMPRA CP, EQTIPOMOV TM, EQCLIFOR CF ");
            sSQL.append( "WHERE CP.CODEMP=? AND CP.CODFILIAL=? AND  TM.CODEMP=CP.CODEMPTM AND ");
            sSQL.append( "TM.CODFILIAL = CP.CODFILIALTM AND TM.ESTIPOMOV='E' AND TM.TIPOMOV='DV' AND ");
            sSQL.append( "TM.CODTIPOMOV=CP.CODTIPOMOV AND ");
            sSQL.append( "CF.CODEMP=C.CODEMP AND CF.CODFILIAL=C.CODFILIAL AND CF.CODCLI=C.CODCLI AND ");
            sSQL.append( "CP.CODEMPFR=CF.CODEMPFR AND CP.CODFILIALFR=CF.CODFILIALFR AND ");
            sSQL.append( "CP.CODFOR=CF.CODFOR AND CP.DTENTCOMPRA < ? ),0) ) ) " );
			sSQL.append( "VLRDEB, 0.00 VLRCRED " );
			/**
			 * Filtro do cliente
			 */
			sSQL.append( "FROM VDCLIENTE C WHERE C.CODEMP=? AND C.CODFILIAL=? AND " );
			if ( codcli != 0 ) {
				sSQL.append( "C.CODCLI=? AND " );
			}
			/**
			 * Verifica a existência dos valores
			 */
			sSQL.append( "( EXISTS (SELECT * FROM FNRECEBER R2 WHERE R2.CODEMP=? AND R2.CODFILIAL=? AND " );
			sSQL.append( "R2.CODEMPCL=C.CODEMP AND R2.CODFILIALCL=C.CODFILIAL AND R2.CODCLI=C.CODCLI AND " );
			sSQL.append( " DATAREC BETWEEN ? AND ? ) OR ");
			sSQL.append( "EXISTS (SELECT * FROM FNLANCA L2, FNRECEBER R2 " );
			sSQL.append( "WHERE L2.CODEMPRC=R2.CODEMP AND L2.CODFILIALRC=R2.CODFILIAL AND " );
			sSQL.append( "L2.CODREC=R2.CODREC AND C.CODEMP=R2.CODEMPCL AND C.CODFILIAL=R2.CODFILIALCL AND " );
			sSQL.append( "C.CODCLI=R2.CODCLI AND R2.CODEMP=? AND R2.CODFILIAL=? AND L2.DATALANCA BETWEEN ?  AND ?) OR ");
			sSQL.append( " EXISTS (SELECT * FROM CPCOMPRA CP, EQTIPOMOV TM, EQCLIFOR CF ");
            sSQL.append(  "WHERE CP.CODEMP=? AND CP.CODFILIAL=? AND  TM.CODEMP=CP.CODEMPTM AND ");
            sSQL.append(  "TM.CODFILIAL = CP.CODFILIALTM AND TM.CODTIPOMOV=CP.CODTIPOMOV AND TM.ESTIPOMOV='E' AND ");
            sSQL.append(  "TM.TIPOMOV='DV' AND CF.CODEMP=C.CODEMP AND CF.CODFILIAL=C.CODFILIAL AND CF.CODCLI=C.CODCLI AND ");
            sSQL.append(  "CP.CODEMPFR=CF.CODEMPFR AND CP.CODFILIALFR=CF.CODFILIALFR AND ");
            sSQL.append(  "CP.CODFOR=CF.CODFOR AND CP.DTENTCOMPRA BETWEEN ? AND ? ) ) " );
			/**
			 * Fim da query de saldo anterior
			 */
			
			/**
			 * Query das vendas 
			 */
			sSQL.append( "UNION ALL SELECT R.CODCLI CODEMIT, C.RAZCLI RAZEMIT, R.DATAREC DATA, 'Q' TIPO, R.DOCREC DOC, " );
			sSQL.append( "R.VLRPARCREC VLRDEB, (R.VLRMULTAREC-R.VLRJUROSREC)*-1 VLRCRED ");
			sSQL.append( "FROM FNRECEBER R, VDCLIENTE C WHERE C.CODEMP=R.CODEMPCL AND " );
			sSQL.append( "C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI AND R.CODEMP=? AND " );
			sSQL.append( "R.CODFILIAL=? AND " );
			if ( codcli != 0 ) {
				sSQL.append( "C.CODCLI=? AND " );
			}
			sSQL.append( "R.DATAREC BETWEEN ? AND ? " );
			
			/**
			 * Query dos recebimentos 
			 */
			sSQL.append( "UNION ALL SELECT R.CODCLI CODEMIT, C.RAZCLI RAZEMIT, L.DATALANCA DATA, " );
			sSQL.append( " 'R' TIPO, R.DOCREC DOC, 0.00 VLRDEB, (L.VLRLANCA * -1) VLRCRED ");
			sSQL.append( "FROM FNLANCA L, FNRECEBER R, VDCLIENTE C " );
			sSQL.append( "WHERE L.CODEMPRC=R.CODEMP AND L.CODFILIALRC=R.CODFILIAL AND " );
			sSQL.append( "L.CODREC=R.CODREC AND C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND " );
			sSQL.append( "C.CODCLI=R.CODCLI AND " );
			if ( codcli != 0 ) {
				sSQL.append( "C.CODCLI=? AND " );
			}
			sSQL.append( "R.CODEMP=? AND R.CODFILIAL=? AND " );
			sSQL.append( "L.DATALANCA BETWEEN ? AND ? " );
			/**
			 * Query das devoluções 
			 */
			sSQL.append( "UNION ALL SELECT C.CODCLI CODEMIT, C.RAZCLI RAZEMIT, CP.DTENTCOMPRA DATA, " );
			sSQL.append( " 'Z' TIPO, CP.DOCCOMPRA DOC, 0.00 VLRDEB, (CP.VLRLIQCOMPRA * -1) VLRCRED ");
			sSQL.append( "FROM CPCOMPRA CP, EQTIPOMOV TM, EQCLIFOR CF, VDCLIENTE C " );
			sSQL.append( "WHERE TM.CODEMP=CP.CODEMPTM AND TM.CODFILIAL=CP.CODFILIALTM AND ");
			sSQL.append( "TM.CODTIPOMOV=CP.CODTIPOMOV AND TM.ESTIPOMOV='E' AND TM.TIPOMOV='DV' AND ");
            sSQL.append( "CF.CODEMP=C.CODEMP AND CF.CODFILIAL=C.CODFILIAL AND CF.CODCLI=C.CODCLI AND ");
            sSQL.append( "CP.CODEMPFR=CF.CODEMPFR AND CP.CODFILIALFR=CF.CODFILIALFR AND ");
            sSQL.append( "CP.CODFOR=CF.CODFOR AND " );
			if ( codcli != 0 ) {
				sSQL.append( "C.CODCLI=? AND " );
			}
			sSQL.append( "CP.CODEMP=? AND CP.CODFILIAL=? AND " );
			sSQL.append( "CP.DTENTCOMPRA BETWEEN ? AND ? " ); 
			
			/**
			 * Query dos descontos 
			 */
			sSQL.append( "UNION ALL SELECT R.CODCLI CODEMIT, C.RAZCLI RAZEMIT, L.DATALANCA DATA, " );
			sSQL.append( " 'X' TIPO, R.DOCREC DOC, 0.00 VLRDEB, (SL.VLRSUBLANCA * -1) VLRCRED ");
			sSQL.append( "FROM FNLANCA L, FNSUBLANCA SL, FNRECEBER R, VDCLIENTE C, SGPREFERE1 PF " );
			sSQL.append( "WHERE L.CODEMPRC=R.CODEMP AND L.CODFILIALRC=R.CODFILIAL AND " );
			
			sSQL.append( " SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND SL.CODLANCA=L.CODLANCA AND ");
			sSQL.append( " SL.CODEMPPN=PF.CODEMPDC AND SL.CODFILIALPN=PF.CODFILIALDC AND SL.CODPLAN=PF.CODPLANDC AND ");			
			sSQL.append( " PF.CODEMP=? AND PF.CODFILIAL=? AND " );
			
			sSQL.append( " L.CODREC=R.CODREC AND C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND " );

			sSQL.append( "C.CODCLI=R.CODCLI AND " );
			if ( codcli != 0 ) {
				sSQL.append( "C.CODCLI=? AND " );
			}
			sSQL.append( "R.CODEMP=? AND R.CODFILIAL=? AND R.VLRDESCREC>0 AND " );
			sSQL.append( "L.DATALANCA BETWEEN ? AND ?  " );
			
			sSQL.append( "ORDER BY 1, 2, 3, 4, 5 " );

			
			
			ps = con.prepareStatement( sSQL.toString() );

			ps.setInt( param++, Aplicativo.iCodEmp ); // 1
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 2
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 3 
			
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 5
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 6

			// Parametros do desconto

			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGPREFERE1" ) ); // 5
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 5
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 6

			// Parametros do saldo de devoluções
			ps.setInt( param++, Aplicativo.iCodEmp ); // 7
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) ); // 8
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 9
			// Parametros de filtro do cliente
			ps.setInt( param++, Aplicativo.iCodEmp ); // 10
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDCLIENTE" ) ); // 11
			if ( codcli != 0 ) {
				ps.setInt( param++, codcli ); // 12
			}
			// Parametros do exists
			ps.setInt( param++, Aplicativo.iCodEmp ); // 13
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 14
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 15
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 16
			ps.setInt( param++, Aplicativo.iCodEmp ); // 17
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 18
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 19
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 20
			// Parametros do exists referente ao saldo de devoluções
			ps.setInt( param++, Aplicativo.iCodEmp ); // 21
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) ); // 22
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 23
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 24
			ps.setInt( param++, Aplicativo.iCodEmp ); // 25
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 26
			if ( codcli != 0 ) {
				ps.setInt( param++, codcli ); // 27
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 28
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 29
			if ( codcli != 0 ) {
				ps.setInt( param++, codcli ); // 30
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 31
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 32
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 33
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 34

			// Parâmetros das devoluções
			if ( codcli != 0 ) {
				ps.setInt( param++, codcli ); // 36
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 37
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) ); // 38
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 39
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 40
			
			// Parametros dos descontos

			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGPREFERE1" ) ); // 5
			
			if ( codcli != 0 ) {
				ps.setInt( param++, codcli ); // 41
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 42
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 43
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 44
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 45
			
			System.out.println("QUERY" + sSQL.toString());
			
			rs = ps.executeQuery();
			
			imprimiGrafico( bVisualizar, rs, sCab.toString() );

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados na tabela!\n" + err.getMessage(), true, con, err );
		}
	}

	private void imprimiGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNRECEBER" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRRazCli.jasper", "Relatório de Razão por Cliente", sCab, rs, hParam, this );

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

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );

	}
}
