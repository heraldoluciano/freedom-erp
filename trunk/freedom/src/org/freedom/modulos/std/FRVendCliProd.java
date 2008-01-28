/**
 * @version 16/01/2008 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVendCliProd.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRVendCliProd extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	
	public FRVendCliProd(){
		
		setTitulo( "Ultimas Vendas de Cliente/Produto" );
		setAtribos( 50, 50, 350, 200 );
	
		montaTela();
		montaListaCampos();
		
	}
	
	private void montaTela(){
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 15, 10, 80, 20 );
		adic( lbLinha, 7, 25, 303, 40 );

		adic( new JLabelPad( "De:", SwingConstants.CENTER ), 10, 35, 40, 20 );
		adic( txtDataini, 50, 35, 100, 20 );
		adic( new JLabelPad( "Até:", SwingConstants.CENTER ), 150, 35, 45, 20 );
		adic( txtDatafim, 195, 35, 100, 20 );
		adic( new JLabelPad("Cód.Cli"),7, 75, 70, 20 );
		adic( txtCodCli, 7, 95, 70, 20 );
		adic( new JLabelPad("Razão social do cliente"), 80, 75, 170, 20 );
		adic( txtRazCli, 80, 95, 230, 20 );
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}
	
	private void montaListaCampos(){
		
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );
	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		
		sCab.append( "de : " + txtDataini.getVlrDate() + "Até : " + txtDatafim.getVlrDate()  );
	
		if( txtRazCli.getVlrString().trim().length() > 0 ){
			sWhere.append( "AND C.CODCLI=" + txtCodCli.getVlrInteger());
		}
		
		try {
			
			sSQL.append( "SELECT C.RAZCLI, V.CODCLI, P.DESCPROD, IV.CODPROD, " );
			sSQL.append( "MAX(V.DTEMITVENDA) DTEMITVENDA, MAX(V.DOCVENDA) DOCVENDA, " );
			sSQL.append( "MAX(V.SERIE) SERIE, " );
			sSQL.append( "MAX (IV.VLRLIQITVENDA/(CASE WHEN IV.QTDITVENDA=0 THEN 1 ELSE IV.QTDITVENDA END)) PRECOVENDA " );
			sSQL.append( "FROM VDCLIENTE C, VDVENDA V, VDITVENDA IV, EQPRODUTO P " );
			sSQL.append( "WHERE C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " ); 
			sSQL.append( "C.CODCLI=V.CODCLI AND C.CODEMP=? AND C.CODFILIAL=? AND " );
			sSQL.append( "IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND " );
			sSQL.append( "IV.TIPOVENDA=V.TIPOVENDA AND IV.CODVENDA=V.CODVENDA AND " ); 
			sSQL.append( "P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND " );
			sSQL.append( "P.CODPROD=IV.CODPROD AND " ); 
			sSQL.append( "V.DTEMITVENDA BETWEEN ? AND ? " );
			sSQL.append( sWhere );
			sSQL.append( "GROUP BY C.RAZCLI, V.CODCLI, P.DESCPROD, IV.CODPROD " );
			
			ps = con.prepareStatement( sSQL.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp ); 
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) ); 
			ps.setDate( 3, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 4, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			rs = ps.executeQuery();
			
			imprimiGrafico( bVisualizar, rs, sCab.toString() );

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro ao buscar dados da venda!" );
		}
	}
	
	private void imprimiGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {
		
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/UltVendCli.jasper", "Ultimas Vendas por Cliente/Produto", sCab, rs, hParam, this );

		if ( bVisualizar ) {
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

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( con );
	}
}
