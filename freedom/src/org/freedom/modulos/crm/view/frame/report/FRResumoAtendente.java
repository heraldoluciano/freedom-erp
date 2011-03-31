/**
 * @version 30/03/2011 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FRDiario.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Relatório resumido de atendimentos por atendente
 * 
 */

package org.freedom.modulos.crm.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
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

public class FRResumoAtendente extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcAtendente = new ListaCampos( this, "AE" );

	public FRResumoAtendente() {

		setTitulo( "Resumo de por atendente" );
		setAtribos( 80, 80, 350	, 160 );

		montaListaCampos();
		montaTela();


	}

	public void setParametros( Integer codcli, Date dtini, Date dtfim ) {

		txtDataini.setVlrDate( dtini );
		txtDatafim.setVlrDate( dtfim );
		
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Período:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 310, 45 );
		
		adic( txtDataini, 38, 25, 95, 20, "De:" );		
		adic( txtDatafim, 178, 25, 95, 20, "Até:" );		

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
	
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

	}

	private void montaListaCampos() {

	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iparam = 1;

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		sql.append( "select a.nomeatend, ( sum(a.totalmin) / 60 ) totalgeral, ");
		sql.append( "(sum( (case when a.contmetaespec='S' then (case when ");
		sql.append( "a.totalmin<a.tempomincobespec ");
		sql.append( "then a.tempomincobespec else ");
		sql.append( "(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 ");
		sql.append( "then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) ");
		sql.append( ")/60 ) totalmeta, ");
		sql.append( "(sum( (case when a.pgcomiespec='S' then (case when a.totalmin<a.tempomincobespec ");
		sql.append( "then a.tempomincobespec else ");
		sql.append( "(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 ");
		sql.append( "then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) ");
		sql.append( ")/60 ) totalcomis, ");
		sql.append( "(sum( (case when a.cobcliespec='S' then (case when a.totalmin<a.tempomincobespec ");
		sql.append( "then a.tempomincobespec else ");
		sql.append( "(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 ");
		sql.append( "then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) ");
		sql.append( ")/60 ) totalcobcli ");
		sql.append( "from atatendimentovw01 a ");
		sql.append( "where a.codemp=? and a.codfilial=? and ");
		sql.append( "a.dataatendo between ? and ? ");
		sql.append( "group by a.nomeatend;" );

		System.out.println( "SQL:" + sql.toString() );

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );

			rs = ps.executeQuery();

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, " Erro na consulta da view de resumo de atendimentos" );
		}

		imprimiGrafico( rs, bVisualizar );

	}

	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/" );
		hParam.put( "DTINI", txtDataini.getVlrDate() );
		hParam.put( "DTFIM", txtDatafim.getVlrDate() );
		hParam.put( "CONEXAO", con.getConnection() );

		dlGr = new FPrinterJob( "layout/rel/REL_CRM_RESUMO_ATENDENTE_01.jasper", "Resumo de atendimentos por atendente", "", rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório resumo de atendimentos!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcAtendente.setConexao( cn );
	}

}
