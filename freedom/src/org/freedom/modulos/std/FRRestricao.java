package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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


public class FRRestricao extends FRelatorio{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcCli = new ListaCampos( this );

	public FRRestricao(){
		
		setTitulo( "Relatório de clientes com restrição" );
		setAtribos( 80, 30, 350, 150 );
		montaTela();
		montaListaCampos();
	}
	
	private void montaTela(){
		
		adic( new JLabelPad("Cód.Cli"), 7, 5, 70, 20 ); 
		adic( txtCodCli, 7, 25, 70, 20 ); 
		adic( new JLabelPad("Nome do Cliente"),80, 5, 250, 20 );
		adic( txtNomeCli, 80, 25, 240, 20 );
		
	}
	
	private void montaListaCampos(){
	
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );
	}
	
	public void imprimir( boolean bVisualizar ) {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sFiltros = new StringBuffer();
		int iparam = 1;
		
		
		if(!"".equals( txtCodCli.getVlrString())){
			
			sWhere.append( "AND V.CODCLI=?" );
			sFiltros.append( "Cliente: " + txtNomeCli.getVlrString() );
		}
		
		sSQL.append( "SELECT FR.CODCLI, V.RAZCLI, FR.CODTPRESTR, FT.DESCTPRESTR, FR.DTRESTR, FR.DTCANCRESTR, " );
		sSQL.append( "FR.OBSRESTR FROM FNRESTRICAO FR, VDCLIENTE V, FNTIPORESTR FT WHERE " );
		sSQL.append( "FR.CODEMP=? AND FR.CODFILIAL=? AND " );
		sSQL.append( "FR.CODCLI=V.CODCLI AND FT.CODTPRESTR=FR.CODTPRESTR AND " );
		sSQL.append( "FT.CODEMP=FR.CODEMPTR AND FT.CODFILIAL=FR.CODFILIALTR " );
		
		if(!"".equals( txtCodCli.getVlrString())){
			sSQL.append( sWhere.toString() );
		}
		
		try {
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNRESTRICAO" ) );
			
			if(!"".equals( txtCodCli.getVlrString())){
				
				ps.setString( iparam++, txtCodCli.getVlrString() );
			}
			
			rs = ps.executeQuery();
			
		} catch ( Exception e ) {
		
			Funcoes.mensagemErro( this, "Erro ao buscar dados !\n" + e.getMessage());
			e.printStackTrace();
		}
		
		FPrinterJob dlGr = new FPrinterJob( "relatorios/FRRestricao.jasper", "Restricão de clientes", sFiltros.toString(), rs, null, this );
		
		if ( bVisualizar ) {
			
			dlGr.setVisible( true );
		}
		else {		
			
			try {				
				
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );				
			
			} catch ( Exception err ) {					
				
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de clientes com restrição!\n" + err.getMessage(), true, con, err );
			}
		}
	}
	
	public void setConexao( Connection cn ) {
		
		super.setConexao( cn );
		lcCli.setConexao( cn );
	}
}
