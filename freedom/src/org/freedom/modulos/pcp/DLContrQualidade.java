package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLContrQualidade extends FFDialogo implements MouseListener{

	private static final long serialVersionUID = 1L;

	private JPanelPad pnControl = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCab = new JPanelPad( 400, 60 );

	private Tabela tabControl = new Tabela();

	private JScrollPane spnTabRec = new JScrollPane( tabControl );

	private JTextFieldPad txtCodOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSeqOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRefProdEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtSeqEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtQtdDist = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtQtdPrev = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtQtdDistpOp = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtQtdProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	String sEstAnalise = "";
	
	public DLContrQualidade( Connection con, boolean bPref ) {

		setTitulo( "Controle de qualidade" );
		setAtribos( 610, 380 );
		setConexao( con );
		montaTela( bPref );
	}

	private void montaTela( boolean bPref ) {

		pinCab.setPreferredSize( new Dimension( 400, 100 ) );
		pnControl.add( pinCab, BorderLayout.NORTH );
		pnControl.add( spnTabRec, BorderLayout.CENTER );
		c.add( pnControl, BorderLayout.CENTER );

		setPainel( pinCab );

		adic( new JLabelPad( "Nº.OP." ), 7, 0, 80, 20 );
		adic( txtCodOP, 7, 20, 80, 20 );
		
		adic( new JLabelPad( "Seq.OP." ), 90, 0, 80, 20 );
		adic( txtSeqOP, 90, 20, 80, 20 );
		
		 if( bPref ){
			    
			 adic( new JLabelPad("Referência"), 173, 0, 80, 20 );
			 adic( txtRefProdEst, 173, 20, 80, 20 );
		 }
		 else{
			 adic( new JLabelPad("Cód.prod."), 173, 0, 80, 20 );
			 adic( txtCodProdEst, 173, 20, 80, 20 );
		 }	 
		
		adic( new JLabelPad( "Qtd.prev." ), 256, 0, 80, 20 );
		adic( txtQtdPrev, 256, 20, 80, 20 );

		adic( new JLabelPad( "Qtd.prod." ), 339, 0, 80, 20 );
		adic( txtQtdProd, 339, 20, 80, 20 );

		adic( new JLabelPad( "Qtd.dist." ), 422, 0, 80, 20 );
		adic( txtQtdDistpOp, 422, 20, 80, 20 );

		adic( new JLabelPad( "Qtd.dist.at." ), 505, 0, 80, 20 );
		adic( txtQtdDist, 505, 20, 80, 20 );

		adic( new JLabelPad( "Seq.Est." ), 7, 40, 80, 20 );
		adic( txtSeqEst, 7, 60, 80, 20 );
		
		adic( new JLabelPad( "Descrição da estrutura principal" ), 90, 40, 250, 20 );
		adic( txtDescEst, 90, 60, 330, 20 );

		txtCodOP.setAtivo( false );
		txtSeqOP.setAtivo( false );
		txtCodProdEst.setAtivo( false );
		txtRefProdEst.setAtivo( false );
		txtSeqEst.setAtivo( false );
		txtDescEst.setAtivo( false );
		txtQtdProd.setAtivo( false );
		txtQtdPrev.setAtivo( false );
		txtQtdDist.setAtivo( false );
		txtQtdDistpOp.setAtivo( false );
		
		tabControl.adicColuna( "" );
		tabControl.adicColuna( "Seq.op.cq" );
		tabControl.adicColuna( "Cod.Estr.Análise" );
		tabControl.adicColuna( "Desc.Estr.Análise" );
		tabControl.adicColuna( "Vlr.Mín" );
		tabControl.adicColuna( "Vlr.Máx." );
		tabControl.adicColuna( "Valor aferido" );
		tabControl.adicColuna( "Desc.Aferido" );
		tabControl.adicColuna( "Tipo" );
	
		tabControl.setTamColuna( 10, 0 );
		tabControl.setTamColuna( 20, 1 );
		tabControl.setTamColuna( 50, 2 );
		tabControl.setTamColuna( 200, 3 );
		tabControl.setTamColuna( 70, 4 );
		tabControl.setTamColuna( 70, 5 );
		tabControl.setTamColuna( 80, 6 );
		tabControl.setTamColuna( 150, 7 );
		tabControl.setTamColuna( 30, 8 );
		
		tabControl.addMouseListener( this );
	}
	 
	public void carregaTabela( int iCodop, int iSeqop ) {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
		Vector<Object> vLinha = null;
		 
		sSQL.append( "SELECT PQ.SEQOPCQ, PQ.CODESTANALISE, PQ.VLRAFER, PQ.DESCAFER, PA.DESCTPANALISE, PA.TIPOEXPEC, " );
		sSQL.append( "PE.VLRMIN, PE.VLRMAX " );
		sSQL.append( "FROM PPOPCQ PQ, PPESTRUANALISE PE, PPTIPOANALISE PA WHERE PQ.CODEMP=? AND PQ.CODFILIAL=? AND " );
		sSQL.append( "PQ.CODOP=? AND PQ.SEQOP=? AND PE.CODEMP=PQ.CODEMPEA AND " );
		sSQL.append( "PE.CODFILIAL=PQ.CODFILIALEA AND PE.CODESTANALISE=PQ.CODESTANALISE AND " );
		sSQL.append( "PA.CODEMP=PE.CODEMPTA AND PA.CODFILIAL=PE.CODFILIALTA AND " );
		sSQL.append( "PA.CODTPANALISE=PE.CODTPANALISE" );		
	
		try {
			
		  	ps = con.prepareStatement( sSQL.toString() );
		  	ps.setInt( 1, Aplicativo.iCodEmp );
		  	ps.setInt( 2, ListaCampos.getMasterFilial( "PPOPCQ" ) );
		  	ps.setInt( 3, iCodop );
		  	ps.setInt( 4, iSeqop );

	  	  	rs = ps.executeQuery();
			
	  	  for( int i=0; rs.next(); i++ ){
	  		  
	  		 tabControl.adicLinha();
	  		 
	  		 tabControl.setValor( rs.getInt( "SEQOPCQ" ), i, 1 );
	  		 tabControl.setValor( rs.getInt( "CODESTANALISE" ), i, 2 );
	  		 tabControl.setValor( rs.getString( "DESCTPANALISE" ), i, 3 );
	  		 tabControl.setValor( rs.getBigDecimal( "VLRMIN" ), i, 4 );
	  		 tabControl.setValor( rs.getBigDecimal( "VLRMAX" ), i, 5 );
	  		 tabControl.setValor( rs.getBigDecimal( "VLRAFER" ), i, 6 );
	  		 tabControl.setValor( rs.getString( "DESCAFER" ), i, 7 );
	  		 tabControl.setValor( rs.getString( "TIPOEXPEC" ), i, 8 );
	  	  }
	  	  
	  	  rs.close();
	  	  ps.close();
	  	  
	  	  	if (!con.getAutoCommit()){
	  	  		con.commit();
	  	  	
	  	  }
		} catch ( SQLException err ) {
			err.printStackTrace();
		}
	}
	
	private void alteraQual(){
		
		int iLinha = tabControl.getLinhaSel();
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		
		try {
			
			String sDescAnalise = (String)tabControl.getValor( iLinha, 3 );
			BigDecimal bVlrMin = (BigDecimal)tabControl.getValor( iLinha, 4 ) == null ? new BigDecimal(0) : (BigDecimal)tabControl.getValor( iLinha, 4 );
			BigDecimal bVlrMax = (BigDecimal)tabControl.getValor( iLinha, 5 ) == null ? new BigDecimal(0) : (BigDecimal)tabControl.getValor( iLinha, 5 );
			BigDecimal bVlrAfer = (BigDecimal)tabControl.getValor( iLinha, 6 ) == null ? new BigDecimal(0) : (BigDecimal)tabControl.getValor( iLinha, 6 )  ; 
			String sAfer = (String)tabControl.getValor( iLinha, 7 ); 
			String sTipo = (String)tabControl.getValor( iLinha, 8 );
			
			String sUpdate = "";
			
			DLFechaQual dl = new DLFechaQual( sDescAnalise, sTipo, bVlrMin, bVlrMax, bVlrAfer, sAfer );
			dl.setVisible( true );
			
			if( "MM".equals( sTipo )){
				sUpdate = " VLRAFER=? ";
			}
			else if( "DT".equals( sTipo )){
				sUpdate = " DESCAFER=? ";
			}
			
			HashMap<String, Object> hsRet = dl.getValor();
			
			if( dl.OK ){
				
				sSQL.append( "UPDATE PPOPCQ SET" + sUpdate + "WHERE " );
				sSQL.append( "CODEMP=? AND CODFILIAL=? AND CODOP=? AND SEQOP=? AND SEQOPCQ=?" );
				
				ps = con.prepareStatement( sSQL.toString() );
				
				if( "MM".equals( sTipo )){
					
					BigDecimal vlrAfer = (BigDecimal)hsRet.get( "VLRAFER" );
					ps.setBigDecimal( 1, vlrAfer );
				}
				else if( "DT".equals( sTipo )){
					
					String descAfer = (String)hsRet.get( "DESCAFER" );
					ps.setString( 1, descAfer ); 
				}
				
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "PPOPCQ" ) );
				ps.setInt( 4, txtCodOP.getVlrInteger() );
				ps.setInt( 5, txtSeqOP.getVlrInteger() );
				ps.setInt( 6, (Integer) tabControl.getValor( iLinha, 1 ) );
				
				ps.executeUpdate();

				if ( !con.getAutoCommit() ) {
					con.commit();
				}
				
				tabControl.limpa();
				carregaTabela( txtCodOP.getVlrInteger(), txtSeqOP.getVlrInteger() );
				
			}
		} catch ( Exception e ) {
			
			e.getMessage();
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao salvar aferimento!" );
		}
	}
	
	public void carregaCampos(Object[] sValores){
		
		txtCodOP.setVlrInteger((Integer) sValores[0]); 
		txtSeqOP.setVlrInteger((Integer) sValores[1]); 
		txtCodProdEst.setVlrInteger((Integer) sValores[2]); 
		txtRefProdEst.setVlrString((String) sValores[3]); 
		txtSeqEst.setVlrInteger((Integer) sValores[4]); 
		txtDescEst.setVlrString((String) sValores[5]);
		txtQtdProd.setVlrBigDecimal((BigDecimal) sValores[6]); 
		txtQtdPrev.setVlrBigDecimal((BigDecimal) sValores[7]);
		txtQtdDist.setVlrBigDecimal(new BigDecimal(0));	
		
	}

	public void mouseClicked( MouseEvent mevt ) {
	
		 if ( mevt.getClickCount() == 2 ) {
			
			 if ( mevt.getSource() == tabControl && tabControl.getLinhaSel() >= 0 )
				
				alteraQual();
		}
	}

	public void mouseEntered( MouseEvent e ) {}

	public void mouseExited( MouseEvent e ) {}

	public void mousePressed( MouseEvent e ) {}

	public void mouseReleased( MouseEvent e ) {}
}
