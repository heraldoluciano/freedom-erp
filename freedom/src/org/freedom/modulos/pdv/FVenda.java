/**
 * @version 10/06/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FVenda.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.StatusBar;
import org.freedom.componentes.Tabela;
import org.freedom.comutacao.Tef;
import org.freedom.drivers.ECFDriver;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.modulos.std.DLCodProd;
import org.freedom.modulos.std.FAdicOrc;
import org.freedom.plugin.AbstractControleVendaPDV;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.DlgCalc;
import org.freedom.telas.FDialogo;
import org.freedom.telas.FPassword;

public class FVenda extends FDialogo implements KeyListener, CarregaListener, PostListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private StatusBar sbVenda = new StatusBar( new BorderLayout() );

	private JPanelPad pnStatusBar = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnClienteGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCliente = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnNorte = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnEntrada = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBarra = new JPanelPad( 798, 45 );

	private JPanelPad pinCab = new JPanelPad( 798, 45 );

	private JPanelPad pinProduto = new JPanelPad( 798, 130 );

	private JPanelPad pinEntrada = new JPanelPad( 190, 180 );

	private JPanelPad pnRodTb = new JPanelPad( new BorderLayout() );

	private JPanelPad pnTots = new JPanelPad( 440, 45 );

	private Tabela tbItem = new Tabela();

	private JScrollPane spTb = new JScrollPane( tbItem );

	private JButtonPad btF3 = new JButtonPad();

	private JButtonPad btCtrlF3 = new JButtonPad();

	private JButtonPad btF4 = new JButtonPad();

	private JButtonPad btF5 = new JButtonPad();

	private JButtonPad btF6 = new JButtonPad();

	private JButtonPad btF7 = new JButtonPad();

	private JButtonPad btF8 = new JButtonPad();

	private JButtonPad btF9 = new JButtonPad();

	private JButtonPad btF10 = new JButtonPad();

	private JButtonPad btF11 = new JButtonPad();

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtEmitVenda = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtSaidaVenda = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNomeVend = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodClComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescClComis = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProd1 = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 13, 0 );

	private JTextFieldPad txtDescProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtQtdade = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtPreco = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtBaseCalc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtAliqIcms = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 7, 2 );

	private JTextFieldPad txtTotalItem = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtValorIcms = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtBaseCalc1 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtValorIcms1 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtTotalCupom = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtNumeroCupom = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSerieCupom = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtQtdadeItem = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtValorTotalItem = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtValorTotalCupom = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtTelaAdicPDV = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtTipoFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtPercDescItOrc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtVlrDescItOrc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCLoteProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeConv = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtSldLiqProd = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, AplicativoPDV.casasDec );

	private JLabelPad lValorTotalItem = new JLabelPad( "Valor total do item" );

	private JLabelPad lValorTotalCupom = new JLabelPad( "Valor total do cupom" );

	private JLabelPad lbAvisoImp = new JLabelPad();

	private ListaCampos lcVenda = new ListaCampos( this );

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private ListaCampos lcSerie = new ListaCampos( this, "SE" );

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private ListaCampos lcVendedor = new ListaCampos( this, "VD" );

	private ListaCampos lcClComis = new ListaCampos( this, "CM" );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcProduto = new ListaCampos( this, "PD" );

	private ListaCampos lcClFiscal = new ListaCampos( this, "FC" );

	private ListaCampos lcLote = new ListaCampos( this, "LE" );

	private ListaCampos lcConv = new ListaCampos( this, "CV" );

	private Vector vCacheItem = new Vector();

	private Vector vAliquotas = null;

	private ECFDriver ecf = new ECFDriver( !AplicativoPDV.usaEcfDriver() );

	private Tef tef = null;

	private Font fntTotalItem = null;

	private Font fntTotalCupom = null;

	private ImageIcon imgCanc = Icone.novo( "clVencido.gif" );

	private Object[] pref = new Object[ 3 ];

	private boolean trocouCli = false;

	private boolean colocouFrete = false;

	private boolean carregaPesoFrete = false;

	private boolean CTRL = false;

	private float pesoBrutFrete = 0;

	private float pesoLiqFrete = 0;

	private float vlrFrete = 0;

	private int iCodItVenda = 0;

	private int CODTIPOMOV = 0;

	private int CODCAIXA = 0;

	private int CODORC = 0;
	
	private int CODCLI = 0;
	
	private int CODVEND = 0;
	
	private int PLANOPAG = 0;
	
	private String nomePluginVenda;
	
	private static AbstractControleVendaPDV pluginVenda; 

	public FVenda() {

		setTitulo( "Venda" );
		setAtribos( 798, 580 );
		setToFrameLayout();
		setResizable( false );
		setLocation( 0, 0 );
		Dimension size = Aplicativo.telaPrincipal.getSize();
		size.height -= 9;
		size.width -= 9;
		setSize( size );

		txtPreco.setAtivo( false );
		txtCodVenda.setAtivo( false );
		txtSerieCupom.setAtivo( false );
		txtNumeroCupom.setAtivo( false );

		fntTotalItem = new Font( lValorTotalItem.getFont().getFontName(), lValorTotalItem.getFont().getStyle(), 26 );
		fntTotalCupom = new Font( lValorTotalCupom.getFont().getFontName(), lValorTotalCupom.getFont().getStyle(), 26 );

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão Social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		lcCliente.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comis.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.add( new GuardaCampo( txtCodClComis, "CodClComis", "Cód.cl.comis.", ListaCampos.DB_FK, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CodVend" );

		lcClComis.add( new GuardaCampo( txtCodClComis, "CodClComis", "Cód.cl.comis.", ListaCampos.DB_PK, false ) );
		lcClComis.add( new GuardaCampo( txtDescClComis, "DescClComis", "Descrição da classificação da comissã", ListaCampos.DB_SI, false ) );
		lcClComis.montaSql( false, "CLCOMIS", "VD" );
		lcClComis.setReadOnly( true );
		txtCodClComis.setTabelaExterna( lcClComis );
		txtCodClComis.setFK( true );
		txtCodClComis.setNomeCampo( "CodClComis" );

		lcClFiscal.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cód.fisc", ListaCampos.DB_PK, false ) );
		lcClFiscal.add( new GuardaCampo( txtTipoFisc, "TipoFisc", "Cód.tp.fisc.", ListaCampos.DB_SI, false ) );
		lcClFiscal.montaSql( false, "CLFISCAL", "LF" );
		lcClFiscal.setReadOnly( true );
		txtCodFisc.setTabelaExterna( lcClFiscal );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );

		lcSerie.add( new GuardaCampo( txtSerieCupom, "Serie", "Serie", ListaCampos.DB_PK, false ) );
		lcSerie.add( new GuardaCampo( txtNumeroCupom, "DocSerie", "Doc", ListaCampos.DB_SI, true ) );
		lcSerie.montaSql( false, "SERIE", "LF" );
		lcSerie.setReadOnly( true );
		txtSerieCupom.setTabelaExterna( lcSerie );
		txtSerieCupom.setFK( true );
		txtSerieCupom.setNomeCampo( "Serie" );

		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, true ) );
		lcTipoMov.add( new GuardaCampo( txtSerieCupom, "Serie", "Serie", ListaCampos.DB_FK, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		txtCodTipoMov.setFK( true );
		txtCodTipoMov.setNomeCampo( "CodTipoMov" );
		
		lcConv.add( new GuardaCampo( txtCodConv, "CodConv", "Cód.conv.", ListaCampos.DB_PK, true ) );
		lcConv.add( new GuardaCampo( txtNomeConv, "NomeConv", "Nome do conveniado", ListaCampos.DB_SI, false ) );
		lcConv.montaSql( false, "CONVENIADO", "AT" );
		lcConv.setReadOnly( true );
		txtCodConv.setTabelaExterna( lcConv );
		txtCodConv.setFK( true );
		txtCodConv.setNomeCampo( "CodConv" );

		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "Nº pedido", ListaCampos.DB_PK, true ) );
		lcVenda.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tipo venda.", ListaCampos.DB_PK, true ) );
		lcVenda.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_FK, true ) );
		lcVenda.add( new GuardaCampo( txtSerieCupom, "Serie", "Serie", ListaCampos.DB_FK, false ) );
		lcVenda.add( new GuardaCampo( txtNumeroCupom, "DocVenda", "Doc", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtDtSaidaVenda, "DtSaidaVenda", "Saída", ListaCampos.DB_SI, true ) );
		lcVenda.add( new GuardaCampo( txtDtEmitVenda, "DtEmitVenda", "Emissão", ListaCampos.DB_SI, true ) );
		lcVenda.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_FK, true ) );
		lcVenda.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK, true ) );
		lcVenda.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_FK, false ) );
		lcVenda.add( new GuardaCampo( txtCodClComis, "CodClComis", "Cód.cl.comiss.", ListaCampos.DB_FK, false ) );
		lcVenda.montaSql( false, "VENDA", "VD" );
		txtCodVenda.setListaCampos( lcVenda );
		txtCodVenda.setPK( true );
		txtCodVenda.setNomeCampo( "CodVenda" );

		lcLote.add( new GuardaCampo( txtCodLote, "CodLote", "Lote", ListaCampos.DB_PK, txtDescLote, false ) );
		lcLote.add( new GuardaCampo( txtDescLote, "VenctoLote", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcLote.add( new GuardaCampo( txtSldLiqProd, "SldLiqLote", "Saldo", ListaCampos.DB_SI, false ) );
		lcLote.setDinWhereAdic( "CODPROD=#N ", txtCodProd );
		lcLote.montaSql( false, "LOTE", "EQ" );
		lcLote.setReadOnly( true );
		txtCodLote.setTabelaExterna( lcLote );
		txtCodLote.setFK( true );
		txtCodLote.setNomeCampo( "CodLote" );

		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtCodProd1, "CodProd", "Cód.prod.", ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtCodFisc, "CodFisc", "Cód.fisc.", ListaCampos.DB_FK, false ) );
		lcProduto.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtTelaAdicPDV, "UsaTelaAdicPDV", "PDV", ListaCampos.DB_SI, false ) );
		lcProduto.setWhereAdic( "CVPROD IN ('V','A')" );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
		lcProduto.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProduto );
		txtCodProd.setFK( true );
		txtCodProd.setNomeCampo( "CodProd" );

		lbAvisoImp.setFont( new Font( lValorTotalItem.getFont().getFontName(), lValorTotalItem.getFont().getStyle(), 12 ) );
		lbAvisoImp.setForeground( Color.RED );
		lbAvisoImp.setHorizontalAlignment( SwingConstants.CENTER );

		txtCodProd1.setSoLeitura( true );
		txtDescProd.setSoLeitura( true );
		txtPreco.setTipo( JTextFieldPad.TP_DECIMAL, 12, 2 );
		txtBaseCalc.setSoLeitura( true );
		txtAliqIcms.setSoLeitura( true );
		txtTotalItem.setSoLeitura( true );
		txtValorIcms.setSoLeitura( true );

		txtBaseCalc1.setSoLeitura( true );
		txtValorIcms1.setSoLeitura( true );
		txtTotalCupom.setSoLeitura( true );
		txtQtdadeItem.setSoLeitura( true );
		txtValorTotalItem.setSoLeitura( true );
		txtValorTotalCupom.setSoLeitura( true );

		tbItem.adicColuna( "Item" );
		tbItem.adicColuna( "Cód.prod." );
		tbItem.adicColuna( "Descrição do produto" );
		tbItem.adicColuna( "Qtd." );
		tbItem.adicColuna( "Preço" );
		tbItem.adicColuna( "% Icms" );
		tbItem.adicColuna( "Base cálc." );
		tbItem.adicColuna( "Valor Icms" );
		tbItem.adicColuna( "C" );
		tbItem.adicColuna( "Conv" );

		tbItem.setTamColuna( 40, 0 );
		tbItem.setTamColuna( 100, 1 );
		tbItem.setTamColuna( 250, 2 );
		tbItem.setTamColuna( 70, 3 );
		tbItem.setTamColuna( 90, 4 );
		tbItem.setTamColuna( 90, 5 );
		tbItem.setTamColuna( 90, 6 );
		tbItem.setTamColuna( 90, 7 );
		tbItem.setTamColuna( 20, 8 );
		tbItem.setTamColuna( 50, 9 );

		c.add( pnClienteGeral, BorderLayout.CENTER );

		btF3.setToolTipText( "Cancela último item." );
		btCtrlF3.setToolTipText( "Cancela item por posição." );
		btF4.setToolTipText( "Fecha venda." );
		btF5.setToolTipText( "Leitura X." );
		btF6.setToolTipText( "Abre gaveta." );
		btF7.setToolTipText( "Calculadora." );
		btF8.setToolTipText( "Repete item." );
		btF9.setToolTipText( "Seleciona cliente." );
		btF10.setToolTipText( "Fecha caixa." );
		btF11.setToolTipText( "Busca orçamento." );
		btF3.setIcon( Icone.novo( "btPdvCancelaItem.gif" ) );
		btCtrlF3.setIcon( Icone.novo( "btPdvCtrlCancelaItem.gif" ) );
		btF4.setIcon( Icone.novo( "btPdvFechaVenda.gif" ) );
		btF5.setIcon( Icone.novo( "btPdvLeituraX.gif" ) );
		btF6.setIcon( Icone.novo( "btPdvGaveta.gif" ) );
		btF7.setIcon( Icone.novo( "btPdvCalc.gif" ) );
		btF8.setIcon( Icone.novo( "btPdvCopiaItem.gif" ) );
		btF9.setIcon( Icone.novo( "btPdvSelCliente.gif" ) );
		btF10.setIcon( Icone.novo( "btPdvFechaCaixa.gif" ) );
		btF11.setIcon( Icone.novo( "btOrcVendaPdv.gif" ) );

		pinBarra.adic( btF3, 5, 3, 60, 35 );
		pinBarra.adic( btCtrlF3, 70, 3, 60, 35 );
		pinBarra.adic( btF4, 135, 3, 60, 35 );
		pinBarra.adic( btF5, 200, 3, 60, 35 );
		pinBarra.adic( btF6, 265, 3, 60, 35 );
		pinBarra.adic( btF7, 330, 3, 60, 35 );
		pinBarra.adic( btF8, 395, 3, 60, 35 );
		pinBarra.adic( btF9, 460, 3, 60, 35 );
		pinBarra.adic( btF10, 525, 3, 60, 35 );
		pinBarra.adic( btF11, 590, 3, 60, 35 );

		pinCab.adic( new JLabelPad( "Cód.cli." ), 5, 3, 70, 15 );
		pinCab.adic( new JLabelPad( "Razão social do cliente" ), 75, 3, 200, 15 );
		pinCab.adic( txtCodCli, 5, 20, 68, 20 );
		pinCab.adic( txtRazCli, 75, 20, 200, 20 );
		pinCab.adic( new JLabelPad( "Cód.p.pag." ), 283, 3, 70, 15 );
		pinCab.adic( new JLabelPad( "Descrição do plano de pagamento" ), 353, 3, 200, 15 );
		pinCab.adic( txtCodPlanoPag, 283, 20, 68, 20 );
		pinCab.adic( txtDescPlanoPag, 353, 20, 200, 20 );
		pinCab.adic( new JLabelPad( "Nº seq.venda" ), 561, 3, 105, 15 );
		pinCab.adic( txtCodVenda, 561, 20, 105, 20 );

		txtDescProd.setFont( fntTotalItem );
		txtCodProd1.setFont( fntTotalItem );
		txtQtdadeItem.setFont( fntTotalItem );
		txtValorTotalItem.setFont( fntTotalItem );
		txtValorTotalCupom.setFont( fntTotalCupom );

		txtDescProd.setForeground( Color.BLUE );
		txtCodProd1.setForeground( Color.BLUE );
		txtQtdadeItem.setForeground( Color.BLUE );
		txtValorTotalItem.setForeground( Color.BLUE );
		txtValorTotalCupom.setForeground( Color.RED );

		pinProduto.adic( new JLabelPad( "Descrição do produto" ), 5, 3, 450, 15 );
		pinProduto.adic( new JLabelPad( "Cód.prod." ), 460, 3, 206, 15 );
		pinProduto.adic( txtDescProd, 5, 20, 450, 40 );
		pinProduto.adic( txtCodProd1, 460, 20, 206, 40 );
		pinProduto.adic( new JLabelPad( "Quantidade do item" ), 5, 65, 215, 15 );
		pinProduto.adic( lValorTotalItem, 225, 65, 215, 15 );
		pinProduto.adic( lValorTotalCupom, 445, 65, 220, 15 );
		pinProduto.adic( txtQtdadeItem, 5, 82, 215, 40 );
		pinProduto.adic( txtValorTotalItem, 225, 82, 215, 40 );
		pinProduto.adic( txtValorTotalCupom, 445, 82, 220, 40 );

		pinEntrada.adic( new JLabelPad( "Cód.prod." ), 5, 0, 70, 20 );
		pinEntrada.adic( txtCodProd, 5, 20, 175, 20 );
		pinEntrada.adic( new JLabelPad( "Quantidade" ), 5, 40, 85, 20 );
		pinEntrada.adic( txtQtdade, 5, 60, 85, 20 );
		pinEntrada.adic( new JLabelPad( "Preço" ), 95, 40, 85, 20 );
		pinEntrada.adic( txtPreco, 95, 60, 85, 20 );
		pinEntrada.adic( new JLabelPad( "% Desc." ), 5, 80, 50, 20 );
		pinEntrada.adic( txtPercDescItOrc, 5, 100, 50, 20 );
		pinEntrada.adic( new JLabelPad( "Valor desc." ), 60, 80, 120, 20 );
		pinEntrada.adic( txtVlrDescItOrc, 60, 100, 120, 20 );
		pinEntrada.adic( new JLabelPad( "Total item" ), 5, 120, 85, 20 );
		pinEntrada.adic( txtTotalItem, 5, 140, 85, 20 );
		pinEntrada.adic( new JLabelPad( "Base cálc." ), 95, 120, 85, 20 );
		pinEntrada.adic( txtBaseCalc, 95, 140, 85, 20 );
		pinEntrada.adic( new JLabelPad( "Còd.lote" ), 5, 160, 70, 20 );
		pinEntrada.adic( txtCodLote, 5, 180, 175, 20 );
		pinEntrada.adic( new JLabelPad( "% Icms" ), 5, 200, 50, 20 );
		pinEntrada.adic( txtAliqIcms, 5, 220, 50, 20 );
		pinEntrada.adic( new JLabelPad( "Valor Icms" ), 60, 200, 120, 20 );
		pinEntrada.adic( txtValorIcms, 60, 220, 120, 20 );

		pnTots.adic( new JLabelPad( "Base cálculo" ), 5, 3, 90, 15 );
		pnTots.adic( new JLabelPad( "Total Icms" ), 100, 3, 90, 15 );
		pnTots.adic( new JLabelPad( "Total cupom" ), 195, 3, 90, 15 );
		pnTots.adic( new JLabelPad( "Número cupom" ), 290, 3, 90, 15 );
		pnTots.adic( new JLabelPad( "Série" ), 385, 3, 50, 15 );

		pnTots.adic( txtBaseCalc1, 5, 20, 90, 20 );
		pnTots.adic( txtValorIcms1, 100, 20, 90, 20 );
		pnTots.adic( txtTotalCupom, 195, 20, 90, 20 );
		pnTots.adic( txtNumeroCupom, 290, 20, 90, 20 );
		pnTots.adic( txtSerieCupom, 385, 20, 50, 20 );

		pnTots.tiraBorda();
		pnRodTb.add( pnTots, BorderLayout.WEST );
		pnRodTb.add( lbAvisoImp, BorderLayout.CENTER );
		pnRodTb.setBorder( BorderFactory.createEtchedBorder() );

		pnNorte.add( pinBarra, BorderLayout.NORTH );
		pnNorte.add( pinCab, BorderLayout.SOUTH );
		pnTabela.add( pnRodTb, BorderLayout.SOUTH );
		pnTabela.add( spTb, BorderLayout.CENTER );
		pnEntrada.add( pinEntrada, BorderLayout.CENTER );
		pnCliente.add( pinProduto, BorderLayout.NORTH );
		pnCliente.add( pnEntrada, BorderLayout.EAST );
		pnCliente.add( pnTabela, BorderLayout.CENTER );
		pnClienteGeral.add( pnNorte, BorderLayout.NORTH );
		pnClienteGeral.add( pnCliente, BorderLayout.CENTER );

		addKeyListener( this );
		txtCodProd.addKeyListener( this );
		txtQtdade.addKeyListener( this );
		txtPercDescItOrc.addKeyListener( this );
		txtVlrDescItOrc.addKeyListener( this );

		btF3.addActionListener( this );
		btCtrlF3.addActionListener( this );
		btF4.addActionListener( this );
		btF5.addActionListener( this );
		btF6.addActionListener( this );
		btF7.addActionListener( this );
		btF8.addActionListener( this );
		btF9.addActionListener( this );
		btF10.addActionListener( this );
		btF11.addActionListener( this );

		txtPercDescItOrc.addFocusListener( this );
		txtVlrDescItOrc.addFocusListener( this );

		lcVenda.addPostListener( this );

		lcProduto.addCarregaListener( this );

		if ( AplicativoPDV.bTEFTerm ) {
			tef = new Tef( Aplicativo.strTefEnv, Aplicativo.strTefRet );
		}
		
		setPrimeiroFoco( txtCodProd );

	}

	private void abreAdicOrc() {

		if ( !Aplicativo.telaPrincipal.temTela( "Busca orçamento" ) ) {
			FAdicOrc tela = new FAdicOrc( this, "E" );
			Aplicativo.telaPrincipal.criatela( "Orcamento", tela, con );
		}
	}

	private void abreGaveta() {

		if ( mostraTelaPass() ) {
			if ( ( FreedomPDV.bECFTerm ) && ( ecf != null ) ) {
				ecf.abreGaveta();
			}
		}
	}

	public synchronized void adicItemOrc( int iCodItOrc ) {

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		String sSQL = null;
		Robot robo = null;
		boolean bUPOrc = false;

		try {

			robo = new Robot();

			sSQL = "SELECT CODPROD, QTDITORC, VLRPRODITORC, PERCDESCITORC, VLRDESCITORC, CODLOTE FROM VDITORCAMENTO WHERE CODEMP=? AND CODFILIAL=? AND CODORC=? AND CODITORC=?";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, AplicativoPDV.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ORCAMENTO" ) );
			ps.setInt( 3, CODORC );
			ps.setInt( 4, iCodItOrc );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				txtCodProd.setVlrString( rs.getString( "CODPROD" ) );
				txtCodProd.requestFocus();

				robo.keyPress( KeyEvent.VK_ENTER );

				txtPreco.setVlrBigDecimal( rs.getBigDecimal( "VLRPRODITORC" ) );
				txtPercDescItOrc.setVlrBigDecimal( rs.getBigDecimal( "PERCDESCITORC" ) != null ? rs.getBigDecimal( "PERCDESCITORC" ) : new BigDecimal( "0" ) );
				txtVlrDescItOrc.setVlrBigDecimal( rs.getBigDecimal( "VLRDESCITORC" ) != null ? rs.getBigDecimal( "VLRDESCITORC" ) : new BigDecimal( "0" ) );
				txtQtdade.setVlrBigDecimal( rs.getBigDecimal( "QTDITORC" ) );
				txtCodLote.setVlrString( rs.getString( "CODLOTE" ) );
				txtQtdade.requestFocus();

				robo.keyPress( KeyEvent.VK_ENTER );

				lcProduto.carregaDados();
				lcLote.carregaDados();
				if ( lcVenda.getStatus() == ListaCampos.LCS_INSERT ) {
					if ( lcVenda.post() ) {
						bUPOrc = insereItem( iCodItOrc );
						iniItem();
					}
				}
				else if ( lcVenda.getStatus() == ListaCampos.LCS_SELECT ) {
					bUPOrc = insereItem( iCodItOrc );
					iniItem();
					lcVenda.carregaDados();
				}
			}

			if ( bUPOrc ) {
				sSQL = "EXECUTE PROCEDURE VDUPVENDAORCSP(?,?,?,?,?,?,?,?)";
				ps2 = con.prepareStatement( sSQL );
				ps2.setInt( 1, AplicativoPDV.iCodEmp );
				ps2.setInt( 2, ListaCampos.getMasterFilial( "ORCAMENTO" ) );
				ps2.setInt( 3, CODORC );
				ps2.setInt( 4, iCodItOrc );
				ps2.setInt( 5, ListaCampos.getMasterFilial( "VDVENDA" ) );
				ps2.setInt( 6, txtCodVenda.getVlrInteger().intValue() );
				ps2.setInt( 7, iCodItVenda );
				ps2.setString( 8, txtTipoVenda.getVlrString() );
				ps2.execute();
				ps2.close();
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao gerar venda do orçamento.\n" + e.getMessage() );
			e.printStackTrace();
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao gerar venda do orçamento.\n" + e.getMessage() );
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			robo = null;
		}

	}

	private void addPesoFrete( int iCodProd, BigDecimal iQtd ) {

		String sSQL = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			
			sSQL = "SELECT PESOBRUTPROD, PESOLIQPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, iCodProd );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				pesoBrutFrete += ( rs.getFloat( 1 ) * iQtd.floatValue() );
				pesoLiqFrete += ( rs.getFloat( 2 ) * iQtd.floatValue() );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao somar peso do produto!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}
	}

	private void atualizaTot() {
		
		try {
		
			String sSQL = "SELECT VLRLIQVENDA,VLRBASEICMSVENDA,VLRICMSVENDA FROM VDVENDA WHERE CODVENDA=? AND CODEMP=? AND CODFILIAL=? AND TIPOVENDA='E'";
			
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, txtCodVenda.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, lcVenda.getCodFilial() );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtValorTotalCupom.setVlrBigDecimal( rs.getBigDecimal( "VlrLiqVenda" ) );
				txtBaseCalc1.setVlrBigDecimal( rs.getBigDecimal( "VlrBaseICMSVenda" ) );
				txtValorIcms1.setVlrBigDecimal( rs.getBigDecimal( "VlrICMSVenda" ) );
				txtTotalCupom.setVlrBigDecimal( rs.getBigDecimal( "VlrLiqVenda" ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao atualizar o saldo!\n" + "Talvez esta venda ainda não esteja salva!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	private void cancItem() {

		if ( tbItem.getNumLinhas() < 1 ) {
			Funcoes.mensagemErro( this, "Não existe nenhum item para ser cancelado!" );
			return;
		}
		
		int iItem = ( (Integer) tbItem.getValor( tbItem.getNumLinhas() - 1, 0 ) ).intValue();
		
		if ( Funcoes.mensagemConfirma( null, "Deseja realmente cancelar o item anterior?" ) == JOptionPane.YES_OPTION ) {
			if ( cancItem( iItem ) ) {
				if ( AplicativoPDV.bECFTerm ) {
					if ( ecf.cancelaItemAnterior() ) {
						btOK.doClick();
					}
				}
				atualizaTot();
			}
			else {
				Funcoes.mensagemErro( null, "Não foi possível cancelar o item." );
			}
		}
	}

	private boolean cancItem( int iItem ) {

		boolean bRet = false;
		String sSQL = null;
		PreparedStatement ps = null;
		int iLinha = -1;
		
		try {
			
			sSQL = "UPDATE VDITVENDA SET CANCITVENDA='S' WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='E' AND CODITVENDA=?";
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, txtCodVenda.getVlrInteger().intValue() );
			ps.setInt( 4, iItem );
			ps.executeUpdate();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			iLinha = getLinha( iItem );
			
			if ( iLinha > -1 ) {
				tbItem.setValor( new BigDecimal( "0.00" ), iLinha, 3 );
				tbItem.setValor( new BigDecimal( "0.00" ), iLinha, 6 );
				tbItem.setValor( new BigDecimal( "0.00" ), iLinha, 7 );
				tbItem.setValor( "C", iLinha, 8 );
				marcaLinha( iItem );
				minPesoFrete( txtCodProd1.getVlrInteger().intValue(), txtQtdade.getVlrBigDecimal() );
				atualizaTot();
			}

			bRet = true;
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro cancelar o item " + err.getMessage() );
		} finally {
			ps = null;
			sSQL = null;
			iLinha = 0;
		}
		
		return bRet;
	}

	private void cancCupom() {

		if ( lcVenda.getStatus() != ListaCampos.LCS_SELECT ) {
			Funcoes.mensagemErro( this, "Não existe nenhuma venda ativa!" );
			return;
		}
		
		DLCancCupom canc = new DLCancCupom();
		canc.setVenda( txtCodVenda.getVlrInteger().intValue() );
		canc.setConexao( con );
		canc.setVisible( true );
		
		if ( canc.OK ) {
			if ( canc.isCancCupom() ) {
				iniVenda();
			}
			else {
				int[] iItens = canc.getCancItem();
				int iLinha = 0;
				
				for ( int i=0; i < iItens.length; i++ ) {
					
					iLinha = getLinha( iItens[ i ] );
					
					if ( iLinha > -1 ) {
						tbItem.setValor( new BigDecimal( "0.00" ), iLinha, 3 );
						tbItem.setValor( new BigDecimal( "0.00" ), iLinha, 6 );
						tbItem.setValor( new BigDecimal( "0.00" ), iLinha, 7 );
						tbItem.setValor( imgCanc, iLinha, 8 );
						marcaLinha( iItens[ i ] );
						minPesoFrete( txtCodProd1.getVlrInteger().intValue(), txtQtdade.getVlrBigDecimal() );
						atualizaTot();
					}
					
				}
			}
		}
		
		canc.dispose();
		
	}
	
	private void carregaPlugin() {

		nomePluginVenda = AplicativoPDV.getPluginVenda();
		
        if ( nomePluginVenda.length() > 0 ) {
        	
        	try {
        		
                pluginVenda = (AbstractControleVendaPDV) Class.forName( nomePluginVenda ).newInstance();
                
                pluginVenda.addAttribute( "conexao", con );
                
                pluginVenda.inicializa();
                
            } catch ( Exception e ) {
                e.printStackTrace();
                Funcoes.mensagemErro( null, "Erro ao carregar plugin de venda", true, con, e );
            }
           
        }
		
	}

	private void fechaCaixa() {

		if ( lcVenda.getStatus() == ListaCampos.LCS_SELECT ) {
			Funcoes.mensagemInforma( this, "Ainda existe uma venda ativa!" );
			return;
		}
		
		DLFechaDia fecha = new DLFechaDia();
		fecha.setConexao( con );
		fecha.setVisible( true );
		
		if ( fecha.OK ) {
			fecha.dispose();
			this.dispose();
		}
		
		fecha.dispose();
		
	}

	public synchronized void fechaVenda() {

		if ( lcVenda.getStatus() != ListaCampos.LCS_SELECT ) {
			Funcoes.mensagemErro( this, "Não existe nenhuma venda ativa!" );
			return;
		}
		if ( txtCodCli.getVlrInteger().intValue() != ( CODCLI ) )
			trocouCli = true;

		if ( ( (Boolean) prefs( 0 ) ).booleanValue() ) {
			if ( prefs( 1 ) != null ) {
				if ( Funcoes.mensagemConfirma( null, "Deseja adicionar o frete ao cupom?" ) == JOptionPane.YES_OPTION ) {
					txtCodProd.setVlrString( (String) prefs( 1 ) );
					lcProduto.carregaDados();
					txtQtdade.setVlrBigDecimal( new BigDecimal( 1 ) );
					vlrFrete = txtPreco.getVlrBigDecimal().floatValue();
					if ( insereItem() ) {
						iniItem();
						carregaPesoFrete = true;
						colocouFrete = true;
					}
					else {
						txtCodProd.setVlrString( "" );
						lcProduto.carregaDados();
						txtQtdade.setVlrBigDecimal( new BigDecimal( 0 ) );
						return;
					}
				}
			}
		}

		DLFechaVenda fecha = new DLFechaVenda( paramFecha() );

		if ( tef != null ) {
			fecha.setTef( tef );
		}

		fecha.setVisible( true );

		if ( fecha.OK ) {
			iniVenda();
		}
		else if ( colocouFrete ) {
		
			cancItem( ( (Integer) tbItem.getValor( tbItem.getNumLinhas() - 1, 0 ) ).intValue() );
			
			if ( AplicativoPDV.bECFTerm ) {
				if ( ecf.cancelaItemAnterior() ) {
					btOK.doClick();
				}
			}
			
			colocouFrete = false;
			
		}

		fecha.dispose();
		setFocusProd();
		trocouCli = false;
		
	}

	private synchronized void iniItem() {

		txtBaseCalc.setVlrString( "" );
		txtAliqIcms.setVlrString( "" );
		txtValorIcms.setVlrString( "" );
		txtQtdade.setVlrBigDecimal( new BigDecimal( 1 ) );
		txtPreco.setVlrString( "" );
		txtCodProd.setVlrString( "" );
		txtPercDescItOrc.setVlrString( "" );
		txtVlrDescItOrc.setVlrString( "" );
		txtCodLote.setVlrString( "" );
		txtCodLote.setAtivo( false );
		setFocusProd();
	}

	private boolean insereItem() {

		// passa -1 pra que no metodo getItComis(int) seja buscado pelo produto e não pelo orçamento.
		return insereItem( -1 );
	}

	private boolean insereItem( int iCodItOrc ) {

		if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
			if ( txtCodLote.getVlrString().length() <= 0 || testaCodLote() ) {
				Funcoes.mensagemErro( null, "Código do lote é requerido!" );
				return false;
			}
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sTributo = null;
		String sSQL = null;
		BigDecimal[] argsComis = getItComis( iCodItOrc );
		
		try {
			
			sSQL = "SELECT CODITVENDA,PERCICMSITVENDA,VLRBASEICMSITVENDA,VLRICMSITVENDA,VLRLIQITVENDA FROM VDADICITEMPDVSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, txtCodVenda.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "VDITVENDA" ) );
			ps.setString( 4, txtCodProd.getVlrString().trim() );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			ps.setBigDecimal( 7, txtQtdade.getVlrBigDecimal() );
			ps.setBigDecimal( 8, txtPreco.getVlrBigDecimal() );
			ps.setBigDecimal( 9, txtVlrDescItOrc.getVlrBigDecimal() );
			ps.setBigDecimal( 10, txtPercDescItOrc.getVlrBigDecimal() );
			if ( argsComis[ 1 ] != null ) {
				ps.setBigDecimal( 11, argsComis[ 1 ] );
				ps.setBigDecimal( 12, argsComis[ 0 ] );
			}
			else {
				ps.setNull( 11, Types.NUMERIC );
				ps.setNull( 12, Types.NUMERIC );
			}
			if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
				ps.setString( 13, txtCodLote.getVlrString().trim() );
				ps.setInt( 14, Aplicativo.iCodEmp );
				ps.setInt( 15, ListaCampos.getMasterFilial( "EQLOTE" ) );
			}
			else {
				ps.setNull( 13, Types.INTEGER );
				ps.setNull( 14, Types.SMALLINT );
				ps.setNull( 15, Types.CHAR );
			}
			if ( txtCodConv.getVlrInteger().intValue() > 0 ) {
				ps.setInt( 16, ListaCampos.getMasterFilial( "ATCONVENIADO" ) );
				ps.setInt( 17, txtCodConv.getVlrInteger().intValue() );
			}
			else {
				ps.setNull( 16, Types.SMALLINT );
				ps.setNull( 17, Types.INTEGER );
			}
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				iCodItVenda = rs.getInt( "CodItVenda" );
				txtAliqIcms.setVlrBigDecimal( rs.getBigDecimal( "PercICMSItVenda" ) );
				txtBaseCalc.setVlrBigDecimal( rs.getBigDecimal( "VlrBaseICMSItVenda" ) );
				txtValorIcms.setVlrBigDecimal( rs.getBigDecimal( "VlrICMSItVenda" ) );
				txtValorTotalItem.setVlrBigDecimal( rs.getBigDecimal( "VlrLiqItVenda" ) == null ? new BigDecimal( 0 ) : rs.getBigDecimal( "VlrLiqItVenda" ) );
				txtQtdadeItem.setVlrBigDecimal( txtQtdade.getVlrBigDecimal() );

				tbItem.adicLinha( new Object[] { 
						new Integer( iCodItVenda ), 
						txtCodProd.getVlrString().trim(), 
						txtDescProd.getVlrString(), 
						txtQtdade.getVlrBigDecimal(), 
						txtPreco.getVlrBigDecimal(), 
						txtAliqIcms.getVlrBigDecimal(), 
						txtBaseCalc.getVlrBigDecimal(), 
						txtValorIcms.getVlrBigDecimal(), 
						"",
						txtCodConv.getVlrInteger() } 
				);
			}

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			if ( ( FreedomPDV.bECFTerm && ( ecf != null ) ) ) {
				if ( txtTipoFisc.getVlrString().equals( "TT" ) ) {
					sTributo = getPosAliquota( txtAliqIcms.getVlrBigDecimal().floatValue() );
					if ( sTributo.equals( "00" ) ) {
						Funcoes.mensagemErro( this, "Alíquota " + txtAliqIcms.getVlrBigDecimal().floatValue() + " não foi cadastrada na impressora fiscal!" );
						return false;
					}
				}
				else {
					sTributo = txtTipoFisc.getVlrString();
				}
				
				ecf.vendaItem( Integer.parseInt( txtCodProd.getVlrString().trim() ), txtDescProd.getVlrString(), sTributo, txtQtdade.getVlrBigDecimal(), txtPreco.getVlrBigDecimal(), txtVlrDescItOrc.getVlrBigDecimal() );
				
			}

			addPesoFrete( Integer.parseInt( txtCodProd.getVlrString().trim() ), txtQtdade.getVlrBigDecimal() );
			atualizaTot();
			vCacheItem.clear();
			vCacheItem.add( txtCodProd.getVlrString() );
			vCacheItem.add( txtQtdade.getVlrBigDecimal() );

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception err ) {
			Funcoes.mensagemErro( null, "Erro ao inserir o ítem.\nInforme esta mensagem ao administrador: \n" + err.getMessage() );
			err.printStackTrace();
			return false;
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sTributo = null;
		}

		return true;
	}

	private synchronized void iniVenda() {

		iniVenda( CODCLI, PLANOPAG, CODVEND );
	}

	private synchronized void iniVenda( int codCli, int codPlanoPag, int vend ) {

		CODORC = 0;
		lcVenda.insert( false );
		txtCodVenda.setVlrInteger( getCodSeqCaixa() );
		txtTipoVenda.setVlrString( "E" );
		txtCodCli.setVlrInteger( new Integer( codCli ) );
		lcCliente.carregaDados();
		lcProduto.limpaCampos( true );
		txtCodPlanoPag.setVlrInteger( new Integer( codPlanoPag ) );
		txtQtdadeItem.setVlrString( "" );
		txtValorTotalCupom.setVlrString( "" );
		txtValorTotalItem.setVlrString( "" );
		txtBaseCalc1.setVlrString( "" );
		txtValorIcms1.setVlrString( "" );
		txtTotalCupom.setVlrString( "" );
		lcPlanoPag.carregaDados();
		txtCodTipoMov.setVlrInteger( new Integer( CODTIPOMOV ) );
		lcTipoMov.carregaDados();
		txtCodVend.setVlrInteger( new Integer( vend ) );
		lcVendedor.carregaDados();
		lcClComis.carregaDados();
		txtDtEmitVenda.setVlrDate( new Date() );
		txtDtSaidaVenda.setVlrDate( new Date() );
		
		if ( ( AplicativoPDV.bECFTerm ) && ( ecf != null ) ) {
			txtNumeroCupom.setVlrInteger( new Integer( ecf.numeroCupom() + 1 ) );
		}
		else {
			txtNumeroCupom.setVlrInteger( new Integer( 999999999 ) );
		}
		
		tbItem.limpa();
		mostraInfoImp();
		
		iniItem();
		
	}

	private void leituraX() {

		if ( lcVenda.getStatus() == ListaCampos.LCS_SELECT ) {
			Funcoes.mensagemInforma( this, "Ainda existe uma venda ativa!" );
			return;
		}
		if ( Funcoes.mensagemConfirma( null, "Confirma impressão de leitura X?" ) == JOptionPane.YES_OPTION ) {
			if ( ( FreedomPDV.bECFTerm ) && ( ecf != null ) ) {
				ecf.leituraX();
			}
		}
	}

	private void marcaLinha( int iItem ) {

		int iLinha = -1;
		iLinha = getLinha( iItem );
		if ( iLinha >= 0 ) {
			tbItem.setRowBackGround( iLinha, new Color( 254, 213, 192 ) );
			tbItem.updateUI();
			tbItem.repaint();
		}
	}

	private void minPesoFrete( int iCodProd, BigDecimal iQtd ) {

		String sSQL = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			
			sSQL = "SELECT PESOBRUTPROD, PESOLIQPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, iCodProd );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				pesoBrutFrete -= ( rs.getFloat( 1 ) * iQtd.floatValue() );
				pesoLiqFrete -= ( rs.getFloat( 2 ) * iQtd.floatValue() );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao somar peso do produto!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}
	}

	public synchronized boolean montaVendaOrc( int arg ) {

		boolean retorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		Vector vArgs = new Vector();

		try {

			sSQL = "SELECT CODCLI, CODPLANOPAG, CODVEND FROM VDORCAMENTO WHERE CODEMP=? AND CODFILIAL=? AND CODORC=?";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, AplicativoPDV.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ORCAMENTO" ) );
			ps.setInt( 3, arg );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				vArgs.addElement( new Integer( rs.getInt( 1 ) ) );
				vArgs.addElement( new Integer( rs.getInt( 2 ) ) );
				vArgs.addElement( new Integer( rs.getInt( 3 ) ) );
			}

			if ( vArgs.size() == 3 ) {
				txtCodCli.setVlrInteger( (Integer) vArgs.elementAt( 0 ) );
				lcCliente.carregaDados();
				txtCodPlanoPag.setVlrInteger( (Integer) vArgs.elementAt( 1 ) );
				lcPlanoPag.carregaDados();
				txtCodVend.setVlrInteger( (Integer) vArgs.elementAt( 2 ) );
				lcVendedor.carregaDados();
				lcClComis.carregaDados();

				CODORC = arg;
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao gerar venda do orçamento.", true, con, e );
			e.printStackTrace();
			CODORC = 0;
			retorno = false;
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			vArgs = null;
		}
		
		return retorno;
		
	}

	private void mostraInfoImp() {

		if ( ( AplicativoPDV.bECFTerm ) && ( ecf != null ) ) {
			
			String sStatus = ecf.leStatus();
			
			if ( ! "".equals( sStatus ) ) {
				
				sStatus = sStatus.replaceAll( "\n", "<BR>" );
				sStatus = "<HTML><CENTER>" + sStatus + "</CENTER></HTML>";
				
			}
			
			lbAvisoImp.setText( sStatus );
			
		}
		
		else {
			lbAvisoImp.setText( "" );
		}
	}

	private boolean mostraTelaPass() {

		boolean retorno = false;

		FPassword fpw = new FPassword( this, FPassword.ABRE_GAVETA, "Abrir gaveta", con );
		fpw.execShow();

		retorno = fpw.OK;

		fpw.dispose();

		return retorno;
	}

	private Object[] paramFecha() {

		Object[] param = new Object[ 13 ];

		param[ 0 ] = txtCodVenda.getVlrInteger();
		param[ 1 ] = txtTipoVenda.getVlrString();
		param[ 2 ] = txtTotalCupom.getVlrBigDecimal();
		param[ 3 ] = txtNumeroCupom.getVlrInteger();
		param[ 4 ] = txtCodPlanoPag.getVlrInteger();
		param[ 5 ] = con;
		param[ 6 ] = getInfoCli( txtCodCli.getVlrInteger().intValue() );
		param[ 7 ] = new Boolean( trocouCli );
		if ( carregaPesoFrete ) {
			param[ 8 ] = new BigDecimal( pesoBrutFrete );
			param[ 9 ] = new BigDecimal( pesoLiqFrete );
			param[ 10 ] = new BigDecimal( vlrFrete );
		}
		else {
			param[ 8 ] = new Boolean( false );
			param[ 9 ] = null;
			param[ 10 ] = null;
		}
		param[ 11 ] = new Boolean( CODORC > 0 );
		param[ 12 ] = txtCodVend.getVlrInteger();

		carregaPesoFrete = false;

		return param;
	}

	private void repeteItem() {

		if ( vCacheItem.size() == 2 ) {
			try {
				Robot robo = new Robot();
				setFocusProd();
				txtCodProd.setVlrString( (String) vCacheItem.elementAt( 0 ) );
				robo.keyPress( KeyEvent.VK_ENTER );
				txtQtdade.requestFocus();
				txtQtdade.setVlrBigDecimal( (BigDecimal) vCacheItem.elementAt( 1 ) );
				robo.keyPress( KeyEvent.VK_ENTER );
			} catch ( AWTException err ) {
			}
		}
	}

	private boolean testaCodLote() {

		boolean bValido = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT COUNT(*) FROM EQLOTE " + "WHERE CODLOTE=? AND CODPROD=? AND CODEMP=? AND CODFILIAL=?";
		try {
			ps = con.prepareStatement( sSQL );
			ps.setString( 1, txtCodLote.getVlrString().trim() );
			ps.setInt( 2, txtCodPlanoPag.getVlrInteger().intValue() );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "EQLOTE" ) );
			rs = ps.executeQuery();
			if ( rs.next() )
				if ( rs.getInt( 1 ) > 0 )
					bValido = true;

			rs.close();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela EQLOTE!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		return bValido;

	}

	private void trocaCli() {

		try {
			Robot robo = new Robot();
			txtCodCli.requestFocus();
			robo.keyPress( KeyEvent.VK_F2 );
		} catch ( AWTException err ) {
		}
	}

	public synchronized void setFocusProd() {

		if ( txtCodProd.isFocusable() ) {
			txtCodProd.requestFocus();
		}
	}

	private void setCodCaixa() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		
		try {
			
			sSQL = "SELECT C.CODCAIXA FROM PVCAIXA C, SGESTACAO E WHERE E.CODEMP=? AND E.CODFILIAL=? AND E.CODEST=? AND E.CODEMP=C.CODEMPET AND E.CODFILIAL=C.CODFILIALET AND E.CODEST=C.CODEST ";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, Aplicativo.iNumEst );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				CODCAIXA = rs.getInt( 1 );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar caixa.\n" + err.getMessage() );
			err.printStackTrace();
			dispose();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	// O botão sair execute este método para sair:
	public void setVisible( boolean bVal ) {

		if ( !bVal ) {
			if ( ( FreedomPDV.bECFTerm ) && ( ecf != null ) ) {
				if ( ecf.verificaCupomAberto() ) {
					Funcoes.mensagemInforma( null, "Cupom fiscal está aberto!" );
					return;
				}
			}
		}
		
		super.setVisible( bVal );
	}

	private int getLinha( int iItem ) {

		int iLinha = -1;
		for ( int i = 0; i < tbItem.getNumLinhas(); i++ ) {
			if ( ! ( (String) tbItem.getValor( i, 8 ) ).equals( "C" ) ) {
				if ( iItem == ( (Integer) tbItem.getValor( i, 0 ) ).intValue() ) {
					iLinha = i;
					break;
				}
			}
		}
		return iLinha;
	}

	private void getLote() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		try {
			sSQL = "SELECT MIN(L.CODLOTE) " + "FROM EQLOTE L " + "WHERE L.CODPROD=? AND L.CODFILIAL=? " + ( ( (Boolean) prefs( 2 ) ).booleanValue() ? "AND L.SLDLIQLOTE>0 " : "" ) + "AND L.CODEMP=? " + "AND L.VENCTOLOTE = ( SELECT MIN(VENCTOLOTE) " + "FROM EQLOTE LS "
					+ "WHERE LS.CODPROD=L.CODPROD AND LS.CODFILIAL=L.CODFILIAL " + "AND LS.CODEMP=L.CODEMP " + ( ( (Boolean) prefs( 2 ) ).booleanValue() ? "AND LS.SLDLIQLOTE>0 " : "" ) + "AND VENCTOLOTE >= CAST('today' AS DATE)" + ")";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, txtCodProd.getVlrInteger().intValue() );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQLOTE" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				txtCodLote.setVlrString( rs.getString( 1 ) != null ? rs.getString( 1 ) : "" );
				lcLote.carregaDados();
			}

			rs.close();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar lote!\n" + err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

	}

	private String[] getInfoCli( int codcli ) {

		String[] ret = new String[ 6 ];
		String sSQL = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			sSQL = "SELECT RAZCLI, CPFCLI, ENDCLI, NUMCLI, CIDCLI, UFCLI " + "FROM VDCLIENTE " + "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? ";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, codcli );
			rs = ps.executeQuery();

			while ( rs.next() ) {
				for ( int i = 0; i < 6; i++ )
					ret[ i ] = rs.getString( i + 1 );
			}

			rs.close();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao pegar dados do cliente!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}

		return ret;
	}

	public String getDescEst() {

		String sDesc = "ESTAÇÃO DE TRABALHO NÃO CADASTRADA";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT DESCEST FROM SGESTACAO WHERE CODEST=? AND CODEMP=? AND CODFILIAL=?";
		
		try {
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iNumEst );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, Aplicativo.iCodFilial );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				sDesc = rs.getString( "DescEst" );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, err.getMessage() );
			sDesc = "NÃO FOI POSSÍVEL REGISTRAR A ESTAÇÃO DE TRABALHO! ! !";
		}
		return sDesc;
	}

	public String getPosAliquota( float ftAliquota ) {

		String sRetorno = "";
		String sAliquota = null;
		try {
			sAliquota = Funcoes.transValor( String.valueOf( ftAliquota ), 4, 2, true );
			sRetorno = Funcoes.strZero( String.valueOf( vAliquotas.indexOf( sAliquota ) + 1 ), 2 );
		} finally {
			sAliquota = null;
		}
		return sRetorno;
	}

	private void setTipoMov() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODTIPOMOV FROM SGPREFERE4 WHERE CODEMP=? AND CODFILIAL=?";
		
		try {
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				CODTIPOMOV = rs.getInt( "CodTipoMov" );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o tipo de movimento.\nProvavelmente não foram gravadas corretamente as preferências!\n" + err.getMessage() );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

	}

	private int getVendedor() {

		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODVEND FROM ATATENDENTE WHERE CODEMPUS=? AND CODFILIALUS=? AND IDUSU=?";

		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATENDENTE" ) );
			ps.setString( 3, Aplicativo.strUsuario );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				iRet = rs.getInt( "CODVEND" );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o comissionado.\n" + err.getMessage() );
			err.printStackTrace();
			dispose();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}

	private BigDecimal[] getItComis( int iCodItOrc ) {

		BigDecimal[] retorno = new BigDecimal[ 2 ];

		if ( iCodItOrc > 0 ) {

			PreparedStatement ps = null;
			ResultSet rs = null;
			String sSQL = null;

			try {

				sSQL = "SELECT P.COMISPROD, ( ( IT.VLRLIQITORC * P.COMISPROD ) / 100 ) as VLRLIQCOMIS FROM VDITORCAMENTO IT, VDORCAMENTO O, VDVENDEDOR VD, EQPRODUTO P " + "WHERE IT.CODEMP=? AND IT.CODFILIAL=? AND IT.CODORC=? AND IT.CODITORC=? "
						+ "AND O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODORC=IT.CODORC " + "AND VD.CODEMP=O.CODEMPVD AND VD.CODFILIAL=O.CODFILIALVD AND VD.CODVEND=O.CODVEND " + "AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD AND P.CODPROD=IT.CODPROD";

				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDITORCAMENTO" ) );
				ps.setInt( 3, CODORC );
				ps.setInt( 4, iCodItOrc );
				rs = ps.executeQuery();

				if ( rs.next() ) {
					retorno[ 0 ] = rs.getBigDecimal( "COMISPROD" );
					retorno[ 1 ] = rs.getBigDecimal( "VLRLIQCOMIS" );
				}

				rs.close();
				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao buscar comissão.\n" + err.getMessage() );
				err.printStackTrace();
			} finally {
				ps = null;
				rs = null;
				sSQL = null;
			}

		}
		else {

			PreparedStatement ps = null;
			ResultSet rs = null;
			String sSQL = null;

			try {

				sSQL = "SELECT P.COMISPROD FROM EQPRODUTO P WHERE P.CODEMP=? AND P.CODFILIAL=? AND P.CODPROD=?";

				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
				ps.setInt( 3, txtCodProd.getVlrInteger().intValue() );
				rs = ps.executeQuery();

				if ( rs.next() ) {
					retorno[ 0 ] = rs.getBigDecimal( "COMISPROD" );
					retorno[ 1 ] = ( rs.getBigDecimal( "COMISPROD" ).multiply( txtPreco.getVlrBigDecimal() ) ).divide( new BigDecimal( 100 ), AplicativoPDV.casasDecFin, BigDecimal.ROUND_HALF_UP );
				}

				rs.close();
				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao buscar comissão.\n" + err.getMessage() );
				err.printStackTrace();
			} finally {
				ps = null;
				rs = null;
				sSQL = null;
			}

		}

		return retorno;

	}

	private int getCodCli() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int iRet = 0;
		String sSQL = "SELECT CODCLI FROM SGPREFERE4 WHERE CODEMP=? AND CODFILIAL=?";
		
		try {
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				iRet = rs.getInt( "CODCLI" );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o código do cliente.\nProvavelmente não foram gravadas corretamente as preferências!\n" + err.getMessage() );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		return iRet;
	}

	private int getPlanoPag() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int iRet = 0;
		String sSQL = "SELECT CodPlanoPag FROM SGPREFERE4 WHERE CODEMP=? AND CODFILIAL=?";
		
		try {
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				iRet = rs.getInt( "CodPlanoPag" );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o plano de pagamento.\nProvavelmente não foram gravadas corretamente as preferências!\n" + err.getMessage() );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		return iRet;
	}

	private Integer getCodSeqCaixa() {

		Integer retorno = new Integer( 0 );
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "";
		
		try {
			
			sSQL = "SELECT ISEQ FROM SPGERANUMPDV(?,?,?)";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, CODCAIXA );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				retorno = new Integer( rs.getInt( 1 ) );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar sequencia." + err.getMessage() );
			err.printStackTrace();
			setVisible( false );
		}
		
		return retorno;
	}

	private void buscaPreco() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		String sSQL = "SELECT PRECO FROM VDBUSCAPRECOSP(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
		
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Integer.parseInt( txtCodProd.getVlrString().trim() ) );
			ps.setInt( 2, txtCodCli.getVlrInteger().intValue() );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 5, txtCodPlanoPag.getVlrInteger().intValue() );
			ps.setInt( 6, Aplicativo.iCodEmp );
			ps.setInt( 7, lcPlanoPag.getCodFilial() );
			ps.setInt( 8, txtCodTipoMov.getVlrInteger().intValue() );
			ps.setInt( 9, Aplicativo.iCodEmp );
			ps.setInt( 10, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			ps.setInt( 11, Aplicativo.iCodEmp );
			ps.setInt( 12, Aplicativo.iCodFilial );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtPreco.setVlrBigDecimal( rs.getString( 1 ) != null ? ( new BigDecimal( rs.getString( 1 ) ) ) : ( new BigDecimal( 0 ) ) );
			}
			else {
				txtPreco.setVlrBigDecimal( new BigDecimal( 0 ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar o preço!\n" + err.getMessage(), true, con, err );
		}
	}

	private Object prefs( int index ) {

		if ( pref[ 0 ] == null ) {

			pref = new Object[ 3 ];

			PreparedStatement ps = null;
			ResultSet rs = null;
			String sSQL = "SELECT P4.ADICPDV, P4.CODPROD, P1.CONTESTOQ " + "FROM SGPREFERE1 P1, SGPREFERE4 P4 " + "WHERE P4.CODEMP=? AND P4.CODFILIAL=? " + "AND P1.CODEMP=P4.CODEMP AND P1.CODFILIAL=P4.CODFILIAL";

			try {
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE4" ) );
				rs = ps.executeQuery();
				if ( rs.next() ) {
					pref[ 0 ] = new Boolean( rs.getString( "ADICPDV" ).equals( "S" ) );

					if ( rs.getString( "CODPROD" ) != null ) {
						pref[ 1 ] = rs.getString( "CODPROD" );
					}
					else {
						pref[ 1 ] = null;
					}

					pref[ 2 ] = new Boolean( rs.getString( "CONTESTOQ" ).equals( "S" ) );
				}
				
				rs.close();
				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
			} finally {
				sSQL = null;
				ps = null;
				rs = null;
			}

		}

		return pref[ index ];
	}

	public void keyPressed( KeyEvent kevt ) {

		switch ( kevt.getKeyCode() ) {
			case KeyEvent.VK_CONTROL :
				CTRL = true;
				break;
			case KeyEvent.VK_F3 :
				if ( CTRL ) {
					btCtrlF3.doClick();
				}
				else {
					btF3.doClick();
				}
				CTRL = false;
				break;
			case KeyEvent.VK_F4 :
				btF4.doClick();
				break;
			case KeyEvent.VK_F5 :
				btF5.doClick();
				break;
			case KeyEvent.VK_F6 :
				btF6.doClick();
				break;
			case KeyEvent.VK_F7 :
				btF7.doClick();
				break;
			case KeyEvent.VK_F8 :
				btF8.doClick();
				break;
			case KeyEvent.VK_F9 :
				btF9.doClick();
				break;
			case KeyEvent.VK_F10 :
				btF10.doClick();
				break;
			case KeyEvent.VK_F11 :
				btF11.doClick();
				break;
		}
		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( kevt.getSource() == txtQtdade ) {
				if ( txtCodProd.getVlrString().length() == 0 )
					Funcoes.mensagemInforma( null, "Produto em branco." );
				else if ( txtQtdade.getVlrDouble().doubleValue() == 0 )
					Funcoes.mensagemInforma( null, "Quantidade em branco." );
				else if ( txtPercDescItOrc.getAtivo() )
					txtPercDescItOrc.requestFocus();
				else {
					if ( lcVenda.getStatus() == ListaCampos.LCS_INSERT ) {
						if ( lcVenda.post() ) {
							insereItem();
							iniItem();
						}
					}
					else if ( lcVenda.getStatus() == ListaCampos.LCS_SELECT ) {
						insereItem();
						iniItem();
						lcVenda.carregaDados();
					}
				}
			}
			else if ( kevt.getSource() == txtVlrDescItOrc ) {
				if ( lcVenda.getStatus() == ListaCampos.LCS_INSERT ) {
					if ( lcVenda.post() ) {
						insereItem();
						iniItem();
					}
				}
				else if ( lcVenda.getStatus() == ListaCampos.LCS_SELECT ) {
					insereItem();
					iniItem();
					lcVenda.carregaDados();
				}
			}
			else if ( kevt.getSource() == txtCodProd ) {
				
				if ( "S".equals( txtTelaAdicPDV.getVlrString().trim() ) && pluginVenda != null ) {
					
					final Map map = pluginVenda.beforeVendaItem();
					
					int iCodConv = ( (Integer) map.get( "conveniado" ) ).intValue();
					
					if ( iCodConv > 0 ) {
						
						txtCodConv.setVlrInteger( new Integer( iCodConv ) );
						lcConv.carregaDados();
						
						txtQtdade.requestFocus();
					}
					
				}
				
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freedom.acao.PostListener#beforePost(org.freedom.acao.PostEvent)
	 */
	public synchronized void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btF3 ) {
			cancItem();
		}
		else if ( evt.getSource() == btCtrlF3 ) {
			cancCupom();
		}
		else if ( evt.getSource() == btF4 ) {
			fechaVenda();
		}
		else if ( evt.getSource() == btF5 ) {
			leituraX();
		}
		else if ( evt.getSource() == btF6 ) {
			abreGaveta();
		}
		else if ( evt.getSource() == btF7 ) {
			DlgCalc calc = new DlgCalc();
			Aplicativo.telaPrincipal.criatela( "Calc", calc, con );
			calc.setTelaPrim( Aplicativo.telaPrincipal );
		}
		else if ( evt.getSource() == btF8 ) {
			repeteItem();
		}
		else if ( evt.getSource() == btF9 ) {
			trocaCli();
		}
		else if ( evt.getSource() == btF10 ) {
			fechaCaixa();
		}
		else if ( evt.getSource() == btF11 ) {
			abreAdicOrc();
		}
	}

	public void windowGainedFocus( WindowEvent e ) {

		setFocusProd();
	}

	public void windowLostFocus( WindowEvent e ) {

	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcProduto && txtCodProd.getVlrString().length() > 0 ) {
			
			buscaPreco();
			
			if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
				txtCodLote.setAtivo( true );
				getLote();
			}
			else {
				txtCodLote.setVlrString( "" );
				txtCodLote.setAtivo( false );
			}
			
		}
		
	}

	public void beforePost( PostEvent pevt ) {

	}

	public void afterPost( PostEvent pevt ) {

		if ( pevt.getListaCampos() == lcVenda && pevt.ok ) {
			if ( ( AplicativoPDV.bECFTerm ) && ( ecf != null ) ) {
				ecf.abreCupom( );
			}
			else {
				return;
			}
		}
	}

	public void focusGained( FocusEvent fevt ) {

	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtPercDescItOrc ) {
			if ( txtPercDescItOrc.getText().trim().length() < 1 ) {
				txtVlrDescItOrc.setAtivo( true );
				txtVlrDescItOrc.requestFocus();
			}
			else {
				txtVlrDescItOrc.setVlrBigDecimal( txtQtdade.getVlrBigDecimal().multiply( txtPreco.getVlrBigDecimal().multiply( txtPercDescItOrc.getVlrBigDecimal() ).divide( new BigDecimal( "100" ), 2, BigDecimal.ROUND_HALF_UP ) ) );
				txtVlrDescItOrc.setAtivo( false );
				if ( lcVenda.getStatus() == ListaCampos.LCS_INSERT ) {
					if ( lcVenda.post() ) {
						insereItem();
						iniItem();
					}
				}
				else if ( lcVenda.getStatus() == ListaCampos.LCS_SELECT ) {
					insereItem();
					iniItem();
					lcVenda.carregaDados();
				}
			}
		}
		else if ( fevt.getSource() == txtVlrDescItOrc ) {
			if ( txtVlrDescItOrc.getText().trim().length() < 1 ) {
				txtPercDescItOrc.setAtivo( true );
			}
			else if ( txtVlrDescItOrc.getAtivo() ) {
				txtPercDescItOrc.setAtivo( false );
			}
		}
	}

	public void setConexao( Connection con ) {

		super.setConexao( con );
		lcCliente.setConexao( con );
		lcVendedor.setConexao( con );
		lcClComis.setConexao( con );
		lcPlanoPag.setConexao( con );
		lcVenda.setConexao( con );
		lcProduto.setConexao( con );
		lcLote.setConexao( con );
		lcTipoMov.setConexao( con );
		lcSerie.setConexao( con );
		lcClFiscal.setConexao( con );
		lcConv.setConexao( con );

		setCodCaixa();
		setTipoMov();
		
		CODCLI = getCodCli();
		PLANOPAG = getPlanoPag();
		CODVEND = getVendedor();

		txtCodTipoMov.setVlrInteger( new Integer( CODTIPOMOV ) );
		txtCodCli.setVlrInteger( new Integer( CODCLI ) );

		pnStatusBar.add( sbVenda, BorderLayout.CENTER );
		pnRodape.add( pnStatusBar, BorderLayout.CENTER );
		vAliquotas = FAliquota.getAliquotas( ecf );

		iniVenda();
		
		sbVenda.setUsuario( Aplicativo.strUsuario );
		sbVenda.setCodFilial( Aplicativo.iCodFilial );
		sbVenda.setRazFilial( Aplicativo.sRazFilial );
		sbVenda.setNumEst( Aplicativo.iNumEst );
		sbVenda.setDescEst( getDescEst() );

		txtCodProd.setBuscaGenProd( new DLCodProd( con, txtQtdade ) );
		
		carregaPlugin();

	}

}
