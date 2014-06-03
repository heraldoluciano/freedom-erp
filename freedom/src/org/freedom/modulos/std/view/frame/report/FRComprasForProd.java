/**
 * @version 03/06/2014 <BR>
 * @author Robson Sanchez/Setpoint Tecnologia.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.report <BR>
 *         Classe:
 * @(#)FRComprasForProdjava <BR>
 * 
 *                        Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                        modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                        na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                        Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                        sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                        Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                        Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                        de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                        Relatório de compras por fornecedor/produto
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class FRComprasForProd extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodTipoFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );


	
	private Vector<String> vLabsRel = new Vector<String>();

	private Vector<String> vValsRel = new Vector<String>();

	private JRadioGroup<String, String> rgTipoDeRelatorio = null;

	private ListaCampos lcFor = new ListaCampos( this, "CL" );

	private ListaCampos lcTipoFor = new ListaCampos( this );
	
	private JCheckBoxPad cbObsItCompra = new JCheckBoxPad( "Imprimir Obs./ítens", "S", "N" );
	

	
	public FRComprasForProd() {

		super( false );
		setTitulo( "Últimas compras por fornecedor/produto" );
		setAtribos( 50, 50, 355, 350 );

		montaRadioGrupo();
		montaListaCampos();
		montaTela();
	}

	private void montaRadioGrupo() {
	
		vLabsRel.addElement( "Retrato" );
		vLabsRel.addElement( "Paisagem" );
		vValsRel.addElement( "relatorios/UltCompFor.jasper" );
		vValsRel.addElement( "relatorios/UltCompFor_landscape.jasper" );

		rgTipoDeRelatorio = new JRadioGroup<String, String>( 1, 2, vLabsRel, vValsRel );
		rgTipoDeRelatorio.setVlrString( "relatorios/UltCompFor.jasper" );
		
	}

	private void montaListaCampos() {

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.forn.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECEDOR", "CP" );

		lcTipoFor.add( new GuardaCampo( txtCodTipoFor, "CodTipoFor", "Cód.tp.forn.", ListaCampos.DB_PK, false ) );
		lcTipoFor.add( new GuardaCampo( txtDescTipoFor, "DescTipoFor", "Descrição do tipo de fornecedor", ListaCampos.DB_SI, false ) );
		txtCodTipoFor.setTabelaExterna( lcTipoFor, null );
		txtCodTipoFor.setNomeCampo( "CodTipoFor" );
		txtCodTipoFor.setFK( true );
		lcTipoFor.setReadOnly( true );
		lcTipoFor.montaSql( false, "TIPOCLI", "VD" );

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
		adic( new JLabelPad( "Cód.tp.forn." ), 7, 70, 90, 20 );
		adic( txtCodTipoFor, 7, 90, 90, 20 );
		adic( new JLabelPad( "Descrição do tipo de fornecedor" ), 100, 70, 227, 20 );
		adic( txtDescTipoFor, 100, 90, 227, 20 );
		adic( new JLabelPad( "Cód.forn." ), 7, 110, 90, 20 );
		adic( txtCodFor, 7, 130, 90, 20 );
		adic( new JLabelPad( "Razão social do fornecedor" ), 100, 110, 227, 20 );
		adic( txtRazFor, 100, 130, 227, 20 );
		adic( rgTipoDeRelatorio, 7, 170, 320, 30 );
		adic( cbObsItCompra,  	7,	210, 	200, 	20 );
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer cab = new StringBuffer();
		cab.append( "Período de : " );
		cab.append( Funcoes.dateToStrDate( txtDataini.getVlrDate() ) );
		cab.append( " Até : " );
		cab.append( Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );
		try {
			sql.append( "select razfor_ret razfor, codfor_ret codfor ");
			if( "N".equals( cbObsItCompra.getVlrString() )) {
				sql.append( ", descprod_ret descprod ");
			}
			else {
				sql.append( ", coalesce(obsitcompra_ret,descprod_ret) as descprod ");
			}
			sql.append( ", codprod_ret codprod, dtemitcompra_ret dtemitcompra " );
			sql.append( ", doccompra_ret doccompra, serie_ret serie");
			sql.append( ", precocompra_ret precocompra, refprod_ret refprod, qtdprod_ret qtditcompra " );
			sql.append( "from cpretultcpforprod (?,?,?,?,?,?,?,?) " );
			ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			if ( txtRazFor.getVlrString().trim().length() > 0 ) {
				ps.setInt( param++, txtCodFor.getVlrInteger() );
			}
			else {
				ps.setNull( param++, Types.INTEGER );
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			if ( txtDescTipoFor.getVlrString().trim().length() > 0 ) {
				ps.setInt( param++, lcTipoFor.getCodEmp() );
				ps.setInt( param++, lcTipoFor.getCodFilial() );
				ps.setInt( param++, txtCodTipoFor.getVlrInteger() );
			}
			else {
				ps.setNull( param++, Types.INTEGER );
				ps.setNull( param++, Types.INTEGER );
				ps.setNull( param++, Types.INTEGER );
			}
			rs = ps.executeQuery();
			imprimiGrafico( bVisualizar, rs, sql.toString() );
			con.commit();
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro ao buscar dados da venda!\n" + err.getMessage() );
		}
	}

	private void imprimiGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );
		dlGr = new FPrinterJob( rgTipoDeRelatorio.getVlrString(), "Últimas compras por fornecedor/produto", sCab, rs, hParam, this );
		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de Cliente por Produto!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );
		lcTipoFor.setConexao( cn );

	}
}
