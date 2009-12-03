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
import javax.swing.SwingConstants;

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

	private JPanelPad panelVendas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelTabVendas = new JPanelPad( 700, 60 );

	private JPanelPad panelGridVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JPanelPad panelTabVendasNotas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

//	private JPanelPad panelTabItensVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelReceber = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelHistorico = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1, 10, 10 ) );
	
	// *** Geral

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

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
	
//	private Tabela tabItensVendas = new Tabela();
	
	private ImageIcon imgItemPendente = Icone.novo( "clVencido.gif" );

	private ImageIcon imgItemAprovado = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgItemProducao = Icone.novo( "clPago.gif" );
	
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
		setAtribos( 20, 20, 780, 600 );
    	int x = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getWidth()-getWidth())/2;
    	int y = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getHeight()-getHeight())/2;
    	setLocation( x, y );
		
		montaListaCampos();
		montaTela();
		
//		lcCliente.addCarregaListener( this );
		lcProd.addCarregaListener( this );
		btBuscar.addActionListener( this );
		tabOrcamentos.addTabelaSelListener( this );	
		tabOrcamentos.addMouseListener( this );	
//		tabItensVendas.addMouseListener( this );	
		btBuscar.addKeyListener( this );
		
		Calendar periodo = Calendar.getInstance();
		txtDatafim.setVlrDate( periodo.getTime() );
		periodo.set( Calendar.YEAR, periodo.get( Calendar.YEAR ) - 1 );
		txtDataini.setVlrDate( periodo.getTime() );
	}

	private void montaListaCampos() {
	
//		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
//		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
//		lcCliente.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
//		lcCliente.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
//		lcCliente.add( new GuardaCampo( txtEmailCli, "EmailCli", "E-Mail", ListaCampos.DB_SI, false ) );
//		lcCliente.add( new GuardaCampo( txtContCli, "ContCli", "Contato", ListaCampos.DB_SI, false ) );
//		lcCliente.add( new GuardaCampo( txtAtivoCli, "AtivoCli", "ativo", ListaCampos.DB_SI, false ) );
//		txtCodCli.setTabelaExterna( lcCliente );
//		txtCodCli.setNomeCampo( "CodCli" );
//		txtCodCli.setFK( true );
//		lcCliente.setReadOnly( true );
//		lcCliente.montaSql( false, "CLIENTE", "VD" );		
		
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
		
		// ***** Cabeçario

		JLabel periodo = new JLabel( "Período", SwingConstants.CENTER );
		periodo.setOpaque( true );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		
//		panelMaster.adic( new JLabelPad( "Cód.Cli" ), 7, 5, 60, 20 );
//		panelMaster.adic( txtCodCli, 7, 25, 60, 20 );
//		panelMaster.adic( new JLabelPad( "Razão social do cliente" ), 70, 5, 340, 20 );
//		panelMaster.adic( txtRazCli, 70, 25, 340, 20 );
		
/*		panelMaster.adic( new JLabelPad( "Contato" ), 413, 5, 100, 20 );
		panelMaster.adic( txtContCli, 413, 25, 100, 20 );
		
		panelMaster.adic( new JLabelPad( "DDD" ), 7, 45, 60, 20 );
		panelMaster.adic( txtDDDCli, 7, 65, 60, 20 );
		panelMaster.adic( new JLabelPad( "Fone" ), 70, 45, 75, 20 );
		panelMaster.adic( txtFoneCli, 70, 65, 75, 20 );
		panelMaster.adic( new JLabelPad( "e-mail" ), 148, 45, 262, 20 );
		panelMaster.adic( txtEmailCli, 148, 65, 262, 20 );
		
		panelMaster.adic( lbAtivoCli, 413, 65, 100, 20 );*/
		
		panelMaster.adic( new JLabelPad( "Cód.Prod." ), 7, 0, 60, 20 );
		panelMaster.adic( txtCodProd, 7, 20, 60, 20 );
		panelMaster.adic( new JLabelPad( "Descrição do produto" ), 70, 0, 340, 20 );
		panelMaster.adic( txtDescProd, 70, 20, 340, 20 );

		panelMaster.adic( periodo, 540, 0, 60, 20 );
		panelMaster.adic( borda, 530, 10, 220, 45 );
		panelMaster.adic( txtDataini, 540, 25, 80, 20 );
		panelMaster.adic( new JLabel( "até", SwingConstants.CENTER ), 620, 25, 40, 20 );
		panelMaster.adic( txtDatafim, 660, 25, 80, 20 );

		panelMaster.adic( btBuscar, 530, 60, 220, 30 );
		
/*		

  		txtFoneCli.setMascara( JTextFieldPad.MC_FONE );
		
		lbAtivoCli.setOpaque( true );
		lbAtivoCli.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbAtivoCli.setBackground( GREEN );
		lbAtivoCli.setForeground( Color.WHITE );
		
*/		
		
		// ***** Detalhamento (abas)
		
		panelGeral.add( panelDetail, BorderLayout.CENTER );
		panelDetail.add( tabbedDetail );
		tabbedDetail.addTab( "Vendas", panelVendas );
		//tabbedDetail.addTab( "Receber", panelReceber );
		//tabbedDetail.addTab( "Histórico", panelHistorico );
		
		// ***** Vendas
		
		panelVendas.add( panelTabVendas, BorderLayout.NORTH );
		panelVendas.add( panelGridVendas, BorderLayout.CENTER );		
		panelGridVendas.add( panelTabVendasNotas );
//		panelGridVendas.add( panelTabItensVendas );
		
		panelTabVendasNotas.setBorder( BorderFactory.createTitledBorder( "Vendas" ) );
//		panelTabItensVendas.setBorder( BorderFactory.createTitledBorder( "Itens de vendas" ) );
//		panelTabItensVendas.setPreferredSize( new Dimension( 700, 120 ) );
		
		panelTabVendas.adic( new JLabelPad( "Qtd.Vendida" ), 10, 10, 100, 20 );
		panelTabVendas.adic( txtQuantidadeVendida, 10, 30, 100, 20 );
		panelTabVendas.adic( new JLabelPad( "Qtd.Estoque" ), 113, 10, 120, 20 );
		panelTabVendas.adic( txtQuantidadeEstoque, 113, 30, 120, 20 );
		panelTabVendas.adic( new JLabelPad( "Qtd.Em Produção" ), 236, 10, 120, 20 );
		panelTabVendas.adic( txtQuantidadeProducao, 236, 30, 120, 20 );
		panelTabVendas.adic( new JLabelPad( "Qtd. Produzir" ), 359, 10, 120, 20 );
		panelTabVendas.adic( txtQuantidadeProduzir, 359, 30, 120, 20 );
		
		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = SwingParams.getFontpadmin(); 
			
//		new Font( "Tomoha", Font.PLAIN, 11 );
		
		panelTabVendas.adic( new JLabelPad( imgItemPendente ), 600, 5, 20, 15 );
		JLabelPad canceladas = new JLabelPad( "canceladas" );
		canceladas.setForeground( statusColor );
		canceladas.setFont( statusFont );
		panelTabVendas.adic( canceladas, 620, 5, 100, 15 );
		
		panelTabVendas.adic( new JLabelPad( imgItemAprovado ), 600, 20, 20, 15 );
		JLabelPad pedidos = new JLabelPad( "pedidos" );
		pedidos.setForeground( statusColor );
		pedidos.setFont( statusFont );
		panelTabVendas.adic( pedidos, 620, 20, 100, 15 );
		
		panelTabVendas.adic( new JLabelPad( imgItemProducao ), 600, 35, 20, 15 );
		JLabelPad faturadas = new JLabelPad( "faturadas" );
		faturadas.setForeground( statusColor );
		faturadas.setFont( statusFont );
		panelTabVendas.adic( faturadas, 620, 35, 100, 15 );
				
		tabOrcamentos.adicColuna( "" );
		tabOrcamentos.adicColuna( "Cód.Orc." );
		tabOrcamentos.adicColuna( "Item" );
		tabOrcamentos.adicColuna( "Cod.Cli." );
		tabOrcamentos.adicColuna( "Razão social do cliente" );
		tabOrcamentos.adicColuna( "Cod.Prod." );
		tabOrcamentos.adicColuna( "Descrição do produto" );
		tabOrcamentos.adicColuna( "Qtd.Aprov." );
		tabOrcamentos.adicColuna( "Estoque At." );
		tabOrcamentos.adicColuna( "Qtd. em prod." );
		tabOrcamentos.adicColuna( "Qtd. a prod." );

		tabOrcamentos.setTamColuna( 20, VENDAS.STATUS.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.DATAAPROV.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.DATAENTREGA.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.CODORC.ordinal() );
		tabOrcamentos.setTamColuna( 40, VENDAS.CODITORC.ordinal() );
		tabOrcamentos.setTamColuna( 40, VENDAS.CODCLI.ordinal() );
		tabOrcamentos.setTamColuna( 150, VENDAS.RAZCLI.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.CODPROD.ordinal() );
		tabOrcamentos.setTamColuna( 150, VENDAS.DESCPROD.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDAPROV.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDESTOQUE.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDESTOQUE.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDEMPROD.ordinal() );
		tabOrcamentos.setTamColuna( 60, VENDAS.QTDAPROD.ordinal() );
				
/*		panelTabVendasNotas.add( new JScrollPane( tabOrcamentos ) );

		tabItensVendas.adicColuna( "Item" );
		tabItensVendas.adicColuna( "Código" );
		tabItensVendas.adicColuna( "Descrição do produto" );
		tabItensVendas.adicColuna( "Lote" );
		tabItensVendas.adicColuna( "Qtd." );
		tabItensVendas.adicColuna( "Preço" );
		tabItensVendas.adicColuna( "V.Desc." );
		tabItensVendas.adicColuna( "V.Frete" );
		tabItensVendas.adicColuna( "V.líq." );

		tabItensVendas.setTamColuna( 30, ITEMVENDAS.ITEM.ordinal() );
		tabItensVendas.setTamColuna( 50, ITEMVENDAS.CODPROD.ordinal() );
		tabItensVendas.setTamColuna( 200, ITEMVENDAS.DESCPROD.ordinal() );
		tabItensVendas.setTamColuna( 70, ITEMVENDAS.LOTE.ordinal() );
		tabItensVendas.setTamColuna( 50, ITEMVENDAS.QUANTIDADE.ordinal() );
		tabItensVendas.setTamColuna( 70, ITEMVENDAS.PRECO.ordinal() );
		tabItensVendas.setTamColuna( 80, ITEMVENDAS.DESCONTO.ordinal() );
		tabItensVendas.setTamColuna( 80, ITEMVENDAS.FRETE.ordinal() );
		tabItensVendas.setTamColuna( 90, ITEMVENDAS.TOTAL.ordinal() );
				
		panelTabItensVendas.add( new JScrollPane( tabItensVendas ) );
	*/	
		// ***** Rodapé
		
		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair() );
	}
	
	private void buscaVendas() {
			
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT V.CODVENDA, V.DOCVENDA, V.DTEMITVENDA, V.STATUSVENDA, V.CODPLANOPAG," );
			sql.append( "P.DESCPLANOPAG, V.CODVEND, VD.NOMEVEND, V.VLRPRODVENDA, V.VLRDESCVENDA," );
			sql.append( "V.VLRADICVENDA, V.VLRFRETEVENDA, V.VLRLIQVENDA " );
			sql.append( "FROM VDVENDA V, FNPLANOPAG P, VDVENDEDOR VD " );
			sql.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
			sql.append( "P.CODEMP=V.CODEMPPG AND P.CODFILIAL=V.CODFILIALPG AND P.CODPLANOPAG=V.CODPLANOPAG AND " );
			sql.append( "VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND VD.CODVEND=V.CODVEND" );
			
			
			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " AND EXISTS (" );
				sql.append( " SELECT CODVENDA FROM VDITVENDA IV WHERE ");
				sql.append( " IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA ");
				sql.append( " AND IV.CODEMPPD=? AND IV.CODFILIALPD=? AND IV.CODPROD=? ) " );
				
			}
			
			sql.append( " ORDER BY V.DTEMITVENDA DESC ");
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if(txtCodProd.getVlrInteger()>0) {
				ps.setInt( iparam++, lcProd.getCodEmp() );
				ps.setInt( iparam++, lcProd.getCodFilial() );
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );								
			}			
			
			ResultSet rs = ps.executeQuery();		
			
			carregandoOrcamentos = true;
			tabOrcamentos.limpa();
						
			int row = 0;
			while ( rs.next() ) {
				
				tabOrcamentos.adicLinha();
				
				if ( "C".equals( rs.getString( "STATUSVENDA" ).substring( 0, 1 ) ) ) {
					imgColuna = imgItemPendente;
				}
				else if ( "P".equals( rs.getString( "STATUSVENDA" ).substring( 0, 1 ) ) ) {
					imgColuna = imgItemAprovado;
				}
				else {
					imgColuna = imgItemProducao;
				}
/*								
				tabOrcamentos.setValor( imgColuna, row, VENDAS.STATUS.ordinal() );
				tabOrcamentos.setValor( rs.getInt( "CODVENDA" ), row, VENDAS.CODVENDA.ordinal() );
				tabOrcamentos.setValor( rs.getString( "DOCVENDA" ), row, VENDAS.NOTA.ordinal() );
				tabOrcamentos.setValor( Funcoes.dateToStrDate( rs.getDate( "DTEMITVENDA" ) ), row, VENDAS.DATA.ordinal() );
				tabOrcamentos.setValor( rs.getString( "DESCPLANOPAG" ), row, VENDAS.PAGAMENTO.ordinal() );
				tabOrcamentos.setValor( rs.getString( "NOMEVEND" ), row, VENDAS.VENDEDOR.ordinal() );
				tabOrcamentos.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPRODVENDA" ) ), row, VENDAS.VALOR_PRODUTOS.ordinal() );
				tabOrcamentos.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCVENDA" ) ), row, VENDAS.VALOR_DESCONTO.ordinal() );
				tabOrcamentos.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRADICVENDA" ) ), row, VENDAS.VALOR_ADICIONAL.ordinal() );
				tabOrcamentos.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRFRETEVENDA" ) ), row, VENDAS.VALOR_FRETE.ordinal() );
				tabOrcamentos.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRLIQVENDA" ) ), row, VENDAS.VALOR_LIQUIDO.ordinal() );
	*/			
				row++;
			}
			
			sql = new StringBuilder();
			sql.append( "SELECT FIRST 1 V.DTEMITVENDA, V.VLRLIQVENDA, V.CODVENDA " );
			sql.append( "FROM VDVENDA V, VDCLIENTE C " );
			sql.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
			sql.append( "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND " );
			sql.append( "C.CODEMP=? AND C.CODFILIAL=? AND C.CODCLI=? " );
			sql.append( "ORDER BY V.DTEMITVENDA DESC" );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "VDCLIENTE" ) );

			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtQuantidadeVendida.setVlrDate( Funcoes.sqlDateToDate( rs.getDate( "DTEMITVENDA" ) ) );
			}
			
			sql = new StringBuilder();
			sql.append( "SELECT SUM(V.VLRLIQVENDA) TOTAL, SUM(R.VLRAPAGREC) TOTAL_ABERTO " );
			sql.append( "FROM VDVENDA V, VDCLIENTE C, FNRECEBER R " );
			sql.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
			sql.append( "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND " );
			sql.append( "C.CODEMP=? AND C.CODFILIAL=? AND C.CODCLI=? AND " );
			sql.append( "R.CODEMPVD=V.CODEMP AND R.CODFILIALVD=V.CODFILIAL AND R.CODVENDA=V.CODVENDA AND R.TIPOVENDA=V.TIPOVENDA " );
			sql.append( "GROUP BY V.CODCLI " );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "VDCLIENTE" ) );

			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {

			}
			
			if(row>0) {
				//Selecionando primeiro do grid e carregando itens
				
				tabOrcamentos.setLinhaSel( 0 );

			}
			
			rs.close();
			ps.close();
			con.commit();
			
			carregandoOrcamentos = false;
			tabOrcamentos.requestFocus();
		} catch ( Exception e ) {
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
		}*/
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
/*			else if ( e.getSource() == tabItensVendas && tabItensVendas.getLinhaSel() > -1 ) {
				FVenda venda = null;    
	    		if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
	    			venda = (FVenda)Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
	    		}
	    		else {
	    			venda = new FVenda();
	    			Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
	    		}    		
				venda.exec( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ),
							(Integer)tabItensVendas.getValor( tabItensVendas.getLinhaSel(), ITEMVENDAS.ITEM.ordinal() ) );
			}*/
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
