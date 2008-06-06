/**
 * @version 04/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPConsPedido.java <BR>
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
 * Tela de consulta de pedidos.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFilho;

public class RPComissao extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelConsulta = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCampos = new JPanelPad();
	
	private final JPanelPad panelTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JCheckBoxPad cbNaoPagas = new JCheckBoxPad( "Não Pagas", "S", "N" );
	
	private final JCheckBoxPad cbPagasParcial = new JCheckBoxPad( "Pagas Parcialmente", "S", "N" );
	
	private final JCheckBoxPad cbPagas = new JCheckBoxPad( "Pagas", "S", "N" );
	
	private final JButton btPesquisar = new JButton( "Pesquisar", Icone.novo( "btObs.gif" ) );
	
	private final Tabela tabConsulta = new Tabela();
	
	private final ListaCampos lcVendedor = new ListaCampos( this, "" );
	
	
	public RPComissao() {

		super( false );
		setTitulo( "Manutenção de Comissões" );
		setAtribos( 50, 50, 700, 400 );
		
		montaListaCampos();		
		montaTela();
		adicBotaoSair();
		
		Calendar cal = Calendar.getInstance();			
		txtDtFim.setVlrDate( cal.getTime() );		
		cal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ) - 1, cal.get( Calendar.DATE ) );
		txtDtIni.setVlrDate( cal.getTime() );	
		
		btPesquisar.addActionListener( this );
	}
	
	private void montaListaCampos() {

		/************
		 * VENDEDOR *
		 ************/

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.vend.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "RP" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CODVEND" );
	}
	
	private void montaTela() {
		
		panelCampos.setPreferredSize( new Dimension( 400, 160 ) );
				
		panelCampos.adic( new JLabel( "Cód.vend." ), 7, 10, 100, 20 );
		panelCampos.adic( txtCodVend, 7, 30, 100, 20 );
		panelCampos.adic( new JLabel( "Nome do comissionado" ), 110, 10, 260, 20 );
		panelCampos.adic( txtNomeVend, 110, 30, 360, 20 );	
		
		Funcoes.setBordReq( txtCodVend );
		
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );		
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		
		panelCampos.adic( periodo, 20, 55, 60, 20 );
		panelCampos.adic( borda, 7, 65, 226, 75 );
		
		panelCampos.adic( txtDtIni, 20, 95, 80, 20 );
		panelCampos.adic( new JLabel( "até", SwingConstants.CENTER ), 100, 95, 40, 20 );
		panelCampos.adic( txtDtFim, 140, 95, 80, 20 );
		
		JLabel filtros = new JLabel( "Filtros", SwingConstants.CENTER );
		filtros.setOpaque( true );
		JLabel borda2 = new JLabel();
		borda2.setBorder( BorderFactory.createEtchedBorder() );
		
		panelCampos.adic( filtros, 250, 55, 60, 20 );
		panelCampos.adic( borda2, 240, 65, 230, 75 );
		
		panelCampos.adic( cbNaoPagas, 250, 75, 200, 20 );
		panelCampos.adic( cbPagasParcial, 250, 95, 200, 20 );
		panelCampos.adic( cbPagas, 250, 115, 200, 20 );
		
		panelCampos.adic( btPesquisar, 490, 30, 180, 30 );
				
		panelTabela.add( new JScrollPane( tabConsulta ), BorderLayout.CENTER );
		
		panelConsulta.add( panelCampos, BorderLayout.NORTH );		
		panelConsulta.add( panelTabela, BorderLayout.CENTER );
				
		pnCliente.add( panelConsulta, BorderLayout.CENTER );
		
		tabConsulta.adicColuna( "" );
		tabConsulta.adicColuna( "Pedido" );
		tabConsulta.adicColuna( "Item" );
		tabConsulta.adicColuna( "Comissão" );
		tabConsulta.adicColuna( "Pago" );
		tabConsulta.adicColuna( "Em aberto" );
		tabConsulta.adicColuna( "Pagamento" );
		tabConsulta.adicColuna( "" );
		
		tabConsulta.setTamColuna( 30, EConsulta.STATUS.ordinal() );
		tabConsulta.setTamColuna( 80, EConsulta.PEDIDO.ordinal() );
		tabConsulta.setTamColuna( 40, EConsulta.ITEM.ordinal() );
		tabConsulta.setTamColuna( 120, EConsulta.VALORCOMISS.ordinal() );
		tabConsulta.setTamColuna( 120, EConsulta.VALORPAGO.ordinal() );
		tabConsulta.setTamColuna( 120, EConsulta.VALORABERTO.ordinal() );
		tabConsulta.setTamColuna( 120, EConsulta.DATA.ordinal() );
		tabConsulta.setTamColuna( 30, EConsulta.SEL.ordinal() );
	}
	
	private void carregaTabela() {
		
		if ( txtCodVend.getVlrInteger() == 0 ) {
			Funcoes.mensagemInforma( this, "Informe o vendedor!" );
			txtCodVend.requestFocus();
			return;
		}
		
		if ( txtDtIni.getVlrDate() != null && txtDtFim.getVlrDate() != null ) {
			if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
				return;
			}
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			tabConsulta.limpa();
			
			/*StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT P.CODPED, P.DATAPED, P.VLRLIQPED " );
			sql.append( "FROM RPPEDIDO P " );
			sql.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? AND P.DATAPED BETWEEN ? AND ? " );
			sql.append( " ORDER BY DATAPED, CODPED, VLRLIQPED DESC " );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPCOTMOEDA" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			rs = ps.executeQuery();
			
			for ( int i=0; rs.next(); i++ ) {
				
				tabConsulta.adicLinha();
				tabConsulta.setValor( rs.getInt( "CODPED" ), i, EConsulta.PEDIDO.ordinal() );
				tabConsulta.setValor( rs.getDate( "DATAPED" ), i, EConsulta.EMISSAO.ordinal() );
				tabConsulta.setValor( rs.getBigDecimal( "VLRLIQPED" ), i, EConsulta.VALOR.ordinal() );				
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}*/
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "erro ao carregar tabela!" , true, con, e );
			e.printStackTrace();
		}		
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btPesquisar ) {
			
			carregaTabela();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcVendedor.setConexao( cn );
	}
	
	private enum EConsulta {
		STATUS,
		PEDIDO,
		ITEM,
		VALORCOMISS,
		VALORPAGO,
		VALORABERTO,
		DATA,
		SEL
	}
}
