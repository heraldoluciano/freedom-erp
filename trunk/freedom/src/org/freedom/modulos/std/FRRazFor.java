/**
 * @version 24/10/2007 <BR>
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

package org.freedom.modulos.std;

import org.freedom.infra.model.jdbc.DbConnection;
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
			sSQL.append( "(COALESCE( ( SELECT SUM(P.VLRPARCPAG+P.VLRMULTAPAG+P.VLRJUROSPAG) " );
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
			sSQL.append( "L.DATALANCA < ? ), 0) +  " );
			
			
			 /**
			 *  Subtrai o valor do desconto na data do lançamento financeiro 
			 */
			
			sSQL.append( "COALESCE( ( SELECT SUM(SL.VLRSUBLANCA) FROM FNLANCA L, FNSUBLANCA SL, SGPREFERE1 PF WHERE  " );
			sSQL.append( " L.CODEMPFR=F.CODEMP AND L.CODFILIALFR=F.CODFILIAL AND L.CODFOR=F.CODFOR AND ");
			sSQL.append( " SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND SL.CODLANCA=L.CODLANCA AND ");
			sSQL.append( " SL.CODEMPPN=PF.CODEMPDR AND SL.CODFILIALPN=PF.CODFILIALDR AND SL.CODPLAN=PF.CODPLANDR AND ");
			sSQL.append( " PF.CODEMP=? AND PF.CODFILIAL=? AND " );
			sSQL.append( " L.CODEMP=? AND L.CODFILIAL=? AND L.DATALANCA < ? ), 0) - " );
			
			
			/**
			 *  Subtrai o valor das devoluções do saldo anterior
			 */
			sSQL.append(  "( COALESCE( ( SELECT SUM(VD.VLRLIQVENDA) ");
            sSQL.append(  "FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF ");
            sSQL.append(  "WHERE VD.CODEMP=? AND VD.CODFILIAL=? AND  TM.CODEMP=VD.CODEMPTM AND ");
            sSQL.append(  "TM.CODFILIAL=VD.CODFILIALTM AND TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND ");
            sSQL.append(  "TM.CODTIPOMOV=VD.CODTIPOMOV AND CF.CODEMPFR=F.CODEMP AND ");
            sSQL.append(  "CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND VD.CODEMPCL=CF.CODEMP AND ");
            sSQL.append(  "VD.CODFILIALCL=CF.CODFILIAL AND VD.CODCLI=CF.CODCLI AND VD.DTEMITVENDA < ? ),0) ) ) " );
			sSQL.append( " VLRCRED " );
			/**
			 * Filtro do fornecedor
			 */			
			sSQL.append( "FROM CPFORNECED F " );
			sSQL.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			/**
			 * Verifica a existência dos valores
			 */			
			sSQL.append( "( EXISTS (SELECT * FROM FNPAGAR P2 " );
			sSQL.append( "WHERE P2.CODEMP=? AND P2.CODFILIAL=? AND " );
			sSQL.append( "P2.CODEMPFR=F.CODEMP AND P2.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "P2.CODFOR=F.CODFOR AND DATAPAG BETWEEN ? AND ? )" );
			sSQL.append( "OR EXISTS (SELECT * FROM FNLANCA L2 " );
			sSQL.append( "WHERE F.CODEMP=L2.CODEMPFR AND " );
			sSQL.append( "F.CODFILIAL=L2.CODFILIALFR AND F.CODFOR=L2.CODFOR AND " );
			sSQL.append( "L2.CODEMP=? AND L2.CODFILIAL=? AND " );
			sSQL.append( "L2.DATALANCA BETWEEN ? AND ? ) OR " );
			sSQL.append( " EXISTS (SELECT * FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF ");
            sSQL.append( "WHERE VD.CODEMP=? AND VD.CODFILIAL=? AND TM.CODEMP=VD.CODEMPTM AND ");
            sSQL.append( "TM.CODFILIAL=VD.CODFILIALTM AND TM.CODTIPOMOV=VD.CODTIPOMOV AND ");
            sSQL.append( "TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND ");
            sSQL.append( "CF.CODEMPFR=F.CODEMP AND CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND ");
            sSQL.append( "VD.CODEMPCL=CF.CODEMP AND VD.CODFILIALCL=CF.CODFILIAL AND ");
            sSQL.append( "VD.CODCLI=CF.CODCLI AND VD.DTEMITVENDA BETWEEN ? AND ? ) ) " );			
			/**
			 * Fim da query do saldo anterior
			 */
			
			/**
			 * Query das compras 
			 */
			sSQL.append( "UNION ALL " );
			sSQL.append( "SELECT P.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "P.DATAPAG DATA, 'C' TIPO, P.DOCPAG DOC, ");
			sSQL.append( "(P.VLRMULTAPAG-P.VLRJUROSPAG) VLRDEB, P.VLRPARCPAG VLRCRED " );
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
			sSQL.append( "UNION ALL " );
			sSQL.append( "SELECT L.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "L.DATALANCA DATA, 'P' TIPO, P.DOCPAG DOC, L.VLRLANCA*-1 VLRDEB, 0.00 VLRCRED " ); 
			sSQL.append( "FROM FNLANCA L, CPFORNECED F, FNPAGAR P " );
			sSQL.append( "WHERE F.CODEMP=L.CODEMPFR AND F.CODFILIAL=L.CODFILIALFR AND " );
			sSQL.append(  "P.CODEMP=L.CODEMPPG AND P.CODFILIAL=L.CODFILIALPG AND P.CODPAG=L.CODPAG AND " );
			sSQL.append( "F.CODFOR=L.CODFOR AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "L.CODEMP=? AND L.CODFILIAL=? AND " );
			sSQL.append( "L.DATALANCA BETWEEN ? AND ? " );
			/**
			 * Query das devoluções 
			 */
			sSQL.append( "UNION ALL SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, VD.DTEMITVENDA DATA, " );
			sSQL.append( " 'Z' TIPO, VD.DOCVENDA DOC, VD.VLRLIQVENDA VLRCRED, 0.00 VLRDEB ");
			sSQL.append( "FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF, CPFORNECED F " );
			sSQL.append( "WHERE TM.CODEMP=VD.CODEMPTM AND TM.CODFILIAL=VD.CODFILIALTM AND ");
			sSQL.append( "TM.CODTIPOMOV=VD.CODTIPOMOV AND TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND ");
            sSQL.append( "CF.CODEMPFR=F.CODEMP AND CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND ");
            sSQL.append( "VD.CODEMPCL=CF.CODEMP AND VD.CODFILIALCL=CF.CODFILIAL AND ");
            sSQL.append( "VD.CODCLI=CF.CODCLI AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "VD.CODEMP=? AND VD.CODFILIAL=? AND " );
			sSQL.append( "VD.DTEMITVENDA BETWEEN ? AND ? " ); 
			
			
			/**
			 * Query dos descontos 
			 */
			sSQL.append( "UNION ALL SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, L.DATALANCA DATA, " );
			sSQL.append( " 'X' TIPO, P.DOCPAG DOC, (SL.VLRSUBLANCA * -1) VLRDEB,  0.00 VLRCRED ");
			sSQL.append( "FROM FNLANCA L, FNSUBLANCA SL, FNPAGAR P, CPFORNECED F, SGPREFERE1 PF " );
			sSQL.append( "WHERE L.CODEMPPG=P.CODEMP AND L.CODFILIALPG=P.CODFILIAL AND " );
			
			sSQL.append( " SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND SL.CODLANCA=L.CODLANCA AND ");
			sSQL.append( " SL.CODEMPPN=PF.CODEMPDR AND SL.CODFILIALPN=PF.CODFILIALDR AND SL.CODPLAN=PF.CODPLANDR AND ");			
			sSQL.append( " PF.CODEMP=? AND PF.CODFILIAL=? AND " );
			
			sSQL.append( " L.CODPAG=P.CODPAG AND F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );

			sSQL.append( "F.CODFOR=P.CODFOR AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
			sSQL.append( "L.DATALANCA BETWEEN ? AND ?  " );

			
			sSQL.append( "ORDER BY 1, 2, 3, 4, 5" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp ); // 1
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 2
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 3
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 5
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 6

			//Paramatro do saldo de descontos
			
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGPREFERE1" ) ); // 5
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 5
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 6					
			
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
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 18
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
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNLANCA" ) ); // 32
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 33
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 34
            // Parâmetros das devoluções
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 35
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 36
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) ); // 37
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 38
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 39
			
			// Parametros dos descontos

			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGPREFERE1" ) ); // 5
			
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 41
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
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
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

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );

	}
}
