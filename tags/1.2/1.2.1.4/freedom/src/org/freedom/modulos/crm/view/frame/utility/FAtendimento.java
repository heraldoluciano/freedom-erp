/*
 * Projeto: Freedom Pacote: org.freedom.modules.crm Classe: @(#)FAtendimento.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR> na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR> Veja a Licença Pública Geral GNU para maiores detalhes. <BR> Você deve ter recebido uma cópia da Licença Pública
 * Geral GNU junto com este programa, se não, <BR> escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 */

package org.freedom.modulos.crm.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.functions.FuncoesCRM;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.atd.view.frame.crud.plain.FAtendente;
import org.freedom.modulos.crm.business.component.Atendimento;
import org.freedom.modulos.crm.business.object.Chamado;
import org.freedom.modulos.crm.business.object.Prioridade;
import org.freedom.modulos.crm.view.dialog.utility.DLNovoAtend;
import org.freedom.modulos.crm.view.frame.crud.plain.FChamado;
import org.freedom.modulos.crm.view.frame.report.FRAtendimentos;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

/**
 * 
 * @author Setpoint Informática Ltda. / Alex Rodrigues
 * @version 15/04/2010 - Anderson Sanchez
 * 
 */

public class FAtendimento extends FFilho implements CarregaListener, ActionListener, FocusListener, JComboBoxListener, KeyListener, ChangeListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private JTabbedPanePad tpnAbas = new JTabbedPanePad();

	private JPanelPad pinCli = new JPanelPad( );

	private JPanelPad pinFiltrosAtend = new JPanelPad( 510, 120 );
	
	private JPanelPad pinFiltrosChamado = new JPanelPad( 510, 120 );
	
	private JPanelPad pinFiltrosTitulo = new JPanelPad( 510, 120 );

	private JPanelPad pnAtd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnChm = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCabCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCabCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRodCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabatd = new JTablePad();

	private JTablePad tabchm = new JTablePad();
	
	private JTablePad tabstatus= new JTablePad();

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAtendenteAtendimento = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeAtendenteAtendimento = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodAtendenteChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeAtendenteChamado = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescChamado = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDDDCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtFoneCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDDDFax = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtFaxCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDDDCel = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtCelCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtEmailCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtContatoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtEndCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtCidCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtUfCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtNumCli = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtDatainiAtend = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimAtend = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatainiCham = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimCham = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNParcItRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDiasAtrazo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlrParcItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrApagItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrPagoItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextAreaPad txaObsItRec = new JTextAreaPad();

	private JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtPagoItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtVencItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtStatusItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JButtonPad btNovo = new JButtonPad( Icone.novo( "btNovo.gif" ) );
	
	private JButtonPad btAtualizaChamados = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );
	
	private JButtonPad btAtualizaAtendimentos = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );

	private JButtonPad btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btImprimir = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );

	private Vector<String> vCodAtends = new Vector<String>();

	private Vector<String> vCodChamados = new Vector<String>();

	private Vector<Integer> vValsContr = new Vector<Integer>();

	private Vector<String> vLabsContr = new Vector<String>();

	private JComboBoxPad cbContr = new JComboBoxPad( vLabsContr, vValsContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private Vector<Integer> vValsitContr = new Vector<Integer>();

	private Vector<String> vLabsitContr = new Vector<String>();

	private JComboBoxPad cbitContr = new JComboBoxPad( vLabsitContr, vValsitContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private Vector<Integer> vValsTipo = new Vector<Integer>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JComboBoxPad cbTipoAtend = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	private JComboBoxPad cbTpChamado = new JComboBoxPad( null, null, JComboBoxPad.TP_INTEGER, 4, 0 );
	
//	private JComboBoxPad cbStatus = new JComboBoxPad( null, null, JComboBoxPad.TP_STRING, 2, 0 );

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcAtendimento = new ListaCampos( this );

	private ListaCampos lcAtendenteAtendimento = new ListaCampos( this, "AE" );
	
	private ListaCampos lcAtendenteChamado = new ListaCampos( this, "AE" );
	
	private ListaCampos lcChamado = new ListaCampos( this, "CH" );

	private ListaCampos lcItRec = new ListaCampos( this );

	private ListaCampos lcRec = new ListaCampos( this );

	private boolean carregagrid = false;

	private String tipoatendo = null; // Tipo de atendimento

	private JPanelPad pnBotConv = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );
	
	private JComboBoxPad cbPrioridade = new JComboBoxPad( null, null, JComboBoxPad.TP_INTEGER, 4, 0 );

	private JScrollPane scpStatus = new JScrollPane( tabstatus );
	
	public enum GridChamado {
		DTCHAMADO, PRIORIDADE, DESCTPCHAMADO, CODCHAMADO, DESCCHAMADO, SOLICITANTE, STATUS, QTDHORASPREVISAO, DTPREVISAO, DTCONCLUSAO
	}

	// Construção padrão para tela de atendimento
	
	public FAtendimento() {

		super( false );

		setTitulo( "Atendimento" );
		setAtribos( 20, 20, 780, 650 );

		tipoatendo = "A"; // Setando o tipo de atendimento para "A" de atendimento;

		pnCabCli.setPreferredSize( new Dimension( 500, 155 ) );

		montaListaCamposAtend();
		montaTela();

		adicFiltrosAtend();
		adicFiltrosChamado();
		
	}
	
	private void adicFiltrosAtend() {
		
		pinFiltrosAtend.setBorder( SwingParams.getPanelLabel( "Filtros de atendimentos", Color.BLUE ) );

		pinFiltrosAtend.adic( new JLabelPad( "Data Inicial" ), 7, 0, 70, 20 );
		pinFiltrosAtend.adic( txtDatainiAtend, 7, 20, 70, 20 );

		pinFiltrosAtend.adic( new JLabelPad( "Data Final" ), 80, 0, 70, 20 );
		pinFiltrosAtend.adic( txtDatafimAtend, 80, 20, 70, 20 );

		pinFiltrosAtend.adic( new JLabelPad( "Cód.Atend." ), 153, 0, 70, 20 );
		pinFiltrosAtend.adic( txtCodAtendenteAtendimento, 153, 20, 70, 20 );
		
		pinFiltrosAtend.adic( new JLabelPad( "Nome do Atendente" ), 226, 0, 180, 20 );
		pinFiltrosAtend.adic( txtNomeAtendenteAtendimento, 226, 20, 180, 20 );

		pinFiltrosAtend.adic( new JLabelPad( "Cód.Cham." ), 409, 0, 70, 20 );
		pinFiltrosAtend.adic( txtCodChamado, 409, 20, 70, 20 );
		pinFiltrosAtend.adic( new JLabelPad( "Descrição do chamado" ), 481, 0, 230, 20 );
		pinFiltrosAtend.adic( txtDescChamado, 481, 20, 230, 20 );
		
		pinFiltrosAtend.adic( new JLabelPad( "Tipo" ), 7, 40, 215, 20 );
		pinFiltrosAtend.adic( cbTipoAtend, 7, 60, 215, 20 );

		pinFiltrosAtend.adic( new JLabelPad( "Contrato/Projeto" ), 226, 40, 252, 20 );
		pinFiltrosAtend.adic( cbContr, 226, 60, 252, 20 );

		pinFiltrosAtend.adic( new JLabelPad( "Item" ), 481, 40, 230, 20 );
		pinFiltrosAtend.adic( cbitContr, 481, 60, 230, 20 );
		
		pinFiltrosAtend.adic( btAtualizaAtendimentos, 715, 15, 30, 30 );

		pnAtd.add( pinFiltrosAtend, BorderLayout.NORTH );
		
		JPanelPad pnGridAtd = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout());
		
		JScrollPane scpAtd = new JScrollPane( tabatd );
		
		pnGridAtd.add( scpAtd, BorderLayout.CENTER );
		
		pnAtd.add( pnGridAtd, BorderLayout.CENTER );
		
	}
	
	private void adicFiltrosChamado() {

		pinFiltrosChamado.setBorder( SwingParams.getPanelLabel( "Filtros de chamados", Color.BLUE ) );

		pinFiltrosChamado.adic( new JLabelPad( "Data Inicial" ), 7, 0, 70, 20 );
		pinFiltrosChamado.adic( txtDatainiCham, 7, 20, 70, 20 );

		pinFiltrosChamado.adic( new JLabelPad( "Data Final" ), 80, 0, 70, 20 );
		pinFiltrosChamado.adic( txtDatafimCham, 80, 20, 70, 20 );

		
		pinFiltrosChamado.adic( new JLabelPad( "Cód.Atend." ), 153, 0, 70, 20 );
		pinFiltrosChamado.adic( txtCodAtendenteChamado, 153, 20, 70, 20 );

		pinFiltrosChamado.adic( new JLabelPad( "Nome do Atendente designado" ), 226, 0, 230, 20 );
		pinFiltrosChamado.adic( txtNomeAtendenteChamado, 226, 20, 230, 20 );

		pinFiltrosChamado.adic( new JLabelPad( "Tipo" ), 7, 40, 215, 20 );
		pinFiltrosChamado.adic( cbTpChamado, 7, 60, 215, 20 );		
		
		pinFiltrosChamado.adic( btAtualizaChamados, 715, 15, 30, 30 );
		
//		pinFiltrosChamado.adic( new JLabelPad( "Status" ), 225, 20, 110, 20 );
//		pinFiltrosChamado.adic( cbStatus, 225, 60, 110, 20 ); 
		
		pinFiltrosChamado.adic( new JLabelPad( "Prioridade" ), 226, 40, 110, 20 );
		pinFiltrosChamado.adic( cbPrioridade, 226, 60, 110, 20 );

		pinFiltrosChamado.adic( scpStatus, 459, 7, 140, 78 );				
		
		pnChm.add( pinFiltrosChamado, BorderLayout.NORTH );
		
		JPanelPad pnGridChm = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout());
		
		JScrollPane scpChm = new JScrollPane( tabchm );
		
		pnGridChm.add( scpChm, BorderLayout.CENTER );
		
		pnChm.add( pnGridChm, BorderLayout.CENTER );
		
	}

	

	// Construção padrão lançamento de contatos financeiro/cobrança
	
	public FAtendimento( Integer codcli, Integer codrec, Integer nparcitrec, boolean isUpdate ) {

		super( false );

		setTitulo( "Atendimento" );
		setAtribos( 20, 20, 780, 500 );

		tipoatendo = "C"; // Setando o tipo de atendimento para "C" de Contato;

		pnCabCli.setPreferredSize( new Dimension( 500, 155 ) );

		setConexao( Aplicativo.getInstace().getConexao() );

		montaListaCamposAtend();

		montaTela();

		txtCodCli.setVlrInteger( codcli );
		txtCodRec.setVlrInteger( codrec );
		txtNParcItRec.setVlrInteger( nparcitrec );

		pinFiltrosTitulo.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Título" ) );
		pinCabCli.adic( pinFiltrosTitulo, 8, 175, 744, 116 );

		montaListaCamposFinanc();
		
		adicCamposFinanc();

		ativaCamposFinanc();

		lcCli.carregaDados();
		lcRec.carregaDados();
		lcItRec.carregaDados();
		
		tpnAbas.setEnabled( false );

		calcAtrazo();

	}
	
	private void adicCamposFinanc() {
		
		pinFiltrosTitulo.setBorder( SwingParams.getPanelLabel( "Título", Color.BLUE ) );
		
		pinFiltrosTitulo.adic( new JLabelPad( "Cód.rec" ), 7, 0, 70, 20 );
		pinFiltrosTitulo.adic( txtCodRec, 7, 20, 70, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Parc." ), 80, 0, 40, 20 );
		pinFiltrosTitulo.adic( txtNParcItRec, 80, 20, 40, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Venda" ), 123, 0, 50, 20 );
		pinFiltrosTitulo.adic( txtCodVenda, 123, 20, 50, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Doc." ), 176, 0, 50, 20 );
		pinFiltrosTitulo.adic( txtDoc, 176, 20, 50, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Emissão" ), 229, 0, 70, 20 );
		pinFiltrosTitulo.adic( txtDtEmis, 229, 20, 70, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Vencimento" ), 302, 0, 70, 20 );
		pinFiltrosTitulo.adic( txtDtVencItRec, 302, 20, 70, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Atrazo" ), 375, 0, 40, 20 );
		pinFiltrosTitulo.adic( txtDiasAtrazo, 375, 20, 40, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Obs." ), 421, 0, 150, 20 );
		pinFiltrosTitulo.adic( txaObsItRec, 421, 20, 303, 60 );
		pinFiltrosTitulo.adic( new JLabelPad( "Valor do título" ), 7, 40, 113, 20 );
		pinFiltrosTitulo.adic( txtVlrParcItRec, 7, 60, 113, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Total pago" ), 123, 40, 104, 20 );
		pinFiltrosTitulo.adic( txtVlrPagoItRec, 123, 60, 104, 20 );
		pinFiltrosTitulo.adic( new JLabelPad( "Total em aberto" ), 229, 40, 110, 20 );
		pinFiltrosTitulo.adic( txtVlrApagItRec, 229, 60, 110, 20 );

		pnAtd.add( pinFiltrosTitulo, BorderLayout.NORTH );
		
		JPanelPad pnGridFinanc = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout());
		
		JScrollPane scpFinanc = new JScrollPane( tabatd );
		
		pnGridFinanc.add( scpFinanc, BorderLayout.CENTER );
		
		pnAtd.add( pnGridFinanc, BorderLayout.CENTER );
		
	}
	
	private void ativaCamposFinanc() {
		txtCodRec.setAtivo( false );
		txtNParcItRec.setAtivo( false );
		txtCodVenda.setAtivo( false );
		txtDoc.setAtivo( false );
		txtDtEmis.setAtivo( false );
		txtDtVencItRec.setAtivo( false );
		txtDiasAtrazo.setAtivo( false );
		txaObsItRec.setAtivo( false );

		txtVlrApagItRec.setAtivo( false );
		txtVlrPagoItRec.setAtivo( false );
		txtVlrParcItRec.setAtivo( false );
		
		txtCodRec.setEnabled( false );
		txtCodCli.setEnabled( false );
		txtNParcItRec.setEnabled( false );

	}
	
	private void montaListaCamposFinanc() {
		
		lcRec.add( new GuardaCampo( txtCodRec, "CodRec", "Cód.rec.", ListaCampos.DB_PK, false ) );
		lcRec.add( new GuardaCampo( txtCodVenda, "CodVenda", "Cód.venda", ListaCampos.DB_SI, false ) );
		lcRec.add( new GuardaCampo( txtDoc, "DocRec", "Doc", ListaCampos.DB_SI, false ) );
		lcRec.add( new GuardaCampo( txtDtEmis, "DataRec", "Emissão", ListaCampos.DB_SI, false ) );

		lcRec.montaSql( false, "RECEBER", "FN" );
		lcRec.setQueryCommit( false );
		lcRec.setReadOnly( true );
		txtCodRec.setTabelaExterna( lcRec, null );
		txtCodRec.setFK( true );

		lcItRec.add( new GuardaCampo( txtCodRec, "CodRec", "Cód.rec.", ListaCampos.DB_PK, false ) );
		lcItRec.add( new GuardaCampo( txtNParcItRec, "NParcItRec", "Cód.It.rec.", ListaCampos.DB_PK, false ) );
		lcItRec.add( new GuardaCampo( txtVlrParcItRec, "VlrParcItRec", "Vlr.titulo", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtVlrApagItRec, "VlrApagItRec", "Vlr.Aberto", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtVlrPagoItRec, "VlrPagoItRec", "Vlr.Pago", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtDtVencItRec, "DtVencItRec", "Vencimento", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtDtPagoItRec, "DtPagoItRec", "Dt.Pagto.", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtStatusItRec, "StatusItRec", "Status", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txaObsItRec, "ObsItRec", "Observaçoes", ListaCampos.DB_SI, false ) );

		lcItRec.montaSql( false, "ITRECEBER", "FN" );
		lcItRec.setQueryCommit( false );
		lcItRec.setReadOnly( true );
		txtCodRec.setTabelaExterna( lcItRec, null );
		txtCodRec.setFK( true );

		
	}

	private void calcAtrazo() {

		Integer atrazo = 0;

		if ( "R1".equals( txtStatusItRec.getVlrString() ) || "RL".equals( txtStatusItRec.getVlrString() ) ) {
			atrazo = ( (Long) Funcoes.getNumDias( txtDtVencItRec.getVlrDate(), new Date() ) ).intValue();
			if ( atrazo.compareTo( new Integer( 0 ) ) > 0 ) {
				txtDiasAtrazo.setVlrInteger( atrazo );
			}
			else {
				txtDiasAtrazo.setVlrInteger( new Integer( 0 ) );
			}
		}
		if ( "RP".equals( txtStatusItRec.getVlrString() ) ) {
			atrazo = ( (Long) Funcoes.getNumDias( txtDtVencItRec.getVlrDate(), txtDtPagoItRec.getVlrDate() ) ).intValue();
			if ( atrazo.compareTo( new Integer( 0 ) ) > 0 ) {
				txtDiasAtrazo.setVlrInteger( atrazo );
			}
			else {
				txtDiasAtrazo.setVlrInteger( new Integer( 0 ) );
			}
		}
	}

	private void montaListaCamposAtend() {

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDFax, "DDDFaxCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtFaxCli, "FaxCli", "Fax", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDCel, "DDDCelCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCelCli, "CelCli", "Fax", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtEmailCli, "EmailCli", "Email", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtContatoCli, "ContCli", "Contato", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtEndCli, "EndCli", "Endereço", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCidCli, "CidCli", "Cidade", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtUfCli, "UFCli", "UF", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtNumCli, "NumCli", "Número", ListaCampos.DB_SI, false ) );
		lcCli.setWhereAdic( "ATIVOCLI='S'" );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		lcAtendimento.add( new GuardaCampo( txtCodAtendo, "CodAtendo", "Cód.Atendo", ListaCampos.DB_PK, false ) );
		lcAtendimento.montaSql( false, "ATENDIMENTO", "AT" );
		lcAtendimento.setReadOnly( true );
		txtCodAtendo.setTabelaExterna( lcAtendimento, null );
		txtCodAtendo.setFK( true );
		txtCodAtendo.setNomeCampo( "CodAtendo" );

		// Atendimento para funcionamento 
		txtCodAtendenteAtendimento.setTabelaExterna( lcAtendenteAtendimento, FAtendente.class.getCanonicalName() );
		txtCodAtendenteAtendimento.setFK( true );
		txtCodAtendenteAtendimento.setNomeCampo( "CodAtend" );
		lcAtendenteAtendimento.add( new GuardaCampo( txtCodAtendenteAtendimento, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ) );
		lcAtendenteAtendimento.add( new GuardaCampo( txtNomeAtendenteAtendimento, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtendenteAtendimento.montaSql( false, "ATENDENTE", "AT" );
		lcAtendenteAtendimento.setReadOnly( true );
		
		txtCodAtendenteChamado.setTabelaExterna( lcAtendenteChamado, FAtendente.class.getCanonicalName() );
		txtCodAtendenteChamado.setFK( true );
		txtCodAtendenteChamado.setNomeCampo( "CodAtend" );
		lcAtendenteChamado.add( new GuardaCampo( txtCodAtendenteChamado, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ) );
		lcAtendenteChamado.add( new GuardaCampo( txtNomeAtendenteChamado, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtendenteChamado.montaSql( false, "ATENDENTE", "AT" );
		lcAtendenteChamado.setReadOnly( true );

		txtCodChamado.setTabelaExterna( lcChamado, FChamado.class.getCanonicalName() );
		txtCodChamado.setFK( true );
		txtCodChamado.setNomeCampo( "CodChamado" );
		lcChamado.add( new GuardaCampo( txtCodChamado, "CodChamado", "Cód.Cham.", ListaCampos.DB_PK, false ) );
		lcChamado.add( new GuardaCampo( txtDescChamado, "DescChamado", "Descrição do chamado", ListaCampos.DB_SI, false ) );
		lcChamado.setDinWhereAdic( "CODCLI = #N", txtCodCli );
		lcChamado.montaSql( false, "CHAMADO", "CR" );
		lcChamado.setReadOnly( true );

	}

	private void montaGridAtend() {

		tabatd.adicColuna( "Atd." );
		tabatd.adicColuna( "Doc." );
		tabatd.adicColuna( "Status" );
		tabatd.adicColuna( "Data" );
		tabatd.adicColuna( "Data fim" );
		tabatd.adicColuna( "Ocorrencia" );
		tabatd.adicColuna( "" );
		tabatd.adicColuna( "Atendente" );
		tabatd.adicColuna( "Hora inicial" );
		tabatd.adicColuna( "Hora final" );
		tabatd.adicColuna( "chamado" );

		tabatd.setTamColuna( 0, 0 );
		tabatd.setTamColuna( 0, 1 );
		tabatd.setTamColuna( 0, 2 );
		tabatd.setTamColuna( 67, 3 ); // Data de início
		tabatd.setTamColuna( 0, 4 ); // Data de fim
		tabatd.setTamColuna( 400, 5 ); // Ocorrência
		tabatd.setTamColuna( 0, 6 );
		tabatd.setTamColuna( 140, 7 ); // Atendente
		tabatd.setTamColuna( 70, 8 );
		tabatd.setTamColuna( 70, 9 );


		tabatd.setColunaInvisivel( 0 );
		tabatd.setColunaInvisivel( 1 );
		tabatd.setColunaInvisivel( 2 );
		tabatd.setColunaInvisivel( 4 );
		tabatd.setColunaInvisivel( 6 );
		tabatd.setColunaInvisivel( 10 );

		tabatd.setRowHeight( 20 );
	}
	
	private void montaGridStatus() {

		tabstatus.adicColuna( "" );
		tabstatus.adicColuna( "Cod." );
		tabstatus.adicColuna( "Status" );

		tabstatus.setTamColuna( 10, 0 );
		
		tabstatus.setColunaInvisivel( 1 );
		
		tabstatus.setTamColuna( 100, 2 );

		tabstatus.setRowHeight( 12 );
		
		tabstatus.setColunaEditavel( 0, new Boolean(true) );
		
	}
	
	private void carregaStatus() {
		
		Vector<Object> valores = Chamado.getValores();
		Vector<String> labels = Chamado.getLabels( );
		
		Vector<Object> item = null;
		
		for(int i = 1 ; i < valores.size(); i++) { // Começa em um para não carregar o item <--Selecione-->
			
			item = new Vector<Object>();
			
			item.addElement( new Boolean(true) );
			item.addElement( valores.elementAt( i ) );
			item.addElement( labels.elementAt( i ) );
			
			tabstatus.adicLinha( item );
			
		}
		

		
			
	}


	private void montaGridChamado() {
		
		tabchm.adicColuna( "Data" );
		tabchm.adicColuna( "Pri." );
		tabchm.adicColuna( "Tipo" );
		tabchm.adicColuna( "Cód." );
		tabchm.adicColuna( "Descrição" );
		tabchm.adicColuna( "Solicitante" );
		tabchm.adicColuna( "St." );
		tabchm.adicColuna( "Qtd.Prev." );
		tabchm.adicColuna( "Dt.Prev." );
		tabchm.adicColuna( "Dt.Concl." );

		tabchm.setTamColuna( 60, GridChamado.DTCHAMADO.ordinal() );
		tabchm.setTamColuna( 30, GridChamado.PRIORIDADE.ordinal() );
		tabchm.setTamColuna( 100, GridChamado.DESCTPCHAMADO.ordinal() );
		tabchm.setTamColuna( 50, GridChamado.CODCHAMADO.ordinal() );
		tabchm.setTamColuna( 250, GridChamado.DESCCHAMADO.ordinal() );
		tabchm.setTamColuna( 80, GridChamado.SOLICITANTE.ordinal() );
		tabchm.setTamColuna( 25, GridChamado.STATUS.ordinal() );
		tabchm.setTamColuna( 40, GridChamado.QTDHORASPREVISAO.ordinal() );
		tabchm.setTamColuna( 60, GridChamado.DTPREVISAO.ordinal() );
		tabchm.setTamColuna( 60, GridChamado.DTCONCLUSAO.ordinal() );
		
		tabchm.setRowHeight( 20 );

	}

	private void formataMascaras() {

		txtFoneCli.setMascara( JTextFieldPad.MC_FONE );
		txtFaxCli.setMascara( JTextFieldPad.MC_FONE );
		txtCelCli.setMascara( JTextFieldPad.MC_FONE );

	}

	private void adicionaAbas() {

		tpnAbas.setTabLayoutPolicy( JTabbedPanePad.SCROLL_TAB_LAYOUT );

		tpnAbas.setPreferredSize( new Dimension( 600, 30 ) );

		tpnAbas.setTabPlacement( SwingConstants.BOTTOM );

		tpnAbas.addTab( "Atendimentos", pnAtd );

		tpnAbas.addTab( "Chamados", pnChm );

	}
	
	private void adicionaFiltroCli() {

		pinCabCli.add( pinCli, BorderLayout.CENTER );
		pinCli.setBorder( SwingParams.getPanelLabel( "Filtro de cliente", Color.blue ) );

		pinCli.adic( new JLabelPad( "Cód.Cli." ), 10, 0, 50, 20 );
		pinCli.adic( txtCodCli, 10, 20, 50, 20 );
		
		pinCli.adic( new JLabelPad( "Razão social do cliente" ), 63, 0, 200, 20 );
		pinCli.adic( txtRazCli, 63, 20, 412, 20 );
		
		pinCli.adic( new JLabelPad( "Contato" ), 478, 0, 237, 20 );
		pinCli.adic( txtContatoCli, 478, 20, 237, 20 );
		
		pinCli.adic( new JLabelPad( "DDD" ), 10, 40, 50, 20 );
		pinCli.adic( txtDDDCli, 10, 60, 40, 20 );
		
		pinCli.adic( new JLabelPad( "Telefone" ), 63, 40, 100, 20 );
		pinCli.adic( txtFoneCli, 63, 60, 100, 20 );
		
		pinCli.adic( new JLabelPad( "DDD" ), 166, 40, 50, 20 );
		pinCli.adic( txtDDDFax, 166, 60, 50, 20 );
		
		pinCli.adic( new JLabelPad( "Fax" ), 219, 40, 100, 20 );
		pinCli.adic( txtFaxCli, 219, 60, 100, 20 );
		
		pinCli.adic( new JLabelPad( "DDD" ), 322, 40, 50, 20 );
		pinCli.adic( txtDDDCel, 322, 60, 50, 20 );
		
		pinCli.adic( new JLabelPad( "Celular" ), 375, 40, 100, 20 );
		pinCli.adic( txtCelCli, 375, 60, 100, 20 );
		
		pinCli.adic( new JLabelPad( "Email" ), 478, 40, 237, 20 );
		pinCli.adic( txtEmailCli, 478, 60, 237, 20 );
		
		pinCli.adic( new JLabelPad( "Endereço" ), 10, 80, 362, 20 );
		pinCli.adic( txtEndCli, 10, 100, 362, 20 );
		
		pinCli.adic( new JLabelPad( "Numero" ), 375, 80, 100, 20 );
		pinCli.adic( txtNumCli, 375, 100, 100, 20 );
		
		pinCli.adic( new JLabelPad( "Cidade" ), 478, 80, 100, 20 );
		pinCli.adic( txtCidCli, 478, 100, 169, 20 );
		
		pinCli.adic( new JLabelPad( "UF" ), 650, 80, 60, 20 );
		pinCli.adic( txtUfCli, 650, 100, 65, 20 );
		
	}

	private void adicBotoes() {

		pnBotConv.setPreferredSize( new Dimension( 90, 30 ) );

		pnBotConv.add( btNovo );
		pnBotConv.add( btExcluir );
		pnBotConv.add( btImprimir );
		
	}

	private void montaTela() {

		getTela().add( pnCli, BorderLayout.CENTER );

		formataMascaras();

		pnCabCli.add( pinCabCli, BorderLayout.CENTER );
		pnCli.add( pnCabCli, BorderLayout.NORTH );
		pnCli.add( tpnAbas, BorderLayout.CENTER );

		adicionaAbas();

		adicionaFiltroCli();

		montaGridAtend();

		montaGridChamado();
		
		montaGridStatus();

		adicBotoes();
		
		adicRodape();

		adicListeners();
	
		txtDatainiAtend.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );
		txtDatafimAtend.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );

		txtDatainiCham.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ) , Funcoes.getAno( new Date() ) - 1 ) );
		txtDatafimCham.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );
		
		vValsContr.addElement( -1 );
		vLabsContr.addElement( "<Todos>" );
		cbContr.setItensGeneric( vLabsContr, vValsContr );

		vValsitContr.addElement( -1 );
		vLabsitContr.addElement( "<Todos>" );
		cbitContr.setItensGeneric( vLabsitContr, vValsitContr );

		vValsTipo.addElement( -1 );
		vLabsTipo.addElement( "<Todos>" );
		cbTipoAtend.setItensGeneric( vLabsTipo, vValsTipo );
		
//		cbStatus.setItens( Chamado.getLabels( ), Chamado.getValores( ) );
//		cbStatus.setVlrString( (String) Chamado.STATUS_PENDENTE.getValue() );
		
		cbPrioridade.setItens( Prioridade.getLabels( ), Prioridade.getValores( ) );
		
		carregaStatus();
		
	}
	
	private void adicListeners() {
		tpnAbas.addChangeListener( this );

		btNovo.addActionListener( this );
		btExcluir.addActionListener( this );
		btImprimir.addActionListener( this );
		btAtualizaChamados.addActionListener( this );
		btAtualizaAtendimentos.addActionListener( this );

		btSair.addActionListener( this );
		lcCli.addCarregaListener( this );
		lcChamado.addCarregaListener( this );
		lcAtendenteAtendimento.addCarregaListener( this );
		
		lcAtendenteChamado.addCarregaListener( this );
		
		txtDatafimAtend.addFocusListener( this );
		txtDatafimCham.addFocusListener( this );

		cbContr.addComboBoxListener( this );
		cbitContr.addComboBoxListener( this );
		cbTipoAtend.addComboBoxListener( this );
		txtCodCli.addKeyListener( this );
		txtCodAtendenteAtendimento.addKeyListener( this );
		
//		cbStatus.addComboBoxListener( this );
		cbTpChamado.addComboBoxListener( this );
		cbPrioridade.addComboBoxListener( this );
		
		tabatd.addMouseListener( this );
		tabchm.addMouseListener( this );


	}
	
	private void adicRodape() {
		pnRodCli.setBorder( SwingParams.loweredetched );
		
		pnRodCli.add( pnBotConv, BorderLayout.WEST );
		
		btSair.setPreferredSize( new Dimension( 110, 30 ) );

		pnRodCli.add( btSair, BorderLayout.EAST );
		
		pnCli.add( pnRodCli, BorderLayout.SOUTH );
	}
	
	private void visualizaCham() {
		
		FChamado chamado = null;    

		if ( Aplicativo.telaPrincipal.temTela( FChamado.class.getName() ) ) {
			chamado = (FChamado) Aplicativo.telaPrincipal.getTela( FChamado.class.getName() );
		}
		else {
			chamado = new FChamado();
			Aplicativo.telaPrincipal.criatela( "Chamado", chamado, con );
		}    	    		 
		
		chamado.exec( (Integer) tabchm.getValor( tabchm.getLinhaSel(), GridChamado.CODCHAMADO.ordinal() ), null );

	}
	

	private void visualizaAtend() {

		DLNovoAtend dl = null;
		String codatendo = (String) tabatd.getValor( tabatd.getLinhaSel(), 0 ).toString();
		String codatend = (String) tabatd.getValor( tabatd.getLinhaSel(), 6 ).toString();
		int icodAtend = Integer.parseInt( codatend );
		int icodAtendo = Integer.parseInt( codatendo );
		Integer codchamado = (Integer) tabatd.getValor( tabatd.getLinhaSel(), 10 );

		try {
			dl = new DLNovoAtend( txtCodCli.getVlrInteger(), this, true, con, icodAtendo, icodAtend, tipoatendo, codchamado );
			dl.setVisible( true );
			dl.dispose();

			if ( dl.OK ) {
				carregaAtendimentos();
			}
		} 
		catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar campos!" );
		}
	}
	

	private void carregaAtendimentos() {

		StringBuilder sql = new StringBuilder();

		if ( carregagrid ) {

			sql.append( "SELECT ATEND.CODATENDO,ATEND.DOCATENDO,ATEND.STATUSATENDO,ATEND.DATAATENDO,TA.DESCTPATENDO, " );
			sql.append( "ATEND.DATAATENDOFIN, ATEND.HORAATENDOFIN,ATEND.OBSATENDO, ATEND.CODATEND, " );
			sql.append( "A.NOMEATEND,ATEND.HORAATENDO, ATEND.CODCHAMADO FROM ATATENDIMENTO ATEND, ATTIPOATENDO TA, ATATENDENTE A WHERE " );
			sql.append( "TA.CODTPATENDO=ATEND.CODTPATENDO AND TA.CODEMP=ATEND.CODEMPTO AND TA.CODFILIAL=ATEND.CODFILIALTO " );
			sql.append( "AND A.CODATEND=ATEND.CODATEND AND A.CODEMP=ATEND.CODEMPAE AND A.CODFILIAL=ATEND.CODFILIALAE " );
			sql.append( "AND TA.TIPOATENDO=? " );
			
			if ( ! (txtCodRec.getVlrInteger() > 0) ) {
				sql.append( "AND ATEND.DATAATENDO BETWEEN ? AND ?" );
			}

			if ( txtCodCli.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPCL=? AND ATEND.CODFILIALCL=? AND ATEND.CODCLI=? " );
			}
			if ( cbContr.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPCT=? AND ATEND.CODFILIALCT=? AND ATEND.CODCONTR=? " );
			}
			if ( cbitContr.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODITCONTR=? " );
			}
			if ( cbTipoAtend.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPTO=? AND ATEND.CODFILIALTO=? AND ATEND.CODTPATENDO=? " );
			}
			if ( txtCodAtendenteAtendimento.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPAE=? AND ATEND.CODFILIALAE=? AND ATEND.CODATEND=? " );
			}			
			if ( txtCodChamado.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPCH=? AND ATEND.CODFILIALCH=? AND ATEND.CODCHAMADO=? " );
			}

			if ( txtCodRec.getVlrInteger() > 0 ) {
				sql.append( " AND EXISTS(SELECT CODREC FROM ATATENDIMENTOITREC IR " );
				sql.append( "WHERE IR.CODEMP=ATEND.CODEMP AND IR.CODFILIAL=ATEND.CODFILIAL " );
				sql.append( "AND IR.CODATENDO=ATEND.CODATENDO AND IR.CODEMPIR=? AND IR.CODFILIALIR=? " );
				sql.append( "AND IR.CODREC=? AND IR.NPARCITREC=?)" );
			}

			sql.append( "ORDER BY ATEND.DATAATENDO DESC,ATEND.HORAATENDO DESC " );

			try {
				int iparam = 1;

				PreparedStatement ps = con.prepareStatement( sql.toString() );
				
				ps.setString( iparam++, tipoatendo );
				
				if ( ! (txtCodRec.getVlrInteger() > 0) ) {
					ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatainiAtend.getVlrDate() ) );
					ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafimAtend.getVlrDate() ) );
				}
				

				if ( txtCodCli.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, lcCli.getCodEmp() );
					ps.setInt( iparam++, lcCli.getCodFilial() );
					ps.setInt( iparam++, txtCodCli.getVlrInteger() );
				}
				if ( cbContr.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDCONTRATO" ) );
					ps.setInt( iparam++, cbContr.getVlrInteger() );
				}
				if ( cbitContr.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, cbitContr.getVlrInteger() );
				}
				if ( cbTipoAtend.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
					ps.setInt( iparam++, cbTipoAtend.getVlrInteger() );
				}
				if ( txtCodAtendenteAtendimento.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, lcAtendenteAtendimento.getCodEmp() );
					ps.setInt( iparam++, lcAtendenteAtendimento.getCodFilial() );
					ps.setInt( iparam++, txtCodAtendenteAtendimento.getVlrInteger() );
				}
				if ( txtCodRec.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, lcRec.getCodEmp() );
					ps.setInt( iparam++, lcRec.getCodFilial() );
					ps.setInt( iparam++, txtCodRec.getVlrInteger() );
					ps.setInt( iparam++, txtNParcItRec.getVlrInteger() );
				}				
				if ( txtCodChamado.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "CHCHAMADO" ) );
					ps.setInt( iparam++, txtCodChamado.getVlrInteger() );
				}

				ResultSet rs = ps.executeQuery();

				tabatd.limpa();
				vCodAtends.clear();

				for ( int i = 0; rs.next(); i++ ) {
					tabatd.adicLinha();

					vCodAtends.add( "" + rs.getString( "CodAtendo" ) );
					tabatd.setValor( rs.getString( "CodAtendo" ), i, 0 );
					tabatd.setValor( rs.getString( "DocAtendo" ), i, 1 );
					tabatd.setValor( rs.getString( "StatusAtendo" ), i, 2 );
					tabatd.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DataAtendo" ) ), i, 3 );
					tabatd.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DataAtendoFin" ) ), i, 4 );
					tabatd.setValor( rs.getString( "OBSATENDO" ), i, 5 );
					tabatd.setValor( rs.getInt( "CODATEND" ), i, 6 );
					tabatd.setValor( rs.getString( "NomeAtend" ), i, 7 );
					tabatd.setValor( rs.getTime( "HoraAtendo" ).toString(), i, 8 );
					tabatd.setValor( rs.getTime( "HoraAtendoFin" ).toString(), i, 9 );
					tabatd.setValor( rs.getInt( "CODCHAMADO" ), i, 10 );

				}
				rs.close();
				ps.close();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar tabela de atendimento!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	private void carregaChamados() {

		StringBuilder sql = new StringBuilder();

		if ( carregagrid ) {

			sql.append( "select ch.dtchamado, ch.prioridade, ch.codchamado, ch.descchamado, ch.codcli, ch.solicitante, " );
			sql.append( "ch.status, ch.qtdhorasprevisao, ch.dtprevisao, ch.dtconclusao, tc.desctpchamado " );
			sql.append( "from crchamado ch, crtipochamado tc " );
			sql.append( "where tc.codemp=ch.codemptc and tc.codfilial=ch.codfilialtc and tc.codtpchamado=ch.codtpchamado " );
			sql.append( "and ch.codemp=? and ch.codfilial=? and dtchamado between ? and ? " );

			if ( txtCodCli.getVlrInteger() > 0 ) {
				sql.append( " and ch.codempcl=? and ch.codfilialcl=? and ch.codcli=? " );
			}			
			if ( cbTpChamado.getVlrInteger() > 0 ) { 
				sql.append( " and ch.codemptc=? and ch.codfilialtc=? and tc.codtpchamado=? " );
			}

			// Verifica os status selecionados 
			
			boolean primeiro = true;

			for(int i = 0; i< tabstatus.getNumLinhas(); i++ ) {
				
				if( (Boolean) tabstatus.getValor( i, 0 )) {
					
					if(primeiro) {
						sql.append( " and ch.status in (" );					
					}
					else {
						sql.append( "," );						
					}
					
					sql.append( "'" + tabstatus.getValor( i, 1 ) + "'" );
					
					primeiro = false;
				}
				
				if(i==tabstatus.getNumLinhas()-1 && !primeiro) {
					sql.append( " ) " );		
				}
				
			}
			
			if ( cbPrioridade.getVlrInteger() > 0 ) { 
				sql.append( " and ch.prioridade=? " );
			}
			if ( txtCodAtendenteChamado.getVlrInteger() > 0 ) { 
				sql.append( " and ch.codempae=? and ch.codfilialae=? and ch.codatend=? " );
			}

			sql.append( "order by ch.dtprevisao, ch.prioridade, ch.dtchamado, ch.status, ch.dtconclusao " );

			try {
				int param = 1;

				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "CRCHAMADO" ) );
				ps.setDate( param++, Funcoes.dateToSQLDate( txtDatainiCham.getVlrDate() ) );
				ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafimCham.getVlrDate() ) );

				if ( txtCodCli.getVlrInteger() > 0 ) {
					ps.setInt( param++, lcCli.getCodEmp() );
					ps.setInt( param++, lcCli.getCodFilial() );
					ps.setInt( param++, txtCodCli.getVlrInteger() );
				}

				
				if ( cbTpChamado.getVlrInteger() > 0 ) {
				
					ps.setInt( param++, Aplicativo.iCodEmp ); 
					ps.setInt( param++, ListaCampos.getMasterFilial( "CRTIPOCHAMADO" ) ); 
					ps.setInt( param++, cbTpChamado.getVlrInteger() );
					
				}

				if ( cbPrioridade.getVlrInteger() > 0 ) { 
					ps.setInt( param++, cbPrioridade.getVlrInteger() );
				}
				
				if ( txtCodAtendenteChamado.getVlrInteger() > 0 ) { 
					ps.setInt( param++, Aplicativo.iCodEmp ); 
					ps.setInt( param++, ListaCampos.getMasterFilial( "ATATENDENTE" ) ); 
					ps.setInt( param++, txtCodAtendenteChamado.getVlrInteger() );
					
				}


				ResultSet rs = ps.executeQuery();

				tabchm.limpa();

				for ( int i = 0; rs.next(); i++ ) {
					tabchm.adicLinha();

					tabchm.setValor( Funcoes.sqlDateToStrDate( rs.getDate( GridChamado.DTCHAMADO.name() ) ), i, GridChamado.DTCHAMADO.ordinal() );
					tabchm.setValor( rs.getInt( GridChamado.PRIORIDADE.name() ), i, GridChamado.PRIORIDADE.ordinal() );
					tabchm.setValor( rs.getInt( GridChamado.CODCHAMADO.name() ), i, GridChamado.CODCHAMADO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.DESCCHAMADO.name() ), i, GridChamado.DESCCHAMADO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.SOLICITANTE.name() ), i, GridChamado.SOLICITANTE.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.STATUS.name() ), i, GridChamado.STATUS.ordinal() );
					tabchm.setValor( rs.getBigDecimal( GridChamado.QTDHORASPREVISAO.name() ), i, GridChamado.QTDHORASPREVISAO.ordinal() );
					tabchm.setValor( Funcoes.sqlDateToStrDate( rs.getDate( GridChamado.DTPREVISAO.name() ) ), i, GridChamado.DTPREVISAO.ordinal() );
					tabchm.setValor( Funcoes.sqlDateToStrDate( rs.getDate( GridChamado.DTCONCLUSAO.name() ) ), i, GridChamado.DTCONCLUSAO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.DESCTPCHAMADO.name() ), i, GridChamado.DESCTPCHAMADO.ordinal() );

				}
				rs.close();
				ps.close();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar tabela de chamados!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
		}
	}

	private void excluiAtend() {

		if ( tabatd.getLinhaSel() == -1 ) {
			Funcoes.mensagemInforma( this, "Selecione um item na lista!" );
			return;
		}
		try {
			String sSQL = "DELETE FROM ATATENDIMENTO WHERE CODATENDO=? AND CODEMP=? AND CODFILIAL=?";
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setString( 1, "" + vCodAtends.elementAt( tabatd.getLinhaSel() ) );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.execute();
			ps.close();
			con.commit();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao salvar o atendimento!\n" + err.getMessage(), true, con, err );
		}
		carregaAtendimentos();
	}
	

	private void novoAtend() {

		if ( txtCodCli.getVlrInteger().intValue() == 0 ) {

			Funcoes.mensagemInforma( this, "Não há nenhum cliente selecionado!" );
			txtCodCli.requestFocus();
			return;
		}

		Object ORets[];

		DLNovoAtend dl = null;

		if ( txtCodRec.getVlrInteger() > 0 && txtNParcItRec.getVlrInteger() > 0 ) {
			dl = new DLNovoAtend( txtCodCli.getVlrInteger().intValue(), this, con, false, tipoatendo, txtCodRec.getVlrInteger(), txtNParcItRec.getVlrInteger() );
		}
		else {
			dl = new DLNovoAtend( txtCodCli.getVlrInteger().intValue(), this, con, false, tipoatendo );
		}

		dl.setVisible( true );

		if ( dl.OK ) {
			dl.dispose();
		}

		carregaAtendimentos();
	}
	
	private void novoChamado() {
			
		FChamado chamado = null;    

		if ( Aplicativo.telaPrincipal.temTela( FChamado.class.getName() ) ) {
			chamado = (FChamado) Aplicativo.telaPrincipal.getTela( FChamado.class.getName() );
		}
		else {
			chamado = new FChamado();
			Aplicativo.telaPrincipal.criatela( "Chamado", chamado, con );
		}    	    		 
		
		chamado.exec( null, txtCodCli.getVlrInteger() ) ;

	}
	

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcAtendenteAtendimento ) {
			carregaAtendimentos();
		}
		if ( cevt.getListaCampos() == lcAtendenteChamado ) {
			carregaChamados();
		}
		if ( cevt.getListaCampos() == lcChamado ) {
			carregaAtendimentos();
		}

		else if ( cevt.getListaCampos() == lcCli ) {
			carregaAtendimentos();
			HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboContr( con, txtCodCli.getVlrInteger(), "<Todos>" );
			cbContr.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
			carregaChamados();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {	}
	

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btNovo ) {
			if(tpnAbas.getSelectedIndex()==0){ // Aba de Atendimentos selecionada
				novoAtend();
			}
			else if(tpnAbas.getSelectedIndex()==1) {
				novoChamado();
			}
		
		
		}
		else if ( evt.getSource() == btExcluir ) {
			excluiAtend();
		}
		else if ( evt.getSource() == btImprimir ) {
			try {
				FRAtendimentos tela = FRAtendimentos.class.newInstance();
				tela.setParametros( txtCodCli.getVlrInteger(), txtDatainiAtend.getVlrDate(), txtDatafimAtend.getVlrDate() );
				Aplicativo.telaPrincipal.criatela( "", tela, con );

			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		else if ( evt.getSource() == btAtualizaChamados ) {
			carregaChamados();
		}
		else if ( evt.getSource() == btAtualizaAtendimentos ) {
			carregaAtendimentos();
		}
	}

	
	private void montaComboTipoChamado() {
		cbTpChamado.setAutoSelect( "codtpchamado", "desctpchamado", "crtipochamado" );		
		cbTpChamado.carregaValores();
	}
	
	private void montaComboTipoAtend() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODTPATENDO,DESCTPATENDO FROM ATTIPOATENDO WHERE CODEMP=? AND CODFILIAL=? AND TIPOATENDO=? ORDER BY 2";
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
			ps.setString( 3, tipoatendo );

			rs = ps.executeQuery();

			vValsTipo.clear();
			vLabsTipo.clear();

			vValsTipo.addElement( -1 );
			vLabsTipo.addElement( "<Todos>" );

			while ( rs.next() ) {
				vValsTipo.addElement( new Integer( rs.getInt( "CodTpAtendo" ) ) );
				vLabsTipo.addElement( rs.getString( "DescTpAtendo" ) );
			}
			cbTipoAtend.setItensGeneric( vLabsTipo, vValsTipo );
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar os tipos de atendimento!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			carregagrid = true;
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcAtendimento.setConexao( cn );
		lcAtendenteAtendimento.setConexao( cn );
		lcAtendenteChamado.setConexao( cn );
		lcRec.setConexao( cn );
		lcItRec.setConexao( cn );
		lcChamado.setConexao( cn );

		montaComboTipoAtend();
		montaComboTipoChamado();
		
		txtCodAtendenteAtendimento.setVlrInteger( Atendimento.buscaAtendente() );
		txtCodAtendenteChamado.setVlrInteger( Atendimento.buscaAtendente() );
		lcAtendenteAtendimento.carregaDados();
		lcAtendenteChamado.carregaDados();		
		
	}

	public void focusGained( FocusEvent arg0 ) {

	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtDatafimAtend ) {
			carregaAtendimentos();
		}
		else if ( fevt.getSource() == txtDatafimCham ) {
			carregaChamados();
		}

	}

	public void valorAlterado( JComboBoxEvent evt ) {

		if ( evt.getComboBoxPad() == cbContr ) {
			if ( cbContr.getVlrInteger() > 0 ) {
				HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboItContr( con, cbContr.getVlrInteger(), "<Todos>" );
				cbitContr.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
			}
			else {
				cbitContr.limpa();
				vValsitContr.addElement( -1 );
				vLabsitContr.addElement( "<Todos>" );
				cbitContr.setItensGeneric( vLabsitContr, vValsitContr );
				carregaAtendimentos();
			}
		}
		else if ( evt.getComboBoxPad() == cbitContr ) {
			carregaAtendimentos();
		}
		else if ( evt.getComboBoxPad() == cbTipoAtend ) {
			carregaAtendimentos();
		}
		else if ( evt.getComboBoxPad() == cbTpChamado ) {
			carregaChamados();
		}
		else if ( evt.getComboBoxPad() == cbPrioridade ) {
			carregaChamados();
		}

	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( ( kevt.getSource() == txtCodCli ) && ( txtCodCli.getVlrInteger() == 0 ) ) {
				carregaAtendimentos();
			}
			else if ( ( kevt.getSource() == txtCodAtendenteAtendimento ) && ( txtCodAtendenteAtendimento.getVlrInteger() == 0 ) ) {
				carregaAtendimentos();
			}
		}
	}

	public void keyReleased( KeyEvent arg0 ) {

	}

	public void keyTyped( KeyEvent arg0 ) {

	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tpnAbas ) {
			if ( tpnAbas.getSelectedIndex() == 0 ) {
				carregaAtendimentos();
			}
			else if ( tpnAbas.getSelectedIndex() == 1 ) {
				carregaChamados();
			}
		}

	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getSource() == tabatd && mevt.getClickCount() == 2 ) {
			visualizaAtend();
		}
		else if ( mevt.getSource() == tabchm && mevt.getClickCount() == 2 ) {
			visualizaCham();
		}
		
		
	}
	


	public void mouseEntered( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mouseExited( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mousePressed( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mouseReleased( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

}
