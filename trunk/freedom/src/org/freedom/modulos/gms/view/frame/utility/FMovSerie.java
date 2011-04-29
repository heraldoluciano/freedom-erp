package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

/**
 * Acompanhamento de Numero de Series.
 * 
 * @version 1.0 21/04/2011
 * @author ffrizzo
 * 
 */
public class FMovSerie extends FRelatorio{

	private static final long serialVersionUID = 1L;
	
	private Container cTela = null;
	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	private JPanelPad pinCab = new JPanelPad( 560, 70 );
	
	private JTablePad tab = new JTablePad();
	private JScrollPane spnTab = new JScrollPane( tab );
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	private JTextFieldPad txtNumSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.gif" ) );
	
	private ListaCampos lcProd = new ListaCampos( this );
	private ListaCampos lcMovSerie = new ListaCampos( this );
	
	private PreparedStatement ps;
	private ResultSet rs;
	
	public FMovSerie() {
		
		setTitulo( "Mov. Numero de Série" );
		setAtribos( 10, 10, 750, 400 );
		
		txtCodProd.setRequerido( true );
		txtDataini.setRequerido( true );
		txtDatafim.setRequerido( true );
		
		cTela = getTela();
		cTela.add( pnCli, BorderLayout.CENTER );
		pnCli.add( pinCab, BorderLayout.NORTH );
		pnCli.add( spnTab, BorderLayout.CENTER );
		
		setPainel( pinCab );
		adic( new JLabelPad( "Período" ), 7, 5, 90, 20 );
		adic( txtDataini, 7, 25, 90, 20 );
		adic( new JLabelPad( "até" ), 100, 5, 90, 20 );
		adic( txtDatafim, 100, 25, 100, 20 );
		adic( new JLabelPad( "Cód.prod." ), 213, 5, 70, 20 );
		adic( new JLabelPad( "Descrição do produto" ), 286, 5, 260, 20 );
		adic( txtCodProd, 213, 25, 70, 20 );
		adic( txtDescProd, 286, 25, 260, 20 );
		adic( new JLabelPad( "Numero de Série" ), 550, 5, 120, 20 );
		adic( txtNumSerie, 550, 25, 120, 20 );
		
		adic( btExec, 675, 15, 30, 30 );
		btExec.setToolTipText( "Executa consulta." );
		btExec.addActionListener( this );
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		
		tab.adicColuna( "Data" );
		tab.adicColuna( "Tipo" );
		tab.adicColuna( "Tipo Mov. Série" );
		tab.adicColuna( "Num Série." );
//		tab.adicColuna( "Quantidade." );
		tab.setTamColuna( 90, 0 );		
		tab.setTamColuna( 90, 1 );
		tab.setTamColuna( 90, 2 );
		tab.setTamColuna( 90, 3 );
		
		this.montaListaCampos();
	}
	
	private void montaListaCampos(){
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
	}
	
	private void executar(){
		try {
			ResultSet rs = this.getResultSet();
			
			tab.limpa();
			int iLinha = 0;

			while ( rs.next() ) {
				tab.adicLinha();
				
				String tipoMovSerie = rs.getString( "TIPOMOVSERIE" ).trim();
				if("1".equals( tipoMovSerie )){
					tipoMovSerie = "Entrada";
				}else if("0".equals( tipoMovSerie )){
					tipoMovSerie = "Sem Movimento";
				}else if("-1".equals( tipoMovSerie )){
					tipoMovSerie = "Saida";
				}
				
				tab.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTMOVSERIE" ) ), iLinha, 0 );
				tab.setValor( rs.getString( "TIPOMOV" ), iLinha, 1 );
				tab.setValor( tipoMovSerie, iLinha, 2 );
				tab.setValor( rs.getString( "NUMSERIE" ), iLinha, 3 );
				
				iLinha++;
			}
			
			rs.close();
			ps.close();
			con.commit();
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carrregar a tabela MOVSERIE !\n" + err.getMessage(), true, con, err );
		}
	}
	
	@ Override
	public void imprimir( boolean bVisualizar ) {
		
		try {
			rs = this.getResultSet();
		
			StringBuilder filtros = new StringBuilder();
			filtros.append( "De " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " ");
			filtros.append( "Até "+ Funcoes.dateToStrDate(  txtDatafim.getVlrDate() ) );
		
			FPrinterJob dlGr = null;
			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
	
			dlGr = new FPrinterJob( "relatorios/RelMovNumSerie.jasper", "Relatório acompanhamento de numero de Série", filtros.toString(), rs, hParam, this );
	
			if ( bVisualizar ) {
				dlGr.setVisible( true );
			} else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro na impressão de relatório Compras Geral!" + err.getMessage(), true, con, err );
				}
			}
			
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carrregar a tabela MOVSERIE !\n" + err.getMessage(), true, con, err );
		}
	}
	
	public ResultSet getResultSet() throws SQLException{
		// Validando a data do filtro
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return null;
		}
		
		Integer codProd = txtCodProd.getVlrInteger().intValue();
		String numSerie = txtNumSerie.getVlrString();
		
		StringBuilder sql = new StringBuilder();
		sql.append( "select p.descprod, ms.codprod, ms.numserie, ms.tipomovserie, ");
		sql.append( "ms.dtmovserie, ms.docmovserie, tm.tipomov ");
		sql.append( "from eqmovserie ms ");
		sql.append( "inner join eqproduto p on ");
		sql.append( "(p.codemp = ms.codemp and p.codfilial = ms.codfilial and p.codprod = ms.codprod) ");
		sql.append( "left join eqtipomov tm on ");
		sql.append( "(tm.codemp = ms.codemp and tm.codfilial = ms.codfilial and tm.codtipomov = ms.codtipomov) ");
		sql.append( "where ms.codemp = ? and ms.codfilial = ? and ms.codprod = ? " );
		sql.append( "and dtmovserie between ? and ? " );
		
		if(numSerie != null && !"".equals(numSerie.trim())){
			sql.append( "and ms.numserie = ?" );
		}
		
		ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, Aplicativo.iCodFilial );
		ps.setInt( 3, codProd );
		ps.setDate( 4, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
		ps.setDate( 5, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
		
		if(numSerie != null && !"".equals(numSerie.trim())){
			ps.setString( 6, numSerie );
		}
		
		rs = ps.executeQuery();
		return rs;
	}
	
	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcProd.setConexao( cn );
	}
	
	@ Override
	public void actionPerformed( ActionEvent evt ) {
		if(evt.getSource() == btExec){
			this.executar();
		}else{
			super.actionPerformed( evt );
		}
	}

}
