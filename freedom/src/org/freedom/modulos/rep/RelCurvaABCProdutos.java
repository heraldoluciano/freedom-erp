/**
 * @version 11/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelResumoDiario.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Relatorio produtos por clientes.
 * 
 */

package org.freedom.modulos.rep;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class RelCurvaABCProdutos extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtCurvaA = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );
	
	private final JTextFieldPad txtCurvaB = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );
	
	private final JTextFieldPad txtCurvaC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );
	
	private final ListaCampos lcCliente = new ListaCampos( this );
	
	private final ListaCampos lcFornecedor = new ListaCampos( this );
	
	private final ListaCampos lcVendedor = new ListaCampos( this );


	public RelCurvaABCProdutos() {

		super( false );
		setTitulo( "Relatorio de Curva ABC de produtos" );		
		setAtribos( 100, 50, 325, 270 );
		
		montaListaCampos();
		montaTela();
		
		Calendar cal = Calendar.getInstance();			
		txtDtFim.setVlrDate( cal.getTime() );		
		cal.set( cal.get( Calendar.YEAR ), 0, 1 );
		txtDtIni.setVlrDate( cal.getTime() );	
		
		txtCurvaA.setVlrInteger( 20 );
		txtCurvaB.setVlrInteger( 30 );
		txtCurvaC.setVlrInteger( 50 );
	}
	
	private void montaListaCampos() {
		
		/***********
		 * CLIENTE *
		 ***********/
		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "RP" );
		lcCliente.setQueryCommit( false );
		lcCliente.setReadOnly( true );
		txtCodCli.setListaCampos( lcCliente );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setPK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		/**************
		 * FORNECEDOR *
		 **************/
		
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setListaCampos( lcFornecedor );
		txtCodFor.setTabelaExterna( lcFornecedor );
		txtCodFor.setPK( true );
		txtCodFor.setNomeCampo( "CodFor" );
		
		/************
		 * VENDEDOR *
		 ************/
		
		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.vend.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "RP" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setListaCampos( lcVendedor );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setPK( true );
		txtCodVend.setNomeCampo( "CodVend" );
	}
	
	private void montaTela() {
				
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
		adic( periodo, 25, 10, 60, 20 );
		
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 10, 20, 290, 45 );
		
		adic( txtDtIni, 25, 35, 110, 20 );
		adic( new JLabel( "até", SwingConstants.CENTER ), 135, 35, 40, 20 );
		adic( txtDtFim, 175, 35, 110, 20 );
		
		adic( new JLabel( "Cód.for." ), 10, 70, 77, 20 );
		adic( txtCodFor, 10, 90, 77, 20 );
		adic( new JLabel( "Razão social do fornecedor" ), 90, 70, 210, 20 );
		adic( txtRazFor, 90, 90, 210, 20 );
		
		adic( new JLabel( "Cód.vend." ), 10, 110, 77, 20 );
		adic( txtCodVend, 10, 130, 77, 20 );
		adic( new JLabel( "Nome do vendedor" ), 90, 110, 210, 20 );
		adic( txtNomeVend, 90, 130, 210, 20 );
		
		adic( new JLabel( "Cód.cli." ), 10, 150, 77, 20 );
		adic( txtCodCli, 10, 170, 77, 20 );
		adic( new JLabel( "Razão social do cliente" ), 90, 150, 210, 20 );
		adic( txtRazCli, 90, 170, 210, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {
				
		if ( txtDtIni.getVlrDate() != null && txtDtFim.getVlrDate() != null ) {
			if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
				return;
			}
		}
		
		if ( (txtCurvaA.getVlrInteger() + 
				txtCurvaB.getVlrInteger() + 
					txtCurvaC.getVlrInteger()) != 100 ) {
			Funcoes.mensagemInforma( this, "O total da soma do valor das curvas deve ser 100 !" );
			return;
		}

		try {
			
			String nomevend = null;
			String razcli = null;
			String razfor = null;
			Date dtini = txtDtIni.getVlrDate();
			Date dtfim = txtDtFim.getVlrDate();
			
			double itensTotal = 0;
			BigDecimal valorTotal = null;
			
			StringBuilder where = new StringBuilder();
			
			if ( txtCodCli.getVlrString().trim().length() > 0 ) {
				where.append( " P.CODEMPCL=P.CODEMP AND P.CODFILIALCL=P.CODEMP AND P.CODCLI=" + txtCodCli.getVlrInteger() );
				razcli = txtRazCli.getVlrString();
			}
			if ( txtCodFor.getVlrString().trim().length() > 0 ) {
				where.append( " P.CODEMPFO=P.CODEMP AND P.CODFILIALFO=P.CODEMP AND P.CODFOR=" + txtCodFor.getVlrInteger() );
				razfor = txtRazFor.getVlrString();
			}
			if ( txtCodVend.getVlrString().trim().length() > 0 ) {
				where.append( " P.CODEMPVD=P.CODEMP AND P.CODFILIALVD=P.CODEMP AND P.CODVEND=" + txtCodVend.getVlrInteger().intValue() );
				nomevend = txtNomeVend.getVlrString();
			}
			
			StringBuilder sql = new StringBuilder();

			sql.append( "SELECT COUNT(I.CODPROD) ITENS_TOTAL, SUM(COALESCE(I.VLRLIQITPED,0)) VALOR_TOTAL " );
			sql.append( "FROM RPITPEDIDO I, RPPEDIDO P " );
			sql.append( "WHERE" );
			sql.append( "  I.CODEMP=? AND I.CODFILIAL=? AND " );
			sql.append( "  I.CODEMP=P.CODEMP AND I.CODFILIAL=P.CODFILIAL AND I.CODPED=P.CODPED AND " );
			sql.append( where );
			sql.append( "  P.DATAPED BETWEEN ? AND ? " );			
			sql.append( "GROUP BY I.CODEMP, I.CODFILIAL " );
						
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPITPEDIDO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {				
				itensTotal = rs.getDouble( "ITENS_TOTAL" );
				valorTotal = rs.getBigDecimal( "VALOR_TOTAL" );
			}
			
			rs.close();
			ps.close();
			
			sql = new StringBuilder();

			sql.append( "SELECT" );
			sql.append( "  I.CODPROD PRODUTO," );
			sql.append( "  PD.REFPROD REFERENCIA," );
			sql.append( "  PD.DESCPROD DESCRICAO," );
			sql.append( "  SUM(I.QTDITPED) QUANTIDADE," ); 
			sql.append( "  MIN(I.PRECOITPED) MENOR_PRECO," ); 
			sql.append( "  MAX(I.PRECOITPED) MAIOR_PRECO," );
			sql.append( "  SUM(I.VLRLIQITPED)/(" + String.valueOf( valorTotal ) + "/100) PORCENTAGEM," );
			sql.append( "  SUM(I.VLRLIQITPED) VALOR " );
			sql.append( "FROM" ); 
			sql.append( "  RPITPEDIDO I, RPPEDIDO P, RPPRODUTO PD " );
			sql.append( "WHERE" );
			sql.append( "  I.CODEMP=? AND I.CODFILIAL=? AND " );
			sql.append( "  I.CODEMP=P.CODEMP AND I.CODFILIAL=P.CODFILIAL AND I.CODPED=P.CODPED AND" );
			sql.append( "  PD.CODEMP=I.CODEMP AND PD.CODFILIAL=I.CODFILIAL AND PD.CODPROD=I.CODPROD AND" );
			sql.append( where );
			sql.append( "  P.DATAPED BETWEEN ? AND ? " );
			sql.append( "GROUP BY" );
			sql.append( "  I.CODPROD, PD.REFPROD, PD.DESCPROD " );
			sql.append( "ORDER BY" ); 
			sql.append( "  6 DESC, 2 DESC" );
						
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPITPEDIDO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );
			rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con );
			hParam.put( "DTINI", dtini );
			hParam.put( "DTFIM", dtfim );
			hParam.put( "NOMEVEND", nomevend );
			hParam.put( "RAZFOR", razfor );
			hParam.put( "RAZCLI", razcli );					
			hParam.put( "CURVA_A", (int)((itensTotal/100)*txtCurvaA.getVlrInteger()) );
			hParam.put( "CURVA_B", (int)((itensTotal/100)*txtCurvaB.getVlrInteger()) );
			hParam.put( "CURVA_C", (int)((itensTotal/100)*txtCurvaC.getVlrInteger()) );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/rpcurvaABC.jasper", "PRODUTO POR CLIENTE", null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcCliente.setConexao( cn );
		lcFornecedor.setConexao( cn );
		lcVendedor.setConexao( cn );
	}

}
