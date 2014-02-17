/**
 * @version 25/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRRazaoFin.java <BR>
 * 
 *                     Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                     na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                     Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                     Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                     Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.fnc.library.swing.component.JTextFieldPlan;

public class FRRazaoFin extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDescPlan = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPlan txtCodPlan = new JTextFieldPlan( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JRadioGroup<String, String> rgTipoRel = null;

	private ListaCampos lcPlan = new ListaCampos( this );

	public FRRazaoFin() {

		super( false );
		setTitulo( "Relatório razão financeiro" );
		setAtribos( 80, 80, 330, 230 );

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "Cód.plan", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descrição do planejamento", ListaCampos.DB_SI, false ) );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setWhereAdic( "NIVELPLAN=6" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan, null );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );

		Vector<String> labelTipoRel = new Vector<String>();
		labelTipoRel.addElement( "Gráfico" );
		labelTipoRel.addElement( "Texto" );
		Vector<String> valueTipoRel = new Vector<String>();
		valueTipoRel.addElement( "G" );
		valueTipoRel.addElement( "T" );
		rgTipoRel = new JRadioGroup<String, String>( 1, 2, labelTipoRel, valueTipoRel, JComboBoxPad.TP_STRING );
		
		adic( new JLabelPad( "Periodo:" ), 7, 5, 125, 20 );
		adic( new JLabelPad( "De:" ), 7, 25, 30, 20 );
		adic( txtDataini, 32, 25, 125, 20 );
		adic( new JLabelPad( "Até:" ), 160, 25, 22, 20 );
		adic( txtDatafim, 185, 25, 125, 20 );
		adic( new JLabelPad( "Nº planejamento" ), 7, 50, 250, 20 );
		adic( txtCodPlan, 7, 70, 100, 20 );
		adic( new JLabelPad( "Descrição do planejamento" ), 110, 50, 240, 20 );
		adic( txtDescPlan, 110, 70, 200, 20 );
		adic( new JLabelPad( "Formato de impressão" ), 7, 90, 240, 20 );
		adic( rgTipoRel, 7, 110, 200, 30 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		txtCodPlan.setRequerido( true );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcPlan.setConexao( cn );
	}

	private double buscaSaldo() {

		double dRet = 0.00;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int iParam = 1;

			StringBuilder sql = new StringBuilder();
			sql.append( "select first 1 sl.codplan, saldosl from fnsaldolanca sl ");
			sql.append( "where sl.codemp=? and sl.codfilial=? and sl.codemp=? and sl.codfilialpn=? ");
			sql.append( "and sl.codplan=? and sl.datasl<? order by sl.datasl desc" );
			ps = con.prepareStatement(sql.toString());
			ps.setInt( iParam++, Aplicativo.iCodEmp );
			ps.setInt( iParam++, Aplicativo.iCodFilial );
			ps.setInt( iParam++, Aplicativo.iCodEmp );
			ps.setInt( iParam++, lcPlan.getCodFilial() );
			ps.setString( iParam++, txtCodPlan.getVlrString() );
			ps.setDate( iParam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				dRet = Funcoes.strCurrencyToDouble( rs.getString( "SALDOSL" ) == null ? "0,00" : rs.getString( "SALDOSL" ) );
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao buscar saldo!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}

		return dRet;
	}

	private ResultSet getResultSet() throws SQLException {
		ResultSet result = null;
		StringBuilder sql = new StringBuilder();
		sql.append( "select sl.datasublanca, sl.codlanca, sl.histsublanca, sl.vlrsublanca ");
		sql.append( ", coalesce(f.codfor, c.codcli) codemit, coalesce(f.razfor, c.razcli) razemit ");
		sql.append( ", (case when f.codfor is not null then 'F' when c.codcli is not null then 'C' else 'A' end) tipo ");
		sql.append( "from fnsublanca sl ");
		sql.append( "left outer join cpforneced f ");
		sql.append( "on f.codemp=sl.codempfr and f.codfilial=sl.codfilialfr and f.codfor=sl.codfor ");
		sql.append( "left outer join vdcliente c ");
		sql.append( "on c.codemp=sl.codempcl and c.codfilial=sl.codfilialcl and c.codcli=sl.codcli ");
		sql.append( "where sl.codemp=? and sl.codfilial=? and sl.codplan=? ");
		sql.append( "and sl.codemppn=? and sl.codfilialpn=? and sl.datasublanca between ? and ? " );
		sql.append( "order by sl.datasublanca");

		PreparedStatement ps = con.prepareStatement( sql.toString() );

		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, Aplicativo.iCodFilial );
		ps.setString( 3, txtCodPlan.getVlrString() );
		ps.setInt( 4, Aplicativo.iCodEmp );
		ps.setInt( 5, lcPlan.getCodFilial() );
		ps.setDate( 6, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
		ps.setDate( 7, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
		result = ps.executeQuery();
		return result;
	}
	
	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		String codplan = txtCodPlan.getVlrString().trim();

		if ( codplan.equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Informe um código de conta !" );
			return;
		}
		
		if ("T".equals(rgTipoRel.getVlrString())) {
			imprimirTexto( bVisualizar, codplan );
		} else {
			imprimirGrafico( bVisualizar, codplan);
		}

	}
	
	private void imprimirGrafico( TYPE_PRINT bVisualizar, String codplan) {
		
	}
	
	private void imprimirTexto( TYPE_PRINT bVisualizar, String codplan) {
		BigDecimal bVlrSubLanca = new BigDecimal( 0 );
		BigDecimal bTotal = new BigDecimal( 0 );
		BigDecimal bTotDesp = new BigDecimal( 0 );
		BigDecimal bTotRec = new BigDecimal( 0 );
		String sSQL = null;
		String sConta = "";
		String sSaldoAnt = "";
		ResultSet rs = null;
		
		int linPag = 0;
		Vector<String> hist = null;
		ImprimeOS imp = null;
		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Razão financeiro" );
			imp.addSubTitulo( "RELATORIO RAZÃO FINANCEIRO" );
			if ( ! ( codplan.trim().equals( "" ) ) ) {
				sConta = "CONTA: " + codplan + " - " + txtDescPlan.getVlrString();
				imp.addSubTitulo( sConta );
			}
			imp.limpaPags();

			sSaldoAnt = Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( buscaSaldo() ) );

			rs = getResultSet();

			while ( rs.next() ) {
				if ( imp.pRow() >= linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
					imp.eject();
					imp.incPags();
				}
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 105, "SALDO ANTERIOR" );
					imp.say( 121, sSaldoAnt );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 6, "Data" );
					imp.say( 14, "|" );
					imp.say( 15, "Lançamento" );
					imp.say( 25, "|" );
					imp.say( 27, "Histórico" );
					imp.say( 90, "|" );
					imp.say( 94, "Receita" );
					imp.say( 105, "|" );
					imp.say( 109, "Despesa" );
					imp.say( 120, "|" );
					imp.say( 126, "Saldo" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 3, Funcoes.dateToStrDate( rs.getDate( "DATASUBLANCA" ) ) );
				imp.say( 14, "|" );
				imp.say( 16, rs.getString( "CODLANCA" ) );
				imp.say( 25, "|" );

				StringBuilder histSublanca = new StringBuilder();
				String tipo = rs.getString( "tipo" );
				if (!"A".equals( tipo ) ) {
					if ("F".equals( tipo )) {
						histSublanca.append( "Forn.: " );
					} else if ("C".equals( tipo )) {
						histSublanca.append( "Cli.: ");
					}
					histSublanca.append( rs.getInt( "codemit" ));
					histSublanca.append(" - ");
					histSublanca.append( rs.getString( "razemit" ).trim() );
					histSublanca.append( ". " );
				}
		
				if (rs.getString( "histsublanca" )!=null) {
					histSublanca.append( rs.getString( "histsublanca" ).trim() );
				}
				hist  = Funcoes.strToVectorSilabas( histSublanca.toString(), 62 );
				imp.say( 27, hist.get( 0 ) );
				bVlrSubLanca = rs.getBigDecimal( "VLRSUBLANCA" );
				bTotal = bTotal.add( bVlrSubLanca );

				if ( bVlrSubLanca.doubleValue() < 0 ) {
					imp.say( 90, "|" );
					imp.say( 92, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bVlrSubLanca.doubleValue() * -1 ) ) );
					imp.say( 105, "|" );
					imp.say( 120, "|" );
					bTotRec = bTotRec.add( new BigDecimal( bVlrSubLanca.doubleValue() * -1 ) );
				}
				else {
					imp.say( 90, "|" );
					imp.say( 105, "|" );
					imp.say( 107, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bVlrSubLanca.doubleValue() ) ) );
					imp.say( 120, "|" );
					bTotDesp = bTotDesp.add( rs.getBigDecimal( "VLRSUBLANCA" ) );
				}

				imp.say( 122, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bTotal ) ) );
				imp.say( 135, "|" );

				if ( hist.size() > 1 ) {
					for ( int i = 1; i < hist.size(); i++ ) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 14, "|" );
						imp.say( 25, "|" );
						imp.say( 27, hist.get( i ) );
						imp.say( 90, "|" );
						imp.say( 105, "|" );
						imp.say( 120, "|" );
						imp.say( 135, "|" );
					}
				}
			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 90, "|" );
			imp.say( 91, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bTotRec ) ) );
			imp.say( 105, "|" );
			imp.say( 106, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bTotDesp ) ) );
			imp.say( 120, "|" );
			imp.say( 122, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bTotal ) ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );

			imp.eject();

			imp.fechaGravacao();

			rs.close();
			
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro na consulta de sublançamentos!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			System.gc();
		}

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
		
	}
}
