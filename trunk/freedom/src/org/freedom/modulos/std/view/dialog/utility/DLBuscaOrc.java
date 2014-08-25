/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLAdicOrc.java <BR>
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
 *                    Comentários sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.utility;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.exceptions.ExceptionCarregaDados;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPassword;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;
import org.freedom.modulos.std.business.object.VDContrOrc;
import org.freedom.modulos.std.business.object.VDContrato;
import org.freedom.modulos.std.business.object.VDItContrato;
import org.freedom.modulos.std.dao.DAOBuscaOrc;
import org.freedom.modulos.std.dao.DAOBuscaOrc.COL_PREFS;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;
import org.freedom.modulos.std.view.frame.utility.FPesquisaOrc;

public class DLBuscaOrc extends FDialogo implements ActionListener, RadioGroupListener, CarregaListener, MouseListener {

	public enum GRID_ITENS { SEL, CODITORC, CODPROD, DESCPROD, QTDITORC, QTDAFATITORC, QTDFATITORC
		, QTDFINALPRODITORC, CODALMOX, CODLOTE, SALDOPROD, PRECO, DESC, VLRLIQ, TPAGR, PAI, VLRAGRP, CODORC, USALOTE, CODOP };

	private static final long serialVersionUID = 1L;

	private JTablePad tabitorc = new JTablePad();

	private JScrollPane spnTab = new JScrollPane( tabitorc );

	private JTablePad tabOrc = new JTablePad();

	private JScrollPane spnTabOrc = new JScrollPane( tabOrc );

	private JPanelPad pinCab = new JPanelPad( 0, 65 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnSubRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinRod = new JPanelPad( 480, 55 );

	private JPanelPad pinSair = new JPanelPad( 120, 45 );

	private JPanelPad pinBtSel = new JPanelPad( 40, 110 );

	private JPanelPad pinBtSelOrc = new JPanelPad( 40, 110 );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnTabOrc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCliTab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDtOrc = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtVal = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeConv = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtVlrProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrLiq = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JRadioGroup<?, ?> rgBusca = null;

	private JButtonPad btBusca = new JButtonPad( "Buscar", Icone.novo( "btPesquisa.png" ) );

	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.png" ) );

	private JButtonPad btTudoOrc = new JButtonPad( Icone.novo( "btTudo.png" ) );

	private JButtonPad btEditQtd = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JButtonPad btNadaOrc = new JButtonPad( Icone.novo( "btNada.png" ) );

	private JButtonPad btTudoIt = new JButtonPad( Icone.novo( "btTudo.png" ) );

	private JButtonPad btNadaIt = new JButtonPad( Icone.novo( "btNada.png" ) );

	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.png" ) );

	private JButtonPad btAgruparItens = new JButtonPad( Icone.novo( "btAdic2.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.png" ) );

	private JButtonPad btResetOrc = new JButtonPad( Icone.novo( "btReset.png" ) );

	private JButtonPad btResetItOrc = new JButtonPad( Icone.novo( "btReset.png" ) );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcConv = new ListaCampos( this, "CV" );

	private ListaCampos lcOrc = new ListaCampos( this, "OC" );

	private Vector<Object> vValidos = new Vector<Object>();

	private final String sTipoVenda;

	private org.freedom.modulos.std.view.frame.crud.detail.FVenda vendaSTD = null;

	private org.freedom.modulos.pdv.FVenda vendaPDV = null;

	private JLabelPad lbNomeCli = new JLabelPad( "Razão social do cliente" );

	private JLabelPad lbNomeConv = new JLabelPad( "Nome do conveniado" );

	private JLabelPad lbCodCli = new JLabelPad( "Cód.Cli." );

	private JLabelPad lbCodConv = new JLabelPad( "Cód.Conv." );

	private Object vd = null;

	private Map<String, Object> prefs;

	private DAOBuscaOrc daobusca;

	private int casasDec = 2;
	private int casasDecFin = 2;
	private int casasDecPre = 2;

	private String origem;
	
	private boolean contingencia = false;

	public DLBuscaOrc(Object vdparam, String tipo, String origem, boolean contingencia) {
		super();
		this.origem = origem;
		this.contingencia = contingencia;
		sTipoVenda = tipo;
		vd = vdparam;
		casasDec = Aplicativo.casasDec;
		casasDecFin = Aplicativo.casasDecFin;
		casasDecPre = Aplicativo.casasDecPre;

	}

	private void montaTela(){

		int posIniItens = 0; //Posição inicial da barra de ferramentas dos itens.

		if ("Venda".equals( origem )) {
			if ( sTipoVenda.equals( "V" ) && vd instanceof org.freedom.modulos.std.view.frame.crud.detail.FVenda ) {
				vendaSTD = (org.freedom.modulos.std.view.frame.crud.detail.FVenda) vd;
			}
			else if ( sTipoVenda.equals( "E" ) ) {
				vendaPDV = (org.freedom.modulos.pdv.FVenda) vd;
			}
			else if ( vd instanceof FPesquisaOrc ) {
				vendaSTD = null;
			}
		}

		setTitulo( "Nova venda de orçamento", this.getClass().getName() );
		setAtribos( 870, 480 );

		ocultaConveniado();

		c.setLayout( new BorderLayout() );

		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pnCli, BorderLayout.CENTER );
		c.add( pinCab, BorderLayout.NORTH );

		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "L" );
		vVals.addElement( "O" );
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "Cliente" );
		vLabs.addElement( "Conveniado" );
		rgBusca = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );

		pinCab.adic( new JLabelPad( "Nº orçamento" ), 7, 5, 90, 20 );
		pinCab.adic( txtCodOrc, 7, 25, 90, 20 );
		pinCab.adic( lbCodCli, 100, 5, 70, 20 );
		pinCab.adic( txtCodCli, 100, 25, 70, 20 );
		pinCab.adic( lbNomeCli, 173, 5, 245, 20 );
		pinCab.adic( txtNomeCli, 173, 25, 245, 20 );

		pinCab.adic( lbCodConv, 100, 5, 70, 20 );
		pinCab.adic( txtCodConv, 100, 25, 70, 20 );
		pinCab.adic( lbNomeConv, 173, 5, 245, 20 );
		pinCab.adic( txtNomeConv, 173, 25, 245, 20 );

		pinCab.adic( new JLabelPad( "Buscar por:" ), 421, 5, 200, 20 );

		pinCab.adic( rgBusca, 421, 25, 210, 31 );

		pinCab.adic( btBusca, 632, 25, 100, 30 );

		pnRod.setPreferredSize( new Dimension( 600, 50 ) );

		pnSubRod.setPreferredSize( new Dimension( 600, 50 ) );
		pnRod.add( pnSubRod, BorderLayout.SOUTH );

		pinSair.tiraBorda();
		pinSair.adic( btSair, 10, 10, 100, 30 );
		btSair.setPreferredSize( new Dimension( 120, 30 ) );

		pnSubRod.add( pinSair, BorderLayout.EAST );
		pnSubRod.add( pinRod, BorderLayout.CENTER );

		pinRod.tiraBorda();
		pinRod.adic( new JLabelPad( "Vlr.bruto" ), 7, 0, 100, 20 );
		pinRod.adic( txtVlrProd, 7, 20, 100, 20 );
		pinRod.adic( new JLabelPad( "Vlr.desc." ), 110, 0, 97, 20 );
		pinRod.adic( txtVlrDesc, 110, 20, 97, 20 );
		pinRod.adic( new JLabelPad( "Vlr.liq." ), 210, 0, 97, 20 );
		pinRod.adic( txtVlrLiq, 210, 20, 97, 20 );

		pnTabOrc.setPreferredSize( new Dimension( 600, 133 ) );

		pnTabOrc.add( spnTabOrc, BorderLayout.CENTER );
		pnTabOrc.add( pinBtSelOrc, BorderLayout.EAST );

		pinBtSelOrc.adic( btTudoOrc, 3, 3, 30, 30 );
		pinBtSelOrc.adic( btNadaOrc, 3, 34, 30, 30 );
		pinBtSelOrc.adic( btResetOrc, 3, 65, 30, 30 );
		pinBtSelOrc.adic( btExec, 3, 96, 30, 30 );

		pnCliTab.add( spnTab, BorderLayout.CENTER );
		pnCliTab.add( pinBtSel, BorderLayout.EAST );

		if ((Boolean) prefs.get(COL_PREFS.FATORCPARC.name())){
			pinBtSel.adic( btEditQtd, 3, 3, 30, 30);
			posIniItens = 30;
		}

		pinBtSel.adic( btTudoIt, 3, posIniItens + 4, 30, 30 );
		pinBtSel.adic( btNadaIt, 3, posIniItens + 35, 30, 30 );
		pinBtSel.adic( btResetItOrc, 3, posIniItens + 66, 30, 30 );
		pinBtSel.adic( btAgruparItens, 3, posIniItens + 97, 30, 30 );
		pinBtSel.adic( btGerar, 3, posIniItens + 127, 30, 30 );

		pnCli.add( pnTabOrc, BorderLayout.NORTH );
		pnCli.add( pnCliTab, BorderLayout.CENTER );

		txtVlrProd.setAtivo( false );
		txtVlrDesc.setAtivo( false );
		txtVlrLiq.setAtivo( false );

		// Seta os comentários

		btExec.setToolTipText( "Executar montagem" );
		btTudoOrc.setToolTipText( "Selecionar tudo" );
		btNadaOrc.setToolTipText( "Limpar seleção" );
		btGerar.setToolTipText( "Gerar venda" );
		btAgruparItens.setToolTipText( "Agrupar ítens" );


		montaTabOrc();
		montaItemTabOrc();

		addWindowListener( this );
	}


	private void montaListaCampos() {

		lcOrc.add( new GuardaCampo( txtCodOrc, "CodOrc", "N. orçamento", ListaCampos.DB_PK, null, false ) );
		lcOrc.add( new GuardaCampo( txtDtOrc, "DtOrc", "Data", ListaCampos.DB_SI, null, false ) );
		lcOrc.add( new GuardaCampo( txtDtVal, "DtVencOrc", "Validade", ListaCampos.DB_SI, null, false ) );
		lcOrc.montaSql( false, "ORCAMENTO", "VD" );
		lcOrc.setQueryCommit( false );
		lcOrc.setReadOnly( true );
		txtCodOrc.setNomeCampo( "CodOrc" );
		txtCodOrc.setListaCampos( lcOrc );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, null, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

		lcConv.add( new GuardaCampo( txtCodConv, "CodConv", "Cód.conv.", ListaCampos.DB_PK, null, false ) );
		lcConv.add( new GuardaCampo( txtNomeConv, "NomeConv", "Nome do conveniado", ListaCampos.DB_SI, null, false ) );
		txtCodConv.setTabelaExterna( lcConv, null );
		txtCodConv.setNomeCampo( "CodConv" );
		txtCodConv.setFK( true );
		lcConv.setReadOnly( true );
		lcConv.montaSql( false, "CONVENIADO", "AT" );

	}

	private boolean testaPagto(int codcli) {
		boolean result = true;
		try {
			String mensagem = daobusca.testaPgto( "", codcli
				, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCLIENTE" ) ) ; 
			if ( "N".equals( mensagem ) ) {
				if ( Funcoes.mensagemConfirma( null, "Cliente com duplicatas em aberto! Continuar?" ) != 0 ) {
					result = false;
				}
			} else if (!"".equals( mensagem )) {
				Funcoes.mensagemInforma( null, mensagem );
				if (!"S".equals( Aplicativo.getUsuario().getLiberacredusu()) ) {
					if (!senhaLiberaAtraso() ) {
						Funcoes.mensagemInforma(null, "Usuário não tem permissão para liberação da venda !");
						result = false;
					}
				}
			}
		} catch (Exception err) {
			Funcoes.mensagemErro( null, err.getMessage() );
		}
		
		return result;
	}

	private boolean senhaLiberaAtraso() {
		boolean result = false;
		FPassword fpw = new FPassword( this, FPassword.LIBERA_CRED, "Liberação de título em atraso", con );
		fpw.execShow();
		result=fpw.OK;
		fpw.dispose();
		return result;
	}
	
	private void montaListener() {

		tabitorc.addKeyListener( this );
		tabitorc.addMouseListener( this );
		tabOrc.addKeyListener( this );
		btBusca.addKeyListener( this );
		btGerar.addKeyListener( this );
		btAgruparItens.addKeyListener( this );

		txtCodOrc.addActionListener( this );
		btSair.addActionListener( this );
		btBusca.addActionListener( this );
		btExec.addActionListener( this );
		btGerar.addActionListener( this );
		btAgruparItens.addActionListener( this );
		btTudoOrc.addActionListener( this );
		btNadaOrc.addActionListener( this );
		btTudoIt.addActionListener( this );
		btNadaIt.addActionListener( this );
		btResetOrc.addActionListener( this );
		btResetItOrc.addActionListener( this );
		btEditQtd.addActionListener( this );

		rgBusca.addRadioGroupListener( this );

		lcOrc.addCarregaListener( this );

	}


	private void montaTabOrc() {
		// Monta as tabelas

		tabOrc.adicColuna( "S/N" );
		tabOrc.adicColuna( "Cód.orc." );
		tabOrc.adicColuna( "Cód.cli." );
		tabOrc.adicColuna( "Nome do conveniado" );
		tabOrc.adicColuna( "Nº itens." );
		tabOrc.adicColuna( "Nº lib." );
		tabOrc.adicColuna( "Valor total" );
		tabOrc.adicColuna( "Valor liberado" );
		tabOrc.adicColuna( "" );

		tabOrc.setTamColuna( 25, 0 );
		tabOrc.setTamColuna( 60, 1 );
		tabOrc.setTamColuna( 60, 2 );
		tabOrc.setTamColuna( 210, 3 );
		tabOrc.setTamColuna( 60, 4 );
		tabOrc.setTamColuna( 60, 5 );
		tabOrc.setTamColuna( 100, 6 );
		tabOrc.setTamColuna( 100, 7 );
		tabOrc.setColunaInvisivel( 8 );

		tabOrc.setColunaEditavel( 0, true );


	}

	private void montaItemTabOrc() {

		tabitorc.adicColuna( "" );
		tabitorc.adicColuna( "It." );
		tabitorc.adicColuna( "Cód.Pd." );
		tabitorc.adicColuna( "Descrição" );
		tabitorc.adicColuna( "Qtd." );
		tabitorc.adicColuna( "Qtd.a fat.");
		tabitorc.adicColuna( "Qtd.fat." );
		tabitorc.adicColuna( "Qtd.OP" );
		tabitorc.adicColuna( "Almox." );
		tabitorc.adicColuna( "Lote" );
		tabitorc.adicColuna( "Saldo" );
		tabitorc.adicColuna( "Preço" );
		tabitorc.adicColuna( "Vlr.desc." );
		tabitorc.adicColuna( "Vlr.liq." );
		tabitorc.adicColuna( "Tp.Agr." );
		tabitorc.adicColuna( "Agr." );
		tabitorc.adicColuna( "Vlr.Agr." );
		tabitorc.adicColuna( "Orc." );
		tabitorc.adicColuna( "Usa Lote" );
		tabitorc.adicColuna( "Cod.OP." );

		tabitorc.setTamColuna( 20, GRID_ITENS.SEL.ordinal() );
		tabitorc.setTamColuna( 25, GRID_ITENS.CODITORC.ordinal() );
		tabitorc.setTamColuna( 45, GRID_ITENS.CODPROD.ordinal() );
		tabitorc.setTamColuna( 170, GRID_ITENS.DESCPROD.ordinal() );
		tabitorc.setTamColuna( 75, GRID_ITENS.QTDITORC.ordinal() );
		tabitorc.setTamColuna( 75, GRID_ITENS.QTDAFATITORC.ordinal() );
		tabitorc.setTamColuna( 75, GRID_ITENS.QTDFATITORC.ordinal() );
		tabitorc.setTamColuna( 75, GRID_ITENS.QTDFINALPRODITORC.ordinal() );
		tabitorc.setTamColuna( 50, GRID_ITENS.CODALMOX.ordinal() );
		tabitorc.setTamColuna( 80, GRID_ITENS.CODLOTE.ordinal() );
		tabitorc.setTamColuna( 75, GRID_ITENS.SALDOPROD.ordinal() );
		tabitorc.setTamColuna( 60, GRID_ITENS.PRECO.ordinal() );
		tabitorc.setTamColuna( 75, GRID_ITENS.DESC.ordinal() );
		tabitorc.setTamColuna( 75, GRID_ITENS.VLRLIQ.ordinal() );
		tabitorc.setColunaInvisivel( GRID_ITENS.TPAGR.ordinal() );
		tabitorc.setColunaInvisivel( GRID_ITENS.PAI.ordinal() );
		tabitorc.setTamColuna( 75, GRID_ITENS.VLRAGRP.ordinal() );
		tabitorc.setTamColuna( 35, GRID_ITENS.CODORC.ordinal() );
		tabitorc.setColunaInvisivel( GRID_ITENS.USALOTE.ordinal() );
		tabitorc.setTamColuna( 80, GRID_ITENS.CODOP.ordinal() );

		tabitorc.setColunaEditavel( 0, true );
	}

	private void carregar() {
		try {
			tabitorc.setDataVector(daobusca.carregar(  ListaCampos.getMasterFilial( "EQALMOX" )
					, tabOrc.getDataVector(), (Boolean) prefs.get(COL_PREFS.APROVORCFATPARC.name()), origem));
			calcTotalizadores();
			vValidos = daobusca.getvValidos();
		} catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao carregar Itens do orçamento!\n"+e.getMessage() );
			e.printStackTrace();
		}
	}

	private void calcTotalizadores() {
		float fValProd = 0;
		float fValDesc = 0;
		float fValLiq = 0;

		for ( int i = 0; i < tabitorc.getNumLinhas(); i++ ) {
			fValProd += new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.PRECO.ordinal() ).toString() ) );
			fValDesc += new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.DESC.ordinal() ).toString() ) );
			fValLiq +=  new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.VLRLIQ.ordinal() ).toString() ) );
		}

		txtVlrProd.setVlrBigDecimal( new BigDecimal( fValProd ) );
		txtVlrDesc.setVlrBigDecimal( new BigDecimal( fValDesc ) );
		txtVlrLiq.setVlrBigDecimal( new BigDecimal( fValLiq ) );

	}

	private void zeraTotalizadores() {
		txtVlrProd.setVlrBigDecimal( new BigDecimal(0) );
		txtVlrDesc.setVlrBigDecimal( new BigDecimal(0) );
		txtVlrLiq.setVlrBigDecimal(  new BigDecimal(0) );
	}

	public void CarregaOrcamento(Integer codorc) {
		txtCodOrc.setVlrInteger( codorc );
		lcOrc.carregaDados();
		btBusca.doClick();
		btExec.doClick();
	}

	private boolean gerarContrato() {
		boolean result = false;
		DLCriaContrato dlContato = null;
		Integer codcontr = null;
		String descContr = null;
		if ( tabitorc.getNumLinhas() > 0 ) {
			try {
				codcontr = daobusca.getMaxCodContr( Aplicativo.iCodEmp, ListaCampos.getMasterFilial("VDCONTRATO") );	
			} catch (SQLException e) {
				Funcoes.mensagemErro( null, "Erro ao buscar código do contrato!!!" );
				 e.printStackTrace();
			}
			dlContato = new DLCriaContrato(codcontr,  txtCodCli.getVlrInteger(), txtNomeCli.getVlrString() );
			dlContato.setNewCodigo( codcontr );
			dlContato.setVisible( true );
			if ( dlContato.OK ) {
				codcontr = dlContato.getNewCodigo();
				descContr = dlContato.getDescContr();
				
				dlContato.setVisible( false );
				dlContato.dispose();
			}
			else
				return false;
			
			try {
				result = criarContrato(codcontr, descContr);
				tabitorc.limpa();
				zeraTotalizadores();
			}catch (SQLException e) {
				result = false;
				e.printStackTrace();
			}
		} else {
			Funcoes.mensagemInforma( null, "Não existe nenhum item pra gerar um contrato/projeto!" );
		}
		return result;
	}

	private boolean criarContrato(int codcontr, String descContr)  throws SQLException {
		boolean result = false;
		Date dataInicio = new Date();
		Date dataFim = Funcoes.getDataFimMes( Funcoes.getMes( dataInicio ) - 1, Funcoes.getAno( dataInicio ) );
		Integer index = 1;
		Integer codemp = Aplicativo.iCodEmp;
		VDContrato contrato = new VDContrato();
		contrato.setCodEmp(codemp);
		contrato.setCodFilial( ListaCampos.getMasterFilial("VDCONTRATO") );
		contrato.setCodContr( codcontr );
		contrato.setDescContr( descContr);
		contrato.setCodEmpCl(codemp);
		contrato.setCodFilialCl( ListaCampos.getMasterFilial("VDCLIENTE") );
		contrato.setCodCli( txtCodCli.getVlrInteger() );
		contrato.setDtInicio(dataInicio);
		contrato.setDtFim( dataFim );
		contrato.setTpCobContr( "ME" ); //VERIFICAR
		contrato.setDiaVencContr( Funcoes.getDiaMes( dataFim ));
		contrato.setDiaFechContr( Funcoes.getDiaMes( dataFim ));
		contrato.setIndexContr( index );
		contrato.setTpcontr("P");
		contrato.setDtPrevFin( dataFim );
		contrato.setAtivo( "S" );
		daobusca.insertVDContrato( contrato );
		for ( int i = 0; i < tabitorc.getNumLinhas(); i++ ) {
			VDItContrato itemContrato = new VDItContrato();
			itemContrato.setCodEmp( codemp );
			itemContrato.setCodFilial(ListaCampos.getMasterFilial("VDITCONTRATO")); 
			itemContrato.setCodContr(codcontr); 
			itemContrato.setCodItContr(i + 1); 
			itemContrato.setDescItContr( tabitorc.getValor( i, GRID_ITENS.DESCPROD.ordinal() ).toString()); 
			itemContrato.setCodEmpPd( codemp ); 
			itemContrato.setCodFilialPd( ListaCampos.getMasterFilial( "EQPRODUTO")); 
			itemContrato.setCodProd( new Integer(tabitorc.getValor( i, GRID_ITENS.CODPROD.ordinal() ).toString())); 
			itemContrato.setQtdItContr( new BigDecimal(Funcoes.strCurrencyToDouble( tabitorc.getValor(i, GRID_ITENS.QTDITORC.ordinal()).toString()))); 
			itemContrato.setVlrItContr( new BigDecimal(Funcoes.strCurrencyToDouble( tabitorc.getValor(i, GRID_ITENS.VLRLIQ.ordinal()).toString()))); 
			itemContrato.setCodEmpPe( codemp ); 
			itemContrato.setCodFilialPe( ListaCampos.getMasterFilial( "EQPRODUTO")); 
			itemContrato.setCodProdPe( new Integer(tabitorc.getValor( i, GRID_ITENS.CODPROD.ordinal() ).toString())); 
			itemContrato.setVlrItContrRexCed( new BigDecimal(Funcoes.strCurrencyToDouble( tabitorc.getValor(i, GRID_ITENS.VLRLIQ.ordinal()).toString())));
			itemContrato.setIndexItContr( index++ );
			itemContrato.setAcumuloItContr( 0 ); 
			itemContrato.setFranquiaItContr( "N" );
			daobusca.insertVDItContrato( itemContrato );
			VDContrOrc contrOrc = new VDContrOrc();
			contrOrc.setCodEmp( codemp );
			contrOrc.setCodFilial( ListaCampos.getMasterFilial("VDCONTRATO") );
			contrOrc.setCodContr( codcontr );
			contrOrc.setCodItContr( i + 1 ); 
			contrOrc.setCodEmpOr( codemp ); 
			contrOrc.setCodFilialOr( ListaCampos.getMasterFilial("VDORCAMENTO")  ); 
			contrOrc.setTipoOrc( "O" );
			contrOrc.setCodOrc( new Integer( tabitorc.getValor( i, GRID_ITENS.CODORC.ordinal() ).toString())); 
			contrOrc.setCodItOrc( new Integer( tabitorc.getValor( i, GRID_ITENS.CODITORC.ordinal() ).toString() ) );
			daobusca.insertVDContrOrc( contrOrc );
		}
	daobusca.commit();
	result = true;
	if ( Funcoes.mensagemConfirma( null, "Contrato '" + codcontr + "' gerado com sucesso!!!\n\n" + "Deseja edita-lo?" ) == JOptionPane.YES_OPTION ) {
			FContrato contr = new FContrato( con, codcontr );
			Aplicativo.telaPrincipal.criatela( "Projetos/Contratos", contr , con );
			this.dispose();
	}
	return  result;
}

private boolean gerarVenda() {
	PreparedStatement ps = null;
	PreparedStatement ps2 = null;
	PreparedStatement ps3 = null;
	ResultSet rs = null;
	String sSQL = null;
	boolean bPrim = true;
	int iCodVenda = 0;
	Date dataSaida = null;
	int[] iValsVec = null;
	StringBuffer obs = new StringBuffer();
	DLCriaVendaCompra diag = null;
	Vector<Integer> vOrcAdicObs = new Vector<Integer>();
	try {
		if ( tabitorc.getNumLinhas() > 0 ) {
			boolean usaPedSeq = (Boolean) prefs.get(COL_PREFS.USAPEDSEQ.name());
			//Boolean que determina se data de saida/entrega aparecerá na dialog de Confirmação.
			boolean solDtSaida = ( Boolean) prefs.get(COL_PREFS.SOLDTSAIDA.name());

			diag = new DLCriaVendaCompra( !usaPedSeq, sTipoVenda, solDtSaida, contingencia );

			if ( sTipoVenda.equals( "V" ) && !usaPedSeq && vendaSTD!=null) {
				diag.setNewCodigo( Integer.parseInt( vendaSTD.lcCampos.getNovoCodigo() ) );
			}
			else if (vendaSTD == null && sTipoVenda.equals( "V" )) {
				//xxxdiag.setNewCodigo( Integer.parseInt( vendaSTD.lcCampos.getNovoCodigo() ) );
			}

			diag.setVisible( true );

			if ( diag.OK ) {
				if ( !usaPedSeq && sTipoVenda.equals( "V" ) ) {
					iCodVenda = diag.getNewCodigo();
				}
				if (solDtSaida)
					dataSaida = diag.getDataSaida();

				diag.setVisible( false );
				diag.dispose();
			}
			else
				return false;
			// STD
			if ( sTipoVenda.equals( "V" ) ) {
				for ( int i = 0; i < tabitorc.getNumLinhas(); i++ ) {
					if ( ! ( (Boolean) tabitorc.getValor( i, GRID_ITENS.SEL.ordinal() ) ).booleanValue() ) {
						continue;
					}
					iValsVec = (int[]) vValidos.elementAt( i );
					// Informa na observação da venda a mesma observação do orçamento (primeiro do grid)
					if ( (Boolean) prefs.get(COL_PREFS.ADICOBSORCPED.name()) && bPrim ) {
						obs.append( tabOrc.getValor( 0, 8 ) );
					}
					// Informa na observação da venda os orçamentos que compoe a venda.
					if ( (Boolean) prefs.get(COL_PREFS.ADICORCOBSPED.name())) {
						int codorc =  iValsVec[ 0 ];
						if ( bPrim ) {
							obs.append( "Orçamentos:\n" );
							obs.append(codorc );
							vOrcAdicObs.addElement( codorc );
						}
						else {
							if (vOrcAdicObs.indexOf( (new Integer(codorc )) )==-1) {
								obs.append( " , " );
								obs.append( codorc );
								vOrcAdicObs.addElement( codorc );
							}
						}
						if ( vValidos.size() > 1 && vValidos.size() == i+1 ){
							obs.append( " . " );
						}
					}
					if ( bPrim ) {
						try {
							iCodVenda = daobusca.executaVDAdicVendaORCSP(  Aplicativo.iCodEmp
									, ListaCampos.getMasterFilial( "VDORCAMENTO" )
									, new Integer( tabitorc.getValor( i, GRID_ITENS.CODORC.ordinal() ).toString() )
									, ListaCampos.getMasterFilial( "VDVENDA") 
									, sTipoVenda 
									, iCodVenda 
									, dataSaida 
									, ListaCampos.getMasterFilial( "SGPREFERE1"));
						} catch ( SQLException err ) {
							con.rollback();
							if ( err.getErrorCode() == 335544665 ) {
								Funcoes.mensagemErro( null, "Número de pedido já existe!" );
								return gerarVenda();
							}
							else
								Funcoes.mensagemErro( null, "Erro ao gerar venda!\n" + err.getMessage(), true, con, err );

							err.printStackTrace();
							return false;
						} catch ( Exception e ) {
							Funcoes.mensagemErro( null, "Erro genérico ao gerar venda!\n" + e.getMessage(), true, con, e );
						}
						bPrim = false;
					}
					try {
						Integer icodorc = new Integer( tabitorc.getValor( i, GRID_ITENS.CODORC.ordinal() ).toString());
						Integer coditorc = new Integer( tabitorc.getValor( i, GRID_ITENS.CODITORC.ordinal() ).toString() );
						BigDecimal qtdprod	= new BigDecimal( Funcoes.strCurrencyToDouble( tabitorc.getValor( i,GRID_ITENS.QTDFINALPRODITORC.ordinal() ).toString() ) ) ;
						BigDecimal qtdafatitorc	= new BigDecimal( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.QTDAFATITORC.ordinal() ).toString() ) ) ;
						BigDecimal desc = new BigDecimal( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.DESC.ordinal() ).toString() ) );
						daobusca.executaVDAdicItVendaORCSP( 
								Aplicativo.iCodFilial, 
								iCodVenda, 
								icodorc,
								coditorc, 
								ListaCampos.getMasterFilial( "VDORCAMENTO" ), 
								Aplicativo.iCodEmp, 
								sTipoVenda, 
								tabitorc.getValor( i, GRID_ITENS.TPAGR.ordinal() ).toString(), 
								qtdprod, 
								qtdafatitorc, 
								desc);
					} 
					catch ( SQLException err ) {
						try {
							con.rollback();
						} 
						catch ( SQLException err1 ) {
							err1.printStackTrace();
						}
						Funcoes.mensagemErro( null, "Erro ao gerar itvenda: '" + ( i + 1 ) + "'!\n" + err.getMessage(), true, con, err );
						return false;
					}

				}
				try {
					try {
						daobusca.executaVDAtuDescVendaORCSP( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDVENDA" ), "V", iCodVenda );
					} catch ( SQLException err ) {
						con.rollback();
						Funcoes.mensagemErro( null, "Erro ao atualizar desconto da venda!\n" + err.getMessage(), true, con, err );
						return false;
					}
					try {
						daobusca.atualizaObsPed( obs, iCodVenda );
					} catch ( SQLException err ) {
						con.rollback();
						Funcoes.mensagemErro( null, "Erro ao atualizar observações da venda!\n" + err.getMessage(), true, con, err );
						return false;
					}
					con.commit();
					carregar();

				} catch ( SQLException err ) {
					Funcoes.mensagemErro( null, "Erro ao realizar commit!!" + "\n" + err.getMessage(), true, con, err );
					return false;
				}
				if ( Funcoes.mensagemConfirma( null, "Venda '" + iCodVenda + "' gerada com sucesso!!!\n\n" + "Deseja edita-la?" ) == JOptionPane.YES_OPTION ) {
					if(vendaSTD == null && sTipoVenda.equals( "V" )) {
						vendaSTD = new FVenda();
						Aplicativo.telaPrincipal.criatela( "Venda", vendaSTD, con );
						vendaSTD.exec( iCodVenda );
						this.dispose();
					}
					else {
						vendaSTD.exec( iCodVenda );
						dispose();
					}

				}
			}
			// PDV
			else if ( sTipoVenda.equals( "E" ) ) {
				iValsVec = (int[]) vValidos.elementAt( 0 );

				if ( vendaPDV.montaVendaOrc( iValsVec[ 0 ] ) ) {// Gera a venda
					for ( int i = 0; i < vValidos.size(); i++ ) {
						iValsVec = (int[]) vValidos.elementAt( i );
						vendaPDV.adicItemOrc( iValsVec );// Adiciona os itens
					}
				}
				dispose();
				if ( (Boolean) prefs.get(COL_PREFS.AUTOFECHAVENDA.name()))
					vendaPDV.fechaVenda();
			}
		}
		else
			Funcoes.mensagemInforma( null, "Não existe nenhum item pra gerar uma venda!" );
		con.commit();
	} catch ( Exception e ) {
		try {
			con.rollback();
		} catch (SQLException err) {
			err.printStackTrace();
		}
		e.printStackTrace();
	} finally {
		ps = null;
		ps2 = null;
		rs = null;
		sSQL = null;
		iValsVec = null;
		diag = null;
	}

	return true;
}

private void buscar(boolean proj) {
	int codcli = -1;
	try {
		tabOrc.limpa();
		tabitorc.limpa();
		zeraTotalizadores();
		if ("".equals(txtCodCli.getVlrString()) ) {
			codcli = daobusca.getCodcli(txtCodOrc.getVlrInteger(), Aplicativo.iCodEmp, ListaCampos.getMasterFilial("VDORCAMENTO"));
		} else {
			codcli = txtCodCli.getVlrInteger();
		}
		if (codcli==-1 || testaPagto(codcli)) {
			tabOrc.setDataVector(daobusca.buscar( txtCodOrc.getVlrInteger(), txtCodCli.getVlrInteger()
					, txtCodConv.getVlrInteger(), rgBusca.getVlrString(), proj));
		}
	} catch (ExceptionCarregaDados e) {
		Funcoes.mensagemErro( null, e.getMessage());
		txtCodCli.requestFocus();
		tabOrc.limpa();
		tabitorc.limpa();
	}

}

private void limpaNaoSelecionados( JTablePad ltab ) {

	int linhas = ltab.getNumLinhas();
	int pos = 0;
	try {
		for ( int i = 0; i < linhas; i++ ) {
			if ( ! ( (Boolean) ltab.getValor( i, GRID_ITENS.SEL.ordinal() ) ).booleanValue() ) { // xxx
				ltab.tiraLinha( i );
				vValidos.remove( i );
				i--;
			}
		}
	} catch ( Exception e ) {
		e.printStackTrace();
	}
}

private void limpaFilhos( JTablePad ltab ) {

	int linhas = ltab.getNumLinhas();
	int pos = 0;
	try {
		for ( int i = 0; i < linhas; i++ ) {
			if ( ltab.getValor( i, GRID_ITENS.TPAGR.ordinal() ).toString().equals( "F" ) ) {
				ltab.tiraLinha( i );
				i--;
			}
		}
	} catch ( Exception e ) {
		e.printStackTrace();
	}
}

private Float marcaFilhos( final int iLinha, final Integer codprodpai, final Float precopai ) {

	Integer codprodfilho = null;

	Float precofilho = null;
	Float vlrliqfilho = null;
	Float qtdfilho = null;
	Float ret = new Float( 0 );

	String tpagrup = null;

	int i = iLinha;
	int iPai = iLinha - 1;

	try {

		while ( i < tabitorc.getNumLinhas() ) {

			codprodfilho	= new Integer( tabitorc.getValor( i, GRID_ITENS.CODPROD.ordinal() ).toString() );
			qtdfilho 		= new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.QTDITORC.ordinal() ).toString() ) );
			vlrliqfilho 	= new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.VLRLIQ.ordinal() ).toString() ) );
			tpagrup 		= tabitorc.getValor( i, GRID_ITENS.TPAGR.ordinal() ).toString();
			precofilho 		= new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.PRECO.ordinal() ).toString() ) );

			if ( ( codprodfilho.compareTo( codprodpai ) == 0 ) && ( precopai.compareTo( precofilho ) == 0 ) ) {

				tabitorc.setValor( "F", i, GRID_ITENS.TPAGR.ordinal() );
				tabitorc.setValor( String.valueOf( iPai ), i, GRID_ITENS.PAI.ordinal() );

				ret += qtdfilho;

			}

			i++;
		}
	} 
	catch ( Exception e ) {
		e.printStackTrace();
	}
	return ret;
}

private void agrupaItens() {

	Integer codprodpai = null;
	Float vlrdescnovopai = new Float( 0 );
	Float qtdatupai = null;
	Float qtdnovopai = new Float( 0 );
	Float precopai = null;
	String tpagr = "";

	try {
		limpaNaoSelecionados( tabitorc );

		int linhaPai = -1;

		for ( int i = 0; i < tabitorc.getNumLinhas(); i++ ) {

			codprodpai 		= new Integer( tabitorc.getValor( i, GRID_ITENS.CODPROD.ordinal() ).toString() );
			qtdatupai 		= new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.QTDITORC.ordinal() ).toString() ) );
			precopai		= new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.PRECO.ordinal() ).toString() ) );
			tpagr 			= tabitorc.getValor( i, GRID_ITENS.TPAGR.ordinal() ).toString();
			vlrdescnovopai += new Float( Funcoes.strCurrencyToDouble( tabitorc.getValor( i, GRID_ITENS.DESC.ordinal() ).toString() ) );

			if ( tpagr.equals( "" ) ) {

				qtdnovopai = qtdatupai;
				qtdnovopai += marcaFilhos( i + 1, codprodpai, precopai );

				if ( qtdatupai.compareTo( qtdnovopai ) != 0 ) {

					tabitorc.setValor( "P", i, GRID_ITENS.TPAGR.ordinal() );
					tabitorc.setValor( Funcoes.strDecimalToStrCurrencyd( 2, String.valueOf( qtdnovopai ) ), i, GRID_ITENS.QTDITORC.ordinal() );
					linhaPai = i;

				}
				else {
					tabitorc.setValor( "N", i, GRID_ITENS.TPAGR.ordinal() );
				}
			}
		}

		if ( linhaPai > -1 ) {
			tabitorc.setValor( Funcoes.strDecimalToStrCurrencyd( 2, String.valueOf( vlrdescnovopai ) ), linhaPai, GRID_ITENS.DESC.ordinal() );
		}

	} 
	catch ( Exception e ) {
		e.printStackTrace();
	}
}

private void carregaTudo( JTablePad tb ) {

	for ( int i = 0; i < tb.getNumLinhas(); i++ ) {
		tb.setValor( new Boolean( true ), i, 0 );
	}
}

private void carregaNada( JTablePad tb ) {

	for ( int i = 0; i < tb.getNumLinhas(); i++ ) {
		tb.setValor( new Boolean( false ), i, 0 );
	}
}

public void keyPressed( KeyEvent kevt ) {

	if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
		if ( kevt.getSource() == btBusca ) {
			btBusca.doClick();
			tabOrc.requestFocus();
		}
		else if ( kevt.getSource() == tabOrc ) {
			btExec.doClick();
			tabitorc.requestFocus();
		}
		else if ( kevt.getSource() == btGerar ) {

			if ("Venda".equals( origem )) {
				if ( !gerarVenda() ) {
					try {
						con.rollback();
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( null, "Erro ao realizar rollback!!\n" + err.getMessage(), true, con, err );
					}
				}
			} else {
				if (!gerarContrato()) {
					try {
						con.rollback();
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( null, "Erro ao realizar rollback!!\n" + err.getMessage(), true, con, err );
					}
				}
			}
		}
		else if ( kevt.getSource() == tabitorc )
			btGerar.requestFocus();
	}
	// super.keyPressed(kevt);
}

public void actionPerformed( ActionEvent evt ) {

	if ( evt.getSource() == btSair ) {
		dispose();
	}
	else if ( evt.getSource() == btBusca ) {
		boolean proj = !"Venda".equalsIgnoreCase( origem );
		buscar(proj);
	}
	else if ( evt.getSource() == btExec ) {
		carregar();
	}
	else if ( evt.getSource() == btGerar ) {

		if ("Venda".equalsIgnoreCase( origem )) {
			if ( !gerarVenda() ) {
				try {
					con.rollback();
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( null, "Erro ao realizar rollback!!\n" + err.getMessage(), true, con, err );
				}
			}
		} else {
			if (!gerarContrato()) {
				try {
					con.rollback();
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( null, "Erro ao realizar rollback!!\n" + err.getMessage(), true, con, err );
				}
			}
		}
	}
	else if ( evt.getSource() == btAgruparItens ) {
		try {
			if ( Funcoes.mensagemConfirma( null, "Confirma o agrupamento dos ítens iguais?\nSerão agrupados apenas os ítens de código e preços iguais." ) == JOptionPane.YES_OPTION ) {
				agrupaItens(); // comentar
			}
		} catch ( Exception err ) {
			Funcoes.mensagemErro( null, "Erro ao realizar agrupamento de ítens!!\n" + err.getMessage(), true, con, err );
		}
	}
	else if ( evt.getSource() == btTudoOrc ) {
		carregaTudo( tabOrc );
	}
	else if ( evt.getSource() == btNadaOrc ) {
		carregaNada( tabOrc );
	}
	else if ( evt.getSource() == btTudoIt ) {
		carregaTudo( tabitorc );
	}
	else if ( evt.getSource() == btNadaIt ) {
		carregaNada( tabitorc );
	}
	else if ( evt.getSource() == txtCodOrc ) {
		if ( txtCodOrc.getVlrInteger().intValue() > 0 )
			btBusca.requestFocus();
	}
	else if ( evt.getSource() == btResetOrc ) {
		tabOrc.limpa();
		tabitorc.limpa();
		zeraTotalizadores();
	}
	else if ( evt.getSource() == btResetItOrc ) {
		tabitorc.limpa();
		zeraTotalizadores();
	}
	else if ( evt.getSource() == btEditQtd ) {
		editItem();


	}


}

private void editItem() {
	if ((Boolean) prefs.get(COL_PREFS.FATORCPARC.name())) {
		int linhasel = tabitorc.getLinhaSel();
		if ( linhasel < 0 ) {
			Funcoes.mensagemInforma( null, "Selecione um item para edição !" );
		} else {

			int coditorc = Integer.parseInt(tabitorc.getValor( linhasel, GRID_ITENS.CODITORC.ordinal() ).toString().trim() );
			//rs.getInt( "CodItOrc" ) ), irow, GRID_ITENS.CODITORC.ordinal() 
			int codprod = Integer.parseInt(tabitorc.getValor( linhasel, GRID_ITENS.CODPROD.ordinal() ).toString().trim() );

			String descprod = tabitorc.getValor( linhasel, GRID_ITENS.DESCPROD.ordinal() ).toString().trim();
			//	tabitorc.setValor( new Integer( rs.getInt( "CodProd" ) ), irow, GRID_ITENS.CODPROD.ordinal() );



			BigDecimal qtditorc = new BigDecimal( Funcoes.strCurrencyToDouble( 
					tabitorc.getValor( linhasel, GRID_ITENS.QTDITORC.ordinal() ).toString().trim() ) );
			BigDecimal qtdafatitorc =  new BigDecimal( Funcoes.strCurrencyToDouble(
					tabitorc.getValor( linhasel, GRID_ITENS.QTDAFATITORC.ordinal() ).toString().trim() ) );
			BigDecimal qtdfatitorc =  new BigDecimal( Funcoes.strCurrencyToDouble(
					tabitorc.getValor( linhasel, GRID_ITENS.QTDFATITORC.ordinal() ).toString().trim() ) );

			if ( qtdafatitorc.compareTo( new BigDecimal(0) ) <= 0 ) {
				Funcoes.mensagemInforma( null, "Não há quantidade(s) a faturar !" );
			} else {

				DLEditQtd dl = new DLEditQtd(coditorc, codprod, descprod, qtditorc, qtdafatitorc, qtdfatitorc);
				dl.setVisible( true );
				dl.dispose();

				if (dl.OK) {

					qtdafatitorc = dl.getQtdafatitorc();
					//qtdfatitorc = dl.getQtdfatitorc();


					if (qtdafatitorc.compareTo( new BigDecimal(0) )>0) {
						tabitorc.setValor( Funcoes.strDecimalToStrCurrencyd( casasDec, qtdafatitorc.toString() ) , 
								linhasel, GRID_ITENS.QTDAFATITORC.ordinal() );
						/*	
						 * 	tabitorc.setValor( Funcoes.strDecimalToStrCurrencyd( casasDec, qtdfatitorc.toString() ) , 
									linhasel, GRID_ITENS.QTDFATITORC.ordinal() );
						 */
					} 
				}

				if ( dl.OK == false ) {
					dl.dispose();
					return;
				}

			}
		}
	}
}

public void beforeCarrega( CarregaEvent e ) {

}

public void afterCarrega( CarregaEvent e ) {

	txtCodConv.setAtivo( false );
	txtCodCli.setAtivo( false );
	lcCli.limpaCampos( true );
	lcConv.limpaCampos( true );
}

public void valorAlterado( RadioGroupEvent rgevt ) {

	if ( rgBusca.getVlrString().equals( "O" ) ) {
		ocultaCliente();
	}
	else if ( rgBusca.getVlrString().equals( "L" ) ) {
		ocultaConveniado();
	}
	lcOrc.limpaCampos( true );
}

public void ocultaConveniado() {

	lcConv.limpaCampos( true );

	txtCodCli.setVisible( true );
	txtNomeCli.setVisible( true );
	lbCodCli.setVisible( true );
	lbNomeCli.setVisible( true );
	txtCodConv.setVisible( false );
	txtNomeConv.setVisible( false );
	lbCodConv.setVisible( false );
	lbNomeConv.setVisible( false );
}

public void ocultaCliente() {

	lcCli.limpaCampos( true );

	txtCodCli.setVisible( false );
	txtNomeCli.setVisible( false );
	lbCodCli.setVisible( false );
	lbNomeCli.setVisible( false );
	txtCodConv.setVisible( true );
	txtNomeConv.setVisible( true );
	lbCodConv.setVisible( true );
	lbNomeConv.setVisible( true );

}

public void firstFocus() {

	txtCodOrc.requestFocus();
}

public void setConexao( DbConnection cn ) {

	super.setConexao( cn );
	lcCli.setConexao( cn );
	lcConv.setConexao( cn );
	lcOrc.setConexao( cn );
	daobusca = new DAOBuscaOrc( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDORCAMENTO" ), cn );
	try {
		prefs =	daobusca.getPrefs();
	} catch (SQLException err) {
		Funcoes.mensagemErro( null, "Erro ao buscar preferências gerais!\n" + err.getMessage(), true, con, err );
		err.printStackTrace();
	}
	montaListaCampos();
	montaTela();
	montaListener();
	txtCodOrc.setFocusable( true );
	setFirstFocus( txtCodOrc );
}

public void mouseClicked( MouseEvent e ) {
	if ( e.getSource() == tabitorc ) {
		if ( tabitorc.getLinhaSel() > -1 ) {
			if ( e.getClickCount() == 2 ) {
				String cloteprod 	= (String) 	tabitorc.getValor( tabitorc.getLinhaSel(), GRID_ITENS.USALOTE.ordinal() );
				String codlote 		= (String) 	tabitorc.getValor( tabitorc.getLinhaSel(), GRID_ITENS.CODLOTE.ordinal() );
				Integer codprod 	= (Integer) tabitorc.getValor( tabitorc.getLinhaSel(), GRID_ITENS.CODPROD.ordinal() );
				String descprod 	= (String) 	tabitorc.getValor( tabitorc.getLinhaSel(), GRID_ITENS.DESCPROD.ordinal() );
				if ( "S".equals( cloteprod ) ) {
					DLSelecionaLote dl = new DLSelecionaLote( this, codprod.toString(), descprod, con );
					dl.setVisible( true );
					if ( dl.OK ) {
						try {
							daobusca.atualizaLoteItVenda( dl.getValor(), tabitorc.getLinhaSel(),
									(Integer) tabitorc.getValor(tabitorc.getLinhaSel(), GRID_ITENS.CODITORC.ordinal() ),
									(Integer) tabitorc.getValor(tabitorc.getLinhaSel(), GRID_ITENS.CODITORC.ordinal() ));

							tabitorc.setValor( codlote, tabitorc.getLinhaSel(), GRID_ITENS.CODLOTE.ordinal() );
						} catch (SQLException err) {
							err.printStackTrace();
						} finally {
							dl.dispose();
						}
					}
					else {
						dl.dispose();
					}
				}
			}
		}
	}

}

public void mouseEntered( MouseEvent arg0 ) {

}

public void mouseExited( MouseEvent arg0 ) {

}

public void mousePressed( MouseEvent arg0 ) {

}

public void mouseReleased( MouseEvent arg0 ) {

}
}
