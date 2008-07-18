/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRComprasFor.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRComprasFor extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcFor = new ListaCampos( this );
	
	private Vector<String> vLabs1 = new Vector<String>();
	
	private Vector<String> vVals1 = new Vector<String>();
	
	private JRadioGroup<?, ?> rgTipo = null;

	private String sCodProd = "CODPROD";

	public FRComprasFor() {

		setTitulo( "Compras por Fornecedor" );
		setAtribos( 50, 50, 340, 225 );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );
		
		vLabs1.addElement("Texto");
 		vLabs1.addElement("Gráfico"); 
 		vVals1.addElement("T");
 		vVals1.addElement("G");
		    
 		rgTipo = new JRadioGroup<String, String>(1,2,vLabs1,vVals1);
 		rgTipo.setVlrString("T");
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad("Período:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque(true);
		
		adic(lbPeriodo,7, 1, 80, 20 );
		adic(lbLinha,5,10,300,45);
		
		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "Até:" ), 140, 25, 37, 20 );
		adic( txtDatafim, 180, 25, 100, 20 );
		adic( new JLabelPad( "Cód.for." ), 7, 60, 280, 20 );
		adic( txtCodFor, 7, 80, 70, 20 );
		adic( new JLabelPad( "Descrição do fornecedor" ), 80, 60, 280, 20 );
		adic( txtDescFor, 80, 80, 225, 20 );
		adic( rgTipo, 7, 105, 300, 30 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuilder sCab = new StringBuilder();
		

		ehRef();

		if ( txtCodFor.getText().trim().length() > 0 ) {
			
			sWhere.append( " AND C.CODFOR = " );
			sWhere.append( txtCodFor.getText().trim() );
			sCab.append( "Fornecedro: " + txtDescFor.getVlrString() );
		}
		
		sSQL.append( "SELECT C.CODFOR,F.RAZFOR,C.DTEMITCOMPRA,C.CODCOMPRA,C.DOCCOMPRA," ); 
		sSQL.append( "IT." + sCodProd );
		sSQL.append( ",P.DESCPROD,IT.QTDITCOMPRA,IT.PRECOITCOMPRA,IT.VLRLIQITCOMPRA " );
		sSQL.append( "FROM CPCOMPRA C,CPITCOMPRA IT,EQPRODUTO P,CPFORNECED F " ); 
		sSQL.append( "WHERE P.CODPROD = IT.CODPROD AND IT.CODCOMPRA = C.CODCOMPRA AND F.CODFOR = C.CODFOR " );
		sSQL.append( "AND C.DTEMITCOMPRA BETWEEN ? AND ? " ); 
		sSQL.append( sWhere ); 
		sSQL.append( " AND C.FLAG IN " + AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) ); 
		sSQL.append( " ORDER BY C.CODFOR,C.DTEMITCOMPRA" );
		
		try {
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				
			rs = ps.executeQuery();
				
		} catch ( SQLException e ) {
				
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela de compras", true, con, e );
		}
		
		if("T".equals( rgTipo.getVlrString())){
			
			imprimeTexto( rs, bVisualizar, sCab.toString() );
		}
		else{
			imprimiGrafico( rs, bVisualizar, sCab.toString() ); 
		}
	} 
	
	public void imprimeTexto( final ResultSet rs, final boolean bVisualizar, final String sCab ){
		
		float fVlr = 0;
		float fQtd = 0;
		float fVlrFor = 0;
		float fQtdFor = 0;	
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		int iCodFor = 0;
		int iCodForAnt = -1;
		String sLinhaFina = Funcoes.replicate( "-", 133 );
		boolean termFor = false;
		String sTmp = "";
		
		try {

			imp.montaCab();
			imp.setTitulo( "Relatório de Compras por Fornecedor" );
			imp.addSubTitulo( "RELATORIO DE COMPRAS POR FORNECEDOR  -  PERIODO DE: " + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() );			
			imp.limpaPags();
			
			while ( rs.next() ) {
				
				iCodFor = rs.getInt( "CodFor" );
				
				if ( imp.pRow() == linPag ) {
					
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();
				}
				
				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 136, true );

					imp.say( 0, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "| Data       " );
					imp.say( 14, "| Pedido   " );
					imp.say( 26, "| Doc      " );
					imp.say( 38, "| Cod. Produto   " );
					imp.say( 55, "| Desc. Produto  " );
					imp.say( 89, "| Qtd.    " );
					imp.say( 100, "| Preco     " );
					imp.say( 113, "| Total     " );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
				}
				if ( iCodFor != iCodForAnt ) {
					
					if ( termFor ) {
						
						fQtd += fQtdFor;
						fVlr += fVlrFor;
						
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + sLinhaFina + "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 1, "Sub-total fornecedor:" );
						imp.say( 30, "Quant. comprada -> " );
						imp.say( 50, Funcoes.copy( String.valueOf( fQtdFor ), 0, 6 ) );
						imp.say( 60, "Valor comprado -> " );
						imp.say( 78, Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( fVlrFor ) ) );
						imp.say( 135, "|" );
					}
					
					fQtdFor = 0;
					fVlrFor = 0;
					
					sTmp = "FORNECEDOR: " + rs.getInt( "CodFor" ) + " - " + rs.getString( "RazFor" ).trim();
					
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( ( 133 - sTmp.length() ) / 2, sTmp );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
					
					termFor = true;
				}
				
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| " + Funcoes.sqlDateToStrDate( rs.getDate( "DtEmitCompra" ) ) );
				imp.say( 14, "| " + Funcoes.copy( rs.getString( "CodCompra" ), 0, 8 ) );
				imp.say( 26, "| " + Funcoes.copy( rs.getString( "DocCompra" ), 0, 8 ) );
				imp.say( 38, "| " + Funcoes.copy( rs.getString( sCodProd ), 0, 13 ) );
				imp.say( 55, "| " + Funcoes.copy( rs.getString( "DescProd" ), 0, 30 ) );
				imp.say( 89, "| " + Funcoes.strDecimalToStrCurrency( 7, 1, rs.getString( "QtdItCompra" ) ) );
				imp.say( 100, "| " + Funcoes.strDecimalToStrCurrency( 9, 2, rs.getString( "PrecoItCompra" ) ) );
				imp.say( 113, "| " + Funcoes.strDecimalToStrCurrency( 9, 2, rs.getString( "VlrLiqItCompra" ) ) );
				imp.say( 135, "|" );
				
				fQtdFor += rs.getFloat( "QtdItCompra" );
				fVlrFor += rs.getFloat( "VlrLiqItCompra" );

				iCodForAnt = iCodFor;
			}
			
			fQtd += fQtdFor;
			fVlr += fVlrFor;
			
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + sLinhaFina + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 1, "Sub-total fornecedor:" );
			imp.say( 30, "Quant. comprada -> " );
			imp.say( 50, Funcoes.copy( String.valueOf( fQtdFor ), 0, 6 ) );
			imp.say( 60, "Valor comprado -> " );
			imp.say( 78, Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( fVlrFor ) ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + sLinhaFina + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 1, "Total compras:" );
			imp.say( 30, "Quant. comprada -> " );
			imp.say( 50, Funcoes.copy( String.valueOf( fQtd ), 0, 6 ) );
			imp.say( 60, "Valor comprado -> " );
			imp.say( 78, Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( fVlr) ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinhaFina + "+" );

			imp.eject();
			imp.fechaGravacao();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		}catch( SQLException err ){
			
	}
		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}
	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar,  final String sCab ) {
		
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRComprasFor.jasper", "Relatório de Compras por fornecedor", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório Compras por fornecedor!" + err.getMessage(), true, con, err );
			}
		}
	}

	private void ehRef() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement( "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				sCodProd = "S".equals( rs.getString( "UsaRefProd" ) ) ? "REFPROD" : "CODPROD";
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage() );
		}		
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );
	}
}
