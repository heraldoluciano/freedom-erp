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

package org.freedom.modulos.pcp;

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
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.std.FVenda;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import org.freedom.telas.SwingParams;


/**
 * Tela para planejamento e controle da produção.
 * 
 * @author Setpoint Informática Ltda./Anderson Sanchez
 * @version 03/12/2009
 */
public class FPCP extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener {

	private static final long serialVersionUID = 1L;
	
	private static final Color GREEN = new Color( 45, 190, 60 );

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 140 );

	private JPanelPad panelDetail = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTabbedPanePad tabbedDetail = new JTabbedPanePad();

	private JPanelPad panel = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );	

	private JPanelPad panelTabVendas = new JPanelPad( 700, 60 );

	private JPanelPad panelGridVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabVendasItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	
	private JPanelPad panelReceber = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelHistorico = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1, 10, 10 ) );
	
	// *** Geral

//	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

//	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

//	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

//	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JButtonPad btBuscar = new JButtonPad( "Buscar vendas", Icone.novo( "btExecuta.gif" ) );
	
	// *** Vendas 

	private JTextFieldFK txtQuantidadeVendida = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	
	private JTextFieldFK txtQuantidadeEstoque = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	
	private JTextFieldFK txtQuantidadeProducao = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	
	private JTextFieldFK txtQuantidadeProduzir = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private Tabela tabOrcamentos = new Tabela();
	
	private ImageIcon imgPendente = Icone.novo( "clVencido.gif" );

	private ImageIcon imgProducao = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgProduzido = Icone.novo( "clPago.gif" );

	private ImageIcon imgColuna = null;
	
	// *** Listacampos

//	private ListaCampos lcCliente = new ListaCampos( this, "CL" );
	
	private ListaCampos lcProd = new ListaCampos( this );
	
	private boolean carregandoOrcamentos = false;
	
	
	private enum VENDAS {
		STATUS, DATAAPROV, DATAENTREGA, CODORC, CODITORC, CODCLI, RAZCLI, CODPROD, DESCPROD, QTDAPROV, QTDESTOQUE, QTDEMPROD, QTDAPROD ;
	}
	
	public FPCP() {

		super( false );
		setTitulo( "Consulta de clientes", this.getClass().getName() );
		setAtribos( 20, 20, 860, 600 );
    	int x = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getWidth()-getWidth())/2;
    	int y = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getHeight()-getHeight())/2;
    	setLocation( x, y );
		
		montaListaCampos();
		montaTela();
		
		lcProd.addCarregaListener( this );
		btBuscar.addActionListener( this );
		tabOrcamentos.addTabelaSelListener( this );	
		tabOrcamentos.addMouseListener( this );	
		btBuscar.addKeyListener( this );
		
	}

	private void montaListaCampos() {
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

	}

	private void montaTela() {
		
		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
		
		// ***** Cabeçalho
		
		panelMaster.adic( new JLabelPad( "Cód.Prod." ), 7, 0, 60, 20 );
		panelMaster.adic( txtCodProd, 7, 20, 60, 20 );
		panelMaster.adic( new JLabelPad( "Descrição do produto" ), 70, 0, 340, 20 );
		panelMaster.adic( txtDescProd, 70, 20, 340, 20 );
		panelMaster.adic( btBuscar, 685, 10, 150, 30 );
				
//		***** Detalhamento (abas)
		
		panelGeral.add( panelDetail, BorderLayout.CENTER );
		panelGeral.add( panelDetail);
		panelDetail.add( tabbedDetail );
		tabbedDetail.addTab( "Vendas", panel );
		
		// ***** Vendas
		
		panel.add( panelTabVendas, BorderLayout.NORTH );
		panel.add( panelGridVendas, BorderLayout.CENTER );		
		panelGridVendas.add( panelTabVendasItens );
		
//		panelTabVendasItens.setBorder( BorderFactory.createTitledBorder( "Vendas" ) );
		
		panelTabVendas.adic( new JLabelPad( "Qtd.Vendida" ), 10, 5, 100, 20 );
		panelTabVendas.adic( txtQuantidadeVendida, 10, 25, 100, 20 );
		panelTabVendas.adic( new JLabelPad( "Qtd.Estoque" ), 113, 5, 120, 20 );
		panelTabVendas.adic( txtQuantidadeEstoque, 113, 25, 120, 20 );
		panelTabVendas.adic( new JLabelPad( "Qtd.Em Produção" ), 236, 5, 120, 20 );
		panelTabVendas.adic( txtQuantidadeProducao, 236, 25, 120, 20 );
		panelTabVendas.adic( new JLabelPad( "Qtd. Produzir" ), 359, 5, 120, 20 );
		panelTabVendas.adic( txtQuantidadeProduzir, 359, 25, 120, 20 );
		
		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = SwingParams.getFontpadmin(); 
				
		panelTabVendas.adic( new JLabelPad( imgPendente ), 600, 5, 20, 15 );

		JLabelPad canceladas = new JLabelPad( "Pendentes" );
		canceladas.setForeground( statusColor );
		canceladas.setFont( statusFont );
		panelTabVendas.adic( canceladas, 620, 5, 100, 15 );
		
		panelTabVendas.adic( new JLabelPad( imgProducao ), 600, 20, 20, 15 );
		JLabelPad pedidos = new JLabelPad( "Em Produção" );
		pedidos.setForeground( statusColor );
		pedidos.setFont( statusFont );
		panelTabVendas.adic( pedidos, 620, 20, 100, 15 );
		
		panelTabVendas.adic( new JLabelPad( imgProduzido ), 600, 35, 20, 15 );
		JLabelPad faturadas = new JLabelPad( "Produzidos" );
		faturadas.setForeground( statusColor );
		faturadas.setFont( statusFont );
		panelTabVendas.adic( faturadas, 620, 35, 100, 15 );
		
		
		
		tabOrcamentos.adicColuna( "" );
		tabOrcamentos.adicColuna( "Dt.Aprov." );
		tabOrcamentos.adicColuna( "Dt.Entrega" );
		tabOrcamentos.adicColuna( "Orc." );
		tabOrcamentos.adicColuna( "Item" );
		tabOrcamentos.adicColuna( "Cli." );
		tabOrcamentos.adicColuna( "Razão social do cliente" );
		tabOrcamentos.adicColuna( "Prod." );
		tabOrcamentos.adicColuna( "Descrição do produto" );
		tabOrcamentos.adicColuna( "Aprov." );
		tabOrcamentos.adicColuna( "Estoque" );
		tabOrcamentos.adicColuna( "Produção" );
		tabOrcamentos.adicColuna( "Produzir" );
		
		tabOrcamentos.setTamColuna( 15, VENDAS.STATUS.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.DATAAPROV.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.DATAENTREGA.ordinal() );
		tabOrcamentos.setTamColuna( 30, VENDAS.CODORC.ordinal() );
		tabOrcamentos.setTamColuna( 30, VENDAS.CODITORC.ordinal() );
		tabOrcamentos.setTamColuna( 40, VENDAS.CODCLI.ordinal() );
		tabOrcamentos.setTamColuna( 150, VENDAS.RAZCLI.ordinal() );
		tabOrcamentos.setTamColuna( 40, VENDAS.CODPROD.ordinal() );
		tabOrcamentos.setTamColuna( 150, VENDAS.DESCPROD.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDAPROV.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDESTOQUE.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDEMPROD.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDAPROD.ordinal() );
				
		panelTabVendasItens.add( new JScrollPane( tabOrcamentos ) );
		
		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair() );
	}
	
	private void buscaVendas() {
			
		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			
			sql.append( "oc.statusorc status, io.sitproditorc, io.dtaprovitorc dataaprov, ");
			sql.append( "io.dtaprovitorc + coalesce(oc.prazoentorc,0) dataentrega, oc.codorc, io.coditorc, cl.codcli, ");
			sql.append( "cl.razcli, pd.codprod, pd.descprod, coalesce(io.qtdaprovitorc,0) qtdaprov, ");

			sql.append( "coalesce((select first 1 mp.sldmovprod ");
			sql.append( "from eqmovprod mp ");
			sql.append( "where mp.codemp=pd.codemp and mp.codfilial=pd.codfilial and mp.codemppd=pd.codemp and mp.codfilialpd=pd.codfilial ");
			sql.append( "and mp.codprod=pd.codprod and mp.dtmovprod<=cast('today' as date) ");
			sql.append( "order by mp.dtmovprod desc, mp.codmovprod desc),0) qtdestoque, ");
			
			sql.append( "coalesce((select sum(op.qtdprevprodop) from ppop op ");
			sql.append( "where op.codemppd=pd.codemp and op.codfilialpd=pd.codfilial and op.codprod=pd.codprod ");
			sql.append( "and op.sitop in ('PE','BL') ),0) qtdemprod ");
			
			sql.append( "from vdorcamento oc ");
			
			sql.append( "left outer join vditorcamento io on ");
			sql.append( "io.codemp=oc.codemp and io.codfilial=oc.codfilial and io.codorc=oc.codorc and io.tipoorc=oc.tipoorc ");
			
			sql.append( "left outer join vdcliente cl on ");
			sql.append( "cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli ");
			
			sql.append( "left outer join eqproduto pd on ");
			sql.append( "pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod ");
			
			sql.append( "where oc.codemp=? and oc.codfilial=? and io.aprovitorc='S' ");
			
			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " and io.codemppd=? and io.codfilialpd=? and io.codprod=? " );				
			}
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			System.out.println("SQL:" + sql.toString());
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );

			if(txtCodProd.getVlrInteger()>0) {
				ps.setInt( iparam++, lcProd.getCodEmp() );
				ps.setInt( iparam++, lcProd.getCodFilial() );
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );								
			}			
			
			ResultSet rs = ps.executeQuery();		
			
			tabOrcamentos.limpa();
						
			int row = 0;
			while ( rs.next() ) {
				
				tabOrcamentos.adicLinha();
				

				tabOrcamentos.setValor( Funcoes.dateToStrDate( rs.getDate( VENDAS.DATAAPROV.toString() ) ), row, VENDAS.DATAAPROV.ordinal() );
				tabOrcamentos.setValor( Funcoes.dateToStrDate( rs.getDate( VENDAS.DATAENTREGA.toString() ) ), row, VENDAS.DATAENTREGA.ordinal() );
				tabOrcamentos.setValor( rs.getInt( VENDAS.CODORC.toString() ), row, VENDAS.CODORC.ordinal() );
				tabOrcamentos.setValor( rs.getInt( VENDAS.CODITORC.toString() ), row, VENDAS.CODITORC.ordinal() );
				tabOrcamentos.setValor( rs.getInt( VENDAS.CODCLI.toString() ), row, VENDAS.CODCLI.ordinal() );
				tabOrcamentos.setValor( rs.getString( VENDAS.RAZCLI.toString().trim() ), row, VENDAS.RAZCLI.ordinal() );
				tabOrcamentos.setValor( rs.getInt( VENDAS.CODPROD.toString() ), row, VENDAS.CODPROD.ordinal() );
				tabOrcamentos.setValor( rs.getString( VENDAS.DESCPROD.toString().trim() ), row, VENDAS.DESCPROD.ordinal() );

				BigDecimal qtdaprov = rs.getBigDecimal( VENDAS.QTDAPROV.toString() );
				BigDecimal qtdestoque = rs.getBigDecimal( VENDAS.QTDESTOQUE.toString() );
				BigDecimal qtdemprod = rs.getBigDecimal( VENDAS.QTDEMPROD.toString() );
				BigDecimal qtdaprod = qtdaprov.subtract( qtdestoque ).subtract( qtdemprod );
				
				
				if ( "PE".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgPendente;
				}
				else if ( "EP".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgProducao;
				}
				else if ( "PD".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgProduzido;
				}
												
				tabOrcamentos.setValor( imgColuna, row, VENDAS.STATUS.ordinal() );
				
				
				
				tabOrcamentos.setValor( Funcoes.bdToStr( qtdaprov, Aplicativo.casasDec ), row, VENDAS.QTDAPROV.ordinal() );
				tabOrcamentos.setValor( Funcoes.bdToStr( qtdestoque, Aplicativo.casasDec ), row, VENDAS.QTDESTOQUE.ordinal() );
				tabOrcamentos.setValor( Funcoes.bdToStr( qtdemprod, Aplicativo.casasDec ), row, VENDAS.QTDEMPROD.ordinal() );
				tabOrcamentos.setValor( Funcoes.bdToStr( qtdaprod.floatValue() < 0 ? new BigDecimal(0) : qtdaprod , Aplicativo.casasDec), row, VENDAS.QTDAPROD.ordinal() );
								
				row++;
				
			}

		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btBuscar ) {
			buscaVendas();
		}
	}

	public void valorAlterado( TabelaSelEvent e ) {
		/*		
		if ( e.getTabela() == tabOrcamentos && tabOrcamentos.getLinhaSel() > -1 && !carregandoOrcamentos ) {
			buscaItensVenda( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), "V" );
		}
		*/
	}

	public void mouseClicked( MouseEvent e ) {
		
		if ( e.getClickCount() == 2 ) {
			if ( e.getSource() == tabOrcamentos && tabOrcamentos.getLinhaSel() > -1 ) {
				FVenda venda = null;    
	    		if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
	    			venda = (FVenda)Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
	    		}
	    		else {
	    			venda = new FVenda();
	    			Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
	    		}    
//				venda.exec( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ) );
			}
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	public void keyPressed( KeyEvent e ) {
		
		if ( e.getSource() == btBuscar && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btBuscar.doClick();
		}
	}

	public void keyReleased( KeyEvent e ) { }

	public void keyTyped( KeyEvent e ) { }

	public void beforeCarrega( CarregaEvent e ) { }

	public void afterCarrega( CarregaEvent e ) {

		if ( lcProd == e.getListaCampos() ) {
			buscaVendas();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
//		lcCliente.setConexao( con );
		lcProd.setConexao( con );
		
	}
}
