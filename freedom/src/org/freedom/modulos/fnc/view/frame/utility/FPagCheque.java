/**
 * @version 01/08/2010 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.fnc.view.frame.utility <BR>
 *         Classe:
 *         
 * @(#)FPagCheque.java <BR>
 * 
 *                    Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                    na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                    Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                    Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                    Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Tela de emissão de cheques para pagamento de fornecedores.
 * 
 */

package org.freedom.modulos.fnc.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.bmps.Icone; // import org.freedom.componentes.ObjetoHistorico;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;

public class FPagCheque extends FFilho implements ActionListener, TabelaEditListener {

	private static final long serialVersionUID = 1L;

	// private static final String HISTORICO_PADRAO = "PAGAMENTO REF. A COMPRA: <DOCUMENTO>";

	private ImageIcon imgApag = Icone.novo( "clVencido.gif" );

	private ImageIcon imgSelecionadoPag = Icone.novo( "clPago.gif" );

	private ImageIcon imgCheq = Icone.novo( "clVencido.gif" );

	private ImageIcon imgSelecionadoCheq = Icone.novo( "clPago.gif" );
	
	private JButtonPad btSelTudoPag = new JButtonPad( Icone.novo( "btTudo.gif" ) );

	private JButtonPad btSelNadaPag = new JButtonPad( Icone.novo( "btNada.gif" ) );

	private JButtonPad btGerarPag = new JButtonPad( Icone.novo( "btGerar.gif" ) );	

	private JButtonPad btSelTudoCheq = new JButtonPad( Icone.novo( "btTudo.gif" ) );

	private JButtonPad btSelNadaCheq = new JButtonPad( Icone.novo( "btNada.gif" ) );

	private JButtonPad btImpCheq = new JButtonPad( Icone.novo( "btImprime.gif" ) );	
	
	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL );

    private JLabelPad lbVlrapag = new JLabelPad( "Total a pagar", imgApag, SwingConstants.LEFT );

	private JLabelPad lbSelecionadoPag = new JLabelPad( "Total selecionado", imgSelecionadoPag, SwingConstants.LEFT );

    private JLabelPad lbVlrcheq = new JLabelPad( "Total cheques", imgCheq, SwingConstants.LEFT );

	private JLabelPad lbSelecionadoCheq = new JLabelPad( "Total selecionado", imgSelecionadoCheq, SwingConstants.LEFT );
	
	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTabbedPanePad tpn = new JTabbedPanePad();

	// Aba pagar
	
	private JPanelPad pnTabPagar = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesPagar = new JPanelPad( 40, 210 );

	private JPanelPad pinPagar = new JPanelPad( 500, 60 );

	private JPanelPad pnPagar = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabPagar = new JTablePad();

	private JScrollPane spnPagar = new JScrollPane( tabPagar );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlrTotApag = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrTotSelPag = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtDatainiPagar = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimPagar = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCNPJFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0);

	private JButtonPad btExecpagar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );
	
    // Aba cheques
	
	private JPanelPad pnTabCheq = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesCheq = new JPanelPad( 40, 210 );

	private JPanelPad pinCheq = new JPanelPad( 500, 60 );

	private JPanelPad pnCheq = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabCheq = new JTablePad();

	private JScrollPane spnCheq = new JScrollPane( tabCheq );

	private JTextFieldPad txtVlrTotCheq = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrTotSelCheq = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtDatainiCheq = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimCheq = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JButtonPad btExeccheq = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	// Fim aba cheques

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private ListaCampos lcFor = new ListaCampos( this );

	private Date dIniPagar = null;

	private Date dFimPagar = null;

	private Date dIniCheq = null;

	private Date dFimCheq = null;
	
	private boolean carregandoTabela = false; 
	
	private static String LABEL_SEL = "Sel.";

	private enum COLS_PAG {SEL, DTEMIT, DTVENCTO, STATUS, CODCOMPRA, CODPAG, NPARC, DOCLANCA, DOCCOMPRA, 
		VLRAPAG, NUMCONTA, CODTIPOCOB, DESCTIPOCOB, HISTPAG};

	private enum COLS_CHEQ { SEL, SEQ, DTEMIT, DTVENCTO, NUMCHEQ, NOMEFAVCHEQ, SITCHEQ, VLRCHEQ, NUMCONTA, HISTCHEQ };
	
	private enum SQL_PARAMS_PAG {NONE, DATAINI, DATAFIM, CODEMP, CODFILIAL, CODEMPFR, CODFILIALFR, CODFOR };

	private enum SQL_PARAMS_CHEQ {NONE, CODEMP, CODFILIAL, DATAINI, DATAFIM };
	
	public FPagCheque() {

		super( false );
		setTitulo( "Emissão de cheques" );
		setAtribos( 20, 20, 820, 480 );

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( tpn, BorderLayout.CENTER );
		btSair.setPreferredSize( new Dimension( 90, 30 ) );

		pnLegenda.setPreferredSize( new Dimension( 700, 50 ) );
		pnLegenda.setLayout( null );

		lbVlrapag.setBounds( 5, 0, 130, 17 );
		txtVlrTotApag.setBounds( 5, 18, 130, 18 );
		lbSelecionadoPag.setBounds( 140, 0, 130, 17 );
		txtVlrTotSelPag.setBounds( 140, 18, 130, 18 );

		lbVlrcheq.setBounds( 275, 0, 130, 17 );
		txtVlrTotCheq.setBounds( 275, 18, 130, 18 );
		lbSelecionadoCheq.setBounds( 410, 0, 130, 17 );
		txtVlrTotSelCheq.setBounds( 410, 18, 130, 18 );
		
		pnLegenda.add( lbVlrapag );
		pnLegenda.add( txtVlrTotApag );
		pnLegenda.add( lbSelecionadoPag );
		pnLegenda.add( txtVlrTotSelPag );

		pnLegenda.add( lbVlrcheq );
		pnLegenda.add( txtVlrTotCheq );
		pnLegenda.add( lbSelecionadoCheq );
		pnLegenda.add( txtVlrTotSelCheq );
		
		txtVlrTotApag.setSoLeitura( true );
		txtVlrTotSelPag.setSoLeitura( true );
		txtVlrTotCheq.setSoLeitura( true );
		txtVlrTotSelCheq.setSoLeitura( true );

		pnRod.setBorder( BorderFactory.createEtchedBorder() );
		pnRod.setPreferredSize( new Dimension( 600, 42 ) );

		pnRod.add( btSair, BorderLayout.EAST );
		pnRod.add( pnLegenda, BorderLayout.WEST );

		btSair.addActionListener( this );

		// Pagar

		tpn.addTab( "Pagar", pnPagar );

		btSelTudoPag.setToolTipText( "Marcar todos" );
		btSelNadaPag.setToolTipText( "Demarcar todos" );
		btGerarPag.setToolTipText( "Emitir cheque" );
		btExecpagar.setToolTipText( "Listar pagamentos" );

		pnPagar.add( pinPagar, BorderLayout.NORTH );
		pnTabPagar.add( pinBotoesPagar, BorderLayout.EAST );
		pnTabPagar.add( spnPagar, BorderLayout.CENTER );
		pnPagar.add( pnTabPagar, BorderLayout.CENTER );

		txtDatainiPagar.setVlrDate( new Date() );
		txtDatafimPagar.setVlrDate( new Date() );
		
		pinPagar.adic( new JLabelPad( "Cód.forn." ), 7, 0, 80, 20 );
		pinPagar.adic( txtCodFor, 7, 20, 80, 20 );
		pinPagar.adic( new JLabelPad( "Razão social do fornecedor" ), 90, 0, 250, 20 );
		pinPagar.adic( txtRazFor, 90, 20, 250, 20 );
		
		pinPagar.adic( new JLabelPad( "Período" ), 343, 0, 100, 20 );
		pinPagar.adic( txtDatainiPagar, 343, 20, 100, 20 );
		pinPagar.adic( new JLabelPad( "até" ), 446, 0, 100, 20 );
		pinPagar.adic( txtDatafimPagar, 446, 20, 100, 20 );
		pinPagar.adic( btExecpagar, 556, 10, 30, 30 );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCNPJFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );

		lcFor.montaSql( false, "FORNECED", "CP" );
		lcFor.setQueryCommit( false );
		lcFor.setReadOnly( true );

		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CodFor" );
		
		txtCodFor.setRequerido( true );
		txtDatainiPagar.setRequerido( true );
		txtDatafimPagar.setRequerido( true );
		txtCodFor.requestFocus();

		pinBotoesPagar.adic( btSelTudoPag, 5, 10, 30, 30 );
		pinBotoesPagar.adic( btSelNadaPag, 5, 40, 30, 30 );
		pinBotoesPagar.adic( btGerarPag, 5, 70, 30, 30 );

		tabPagar.adicColuna( LABEL_SEL ); 
		tabPagar.adicColuna( "Emissão" ); 
		tabPagar.adicColuna( "Vencto." ); 
		tabPagar.adicColuna( "Sit." ); 
		tabPagar.adicColuna( "Nº Compra" ); 
		tabPagar.adicColuna( "Cód.pag." ); 
		tabPagar.adicColuna( "Nº parc." ); 
		tabPagar.adicColuna( "Doc.lanca" ); 
		tabPagar.adicColuna( "Doc.compra" );
		tabPagar.adicColuna( "Valor parcela" ); 
		tabPagar.adicColuna( "Nº conta" ); 
		tabPagar.adicColuna( "Cód.tp.cob." );
		tabPagar.adicColuna( "Descrição do tipo de cobrança" ); 
		tabPagar.adicColuna( "Histórico" ); 

		tabPagar.setColunaEditavel( COLS_PAG.SEL.ordinal(), true );
		tabPagar.setTamColuna( 20, COLS_PAG.SEL.ordinal() );
		tabPagar.setTamColuna( 90, COLS_PAG.DTEMIT.ordinal() );
		tabPagar.setTamColuna( 90, COLS_PAG.DTVENCTO.ordinal() );
		tabPagar.setTamColuna( 20, COLS_PAG.STATUS.ordinal() );
		tabPagar.setTamColuna( 80, COLS_PAG.CODCOMPRA.ordinal() );
		tabPagar.setTamColuna( 80, COLS_PAG.CODPAG.ordinal() );
		tabPagar.setTamColuna( 50, COLS_PAG.NPARC.ordinal() );
		tabPagar.setTamColuna( 80, COLS_PAG.DOCLANCA.ordinal() );
		tabPagar.setTamColuna( 80, COLS_PAG.DOCCOMPRA.ordinal() );
		tabPagar.setTamColuna( 90, COLS_PAG.VLRAPAG.ordinal() );
		tabPagar.setTamColuna( 90, COLS_PAG.NUMCONTA.ordinal() );
		tabPagar.setTamColuna( 70, COLS_PAG.CODTIPOCOB.ordinal() );
		tabPagar.setTamColuna( 250, COLS_PAG.DESCTIPOCOB.ordinal() );
		tabPagar.setTamColuna( 300, COLS_PAG.HISTPAG.ordinal() );
		tabPagar.addTabelaEditListener( this );
		btExecpagar.addActionListener( this );
		btSelTudoPag.addActionListener( this );
		btSelNadaPag.addActionListener( this );
		btGerarPag.addActionListener( this );
		
		btExecpagar.setFocusable( false );

		// Aba cheques
		
		tpn.addTab( "Cheques", pnCheq );
		btSelTudoCheq.setToolTipText( "Marcar todos" );
		btSelNadaCheq.setToolTipText( "Demarcar todos" );
		btImpCheq.setToolTipText( "Imprimir cheques" );
		btExeccheq.setToolTipText( "Listar cheques" );

		pnCheq.add( pinCheq, BorderLayout.NORTH );
		pnTabCheq.add( pinBotoesCheq, BorderLayout.EAST );
		pnTabCheq.add( spnCheq, BorderLayout.CENTER );
		pnCheq.add( pnTabCheq, BorderLayout.CENTER );
		txtDatainiCheq.setVlrDate( new Date() );
		txtDatafimCheq.setVlrDate( new Date() );
		pinCheq.adic( new JLabelPad( "Período" ), 7, 0, 100, 20 );
		pinCheq.adic( txtDatainiCheq, 7, 20, 100, 20 );
		pinCheq.adic( new JLabelPad( "até" ), 110, 0, 100, 20 );
		pinCheq.adic( txtDatafimCheq, 110, 20, 100, 20 );
		pinCheq.adic( btExeccheq, 556, 10, 30, 30 );

		txtDatainiCheq.setRequerido( true );
		txtDatafimCheq.setRequerido( true );
		
		pinBotoesCheq.adic( btSelTudoCheq, 5, 10, 30, 30 );
		pinBotoesCheq.adic( btSelNadaCheq, 5, 40, 30, 30 );
		pinBotoesCheq.adic( btImpCheq, 5, 70, 30, 30 );

//		private enum COLS_CHEQ { SEL, DTEMIT, DTVENCTO, NOMEFAVCHEQ, SITCHEQ, VLRCHEQ, NUMCONTA };
		
		tabCheq.adicColuna( LABEL_SEL ); 
		tabCheq.adicColuna( "Seq." ); 
		tabCheq.adicColuna( "Emissão" ); 
		tabCheq.adicColuna( "Vencto." ); 
		tabCheq.adicColuna( "Número" ); 
		tabCheq.adicColuna( "Nome favorecido" ); 
		tabCheq.adicColuna( "Sit." ); 
		tabCheq.adicColuna( "Valor" ); 
		tabCheq.adicColuna( "Nº conta" ); 
		tabCheq.adicColuna( "Histórico" ); 

		tabCheq.setColunaEditavel( COLS_CHEQ.SEL.ordinal(), true );
		tabCheq.setTamColuna( 20, COLS_CHEQ.SEL.ordinal() );
		tabCheq.setTamColuna( 70, COLS_CHEQ.SEQ.ordinal() );
		tabCheq.setTamColuna( 90, COLS_CHEQ.DTEMIT.ordinal() );
		tabCheq.setTamColuna( 90, COLS_CHEQ.DTVENCTO.ordinal() );
		tabCheq.setTamColuna( 70, COLS_CHEQ.NUMCHEQ.ordinal() );
		tabCheq.setTamColuna( 200, COLS_CHEQ.NOMEFAVCHEQ.ordinal() );
		tabCheq.setTamColuna( 20, COLS_CHEQ.SITCHEQ.ordinal() );
		tabCheq.setTamColuna( 90, COLS_CHEQ.VLRCHEQ.ordinal() );
		tabCheq.setTamColuna( 80, COLS_CHEQ.NUMCONTA.ordinal() );
		tabCheq.setTamColuna( 250, COLS_CHEQ.HISTCHEQ.ordinal() );

		tabCheq.addTabelaEditListener( this );
		btExeccheq.addActionListener( this );
		btSelTudoCheq.addActionListener( this );
		btSelNadaCheq.addActionListener( this );
		btImpCheq.addActionListener( this );

		btExeccheq.setFocusable( false );
		
		
	}

	private synchronized void marcarTodosPag() {
		try {
			carregandoTabela = true; // Evitar execução circular
			for ( int i=0; i < tabPagar.getNumLinhas(); i++ ) {
				tabPagar.setValor( new Boolean( true ), i, COLS_PAG.SEL.ordinal() );
			}
		}
		finally {
			carregandoTabela = false;
		}
		calcTotaisPag();
	}
	
	private synchronized void desmarcarTodosPag() {
		try {
			carregandoTabela = true;
			for ( int i=0; i < tabPagar.getNumLinhas(); i++ ) {
				tabPagar.setValor( new Boolean( false ), i, COLS_PAG.SEL.ordinal() );
			}
		}
		finally {
			carregandoTabela = false;
		}
		calcTotaisPag();
	}

	private synchronized void marcarTodosCheq() {
		try {
			carregandoTabela = true; // Evitar execução circular
			for ( int i=0; i < tabCheq.getNumLinhas(); i++ ) {
				tabCheq.setValor( new Boolean( true ), i, COLS_CHEQ.SEL.ordinal() );
			}
		}
		finally {
			carregandoTabela = false;
		}
		calcTotaisCheq();
	}
	
	private synchronized void desmarcarTodosCheq() {
		try {
			carregandoTabela = true;
			for ( int i=0; i < tabCheq.getNumLinhas(); i++ ) {
				tabCheq.setValor( new Boolean( false ), i, COLS_CHEQ.SEL.ordinal() );
			}
		}
		finally {
			carregandoTabela = false;
		}
		calcTotaisCheq();
	}
	
	private synchronized void calcTotaisPag() {
		BigDecimal vlrtotapag = new BigDecimal(0);
		BigDecimal vlrtotsel = new BigDecimal(0);
		BigDecimal vlrapag = new BigDecimal(0);
		//Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VLRAPAGITPAG" ) )
		for ( int i=0; i < tabPagar.getNumLinhas(); i++ ) {
			vlrapag = ConversionFunctions.stringCurrencyToBigDecimal( 
							(String) tabPagar.getValor( i, COLS_PAG.VLRAPAG.ordinal() ) );
			vlrtotapag = vlrtotapag.add( vlrapag  );
			if ( (Boolean) tabPagar.getValor( i, COLS_PAG.SEL.ordinal() ) ) {
				vlrtotsel = vlrtotsel.add( vlrapag );
			}
		}
		txtVlrTotApag.setVlrBigDecimal( vlrtotapag );
		txtVlrTotSelPag.setVlrBigDecimal( vlrtotsel );
	}

	private synchronized void calcTotaisCheq() {
		BigDecimal vlrtotcheq = new BigDecimal(0);
		BigDecimal vlrtotsel = new BigDecimal(0);
		BigDecimal vlrcheq = new BigDecimal(0);
		//Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VLRAPAGITPAG" ) )
		for ( int i=0; i < tabCheq.getNumLinhas(); i++ ) {
			vlrcheq = ConversionFunctions.stringCurrencyToBigDecimal( 
							(String) tabCheq.getValor( i, COLS_CHEQ.VLRCHEQ.ordinal() ) );
			vlrtotcheq = vlrtotcheq.add( vlrcheq  );
			if ( (Boolean) tabCheq.getValor( i, COLS_CHEQ.SEL.ordinal() ) ) {
				vlrtotsel = vlrtotsel.add( vlrcheq );
			}
		}
		txtVlrTotCheq.setVlrBigDecimal( vlrtotcheq );
		txtVlrTotSelCheq.setVlrBigDecimal( vlrtotsel );
	}
	
	private synchronized void carregaGridPagar() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();

		BigDecimal vlrtotapag = new BigDecimal(0);
		BigDecimal vlrtotsel = new BigDecimal(0);
		
		try {

			carregandoTabela = true;

			if ( validaPeriodoPag() ) {
				
				tabPagar.limpa();

				sSQL.append( "SELECT IT.CODPAG, IT.NPARCPAG, IT.DTITPAG, IT.DTVENCITPAG, ");
				sSQL.append( "IT.STATUSITPAG, P.CODFOR, F.RAZFOR, IT.DOCLANCAITPAG, P.CODCOMPRA, ");
				sSQL.append( "IT.VLRAPAGITPAG, IT.NUMCONTA, IT.OBSITPAG, CO.DOCCOMPRA, P.DOCPAG, " );
				sSQL.append( "IT.CODTIPOCOB, ");
				sSQL.append( "( SELECT TC.DESCTIPOCOB FROM FNTIPOCOB TC ");
				sSQL.append( "WHERE TC.CODEMP=IT.CODEMPTC AND TC.CODFILIAL=IT.CODFILIALTC AND ");
				sSQL.append( "TC.CODTIPOCOB=IT.CODTIPOCOB) DESCTIPOCOB " );
				sSQL.append( "FROM CPFORNECED F, FNITPAGAR IT, FNPAGAR P " );
				sSQL.append( "LEFT OUTER JOIN CPCOMPRA CO ON ");
				sSQL.append( "CO.CODCOMPRA=P.CODCOMPRA AND CO.CODEMP=P.CODEMPCP AND ");
				sSQL.append( "CO.CODFILIAL=P.CODFILIALCP " );
				sSQL.append( "WHERE F.CODFOR=P.CODFOR AND ");
				sSQL.append( "F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
				sSQL.append( "IT.CODEMP=P.CODEMP AND IT.CODFILIAL=P.CODFILIAL AND ");
				sSQL.append( "IT.CODPAG=P.CODPAG AND IT.STATUSITPAG='P1' AND ");
				sSQL.append( "IT.DTITPAG BETWEEN ? AND ? AND " );				
				sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
				sSQL.append( "P.CODEMPFR=? AND P.CODFILIALFR=? AND P.CODFOR=? AND " );
				sSQL.append( "NOT EXISTS (SELECT * FROM FNPAGCHEQ PC " );
				sSQL.append( "WHERE PC.CODEMP=IT.CODEMP AND PC.CODFILIAL=IT.CODFILIAL AND ");
				sSQL.append( "PC.CODPAG=IT.CODPAG AND PC.NPARCPAG=IT.NPARCPAG ) " );
				sSQL.append( "ORDER BY IT.DTITPAG, IT.CODPAG, IT.NPARCPAG" );

				try {

					ps = con.prepareStatement( sSQL.toString() );
					ps.setDate( SQL_PARAMS_PAG.DATAINI.ordinal(), Funcoes.dateToSQLDate( dIniPagar ) );
					ps.setDate( SQL_PARAMS_PAG.DATAFIM.ordinal(), Funcoes.dateToSQLDate( dFimPagar ) );
					ps.setInt( SQL_PARAMS_PAG.CODEMP.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( SQL_PARAMS_PAG.CODFILIAL.ordinal(), ListaCampos.getMasterFilial( "FNPAGAR" ) );
					ps.setInt( SQL_PARAMS_PAG.CODEMPFR.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( SQL_PARAMS_PAG.CODFILIALFR.ordinal(), ListaCampos.getMasterFilial( "CPFORNECED" ) );
					ps.setInt( SQL_PARAMS_PAG.CODFOR.ordinal(), txtCodFor.getVlrInteger() );
					rs = ps.executeQuery();
//					System.out.println( sSQL.toString() );

					for ( int i = 0; rs.next(); i++ ) {
						vlrtotapag  = vlrtotapag.add( rs.getBigDecimal( "VLRAPAGITPAG" )  ) ;
						vlrtotsel  = vlrtotsel.add( rs.getBigDecimal( "VLRAPAGITPAG" )  ) ;
						tabPagar.adicLinha();
						tabPagar.setValor( new Boolean(true), i, COLS_PAG.SEL.ordinal() );
						tabPagar.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTITPAG" ) ), i, COLS_PAG.DTEMIT.ordinal() );
						tabPagar.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTVENCITPAG" ) ), i, COLS_PAG.DTVENCTO.ordinal() );
						tabPagar.setValor( rs.getString( "STATUSITPAG" ), i, COLS_PAG.STATUS.ordinal() );
						tabPagar.setValor( rs.getString( "CODCOMPRA" ), i, COLS_PAG.CODCOMPRA.ordinal() );
						tabPagar.setValor( new Integer(rs.getInt( "CODPAG" )), i, COLS_PAG.CODPAG.ordinal() );
						tabPagar.setValor( new Integer(rs.getInt( "NPARCPAG" )), i, COLS_PAG.NPARC.ordinal() );
						tabPagar.setValor( ( rs.getString( "DOCLANCAITPAG" ) != null ? rs.getString( "DOCLANCAITPAG" ) : 
							   ( rs.getString( "DOCPAG" ) != null ? rs.getString( "DOCPAG" ) + "/" + rs.getString( "NPARCPAG" ) : "" ) ), i, COLS_PAG.DOCLANCA.ordinal() );
						tabPagar.setValor( rs.getString( "DOCCOMPRA" ), i, COLS_PAG.DOCCOMPRA.ordinal() );
						tabPagar.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VLRAPAGITPAG" ) ), i, COLS_PAG.VLRAPAG.ordinal() );
						tabPagar.setValor( rs.getString( "NUMCONTA" ), i, COLS_PAG.NUMCONTA.ordinal() );
						tabPagar.setValor( rs.getString( "CODTIPOCOB" ), i, COLS_PAG.CODTIPOCOB.ordinal() );
						tabPagar.setValor( rs.getString( "DESCTIPOCOB" ), i, COLS_PAG.DESCTIPOCOB.ordinal() );
						tabPagar.setValor( rs.getString( "OBSITPAG" ), i, COLS_PAG.HISTPAG.ordinal() );
					}

					rs.close();
					ps.close();

					txtVlrTotApag.setVlrBigDecimal( vlrtotapag );
					txtVlrTotSelPag.setVlrBigDecimal( vlrtotsel );

					con.commit();
				} catch ( SQLException err ) {
					err.printStackTrace();
					Funcoes.mensagemErro( this, "Erro ao montar a tabela de pagamentos!\n" + err.getMessage(), true, con, err );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			carregandoTabela = false;
		}
		
	}

	private boolean validaPeriodoPag() {

		boolean bRetorno = false;

		if ( "".equals(txtCodFor.getText())) {
			txtCodFor.requestFocus();
		}
		
		if ( txtDatainiPagar.getText().trim().length() < 10 ) {

			Funcoes.mensagemInforma( this, "Data inicial inválida!" );
		}
		else if ( txtDatafimPagar.getText().trim().length() < 10 ) {

			Funcoes.mensagemInforma( this, "Data final inválida!" );
		}
		else if ( txtDatafimPagar.getVlrDate().before( txtDatainiPagar.getVlrDate() ) ) {

			Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
		}
		else {

			dIniPagar = txtDatainiPagar.getVlrDate();
			dFimPagar = txtDatafimPagar.getVlrDate();
			bRetorno = true;
		}

		return bRetorno;
	}

	private boolean validaPeriodoCheq() {

		boolean bRetorno = false;

		if ( txtDatainiCheq.getText().trim().length() < 10 ) {

			Funcoes.mensagemInforma( this, "Data inicial inválida!" );
		}
		else if ( txtDatafimCheq.getText().trim().length() < 10 ) {

			Funcoes.mensagemInforma( this, "Data final inválida!" );
		}
		else if ( txtDatafimCheq.getVlrDate().before( txtDatainiCheq.getVlrDate() ) ) {

			Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
		}
		else {

			dIniCheq = txtDatainiCheq.getVlrDate();
			dFimCheq = txtDatafimCheq.getVlrDate();
			bRetorno = true;
		}

		return bRetorno;
	}
	
	private synchronized void carregaGridCheq() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		BigDecimal vlrtotcheq = new BigDecimal(0);
		BigDecimal vlrtotsel = new BigDecimal(0);
		
		try {

			carregandoTabela = true;

			if ( validaPeriodoCheq() ) {
				
				tabCheq.limpa();
				sSQL.append( "SELECT CH.SEQCHEQ, CH.DTEMITCHEQ, CH.DTVENCTOCHEQ, CH.NUMCHEQ, ");
				sSQL.append( "CH.NOMEFAVCHEQ, CH.SITCHEQ, CH.VLRCHEQ, CH.CONTACHEQ, CH.HISTCHEQ ");
				sSQL.append( "FROM FNCHEQUE CH ");
				sSQL.append( "WHERE CH.CODEMP=? AND CH.CODFILIAL=? AND " );
				sSQL.append( "CH.DTEMITCHEQ BETWEEN ? AND ? AND CH.TIPOCHEQ='PF' " );				
				sSQL.append( "ORDER BY CH.DTEMITCHEQ, CH.SEQCHEQ" );

				try {

					ps = con.prepareStatement( sSQL.toString() );
					ps.setInt( SQL_PARAMS_CHEQ.CODEMP.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( SQL_PARAMS_CHEQ.CODFILIAL.ordinal(), ListaCampos.getMasterFilial( "FNPAGAR" ) );
					ps.setDate( SQL_PARAMS_CHEQ.DATAINI.ordinal(), Funcoes.dateToSQLDate( dIniCheq ) );
					ps.setDate( SQL_PARAMS_CHEQ.DATAFIM.ordinal(), Funcoes.dateToSQLDate( dFimCheq ) );
					rs = ps.executeQuery();
//					System.out.println( sSQL.toString() );

					for ( int i = 0; rs.next(); i++ ) {
						vlrtotcheq  = vlrtotcheq.add( rs.getBigDecimal( "VLRCHEQ" )  ) ;
						vlrtotsel  = vlrtotsel.add( rs.getBigDecimal( "VLRCHEQ" )  ) ;
						tabCheq.adicLinha();
						tabCheq.setValor( new Boolean(true), i, COLS_CHEQ.SEL.ordinal() );
						tabCheq.setValor( new Integer(rs.getInt( "SEQCHEQ" )), i, COLS_CHEQ.SEQ.ordinal() );
						tabCheq.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTEMITCHEQ" ) ), i, COLS_CHEQ.DTEMIT.ordinal() );
						tabCheq.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTVENCTOCHEQ" ) ), i, COLS_CHEQ.DTVENCTO.ordinal() );
						tabCheq.setValor( new Integer(rs.getInt( "NUMCHEQ" )), i, COLS_CHEQ.NUMCHEQ.ordinal() );
						tabCheq.setValor( rs.getString( "NOMEFAVCHEQ" ), i, COLS_CHEQ.NOMEFAVCHEQ.ordinal() );
						tabCheq.setValor( rs.getString( "SITCHEQ" ), i, COLS_CHEQ.SITCHEQ.ordinal() );
						tabCheq.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VLRCHEQ" ) ), i, COLS_CHEQ.VLRCHEQ.ordinal() );
						tabCheq.setValor( rs.getString( "CONTACHEQ" ), i, COLS_CHEQ.NUMCONTA.ordinal() );
						tabCheq.setValor( rs.getString( "HISTCHEQ" ), i, COLS_CHEQ.HISTCHEQ.ordinal() );
					}

					rs.close();
					ps.close();

					txtVlrTotCheq.setVlrBigDecimal( vlrtotcheq );
					txtVlrTotSelCheq.setVlrBigDecimal( vlrtotsel );

					con.commit();
				} catch ( SQLException err ) {
					err.printStackTrace();
					Funcoes.mensagemErro( this, "Erro ao montar a tabela de cheques!\n" + err.getMessage(), true, con, err );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			carregandoTabela = false;
		}
		
	}

	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btExecpagar ) {
			carregaGridPagar();
		}
		else if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btSelTudoPag ) {
			marcarTodosPag();
		}
		else if ( evt.getSource() == btSelNadaPag ) {
			desmarcarTodosPag();
		}
		else if ( evt.getSource() == btGerarPag ) {
			gerar();		
		} 
		else if ( evt.getSource() == btSelTudoCheq ) {
			marcarTodosCheq();
		}
		else if ( evt.getSource() == btSelNadaCheq ) {
			desmarcarTodosCheq();
		}
		else if ( evt.getSource() == btImpCheq ) {
			imprimir();		
		} 
		else if ( evt.getSource() == btExeccheq ) {
			carregaGridCheq();
		}

	}

    private void gerar() {
    	LinkedList<Vector<Object>> listapagar = new LinkedList<Vector<Object>>();
    	listapagar = getListapagar( listapagar );
    	if (validaListapagar( listapagar ) ) {
    		if ( Funcoes.mensagemConfirma( this, "Executar a geração do cheque?" )==JOptionPane.YES_OPTION) {
    			gerarCheque( listapagar );
    		}
    	}
    }

    private void imprimir() {
    	LinkedList<Vector<Object>> listacheq = new LinkedList<Vector<Object>>();
    	listacheq = getListacheq( listacheq );
    	if (validaListacheq( listacheq ) ) {
    		if ( Funcoes.mensagemConfirma( this, "Imprimir cheques?" )==JOptionPane.YES_OPTION) {
    			imprimirCheque( listacheq );
    		}
    	}
    }
    
    private int execSqlInsertPagcheq(LinkedList<Vector<Object>> listapagar, int seqcheq) throws SQLException {
    	StringBuffer sqlins = new StringBuffer();
    	PreparedStatement ps = null;
    	int count = 0;
    	
    	sqlins.append( "INSERT INTO FNPAGCHEQ ( CODEMP, CODFILIAL, CODPAG, NPARCPAG, " );
    	sqlins.append( "CODEMPCH, CODFILIALCH, SEQCHEQ ) " );
    	sqlins.append( "VALUES ( ?, ?, ?, ?, ?, ?, ?)" );
    	
    	for (int i=0; i<listapagar.size(); i++) {
    		ps = con.prepareStatement( sqlins.toString() );
    		ps.setInt( 1, Aplicativo.iCodEmp );
    		ps.setInt( 2, ListaCampos.getMasterFilial( "FNITPAGAR" ));
    		ps.setInt( 3, (Integer) ( (Vector<Object>) listapagar.get( i ) ).elementAt( COLS_PAG.CODPAG.ordinal() ) );
    		ps.setInt( 4, (Integer) ( (Vector<Object>) listapagar.get( i ) ).elementAt( COLS_PAG.NPARC.ordinal() ) );
    		ps.setInt( 5, Aplicativo.iCodEmp );
    		ps.setInt( 6, ListaCampos.getMasterFilial( "FNCHEQUE" ));
    		ps.setInt( 7, seqcheq );
    	}
    	
    	count += ps.executeUpdate();
    	return count;
    }
    
    private int execSqlInsertCheque( int codfor, String numconta, int seqcheq, BigDecimal vlrcheque ) throws SQLException {
    	StringBuffer sqlins = new StringBuffer();
    	PreparedStatement ps = null;
    	
    	StringBuffer histcheq = new StringBuffer( "'CHEQUE DE PAGAMENTO DE FORNECEDOR: " );
    	   histcheq.append( codfor );
    	   histcheq.append( " - '|| " );
    	// Não estão todos os campos da tabela cheque no comando INSERT, 
    	// alguns valores são inseridos por padrão.
    	
		sqlins.append( "INSERT INTO FNCHEQUE ( " );
		sqlins.append( "CODEMP, CODFILIAL, CODEMPBO, CODFILIALBO, CODBANC, SEQCHEQ, NUMCHEQ, ");
		sqlins.append( "AGENCIACHEQ, CONTACHEQ, NOMEEMITCHEQ,  NOMEFAVCHEQ, ");
		sqlins.append( "VLRCHEQ,  HISTCHEQ, CNPJEMITCHEQ, CPFEMITCHEQ, DDDEMITCHEQ, FONEEMITCHEQ, ");
		sqlins.append( "CNPJFAVCHEQ, CPFFAVCHEQ ) ");
		sqlins.append( "SELECT ");
		sqlins.append( Aplicativo.iCodEmp );
		sqlins.append(" CODEMP, ");
		sqlins.append( ListaCampos.getMasterFilial( "FNCHEQUE" ) );
		sqlins.append( " CODFILIAL, ");
		sqlins.append( "CT.CODEMPBO, CT.CODFILIALBO, CT.CODBANCO CODBANC, ");
		sqlins.append( seqcheq );
		sqlins.append( " SEQCHEQ, 0 NUMCHEQ, CT.AGENCIACONTA AGENCIACHEQ, '");
		sqlins.append( numconta.trim() );
		sqlins.append( "' CONTACHEQ, F.RAZFILIAL NOMEEMITCHEQ, FR.RAZFOR NOMEFAVCHEQ, ");
		sqlins.append( vlrcheque );
		sqlins.append( " VLRCHEQ, ");
		sqlins.append( histcheq.toString() );
		sqlins.append(" FR.RAZFOR  HISTCHEQ, F.CNPJFILIAL CNPJEMITCHEQ, '' CPFEMITCHEQ, ");
		sqlins.append( "F.DDDFILIAL DDDEMITCHEQ, SUBSTRING( F.FONEFILIAL FROM 1 FOR 8) FONEEMITCHEQ, ");
		sqlins.append( "FR.CNPJFOR CNPJFAVCHEQ, FR.CPFFOR CPFFAVCHEQ ");
		sqlins.append( "FROM SGFILIAL F, CPFORNECED FR, FNCONTA CT ");
		sqlins.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND ");
		sqlins.append( "FR.CODEMP=? AND FR.CODFILIAL=? AND FR.CODFOR=? AND " );
		sqlins.append( "CT.CODEMP=? AND CT.CODFILIAL=? AND CT.NUMCONTA=? ");
		
		ps = con.prepareStatement( sqlins.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGFILIAL" ) );
		ps.setInt( 3, Aplicativo.iCodEmp );
		ps.setInt( 4, ListaCampos.getMasterFilial( "CPFORNECED" ) );
		ps.setInt( 5, codfor );
		ps.setInt( 6, Aplicativo.iCodEmp );
		ps.setInt( 7, ListaCampos.getMasterFilial( "FNCONTA" ) );
		ps.setString( 8, numconta );
    	return ps.executeUpdate(); 
    }
    
    private String getNumconta(LinkedList<Vector<Object>> listapagar) {
    	String numconta = "";
    	for ( int i=0; i<listapagar.size(); i++ ) {
    		if ( (Boolean) ( (Vector<Object>) listapagar.get( i ) ).elementAt( COLS_PAG.SEL.ordinal() ) ) {
    			numconta = ( String ) ( (Vector<Object>) listapagar.get( i ) ).elementAt( COLS_PAG.NUMCONTA.ordinal() ) ;
    		}
    	}
    	return numconta;
    }
    
    private int getSeqcheque() throws SQLException {
    	int seqcheq = 1;
    	String sqlquery = "SELECT COALESCE(NROSEQ,0)+1 NROSEQ FROM SGSEQUENCIA WHERE CODEMP=? AND CODFILIAL=? AND SGTAB=?";
    	String sqlinsert = "INSERT INTO SGSEQUENCIA (CODEMP, CODFILIAL, SGTAB, NROSEQ) VALUES (?,?,?,?)";
    	String sqlupdate = "UPDATE SGSEQUENCIA SET NROSEQ=? WHERE CODEMP=? AND CODFILIAL=? AND SGTAB=?";
    	PreparedStatement ps = con.prepareStatement( sqlquery );
    	ps.setInt( 1, Aplicativo.iCodEmp );
    	ps.setInt( 2, ListaCampos.getMasterFilial( "FNCHEQUE" ));
    	ps.setString( 3, "CH" );
    	ResultSet rs =  ps.executeQuery();
    	if (rs.next()) {
    		seqcheq = rs.getInt( "NROSEQ" );
    		ps =  con.prepareStatement( sqlupdate );
    		ps.setInt( 1, seqcheq );
    		ps.setInt( 2, Aplicativo.iCodEmp );
    		ps.setInt( 3, ListaCampos.getMasterFilial( "FNCHEQUE" ) );
    		ps.setString( 4, "CH" );
    		ps.executeUpdate();
    	} else {
    		ps = con.prepareStatement( sqlinsert );
    		ps.setInt( 1, Aplicativo.iCodEmp );
    		ps.setInt( 2, ListaCampos.getMasterFilial( "FNCHEQUE" ) );
    		ps.setString( 3, "CH" );
    		ps.setInt( 4, seqcheq );
    		ps.executeUpdate();
    	}
    	return seqcheq;
    }
    
    private void gerarCheque(LinkedList<Vector<Object>> listapagar) {
    	PreparedStatement ps = null;
    	String numconta = getNumconta(listapagar);
    	BigDecimal vlrcheque = txtVlrTotSelPag.getVlrBigDecimal();
    	int seqcheq = 0;
    	try {
    		seqcheq = getSeqcheque();
    		execSqlInsertCheque( txtCodFor.getVlrInteger() , numconta, seqcheq, vlrcheque );
    		execSqlInsertPagcheq( listapagar, seqcheq );
//    		ps.executeUpdate();
        	con.commit();
        	carregaGridPagar();
        	tabCheq.requestFocus();
        	carregaGridCheq();
    	} catch (SQLException e) {
    		try {
    			con.rollback();
    		} catch ( SQLException err ) {
        		Funcoes.mensagemErro( this, "Erro executando rollback!\n" + err.getMessage() );
    		}
    		Funcoes.mensagemErro( this, "Erro executando inserção do cheque!\n" + e.getMessage() );
    	}
    }

    private void imprimirCheque(LinkedList<Vector<Object>> listacheq) {
    	PreparedStatement ps = null;
  //  	String numconta = getNumconta(listapagar);
    	BigDecimal vlrcheque = txtVlrTotSelCheq.getVlrBigDecimal();
    	int seqcheq = 0;
    	try {
//    		seqcheq = getSeqcheque();
 ///   		execSqlInsertCheque( txtCodFor.getVlrInteger() , numconta, seqcheq, vlrcheque );
//    		execSqlInsertPagcheq( listapagar, seqcheq );
//    		ps.executeUpdate();
        	con.commit();
    //    	carregaGridPagar();
    	} catch (SQLException e) {
    		try {
    			con.rollback();
    		} catch ( SQLException err ) {
        		Funcoes.mensagemErro( this, "Erro executando rollback!\n" + err.getMessage() );
    		}
    		Funcoes.mensagemErro( this, "Erro executando impressão de cheques!\n" + e.getMessage() );
    	}
    }
    
    private boolean validaListapagar(LinkedList<Vector<Object>> listapagar) {
    	boolean result = false;
    	Vector<Object> item = null;
    	String numconta = "";
    	if ( listapagar.size()>0 ) {
    		for ( int i=0; i<listapagar.size(); i++ ) {
    			item = listapagar.get( i );
    			if ( "".equals( item.elementAt( COLS_PAG.NUMCONTA.ordinal() ) ) ) {
    				Funcoes.mensagemInforma( this, "Um item selecionado não possui conta definida!" );
    				break;
    			} else if ( "".equals( item.elementAt( COLS_PAG.NUMCONTA.ordinal() ) ) ) {
    				if ( "".equals( numconta ) ) {
    					numconta = (String) item.elementAt( COLS_PAG.NUMCONTA.ordinal() );
    				} else if ( numconta.equals( item.elementAt( COLS_PAG.NUMCONTA.ordinal() ) ) ) {
    					Funcoes.mensagemInforma( this, "Um item selecionado possui conta diferente dos demais!" );
    					break;
    				}
    			} else if ("".equals( item.elementAt( COLS_PAG.CODTIPOCOB.ordinal() ) )) {
    				Funcoes.mensagemInforma( this, "Um item selecionado não possui tipo de cobrança definido!" );
    				break;
    			} else {
    				result = true;
    			}
    		}
    	} else {
    		Funcoes.mensagemInforma( this, "Selecione algum item na lista!" );
    	}
    	return result;
    }

    private boolean validaListacheq(LinkedList<Vector<Object>> listacheq) {
    	boolean result = false;
    	Vector<Object> item = null;
    	String numconta = "";
    	if ( listacheq.size()>0 ) {
    		for ( int i=0; i<listacheq.size(); i++ ) {
    			item = listacheq.get( i );
    			if ( "".equals( item.elementAt( COLS_CHEQ.NUMCONTA.ordinal() ) ) ) {
    				Funcoes.mensagemInforma( this, "Um item selecionado não possui conta definida!" );
    				break;
    			} else if ( "".equals( item.elementAt( COLS_CHEQ.NUMCONTA.ordinal() ) ) ) {
    				if ( "".equals( numconta ) ) {
    					numconta = (String) item.elementAt( COLS_CHEQ.NUMCONTA.ordinal() );
    				} else if ( numconta.equals( item.elementAt( COLS_CHEQ.NUMCONTA.ordinal() ) ) ) {
    					Funcoes.mensagemInforma( this, "Um item selecionado possui conta diferente dos demais!" );
    					break;
    				}
    			} else {
    				result = true;
    			}
    		}
    	} else {
    		Funcoes.mensagemInforma( this, "Selecione algum item na lista!" );
    	}
    	return result;
    }
    
    private LinkedList<Vector<Object>> getListapagar(LinkedList<Vector<Object>> listapagar) {
    	Vector<Object> item = null;
    	for (int i=0; i<tabPagar.getNumLinhas(); i++) {
    		item = (Vector<Object>) tabPagar.getLinha( i );
    		if ( (Boolean) item.elementAt( COLS_PAG.SEL.ordinal() ) ) {
    			listapagar.add( item );
    		}
    	}
    	return listapagar;
    }

    private LinkedList<Vector<Object>> getListacheq(LinkedList<Vector<Object>> listacheq) {
    	Vector<Object> item = null;
    	for (int i=0; i<tabCheq.getNumLinhas(); i++) {
    		item = (Vector<Object>) tabCheq.getLinha( i );
    		if ( (Boolean) item.elementAt( COLS_CHEQ.SEL.ordinal() ) ) {
    			listacheq.add( item );
    		}
    	}
    	return listacheq;
    }
    
	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcFor.setConexao( cn );
	}

	public void valorAlterado( TabelaEditEvent evt ) {

		if ( (evt.getTabela()==tabPagar) && 
				(evt.getTabela().getColunaEditada()==COLS_PAG.SEL.ordinal()) && 
					(!carregandoTabela) ) {
			calcTotaisPag();
		} else if ( (evt.getTabela()==tabCheq) && 
				(evt.getTabela().getColunaEditada()==COLS_CHEQ.SEL.ordinal()) && 
					(!carregandoTabela) ) {
			calcTotaisCheq();
		}
		
	}

}
