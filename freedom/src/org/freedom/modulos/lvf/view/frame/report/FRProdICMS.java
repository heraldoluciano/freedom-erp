/**
 * @version 03/04/2014 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.lvf.view.frame.report <BR>
 *         Classe: @(#)FRProdICMS.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Relatório de produtos e alíquotas de ICMS.
 * 
 */

package org.freedom.modulos.lvf.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.cfg.view.frame.crud.plain.FUF;
import org.freedom.modulos.std.view.frame.crud.plain.FTipoFisc;

public class FRProdICMS extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodFiscCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescFiscCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbSoST = new JCheckBoxPad( "Somente produtos com ST", "S", "N" );
	
	private ListaCampos lcUF = new ListaCampos( this );

	private ListaCampos lcTipoFiscCli = new ListaCampos( this );

	public FRProdICMS() {

		setTitulo( "Produtos/ICMS" );
		setAtribos( 100, 100, 500, 200 );

		montaListaCampos();
		montaTela();

	}

	private void montaTela() {
		adic( new JLabelPad( "UF" ), 7, 0, 50, 20 );
		adic( txtSiglaUF, 7, 20, 50, 20 );
		adic( new JLabelPad( "Nome UF" ), 60, 0, 250, 20 );
		adic( txtNomeUF, 60, 20, 250, 20 );
		adic( new JLabelPad( "Cód.fiscal" ), 7, 40, 70, 20 );
		adic( txtCodFiscCli, 7, 60, 70, 20 );
		adic( new JLabelPad( "Descrição" ), 80, 40, 250, 20 );
		adic( txtDescFiscCli, 80, 60, 250, 20 );
		adic( cbSoST, 7, 85, 250, 25 );
		cbSoST.setVlrString( "S" );
	}

	private void montaListaCampos() {
		/***************
		 * UF *
		 **************/
		lcUF.setUsaME( false );
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, true ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		lcUF.add( new GuardaCampo( txtCodPais, "Codpais", "Cód.país", ListaCampos.DB_PK, true ) );
		lcUF.setDinWhereAdic( "codpais=#N", txtCodPais );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUF.setNomeCampo( "SiglaUf" );
		txtSiglaUF.setFK( true );
		txtSiglaUF.setTabelaExterna( lcUF, FUF.class.getCanonicalName() );
		/***************
		 * Tipo fiscal *
		 **************/
		lcTipoFiscCli.setUsaME( false );
		lcTipoFiscCli.add( new GuardaCampo( txtCodFiscCli, "CodFiscCli", "Cód.fiscal", ListaCampos.DB_PK, true ) );
		lcTipoFiscCli.add( new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descrição", ListaCampos.DB_SI, false ) );
		lcTipoFiscCli.montaSql( false, "TIPOFISCCLI", "LF" );
		lcTipoFiscCli.setQueryCommit( false );
		lcTipoFiscCli.setReadOnly( true );
		txtCodFiscCli.setNomeCampo( "CodFiscCli" );
		txtCodFiscCli.setFK( true );
		txtCodFiscCli.setTabelaExterna( lcTipoFiscCli, FTipoFisc.class.getCanonicalName() );

	}

	private int getCodpais() {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("select codpais from sgfilial f where f.codemp=? and f.codfilial=?");
		try {
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt( "codpais" );
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro carregando cóidog do pais.\n" + e.getMessage() );
			try {
				con.rollback();
			} catch( SQLException rerr) {
				rerr.printStackTrace();
			}
		}
		return result;
	}
	
	public void imprimir( TYPE_PRINT bVisualizar ) {

		imprimeGrafico( bVisualizar );
	}

	
	private void imprimeGrafico(final TYPE_PRINT visualizar ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder filtros = new StringBuilder();
		int param = 1;

		try {

			sql.append( "select pd.codprod, pd.descprod, cf.codncm,  icf.siglauf ");
			sql.append(", tfc.codfisccli, tfc.descfisccli, icf.margemvlagr, icf.aliqfiscintra, icf.aliqfisc ");
			sql.append(" from eqproduto pd ");
			sql.append(" inner join lfclfiscal cf ");
			sql.append(" on cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc and cf.codfisc=pd.codfisc ");
			sql.append(" inner join lfitclfiscal icf ");
			sql.append(" on icf.codemp=cf.codemp and icf.codfilial=cf.codfilial and icf.codfisc=cf.codfisc ");
			sql.append(" inner join lftipofisccli tfc ");
			sql.append(" on tfc.codemp=icf.codempfc and tfc.codfilial=icf.codfilialfc and tfc.codfisccli=icf.codfisccli ");
			sql.append(" where pd.codemp=? and pd.codfilial=? ");
			if (!"".equals( txtSiglaUF.getVlrString().trim())) {
				sql.append(" and icf.siglauf=? ");
				filtros.append( " ( UF: " );
				filtros.append(txtSiglaUF.getVlrString());
				filtros.append( " - ");
				filtros.append(txtNomeUF.getVlrString());
				filtros.append( " ) ");
			}
			if (!"".equals( txtCodFiscCli.getVlrString().trim())) {
				sql.append(" and icf.codempfc=? and icf.codfilialfc=? and icf.codfisccli=? ");
				filtros.append( " ( Tipo fiscal: " );
				filtros.append(txtCodFiscCli.getVlrString());
				filtros.append( " - ");
				filtros.append(txtDescFiscCli.getVlrString());
				filtros.append( " ) ");
			}
			if ("S".equals(cbSoST.getVlrString())) {
				sql.append(" and icf.tipofisc='FF' ");
				filtros.append( " ( " );
				filtros.append(cbSoST.getText());
				filtros.append( " ) ");
			}
			sql.append(" and icf.ativoitfisc='S' ");
			sql.append(" order by pd.descprod, pd.codprod, icf.siglauf, tfc.codfisccli" );
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			if (!"".equals( txtSiglaUF.getVlrString().trim())) {
				ps.setString( param++, txtSiglaUF.getVlrString() );
			}
			if (!"".equals( txtCodFiscCli.getVlrString().trim())) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "LFTIPOFISCCLI" ) );
				ps.setInt( param++, txtCodFiscCli.getVlrInteger() );
			}
			rs = ps.executeQuery();
			
			String pathReportFile = "relatorios/ProdICMS.jasper";

			FPrinterJob dlGr = null;
			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ) );
			hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
			hParam.put( "FILTROS", filtros.toString() );

			dlGr = new FPrinterJob( pathReportFile, "Relatório de PRODUTOS/ICMS ", filtros.toString(), rs, hParam, this );

			if ( visualizar==TYPE_PRINT.VIEW ) {
				dlGr.setVisible( true );
			}
			else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro na impressão do relatório!" + err.getMessage(), true, con, err );
				}
			}
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "Erro executando a consulta !\n"+e.getMessage() );
		} finally {
			sql = null;
			filtros = null;
			ps = null;
			rs = null;
			System.gc();
		}
	}
	
	@ Override
	public void setConexao( DbConnection cn ) {
	
		super.setConexao( cn );
		lcUF.setConexao( cn );
		lcTipoFiscCli.setConexao( cn );
		txtCodPais.setVlrInteger( getCodpais() );
	}
}
