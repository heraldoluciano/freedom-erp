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

public class FREvolucaoAnualVendas extends FRelatorio implements FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtAnoini = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtMesini = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtAnofim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtMesfim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtDataini_01 = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim_01 = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDataini_02 = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim_02 = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDataini_03 = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim_03 = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

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

	public FREvolucaoAnualVendas() {

		setTitulo( "Evolução anual de vendas" );
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
		txtDataini_03.setVlrDate( new Date() );
		txtDatafim_03.setVlrDate( new Date() );
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
		adic( txtDataini_03, 32, 70, 97, 20 );
		adic( new JLabelPad( "Até:" ), 140, 70, 100, 20 );
		adic( txtDatafim_03, 170, 70, 100, 20 );
		adic( new JLabelPad( "De:" ), 10, 110, 97, 20 );
		adic( txtDataini_02, 32, 110, 97, 20 );
		adic( new JLabelPad( "Até:" ), 140, 110, 100, 20 );
		adic( txtDatafim_02, 170, 110, 100, 20 );
		adic( new JLabelPad( "De:" ), 10, 150, 97, 20 );
		adic( txtDataini_01, 32, 150, 97, 20 );
		adic( new JLabelPad( "Até:" ), 140, 150, 100, 20 );
		adic( txtDatafim_01, 170, 150, 100, 20 );
		adic( txtCodVend, 7, 190, 70, 20, "Cód.comiss." );
		adic( txtDescVend, 80, 190, 190, 20, "Nome do comissionado" );
		adic( txtCodCli, 7, 230, 70, 20, "Cód.Cli" );
		adic( txtNomeCli, 80, 230, 190, 20, "Nome do cliente" );
		adic( rgFaturados, 7, 280, 120, 70 );
		adic( rgFinanceiro, 153, 280, 120, 70 );
		
		btExportXLS.setEnabled( true );
		txtDataini_01.setEditable( false );
		txtDatafim_01.setEditable( false );
		txtDataini_02.setEditable( false );
		txtDatafim_02.setEditable( false );
		txtDataini_03.setEditable( false );
		txtDatafim_03.setEditable( false );
		
		setParamIni();
		
		txtAnoini.addFocusListener( this );
		txtMesini.addFocusListener( this );
		txtAnofim.addFocusListener( this );
		txtMesfim.addFocusListener( this );
	}

	private StringBuilder getQuerReport( Integer codcli, Integer codvend, String faturado
			, String financeiro, StringBuilder filtros
			, Integer ano_01, Integer ano_02, Integer ano_03, TYPE_PRINT visualizar  ) {
        int num_anos = 0;
        if (ano_01>0) {
        	num_anos++;
        }
        if (ano_02>0) {
        	num_anos++;
        }
        if (ano_03>0) {
        	num_anos++;
        }
		StringBuilder sql = new StringBuilder();
		sql.append( "select p.descprod, p.codprod, p.refprod " );
		sql.append( ", sum( case when extract(year from v.dtemitvenda)=");
		sql.append( ano_01 );
		sql.append( " then iv.qtditvenda else 0 end) ano_01 " );
		sql.append( ", sum( case when extract(year from v.dtemitvenda)=");
		sql.append( ano_02 ); 
		sql.append(" then iv.qtditvenda else 0 end) ano_02 " );
		sql.append( ", sum( case when extract(year from v.dtemitvenda)=");
		sql.append( ano_03 );
		sql.append(" then iv.qtditvenda else 0 end) ano_03 " );
		sql.append( ", sum(iv.qtditvenda)/");
		sql.append( num_anos );
		sql.append( " media " );
		sql.append( "from eqproduto p " );
		sql.append( "inner join vditvenda iv on " );
		sql.append( "iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and iv.codprod=p.codprod " );
		sql.append( "inner join vdvenda v on " );
		sql.append( "v.codemp=iv.codemp and v.codfilial=iv.codfilial and v.tipovenda=iv.tipovenda and v.codvenda=iv.codvenda " );
		sql.append( "inner join vdcliente c on " );
		sql.append( "c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli " );
		sql.append( "inner join eqtipomov tm on " );
		sql.append( "tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov " );
		sql.append( "inner join fnplanopag pp on " );
		sql.append( "pp.codemp=v.codemppg and pp.codfilial=v.codfilialpg and pp.codplanopag=v.codplanopag " );
		sql.append( "where p.codemp=? and p.codfilial=? " );
		sql.append( "and v.codemp=? and v.codfilial=? " );
		sql.append( "and  ( ( v.dtemitvenda between ? and ? ) " );
		sql.append( "or ( v.dtemitvenda between ? and ? ) " );
		sql.append( "or ( v.dtemitvenda between ? and ? ) ) " );
		sql.append( "and substring(v.statusvenda from 1 for 1)<>'C' " );
		sql.append( "and pp.parcplanopag>0 " );
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
		sql.append( "group by 1, 2, 3 " );
		sql.append( "order by 1, 2, 3 " );
		return sql;
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim_03.getVlrDate().before( txtDataini_03.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final menor que a data inicial!" );
			return;
		}
		if ( Funcoes.getAno(  txtDatafim_03.getVlrDate() )!= Funcoes.getAno( txtDataini_03.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Período deve ficar dentro do mesmo ano !" );
			return;
		}
		Vector<String> meses = Funcoes.getMeses( txtDataini_03.getVlrDate(), txtDatafim_03.getVlrDate() );
		if ( meses.size()>12 && bVisualizar!=TYPE_PRINT.EXPORT ) {
			Funcoes.mensagemInforma( this
					, "Não é permitido a visualização ou impressão do relatório com período superior a 12 meses!\n" );
			return;
		}
		try {
			StringBuilder filtros = new StringBuilder();
			filtros.append( "Períodos de ");
			filtros.append( txtDataini_03.getVlrString());
			filtros.append( " até " );
			filtros.append( txtDatafim_03.getVlrString() );
			filtros.append( " - de ");
			filtros.append( txtDataini_02.getVlrString());
			filtros.append( " até " );
			filtros.append( txtDatafim_02.getVlrString() );
			filtros.append( " - de ");
			filtros.append( txtDataini_01.getVlrString());
			filtros.append( " até " );
			filtros.append( txtDatafim_01.getVlrString() );
			int codemp = Aplicativo.iCodEmp;
			int codfilialpd = ListaCampos.getMasterFilial( "EQPRODUTO" );
			int codfilialcl = ListaCampos.getMasterFilial( "VDCLIENTE" );
			int codcli = txtCodCli.getVlrInteger();
			int codfilialvd = ListaCampos.getMasterFilial( "VDVENDA" );
			int codfilialva = ListaCampos.getMasterFilial( "VDVENDEDOR" );
			int codvend = txtCodVend.getVlrInteger();
			Date dataini_01 = txtDataini_01.getVlrDate();
			Date dataini_02 = txtDataini_02.getVlrDate();
			Date dataini_03 = txtDataini_03.getVlrDate();
			Date datafim_01 = txtDatafim_01.getVlrDate();
			Date datafim_02 = txtDatafim_02.getVlrDate();
			Date datafim_03 = txtDatafim_03.getVlrDate();
			Integer ano_01 = Funcoes.getAno( dataini_01 );
			Integer ano_02 = Funcoes.getAno( dataini_02 );
			Integer ano_03 = Funcoes.getAno( dataini_03 );
			StringBuilder cabmeses = new StringBuilder();
			cabmeses.append( "DE ");
			cabmeses.append( Funcoes.getMesExtenso( dataini_03 ).toUpperCase());
			cabmeses.append( " ATÉ " );
			cabmeses.append( Funcoes.getMesExtenso( datafim_03 ).toUpperCase());
			StringBuilder sql = getQuerReport( codcli , codvend	, rgFaturados.getVlrString(), rgFinanceiro.getVlrString(), filtros
					, ano_01, ano_02, ano_03, bVisualizar );
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilialpd );
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilialvd );
			ps.setDate( param++, Funcoes.dateToSQLDate( dataini_01 ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( datafim_01) );
			ps.setDate( param++, Funcoes.dateToSQLDate( dataini_02 ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( datafim_02) );
			ps.setDate( param++, Funcoes.dateToSQLDate( datafim_03) );
			ps.setDate( param++, Funcoes.dateToSQLDate( datafim_03) );
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
				imprimirGrafico( bVisualizar, rs, filtros, ano_01, ano_02, ano_03, cabmeses );
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

	private void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final StringBuilder filtros
			, final Integer ano_01, final Integer ano_02, final Integer ano_03, final StringBuilder cabmeses ) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put( "ANO_01", ano_01 );
		params.put( "ANO_02", ano_02 );
		params.put( "ANO_03", ano_03 );
		params.put( "CABMESES", cabmeses.toString() );
		
		FPrinterJob dlGr = null;
		try {
			dlGr = new FPrinterJob( "relatorios/evolucaoanualvendas.jasper", "Evolução anual de vendas", filtros.toString(), rs, params, this );
			if ( bVisualizar == TYPE_PRINT.VIEW ) {
				dlGr.preview();
			}
			else {
				dlGr.print(true);
			}
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro na impressão de relatório!" + err.getMessage(), true, con, err );
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
		Date dataini_03 = Funcoes.encodeDate( txtAnoini.getVlrInteger(), txtMesini.getVlrInteger(), 1 );
		txtDataini_03.setVlrDate( dataini_03  );
		Calendar cal = Calendar.getInstance();
		cal.setTime( dataini_03 );
		cal.add( Calendar.YEAR, -1 );
		txtDataini_02.setVlrDate( cal.getTime() );
		cal.add( Calendar.YEAR, -1 );
		txtDataini_01.setVlrDate( cal.getTime() );
		
	}

	private void setParamIni() {
		
		Calendar cal = Calendar.getInstance();
		Date datafim = cal.getTime();
		cal.add( Calendar.MONTH, -1 );
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
		txtDatafim_03.setVlrDate( cal.getTime() );
		cal.add( Calendar.YEAR, -1 );
		txtDatafim_02.setVlrDate( cal.getTime() );
		cal.add( Calendar.YEAR, -1 );
		txtDatafim_01.setVlrDate( cal.getTime() );

	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource()==txtAnoini || e.getSource()==txtMesini ) {
			setDataini();
		} else if ( e.getSource()==txtAnofim || e.getSource()==txtMesfim ) {
			setDatafim();
		}
		
	}
}
