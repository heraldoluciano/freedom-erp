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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class RPPedido extends FDetalhe {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelPedido = new JPanelPad();
	
	private final JPanelPad panelItens = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelTotaisItens = new JPanelPad();
	
	private final JPanelPad panelCamposItens = new JPanelPad();

	private final JTextFieldPad txtCodPed = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDataPed = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
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
	
	private final JTextFieldPad txtCodTransp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtRazTransp = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtCodPedFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtCodPedCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtCodItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtQtdItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
	
	private final JTextFieldPad txtPrecoItem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
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
	
	private final JTextFieldPad txtVlrTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final JTextFieldPad txtQdtTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
	
	private final JTextFieldPad txtIPITotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final JTextFieldPad txtDescTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final JTextFieldPad txtAdicTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final JTextFieldPad txtRecTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final JTextFieldPad txtPagTotPed = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private JRadioGroup rgFrete;
	
	private JRadioGroup rgRemessa;
	
	
	public RPPedido() {

		super( false );
		setTitulo( "Cadastro de Pedidos" );
		setAtribos( 50, 50, 700, 500 );
		
		montaRadioGrups();
		
		montaMaster();	

		//setListaCampos( true, "PLANOPAG", "RP" );
		//lcCampos.setQueryInsert( true );

		setListaCampos( lcDet );
		setNavegador( navRod );

		montaDetale();

		//setListaCampos( true, "PARCPLANOPAG", "RP" );
		//lcDet.setQueryInsert( false );

		navRod.setAtivo( 4, false );
		navRod.setAtivo( 5, false );
		
		montaTab();
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
	
	private void montaMaster() {
		
		/*********************
		 *      PEDIDOS      * 
		 *********************/

		setAltCab( 175 );
		setPainel( panelPedido, pnCliCab );
		
		adicCampo( txtCodPed, 7, 20, 70, 20, "CodPlanoPag", "Pedido", ListaCampos.DB_PK, true );
		adicCampo( txtDataPed, 80, 20, 70, 20, "DescPlanoPag", "Data", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodCli, 153, 20, 70, 20, "CodCli", "Cód.cli.", ListaCampos.DB_SI, txtRazCli, true );
		adicDescFK( txtRazCli, 226, 20, 187, 20, "RazCli", "Razão social do cliente" );
		adicCampo( txtCodPedFor, 416, 20, 80, 20, "CodPedFor", "Pedido X For.", ListaCampos.DB_SI, false );
		adicCampo( txtCodPedCli, 499, 20, 80, 20, "CodPedCli", "Pedido X Cli.", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodVend, 7, 60, 70, 20, "CodVend", "Cód.vend.", ListaCampos.DB_SI, txtRazCli, true );
		adicDescFK( txtNomeVend, 80, 60, 155, 20, "NomeVend", "Nome do vendedor" );		
		adicCampo( txtCodPlanoPag, 238, 60, 70, 20, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_SI, txtRazCli, true );
		adicDescFK( txtDescPlanoPag, 311, 60, 155, 20, "DescPlanoPag", "Plano de pagamento" );		
		adicCampo( txtCodMoeda, 469, 60, 110, 20, "CodMoeda", "Cód.moeda", ListaCampos.DB_SI, txtRazCli, true );
		
		adicCampo( txtCodFor, 7, 100, 70, 20, "CodFor", "Cód.for.", ListaCampos.DB_SI, txtRazCli, true );
		adicDescFK( txtRazFor, 80, 100, 155, 20, "RazFor", "Razão social do fornecedor" );
		adicCampo( txtCodTransp, 238, 100, 70, 20, "CodTran", "Cód.transp.", ListaCampos.DB_SI, txtRazCli, true );
		adicDescFK( txtRazTransp, 311, 100, 155, 20, "RazTran", "Razão social da transportadora" );

		adicDB( rgRemessa, 584, 20, 90, 110, "remessa", "Remessa", false );
		adicDB( rgFrete, 469, 100, 110, 30, "frete", "Frete", false );
		
	}
	
	private void montaDetale() {
		
		/*********************
		 *       ITENS       * 
		 *********************/	

		pnDet.add( panelItens, BorderLayout.CENTER );
		
		panelItens.add( panelTotaisItens, BorderLayout.NORTH );
		
		panelTotaisItens.setPreferredSize( new Dimension( 300, 70 ) );
		panelTotaisItens.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Totais" ) );
		panelTotaisItens.adic( new JLabel( "Pedido" ), 7, 0, 91, 20 );		
		panelTotaisItens.adic( txtVlrTotPed, 7, 20, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Itens" ), 101, 0, 91, 20 );
		panelTotaisItens.adic( txtQdtTotPed, 101, 20, 91, 20 );
		panelTotaisItens.adic( new JLabel( "IPI" ), 195, 0, 91, 20 );
		panelTotaisItens.adic( txtIPITotPed, 195, 20, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Desconto" ), 289, 0, 91, 20 );
		panelTotaisItens.adic( txtDescTotPed, 289, 20, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Adicional" ), 383, 0, 91, 20 );
		panelTotaisItens.adic( txtAdicTotPed, 383, 20, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Receber" ), 477, 0, 91, 20 );
		panelTotaisItens.adic( txtRecTotPed, 477, 20, 91, 20 );
		panelTotaisItens.adic( new JLabel( "Pagar" ), 571, 0, 91, 20 );
		panelTotaisItens.adic( txtPagTotPed, 571, 20, 97, 20 );
			
		txtVlrTotPed.setAtivo( false );
		txtQdtTotPed.setAtivo( false );
		txtIPITotPed.setAtivo( false );
		txtDescTotPed.setAtivo( false );
		txtAdicTotPed.setAtivo( false );
		txtRecTotPed.setAtivo( false );
		txtPagTotPed.setAtivo( false );
		
		setAltDet( 165 );
		setPainel( panelCamposItens, panelItens );
		
		adicCampo( txtCodItem, 7, 20, 70, 20, "CodItVenda", "Item", ListaCampos.DB_SI, true );		
		adicCampo( txtCodProd, 80, 20, 80, 20, "CodProd", "Cód.prod.", ListaCampos.DB_SI, txtDescProd, true );
		adicDescFK( txtDescProd, 163, 20, 262, 20, "DescProd", "Descrição do produto" );
		adicCampo( txtQtdItem, 428, 20, 80, 20, "QtdItVenda", "Quantidade", ListaCampos.DB_SI, false );
		adicCampo( txtPrecoItem, 511, 20, 80, 20, "PrecoItVenda", "Preço", ListaCampos.DB_SI, false );
		adicCampo( txtPercIPIItem, 594, 20, 80, 20, "PercIPIItVenda", "% IPI", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrIPIItem, "VlrIPIItVenda", "Valor IPI", ListaCampos.DB_SI, false );
		
		adicCampo( txtPercDescItem, 7, 60, 80, 20, "PercDescItVenda", "% Desconto", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrDescItem, "VlrDescItVenda", "Vlr. Desconto", ListaCampos.DB_SI, false );
		adicCampo( txtPercAdicItem, 90, 60, 80, 20, "PercAdicVenda", "% Acrecimo", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrAdicItem, "VlrAdicItVenda", "Vlr. Adicional", ListaCampos.DB_SI, false );
		adicCampo( txtPercRedItem, 173, 60, 80, 20, "PercRecItVenda", "% Recebimento", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrRecItem, "VlrRecItVenda", "Vlr. Receber", ListaCampos.DB_SI, false );
		adicCampo( txtPercPagItem, 256, 60, 80, 20, "PercPagItVenda", "% Pagamento", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrPagItem, "VlrPagItVenda", "Vlr. Pagar", ListaCampos.DB_SI, false );
		adicCampo( txtCodForItem, 339, 60, 80, 20, "CodFor", "Cód.for.", ListaCampos.DB_SI, txtDescProd, true );
		adicDescFK( txtRazForItem, 422, 60, 252, 20, "RazFor", "Razão social do fornecedor" );
		
	}

}
