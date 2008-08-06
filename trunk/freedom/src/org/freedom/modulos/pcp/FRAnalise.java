package org.freedom.modulos.pcp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class FRAnalise extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	 
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtRefProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private ListaCampos lcProd = new ListaCampos( this, ""  );
	

	public FRAnalise(){
				
		super( false );
		setTitulo( "Análises" );
		setAtribos( 50, 50, 350, 200 );
		
		montaTela();
		montaListaCampos();
	}
	
	private void montaTela(){
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad( "Período:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );
		
		adic( lbPeriodo,7, 1, 80, 20 );
		adic( lbLinha,5, 10, 300, 45 );
		
		adic( new JLabelPad("De:"), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad("Até:"),152, 25, 37, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );
		adic( new JLabelPad("Cód.Prod"), 7, 55, 80, 20 );
		adic( txtCodProd, 7, 75, 70, 20 );
		adic( new JLabelPad("Descrição do produto"), 83, 55, 200, 20 );
		adic( txtDescProd, 83, 75, 220, 20 );
		
		
		Calendar cPeriodo = Calendar.getInstance();
	    txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) -30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}
	
	private void montaListaCampos() {

		/**************
		 *  Produto   * 
		 **************/
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

	}
	
	public void imprimir( boolean b ) {

		StringBuffer sql = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		
			if( txtCodProd.getVlrInteger() > 0 ){
				
				sWhere.append( "and op.codprod= " + txtCodProd.getVlrInteger() );
			}
			
			sql.append( "select op.codprod,pd.descprod,op.codlote,op.dtfabrop,op.dtvalidpdop, " );
			sql.append( "ta.desctpanalise,ea.vlrmin,ea.vlrmax,ea.especificacao,cq.vlrafer,cq.descafer,pr.imgassresp,cq.dtins, " );
			sql.append( "op.codop, eq.casasdec " );
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
			ps.setDate( 3, Funcoes.strDateToSqlDate( txtDataini.getVlrString()));
			ps.setDate( 4, Funcoes.strDateToSqlDate( txtDatafim.getVlrString()));
			rs = ps.executeQuery();
			
			sCab.append( "Perido: " + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() );
			
			imprimiGrafico( rs, b, sCab.toString() );
			
		} catch ( SQLException err ) {
			
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar análises", true, con, err );
		}
	}
	
	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar,  final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );
		hParam.put( "DESCPROD", txtDescProd.getVlrString() );
		hParam.put( "CODPROD",  txtCodProd.getVlrInteger()== 0 ? null : txtCodProd.getVlrInteger() );

		dlGr = new FPrinterJob( "relatorios/RelAnalise.jasper", "Relatório de Análises", sCab, rs, hParam, this );

		if ( bVisualizar ) {
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
	
	public void setConexao( Connection con ){
		
		super.setConexao( con );
		lcProd.setConexao( con );
	}
}
