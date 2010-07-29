/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRVendasItem.java <BR>
 * 
 *                       Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                       modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                       na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                       Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                       sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                       Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                       Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                       de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                       Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.gms.view.frame.report;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

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

public class FRFreteRecMerc extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcTransp = new ListaCampos( this );

	boolean cliente = false;

	boolean diario = false;
	
	private JCheckBoxPad cbPendentes = new JCheckBoxPad( "Pendentes", "S", "N" );

	private JCheckBoxPad cbEmPagamento = new JCheckBoxPad( "Em pagamento", "S", "N" );

	private JCheckBoxPad cbPagos = new JCheckBoxPad( "Pagas", "S", "N" );


	public FRFreteRecMerc() {

		setTitulo( "Fretes de recebimento de mercadorias" );
		setAtribos( 80, 80, 380, 280 );

		txtNomeTran.setAtivo( false );

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		lcTransp.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.Tran.", ListaCampos.DB_PK, false ) );
		lcTransp.add( new GuardaCampo( txtNomeTran, "NomeTran", "Nome do transportador", ListaCampos.DB_SI, false ) );
		txtCodTran.setTabelaExterna( lcTransp, null );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );
		lcTransp.setReadOnly( true );
		lcTransp.montaSql( false, "TRANSP", "VD" );

		JPanelPad pnPeriodo = new JPanelPad();
		pnPeriodo.setBorder( SwingParams.getPanelLabel( "Período", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnPeriodo, 4, 5, 325, 60 );

		pnPeriodo.adic( new JLabelPad( "De:" ), 5, 05, 30, 20 );
		pnPeriodo.adic( txtDataini, 35, 05, 90, 20 );
		pnPeriodo.adic( new JLabelPad( "Até:" ), 135, 05, 30, 20 );
		pnPeriodo.adic( txtDatafim, 170, 05, 90, 20 );
		
		JPanelPad pnFiltros = new JPanelPad();
		pnFiltros.setBorder( SwingParams.getPanelLabel( "Filtros", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnFiltros, 4, 75, 325, 120 );

		pnFiltros.adic( new JLabelPad( "Cód.Tran." ), 4, 5, 70, 20 );
		pnFiltros.adic( txtCodTran, 4, 25, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Nome do transportador" ), 77, 5, 230, 20 );
		pnFiltros.adic( txtNomeTran, 77, 25, 230, 20 );
		
		pnFiltros.adic( cbPendentes, 7, 55, 90, 20 );
		pnFiltros.adic( cbEmPagamento, 100, 55, 120, 20 );
		pnFiltros.adic( cbPagos, 230, 55, 65, 20 );
		
		cbPendentes.setVlrString( "S" );
		cbEmPagamento.setVlrString( "N" );
		cbPagos.setVlrString( "N" );

	}

	public void imprimir( boolean visualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuffer sCab = new StringBuffer();		

		int param = 1;

		sql.append( "select ");
		sql.append( "rm.codtran, tr.nometran, rm.placaveiculo, rm.dtent, rm.codbairro, br.nomebairro, rm.ticket, br.vlrfrete preco, ");
		sql.append( "fr.pesoliquido, fr.vlrfrete, ");

		sql.append( "(select p.vlrpagopag from FNPAGAR p ");
		sql.append( "where p.codemp=fr.codemppa and p.codfilial=fr.codfilialpa and p.codpag=fr.codpag and ");
		sql.append( "(select sum(ip1.nparcpag) from fnitpagar ip1 ");
		sql.append( "where ip1.codemp=p.codemp and ip1.codfilial=p.codfilial and ip1.codpag=p.codpag) = ");
		sql.append( "(select sum(ip1.nparcpag) from fnitpagar ip1 ");
		sql.append( "where ip1.codemp=p.codemp and ip1.codfilial=p.codfilial and ip1.codpag=p.codpag and ");
		sql.append( "ip1.statusitpag='PP')) as pago, ");

		sql.append( "(select p.vlrpagopag from FNPAGAR p ");
		sql.append( "where p.codemp=fr.codemppa and p.codfilial=fr.codfilialpa and p.codpag=fr.codpag) as emPagamento ");

		sql.append( "from ");
		sql.append( "eqrecmerc rm ");
		sql.append( "left outer join vdtransp tr on ");
		sql.append( "tr.codemp=rm.codemptn and tr.codfilial=rm.codfilialtn and tr.codtran=rm.codtran ");
		sql.append( "left outer join sgbairro br on ");
		sql.append( "br.codpais=rm.codpais and br.siglauf=rm.siglauf and br.codmunic=rm.codmunic and br.codbairro=rm.codbairro ");
		sql.append( "right outer join lffrete fr on ");
		sql.append( "fr.codemprm=rm.codemp and fr.codfilialrm=rm.codfilial and fr.ticket=rm.ticket ");

		sql.append( "where ");

		sql.append( " rm.codemp=? and rm.codfilial=? and rm.dtent between ? and ? ");

		if ( txtCodTran.getVlrInteger() > 0 ) {
			sql.append( "and rm.codemptn=? and rm.codfilialtn=? and rm.codtran=? " );
		}
		
		StringBuilder where = new StringBuilder();
		
		if ( "S".equals( cbPendentes.getVlrString() ) ) {
			where.append( " fr.codpag is null" );
		}
		if ( "S".equals( cbPagos.getVlrString() ) ) {
			if ( where.length() > 0 ) {
				where.append( " or" );
			}
			where.append( " (select p.codpag from FNPAGAR p" );
			where.append( "  where p.codemp=fr.codemppa and p.codfilial=fr.codfilialpa and p.codpag=fr.codpag and" );
			where.append( "  (select sum(ip1.nparcpag) from fnitpagar ip1" );
			where.append( "   where ip1.codemp=p.codemp and ip1.codfilial=p.codfilial and ip1.codpag=p.codpag) =" );
			where.append( "  (select sum(ip1.nparcpag) from fnitpagar ip1" );
			where.append( "   where ip1.codemp=p.codemp and ip1.codfilial=p.codfilial and ip1.codpag=p.codpag and" );
			where.append( "   ip1.statusitpag='PP')) is not null " );
		}
		if ( "S".equals( cbEmPagamento.getVlrString() ) ) {
			if ( where.length() > 0 ) {
				where.append( " or" );
			}
			where.append( " (fr.codpag is not null and" );
			where.append( " (select p.codpag from FNPAGAR p" );
			where.append( "  where p.codemp=fr.codemppa and p.codfilial=fr.codfilialpa and p.codpag=fr.codpag and" );
			where.append( "  (select sum(ip1.nparcpag) from fnitpagar ip1" );
			where.append( "   where ip1.codemp=p.codemp and ip1.codfilial=p.codfilial and ip1.codpag=p.codpag) =" );
			where.append( "  (select sum(ip1.nparcpag) from fnitpagar ip1" );
			where.append( "   where ip1.codemp=p.codemp and ip1.codfilial=p.codfilial and ip1.codpag=p.codpag and" );
			where.append( "   ip1.statusitpag='PP')) is null) " );
		}

		sql.append( where.length() > 0 ? ( " and (" + where.toString() + ")" ) : "" );

		sql.append( " order by rm.dtent,rm.ticket " );

		try {

			System.out.println( "SQL:" + sql.toString() );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( txtCodTran.getVlrInteger() > 0 ) {
				ps.setInt( param++, lcTransp.getCodEmp() );
				ps.setInt( param++, lcTransp.getCodFilial() );
				ps.setInt( param++, txtCodTran.getVlrInteger() );

				sCab.append( "Transportador: " + txtNomeTran.getVlrString() + "\n" );
			}

			System.out.println( "SQL:" + sql.toString() );

			rs = ps.executeQuery();

		} 
		catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados da coleta" );

		}
 
		imprimirGrafico( visualizar, rs, sCab.toString() );

	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();

		FPrinterJob dlGr = null;

		dlGr = new FPrinterJob( "layout/rel/REL_FRETE_RECMERC.jasper", "Relatório de fretes", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcTransp.setConexao( cn );

	}
}
