/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendasItem.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.gms;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRColetasDia extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JRadioGroup<?, ?> rgTipo = null;

	private ListaCampos lcGrup = new ListaCampos( this );

	private ListaCampos lcCliente = new ListaCampos( this );

	private boolean comref = false;

	public FRColetasDia() {

		setTitulo( "Coletas por dia" );
		setAtribos( 80, 80, 620, 380 );

		txtDescGrup.setAtivo( false );
		txtRazCli.setAtivo( false );

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "Grafico" );
		vLabs.addElement( "Texto" );
		vVals.addElement( "G" );
		vVals.addElement( "T" );
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "T" );
		
		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		txtCodGrup.setTabelaExterna( lcGrup );
		txtCodGrup.setNomeCampo( "CodGrup" );
		txtCodGrup.setFK( true );
		lcGrup.setReadOnly( true );
		lcGrup.montaSql( false, "GRUPO", "EQ" );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 7, 25, 273, 55 );
		adic( new JLabelPad( "De:" ), 15, 40, 30, 20 );
		adic( txtDataini, 45, 40, 90, 20 );
		adic( new JLabelPad( "Até:" ), 145, 40, 30, 20 );
		adic( txtDatafim, 180, 40, 90, 20 );		
		adic( new JLabelPad( "Cód.grupo" ), 7, 100, 70, 20 );
		adic( txtCodGrup, 7, 100, 70, 20 );
		adic( new JLabelPad( "Descrição do grupo" ), 90, 100, 200, 20 );
		adic( txtDescGrup, 90, 120, 200, 20 );		
		adic( new JLabelPad( "Cód.cli." ), 7, 140, 200, 20 );
		adic( txtCodCli, 7, 160, 70, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 80, 140, 200, 20 );
		adic( txtRazCli, 80, 160, 200, 20 );		

	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sCab2 = new StringBuffer();
		
		int param = 1;


		try {
	
			sql.append( "select " );
			sql.append( "se.descsecao, rm.dtent, rm.hins, rm.dtprevret, it.qtditrecmerc, pd.codprod, pd.refprod, " );
			sql.append( "pd.descprod, rm.ticket, cl.codcli, cl.razcli " );
			sql.append( "from " );
			sql.append( "eqrecmerc rm " );
			sql.append( "left outer join vdcliente cl on " );
			sql.append( "cl.codemp=rm.codempcl and cl.codfilial=rm.codfilialcl and cl.codcli=rm.codcli " );
			sql.append( "left outer join eqitrecmerc it on " );
			sql.append( "it.codemp=rm.codemp and it.codfilial=rm.codfilial and it.ticket=rm.ticket " );
			sql.append( "left outer join eqproduto pd on " );
			sql.append( "pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod " );
			sql.append( "left outer join eqsecao se on " );
			sql.append( "se.codemp=pd.codempsc and se.codfilial=pd.codfilialsc and se.codsecao=pd.codsecao " );
			sql.append( "where " );
			sql.append( "rm.codemp=? and rm.codfilial=? and rm.dtent between ? and ? " );
			sql.append( "order by rm.dtent, rm.codcli, it.codprod " );

			ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, Aplicativo.iCodFilial );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			rs = ps.executeQuery();
			
			imprimirGrafico( bVisualizar, rs, sCab.toString() + "\n" + sCab2.toString(), comref );
			
			rs.close();
			ps.close();
			
			con.commit();
		}
		catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		} 
		finally {
			System.gc();
		}
	}

	@SuppressWarnings("unchecked")
	public void imprimirTexto( final boolean bVisualizar, final ResultSet rs, final Vector cab, final boolean bComRef ) {

		String sLinhaFina = StringFunctions.replicate( "-", 133 );
		String sLinhaLarga = StringFunctions.replicate( "=", 133 );
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		BigDecimal bdQtd = new BigDecimal("0");
		BigDecimal bdVlr = new BigDecimal("0");
		
		try {

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Vendas por ítem" );
			imp.addSubTitulo( "RELATORIO DE VENDAS POR ITEM  -  PERIODO DE :" + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() );
			
			for ( int i=0; i < cab.size(); i++ ) {
				imp.addSubTitulo( (String) cab.elementAt( i ) );
			}
			
			while ( rs.next() ) {
				if ( imp.pRow() == linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();
				}
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 1, "| Cod.prod." );
					imp.say( 14, "| Desc.produto" );
					imp.say( 68, "| Unid. " );
					imp.say( 76, "|   Quantidade " );
					imp.say( 99, "|    Vlr.tot.item. " );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 3, Funcoes.copy( rs.getString( 1 ), 0, 10 ) + " | " );
				imp.say( 17, Funcoes.copy( rs.getString( 3 ), 0, 50 ) + " | " );
				imp.say( 70, Funcoes.copy( rs.getString( 4 ), 0, 5 ) + " | " );
				imp.say( 86, Funcoes.strDecimalToStrCurrency( 10, 1, rs.getString( 5 ) ) );
				imp.say( 99, "|" );
				imp.say( 100, Funcoes.strDecimalToStrCurrency( 20, 2, rs.getString( 6 ) ) );
				imp.say( 135, "|" );
				
				bdQtd = bdQtd.add( rs.getBigDecimal( 5 ) );
				bdVlr = bdVlr.add( rs.getBigDecimal( 6 ) );
			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + sLinhaLarga + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 30, "Quant. vendida -> " );
			imp.say( 50, Funcoes.copy( String.valueOf( bdQtd ), 6 ) );
			imp.say( 60, "Valor vendido -> " );
			imp.say( 78, Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( bdVlr ) ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinhaLarga + "+" );

			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
			
		}catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		}
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab, final boolean bComRef ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "COMREF", bComRef ? "S" : "N" );
		
		FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_COLETAS_01.jasper", "Coletas por dia", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de vendas detalhadas!" + err.getMessage(), true, con, err );
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
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}
		return bRetorno;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcGrup.setConexao( cn );

		lcCliente.setConexao( cn );

		comref = comRef();
	}
}
