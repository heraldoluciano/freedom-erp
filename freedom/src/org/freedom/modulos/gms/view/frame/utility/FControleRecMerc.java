/*
 * Projeto: Freedom
 * Pacote: org.freedom.modules.crm
 * Classe: @(#)FPCP.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> 
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.business.object.RecMerc;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTabbedPanePad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.gms.view.frame.crud.detail.FRecMerc;


/**
 * Painel de controle para recepção de materias primas
 * 
 * @author Setpoint Informática Ltda./Anderson Sanchez
 * @version 08/01/2010
 */

public class FControleRecMerc extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener, TabelaEditListener, ChangeListener {

	private static final long serialVersionUID = 1L;	
	private static final Color GREEN = new Color( 45, 190, 64 );

	// *** Paineis tela
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	private JPanelPad panelMaster = new JPanelPad( 700, 60 );
	private JPanelPad panelAbas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JTabbedPanePad tabbedAbas = new JTabbedPanePad();
	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );	
	private JPanelPad panelLegenda = new JPanelPad();	
	private JPanelPad panelNavegador = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad panelFiltros = new JPanelPad("Filtros", Color.BLUE);
	
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
	
	// *** Campos

//	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// ** Checkbox
	
	private JCheckBoxPad cbEtapa0 =  new JCheckBoxPad( "Pendentes", "S", "N" );
	private JCheckBoxPad cbEtapa1 =  new JCheckBoxPad( "Pesagem 1", "S", "N" );
	private JCheckBoxPad cbEtapa2 =  new JCheckBoxPad( "Descarregamento", "S", "N" );
	private JCheckBoxPad cbEtapa3 =  new JCheckBoxPad( "Pesagem 2", "S", "N" );
	
	// ** Legenda
	
	private ImageIcon imgPesagem1 = Icone.novo( "blAzul1_18x18.png" );
	private ImageIcon imgDescarregamento = Icone.novo( "blAzul2_18x18.png" );
	private ImageIcon imgPesagem2 = Icone.novo( "blAzul3_18x18.png" );
	private ImageIcon imgColuna = null;
	
	// *** Listacampos

//	private ListaCampos lcCliente = new ListaCampos( this, "CL" );
//	private ListaCampos lcProd = new ListaCampos( this );

	// *** Botões
	private JButtonPad btRecarregar = new JButtonPad( "Recarregar", Icone.novo( "btExecuta.gif" ) );
	private JButtonPad btNovo = new JButtonPad( Icone.novo( "btNovo.gif" ) );	
//	private JButtonPad btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );
	private JButtonPad btEditar = new JButtonPad( Icone.novo( "btEditar.gif" ) );
	private JButtonPad btCompra = new JButtonPad( Icone.novo( "btCompra.gif" ) );
		
	// Enums
	
	private enum DETALHAMENTO {
		STATUS, TICKET, CODTIPORECMERC, DATA, HORA, PLACA, CODTRAN, NOMETRAN; 
	}
	
	public FControleRecMerc() {
		
		super( false );
		
		setTitulo( "Painel de recepção de matéria prima", this.getClass().getName() );
		setAtribos( 20, 20, 740, 400 );
		
    	int x = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getWidth()-getWidth())/2;
    	int y = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getHeight()-getHeight())/2;
    	
    	setLocation( x, y );
		
		montaListaCampos();
		criaTabelas();
		montaTela();				
		montaListeners();
		carregaValoresPadrao();
	
	}

	private void carregaValoresPadrao() {
		cbEtapa0.setVlrString( "S" );
		cbEtapa1.setVlrString( "S" );
		cbEtapa2.setVlrString( "S" );
	}
	
	private void montaListaCampos() {
		
	}
	
	private void montaListeners() {
		
		btRecarregar.addActionListener( this );
		btNovo.addActionListener( this );

		btEditar.addActionListener( this );
		btCompra.addActionListener( this );
		
		tabDet.addTabelaSelListener( this );	
		tabDet.addMouseListener( this );	
		
	}

	private void montaTela() {
		
		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
		
		// ***** Cabeçalho
		
		panelFiltros.adic( cbEtapa0, 2, 0, 90, 20 );
		panelFiltros.adic( cbEtapa1, 95, 0, 100, 20 );
		panelFiltros.adic( cbEtapa2, 193, 0, 140, 20 );
		panelFiltros.adic( cbEtapa3, 335, 0, 100, 20 );
		
		panelMaster.adic( panelFiltros, 4, 0, 450, 52 );

		panelMaster.adic( btRecarregar, 595, 8, 123, 42 );
		
//		***** Abas
		
		panelGeral.add( panelAbas, BorderLayout.CENTER );
		panelGeral.add( panelAbas);
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
		
		JLabelPad labelPesagem1 = new JLabelPad( "Pesagem 1" );
		labelPesagem1.setForeground( statusColor );
		labelPesagem1.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgPesagem1 ), 60, 5, 18, 18 );
		panelLegenda.adic( labelPesagem1, 84, 7, 80, 15 );
		
		JLabelPad labelDescarregamento = new JLabelPad( "Descarregamento" );
		labelDescarregamento.setForeground( statusColor );
		labelDescarregamento.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgDescarregamento ), 164, 5, 18, 18 );
		panelLegenda.adic( labelDescarregamento, 188, 7, 100, 15 );
		
		JLabelPad faturadas = new JLabelPad( "Pesagem 2" );
		faturadas.setForeground( statusColor );
		faturadas.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgPesagem2 ), 294, 5, 18, 18 );		
		panelLegenda.adic( faturadas, 318, 7, 100, 15 );
		
		panelLegenda.setBorder( null );		
		
		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );

		panelNavegador.add( btNovo );
//		panelNavegador.add( btExcluir );
		panelNavegador.add( btEditar );
		panelNavegador.add( btCompra );
		
		panelSouth.add( panelNavegador, BorderLayout.WEST);
		panelSouth.add( panelLegenda, BorderLayout.CENTER );		
		panelSouth.add( adicBotaoSair(), BorderLayout.EAST);
				
	}
	
	private void criaTabelas() {
		
		// Tabela de detalhamento
		
		tabDet = new JTablePad();
		tabDet.setRowHeight( 21 );

		tabDet.adicColuna( "" );
		tabDet.adicColuna( "Ticket" );
		tabDet.adicColuna( "Cód.Tipo.Rec.Merc." );
		
		tabDet.adicColuna( "Data" );
		tabDet.adicColuna( "Hora" );
		tabDet.adicColuna( "Placa" );
		tabDet.adicColuna( "Cod.T." );
		tabDet.adicColuna( "Transportador" );
				
		tabDet.setTamColuna( 21, DETALHAMENTO.STATUS.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.TICKET.ordinal() );
		// Coluna invisível
		tabDet.setTamColuna( 60, DETALHAMENTO.DATA.ordinal() );
		tabDet.setTamColuna( 50, DETALHAMENTO.HORA.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.PLACA.ordinal() );
		tabDet.setTamColuna( 40, DETALHAMENTO.CODTRAN.ordinal() );		
		tabDet.setTamColuna( 400, DETALHAMENTO.NOMETRAN.ordinal() );
		
		tabDet.setColunaInvisivel( 2 );
		
	}
	
	public void montaGrid() {
			
		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			sql.append( "rm.ticket, rm.codtiporecmerc, rm.status, rm.dtins data, rm.hins hora, rm.placaveiculo placa, rm.codtran, tr.nometran ");			
			sql.append( "from eqrecmerc rm, vdtransp tr ");
			sql.append( "where tr.codemp=rm.codemptr and tr.codfilial=rm.codfilialtr and tr.codtran=rm.codtran ");
			sql.append( "and rm.codemp=? and rm.codfilial=? " );
			
			StringBuffer status = new StringBuffer("");

			
			if("S".equals(cbEtapa0.getVlrString())) {
				status.append( " 'PE' ");
			}
			if("S".equals(cbEtapa1.getVlrString())) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( " 'E1' " );
			}
			if("S".equals(cbEtapa2.getVlrString())) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( " 'E2' " );
			}
			if("S".equals(cbEtapa3.getVlrString())) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( " 'FN' " );
			}

			if ( status.length() > 0 ) {
				sql.append( " and rm.status in (" );
				sql.append( status );
				sql.append( ") ");
			}
		
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			
			ResultSet rs = ps.executeQuery();		
			
			tabDet.limpa();
						
			int row = 0;
			
			while ( rs.next() ) {
				
				tabDet.adicLinha();
				

				if ( "PE".equals( rs.getString( "status" ) ) ) {
					imgColuna = imgPesagem1;
				}
				else if ( "EP".equals( rs.getString( "status" ) ) ) {
					imgColuna = imgDescarregamento;
				}
				else if ( "FN".equals( rs.getString( "status" ) ) ) {
					imgColuna = imgPesagem2;
				}				
				
				tabDet.setValor( imgColuna, row, DETALHAMENTO.STATUS.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.TICKET.toString().trim() ), row, DETALHAMENTO.TICKET.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODTIPORECMERC.toString().trim() ), row, DETALHAMENTO.CODTIPORECMERC.ordinal() );
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DATA.toString() ) ), row, DETALHAMENTO.DATA.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.HORA.toString().trim() ), row, DETALHAMENTO.HORA.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.PLACA.toString().trim() ), row, DETALHAMENTO.PLACA.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODTRAN.toString().trim() ), row, DETALHAMENTO.CODTRAN.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.NOMETRAN.toString().trim() ), row, DETALHAMENTO.NOMETRAN.ordinal() );
				
				row++;
				
			}

			// Permitindo reordenação
			
			if(row>0) {
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabDet.getModel());    
				tabDet.setRowSorter(sorter);				   
			}
			else {
				tabDet.setRowSorter( null );
			}
			
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btRecarregar ) {
			montaGrid();
		}
		else if ( e.getSource() == btNovo ) {
			novoRecebimento();
		}
		else if ( e.getSource() == btEditar ) {
			abreRecMerc();
		}
		else if( e.getSource() == btCompra ) {
			geraCompra();
		}

	}

	private void novoRecebimento() {
		
		FRecMerc recebimento = new FRecMerc( true );
		
		try {

			Aplicativo.telaPrincipal.criatela( "Recepção de mercadorias", recebimento, con );
			recebimento.setTelaMae( this );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void valorAlterado( TabelaSelEvent e ) {
		/*		
		if ( e.getTabela() == tabOrcamentos && tabOrcamentos.getLinhaSel() > -1 && !carregandoOrcamentos ) {
			buscaItensVenda( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), "V" );
		}
		*/
	}

	private void abreRecMerc() {
		
		FRecMerc recmerc = null;
	    
		try {
			
			if(tabDet.getLinhaSel()>-1) {
				
				if ( Aplicativo.telaPrincipal.temTela( FRecMerc.class.getName() ) ) {
					recmerc = (FRecMerc) Aplicativo.telaPrincipal.getTela( FRecMerc.class.getName() );
				}
				else {
					recmerc = new FRecMerc(false);
					Aplicativo.telaPrincipal.criatela( "Recepção de mercadorias", recmerc, con );
				}    	
				
				int ticket =  (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.TICKET.ordinal() ) ;
				int codtiporecmerc = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.CODTIPORECMERC.ordinal() ) ;
		
				recmerc.exec(ticket, codtiporecmerc, this);
			}
			else {
				Funcoes.mensagemInforma( this, "Não há nenhum registro selecionado para edição!" );
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void mouseClicked( MouseEvent mevt ) {
		JTablePad tabEv = (JTablePad) mevt.getSource();
		
		if ( mevt.getClickCount() == 2 ) {					
			if( tabEv == tabDet && tabEv.getLinhaSel() > -1 ) {
				
				abreRecMerc();
				
			}
		}				
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	public void keyPressed( KeyEvent e ) {
		
		if ( e.getSource() == btRecarregar && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btRecarregar.doClick();
		}
	}

	public void keyReleased( KeyEvent e ) { }

	public void keyTyped( KeyEvent e ) { }

	public void beforeCarrega( CarregaEvent e ) { }

	public void afterCarrega( CarregaEvent e ) {

//		if ( lcProd == e.getListaCampos() || lcCliente == e.getListaCampos() ) {
			montaGrid();
//		}
		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		montaGrid();
//		lcCliente.setConexao( con );
//		lcProd.setConexao( con );
		
	}

	public void valorAlterado( TabelaEditEvent evt ) {

		
	}
	
	private void selectAll(JTablePad tab) {
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}
	
	private void limpaNaoSelecionados(JTablePad tab) {
		int linhas = tab.getNumLinhas();
		int pos = 0;
		try {			
			for ( int i = 0; i < linhas; i++ ) {
				if ( tab.getValor( i, 0 )!=null && ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() ) { //xxx
					tab.tiraLinha( i );
					i--;
				}					
			}									
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tabbedAbas ) {
			if ( tabbedAbas.getSelectedIndex() == 1 ) {
//				geraTabTemp();	
			}
		}
	}
	
	private void informaPlanoPag() {
		
	}
	
	private void geraCompra() {
		
		StringBuilder sql = new StringBuilder();
		
		Integer ticket = null;
		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		String unid = null;
		PreparedStatement ps = null;
		
		RecMerc recmerc = null;
		
		try {
			
			if(tabDet.getLinhaSel()>-1) {

				ticket = (Integer) tabDet.getValor( tabDet.getLinhaSel(), DETALHAMENTO.TICKET.ordinal() );
			
				recmerc = new RecMerc(ticket, con);
				
				if(Funcoes.mensagemConfirma( this, "Confirma a geração do pedido de compra para o ticket nro.:" + ticket.toString() + " ?" )==JOptionPane.YES_OPTION) {

					recmerc.geraCompra();
					
				}	
				
			}
			else {
				Funcoes.mensagemInforma( this, "Selecione um ticket no grid!" );
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

