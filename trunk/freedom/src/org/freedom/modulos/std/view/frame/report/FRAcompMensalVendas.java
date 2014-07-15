/**
 * @version 31/10/2013 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.report <BR>
 *         Classe: * @(#)FRAcompMensalVendas.java <BR>
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
 *         Relatório de acompanhamento mensal de vendas
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;

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
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

public class FRAcompMensalVendas extends FRelatorio implements FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtAnoini = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtMesini = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtAnofim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtMesfim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JRadioGroup<?, ?> rgFaturados = null;

	private JRadioGroup<?, ?> rgFinanceiro = null;

	private ListaCampos lcVend = new ListaCampos( this );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private Vector<String> vLabsEmit = new Vector<String>();

	private Vector<String> vValsEmit = new Vector<String>();

	public FRAcompMensalVendas() {

		setTitulo( "Acompanhamento mensal de vendas" );
		setAtribos( 80, 80, 333, 400 );

		Vector<String> vLabs2 = new Vector<String>();
		Vector<String> vVals2 = new Vector<String>();

		vLabs2.addElement( "Faturado" );
		vLabs2.addElement( "Não Faturado" );
		vLabs2.addElement( "Ambos" );
		vVals2.addElement( "S" );
		vVals2.addElement( "N" );
		vVals2.addElement( "A" );
		rgFaturados = new JRadioGroup<String, String>( 3, 1, vLabs2, vVals2 );
		rgFaturados.setVlrString( "S" );

		Vector<String> vLabs3 = new Vector<String>();
		Vector<String> vVals3 = new Vector<String>();

		vLabs3.addElement( "Financeiro" );
		vLabs3.addElement( "Não Financeiro" );
		vLabs3.addElement( "Ambos" );
		vVals3.addElement( "S" );
		vVals3.addElement( "N" );
		vVals3.addElement( "A" );
		rgFinanceiro = new JRadioGroup<String, String>( 3, 1, vLabs3, vVals3 );
		rgFinanceiro.setVlrString( "S" );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		txtCodVend.setTabelaExterna( lcVend, null );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 60, 15, 210, 2 );
		adic( new JLabelPad( "Mês" ), 10, 25, 40, 20 );
		adic( txtMesini, 10, 45, 40, 20 );
		adic( new JLabelPad( "/" ), 54, 45, 5, 20 );
		adic( new JLabelPad( "Ano" ), 64, 25, 40, 20 );
		adic( txtAnoini, 64, 45, 60, 20 );

		adic( new JLabelPad( "Mês" ), 140, 25, 40, 20 );
		adic( txtMesfim, 140, 45, 40, 20 );
		adic( new JLabelPad( "/" ), 184, 45, 5, 20 );
		adic( new JLabelPad( "Ano" ), 194, 25, 40, 20 );
		adic( txtAnofim, 194, 45, 60, 20 );

		adic( new JLabelPad( "De:" ), 10, 70, 97, 20 );
		adic( txtDataini, 32, 70, 97, 20 );

		adic( new JLabelPad( "Até:" ), 140, 70, 100, 20 );
		adic( txtDatafim, 170, 70, 100, 20 );

		adic( txtCodVend, 7, 110, 70, 20, "Cód.comiss." );
		adic( txtDescVend, 80, 110, 190, 20, "Nome do comissionado" );

		adic( txtCodCli, 7, 150, 70, 20, "Cód.Cli" );
		adic( txtNomeCli, 80, 150, 190, 20, "Nome do cliente" );

		adic( rgFaturados, 7, 180, 120, 70 );

		adic( rgFinanceiro, 153, 180, 120, 70 );
		
		btExportXLS.setEnabled( true );
		txtDataini.setEditable( false );
		txtDatafim.setEditable( false );
		
		setParamIni();
		
		txtAnoini.addFocusListener( this );
		txtMesini.addFocusListener( this );
		txtAnofim.addFocusListener( this );
		txtMesfim.addFocusListener( this );
	}

	private StringBuilder getQuerReport( Integer codemp, Integer codfilialcl, Integer codcli
			, Integer codfilialva, Integer codvend, Date dataini, Date datafim, String faturado
			, String financeiro, StringBuilder filtros, Vector<String> meses, TYPE_PRINT visualizar  ) {

		StringBuilder sql = new StringBuilder();
		sql.append( "select c.codcli, c.razcli " );
		for ( int i = 0; i < meses.size(); i++ ) {
			String anomes = meses.elementAt( i );
			String ano = anomes.substring( 0, 4 );
			String mes = anomes.substring( 4 );
			sql.append( ", sum((case when extract(month from v.dtemitvenda)=" );
			sql.append( mes );
			sql.append( " and extract(year from v.dtemitvenda)=" );
			sql.append( ano );
			sql.append( " then v.vlrliqvenda else 0 end)) vlr_" );
			if (visualizar==TYPE_PRINT.EXPORT) {
				sql.append(mes);
				sql.append("_");
				sql.append(ano);
			} else {
				sql.append( i + 1 );
			}
		}
		sql.append( " , sum(v.vlrliqvenda) subtotal " );
		sql.append( " from vdcliente c " );
		sql.append( " inner join vdvenda v on " );
		sql.append( " v.codempcl=c.codemp and v.codfilialcl=c.codfilial" );
		sql.append( " and v.codcli=c.codcli" );
		sql.append( " inner join eqtipomov tm on" );
		sql.append( " tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov" );
		sql.append( " inner join fnplanopag pp on " );
		sql.append( " pp.codemp=v.codemppg and pp.codfilial=v.codfilialpg and pp.codplanopag=v.codplanopag ");
		sql.append( " where c.codemp=? and c.codfilial=?" );
		sql.append( " and v.dtemitvenda between ? and ?" );
		sql.append( " and substring(v.statusvenda from 1 for 1) not in ('C','N') " );
		if ( "S".equalsIgnoreCase( faturado ) ) {
			sql.append( " and tm.fiscaltipomov='S' " );
			filtros.append( ", faturados" );
		}
		else if ( "N".equalsIgnoreCase( faturado ) ) {
			sql.append( " and tm.fiscaltipomov='N' " );
			filtros.append( ", não faturados" );
		}
		if ( "S".equalsIgnoreCase( financeiro ) ) {
			sql.append( " and tm.somavdtipomov='S' and pp.parcplanopag>0 " );
			filtros.append( ", financeiros" );

		}
		else if ( "N".equalsIgnoreCase( financeiro ) ) {
			sql.append( " and tm.somavdtipomov='N' " );
			filtros.append( ", não financeiros" );
		}
		if ( codcli!=0 ) {
			sql.append( " and c.codemp=? and c.codfilial=? and c.codcli=? ");
			filtros.append( ", cód.cliente: " );
			filtros.append( codcli );
		}
		if ( codvend!=0 ) {
			sql.append( " and v.codempvd=? and c.codfilialvd=? and c.codvend=? ");
			filtros.append( ", cód.comissioando: " );
			filtros.append( codvend );
		}
		sql.append( " group by c.codcli, c.razcli" );
		sql.append( " order by c.codcli" );
		return sql;
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final menor que a data inicial!" );
			return;
		}
		Vector<String> meses = Funcoes.getMeses( txtDataini.getVlrDate(), txtDatafim.getVlrDate() );
		if ( meses.size()>12 && bVisualizar!=TYPE_PRINT.EXPORT ) {
			Funcoes.mensagemInforma( this
					, "Não é permitido a visualização ou impressão do relatório com período superior a 12 meses!\n" );
			return;
		}

		try {

			StringBuilder filtros = new StringBuilder();
			filtros.append( "Período de ");
			filtros.append( txtDataini.getVlrString());
			filtros.append( " até " );
			filtros.append( txtDatafim.getVlrString() );

			int codemp = Aplicativo.iCodEmp;
			int codfilialcl = ListaCampos.getMasterFilial( "VDCLIENTE" );
			int codcli = txtCodCli.getVlrInteger();
			int codfilialva = ListaCampos.getMasterFilial( "VDVENDEDOR" );
			int codvend = txtCodVend.getVlrInteger();
			
			StringBuilder sql = getQuerReport( codemp, codfilialcl, codcli
					, codfilialva, codvend	, txtDataini.getVlrDate(), txtDatafim.getVlrDate()
					, rgFaturados.getVlrString(), rgFinanceiro.getVlrString(), filtros, meses, bVisualizar );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilialcl );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			if ( codcli!=0 ) {
				ps.setInt( param++, codemp );
				ps.setInt( param++, codfilialcl );
				ps.setInt( param++, codcli );
			}
			if ( codvend!=0 ) {
				ps.setInt( param++, codemp );
				ps.setInt( param++, codfilialva );
				ps.setInt( param++, codvend );
			}

			ResultSet rs = ps.executeQuery();

			if (bVisualizar==TYPE_PRINT.EXPORT) {
				if (btExportXLS.execute(rs, getTitle())) {
					Funcoes.mensagemInforma( this, "Arquivo exportado com sucesso !" );
				}
			} else {
				imprimirGrafico( bVisualizar, rs, filtros, meses );
			}
			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de vendas!\n" + err.getMessage(), true, con, err );
		} finally {
			System.gc();
		}
	}

	private void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final StringBuilder filtros, Vector<String> meses ) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put( "MESES", meses );
		
		FPrinterJob dlGr = null;

		dlGr = new FPrinterJob( "relatorios/acompmensalvendas.jasper", "Acompanhamento mensal de vendas", filtros.toString(), rs, params, this );

		if ( bVisualizar == TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de resumo diario!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcVend.setConexao( con );
		lcCli.setConexao( con );
	}

	public void focusGained( FocusEvent e ) {

	}

	private void setDataini() {
		
		txtDataini.setVlrDate( Funcoes.encodeDate( txtAnoini.getVlrInteger(), txtMesini.getVlrInteger(), 1 ) );
	}

	private void setParamIni() {
		
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.MONTH, -1 );
		Date datafim = cal.getTime();
		cal.add( Calendar.MONTH, +1 );
		cal.add( Calendar.YEAR, -1 );
		Date dataini = cal.getTime();
		txtMesfim.setVlrInteger( Funcoes.getMes( datafim ) );
		txtAnofim.setVlrInteger( Funcoes.getAno( datafim ) );
		txtMesini.setVlrInteger( Funcoes.getMes( dataini ) );
		txtAnoini.setVlrInteger( Funcoes.getAno( dataini ) );
		setDataini();
		setDatafim();
	}
	
	private void setDatafim() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime( Funcoes.encodeDate( txtAnofim.getVlrInteger(), txtMesfim.getVlrInteger()+1, 1 ) );
		cal.add( Calendar.DAY_OF_MONTH, -1 );
		txtDatafim.setVlrDate( cal.getTime() );
	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource()==txtAnoini || e.getSource()==txtMesini ) {
			setDataini();
		} else if ( e.getSource()==txtAnofim || e.getSource()==txtMesfim ) {
			setDatafim();
		}
		
	}
}
