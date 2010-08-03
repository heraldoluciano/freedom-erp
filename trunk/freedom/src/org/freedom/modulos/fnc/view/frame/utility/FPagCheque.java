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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

	private ImageIcon imgAberto = Icone.novo( "clVencido.gif" );

	private ImageIcon imgSelecionado = Icone.novo( "clPago.gif" );
	
	private JButtonPad btSelTudo = new JButtonPad( Icone.novo( "btTudo.gif" ) );

	private JButtonPad btSelNada = new JButtonPad( Icone.novo( "btNada.gif" ) );

	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.gif" ) );	

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL );

    private JLabelPad lbVlrapag = new JLabelPad( "Total a pagar", imgAberto, SwingConstants.LEFT );

	private JLabelPad lbSelecionado = new JLabelPad( "Total selecionado", imgSelecionado, SwingConstants.LEFT );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTabbedPanePad tpn = new JTabbedPanePad();

	private JPanelPad pnTabPagar = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesPagar = new JPanelPad( 40, 210 );

	private JPanelPad pinPagar = new JPanelPad( 500, 60 );

	private JPanelPad pnPagar = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabPagar = new JTablePad();

	private JScrollPane spnPagar = new JScrollPane( tabPagar );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlrTotApag = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrTotSel = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtDatainiPagar = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimPagar = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCNPJFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0);

	private JButtonPad btExecutar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private ListaCampos lcFor = new ListaCampos( this );

	private Date dIniPagar = null;

	private Date dFimPagar = null;
	
	private boolean carregandoTabela = false; 
	
	private static String LABEL_SEL = "Sel.";
	
	private enum COLS_PAG {SEL, DTEMIT, DTVENCTO, STATUS, CODCOMPRA, CODPAG, NPARC, DOCLANCA, DOCCOMPRA, 
		VLRAPAG, NUMCONTA, CODTIPOCOB, DESCTIPOCOB, HISTPAG};

	private enum SQL_PARAMS {NONE, DATAINI, DATAFIM, CODEMP, CODFILIAL, CODEMPFR, CODFILIALFR, CODFOR };

	enum COLS_GERAR {CODPAG, NPARCPAG};
	
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
		lbSelecionado.setBounds( 140, 0, 130, 17 );
		txtVlrTotSel.setBounds( 140, 18, 130, 18 );

		pnLegenda.add( lbVlrapag );
		pnLegenda.add( txtVlrTotApag );
		pnLegenda.add( lbSelecionado );
		pnLegenda.add( txtVlrTotSel );

		txtVlrTotApag.setSoLeitura( true );
		txtVlrTotSel.setSoLeitura( true );

		pnRod.setBorder( BorderFactory.createEtchedBorder() );
		pnRod.setPreferredSize( new Dimension( 600, 42 ) );

		pnRod.add( btSair, BorderLayout.EAST );
		pnRod.add( pnLegenda, BorderLayout.WEST );

		btSair.addActionListener( this );

		// Pagar

		tpn.addTab( "Pagar", pnPagar );

		btSelTudo.setToolTipText( "Marcar todos" );
		btSelNada.setToolTipText( "Demarcar todos" );
		btGerar.setToolTipText( "Emitir cheque" );
		btExecutar.setToolTipText( "Listar pagamentos" );

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
		pinPagar.adic( btExecutar, 556, 10, 30, 30 );

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

		pinBotoesPagar.adic( btSelTudo, 5, 10, 30, 30 );
		pinBotoesPagar.adic( btSelNada, 5, 40, 30, 30 );
		pinBotoesPagar.adic( btGerar, 5, 70, 30, 30 );

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
		btExecutar.addActionListener( this );
		btSelTudo.addActionListener( this );
		btSelNada.addActionListener( this );
		btGerar.addActionListener( this );
//		tpn.addChangeListener( this );
		
		btExecutar.setFocusable( false );
		//btExecutar.setSelected( false );
		//btExecutar.setDefaultCapable( false );
		//btExecutar.setRequestFocusEnabled( false );

		tabPagar.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getSource() == tabPagar && mevt.getClickCount() == 2 ) {
					
				}
			}
		} );

	}

	private synchronized void marcarTodos() {
		try {
			carregandoTabela = true; // Evitar execução circular
			for ( int i=0; i < tabPagar.getNumLinhas(); i++ ) {
				tabPagar.setValor( new Boolean( true ), i, COLS_PAG.SEL.ordinal() );
			}
		}
		finally {
			carregandoTabela = false;
		}
		calcTotais();
	}
	
	private synchronized void desmarcarTodos() {
		try {
			carregandoTabela = true;
			for ( int i=0; i < tabPagar.getNumLinhas(); i++ ) {
				tabPagar.setValor( new Boolean( false ), i, COLS_PAG.SEL.ordinal() );
			}
		}
		finally {
			carregandoTabela = false;
		}
		calcTotais();
	}
	
	private synchronized void calcTotais() {
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
		txtVlrTotSel.setVlrBigDecimal( vlrtotsel );
	}

	private synchronized void carregaGridPagar() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWherePagar = new StringBuffer();
		StringBuffer sWhereStatus = new StringBuffer();

		BigDecimal vlrtotapag = new BigDecimal(0);
		BigDecimal vlrtotsel = new BigDecimal(0);
		
		try {

			carregandoTabela = true;

			if ( validaPeriodo() ) {
				
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
				sSQL.append( "P.CODEMPFR=? AND P.CODFILIALFR=? AND P.CODFOR=? " );
				sSQL.append( "ORDER BY IT.DTITPAG" );

				try {

					ps = con.prepareStatement( sSQL.toString() );
					ps.setDate( SQL_PARAMS.DATAINI.ordinal(), Funcoes.dateToSQLDate( dIniPagar ) );
					ps.setDate( SQL_PARAMS.DATAFIM.ordinal(), Funcoes.dateToSQLDate( dFimPagar ) );
					ps.setInt( SQL_PARAMS.CODEMP.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( SQL_PARAMS.CODFILIAL.ordinal(), ListaCampos.getMasterFilial( "FNPAGAR" ) );
					ps.setInt( SQL_PARAMS.CODEMPFR.ordinal(), Aplicativo.iCodEmp );
					ps.setInt( SQL_PARAMS.CODFILIALFR.ordinal(), ListaCampos.getMasterFilial( "CPFORNECED" ) );
					ps.setInt( SQL_PARAMS.CODFOR.ordinal(), txtCodFor.getVlrInteger() );
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
					txtVlrTotSel.setVlrBigDecimal( vlrtotsel );

					con.commit();
				} catch ( SQLException err ) {
					err.printStackTrace();
					Funcoes.mensagemErro( this, "Erro ao montar a tabela de manutenção!\n" + err.getMessage(), true, con, err );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sWherePagar = null;
			sWhereStatus = null;
			carregandoTabela = false;
		}
		
	}

	private boolean validaPeriodo() {

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


	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btExecutar ) {
			carregaGridPagar();
		}
		else if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btSelTudo ) {
			marcarTodos();
		}
		else if ( evt.getSource() == btSelNada ) {
			desmarcarTodos();
		}
		else if ( evt.getSource() == btGerar ) {
			gerarCheque();		
		}
	}

    private void gerarCheque() {
    	LinkedList<Vector<Object>> listapagar = new LinkedList<Vector<Object>>();
    	listapagar = getListapagar( listapagar );
    	if (validaListapagar( listapagar ) ) {
    		
    	}
    }
    
    private boolean validaListapagar(LinkedList<Vector<Object>> listapagar) {
    	boolean result = false;
    	Vector<Object> item = null;
    	if ( listapagar.size()>0 ) {
    		for ( int i=0; i<listapagar.size(); i++ ) {
    			item = listapagar.get( i );
    			if ( "".equals( item.elementAt( COLS_PAG.NUMCONTA.ordinal() ) ) ) {
    				Funcoes.mensagemInforma( this, "Um item selecionado não possui conta definida!" );
    				break;
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
    
    //@ SuppressWarnings ( "unchecked" )
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

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcFor.setConexao( cn );
	}

	public void valorAlterado( TabelaEditEvent evt ) {

		if ( (evt.getTabela()==tabPagar) && 
				(evt.getTabela().getColunaEditada()==COLS_PAG.SEL.ordinal()) && 
					(!carregandoTabela) ) {
			calcTotais();
		}
		
	}

}
