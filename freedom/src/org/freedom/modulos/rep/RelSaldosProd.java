/**
 * @version 11/2008 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Reginaldo Garcia Heua<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelSaldosProd.java <BR>
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
 * Relatorio de clientes, em dois modos: completo e resumido.
 * 
 */

package org.freedom.modulos.rep;

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

public class RelSaldosProd extends FRelatorio {
	
	private static final long serialVersionUID = 1;
	
	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final ListaCampos lcFornecedor = new ListaCampos( this );


	public RelSaldosProd() {

		super( false );
		setTitulo( "Tabela com saldo" );		
		setAtribos( 50, 50, 325, 200 );

		montaListaCampos();
		montaTela();
		
		Calendar cal = Calendar.getInstance();			
		txtDtFim.setVlrDate( cal.getTime() );		
		cal.set( cal.get( Calendar.YEAR ), 0, 1 );
		txtDtIni.setVlrDate( cal.getTime() );	
	}
	
	
	private void montaListaCampos() {
		
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
	
	}

	public void imprimir( boolean visualizar ) {

		try {
			
			StringBuilder sql = new StringBuilder();
			Date dtini = txtDtIni.getVlrDate();
			Date dtfim = txtDtFim.getVlrDate();
					
			sql.append( "select pr.codfor, pr.refprod,pr.descprod,pr.saldoprod as compra, pr.precoprod1, " );
			sql.append( "coalesce(sum(it.qtditped),0) as venda ,coalesce(pr.saldoprod-sum(coalesce(it.qtditped,0)),0) as saldo " );
			sql.append( "from rpproduto pr left outer join rpitpedido it on " );
			sql.append( "pr.codemp=it.codemppd and pr.codfilial=it.codfilialpd and pr.codprod = it.codprod " );
			sql.append( "left outer join rppedido pd on " );
			sql.append( "pd.codemp = it.codemp and pd.codfilial = it.codfilial and pd.codped = it.codped " );
			sql.append( "and pd.codemp=? and pd.codfilial=? " );
			sql.append( "and pd.dataped between ? and ? " );
			
			if( !txtCodFor.getVlrString().equals( "" ) ){
				sql.append( "where pd.codfor=? " );
			}			
			
			sql.append( "group by 1,2,3,4,5 " );
			sql.append( "order by pr.descprod " );
									
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "rpproduto" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );
			
			if( !txtCodFor.getVlrString().equals( "" )){
				ps.setInt( 5, txtCodFor.getVlrInteger() );
			}
			
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();
			
			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/rpsaldoprod.jasper", "TABELA", null, rs, hParam, this );

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
		lcFornecedor.setConexao( cn );
	}

}
