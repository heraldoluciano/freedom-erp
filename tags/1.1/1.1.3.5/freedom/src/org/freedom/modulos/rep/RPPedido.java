/**
 * @version 03/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPPedido.java <BR>
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
 * Tela de cadastro de pedidos de vendas.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.rep.RPPrefereGeral.EPrefere;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FObservacao;
import org.freedom.telas.FPrinterJob;

public class RPPedido extends FDetalhe implements CarregaListener, InsertListener, DeleteListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private static final BigDecimal bdCem = new BigDecimal( "100" );

	private final JPanelPad panelPedido = new JPanelPad();

	private final JPanelPad panelItens = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelTotaisItens = new JPanelPad();

	private final JPanelPad panelCamposItens = new JPanelPad();

	private final JTextFieldPad txtCodPed = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDataPed = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtDescMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtRazTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodPedFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCodPedCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCodItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtQtdItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private final JTextFieldPad txtPrecoItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtVlrItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPercIPIItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 8, Aplicativo.casasDec );

	private final JTextFieldPad txtVlrIPIItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPercDescItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 8, Aplicativo.casasDec );

	private final JTextFieldPad txtVlrDescItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPercAdicItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 8, Aplicativo.casasDec );

	private final JTextFieldPad txtVlrAdicItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPercRedItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 8, Aplicativo.casasDec );

	private final JTextFieldPad txtVlrRecItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPercPagItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 8, Aplicativo.casasDec );

	private final JTextFieldPad txtVlrPagItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtCodForItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtRazForItem = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtVlrLiqItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtVlrTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtVlrLiqPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtQdtTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private final JTextFieldPad txtIPITotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtDescTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtAdicTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtRecTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPagTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtObsPed = new JTextFieldPad( JTextFieldPad.TP_STRING, 500, 0 );

	private JRadioGroup rgFrete;

	private JRadioGroup rgRemessa;
	
	private final JButton btObsPed = new JButton( "Obs.", Icone.novo( "btObs.gif" ) );

	private final ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private final ListaCampos lcVendedor = new ListaCampos( this, "VD" );

	private final ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private final ListaCampos lcMoeda = new ListaCampos( this, "MO" );

	private final ListaCampos lcFornecedor = new ListaCampos( this, "FO" );

	private final ListaCampos lcTransportadora = new ListaCampos( this, "TP" );

	private final ListaCampos lcProduto = new ListaCampos( this, "PD" );

	private final ListaCampos lcFornecedorItem = new ListaCampos( this, "FO" );

	private final ListaCampos lcPedido = new ListaCampos( this, "" );
	
	private List<Object> prefere = new ArrayList<Object>();

	public RPPedido() {

		super( false );
		setTitulo( "Cadastro de Pedidos" );
		setAtribos( 50, 50, 700, 500 );

		montaRadioGrups();
		montaListaCampos();

		montaMaster();
		setListaCampos( true, "PEDIDO", "RP" );

		setListaCampos( lcDet );
		setNavegador( navRod );
		montaDetale();
		setListaCampos( true, "ITPEDIDO", "RP" );
		montaTab();

		tab.setTamColuna( 50, 0 );
		tab.setTamColuna( 100, 1 );
		tab.setTamColuna( 235, 2 );
		tab.setTamColuna( 100, 3 );
		tab.setTamColuna( 100, 4 );
		tab.setTamColuna( 100, 5 );
		
		setImprimir( true );
		btPrevimp.addActionListener( this );
		btImp.addActionListener( this );

		lcCampos.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcCliente.addCarregaListener( this );
		lcProduto.addCarregaListener( this );
		
		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );
		
		lcDet.addPostListener( this );
		
		lcDet.addDeleteListener( this );
		
		btObsPed.addActionListener( this );
		
		txtPercPagItem.addFocusListener( this );
		txtPercIPIItem.addFocusListener( this );
	}

	private void montaRadioGrups() {

		Vector<String> labs = new Vector<String>();
		labs.add( "CIF" );
		labs.add( "FOB" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "F" );
		rgFrete = new JRadioGroup( 1, 2, labs, vals );

		Vector<String> labs1 = new Vector<String>();
		labs1.add( "Molote" );
		labs1.add( "Correio" );
		labs1.add( "Fone" );
		labs1.add( "Fax" );
		labs1.add( "e-mail" );
		Vector<String> vals1 = new Vector<String>();
		vals1.add( "M" );
		vals1.add( "C" );
		vals1.add( "T" );
		vals1.add( "F" );
		vals1.add( "E" );
		rgRemessa = new JRadioGroup( 5, 1, labs1, vals1 );
	}

	private void montaListaCampos() {

		/**********
		 * PEDIDO *
		 **********/

		lcPedido.add( new GuardaCampo( txtCodPed, "CODPED", "Cód.ped.", ListaCampos.DB_PK, false ) );
		lcPedido.add( new GuardaCampo( txtVlrTotPed, "VLRTOTPED", "Pedido", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtVlrLiqPed, "VLRLIQPED", "Liquido", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtQdtTotPed, "QTDTOTPED", "Itens", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtIPITotPed, "VLRIPIPED", "IPI", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtDescTotPed, "VLRDESCPED", "Desconto", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtAdicTotPed, "VLRADICPED", "Adicional", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtRecTotPed, "VLRRECPED", "Receber", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtPagTotPed, "VLRPAGPED", "Pagar", ListaCampos.DB_SI, false ) );
		lcPedido.montaSql( false, "PEDIDO", "RP" );
		lcPedido.setQueryCommit( false );
		lcPedido.setReadOnly( true );

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

		/************
		 * VENDEDOR *
		 ************/

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.vend.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "RP" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );

		/**********************
		 * PLANO DE PAGAMENTO *
		 **********************/

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "RP" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );

		/*********
		 * MOEDA *
		 *********/

		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK, false ) );
		lcMoeda.add( new GuardaCampo( txtDescMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "RP" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setTabelaExterna( lcMoeda );

		/**************
		 * FORNECEDOR *
		 **************/

		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFornecedor );

		/******************
		 * TRANSPORTADORA *
		 ******************/

		lcTransportadora.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.transp.", ListaCampos.DB_PK, false ) );
		lcTransportadora.add( new GuardaCampo( txtRazTran, "RazTran", "Razão social da transportadora", ListaCampos.DB_SI, false ) );
		lcTransportadora.montaSql( false, "TRANSP", "RP" );
		lcTransportadora.setQueryCommit( false );
		lcTransportadora.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcTransportadora );

		/***********
		 * PRODUTO *
		 ***********/

		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtCodForItem, "CodFor", "Cód.for.", ListaCampos.DB_SI, false ) );
		lcProduto.montaSql( false, "PRODUTO", "RP" );
		lcProduto.setQueryCommit( false );
		lcProduto.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProduto );

		/****************
		 * FORNECEDOR 2 *
		 ****************/

		lcFornecedorItem.add( new GuardaCampo( txtCodForItem, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFornecedorItem.add( new GuardaCampo( txtRazForItem, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedorItem.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedorItem.setQueryCommit( false );
		lcFornecedorItem.setReadOnly( true );
		txtCodForItem.setTabelaExterna( lcFornecedorItem );

	}

	private void montaMaster() {

		/***********
		 * PEDIDOS *
		 ***********/

		setAltCab( 175 );
		setPainel( panelPedido, pnCliCab );

		adicCampo( txtCodPed, 7, 20, 70, 20, "CodPed", "Pedido", ListaCampos.DB_PK, true );
		adicCampo( txtDataPed, 80, 20, 70, 20, "DataPed", "Data", ListaCampos.DB_SI, true );

		adicCampo( txtCodCli, 153, 20, 70, 20, "CodCli", "Cód.cli.", ListaCampos.DB_FK, txtRazCli, true );
		adicDescFK( txtRazCli, 226, 20, 187, 20, "RazCli", "Razão social do cliente" );
		adicCampo( txtCodPedFor, 416, 20, 80, 20, "NumPedFor", "Pedido X For.", ListaCampos.DB_SI, false );
		adicCampo( txtCodPedCli, 499, 20, 80, 20, "NumPedCli", "Pedido X Cli.", ListaCampos.DB_SI, false );

		adicCampo( txtCodVend, 7, 60, 70, 20, "CodVend", "Cód.vend.", ListaCampos.DB_FK, txtRazCli, true );
		adicDescFK( txtNomeVend, 80, 60, 155, 20, "NomeVend", "Nome do vendedor" );
		adicCampo( txtCodPlanoPag, 238, 60, 70, 20, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK, txtRazCli, true );
		adicDescFK( txtDescPlanoPag, 311, 60, 155, 20, "DescPlanoPag", "Plano de pagamento" );
		adicCampo( txtCodMoeda, 469, 60, 110, 20, "CodMoeda", "Cód.moeda", ListaCampos.DB_FK, txtRazCli, true );

		adicCampo( txtCodFor, 7, 100, 70, 20, "CodFor", "Cód.for.", ListaCampos.DB_FK, txtRazCli, true );
		adicDescFK( txtRazFor, 80, 100, 155, 20, "RazFor", "Razão social do fornecedor" );
		adicCampo( txtCodTran, 238, 100, 70, 20, "CodTran", "Cód.transp.", ListaCampos.DB_FK, txtRazCli, false );
		adicDescFK( txtRazTran, 311, 100, 155, 20, "RazTran", "Razão social da transportadora" );

		adicDB( rgRemessa, 584, 20, 90, 110, "TipoRemPed", "Remessa", false );
		adicDB( rgFrete, 469, 100, 110, 30, "TipoFretePed", "Frete", false );

	}

	private void montaDetale() {

		/*********
		 * ITENS *
		 *********/

		pnDet.add( panelItens, BorderLayout.CENTER );

		panelItens.add( panelTotaisItens, BorderLayout.NORTH );

		panelTotaisItens.setPreferredSize( new Dimension( 300, 65 ) );
		panelTotaisItens.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Totais" ) );
		panelTotaisItens.adic( new JLabel( "Pedido" ), 7, 0, 91, 15 );
		panelTotaisItens.adic( txtVlrLiqPed, 7, 15, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Itens" ), 101, 0, 91, 15 );
		panelTotaisItens.adic( txtQdtTotPed, 101, 15, 91, 20 );
		panelTotaisItens.adic( new JLabel( "IPI" ), 195, 0, 91, 15 );
		panelTotaisItens.adic( txtIPITotPed, 195, 15, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Desconto" ), 289, 0, 91, 15 );
		panelTotaisItens.adic( txtDescTotPed, 289, 15, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Adicional" ), 383, 0, 91, 15 );
		panelTotaisItens.adic( txtAdicTotPed, 383, 15, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Receber" ), 477, 0, 91, 15 );
		panelTotaisItens.adic( txtRecTotPed, 477, 15, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Pagar" ), 571, 0, 91, 15 );
		panelTotaisItens.adic( txtPagTotPed, 571, 15, 97, 20 );

		txtVlrLiqPed.setAtivo( false );
		txtVlrTotPed.setAtivo( false );
		txtQdtTotPed.setAtivo( false );
		txtIPITotPed.setAtivo( false );
		txtDescTotPed.setAtivo( false );
		txtAdicTotPed.setAtivo( false );
		txtRecTotPed.setAtivo( false );
		txtPagTotPed.setAtivo( false );

		setAltDet( 175 );
		setPainel( panelCamposItens, panelItens );

		panelCamposItens.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Itens" ) );

		adicCampo( txtCodItem, 7, 15, 70, 20, "CodItPed", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodProd, 80, 15, 70, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, true );
		adicDescFK( txtDescProd, 153, 15, 246, 20, "DescProd", "Descrição do produto" );
		adicCampo( txtQtdItem, 402, 15, 92, 20, "QtdItPed", "Qtd.", ListaCampos.DB_SI, true );
		adicCampo( txtPrecoItem, 497, 15, 94, 20, "PrecoItPed", "Preço", ListaCampos.DB_SI, true );
		adicCampoInvisivel( txtVlrItem, "VlrItPed", "Valor item", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrLiqItem, "VlrLiqItPed", "Liquido", ListaCampos.DB_SI, false );
		adicCampo( txtPercIPIItem, 594, 15, 72, 20, "PercIPIItPed", "% IPI", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrIPIItem, "VlrIPIItPed", "Valor IPI", ListaCampos.DB_SI, false );

		adicCampo( txtPercDescItem, 7, 55, 70, 20, "PercDescItPed", "% Desconto", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrDescItem, "VlrDescItPed", "Vlr. Desconto", ListaCampos.DB_SI, false );
		adicCampo( txtPercAdicItem, 80, 55, 70, 20, "PercAdicItPed", "% Acrécimo", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrAdicItem, "VlrAdicItPed", "Vlr. Adicional", ListaCampos.DB_SI, false );
		adicCampo( txtPercRedItem, 153, 55, 80, 20, "PercRecItPed", "% Receber", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrRecItem, "VlrRecItPed", "Vlr. Receber", ListaCampos.DB_SI, false );
		adicCampo( txtPercPagItem, 236, 55, 80, 20, "PercPagItPed", "% Pagar", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrPagItem, "VlrPagItPed", "Vlr. Pagar", ListaCampos.DB_SI, false );
		adic( new JLabel( "Cód.for." ), 319, 35, 80, 20 );
		adic( txtCodForItem, 319, 55, 80, 20 );
		adic( new JLabel( "Razão social do fornecedor" ), 402, 35, 189, 20 );
		adic( txtRazForItem, 402, 55, 189, 20 );
		
		txtCodForItem.setAtivo( false );
		
		panelCamposItens.adic( btObsPed, 595, 45, 70, 30 );
	}
	
	private void calculaValorItem() {

		if ( txtQtdItem.getVlrBigDecimal() != null ) {
			txtVlrItem.setVlrBigDecimal( txtQtdItem.getVlrBigDecimal().multiply( txtPrecoItem.getVlrBigDecimal() ) );
		}
	}
	
	private void calculaValorLiquido() {

		if ( txtVlrItem.getVlrBigDecimal() != null ) {
			
			txtVlrLiqItem.setVlrBigDecimal( txtVlrItem.getVlrBigDecimal() );
			
			if ( txtVlrDescItem.getVlrBigDecimal() != null ) {
				txtVlrLiqItem.setVlrBigDecimal( txtVlrLiqItem.getVlrBigDecimal().subtract( txtVlrDescItem.getVlrBigDecimal() ) );
			}
			if ( txtVlrAdicItem.getVlrBigDecimal() != null ) {
				txtVlrLiqItem.setVlrBigDecimal( txtVlrLiqItem.getVlrBigDecimal().add( txtVlrAdicItem.getVlrBigDecimal() ) );
			}
			if ( txtVlrIPIItem.getVlrBigDecimal() != null ) {
				txtVlrLiqItem.setVlrBigDecimal( txtVlrLiqItem.getVlrBigDecimal().add( txtVlrIPIItem.getVlrBigDecimal() ) );
			}
		}
	}

	
	private void calculaValorIpi() {

		if ( txtPercIPIItem.getVlrBigDecimal() != null && txtVlrItem.getVlrBigDecimal() != null ) {
			txtVlrIPIItem.setVlrBigDecimal( ( txtVlrItem.getVlrBigDecimal().divide( bdCem ) ).multiply( txtPercIPIItem.getVlrBigDecimal() ) );
		}
	}

	
	private void calculaValorDesconto() {

		if ( txtPercDescItem.getVlrBigDecimal() != null && txtVlrItem.getVlrBigDecimal() != null ) {
			txtVlrDescItem.setVlrBigDecimal( ( txtVlrItem.getVlrBigDecimal().divide( bdCem ) ).multiply( txtPercDescItem.getVlrBigDecimal() ) );
		}
	}

	
	private void calculaValorAdicional() {

		if ( txtPercAdicItem.getVlrBigDecimal() != null && txtVlrItem.getVlrBigDecimal() != null ) {
			txtVlrAdicItem.setVlrBigDecimal( ( txtVlrItem.getVlrBigDecimal().divide( bdCem ) ).multiply( txtPercAdicItem.getVlrBigDecimal() ) );
		}
	}

	
	private void calculaValorRecebimento() {

		if ( txtPercRedItem.getVlrBigDecimal() != null && txtVlrItem.getVlrBigDecimal() != null ) {
			txtVlrRecItem.setVlrBigDecimal( ( txtVlrItem.getVlrBigDecimal().divide( bdCem ) ).multiply( txtPercRedItem.getVlrBigDecimal() ) );
		}
	}

	
	private void calculaValorPagamento() {

		if ( txtPercPagItem.getVlrBigDecimal() != null && txtVlrItem.getVlrBigDecimal() != null ) {
			txtVlrPagItem.setVlrBigDecimal( ( txtVlrItem.getVlrBigDecimal().divide( bdCem ) ).multiply( txtPercPagItem.getVlrBigDecimal() ) );
		}
	}
	
	private void getObservacao() {
		
		if ( txtCodPed.getVlrInteger() != null && txtCodPed.getVlrInteger() > 0 ) {
			
			FObservacao obs = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sSQLSelect = null;
			String sSQLUpdate = null;
			
			try {
			
				sSQLSelect = "SELECT OBSPED FROM RPPEDIDO WHERE CODEMP=? AND CODFILIAL=? AND CODPED=?";
				sSQLUpdate = "UPDATE RPPEDIDO SET OBSPED=? WHERE CODEMP=? AND CODFILIAL=? AND CODPED=?";
				
				ps = con.prepareStatement( sSQLSelect );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "PRPEDIDO" ) );
				ps.setInt( 3, txtCodPed.getVlrInteger() );
				rs = ps.executeQuery();
					
				if ( rs.next() ) {
					obs = new FObservacao( ( rs.getString( "OBSPED" ) != null ? rs.getString( "OBSPED" ) : "" ) );
				}
				else {
					obs = new FObservacao( "" );
				}

				rs.close();
				ps.close();

				if ( !con.getAutoCommit() ) {
					con.commit();
				}				
				if ( obs != null ) {					
					obs.setVisible( true );					
					if ( obs.OK ) {						
						try {
							ps = con.prepareStatement( sSQLUpdate );
							ps.setString( 1, obs.getTexto() );
							ps.setInt( 2, Aplicativo.iCodEmp );
							ps.setInt( 3, ListaCampos.getMasterFilial( "PRPEDIDO" ) );
							ps.setInt( 4, txtCodPed.getVlrInteger() );
							ps.executeUpdate();
							ps.close();
							if ( !con.getAutoCommit() ) {
								con.commit();
							}							
						} catch ( SQLException e ) {
							Funcoes.mensagemErro( this, "Erro ao alterar observação!\n" + e.getMessage() );
							e.printStackTrace();
						}
					}					
					obs.dispose();					
				}
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao carregar a observação!\n" + e.getMessage() );
				e.printStackTrace();
			}
		}
	}
	
	private void imprimir( final boolean visualizar ) {
		
		if ( txtCodPed.getVlrInteger() != null && txtCodPed.getVlrInteger() > 0 ) {
			
			try {
				
				String classLayout = "pedido";
				
				if ( prefere.get(  EPrefere.LAYOUTPED.ordinal() ) != null &&
						( (String) prefere.get(  EPrefere.LAYOUTPED.ordinal() ) ).trim().length() > 0 ) {
					classLayout = (String) prefere.get(  EPrefere.LAYOUTPED.ordinal() );
				}
				
				StringBuilder sql = new StringBuilder();
				
				sql.append( "SELECT IT.CODPED,P.DATAPED,P.CODCLI,C.RAZCLI,P.CODVEND,V.NOMEVEND,P.CODPLANOPAG,PG.DESCPLANOPAG, " );
				sql.append( "P.CODMOEDA,M.SINGMOEDA,P.CODFOR,F.RAZFOR,P.CODTRAN, " );
				sql.append( "(SELECT T.RAZTRAN FROM RPTRANSP T WHERE T.CODEMP=P.CODEMPTP AND T.CODFILIAL=P.CODFILIALTP AND T.CODTRAN=P.CODTRAN) AS RAZTRAN, " );
				sql.append( "P.TIPOFRETEPED,P.TIPOREMPED,P.NUMPEDCLI,P.NUMPEDFOR,P.VLRTOTPED, " );
				sql.append( "P.QTDTOTPED,P.VLRLIQPED,P.VLRIPIPED,P.VLRDESCPED,P.VLRADICPED,P.VLRRECPED, " );
				sql.append( "P.VLRPAGPED,P.OBSPED,IT.CODITPED,IT.CODPROD,PD.DESCPROD,IT.CODFOR,FI.RAZFOR, " );
				sql.append( "IT.QTDITPED,IT.PRECOITPED,IT.VLRITPED,IT.VLRLIQITPED,IT.PERCIPIITPED, " );
				sql.append( "IT.VLRIPIITPED,IT.PERCDESCITPED,IT.VLRDESCITPED,IT.PERCADICITPED, " );
				sql.append( "IT.VLRADICITPED,IT.PERCRECITPED,IT.VLRRECITPED,IT.PERCPAGITPED,IT.VLRPAGITPED,PD.CUBAGEMPROD " );
				sql.append( "FROM RPPEDIDO P, RPITPEDIDO IT, RPPRODUTO PD, RPFORNECEDOR FI, " );
				sql.append( "RPCLIENTE C,RPVENDEDOR V, RPPLANOPAG PG, RPMOEDA M, RPFORNECEDOR F " );
				sql.append( "WHERE IT.CODEMP=? AND IT.CODFILIAL=? AND IT.CODPED=? " );
				sql.append( "AND P.CODEMP=IT.CODEMP AND P.CODFILIAL=IT.CODFILIAL AND P.CODPED=IT.CODPED " );
				sql.append( "AND C.CODEMP=P.CODEMPCL AND C.CODFILIAL=P.CODFILIALCL AND C.CODCLI=P.CODCLI " );
				sql.append( "AND V.CODEMP=P.CODEMPVD AND V.CODFILIAL=P.CODFILIALVD AND V.CODVEND=P.CODVEND " );
				sql.append( "AND PG.CODEMP=P.CODEMPPG AND PG.CODFILIAL=P.CODFILIALPG AND PG.CODPLANOPAG=P.CODPLANOPAG " );
				sql.append( "AND M.CODEMP=P.CODEMPMO AND M.CODFILIAL=P.CODFILIALMO AND M.CODMOEDA=P.CODMOEDA " );
				sql.append( "AND F.CODEMP=P.CODEMPFO AND F.CODFILIAL=P.CODFILIALFO AND F.CODFOR=P.CODFOR " );
				sql.append( "AND PD.CODEMP=IT.CODEMPPD AND PD.CODFILIAL=IT.CODFILIALPD AND PD.CODPROD=IT.CODPROD " );
				sql.append( "AND FI.CODEMP=IT.CODEMPFO AND FI.CODFILIAL=IT.CODFILIALFO AND FI.CODFOR=IT.CODFOR " );
				
				if ( "S".equals( (String) prefere.get(  EPrefere.ORDEMPED.ordinal() ) ) ) {
					sql.append( "ORDER BY PD.DESCPROD " );
				}
				else {
					sql.append( "ORDER BY IT.CODITPED " );
				}
				
				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "RPPEDIDO" ) );
				ps.setInt( 3, txtCodPed.getVlrInteger() );
				ResultSet rs = ps.executeQuery();
				
				HashMap<String,Object> hParam = new HashMap<String, Object>();
	
				hParam.put( "CODEMP", Aplicativo.iCodEmp );
				//hParam.put( "SUBREPORT_DIR", RelTipoCli.class.getResource( "relatorios/" ).getPath() );
				hParam.put( "REPORT_CONNECTION", con );
				
				FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/" + classLayout +".jasper", "PEDIDO Nº " + txtCodPed.getVlrInteger(), null, rs, hParam, this );
	
				if ( visualizar ) {
					dlGr.setVisible( true );
				}
				else {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				}
				
				dispose();
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
				e.printStackTrace();
			}
		}
	}
	
	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcDet ) {
			lcProduto.carregaDados();
			lcPedido.carregaDados();
		}
		else if ( e.getListaCampos() == lcCampos ) {
			String s = txtCodPed.getVlrString();
			lcPedido.carregaDados();
			txtCodPed.setVlrString( s );
		}
		else if ( e.getListaCampos() == lcCliente ) {
			lcVendedor.carregaDados();
			lcPlanoPag.carregaDados();
		}
		else if ( e.getListaCampos() == lcProduto ) {
			lcFornecedorItem.carregaDados();
		}
	}

	public void beforeCarrega( CarregaEvent e ) { }

	
	public void afterInsert( InsertEvent e ) {

		if ( e.getListaCampos() == lcCampos ) {
			
			txtCodMoeda.setVlrString( (String) prefere.get( EPrefere.CODMOEDA.ordinal() ) );
			lcMoeda.carregaDados();
			
			txtDataPed.setVlrDate( Calendar.getInstance().getTime() );
			txtDataPed.requestFocus();
		}
		else if ( e.getListaCampos() == lcDet ) {
			
			txtCodProd.requestFocus();
		}
	}

	public void beforeInsert( InsertEvent e ) { }

	
	@ Override
	public void afterPost( PostEvent e ) {
	
		if ( e.getListaCampos() == lcCampos && lcCampos.getStatusAnt() == ListaCampos.LCS_INSERT ) {
			
			lcDet.insert( true );
		}
		super.afterPost( e );
	}

	@ Override
	public void beforePost( PostEvent e ) {
	
		if ( e.getListaCampos() == lcDet ) {
			calculaValorItem();
			calculaValorIpi();
			calculaValorDesconto();
			calculaValorAdicional();
			calculaValorRecebimento();
			calculaValorPagamento();
			calculaValorLiquido();
		}
		super.beforePost( e );
	}

	public void afterDelete( DeleteEvent e ) {
	
		if ( e.getListaCampos() == lcDet ) {
			lcPedido.carregaDados();
		}
	}

	public void beforeDelete( DeleteEvent e ) { }

	@ Override
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btObsPed ) {
			getObservacao();
		}
		else if ( e.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( e.getSource() == btImp ) {
			imprimir( false );
		}
		super.actionPerformed( e );
	}

	public void focusGained( FocusEvent e ) { }

	public void focusLost( FocusEvent e ) {

		if ( e.getSource() == txtPercPagItem && 
				( lcDet.getStatus() == ListaCampos.LCS_EDIT || lcDet.getStatus() == ListaCampos.LCS_INSERT ) ) {
			lcDet.post();
			lcDet.insert( true );
		}		
		else if ( e.getSource() == txtPercIPIItem ) {
			txtPercDescItem.requestFocus();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcCliente.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcMoeda.setConexao( cn );
		lcFornecedor.setConexao( cn );
		lcTransportadora.setConexao( cn );
		lcProduto.setConexao( cn );
		lcFornecedorItem.setConexao( cn );
		lcPedido.setConexao( cn );
		
		prefere = RPPrefereGeral.getPrefere( cn );
	}

}
