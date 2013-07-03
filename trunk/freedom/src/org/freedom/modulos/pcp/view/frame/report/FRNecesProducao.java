/**
 * @version 03/06/2013 <BR>
 * @author Robson Sanchez / Setpoint Informática Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp.view.frame.report <BR>
 *         Classe:
 * @(#)FRAnalise.java <BR>
 * 
 *                    Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                    na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                    Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                    Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                    Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Relatório de necessidades de produção.
 * 
 */

package org.freedom.modulos.pcp.view.frame.report;

import org.freedom.library.type.TYPE_PRINT;
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
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import net.sf.jasperreports.engine.JasperPrintManager;

public class FRNecesProducao extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodProdIni = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdIni = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRefProdIni = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodProdFim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdFim = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRefProdFim = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescGrupo = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private Vector<String> vLbOrdem = new Vector<String>();

	private Vector<String> vVlrOrdem = new Vector<String>();

	private JRadioGroup<?, ?> rgOrdem = null;

	private Vector<String> vLbTipoOrdem = new Vector<String>();

	private Vector<String> vVlrTipoOrdem = new Vector<String>();

	private JRadioGroup<?, ?> rgTipoOrdem = null;

	private Vector<String> vLbTipoFiltro = new Vector<String>();

	private Vector<String> vVlrTipoFiltro = new Vector<String>();

	private JRadioGroup<?, ?> rgTipoFiltro = null;

	private ListaCampos lcProdIni = new ListaCampos( this, "" );

	private ListaCampos lcProdFim = new ListaCampos( this, "" );

	private ListaCampos lcGrupo = new ListaCampos( this );

	public FRNecesProducao() {
		super( false );
		setAtribos( 50, 50, 420, 440 );

		montaTela();
		montaListaCampos();
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Período:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		vLbOrdem.addElement( "Código" );
		vLbOrdem.addElement( "Descrição" );
		vLbOrdem.addElement( "Necessidade" );
		vVlrOrdem.addElement( "C" );
		vVlrOrdem.addElement( "D" );
		vVlrOrdem.addElement( "N" );
		rgOrdem = new JRadioGroup<String, String>( 3, 1, vLbOrdem, vVlrOrdem );
		rgOrdem.setVlrString( "C" );

		vLbTipoOrdem.addElement( "Crescente" );
		vLbTipoOrdem.addElement( "Decrescente" );
		vVlrTipoOrdem.addElement( "C" );
		vVlrTipoOrdem.addElement( "D" );
		rgTipoOrdem = new JRadioGroup<String, String>( 2, 1, vLbTipoOrdem, vVlrTipoOrdem );
		rgTipoOrdem.setVlrString( "C" );

		vLbTipoFiltro.addElement( "Todos os itens" );
		vLbTipoFiltro.addElement( "Estoq. menor que mín." );
		vVlrTipoFiltro.addElement( "T" );
		vVlrTipoFiltro.addElement( "E" );
		rgTipoFiltro = new JRadioGroup<String, String>( 1, 2, vLbTipoFiltro, vVlrTipoFiltro );
		rgTipoFiltro.setVlrString( "E" );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 355, 45 );

		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "Até:" ), 152, 25, 37, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );
		adic( new JLabelPad( "Cód.prod.i." ), 7, 55, 80, 20 );
		adic( txtCodProdIni, 7, 75, 70, 20 );
		adic( new JLabelPad( "Descrição do produto inicial" ), 83, 55, 280, 20 );
		adic( txtDescProdIni, 83, 75, 280, 20 );

		adic( new JLabelPad( "Cód.prod.f." ), 7, 95, 80, 20 );
		adic( txtCodProdFim, 7, 115, 70, 20 );
		adic( new JLabelPad( "Descrição do produto final" ), 83, 95, 280, 20 );
		adic( txtDescProdFim, 83, 115, 280, 20 );

		adic( new JLabelPad( "Cód.grupo" ), 7, 135, 80, 20 );
		adic( txtCodGrupo, 7, 155, 70, 20 );
		adic( new JLabelPad( "Descrição do grupo" ), 83, 135, 280, 20 );
		adic( txtDescGrupo, 83, 155, 280, 20 );

		adic( new JLabelPad("Ordem:"), 7, 175, 150, 20 );
		adic( rgOrdem, 7, 195, 173, 75 );
		adic( rgTipoOrdem, 193, 195, 173, 75);

		adic( new JLabelPad("Tipo de filtro:"), 7, 275, 400, 20 );
		adic( rgTipoFiltro, 7, 295, 360, 30 );
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	private void montaListaCampos() {

		/*******************
		 * Produto inicial *
		 *******************/

		lcProdIni.add( new GuardaCampo( txtCodProdIni, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProdIni.add( new GuardaCampo( txtRefProdIni, "RefProd", "Referência do produto", ListaCampos.DB_SI, false ) );
		lcProdIni.add( new GuardaCampo( txtDescProdIni, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProdIni.setTabelaExterna( lcProdIni, FProduto.class.getCanonicalName() );
		txtCodProdIni.setNomeCampo( "CodProd" );
		txtCodProdIni.setFK( true );
		lcProdIni.setReadOnly( true );
		lcProdIni.montaSql( false, "PRODUTO", "EQ" );

		/*******************
		 * Produto final *
		 *******************/

		lcProdFim.add( new GuardaCampo( txtCodProdFim, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProdFim.add( new GuardaCampo( txtRefProdFim, "RefProd", "Referência do produto", ListaCampos.DB_SI, false ) );
		lcProdFim.add( new GuardaCampo( txtDescProdFim, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProdFim.setTabelaExterna( lcProdFim, FProduto.class.getCanonicalName() );
		txtCodProdFim.setNomeCampo( "CodProd" );
		txtCodProdFim.setFK( true );
		lcProdFim.setReadOnly( true );
		lcProdFim.montaSql( false, "PRODUTO", "EQ" );

		/**********
		 * Grupo *
		 **********/
		lcGrupo.add( new GuardaCampo( txtCodGrupo, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false ) );
		lcGrupo.add( new GuardaCampo( txtDescGrupo, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		lcGrupo.montaSql( false, "GRUPO", "EQ" );
		lcGrupo.setReadOnly( true );
		txtCodGrupo.setTabelaExterna( lcGrupo, null );
		txtCodGrupo.setFK( true );
		txtCodGrupo.setNomeCampo( "CodGrup" );
	}

	public void imprimir( TYPE_PRINT b ) {

		StringBuffer sql = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			if ( txtCodProdIni.getVlrInteger() > 0 ) {

				sWhere.append( "and op.codprod= " + txtCodProdIni.getVlrInteger() );
			}
			/*if ( txtCodOP.getVlrInteger() > 0 ) {
				sWhere.append( "and op.codop= " + txtCodOP.getVlrInteger() );
			}
*/
			
			/*
			 * 
			 * select p.codprod, p.descprod, p.codunid, p.sldliqprod, p.qtdminprod
, cast(sum(iv.qtditvenda) as decimal(15,2)) qtditvenda
, cast(sum(iv.qtditvenda) / 30 * 30 as decimal(15,2)) qtditvenda_mes
, cast(case when p.sldliqprod>p.qtdminprod then 0 else p.qtdminprod-p.sldliqprod end as decimal(15,2)) qtdnecesprod
from eqproduto p, vdvenda v, vditvenda iv
where p.codemp=7 and p.codfilial=1
and v.dtemitvenda between '01.06.2013' and '30.06.2013'
and iv.codemp=v.codemp and iv.codfilial=v.codfilial
and iv.tipovenda=v.tipovenda and iv.codvenda=v.codvenda
and iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and iv.codprod=p.codprod
group by 1,2,3,4,5

*/
			sql.append( "select op.codprod,pd.descprod,op.codlote,op.dtfabrop,op.dtvalidpdop, " );
			sql.append( "ta.desctpanalise,ea.vlrmin,ea.vlrmax,ea.especificacao,cq.vlrafer,cq.descafer,pr.imgassresp,cq.dtins, " );
			sql.append( "op.codop, eq.casasdec, eq.codunid, eq.descunid,cq.idusualt,pr.nomeresp, pr.nomerelanal, pr.cargoresp, pr.identprofresp " );
			sql.append( "from ppopcq cq, ppop op,ppestruanalise ea,pptipoanalise ta, sgprefere5 pr,eqproduto pd, equnidade eq " );
			sql.append( "where " );
			sql.append( "op.codemp = ? and op.codfilial=? and op.seqop=cq.seqop " );
			sql.append( "and cq.codemp=op.codemp and cq.codfilial=op.codfilial and cq.codop=op.codop and cq.seqop=op.seqop " );
			sql.append( "and pr.codemp=7 and pr.codfilial=1 " );
			sql.append( "and ea.codemp=op.codemppd and ea.codfilial=op.codfilialpd and ea.codprod=op.codprod " );
			sql.append( "and ea.seqest=op.seqest " );
			sql.append( "and ta.codemp=ea.codempta and ta.codfilial=ea.codfilialta and ta.codtpanalise=ea.codtpanalise " );
			sql.append( "and ea.codestanalise=cq.codestanalise " );
			sql.append( "and cq.status='AP' " );
			sql.append( "and pd.codemp=op.codemppd and pd.codfilial=op.codfilialpd and pd.codprod=op.codprod " );
			sql.append( "and eq.codemp=ta.codempud and eq.codfilial=ta.codfilialud and eq.codunid=ta.codunid " );
			sql.append( "and op.dtfabrop between ? and ? " );
			sql.append( sWhere.toString() );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setDate( 3, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 4, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			rs = ps.executeQuery();

			sCab.append( "Período: " + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() );

			imprimiGrafico( rs, b, sCab.toString() );

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar análises", true, con, err );
		}
	}

	private void imprimiGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );
		hParam.put( "DESCPROD", txtDescProdIni.getVlrString() );
		hParam.put( "CODPROD", txtCodProdIni.getVlrInteger() == 0 ? null : txtCodProdIni.getVlrInteger() );

		dlGr = new FPrinterJob( "relatorios/NecesProducao.jasper", "Relatório de necessidade de produção", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de Relatório de Análises!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		lcProdIni.setConexao( con );
		lcProdFim.setConexao( con );
		lcGrupo.setConexao( con );
	}
}
