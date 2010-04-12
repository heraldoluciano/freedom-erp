/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FEstrutura.java <BR>
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
 * Tela para cadastro de estruturas de produtos.
 * 
 */

package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTabbedPanePad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPrinterJob;

public class FEstrutura extends FDetalhe implements ChangeListener, ActionListener, CarregaListener, PostListener {

	
	private static final long serialVersionUID = 1L;

	private int casasDec = Aplicativo.casasDec;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDetFases = new JPanelPad( new GridLayout( 2, 1 ) );

	private JPanelPad pinDetFasesCampos = new JPanelPad();

	private JPanelPad pinDetFasesInstrucao = new JPanelPad( new GridLayout( 1, 1 ) );

	private JPanelPad pinDetItens = new JPanelPad( 590, 110 );
	
	private JPanelPad pinDetEstrAnalise = new JPanelPad( 590, 110 );

	private JPanelPad pinDetDistrib = new JPanelPad();

	private JTabbedPanePad tpnAbas = new JTabbedPanePad();

	private JTextFieldPad txtCodProdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdEst = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescEstDistrib = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtCLoteProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtSerieProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodFase = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescFase = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtTipoFase = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtQtdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSeqItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtSeqDistrib = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtRefProdItem = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescProdItem = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProdDistrib = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtVlrMin = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtVlrMax = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtEspecificacao = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtQtdMat = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtRMA = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtRefProdEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodModLote = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNroDiasValid = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtSeqEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtSeqEstDistrib = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtDescModLote = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtSeqEfEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTpRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTpRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtTempoEf = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodEstAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodTpAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescTpAnalise = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtTpExp = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtCasasDec = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JCheckBoxPad cbFinaliza = new JCheckBoxPad( "Finaliza", "S", "N" );

	private JCheckBoxPad cbAtiva = new JCheckBoxPad( "Ativa", "S", "N" );

	private JCheckBoxPad cbGLoteOPP = new JCheckBoxPad( "Mod.lote da OP principal", "S", "N" );

	private JCheckBoxPad cbOpDensidade = new JCheckBoxPad( "Usa densidade na OP?", "S", "N" );

	private JCheckBoxPad cbRmaAutoItEst = new JCheckBoxPad( "Rma", "S", "N" );
	
	private JCheckBoxPad cbCProva = new JCheckBoxPad( "Contra prova?", "S", "N" );
	
	private JCheckBoxPad cbQtdVariavelItem = new JCheckBoxPad( "Qtd. variavél?", "S", "N" );
	
	private JCheckBoxPad cbQtdFixaItem = new JCheckBoxPad( "Qtd. fixa?", "S", "N" );
	
	private JCheckBoxPad cbEmitCert = new JCheckBoxPad( "Certificado?", "S", "N" );

	private JTextAreaPad txaModoPreparo = new JTextAreaPad();

	private JTablePad tabItens = new JTablePad();
	
	private JTablePad tabEstru = new JTablePad();

	private JTablePad tabDist = new JTablePad();
	
	private JTablePad tabQuali = new JTablePad();

	private JScrollPane spItens = new JScrollPane( tabItens );
	
	private JScrollPane spQuali = new JScrollPane( tabQuali );

	private JScrollPane spDist = new JScrollPane( tabDist );
	
	private JScrollPane spEstru = new JScrollPane( tabEstru );

	private JScrollPane spnModoPreparo = new JScrollPane( txaModoPreparo );

	private ListaCampos lcProdEst = new ListaCampos( this, "" );

	private ListaCampos lcProdItem = new ListaCampos( this, "PD" );
	
	private ListaCampos lcProdItemRef = new ListaCampos( this, "PD" );

	private ListaCampos lcFase = new ListaCampos( this, "FS" );

	private ListaCampos lcModLote = new ListaCampos( this, "ML" );

	private ListaCampos lcDetItens = new ListaCampos( this );
	
	private ListaCampos lcDetEstrAnalise = new ListaCampos( this );

	private ListaCampos lcDetDistrib = new ListaCampos( this );

	private ListaCampos lcEstDistrib = new ListaCampos( this, "DE" );

	private ListaCampos lcTipoRec = new ListaCampos( this, "TR" );
	
	private ListaCampos lcTpAnalise = new ListaCampos( this, "TA" );
	
	private ListaCampos lcUnid = new ListaCampos( this, "UD" );
	
	private HashMap<String, Object> prefere = null;
	

	public FEstrutura() {

		setTitulo( "Estrutura de produtos" );
		setAtribos( 50, 20, 670, 550 );
		setAltCab( 170 );

	}
	
	private void montaTela() {
		pnMaster.remove( spTab );
		pnMaster.remove( pnDet );

		tpnAbas.addTab( "Fases", spTab );
		tpnAbas.addTab( "Itens X Fase", spItens );
		tpnAbas.addTab( "Controle de qualidade", spQuali );
		tpnAbas.addTab( "Distribuição X Fase", spDist );
		

		pnMaster.add( tpnAbas, BorderLayout.CENTER );

		pnGImp.removeAll();
		pnGImp.setLayout( new GridLayout( 1, 2 ) );
		pnGImp.setPreferredSize( new Dimension( 100, 26 ) );
		pnGImp.add( btPrevimp );
		pnGImp.add( btImp );

		cbAtiva.setVlrString( "N" );

		lcDetItens.setMaster( lcDet );
		lcDet.adicDetalhe( lcDetItens );
		lcDetDistrib.setMaster( lcDet );
		lcDet.adicDetalhe( lcDetDistrib );
		lcDetEstrAnalise.setMaster( lcDet );
		lcDet.adicDetalhe( lcDetEstrAnalise );

		txtQtdMat.addKeyListener( this );
		
		pinCab = new JPanelPad( 500, 90 );
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		lcCampos.addPostListener( this );
		lcProdEst.setUsaME( false );
		lcProdEst.add( new GuardaCampo( txtCodProdEst, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true ) );
		lcProdEst.add( new GuardaCampo( txtDescProdEst, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProdEst.add( new GuardaCampo( txtRefProdEst, "RefProd", "Referencia", ListaCampos.DB_SI, false ) );
		lcProdEst.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "Usa Lote", ListaCampos.DB_SI, false ) );
		lcProdEst.add( new GuardaCampo( txtSerieProd, "SerieProd", "Usa Série", ListaCampos.DB_SI, false ) );
		
		lcProdEst.setWhereAdic( "TIPOPROD='F' AND CODEMP=" + Aplicativo.iCodEmp + " AND CODFILIAL=" + Aplicativo.iCodFilial );
		lcProdEst.montaSql( false, "PRODUTO", "EQ" );
		lcProdEst.setQueryCommit( false );
		lcProdEst.setReadOnly( true );
		txtRefProdEst.setTabelaExterna( lcProdEst );
		txtCodProdEst.setTabelaExterna( lcProdEst );
		txtSeqEst.setTabelaExterna( lcProdEst );
		txtDescProdEst.setListaCampos( lcProdEst );
		
		lcProdItem.add( new GuardaCampo( txtCodProdItem, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true ) );
		lcProdItem.add( new GuardaCampo( txtDescProdItem, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProdItem.add( new GuardaCampo( txtRefProdItem, "RefProd", "Referência", ListaCampos.DB_SI, false ) );
		lcProdItem.add( new GuardaCampo( txtRMA, "RMAProd", "RMA", ListaCampos.DB_SI, false ) );

		lcProdItem.montaSql( false, "PRODUTO", "EQ" );
		lcProdItem.setQueryCommit( false );
		lcProdItem.setReadOnly( true );
		txtCodProdItem.setTabelaExterna( lcProdItem );
		txtDescProdItem.setListaCampos( lcProdItem );

		lcProdItemRef.add( new GuardaCampo( txtRefProdItem, "RefProd", "Referência", ListaCampos.DB_PK, false ) );
		lcProdItemRef.add( new GuardaCampo( txtDescProdItem, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProdItemRef.add( new GuardaCampo( txtCodProdItem, "CodProd", "Cód.prod.", ListaCampos.DB_SI, true ) );		
		lcProdItemRef.add( new GuardaCampo( txtRMA, "RMAProd", "RMA", ListaCampos.DB_SI, false ) );

		lcProdItemRef.montaSql( false, "PRODUTO", "EQ" );
		lcProdItemRef.setQueryCommit( false );
		lcProdItemRef.setReadOnly( true );
		txtRefProdItem.setTabelaExterna( lcProdItemRef );
		txtDescProdItem.setListaCampos( lcProdItemRef );
		
		lcFase.add( new GuardaCampo( txtCodFase, "CodFase", "Cód.fase", ListaCampos.DB_PK, true ) );
		lcFase.add( new GuardaCampo( txtDescFase, "DescFase", "Descrição da fase", ListaCampos.DB_SI, false ) );
		lcFase.add( new GuardaCampo( txtTipoFase, "TipoFase", "Tipo da fase", ListaCampos.DB_SI, false ) );
		lcFase.montaSql( false, "FASE", "PP" );
		lcFase.setQueryCommit( false );
		lcFase.setReadOnly( true );
		txtCodFase.setTabelaExterna( lcFase );
		txtCodFase.setNomeCampo( "codfase" );
		txtDescFase.setListaCampos( lcFase );

		lcModLote.add( new GuardaCampo( txtCodModLote, "CodModLote", "Cód.Mod.Lote", ListaCampos.DB_PK, false ) );
		lcModLote.add( new GuardaCampo( txtDescModLote, "DescModLote", "Descrição do modelo de lote", ListaCampos.DB_SI, false ) );
		lcModLote.montaSql( false, "MODLOTE", "EQ" );
		lcModLote.setQueryCommit( false );
		lcModLote.setReadOnly( true );
		txtCodModLote.setTabelaExterna( lcModLote );
		txtDescModLote.setListaCampos( lcModLote );

		adicCampo( txtCodProdEst, 7, 20, 80, 20, "CodProd", "Cód.prod.", ListaCampos.DB_PF, txtDescProdEst, true );
		adicDescFK( txtDescProdEst, 90, 20, 297, 20, "DescProd", "Descrição do produto" );
		adicCampo( txtSeqEst, 390, 20, 85, 20, "SeqEst", "Seq.Est.", ListaCampos.DB_PF, true );// era pra ser DB_PF, mas ta dando erro.
		adicCampo( txtQtdEst, 7, 60, 80, 20, "QtdEst", "Quantidade", ListaCampos.DB_SI, true );
		adicCampo( txtDescEst, 90, 60, 297, 20, "DescEst", "Descrição", ListaCampos.DB_SI, true );
		adicCampoInvisivel( txtRefProdEst, "RefProd", "Ref.prod.", ListaCampos.DB_SI, false );
		adicDB( cbAtiva, 485, 20, 80, 20, "ATIVOEST", "", true );
		adicDB( cbGLoteOPP, 485, 40, 160, 20, "GLOTEOPP", "", true );
		adicCampo( txtCodModLote, 7, 100, 80, 20, "CodModLote", "Cód.Mod.Lote", ListaCampos.DB_FK, txtDescModLote, false );
		adicDescFK( txtDescModLote, 90, 100, 297, 20, "DescModLote", "Descrição do modelo do lote" );
		adicCampo( txtNroDiasValid, 390, 60, 85, 20, "NroDiasValid", "Dias de valid.", ListaCampos.DB_SI, false );
		adicDB( cbOpDensidade, 485, 60, 250, 20, "USADENSIDADEOP", "", true );

		setListaCampos( false, "ESTRUTURA", "PP" );
		lcCampos.setQueryInsert( false );

		// Detalhe Fases

		lcTipoRec.add( new GuardaCampo( txtCodTpRec, "CodTpRec", "Cód.tp.rec.", ListaCampos.DB_PK, true ) );
		lcTipoRec.add( new GuardaCampo( txtDescTpRec, "DescTpRec", "Descrição do tipo de recurso", ListaCampos.DB_SI, false ) );
		lcTipoRec.montaSql( false, "TIPOREC", "PP" );
		lcTipoRec.setQueryCommit( false );
		lcTipoRec.setReadOnly( true );
		txtCodTpRec.setTabelaExterna( lcTipoRec );

		setPainel( pinDetFases, pnDet );
		pinDetFases.add( pinDetFasesCampos );
		pinDetFases.add( pinDetFasesInstrucao );
		setPainel( pinDetFasesCampos );
		setListaCampos( lcDet );

		adicCampo( txtSeqEfEst, 7, 20, 40, 20, "SeqEf", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodFase, 50, 20, 77, 20, "CodFase", "Cód.fase", ListaCampos.DB_PF, txtDescFase, true );
		adicDescFK( txtDescFase, 130, 20, 310, 20, "DescFase", "Descrição da fase" );

		adicCampo( txtTempoEf, 450, 20, 80, 20, "TempoEf", "Tempo(Seg)", ListaCampos.DB_SI, true );
		adicCampo( txtCodTpRec, 7, 60, 80, 20, "CodTpRec", "Cód.tp.rec.", ListaCampos.DB_FK, txtDescTpRec, true );
		adicDescFK( txtDescTpRec, 90, 60, 350, 20, "DescTpRec", "Desc. tipo de recurso" );
		adicDescFKInvisivel( txtTipoFase, "TipoFase", "Tipo da fase" );
		
		adicDB( cbFinaliza, 533, 20, 80, 20, "FINALIZAOP", "", true );

		setPainel( pinDetFasesInstrucao );
		GridLayout gi = (GridLayout)pinDetFasesInstrucao.getLayout();
		gi.setHgap( 10 );
		gi.setVgap( 10 );

		pinDetFasesInstrucao.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createEtchedBorder(), "Instruções" ) );
		adicDBLiv( txaModoPreparo, "Instrucoes", "Instruções", false );
		pinDetFasesInstrucao.add( spnModoPreparo );

		setListaCampos( true, "ESTRUFASE", "PP" );
		lcDet.setQueryInsert( false );

		// Fim do detalhe fases

		// Detalhe Itens

		setPainel( pinDetItens );
		setListaCampos( lcDetItens );
		setNavegador( navRod );

		adicCampo( txtSeqItem, 7, 20, 40, 20, "SeqItEst", "Item", ListaCampos.DB_PK, true );

		if ( comRef() ) {
//			txtRefProdItem.setBuscaAdic( new DLBuscaProd( con, "RefProdPD", lcProdItemRef.getWhereAdic() ) );
			adicCampoInvisivel( txtCodProdItem, "CodProdPD", "Cód.Prod.", ListaCampos.DB_FK, txtDescProdItem, false );
			adicCampoInvisivel( txtRefProdItem, "RefProdPD", "Referência", ListaCampos.DB_FK, false );

			adic( new JLabelPad( "Referência" ), 50, 0, 77, 20 );
			adic( txtRefProdItem, 50, 20, 77, 20 );
			txtRefProdItem.setRequerido( true );
			txtRefProdItem.setFK( true );
		}
		else {
//			txtCodProdItem.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProdItem.getWhereAdic() ) );
			adicCampo( txtCodProdItem, 50, 20, 77, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProdItem, false );
			adicCampoInvisivel( txtRefProdItem, "RefProdPD", "Referência", ListaCampos.DB_FK, false );
		}

//		adicCampo( txtCodProdItem, 50, 20, 77, 20, "CodProdPD", "Cód.prod.", ListaCampos.DB_FK, txtDescProdItem, true );
		adicDescFK( txtDescProdItem, 130, 20, 327, 20, "DescProd", "Descrição do produto" );
		adicCampo( txtQtdMat, 460, 20, 100, 20, "QtdItEst", "Qtd.", ListaCampos.DB_SI, true );
		
		adicDB( cbRmaAutoItEst, 10, 60, 80, 20, "RmaAutoItEst", "", true );		
		adicDB( cbCProva, 90, 60, 120, 20, "CPROVA", "", true );
		adicDB( cbQtdVariavelItem, 210, 60, 100, 20, "QtdVariavel", "", true );
		adicDB( cbQtdFixaItem, 330, 60, 100, 20, "QtdFixa", "", true );
			
		setListaCampos( true, "ITESTRUTURA", "PP" );
		lcDetItens.setQueryInsert( false );
//		txtCodProdItem.setNomeCampo( "CodProd" );
		lcDetItens.setTabela( tabItens );

		// Fim Detalhe Itens

		// Detalhe Distribuição

		lcEstDistrib.add( new GuardaCampo( txtCodProdDistrib, "Codprod", "Cód.prod.", ListaCampos.DB_PK, txtDescEstDistrib, true ) );
		lcEstDistrib.add( new GuardaCampo( txtSeqEstDistrib, "seqest", "Seq.Est.", ListaCampos.DB_PK, txtDescEstDistrib, true ) );
		lcEstDistrib.add( new GuardaCampo( txtDescEstDistrib, "DescEst", "Descrição da estrutura", ListaCampos.DB_SI, false ) );
		lcEstDistrib.setWhereAdic( "ATIVOEST='S'" );
		lcEstDistrib.montaSql( false, "ESTRUTURA", "PP" );
		lcEstDistrib.setReadOnly( true );
		lcEstDistrib.setQueryCommit( false );
		txtCodProdDistrib.setListaCampos( lcEstDistrib );
		txtCodProdDistrib.setTabelaExterna( lcEstDistrib );
		txtCodProdDistrib.setNomeCampo( "Codprod" );
		txtCodProdDistrib.setFK( true );
		txtSeqEstDistrib.setListaCampos( lcEstDistrib );
		txtSeqEstDistrib.setTabelaExterna( lcEstDistrib );
		txtSeqEstDistrib.setNomeCampo( "seqest" );
		txtSeqEstDistrib.setFK( true );
		txtDescEstDistrib.setListaCampos( lcEstDistrib );

		setPainel( pinDetDistrib );
		setListaCampos( lcDetDistrib );

		adicCampo( txtSeqDistrib, 7, 20, 60, 20, "seqde", "Seq.", ListaCampos.DB_PK, true );
		adicCampo( txtCodProdDistrib, 70, 20, 77, 20, "CodProdDe", "Cód.prod.", ListaCampos.DB_FK, true );
		adicCampo( txtSeqEstDistrib, 150, 20, 77, 20, "SeqEstDe", "Seq.Est", ListaCampos.DB_FK, txtDescEstDistrib, true );
//		adicDB( cbQtdVariavelDistrib, 7, 60, 70, 20, "QtdVariavel", "Qtd.variável", true );
		adicDescFK( txtDescEstDistrib, 230, 20, 277, 20, "DescEst", "Descrição da estrutura" );
		setListaCampos( true, "DISTRIB", "PP" );
		lcDetDistrib.setQueryInsert( false );
		lcDetDistrib.setTabela( tabDist );
		lcDetDistrib.montaTab();

		// Fim Detalhe Distribuição
			
		setAltDet( 210 );
		setPainel( pinDetFases, pnDet );
		setListaCampos( lcDet );		
		lcDet.montaTab();
		lcDetItens.montaTab();
		
		// Controle de Qualidade
		
		setPainel( pinDetEstrAnalise );
		setListaCampos( lcDetEstrAnalise );
		setNavegador( navRod );	
		
		lcTpAnalise.add( new GuardaCampo( txtCodTpAnalise, "CodTpAnalise", "Cód.Tp.Análise", ListaCampos.DB_PK, null, false ) );
		lcTpAnalise.add( new GuardaCampo( txtDescTpAnalise, "DescTpAnalise", "Descrição da Análise", ListaCampos.DB_SI, false ) );
		lcTpAnalise.add( new GuardaCampo( txtTpExp, "TipoExpec", "Tipo expecificação", ListaCampos.DB_SI, false ) );
		lcTpAnalise.add( new GuardaCampo( txtCodUnid, "CodUnid", "Cód.Unid.", ListaCampos.DB_FK, txtCasasDec, false ) );
		
		lcTpAnalise.montaSql( false, "TIPOANALISE", "PP" );
		lcTpAnalise.setReadOnly( true );
		lcTpAnalise.setQueryCommit( false );
		txtCodTpAnalise.setListaCampos( lcTpAnalise );
		txtCodTpAnalise.setTabelaExterna( lcTpAnalise );

		lcUnid.add( new GuardaCampo( txtCodUnid, "CodUnid", "Cód.unid.", ListaCampos.DB_PK, true ) );
		lcUnid.add( new GuardaCampo( txtCasasDec, "CasasDec", "Cód.unid.", ListaCampos.DB_SI, true ) );
		lcUnid.montaSql( false, "UNIDADE", "EQ" );
		lcUnid.setReadOnly( true );
		lcUnid.setQueryCommit( false );
		txtCodUnid.setTabelaExterna( lcUnid );
				
		adicCampo( txtCodEstAnalise, 7, 20, 70, 20, "CODESTANALISE", "Cód.Est.An.", ListaCampos.DB_PK, true );	
		adicCampo( txtCodTpAnalise, 80, 20, 70, 20, "CodTpAnalise", "Cód.Tp.An", ListaCampos.DB_FK, txtDescTpAnalise, true );
		adicDescFK( txtDescTpAnalise, 155, 20, 300, 20, "DescTpAnalise", "Descrição da análise" );
		
/*		adic( txtCodUnid, 458, 20, 50, 20 );
		adic( txtCasasDec, 511, 20, 50, 20 );*/
		
		adicDescFKInvisivel( txtTpExp, "TipoExpec", "TipoExpec" );
		adicCampo( txtVlrMin, 7, 65, 70, 20, "VlrMin", "Vlr.Min.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrMax, 80, 65, 70, 20, "VlrMax", "Vlr.Máx.", ListaCampos.DB_SI, false );
		adicCampo( txtEspecificacao, 153, 65, 300, 20, "Especificacao", "Especificação", ListaCampos.DB_SI, false );
		adicDB( cbEmitCert, 456, 65, 100, 20, "EmitCert", "", true );
				
		setListaCampos( true, "ESTRUANALISE", "PP" );
		lcDetEstrAnalise.setQueryInsert( true );
		
		lcDetEstrAnalise.setTabela( tabQuali );
		lcDetEstrAnalise.montaTab();
		
		lcDetEstrAnalise.setSQLMax( "SELECT MAX(CODESTANALISE) FROM PPESTRUANALISE WHERE CODEMP=? AND CODFILIAL=? " );
		
		//lcDetEstrAnalise.add( new GuardaCampo( txtCodProdEst, "CodProd", "Cód.prod.", ListaCampos.DB_FK, false ));
		 
		// fim controle de qualidade
		
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		setImprimir( true );
		tpnAbas.addChangeListener( this );

		lcCampos.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcDet.addPostListener( this );
		lcProdEst.addCarregaListener( this );
		lcProdItem.addCarregaListener( this );
		lcProdItemRef.addCarregaListener( this );
		lcDetDistrib.addCarregaListener( this );
		lcFase.addCarregaListener( this );
		lcTpAnalise.addCarregaListener( this );
		lcUnid.addCarregaListener( this );
		lcDetEstrAnalise.addPostListener( this );

		tabQuali.setTamColuna( 250, 2 );
		
		tab.setTamColuna( 35, 0 );
		tab.setTamColuna( 50, 1 );
		tab.setTamColuna( 230, 2 );
		tab.setTamColuna( 80, 3 );
		tab.setTamColuna( 70, 4 );
		tab.setTamColuna( 0, 5 );
		tab.setTamColuna( 60, 6 );
		tab.setTamColuna( 0, 7 );
		
		tab.setColunaInvisivel( 5 );
		tab.setColunaInvisivel( 7 );

//		Tabela Itens X Fase
		
		tabItens.setTamColuna( 35, 0 );
		tabItens.setTamColuna( 60, 1 );
		tabItens.setTamColuna( 340, 2 );
		tabItens.setTamColuna( 80, 3 );
		tabItens.setTamColuna( 40, 4 );
		tabItens.setTamColuna( 75, 5 );

//		Tabela Distribuicao X Fase		
		
		tabDist.setTamColuna( 35, 0 );
		tabDist.setTamColuna( 60, 1 );
		tabDist.setTamColuna( 50, 2 );
		tabDist.setTamColuna( 370, 3 );
		tabItens.setTamColuna( 75, 4 );

		cbRmaAutoItEst.setEnabled( false );
		setAltDet( 190 );
		navRod.setListaCampos( lcDet );
		lcDet.setNavegador( navRod );
		nav.setListaCampos( lcCampos );
		lcCampos.setNavegador( nav );

	}
	
	private void imprimirTexto(ResultSet rs, boolean visualizar, boolean resumido) {
		
		ImprimeOS imp = null;
		int linPag = 0;
		imp = new ImprimeOS( "", con );
		linPag = imp.verifLinPag() - 1;
		imp.montaCab();
		imp.setTitulo( "Relatório de Estrutura do Produto" );
		imp.limpaPags();
				
		try {
			
			if(resumido) {
			
				while ( rs.next() ) {
					
					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
						imp.say( imp.pRow() + 1, 0, "| Cód.prod." );
						imp.say( imp.pRow() + 0, 13, "| Descrição do produto" );
						imp.say( imp.pRow() + 0, 50, "| Seq.Est." );
						imp.say( imp.pRow() + 0, 60, "| Qtd." );
						imp.say( imp.pRow() + 0, 70, "| Descrição" );
						imp.say( imp.pRow() + 0, 101, "| Ativo" );
						imp.say( imp.pRow() + 0, 110, "| Mod.Lote" );
						imp.say( imp.pRow() + 0, 121, "| Validade" );
						imp.say( imp.pRow() + 0, 135, "|" );
					}
					
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "| " + rs.getString( "CodProd" ) );
					imp.say( imp.pRow() + 0, 13, "| " + rs.getString( "DescProd" ).substring( 0, 34 ) );
					imp.say( imp.pRow() + 0, 50, "| " + rs.getString( "SeqEst" ) );
					imp.say( imp.pRow() + 0, 60, "| " + rs.getString( "QtdEst" ) );
					imp.say( imp.pRow() + 0, 70, "| " + rs.getString( "DescEst" ).substring( 0, 29 ) );
					imp.say( imp.pRow() + 0, 100, "| " + ( rs.getString( "AtivoEst" ).equals( "S" ) ? "Sim" : "Não" ) );
					imp.say( imp.pRow() + 0, 110, "| " + rs.getString( "CodModLote" ) );
					imp.say( imp.pRow() + 0, 121, "| " + rs.getString( "NroDiasValid" ) + " Dias" );
					imp.say( imp.pRow() + 0, 135, "|" );
		
					if ( imp.pRow() >= linPag ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );
						imp.incPags();
						imp.eject();
					}
				}
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );
		
			}
			else {
				int seqest = 0;
				int cont = 0;
				while ( rs.next() ) {
					
					
					String sCodProd = txtCodProdEst.getVlrString();

					if ( imp.pRow() >= linPag ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );
						imp.incPags();
						imp.eject();
					}
					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( " ", 133 ) + "|" );
					}
					if ( (!sCodProd.equals( rs.getString( 1 ) )) || (seqest!=rs.getInt( "SEQEST" )) ) {
						cont = 0;
						sCodProd = rs.getString( 1 );
						seqest = rs.getInt( "SEQEST" );
					}
					if ( sCodProd.equals( rs.getString( 1 ) ) && cont == 0 ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );
						
						if(comRef()) {
							imp.say( imp.pRow() + 1, 0, "| Referência" );
						}
						else {
							imp.say( imp.pRow() + 1, 0, "| Cód.prod." );
						}
						
						imp.say( imp.pRow() + 0, 13, "| Descrição do produto" );
						imp.say( imp.pRow() + 0, 50, "| Seq.Est." );
						imp.say( imp.pRow() + 0, 60, "| Qtd." );
						imp.say( imp.pRow() + 0, 70, "| Descrição" );
						imp.say( imp.pRow() + 0, 101, "| Ativo" );
						imp.say( imp.pRow() + 0, 110, "| Mod.Lote" );
						imp.say( imp.pRow() + 0, 121, "| Validade" );
						imp.say( imp.pRow() + 0, 135, "|" );
						
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );

						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						
						if(comRef()) {
							imp.say( imp.pRow() + 0, 0, "| " + rs.getString( "REFPROD" ).trim() );
						}
						else{
							imp.say( imp.pRow() + 0, 0, "| " + rs.getString( "CODPROD" ) );
						}
						
						imp.say( imp.pRow() + 0, 13, "| " + rs.getString( "DESCPROD" ).substring( 0, 34 ) );
						imp.say( imp.pRow() + 0, 50, "| " + rs.getString( "SEQEST" ) );
						imp.say( imp.pRow() + 0, 60, "| " + rs.getString( "QTDEST" ) );
						imp.say( imp.pRow() + 0, 70, "| " + rs.getString( "DESCEST" ).substring( 0, 29 ) );
						imp.say( imp.pRow() + 0, 100, "| " + ( rs.getString( "ATIVOEST" ).equals( "S" ) ? "Sim" : "Não" ) );
						imp.say( imp.pRow() + 0, 110, "| " + rs.getString( "CODMODLOTE" )==null ? "" : rs.getString( "CODMODLOTE" ) );
						imp.say( imp.pRow() + 0, 121, "| " + rs.getString( "NRODIASVALID" )==null ? "" :  ( rs.getString( "NRODIASVALID" ) + " Dias" ) );
						imp.say( imp.pRow() + 0, 135, "|" );
						
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
						
						imp.say( imp.pRow() + 1, 0, "| Item" );
						imp.say( imp.pRow() + 0, 8, "| Cod.prod" );
						imp.say( imp.pRow() + 0, 20, "| Descrição do produto" );
						imp.say( imp.pRow() + 0, 60, "| Qtd." );
						imp.say( imp.pRow() + 0, 70, "| Cod.fase" );
						imp.say( imp.pRow() + 0, 80, "| Descrição da fase" );
						imp.say( imp.pRow() + 0, 123, "| Auto Rma" );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
						cont++;
					}
					
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					
					imp.say( imp.pRow() + 0, 0, "| " + rs.getString( "SEQITEST" ) );
					if(comRef()) {
						imp.say( imp.pRow() + 0, 8, "| " + rs.getString( "REFPRODPD" ).trim() );
					}
					else {
						imp.say( imp.pRow() + 0, 8, "| " + rs.getString( "CODPRODPD" ) );
					}
					imp.say( imp.pRow() + 0, 20, "| " + rs.getString( "DESCPRODPD" ).substring( 0, 38 ) );
					imp.say( imp.pRow() + 0, 60, "| " + rs.getString( "QTDITEST" ) );
					imp.say( imp.pRow() + 0, 70, "| " + rs.getString( "CODFASE" ) );
					imp.say( imp.pRow() + 0, 80, "| " + rs.getString( "DESCFASE" ).substring( 0, 38 ) );
					imp.say( imp.pRow() + 0, 123, "|   " + ( rs.getString( "RMAAUTOITEST" ).equals( "S" ) ? "Sim" : "Não" ) );
					imp.say( imp.pRow() + 0, 135, "|" );
				}
				
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );

			}
			
			imp.eject();
			imp.fechaGravacao();

			con.commit();
			
			if ( visualizar )
				imp.preview( this );
			else
				imp.print();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sWhere = "";
		String[] sValores;
		DLREstrutura dl = null;
		

		try {
			
			dl = new DLREstrutura();
			dl.setVisible( true );
			
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}
			
			sValores = dl.getValores(comRef());
			
			boolean resumido = "R".equals( sValores[ 1 ] );
			
			if(resumido && sValores[ 3 ].equals( "G" ) ) { 
				Funcoes.mensagemInforma( this, "Não existe modelo de relatório resumido para o tipo de impressão gráfica!" );
				return;
			}
			
			dl.dispose();

			if ( "A".equals( sValores[ 2 ] )) {

				if(txtCodProdEst.getVlrInteger()>0) {
					sWhere += " AND E.CODPROD=" + txtCodProdEst.getVlrString();				
					sWhere += " AND E.SEQEST=" + txtSeqEst.getVlrString();
				}
				else {
					Funcoes.mensagemInforma( this, "Nenhum estrutura selecionada para impressão!" );
					return;
				}
			
			}

			if ( resumido ) {

				try {

					sSQL = "SELECT E.CODPROD, E.REFPROD, PD.DESCPROD, E.SEQEST, E.QTDEST, E.DESCEST, E.ATIVOEST, E.CODMODLOTE, E.NRODIASVALID " 
						+ "FROM PPESTRUTURA E, EQPRODUTO PD " 
						+ "WHERE PD.CODEMP=E.CODEMP AND PD.CODFILIAL=E.CODFILIAL AND E.CODPROD=PD.CODPROD " 
						+ sWhere + " ORDER BY " + sValores[ 0 ] + ",E.SEQEST";

					ps = con.prepareStatement( sSQL );
					rs = ps.executeQuery();
					
				} 
				catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro consulta tabela de estrutura do produto!\n" + err.getMessage(), true, con, err );
				}
			}
			else if ( sValores[ 1 ].equals( "C" ) ) {

				sSQL = "SELECT E.CODPROD, E.REFPROD, PD.DESCPROD, IT.REFPRODPD, E.SEQEST, E.QTDEST, E.DESCEST, E.ATIVOEST, " 
					 + "E.CODMODLOTE, E.NRODIASVALID, IT.SEQITEST, IT.CODPRODPD, PI.DESCPROD DESCPRODPD, IT.QTDITEST, " 
					 + "IT.CODFASE, F.DESCFASE, IT.RMAAUTOITEST "
						+ "FROM PPESTRUTURA E, PPITESTRUTURA IT, EQPRODUTO PD, EQPRODUTO PI, PPFASE F " 
						+ "WHERE E.CODPROD=PD.CODPROD AND E.CODEMP=PD.CODEMP AND E.CODFILIAL=PD.CODFILIAL " 
						+ "AND IT.CODPROD=E.CODPROD AND IT.SEQEST=E.SEQEST AND IT.CODEMP=E.CODEMP AND IT.CODFILIAL=E.CODFILIAL "
						+ "AND IT.CODPRODPD=PI.CODPROD AND IT.CODEMPPD=PI.CODEMP AND IT.CODFILIALPD=PI.CODFILIAL " 
						+ "AND IT.CODFASE=F.CODFASE AND IT.CODEMPFS=F.CODEMP AND IT.CODFILIALFS=F.CODFILIAL " 
						+ sWhere + " ORDER BY " + sValores[ 0 ] + " ";

				

				try {

					ps = con.prepareStatement( sSQL );
					rs = ps.executeQuery();
				
				} 
				catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro consulta tabela de insumos do produto!\n" + err.getMessage(), true, con, err );
				}
			}

			System.out.println("SQL:" + sSQL );
			
			if ( sValores[ 3 ].equals( "T" ) ) {
				imprimirTexto(rs, bVisualizar, resumido);
			}
			else {
				imprimirGrafico(bVisualizar, rs, comRef(), resumido );
			}
						
		} 
		finally {
			ps = null;
			rs = null;
			sSQL = null;
			sWhere = null;
			sValores = null;
			dl = null;			
		}

	}
	
	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final boolean bComRef, boolean resumido ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "COMREF", bComRef ? "S" : "N" );
		
		FPrinterJob dlGr = null;
		
		dlGr = new FPrinterJob( "layout/rel/REL_ESTRUTURA_01.jasper", "Relação de componentes do produto", "", rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} 
			catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de extruturas!" + err.getMessage(), true, con, err );
			}
		}
	}
	

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp )
			imprimir( true );
		else if ( evt.getSource() == btImp )
			imprimir( false );
		super.actionPerformed( evt );
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( ( (JTabbedPanePad) ( cevt.getSource() ) ) == tpnAbas ) {
			if ( tpnAbas.getSelectedIndex() == 0 ) {
				setAltDet( 200 );
				pnDet.removeAll();
				setPainel( pinDetFases, pnDet );
				setListaCampos( lcDet );
				pnDet.repaint();
				navRod.setListaCampos( lcDet );
				navRod.setAtivo( 6, true );
			}
			else if ( tpnAbas.getSelectedIndex() == 1 ) {
				setAltDet( 110 );
				pnDet.removeAll();
				setPainel( pinDetItens, pnDet );
				setListaCampos( lcDetItens );
				pnDet.repaint();
				navRod.setListaCampos( lcDetItens );
				navRod.setAtivo( 6, true );
			}
			else if ( tpnAbas.getSelectedIndex() == 2 ) {
				setAltDet( 110 );
				pnDet.removeAll();
				setPainel( pinDetEstrAnalise, pnDet );
				setListaCampos( lcDetItens );
				pnDet.repaint();
				navRod.setListaCampos( lcDetEstrAnalise );
				navRod.setAtivo( 6, true );
			}
			else if ( tpnAbas.getSelectedIndex() == 3 ) {
				setAltDet( 110 );
				pnDet.removeAll();
				setPainel( pinDetDistrib, pnDet );
				setListaCampos( lcDetDistrib );
				pnDet.repaint();
				navRod.setListaCampos( lcDetDistrib );
				navRod.setAtivo( 6, false );
			}
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcProdItem || cevt.getListaCampos() == lcProdItemRef ) {
			String sRma = txtRMA.getVlrString();
			if ( sRma.equals( "S" ) )
				cbRmaAutoItEst.setEnabled( true );
			else
				cbRmaAutoItEst.setEnabled( false );
		}
		else if ( cevt.getListaCampos() == lcProdEst ) {
			if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
				txtCodModLote.setAtivo( true );
				txtNroDiasValid.setAtivo( true );
			}
			else if("S".equals(txtSerieProd.getVlrString())) {
				txtNroDiasValid.setAtivo( true );
			}
			else {
				txtCodModLote.setAtivo( false );
				txtNroDiasValid.setAtivo( false );
			}
		}
		else if ( cevt.getListaCampos() == lcDet ) {
			bloqueiaAbas();
		}
		if( cevt.getListaCampos() == lcTpAnalise ){
			if( "MM".equals( txtTpExp.getVlrString())){
				txtVlrMin.setEnabled( true );
				txtVlrMax.setEnabled( true );
				txtVlrMin.setRequerido( true );
				txtVlrMax.setRequerido( true );
				txtEspecificacao.setEnabled( false ); 
				txtEspecificacao.setRequerido( false );								
			}
			else if( "DT".equals( txtTpExp.getVlrString())){
				txtVlrMin.setEnabled( false );
				txtVlrMax.setEnabled( false );
				txtVlrMin.setRequerido( false );
				txtVlrMax.setRequerido( false );
				txtEspecificacao.setEnabled( true );
				txtEspecificacao.setRequerido( true );				
			}
		}
		if( cevt.getListaCampos() == lcUnid ){				
			txtVlrMin.setDecimal( txtCasasDec.getVlrInteger() );
			txtVlrMax.setDecimal( txtCasasDec.getVlrInteger() );
		}

		
	}

	private void bloqueiaAbas() {
		
		if ( cbFinaliza.getStatus() ) {
			tpnAbas.setEnabledAt( 3, true );
		}
		else {
			tpnAbas.setEnabledAt( 3, false );
		}
		
		if(txtTipoFase.getVlrString().equals( "CQ" )){
			tpnAbas.setEnabledAt( 2, true );
			cbCProva.setEnabled( true );
		}
		else {
			tpnAbas.setEnabledAt( 2, false );
			cbCProva.setEnabled( false );
		}
		
	}
	
	public void beforeCarrega( CarregaEvent cevt ) {}

	public void afterPost( PostEvent pevt ) {

		if ( pevt.getListaCampos() == lcCampos ) {
			if ( tpnAbas.getSelectedIndex() != 0 )
				tpnAbas.setSelectedIndex( 1 );
		}
		else if ( pevt.getListaCampos() == lcDet ) {
			bloqueiaAbas();
		}
	}
	
	public void beforePost( PostEvent pevt ) {

		super.beforePost( pevt );
		
		if( pevt.getListaCampos() == lcDetEstrAnalise ){
			if( "MM".equals( txtTpExp.getVlrString())){
				if( txtVlrMin.getVlrString().equals( "0" )  || txtVlrMax.getVlrInteger().equals( "0" ) ){
					Funcoes.mensagemInforma( this, "Informe os valores!" );
					pevt.cancela();
				}
				
			}else if( "DT".equals( txtTpExp.getVlrString())){
				if("".equals( txtEspecificacao.getVlrString())){
					Funcoes.mensagemInforma( this, "Informe a descrição!" );
					pevt.cancela();
				}
			}	
		}
	}
	
	private boolean comRef(){
		return ((Boolean) prefere.get( "usarefprod" )).booleanValue(); 
	}
	
	private void getPreferencias() {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			prefere = new HashMap<String, Object>();

			sql.append( "select pf1.usarefprod from sgprefere1 pf1 ");
			sql.append( "where pf1.codemp=? and pf1.codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				prefere.put( "usarefprod", new Boolean("S".equals( rs.getString( "usarefprod" ))) );
			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		getPreferencias();
		
		montaTela();
		
		lcProdEst.setConexao( cn );
		lcProdItem.setConexao( cn );
		lcProdItemRef.setConexao( cn );
		lcModLote.setConexao( cn );
		lcTipoRec.setConexao( cn );
		lcFase.setConexao( cn );
		lcDetDistrib.setConexao( cn );
		lcDetItens.setConexao( cn );
		lcEstDistrib.setConexao( cn );
		lcTpAnalise.setConexao( cn ); 
		lcDetEstrAnalise.setConexao( cn );
		lcUnid.setConexao( cn );
		
		
		
	}
	
	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER && kevt.getSource()==txtQtdMat) {

			if ( lcDetItens.getStatus() == ListaCampos.LCS_INSERT ) {

				cbRmaAutoItEst.setVlrString( "S" );
				cbQtdVariavelItem.setVlrString( "S" );
				
				lcDetItens.post();
//				lcDetItens.limpaCampos( true );
				lcDetItens.insert( true );
				lcDetItens.setState( ListaCampos.LCS_NONE );
				
				if ( comRef() ) {
					txtRefProdItem.requestFocus();
				}
				else {
					txtCodProdItem.requestFocus();
				}
				
				
			}
		}
	}
	
	
	
}

