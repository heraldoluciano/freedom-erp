/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPCotMoeda.java <BR>
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
 * Tela de cadastros de cotações de valores das moedas cadastradas no sistema.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class RPCotMoeda extends FDados implements InsertListener, CarregaListener {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelCotacao = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCampos = new JPanelPad();
	
	private final JPanelPad panelTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JTextFieldPad txtDataCot = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtValor = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtSingMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final Tabela tabCotacao = new Tabela();
	
	private final ListaCampos lcMoeda = new ListaCampos( this, "" );
	
	
	public RPCotMoeda() {

		super( false );
		setTitulo( "Cadastro de Moedas" );
		setAtribos( 50, 50, 425, 270 );
		
		montaListaCampos();
		
		montaTela();
		
		setListaCampos( false, "COTMOEDA", "RP" );
		
		lcCampos.addInsertListener( this );
		lcCampos.addCarregaListener( this );
		
		tabCotacao.adicColuna( "Data" );
		tabCotacao.adicColuna( "Valor" );
		tabCotacao.adicColuna( "Moeda" );
		
		tabCotacao.setTamColuna( 100, 0 );
		tabCotacao.setTamColuna( 200, 1 );
		tabCotacao.setTamColuna( 100, 2 );
	}
	
	private void montaListaCampos() {
		
		/*******************
		 *      MOEDA      *
		 *******************/
		
		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtSingMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "RP" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setTabelaExterna( lcMoeda );
	}
	
	private void montaTela() {
		
		panelCampos.setPreferredSize( new Dimension( 400, 110 ) );
		
		setPainel( panelCampos );
		
		adicCampo( txtDataCot, 7, 30, 100, 20, "DataCot", "Data", ListaCampos.DB_PK, true );
		adicCampo( txtValor, 110, 30, 150, 20, "ValorCot", "Valor", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodMoeda, 7, 70, 100, 20, "CodMoeda", "Cód.moeda", ListaCampos.DB_FK, txtSingMoeda, true );
		adicDescFK( txtSingMoeda, 110, 70, 285, 20, "SingMoeda", "Nome no singular" );
		
		panelTabela.add( new JScrollPane( tabCotacao ), BorderLayout.CENTER );
		
		panelCotacao.add( panelCampos, BorderLayout.NORTH );		
		panelCotacao.add( panelTabela, BorderLayout.CENTER );
				
		pnCliente.add( panelCotacao, BorderLayout.CENTER );
	}
	
	private void carregaTabela() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		
		try {
			
			tabCotacao.limpa();
			
			sql.append( "SELECT DATACOT, VALORCOT, CODMOEDA " );
			sql.append( "FROM RPCOTMOEDA " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? " );
			sql.append( "ORDER BY DATACOT DESC, CODMOEDA " );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPCOTMOEDA" ) );
			rs = ps.executeQuery();
			
			for ( int i=0; rs.next(); i++ ) {
				
				tabCotacao.adicLinha();
				tabCotacao.setValor( rs.getDate( "DATACOT" ), i, ECotacao.DATA.ordinal() );
				tabCotacao.setValor( rs.getBigDecimal( "VALORCOT" ), i, ECotacao.VALOR.ordinal() );
				tabCotacao.setValor( rs.getString( "CODMOEDA" ), i, ECotacao.MOEDA.ordinal() );				
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "erro ao carregar tabela!" );
			e.printStackTrace();
		}
		
	}

	public void afterInsert( InsertEvent e ) {
		
		txtDataCot.setVlrDate( Calendar.getInstance().getTime() );
	}

	public void beforeInsert( InsertEvent e ) { }

	public void afterCarrega( CarregaEvent e ) {

		carregaTabela();		
	}

	public void beforeCarrega( CarregaEvent e ) { }

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcMoeda.setConexao( cn );
		
		carregaTabela();
	}
	
	private enum ECotacao {
		DATA,
		VALOR,
		MOEDA
	}
}
