/**
 * @version 16/01/2008 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendCliProd.java <BR>
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
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRVendasCliProd extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodComiss = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeComiss = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	private JRadioGroup<String, String> rgTipo = null;

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcComiss = new ListaCampos( this, "VD" );

	public FRVendasCliProd() {

		super( false );
		setTitulo( "Ultimas Vendas de Cliente/Produto" );
		setAtribos( 50, 50, 355, 290 );

		montaRadioGrupo();
		montaListaCampos();
		montaTela();
	}
	
	private void montaRadioGrupo() {
		
		vLabs.addElement( "Texto" );
		vLabs.addElement( "Grafico" );
		vVals.addElement( "T" );
		vVals.addElement( "G" );

		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "G" );
	}

	private void montaListaCampos() {
	
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );
	
		lcComiss.add( new GuardaCampo( txtCodComiss, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcComiss.add( new GuardaCampo( txtNomeComiss, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		txtCodComiss.setTabelaExterna( lcComiss );
		txtCodComiss.setNomeCampo( "CodVend" );
		txtCodComiss.setFK( true );
		lcComiss.setReadOnly( true );
		lcComiss.montaSql( false, "VENDEDOR", "VD" );
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 15, 5, 80, 20 );
		adic( lbLinha, 7, 15, 320, 45 );

		adic( new JLabelPad( "De:", SwingConstants.CENTER ), 17, 30, 40, 20 );
		adic( txtDataini, 57, 30, 100, 20 );
		adic( new JLabelPad( "Até:", SwingConstants.CENTER ), 157, 30, 45, 20 );
		adic( txtDatafim, 202, 30, 100, 20 );
		adic( new JLabelPad( "Cód.Cli" ), 7, 70, 90, 20 );
		adic( txtCodCli, 7, 90, 90, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 100, 70, 227, 20 );
		adic( txtRazCli, 100, 90, 227, 20 );
		adic( new JLabelPad( "Cód.Comiss." ), 7, 110, 90, 20 );
		adic( txtCodComiss, 7, 130, 90, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 100, 110, 227, 20 );
		adic( txtNomeComiss, 100, 130, 227, 20 );
		adic( rgTipo, 7, 170, 320, 30 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sWhereCli = new StringBuffer();
		StringBuffer sWhereComiss = new StringBuffer();

		sCab.append( "de : " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + "Até : " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );

/*		if ( txtRazCli.getVlrString().trim().length() > 0 ) {
			sWhereCli.append( "AND C.CODCLI=" + txtCodCli.getVlrInteger() );
		}
		if ( txtNomeComiss.getVlrString().trim().length() > 0 ) {
			sWhereComiss.append( " AND V.CODEMPVD=" + Aplicativo.iCodEmp );
			sWhereComiss.append( " AND V.CODFILIALVD=" + ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
			sWhereComiss.append( " AND V.CODVEND=" + txtCodComiss.getVlrInteger() );
		}*/

		try {
/*
			sSQL.append( "SELECT C.RAZCLI, V.CODCLI, P.DESCPROD, IV.CODPROD, " );
			sSQL.append( "MAX(V.DTEMITVENDA) DTEMITVENDA, MAX(V.DOCVENDA) DOCVENDA, " );
			sSQL.append( "MAX(V.SERIE) SERIE, " );
			sSQL.append( "MAX (IV.VLRLIQITVENDA/(CASE WHEN IV.QTDITVENDA=0 THEN 1 ELSE IV.QTDITVENDA END)) PRECOVENDA " );
			sSQL.append( "FROM VDCLIENTE C, VDVENDA V, VDITVENDA IV, EQPRODUTO P " );
			sSQL.append( "WHERE C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " );
			sSQL.append( "C.CODCLI=V.CODCLI AND C.CODEMP=? AND C.CODFILIAL=? AND " );
			sSQL.append( "IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND " );
			sSQL.append( "IV.TIPOVENDA=V.TIPOVENDA AND IV.CODVENDA=V.CODVENDA AND " );
			sSQL.append( "P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND " );
			sSQL.append( "P.CODPROD=IV.CODPROD AND " );
			sSQL.append( "V.DTEMITVENDA BETWEEN ? AND ? " );
			sSQL.append( sWhereCli );
			sSQL.append( sWhereComiss );
			sSQL.append( " GROUP BY C.RAZCLI, V.CODCLI, P.DESCPROD, IV.CODPROD " );
*/

			sSQL.append("select razcli_ret razcli, codcli_ret codcli, descprod_ret descprod, codprod_ret codprod, ");
			sSQL.append("dtemitvenda_ret dtemitvenda, docvenda_ret docvenda, serie_ret serie, precovenda_ret precovenda ");
			sSQL.append("from vdretultvdcliprod (?,?,?,?,?,?) ");
//			sSQL.append("order by 1");
						
//			System.out.println("SQL_REL:" + sSQL.toString());
						
			ps = con.prepareStatement( sSQL.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			
			if(txtRazCli.getVlrString().trim().length() > 0 ) {				
				ps.setInt( 2, txtCodCli.getVlrInteger()  );
			}
			else {
				ps.setNull( 2, Types.INTEGER );
			}
			
			ps.setInt( 3, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
			
			if ( txtNomeComiss.getVlrString().trim().length() > 0 ) {
				ps.setInt( 4, txtCodComiss.getVlrInteger() );				
			}
			else {
				ps.setNull( 4, Types.INTEGER );
			}		
				
			ps.setDate( 5, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 6, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			
			rs = ps.executeQuery();

			if ( "G".equals( rgTipo.getVlrString() ) ) {
				imprimiGrafico( bVisualizar, rs, sCab.toString() );
			}
			else {
				imprimiTexto( bVisualizar, rs );
			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro ao buscar dados da venda!" );
		}
	}

	public void imprimiTexto( final boolean bVisualizar, final ResultSet rs ) {

		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
		ImprimeOS imp = null;
		int linPag = 0;
		String sLinFina = Funcoes.replicate( "-", 133 );
		String sLinDupla = Funcoes.replicate( "=", 133 );
		int codcli = -1;
		boolean printCliente = true;

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.verifLinPag();
			imp.montaCab();
			imp.setTitulo( "Relatório de Vendas por cliente/produto" );
			imp.addSubTitulo( "PERIODO DE :" + txtDataini.getVlrString() + " ATÉ: " + txtDatafim.getVlrString() );
			imp.limpaPags();

			while ( rs.next() ) {

				if ( imp.pRow() >= linPag - 1 ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinFina + "+" );
					imp.incPags();
					imp.eject();
				}

				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" + sLinDupla + "|" );

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 3, "CLIENTE" );
					imp.say( 44, "|" );
					imp.say( 46, "PRODUTO" );
					imp.say( 88, "|" );
					imp.say( 93, "VLR. UNIT." );
					imp.say( 107, "|" );
					imp.say( 108, "DT.UT.COMPRA" );
					imp.say( 120, "|" );
					imp.say( 122, "DOC." );
					imp.say( 135, "|" );
					
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinDupla + "|" );
				}

				if ( codcli == rs.getInt( "CODCLI" ) ) {
					printCliente = false;
				}
				else {
					if ( codcli != -1 ) { // -1 é o valor default, isso não imprime na primeira vez.
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + sLinFina + "|" );
					}
					codcli = rs.getInt( "CODCLI" );
					printCliente = true;
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				
				if ( printCliente ) {
					imp.say( 3, Funcoes.copy( rs.getString( "RAZCLI" ).trim(), 0, 40 ) );
				}
				
				imp.say( 44, "|" );
				imp.say( 46, Funcoes.copy( rs.getString( "DESCPROD" ).trim(), 0, 41 ) );
				imp.say( 88, "|" );
				imp.say( 90, rs.getBigDecimal( "PRECOVENDA" ) != null ? 
						Funcoes.strDecimalToStrCurrency( 16, Aplicativo.casasDecFin, String.valueOf( rs.getBigDecimal( "PRECOVENDA" ) ) ) : "" );
				imp.say( 107, "|" );
				imp.say( 109, rs.getDate( "DTEMITVENDA" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DTEMITVENDA" ) ) : "" );
				imp.say( 120, "|" );
				imp.say( 122, rs.getString( "DOCVENDA" ) != null ? Funcoes.copy( rs.getString( "DOCVENDA" ), 0, 12 ) : "" );
				imp.say( 135, "|" );

			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinFina + "+" );
			imp.eject();
			imp.fechaGravacao();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao imprmir relatório texto!\n" + e.getMessage(), true, con, e );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	private void imprimiGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/UltVendCli.jasper", "Ultimas Vendas por Cliente/Produto", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de Cliente por Produto!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( con );
		lcComiss.setConexao( con );
	}
}
