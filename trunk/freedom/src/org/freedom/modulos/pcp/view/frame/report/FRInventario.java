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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
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
		StringBuilder sSQL = new StringBuilder();
		StringBuilder sFiltro = new StringBuilder();
		StringBuilder sCab = new StringBuilder();
		//sCab.append( "Compras anteriores a "); 
		
		if ( txtCodGrupo.getVlrString() != null && txtCodGrupo.getVlrString().trim().length() > 0 ) {

			sFiltro.append( "AND P.CODGRUP='" + txtCodGrupo.getVlrString() + "'" );
			sCab.append( " Grupo: " + txtDescGrupo.getVlrString() );

		}

		if ( txtCodMarca.getVlrString() != null && txtCodMarca.getVlrString().trim().length() > 0 ) {

			sFiltro.append( "AND P.CODMARCA='" + txtCodMarca.getVlrString() + "'" );
			sCab.append( " Marca: " + txtDescMarca.getVlrString() );

		}
		
		sSQL.append( "SELECT P.CODPROD, P.REFPROD, P.DESCPROD, P.CODUNID," );
		sSQL.append( "IT.VLRPRODITCOMPRA, COALESCE(IT.VLRIPIITCOMPRA,0) VLRIPIITCOMPRA," );
		sSQL.append( "IT.VLRLIQITCOMPRA, C.DTEMITCOMPRA, C.DOCCOMPRA," );
		sSQL.append( "COALESCE((IT.VLRIPIITCOMPRA/ (CASE WHEN IT.QTDITCOMPRA IS NULL OR IT.QTDITCOMPRA=0 THEN 1 " ); // IPI
		sSQL.append( "ELSE IT.QTDITCOMPRA END )),0) IPIITCOMPRA, " );
		// Não é mais necessário realizar a divisão, pois o valor no campo vlrfreteitcompra já está correto
		// sSQL.append( "(IT.VLRFRETEITCOMPRA/ (CASE WHEN IT.QTDITCOMPRA IS NULL OR IT.CODITCOMPRA=0 THEN 1 " );// FRETE
		sSQL.append( "COALESCE(IT.VLRFRETEITCOMPRA,0) FRETEITCOMPRA, " );
		sSQL.append( "COALESCE((IT.VLRPRODITCOMPRA/(CASE WHEN IT.QTDITCOMPRA IS NULL OR IT.QTDITCOMPRA=0 THEN 1 " );// PREÇO " R$ UNIT "
		sSQL.append( "ELSE IT.QTDITCOMPRA END)),0) PRECOITCOMPRA " );
		sSQL.append( ", f.razfilial, f.dddfilial, f.fonefilial " );
		sSQL.append( ", f.endfilial, f.numfilial, f.siglauf siglauff " );
		sSQL.append( ", f.bairfilial, f.cnpjfilial,f.emailfilial " );
		sSQL.append( ", f.unidfranqueada, f.wwwfranqueadora, f.marcafranqueadora " );
		sSQL.append( "FROM EQPRODUTO P, CPITCOMPRA IT, CPCOMPRA C, SGFILIAL f " );
		sSQL.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? AND " );
		sSQL.append( "C.CODEMP=IT.CODEMP AND C.CODFILIAL=IT.CODFILIAL AND " );
		sSQL.append( "C.CODCOMPRA=IT.CODCOMPRA AND " );
		sSQL.append( "IT.CODEMPPD=P.CODEMP AND IT.CODFILIALPD=P.CODFILIAL AND " );
		sSQL.append( "IT.CODPROD=P.CODPROD AND IT.CODCOMPRA = ( SELECT FIRST 1 C2.CODCOMPRA FROM " );
		sSQL.append( "CPCOMPRA C2, CPITCOMPRA IT2 " );
		sSQL.append( "WHERE  C2.CODEMP=IT2.CODEMP AND C2.CODFILIAL=IT2.CODFILIAL AND " );
		sSQL.append( "C2.CODCOMPRA=IT2.CODCOMPRA AND IT2.CODEMP=IT.CODEMP AND " );
		sSQL.append( "IT2.CODFILIAL=IT.CODFILIAL AND IT2.CODEMPPD=IT.CODEMPPD AND " );
		sSQL.append( "IT2.CODFILIALPD=IT.CODFILIALPD AND " );
		sSQL.append( "IT2.CODPROD=IT.CODPROD " );
		//sSQL.append( sFiltro.toString() );
		sSQL.append( "ORDER BY C2.DTEMITCOMPRA DESC ) " );
		sSQL.append( " AND f.CODEMP=P.CODEMP AND f.CODFILIAL=P.CODFILIAL ");
		sSQL.append( " ORDER BY " );
		sSQL.append( rgOrdem.getVlrString() );
		
		

		System.out.println( "SQL:" + sSQL.toString() );

		try {

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			
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
