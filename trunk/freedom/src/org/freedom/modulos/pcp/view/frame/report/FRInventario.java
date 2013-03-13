/**
 * @version 27/09/2007 <BR>
 * @author Setpoint Informática Ltda./Reginado Garcia Heua <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRCpProd.java <BR>
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
 *         Comentários sobre a classe...
 * 
 */
package org.freedom.modulos.pcp.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class FRInventario extends FRelatorio  {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescGrupo = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldFK txtDescMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcGrupo = new ListaCampos( this );

	private ListaCampos lcMarca = new ListaCampos( this );

	private JRadioGroup<?, ?> rgOrdem = null;

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	public FRInventario() {
		super( false );
		setTitulo( "Relatório de Inventário" );
		setAtribos( 50, 50, 345, 250 );
		
		montaTela();
		montaListaCampos();

	}

	public void montaTela() {

		vLabs.addElement( "Código" );
		vLabs.addElement( "Descrição" );
		vLabs.addElement( "Grupo" );
		vVals.addElement( "P.CODPROD" );
		vVals.addElement( "P.DESCPROD" );
		vVals.addElement( "CODGRUPO" );

		rgOrdem = new JRadioGroup<String, String>( 1, 3, vLabs, vVals );
		rgOrdem.setVlrString( "P.CODPROD" );
		rgOrdem.setVlrString( "T" );
		
		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
		JLabel periodo = new JLabel( "Compra anterior a: ", SwingConstants.LEFT );
		periodo.setOpaque( true );

		
		adic( txtCodGrupo, 7, 20, 70, 20, "Cód.grupo" );
		adic( txtDescGrupo, 80, 20, 225, 20, "Descrição do Grupo" );
		adic( txtCodMarca, 7, 60, 70, 20, "Cód.marca" );
		adic( txtDescMarca, 80, 60, 225, 20, "Descrição da Marca" );
		adic( rgOrdem, 7, 100, 300, 35, "Ordenar por:" );
	}

	public void montaListaCampos() {

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

		/***********
		 * Marca *
		 ***********/
		lcMarca.add( new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false ) );
		lcMarca.add( new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false ) );
		txtCodMarca.setTabelaExterna( lcMarca, null );
		txtCodMarca.setNomeCampo( "CodMarca" );
		txtCodMarca.setFK( true );
		lcMarca.setReadOnly( true );
		lcMarca.montaSql( false, "MARCA", "EQ" );

	}

	@ Override
	public void imprimir( TYPE_PRINT bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder sFiltro = new StringBuilder();
		StringBuilder sCab = new StringBuilder();
		int param = 1;
		
		if ( txtCodGrupo.getVlrString() != null && txtCodGrupo.getVlrString().trim().length() > 0 ) {

			sFiltro.append( "AND P.CODGRUP='" + txtCodGrupo.getVlrString() + "'" );
			sCab.append( " Grupo: " + txtDescGrupo.getVlrString() );

		}

		if ( txtCodMarca.getVlrString() != null && txtCodMarca.getVlrString().trim().length() > 0 ) {

			sFiltro.append( "AND P.CODMARCA='" + txtCodMarca.getVlrString() + "'" );
			sCab.append( " Marca: " + txtDescMarca.getVlrString() );

		}
		
		
		sql.append("select refprod, descprod, sldprod, custounit, custotot ");  
		sql.append(", coalesce(codfabprod,0) codfabprod, coalesce(codbarprod,0) codbarprod, ativoprod, codlote, venctolote, codunid, codgrup ");
		sql.append(", f.razfilial, f.dddfilial, f.fonefilial ");
		sql.append(", f.endfilial, f.numfilial, f.siglauf siglauff ");
		sql.append(", f.bairfilial, f.cnpjfilial,f.emailfilial ");
		sql.append(", f.unidfranqueada, f.wwwfranqueadora, f.marcafranqueadora "); 
		sql.append("from sgfilial f, eqrelpepssp(?,?,?,null,null,null,null,null,null,");
		sql.append("null,null,null,null,'N')  where f.codemp=? and f.codfilial=? and SLDPROD!=0  AND ATIVOPROD IN ('S')");
		
		
		

		System.out.println( "SQL:" + sql.toString() );

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( new Date() ));
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGFILIAL" ));
			rs = ps.executeQuery();

		} catch ( Exception e ) {

			Funcoes.mensagemErro( this, "Erro ao buscar dados do produto !\n" + e.getMessage() );
			e.printStackTrace();
		}

		imprimeGrafico( rs, bVisualizar, sCab.toString() );
		
	}

	public void imprimeGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, final String sCab ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "EQPRODUTO" ) );
		hParam.put( "FILTROS", sCab );

		FPrinterJob dlGr = new FPrinterJob( "relatorios/inventario.jasper", "Relatório de Inventário", null, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {

			dlGr.setVisible( true );

		}
		else {
			try {

				JasperPrintManager.printReport( dlGr.getRelatorio(), true );

			} catch ( Exception err ) {

				Funcoes.mensagemErro( this, "Erro na impressão do relatório de Últimas compras/produto!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcGrupo.setConexao( cn );
		lcMarca.setConexao( cn );
	}
}
