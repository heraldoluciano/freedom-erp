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
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.PostEvent;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class RPPedido extends FDetalhe implements CarregaListener, DeleteListener {

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

	private JRadioGroup rgFrete;

	private JRadioGroup rgRemessa;

	private final ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private final ListaCampos lcVendedor = new ListaCampos( this, "VD" );

	private final ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private final ListaCampos lcMoeda = new ListaCampos( this, "MO" );

	private final ListaCampos lcFornecedor = new ListaCampos( this, "FO" );

	private final ListaCampos lcTransportadora = new ListaCampos( this, "TP" );

	private final ListaCampos lcProduto = new ListaCampos( this, "PD" );

	private final ListaCampos lcFornecedorItem = new ListaCampos( this, "FO" );

	private final ListaCampos lcPedido = new ListaCampos( this, "" );

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

		lcCampos.addCarregaListener( this );
		lcDet.addPostListener( this );
		lcDet.addDeleteListener( this );
		lcDet.addCarregaListener( this );
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

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * PEDIDO *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

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

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * CLIENTE *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "RP" );
		lcCliente.setQueryCommit( false );
		lcCliente.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCliente );

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * VENDEDOR *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.vend.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "RP" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * PLANO DE PAGAMENTO *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "RP" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * MOEDA *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK, false ) );
		lcMoeda.add( new GuardaCampo( txtDescMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "RP" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setTabelaExterna( lcMoeda );

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * FORNECEDOR *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFornecedor );

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * TRANSPORTADORA *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

		lcTransportadora.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.transp.", ListaCampos.DB_PK, false ) );
		lcTransportadora.add( new GuardaCampo( txtRazTran, "RazTran", "Razão social da transportadora", ListaCampos.DB_SI, false ) );
		lcTransportadora.montaSql( false, "TRANSP", "RP" );
		lcTransportadora.setQueryCommit( false );
		lcTransportadora.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcTransportadora );

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * PRODUTO *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProduto.montaSql( false, "PRODUTO", "RP" );
		lcProduto.setQueryCommit( false );
		lcProduto.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProduto );

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * FORNECEDOR 2 *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

		lcFornecedorItem.add( new GuardaCampo( txtCodForItem, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFornecedorItem.add( new GuardaCampo( txtRazForItem, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedorItem.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedorItem.setQueryCommit( false );
		lcFornecedorItem.setReadOnly( true );
		txtCodForItem.setTabelaExterna( lcFornecedorItem );

	}

	private void montaMaster() {

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * PEDIDOS *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

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

		/***************************************************************************************************************************************************************************************************************************************************************************************************
		 * ITENS *
		 **************************************************************************************************************************************************************************************************************************************************************************************************/

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

		txtVlrLiqItem.setAtivo( false );
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
		adicCampo( txtCodProd, 80, 15, 80, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, true );
		adicDescFK( txtDescProd, 163, 15, 262, 20, "DescProd", "Descrição do produto" );
		adicCampo( txtQtdItem, 428, 15, 80, 20, "QtdItPed", "Qtd.", ListaCampos.DB_SI, true );
		adicCampo( txtPrecoItem, 511, 15, 80, 20, "PrecoItPed", "Preço", ListaCampos.DB_SI, true );
		adicCampoInvisivel( txtVlrItem, "VlrItPed", "Valor item", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrLiqItem, "VlrLiqItPed", "Liquido", ListaCampos.DB_SI, false );
		adicCampo( txtPercIPIItem, 594, 15, 72, 20, "PercIPIItPed", "% IPI", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrIPIItem, "VlrIPIItPed", "Valor IPI", ListaCampos.DB_SI, false );

		adicCampo( txtPercDescItem, 7, 55, 90, 20, "PercDescItPed", "% Desconto", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrDescItem, "VlrDescItPed", "Vlr. Desconto", ListaCampos.DB_SI, false );
		adicCampo( txtPercAdicItem, 100, 55, 90, 20, "PercAdicItPed", "% Acrecimo", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrAdicItem, "VlrAdicItPed", "Vlr. Adicional", ListaCampos.DB_SI, false );
		adicCampo( txtPercRedItem, 193, 55, 90, 20, "PercRecItPed", "% Recebimento", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrRecItem, "VlrRecItPed", "Vlr. Receber", ListaCampos.DB_SI, false );
		adicCampo( txtPercPagItem, 286, 55, 90, 20, "PercPagItPed", "% Pagamento", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrPagItem, "VlrPagItPed", "Vlr. Pagar", ListaCampos.DB_SI, false );
		adicCampo( txtCodForItem, 379, 55, 80, 20, "CodFor", "Cód.for.", ListaCampos.DB_FK, txtRazForItem, true );
		adicDescFK( txtRazForItem, 462, 55, 205, 20, "RazFor", "Razão social do fornecedor" );
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

	@ Override
	public void afterPost( PostEvent e ) {

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

	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcDet ) {
			lcPedido.carregaDados();
		}
		else if ( e.getListaCampos() == lcCampos ) {
			String s = txtCodPed.getVlrString();
			lcPedido.carregaDados();
			txtCodPed.setVlrString( s );
		}
	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterDelete( DeleteEvent E ) {

		if ( E.getListaCampos() == lcDet ) {
			lcPedido.carregaDados();
		}
	}

	public void beforeDelete( DeleteEvent devt ) {

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
	}

}
