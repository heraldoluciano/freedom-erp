/**
 * @version 05/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPNota.java <BR>
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
 * Tela de faturamento de pedidos.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;

public class RPFaturamento extends FDialogo {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelPedido = new JPanelPad();

	private final JPanelPad panelTabItens = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JTextFieldPad txtCodPed = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtDataPed = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtVlrLiqPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JButton buscarItens = new JButton( Icone.novo( "btExecuta.gif" ) );
	
	private final JButton gerarFaturamento = new JButton( Icone.novo( "btGerar.gif" ) );
	
	private final JButton salvarFaturamento = new JButton( Icone.novo( "btSalvar2.gif" ) );
	
	private final JButton gerarComissao = new JButton( Icone.novo( "btProcessos.gif" ) );
	
	private final Tabela tab = new Tabela(); 

	private final ListaCampos lcPedido = new ListaCampos( this, "" );

	private final ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private final ListaCampos lcVendedor = new ListaCampos( this, "VD" );

	private final ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private final ListaCampos lcFornecedor = new ListaCampos( this, "FO" );


	public RPFaturamento() {

		super();
		setTitulo( "Faturamento de pedidos" );
		setAtribos( 700, 500 );
		setToFrameLayout();

		montaListaCampos();
		montaTabela();

		montaTela();		
		
		txtCodPed.addKeyListener( this );
	}

	private void montaListaCampos() {

		/**********
		 * PEDIDO *
		 **********/

		lcPedido.add( new GuardaCampo( txtCodPed, "CODPED", "Cód.ped.", ListaCampos.DB_PK, false ) );		
		lcPedido.add( new GuardaCampo( txtDataPed, "DATAPED", "Data ped.", ListaCampos.DB_SI, false ) );	
		lcPedido.add( new GuardaCampo( txtCodCli, "CODCLI", "Cód.cli.", ListaCampos.DB_FK, txtRazCli, false ) );
		lcPedido.add( new GuardaCampo( txtCodVend, "CODVEND", "Cód.vend.", ListaCampos.DB_FK, txtNomeVend, false ) );
		lcPedido.add( new GuardaCampo( txtCodPlanoPag, "CODPLANOPAG", "Cód.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag, false ) );
		lcPedido.add( new GuardaCampo( txtCodFor, "CODFOR", "Cód.for.", ListaCampos.DB_FK, txtRazFor, false ) );
		lcPedido.add( new GuardaCampo( txtVlrLiqPed, "VLRLIQPED", "Liquido", ListaCampos.DB_SI, false ) );
		lcPedido.montaSql( false, "PEDIDO", "RP" );
		lcPedido.setQueryCommit( false );
		lcPedido.setReadOnly( true );
		txtCodPed.setPK( true );
		txtCodPed.setNomeCampo( "CODPED" );
		txtCodPed.setListaCampos( lcPedido );

		/***********
		 * CLIENTE *
		 ***********/

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.vend.", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "RP" );
		lcCliente.setQueryCommit( false );
		lcCliente.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CODCLI" );

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

		/**********************
		 * PLANO DE PAGAMENTO *
		 **********************/

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "RP" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CODPLANOPAG" );

		/**************
		 * FORNECEDOR *
		 **************/

		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFornecedor );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CODFOR" );
	}
	
	private void montaTabela() {

		tab.adicColuna( "" );
		tab.adicColuna( "Item" );
		tab.adicColuna( "Cód.prod." );
		tab.adicColuna( "Descrição do produto" );
		tab.adicColuna( "Qtd. Faturada" );
		tab.adicColuna( "Qtd. Pendente" );
		tab.adicColuna( "Vlr. Faturado" );
		tab.adicColuna( "Vlr. Comissão" );
		tab.adicColuna( "% Comissão" );
		tab.adicColuna( "Data Faturado" );
		
		tab.setTamColuna( 20, ETabNota.STATUS.ordinal() );
		tab.setTamColuna( 40, ETabNota.ITEM.ordinal() );
		tab.setTamColuna( 60, ETabNota.CODPROD.ordinal() );
		tab.setTamColuna( 200, ETabNota.DESCPROD.ordinal() );
		tab.setTamColuna( 80, ETabNota.QTDFATURADA.ordinal() );
		tab.setTamColuna( 80, ETabNota.QDTPENDENTE.ordinal() );
		tab.setTamColuna( 100, ETabNota.VLRFATURADO.ordinal() );
		tab.setTamColuna( 100, ETabNota.VLRCOMIS.ordinal() );
		tab.setTamColuna( 60, ETabNota.PERCCOMIS.ordinal() );
		tab.setTamColuna( 80, ETabNota.DATAFAT.ordinal() );	
	}

	private void montaTela() {

		panelPedido.setPreferredSize( new Dimension( 450, 160 ) );
		panelPedido.setBorder( BorderFactory.createEtchedBorder() );

		panelPedido.adic( new JLabel( "Pedido" ), 7, 10, 80, 20 );
		panelPedido.adic( txtCodPed, 7, 30, 80, 20 );
		panelPedido.adic( new JLabel( "Data" ), 90, 10, 120, 20 );
		panelPedido.adic( txtDataPed, 90, 30, 120, 20 );
		panelPedido.adic( new JLabel( "Valor líquido" ), 213, 10, 127, 20 );
		panelPedido.adic( txtVlrLiqPed, 213, 30, 127, 20 );
		
		panelPedido.adic( buscarItens, 446, 20, 50, 30 );
		panelPedido.adic( gerarFaturamento, 506, 20, 50, 30 );
		panelPedido.adic( salvarFaturamento, 566, 20, 50, 30 );
		panelPedido.adic( gerarComissao, 626, 20, 50, 30 );
		
		panelPedido.adic( new JLabel( "Cód.cli." ), 7, 60, 80, 20 );
		panelPedido.adic( txtCodCli, 7, 80, 80, 20 );
		panelPedido.adic( new JLabel( "Razão social do cliente" ), 90, 60, 250, 20 );
		panelPedido.adic( txtRazCli, 90, 80, 250, 20 );

		panelPedido.adic( new JLabel( "Cód.vend." ), 343, 60, 80, 20 );
		panelPedido.adic( txtCodVend, 343, 80, 80, 20 );
		panelPedido.adic( new JLabel( "Nome do vendedor" ), 426, 60, 250, 20 );
		panelPedido.adic( txtNomeVend, 426, 80, 250, 20 );
		
		panelPedido.adic( new JLabel( "Cód.p.pag." ), 7, 100, 80, 20 );
		panelPedido.adic( txtCodPlanoPag, 7, 120, 80, 20 );
		panelPedido.adic( new JLabel( "Plano de pagamento" ), 90, 100, 250, 20 );
		panelPedido.adic( txtDescPlanoPag, 90, 120, 250, 20 );

		panelPedido.adic( new JLabel( "Cód.for." ), 343, 100, 980, 20 );
		panelPedido.adic( txtCodFor, 343, 120, 80, 20 );
		panelPedido.adic( new JLabel( "Razão social do fornecedor" ), 426, 100, 250, 20 );
		panelPedido.adic( txtRazFor, 426, 120, 250, 20 );
		
		c.add( new JScrollPane( tab ), BorderLayout.CENTER );		
		c.add( panelPedido, BorderLayout.NORTH );
		
		
		txtDataPed.setAtivo( false );
		txtVlrLiqPed.setAtivo( false );
		txtCodCli.setAtivo( false );
		txtCodVend.setAtivo( false );
		txtCodPlanoPag.setAtivo( false );
		txtCodFor.setAtivo( false );
		
		buscarItens.setToolTipText( "Buscar Itens" );
		gerarFaturamento.setToolTipText( "Criar Faturamento" );
		salvarFaturamento.setToolTipText( "Salvar Alterações" );
		gerarComissao.setToolTipText( "Gerar Comissões" );
		
		buscarItens.addActionListener( this );
		gerarFaturamento.addActionListener( this );
		salvarFaturamento.addActionListener( this );
		gerarComissao.addActionListener( this );	

		buscarItens.setEnabled( false );
		gerarFaturamento.setEnabled( false );
		salvarFaturamento.setEnabled( false );
		gerarComissao.setEnabled( false );
	}
	
	private void carregaTabela() {		

		buscarItens.setEnabled( false );
		gerarFaturamento.setEnabled( false );
		salvarFaturamento.setEnabled( false );
		gerarComissao.setEnabled( false );
		
		tab.limpa();
				
		List<Object[]> rows = getFaturamento();
		
		if ( rows.size() > 0 ) {
			for( Object[] row : rows ) {			
				tab.adicLinha( row );
			}
			
			gerarComissao.setEnabled( true );
		}	
		else {
			
			buscarItens.setEnabled( true );
		}		
	}
	
	private List<Object[]> getFaturamento() {
		
		List<Object[]> itens = new ArrayList<Object[]>();
		
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT" );
			sql.append( "  F.CODITPED, F.QTDFATURADO, F.VLRFATURADO, F.QTDPENDENTE, F.PERCCOMISFAT, F.VLRCOMISFAT, F.DTFATURADO, F.STATUSFAT," ); 
			sql.append( "  I.CODITPED, I.CODPROD, PD.DESCPROD, I.QTDITPED " );
			sql.append( "FROM" );
			sql.append( "  RPFATURAMENTO F, RPITPEDIDO I, RPPRODUTO PD " );
			sql.append( "WHERE" );
			sql.append( "  F.CODEMP=? AND F.CODFILIAL=? AND F.CODPED=? AND" );
			sql.append( "  I.CODEMP=F.CODEMP AND I.CODFILIAL=F.CODFILIAL AND I.CODPED=F.CODPED AND I.CODITPED=F.CODITPED AND" );
			sql.append( "  PD.CODEMP=I.CODEMPPD AND PD.CODFILIAL=I.CODFILIALPD AND PD.CODPROD=I.CODPROD " );
			sql.append( "ORDER BY I.CODITPED" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPFATURAMENTO" ) );
			ps.setInt( 3, txtCodPed.getVlrInteger() );
			
			ResultSet rs = ps.executeQuery();
			
			while( rs.next() ) {
				
				itens.add( new Object[] {
						rs.getString( "STATUSFAT" ),
						rs.getInt( "CODITPED" ),
						rs.getInt( "CODPROD" ),
						rs.getString( "DESCPROD" ),
						rs.getBigDecimal( "QTDFATURADO" ).setScale( AplicativoRep.casasDec, BigDecimal.ROUND_HALF_UP ),
						rs.getBigDecimal( "QTDPENDENTE" ).setScale( AplicativoRep.casasDec, BigDecimal.ROUND_HALF_UP ),
						rs.getBigDecimal( "VLRFATURADO" ).setScale( AplicativoRep.casasDecFin, BigDecimal.ROUND_HALF_UP ),
						rs.getBigDecimal( "VLRCOMISFAT" ).setScale( AplicativoRep.casasDecFin, BigDecimal.ROUND_HALF_UP ),
						rs.getBigDecimal( "PERCCOMISFAT" ).setScale( AplicativoRep.casasDec, BigDecimal.ROUND_HALF_UP ),
						rs.getDate( "DTFATURADO" )
				} );
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return itens;
	}
	
	private List<Object[]> getItensPedido() {
		
		List<Object[]> itens = new ArrayList<Object[]>();
		
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT" );
			sql.append( "  I.CODITPED, I.CODPROD, PD.DESCPROD, I.QTDITPED, I.VLRLIQITPED, I.VLRPAGITPED, I.PERCPAGITPED " );
			sql.append( "FROM" );
			sql.append( "  RPITPEDIDO I, RPPRODUTO PD " );
			sql.append( "WHERE" );
			sql.append( "  I.CODEMP=? AND I.CODFILIAL=? AND I.CODPED=? AND" );
			sql.append( "  PD.CODEMP=I.CODEMPPD AND PD.CODFILIAL=I.CODFILIALPD AND PD.CODPROD=I.CODPROD " );
			sql.append( "ORDER BY I.CODITPED" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPPEDIDO" ) );
			ps.setInt( 3, txtCodPed.getVlrInteger() );
			
			ResultSet rs = ps.executeQuery();
			
			while( rs.next() ) {
				
				itens.add( new Object[] {
						"",
						rs.getInt( "CODITPED" ),
						rs.getInt( "CODPROD" ),
						rs.getString( "DESCPROD" ),
						rs.getBigDecimal( "QTDITPED" ).setScale( AplicativoRep.casasDec, BigDecimal.ROUND_HALF_UP ),
						new BigDecimal( "0.00" ).setScale( AplicativoRep.casasDec, BigDecimal.ROUND_HALF_UP ),
						rs.getBigDecimal( "VLRLIQITPED" ).setScale( AplicativoRep.casasDecFin, BigDecimal.ROUND_HALF_UP ),
						rs.getBigDecimal( "VLRPAGITPED" ).setScale( AplicativoRep.casasDecFin, BigDecimal.ROUND_HALF_UP ),
						rs.getBigDecimal( "PERCPAGITPED" ).setScale( AplicativoRep.casasDec, BigDecimal.ROUND_HALF_UP ),
						Calendar.getInstance().getTime()
				} );
			}
			
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return itens;
	}
	
	private void buscarItens() {
		
		tab.limpa();			
		List<Object[]> rows = getItensPedido();		
		
		if ( rows.size() > 0 ) {
			
			for( Object[] row : rows ) {			
				tab.adicLinha( row );
			}
			
			buscarItens.setEnabled( false );
			gerarFaturamento.setEnabled( true );
		}
	}
	
	private void gerarFaturamento() {
	
		int opt = Funcoes.mensagemConfirma( null, 
				"Caso não tenha sido alterado a quantidade faturada para o item,\n" +
				"o faturamento será criado com a quantidade do pedido." );
		
		if ( opt == JOptionPane.OK_OPTION ) {
			
			StringBuilder insert = new StringBuilder();
			insert.append( "INSERT INTO RPFATURAMENTO " );
			insert.append( "(CODEMP, CODFILIAL, CODPED, CODITPED, " );
			insert.append( "QTDFATURADO, VLRFATURADO, QTDPENDENTE, " );
			insert.append( "PERCCOMISFAT, VLRCOMISFAT, DTFATURADO ) " );
			insert.append( "VALUES" );
			insert.append( "(?,?,?,?,?,?,?,?,?,?)" );
			
			PreparedStatement ps;
			int parameterIndex;

			try {
				for ( int i = 0; i < tab.getNumLinhas(); i++ ) {

					parameterIndex = 1;
					ps = con.prepareStatement( insert.toString() );
					ps.setInt( parameterIndex++, AplicativoRep.iCodEmp );
					ps.setInt( parameterIndex++, ListaCampos.getMasterFilial( "RPFATURAMENTO" ) );
					ps.setInt( parameterIndex++, txtCodPed.getVlrInteger() );
					ps.setInt( parameterIndex++, (Integer) tab.getValor( i, 1 ) );
					ps.setBigDecimal( parameterIndex++, (BigDecimal) tab.getValor( i, 4 ) );
					ps.setBigDecimal( parameterIndex++, (BigDecimal) tab.getValor( i, 6 ) );
					ps.setBigDecimal( parameterIndex++, (BigDecimal) tab.getValor( i, 5 ) );
					ps.setBigDecimal( parameterIndex++, (BigDecimal) tab.getValor( i, 8 ) );
					ps.setBigDecimal( parameterIndex++, (BigDecimal) tab.getValor( i, 7 ) );
					ps.setDate( parameterIndex++, Funcoes.dateToSQLDate( Calendar.getInstance().getTime() ) );

					ps.executeUpdate();
				}
				
				Funcoes.mensagemInforma( null, "Faturamento criado para pedido " + txtCodPed.getVlrInteger() );
				gerarComissao.setEnabled( true );
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			} catch ( Exception e ) {				
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao gerar faturamento!\n" + e.getMessage() );
				
				try {
					con.rollback();
				} catch ( SQLException e1 ) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void actionPerformed( ActionEvent e ) {
		
		if ( e.getSource() == buscarItens ) {
			buscarItens();	
		}
		if ( e.getSource() == gerarFaturamento ) {
			gerarFaturamento();
		}
		else {
			super.actionPerformed( e );
		}
	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == txtCodPed && e.getKeyCode() == KeyEvent.VK_ENTER ) {				
			carregaTabela();
		}
		else {
			super.keyPressed( e );
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcCliente.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcFornecedor.setConexao( cn );
		lcPedido.setConexao( cn );
	}
	
	private enum ETabNota {
		STATUS,
		ITEM,
		CODPROD,
		DESCPROD,
		QTDFATURADA,
		QDTPENDENTE,
		VLRFATURADO,
		VLRCOMIS,
		PERCCOMIS,
		DATAFAT;
	}

}
