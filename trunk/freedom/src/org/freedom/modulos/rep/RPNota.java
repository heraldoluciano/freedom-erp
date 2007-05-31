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
import java.sql.Connection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class RPNota extends FDados {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelNota = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
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

	private final JTextFieldPad txtFrete = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldPad txtRemessa = new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );

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

	private final ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private final ListaCampos lcVendedor = new ListaCampos( this, "VD" );

	private final ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private final ListaCampos lcMoeda = new ListaCampos( this, "MO" );

	private final ListaCampos lcFornecedor = new ListaCampos( this, "FO" );

	private final ListaCampos lcTransportadora = new ListaCampos( this, "TP" );

	private final ListaCampos lcProduto = new ListaCampos( this, "PD" );

	private final ListaCampos lcFornecedorItem = new ListaCampos( this, "FO" );

	private final ListaCampos lcPedido = new ListaCampos( this, "" );

	private List<Object> prefere = null;

	public RPNota() {

		super( false );
		setTitulo( "Cadastro de Pedidos" );
		setAtribos( 50, 50, 700, 500 );

		montaListaCampos();

		montaTela();
		
	}

	private void montaListaCampos() {

		/**********
		 * PEDIDO *
		 **********/

		lcPedido.add( new GuardaCampo( txtCodPed, "CODPED", "Cód.ped.", ListaCampos.DB_PK, false ) );		
		lcPedido.add( new GuardaCampo( txtDataPed, "DATAPED", "Data ped.", ListaCampos.DB_SI, false ) );	
		lcPedido.add( new GuardaCampo( txtCodCli, "CODCLI", "Cód.cli.", ListaCampos.DB_FK, false ) );
		lcPedido.add( new GuardaCampo( txtCodVend, "CODVEND", "Cód.vend.", ListaCampos.DB_FK, false ) );
		lcPedido.add( new GuardaCampo( txtCodPlanoPag, "CODPLANOPAG", "Cód.p.pag.", ListaCampos.DB_FK, false ) );
		lcPedido.add( new GuardaCampo( txtCodFor, "CODFOR", "Cód.for.", ListaCampos.DB_FK, false ) );
		lcPedido.add( new GuardaCampo( txtCodTran, "CODTRAN", "Cód.tran.", ListaCampos.DB_FK, false ) );
		lcPedido.add( new GuardaCampo( txtCodMoeda, "CODMOEDA", "Moeda", ListaCampos.DB_FK, false ) );
		lcPedido.add( new GuardaCampo( txtCodPedFor, "NUMPEDCLI", "Cód.ped.cli.", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtCodPedCli, "NUMPEDFOR", "Cód.ped.for.", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtFrete, "TIPOFRETEPED", "Frete", ListaCampos.DB_SI, false ) );
		lcPedido.add( new GuardaCampo( txtRemessa, "TIPOREMPED", "Remessa", ListaCampos.DB_SI, false ) );		
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

	private void montaTela() {

		/***********
		 * PEDIDOS *
		 ***********/

		panelPedido.setPreferredSize( new Dimension( 500, 150 ) );
		panelPedido.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Pedido" ) );

		panelPedido.adic( new JLabel( "Pedido" ), 7, 0, 70, 20 );
		panelPedido.adic( txtCodPed, 7, 20, 70, 20 );
		panelPedido.adic( new JLabel( "Data" ), 80, 0, 70, 20 );
		panelPedido.adic( txtDataPed, 80, 20, 70, 20 );
		panelPedido.adic( new JLabel( "Cód.cli." ), 153, 0, 70, 20 );
		panelPedido.adic( txtCodCli, 153, 20, 70, 20 );
		panelPedido.adic( new JLabel( "Razão social do cliente" ), 226, 0, 187, 20 );
		panelPedido.adic( txtRazCli, 226, 20, 187, 20 );
		panelPedido.adic( new JLabel( "Pedido X For." ), 416, 0, 80, 20 );
		panelPedido.adic( txtCodPedFor, 416, 20, 80, 20 );
		panelPedido.adic( new JLabel( "Pedido X Cli." ), 499, 0, 80, 20 );
		panelPedido.adic( txtCodPedCli, 499, 20, 80, 20 );
		panelPedido.adic( new JLabel( "Moeda" ), 582, 0, 90, 20 );
		panelPedido.adic( txtCodMoeda, 582, 20, 90, 20 );

		panelPedido.adic( new JLabel( "Cód.vend." ), 7, 40, 70, 20 );
		panelPedido.adic( txtCodVend, 7, 60, 70, 20 );
		panelPedido.adic( new JLabel( "Nome do vendedor" ), 80, 40, 210, 20 );
		panelPedido.adic( txtNomeVend, 80, 60, 210, 20 );
		panelPedido.adic( new JLabel( "Cód.p.pag." ), 293, 40, 70, 20 );
		panelPedido.adic( txtCodPlanoPag, 293, 60, 70, 20 );
		panelPedido.adic( new JLabel( "Plano de pagamento" ), 366, 40, 213, 20 );
		panelPedido.adic( txtDescPlanoPag, 366, 60, 213, 20 );
		panelPedido.adic( new JLabel( "Frete" ), 582, 40, 90, 20 );
		panelPedido.adic( txtFrete, 582, 60, 90, 20 );

		panelPedido.adic( new JLabel( "Cód.for." ), 7, 80, 70, 20 );
		panelPedido.adic( txtCodFor, 7, 100, 70, 20 );
		panelPedido.adic( new JLabel( "Razão social do fornecedor" ), 80, 80, 210, 20 );
		panelPedido.adic( txtRazFor, 80, 100, 210, 20 );
		panelPedido.adic( new JLabel( "Cód.transp." ), 293, 80, 70, 20 );
		panelPedido.adic( txtCodTran, 293, 100, 70, 20 );
		panelPedido.adic( new JLabel( "Razão social da transportadora" ), 366, 80, 213, 20 );
		panelPedido.adic( txtRazTran, 366, 100, 213, 20 );
		panelPedido.adic( new JLabel( "Remessa" ), 582, 80, 90, 20 );
		panelPedido.adic( txtRemessa, 582, 100, 90, 20 );
		
		panelNota.add( panelPedido, BorderLayout.NORTH );
		
		txtDataPed.setAtivo( false );
		txtCodCli.setAtivo( false );
		txtCodPedFor.setAtivo( false );
		txtCodPedCli.setAtivo( false );
		txtCodMoeda.setAtivo( false );
		txtCodVend.setAtivo( false );
		txtCodPlanoPag.setAtivo( false );
		txtFrete.setAtivo( false );
		txtCodFor.setAtivo( false );
		txtCodTran.setAtivo( false );
		txtRemessa.setAtivo( false );

		/*********
		 * ITENS *
		 *********/

		panelNota.add( panelTotaisItens, BorderLayout.SOUTH );

		panelTotaisItens.setPreferredSize( new Dimension( 300, 65 ) );
		panelTotaisItens.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Totais do pedido" ) );
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
		
		pnCliente.add( panelNota, BorderLayout.NORTH );
	
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

		pinDados.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Itens" ) );
		
		txtCodForItem.setAtivo( false );
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
