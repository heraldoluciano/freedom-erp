/**
 * @version 03/05/2010 <BR>
 * @author Setpoint Informática Ltda./Cristian Ribeiro Mietlicki<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std.view.frame.report <BR>
 * Classe:
 * @(#)FRVendasIpi.java <BR>
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
 * Tela de filtros para o relatório IPI.
 * 
 */

package org.freedom.modulos.lvf.view.frame.report;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

public class FRPisCofins extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	public FRPisCofins() {

		super( false );
		setTitulo( "Relatório de PIS e COFINS" );
		setAtribos( 50, 50, 355, 140 );

		montaTela();
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
		StringBuffer sql = new StringBuffer();
		StringBuffer sCab = new StringBuffer();

		sCab.append( "Perído de : " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + "Até : " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );
		
		try {
			
			sql.append( "select vd.dtemitvenda dtemit, 'S' tipo , sum(lfi.vlrbasepis) vlrbasepis, sum(lfi.vlrbasecofins) vlrbasecofins, ");
			sql.append( "sum(lfi.vlrpis) vlrpis, sum(lfi.vlrcofins) vlrcofins ");
			sql.append( "from ");
			sql.append( "vdvenda vd ");
			sql.append( "left outer join  eqtipomov tm on ");
			sql.append( "tm.codemp=vd.codemptm and tm.codfilial=vd.codfilialtm and tm.codtipomov=vd.codtipomov ");
			sql.append( "left outer join  vditvenda iv on ");
			sql.append( "iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and iv.codvenda=vd.codvenda ");
			sql.append( "left outer join eqproduto pd on ");
			sql.append( "pd.codemp=iv.codemppd and pd.codfilial=iv.codfilialpd and pd.codprod=iv.codprod ");
			sql.append( "left outer join lfitvenda lfi on ");
			sql.append( "lfi.codemp=iv.codemp and lfi.codfilial=iv.codfilial and lfi.codvenda=iv.codvenda and lfi.tipovenda=iv.tipovenda and lfi.coditvenda=iv.coditvenda ");
			sql.append( "where ");
			sql.append( "vd.codemp=? and vd.codfilial=? and vd.dtemitvenda between ? AND ? "); 
			sql.append( "and tm.fiscaltipomov='S' ");
			sql.append( "group by 1,2 ");

			sql.append( "union all ");
			sql.append( "select cp.dtemitcompra dtemit, 'E' tipo, sum(lfi.vlrbasepis) vlrbasepis, sum(lfi.vlrbasecofins) vlrbasecofins, ");
			sql.append( "sum(lfi.vlrpis) vlrpis, sum(lfi.vlrcofins) vlrcofins ");
			sql.append( "from ");
			sql.append( "cpcompra cp ");
			sql.append( "left outer join  eqtipomov tm on ");
			sql.append( "tm.codemp=cp.codemptm and tm.codfilial=cp.codfilialtm and tm.codtipomov=cp.codtipomov ");
			sql.append( "left outer join  cpitcompra ic on ");
			sql.append( "ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra ");
			sql.append( "left outer join eqproduto pd on ");
			sql.append( "pd.codemp=ic.codemppd and pd.codfilial=ic.codfilialpd and pd.codprod=ic.codprod ");
			sql.append( "left outer join lfitcompra lfi on ");
			sql.append( "lfi.codemp=ic.codemp and lfi.codfilial=ic.codfilial and lfi.codcompra=ic.codcompra and lfi.coditcompra=ic.coditcompra ");
			sql.append( "where ");
			sql.append( "cp.codemp=? and cp.codfilial=? and cp.dtemitcompra between ? AND ? ");
			sql.append( "and tm.fiscaltipomov='S' ");

			sql.append( "group by 1,2 "); 

			ps = con.prepareStatement( sql.toString() ); 

			int param = 1; 
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, Aplicativo.iCodFilial );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, Aplicativo.iCodFilial );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
							
			rs = ps.executeQuery();

			imprimiGrafico( bVisualizar, rs, sCab.toString() );

			con.commit();

		}  
		catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro ao buscar dados do relatório!" );
		}
	}

	private void imprimiGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );
		
		dlGr = new FPrinterJob( "layout/rel/REL_PIS_COFINS.jasper", "Relatório de PIS/COFINS ", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão do relatório!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
	}
}
