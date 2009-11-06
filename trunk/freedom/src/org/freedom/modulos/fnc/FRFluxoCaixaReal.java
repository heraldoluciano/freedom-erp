/**
 * @version 06/11/2009 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.fnc <BR>
 *         Classe: @(#)FRFluxoCaixaReal.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Fluxo de Caixa / Realizado.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.Component;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRFluxoCaixaReal extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	
	private boolean bComp = false;
	
	private Component tela = null;
	
	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	public FRFluxoCaixaReal() {

		setTitulo( "Relatório de atendimentos" );
		setAtribos( 80, 80, 350, 300 );
	
		montaTela();
		tela = this; 
		
	}
	
	public void setParametros(Integer codcli, Date dtini, Date dtfim ) {
		txtDataini.setVlrDate( dtini );
		txtDatafim.setVlrDate( dtfim );	
	}


	private void montaTela() {
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Período:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );
		
		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 310, 45 );	
		adic( new JLabelPad("De:"), 15, 25, 20, 20 );
		adic( txtDataini, 38, 25, 95, 20 );
		adic( new JLabelPad("Até:"), 145, 25, 35, 20 );
		adic( txtDatafim, 178, 25, 95, 20 );
		adic( new JLabelPad("Cód.Cli"), 7, 60, 80, 20 );

		Calendar cPeriodo = Calendar.getInstance();
	    txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
		
	}
	
	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		int iparam = 1;		
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( tela, "Data final maior que a data inicial!" );
			return;
		}
		
		
		/*
		 * 
		 * SELECT 'L' TIPO, F.CLASFIN, F.ESFIN, P.CODPLAN, P.DESCPLAN, P.NIVELPLAN,
 (SELECT SUM(SL.VLRSUBLANCA*-1) FROM FNSUBLANCA SL, FNLANCA L
 WHERE SL.CODPLAN LIKE RTRIM(P.CODPLAN)||'%' AND SL.CODLANCA=L.CODLANCA AND
 SL.DATASUBLANCA BETWEEN '01.01.2008' AND '31.01.2008' AND
 SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND L.CODEMP=5 AND L.CODFILIAL=1) VLRSUBLANCA
 FROM FNPLANEJAMENTO P, FNFINALIDADE F WHERE P.TIPOPLAN IN ('R','D') AND
 P.CODEMP=5 AND P.CODFILIAL=1 AND F.CODFIN=P.FINPLAN AND
 EXISTS( SELECT * FROM FNSUBLANCA SL, FNLANCA L
 WHERE SL.CODPLAN LIKE RTRIM(P.CODPLAN)||'%' AND SL.CODLANCA=L.CODLANCA AND
 SL.DATASUBLANCA BETWEEN '01.01.2008' AND '31.01.2008' AND
 SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND L.CODEMP=5 AND L.CODFILIAL=1 )
 ORDER BY 1 DESC, 2 DESC, 3, 4, 5, 6
		 */
		sql.append( "select a.codtpatendo, a.codatend, a.dataatendo, a.dataatendofin, a.codatendo, " );
		sql.append( "a.horaatendo, a.horaatendofin, a.obsatendo, a.codatend, atd.nomeatend, t.desctpatendo, cl.razcli, a.statusatendo " );
		sql.append( "from  atatendimento a, atatendente atd , attipoatendo t, vdcliente cl where " );
		sql.append( "atd.codemp=a.codempae and atd.codfilial=a.codfilialae " );
		sql.append( "and t.codemp=a.codempto and t.codfilial=a.codfilial and t.codtpatendo=a.codtpatendo " );
		sql.append( "and cl.codemp=a.codempcl and cl.codfilial=a.codfilialcl and cl.codcli=a.codcli " );
		sql.append( "and atd.codatend=a.codatend and a.codemp=? and a.codfilial=? " );
		sql.append( "and a.dataatendo between ? and ?  " );
		

		sql.append(" order by a.dataatendo, a.horaatendo ");
		
		try {
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp);
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATATENDIMENTO" ));
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString()));
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString()));
			
			rs = ps.executeQuery();
			
		} 
		catch ( SQLException err ) {
				
			err.printStackTrace();
			Funcoes.mensagemErro( this," Erro na consulta da tabela de atendimentos" );
		}
		
		imprimiGrafico( rs, bVisualizar );
		
	}
	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/fnc/"); 
		hParam.put( "DTINI", txtDataini.getVlrDate());
		hParam.put( "DTFIM", txtDatafim.getVlrDate());
		hParam.put( "CONEXAO", con.getConnection() );
				
		hParam.put( "CLIENTE", "DIVERSOS" );
		dlGr = new FPrinterJob( "relatorios/atendimentos_cli.jasper", "RELATÓRIO DE ATENDIMENTOS", "", rs, hParam,this );	

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( tela, "Erro na impressão de relatório Compras Geral!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcAtend.setConexao( cn );
	}
	
}
