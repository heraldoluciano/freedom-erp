/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRBalancete.java <BR>
 * 
 *                      Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                      modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                      na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                      Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                      sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                      Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                      Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                      Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

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

public class FRBalancete extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtCodplanConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );
	
	private JTextFieldPad txtAnocc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0);

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private Vector<String> descOpcao = new Vector<String>();
	
	private Vector<String> valOpcao = new Vector<String>();
	
	private JRadioGroup<String, String> rgOpcao = null;
	
	private JCheckBoxPad cbDataCompetencia = new JCheckBoxPad( "Por data de competência", "S", "N" );

	private ListaCampos lcCC = new ListaCampos( this );

	private ListaCampos lcConta = new ListaCampos( this );

	public FRBalancete() {

		setTitulo( "Balancete" );
		setAtribos( 80, 80, 400, 360 );

		descOpcao.addElement( "Gráfico" );
		descOpcao.addElement( "Texto ");
		
		valOpcao.addElement( "G" );
		valOpcao.addElement( "T" );
		
		rgOpcao = new JRadioGroup<String, String>( 1, 2, descOpcao, valOpcao );
		
		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "Cód.conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.add( new GuardaCampo( txtCodplanConta, "CodPlan", "Cód.planej.", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta, null );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "Cód.cc.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descrição do centro de custos", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtAnocc, "AnoCC", "Ano.cc.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI, false ) );
		lcCC.setReadOnly( true );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC, null );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );
		txtSiglaCC.setListaCampos( lcCC );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 120, 20 );
		adic( new JLabelPad( "De:" ), 7, 25, 30, 20 );
		adic( txtDataini, 40, 25, 117, 20 );
		adic( new JLabelPad( "Até:" ), 160, 25, 22, 20 );
		adic( txtDatafim, 185, 25, 120, 20 );

		adic( txtCodConta, 7, 70, 80, 20, "Nº conta" );
		adic( txtDescConta, 90, 70, 200, 20, "Descrição da conta" );
		adic( txtCodCC, 7, 110, 80, 20, "Cód.cc."  );
		adic( txtDescCC, 90, 110, 200, 20, "Descrição do centro de custo" );

		adic( cbDataCompetencia, 7, 140, 200, 20 );
		
		adic( rgOpcao, 7, 170, 200, 30 );
		
		btExportXLS.setEnabled( true );
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcCC.setConexao( cn );
		lcCC.setWhereAdic( "NIVELCC=10 AND ANOCC=" + buscaAnoBaseCC() );
	}

	private int buscaAnoBaseCC() {

		int iRet = 0;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() )
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo!\n" + err.getMessage(), true, con, err );
		}
		return iRet;
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {
		String codconta = txtCodConta.getVlrString();
		String codcc = txtCodCC.getVlrString().trim();
		String dataini = txtDataini.getVlrString();
		String datafim = txtDatafim.getVlrString();
		String codplanconta = txtCodplanConta.getVlrString();
		Integer anocc = txtAnocc.getVlrInteger();
        StringBuffer sql = new StringBuffer();
        StringBuilder filtros = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int param = 1;
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		sql.append( "select p.codplan, p.descplan, p.nivelplan " );
		sql.append( ", sum(sl.vlrsublanca*-1) valor ");
		sql.append( "from fnplanejamento p ");
		sql.append( "inner join fnsublanca sl on ");
		sql.append( "sl.codemppn=p.codemp and sl.codfilialpn=p.codfilial and sl.codplan like rtrim(p.codplan)||'%' ");
		sql.append( "and ");
		if ("S".equals( cbDataCompetencia.getVlrString() )) {
			sql.append( "sl.dtcompsublanca" );	
			filtros.append( "FILTROS: DATA DE COMPETÊNCIA" );
		} else {
			sql.append( "sl.datasublanca");
			filtros.append( "FILTROS: DATA DE EXECUÇÃO" );
		}
		sql.append( " between ? and ? ");
		if (! "".equals( codcc.trim() )) {
			sql.append( " and sl.codempcc=? and sl.codfilialcc=? and sl.codcc=? and sl.anocc=? " );
			filtros.append(" - CENTRO DE CUSTO: ");
			filtros.append(codcc);
		}
		sql.append( "inner join fnlanca l on ");
		sql.append( "l.codemp=sl.codemp and l.codfilial=sl.codfilial and l.codlanca=sl.codlanca ");
		sql.append( "and l.flag in ");
		sql.append(  AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) );
		if (! "".equals(codconta.trim())) {
			sql.append( " and l.codemppn=? and l.codfilialpn=? and l.codplan=? ");	
			filtros.append( " - CONTA: " );
			filtros.append(codconta);
		}
		sql.append( " where p.codemp=? and p.codfilial=? and p.tipoplan in ('R','D') ");
		sql.append( "group by p.codplan, p.descplan, p.nivelplan ");
		sql.append( "order by p.codplan, p.descplan, p.nivelplan ");

		try {
			ps = con.prepareStatement( sql.toString() );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			if ( !codcc.trim().equals( "" ) ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "FNCC" ) );
				ps.setString( param++, codcc );
				ps.setInt( param++, anocc );
			}
			if ( !codconta.trim().equals( "" ) ) {
				// Planejamento da conta bancária
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
				ps.setString( param++, codplanconta );
			}
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
			rs = ps.executeQuery();
			
			if (bVisualizar==TYPE_PRINT.EXPORT) {
				if (btExportXLS.execute(rs, getTitle())) {
					Funcoes.mensagemInforma( this, "Arquivo exportado com sucesso !" );
				}
				rs.close();
				ps.close();
				con.commit();
			} else {

				if ("G".equals( rgOpcao.getVlrString()) ) {
					
					imprimirGrafico(bVisualizar, rs, dataini, datafim, codconta, codcc, filtros );
					
				} else {
					imprimirTexto(bVisualizar, rs, dataini, datafim, codconta, codcc );
				}
			}

		} catch ( SQLException e) {
			Funcoes.mensagemErro( this, "Erro executando consulta: \n" + e.getMessage() );
			e.printStackTrace();
		}
	}

	private void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sDataini, 
			final String sDatafim, final String sCodConta, final String sCodCC, final StringBuilder filtros ) {
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		    filtros.append( " - PERÍODO DE " );
			filtros.append( sDataini );
			filtros.append( " A " );
			filtros.append( sDatafim );
		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNLANCA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		//hParam.put( "FILTROS", filtros.toString() );

		dlGr = new FPrinterJob( "relatorios/balancete.jasper", "Balancete", filtros.toString(), rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão do balancete!\n" + err.getMessage(), true, con, err );
			}
		}
	}
	
	private void imprimirTexto( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sDataini, 
			final String sDatafim, final String sCodConta, final String sCodCC ) {

		String sConta = "";
		String sCC = "";
		String sDescplan = "";
		
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		
		StringBuilder sql = new StringBuilder();

		BigDecimal bTotal = new BigDecimal( "0" );

		imp.montaCab();

		imp.setTitulo( "Balancete" );

		try {
			imp.limpaPags();
			BigDecimal bigValMaster = null;

			imp.addSubTitulo( "BALANCETE - PERIODO DE " + sDataini + " A " + sDatafim );

			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {

					if ( ! ( sCodConta.trim().equals( "" ) ) ) {
						sConta = "CONTA: " + sCodConta + " - " + txtDescConta.getVlrString();
						imp.addSubTitulo( sConta );
					}
					if ( ! ( sCodCC.equals( "" ) ) ) {
						sCC = "CENTRO DE CUSTO: " + txtSiglaCC.getVlrString() + " - " + txtDescCC.getVlrString();
						imp.addSubTitulo( sCC );
					}

					imp.impCab( 80, true );
					imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 77 ) + "|" );
					imp.say( imp.pRow() + 1, 0, "| Código Plan." );
					imp.say( imp.pRow(), 15, "| Descrição" );
					imp.say( imp.pRow(), 59, "|  %   " );
					imp.say( imp.pRow(), 66, "| Valor" );
					imp.say( imp.pRow(), 79, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 77 ) + "|" );
				}

				if ( rs.getString( 4 ) != null ) {
					int iNivel = rs.getString( "nivelplan" ) == null ? 1 : rs.getInt( "nivelplan" );
					if ( !rs.getString( 4 ).equals( "0" ) ) {
						BigDecimal bigBasePerc = null;
						int iNivelplan = iNivel == 0 || iNivel == 2 ? 1 : iNivel;
						iNivelplan = ( iNivelplan - 1 ) * 2;

						sDescplan = " " + StringFunctions.replicate( " ", iNivelplan ) + rs.getString( "descplan" );

						if ( iNivel > 1 ) {
							bigBasePerc = new BigDecimal( rs.getString( 4 ) );
						}

						imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
						imp.say( imp.pRow(), 0, "|" + Funcoes.copy( rs.getString( "codplan" ), 0, 13 ) + "|" + Funcoes.copy( sDescplan, 0, 43 ) + "|"
								+ ( bigBasePerc == null ? "  --  " : Funcoes.strDecimalToStrCurrency( 6, 2, "" + bigBasePerc.multiply( new BigDecimal( 100 ) ).divide( bigValMaster, 2, BigDecimal.ROUND_HALF_UP ) ) ) + // Não imprime nada se o nivel
								// superior tiver -1.
								"|" + Funcoes.strDecimalToStrCurrency( 12, 2, rs.getString( 4 ) ) + "|" );

					}
					if ( iNivel == 1 ) {
						bigValMaster = new BigDecimal( rs.getString( 4 ) );
						bTotal = bTotal.add( new BigDecimal( rs.getString( 4 ) ) );
					}
				}

				if ( imp.pRow() == ( linPag - 1 ) ) {
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "-", 77 ) + "+" );
					imp.eject();
					imp.incPags();
				}

			}

			imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
			imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "-", 77 ) + "+" );
			imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
			imp.say( imp.pRow(), 0, "|" );
			imp.say( imp.pRow(), 40, "TOTAL RECEITAS/DESPESAS" );
			imp.say( imp.pRow(), 66, "|" + Funcoes.strDecimalToStrCurrency( 12, 2, "" + bTotal ) + "|" );
			imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
			imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "-", 77 ) + "+" );

			imp.eject();

			imp.fechaGravacao();

			// rs.close();
			// ps.close();
			con.commit();
			// dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consultar as bases financeiras!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}
}
