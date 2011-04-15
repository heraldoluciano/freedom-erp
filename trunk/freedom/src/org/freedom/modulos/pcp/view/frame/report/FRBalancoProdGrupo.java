/**
 * @version 22/02/2011 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp <BR>
 *         Classe:
 * @(#)FRConsumoMat.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela para filtros do relatório de consumo de matéria prima por seção.
 * 
 */

package org.freedom.modulos.pcp.view.frame.report;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.swing.util.SwingParams;


public class FRBalancoProdGrupo extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private boolean comref = false;

	boolean cliente = false;

	boolean diario = false;
	
	private ListaCampos lcGrupo = new ListaCampos( this );
	
	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDescSecao = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JCheckBoxPad cbPorFolha = new JCheckBoxPad( "Por folhas (FSC)", "S", "N" );

	public FRBalancoProdGrupo() {

	setTitulo( "Relatório de Balanço de produção por Grupo FSC" );
		
		setAtribos( 80, 80, 370, 250 );

		lcGrupo.add( new GuardaCampo( txtCodGrup, "CodGrup", "Cód.Grupo", ListaCampos.DB_PK, false ) );
		lcGrupo.add( new GuardaCampo( txtDescSecao, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		lcGrupo.montaSql( false, "GRUPO", "EQ" );
		lcGrupo.setReadOnly( true );
		txtCodGrup.setTabelaExterna( lcGrupo, null );
		txtCodGrup.setFK( true );
		txtCodGrup.setNomeCampo( "CodGrup" );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		cbPorFolha.setVlrString( "S" );
		
		JPanelPad pnPeriodo = new JPanelPad();
		pnPeriodo.setBorder( SwingParams.getPanelLabel( "Período", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnPeriodo, 4, 5, 335, 60 );

		pnPeriodo.adic( new JLabelPad( "De:" ), 5, 05, 30, 20 );
		pnPeriodo.adic( txtDataini, 35, 05, 90, 20 );
		pnPeriodo.adic( new JLabelPad( "Até:" ), 135, 05, 30, 20 );
		pnPeriodo.adic( txtDatafim, 170, 05, 90, 20 );

		JPanelPad pnFiltros = new JPanelPad();
		pnFiltros.setBorder( SwingParams.getPanelLabel( "Filtros", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnFiltros, 4, 70, 335, 85 );

		pnFiltros.adic( txtCodGrup, 4, 25, 120, 20, "Cód.Grupo" );
		pnFiltros.adic( txtDescSecao, 127, 25, 185, 20, "Descrição do grupo" );

		adic(cbPorFolha, 7, 165, 200, 20);
		

	}

	public void imprimir( boolean visualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sCab2 = new StringBuffer();

		int param = 1;

		try {


			if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
				return;
			}

			
			sql.append( "select ");
			sql.append( "sc.codgrup, sc.descgrup, ");
			
			sql.append( "sum(( ");
			sql.append( "select sum(ir.qtdexpitrma) from ppop op, eqrma rm, eqitrma ir ");
			sql.append( "where rm.codempof=op.codemp and rm.codfilialof=op.codfilial and ");
			sql.append( "rm.codop=op.codop and rm.seqop=op.seqop ");
			sql.append( "and ir.codemp=rm.codemp and ir.codfilial=rm.codfilial and ir.codrma=rm.codrma ");
			sql.append( "and pe.codemp=ir.codemppd and pe.codfilial=ir.codfilialpd and ");
			sql.append( "pe.codprod=ir.codprod ");
			sql.append( "and pe.nroplanos is not null and pe.qtdporplano is not null ");
			sql.append( "and op.dtfabrop between ? and ? ");
			sql.append( ")) consumidas, ");
			
			if("S".equals( cbPorFolha.getVlrString())) {

				sql.append( "sum(( select sum( coalesce(ope.qtdent, op.qtdfinalprodop) / (pd.nroplanos*pd.qtdporplano) * coalesce(pd.fatorfsc,1.00) ) ");
				sql.append( "from ppop op ");
				sql.append( "left outer join ppopentrada ope on ope.codemp=op.codemp and ope.codfilial=op.codfilial and ope.codop=op.codop ");
				sql.append( "and ope.seqop=op.seqop ");
				sql.append( "left outer join eqproduto pd on pd.codprod=pe.codprod and pd.codfilial=pe.codfilial and pd.codprod=pe.codprod ");
				sql.append( "where op.codemppd=pd.codemp and op.codfilialpd=pd.codfilial and op.codprod=pd.codprod and op.dtfabrop between ? and ? ) ) produzidas,"); 

			}
			else {

				sql.append( "sum(( select sum( coalesce(ope.qtdent, op.qtdfinalprodop)  ) from ppop op ");
				sql.append( "left outer join ppopentrada ope ");
				sql.append( "on ope.codemp=op.codemp and ope.codfilial=op.codfilial and ");
				sql.append( "ope.codop=op.codop and ope.seqop=op.seqop ");
				sql.append( "where op.codemppd=pe.codemp and op.codfilialpd=pe.codfilial and ");
				sql.append( "op.codprod=pe.codprod ");
				sql.append( "and op.dtfabrop between ? and ? ");
				sql.append( ")) produzidas, ");

			}
			
			sql.append( "coalesce(sum( ");
			sql.append( "(select first 1 m.sldmovprod ");
			sql.append( "from eqmovprod m, eqproduto ps ");
			sql.append( "where m.codemppd=ps.codemp and ");
			sql.append( "m.codfilial=ps.codfilial and ");
			sql.append( "m.codprod=ps.codprod and ");
			sql.append( "m.dtmovprod<=? ");
			sql.append( "and ps.codemp=pe.codemp and ps.codfilial=pe.codfilial and ps.codprod=pe.codprod ");
			sql.append( "and ps.tipoprod in ('F','05','06') ");
			sql.append( "order by m.codprod, m.dtmovprod desc, m.codmovprod desc "); 
			sql.append( " ) ");
			sql.append( " ),0) saldoanterior, ");
			
			if("S".equals( cbPorFolha.getVlrString())) {
			
				sql.append( "sum( ( select sum(iv.qtditvenda) / (pe.nroplanos*pe.qtdporplano ) * coalesce(pe.fatorfsc,1.00)  from vditvenda iv, vdvenda v ");
			
			}
			else {
			
				sql.append( "sum( (select sum(iv.qtditvenda) from vditvenda iv, vdvenda v ");
			
			}
			
			sql.append( "where v.codemp=? and v.codfilial=? ");
			sql.append( "and v.dtemitvenda between ? and ? ");
			sql.append( "and iv.codemp=v.codemp and iv.codfilial=v.codfilial and ");
			sql.append( "iv.tipovenda=v.tipovenda and iv.codvenda=v.codvenda ");
			sql.append( "and iv.codemppd=pe.codemp and iv.codfilialpd=pe.codfilial and ");
			sql.append( "iv.codprod=pe.codprod ");
			sql.append( ")) vendidas ");
			
			
			sql.append( "from eqgrupo sc, eqproduto pe ");
			sql.append( "where sc.codemp=pe.codempgp and sc.codfilial=pe.codfilialgp and sc.codgrup=pe.codgrup ");
			
			if ( !"".equals( txtCodGrup.getVlrString() ) ) {
				sql.append( "and pe.codempgp=? and pe.codfilialgp=? and pe.codgrup=? " );
			}

			sql.append( "group by sc.codgrup, sc.descgrup ");
					
			if("S".equals( cbPorFolha.getVlrString())) {
				sql.append(",pe.nroplanos, pe.qtdporplano, pe.fatorfsc ");
			}						
			
			System.out.println("SQL:" + sql.toString());

			ps = con.prepareStatement( sql.toString() );

			Date dtant = txtDataini.getVlrDate();
			Calendar cant = new GregorianCalendar();
			cant.setTime( dtant );
			cant.add( Calendar.DAY_OF_YEAR, -1 ); 
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( cant.getTime()) );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			sCab.append( "Período de " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " até " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );

			if ( !"".equals( txtCodGrup.getVlrString() ) ) {
				ps.setInt( param++, lcGrupo.getCodEmp() );
				ps.setInt( param++, lcGrupo.getCodFilial() );
				ps.setString( param++, txtCodGrup.getVlrString() );

				sCab2.append( "Seção: " + txtDescSecao.getVlrString() );
			}
			
			rs = ps.executeQuery();

			imprimirGrafico( visualizar, rs, sCab.toString() + "\n" + sCab2.toString(), comref, "layout/rel/REL_FSC_BALANCO_02.jasper" );

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		} finally {
			System.gc();
		}
	}
	
	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab, final boolean bComRef , String rel ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();
	//	hParam.put( "COMREF", bComRef ? "S" : "N" );

		FPrinterJob dlGr = null;
	
		dlGr = new FPrinterJob( rel, "Relatório Balanço de Produção por grupo (FSC) ", sCab, rs, hParam, this );
		

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de consumo!" + err.getMessage(), true, con, err );
			}
		}
	}

	private boolean comRef() {

		boolean bRetorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				bRetorno = "S".equals( rs.getString( "UsaRefProd" ) );
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}
		return bRetorno;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcGrupo.setConexao( cn );

		comref = comRef();
	}
}
