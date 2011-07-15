/**
 * @version 15/07/2011 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms.view.frame.utility <BR>
 *         Classe: @(#)FGeraRomaneio.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * 
 *         Tela para geração de romaneio de carga.
 * 
 */

package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
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
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.fnc.view.dialog.utility.DLInfoPlanoPag;
import org.freedom.modulos.gms.view.frame.crud.detail.FCompra;
import org.freedom.modulos.gms.view.frame.crud.detail.FCotacaoPrecos;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;
import org.freedom.modulos.std.view.frame.crud.tabbed.FTransp;

public class FGeraRomaneio extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener, TabelaEditListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private static final Color GREEN = new Color( 45, 190, 64 );

	// *** Paineis tela

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 130 );

	private JPanelPad panelAbas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTabbedPanePad tabbedAbas = new JTabbedPanePad();

	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelLegenda = new JPanelPad();

	private JPanelPad panelNavegador = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelFiltros = new JPanelPad( "Filtros", Color.BLUE );

	// *** Paineis Detalhamento

	private JPanelPad panelDet = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelTabDet = new JPanelPad( 700, 0 );

	private JPanelPad panelGridDet = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabDetItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTablePad tabDet = null;

	// *** Labels

	private JLabelPad sepdet = new JLabelPad();

	// *** Geral

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtFoneCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtCelCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtContatoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos( this );

	// *** Botões
	private JButtonPad btAtualiza = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );

	private JButtonPad btEditar = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private ListaCampos lcProduto = new ListaCampos( this, "PD" );
	
	private ListaCampos lcTran = new ListaCampos( this, "TN" );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ImageIcon imgColuna = Icone.novo( "clAgdCanc.png" );
	
	private ImageIcon imgCancelada = Icone.novo( "clAgdCanc.png" );
	
	private ImageIcon imgEmitida = Icone.novo( "clAgdFin.png" );
	
	private ImageIcon imgAberto = Icone.novo( "clAgdPend.png" );
	
	private ImageIcon imgPedidoEmitido = Icone.novo( "clCheckYellow.png" );
	
	private ImageIcon imgPendente = Icone.novo( "clAgdPend.png" );
	
	// Enums

	private enum DETALHAMENTO {
		SEL, STATUS, STATUSTXT, CODVENDA, DOCVENDA, CODCLI, RAZCLI, CODTRAN, RAZTRAN, PLACA, VALOR, QTD, PESOLIQ, PESOBRUT;
	}
	
	public FGeraRomaneio() {

		super( false );

		setTitulo( "Painel de geração de Romaneio de Carga", this.getClass().getName() );
		setAtribos( 20, 20, 960, 600 );

		int x = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getWidth() - getWidth() ) / 2;
		int y = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getHeight() - getHeight() ) / 2;

		setLocation( x, y );
		setValoresPadrao();
		montaListaCampos();
		criaTabelas();
		montaTela();
		montaListeners();
		adicToolTips();

	}

	private void setValoresPadrao() {

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

	}

	private void montaListaCampos() {

		lcCliente.add( new GuardaCampo( txtCodCli		, "CodcLI"	, "Cód.Cli."				, ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli		, "RazCli"	, "Razão social do cliente"	, ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtNomeCli		, "NomeCli"	, "Nome do cliente"			, ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtContatoCli	, "ContCli"	, "Contato"					, ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtFoneCli		, "FoneCli"	, "Telefone"				, ListaCampos.DB_SI, false ) );

		lcCliente.setWhereAdic( "ATIVOCLI='S'" );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		lcCliente.setReadOnly( true );
		
		txtCodCli.setTabelaExterna( lcCliente, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		// * Produto (

		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );

		txtCodProd.setTabelaExterna( lcProduto, FProduto.class.getCanonicalName() );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );

		lcProduto.setReadOnly( true );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
		
		// * Transportadora

		lcTran.add( new GuardaCampo( txtCodTran			, "CodTran"			, "Cód.For."				, ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtNomeTran		, "NomeTran"		, "Nome da transportadora"	, ListaCampos.DB_SI, false ) );

		txtCodTran.setTabelaExterna( lcTran, FTransp.class.getCanonicalName() );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );

		lcTran.setReadOnly( true );
		lcTran.montaSql( false, "TRANSP", "VD" );


	}

	private void adicToolTips() {

		btAtualiza.setToolTipText( "Executa pesquisa - (F5)" );
		btEditar.setToolTipText( "Abre venda - (ENTER/SPACE)" );

	}

	private void montaListeners() {

		btAtualiza.addActionListener( this );
		btEditar.addActionListener( this );

		tabDet.addTabelaSelListener( this );
		tabDet.addMouseListener( this );
		tabDet.addKeyListener( this );

		this.addKeyListener( this );

	}

	private void montaTela() {

		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );

		// ***** Cabeçalho

		panelMaster.adic( panelFiltros	, 4		, 0		, 935	, 120 );
		
		panelFiltros.adic( btAtualiza	, 740	, 0		, 30	, 89 );
		panelFiltros.adic( txtDataini	, 7		, 20	, 70	, 20	, "Data Inicial" );
		panelFiltros.adic( txtDatafim	, 80	, 20	, 70	, 20	, "Data Final" );
		panelFiltros.adic( txtCodCli	, 153	, 20	, 70	, 20	, "Cód.Cli." );
		panelFiltros.adic( txtRazCli	, 226	, 20	, 200	, 20	, "Razão social do cliente" );

		panelFiltros.adic( txtCodTran	, 429	, 20	, 70	, 20	, "Cód.Tran." );
		panelFiltros.adic( txtNomeTran	, 502	, 20	, 200	, 20	, "Nome do transportador" );
		
		panelFiltros.adic( txtCodProd	, 153	, 60	, 70	, 20	, "Cód.Prod." );

		panelFiltros.adic( txtDescProd	, 226	, 60	, 200	, 20	, "Descrição do produto" );

		// ***** Abas

		panelGeral.add( panelAbas, BorderLayout.CENTER );
		panelGeral.add( panelAbas );
		panelAbas.add( tabbedAbas );

		tabbedAbas.addTab( "Detalhamento", panelDet );

		tabbedAbas.addChangeListener( this );

		// ***** Detalhamento

		panelDet.add( panelTabDet, BorderLayout.NORTH );
		panelDet.add( panelGridDet, BorderLayout.CENTER );
		panelGridDet.add( panelTabDetItens );

		panelTabDetItens.add( new JScrollPane( tabDet ) );

		// ***** Rodapé

		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = SwingParams.getFontpadmed();

		panelLegenda.setBorder( null );

		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );

		// panelNavegador.add( btExcluir );
		panelNavegador.add( btEditar );

		panelSouth.add( panelNavegador, BorderLayout.WEST );
		panelSouth.add( panelLegenda, BorderLayout.CENTER );
		panelSouth.add( adicBotaoSair(), BorderLayout.EAST );

	}

	private void criaTabelas() {

		// Tabela de detalhamento

		tabDet = new JTablePad();
		tabDet.setRowHeight( 21 );

		tabDet.adicColuna( "" ); 			// Seleção
		tabDet.adicColuna( "" ); 			// Status
		tabDet.adicColuna( "" ); 			// Status em texto
		tabDet.adicColuna( "Cod.Vend." ); 	// Codvenda

		tabDet.adicColuna( "Doc." ); 					// DocVenda
		tabDet.adicColuna( "Cód.Cli." ); 				// CodCli
		tabDet.adicColuna( "Razão social do cliente" ); // RazCli
		tabDet.adicColuna( "Cód.T." ); 					// CodTran
		tabDet.adicColuna( "Transportadora" ); 			// RazTran
		tabDet.adicColuna( "Placa" ); 					// Placa
		tabDet.adicColuna( "Valor" ); 					// Valor
		tabDet.adicColuna( "Qtd." ); 					// Qtd
		tabDet.adicColuna( "Peso Liquido" ); 			// Pesoliq
		tabDet.adicColuna( "Peso Bruto" ); 				// PesoBrut

		tabDet.setTamColuna( 21	, DETALHAMENTO.SEL.ordinal() );
		tabDet.setTamColuna( 20 , DETALHAMENTO.STATUS.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.STATUSTXT.ordinal() );
		tabDet.setTamColuna( 50, DETALHAMENTO.CODVENDA.ordinal() );
		tabDet.setTamColuna( 50, DETALHAMENTO.DOCVENDA.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.CODCLI.ordinal() );
		tabDet.setTamColuna( 150, DETALHAMENTO.RAZCLI.ordinal() );
		tabDet.setTamColuna( 40, DETALHAMENTO.CODTRAN.ordinal() );
		tabDet.setTamColuna( 150, DETALHAMENTO.RAZTRAN.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.PLACA.ordinal() );
		tabDet.setTamColuna( 70, DETALHAMENTO.VALOR.ordinal() );
		tabDet.setTamColuna( 70, DETALHAMENTO.QTD.ordinal() );
		tabDet.setTamColuna( 70, DETALHAMENTO.PESOLIQ.ordinal() );
		tabDet.setTamColuna( 70, DETALHAMENTO.PESOBRUT.ordinal() );

		tabDet.setColunaEditavel( DETALHAMENTO.SEL.ordinal(), true );
		
	}

	public void montaGrid() {

		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append( "select " );
			sql.append( "vd.statusvenda, vd.codvenda, vd.docvenda, vd.codcli, cl.razcli, fr.codtran, tr.raztran, fr.placafretevd, vd.vlrliqvenda, fr.qtdfretevd, fr.pesoliqvd, fr.pesobrutvd ");

			sql.append( "from vdvenda vd, vdcliente cl, vdfretevd fr, vdtransp tr " );
			
			sql.append( "where  " );
			
			sql.append( "cl.codemp=vd.codempcl and cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli and ");
			sql.append( "fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda and fr.tipovenda=vd.tipovenda and " );
			sql.append( "tr.codemp=fr.codemptn and tr.codfilial=fr.codfilialtn and tr.codtran=fr.codtran and ");
			sql.append( "vd.codemp=? and vd.codfilial=? and vd.dtsaidavenda between ? and ? " );

			StringBuffer status = new StringBuffer( "" );

			if ( txtCodCli.getVlrInteger() > 0 ) {
				sql.append( " and vd.codempcl=? and vd.codfilialcl=? and vd.codcli=? " );
			}
			
			if ( txtCodProd.getVlrInteger() > 0 ) {
				sql.append( " and exists( select codprod from vditvenda iv where iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.codvenda=vd.codvenda and iv.tipovenda=vd.tipovenda and iv.codemppd=? and iv.codfilialpd=? and iv.codprod=? )" );
			}
			
			if ( txtCodTran.getVlrInteger() > 0 ) {
				sql.append( " and tr.codemp=? and tr.codfilial=? and tr.codtran=? " );
			}

			System.out.println( "SQL:" + sql.toString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( txtCodCli.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcCliente.getCodEmp() );
				ps.setInt( iparam++, lcCliente.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );
			}
			
			if ( txtCodProd.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcProduto.getCodEmp() );
				ps.setInt( iparam++, lcProduto.getCodFilial() );
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );				
			}

			if ( txtCodTran.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcTran.getCodEmp() );
				ps.setInt( iparam++, lcTran.getCodFilial() );
				ps.setInt( iparam++, txtCodTran.getVlrInteger() );				
			}
			
			ResultSet rs = ps.executeQuery();

			tabDet.limpa();

			int row = 0;
			
			BigDecimal pesoliquido = new BigDecimal(0);
			BigDecimal pesobruto = new BigDecimal(0);

			String status_venda = null;

			while ( rs.next() ) {

				tabDet.adicLinha();
				
				pesoliquido = new BigDecimal(0);
				pesobruto = new BigDecimal(0);

				status_venda = rs.getString( "statusvenda" );

				if ( status_venda.length() > 0 && status_venda.substring( 0, 1 ).equals( "C" ) ) {
					imgColuna = imgCancelada;
					tabDet.setValor( false, row, DETALHAMENTO.SEL.ordinal() );
				}
				else if ( status_venda.length() > 0 && ( status_venda.equals( "V2" ) || status_venda.equals( "V3" ) ) ) {
					imgColuna = imgEmitida;
					tabDet.setValor( true, row, DETALHAMENTO.SEL.ordinal() );
				}
				else if ( status_venda.length() > 0 && status_venda.equals( "P2" ) ) {
					imgColuna = imgPendente;
					tabDet.setValor( false, row, DETALHAMENTO.SEL.ordinal() );
				}
				else if ( status_venda.length() > 0 && status_venda.equals( "P3" ) ) {
					imgColuna = imgPedidoEmitido;
					tabDet.setValor( true, row, DETALHAMENTO.SEL.ordinal() );
				}
				else if ( status_venda.equals( "P1" ) || status_venda.equals( "V1" ) ) {
					imgColuna = imgPendente;
					tabDet.setValor( false, row, DETALHAMENTO.SEL.ordinal() );
				}
				else {
					imgColuna = imgPendente;
					tabDet.setValor( false, row, DETALHAMENTO.SEL.ordinal() );
				}
						
				//SEL, STATUS, STATUSTXT, CODVENDA, DOCVENDA, CODCLI, RAZCLI, CODTRAN, RAZTRAN, PLACA, VALOR, QTD, PESOLIQ, PESOBRUT;
				
				tabDet.setValor( imgColuna							, row, DETALHAMENTO.STATUS.ordinal() );
		
				tabDet.setValor( status_venda						, row, DETALHAMENTO.STATUSTXT.ordinal() );
				
				tabDet.setValor( rs.getInt( "CODVENDA" )			, row, DETALHAMENTO.CODVENDA.ordinal() );
				tabDet.setValor( rs.getInt( "DOCVENDA" )			, row, DETALHAMENTO.DOCVENDA.ordinal() );
				tabDet.setValor( rs.getInt( "CODCLI" )				, row, DETALHAMENTO.CODCLI.ordinal() );
				tabDet.setValor( rs.getString( "RAZCLI" ).trim()	, row, DETALHAMENTO.RAZCLI.ordinal() );
				tabDet.setValor( rs.getInt( "CODTRAN" )				, row, DETALHAMENTO.CODTRAN.ordinal() );
				tabDet.setValor( rs.getString( "RAZTRAN" ).trim()	, row, DETALHAMENTO.RAZTRAN.ordinal() );
				tabDet.setValor( rs.getString( "PLACAFRETEVD" )		, row, DETALHAMENTO.PLACA.ordinal() );
				tabDet.setValor( rs.getBigDecimal( "VLRLIQVENDA" )	, row, DETALHAMENTO.VALOR.ordinal() );
				tabDet.setValor( rs.getBigDecimal( "QTDFRETEVD" )	, row, DETALHAMENTO.QTD.ordinal() );
				tabDet.setValor( rs.getBigDecimal( "PESOLIQVD" )	, row, DETALHAMENTO.PESOLIQ.ordinal() );
				tabDet.setValor( rs.getBigDecimal( "PESOBRUTVD" )	, row, DETALHAMENTO.PESOBRUT.ordinal() );
								
				row++;

			}
		
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btAtualiza ) {
			montaGrid();
		}
		else if ( e.getSource() == btEditar ) {
			abreVenda();
		}

	}

	public void valorAlterado( TabelaSelEvent e ) {

		/*
		 * if ( e.getTabela() == tabOrcamentos && tabOrcamentos.getLinhaSel() > -1 && !carregandoOrcamentos ) { buscaItensVenda( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), "V" ); }
		 */
	}

	private void abreVenda() {

		try {

			FVenda venda = null;
			
			if ( tabDet.getLinhaSel() > -1 ) {

				if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
					venda = (FVenda) Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
				}
				else {
					venda = new FVenda();
					Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
				}

				int codvenda = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.CODVENDA.ordinal() );

				venda.exec( codvenda );
			
			}
			else {
				Funcoes.mensagemInforma( this, "Não há nenhum registro selecionado para edição!" );
			}

		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	public void mouseClicked( MouseEvent mevt ) {

		JTablePad tabEv = (JTablePad) mevt.getSource();

		if ( mevt.getClickCount() == 2 ) {
			if ( tabEv == tabDet && tabEv.getLinhaSel() > -1 ) {

				abreVenda();

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

		if ( e.getSource() == btAtualiza && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btAtualiza.doClick();
		}
		else if ( e.getSource() == tabDet ) {
			if ( e.getKeyCode() == KeyEvent.VK_SPACE ) {
				btEditar.doClick();
			}
			else if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
				btEditar.doClick();
			}
			else if ( e.getKeyCode() == KeyEvent.VK_F5 ) {
				btAtualiza.doClick();
			}
		}

	}

	public void keyReleased( KeyEvent e ) {

	}

	public void keyTyped( KeyEvent e ) {

	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		montaGrid();

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		montaGrid();
		
		lcCliente.setConexao( con );
		lcProduto.setConexao( con );
		lcTran.setConexao( con );

	}

	public void valorAlterado( TabelaEditEvent evt ) {

	}

	private void selectAll( JTablePad tab ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}

	private void limpaNaoSelecionados( JTablePad tab ) {

		int linhas = tab.getNumLinhas();
		int pos = 0;
		try {
			for ( int i = 0; i < linhas; i++ ) {
				if ( tab.getValor( i, 0 ) != null && ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() ) { // xxx
					tab.tiraLinha( i );
					i--;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tabbedAbas ) {
			if ( tabbedAbas.getSelectedIndex() == 1 ) {
				// geraTabTemp();
			}
		}
	}

	private Integer getPlanoPag() {

		Integer codplanopag = null;

		try {

			DLInfoPlanoPag dl = new DLInfoPlanoPag( this, con );
			dl.setVisible( true );

			if ( dl.OK ) {
				codplanopag = dl.getValor();
				dl.dispose();
			}
			else {
				dl.dispose();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return codplanopag;
	}

	private void abrecompra( Integer codcompra ) {

		if ( fPrim.temTela( "Compra" ) == false ) {
			FCompra tela = new FCompra();
			fPrim.criatela( "Compra", tela, con );
			tela.exec( codcompra );
		}

	}

	private void abreSolicitacao( Integer codsolicitacao, Integer codfor, Integer renda ) {
		
		if ( fPrim.temTela( "Compra" ) == false ) {
			FCotacaoPrecos tela = new FCotacaoPrecos();
			fPrim.criatela( "Cotações de preços ", tela, con );
			
			tela.abreCotacao( codsolicitacao, codfor, renda  );
		}

	}

	
	private void geraRomaneio() {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;

		try {


		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}

	}
	
}
