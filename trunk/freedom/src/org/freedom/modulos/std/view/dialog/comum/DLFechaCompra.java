/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLFechaCompra.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.comum;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTabbedPanePad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPassword;

public class DLFechaCompra extends FFDialogo implements FocusListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JTabbedPanePad tpn = new JTabbedPanePad();

	private JPanelPad pinFecha = new JPanelPad( 420, 300 );

	private JPanelPad pnPagar = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabPag = new JTablePad();

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlrDescItCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtPercDescCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtVlrDescCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtVlrBaseICMSST = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtVlrICMSST = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtPercAdicCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private JTextFieldPad txtVlrLiqCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtQtdFreteCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrAdicCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrProdCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrFreteCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrICMSCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrIPICompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtVlrBaseICMS = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtNParcPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlrParcItPag = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrParcPag = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtDtVencItPag = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtStatusCompra = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbEmitePedido = new JCheckBoxPad( "Emite Pedido?", "S", "N" );
	
	private JCheckBoxPad cbFinalizar = new JCheckBoxPad( "Finalizar?", "S", "N" );

	private JCheckBoxPad cbEmiteNota = new JCheckBoxPad( "Emite Nota Fiscal?", "S", "N" );
	
	private JCheckBoxPad cbAdicFreteCusto = new JCheckBoxPad( "Soma Valor do frete ao custo dos produtos.", "S", "N" );
	
	private JCheckBoxPad cbAdicAdicCusto = new JCheckBoxPad( "Soma Valor adicional ao custo dos produtos.", "S", "N" );
	
	private JCheckBoxPad cbAdicIPIBase = new JCheckBoxPad( "Soma IPI à base de cálculo do ICMS.", "S", "N" );
	
	private JCheckBoxPad cbAdicFreteBase = new JCheckBoxPad( "Soma Frete à base de cálculo do ICMS.", "S", "N" );
	
	private JCheckBoxPad cbAdicAdicBase = new JCheckBoxPad( "Soma Vlr. Adicionais à base de cálculo do ICMS.", "S", "N" );
	
	private JRadioGroup<?, ?> rgFreteVD = null;

	private ListaCampos lcCompra = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcPagar = new ListaCampos( this );

	private ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private ListaCampos lcItPagar = new ListaCampos( this );

	private Vector<String> vVals = new Vector<String>();

	private Vector<String> vLabs = new Vector<String>();

    public JButtonPad btFechar = new JButtonPad("Fechar Compra", Icone.novo("btOk.gif"));

	private int iCodCompraFecha = 0;

	private boolean bPodeSair = false;
	
	BigDecimal volumes = null;
	
	private JPanelPad pinLbCusto = new JPanelPad();
	private JPanelPad pinCusto = new JPanelPad();

	private JPanelPad pinLbValores = new JPanelPad();
	private JPanelPad pinValores = new JPanelPad();
	
	private JPanelPad pinLbFrete = new JPanelPad();
	private JPanelPad pinFrete = new JPanelPad();
	
	private JPanelPad pinLbImp = new JPanelPad();
	private JPanelPad pinImp = new JPanelPad();

	private JPanelPad pinLbTrib = new JPanelPad();
	private JPanelPad pinTrib = new JPanelPad();
	
	private JPanelPad pinLbICMS = new JPanelPad();
	private JPanelPad pinICMS = new JPanelPad();


	public DLFechaCompra( DbConnection cn, Integer iCodCompra, Component cOrig, BigDecimal volumes, boolean NFe) {

		super( cOrig );
		setConexao( cn );
		
		if ( NFe ) {
		    cbEmiteNota.setText( "Emite NFE?" );
		} 
		
		iCodCompraFecha = iCodCompra.intValue();
		setTitulo( "Fechar Compra" );
		setAtribos( 560, 530 );

		this.volumes = volumes;
		
		lcItPagar.setMaster( lcPagar );
		lcPagar.adicDetalhe( lcItPagar );
		lcItPagar.setTabela( tabPag );

		c.add( tpn );

		tpn.add( "Fechamento", pinFecha );
		tpn.add( "Pagar", pnPagar );

		vVals.addElement( "C" );
		vVals.addElement( "F" );
		vLabs.addElement( "CIF" );
		vLabs.addElement( "FOB" );
		rgFreteVD = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );

		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setFK( true );
		txtDescPlanoPag.setListaCampos( lcPlanoPag );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		lcPlanoPag.setConexao( cn );

		txtCodBanco.setNomeCampo( "CodBanco" );
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		txtDescBanco.setListaCampos( lcBanco );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		lcBanco.setConexao( cn );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );

		txtCodCompra.setNomeCampo( "CodCompra" );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		lcCompra.add( new GuardaCampo( txtCodCompra, "CodCompra", "N.pedido", ListaCampos.DB_PK, false ) );
		lcCompra.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cod.p.pg.", ListaCampos.DB_FK, txtDescPlanoPag, false ) );
		lcCompra.add( new GuardaCampo( txtVlrLiqCompra, "VlrLiqCompra", "V.compra", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrICMSCompra, "VlrICMSCompra", "V.ICMS", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrICMSST, "VlrICMSSTCompra", "V.ICMS ST", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrIPICompra, "VlrIPICompra", "V.IPI", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrDescItCompra, "VlrDescItCompra", "% Desc.it.", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrDescCompra, "VlrDescCompra", "% Desc.it.", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrAdicCompra, "VlrAdicCompra", "V.adic.", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrProdCompra, "VlrProdCompra", "V.prod.", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrFreteCompra, "VlrFreteCompra", "V.prod.", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtStatusCompra, "StatusCompra", "Status", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtCodBanco, "CodBanco", "CodBanco", ListaCampos.DB_FK, txtDescBanco, false ) );
		lcCompra.add( new GuardaCampo( rgFreteVD, "TipoFreteCompra", "Tipo do frete", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( cbAdicFreteCusto, "AdicFreteCompra", "frete na campra", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( cbAdicAdicCusto, "AdicAdicCompra", "Vlr Adicional na campra", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtQtdFreteCompra, "QtdFreteCompra", "Qtd. de volumes na compra", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrBaseICMS, "VlrBaseICMSCompra", "Vlr. Base do ICMS", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrBaseICMSST, "VlrBaseICMSSTCompra", "Vlr. Base do ICMS ST", ListaCampos.DB_SI, false ) );
		
		lcCompra.montaSql( false, "COMPRA", "CP" );
		lcCompra.setConexao( cn );
		txtVlrLiqCompra.setListaCampos( lcCompra );
		txtVlrICMSCompra.setListaCampos( lcCompra );
		txtVlrIPICompra.setListaCampos( lcCompra );
		txtVlrAdicCompra.setListaCampos( lcCompra );
		txtPercAdicCompra.setListaCampos( lcCompra );
		txtVlrDescCompra.setListaCampos( lcCompra );
		txtVlrFreteCompra.setListaCampos( lcCompra );
		txtQtdFreteCompra.setListaCampos( lcCompra );
		txtPercDescCompra.setListaCampos( lcCompra );
		txtStatusCompra.setListaCampos( lcCompra );
		txtCodPlanoPag.setListaCampos( lcCompra );

		JPanelPad pinTopPag = new JPanelPad( 400, 60 );
		pinTopPag.setPreferredSize( new Dimension( 400, 60 ) );
		pnPagar.add( pinTopPag, BorderLayout.NORTH );
		JScrollPane spnTabPag = new JScrollPane( tabPag );
		pnPagar.add( spnTabPag, BorderLayout.CENTER );

		txtVlrParcPag.setAtivo( false );

		pinTopPag.adic( new JLabelPad( "Valor Tot." ), 7, 0, 130, 20 );
		pinTopPag.adic( txtVlrParcPag, 7, 20, 130, 20 );

		txtCodPag.setNomeCampo( "CodPag" );
		lcPagar.add( new GuardaCampo( txtCodPag, "CodPag", "Cód.pgto.", ListaCampos.DB_PK, false ) );
		lcPagar.add( new GuardaCampo( txtVlrParcPag, "VlrParcPag", "Valor tot.", ListaCampos.DB_SI, false ) );
		lcPagar.montaSql( false, "PAGAR", "FN" );
		lcPagar.setConexao( cn );
		txtCodPag.setListaCampos( lcPagar );
		txtVlrParcPag.setListaCampos( lcPagar );
		txtCodBanco.setListaCampos( lcPagar );

		txtNParcPag.setNomeCampo( "NParcPag" );
		lcItPagar.add( new GuardaCampo( txtNParcPag, "NParcPag", "N.parc.", ListaCampos.DB_PK, false ) );
		lcItPagar.add( new GuardaCampo( txtVlrParcItPag, "VlrParcItPag", "Valor tot.", ListaCampos.DB_SI, false ) );
		lcItPagar.add( new GuardaCampo( txtDtVencItPag, "DtVencItPag", "Valor tot.", ListaCampos.DB_SI, false ) );
		lcItPagar.montaSql( false, "ITPAGAR", "FN" );
		lcItPagar.setConexao( cn );
		txtNParcPag.setListaCampos( lcItPagar );
		txtVlrParcItPag.setListaCampos( lcItPagar );
		txtDtVencItPag.setListaCampos( lcItPagar );

		lcItPagar.montaTab();
		tabPag.addMouseListener( this );

		txtCodCompra.setVlrInteger( iCodCompra );
		lcCompra.carregaDados();

		setPainel( pinFecha );
		adic( new JLabelPad( "Cód.pag." ), 7, 0, 60, 20 );
		adic( txtCodPlanoPag, 7, 20, 60, 20 );
		adic( new JLabelPad( "Descrição do plano de pagto." ), 70, 0, 200, 20 );
		adic( txtDescPlanoPag, 70, 20, 200, 20 );

		adic( new JLabelPad( "Cód.bco." ), 273, 0, 60, 20 );
		adic( txtCodBanco, 273, 20, 60, 20 );

		adic( new JLabelPad( "Descrição do Banco" ), 336, 0, 200, 20 );
		adic( txtDescBanco, 336, 20, 200, 20 );

   	   /**********************
		*  Quadro de valores
		**********************/
		
		pinLbValores.adic( new JLabelPad( "   Valores " ), 0, 0, 70, 15 );
		pinLbValores.tiraBorda();

		adic( pinLbValores, 20, 52, 60, 15 ); 
		adic( pinValores, 7, 60, 326, 110 );
				
		pinValores.adic( new JLabelPad( "% Desc." ), 7, 10, 60, 20 );
		pinValores.adic( txtPercDescCompra, 7, 30, 60, 20 );
		
		pinValores.adic( new JLabelPad( "Vlr.Desc." ), 70, 10, 80, 20 );
		pinValores.adic( txtVlrDescCompra, 70, 30, 80, 20 );

		pinValores.adic( new JLabelPad( "Vlr.Frete" ), 153, 10, 80, 20 );
		pinValores.adic( txtVlrFreteCompra, 153, 30, 80, 20 );

		pinValores.adic( new JLabelPad( "Vlr.Compra" ), 236, 10, 80, 20 );
		pinValores.adic( txtVlrLiqCompra, 236, 30, 77, 20 );

  		pinValores.adic( new JLabelPad( "% Adic." ), 7, 50, 60, 20 );
		pinValores.adic( txtPercAdicCompra, 7, 70, 60, 20 );
		
		pinValores.adic( new JLabelPad( "Vlr.Adic." ), 70, 50, 80, 20 );
		pinValores.adic( txtVlrAdicCompra, 70, 70, 80, 20 );
			
//		pinValores.adic( new JLabelPad( "Vlr.ICMS" ), 153, 50, 80, 20 );
//		pinValores.adic( txtVlrICMSCompra, 153, 70, 80, 20 );
		
		pinValores.adic( new JLabelPad( "Vlr.IPI" ), 153, 50, 80, 20 );
		pinValores.adic( txtVlrIPICompra, 153, 70, 80, 20 );

   	   /**********************
		*  Quadro Frete
		**********************/
		
		pinLbFrete.adic( new JLabelPad( "   Frete " ), 0, 0, 70, 15 );
		pinLbFrete.tiraBorda();
		
		adic( pinLbFrete, 349, 52, 60, 15 ); 
		adic( pinFrete, 336, 60, 200, 110 );
		
		pinFrete.adic( rgFreteVD, 7, 20, 182, 30 );
			
		pinFrete.adic( new JLabelPad( "Volumes" ), 7, 50, 80, 20 );
		pinFrete.adic( txtQtdFreteCompra, 7, 70, 80, 20 );

		
  	   /**********************
		*  Quadro Tributação
		**********************/
		
		pinLbTrib.adic( new JLabelPad( "   Tributação " ), 0, 0, 90, 15 );
		pinLbTrib.tiraBorda();

		adic( pinLbTrib, 20, 178, 90, 15 ); 
		adic( pinTrib, 7, 185,  326, 110 );
		
/*		
  		pinTrib.adic( cbAdicFreteBase, 7, 10, 320, 20 );
		pinTrib.adic( cbAdicAdicBase, 7, 30, 320, 20 );
		pinTrib.adic( cbAdicIPIBase, 7, 50, 320, 20 );
		
		*/

	   /**********************
		*  ICMS
		**********************/
		
		pinLbICMS.adic( new JLabelPad( "   ICMS " ), 0, 0, 90, 15 );
		pinLbICMS.tiraBorda();

		adic( pinLbICMS, 349, 178, 90, 15 ); 
		adic( pinICMS, 336, 185,  200, 110 );
		
		pinICMS.adic( new JLabelPad( "Base calc." ), 7, 10, 90, 20 );
		pinICMS.adic( txtVlrBaseICMS, 7, 30, 90, 20 );
		pinICMS.adic( new JLabelPad( "Vlr.ICMS" ), 7, 50, 90, 20 );
		pinICMS.adic( txtVlrICMSCompra, 7, 70, 90, 20 );
		
		
		pinICMS.adic( new JLabelPad( "Base calc. ST" ), 100, 10, 90, 20 );
		pinICMS.adic( txtVlrBaseICMSST, 100, 30, 90, 20 );		
		pinICMS.adic( new JLabelPad( "Vlr.ICMS ST" ), 100, 50, 90, 20 );
		pinICMS.adic( txtVlrICMSST, 100, 70, 90, 20 );

		
        /******************************
		*  Quadro Composição do custo
		******************************/
				
		pinLbCusto.adic( new JLabelPad( "   Composição do custo " ), 0, 0, 150, 15 );
		pinLbCusto.tiraBorda();

		adic( pinLbCusto, 20, 300, 150, 15 ); 
		adic( pinCusto, 7, 310,  326, 90 );
		
		pinCusto.adic( cbAdicFreteCusto, 7, 10, 280, 20 );
		pinCusto.adic( cbAdicAdicCusto, 7, 30, 280, 20 );

	   /**********************
		*  Quadro Emissão
		**********************/
		
		pinLbImp.adic( new JLabelPad( "   Emissão " ), 0, 0, 90, 15 );
		pinLbImp.tiraBorda();
		
		adic( pinLbImp, 349, 300, 90, 15 ); 
		adic( pinImp, 336, 310, 200, 90 );

		pinImp.adic( cbEmitePedido, 7, 10, 180, 20 );
		pinImp.adic( cbEmiteNota, 7, 30, 180, 20 );
		pinImp.adic( cbFinalizar, 7, 50, 180, 20 );		
		
		/********** FIM DOS QUADROS  ***********/
		
		if (txtVlrDescItCompra.getVlrBigDecimal().compareTo( new BigDecimal(0) )!=0) {
			txtPercDescCompra.setAtivo( false );
			txtVlrDescCompra.setAtivo( false );
		}
		
		tpn.setEnabledAt( 1, false );
		Funcoes.transValor( new BigDecimal(0), 10, 2, false );
		btFechar.addActionListener( this );
		
		txtPercDescCompra.addFocusListener( this );
		txtVlrDescCompra.addFocusListener( this );
		txtPercAdicCompra.addFocusListener( this );
		txtVlrAdicCompra.addFocusListener( this );

		txtQtdFreteCompra.setVlrBigDecimal( volumes );
		
		lcCompra.edit();
	}

	private void adicVlrFrete(){
		
		if( txtVlrFreteCompra.getVlrBigDecimal().intValue() > 0 ){
			
			BigDecimal bdVlrFrete = txtVlrFreteCompra.getVlrBigDecimal();
			BigDecimal bdVlrCompra = txtVlrLiqCompra.getVlrBigDecimal();
				
			if ( Funcoes.mensagemConfirma( null, "Deseja adicionar o valor do frete no valor total?" ) == JOptionPane.YES_OPTION ) {
				
				txtVlrLiqCompra.setVlrBigDecimal( bdVlrCompra.add( bdVlrFrete ) );
			}
		}		
	}
	
	private void alteraParc() {

		lcItPagar.edit();
		DLFechaPag dl = new DLFechaPag( DLFechaCompra.this, txtVlrParcItPag.getVlrBigDecimal(), txtDtVencItPag.getVlrDate() );
		dl.setVisible( true );
		if ( dl.OK ) {
			txtVlrParcItPag.setVlrBigDecimal( (BigDecimal) dl.getValores()[ 0 ] );
			txtDtVencItPag.setVlrDate( (Date) dl.getValores()[ 1 ] );
			lcItPagar.post();
			// Atualiza lcPagar
			if ( lcPagar.getStatus() == ListaCampos.LCS_EDIT ) {
				lcPagar.post(); // Caso o lcPagar estaja como edit executa o post que atualiza
			}
			else {
				lcPagar.carregaDados(); // Caso não, atualiza
			}
		}
		else {
			dl.dispose();
			lcItPagar.cancel( false );
		}
		dl.dispose();
	}

	private int getCodPag() {

		int iRetorno = 0;
		String sSQL = "SELECT CODPAG FROM FNPAGAR WHERE CODCOMPRA=? AND CODEMPCP=? AND CODFILIALCP=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, iCodCompraFecha );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				iRetorno = rs.getInt( "CodPag" );
			}

			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o código da conta a Pagar!\n" + err.getMessage(), true, con, err );
		}
		return iRetorno;
	}

	private boolean getVerificaUsu() {

		boolean bRetorno = false;
		String sSQL = "SELECT VERIFALTPARCVENDA FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				if ( rs.getString( "VerifAltParcVenda" ).trim().equals( "S" ) ) {
					bRetorno = true;
				}
			}
			
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRetorno;
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 8 ];
		
		sRetorno[ 0 ] = txtCodPlanoPag.getVlrString();
		sRetorno[ 1 ] = txtVlrDescCompra.getVlrString();
		sRetorno[ 2 ] = txtVlrAdicCompra.getVlrString();
		sRetorno[ 3 ] = cbEmitePedido.getVlrString();
		sRetorno[ 4 ] = cbEmiteNota.getVlrString();
		sRetorno[ 5 ] = txtVlrLiqCompra.getVlrString();
		sRetorno[ 6 ] = txtQtdFreteCompra.getVlrString();
		sRetorno[ 7 ] = cbFinalizar.getVlrString();
		
		return sRetorno;
		
	}
	
	private void fecharCompra() {

		lcCompra.edit();
		if ( txtStatusCompra.getVlrString().trim().equals( "P1" ) ) {
			txtStatusCompra.setVlrString( "P2" );
		}
		if ( txtStatusCompra.getVlrString().trim().equals( "C1" ) ) {
			txtStatusCompra.setVlrString( "C2" );
		}
		lcCompra.post();
		int iCodPag = getCodPag();
		if ( iCodPag > 0 ) {
			txtCodPag.setVlrInteger( new Integer( iCodPag ) );
			lcPagar.carregaDados();
		}
		bPodeSair = true;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btCancel ) {
			super.actionPerformed( evt );
		}
		else if ( evt.getSource() == btOK ) {
			if ( bPodeSair ) {
				if ( lcPagar.getStatus() == ListaCampos.LCS_EDIT ) {
					lcPagar.post();
				}
				if ( cbEmitePedido.getVlrString().trim().equals( "S" ) ) {
					lcCompra.edit();
					txtStatusCompra.setVlrString( "P3" );
					if ( !lcCompra.post() ) {
						cbEmitePedido.setVlrString( "N" );
					}
				}
				if ( cbEmiteNota.getVlrString().trim().equals( "S" ) ) {
					lcCompra.edit();
					txtStatusCompra.setVlrString( "C3" );
					if ( !lcCompra.post() ) {
						cbEmiteNota.setVlrString( "N" );
					}
				}
				super.actionPerformed( evt );
			}
			else {
				if ( tpn.getSelectedIndex() == 0 ) {
					fecharCompra();
					tpn.setEnabledAt( 1, true );
					tpn.setSelectedIndex( 1 );
				}
			}
		}
		else if ( evt.getSource() == btFechar ) {
			fecharCompra();
			btOK.doClick();
		}
	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtPercDescCompra ) {
			if ( txtPercDescCompra.getText().trim().length() < 1 ) {
				txtVlrDescCompra.setAtivo( true );
			}
			else {
				txtVlrDescCompra.setVlrBigDecimal( txtVlrProdCompra.getVlrBigDecimal().multiply( txtPercDescCompra.getVlrBigDecimal() ).divide( new BigDecimal( "100" ), 2, BigDecimal.ROUND_HALF_UP ) );
				txtVlrDescCompra.setAtivo( false );
			}
		}
		if ( fevt.getSource() == txtPercAdicCompra ) {
			if ( txtPercAdicCompra.getText().trim().length() < 1 ) {
				txtVlrAdicCompra.setAtivo( true );
			}
			else {
				txtVlrAdicCompra.setVlrBigDecimal( txtVlrProdCompra.getVlrBigDecimal().multiply( txtPercAdicCompra.getVlrBigDecimal() ).divide( new BigDecimal( "100" ), 2, BigDecimal.ROUND_HALF_UP ) );
				txtVlrAdicCompra.setAtivo( false );
			}
		}
	}

	public void focusGained( FocusEvent fevt ) { 
		if ( fevt.getSource() == txtVlrDescCompra ) {
			BigDecimal liq = txtVlrLiqCompra.getVlrBigDecimal();
			BigDecimal des = txtVlrDescCompra.getVlrBigDecimal();
			BigDecimal tot = liq.subtract( des ) ;
			txtVlrLiqCompra.setVlrBigDecimal(tot);
		}
	}
	
	public void keyPressed( KeyEvent kevt ) {
		
		if( kevt.getSource() == txtVlrFreteCompra ){
			if( kevt.getKeyCode() == KeyEvent.VK_ENTER ){
				adicVlrFrete();
			}
		}
		
		super.keyPressed( kevt );
	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( ( mevt.getClickCount() == 2 ) && ( tabPag.getLinhaSel() >= 0 ) ) {
			if ( getVerificaUsu() ) {
				FPassword fpw = new FPassword( this, FPassword.ALT_PARC_VENDA, null, con );
				fpw.execShow();
				if ( fpw.OK ) {
					alteraParc();
				}
				fpw.dispose();
			}
			else {
				alteraParc();
			}
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }
}
