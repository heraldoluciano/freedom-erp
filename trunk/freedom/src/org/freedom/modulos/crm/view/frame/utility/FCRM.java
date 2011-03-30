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
import java.awt.Container;
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
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.functions.FuncoesCRM;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
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
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.atd.view.frame.crud.plain.FAtendente;
import org.freedom.modulos.crm.business.component.Atendimento;
import org.freedom.modulos.crm.business.object.Chamado;
import org.freedom.modulos.crm.business.object.Prioridade;
import org.freedom.modulos.crm.view.dialog.utility.DLAtendimento;
import org.freedom.modulos.crm.view.frame.crud.plain.FChamado;
import org.freedom.modulos.crm.view.frame.report.FRAtendimentos;
import org.freedom.modulos.gms.business.object.StatusOS;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

/**
 * 
 * @author Setpoint Informática Ltda. / Alex Rodrigues
 * @version 15/04/2010 - Anderson Sanchez
 * 
 */

public class FCRM extends FFilho implements CarregaListener, ActionListener, FocusListener, JComboBoxListener, KeyListener, ChangeListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private static final int ABA_NONE= -1;

	private static final int ABA_CHAMADO = 0;

	private static final int ABA_ATENDIMENTO = 1;

	private JTabbedPanePad tpnAbas = new JTabbedPanePad();

	private JPanelPad pinCli = new JPanelPad();

	private JPanelPad pinFiltrosAtend = new JPanelPad( 510, 150 );

	private JPanelPad pinFiltrosChamado = new JPanelPad( 510, 150 );

	private JPanelPad pinFiltrosTitulo = new JPanelPad( 510, 200 );

	private JPanelPad pnAtd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnChm = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCabCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCabCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRodCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabatd = new JTablePad();

	private JTablePad tabchm = new JTablePad();

	private JTablePad tabstatus = new JTablePad();

	private JTablePad tabsprioridade = new JTablePad();

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

	private JButtonPad btNovoAtendimento = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btNovoChamado = new JButtonPad( Icone.novo( "btChamado.png" ) );

	private JButtonPad btAtualizaChamados = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );

	private JButtonPad btAtualizaAtendimentos = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );

	private JButtonPad btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btImprimir = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );

	private ImageIcon chamado_em_atendimento = Icone.novo( "chamado_em_atendimento.png" );

	private ImageIcon chamado_parado = Icone.novo( "cl_branco.png" );

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

	// private JComboBoxPad cbStatus = new JComboBoxPad( null, null, JComboBoxPad.TP_STRING, 2, 0 );

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

	private JScrollPane scpPrioridade = new JScrollPane( tabsprioridade );

	private ImageIcon imgColuna = Icone.novo( "clAgdCanc.png" );

	private JCheckBoxPad cbEmAtendimento = new JCheckBoxPad( "Só em atendimento?", "S", "N" );

	private boolean financeiro = false;
	
	private JTextFieldPad txtTotAtendimentos = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtTotHoraAtendimentos = new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );
	
	private JTextFieldPad txtTotChamados = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtTotHoraChamados = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 7, 0 );
	
	final JDialog dlMensagem = new JDialog();

	public enum GridChamado {
		DTCHAMADO, PRIORIDADE, DESCTPCHAMADO, CODCHAMADO, DESCCHAMADO, SOLICITANTE, STATUS, QTDHORASPREVISAO, DTPREVISAO, EM_ATENDIMENTO, DADOS_ATENDIMENTO, CODCLI, DETCHAMADO
	}
	
	private JLabelPad lbStatus = new JLabelPad();
	
	private HashMap<String, Object> prefere = null;
	
	private JTextAreaPad txtDialog = new JTextAreaPad();

	public FCRM() {

		super( false );

		setTitulo( "Gestão de relacionamento com clientes" );
		setAtribos( 20, 20, 780, 650 );

		tipoatendo = "A"; // Setando o tipo de atendimento para "A" de atendimento;

		pnCabCli.setPreferredSize( new Dimension( 500, 175 ) );

		prefere = getPrefere();
		
		montaListaCamposAtend();
		montaTela();

		adicFiltrosAtend();
		adicFiltrosChamado();
		
		criaTelaMensagem();

	}

	private void adicFiltrosAtend() {

		pinFiltrosAtend.setBorder( SwingParams.getPanelLabel( "Filtros de atendimentos", Color.BLUE ) );

		pinFiltrosAtend.adic( new JLabelPad(  ), 7, 0, 70, 20 );
		pinFiltrosAtend.adic( txtDatainiAtend, 7, 20, 70, 20, "Data Inicial" );

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

		// pinFiltrosAtend.adic( btAtualizaAtendimentos, 715, 15, 30, 30 );
		pinFiltrosAtend.adic( btAtualizaAtendimentos, 722, 7, 30, 76 );

		txtTotAtendimentos.setAtivo( false );
		txtTotHoraAtendimentos.setAtivo( false );
		
		pinFiltrosAtend.adic( txtTotAtendimentos, 7, 100, 90, 20, "Atendimentos" );
		pinFiltrosAtend.adic( txtTotHoraAtendimentos, 107, 100, 90, 20, "Horas" );
		
		pnAtd.add( pinFiltrosAtend, BorderLayout.NORTH );

		JPanelPad pnGridAtd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

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

		pinFiltrosChamado.adic( cbEmAtendimento, 7, 50, 200, 20 );

		pinFiltrosChamado.adic( new JLabelPad( "Cód.Atend." ), 153, 0, 70, 20 );
		pinFiltrosChamado.adic( txtCodAtendenteChamado, 153, 20, 70, 20 );

		pinFiltrosChamado.adic( new JLabelPad( "Nome do Atendente designado" ), 226, 0, 230, 20 );
		pinFiltrosChamado.adic( txtNomeAtendenteChamado, 226, 20, 230, 20 );

		pinFiltrosChamado.adic( new JLabelPad( "Tipo" ), 226, 40, 215, 20 );
		pinFiltrosChamado.adic( cbTpChamado, 226, 60, 215, 20 );

		pinFiltrosChamado.adic( btAtualizaChamados, 722, 7, 30, 76 );

		pinFiltrosChamado.adic( scpStatus, 459, 7, 130, 77 );

		pinFiltrosChamado.adic( scpPrioridade, 591, 7, 130, 77 );
		
		txtTotChamados.setAtivo( false );
		txtTotHoraChamados.setAtivo( false );
		
		pinFiltrosChamado.adic( txtTotChamados, 7, 100, 90, 20, "Chamados" );
		pinFiltrosChamado.adic( txtTotHoraChamados, 107, 100, 90, 20, "Horas" );

		pnChm.add( pinFiltrosChamado, BorderLayout.NORTH );

		JPanelPad pnGridChm = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

		JScrollPane scpChm = new JScrollPane( tabchm );

		pnGridChm.add( scpChm, BorderLayout.CENTER );

		pnChm.add( pnGridChm, BorderLayout.CENTER );

	}

	// Construção padrão lançamento de contatos financeiro/cobrança

	public FCRM( Integer codcli, Integer codrec, Integer nparcitrec, boolean isUpdate ) {

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

		tpnAbas.setSelectedIndex( 1 );

		tpnAbas.setEnabled( false );

		calcAtrazo();

		financeiro = true;

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

		JPanelPad pnGridFinanc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

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
		// lcChamado.setDinWhereAdic( "CODCLI = #N", txtCodCli );
		lcChamado.montaSql( false, "CHAMADO", "CR" );
		lcChamado.setReadOnly( true );

	}

	private void montaGridAtend() {

		tabatd.adicColuna( "Atd." );
		tabatd.adicColuna( "Doc." );
		tabatd.adicColuna( "Status" );
		tabatd.adicColuna( "Data" );
		tabatd.adicColuna( "Data fim" );
		tabatd.adicColuna( "Ocorrência" );
		tabatd.adicColuna( "" );
		tabatd.adicColuna( "Atendente" );
		tabatd.adicColuna( "Hora inicial" );
		tabatd.adicColuna( "Hora final" );
		tabatd.adicColuna( "chamado" );
		tabatd.adicColuna( "codcli" );

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
		tabatd.setColunaInvisivel( 11 );

		tabatd.setRowHeight( 20 );
	}

	private void montaGridStatus() {

		tabstatus.adicColuna( "" );
		tabstatus.adicColuna( "Cod." );
		tabstatus.adicColuna( "" ); // Imagem
		tabstatus.adicColuna( "Status" );

		tabstatus.setTamColuna( 10, 0 );

		tabstatus.setColunaInvisivel( 1 );

		tabstatus.setTamColuna( 10, 2 );

		tabstatus.setTamColuna( 90, 3 );

		tabstatus.setRowHeight( 12 );

		tabstatus.setColunaEditavel( 0, new Boolean( true ) );

	}

	private void montaGridPrioridade() {

		tabsprioridade.adicColuna( "" );
		tabsprioridade.adicColuna( "Cod." );
		tabsprioridade.adicColuna( "Prioridade" );

		tabsprioridade.setTamColuna( 10, 0 );

		tabsprioridade.setColunaInvisivel( 1 );

		tabsprioridade.setTamColuna( 100, 2 );

		tabsprioridade.setRowHeight( 12 );

		tabsprioridade.setColunaEditavel( 0, new Boolean( true ) );

	}

	private void carregaStatus() {

		Vector<Object> valores = Chamado.getValores();
		Vector<String> labels = Chamado.getLabels();

		Vector<Object> item = null;

		for ( int i = 1; i < valores.size(); i++ ) { // Começa em um para não carregar o item <--Selecione-->

			item = new Vector<Object>();

			String valor = valores.elementAt( i ).toString();
			String label = labels.elementAt( i );
			ImageIcon icon = Chamado.getImagem( valor, StatusOS.IMG_TAMANHO_P );

			if ( Chamado.CHAMADO_CONCLUIDO.getValue().equals( valor ) ) {
				item.addElement( new Boolean( false ) );
			}
			else if ( Chamado.CHAMADO_CANCELADO.getValue().equals( valor ) ) {
				item.addElement( new Boolean( false ) );
			}
			else {
				item.addElement( new Boolean( true ) );
			}

			item.addElement( valor );
			item.addElement( icon );
			item.addElement( label );

			tabstatus.adicLinha( item );

		}

	}

	private void carregaPrioriadade() {

		Vector<Object> valores = Prioridade.getValores();
		Vector<String> labels = Prioridade.getLabels();

		Vector<Object> item = null;

		for ( int i = 1; i < valores.size(); i++ ) { // Começa em um para não carregar o item <--Selecione-->

			item = new Vector<Object>();

			item.addElement( new Boolean( true ) );
			item.addElement( valores.elementAt( i ) );
			item.addElement( labels.elementAt( i ) );

			tabsprioridade.adicLinha( item );

		}

	}

	private void montaGridChamado() {

		tabchm.adicColuna( "Data" );
		tabchm.adicColuna( "Pri" );
		tabchm.adicColuna( "Tipo" );
		tabchm.adicColuna( "Cód." );
		tabchm.adicColuna( "Descrição" );
		tabchm.adicColuna( "Solicitante" );
		tabchm.adicColuna( "St." );
		tabchm.adicColuna( "Qtd.Prev." );
		tabchm.adicColuna( "Dt.Prev." );
		tabchm.adicColuna( "" );
		tabchm.adicColuna( "Em atendimento" );
		tabchm.adicColuna( "codcli" ); // Oculto
		tabchm.adicColuna( "Detalhamento do chamado" ); // Oculto;

		tabchm.setTamColuna( 60, GridChamado.DTCHAMADO.ordinal() );
		tabchm.setTamColuna( 20, GridChamado.PRIORIDADE.ordinal() );
		tabchm.setTamColuna( 82, GridChamado.DESCTPCHAMADO.ordinal() );
		tabchm.setTamColuna( 30, GridChamado.CODCHAMADO.ordinal() );
		tabchm.setTamColuna( 190, GridChamado.DESCCHAMADO.ordinal() );
		tabchm.setTamColuna( 75, GridChamado.SOLICITANTE.ordinal() );
		tabchm.setTamColuna( 25, GridChamado.STATUS.ordinal() );
		tabchm.setTamColuna( 40, GridChamado.QTDHORASPREVISAO.ordinal() );
		tabchm.setTamColuna( 60, GridChamado.DTPREVISAO.ordinal() );
		tabchm.setTamColuna( 20, GridChamado.EM_ATENDIMENTO.ordinal() );
		tabchm.setTamColuna( 140, GridChamado.DADOS_ATENDIMENTO.ordinal() );
		tabchm.setColunaInvisivel( GridChamado.CODCLI.ordinal() );
		tabchm.setColunaInvisivel( GridChamado.DETCHAMADO.ordinal() );

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

		tpnAbas.addTab( "Chamados", pnChm );

		tpnAbas.addTab( "Atendimentos", pnAtd );

	}

	private void adicionaFiltroCli() {

		pinCabCli.add( pinCli, BorderLayout.CENTER );
		pinCli.setBorder( SwingParams.getPanelLabel( "Filtro de cliente", Color.blue ) );

		pinCli.adic( txtCodCli, 	10, 	20, 	50, 	20, "Cód.Cli." 					);
		pinCli.adic( txtRazCli, 	63, 	20, 	312, 	20, "Razão social do cliente" 	);
		
		pinCli.adic( lbStatus, 		378, 	20, 	97, 	20, "Situação"					);
		
		pinCli.adic( txtContatoCli, 478, 	20, 	237, 	20, "Contato" 					);
		pinCli.adic( txtDDDCli, 	10, 	60, 	50, 	20, "DDD" 						);	
		pinCli.adic( txtFoneCli, 	63, 	60, 	100, 	20, "Telefone" 					);
		pinCli.adic( txtDDDFax, 	166, 	60, 	50, 	20, "DDD" 						);
		pinCli.adic( txtFaxCli, 	219, 	60, 	100, 	20, "Fax" 						);
		pinCli.adic( txtDDDCel, 	322, 	60, 	50, 	20, "DDD" 						);
		pinCli.adic( txtCelCli, 	375, 	60, 	100, 	20, "Celular"					);
		pinCli.adic( txtEmailCli, 	478, 	60, 	237, 	20, "Email" 					);
		pinCli.adic( txtEndCli, 	10, 	100, 	362, 	20, "Endereço"  				);
		pinCli.adic( txtNumCli, 	375, 	100, 	100, 	20, "Número" 					);
		pinCli.adic( txtCidCli, 	478, 	100, 	169, 	20, "Cidade"					);
		pinCli.adic( txtUfCli, 		650, 	100, 	65, 	20, "UF" 						);
		
		lbStatus.setForeground( Color.WHITE );
		lbStatus.setFont( SwingParams.getFontboldmed() );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
		lbStatus.setOpaque( true );
		lbStatus.setText( "" );
		lbStatus.setBackground( Color.LIGHT_GRAY );
		lbStatus.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
	
	}

	private void adicBotoes() {

		pnBotConv.setPreferredSize( new Dimension( 120, 30 ) );

		pnBotConv.add( btNovoAtendimento );
		pnBotConv.add( btExcluir );
		pnBotConv.add( btImprimir );
		pnBotConv.add( btNovoChamado );

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

		montaGridPrioridade();

		adicBotoes();

		adicRodape();

		adicListeners();

		txtDatainiAtend.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );
		txtDatafimAtend.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );

		txtDatainiCham.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ), Funcoes.getAno( new Date() ) - 1 ) );
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

		carregaStatus();
		carregaPrioriadade();

	}

	private void adicListeners() {

		tpnAbas.addChangeListener( this );

		btNovoAtendimento.addActionListener( this );
		btNovoChamado.addActionListener( this );
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

		// cbStatus.addComboBoxListener( this );
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

		chamado.exec( (Integer) tabchm.getValor( tabchm.getLinhaSel(), GridChamado.CODCHAMADO.ordinal() ) );

	}

	private void visualizaAtend() {

		DLAtendimento dl = null;
		String codatendo = (String) tabatd.getValor( tabatd.getLinhaSel(), 0 ).toString();
		String codatend = (String) tabatd.getValor( tabatd.getLinhaSel(), 6 ).toString();
		int icodAtend = Integer.parseInt( codatend );
		int icodAtendo = Integer.parseInt( codatendo );
		Integer codchamado = (Integer) tabatd.getValor( tabatd.getLinhaSel(), 10 );

		try {
			dl = new DLAtendimento( txtCodCli.getVlrInteger(), codchamado, this, true, con, icodAtendo, icodAtend, tipoatendo );
			dl.setVisible( true );
			dl.dispose();

			if ( dl.OK ) {
				carregaAtendimentos();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar campos!" );
		}
	}

	private void carregaAtendimentos() {

		StringBuilder sql = new StringBuilder();

		if ( carregagrid ) {

			sql.append( "SELECT ATEND.CODATENDO,ATEND.DOCATENDO,ATEND.STATUSATENDO,ATEND.DATAATENDO,TA.DESCTPATENDO, " );
			sql.append( "ATEND.DATAATENDOFIN, ATEND.HORAATENDOFIN,ATEND.OBSATENDO, ATEND.CODATEND, " );
			sql.append( "A.NOMEATEND,ATEND.HORAATENDO, ATEND.CODCHAMADO, ATEND.CODCLI FROM ATATENDIMENTO ATEND, ATTIPOATENDO TA, ATATENDENTE A WHERE " );
			sql.append( "TA.CODTPATENDO=ATEND.CODTPATENDO AND TA.CODEMP=ATEND.CODEMPTO AND TA.CODFILIAL=ATEND.CODFILIALTO " );
			sql.append( "AND A.CODATEND=ATEND.CODATEND AND A.CODEMP=ATEND.CODEMPAE AND A.CODFILIAL=ATEND.CODFILIALAE " );
			sql.append( "AND TA.TIPOATENDO=? " );

			if ( ! ( txtCodRec.getVlrInteger() > 0 ) && ( txtDatainiAtend.getVlrDate() != null && txtDatafimAtend.getVlrDate() != null ) ) {
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
			if ( txtCodAtendenteAtendimento.getVlrInteger() > 0 && ( !financeiro ) ) {
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

				if ( ! ( txtCodRec.getVlrInteger() > 0 ) && ( txtDatainiAtend.getVlrDate() != null && txtDatafimAtend.getVlrDate() != null ) ) {
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
				if ( txtCodAtendenteAtendimento.getVlrInteger() > 0 && ( !financeiro ) ) {
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
						
				Long horasatend = new Long(0);
				
				for ( int i = 0; rs.next(); i++ ) {
					tabatd.adicLinha();

					vCodAtends.add( "" + rs.getString( "CodAtendo" ) );
					tabatd.setValor( rs.getString( "CodAtendo" ), i, 0 );
					tabatd.setValor( rs.getString( "DocAtendo" ), i, 1 );
					tabatd.setValor( rs.getString( "StatusAtendo" ), i, 2 );
					tabatd.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DataAtendo" ) ), i, 3 );
					tabatd.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DataAtendoFin" ) ), i, 4 );
					tabatd.setValor( rs.getString( "OBSATENDO" ), i, 5 );
					tabatd.setValor( rs.getInt( "CODATEND" ), i, 6 );
					tabatd.setValor( rs.getString( "NomeAtend" ), i, 7 );
					tabatd.setValor( rs.getTime( "HoraAtendo" ).toString(), i, 8 );
					tabatd.setValor( rs.getTime( "HoraAtendoFin" ).toString(), i, 9 );
					tabatd.setValor( rs.getInt( "CODCHAMADO" ), i, 10 );
					tabatd.setValor( rs.getInt( "CODCLI" ), i, 11 );
					
					horasatend += Funcoes.subtraiTime( rs.getTime( "HoraAtendo" ),rs.getTime( "HoraAtendoFin" ) );
					
				}
				
				
				txtTotHoraAtendimentos.setVlrString( Funcoes.longTostrTimeHoras( horasatend ));
				txtTotAtendimentos.setVlrInteger( tabatd.getNumLinhas() );
				
				rs.close();
				ps.close();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar tabela de atendimento!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	private ResultSet executaQueryChamados() {

		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();

		try {

			sql.append( "select ch.codcli, ch.dtchamado, ch.prioridade, ch.codchamado, ch.descchamado, ch.codcli, ch.solicitante, " );
			sql.append( "ch.status, ch.qtdhorasprevisao, ch.dtprevisao, ch.dtconclusao, tc.desctpchamado, coalesce(ch.ematendimento,'N') ematendimento, " );
			sql.append( "(ch.idusualt || ' desde ' || substring(cast( ch.halt as char(20)) from 1 for 5)) dados_atendimento, ch.detchamado " );
			sql.append( "from crchamado ch, crtipochamado tc " );
			sql.append( "where tc.codemp=ch.codemptc and tc.codfilial=ch.codfilialtc and tc.codtpchamado=ch.codtpchamado " );
			sql.append( "and ch.codemp=? and ch.codfilial=? " );

			// Se deve utilizar todos os filtros

			if ( "N".equals( cbEmAtendimento.getVlrString() ) ) {
				sql.append( "and dtchamado between ? and ? " );

				if ( txtCodCli.getVlrInteger() > 0 ) {
					sql.append( " and ch.codempcl=? and ch.codfilialcl=? and ch.codcli=? " );
				}
				if ( cbTpChamado.getVlrInteger() > 0 ) {
					sql.append( " and ch.codemptc=? and ch.codfilialtc=? and tc.codtpchamado=? " );
				}

				// Verifica os status selecionados

				boolean primeiro = true;

				for ( int i = 0; i < tabstatus.getNumLinhas(); i++ ) {

					if ( (Boolean) tabstatus.getValor( i, 0 ) ) {

						if ( primeiro ) {
							sql.append( " and ch.status in (" );
						}
						else {
							sql.append( "," );
						}

						sql.append( "'" + tabstatus.getValor( i, 1 ) + "'" );

						primeiro = false;
					}

					if ( i == tabstatus.getNumLinhas() - 1 && !primeiro ) {
						sql.append( " ) " );
					}

				}

				boolean prioridade1 = true;

				for ( int i = 0; i < tabsprioridade.getNumLinhas(); i++ ) {

					if ( (Boolean) tabsprioridade.getValor( i, 0 ) ) {

						if ( prioridade1 ) {
							sql.append( " and ch.prioridade in (" );
						}
						else {
							sql.append( "," );
						}

						sql.append( "'" + tabsprioridade.getValor( i, 1 ) + "'" );

						prioridade1 = false;
						primeiro = false;
					}

					if ( i == tabsprioridade.getNumLinhas() - 1 && !primeiro ) {
						sql.append( " ) " );
					}

				}

				if ( cbPrioridade.getVlrInteger() > 0 ) {
					sql.append( " and ch.prioridade=? " );
				}
				if ( txtCodAtendenteChamado.getVlrInteger() > 0 ) {
					sql.append( " and ch.codempae=? and ch.codfilialae=? and ch.codatend=? " );
				}

			}
			// Se deve filtrar apenas os em atendimento.
			else {
				sql.append( " and ch.ematendimento='S' " );
			}

			sql.append( "order by ch.prioridade, ch.dtprevisao, ch.dtchamado, ch.status, ch.dtconclusao " );

			try {
				int param = 1;

				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "CRCHAMADO" ) );

				// Se deve utilizar todos os filtros

				if ( "N".equals( cbEmAtendimento.getVlrString() ) ) {

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

				}

				rs = ps.executeQuery();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return rs;

	}

	private void carregaChamados() {

		StringBuilder sql = new StringBuilder();
		
		BigDecimal totalizacaoCham = new BigDecimal(0);

		if ( carregagrid ) {

			try {

				ResultSet rs = executaQueryChamados();

				tabchm.limpa();

				int row = 0;

				for ( int i = 0; rs.next(); i++ ) {
					tabchm.adicLinha();

					tabchm.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( GridChamado.DTCHAMADO.name() ) ), i, GridChamado.DTCHAMADO.ordinal() );
					tabchm.setValor( rs.getInt( GridChamado.PRIORIDADE.name() ), i, GridChamado.PRIORIDADE.ordinal() );
					tabchm.setValor( rs.getInt( GridChamado.CODCHAMADO.name() ), i, GridChamado.CODCHAMADO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.DESCCHAMADO.name() ), i, GridChamado.DESCCHAMADO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.SOLICITANTE.name() ), i, GridChamado.SOLICITANTE.ordinal() );
					// tabchm.setValor( rs.getString( GridChamado.STATUS.name() ), i, GridChamado.STATUS.ordinal() );
					imgColuna = Chamado.getImagem( rs.getString( "status" ), Chamado.IMG_TAMANHO_M );
					tabchm.setValor( imgColuna, row, GridChamado.STATUS.ordinal() );
					tabchm.setValor( rs.getBigDecimal( GridChamado.QTDHORASPREVISAO.name() ), i, GridChamado.QTDHORASPREVISAO.ordinal() );
					tabchm.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( GridChamado.DTPREVISAO.name() ) ), i, GridChamado.DTPREVISAO.ordinal() );

					tabchm.setValor( rs.getString( GridChamado.DESCTPCHAMADO.name() ), i, GridChamado.DESCTPCHAMADO.ordinal() );

					tabchm.setValor( rs.getInt( GridChamado.CODCLI.name() ), i, GridChamado.CODCLI.ordinal() );
				
										
					// Se chamado estiver em atendimento

					if ( rs.getString( "ematendimento" ).equals( "S" ) ) {
						tabchm.setValor( chamado_em_atendimento, i, GridChamado.EM_ATENDIMENTO.ordinal() );
						tabchm.setValor( rs.getString( GridChamado.DADOS_ATENDIMENTO.name() ), i, GridChamado.DADOS_ATENDIMENTO.ordinal() );
					}
					else {
						tabchm.setValor( chamado_parado, i, GridChamado.EM_ATENDIMENTO.ordinal() );
						tabchm.setValor( "", i, GridChamado.DADOS_ATENDIMENTO.ordinal() );
					}
					
					tabchm.setValor( rs.getString( GridChamado.DETCHAMADO.name() ), i, GridChamado.DETCHAMADO.ordinal() );
					
					totalizacaoCham = totalizacaoCham.add(rs.getBigDecimal( GridChamado.QTDHORASPREVISAO.name() ) );
					
					row++;

				}
				txtTotHoraChamados.setVlrBigDecimal( totalizacaoCham );
				txtTotChamados.setVlrInteger( tabchm.getNumLinhas() );

				rs.close();

				// Permitindo reordenação
				if ( row > 0 ) {
					RowSorter<TableModel> sorter = new TableRowSorter<TableModel>( tabchm.getModel() );

					tabchm.setRowSorter( sorter );
				}
				else {
					tabchm.setRowSorter( null );
				}

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar tabela de chamados!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
		}
	}

	private void excluiAtend() {

		if ( Funcoes.mensagemConfirma( this, "Confirma a exclusão deste atendimento?" ) == JOptionPane.YES_OPTION ) {

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
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao salvar o atendimento!\n" + err.getMessage(), true, con, err );
			}
			carregaAtendimentos();
		}
	}

	private void novoAtend( Integer codchamado, Integer codcli ) {

		Object ORets[];

		DLAtendimento dl = null;

		if ( txtCodRec.getVlrInteger() > 0 && txtNParcItRec.getVlrInteger() > 0 ) {
			dl = new DLAtendimento( txtCodCli.getVlrInteger().intValue(), null, this, con, false, tipoatendo, txtCodRec.getVlrInteger(), txtNParcItRec.getVlrInteger() );
		}
		else {

			if ( txtCodCli.getVlrInteger() > 0 ) {
				codcli = txtCodCli.getVlrInteger();
			}

			if ( codcli == null ) {
				codcli = new Integer( 0 );
			}

			dl = new DLAtendimento( codcli.intValue(), codchamado, this, con, false, tipoatendo );
		}

		dl.setVisible( true );

		if ( dl.OK ) {
			dl.dispose();
		}

		carregaAtendimentos();
	}

	private void novoChamado() {
		try {

			FChamado chamado = null;
			Integer codcli = null;

			if ( Aplicativo.telaPrincipal.temTela( FChamado.class.getName() ) ) {
				chamado = (FChamado) Aplicativo.telaPrincipal.getTela( FChamado.class.getName() );
			}
			else {
				chamado = new FChamado();
				Aplicativo.telaPrincipal.criatela( "Chamado", chamado, con );
			}

			if(txtCodCli.getVlrInteger()<1) {

				if( tpnAbas.getSelectedIndex() == ABA_CHAMADO) {

					codcli = (Integer) tabchm.getValor( tabchm.getSelectedRow(), GridChamado.CODCLI.ordinal() );

				}
				else if( tpnAbas.getSelectedIndex() == ABA_ATENDIMENTO) {

					codcli = (Integer) tabatd.getValor( tabatd.getSelectedRow(), 11 );

				}

			}
			else {
				codcli = txtCodCli.getVlrInteger();
			}


			chamado.novo();

			chamado.setCodCli( codcli );


		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
			
			if(prefere == null) {
				prefere = getPrefere();
			}
			
			if( (Boolean) prefere.get( "MOSTRACLIATRASO" ) && emAtraso() ) {
				
				lbStatus.setText( "Atraso!" );
				lbStatus.setBackground( Color.RED );
				
				
			}
			else if( (Boolean) prefere.get( "MOSTRACLIATRASO" )){
				

				lbStatus.setText( "Normal" );
				lbStatus.setBackground( SwingParams.getVerdeFreedom() );
				
				
			}
			else {
				
				lbStatus.setText( "" );
				lbStatus.setBackground( Color.LIGHT_GRAY );
				
			}
			
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btNovoAtendimento ) {

			int linhasel = tabchm.getLinhaSel();

			if ( linhasel > -1 ) {
				novoAtend( (Integer) tabchm.getValor( linhasel, GridChamado.CODCHAMADO.ordinal() ), (Integer) tabchm.getValor( linhasel, GridChamado.CODCLI.ordinal() ) );
			}
			else {
				novoAtend( null, null );
			}

		}
		else if ( evt.getSource() == btNovoChamado ) {
			novoChamado();			
		}
		else if ( evt.getSource() == btExcluir ) {
			excluiAtend();
		}
		else if ( evt.getSource() == btImprimir ) {

			if ( tpnAbas.getSelectedIndex() == ABA_ATENDIMENTO ) { 
				try {
					FRAtendimentos tela = FRAtendimentos.class.newInstance();
					tela.setParametros( txtCodCli.getVlrInteger(), txtDatainiAtend.getVlrDate(), txtDatafimAtend.getVlrDate() );
					Aplicativo.telaPrincipal.criatela( "", tela, con );

				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
			else if ( tpnAbas.getSelectedIndex() == ABA_CHAMADO ) {

				imprimiGraficoChamado( executaQueryChamados(), true );

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

	private void imprimiGraficoChamado( final ResultSet rs, final boolean bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/" );
		hParam.put( "CODCLI", txtCodCli.getVlrInteger() );
		hParam.put( "DTINI", txtDatainiCham.getVlrDate() );
		hParam.put( "DTFIM", txtDatafimCham.getVlrDate() );

		if ( txtCodCli.getVlrInteger().intValue() > 0 ) {
			hParam.put( "CLIENTE", txtCodCli.getVlrString().trim() + "-" + txtRazCli.getVlrString().trim() );
		}
		else {
			hParam.put( "CLIENTE", "DIVERSOS" );
		}

		dlGr = new FPrinterJob( "relatorios/chamados.jasper", "RELATÓRIO DE CHAMADOS", "", rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				err.printStackTrace();
			}
		}
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

		// lcAtendenteAtendimento.carregaDados();
		// lcAtendenteChamado.carregaDados();

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
			if ( tpnAbas.getSelectedIndex() == ABA_ATENDIMENTO ) {
				carregaAtendimentos();
			}
			else if ( tpnAbas.getSelectedIndex() == ABA_CHAMADO ) {
				carregaChamados();
			}
		}

	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getSource() == tabatd && mevt.getClickCount() == 2 && mevt.getModifiers() == MouseEvent.BUTTON1_MASK ) {
			visualizaAtend();
		}
		else if ( mevt.getSource() == tabchm && mevt.getClickCount() == 1 ) {
			
			
			
			if (mevt.getModifiers() == MouseEvent.BUTTON3_MASK && tabchm.getLinhaSel()>-1) {

				dlMensagem.setVisible(true);
				txtDialog.setText( (String) tabchm.getValor( tabchm.getLinhaSel(), GridChamado.DETCHAMADO.ordinal() ) );
				
			}
			else if(tabchm.getLinhaSel()>-1) { 
			
				txtCodChamado.setVlrInteger( (Integer) tabchm.getValor( tabchm.getLinhaSel(), GridChamado.CODCHAMADO.ordinal() ) );
				txtCodAtendenteAtendimento.setVlrInteger( txtCodAtendenteChamado.getVlrInteger() );
				txtDatainiAtend.setVlrString( "" );
				txtDatafimAtend.setVlrString( "" );
				lcAtendenteAtendimento.carregaDados();
				lcChamado.carregaDados();
				
				
			}
			
		}
		else if ( mevt.getSource() == tabchm && mevt.getClickCount() == 2  && mevt.getModifiers() == MouseEvent.BUTTON1_MASK ) {
			visualizaCham();
		}

	}
	
	public void criaTelaMensagem() {
		
		try {
			
			dlMensagem.setSize(300, 150);
			dlMensagem.setLocationRelativeTo(dlMensagem);
			dlMensagem.setTitle("");
			 
			txtDialog.setBackground( Color.YELLOW );			
			txtDialog.setBorder( null );
			
			Container c = dlMensagem.getContentPane();
			
			txtDialog.setEditable(false);
			
			JScrollPane spnTxt = new JScrollPane(txtDialog);
			
			c.setLayout(new BorderLayout());		
			c.add(spnTxt, BorderLayout.CENTER);
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mouseEntered( MouseEvent mevt ) {

	}

	public void mouseExited( MouseEvent arg0 ) {

		// TODO Auto-generated method stub

	}

	public void mousePressed( MouseEvent arg0 ) {

		// TODO Auto-generated method stub

	}

	public void mouseReleased( MouseEvent mevt ) {

	}

	private boolean emAtraso() {

		boolean bRetorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// Se for devolução não deve verificar parcelas em aberto...

			String sSQL = "SELECT SRETORNO FROM FNCHECAPGTOATRASOSP(?,?,?)";

			ps = con.prepareStatement( sSQL );
			
			ps.setInt( 1, txtCodCli.getVlrInteger() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, Aplicativo.iCodFilial );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				
				bRetorno = "S".equals(  rs.getString( "SRETORNO" ) );
				
			}
			else {
				Funcoes.mensagemErro( this, "Não foi possível checar os pagamentos do cliente!" );
			}

			rs.close();
			ps.close();

			con.commit();

		} 
		catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Não foi possível verificar os pagamentos do cliente!\n" + err.getMessage(), true, con, err );
		}

		return bRetorno;

	}
	
	private HashMap<String, Object> getPrefere() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "select MOSTRACLIATRASO, BLOQATENDCLIATRASO FROM sgprefere3 WHERE CODEMP=? AND CODFILIAL=? ";
		
		HashMap<String, Object> ret = new HashMap<String, Object>();
		
		try {
			
			con = Aplicativo.getInstace().getConexao();
			ps = con.prepareStatement( sSQL );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );

			rs = ps.executeQuery();

			vValsTipo.clear();
			vLabsTipo.clear();

			vValsTipo.addElement( -1 );
			vLabsTipo.addElement( "<Todos>" );

			while ( rs.next() ) {
				
				ret.put( "MOSTRACLIATRASO", "S".equals( rs.getString( "MOSTRACLIATRASO" ) ));
				ret.put( "BLOQATENDCLIATRASO", "S".equals( rs.getString( "MOSTRACLIATRASO" ) ));
				
			}
			
			rs.close();
			ps.close();
			
			con.commit();
		
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar preferencias!\n" + err.getMessage(), true, con, err );
		} 
		finally {
			ps = null;
			rs = null;
		}
		
		return ret;
		
	}

}
