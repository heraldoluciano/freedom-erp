/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FManutPag.java <BR>
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
 *                    Tela de manutenção de contas a pagar.
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone; // import org.freedom.componentes.ObjetoHistorico;
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

public class FPagCheque extends FFilho implements ActionListener, CarregaListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	// private static final String HISTORICO_PADRAO = "PAGAMENTO REF. A COMPRA: <DOCUMENTO>";

	private ImageIcon imgAberto = Icone.novo( "clVencido.gif" );

	private ImageIcon imgSelecionado = Icone.novo( "clPago.gif" );

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL );

    private JLabelPad lbAberto = new JLabelPad( "Aberto", imgAberto, SwingConstants.LEFT );

	private JLabelPad lbSelecionado = new JLabelPad( "Selecionado", imgSelecionado, SwingConstants.LEFT );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTabbedPanePad tpn = new JTabbedPanePad();

	private JPanelPad pnTabManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesManut = new JPanelPad( 40, 210 );

	private JPanelPad pinManut = new JPanelPad( 500, 100 );

	private JPanelPad pnManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabManut = new JTablePad();

	private JScrollPane spnManut = new JScrollPane( tabManut );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtCNPJForManut = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtPrimCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtUltCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrMaxFat = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtDataMaxFat = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrTotCompr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrTotPago = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrTotAberto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtDataMaxAcum = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtVlrMaxAcum = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtCodPagBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDatainiManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescBancoBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTotalAberto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 0 );

	private JTextFieldPad txtTotalSelecionado = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 0 );

	private JButtonPad btBaixaManut = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btEditManut = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JButtonPad btNovoManut = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btExcluirManut = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btEstManut = new JButtonPad( Icone.novo( "btCancelar.gif" ) );

	private JButtonPad btCancItem = new JButtonPad( Icone.novo( "btCancItem.png" ) );

	private JButtonPad btExecManut = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private ListaCampos lcFor = new ListaCampos( this );

	private Date dIniManut = null;

	private Date dFimManut = null;
	
	private enum TAB_MANUT_COLS {};

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

		lbAberto.setBounds( 5, 0, 90, 17 );
		txtTotalAberto.setBounds( 5, 18, 90, 18 );
		lbSelecionado.setBounds( 200, 0, 90, 17 );
		txtTotalSelecionado.setBounds( 200, 18, 90, 18 );

		pnLegenda.add( lbAberto );
		pnLegenda.add( txtTotalAberto );
		pnLegenda.add( lbSelecionado );
		pnLegenda.add( txtTotalSelecionado );

		txtTotalAberto.setSoLeitura( true );
		txtTotalSelecionado.setSoLeitura( true );

		pnRod.setBorder( BorderFactory.createEtchedBorder() );
		pnRod.setPreferredSize( new Dimension( 600, 42 ) );

		pnRod.add( btSair, BorderLayout.EAST );
		pnRod.add( pnLegenda, BorderLayout.WEST );

		btSair.addActionListener( this );

		// Pagar

		tpn.addTab( "Pagar", pnManut );

		btBaixaManut.setToolTipText( "Baixa" );
		btEditManut.setToolTipText( "Editar" );
		btNovoManut.setToolTipText( "Novo" );
		btExcluirManut.setToolTipText( "Excluir" );
		btCancItem.setToolTipText( "Cancela Item" );
		btExecManut.setToolTipText( "Listar" );

		pnManut.add( pinManut, BorderLayout.NORTH );
		pnTabManut.add( pinBotoesManut, BorderLayout.EAST );
		pnTabManut.add( spnManut, BorderLayout.CENTER );
		pnManut.add( pnTabManut, BorderLayout.CENTER );

		txtDatainiManut.setVlrDate( new Date() );
		txtDatafimManut.setVlrDate( new Date() );
		
		pinManut.adic( new JLabelPad( "Cód.forn." ), 7, 0, 80, 20 );
		pinManut.adic( txtCodFor, 7, 20, 80, 20 );
		pinManut.adic( new JLabelPad( "Razão social do fornecedor" ), 90, 0, 250, 20 );
		pinManut.adic( txtRazFor, 90, 20, 250, 20 );
		
		pinManut.adic( new JLabelPad( "Período" ), 343, 0, 100, 20 );
		pinManut.adic( txtDatainiManut, 343, 20, 100, 20 );
		pinManut.adic( new JLabelPad( "até" ), 446, 0, 100, 20 );
		pinManut.adic( txtDatafimManut, 446, 20, 100, 20 );
		pinManut.adic( btExecManut, 556, 10, 30, 30 );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCNPJForManut, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );

		lcFor.montaSql( false, "FORNECED", "CP" );
		lcFor.setQueryCommit( false );
		lcFor.setReadOnly( true );

		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CodFor" );

		pinBotoesManut.adic( btBaixaManut, 5, 10, 30, 30 );
		pinBotoesManut.adic( btEditManut, 5, 40, 30, 30 );
		pinBotoesManut.adic( btNovoManut, 5, 70, 30, 30 );
		pinBotoesManut.adic( btEstManut, 5, 100, 30, 30 );
		pinBotoesManut.adic( btExcluirManut, 5, 130, 30, 30 );
		pinBotoesManut.adic( btCancItem, 5, 160, 30, 30 );

		tabManut.adicColuna( "" ); // 0
		tabManut.adicColuna( "Vencimento" ); // 1
		tabManut.adicColuna( "Status" ); // 2
		tabManut.adicColuna( "Cód.pag." ); // 5
		tabManut.adicColuna( "Nº parc." ); // 6
		tabManut.adicColuna( "Doc. lanca" ); // 7
		tabManut.adicColuna( "Doc. compra" ); // 8
		tabManut.adicColuna( "Valor parcelamento" ); // 9
		tabManut.adicColuna( "Data pagto." ); // 10
		tabManut.adicColuna( "Valor pago" ); // 11
		tabManut.adicColuna( "Valor desc." ); // 12
		tabManut.adicColuna( "Valor juros" ); // 13
		tabManut.adicColuna( "Valor devolução" ); // 14
		tabManut.adicColuna( "Valor adic" ); // 15
		tabManut.adicColuna( "Valor aberto" ); // 16
		tabManut.adicColuna( "Valor cancelado" ); // 17
		tabManut.adicColuna( "Conta" ); // 18
		tabManut.adicColuna( "Categoria" ); // 19
		tabManut.adicColuna( "Centro de custo" ); // 20
		tabManut.adicColuna( "Tp.Cob." ); // 21
		tabManut.adicColuna( "Descrição do tipo de cobrança" ); // 22
		tabManut.adicColuna( "Observação" ); // 23

		tabManut.setTamColuna( 0, 0 );
		tabManut.setTamColuna( 80, 1 );
		tabManut.setTamColuna( 50, 2 );
		tabManut.setTamColuna( 70, 5 );
		tabManut.setTamColuna( 60, 6 );
		tabManut.setTamColuna( 75, 7 );
		tabManut.setTamColuna( 90, 8 );
		tabManut.setTamColuna( 140, 9 );
		tabManut.setTamColuna( 100, 10 );
		tabManut.setTamColuna( 100, 11 );
		tabManut.setTamColuna( 100, 12 );
		tabManut.setTamColuna( 100, 13 );
		tabManut.setTamColuna( 100, 14 );
		tabManut.setTamColuna( 100, 15 );
		tabManut.setTamColuna( 100, 16 );
		tabManut.setTamColuna( 100, 17 );
		tabManut.setTamColuna( 130, 18 );
		tabManut.setTamColuna( 210, 19 );
		tabManut.setTamColuna( 220, 20 );
		tabManut.setTamColuna( 80, 21 );
		tabManut.setTamColuna( 200, 22 );
		tabManut.setTamColuna( 260, 23 );

		lcFor.addCarregaListener( this );
		btBaixaManut.addActionListener( this );
		btEditManut.addActionListener( this );
		btNovoManut.addActionListener( this );
		btExcluirManut.addActionListener( this );
		btExecManut.addActionListener( this );
		btEstManut.addActionListener( this );
		btCancItem.addActionListener( this );
		tpn.addChangeListener( this );

		tabManut.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getSource() == tabManut && mevt.getClickCount() == 2 ) {
					
				}
			}
		} );

	}


	private void carregaGridManut() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhereManut = new StringBuffer();
		StringBuffer sWhereStatus = new StringBuffer();

		Float bdTotAberto = 0.0f;
		Float bdTotSelecionado = 0.0f;

		try {

			if ( validaPeriodo() ) {

				tabManut.limpa();

				sSQL.append( "SELECT IT.CODPAG, IT.NPARCITPAG, IT.DTITPAG, IT.DTVENCITPAG, ");
				sSQL.append( "IT.STATUSITPAG, P.CODFOR, F.RAZFOR, IT.DOCLANCAITPAG, P.CODCOMPRA, ");
				sSQL.append( "IT.VLRAPAGITPAG, IT.NUMCONTA, IT.OBSITPAG, CO.DOCCOMPRA " );
				sSQL.append( "FROM FNITPAGAR IT, CPFORNECED F, FNPAGAR P " );
				sSQL.append( "LEFT OUTER JOIN CPCOMPRA CO ON ");
				sSQL.append( "CO.CODCOMPRA=P.CODCOMPRA AND CO.CODEMP=P.CODEMPCP AND ");
				sSQL.append( "CO.CODFILIAL=P.CODFILIALCP " );
				sSQL.append( "WHERE P.CODPAG=IT.CODPAG AND F.CODFOR=P.CODFOR AND ");
				sSQL.append( "F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
				sSQL.append( "IT.STATUSITPAG='P1' AND "); 
				sSQL.append( "IT.DTITPAG AND BETWEEN ? AND ? AND " );
				sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
				sSQL.append( "P.CODEMPFR=? AND P.CODFILIALFR=? AND P.CODFOR=? AND " );
				sSQL.append( "IT.CODEMP=P.CODEMP AND IT.CODFILIAL=P.CODFILIAL " );
				sSQL.append( "ORDER BY IT.DTITPAG" );

				try {

					ps = con.prepareStatement( sSQL.toString() );
					ps.setDate( 1, Funcoes.dateToSQLDate( dIniManut ) );
					ps.setDate( 2, Funcoes.dateToSQLDate( dFimManut ) );
					ps.setInt( 3, Aplicativo.iCodEmp );
					ps.setInt( 4, ListaCampos.getMasterFilial( "FNPAGAR" ) );
					ps.setInt( 5, Aplicativo.iCodEmp );
					ps.setInt( 6, ListaCampos.getMasterFilial( "CPFORNECED" ) );
					ps.setInt( 7, txtCodFor.getVlrInteger() );
					rs = ps.executeQuery();

					double bdVlrAPagar = 0.0;

					System.out.println( sSQL.toString() );

					for ( int i = 0; rs.next(); i++ ) {

						tabManut.adicLinha();

						bdVlrAPagar = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ).doubleValue();

						tabManut.setValor( new Boolean(true), i, 0 );
						tabManut.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ), i, 1 );
						tabManut.setValor( rs.getString( "StatusItPag" ), i, 2 );
						tabManut.setValor( rs.getString( "CodPag" ), i, 3 );
						tabManut.setValor( rs.getString( "NParcPag" ), i, 4 );
						tabManut.setValor( ( rs.getString( "DocLancaItPag" ) != null ? rs.getString( "DocLancaItPag" ) : ( rs.getString( "DocPag" ) != null ? rs.getString( "DocPag" ) + "/" + rs.getString( "NParcPag" ) : "" ) ), i, 5 );
						tabManut.setValor( Funcoes.copy( rs.getString( 23 ), 0, 10 ).trim(), i, 6 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrParcItPag" ) ), i, 7 );
						tabManut.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DtPagoItPag" ) ), i, 8 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ), i, 9 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrDescItPag" ) ), i, 10 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrJurosItPag" ) ), i, 11 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrDevItPag" ) ), i, 12 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrAdicItPag" ) ), i, 13 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ), i, 14 );
						tabManut.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VLRCANCITPAG" ) ), i, 15 );
						tabManut.setValor( rs.getString( 13 ) != null ? rs.getString( 13 ) : "", i, 16 );
						tabManut.setValor( rs.getString( 17 ) != null ? rs.getString( 17 ) : "", i, 17 );
						tabManut.setValor( rs.getString( 19 ) != null ? rs.getString( 19 ) : "", i, 18 );
						tabManut.setValor( rs.getString( "CODTIPOCOB" ) != null ? rs.getString( "CODTIPOCOB" ) : "", i, 19 );
						tabManut.setValor( rs.getString( "DESCTIPOCOB" ) != null ? rs.getString( "DESCTIPOCOB" ) : "", i, 20 );
						tabManut.setValor( rs.getString( "ObsItPag" ) != null ? rs.getString( "ObsItPag" ) : "", i, 21 );
					}

					rs.close();
					ps.close();

					txtTotalAberto.setVlrDouble( Funcoes.arredDouble( bdTotAberto.doubleValue(), Aplicativo.casasDecFin ) );
//					txtTotalSelecionado.setVlrDouble( Funcoes.arredDouble( bdTotSelecioando.doubleValue(), Aplicativo.casasDecFin ) );

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
			sWhereManut = null;
			sWhereStatus = null;
		}
	}



	private boolean validaPeriodo() {

		boolean bRetorno = false;

		if ( "".equals(txtCodFor.getText())) {
			txtCodFor.requestFocus();
		}
		
		if ( txtDatainiManut.getText().trim().length() < 10 ) {

			Funcoes.mensagemInforma( this, "Data inicial inválida!" );
		}
		else if ( txtDatafimManut.getText().trim().length() < 10 ) {

			Funcoes.mensagemInforma( this, "Data final inválida!" );
		}
		else if ( txtDatafimManut.getVlrDate().before( txtDatainiManut.getVlrDate() ) ) {

			Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
		}
		else {

			dIniManut = txtDatainiManut.getVlrDate();
			dFimManut = txtDatafimManut.getVlrDate();
			bRetorno = true;
		}

		return bRetorno;
	}



	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcFor ) {

		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btBaixaManut ) {
		}
		else if ( evt.getSource() == btEditManut ) {
		}
		else if ( evt.getSource() == btNovoManut ) {
		}
		else if ( evt.getSource() == btExcluirManut ) {
		}
		else if ( evt.getSource() == btExecManut ) {
			carregaGridManut();
		}
		else if ( evt.getSource() == btEstManut ) {
		}
		else if ( evt.getSource() == btCancItem ) {
			cancelaItem();
		}
	}

	public void stateChanged( ChangeEvent cevt ) {

	}

	private void cancelaItem() {

	}

	private void execCancItem( int codpag, int nparcitpag, String obs ) {

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );

	}

}
