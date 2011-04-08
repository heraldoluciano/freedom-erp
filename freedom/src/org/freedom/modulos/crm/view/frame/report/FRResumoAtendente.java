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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.modulos.atd.view.frame.crud.plain.FAtendente;
import org.freedom.modulos.crm.view.frame.crud.plain.FEspecAtend;

public class FRResumoAtendente extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private ListaCampos lcCli = new ListaCampos( this );

	private JRadioGroup<?, ?> rgTipo = null;
	
	private ListaCampos lcAtendente = new ListaCampos( this, "AE" );
	
	private ListaCampos lcEspecAtend = new ListaCampos( this, "EA" );
	
	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescEspec = new JTextFieldFK( JTextFieldPad.TP_STRING, 1000, 0 );

	
	public FRResumoAtendente() {

		setTitulo( "Resumo por atendente" );
		
		setAtribos( 80, 80, 350	, 310 );

		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();
		
		vLabs1.addElement( "Detalhado" );
		vLabs1.addElement( "Resumido" );
		
		vVals1.addElement( "D" );
		vVals1.addElement( "R" );
		
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
		rgTipo.setVlrString( "R" );
		
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
		adic( lbLinha, 5, 10, 300, 45 );
		
		adic( txtDataini, 38, 25, 95, 20 );		
		adic( txtDatafim, 178, 25, 95, 20 );	
		
		adic (txtCodCli, 7, 85, 80, 20, "Cód.Cli.");
		adic (txtNomeCli, 90, 85, 215, 20, "Nome do cliente");
		
		adic (txtCodAtend, 7, 125, 80, 20, "Cód.Atend.");
		adic (txtNomeAtend, 90, 125, 215, 20, "Nome do atendente");
		
		adic (txtCodEspec, 7, 165, 80, 20, "Cód.Espec.");
		adic (txtDescEspec, 90, 165, 215, 20, "Descrição da especificação");
		
		adic( rgTipo, 7, 200, 300, 30 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
	
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

	}

	private void montaListaCampos() {
		
		txtCodAtend.setTabelaExterna( lcAtendente, FAtendente.class.getCanonicalName() );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtendente.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ) );
		lcAtendente.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtendente.montaSql( false, "ATENDENTE", "AT" );
		lcAtendente.setReadOnly( true );
		
		txtCodCli.setTabelaExterna( lcCli, FAtendente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.Cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Nome do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		
		txtCodEspec.setTabelaExterna( lcEspecAtend, FEspecAtend.class.getCanonicalName() );
		txtCodEspec.setFK( true );
		txtCodEspec.setNomeCampo( "CodEspec" );
		lcEspecAtend.add( new GuardaCampo( txtCodEspec, "CodEspec", "Cód.Espec.", ListaCampos.DB_PK, false ) );
		lcEspecAtend.add( new GuardaCampo( txtDescEspec, "DescEspec", "Descrição da especificação", ListaCampos.DB_SI, false ) );
		lcEspecAtend.montaSql( false, "ESPECATEND", "AT" );
		lcEspecAtend.setReadOnly( true );


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
		
		if("R".equals( rgTipo.getVlrString() )) {
		
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
			sql.append( "(sum( (case when a.cobcliespec='S' and a.statusatendo<>'NC' then (case when a.totalmin<a.tempomincobespec ");
			sql.append( "then a.tempomincobespec else ");
			sql.append( "(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 ");
			sql.append( "then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) ");
			sql.append( ")/60 ) totalcobcli ");
			sql.append( "from atatendimentovw01 a, atatendimento atd, vdcliente cl ");
			
			sql.append( "where ");
			
			sql.append( "atd.codemp=a.codemp and atd.codfilial=a.codfilial and atd.codatendo=a.codatendo and ");
			sql.append( "cl.codemp=atd.codempcl and cl.codfilial=atd.codfilialcl and cl.codcli=atd.codcli and ");

			sql.append( "a.codemp=? and a.codfilial=? and a.dataatendo between ? and ? ");

			if(txtCodCli.getVlrInteger()>0) {
			
				sql.append( "and atd.codempcl=? and atd.codfilialcl=? and atd.codcli=? " );
				
			}
			
			if(txtCodAtend.getVlrInteger()>0) {
				
				sql.append( "and atd.codempae=? and atd.codfilialae=? and atd.codatend=? " );
				
			}
			
			if(txtCodEspec.getVlrInteger()>0) {
				
				sql.append( "and atd.codempea=? and atd.codfilialea=? and atd.codespec=? " );
				
			}
						
			sql.append( "group by a.nomeatend;" );
			
		}
		else {
			
			if( ! (txtCodAtend.getVlrInteger() > 0) ) {
				
				Funcoes.mensagemInforma( this, "Informe um atendente!" );
				txtCodAtend.requestFocus();
				return;
				
			}
			
			sql.append( "select atd.dataatendo, atd.horaatendo, atd.horaatendofin, a.nomeatend, atd.obsatendo, atd.codcli, cl.nomecli, (a.totalmin) / 60  totalgeral, ");
			sql.append( "(( (case when a.contmetaespec='S' then (case when ");
			sql.append( "a.totalmin<a.tempomincobespec ");
			sql.append( "then a.tempomincobespec else ");
			sql.append( "(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 ");
			sql.append( "then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) ");
			sql.append( ")/60 ) totalmeta, ");
			sql.append( "(( (case when a.pgcomiespec='S' then (case when a.totalmin<a.tempomincobespec ");
			sql.append( "then a.tempomincobespec else ");
			sql.append( "(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 ");
			sql.append( "then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) ");
			sql.append( ")/60 ) totalcomis, ");
			sql.append( "(( (case when a.cobcliespec='S' and a.statusatendo<>'NC' then (case when a.totalmin<a.tempomincobespec ");
			sql.append( "then a.tempomincobespec else ");
			sql.append( "(case when a.totalmin>a.tempomaxcobespec and a.tempomaxcobespec<>0 ");
			sql.append( "then a.tempomaxcobespec else a.totalmin end) end)  else 0 end) ");
			sql.append( ")/60 ) totalcobcli, ");
			sql.append( "ea.codespec, ea.descespec " );
			sql.append( "from atatendimentovw01 a, vdcliente cl, atatendimento atd ");
			sql.append( "left outer join atespecatend ea on ea.codemp=atd.codempea and ea.codfilial=atd.codfilialea and ea.codespec=atd.codespec ");
			
			sql.append( "where ");
			
			sql.append( "atd.codemp=a.codemp and atd.codfilial=a.codfilial and atd.codatendo=a.codatendo and ");
			sql.append( "cl.codemp=atd.codempcl and cl.codfilial=atd.codfilialcl and cl.codcli=atd.codcli and ");
			sql.append( "a.codemp=? and a.codfilial=? and a.dataatendo between ? and ? ");
			 
			if(txtCodCli.getVlrInteger()>0) {
				
				sql.append( "and atd.codempcl=? and atd.codfilialcl=? and atd.codcli=? " );
				
			}
			
			if(txtCodAtend.getVlrInteger()>0) {
				
				sql.append( "and atd.codempae=? and atd.codfilialae=? and atd.codatend=? " );
				
			}
			
			if(txtCodEspec.getVlrInteger()>0) {
				
				sql.append( "and atd.codempea=? and atd.codfilialea=? and atd.codespec=? " );
				
			}
			
			
			
			
			
			sql.append( "order by atd.dataatendo, atd.horaatendo ");
			
		}
		
		
		System.out.println( "SQL:" + sql.toString() );

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			
			if(txtCodCli.getVlrInteger()>0) {
				
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );
				
			}
			
			if(txtCodAtend.getVlrInteger()>0) {
				
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATATENDENTE" ) );
				ps.setInt( iparam++, txtCodAtend.getVlrInteger() );
				
			}
			
			if(txtCodEspec.getVlrInteger()>0) {
				
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATESPECATEND" ) );
				ps.setInt( iparam++, txtCodEspec.getVlrInteger() );
				
			}
			
			rs = ps.executeQuery();
		
		} 
		catch ( SQLException err ) {

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

		if("R".equals( rgTipo.getVlrString() )) {
			dlGr = new FPrinterJob( "layout/rel/REL_CRM_RESUMO_ATENDENTE_01.jasper", "Resumo de atendimentos por atendente (Resumido)", "", rs, hParam, this );
		}
		else {
			dlGr = new FPrinterJob( "layout/rel/REL_CRM_DETALHAMENTO_ATENDENTE_01.jasper", "Resumo de atendimentos por atendente (Detalhado)", "", rs, hParam, this );
		}

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
		lcEspecAtend.setConexao( cn );
		
	}

}
