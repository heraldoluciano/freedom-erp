/*
 * Projeto: Freedom Pacote: org.freedom.modules.fnc Classe: @(#)FConsultaCheque.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR> na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR> Veja a Licença Pública Geral GNU para maiores detalhes. <BR> Você deve ter recebido uma cópia da Licença Pública
 * Geral GNU junto com este programa, se não, <BR> escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 */

package org.freedom.modulos.fnc.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;

/**
 * Consulta cheques emitidos/recebidos.
 * 
 * @author Setpoint Informática Ltda./Anderson Sanchez
 * @version 07/10/2010
 */
public class FConsultaCheque extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private static final Color GREEN = new Color( 45, 190, 60 );

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 140 );

	private JPanelPad panelDetail = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTabbedPanePad tabbedDetail = new JTabbedPanePad();

	private JPanelPad panelTotais = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelTabTotais = new JPanelPad( 700, 60 );

	private JPanelPad panelGridCheques = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JPanelPad panelTabCheques = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabContasPagas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelReceber = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelHistorico = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1, 10, 10 ) );

	// *** Geral

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDDDCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtFoneCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtEmailCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtContCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtAtivoCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JLabelPad lbAtivoCli = new JLabelPad( "Ativo", SwingConstants.CENTER );

	private JButtonPad btBuscar = new JButtonPad( "Buscar cheques", Icone.novo( "btExecuta.gif" ) );
	
	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );
	
	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	// *** Vendas

	private JTextFieldFK txtUltimaCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtVlrUltimaCompra = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDecFin );

	private JTextFieldFK txtTotalCompras = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDecFin );

	private JTextFieldFK txtTotalAberto = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDecFin );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTablePad tabCheques = new JTablePad();

	private JTablePad tabContasPagas = new JTablePad();

	private ImageIcon imgCancelado = Icone.novo( "clVencido.gif" );

	private ImageIcon imgPedido = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgFaturado = Icone.novo( "clPago.gif" );

	private ImageIcon imgColuna = null;

	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private ListaCampos lcBanco = new ListaCampos( this );

	private boolean carregandoVendas = false;

	private enum enum_grid_cheques {
		SITCHEQ, SEQCHEQ, CODBANC, AGENCIACHEQ, CONTACHEQ, NUMCHEQ, NOMEEMITCHEQ, NOMEFAVCHEQ, DTEMITCHEQ, DTVENCTOCHEQ, DTCOMPCHEQ, TIPOCHEQ, VLRCHEQ ;
	}

	private enum enum_grid_contas_pagas {
		STATUSITPAG, CODFOR, RAZFOR, CODPAG, NPARCPAG, DOCLANCAITPAG, OBSITPAG, DTITPAG, DTVENCITPAG, DTPAGOITPAG, VLRPARCITPAG, VLRPAGOITPAG, VLRAPAGITPAG ;
	}

	public FConsultaCheque() {

		super( false );
		setTitulo( "Consulta cheques", this.getClass().getName() );
		setAtribos( 20, 20, 780, 600 );
		int x = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getWidth() - getWidth() ) / 2;
		int y = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getHeight() - getHeight() ) / 2;
		setLocation( x, y );

		montaListaCampos();
		montaTela();

		lcCliente.addCarregaListener( this );

		btBuscar.addActionListener( this );
		tabCheques.addTabelaSelListener( this );
		tabCheques.addMouseListener( this );
		tabContasPagas.addMouseListener( this );
		btBuscar.addKeyListener( this );

		Calendar periodo = Calendar.getInstance();
		txtDatafim.setVlrDate( periodo.getTime() );
		periodo.set( Calendar.YEAR, periodo.get( Calendar.YEAR ) - 1 );
		txtDataini.setVlrDate( periodo.getTime() );
	}

	private void montaListaCampos() {

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtEmailCli, "EmailCli", "E-Mail", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtContCli, "ContCli", "Contato", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtAtivoCli, "AtivoCli", "ativo", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		
		
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco, null );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );



	}

	private void montaTela() {

		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );

		// ***** Cabeçalho

		JLabel periodo = new JLabel( "Período", SwingConstants.CENTER );
		periodo.setOpaque( true );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
/*
		panelMaster.adic( new JLabelPad( "Cód.Cli" ), 7, 5, 60, 20 );
		panelMaster.adic( txtCodCli, 7, 25, 60, 20 );
		panelMaster.adic( new JLabelPad( "Razão social do cliente" ), 70, 5, 340, 20 );
		panelMaster.adic( txtRazCli, 70, 25, 340, 20 );

		panelMaster.adic( new JLabelPad( "Contato" ), 413, 5, 100, 20 );
		panelMaster.adic( txtContCli, 413, 25, 100, 20 );

		panelMaster.adic( new JLabelPad( "DDD" ), 7, 45, 60, 20 );
		panelMaster.adic( txtDDDCli, 7, 65, 60, 20 );
		panelMaster.adic( new JLabelPad( "Fone" ), 70, 45, 75, 20 );
		panelMaster.adic( txtFoneCli, 70, 65, 75, 20 );
		panelMaster.adic( new JLabelPad( "e-mail" ), 148, 45, 262, 20 );
		panelMaster.adic( txtEmailCli, 148, 65, 262, 20 );

		panelMaster.adic( lbAtivoCli, 413, 65, 100, 20 );

		panelMaster.adic( new JLabelPad( "Cód.Prod." ), 7, 85, 60, 20 );
		panelMaster.adic( txtCodProd, 7, 105, 60, 20 );
		panelMaster.adic( new JLabelPad( "Descrição do produto" ), 70, 85, 340, 20 );
		panelMaster.adic( txtDescProd, 70, 105, 340, 20 );
*/
		panelMaster.adic( periodo, 540, 0, 60, 20 );
		panelMaster.adic( borda, 530, 10, 220, 45 );
		panelMaster.adic( txtDataini, 540, 25, 80, 20 );
		panelMaster.adic( new JLabel( "até", SwingConstants.CENTER ), 620, 25, 40, 20 );
		panelMaster.adic( txtDatafim, 660, 25, 80, 20 );

		panelMaster.adic( btBuscar, 530, 60, 220, 30 );

		txtFoneCli.setMascara( JTextFieldPad.MC_FONE );

		lbAtivoCli.setOpaque( true );
		lbAtivoCli.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbAtivoCli.setBackground( GREEN );
		lbAtivoCli.setForeground( Color.WHITE );
		/*
*/
		// ***** Detalhamento (abas)

		panelGeral.add( panelDetail, BorderLayout.CENTER );
		panelDetail.add( tabbedDetail );
		tabbedDetail.addTab( "Totais", panelTotais );
		// tabbedDetail.addTab( "Receber", panelReceber );
		// tabbedDetail.addTab( "Histórico", panelHistorico );

		// ***** Vendas

		panelTotais.add( panelTabTotais, BorderLayout.NORTH );
		panelTotais.add( panelGridCheques, BorderLayout.CENTER );
		panelGridCheques.add( panelTabCheques );
		panelGridCheques.add( panelTabContasPagas );

		panelTabCheques.setBorder( BorderFactory.createTitledBorder( "Cheques" ) );
		panelTabContasPagas.setBorder( BorderFactory.createTitledBorder( "Contas pagas" ) );
		panelTabContasPagas.setPreferredSize( new Dimension( 700, 120 ) );
/*
		panelTabVendas.adic( new JLabelPad( "Última Compra" ), 10, 10, 100, 20 );
		panelTabVendas.adic( txtUltimaCompra, 10, 30, 100, 20 );
		panelTabVendas.adic( new JLabelPad( "Valor última compra" ), 113, 10, 120, 20 );
		panelTabVendas.adic( txtVlrUltimaCompra, 113, 30, 120, 20 );
		panelTabVendas.adic( new JLabelPad( "Total de compras" ), 236, 10, 120, 20 );
		panelTabVendas.adic( txtTotalCompras, 236, 30, 120, 20 );
		panelTabVendas.adic( new JLabelPad( "Valor em aberto" ), 359, 10, 120, 20 );
		panelTabVendas.adic( txtTotalAberto, 359, 30, 120, 20 );
*/
		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = new Font( "Tomoha", Font.PLAIN, 11 );

//		panelTabTotais.adic( new JLabelPad( imgCancelado ), 600, 5, 20, 15 );
		JLabelPad canceladas = new JLabelPad( "canceladas" );
		canceladas.setForeground( statusColor );
		canceladas.setFont( statusFont );
	//	panelTabVendas.adic( canceladas, 620, 5, 100, 15 );

	//	panelTabVendas.adic( new JLabelPad( imgPedido ), 600, 20, 20, 15 );
		JLabelPad pedidos = new JLabelPad( "pedidos" );
		pedidos.setForeground( statusColor );
		pedidos.setFont( statusFont );
//		panelTabVendas.adic( pedidos, 620, 20, 100, 15 );

//		panelTabVendas.adic( new JLabelPad( imgFaturado ), 600, 35, 20, 15 );
		JLabelPad faturadas = new JLabelPad( "faturadas" );
		faturadas.setForeground( statusColor );
		faturadas.setFont( statusFont );
	//	panelTabVendas.adic( faturadas, 620, 35, 100, 15 );

//		SITCHEQ, SEQCHEQ, CODBANC, AGENCIACHEQ, CONTACHEQ, NUMCHEQ, NOMEEMITCHEQ, NOMEFAVCHEQ, DTEMITCHEQ, DTVENCTOCHEQ, DTCOMPCHEQ, TIPOCHEQ, VLRCHEQ ;
		
		tabCheques.adicColuna( "" );			// SITCHQ
		tabCheques.adicColuna( "Seq." );		// SEQCHEQ
		tabCheques.adicColuna( "Banco" );		// CODBANC
		tabCheques.adicColuna( "Agencia" );		// AGENCIAQUEQ
		tabCheques.adicColuna( "Conta" );		// CONTACHEQ
		tabCheques.adicColuna( "Cheque" );		// NUMCHEQ
		tabCheques.adicColuna( "Emitente" );	// NOMEEMITCHEQ
		tabCheques.adicColuna( "Favorecido" );	// NOMEFAVCHEQ
		tabCheques.adicColuna( "Emissão" );		// DTEMITCHEQ
		tabCheques.adicColuna( "Vencimento" );	// DTVENCTOCHEQ
		tabCheques.adicColuna( "Compensação" );	// DTCOMPCHEQ
		tabCheques.adicColuna( "Tipo" );		// TIPOCHEQ

		tabCheques.setTamColuna( 20		,enum_grid_cheques.SITCHEQ.ordinal() );
		tabCheques.setTamColuna( 30		,enum_grid_cheques.SEQCHEQ.ordinal() );
		tabCheques.setTamColuna( 50		,enum_grid_cheques.CODBANC.ordinal() );
		tabCheques.setTamColuna( 60		,enum_grid_cheques.AGENCIACHEQ.ordinal() );
		tabCheques.setTamColuna( 80		,enum_grid_cheques.CONTACHEQ.ordinal() );
		tabCheques.setTamColuna( 60		,enum_grid_cheques.NUMCHEQ.ordinal() );
		tabCheques.setTamColuna( 200	,enum_grid_cheques.NOMEEMITCHEQ.ordinal() );
		tabCheques.setTamColuna( 200	,enum_grid_cheques.NOMEFAVCHEQ.ordinal() );
		tabCheques.setTamColuna( 70		,enum_grid_cheques.DTEMITCHEQ.ordinal() );
		tabCheques.setTamColuna( 70		,enum_grid_cheques.DTVENCTOCHEQ.ordinal() );
		tabCheques.setTamColuna( 70		,enum_grid_cheques.DTCOMPCHEQ.ordinal() );
		tabCheques.setTamColuna( 60		,enum_grid_cheques.TIPOCHEQ.ordinal() );

		panelTabCheques.add( new JScrollPane( tabCheques ) );

		
//		STATUSITPAG, CODFOR, RAZFOR, CODPAG, NPARCPAG, DOCLANCAITPAG, OBSITPAG, DTITPAG, DTVENCITPAG, DTPAGOITPAG, VLRPARCITPAG, VLRPAGOITPAG, VLRAPAGITPAG ;
		
		
		tabContasPagas.adicColuna( "" 				);			//	STATUSITPAG
		tabContasPagas.adicColuna( "Cód.For." 		);			//	CODFOR	
		tabContasPagas.adicColuna( "Fornecedor"		);			//	RAZFOR
		tabContasPagas.adicColuna( "Cod.Pag." 		);			//	CODPAG
		tabContasPagas.adicColuna( "N.Parc." 		);			//	NPARCPAG
		tabContasPagas.adicColuna( "Doc." 			);			//	DOCLANCAITPAG
		tabContasPagas.adicColuna( "Obs." 			);			//	OBSITPAG
		tabContasPagas.adicColuna( "Emissão" 		);			//	DTITPAG
		tabContasPagas.adicColuna( "Vencimento" 	);			//	DTVENCITPAG
		tabContasPagas.adicColuna( "Pagamento" 		);			//	DTPAGOITPAG
		tabContasPagas.adicColuna( "Vlr.orig." 		);			//	VLRPARCITPAG
		tabContasPagas.adicColuna( "Vlr.pago" 		);			//	VLRPAGOITPAG
		tabContasPagas.adicColuna( "Vlr.a.pag." 	);			//	VLRPAGITPAG

		tabContasPagas.setTamColuna( 30		, enum_grid_contas_pagas.STATUSITPAG.ordinal() );
		tabContasPagas.setTamColuna( 50		, enum_grid_contas_pagas.CODFOR.ordinal() );
		tabContasPagas.setTamColuna( 200	, enum_grid_contas_pagas.RAZFOR.ordinal() );
		tabContasPagas.setTamColuna( 70		, enum_grid_contas_pagas.CODPAG.ordinal() );
		tabContasPagas.setTamColuna( 50		, enum_grid_contas_pagas.NPARCPAG.ordinal() );
		tabContasPagas.setTamColuna( 70		, enum_grid_contas_pagas.DOCLANCAITPAG.ordinal() );
		tabContasPagas.setTamColuna( 80		, enum_grid_contas_pagas.OBSITPAG.ordinal() );
		tabContasPagas.setTamColuna( 80		, enum_grid_contas_pagas.DTITPAG.ordinal() );
		tabContasPagas.setTamColuna( 90		, enum_grid_contas_pagas.DTVENCITPAG.ordinal() );
		tabContasPagas.setTamColuna( 90		, enum_grid_contas_pagas.DTPAGOITPAG.ordinal() );
		tabContasPagas.setTamColuna( 90		, enum_grid_contas_pagas.VLRPARCITPAG.ordinal() );
		tabContasPagas.setTamColuna( 90		, enum_grid_contas_pagas.VLRPAGOITPAG.ordinal() );
		tabContasPagas.setTamColuna( 90		, enum_grid_contas_pagas.VLRAPAGITPAG.ordinal() );		

		panelTabContasPagas.add( new JScrollPane( tabContasPagas ) );

		// ***** Rodapé

		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair() );
		
	}

	private void buscaCheques() {

		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append( "select ");
			sql.append( "ch.sitcheq, ch.seqcheq, ch.codbanc, ch.agenciacheq, ch.contacheq, ch.numcheq, ch.nomeemitcheq, ch.nomefavcheq, ch.dtemitcheq, ");
			sql.append( "ch.dtvenctocheq, ch.dtcompcheq, ch.tipocheq, ch.vlrcheq ");
			sql.append( "from fncheque ch ");
			sql.append( "where ");
			sql.append( "ch.codemp=? and ch.codfilial=? ");
			sql.append( "order by ch.dtemitcheq, ch.numcheq " );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNCHEQUE" ) );

			ResultSet rs = ps.executeQuery();

			carregandoVendas = true;
			tabCheques.limpa();
			tabContasPagas.limpa();

			int row = 0;

			while ( rs.next() ) {

				tabCheques.adicLinha();
				
				tabCheques.setValor( imgColuna, row, enum_grid_cheques.SITCHEQ.ordinal() );
				
				tabCheques.setValor( rs.getInt( enum_grid_cheques.SEQCHEQ.name() ), row, enum_grid_cheques.SEQCHEQ.ordinal() );
				tabCheques.setValor( rs.getString( enum_grid_cheques.CODBANC.name() ), row, enum_grid_cheques.CODBANC.ordinal() );
				tabCheques.setValor( rs.getString( enum_grid_cheques.AGENCIACHEQ.name() ), row, enum_grid_cheques.AGENCIACHEQ.ordinal() );
				tabCheques.setValor( rs.getString( enum_grid_cheques.CONTACHEQ.name() ), row, enum_grid_cheques.CONTACHEQ.ordinal() );
				tabCheques.setValor( rs.getInt( enum_grid_cheques.NUMCHEQ.name() ), row, enum_grid_cheques.NUMCHEQ.ordinal() );
				tabCheques.setValor( rs.getString( enum_grid_cheques.NOMEEMITCHEQ.name() ), row, enum_grid_cheques.NOMEEMITCHEQ.ordinal() );
				tabCheques.setValor( rs.getString( enum_grid_cheques.NOMEFAVCHEQ.name() ), row, enum_grid_cheques.NOMEFAVCHEQ.ordinal() );
				tabCheques.setValor( Funcoes.dateToStrDate( rs.getDate( enum_grid_cheques.DTEMITCHEQ.name() ) ), row, enum_grid_cheques.DTEMITCHEQ.ordinal() );
				tabCheques.setValor( Funcoes.dateToStrDate( rs.getDate( enum_grid_cheques.DTVENCTOCHEQ.name() ) ), row, enum_grid_cheques.DTVENCTOCHEQ.ordinal() );
				tabCheques.setValor( Funcoes.dateToStrDate( rs.getDate( enum_grid_cheques.DTCOMPCHEQ.name() ) ), row, enum_grid_cheques.DTCOMPCHEQ.ordinal() );
				tabCheques.setValor( rs.getString( enum_grid_cheques.TIPOCHEQ.name() ), row, enum_grid_cheques.TIPOCHEQ.ordinal() );
				tabCheques.setValor( rs.getBigDecimal( enum_grid_cheques.VLRCHEQ.name() ), row, enum_grid_cheques.VLRCHEQ.ordinal() );				

				row++;
			}

			rs.close();
			ps.close();
			con.commit();

			carregandoVendas = false;
			tabCheques.requestFocus();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void buscaContasPagas() {

		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append( "select ");
			sql.append( "ip.statusitpag, pg.codfor, fr.razfor, ip.codpag, ip.nparcpag, ip.doclancaitpag, ip.obsitpag, ");
			sql.append( "ip.dtitpag, ip.dtvencitpag, ip.dtpagoitpag, ip.vlrparcitpag, ip.vlrpagoitpag, ip.vlrpagoitpag, ");
			sql.append( "ip.vlrapagitpag ");
			sql.append( "from fnpagcheq pc, fnpagar pg, cpforneced fr, fnitpagar ip ");
			sql.append( "where ");
			sql.append( "pg.codemp=ip.codemp and pg.codfilial=ip.codfilial and pg.codpag=ip.codpag ");
			sql.append( "and fr.codemp=pg.codempfr and fr.codfilial=pg.codfilialfr and fr.codfor=pg.codfor ");
			sql.append( "and pc.codemp=ip.codemp and pc.codfilial=ip.codfilial and pc.codpag=ip.codpag and pc.nparcpag=ip.nparcpag ");
			sql.append( "and pc.codempch=? and pc.codfilial=? and pc.seqcheq=? " );

			sql.append( "order by ip.dtitpag " );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNCHEQUE" ) );
			ps.setInt( iparam++, (Integer) tabCheques.getValor( tabCheques.getSelectedRow(), enum_grid_cheques.SEQCHEQ.ordinal() ) );

			ResultSet rs = ps.executeQuery();

			tabContasPagas.limpa();

			int row = 0;

			while ( rs.next() ) {

				tabContasPagas.adicLinha();

				tabContasPagas.setValor( rs.getString( enum_grid_contas_pagas.STATUSITPAG.name() ), row, enum_grid_contas_pagas.STATUSITPAG.ordinal() );
				tabContasPagas.setValor( rs.getInt( enum_grid_contas_pagas.CODFOR.name() ), row, enum_grid_contas_pagas.CODFOR.ordinal() );
				tabContasPagas.setValor( rs.getString( enum_grid_contas_pagas.RAZFOR.name() ), row, enum_grid_contas_pagas.RAZFOR.ordinal() );
				tabContasPagas.setValor( rs.getInt( enum_grid_contas_pagas.CODPAG.name() ), row, enum_grid_contas_pagas.CODPAG.ordinal() );
				tabContasPagas.setValor( rs.getInt( enum_grid_contas_pagas.NPARCPAG.name() ), row, enum_grid_contas_pagas.NPARCPAG.ordinal() );
				tabContasPagas.setValor( rs.getString( enum_grid_contas_pagas.DOCLANCAITPAG.name() ), row, enum_grid_contas_pagas.DOCLANCAITPAG.ordinal() );
				tabContasPagas.setValor( rs.getString( enum_grid_contas_pagas.OBSITPAG.name() ), row, enum_grid_contas_pagas.OBSITPAG.ordinal() );
				tabContasPagas.setValor( rs.getDate( enum_grid_contas_pagas.DTITPAG.name() ), row, enum_grid_contas_pagas.DTITPAG.ordinal() );
				tabContasPagas.setValor( rs.getDate( enum_grid_contas_pagas.DTVENCITPAG.name() ), row, enum_grid_contas_pagas.DTVENCITPAG.ordinal() );
				tabContasPagas.setValor( rs.getDate( enum_grid_contas_pagas.DTPAGOITPAG.name() ), row, enum_grid_contas_pagas.DTPAGOITPAG.ordinal() );
				tabContasPagas.setValor( rs.getBigDecimal( enum_grid_contas_pagas.VLRPARCITPAG.name() ), row, enum_grid_contas_pagas.VLRPARCITPAG.ordinal() );
				tabContasPagas.setValor( rs.getBigDecimal( enum_grid_contas_pagas.VLRPAGOITPAG.name() ), row, enum_grid_contas_pagas.VLRPAGOITPAG.ordinal() );
				tabContasPagas.setValor( rs.getBigDecimal( enum_grid_contas_pagas.VLRAPAGITPAG.name() ), row, enum_grid_contas_pagas.VLRAPAGITPAG.ordinal() );

				row++;
			}

			rs.close();
			ps.close();
			con.commit();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btBuscar ) {
			buscaCheques();
		}
	}

	public void valorAlterado( TabelaSelEvent e ) {

		if ( e.getTabela() == tabCheques && tabCheques.getLinhaSel() > -1 && !carregandoVendas ) {
			buscaContasPagas( );
		}
	}

	public void mouseClicked( MouseEvent e ) {

		if ( e.getClickCount() == 2 ) {
			if ( e.getSource() == tabCheques && tabCheques.getLinhaSel() > -1 ) {
				FVenda venda = null;
				if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
					venda = (FVenda) Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
				}
				else {
					venda = new FVenda();
					Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
				}
//				venda.exec( (Integer) tabCheques.getValor( tabCheques.getLinhaSel(), enum_grid_cheques.CODVENDA.ordinal() ), (String) tabCheques.getValor( tabCheques.getLinhaSel(), enum_grid_cheques.TIPOVENDA.ordinal() ) );
			}
			else if ( e.getSource() == tabContasPagas && tabContasPagas.getLinhaSel() > -1 ) {
				FVenda venda = null;
				if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
					venda = (FVenda) Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
				}
				else {
					venda = new FVenda();
					Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
				}
	//			venda.exec( (Integer) tabCheques.getValor( tabCheques.getLinhaSel(), enum_grid_cheques.CODVENDA.ordinal() ), (Integer) tabContasPagas.getValor( tabContasPagas.getLinhaSel(), enum_grid_contas_pagas.ITEM.ordinal() ), (String) tabContasPagas.getValor( tabContasPagas.getLinhaSel(), enum_grid_contas_pagas.TIPOVENDA.ordinal() ) );
			}
		}
	}

	public void mouseEntered( MouseEvent e ) {

	}

	public void mouseExited( MouseEvent e ) {

	}

	public void mousePressed( MouseEvent e ) {

	}

	public void mouseReleased( MouseEvent e ) {

	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == btBuscar && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btBuscar.doClick();
		}
	}

	public void keyReleased( KeyEvent e ) {

	}

	public void keyTyped( KeyEvent e ) {

	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		if ( lcCliente == e.getListaCampos() ) {
			if ( "S".equals( txtAtivoCli.getVlrString().trim() ) ) {
				lbAtivoCli.setText( "Ativo" );
				lbAtivoCli.setBackground( GREEN );
			}
			else {
				lbAtivoCli.setText( "Inativo" );
				lbAtivoCli.setBackground( Color.RED );
			}
			buscaCheques();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCliente.setConexao( con );

	}
}
