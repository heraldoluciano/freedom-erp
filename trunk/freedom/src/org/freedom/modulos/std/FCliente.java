/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FCliente.java <BR>
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
 * Tela de cadastro de clientes.
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.atd.FConveniado;
import org.freedom.modulos.tmk.DLNovoHist;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLInputText;
import org.freedom.telas.FAndamento;
import org.freedom.telas.FTabDados;

public class FCliente extends FTabDados implements RadioGroupListener, PostListener, ActionListener, TabelaSelListener, ChangeListener, CarregaListener, InsertListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtAno = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtAntQtdContJan = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContFev = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContMar = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContAbr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContMai = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContJun = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContJul = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContAgo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContSet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContOut = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContNov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAntQtdContDez = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContJan = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContFev = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContMar = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContAbr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContMai = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContJun = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContJul = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContAgo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContSet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContOut = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContNov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNovaQtdContDez = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRazCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNomeCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtContCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCnpjCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtInscCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JTextFieldPad txtCpfCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private JTextFieldPad txtRgCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtSSPCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtEndCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtComplCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtBairCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCidCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtUFCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCepCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtDDDCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtFoneCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtRamalCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldPad txtDDDFaxCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtFaxCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtEmailCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSiteCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtIncraCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JTextFieldPad txtEndCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtComplCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtBairCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCidCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCepCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtUFCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtDDDFoneCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtFoneCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtDDDFaxCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtFaxCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtEndEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumEnt = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtComplEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtBairEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCidEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCepEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtUFEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtDDDFoneEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtFoneEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtDDDFaxEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtFaxEnt = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodFiscCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescFiscCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPesq = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPesq = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodClas = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescClas = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDDDCelCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCelCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCliFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCpCliFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtAnoMetaVend = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtVlrMetaVend = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtCodContDeb = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodContCred = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCliContab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldPad txtCodHistPad = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescHistPad = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JCheckBoxPad cbAtivo = new JCheckBoxPad( "Ativo", "S", "N" );

	private Vector<String> vPessoaLab = new Vector<String>();

	private Vector<String> vPessoaVal = new Vector<String>();

	private JRadioGroup rgPessoa = null;

	private JPanelPad pinEnt = new JPanelPad();

	private JPanelPad pinVend = new JPanelPad();

	private JPanelPad pinCob = new JPanelPad();

	private JPanelPad pnObs1 = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() ); // JPanelPad de observações com 2 linha e 1 coluna

	private JPanelPad pnObs1_1 = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() ); // JPanelPad de observações gerais

	private JPanelPad pnObs1_2 = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() ); // JPanelPad principal de observações por data

	private JPanelPad pnObs1_2_1 = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnObs1_2_2 = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() ); // Pinel para observações e outros

	private JPanelPad pnObs1_2_2_1 = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnObs1_2_2_2 = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinObs1_2_1_1 = new JPanelPad( 200, 200 );

	private JPanelPad pinObs1_2_2_2_1 = new JPanelPad( 0, 30 );

	private JPanelPad pinCli = new JPanelPad();

	private JPanelPad pnFor = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinFor = new JPanelPad( 0, 80 );

	private JPanelPad pnCto = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCont = new JPanelPad( new Dimension( 600, 400 ) );

	private JPanelPad pinContatos = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinHistorico = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinHistbt = new JPanelPad( 0, 32 );

	private JPanelPad pinMetaVend = new JPanelPad( 0, 160 );

	private JPanelPad pnMetaVend = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private Tabela tbObsData = new Tabela();

	private Tabela tabMetaVend = new Tabela();

	private Tabela tabFor = new Tabela();

	private Tabela tabHist = new Tabela();

	private JPanelPad pinMes1 = new JPanelPad();

	private JPanelPad pinMes2 = new JPanelPad();

	private JPanelPad pinMes3 = new JPanelPad();

	private JPanelPad pinMes4 = new JPanelPad();

	private JPanelPad pinMes5 = new JPanelPad();

	private JPanelPad pinMes6 = new JPanelPad();

	private JPanelPad pinMes7 = new JPanelPad();

	private JPanelPad pinMes8 = new JPanelPad();

	private JPanelPad pinMes9 = new JPanelPad();

	private JPanelPad pinMes10 = new JPanelPad();

	private JPanelPad pinMes11 = new JPanelPad();

	private JPanelPad pinMes12 = new JPanelPad();

	private JTextAreaPad txaObs = new JTextAreaPad();

	private JTextAreaPad txaTxtObsCli = new JTextAreaPad(); // Campo memo para observações por data

	private JTextAreaPad txaObsMetaVend = new JTextAreaPad();

	private JScrollPane spnObs = new JScrollPane( txaObs ); // Scroll pane para observações gerais

	private JScrollPane spnObsCli = new JScrollPane( txaTxtObsCli ); // Scrool pane para o campo de observações por data

	private JScrollPane spnObsTb = new JScrollPane( tbObsData ); // Cria tabela de observações dentro do scroll pane

	private ListaCampos lcTipoCli = new ListaCampos( this, "TI" );

	private ListaCampos lcTipoFiscCli = new ListaCampos( this, "FC" );

	private ListaCampos lcVend = new ListaCampos( this, "VD" );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcTran = new ListaCampos( this, "TN" );

	private ListaCampos lcTipoCob = new ListaCampos( this, "TC" );

	private ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private ListaCampos lcSetor = null;

	private ListaCampos lcClas = new ListaCampos( this, "CC" );

	private ListaCampos lcPesq = new ListaCampos( this, "PQ" );

	private ListaCampos lcCliFor = new ListaCampos( this );

	private ListaCampos lcMetaVend = new ListaCampos( this );

	private ListaCampos lcFor = new ListaCampos( this, "FR" );

	private ListaCampos lcPais = new ListaCampos( this, "" );

	private ListaCampos lcHistorico = new ListaCampos( this, "HP" );

	private JScrollPane spnTabFor = new JScrollPane( tabFor );

	private JScrollPane spnTabHist = new JScrollPane( tabHist );

	private JScrollPane spnMetaVend = new JScrollPane( tabMetaVend );

	private JButton btAtEntrega = new JButton( Icone.novo( "btReset.gif" ) );

	private JButton btAtCobranca = new JButton( Icone.novo( "btReset.gif" ) );

	private JButton btNovaObs = new JButton( Icone.novo( "btNovo.gif" ) );

	private JButton btExclObs = new JButton( Icone.novo( "btExcluir.gif" ) );

	private JButton btEditObs = new JButton( Icone.novo( "btEditar.gif" ) );

	private JButton btGrpCli = new JButton( Icone.novo( "btCliente.gif" ) );

	private String sBtGeraHist = "btExecuta2.gif";

	private JButton btSetaQtdJan = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdFev = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdMar = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdAbr = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdMai = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdJun = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdJul = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdAgo = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdSet = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdOut = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdNov = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btSetaQtdDez = new JButton( Icone.novo( sBtGeraHist ) );

	private JButton btMudaTudo = new JButton( "Alterar todos", Icone.novo( "btExecuta.gif" ) );

	private JButton btNovoHist = new JButton( Icone.novo( "btNovo.gif" ) );

	private JButton btExcluiHist = new JButton( Icone.novo( "btExcluir.gif" ) );
	
	private JButton btFirefox = new JButton( Icone.novo( "firefox.gif" ) );

	private Navegador navFor = new Navegador( true );

	private Navegador navMetaVend = new Navegador( false );

	private JTabbedPanePad tpnCont = new JTabbedPanePad();

	private FConveniado telaConv;

	private boolean[] bPref = null;

	private boolean bExecCargaObs = false;
	
	private String sURLBanco = null;

	public FCliente() {

		super();
		setTitulo( "Cadastro de Clientes" );
		setAtribos( 50, 20, 555, 520 );

		lcCliFor.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcCliFor );
		lcCliFor.setTabela( tabFor );
		lcMetaVend.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcMetaVend );
		lcMetaVend.setTabela( tabMetaVend );

		pinCli = new JPanelPad( 500, 330 );
		setPainel( pinCli );
		setImprimir( true );
	}

	private void montaTela() {

		adicTab( "Cliente", pinCli );

		lcCampos.addPostListener( this );
		lcCampos.addInsertListener( this );

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, true ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setQueryCommit( false );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVend );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );

		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.tran.", ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtDescTran, "NomeTran", "Razão social da transportadora", ListaCampos.DB_SI, false ) );
		lcTran.montaSql( false, "TRANSP", "VD" );
		lcTran.setQueryCommit( false );
		lcTran.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcTran );

		lcPais.setUsaME( false );
		lcPais.add( new GuardaCampo( txtCodPais, "CodPais", "Cod.país.", ListaCampos.DB_PK, false ) );
		lcPais.add( new GuardaCampo( txtDescPais, "NomePais", "Nome", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais );

		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descrição do tipo de cobrança", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob );

		lcTipoFiscCli.add( new GuardaCampo( txtCodFiscCli, "CodFiscCli", "Cód.tp.fisc.", ListaCampos.DB_PK, false ) );
		lcTipoFiscCli.add( new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descrição do tipo fiscal", ListaCampos.DB_SI, false ) );
		lcTipoFiscCli.montaSql( false, "TIPOFISCCLI", "LF" );
		lcTipoFiscCli.setQueryCommit( false );
		lcTipoFiscCli.setReadOnly( true );
		txtCodFiscCli.setTabelaExterna( lcTipoFiscCli );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );

		lcPesq.add( new GuardaCampo( txtCodPesq, "CodCli", "Cód.cli.p.", ListaCampos.DB_PK, false ) );
		lcPesq.add( new GuardaCampo( txtDescPesq, "RazCli", "Razão social do cliente pricipal", ListaCampos.DB_SI, false ) );
		lcPesq.montaSql( false, "CLIENTE", "VD" );
		lcPesq.setQueryCommit( false );
		lcPesq.setReadOnly( true );
		txtCodPesq.setTabelaExterna( lcPesq );

		lcClas.add( new GuardaCampo( txtCodClas, "CodClasCli", "Cód.c.cli.", ListaCampos.DB_PK, true ) );
		lcClas.add( new GuardaCampo( txtDescClas, "DescClasCli", "Descrição da classificação do cliente", ListaCampos.DB_SI, false ) );
		lcClas.montaSql( false, "CLASCLI", "VD" );
		lcClas.setQueryCommit( false );
		lcClas.setReadOnly( true );
		txtCodClas.setTabelaExterna( lcClas );
		
		lcHistorico.add( new GuardaCampo( txtCodHistPad, "CodHist", "Cód.hist.", ListaCampos.DB_PK, false ) );
		lcHistorico.add( new GuardaCampo( txtDescHistPad, "DescHist", "Descrição do historico padrão", ListaCampos.DB_SI, false ) );
		lcHistorico.montaSql( false, "HISTPAD", "FN" );
		lcHistorico.setQueryCommit( false );
		lcHistorico.setReadOnly( true );
		txtCodHistPad.setTabelaExterna( lcHistorico );

		adicCampo( txtCodCli, 7, 20, 80, 20, "CodCli", "Cód.cli.", ListaCampos.DB_PK, true );
		adicCampo( txtRazCli, 90, 20, 307, 20, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, true );
		adicCampo( txtNomeCli, 90, 60, 307, 20, "NomeCli", "Nome", ListaCampos.DB_SI, true );

		vPessoaLab.addElement( "Jurídica" );
		vPessoaLab.addElement( "Física" );
		vPessoaVal.addElement( "J" );
		vPessoaVal.addElement( "F" );
		rgPessoa = new JRadioGroup( 2, 1, vPessoaLab, vPessoaVal );
		rgPessoa.addRadioGroupListener( this );

		adicDB( rgPessoa, 400, 20, 100, 60, "PessoaCli", "Pessoa", true );
		rgPessoa.setVlrString( "J" );

		adicDB( cbAtivo, 7, 60, 70, 20, "AtivoCli", "Ativo", true );
		adicCampo( txtCodTipoCli, 7, 100, 80, 20, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_FK, txtDescTipoCli, true );
		adicDescFK( txtDescTipoCli, 90, 100, 237, 20, "DescTipoCli", "Descrição do tipo de cliente" );
		adicCampo( txtCpfCli, 330, 100, 170, 20, "CpfCli", "CPF", ListaCampos.DB_SI, false );
		adicCampo( txtCodClas, 7, 140, 80, 20, "CodClasCli", "Cód.c.cli", ListaCampos.DB_FK, txtDescClas, true );
		adicDescFK( txtDescClas, 90, 140, 237, 20, "DescClasCli", "Descrição da classificação do cliente" );
		adicCampo( txtRgCli, 330, 140, 90, 20, "RgCli", "RG", ListaCampos.DB_SI, false );
		adicCampo( txtSSPCli, 423, 140, 77, 20, "SSPCli", "Orgão exp.", ListaCampos.DB_SI, false );
		adicCampo( txtCnpjCli, 7, 180, 150, 20, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false );
		adicCampo( txtInscCli, 160, 180, 147, 20, "InscCli", "Inscrição Estadual", ListaCampos.DB_SI, false );
		adicCampo( txtContCli, 310, 180, 190, 20, "ContCli", "Contato", ListaCampos.DB_SI, false );
		JCheckBoxPad cbSimples = new JCheckBoxPad( "Simples", "S", "N" );
		adicDB( cbSimples, 7, 220, 80, 20, "SimplesCli", "Fiscal", true );
		adicCampo( txtEndCli, 90, 220, 257, 20, "EndCli", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumCli, 350, 220, 77, 20, "NumCli", "Num.", ListaCampos.DB_SI, false );
		adicCampo( txtComplCli, 430, 220, 70, 20, "ComplCli", "Compl.", ListaCampos.DB_SI, false );
		adicCampo( txtBairCli, 7, 260, 180, 20, "BairCli", "Bairro", ListaCampos.DB_SI, false );
		adicCampo( txtCidCli, 190, 260, 177, 20, "CidCli", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtCepCli, 370, 260, 77, 20, "CepCli", "Cep", ListaCampos.DB_SI, false );
		adicCampo( txtUFCli, 450, 260, 50, 20, "UFCli", "UF", ListaCampos.DB_SI, false );
		adicCampo( txtCodPais, 7, 300, 70, 20, "CodPais", "Cod.país", ListaCampos.DB_FK, false );
		adicDescFK( txtDescPais, 80, 300, 217, 20, "DescPais", "Nome do país" );
		adicCampo( txtDDDCli, 300, 300, 40, 20, "DDDCli", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneCli, 343, 300, 97, 20, "FoneCli", "Telefone", ListaCampos.DB_SI, false );
		adicCampo( txtRamalCli, 443, 300, 57, 20, "RamalCli", "Ramal", ListaCampos.DB_SI, false );
		adicCampo( txtDDDFaxCli, 7, 340, 40, 20, "DDDFaxCli", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFaxCli, 50, 340, 107, 20, "FaxCli", "Fax", ListaCampos.DB_SI, false );
		adicCampo( txtDDDCelCli, 160, 340, 40, 20, "DDDCelCli", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtCelCli, 203, 340, 107, 20, "CelCli", "Celular", ListaCampos.DB_SI, false );
		adicCampo( txtIncraCli, 313, 340, 187, 20, "IncraCli", "Incra", ListaCampos.DB_SI, false );
		adicCampo( txtEmailCli, 7, 380, 245, 20, "EmailCli", "E-Mail", ListaCampos.DB_SI, false );
		adicCampo( txtSiteCli, 255, 380, 220, 20, "SiteCli", "Site", ListaCampos.DB_SI, false );
		adic(btFirefox, 480, 380, 20, 20 );
		txtCpfCli.setMascara( JTextFieldPad.MC_CPF );
		txtCnpjCli.setMascara( JTextFieldPad.MC_CNPJ );
		txtCepCli.setMascara( JTextFieldPad.MC_CEP );
		txtFoneCli.setMascara( JTextFieldPad.MC_FONE );
		txtCelCli.setMascara( JTextFieldPad.MC_FONE );
		txtFaxCli.setMascara( JTextFieldPad.MC_FONE );

		pinEnt = new JPanelPad( 500, 290 );
		setPainel( pinEnt );

		adicTab( "Entrega", pinEnt );

		btAtEntrega.setPreferredSize( new Dimension( 30, 30 ) );
		btAtEntrega.setToolTipText( "Atualiza endereço de entrega." );
		btAtEntrega.addActionListener( this );
		btFirefox.addActionListener( this );
		btFirefox.setToolTipText( "Acessar Site" );

		adicCampo( txtEndEnt, 7, 20, 260, 20, "EndEnt", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumEnt, 270, 20, 50, 20, "NumEnt", "Num.", ListaCampos.DB_SI, false );
		adicCampo( txtComplEnt, 323, 20, 49, 20, "ComplEnt", "Compl.", ListaCampos.DB_SI, false );
		adicCampo( txtBairEnt, 7, 60, 120, 20, "BairEnt", "Bairro", ListaCampos.DB_SI, false );
		adicCampo( txtCidEnt, 130, 60, 120, 20, "CidEnt", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtCepEnt, 253, 60, 80, 20, "CepEnt", "Cep", ListaCampos.DB_SI, false );
		txtCepEnt.setMascara( JTextFieldPad.MC_CEP );
		adicCampo( txtUFEnt, 336, 60, 36, 20, "UFEnt", "UF", ListaCampos.DB_SI, false );
		adicCampo( txtDDDFoneEnt, 7, 100, 40, 20, "DDDFoneEnt", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneEnt, 50, 100, 138, 20, "FoneEnt", "Telefone", ListaCampos.DB_SI, false );
		txtFoneEnt.setMascara( JTextFieldPad.MC_FONE );
		adicCampo( txtDDDFaxEnt, 192, 100, 40, 20, "DDDFaxEnt", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFaxEnt, 235, 100, 138, 20, "FaxEnt", "Fax", ListaCampos.DB_SI, false );
		txtFaxEnt.setMascara( JTextFieldPad.MC_FONE );
		adic( btAtEntrega, 400, 15, 30, 30 );

		pinCob = new JPanelPad( 500, 290 );
		setPainel( pinCob );
		adicTab( "Cobrança", pinCob );

		btAtCobranca.setPreferredSize( new Dimension( 30, 30 ) );
		btAtCobranca.setToolTipText( "Atualiza endereço de cobrança." );
		btAtCobranca.addActionListener( this );

		adicCampo( txtEndCob, 7, 20, 260, 20, "EndCob", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumCob, 270, 20, 50, 20, "NumCob", "Num.", ListaCampos.DB_SI, false );
		adicCampo( txtComplCob, 323, 20, 49, 20, "ComplCob", "Compl.", ListaCampos.DB_SI, false );
		adicCampo( txtBairCob, 7, 60, 120, 20, "BairCob", "Bairro", ListaCampos.DB_SI, false );
		adicCampo( txtCidCob, 130, 60, 120, 20, "CidCob", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtCepCob, 253, 60, 80, 20, "CepCob", "Cep", ListaCampos.DB_SI, false );
		txtCepCob.setMascara( JTextFieldPad.MC_CEP );
		adicCampo( txtUFCob, 336, 60, 36, 20, "UFCob", "UF", ListaCampos.DB_SI, false );
		adicCampo( txtDDDFoneCob, 7, 100, 40, 20, "DDDFoneCob", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneCob, 50, 100, 138, 20, "FoneCob", "Telefone", ListaCampos.DB_SI, false );
		txtFoneCob.setMascara( JTextFieldPad.MC_FONE );
		adicCampo( txtDDDFaxCob, 192, 100, 40, 20, "DDDFaxCob", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFaxCob, 235, 100, 138, 20, "FaxCob", "Fax", ListaCampos.DB_SI, false );
		txtFaxCob.setMascara( JTextFieldPad.MC_FONE );
		adic( btAtCobranca, 400, 15, 30, 30 );

		// Venda:

		pinVend = new JPanelPad( 500, 290 );
		setPainel( pinVend );
		adicTab( "Venda", pinVend );
		adicCampo( txtCodVend, 7, 20, 80, 20, "CodVend", "Cód.comiss.", ListaCampos.DB_FK, txtDescVend, false );
		adicDescFK( txtDescVend, 90, 20, 240, 20, "NomeVend", "Nome do comissionado" );
		adicCampo( txtCodPlanoPag, 7, 60, 80, 20, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag, false );
		adicDescFK( txtDescPlanoPag, 90, 60, 240, 20, "DescPlanoPag", "Descrição do plano de pagamento" );
		adicCampo( txtCodTran, 7, 100, 80, 20, "CodTran", "Cód.tran.", ListaCampos.DB_FK, txtDescTran, false );
		adicDescFK( txtDescTran, 90, 100, 240, 20, "NomeTran", "Nome ou razão social do transportador" );
		adicCampo( txtCodTipoCob, 7, 140, 80, 20, "CodTipoCob", "Cód.t.cob.", ListaCampos.DB_FK, txtDescTipoCob, false );
		adicDescFK( txtDescTipoCob, 90, 140, 240, 20, "DescTipoCob", "Descrição do tipo de cobrança" );
		adicCampo( txtCodBanco, 7, 180, 80, 20, "CodBanco", "Cód.banco", ListaCampos.DB_FK, txtNomeBanco, false );
		adicDescFK( txtNomeBanco, 90, 180, 240, 20, "NomeBanco", "Nome do banco" );
		adicCampo( txtCodPesq, 7, 220, 80, 20, "CodPesq", "Cód.cli.p.", ListaCampos.DB_FK, txtDescPesq, false );
		adicDescFK( txtDescPesq, 90, 220, 240, 20, "RazCli", "Razão social do cliente principal" );
		adicCampo( txtCodFiscCli, 7, 260, 80, 20, "CodFiscCli", "Cód.tp.fisc.", ListaCampos.DB_FK, txtDescFiscCli, false );
		adicDescFK( txtDescFiscCli, 90, 260, 240, 20, "DescFiscCli", "Descrição do tipo fiscal" );
		adicCampo( txtCodCliContab, 7, 300, 160, 20, "CodCliContab", "Cód.cli.contábil", ListaCampos.DB_SI, false );
		adicCampo( txtCodContDeb, 7, 340, 160, 20, "CodContDeb", "Cód.cont.débito", ListaCampos.DB_SI, false );
		adicCampo( txtCodContCred, 170, 340, 160, 20, "CodContCred", "Cód.cont.crédito", ListaCampos.DB_SI, false );
		adicCampo( txtCodHistPad, 7, 380, 80, 20, "CodHist", "Cód.hist.", ListaCampos.DB_FK, txtDescHistPad, false );
		adicDescFK( txtDescHistPad, 90, 380, 240, 20, "DescHist", "Descrição do historico padrão" );
		
		// Adicionar botão para agrupamento de clientes

		btGrpCli.setToolTipText( "Clientes agrupados" );
		btGrpCli.setPreferredSize( new Dimension( 38, 26 ) );
		pnImp.add( btGrpCli );

		// adic(btGrpCli,330,215,25,25);
		btGrpCli.addActionListener( this );

		adicTab( "Observações", pnObs1 );
		adicDBLiv( txaObs, "ObsCli", "Observações", false );

		txaTxtObsCli.setEditable( false );
		tbObsData.adicColuna( "Data" );
		tbObsData.adicColuna( "Hora" );
		tbObsData.adicColuna( "Seq." );
		tbObsData.setTamColuna( 80, 0 );
		tbObsData.setTamColuna( 80, 0 );
		tbObsData.setTamColuna( 40, 1 );

		btNovaObs.setToolTipText( "Nova observação por data." );
		btExclObs.setToolTipText( "Exclui observação selecionada." );
		btEditObs.setToolTipText( "Edita observação selecionanda." );

		btNovaObs.addActionListener( this );
		btExclObs.addActionListener( this );
		btEditObs.addActionListener( this );

		pinObs1_2_2_2_1.adic( btNovaObs, 0, 0, 30, 26 );
		pinObs1_2_2_2_1.adic( btExclObs, 31, 0, 30, 26 );
		pinObs1_2_2_2_1.adic( btEditObs, 62, 0, 30, 26 );
		pnObs1_2_2_1.add( spnObsCli );
		pnObs1_2_2_2.add( pinObs1_2_2_2_1 );
		pinObs1_2_1_1.adic( spnObsTb, 0, 0, 200, 200 );
		pnObs1_2_1.add( pinObs1_2_1_1 ); // adiciona o scrool pane da tabela de datas no painel da esquerda
		pnObs1_2_2.add( pnObs1_2_2_1, BorderLayout.CENTER ); // adiciona memo de observações no painel da direita
		pnObs1_2_2.add( pnObs1_2_2_2, BorderLayout.SOUTH );
		pnObs1_1.add( spnObs, BorderLayout.CENTER ); // adiciona as observações gerais no painel
		pnObs1_2.add( pnObs1_2_1, BorderLayout.WEST );
		pnObs1_2.add( pnObs1_2_2, BorderLayout.CENTER );
		pnObs1.add( pnObs1_1, BorderLayout.CENTER );
		pnObs1.add( pnObs1_2, BorderLayout.SOUTH );

		setListaCampos( true, "CLIENTE", "VD" );

		// Fornecedor:

		setPainel( pinFor, pnFor );
		adicTab( "Cliente X Forn.", pnFor );
		setListaCampos( lcCliFor );

		navFor.setAtivo( 6, false );

		setNavegador( navFor );
		pnFor.add( pinFor, BorderLayout.SOUTH );
		pnFor.add( spnTabFor, BorderLayout.CENTER );

		pinFor.adic( navFor, 0, 50, 270, 25 );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, null, true ) );
		lcFor.add( new GuardaCampo( txtDescFor, "RazFor", "Razão social do fronecedor", ListaCampos.DB_SI, false ) );
		lcFor.montaSql( false, "FORNECED", "CP" );
		lcFor.setReadOnly( true );
		lcFor.setQueryCommit( false );
		txtCodFor.setListaCampos( lcFor );
		txtCodFor.setTabelaExterna( lcFor );

		adicCampo( txtCodFor, 7, 20, 80, 20, "CodFor", "Cód.forn.", ListaCampos.DB_PF, txtDescFor, true );
		adicDescFK( txtDescFor, 90, 20, 257, 20, "RazFor", "Razão social do fornecedor" );
		adicCampo( txtCodCliFor, 350, 20, 80, 20, "CodCliFor", "Cód.cli.for.", ListaCampos.DB_SI, false );
		adicCampo( txtCodCpCliFor, 433, 20, 77, 20, "CodCpCliFor", "Cód.compl.", ListaCampos.DB_SI, false );
		setListaCampos( false, "CLIENTEFOR", "VD" );
		lcCliFor.montaTab();
		lcCliFor.setQueryInsert( false );
		lcCliFor.setQueryCommit( false );
		tabFor.setTamColuna( 250, 1 );

		txtCodPesq.setNomeCampo( "CodCli" );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		tpn.addChangeListener( this );
		lcCampos.setQueryInsert( false );

		// Contatos

		tabHist.adicColuna( "Ind." );
		tabHist.adicColuna( "Sit." );
		tabHist.adicColuna( "tipo" );
		tabHist.adicColuna( "Contato" );
		tabHist.adicColuna( "Atendente" );
		tabHist.adicColuna( "Data" );
		tabHist.adicColuna( "Histórico" );
		tabHist.adicColuna( "Usuário" );
		tabHist.adicColuna( "Hora" );

		tabHist.setTamColuna( 40, 0 );
		tabHist.setTamColuna( 30, 1 );
		tabHist.setTamColuna( 30, 2 );
		tabHist.setTamColuna( 70, 3 );
		tabHist.setTamColuna( 70, 4 );
		tabHist.setTamColuna( 75, 5 );
		tabHist.setTamColuna( 200, 6 );
		tabHist.setTamColuna( 100, 7 );
		tabHist.setTamColuna( 70, 8 );

		tabHist.addMouseListener( new MouseAdapter() {
			public void mouseClicked( MouseEvent mevt ) {
				if ( mevt.getClickCount() == 2 ) {
					editaHist();
				}
			}
		} );

		tpnCont.setTabPlacement( SwingConstants.BOTTOM );
		tpnCont.add( "Historico", pinHistorico );
		tpnCont.add( "Lançamento de Contatos", pinContatos );
		tpnCont.addChangeListener( this );

		setPainel( pinContatos );
		adicTab( "Contatos", pnCto );
		pnCto.add( tpnCont );

		pinHistorico.add( spnTabHist, BorderLayout.CENTER );
		pinHistorico.add( pinHistbt, BorderLayout.SOUTH );

		pinHistbt.setPreferredSize( new Dimension( 63, 36 ) );
		pinHistbt.adic( btNovoHist, 1, 1, 30, 30 );
		pinHistbt.adic( btExcluiHist, 31, 1, 30, 30 );
		btNovoHist.addActionListener( this );
		btExcluiHist.addActionListener( this );

		pinContatos.add( pnCont, BorderLayout.CENTER );

		pnCont.adic( new JLabelPad( "Ano" ), 7, 0, 80, 20 );
		pnCont.adic( txtAno, 7, 20, 80, 20 );
		pnCont.adic( btMudaTudo, 367, 15, 150, 30 );

		txtAno.addFocusListener( this );
		txtAno.setVlrInteger( new Integer( Calendar.getInstance().get( Calendar.YEAR ) ) );

		JLabelPad lbMes1 = new JLabelPad( "   Janeiro" );
		lbMes1.setOpaque( true );
		pnCont.adic( lbMes1, 17, 55, 80, 15 );
		pnCont.adic( pinMes1, 7, 60, 170, 70 );
		pinMes1.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes1.adic( txtAntQtdContJan, 7, 30, 70, 20 );
		txtAntQtdContJan.setAtivo( false );
		pinMes1.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes1.adic( txtNovaQtdContJan, 80, 30, 60, 20 );
		pinMes1.adic( btSetaQtdJan, 143, 30, 20, 20 );
		btSetaQtdJan.setBorder( null );
		btSetaQtdJan.setToolTipText( "Gera contatos" );
		pinMes1.setBorder( BorderFactory.createEtchedBorder() );

		JLabelPad lbMes2 = new JLabelPad( "   Fevereiro" );
		lbMes2.setOpaque( true );
		pnCont.adic( lbMes2, 192, 55, 80, 15 );
		pnCont.adic( pinMes2, 182, 60, 170, 70 );
		pinMes2.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes2.adic( txtAntQtdContFev, 7, 30, 70, 20 );
		txtAntQtdContFev.setAtivo( false );
		pinMes2.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes2.adic( txtNovaQtdContFev, 80, 30, 60, 20 );
		pinMes2.adic( btSetaQtdFev, 143, 30, 20, 20 );
		btSetaQtdFev.setBorder( null );
		btSetaQtdFev.setToolTipText( "Gera contatos" );

		pinMes2.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbMes3 = new JLabelPad( "   Março" );
		lbMes3.setOpaque( true );
		pnCont.adic( lbMes3, 367, 55, 80, 15 );
		pnCont.adic( pinMes3, 357, 60, 170, 70 );
		pinMes3.setBorder( BorderFactory.createEtchedBorder() );
		pinMes3.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes3.adic( txtAntQtdContMar, 7, 30, 70, 20 );
		txtAntQtdContMar.setAtivo( false );
		pinMes3.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes3.adic( txtNovaQtdContMar, 80, 30, 60, 20 );
		pinMes3.adic( btSetaQtdMar, 143, 30, 20, 20 );
		btSetaQtdMar.setBorder( null );
		btSetaQtdMar.setToolTipText( "Gera contatos" );

		JLabelPad lbMes4 = new JLabelPad( "   Abril" );
		lbMes4.setOpaque( true );
		pnCont.adic( lbMes4, 17, 135, 80, 15 );
		pnCont.adic( pinMes4, 7, 140, 170, 70 );
		pinMes4.setBorder( BorderFactory.createEtchedBorder() );
		pinMes4.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes4.adic( txtAntQtdContAbr, 7, 30, 70, 20 );
		txtAntQtdContAbr.setAtivo( false );
		pinMes4.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes4.adic( txtNovaQtdContAbr, 80, 30, 60, 20 );
		pinMes4.adic( btSetaQtdAbr, 143, 30, 20, 20 );
		btSetaQtdAbr.setBorder( null );
		btSetaQtdAbr.setToolTipText( "Gera contatos" );

		JLabelPad lbMes5 = new JLabelPad( "   Maio" );
		lbMes5.setOpaque( true );
		pnCont.adic( lbMes5, 192, 135, 80, 15 );
		pnCont.adic( pinMes5, 182, 140, 170, 70 );
		pinMes5.setBorder( BorderFactory.createEtchedBorder() );
		pinMes5.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes5.adic( txtAntQtdContMai, 7, 30, 70, 20 );
		txtAntQtdContMai.setAtivo( false );
		pinMes5.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes5.adic( txtNovaQtdContMai, 80, 30, 60, 20 );
		pinMes5.adic( btSetaQtdMai, 143, 30, 20, 20 );
		btSetaQtdMai.setBorder( null );
		btSetaQtdMai.setToolTipText( "Gera contatos" );

		JLabelPad lbMes6 = new JLabelPad( "   Junho" );
		lbMes6.setOpaque( true );
		pnCont.adic( lbMes6, 367, 135, 80, 15 );
		pnCont.adic( pinMes6, 357, 140, 170, 70 );
		pinMes6.setBorder( BorderFactory.createEtchedBorder() );
		pinMes6.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes6.adic( txtAntQtdContJun, 7, 30, 70, 20 );
		txtAntQtdContJun.setAtivo( false );
		pinMes6.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes6.adic( txtNovaQtdContJun, 80, 30, 60, 20 );
		pinMes6.adic( btSetaQtdJun, 143, 30, 20, 20 );
		btSetaQtdJun.setBorder( null );
		btSetaQtdJun.setToolTipText( "Gera contatos" );

		JLabelPad lbMes7 = new JLabelPad( "   Julho" );
		lbMes7.setOpaque( true );
		pnCont.adic( lbMes7, 17, 215, 80, 15 );
		pnCont.adic( pinMes7, 7, 220, 170, 70 );
		pinMes7.setBorder( BorderFactory.createEtchedBorder() );
		pinMes7.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes7.adic( txtAntQtdContJul, 7, 30, 70, 20 );
		txtAntQtdContJul.setAtivo( false );
		pinMes7.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes7.adic( txtNovaQtdContJul, 80, 30, 60, 20 );
		pinMes7.adic( btSetaQtdJul, 143, 30, 20, 20 );
		btSetaQtdJul.setBorder( null );
		btSetaQtdJul.setToolTipText( "Gera contatos" );

		JLabelPad lbMes8 = new JLabelPad( "   Agosto" );
		lbMes8.setOpaque( true );
		pnCont.adic( lbMes8, 192, 215, 80, 15 );
		pnCont.adic( pinMes8, 182, 220, 170, 70 );
		pinMes8.setBorder( BorderFactory.createEtchedBorder() );
		pinMes8.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes8.adic( txtAntQtdContAgo, 7, 30, 70, 20 );
		txtAntQtdContAgo.setAtivo( false );
		pinMes8.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes8.adic( txtNovaQtdContAgo, 80, 30, 60, 20 );
		pinMes8.adic( btSetaQtdAgo, 143, 30, 20, 20 );
		btSetaQtdAgo.setBorder( null );
		btSetaQtdAgo.setToolTipText( "Gera contatos" );

		JLabelPad lbMes9 = new JLabelPad( "   Setembro" );
		lbMes9.setOpaque( true );
		pnCont.adic( lbMes9, 367, 215, 80, 15 );
		pnCont.adic( pinMes9, 357, 220, 170, 70 );
		pinMes9.setBorder( BorderFactory.createEtchedBorder() );
		pinMes9.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes9.adic( txtAntQtdContSet, 7, 30, 70, 20 );
		txtAntQtdContSet.setAtivo( false );
		pinMes9.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes9.adic( txtNovaQtdContSet, 80, 30, 60, 20 );
		pinMes9.adic( btSetaQtdSet, 143, 30, 20, 20 );
		btSetaQtdSet.setBorder( null );
		btSetaQtdSet.setToolTipText( "Gera contatos" );

		JLabelPad lbMes10 = new JLabelPad( "   Outubro" );
		lbMes10.setOpaque( true );
		pnCont.adic( lbMes10, 17, 295, 80, 15 );
		pnCont.adic( pinMes10, 7, 300, 170, 70 );
		pinMes10.setBorder( BorderFactory.createEtchedBorder() );
		pinMes10.adic( new JLabelPad( "Cntatos" ), 7, 10, 70, 20 );
		pinMes10.adic( txtAntQtdContOut, 7, 30, 70, 20 );
		txtAntQtdContOut.setAtivo( false );
		pinMes10.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes10.adic( txtNovaQtdContOut, 80, 30, 60, 20 );
		pinMes10.adic( btSetaQtdOut, 143, 30, 20, 20 );
		btSetaQtdOut.setBorder( null );
		btSetaQtdOut.setToolTipText( "Gera contatos" );

		JLabelPad lbMes11 = new JLabelPad( "   Novembro" );
		lbMes11.setOpaque( true );
		pnCont.adic( lbMes11, 192, 295, 80, 15 );
		pnCont.adic( pinMes11, 182, 300, 170, 70 );
		pinMes11.setBorder( BorderFactory.createEtchedBorder() );
		pinMes11.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes11.adic( txtAntQtdContNov, 7, 30, 70, 20 );
		txtAntQtdContNov.setAtivo( false );
		pinMes11.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes11.adic( txtNovaQtdContNov, 80, 30, 60, 20 );
		pinMes11.adic( btSetaQtdNov, 143, 30, 20, 20 );
		btSetaQtdNov.setBorder( null );
		btSetaQtdNov.setToolTipText( "Gera contatos" );

		JLabelPad lbMes12 = new JLabelPad( "   Dezembro" );
		lbMes12.setOpaque( true );
		pnCont.adic( lbMes12, 367, 295, 80, 15 );
		pnCont.adic( pinMes12, 357, 300, 170, 70 );
		pinMes12.setBorder( BorderFactory.createEtchedBorder() );
		pinMes12.adic( new JLabelPad( "Contatos" ), 7, 10, 70, 20 );
		pinMes12.adic( txtAntQtdContDez, 7, 30, 70, 20 );
		txtAntQtdContDez.setAtivo( false );
		pinMes12.adic( new JLabelPad( "Nova qtd." ), 80, 10, 60, 20 );
		pinMes12.adic( txtNovaQtdContDez, 80, 30, 60, 20 );
		pinMes12.adic( btSetaQtdDez, 143, 30, 20, 20 );
		btSetaQtdDez.setBorder( null );
		btSetaQtdDez.setToolTipText( "Gera contatos" );

		// AnotaMetaVend
		setPainel( pinMetaVend, pnMetaVend );
		adicTab( "Meta de Vendas", pnMetaVend );
		setListaCampos( lcMetaVend );
		setNavegador( navMetaVend );

		pnMetaVend.add( pinMetaVend, BorderLayout.SOUTH );
		pnMetaVend.add( spnMetaVend, BorderLayout.CENTER );

		pinMetaVend.adic( navMetaVend, 0, 130, 150, 25 );

		adicCampo( txtAnoMetaVend, 7, 20, 100, 20, "AnoMetaVend", "Ano", ListaCampos.DB_PK, null, true );
		adicCampo( txtVlrMetaVend, 110, 20, 120, 20, "VlrMetaVend", "Valor da meta", ListaCampos.DB_SI, true );
		adicDBLiv( txaObsMetaVend, 7, 60, 500, 60, "ObsMetaVend", "Observações", false );
		setListaCampos( false, "CLIMETAVEND", "VD" );
		lcMetaVend.montaTab();
		lcMetaVend.setQueryInsert( false );
		lcMetaVend.setQueryCommit( false );
		tabMetaVend.setTamColuna( 150, 1 );

		btSetaQtdJan.addActionListener( this );
		btSetaQtdFev.addActionListener( this );
		btSetaQtdMar.addActionListener( this );
		btSetaQtdAbr.addActionListener( this );
		btSetaQtdMai.addActionListener( this );
		btSetaQtdJun.addActionListener( this );
		btSetaQtdJul.addActionListener( this );
		btSetaQtdAgo.addActionListener( this );
		btSetaQtdSet.addActionListener( this );
		btSetaQtdOut.addActionListener( this );
		btSetaQtdNov.addActionListener( this );
		btSetaQtdDez.addActionListener( this );
		btMudaTudo.addActionListener( this );

		if ( bPref[ 0 ] ) {
			lcSetor = new ListaCampos( this, "SR" );
			lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_PK, txtDescSetor, false ) );
			lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descrição do setor", ListaCampos.DB_SI, false ) );
			lcSetor.montaSql( false, "SETOR", "VD" );
			lcSetor.setQueryCommit( false );
			lcSetor.setReadOnly( true );
			txtCodSetor.setTabelaExterna( lcSetor );

			adicCampo( txtCodSetor, 7, 300, 80, 20, "CodSetor", "Cód.setor", ListaCampos.DB_FK, txtDescSetor, true );
			adicDescFK( txtDescSetor, 90, 300, 237, 20, "DescSetor", "Descrição do setor" );
		}

		lcCampos.addCarregaListener( this );
		tbObsData.addTabelaSelListener( this );

	}

	private void carregaTabelaObs() {

		int iCodCli = 0;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer sSql = new StringBuffer();
		
		Object[] oLinha = null;
		
		try {
			
			iCodCli = txtCodCli.getVlrInteger().intValue();
			txaTxtObsCli.setText( "" );
			bExecCargaObs = true;
			tbObsData.limpa();
			
			if ( iCodCli != 0 ) {
				
				sSql.append( "SELECT OC.DTOBSCLI, OC.HOBSCLI, OC.SEQOBSCLI FROM VDOBSCLI OC " );
				sSql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " );
				sSql.append( "ORDER BY OC.DTOBSCLI DESC, OC.HOBSCLI DESC, OC.SEQOBSCLI DESC" );
				
				ps = con.prepareStatement( sSql.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDOBSCLI" ) );
				ps.setInt( 3, iCodCli );
				rs = ps.executeQuery();
				
				while ( rs.next() ) {
					oLinha = new Object[ 3 ];
					oLinha[ 0 ] = rs.getString( "DTOBSCLI" );
					oLinha[ 1 ] = rs.getString( "HOBSCLI" );
					oLinha[ 2 ] = rs.getString( "SEQOBSCLI" );
					tbObsData.adicLinha( oLinha );
				}
				
				rs.close();
				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}
				
			}
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, 
					"Não foi possível carregar dados da tabela observações de cliente (VDOBSCLI).\n" 
						+ err.getMessage(), true, con, err );
		} finally {
			iCodCli = 0;
			rs = null;
			ps = null;
			sSql = null;
			oLinha = null;
			bExecCargaObs = false;
			if ( tbObsData.getNumLinhas() > 0 )
				tbObsData.setLinhaSel( 0 );
		}
	}

	private void carregaTabHist() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSql = new StringBuffer();
		int iLinha = 0;

		try {
			
			sSql.append( "SELECT H.CODHISTTK,H.SITHISTTK,H.TIPOHISTTK,H.DATAHISTTK,H.DESCHISTTK," );
			sSql.append( "H.CODCTO,H.CODATEND,A.NOMEATEND,H.HORAHISTTK " + "FROM TKHISTORICO H, ATATENDENTE A " );
			sSql.append( "WHERE H.CODCLI=? AND H.CODEMPCL=? AND H.CODFILIALCL=? " );
			sSql.append( "AND A.CODATEND=H.CODATEND AND A.CODEMP=H.CODEMPAE AND A.CODFILIAL=H.CODFILIALAE " );
			sSql.append( "ORDER BY H.DATAHISTTK DESC,H.HORAHISTTK DESC,H.CODHISTTK" );
			
			ps = con.prepareStatement( sSql.toString() );
			ps.setInt( 1, txtCodCli.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, Aplicativo.iCodFilial );
			rs = ps.executeQuery();

			tabHist.limpa();

			while ( rs.next() ) {
				
				tabHist.adicLinha();
				tabHist.setValor( rs.getString( "CodHistTK" ), iLinha, 0 );
				tabHist.setValor( rs.getString( "SitHistTK" ), iLinha, 1 );
				tabHist.setValor( rs.getString( "TipoHistTK" ), iLinha, 2 );
				tabHist.setValor( rs.getString( "CodCto" ), iLinha, 3 );
				tabHist.setValor( rs.getString( "CodAtend" ), iLinha, 4 );
				tabHist.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DataHistTK" ) ), iLinha, 5 );
				tabHist.setValor( rs.getString( "DescHistTK" ), iLinha, 6 );
				tabHist.setValor( rs.getString( "NomeAtend" ), iLinha, 7 );
				tabHist.setValor( rs.getString( "HoraHistTK" ), iLinha, 8 );
				iLinha++;
				
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar tabela de históricos!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}
	}

	private void carregaObs() {

		int iCodCli = 0;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSql = null;
		
		try {
			
			if ( !bExecCargaObs ) {
				
				if ( tbObsData.getSelectedRow() > -1 ) {
					
					iCodCli = txtCodCli.getVlrInteger().intValue();
					
					if ( iCodCli != 0 ) {
						
						sSql = "SELECT OC.TXTOBSCLI FROM VDOBSCLI OC WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? AND SEQOBSCLI=? ";
						
						ps = con.prepareStatement( sSql );
						ps.setInt( 1, Aplicativo.iCodEmp );
						ps.setInt( 2, ListaCampos.getMasterFilial( "VDOBSCLI" ) );
						ps.setInt( 3, iCodCli );
						ps.setInt( 4, Integer.parseInt( tbObsData.getValor( tbObsData.getSelectedRow(), 2 ).toString() ) );
						rs = ps.executeQuery();
						
						if ( rs.next() ) {
							txaTxtObsCli.setVlrString( rs.getString( "TXTOBSCLI" ) );
						}
						
						rs.close();
						ps.close();
						
						if ( !con.getAutoCommit() ) {
							con.commit();
						}
					}
					
				}
				
			}
			
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Não foi possível carregar dados da tabela " + "observações de cliente (VDOBSCLI).\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}
	}

	private boolean duploCNPJ() {

		boolean bRetorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CNPJCLI FROM VDCLIENTE WHERE CNPJCLI=?";
		
		try {
		
			ps = con.prepareStatement( sSQL );
			ps.setString( 1, txtCnpjCli.getVlrString() );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				bRetorno = true;
			}
			
			rs.close();
			ps.close();
			
			if(!con.getAutoCommit()) {
				con.commit();
			}
			
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao checar CNPJ.\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRetorno;
	}

	private void editaHist() {

		int iLin = 0;
		int iCod = 0;
		Object oRets[];

		if ( ( iLin = tabHist.getLinhaSel() ) < 0 ) {
			Funcoes.mensagemInforma( this, "Não ha nenhum histórico selecionado!" );
			return;
		}
		iCod = txtCodCli.getVlrInteger().intValue();

		DLNovoHist dl = new DLNovoHist( iCod, 1, this );
		dl.setConexao( con );
		dl.setValores( new Object[] { 
				(String) tabHist.getValor( iLin, 6 ), 
				(String) tabHist.getValor( iLin, 4 ), 
				(String) tabHist.getValor( iLin, 1 ), 
				(String) tabHist.getValor( iLin, 2 ), 
				(Date) Funcoes.strDateToSqlDate( (String) tabHist.getValor( iLin, 5 ) ) 
				} );
		dl.setVisible( true );
		
		if ( dl.OK ) {
			
			oRets = dl.getValores();
			PreparedStatement ps = null;
			String sSQL = null;
			
			try {
				
				sSQL = "EXECUTE PROCEDURE TKSETHISTSP(?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Integer.parseInt( (String) tabHist.getValor( iLin, 0 ) ) );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setNull( 3, Types.INTEGER );
				ps.setNull( 4, Types.INTEGER );
				ps.setInt( 5, lcCampos.getCodFilial() );
				ps.setInt( 6, txtCodCli.getVlrInteger().intValue() );
				ps.setString( 7, (String) oRets[ 0 ] );// Descrição do historico
				ps.setInt( 8, ListaCampos.getMasterFilial( "ATATENDENTE" ) );// Filial do atendete
				ps.setString( 9, (String) oRets[ 1 ] );// codígo atendente
				ps.setString( 10, (String) oRets[ 2 ] );// status do historico
				ps.setDate( 11, (Date) oRets[ 4 ] );// data do historico
				ps.setString( 12, (String) oRets[ 3 ] );// status do historico
				ps.execute();
				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}
				
			} catch ( Exception err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao salvar o histórico!\n" + err.getMessage(), true, con, err );
			} finally {
				ps = null;
				sSQL = null;
			}
		}
		dl.dispose();
		carregaTabHist();
	}

	private void editObs() {

		int iCodCli = 0;
		PreparedStatement ps = null;
		StringBuffer sSql = new StringBuffer();
		DLInputText dl = null;
		iCodCli = txtCodCli.getVlrInteger().intValue();
		
		if ( ( tbObsData.getSelectedRow() > -1 ) && ( iCodCli != 0 ) ) {
			
			try {
				
				dl = new DLInputText( this, "Observação", false );
				dl.setTexto( txaTxtObsCli.getText() );
				dl.setVisible( true );

				if ( dl.OK ) {
					
					try {
						
						sSql.append( "UPDATE VDOBSCLI SET DTOBSCLI=?, HOBSCLI=? , TXTOBSCLI=? " );
						sSql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? AND SEQOBSCLI=?" );
						
						ps = con.prepareStatement( sSql.toString() );
						ps.setDate( 1, new Date( Calendar.getInstance().getTime().getTime() ) );
						ps.setTime( 2, new Time( Calendar.getInstance().getTime().getTime() ) );
						ps.setString( 3, dl.getTexto() );
						ps.setInt( 4, Aplicativo.iCodEmp );
						ps.setInt( 5, ListaCampos.getMasterFilial( "VDOBSCLI" ) );
						ps.setInt( 6, iCodCli );
						ps.setInt( 7, Integer.parseInt( tbObsData.getValor( tbObsData.getSelectedRow(), 2 ).toString() ) );
						ps.executeUpdate();
						ps.close();
						
						if ( !con.getAutoCommit() ) {
							con.commit();
						}
						
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( this, "Não foi possível alterar a observação.\n" + err.getMessage(), true, con, err );
					}
				}
				
				carregaTabelaObs();
				
			} finally {
				ps = null;
				sSql = null;
				dl = null;
			}
		}
	}

	private void exclObs() {

		int iCodCli = 0;
		PreparedStatement ps = null;
		String sSql = null;
		iCodCli = txtCodCli.getVlrInteger().intValue();
		
		if ( ( tbObsData.getSelectedRow() > -1 ) && ( iCodCli != 0 ) ) {
			
			if ( Funcoes.mensagemConfirma( this, "Confirma exclusão da mensagem?" ) == JOptionPane.YES_OPTION ) {
				
				try {
					
					sSql = "DELETE FROM VDOBSCLI WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? AND SEQOBSCLI=?";
					ps = con.prepareStatement( sSql );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "VDOBSCLI" ) );
					ps.setInt( 3, iCodCli );
					ps.setInt( 4, Integer.parseInt( tbObsData.getValor( tbObsData.getSelectedRow(), 2 ).toString() ) );
					ps.executeUpdate();
					ps.close();
					
					if ( !con.getAutoCommit() ) {
						con.commit();
					}
					
					carregaTabelaObs();
					
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Não foi possível excluir a observação.\n" + err.getMessage(), true, con, err );
					err.printStackTrace();
				} finally {
					ps = null;
					iCodCli = 0;
					sSql = null;
				}
				
			}
			
		}
	}

	private void excluiHist() {

		if ( tabHist.getLinhaSel() == -1 ) {
			Funcoes.mensagemInforma( this, "Selecione um item na lista!" );
			return;
		}
		else if ( Funcoes.mensagemConfirma( this, 
				"Deseja relamente excluir o histórico '" + tabHist.getValor( tabHist.getLinhaSel(), 0 ) + "'?" ) 
					!= JOptionPane.YES_OPTION ) {
			return;
		}

		PreparedStatement ps = null;
		String sSQL = null;
		
		try {
			
			sSQL = "DELETE FROM TKHISTORICO WHERE CODHISTTK=? AND CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement( sSQL );
			ps.setString( 1, (String) tabHist.getValor( tabHist.getLinhaSel(), 0 ) );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "TKHISTORICO" ) );
			ps.execute();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			carregaTabHist();
			
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao excluir o histórico!\n" + err.getMessage(), true, con, err );
		}
	}

	public void exec( int iCodCli ) {

		txtCodCli.setVlrString( iCodCli + "" );
		lcCampos.carregaDados();
	}

	private void geraHistorico( Integer iMes ) {

		PreparedStatement ps = null;
		String sSQL = null;
		Integer iCodAtende = getAtendente();

		if ( iCodAtende.compareTo( new Integer( 0 ) ) > 0 ) {
			
			try {
				
				sSQL = "EXECUTE PROCEDURE TKSETHISTSP(0,?,?,?,?,?,?,?,?,?,'" + iMes + "-01-" + txtAno.getVlrInteger() + "','V')";
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setNull( 2, Types.INTEGER );
				ps.setNull( 3, Types.INTEGER );
				ps.setInt( 4, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setInt( 5, txtCodCli.getVlrInteger().intValue() );
				ps.setString( 6, "CONTATO" );// Descrição do historico
				ps.setInt( 7, ListaCampos.getMasterFilial( "ATATENDENTE" ) );// Filial do atendete
				ps.setInt( 8, iCodAtende.intValue() );// codígo atendente
				ps.setString( 9, "EF" );// status do historico
				ps.execute();
				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro ao inserir historicos para o cliente.\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			} finally {
				ps = null;
				sSQL = null;
			}
		}
		else {
			Funcoes.mensagemInforma( this, 
					"Não é possivel gerar contatos para esse cliente, pois não existe um atendente\n" +
					"vinculado ao vendedor padrão!" );
		}

		carregaTabHist();
	}

	private void geraHistoricos( Integer iMes ) {

		Integer iCodAtende = getAtendente();

		if ( iCodAtende.compareTo( new Integer( 0 ) ) > 0 ) {
			
			HashMap<String,HashMap> hmMeses = new HashMap<String,HashMap>();
			HashMap<String,Integer> hmJan = new HashMap<String,Integer>();
			HashMap<String,Integer> hmFev = new HashMap<String,Integer>();
			HashMap<String,Integer> hmMar = new HashMap<String,Integer>();
			HashMap<String,Integer> hmAbr = new HashMap<String,Integer>();
			HashMap<String,Integer> hmMai = new HashMap<String,Integer>();
			HashMap<String,Integer> hmJun = new HashMap<String,Integer>();
			HashMap<String,Integer> hmJul = new HashMap<String,Integer>();
			HashMap<String,Integer> hmAgo = new HashMap<String,Integer>();
			HashMap<String,Integer> hmSet = new HashMap<String,Integer>();
			HashMap<String,Integer> hmOut = new HashMap<String,Integer>();
			HashMap<String,Integer> hmNov = new HashMap<String,Integer>();
			HashMap<String,Integer> hmDez = new HashMap<String,Integer>();

			hmJan.put( "ANT", txtAntQtdContJan.getVlrInteger() );
			hmJan.put( "NOVO", txtNovaQtdContJan.getVlrInteger() );
			hmFev.put( "ANT", txtAntQtdContFev.getVlrInteger() );
			hmFev.put( "NOVO", txtNovaQtdContFev.getVlrInteger() );
			hmMar.put( "ANT", txtAntQtdContMar.getVlrInteger() );
			hmMar.put( "NOVO", txtNovaQtdContMar.getVlrInteger() );
			hmAbr.put( "ANT", txtAntQtdContAbr.getVlrInteger() );
			hmAbr.put( "NOVO", txtNovaQtdContAbr.getVlrInteger() );
			hmMai.put( "ANT", txtAntQtdContMai.getVlrInteger() );
			hmMai.put( "NOVO", txtNovaQtdContMai.getVlrInteger() );
			hmJun.put( "ANT", txtAntQtdContJun.getVlrInteger() );
			hmJun.put( "NOVO", txtNovaQtdContJun.getVlrInteger() );
			hmJul.put( "ANT", txtAntQtdContJul.getVlrInteger() );
			hmJul.put( "NOVO", txtNovaQtdContJul.getVlrInteger() );
			hmAgo.put( "ANT", txtAntQtdContAgo.getVlrInteger() );
			hmAgo.put( "NOVO", txtNovaQtdContAgo.getVlrInteger() );
			hmSet.put( "ANT", txtAntQtdContSet.getVlrInteger() );
			hmSet.put( "NOVO", txtNovaQtdContSet.getVlrInteger() );
			hmOut.put( "ANT", txtAntQtdContOut.getVlrInteger() );
			hmOut.put( "NOVO", txtNovaQtdContOut.getVlrInteger() );
			hmNov.put( "ANT", txtAntQtdContNov.getVlrInteger() );
			hmNov.put( "NOVO", txtNovaQtdContNov.getVlrInteger() );
			hmDez.put( "ANT", txtAntQtdContDez.getVlrInteger() );
			hmDez.put( "NOVO", txtNovaQtdContDez.getVlrInteger() );

			hmMeses.put( "1", hmJan );
			hmMeses.put( "2", hmFev );
			hmMeses.put( "3", hmMar );
			hmMeses.put( "4", hmAbr );
			hmMeses.put( "5", hmMai );
			hmMeses.put( "6", hmJun );
			hmMeses.put( "7", hmJul );
			hmMeses.put( "8", hmAgo );
			hmMeses.put( "9", hmSet );
			hmMeses.put( "10", hmOut );
			hmMeses.put( "11", hmNov );
			hmMeses.put( "12", hmDez );

			if ( iMes == null ) {
				for ( int iM = 1; iM < 13; iM++ ) {
					int iQtdAnt = ( (Integer) ( (HashMap) ( hmMeses.get( String.valueOf(iM) ) ) ).get( "ANT" ) ).intValue();
					int iQtdNov = ( (Integer) ( (HashMap) ( hmMeses.get( String.valueOf(iM) ) ) ).get( "NOVO" ) ).intValue();

					if ( iQtdNov > 0 ) {
						if ( iQtdAnt > iQtdNov ) {
							Funcoes.mensagemInforma( this, 
									"A nova quantidade informada é menor ou igual a quantidade atual, " + 
									"\n você deve excluir os contatos manualmente." );
						}
						else if ( iQtdNov > iQtdAnt ) {
							for ( int i = 0; ( iQtdNov - iQtdAnt ) > i; i++ ) {
								geraHistorico( new Integer( iM ) );
							}
						}
					}
				}
			}
			else {
				int iQtdAnt = ( (Integer) ( (HashMap) ( hmMeses.get( String.valueOf(iMes) ) ) ).get( "ANT" ) ).intValue();
				int iQtdNov = ( (Integer) ( (HashMap) ( hmMeses.get( String.valueOf(iMes) ) ) ).get( "NOVO" ) ).intValue();
				if ( iQtdNov > 0 ) {
					if ( iQtdAnt > iQtdNov ) {
						Funcoes.mensagemInforma( this, 
								"A nova quantidade informada é menor ou igual a quantidade atual, " + 
								"\n você deve excluir os contatos manualmente." );
					}
					else if ( iQtdNov > iQtdAnt ) {
						for ( int i = 0; ( iQtdNov - iQtdAnt ) > i; i++ ) {
							geraHistorico( iMes );
						}
					}
				}
			}
			getContatos();
			carregaTabHist();
		}
		else {
			Funcoes.mensagemInforma( this, 
					"Não é possivel gerar contatos para esse cliente, pois não existe um atendente\n" 
					+ "vinculado ao vendedor padrão!" );
		}
	}

	private Object[] getAgente() {

		Object[] oRet = new Object[ 2 ];
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		
		try {
			
			sSQL = "SELECT U.CODAGE,U.TIPOAGE FROM SGUSUARIO U WHERE CODEMP=? AND CODFILIAL=? AND IDUSU=?";
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, Aplicativo.strUsuario );
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				oRet[ 0 ] = new Integer( rs.getInt( 1 ) );
				oRet[ 1 ] = rs.getString( 2 );
			}
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		return oRet;
	}

	private Integer getAtendente() {

		Integer iRet = new Integer( 0 );
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSql = null;
		
		try {
			
			sSql = "SELECT CODATEND FROM ATATENDENTE WHERE CODEMPVE=? AND CODFILIALVE=? AND CODVEND=?";
			
			ps = con.prepareStatement( sSql );
			ps.setInt( 1, lcVend.getCodEmp() );
			ps.setInt( 2, lcVend.getCodFilial() );
			ps.setInt( 3, txtCodVend.getVlrInteger().intValue() );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				iRet = new Integer( rs.getInt( 1 ) );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro na busca de atendente vinculado ao vendedor.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSql = null;
		}
		return iRet;
	}

	private void getContatos() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSql = new StringBuffer();
		int iMes = 0;
		int iQtd = 0;

		try {

			txtAntQtdContJan.setVlrString( "" );
			txtNovaQtdContJan.setVlrString( "" );
			txtAntQtdContFev.setVlrString( "" );
			txtNovaQtdContFev.setVlrString( "" );
			txtAntQtdContMar.setVlrString( "" );
			txtNovaQtdContMar.setVlrString( "" );
			txtAntQtdContAbr.setVlrString( "" );
			txtNovaQtdContAbr.setVlrString( "" );
			txtAntQtdContMai.setVlrString( "" );
			txtNovaQtdContMai.setVlrString( "" );
			txtAntQtdContJun.setVlrString( "" );
			txtNovaQtdContJun.setVlrString( "" );
			txtAntQtdContJul.setVlrString( "" );
			txtNovaQtdContJul.setVlrString( "" );
			txtAntQtdContAgo.setVlrString( "" );
			txtNovaQtdContAgo.setVlrString( "" );
			txtAntQtdContSet.setVlrString( "" );
			txtNovaQtdContSet.setVlrString( "" );
			txtAntQtdContOut.setVlrString( "" );
			txtNovaQtdContOut.setVlrString( "" );
			txtAntQtdContNov.setVlrString( "" );
			txtNovaQtdContNov.setVlrString( "" );
			txtAntQtdContDez.setVlrString( "" );
			txtNovaQtdContDez.setVlrString( "" );

			sSql.append( "SELECT EXTRACT(MONTH FROM TK.DATAHISTTK),COUNT(1) FROM TKHISTORICO TK " ); 
			sSql.append( "WHERE TK.CODEMP=? AND TK.CODFILIAL=? AND EXTRACT(YEAR FROM TK.DATAHISTTK)=? " );
			sSql.append( "AND CODEMPCL=? AND CODFILIALCL=? AND CODCLI=? " );
			sSql.append( "GROUP BY 1 ORDER BY 1" );

			ps = con.prepareStatement( sSql.toString() );
			ps.setInt( 1, lcCampos.getCodEmp() );
			ps.setInt( 2, lcCampos.getCodFilial() );
			ps.setInt( 3, txtAno.getVlrInteger().intValue() );
			ps.setInt( 4, lcCampos.getCodEmp() );
			ps.setInt( 5, lcCampos.getCodFilial() );
			ps.setInt( 6, txtCodCli.getVlrInteger().intValue() );

			rs = ps.executeQuery();

			while ( rs.next() ) {
				
				iMes = rs.getInt( 1 );
				iQtd = rs.getInt( 2 );

				if ( iMes == 1 ) {
					txtAntQtdContJan.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContJan.setVlrString( "" );
				}
				else if ( iMes == 2 ) {
					txtAntQtdContFev.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContFev.setVlrString( "" );
				}
				else if ( iMes == 3 ) {
					txtAntQtdContMar.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContMar.setVlrString( "" );
				}
				else if ( iMes == 4 ) {
					txtAntQtdContAbr.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContAbr.setVlrString( "" );
				}
				else if ( iMes == 5 ) {
					txtAntQtdContMai.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContMai.setVlrString( "" );
				}
				else if ( iMes == 6 ) {
					txtAntQtdContJun.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContJun.setVlrString( "" );
				}
				else if ( iMes == 7 ) {
					txtAntQtdContJul.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContJul.setVlrString( "" );
				}
				else if ( iMes == 8 ) {
					txtAntQtdContAgo.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContAgo.setVlrString( "" );
				}
				else if ( iMes == 9 ) {
					txtAntQtdContSet.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContSet.setVlrString( "" );
				}
				else if ( iMes == 10 ) {
					txtAntQtdContOut.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContOut.setVlrString( "" );
				}
				else if ( iMes == 11 ) {
					txtAntQtdContNov.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContNov.setVlrString( "" );
				}
				else if ( iMes == 12 ) {
					txtAntQtdContDez.setVlrInteger( new Integer( iQtd ) );
					txtNovaQtdContDez.setVlrString( "" );
				}
				
			}

			rs.close();
			ps.close();
			
			if(!con.getAutoCommit()) {
				con.commit();
			}

		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro na busca de atendente vinculado ao vendedor.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSql = null;
		}
	}

	private boolean[] getPrefere() {

		boolean[] bRet = new boolean[ 5 ];
		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			sSQL = "SELECT SETORVENDA,RGCLIOBRIG,CLIMESMOCNPJ,CNPJOBRIGCLI,CONSISTEIECLI FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";

			try {
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, Aplicativo.iCodFilial );
				rs = ps.executeQuery();
				
				if ( rs.next() ) {
					
					bRet[ 0 ] = "CA".indexOf( rs.getString( "SetorVenda" ) ) >= 0;
					bRet[ 1 ] = "S".equals( rs.getString( "RGCLIOBRIG" ) );
					bRet[ 2 ] = "S".equals( rs.getString( "CLIMESMOCNPJ" ) );
					bRet[ 3 ] = "S".equals( rs.getString( "CLIMESMOCNPJ" ) );
					bRet[ 4 ] = "S".equals( rs.getString( "CONSISTEIECLI" ) );
				}
				
				rs.close();
				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao verificar preferências!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		return bRet;
	}

	private void grpCli() {

		DLGrpCli dl = null;
		int iCodCli = 0;
		int iCodPesq = txtCodPesq.getVlrInteger().intValue();
		try {
			if ( iCodPesq != 0 ) {
				dl = new DLGrpCli( this );
				dl.carregaClientes( con, iCodPesq, txtDescPesq.getVlrString() );
				dl.setVisible( true );
				if ( dl.OK ) {
					iCodCli = dl.getCodCli();
					if ( iCodCli != 0 ) {
						txtCodCli.setVlrInteger( new Integer( iCodCli ) );
						lcCampos.carregaDados();
					}
				}
			}
		} finally {
			dl = null;
			iCodPesq = 0;
			iCodCli = 0;
		}
	}

	private void imprimir( boolean bVisualizar ) {
		
		ImprimeOS imp = new ImprimeOS( "", con );
		String[] sValores;

		DLRCliente dl = new DLRCliente( this, con );
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}

		sValores = dl.getValores();

		if ( "1".equals( sValores[ 7 ] ) ) {
			imprimeResumido1( imp, sValores );
		}
		else if ( "2".equals( sValores[ 7 ] ) ) {
			imprimeResumido2( imp, sValores );			
		}
		else if ( "3".equals( sValores[ 7 ] ) ) {
			imprimeResumido3( imp, sValores );			
		}
		else if ( "C".equals( sValores[ 7 ] ) ) {
			imprimeCompleto( imp, sValores );			
		}
		else if ( "A".equals( sValores[ 7 ] ) ) {
			imprimeAlinhaFilial( imp, sValores );
		}
		
		dl.dispose();	

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else { 
			imp.print();
		}

	}
	
	private void imprimeResumido1( final ImprimeOS imp, final String[] sValores ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String sObs = "";
		StringBuffer sWhere = new StringBuffer();
		String sFrom = "";
		String linhaFina = Funcoes.replicate( "-", 133 );
		Vector vObs = null;
		FAndamento And = null;
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;
		

		if ( sValores[ 1 ].equals( "S" ) ) {
			sObs = ",C1.OBSCLI ";
		}

		if ( sValores[ 2 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.RAZCLI >='" + sValores[ 2 ] + "'" );
			imp.addSubTitulo( "RAZAO MAIORES QUE " + sValores[ 2 ].trim() );
		}
		if ( sValores[ 3 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.RAZCLI <='" + sValores[ 3 ] + "'" );
			imp.addSubTitulo( "RAZAO MENORES QUE " + sValores[ 3 ].trim() );
		}
		if ( sValores[ 4 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'F'" );
			imp.addSubTitulo( "PESSOAS JURIDICAS" );
		}
		if ( sValores[ 5 ].length() > 0 ) {
			if( "A".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDCLI=" );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDENT=" );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDCOB=" );
			}
			sWhere.append( "'" + sValores[ 5 ] + "'" );
			imp.addSubTitulo( "CIDADE = " + sValores[ 5 ].trim() );
		}
		if ( sValores[ 6 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'J'" );
			imp.addSubTitulo( "PESSOAS FISICA" );
		}
		if ( !sValores[ 13 ].trim().equals( "" ) ) {
			sWhere.append( " AND C1.CODVEND =" + sValores[ 13 ] );
			imp.addSubTitulo( "REPRES. = " + sValores[ 13 ] + "-" + sValores[ 14 ] );
		}
		if ( sValores[ 8 ].length() > 0 ) {
			if ( !bPref[ 0 ] ) {
				sFrom = ",VDVENDEDOR V ";
				sWhere.append( " AND C1.CODEMPVD=V.CODEMP AND C1.CODFILIALVD=V.CODFILIAL AND C1.CODVEND=V.CODVEND AND V.CODSETOR = " + sValores[ 8 ] );;
			}
			else {
				sWhere.append( " AND C1.CODSETOR = " + sValores[ 8 ] );
			}
			imp.addSubTitulo( "SETOR = " + sValores[ 9 ] );
		}
		if ( sValores[ 10 ].length() > 0 ) {
			sWhere.append( " AND C1.CODTIPOCLI=" + sValores[ 10 ] );
			imp.addSubTitulo( "TIPO DE CLIENTE=" + sValores[ 11 ] );
		}
		if ( sValores[ 15 ].length() > 0 ) {
			sWhere.append( " AND C1.CODCLASCLI=" + sValores[ 15 ] );
			imp.addSubTitulo( "CLASSIFICACAO DO CLIENTE = " + sValores[ 16 ] );
		}
		if ( sValores[ 18 ].length() > 0 ) {
			if( "A".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRCLI=" );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRENT=" );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRCOB=" );
			}
			sWhere.append( "'" + sValores[ 18 ] + "'" );
			imp.addSubTitulo( "BAIRRO = " + sValores[ 18 ] );
		}

		try {				

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Clientes" );
			
			ps = con.prepareStatement( "SELECT COUNT(*) FROM VDCLIENTE C1" + sFrom + " WHERE C1.CODEMP=? AND C1.CODFILIAL=? " + sWhere.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();
			rs.next();
			
			And = new FAndamento( "Montando Relatório, Aguarde!", 0, rs.getInt( 1 ) - 1 );
			And.setVisible( true );
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			sSQL.append( "SELECT C1.CODCLI,C1.RAZCLI," );
			if( "A".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCLI,C1.CIDCLI," );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDENT AS ENDCLI,C1.CIDENT AS CIDCLI," );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCOB AS ENDCLI,C1.CIDCOB AS CIDCLI," );
			}
			sSQL.append( "C1.FONECLI,C1.DDDCLI,C1.CODPESQ " );
			sSQL.append(  sObs );
			sSQL.append( "FROM VDCLIENTE C1 " );
			sSQL.append( sFrom );
			sSQL.append( "WHERE C1.CODEMP=? AND C1.CODFILIAL=? " );
			sSQL.append( sWhere );
			sSQL.append( " ORDER BY " + sValores[ 0 ] );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();

			while ( rs.next() ) {
				
				if ( imp.pRow() >= linPag ) {
					
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + linhaFina + "+" );
					imp.incPags();
					imp.eject();
					
				}
				
				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 136, true );
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" + linhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|  Código" );
					imp.say( 12, "|  Razão:" );
					imp.say( 46, "|  Matriz" );
					imp.say( 57, "|  Endereço:" );
					imp.say( 93, "|  Cidade:" );
					imp.say( 117, "|  Tel:" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + linhaFina + "|" );
					
				}
				
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 4, rs.getString( "CodCli" ) );
				imp.say( 12, "|" );
				imp.say( 14, rs.getString( "RazCli" ) != null ? rs.getString( "RazCli" ).substring( 0, 30 ) : "" );
				imp.say( 46, "|" );

				if ( rs.getString( "CodPesq" ) != null && !rs.getString( "CodPesq" ).equals( rs.getString( "CodCLi" ) ) ) {
					imp.say( 49, String.valueOf( rs.getString( "CodPesq" ) ) );
				}

				imp.say( 57, "|" );
				imp.say( 59, rs.getString( "EndCli" ) != null ? rs.getString( "EndCli" ).substring( 0, 30 ) : "" );
				imp.say( 93, "|" );
				imp.say( 96, rs.getString( "CidCli" ) != null ? rs.getString( "CidCli" ).substring( 0, 20 ) : "" );
				imp.say( 117, "|" );
				imp.say( 120, ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ).trim() );
				imp.say( 135, "|" );

				if ( !sObs.equals( "" ) ) {
					
					vObs = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsCli" ) ), 101 );
					
					for ( int i = 0; i < vObs.size(); i++ ) {
						
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 14, vObs.elementAt( i ).toString() );
						imp.say( 117, "|" );
						imp.say( 135, "|" );
						
						if ( imp.pRow() >= linPag ) {
							
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 0, "+" + linhaFina + "+" );
							imp.incPags();
							imp.eject();
							
						}
						
					}
					
				}
				
				And.atualiza( iContaReg );
				iContaReg++;
				
			}
			
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + linhaFina + "+" );
			
			imp.eject();
			imp.fechaGravacao();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			And.dispose();
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de contatos!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}
	
	private void imprimeResumido2( final ImprimeOS imp, final String[] sValores ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String sObs = "";
		StringBuffer sWhere = new StringBuffer();
		String sFrom = "";
		String linhaFina = Funcoes.replicate( "-", 133 );
		Vector vObs = null;
		FAndamento And = null;
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;
		

		if ( sValores[ 1 ].equals( "S" ) ) {
			sObs = ",C1.OBSCLI ";
		}

		if ( sValores[ 2 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.RAZCLI >='" + sValores[ 2 ] + "'" );
			imp.addSubTitulo( "RAZAO MAIORES QUE " + sValores[ 2 ].trim() );
		}
		if ( sValores[ 3 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.RAZCLI <='" + sValores[ 3 ] + "'" );
			imp.addSubTitulo( "RAZAO MENORES QUE " + sValores[ 3 ].trim() );
		}
		if ( sValores[ 4 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'F'" );
			imp.addSubTitulo( "PESSOAS JURIDICAS" );
		}
		if ( sValores[ 5 ].length() > 0 ) {
			if( "A".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDCLI=" );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDENT=" );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDCOB=" );
			}
			sWhere.append( "'" + sValores[ 5 ] + "'" );
			imp.addSubTitulo( "CIDADE = " + sValores[ 5 ].trim() );
		}
		if ( sValores[ 6 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'J'" );
			imp.addSubTitulo( "PESSOAS FISICA" );
		}
		if ( !sValores[ 13 ].trim().equals( "" ) ) {
			sWhere.append( " AND C1.CODVEND =" + sValores[ 13 ] );
			imp.addSubTitulo( "REPRES. = " + sValores[ 13 ] + "-" + sValores[ 14 ] );
		}
		if ( sValores[ 8 ].length() > 0 ) {
			if ( !bPref[ 0 ] ) {
				sFrom = ",VDVENDEDOR V ";
				sWhere.append( " AND C1.CODEMPVD=V.CODEMP AND C1.CODFILIALVD=V.CODFILIAL AND C1.CODVEND=V.CODVEND AND V.CODSETOR = " + sValores[ 8 ] );
			}
			else {
				sWhere.append( " AND C1.CODSETOR = " + sValores[ 8 ] );
			}
			imp.addSubTitulo( "SETOR = " + sValores[ 9 ] );
		}
		if ( sValores[ 10 ].length() > 0 ) {
			sWhere.append( " AND C1.CODTIPOCLI=" + sValores[ 10 ] );
			imp.addSubTitulo( "TIPO DE CLIENTE=" + sValores[ 11 ] );
		}
		if ( sValores[ 15 ].length() > 0 ) {
			sWhere.append( " AND C1.CODCLASCLI=" + sValores[ 15 ] );
			imp.addSubTitulo( "CLASSIFICACAO DO CLIENTE = " + sValores[ 16 ] );
		}
		if ( sValores[ 18 ].length() > 0 ) {
			if( "A".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRCLI=" );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRENT=" );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRCOB=" );
			}
			sWhere.append( "'" + sValores[ 18 ] + "'" );
			imp.addSubTitulo( "BAIRRO = " + sValores[ 18 ] );
		}

		try {				

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Clientes" );
			
			ps = con.prepareStatement( "SELECT COUNT(*) FROM VDCLIENTE C1" + sFrom + " WHERE C1.CODEMP=? AND C1.CODFILIAL=? " + sWhere.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();
			rs.next();
			
			And = new FAndamento( "Montando Relatório, Aguarde!", 0, rs.getInt( 1 ) - 1 );
			And.setVisible( true );
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			sSQL.append( "SELECT C1.CODCLI,C1.RAZCLI,C1.NOMECLI,C1.FONECLI," );
			if( "A".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCLI,C1.CIDCLI,C1.NUMCLI," );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDENT AS ENDCLI,C1.CIDENT AS CIDCLI,C1.NUMENT AS NUMCLI," );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCOB AS ENDCLI,C1.CIDCOB AS CIDCLI,C1.NUMCOB AS NUMCLI," );
			}
			sSQL.append( "C1.FAXCLI,C1.DDDCLI,C1.CONTCLI,C1.EMAILCLI,C1.SITECLI " );
			sSQL.append( sObs );
			sSQL.append( "FROM VDCLIENTE C1 " );
			sSQL.append( sFrom );
			sSQL.append( "WHERE C1.CODEMP=? AND C1.CODFILIAL=? " );
			sSQL.append( sWhere );
			sSQL.append( " ORDER BY " + sValores[ 0 ] );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();

			while ( rs.next() ) {

				if ( imp.pRow() >= linPag ) {
					
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + linhaFina + "+" );
					imp.incPags();
					imp.eject();
					
				}

				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 136, true );
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" + linhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|  Código" );
					imp.say( 10, "|  Razão Social / Nome Fantasia" );
					imp.say( 42, "|  Contato / E-Mail" );
					imp.say( 74, "|  Endereço / Web Site" );
					imp.say( 117, "|   Tel / Fax" );
					imp.say( 135, "|" );
					
				}					

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" + linhaFina + "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 2, rs.getString( "CodCli" ) );
				imp.say( 10, "|" );
				imp.say( 11, rs.getString( "RazCli" ) != null ? rs.getString( "RazCli" ).substring( 0, 30 ) : "" );
				imp.say( 42, "|" );
				imp.say( 43, rs.getString( "ContCli" ) != null ? rs.getString( "ContCli" ).substring( 0, 30 ) : "" );
				imp.say( 74, "|" );
				imp.say( 76, ( rs.getString( "EndCli" ) != null ? rs.getString( "EndCli" ).substring( 0, 30 ) : "" ).trim() + ", " + ( rs.getString( "NumCli" ) != null ? rs.getString( "NumCli" ) : "" ) );
				imp.say( 117, "|" );
				imp.say( 119, ( rs.getString( "FoneCli" ) != null ? ( ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) ) : "" ).trim() );
				imp.say( 135, "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 10, "|" );
				imp.say( 11, rs.getString( "NomeCli" ) != null ? rs.getString( "NomeCli" ).substring( 0, 30 ) : "" );
				imp.say( 42, "|" );
				imp.say( 43, rs.getString( "EmailCli" ) != null ? rs.getString( "EmailCli" ).substring( 0, 30 ) : "" );
				imp.say( 74, "|" );
				imp.say( 76, ( rs.getString( "SiteCli" ) != null ? rs.getString( "SiteCli" ).substring( 0, 30 ) : "" ) );
				imp.say( 117, "|" );
				imp.say( 119, ( rs.getString( "FaxCli" ) != null ? ( ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + Funcoes.setMascara( rs.getString( "FaxCli" ).trim(), "####-####" ) ) : "" ).trim() );
				imp.say( 135, "|" );

				if ( !sObs.equals( "" ) ) {
					
					vObs = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsCli" ) ), 101 );
					
					for ( int i = 0; i < vObs.size(); i++ ) {
						
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 14, vObs.elementAt( i ).toString() );
						imp.say( 117, "|" );
						imp.say( 135, "|" );
						
						if ( imp.pRow() >= linPag ) {
							imp.incPags();
							imp.eject();
						}
						
					}
					
				}
				
				And.atualiza( iContaReg );
				iContaReg++;
				
			}
			
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + linhaFina + "+" );
			
			imp.eject();
			imp.fechaGravacao();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			And.dispose();
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de contatos!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}
	
	private void imprimeResumido3( final ImprimeOS imp, final String[] sValores ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String sObs = "";
		StringBuffer sWhere = new StringBuffer();
		String sFrom = "";
		String linhaFina = Funcoes.replicate( "-", 133 );
		Vector vObs = null;
		FAndamento And = null;
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;
		

		if ( sValores[ 1 ].equals( "S" ) ) {
			sObs = ",C1.OBSCLI ";
		}

		if ( sValores[ 2 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.NOMECLI >='" + sValores[ 2 ] + "'" );
			imp.addSubTitulo( "NOMES MAIORES QUE " + sValores[ 2 ].trim() );
		}
		if ( sValores[ 3 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.NOMECLI <='" + sValores[ 3 ] + "'" );
			imp.addSubTitulo( "NOMES MENORES QUE " + sValores[ 3 ].trim() );
		}
		if ( sValores[ 4 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'F'" );
			imp.addSubTitulo( "PESSOAS JURIDICAS" );
		}
		if ( sValores[ 5 ].length() > 0 ) {
			if( "A".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDCLI=" );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDENT=" );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDCOB=" );
			}
			sWhere.append( "'" + sValores[ 5 ] + "'" );
			imp.addSubTitulo( "CIDADE = " + sValores[ 5 ].trim() );
		}
		if ( sValores[ 6 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'J'" );
			imp.addSubTitulo( "PESSOAS FISICA" );
		}
		if ( !sValores[ 13 ].trim().equals( "" ) ) {
			sWhere.append( " AND C1.CODVEND =" + sValores[ 13 ] );
			imp.addSubTitulo( "REPRESENTANTE = " + sValores[ 13 ] + "-" + sValores[ 14 ] );
		}
		if ( sValores[ 8 ].length() > 0 ) {
			if ( !bPref[ 0 ] ) {
				sFrom = ",VDVENDEDOR V ";
				sWhere.append( " AND C1.CODEMPVD=V.CODEMP AND C1.CODFILIALVD=V.CODFILIAL AND C1.CODVEND=V.CODVEND AND V.CODSETOR = " + sValores[ 8 ] );;
			}
			else {
				sWhere.append( " AND C1.CODSETOR = " + sValores[ 8 ] );
			}
			imp.addSubTitulo( "SETOR = " + sValores[ 9 ] );
		}
		if ( sValores[ 10 ].length() > 0 ) {
			sWhere.append( " AND C1.CODTIPOCLI=" + sValores[ 10 ] );
			imp.addSubTitulo( "TIPO DE CLIENTE=" + sValores[ 11 ] );
		}
		if ( sValores[ 15 ].length() > 0 ) {
			sWhere.append( " AND C1.CODCLASCLI=" + sValores[ 15 ] );
			imp.addSubTitulo( "CLASSIFICACAO DO CLIENTE = " + sValores[ 16 ] );
		}
		if ( sValores[ 18 ].length() > 0 ) {
			if( "A".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRCLI=" );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRENT=" );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRCOB=" );
			}
			sWhere.append( "'" + sValores[ 18 ] + "'" );
			imp.addSubTitulo( "BAIRRO = " + sValores[ 18 ] );
		}

		try {				

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Clientes" );
			
			ps = con.prepareStatement( "SELECT COUNT(*) FROM VDCLIENTE C1" + sFrom + " WHERE C1.CODEMP=? AND C1.CODFILIAL=? " + sWhere.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();
			rs.next();
			
			And = new FAndamento( "Montando Relatório, Aguarde!", 0, rs.getInt( 1 ) - 1 );
			And.setVisible( true );
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			sSQL.append( "SELECT C1.CODCLI,C1.NOMECLI," );
			if( "A".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCLI,C1.NUMCLI,C1.BAIRCLI,C1.CIDCLI,C1.COMPLCLI,C1.UFCLI," );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDENT AS ENDCLI,C1.NUMENT AS NUMCLI,C1.BAIRENT AS BAIRCLI," );
				sSQL.append( "C1.CIDENT AS CIDCLI,C1.COMPLENT AS COMPLCLI,C1.UFENT AS UFCLI," );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCOB AS ENDCLI,C1.NUMCOB AS NUMCLI,C1.BAIRCOB AS BAIRCLI," );
				sSQL.append( "C1.CIDCOB AS CIDCLI,C1.COMPLCOB AS COMPLCLI,C1.UFCOB AS UFCLI," );
			}
			sSQL.append( "C1.FONECLI,C1.DDDCLI " );
			sSQL.append(  sObs );
			sSQL.append( "FROM VDCLIENTE C1 " );
			sSQL.append( sFrom );
			sSQL.append( "WHERE C1.CODEMP=? AND C1.CODFILIAL=? " );
			sSQL.append( sWhere );
			sSQL.append( " ORDER BY " + sValores[ 0 ] );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();

			while ( rs.next() ) {
				
				if ( imp.pRow() >= linPag ) {
					
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + linhaFina + "+" );
					imp.incPags();
					imp.eject();
					
				}
				
				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 136, true );
					imp.say( 0, "|" + linhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|  Código" );
					imp.say( 9, "|  Nome fantazia:" );
					imp.say( 40, "|  Endereço:" );
					imp.say( 76, "|  Bairro:" );
					imp.say( 96, "|  Cidade:" );
					imp.say( 116, "|UF" );
					imp.say( 119, "|     Fone" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + linhaFina + "|" );
					
				}
				
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" + Funcoes.alinhaDir( rs.getString( "CODCLI" ), 8 ) );
				imp.say( 9, "|" + ( rs.getString( "NOMECLI" ) != null ? Funcoes.copy( rs.getString( "NOMECLI" ), 29 ) : "" ) );
				imp.say( 40, "|" + ( rs.getString( "ENDCLI" ) != null ? Funcoes.copy( rs.getString( "ENDCLI" ), 30 ) : "" ) );
				imp.say( 71, rs.getString( "NUMCLI" ) != null ? Funcoes.alinhaDir( rs.getString( "NUMCLI" ), 5 ) : "" );
				imp.say( 76, "|" + ( rs.getString( "BAIRCLI" ) != null ? Funcoes.copy( rs.getString( "BAIRCLI" ), 19 ) : "" ) );
				imp.say( 96, "|" + ( rs.getString( "CIDCLI" ) != null ? Funcoes.copy( rs.getString( "CIDCLI" ), 19 ) : "" ) );
				imp.say( 116, "|" + ( rs.getString( "UFCLI" ) != null ? Funcoes.copy( rs.getString( "UFCLI" ), 2 ) : "" ) );
				imp.say( 119, "|" + ( rs.getString( "DDDCLI" ) != null ? ("(" + rs.getString( "DDDCLI" ) + ")") : "" ) + ( rs.getString( "FONECLI" ) != null ? Funcoes.setMascara( rs.getString( "FONECLI" ).trim(), "####-####" ) : "" ).trim() );
				imp.say( 135, "|" );

				if ( "S".equals( sValores[ 1 ] ) ) {
					
					vObs = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsCli" ) ), 100 );
					
					for ( int i = 0; i < vObs.size(); i++ ) {
						
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|      Obs: " + vObs.elementAt( i ).toString() );
						imp.say( 119, "|" );
						imp.say( 135, "|" );
						
						if ( imp.pRow() >= linPag ) {
							
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 0, "+" + linhaFina + "+" );
							imp.incPags();
							imp.eject();
							
						}
						
					}
					
				}
				
				And.atualiza( iContaReg );
				iContaReg++;
				
			}
			
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + linhaFina + "+" );
			
			imp.eject();
			imp.fechaGravacao();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			And.dispose();
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de contatos!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}
	
	private void imprimeCompleto( final ImprimeOS imp, final String[] sValores ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String sObs = "";
		StringBuffer sWhere = new StringBuffer();
		String sFrom = "";
		String linhaFina = Funcoes.replicate( "-", 133 );
		FAndamento And = null;
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;
		

		if ( sValores[ 1 ].equals( "S" ) ) {
			sObs = ",C1.OBSCLI ";
		}

		if ( sValores[ 2 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.RAZCLI >='" + sValores[ 2 ] + "'" );
			imp.addSubTitulo( "RAZAO MAIORES QUE " + sValores[ 2 ].trim() );
		}
		if ( sValores[ 3 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.RAZCLI <='" + sValores[ 3 ] + "'" );
			imp.addSubTitulo( "RAZAO MENORES QUE " + sValores[ 3 ].trim() );
		}
		if ( sValores[ 4 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'F'" );
			imp.addSubTitulo( "PESSOAS JURIDICAS" );
		}
		if ( sValores[ 5 ].length() > 0 ) {
			if( "A".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDCLI=" );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDENT=" );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.CIDCOB=" );
			}
			sWhere.append( "'" + sValores[ 5 ] + "'" );
			imp.addSubTitulo( "CIDADE = " + sValores[ 5 ].trim() );
		}
		if ( sValores[ 6 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'J'" );
			imp.addSubTitulo( "PESSOAS FISICA" );
		}
		if ( !sValores[ 13 ].trim().equals( "" ) ) {
			sWhere.append( " AND C1.CODVEND =" + sValores[ 13 ] );
			imp.addSubTitulo( "REPRES. = " + sValores[ 13 ] + "-" + sValores[ 14 ] );
		}
		if ( sValores[ 8 ].length() > 0 ) {
			if ( !bPref[ 0 ] ) {
				sFrom = ",VDVENDEDOR V ";
				sWhere.append( " AND C1.CODEMPVD=V.CODEMP AND C1.CODFILIALVD=V.CODFILIAL AND C1.CODVEND=V.CODVEND AND V.CODSETOR = " + sValores[ 8 ] );
			}
			else {
				sWhere.append( " AND C1.CODSETOR = " + sValores[ 8 ] );
			}
			imp.addSubTitulo( "SETOR = " + sValores[ 9 ] );
		}
		if ( sValores[ 10 ].length() > 0 ) {
			sWhere.append( " AND C1.CODTIPOCLI=" + sValores[ 10 ] );
			imp.addSubTitulo( "TIPO DE CLIENTE=" + sValores[ 11 ] );
		}
		if ( sValores[ 15 ].length() > 0 ) {
			sWhere.append( " AND C1.CODCLASCLI=" + sValores[ 15 ] );
			imp.addSubTitulo( "CLASSIFICACAO DO CLIENTE = " + sValores[ 16 ] );
		}
		if ( sValores[ 18 ].length() > 0 ) {
			if( "A".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRCLI=" );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRENT=" );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sWhere.append( " AND C1.BAIRCOB=" );
			}
			sWhere.append( "'" + sValores[ 18 ] + "'" );
			imp.addSubTitulo( "BAIRRO = " + sValores[ 18 ] );
		}

		
		try {				

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Clientes" );
			
			ps = con.prepareStatement( "SELECT COUNT(*) FROM VDCLIENTE C1" + sFrom + " WHERE C1.CODEMP=? AND C1.CODFILIAL=? " + sWhere.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();
			rs.next();
			
			And = new FAndamento( "Montando Relatório, Aguarde!", 0, rs.getInt( 1 ) - 1 );
			And.setVisible( true );
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			sSQL.append( "SELECT C1.CODCLI,C1.RAZCLI,C1.PESSOACLI,C1.NOMECLI,C1.CONTCLI," );
			sSQL.append( "C1.CNPJCLI,C1.INSCCLI,C1.CPFCLI," );
			sSQL.append( "C1.RGCLI,C1.FONECLI,C1.DDDCLI,C1.FAXCLI,C1.EMAILCLI,C1.CODPESQ, " );
			if( "A".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCLI,C1.NUMCLI,C1.BAIRCLI,C1.CIDCLI,C1.COMPLCLI,C1.UFCLI,C1.CEPCLI " );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDENT AS ENDCLI,C1.NUMENT AS NUMCLI,C1.BAIRENT AS BAIRCLI," );
				sSQL.append( "C1.CIDENT AS CIDCLI,C1.COMPLENT AS COMPLCLI,C1.UFENT AS UFCLI,C1.CEPENT AS CEPCLI " );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCOB AS ENDCLI,C1.NUMCOB AS NUMCLI,C1.BAIRCOB AS BAIRCLI," );
				sSQL.append( "C1.CIDCOB AS CIDCLI,C1.COMPLCOB AS COMPLCLI,C1.UFCOB AS UFCLI,C1.CEPCOB AS CEPCLI " );
			}
			sSQL.append( sObs );
			sSQL.append( "FROM VDCLIENTE C1 " );
			sSQL.append( sFrom );
			sSQL.append( "WHERE C1.CODEMP=? AND C1.CODFILIAL=? " );
			sSQL.append( sWhere );
			sSQL.append( " ORDER BY " + sValores[ 0 ] );
		
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 135, "|" );
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" + linhaFina + "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| Código:" );
				imp.say( 10, rs.getString( "CodCli" ) );
				imp.say( 20, "Razão:" );
				imp.say( 27, rs.getString( "RazCli" ) != null ? rs.getString( "RazCli" ).substring( 0, 30 ) : "" );

				if ( rs.getString( "CodPesq" ) != null && !rs.getString( "CodPesq" ).equals( rs.getString( "CodCLi" ) ) ) {
					imp.say( 57, "Cod.Matriz : " + rs.getString( "CodPesq" ) );
				}

				imp.say( 127, "Tipo:" );
				imp.say( 133, rs.getString( "PessoaCli" ) );
				imp.say( 135, "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| Nome:" );
				imp.say( 8, rs.getString( "NomeCli" ) );
				imp.say( 60, "Contato:" );
				imp.say( 70, rs.getString( "ContCli" ) );
				imp.say( 135, "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| Endereço:" );
				imp.say( 12, rs.getString( "EndCli" ) );
				imp.say( 63, "N.:" );
				imp.say( 67, String.valueOf( rs.getInt( "NumCli" ) ) );
				imp.say( 76, "Compl.:" );
				imp.say( 85, rs.getString( "ComplCli" ) != null ? rs.getString( "ComplCli" ).trim() : "" );
				imp.say( 94, "Bairro:" );
				imp.say( 103, rs.getString( "BairCli" ) != null ? rs.getString( "BairCli" ).trim() : "" );
				imp.say( 135, "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| Cidade:" );
				imp.say( 10, rs.getString( "CidCli" ) );
				imp.say( 88, "UF:" );
				imp.say( 93, rs.getString( "UfCli" ) );
				imp.say( 120, "CEP:" );
				imp.say( 125, rs.getString( "CepCli" ) != null ? Funcoes.setMascara( rs.getString( "CepCli" ), "#####-###" ) : "" );
				imp.say( 135, "|" );
				imp.pulaLinha( 1, imp.comprimido() );

				if ( ( rs.getString( "CnpjCli" ) ) != null && ( rs.getString( "InscCli" ) != null ) ) {
					imp.say( 0, "| CNPJ:" );
					imp.say( 7, Funcoes.setMascara( rs.getString( "CnpjCli" ), "##.###.###/####-##" ) );
					imp.say( 50, "IE:" );
					if ( !rs.getString( "InscCli" ).trim().toUpperCase().equals( "ISENTO" ) && rs.getString( "UFCli" ) != null ) {
						Funcoes.vIE( rs.getString( "InscCli" ), rs.getString( "UFCli" ) );
						imp.say( 55, Funcoes.sIEValida );
					}
				}
				else {
					imp.say( 0, "| CPF:" );
					imp.say( 6, Funcoes.setMascara( rs.getString( "CPFCli" ), "###.###.###-##" ) );
					imp.say( 50, "RG:" );
					imp.say( 55, rs.getString( "RgCli" ) );
				}

				imp.say( 80, "Tel:" );
				imp.say( 86, ( rs.getString( "DDDCli" ) != null ? rs.getString( "DDDCli" ) + "-" : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ).trim() );
				imp.say( 115, "Fax:" );
				imp.say( 122, rs.getString( "FaxCli" ) != null ? Funcoes.setMascara( rs.getString( "FaxCli" ), "####-####" ) : "" );
				imp.say( 135, "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| Contato:" );
				imp.say( 12, rs.getString( "ContCli" ) );
				imp.say( 72, "E-mail:" );
				imp.say( 79, rs.getString( "EmailCli" ) );
				imp.say( 135, "|" );

				if ( sObs.length() > 0 ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|Obs:" );
					imp.say( 6, rs.getString( "ObsCli" ) );
					imp.say( 135, "|" );
				}

				if ( imp.pRow() >= linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + linhaFina + "+" );
					imp.incPags();
					imp.eject();
				}
				
				And.atualiza( iContaReg++ );
				
			}
			
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + linhaFina + "+" );
			
			imp.eject();
			imp.fechaGravacao();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}			
			
			And.dispose();
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de clientes!" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
		
		
	}
	
	private void imprimeAlinhaFilial( final ImprimeOS imp, final String[] sValores ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sWhere2 = new StringBuffer();
		String sCodpesq = "";
		String sCodpesqant = "";
		String sOrdem = "";
		String sFrom = "";
		String linhaFina = Funcoes.replicate( "-", 133 );
		FAndamento And = null;
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;
		

		if ( sValores[ 12 ].equals( "C" ) ) {
			sOrdem = "1,3,4";
		}
		else {
			sOrdem = "2,3,5";
		}

		if ( sValores[ 2 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.RAZCLI >='" + sValores[ 2 ] + "'" );
			sWhere2.append( " AND C2.RAZCLI >='" + sValores[ 2 ] + "'" );
			imp.addSubTitulo( "RAZAO MAIORES QUE " + sValores[ 2 ].trim() );
		}
		if ( sValores[ 3 ].trim().length() > 0 ) {
			sWhere.append( " AND C1.RAZCLI <='" + sValores[ 3 ] + "'" );
			sWhere2.append( " AND C2.RAZCLI <='" + sValores[ 3 ] + "'" );
			imp.addSubTitulo( "RAZAO MENORES QUE " + sValores[ 3 ].trim() );
		}
		if ( sValores[ 4 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'F'" );
			sWhere2.append( " AND C2.PESSOACLI <> 'F'" );
			imp.addSubTitulo( "PESSOAS JURIDICAS" );
		}
		if ( sValores[ 5 ].length() > 0 ) {
			sWhere.append( " AND C1.CIDCLI ='" + sValores[ 5 ] + "'" );
			sWhere2.append( " AND C2.CIDCLI ='" + sValores[ 5 ] + "'" );
			imp.addSubTitulo( "CIDADE : " + sValores[ 5 ].trim() );
		}
		if ( sValores[ 6 ].equals( "N" ) ) {
			sWhere.append( " AND C1.PESSOACLI <> 'J'" );
			sWhere2.append( " AND C2.PESSOACLI <> 'J'" );
			imp.addSubTitulo( "PESSOAS FISICA" );
		}
		if ( !sValores[ 13 ].trim().equals( "" ) ) {
			sWhere.append( " AND C1.CODVEND =" + sValores[ 13 ] );
			sWhere2.append( " AND C2.CODVEND =" + sValores[ 13 ] );
			imp.addSubTitulo( "REPRES. : " + sValores[ 13 ] + "-" + sValores[ 14 ] );
		}
		if ( sValores[ 8 ].length() > 0 ) {
			if ( !bPref[ 0 ] ) {
				sFrom = ",VDVENDEDOR V ";
				sWhere.append( " AND C1.CODEMPVD=V.CODEMP AND C1.CODFILIALVD=V.CODFILIAL AND C1.CODVEND=V.CODVEND AND V.CODSETOR = " + sValores[ 8 ] );
				sWhere2.append( " AND C2.CODEMPVD=V.CODEMP AND C2.CODFILIALVD=V.CODFILIAL AND C2.CODVEND=V.CODVEND AND V.CODSETOR = " + sValores[ 8 ] );
			}
			else {
				sWhere.append( " AND C1.CODSETOR = " + sValores[ 8 ] );
				sWhere2.append( " AND C2.CODSETOR = " + sValores[ 8 ] );
			}
			imp.addSubTitulo( "SETOR : " + sValores[ 9 ] );
		}
		if ( sValores[ 10 ].length() > 0 ) {
			sWhere.append( " AND C1.CODTIPOCLI=" + sValores[ 10 ] );
			sWhere2.append( " AND C2.CODTIPOCLI=" + sValores[ 10 ] );
			imp.addSubTitulo( "TIPO DE CLIENTE=" + sValores[ 11 ] );
		}
		if ( sValores[ 15 ].length() > 0 ) {
			sWhere.append( " AND C1.CODCLASCLI=" + sValores[ 15 ] );
			sWhere2.append( " AND C2.CODCLASCLI=" + sValores[ 15 ] );
			imp.addSubTitulo( "CLASSIFICACAO DO CLIENTE = " + sValores[ 16 ] );
		}
		if ( sValores[ 18 ].length() > 0 ) {
			sWhere.append( " AND C1.BAIRCLI='" + sValores[ 18 ] + "'" );
			sWhere2.append( " AND C2.BAIRCLI='" + sValores[ 18 ] + "'" );
			imp.addSubTitulo( "BAIRRO : " + sValores[ 18 ] );
		}


		try {				

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Clientes" );
			
			ps = con.prepareStatement( "SELECT COUNT(*) FROM VDCLIENTE C1" + sFrom + " WHERE C1.CODEMP=? AND C1.CODFILIAL=? " + sWhere.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();
			rs.next();
			
			And = new FAndamento( "Montando Relatório, Aguarde!", 0, rs.getInt( 1 ) - 1 );
			And.setVisible( true );
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			sSQL.append( "SELECT C1.CODPESQ,C1.RAZCLI RAZMATRIZ,'A' TIPO,C1.CODCLI,C1.RAZCLI,C1.DDDCLI,C1.FONECLI," );
			if( "A".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCLI,C1.CIDCLI " );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDENT AS ENDCLI,C1.CIDENT AS CIDCLI " );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C1.ENDCOB AS ENDCLI,C1.CIDCOB AS CIDCLI " );
			}
			sSQL.append( "FROM VDCLIENTE C1" );
			sSQL.append( sFrom );
			sSQL.append( " WHERE C1.CODCLI=C1.CODPESQ " );
			sSQL.append( " AND C1.CODEMP=? AND C1.CODFILIAL=? " );
			sSQL.append( sWhere );
			sSQL.append( " UNION SELECT C2.CODPESQ,");
			sSQL.append( "(SELECT C3.RAZCLI FROM VDCLIENTE C3 WHERE C3.CODCLI=C2.CODPESQ) AS RAZMATRIZ," );
			sSQL.append( "'B' TIPO,C2.CODCLI,C2.RAZCLI,C2.DDDCLI,C2.FONECLI," );
			if( "A".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C2.ENDCLI,C2.CIDCLI " );
			}
			else if( "E".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C2.ENDENT AS ENDCLI,C2.CIDENT AS CIDCLI " );
			}
			else if( "C".equals( sValores[ 17 ] ) ) {
				sSQL.append( "C2.ENDCOB AS ENDCLI,C2.CIDCOB AS CIDCLI " );
			}
			sSQL.append( "FROM VDCLIENTE C2" );
			sSQL.append( sFrom );
			sSQL.append( " WHERE NOT C2.CODCLI=C2.CODPESQ AND " );
			sSQL.append( "C2.CODEMP=? AND C2.CODFILIAL=? " );
			sSQL.append( sWhere2 );
			sSQL.append( " ORDER BY " + sOrdem );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				sCodpesq = String.valueOf( rs.getInt( 1 ) );
				
				if ( sCodpesqant.equals( "" ) ){
					sCodpesqant = sCodpesq;
				}

				if ( imp.pRow() >= linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + linhaFina + "+" );
					imp.incPags();
					imp.eject();
				}

				if ( imp.pRow() == 0 ) {

					imp.impCab( 136, true );
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "+" + linhaFina + "+" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "| Codigo" );
					imp.say( 12, "| Nome" );
					imp.say( 58, "| Endereco" );
					imp.say( 101, "| Cidade" );
					imp.say( 115, "|  Telefone" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + linhaFina + "|" );

				}
				else {
					if ( !sCodpesq.equals( sCodpesqant ) ) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + linhaFina + "|" );
					}
				}

				if ( rs.getString( 3 ).equals( "A" ) ) {

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 2, rs.getString( "CodCli" ) );
					imp.say( 12, "|" );
					imp.say( 14, rs.getString( "RazCli" ) != null ? rs.getString( "RazCli" ).substring( 0, 30 ) : "" );
					imp.say( 47, "( M )" );
					imp.say( 58, "|" );
					imp.say( 59, rs.getString( "EndCli" ) != null ? rs.getString( "EndCli" ).substring( 0, 30 ) : "" );
					imp.say( 101, "|" );
					imp.say( 103, rs.getString( "CidCli" ) != null ? rs.getString( "CidCli" ).substring( 0, 12 ) : "" );
					imp.say( 115, "|" );
					imp.say( 120, ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ).trim() );
					imp.say( 135, "|" );

				}
				else if ( !rs.getString( 1 ).equals( rs.getString( 4 ) ) ) {

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 2, rs.getString( "CodCli" ) );
					imp.say( 12, "|" );
					imp.say( 15, rs.getString( "RazCli" ) != null ? rs.getString( "RazCli" ).substring( 0, 30 ) : "" );
					imp.say( 47, "( F )" );
					imp.say( 58, "|" );
					imp.say( 59, rs.getString( "EndCli" ) != null ? rs.getString( "EndCli" ).substring( 0, 30 ) : "" );
					imp.say( 101, "|" );
					imp.say( 103, rs.getString( "CidCli" ) != null ? rs.getString( "CidCli" ).substring( 0, 12 ) : "" );
					imp.say( 115, "|" );
					imp.say( 120, ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ), "####-####" ) : "" ).trim() );
					imp.say( 135, "|" );

				}

				And.atualiza( iContaReg++ );
				sCodpesqant = sCodpesq;

			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + linhaFina + "+" );
			imp.eject();
			imp.fechaGravacao();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}			
			
			And.dispose();
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de clientes!\n" + err.getMessage(), true, con, err );
		}

	}

	private void novoHist() {

		PreparedStatement ps = null;
		int iCod = 0;
		
		try {
			
			Object oRets[];

			if ( txtCodCli.getVlrInteger().intValue() == 0 ) {
				Funcoes.mensagemInforma( this, "Não ha nenhum cliente selecionado!" );
				txtCodCli.requestFocus();
				return;
			}
			else {
				iCod = txtCodCli.getVlrInteger().intValue();
			}

			DLNovoHist dl = new DLNovoHist( iCod, 1, this );
			dl.setConexao( con );
			dl.setVisible( true );
			
			if ( dl.OK ) {
				
				oRets = dl.getValores();
				
				try {
					String sSQL = "EXECUTE PROCEDURE TKSETHISTSP(0,?,?,?,?,?,?,?,?,?,?,?)";
					ps = con.prepareStatement( sSQL );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setNull( 2, Types.INTEGER );
					ps.setNull( 3, Types.INTEGER );
					ps.setInt( 4, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
					ps.setInt( 5, txtCodCli.getVlrInteger().intValue() );
					ps.setString( 6, (String) oRets[ 0 ] );// Descrição do historico
					ps.setInt( 7, ListaCampos.getMasterFilial( "ATATENDENTE" ) );// Filial do atendete
					ps.setString( 8, (String) oRets[ 1 ] );// codígo atendente
					ps.setString( 9, (String) oRets[ 2 ] );// status do historico
					ps.setDate( 10, (Date) oRets[ 4 ] );// data do historico
					ps.setString( 11, (String) oRets[ 3 ] );// tipo do historico
					ps.execute();
					ps.close();

					if ( !con.getAutoCommit() ) {
						con.commit();
					}

					if ( oRets[ 5 ] != null ) {
						Object[] agente = getAgente();
						sSQL = "EXECUTE PROCEDURE SGSETAGENDASP(0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						ps = con.prepareStatement( sSQL );
						ps.setInt( 1, Aplicativo.iCodEmp );
						ps.setDate( 2, (Date) oRets[ 5 ] );
						ps.setString( 3, oRets[ 6 ] + ":00" );
						ps.setDate( 4, (Date) oRets[ 7 ] );
						ps.setString( 5, (String) oRets[ 8 ] + ":00" );
						ps.setString( 6, (String) oRets[ 9 ] );
						ps.setString( 7, (String) oRets[ 10 ] );
						ps.setString( 8, (String) oRets[ 11 ] );
						ps.setInt( 9, 5 );
						ps.setInt( 10, Aplicativo.iCodFilialPad );
						ps.setString( 11, Aplicativo.strUsuario );
						ps.setString( 12, (String) oRets[ 12 ] );
						ps.setString( 13, (String) oRets[ 13 ] );
						ps.setInt( 14, ( (Integer) agente[ 0 ] ).intValue() );
						ps.setString( 15, (String) agente[ 1 ] );
						ps.execute();
						ps.close();
						
						if ( !con.getAutoCommit() ) {
							con.commit();
						}
					}
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro ao salvar o histórico!\n" + err.getMessage(), true, con, err );
					err.printStackTrace();
				}
				dl.dispose();
			}
			carregaTabHist();
		} finally {
			ps = null;
		}
	}

	private void novaObs() {

		String sSql = null;
		int iCodCli = 0;
		PreparedStatement ps = null;
		DLInputText dl = null;
		iCodCli = txtCodCli.getVlrInteger().intValue();
		java.util.Date dtHoje = null;
		
		
		if ( iCodCli != 0 ) {
			try {
				dl = new DLInputText( this, "Observação", false );
				dl.setTexto( "" );
				dl.setVisible( true );
				
				if ( ( dl.OK  ) && ( !dl.getTexto().trim().equals( "" ) ) ) {
					
					try {
						dtHoje = new java.util.Date();
						sSql = "INSERT INTO VDOBSCLI (SEQOBSCLI, CODEMP, CODFILIAL, CODCLI, DTOBSCLI, HOBSCLI, TXTOBSCLI) " + "VALUES ((COALESCE((SELECT MAX(OC.SEQOBSCLI) " + "FROM VDOBSCLI OC " + "WHERE OC.CODEMP=? AND OC.CODFILIAL=? AND OC.CODCLI=?)+1,1 ))," + "?,?,?,?,?,?)";
						ps = con.prepareStatement( sSql );
						ps.setInt( 1, Aplicativo.iCodEmp );
						ps.setInt( 2, ListaCampos.getMasterFilial( "VDOBSCLI" ) );
						ps.setInt( 3, iCodCli );
						ps.setInt( 4, Aplicativo.iCodEmp );
						ps.setInt( 5, ListaCampos.getMasterFilial( "VDOBSCLI" ) );
						ps.setInt( 6, iCodCli );
						ps.setDate( 7, new Date( dtHoje.getTime() ) );
						ps.setTime( 8, new Time( dtHoje.getTime() ) );
						ps.setString( 9, dl.getTexto() );
						ps.executeUpdate();
						ps.close();
						if ( !con.getAutoCommit() ) {
							con.commit();
						}
					} catch ( SQLException err ) {
						err.printStackTrace();
						Funcoes.mensagemErro( this, "Não foi possível inserir a observação.\n" + err.getMessage(), true, con, err );
					}
				}
				carregaTabelaObs();
			} finally {
				ps = null;
				sSql = null;
				dl = null;
				dtHoje = null;
			}
		}
	}

	public void setConveniado( FConveniado telaConv ) {

		this.telaConv = telaConv;
	}

	public void setVlrConveniado( String sRazcli, String sNomecli, String sEndcli, Integer iNumcli, String sComplcli, String sBaircli, String sCidcli, String sCepcli, String sUFcli, String sRgcli, String sCpfcli, String sFonecli, String sFaxcli, String sEmailcli, Integer iCodTipoCli,
			Integer iCodClasCli ) {

		rgPessoa.setVlrString( "F" );
		txtRazCli.setVlrString( sRazcli );
		txtNomeCli.setVlrString( sNomecli );
		txtEndCli.setVlrString( sEndcli );
		txtNumCli.setVlrInteger( iNumcli );
		txtComplCli.setVlrString( sComplcli );
		txtBairCli.setVlrString( sBaircli );
		txtCidCli.setVlrString( sCidcli );
		txtCepCli.setVlrString( sCepcli );
		txtUFCli.setVlrString( sUFcli );
		txtRgCli.setVlrString( sRgcli );
		txtCpfCli.setVlrString( sCpfcli );
		txtFoneCli.setVlrString( sFonecli );
		txtFaxCli.setVlrString( sFaxcli );
		txtEmailCli.setVlrString( sEmailcli );
		if ( iCodTipoCli != null ) {
			txtCodTipoCli.setVlrInteger( iCodTipoCli );
			lcTipoCli.carregaDados();
		}
		if ( iCodClasCli != null ) {
			txtCodClas.setVlrInteger( iCodClasCli );
			lcClas.carregaDados();
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( evt.getSource() == btAtEntrega ) {
			if ( lcCampos.getStatus() != ListaCampos.LCS_EDIT ) {
				lcCampos.edit();
			}
			txtEndEnt.setVlrString( txtEndCli.getVlrString() );
			txtNumEnt.setVlrString( txtNumCli.getVlrString() );
			txtComplEnt.setVlrString( txtComplCli.getVlrString() );
			txtBairEnt.setVlrString( txtBairCli.getVlrString() );
			txtCidEnt.setVlrString( txtCidCli.getVlrString() );
			txtCepEnt.setVlrString( txtCepCli.getVlrString() );
			txtUFEnt.setVlrString( txtUFCli.getVlrString() );
			txtDDDFoneEnt.setVlrString( txtDDDCli.getVlrString() );
			txtFoneEnt.setVlrString( txtFoneCli.getVlrString() );
			txtDDDFaxEnt.setVlrString( txtDDDFaxCli.getVlrString() );
			txtFaxEnt.setVlrString( txtFaxCli.getVlrString() );
		}
		else if ( evt.getSource() == btAtCobranca ) {
			if ( lcCampos.getStatus() != ListaCampos.LCS_EDIT ) {
				lcCampos.edit();
			}
			txtEndCob.setVlrString( txtEndCli.getVlrString() );
			txtNumCob.setVlrString( txtNumCli.getVlrString() );
			txtComplCob.setVlrString( txtComplCli.getVlrString() );
			txtBairCob.setVlrString( txtBairCli.getVlrString() );
			txtCidCob.setVlrString( txtCidCli.getVlrString() );
			txtCepCob.setVlrString( txtCepCli.getVlrString() );
			txtUFCob.setVlrString( txtUFCli.getVlrString() );
			txtDDDFoneCob.setVlrString( txtDDDCli.getVlrString() );
			txtFoneCob.setVlrString( txtFoneCli.getVlrString() );
			txtDDDFaxCob.setVlrString( txtDDDFaxCli.getVlrString() );
			txtFaxCob.setVlrString( txtFaxCli.getVlrString() );
		}
		else if ( evt.getSource() == btExclObs ) {
			exclObs();
		}
		else if ( evt.getSource() == btEditObs ) {
			editObs();
		}
		else if ( evt.getSource() == btNovaObs ) {
			novaObs();
		}
		else if ( evt.getSource() == btGrpCli ) {
			grpCli();
		}
		else if ( evt.getSource() == btSetaQtdJan ) {
			geraHistoricos( new Integer( 1 ) );
		}
		else if ( evt.getSource() == btSetaQtdFev ) {
			geraHistoricos( new Integer( 2 ) );
		}
		else if ( evt.getSource() == btSetaQtdMar ) {
			geraHistoricos( new Integer( 3 ) );
		}
		else if ( evt.getSource() == btSetaQtdAbr ) {
			geraHistoricos( new Integer( 4 ) );
		}
		else if ( evt.getSource() == btSetaQtdMai ) {
			geraHistoricos( new Integer( 5 ) );
		}
		else if ( evt.getSource() == btSetaQtdJun ) {
			geraHistoricos( new Integer( 6 ) );
		}
		else if ( evt.getSource() == btSetaQtdJul ) {
			geraHistoricos( new Integer( 7 ) );
		}
		else if ( evt.getSource() == btSetaQtdAgo ) {
			geraHistoricos( new Integer( 8 ) );
		}
		else if ( evt.getSource() == btSetaQtdSet ) {
			geraHistoricos( new Integer( 9 ) );
		}
		else if ( evt.getSource() == btSetaQtdOut ) {
			geraHistoricos( new Integer( 10 ) );
		}
		else if ( evt.getSource() == btSetaQtdNov ) {
			geraHistoricos( new Integer( 11 ) );
		}
		else if ( evt.getSource() == btSetaQtdDez ) {
			geraHistoricos( new Integer( 12 ) );
		}
		else if ( evt.getSource() == btMudaTudo ) {
			geraHistoricos( null );
		}
		else if ( evt.getSource() == btNovoHist ) {
			novoHist();
		}
		else if ( evt.getSource() == btExcluiHist ) {
			excluiHist();
		}
		super.actionPerformed( evt );
		
		if(evt.getSource() == btFirefox ){
	    	
	    	if(!txtSiteCli.getVlrString().equals( "" )){
	    		
	    		sURLBanco = txtSiteCli.getVlrString();
	        	Funcoes.executeURL( Aplicativo.strOS, Aplicativo.strBrowser, sURLBanco );
	    	}
	    	else
	    		Funcoes.mensagemInforma( this, "Informe o Site do Cliente! " );
	    }
	}

	public void focusGained( FocusEvent fevt ) {

	}

	// Copia a descrição o planejamento para a descrição da conta:
	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtAno ) {
			if ( txtAno.getVlrInteger().intValue() > 0 ) {
				getContatos();
			}
		}
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tpn ) {
			if ( tpn.getSelectedIndex() == 0 ) {
				txtCodCli.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 1 ) {
				txtEndEnt.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 2 ) {
				txtEndCob.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 3 ) {
				txtCodVend.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 4 ) {
				txaObs.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 6 ) {
				getContatos();
			}
		}
		else if ( cevt.getSource() == tpnCont ) {
			getContatos();
		}
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		if ( rgPessoa.getVlrString().compareTo( "J" ) == 0 ) {
			txtCnpjCli.setEnabled( true );
			txtInscCli.setEnabled( true );
			if ( bPref[ 3 ] ) {
				setBordaReq( txtCnpjCli );
				setBordaReq( txtInscCli );
			}
			else {
				setBordaPad( txtCnpjCli );
				setBordaPad( txtInscCli );
			}
			txtCpfCli.setEnabled( false );
			setBordaPad( txtCpfCli );
			txtRgCli.setEnabled( false );
			setBordaPad( txtRgCli );
			txtSSPCli.setEnabled( false );
			setBordaPad( txtSSPCli );
		}
		else if ( rgPessoa.getVlrString().compareTo( "F" ) == 0 ) {
			txtCnpjCli.setEnabled( false );
			setBordaPad( txtCnpjCli );
			txtInscCli.setEnabled( false );
			setBordaPad( txtInscCli );
			txtCpfCli.setEnabled( true );
			setBordaReq( txtCpfCli );
			txtRgCli.setEnabled( true );
			setBordaReq( txtRgCli );
			txtSSPCli.setEnabled( true );
			setBordaReq( txtSSPCli );
		}
	}

	public void valorAlterado( TabelaSelEvent evt ) {

		if ( evt.getTabela() == tbObsData ) {
			carregaObs();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCampos ) {
			carregaTabelaObs();
			txtAno.setVlrInteger( new Integer( Calendar.getInstance().get( Calendar.YEAR ) ) );
			getContatos();
			carregaTabHist();
		}
	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void afterInsert( InsertEvent ievt ) {

		if ( ievt.getListaCampos() == lcCampos ) {
			cbAtivo.setVlrString( "S" );
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		bPref = getPrefere();

		montaTela();

		lcTipoCli.setConexao( cn );
		lcTipoFiscCli.setConexao( cn );
		lcClas.setConexao( cn );
		lcVend.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcTran.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcBanco.setConexao( cn );
		lcPesq.setConexao( cn );
		lcFor.setConexao( con );
		lcCliFor.setConexao( con );
		lcPais.setConexao( con );
		lcMetaVend.setConexao( con );
		lcHistorico.setConexao( con );
		
		if ( lcSetor != null ) {
			lcSetor.setConexao( con );
		}

	}

	public void beforePost( PostEvent pevt ) {

		if ( rgPessoa.getVlrString().compareTo( "F" ) == 0 ) {
			return;
		}

		if ( ( txtCnpjCli.getText().trim().length() < 1 ) && ( bPref[ 3 ] ) ) {
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Campo CNPJ é requerido! ! !" );
			txtCnpjCli.requestFocus();
			return;
		}

		if ( ( txtInscCli.getText().trim().length() < 1 ) && ( bPref[ 3 ] ) ) {
			if ( Funcoes.mensagemConfirma( this, "Inscrição Estadual em branco! Inserir ISENTO?" ) == JOptionPane.OK_OPTION ) {
				txtInscCli.setVlrString( "ISENTO" );
			}
			pevt.cancela();
			txtInscCli.requestFocus();
			return;
		}

		if ( ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) && ( duploCNPJ() ) ) {
			if ( bPref[ 2 ] ) {
				if ( Funcoes.mensagemConfirma( this, "Este CNPJ já está cadastrado! Salvar mesmo assim?" ) != JOptionPane.OK_OPTION ) {
					pevt.cancela();
					txtCnpjCli.requestFocus();
					return;
				}
			}
			else {
				pevt.cancela();
				Funcoes.mensagemInforma( this, "Este CNPJ já está cadastrado!" );
				txtCnpjCli.requestFocus();
				return;
			}
		}

		if ( txtInscCli.getText().trim().toUpperCase().compareTo( "ISENTO" ) == 0 ) {
			return;
		}

		if ( txtUFCli.getText().trim().length() < 2 ) {
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Campo UF é requerido! ! !" );
			txtUFCli.requestFocus();
			return;
		}

		if ( bPref[ 4 ] ) {
			if ( ! Funcoes.vIE( txtInscCli.getText(), txtUFCli.getText() ) ) {
				if ( ! txtInscCli.getText().trim().equals( "" ) ) {
					pevt.cancela();
					Funcoes.mensagemInforma( this, "Inscrição Estadual Inválida ! ! !" );
					txtInscCli.requestFocus();
					return;
				}
			}

			if ( ! txtInscCli.getText().trim().equals( "" ) ) {
				txtInscCli.setVlrString( Funcoes.sIEValida );
			}
		}
	}

	public void afterPost( PostEvent pevt ) {

		if ( ( rgPessoa.getVlrString().equals( "F" ) ) && ( pevt.ok ) ) {
			if ( telaConv != null ) {
				telaConv.setCodcli( txtCodCli.getVlrString(), txtRazCli.getVlrString() );
				this.btSair.doClick();
			}
		}
	}

}
