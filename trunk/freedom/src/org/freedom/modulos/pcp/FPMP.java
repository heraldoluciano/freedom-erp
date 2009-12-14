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
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLLoading;
import org.freedom.telas.FFilho;
import org.freedom.telas.SwingParams;


/**
 * Tela para planejamento mestre da produção.
 * 
 * @author Setpoint Informática Ltda./Anderson Sanchez
 * @version 03/12/2009
 */

public class FPMP extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener, TabelaEditListener {

	private static final long serialVersionUID = 1L;	
	private static final Color GREEN = new Color( 45, 190, 60 );

	// *** Paineis tela
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	private JPanelPad panelMaster = new JPanelPad( 700, 100 );
	private JPanelPad panelAbas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JTabbedPanePad tabbedAbas = new JTabbedPanePad();
	private JPanelPad panelSouth = new JPanelPad(30, 30 );	
	private JPanelPad panelLegenda = new JPanelPad(30, 30 );	
	private JPanelPad panelFiltros = new JPanelPad();
	
	// *** Paineis Detalhamento
	
	private JPanelPad panelDet = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );		
	private JPanelPad panelTabDet = new JPanelPad( 700, 60 );
	private JPanelPad panelGridDet = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad panelTabDetItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private Tabela tabDet = null;
	
	// *** Paineis Agrupamento
	
	private JPanelPad panelAgrup = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );	
	private JPanelPad panelTabAgrup = new JPanelPad( 700, 60 );
	private JPanelPad panelGridAgrup = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad panelTabAgrupItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad pnCritAgrup = new JPanelPad();
	private Tabela tabAgrup = null;
		
	// *** Geral

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JButtonPad btBuscar = new JButtonPad( "Buscar", Icone.novo( "btExecuta.gif" ) );
	
	// *** Campos

	private JTextFieldFK txtQuantidadeVendida = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );	
	private JTextFieldFK txtQuantidadeEstoque = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );	
	private JTextFieldFK txtQuantidadeProducao = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	private JTextFieldFK txtQuantidadeProduzir = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// ** Checkbox
	
	private JCheckBoxPad cbAgrupProd =  new JCheckBoxPad( "Produto", "S", "N" );
	private JCheckBoxPad cbAgrupDataAprov =  new JCheckBoxPad( "Dt.Aprovação", "S", "N" );
	private JCheckBoxPad cbAgrupDataEntrega =  new JCheckBoxPad( "Dt.Entrega", "S", "N" );
	private JCheckBoxPad cbAgrupCli =  new JCheckBoxPad( "Cliente", "S", "N" );
	private JCheckBoxPad cbPend =  new JCheckBoxPad( "Pendentes", "S", "N" );
	private JCheckBoxPad cbEmProd =  new JCheckBoxPad( "Em produção", "S", "N" );
	private JCheckBoxPad cbProd =  new JCheckBoxPad( "Produzidos", "S", "N" );
	
	// ** Legenda
	
	private ImageIcon imgPendente = Icone.novo( "clVencido.gif" );
	private ImageIcon imgProducao = Icone.novo( "clPagoParcial.gif" );
	private ImageIcon imgProduzido = Icone.novo( "clPago.gif" );
	private ImageIcon imgColuna = null;
	
	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );
	private ListaCampos lcProd = new ListaCampos( this );

	// *** Botões
	
	private JButtonPad btSelectAllDet = new JButtonPad( Icone.novo( "btTudo.gif" ) );
	private JButtonPad btDeselectAllDet = new JButtonPad( Icone.novo( "btNada.gif" ) );
//	private JButtonPad btSelectNecessariosDet = new JButtonPad( Icone.novo( "btSelEstrela.png" ) );
	private JButtonPad btLimparGridDet = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btSimulaAgrupamentoDet = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btIniProdDet = new JButtonPad( Icone.novo( "btIniProd.png" ) );
	
	private JButtonPad btSelectAllAgrup = new JButtonPad( Icone.novo( "btTudo.gif" ) );
	private JButtonPad btDeselectAllAgrup = new JButtonPad( Icone.novo( "btNada.gif" ) );
	private JButtonPad btSelectNecessariosAgrup = new JButtonPad( Icone.novo( "btSelEstrela.png" ) );
	private JButtonPad btLimparGridAgrup = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btSimulaAgrupamentoAgrup = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	
	// Enums
	
	private enum DETALHAMENTO {
		MARCACAO,STATUS, CODOP, SEQOP, DATAAPROV, DTFABROP, DATAENTREGA, CODEMPOC, CODFILIALOC, CODORC, CODITORC, TIPOORC, CODCLI, RAZCLI, 
		CODEMPPD, CODFILIALPD, CODPROD, SEQEST, DESCPROD, QTDAPROV, QTDESTOQUE, QTDRESERVADO, QTDEMPROD, QTDAPROD 
	}
	
	private enum AGRUPAMENTO {
		MARCACAO,STATUS, DATAAPROV, DATAENTREGA, CODCLI, RAZCLI, CODPROD, DESCPROD, QTDAPROV, QTDESTOQUE, QTDEMPROD, QTDAPROD 
	}
	
	private enum PROCEDUREOP {
		  CODEMPOP, CODFILIALOP, CODOP, SEQOP, CODEMPPD, CODFILIALPD, CODPROD, CODEMPOC,  CODFILIALOC,  CODORC, TIPOORC, CODITORC, 
		  QTDSUGPRODOP, DTFABROP, SEQEST 
	}
	
	public FPMP() {

		super( false );
		
		setTitulo( "Planejamento mestre da produção", this.getClass().getName() );
		setAtribos( 20, 20, 860, 600 );
		
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
		cbAgrupProd.setVlrString( "S" );
		cbAgrupProd.setEnabled( false );
		cbPend.setVlrString( "S" );
	}
	
	private void montaListaCampos() {
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd.setWhereAdic( "TIPOPROD='F'" );
		txtCodProd.setTabelaExterna( lcProd );		
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );	

	}
	
	private void montaListeners() {
		
		btSelectAllDet.addActionListener( this );
		btDeselectAllDet.addActionListener( this );
//		btSelectNecessariosDet.addActionListener( this );
		btLimparGridDet.addActionListener( this );
		btIniProdDet.addActionListener( this );
		
		btSelectAllAgrup.addActionListener( this );
		btDeselectAllAgrup.addActionListener( this );
		btSelectNecessariosAgrup.addActionListener( this );
		btLimparGridAgrup.addActionListener( this );
	
		btBuscar.addActionListener( this );
	
		lcProd.addCarregaListener( this );
		lcCliente.addCarregaListener( this );
		
//		tabDet.addTabelaEditListener( this );
		tabDet.addTabelaSelListener( this );	
		tabDet.addMouseListener( this );	
		tabAgrup.addMouseListener( this );
		
	}

	private void montaTela() {
		
		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
		
		// ***** Cabeçalho
		
		panelMaster.adic( new JLabelPad( "Cód.Prod." ), 7, 0, 60, 20 );
		panelMaster.adic( txtCodProd, 7, 20, 60, 20 );
		panelMaster.adic( new JLabelPad( "Descrição do produto" ), 70, 0, 340, 20 );
		panelMaster.adic( txtDescProd, 70, 20, 340, 20 );
		
		panelMaster.adic( new JLabelPad( "Cód.Cli." ), 7, 40, 60, 20 );
		panelMaster.adic( txtCodCli, 7, 60, 60, 20 );
		panelMaster.adic( new JLabelPad( "Razão social do cliente" ), 70, 40, 340, 20 );
		panelMaster.adic( txtRazCli, 70, 60, 340, 20 );
		
		panelFiltros.setBorder( SwingParams.getPanelLabel( "Filtros" ) );
		panelFiltros.adic( cbPend, 4, 0, 100, 20 );
		panelFiltros.adic( cbEmProd, 4, 30, 100, 20 );
		panelFiltros.adic( cbProd, 114, 0, 100, 20 );
		
		panelMaster.adic( panelFiltros, 416, 0, 220, 82 );

		panelMaster.adic( btBuscar, 712, 10, 123, 30 );
		
//		***** Abas
		
		panelGeral.add( panelAbas, BorderLayout.CENTER );
		panelGeral.add( panelAbas);
		panelAbas.add( tabbedAbas );
		
		tabbedAbas.addTab( "Detalhamento", panelDet );
		tabbedAbas.addTab( "Agrupamento", panelAgrup );
		
		// ***** Detalhamento
		
		panelDet.add( panelTabDet, BorderLayout.NORTH );
		panelDet.add( panelGridDet, BorderLayout.CENTER );		
		panelGridDet.add( panelTabDetItens );
		
		panelTabDet.adic( new JLabelPad( "Qtd.Vendida" ), 10, 5, 80, 20 );
		panelTabDet.adic( txtQuantidadeVendida, 10, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Qtd.Estoque" ), 93, 5, 80, 20 );
		panelTabDet.adic( txtQuantidadeEstoque, 93, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Qtd.Produção" ), 176, 5, 80, 20 );
		panelTabDet.adic( txtQuantidadeProducao, 176, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Qtd.Produzir" ), 259, 5, 80, 20 );
		panelTabDet.adic( txtQuantidadeProduzir, 259, 25, 80, 20 );
		
		panelTabDet.adic( btSelectAllDet, 743, 12, 30, 30 );
		panelTabDet.adic( btDeselectAllDet, 774, 12, 30, 30 );
//		panelTabDet.adic( btSelectNecessariosDet, 774, 12, 30, 30 );
		panelTabDet.adic( btLimparGridDet, 805, 12, 30, 30 );
		
		JLabelPad separador = new JLabelPad();
		separador.setBorder( BorderFactory.createEtchedBorder() );
		panelTabDet.adic( separador, 350, 4, 2, 48 );

		panelTabDet.adic( btIniProdDet, 360, 4, 48, 48 );
		
		panelTabDetItens.add( new JScrollPane( tabDet ) );

		
		// ***** Agrupamento
		
		panelAgrup.add( panelTabAgrup, BorderLayout.NORTH );
		panelAgrup.add( panelGridAgrup, BorderLayout.CENTER );		
		panelGridAgrup.add( panelTabAgrupItens );		
		
		pnCritAgrup.setBorder( SwingParams.getPanelLabel("Critérios de agrupamento") );
		
		pnCritAgrup.adic( cbAgrupProd, 4, 0, 90, 20 );
		pnCritAgrup.adic( cbAgrupDataEntrega, 94, 0, 90, 20 );
		pnCritAgrup.adic( cbAgrupDataAprov, 184, 0, 110, 20 );
		pnCritAgrup.adic( cbAgrupCli, 294, 0, 90, 20 );		
		
		panelTabAgrup.adic(pnCritAgrup, 4, 0, 375, 53);
		
		panelTabAgrup.adic( btSelectAllAgrup, 712, 12, 30, 30 );
		panelTabAgrup.adic( btDeselectAllAgrup, 743, 12, 30, 30 );
		panelTabAgrup.adic( btSelectNecessariosAgrup, 774, 12, 30, 30 );
		panelTabAgrup.adic( btLimparGridAgrup, 805, 12, 30, 30 );

		panelTabAgrupItens.add( new JScrollPane( tabAgrup ) );
		
		// ***** Rodapé
		
		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = SwingParams.getFontpadmin(); 
		
		JLabelPad canceladas = new JLabelPad( "Pendentes" );
		canceladas.setForeground( statusColor );
		canceladas.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgPendente ), 0, 5, 20, 15 );
		panelLegenda.adic( canceladas, 20, 5, 100, 15 );
		
		JLabelPad pedidos = new JLabelPad( "Em Produção" );
		pedidos.setForeground( statusColor );
		pedidos.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgProducao ), 60, 5, 20, 15 );
		panelLegenda.adic( pedidos, 80, 5, 100, 15 );
		
		JLabelPad faturadas = new JLabelPad( "Produzidos" );
		faturadas.setForeground( statusColor );
		faturadas.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgProduzido ), 130, 5, 20, 15 );		
		panelLegenda.adic( faturadas, 150, 5, 100, 15 );
		
		panelLegenda.setBorder( null );		
		
		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair());
		pnRod.add( panelLegenda, BorderLayout.CENTER );
				
	}
	
	private void criaTabelas() {
		
		// Tabela de detalhamento
		
		tabDet = new Tabela();
		
		tabDet.adicColuna( "" );
		tabDet.adicColuna( "" );
		tabDet.adicColuna( "Dt.Aprov." );
		tabDet.adicColuna( "Dt.Prod." );
		tabDet.adicColuna( "Dt.Entrega" );
		// Invisível
		tabDet.adicColuna( "codempoc" );
		tabDet.adicColuna( "codfilialoc" );
		tabDet.adicColuna( "Cod.OP" );
		tabDet.adicColuna( "Seq.OP" );

		//		
		tabDet.adicColuna( "Orc." );
		tabDet.adicColuna( "It" );
		// Invisível
		tabDet.adicColuna( "tipoorc" );
		//		
		tabDet.adicColuna( "Cli." );
		tabDet.adicColuna( "Razão social do cliente" );
		// Invisível
		tabDet.adicColuna( "codemppd" );
		tabDet.adicColuna( "codfilialpd" );
		//
		tabDet.adicColuna( "Prod." );
		
		tabDet.adicColuna( "Estr." );
		
		tabDet.adicColuna( "Descrição do produto" );
		tabDet.adicColuna( "Aprov." );
		tabDet.adicColuna( "Reservado" );
		tabDet.adicColuna( "Estoque" );
		tabDet.adicColuna( "Produção" );
		tabDet.adicColuna( "Sugestao" );
		
		tabDet.setTamColuna( 15, DETALHAMENTO.MARCACAO.ordinal() );
		tabDet.setTamColuna( 10, DETALHAMENTO.STATUS.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DATAAPROV.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DTFABROP.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DATAENTREGA.ordinal() );

		tabDet.setColunaInvisivel( DETALHAMENTO.CODOP.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.SEQOP.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODEMPOC.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODFILIALOC.ordinal() );
		
		tabDet.setTamColuna( 30, DETALHAMENTO.CODORC.ordinal() );
		tabDet.setTamColuna( 15, DETALHAMENTO.CODITORC.ordinal() );
		
		tabDet.setColunaInvisivel( DETALHAMENTO.TIPOORC.ordinal() );
		
		tabDet.setTamColuna( 40, DETALHAMENTO.CODCLI.ordinal() );
		tabDet.setTamColuna( 100, DETALHAMENTO.RAZCLI.ordinal() );
		
		tabDet.setColunaInvisivel( DETALHAMENTO.CODEMPPD.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODFILIALPD.ordinal() );
		
		tabDet.setTamColuna( 40, DETALHAMENTO.CODPROD.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.SEQEST.ordinal() );
		
		tabDet.setTamColuna( 100, DETALHAMENTO.DESCPROD.ordinal() );
		
		tabDet.setTamColuna( 50, DETALHAMENTO.QTDAPROV.ordinal() );
		
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDRESERVADO.ordinal() );
		
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDESTOQUE.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDEMPROD.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDAPROD.ordinal() );

		tabDet.setColunaEditavel( DETALHAMENTO.QTDAPROD.ordinal(), true );
		tabDet.setColunaEditavel( DETALHAMENTO.DTFABROP.ordinal(), true );
		
		// Tabela de Agrupamento
		
		tabAgrup = new Tabela();
		
		tabAgrup.adicColuna( "" );
		tabAgrup.adicColuna( "" );
		tabAgrup.adicColuna( "Dt.Aprov." );
		tabAgrup.adicColuna( "Dt.Entrega" );
		tabAgrup.adicColuna( "Cli." );
		tabAgrup.adicColuna( "Razão social do cliente" );
		tabAgrup.adicColuna( "Prod." );
		tabAgrup.adicColuna( "Descrição do produto" );
		tabAgrup.adicColuna( "Aprov." );
		tabAgrup.adicColuna( "Estoque" );
		tabAgrup.adicColuna( "Produção" );
		tabAgrup.adicColuna( "Produzir" );
		
		tabAgrup.setTamColuna( 15, AGRUPAMENTO.MARCACAO.ordinal() );
		tabAgrup.setTamColuna( 10, AGRUPAMENTO.STATUS.ordinal() );
		tabAgrup.setTamColuna( 40, AGRUPAMENTO.CODPROD.ordinal() );
		tabAgrup.setTamColuna( 145, AGRUPAMENTO.DESCPROD.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDAPROV.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDESTOQUE.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDEMPROD.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDAPROD.ordinal() );
		
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAAPROV.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAENTREGA.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODCLI.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.RAZCLI.ordinal() );				
		
	}
	
	private void montaGridDet() {
			
		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			
			sql.append( "oc.statusorc status, io.sitproditorc, io.dtaprovitorc dataaprov, ");
			sql.append( "cast('today' as date) dtfabrop, ");
			sql.append( "io.dtaprovitorc + coalesce(oc.prazoentorc,0) dataentrega, oc.codemp codempoc, ");
			sql.append( "oc.codfilial codfilialoc, oc.codorc, "); 
			sql.append( "io.coditorc, io.tipoorc ,cl.codcli, ");
			sql.append( "cl.razcli, io.codemppd, io.codfilialpd, pd.codprod, pe.seqest, ") ;
			sql.append( "pd.descprod, coalesce(io.qtdaprovitorc,0) qtdaprov, op.codop, op.seqop, ");
			
			sql.append( "sum(coalesce(sp.sldliqprod,0)) qtdestoque , ");
			
			sql.append( "sum(coalesce(op.qtdprevprodop,0)) qtdemprod ");
			
			sql.append( "from vdorcamento oc ");
			
			sql.append( "left outer join vditorcamento io on ");
			sql.append( "io.codemp=oc.codemp and io.codfilial=oc.codfilial and io.codorc=oc.codorc and io.tipoorc=oc.tipoorc ");
			
			sql.append( "left outer join vdcliente cl on ");
			sql.append( "cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli ");
			
			sql.append( "left outer join eqproduto pd on ");
			sql.append( "pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod ");
			
			sql.append( "left outer join ppop op on " );
			sql.append( "op.codemppd=pd.codemp and op.codfilial=pd.codfilial and op.codprod=pd.codprod and op.sitop in ('PE','BL') ");
			
			sql.append( "left outer join eqsaldoprod sp on ");
			sql.append( "sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod ");
			
			sql.append( "left outer join ppestrutura pe on ");
			sql.append( "pe.codemp=pd.codemp and pe.codfilial=pd.codfilial and pe.codprod=pd.codprod ");
			
			sql.append( "where oc.codemp=? and oc.codfilial=? and io.aprovitorc='S' and pd.tipoprod='F' ");
			
//			String status = cbPend.getVlrString() + cbEmprod.getVlrString() + cbProd.getVlrString
			StringBuffer status = new StringBuffer("");
			
			if("S".equals(cbPend.getVlrString())) {
				status.append( " 'PE' ");
			}
			if("S".equals(cbEmProd.getVlrString())) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( "'EP'" );
			}
			if("S".equals(cbProd.getVlrString())) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( "'PD'" );
			}

			if ( status.length() > 0 ) {
				sql.append( " and io.sitproditorc in (" );
				sql.append( status );
				sql.append( ") ");
			}
			else {
				sql.append( " and io.sitproditorc not in('PE','EP','PD') " );
			}
					 
			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " and io.codemppd=? and io.codfilialpd=? and io.codprod=? " );				
			}
			if(txtCodCli.getVlrInteger()>0) {
				sql.append( " and oc.codempcl=? and oc.codfilialcl=? and oc.codcli=? " );				
			}
			
			sql.append(" group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 ");
			
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );

			if(txtCodProd.getVlrInteger()>0) {
				ps.setInt( iparam++, lcProd.getCodEmp() );
				ps.setInt( iparam++, lcProd.getCodFilial() );
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );								
			}
			if(txtCodCli.getVlrInteger()>0) {
				ps.setInt( iparam++, lcCliente.getCodEmp() );
				ps.setInt( iparam++, lcCliente.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );								
			}	
			
			ResultSet rs = ps.executeQuery();		
			
			tabDet.limpa();
						
			int row = 0;
			
			BigDecimal totqtdaprov = new BigDecimal(0);
			BigDecimal totqtdestoq = new BigDecimal(0);
			BigDecimal totqtdemprod = new BigDecimal(0);
			BigDecimal totqtdaprod = new BigDecimal(0);

			sql = new StringBuilder();
			sql.append( "select coalesce(sum(io2.qtdaprovitorc),0) qtdreservado from ppopitorc oo, vditorcamento io2 where ");
			sql.append( "oo.codempoc=io2.codemp and oo.codfilialoc=io2.codfilial ");
			sql.append( "and oo.codorc=io2.codorc and oo.coditorc=io2.coditorc and oo.tipoorc=io2.tipoorc " );
			sql.append( "and io2.codemp=? and io2.codfilial=? " );
			sql.append( "and io2.codemppd=? and io2.codfilialpd=? and io2.codprod=? "); 
			sql.append( "and io2.sitproditorc='PD' and coalesce(io2.statusitorc,'PE')!='OV' " );
			
			ResultSet rs2 = null;		

			PreparedStatement ps2 = null;
			
			
			while ( rs.next() ) {
				
				tabDet.adicLinha();
				tabDet.setColColor( -1, DETALHAMENTO.DTFABROP.ordinal(), Color.WHITE, Color.RED );
				tabDet.setColColor( -1, DETALHAMENTO.QTDAPROD.ordinal(), Color.WHITE, Color.RED );
				
				ps2 = con.prepareStatement( sql.toString() );
				
				ps2.setInt( 1, rs.getInt( DETALHAMENTO.CODEMPOC.toString() ) );
				ps2.setInt( 2, rs.getInt( DETALHAMENTO.CODFILIALOC.toString() ) );
				ps2.setInt( 3, rs.getInt( DETALHAMENTO.CODEMPPD.toString() ) );
				ps2.setInt( 4, rs.getInt( DETALHAMENTO.CODFILIALPD.toString() ) );
				ps2.setInt( 5, rs.getInt( DETALHAMENTO.CODPROD.toString() ) );

				
				rs2 = ps2.executeQuery();		
		
				BigDecimal qtdreservado = new BigDecimal(0);
				
				if(rs2.next()) {

					qtdreservado = rs2.getBigDecimal( DETALHAMENTO.QTDRESERVADO.toString() ).setScale( Aplicativo.casasDec );
					
				}

				
				tabDet.setValor( new Boolean(false), row, DETALHAMENTO.MARCACAO.ordinal() );
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DATAAPROV.toString() ) ), row, DETALHAMENTO.DATAAPROV.ordinal() );
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DTFABROP.toString() ) ), row, DETALHAMENTO.DTFABROP.ordinal() );				
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DATAENTREGA.toString() ) ), row, DETALHAMENTO.DATAENTREGA.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODEMPOC.toString() ), row, DETALHAMENTO.CODEMPOC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODFILIALOC.toString() ), row, DETALHAMENTO.CODFILIALOC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODORC.toString() ), row, DETALHAMENTO.CODORC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODITORC.toString() ), row, DETALHAMENTO.CODITORC.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.TIPOORC.toString() ), row, DETALHAMENTO.TIPOORC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODCLI.toString() ), row, DETALHAMENTO.CODCLI.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.RAZCLI.toString().trim() ), row, DETALHAMENTO.RAZCLI.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODEMPPD.toString() ), row, DETALHAMENTO.CODEMPPD.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODFILIALPD.toString() ), row, DETALHAMENTO.CODFILIALPD.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODPROD.toString() ), row, DETALHAMENTO.CODPROD.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.SEQEST.toString() ), row, DETALHAMENTO.SEQEST.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.DESCPROD.toString().trim() ), row, DETALHAMENTO.DESCPROD.ordinal() );

				tabDet.setValor( rs.getInt( DETALHAMENTO.CODOP.toString().trim() ), row, DETALHAMENTO.CODOP.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.SEQOP.toString().trim() ), row, DETALHAMENTO.SEQOP.ordinal() );

				
				
				BigDecimal qtdaprov = rs.getBigDecimal( DETALHAMENTO.QTDAPROV.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdestoque = rs.getBigDecimal( DETALHAMENTO.QTDESTOQUE.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdemprod = rs.getBigDecimal( DETALHAMENTO.QTDEMPROD.toString() ).setScale( Aplicativo.casasDec );
				
				BigDecimal qtdaprod = rs.getBigDecimal( DETALHAMENTO.QTDAPROV.toString() ).setScale( Aplicativo.casasDec );
				
				
				//BigDecimal qtdaprod = qtdaprov.subtract( qtdestoque ).subtract( qtdemprod ).setScale( Aplicativo.casasDec );
				//qtdaprod = qtdaprod.add( qtdreservado );
				/*
				if(qtdaprod.floatValue() < 0) {
					qtdaprod = new BigDecimal(0);
				}
				*/
				
				
				totqtdaprov = totqtdaprov.add( qtdaprov );
				totqtdestoq = qtdestoque; // Não deve ser somado, pois varia no produto
				totqtdemprod = qtdemprod; // Não deve ser somado, pois vaira no produto
				
				if ( "PE".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgPendente;
				}
				else if ( "EP".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgProducao;
					qtdaprod = new BigDecimal(0);
				}
				else if ( "PD".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgProduzido;
					qtdaprod = new BigDecimal(0);
				}
											
				tabDet.setValor( imgColuna, row, DETALHAMENTO.STATUS.ordinal() );
								
				tabDet.setValor( qtdaprov, row, DETALHAMENTO.QTDAPROV.ordinal() );				

				tabDet.setValor( qtdreservado, row, DETALHAMENTO.QTDRESERVADO.ordinal() );
				
				tabDet.setValor( qtdestoque, row, DETALHAMENTO.QTDESTOQUE.ordinal() );
				tabDet.setValor( qtdemprod, row, DETALHAMENTO.QTDEMPROD.ordinal() );
				tabDet.setValor( qtdaprod , row, DETALHAMENTO.QTDAPROD.ordinal() );
				
				row++;
				
			}
			
			totqtdaprod = (totqtdestoq.subtract( totqtdaprov ));
			
			if(totqtdaprod.floatValue()>0) {
				totqtdaprod = new BigDecimal( 0 );
			}
			else {
				totqtdaprod = totqtdaprod.abs();
			}
			
			txtQuantidadeVendida.setVlrBigDecimal( totqtdaprov );
			if(txtCodProd.getVlrInteger()>0) {				
				txtQuantidadeEstoque.setVlrBigDecimal( totqtdestoq );
				txtQuantidadeProducao.setVlrBigDecimal( totqtdemprod );
				txtQuantidadeProduzir.setVlrBigDecimal( totqtdaprod );
			}
			else {
				txtQuantidadeEstoque.setVlrString( "-" );
				txtQuantidadeProducao.setVlrString( "-");
				txtQuantidadeProduzir.setVlrString( "-" );					
			}
			
			// Permitindo reordenação
			if(row>0) {
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabDet.getModel());    
			
					/*	
				  sorter.addRowSorterListener(new RowSorterListener() {  
	                    public void sorterChanged(RowSorterEvent e) {  
	                    	 if(e.getType() == RowSorterEvent.Type.SORTED) {  
	                    		 System.out.println("teste");  
	                    	 }
	                    }  
	                });
	                
	                */

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
	
	private void montaGridAgrup() {
		
		try {

			if( "N".equals( cbAgrupProd.getVlrString() ) &&  
				 	"N".equals( cbAgrupCli.getVlrString() ) &&
				 	"N".equals( cbAgrupDataAprov.getVlrString() ) &&
				 	"N".equals( cbAgrupDataEntrega.getVlrString() ) ) {		
			
				Funcoes.mensagemInforma( this, "Deve haver ao menos um critério de agrupamento!" );
				return;
			}
			
			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			 
			sql.append( "pd.codprod, pd.descprod, coalesce(sp.sldliqprod,0) qtdestoque, sum(coalesce(io.qtdaprovitorc,0)) qtdaprov, sum(coalesce(op.qtdprevprodop,0)) qtdemprod ");
			
	
			int igroup = 5; // Numero padrão de campos
			
			if( "S".equals( cbAgrupDataAprov.getVlrString() ) ) {
				sql.append( ",io.dtaprovitorc dataaprov ");
				igroup++; // Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}
			
			if( "S".equals( cbAgrupDataEntrega.getVlrString() ) ) {
				sql.append( ",io.dtaprovitorc + coalesce(oc.prazoentorc,0) dataentrega ");
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}

			if( "S".equals( cbAgrupCli.getVlrString() ) ) {
				sql.append( ",cl.codcli, cl.razcli ");
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do groupby
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}

			sql.append( "from vdorcamento oc left outer join vditorcamento io on ");
			sql.append( "io.codemp=oc.codemp and io.codfilial=oc.codfilial and io.codorc=oc.codorc and io.tipoorc=oc.tipoorc ");
			sql.append( "left outer join vdcliente cl on cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli ");
			sql.append( "left outer join eqproduto pd on pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod ");
			sql.append( "left outer join ppop op on op.codemppd=pd.codemp and op.codfilial=pd.codfilial and op.codprod=pd.codprod and op.sitop in ('PE','BL') ");
			sql.append( "left outer join eqsaldoprod sp on sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod ");

			sql.append( "where oc.codemp=? and oc.codfilial=? and io.aprovitorc='S' and io.sitproditorc='PE' and pd.tipoprod='F' ");

			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " and io.codemppd=? and io.codfilialpd=? and io.codprod=? " );				
			}
			if(txtCodCli.getVlrInteger()>0) {
				sql.append( " and oc.codempcl=? and oc.codfilialcl=? and oc.codcli=? " );				
			}

			if( "S".equals( cbAgrupProd.getVlrString() ) ||  
			 	"S".equals( cbAgrupCli.getVlrString() ) ||
			 	"S".equals( cbAgrupDataAprov.getVlrString() ) ||
			 	"S".equals( cbAgrupDataEntrega.getVlrString() ) ) {			
			
				sql.append( "group by ");	
			
				if( "S".equals( cbAgrupProd.getVlrString() ) ) {
					sql.append( "1, 2, 3 ");				
				}
			
				if( igroup>5 )  {				
					for(int i=5; i<igroup; i++) {
						sql.append( "," + (i+1) );
					}
				}

			}
			
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );

			if(txtCodProd.getVlrInteger()>0) {
				ps.setInt( iparam++, lcProd.getCodEmp() );
				ps.setInt( iparam++, lcProd.getCodFilial() );
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );								
			}
			if(txtCodCli.getVlrInteger()>0) {
				ps.setInt( iparam++, lcCliente.getCodEmp() );
				ps.setInt( iparam++, lcCliente.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );								
			}	
			
			ResultSet rs = ps.executeQuery();		
			
			tabAgrup.limpa();
						
			int row = 0;
			
			BigDecimal totqtdaprov = new BigDecimal(0);
			BigDecimal totqtdestoq = new BigDecimal(0);
			BigDecimal totqtdemprod = new BigDecimal(0);
			BigDecimal totqtdaprod = new BigDecimal(0);			
			
			while ( rs.next() ) {
				
				tabAgrup.adicLinha();
				
				tabAgrup.setValor( new Boolean(true), row, AGRUPAMENTO.MARCACAO.ordinal() );
				tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODPROD.toString() ), row, AGRUPAMENTO.CODPROD.ordinal() );
				tabAgrup.setValor( rs.getString( AGRUPAMENTO.DESCPROD.toString().trim() ), row, AGRUPAMENTO.DESCPROD.ordinal() );
				
				if("S".equals( cbAgrupDataAprov.getVlrString() )){
					tabAgrup.setColunaVisivel( 60, AGRUPAMENTO.DATAAPROV.ordinal() );					
					tabAgrup.setValor( Funcoes.dateToStrDate( rs.getDate( AGRUPAMENTO.DATAAPROV.toString() ) ), row, AGRUPAMENTO.DATAAPROV.ordinal() );
				}
				else {
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAAPROV.ordinal() );
				}
				
				if("S".equals( cbAgrupDataEntrega.getVlrString() )){
					tabAgrup.setColunaVisivel( 60, AGRUPAMENTO.DATAENTREGA.ordinal() );					
					tabAgrup.setValor( Funcoes.dateToStrDate( rs.getDate( AGRUPAMENTO.DATAENTREGA.toString() ) ), row, AGRUPAMENTO.DATAENTREGA.ordinal() );
				}
				else {
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAENTREGA.ordinal() );
				}
				
				if("S".equals( cbAgrupCli.getVlrString() )){
					tabAgrup.setColunaVisivel( 40, AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setColunaVisivel( 145,AGRUPAMENTO.RAZCLI.ordinal() );
					tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODCLI.toString() ), row, AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setValor( rs.getString( AGRUPAMENTO.RAZCLI.toString().trim() ), row, AGRUPAMENTO.RAZCLI.ordinal() );					
				}				
				else {
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.RAZCLI.ordinal() );					
				}

				BigDecimal qtdaprov = rs.getBigDecimal( AGRUPAMENTO.QTDAPROV.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdestoque = rs.getBigDecimal( AGRUPAMENTO.QTDESTOQUE.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdemprod = rs.getBigDecimal( AGRUPAMENTO.QTDEMPROD.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdaprod = qtdaprov.subtract( qtdestoque ).subtract( qtdemprod ).setScale( Aplicativo.casasDec );
				
				if(qtdaprod.floatValue() < 0) {
					qtdaprod = new BigDecimal(0);
				}
				
				totqtdaprov = totqtdaprov.add( qtdaprov );
				totqtdestoq = qtdestoque; // Não deve ser somado, pois varia no produto
				totqtdemprod = qtdemprod; // Não deve ser somado, pois vaira no produto				
				 
				tabAgrup.setValor( imgColuna, row, AGRUPAMENTO.STATUS.ordinal() );
							
				tabAgrup.setValor( qtdaprov, row, AGRUPAMENTO.QTDAPROV.ordinal() );				
				tabAgrup.setValor( qtdestoque, row, AGRUPAMENTO.QTDESTOQUE.ordinal() );
				tabAgrup.setValor( qtdemprod, row, AGRUPAMENTO.QTDEMPROD.ordinal() );
				tabAgrup.setValor( qtdaprod , row, AGRUPAMENTO.QTDAPROD.ordinal() );
				
				row++;
				
			}
			
			totqtdaprod = (totqtdestoq.subtract( totqtdaprov ));
			
			if(totqtdaprod.floatValue()>0) {
				totqtdaprod = new BigDecimal( 0 );
			}
			else {
				totqtdaprod = totqtdaprod.abs();
			}
						
			// Permitindo reordenação
		
			if(row>0) {
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabAgrup.getModel());    
				tabAgrup.setRowSorter(sorter);				   
			}
			else {
				tabAgrup.setRowSorter( null );
			}
			
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}

	
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btBuscar ) {
			montaGrids();
		}
		else if ( e.getSource() == btSelectAllDet ) {
			selectNecessarios(tabDet);
		}
		else if ( e.getSource() == btDeselectAllDet ) {
			deselectAll(tabDet);
		}
		else if ( e.getSource() == btLimparGridDet ) {
			limpaNaoSelecionados( tabDet );
		}		
		else if ( e.getSource() == btSelectAllAgrup ) {
			selectAll(tabAgrup);
		}
		else if ( e.getSource() == btDeselectAllAgrup ) {
			deselectAll(tabAgrup);
		}
		else if ( e.getSource() == btSelectNecessariosAgrup ) {
			selectNecessarios(tabAgrup);
		}
		else if ( e.getSource() == btLimparGridAgrup ) {
			limpaNaoSelecionados( tabAgrup );
		}
		else if ( e.getSource() == btIniProdDet ) {
			processaOPS(true);
		}
		
	}

	public void valorAlterado( TabelaSelEvent e ) {
		/*		
		if ( e.getTabela() == tabOrcamentos && tabOrcamentos.getLinhaSel() > -1 && !carregandoOrcamentos ) {
			buscaItensVenda( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), "V" );
		}
		*/
	}

	public void mouseClicked( MouseEvent mevt ) {
		Tabela tabEv = (Tabela) mevt.getSource();
		
		if ( mevt.getClickCount() == 2 ) {					
			if( tabEv == tabDet && tabEv.getLinhaSel() > -1 ) {
				ImageIcon imgclicada = (ImageIcon) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.STATUS.ordinal() );
				
				if(imgclicada.equals( imgPendente )) {
					FOrcamento orc = null;    
					if ( Aplicativo.telaPrincipal.temTela( FOrcamento.class.getName() ) ) {
						orc = (FOrcamento) Aplicativo.telaPrincipal.getTela( FOrcamento.class.getName() );
					}
					else {
						orc = new FOrcamento();
						Aplicativo.telaPrincipal.criatela( "Orçamento", orc, con );
					}    	    		 
					orc.exec( (Integer) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.CODORC.ordinal() ) );
				}
				else {
					FOP op = new FOP((Integer) tabDet.getValor( tabEv.getLinhaSel(), DETALHAMENTO.CODOP.ordinal() ), (Integer) tabDet.getValor( tabEv.getLinhaSel(), DETALHAMENTO.SEQOP.ordinal() ));
					Aplicativo.telaPrincipal.criatela( "Ordens de produção", op, con );    	    		 
				}
			}
		}
		if ( (tabEv == tabDet || tabEv == tabAgrup) && (tabEv.getLinhaSel() > -1) ) {
			
			Boolean selecionado = (Boolean) tabEv.getValor( tabEv.getLinhaSel(), 0 );
			BigDecimal qtdaprod = null;
			ImageIcon imgclicada = null;
			
			if(tabEv == tabDet) {
				qtdaprod = (BigDecimal) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.QTDAPROD.ordinal() );
				imgclicada = (ImageIcon) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.STATUS.ordinal() );
			}
			else if (tabEv == tabAgrup){
				qtdaprod = (BigDecimal) tabEv.getValor( tabEv.getLinhaSel(), AGRUPAMENTO.QTDAPROD.ordinal() );				
			}
			if(qtdaprod.floatValue()>0 && imgclicada.equals( imgPendente )) {
				tabEv.setValor( ! ( selecionado ).booleanValue(), tabEv.getLinhaSel(), 0 );
			}
			else if(! ( selecionado ).booleanValue()){
				Funcoes.mensagemInforma( this, "Quantidade a produzir inválida\nOu item já processado!" );
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

	private void montaGrids() {
		montaGridDet();
		montaGridAgrup();
	}
	
	public void afterCarrega( CarregaEvent e ) {

		if ( lcProd == e.getListaCampos() ) {
			montaGrids();
		}
		else if ( lcCliente == e.getListaCampos() ) {
			montaGrids();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCliente.setConexao( con );
		lcProd.setConexao( con );
		
	}

	public void valorAlterado( TabelaEditEvent evt ) {

		// TODO Auto-generated method stub
		
	}
	
	private void selectAll(Tabela tab) {
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}
	
	private void limpaNaoSelecionados(Tabela tab) {
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
	
	private void selectNecessarios(Tabela tab) {
		BigDecimal qtdaprod = null;
		
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			qtdaprod = (BigDecimal) tab.getValor( i, tab==tabDet ? DETALHAMENTO.QTDAPROD.ordinal() : AGRUPAMENTO.QTDAPROD.ordinal() );			
			tab.setValor( new Boolean (qtdaprod.floatValue()>0), i, 0 );			
		}
	}

	private void deselectAll(Tabela tab) {
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) { 
			tab.setValor( new Boolean( false ), i, 0 );
		}
	}
	
	private void processaOPS(boolean det) {	
		
		if(Funcoes.mensagemConfirma( this, "Confirma o processamento dos itens selecionados?" )==JOptionPane.YES_OPTION) {
			if(det) {
				processaOPSDiretoDet();
			}
		}
	}
	
	private void processaOPSDiretoDet() {		
		StringBuffer sql = new StringBuffer();
		sql.append( "select codopret,seqopret " );
		sql.append( "from ppgeraop(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " );
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		BigDecimal qtdsugerida = null;
		DLLoading loading = new DLLoading();
		
		try {
			for(int i=0; i<tabDet.getNumLinhas(); i++) {
				loading.start();
				qtdsugerida = (BigDecimal)(tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal() ));
				
				// Caso o item do grid esteja selecionado... 
				if( (Boolean)(tabDet.getValor( i, DETALHAMENTO.MARCACAO.ordinal() )) && qtdsugerida.floatValue()>0 ) {
					try {
						 ps = con.prepareStatement( sql.toString() );
			
						 /*
							SEQEST 
						*/
						
						ps.setInt( PROCEDUREOP.CODEMPOP.ordinal() + 1, Aplicativo.iCodEmp );
						ps.setInt( PROCEDUREOP.CODFILIALOP.ordinal() + 1, Aplicativo.iCodFilial );
						ps.setNull( PROCEDUREOP.CODOP.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.SEQOP.ordinal() + 1, Types.INTEGER );
						
						ps.setInt( PROCEDUREOP.CODEMPPD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODEMPPD.ordinal() ) );
						ps.setInt( PROCEDUREOP.CODFILIALPD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODFILIALPD .ordinal()) );					
						ps.setInt( PROCEDUREOP.CODPROD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODPROD.ordinal()) );
						
						ps.setInt( PROCEDUREOP.CODEMPOC.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODEMPOC.ordinal()) );
						ps.setInt( PROCEDUREOP.CODFILIALOC.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODFILIALOC.ordinal()) );
						ps.setInt( PROCEDUREOP.CODORC.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODORC.ordinal()) );
						ps.setInt( PROCEDUREOP.CODITORC.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODITORC.ordinal()) );
						ps.setString( PROCEDUREOP.TIPOORC.ordinal() + 1, (String) tabDet.getValor( i, DETALHAMENTO.TIPOORC.ordinal()) );
						
						ps.setBigDecimal( PROCEDUREOP.QTDSUGPRODOP.ordinal() + 1, (BigDecimal) tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal()) );
						
						ps.setDate( PROCEDUREOP.DTFABROP.ordinal() + 1, Funcoes.strDateToSqlDate( (String) tabDet.getValor( i, DETALHAMENTO.DTFABROP.ordinal()) ) );
						
						ps.setInt( PROCEDUREOP.SEQEST.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.SEQEST.ordinal()) );
						
						rs = ps.executeQuery();
						
						if(rs.next()) {
							System.out.println( rs.getString( 1 ) );
						}
						
						
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
		
			}
			montaGridDet();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			loading.stop();
		}
	}
	
}

