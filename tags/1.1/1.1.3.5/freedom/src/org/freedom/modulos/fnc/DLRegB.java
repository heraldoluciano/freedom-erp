package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JScrollPane;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.SiaccUtil.RegB;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLRegB extends FFDialogo{
	
	private static final long serialVersionUID = 1L;
	
	private final Tabela tab = new Tabela();
	
	private JScrollPane spnTab = new JScrollPane( tab );
	
	private JPanelPad pnTab = new JPanelPad(  JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final int COL_CODCLI = 0;
	
	private final int COL_RAZCLI = 1;
	
	private final int COL_DATA = 2;
	
	private final int COL_AGENCIA = 3;
	
	private final int COL_IDENT = 4;
	
	private final int COL_CODMOV = 5;
	

	public DLRegB( final Component cOrig ) {

		super( cOrig );
		setTitulo( "Débito Automático" );
		setAtribos( 500, 300 );
		
		tab.adicColuna( "Cód.cli." );
		tab.adicColuna( "Razão social do cliente" );
		tab.adicColuna( "Data cadastro" );
		tab.adicColuna( "Agência" );
		tab.adicColuna( "Indent." );
		tab.adicColuna( "Cód.mov." );
		
		tab.setTamColuna( 50, COL_CODCLI );
		tab.setTamColuna( 190, COL_RAZCLI );
		tab.setTamColuna( 90, COL_DATA );
		tab.setTamColuna( 60, COL_AGENCIA );
		tab.setTamColuna( 100, COL_IDENT );
		tab.setTamColuna( 80, COL_CODMOV );
		
		c.add( pnTab );
		pnTab.add( spnTab, BorderLayout.CENTER );
		
		adicBotaoSair();
	}
	
	public boolean montaGrid( final List<RegB> regs, final Connection con ) {
		
		boolean retorno = false;
		Object[] cliente = new Object[2];
		String cpfcli = null;
		int row = 0;
		
		if ( regs != null && con != null ) {
			
			montagem: {
				setConexao( con );
				
				tab.limpa();
				
				for ( RegB reg : regs ) {
					
					try {
						tab.adicLinha();
						
						cpfcli = reg.getIdentCliEmp();
						
						cliente = getCliente(cpfcli);
						
						tab.setValor( cliente[0], row, COL_CODCLI );
						tab.setValor( cliente[1], row, COL_RAZCLI );
						tab.setValor( reg.getDataOpcao(), row, COL_DATA );
						tab.setValor( reg.getAgenciaDebt(), row, COL_AGENCIA );
						tab.setValor( reg.getIdentCliBco(), row, COL_IDENT );
						tab.setValor( reg.getCodMovimento(), row, COL_CODMOV );
						row++;
					}
					catch ( Exception e ) {
						Funcoes.mensagemErro( this, "Erro ao verificar registros(B)!\n"+e.getMessage(), true, con, e );
						e.printStackTrace();
						break montagem;
					}
				}
				
				retorno = true;
			}
		}
		
		return retorno;
	}
	
	private Object[] getCliente( final String cpfcnpj ) throws Exception {
		
		Object[] retorno = new Object[2];
		String sql = null;
		String cpf = null;
		String cnpj = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		if ( cpfcnpj != null ) {
			
			cpf = Funcoes.copy( cpfcnpj, 11 );
			cnpj = Funcoes.copy( cpfcnpj, 14 );
			sql = "SELECT CODCLI, RAZCLI FROM VDCLIENTE " +
					"WHERE PESSOACLI='F' AND CODEMP=? AND CODFILIAL=? AND CPFCLI=?";
			
			ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setString( 3, cpf );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				retorno[0] = rs.getInt( "CODCLI" );
				retorno[1] = rs.getString( "RAZCLI" );
			} else {
				sql = "SELECT CODCLI, RAZCLI FROM VDCLIENTE " +
					"WHERE PESSOACLI='J' AND CODEMP=? AND CODFILIAL=? AND CNPJCLI=?";
				ps = con.prepareStatement( sql );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setString( 3, cpf );
				rs = ps.executeQuery();
				if ( rs.next() ) {
					retorno[0] = rs.getInt( "CODCLI" );
					retorno[1] = rs.getString( "RAZCLI" );
				}
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit()) {
				con.commit();
			}
		}
		
		return retorno;
	}
}