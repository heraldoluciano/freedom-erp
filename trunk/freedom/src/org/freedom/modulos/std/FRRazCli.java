/**
 * @version 29/10/2007 <BR>
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

public class FRRazCli extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

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
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
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
			sSQL.append( Funcoes.dateToStrDB( txtDataini.getVlrDate() )  );
			sSQL.append( "' AS DATE) DATA, 'A' TIPO, ");
			sSQL.append( "0 DOC, (COALESCE( ( SELECT SUM(R.VLRREC) FROM FNRECEBER R WHERE ");
			sSQL.append( "R.CODEMP=? AND R.CODFILIAL=? AND R.CODEMPCL=C.CODEMP AND ");
			sSQL.append( "R.CODFILIALCL=C.CODFILIAL AND R.CODCLI=C.CODCLI AND R.DATAREC < ? ),0) + ");
			sSQL.append( "COALESCE( ( SELECT SUM(L.VLRLANCA) FROM FNLANCA L, FNRECEBER R WHERE L.CODEMPRC=R.CODEMP AND ");
			sSQL.append( "L.CODFILIALRC=R.CODFILIAL AND L.CODREC=R.CODREC AND ");
			sSQL.append( "R.CODEMPCL=C.CODEMP AND R.CODFILIALCL=R.CODFILIAL AND ");
			sSQL.append( "R.CODCLI=R.CODCLI AND R.CODEMP=? AND R.CODFILIAL=? AND L.DATALANCA < ? ), 0) ) VALOR ");
			sSQL.append( "FROM VDCLIENTE C WHERE C.CODEMP=? AND C.CODFILIAL=? AND " );
			if ( codcli != 0 ){
				sSQL.append( "C.CODCLI=? AND ");
			}
			sSQL.append( "( EXISTS (SELECT * FROM FNRECEBER R2 WHERE R2.CODEMP=? AND R2.CODFILIAL=? AND ");
			sSQL.append( "R2.CODEMPCL=C.CODEMP AND R2.CODFILIALCL=C.CODFILIAL AND R2.CODCLI=C.CODCLI AND ");
			sSQL.append( " DATAREC BETWEEN ? AND ? )OR EXISTS (SELECT * FROM FNLANCA L2, FNRECEBER R2 ");
			sSQL.append( "WHERE L2.CODEMPRC=R2.CODEMP AND L2.CODFILIALRC=R2.CODFILIAL AND ");
			sSQL.append( "L2.CODREC=R2.CODREC AND C.CODEMP=R2.CODEMPCL AND C.CODFILIAL=R2.CODFILIALCL AND ");
			sSQL.append( "C.CODCLI=R2.CODCLI AND R2.CODEMP=? AND R2.CODFILIAL=? AND L2.DATALANCA BETWEEN ?  AND ?) ) ");
			sSQL.append( "UNION SELECT R.CODCLI CODEMIT, C.RAZCLI RAZEMIT, R.DATAREC DATA, 'Q' TIPO, R.DOCREC DOC, ");
			sSQL.append( "R.VLRREC VALOR FROM FNRECEBER R, VDCLIENTE C WHERE C.CODEMP=R.CODEMPCL AND ");
			sSQL.append( "C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI AND R.CODEMP=? AND ");
			sSQL.append( "R.CODFILIAL=? AND " );
			if( codcli != 0 ){
				sSQL.append("C.CODCLI=? AND ");
			}
			sSQL.append( "R.DATAREC BETWEEN ? AND ? ");
			sSQL.append( "UNION SELECT R.CODCLI CODEMIT, C.RAZCLI RAZEMIT, L.DATALANCA DATA, ");
			sSQL.append( " 'R' TIPO, R.DOCREC DOC, L.VLRLANCA FROM FNLANCA L, FNRECEBER R, VDCLIENTE C " );
			sSQL.append( "WHERE L.CODEMPRC=R.CODEMP AND L.CODFILIALRC=R.CODFILIAL AND ");
			sSQL.append( "L.CODREC=R.CODREC AND C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND ");
			sSQL.append( "C.CODCLI=R.CODCLI AND ");
			if( codcli != 0 ){
				sSQL.append( "C.CODCLI=? AND "); 
			}
			sSQL.append( "R.CODEMP=? AND R.CODFILIAL=? AND ");
			sSQL.append( "L.DATALANCA BETWEEN ? AND ? " );
			sSQL.append( "ORDER BY 1, 2, 3, 4, 5 " );
			
			ps = con.prepareStatement( sSQL.toString() );
			// ps.setDate( param++ , Funcoes.strDateToSqlDate( txtDataini.getVlrString() )); // 1
			ps.setInt( param++, Aplicativo.iCodEmp ); // 2
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 3
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 4
			ps.setInt( param++, Aplicativo.iCodEmp ); // 5
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 6
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 7
			ps.setInt( param++, Aplicativo.iCodEmp ); // 8
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDCLIENTE" ) ); // 9
			if ( codcli != 0 ) {
				ps.setInt( param++, codcli ); // 10
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 11
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 12
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 13
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 14
			ps.setInt( param++, Aplicativo.iCodEmp ); // 15
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 16
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 17
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 18
			ps.setInt( param++, Aplicativo.iCodEmp ); // 19
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 20
			if ( codcli != 0 ) {
				ps.setInt( param++, codcli ); // 21
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 22
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 23
			if ( codcli != 0 ) {
				ps.setInt( param++, codcli ); // 24
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 25
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) ); // 26
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
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNRECEBER" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
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

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );

	}
}
