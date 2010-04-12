/*
 * Projeto: Freedom
 * Pacote: org.freedom.modules.crm
 * Classe: @(#)FAtendimento.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> 
 */

package org.freedom.modulos.crm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.FuncoesCRM;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.JButtonPad;
import org.freedom.library.JComboBoxPad;
import org.freedom.library.JLabelPad;
import org.freedom.library.JPanelPad;
import org.freedom.library.JTabbedPanePad;
import org.freedom.library.JTextAreaPad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.library.Tabela;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

/**
 * 
 * @author Setpoint Informática Ltda./Alex Rodrigues
 * @version 10/10/2009 - Alex Rodrigues
 */
public class FAtendimento extends FFilho implements CarregaListener, ActionListener, FocusListener, JComboBoxListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JTabbedPanePad tpnCli = new JTabbedPanePad();

	private JPanelPad pinCli = new JPanelPad( 510, 110 );

	private JPanelPad pinFiltros = new JPanelPad( 510, 80 );

	private JPanelPad pinTitulo = new JPanelPad( 510, 80 );

	private JPanelPad pinCabCli = new JPanelPad( 530, 100 );

	private JPanelPad pnCabCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRodCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnlbFiltros = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private Tabela tabCli = new Tabela();

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

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

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

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

	private JButtonPad btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btImprimir = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );

	private Vector<String> vCodAtends = new Vector<String>();

	private Vector<Integer> vValsContr = new Vector<Integer>();

	private Vector<String> vLabsContr = new Vector<String>();

	private JComboBoxPad cbContr = new JComboBoxPad( vLabsContr, vValsContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private Vector<Integer> vValsitContr = new Vector<Integer>();

	private Vector<String> vLabsitContr = new Vector<String>();

	private JComboBoxPad cbitContr = new JComboBoxPad( vLabsitContr, vValsitContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private Vector<Integer> vValsTipo = new Vector<Integer>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JComboBoxPad cbTipo = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 8, 0 );

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcAtendimento = new ListaCampos( this );

	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	private ListaCampos lcItRec = new ListaCampos( this );

	private ListaCampos lcRec = new ListaCampos( this );

	private boolean carregagrid = false;

	private String tipoatendo = null; // Tipo de atendimento

	public FAtendimento() {

		super( false );

		setTitulo( "Atendimento" );
		setAtribos( 20, 20, 780, 650 );

		tipoatendo = "A"; // Setando o tipo de atendimento para "A" de atendimento;

		pnCabCli.setPreferredSize( new Dimension( 500, 295 ) );

		montaListaCampos();
		montaTela();

		pnlbFiltros.add( new JLabelPad( " Filtros" ), SwingConstants.CENTER );

		pinCabCli.adic( pnlbFiltros, 15, 165, 60, 20 );
		pinCabCli.adic( pinFiltros, 10, 175, 740, 105 );

		pinFiltros.adic( new JLabelPad( "Data Inicial" ), 7, 10, 70, 20 );
		pinFiltros.adic( txtDataini, 7, 30, 70, 20 );

		pinFiltros.adic( new JLabelPad( "Data Final" ), 80, 10, 70, 20 );
		pinFiltros.adic( txtDatafim, 80, 30, 70, 20 );

		pinFiltros.adic( new JLabelPad( "Cód.Atend." ), 153, 10, 70, 20 );
		pinFiltros.adic( txtCodAtend, 153, 30, 70, 20 );
		pinFiltros.adic( new JLabelPad( "Nome do Atendente" ), 226, 10, 230, 20 );
		pinFiltros.adic( txtNomeAtend, 226, 30, 230, 20 );

		pinFiltros.adic( new JLabelPad( "Tipo de Atendimento" ), 459, 10, 261, 20 );
		pinFiltros.adic( cbTipo, 459, 30, 261, 20 );

		pinFiltros.adic( new JLabelPad( "Contrato/Projeto" ), 153, 50, 303, 20 );
		pinFiltros.adic( cbContr, 153, 70, 303, 20 );

		pinFiltros.adic( new JLabelPad( "Item" ), 459, 50, 261, 20 );
		pinFiltros.adic( cbitContr, 459, 70, 261, 20 );

	}

	public FAtendimento( Integer codcli, Integer codrec, Integer nparcitrec, boolean isUpdate ) {

		super( false );

		setTitulo( "Atendimento" );
		setAtribos( 20, 20, 780, 500 );

		tipoatendo = "C"; // Setando o tipo de atendimento para "C" de Contato;

		pnCabCli.setPreferredSize( new Dimension( 500, 305 ) );

		setConexao( Aplicativo.getInstace().getConexao() );

		montaListaCampos();

		montaTela();

		txtCodCli.setVlrInteger( codcli );
		txtCodCli.setEnabled( false );

		txtCodRec.setVlrInteger( codrec );
		txtCodRec.setEnabled( false );

		txtNParcItRec.setVlrInteger( nparcitrec );
		txtNParcItRec.setEnabled( false );

		pinTitulo.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Título" ) );
		pinCabCli.adic( pinTitulo, 8, 175, 744, 116 );

		lcRec.add( new GuardaCampo( txtCodRec, "CodRec", "Cód.rec.", ListaCampos.DB_PK, false ) );
		lcRec.add( new GuardaCampo( txtCodVenda, "CodVenda", "Cód.venda", ListaCampos.DB_SI, false ) );
		lcRec.add( new GuardaCampo( txtDoc, "DocRec", "Doc", ListaCampos.DB_SI, false ) );
		lcRec.add( new GuardaCampo( txtDtEmis, "DataRec", "Emissão", ListaCampos.DB_SI, false ) );

		lcRec.montaSql( false, "RECEBER", "FN" );
		lcRec.setQueryCommit( false );
		lcRec.setReadOnly( true );
		txtCodRec.setTabelaExterna( lcRec );
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
		txtCodRec.setTabelaExterna( lcItRec );
		txtCodRec.setFK( true );

		pinTitulo.adic( new JLabelPad( "Cód.rec" ), 7, 0, 70, 20 );
		pinTitulo.adic( txtCodRec, 7, 20, 70, 20 );
		pinTitulo.adic( new JLabelPad( "Parc." ), 80, 0, 40, 20 );
		pinTitulo.adic( txtNParcItRec, 80, 20, 40, 20 );
		pinTitulo.adic( new JLabelPad( "Venda" ), 123, 0, 50, 20 );
		pinTitulo.adic( txtCodVenda, 123, 20, 50, 20 );
		pinTitulo.adic( new JLabelPad( "Doc." ), 176, 0, 50, 20 );
		pinTitulo.adic( txtDoc, 176, 20, 50, 20 );
		pinTitulo.adic( new JLabelPad( "Emissão" ), 229, 0, 70, 20 );
		pinTitulo.adic( txtDtEmis, 229, 20, 70, 20 );
		pinTitulo.adic( new JLabelPad( "Vencimento" ), 302, 0, 70, 20 );
		pinTitulo.adic( txtDtVencItRec, 302, 20, 70, 20 );
		pinTitulo.adic( new JLabelPad( "Atrazo" ), 375, 0, 40, 20 );
		pinTitulo.adic( txtDiasAtrazo, 375, 20, 40, 20 );
		pinTitulo.adic( new JLabelPad( "Obs." ), 421, 0, 150, 20 );
		pinTitulo.adic( txaObsItRec, 421, 20, 303, 60 );
		pinTitulo.adic( new JLabelPad( "Valor do título" ), 7, 40, 113, 20 );
		pinTitulo.adic( txtVlrParcItRec, 7, 60, 113, 20 );
		pinTitulo.adic( new JLabelPad( "Total pago" ), 123, 40, 104, 20 );
		pinTitulo.adic( txtVlrPagoItRec, 123, 60, 104, 20 );
		pinTitulo.adic( new JLabelPad( "Total em aberto" ), 229, 40, 110, 20 );
		pinTitulo.adic( txtVlrApagItRec, 229, 60, 110, 20 );

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

		lcCli.carregaDados();
		lcRec.carregaDados();
		lcItRec.carregaDados();

		calcAtrazo();

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

	private void montaListaCampos() {

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
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		lcAtendimento.add( new GuardaCampo( txtCodAtendo, "CodAtendo", "Cód.Atendo", ListaCampos.DB_PK, false ) );
		lcAtendimento.montaSql( false, "ATENDIMENTO", "AT" );
		lcAtendimento.setReadOnly( true );
		txtCodAtendo.setTabelaExterna( lcAtendimento );
		txtCodAtendo.setFK( true );
		txtCodAtendo.setNomeCampo( "CodAtendo" );

		txtCodAtend.setTabelaExterna( lcAtend );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ) );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );

	}

	private void montaTela() {

		getTela().add( tpnCli );

		txtFoneCli.setMascara( JTextFieldPad.MC_FONE );
		txtFaxCli.setMascara( JTextFieldPad.MC_FONE );
		txtCelCli.setMascara( JTextFieldPad.MC_FONE );

		tpnCli.add( "Cliente", pnCli );
		pnCabCli.add( pinCabCli, BorderLayout.CENTER );

		pnCli.add( pnCabCli, BorderLayout.NORTH );
		pnCli.add( new JScrollPane( tabCli ), BorderLayout.CENTER );

		JPanelPad pnlbConv = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
		pnlbConv.add( new JLabelPad( "Cliente", SwingConstants.CENTER ) );
		pinCabCli.adic( pnlbConv, 12, 5, 80, 20 );
		pinCabCli.adic( pinCli, 10, 15, 740, 150 );

		pinCli.adic( new JLabelPad( "Cód.Cli." ), 15, 10, 50, 20 );
		pinCli.adic( txtCodCli, 15, 30, 50, 20 );
		pinCli.adic( new JLabelPad( "Razão social do cliente" ), 68, 10, 200, 20 );
		pinCli.adic( txtRazCli, 68, 30, 412, 20 );
		pinCli.adic( new JLabelPad( "Contato" ), 483, 10, 237, 20 );
		pinCli.adic( txtContatoCli, 483, 30, 237, 20 );
		pinCli.adic( new JLabelPad( "DDD" ), 15, 50, 50, 20 );
		pinCli.adic( txtDDDCli, 15, 70, 50, 20 );
		pinCli.adic( new JLabelPad( "Telefone" ), 68, 50, 100, 20 );
		pinCli.adic( txtFoneCli, 68, 70, 100, 20 );
		pinCli.adic( new JLabelPad( "DDD" ), 171, 50, 50, 20 );
		pinCli.adic( txtDDDFax, 171, 70, 50, 20 );
		pinCli.adic( new JLabelPad( "Fax" ), 224, 50, 100, 20 );
		pinCli.adic( txtFaxCli, 224, 70, 100, 20 );
		pinCli.adic( new JLabelPad( "DDD" ), 327, 50, 50, 20 );
		pinCli.adic( txtDDDCel, 327, 70, 50, 20 );
		pinCli.adic( new JLabelPad( "Celular" ), 380, 50, 100, 20 );
		pinCli.adic( txtCelCli, 380, 70, 100, 20 );
		pinCli.adic( new JLabelPad( "Email" ), 483, 50, 237, 20 );
		pinCli.adic( txtEmailCli, 483, 70, 237, 20 );
		pinCli.adic( new JLabelPad( "Endereço" ), 15, 90, 362, 20 );
		pinCli.adic( txtEndCli, 15, 110, 362, 20 );
		pinCli.adic( new JLabelPad( "Numero" ), 380, 90, 100, 20 );
		pinCli.adic( txtNumCli, 380, 110, 100, 20 );
		pinCli.adic( new JLabelPad( "Cidade" ), 483, 90, 100, 20 );
		pinCli.adic( txtCidCli, 483, 110, 169, 20 );
		pinCli.adic( new JLabelPad( "UF" ), 655, 90, 60, 20 );
		pinCli.adic( txtUfCli, 655, 110, 65, 20 );

		tabCli.setRowHeight( 20 );

		tabCli.adicColuna( "Atd." );
		tabCli.adicColuna( "Doc." );
		tabCli.adicColuna( "Status" );
		tabCli.adicColuna( "Data" );
		tabCli.adicColuna( "Data fim" );
		tabCli.adicColuna( "Ocorrencia" );
		tabCli.adicColuna( "" );
		tabCli.adicColuna( "Atendente" );
		tabCli.adicColuna( "Hora inicial" );
		tabCli.adicColuna( "Hora final" );

		tabCli.setTamColuna( 0, 0 );
		tabCli.setTamColuna( 0, 1 );
		tabCli.setTamColuna( 0, 2 );
		tabCli.setTamColuna( 67, 3 ); // Data de início
		tabCli.setTamColuna( 0, 4 ); // Data de fim
		tabCli.setTamColuna( 400, 5 ); // Ocorrência
		tabCli.setTamColuna( 0, 6 );
		tabCli.setTamColuna( 140, 7 ); // Atendente
		tabCli.setTamColuna( 70, 8 );
		tabCli.setTamColuna( 70, 9 );

		tabCli.setColunaInvisivel( 0 );
		tabCli.setColunaInvisivel( 1 );
		tabCli.setColunaInvisivel( 2 );
		tabCli.setColunaInvisivel( 4 );
		tabCli.setColunaInvisivel( 6 );

		JPanelPad pnBotConv = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );
		pnBotConv.setPreferredSize( new Dimension( 90, 30 ) );
		pnBotConv.add( btNovo );
		pnBotConv.add( btExcluir );
		pnBotConv.add( btImprimir );

		pnRodCli.add( pnBotConv, BorderLayout.WEST );
		btSair.setPreferredSize( new Dimension( 110, 30 ) );

		pnRodCli.add( btSair, BorderLayout.EAST );
		pnCli.add( pnRodCli, BorderLayout.SOUTH );

		btNovo.addActionListener( this );
		btExcluir.addActionListener( this );
		btImprimir.addActionListener( this );

		btSair.addActionListener( this );
		lcCli.addCarregaListener( this );
		lcAtend.addCarregaListener( this );

		tabCli.addMouseListener( new MouseAdapter() {
			public void mouseClicked( MouseEvent mevt ) {
				if ( mevt.getSource() == tabCli && mevt.getClickCount() == 2 )
					visualizaAtend();
			}
		} );

		txtDataini.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );
		txtDatafim.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );

		txtDatafim.addFocusListener( this );

		cbContr.addComboBoxListener( this );
		cbitContr.addComboBoxListener( this );
		cbTipo.addComboBoxListener( this );
		txtCodCli.addKeyListener( this );
		txtCodAtend.addKeyListener( this );

		vValsContr.addElement( -1 );
		vLabsContr.addElement( "<Todos>" );
		cbContr.setItensGeneric( vLabsContr, vValsContr );

		vValsitContr.addElement( -1 );
		vLabsitContr.addElement( "<Todos>" );
		cbitContr.setItensGeneric( vLabsitContr, vValsitContr );

		vValsTipo.addElement( -1 );
		vLabsTipo.addElement( "<Todos>" );
		cbTipo.setItensGeneric( vLabsTipo, vValsTipo );
	}

	private void visualizaAtend() {

		DLNovoAtend dl = null;
		String codatendo = (String) tabCli.getValor( tabCli.getLinhaSel(), 0 ).toString();
		String codatend = (String) tabCli.getValor( tabCli.getLinhaSel(), 6 ).toString();
		int icodAtend = Integer.parseInt( codatend );
		int icodAtendo = Integer.parseInt( codatendo );

		try {
			dl = new DLNovoAtend( txtCodCli.getVlrInteger(), this, true, con, icodAtendo, icodAtend, tipoatendo );
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
			sql.append( "A.NOMEATEND,ATEND.HORAATENDO FROM ATATENDIMENTO ATEND, ATTIPOATENDO TA, ATATENDENTE A WHERE " );
			sql.append( "TA.CODTPATENDO=ATEND.CODTPATENDO AND TA.CODEMP=ATEND.CODEMPTO AND TA.CODFILIAL=ATEND.CODFILIALTO " );
			sql.append( "AND A.CODATEND=ATEND.CODATEND AND A.CODEMP=ATEND.CODEMPAE AND A.CODFILIAL=ATEND.CODFILIALAE " );
			sql.append( "AND ATEND.DATAATENDO BETWEEN ? AND ? AND TA.TIPOATENDO=? " );

			if ( txtCodCli.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPCL=? AND ATEND.CODFILIALCL=? AND ATEND.CODCLI=? " );
			}
			if ( cbContr.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPCT=? AND ATEND.CODFILIALCT=? AND ATEND.CODCONTR=? " );
			}
			if ( cbitContr.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODITCONTR=? " );
			}
			if ( cbTipo.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPTO=? AND ATEND.CODFILIALTO=? AND ATEND.CODTPATENDO=? " );
			}
			if ( txtCodAtend.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPAE=? AND ATEND.CODFILIALAE=? AND ATEND.CODATEND=? " );
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

				ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setString( iparam++, tipoatendo );

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
				if ( cbTipo.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
					ps.setInt( iparam++, cbTipo.getVlrInteger() );
				}
				if ( txtCodAtend.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, lcAtend.getCodEmp() );
					ps.setInt( iparam++, lcAtend.getCodFilial() );
					ps.setInt( iparam++, txtCodAtend.getVlrInteger() );
				}
				if ( txtCodRec.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, lcRec.getCodEmp() );
					ps.setInt( iparam++, lcRec.getCodFilial() );
					ps.setInt( iparam++, txtCodRec.getVlrInteger() );
					ps.setInt( iparam++, txtNParcItRec.getVlrInteger() );
				}

				ResultSet rs = ps.executeQuery();

				tabCli.limpa();
				vCodAtends.clear();

				for ( int i = 0; rs.next(); i++ ) {
					tabCli.adicLinha();

					vCodAtends.add( "" + rs.getString( "CodAtendo" ) );
					tabCli.setValor( rs.getString( "CodAtendo" ), i, 0 );
					tabCli.setValor( rs.getString( "DocAtendo" ), i, 1 );
					tabCli.setValor( rs.getString( "StatusAtendo" ), i, 2 );
					tabCli.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DataAtendo" ) ), i, 3 );
					tabCli.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DataAtendoFin" ) ), i, 4 );
					tabCli.setValor( rs.getString( "OBSATENDO" ), i, 5 );
					tabCli.setValor( rs.getInt( "CODATEND" ), i, 6 );
					tabCli.setValor( rs.getString( "NomeAtend" ), i, 7 );
					tabCli.setValor( rs.getTime( "HoraAtendo" ).toString(), i, 8 );
					tabCli.setValor( rs.getTime( "HoraAtendoFin" ).toString(), i, 9 );

				}
				rs.close();
				ps.close();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar tabela de atendimento!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	private void excluiAtend() {

		if ( tabCli.getLinhaSel() == -1 ) {
			Funcoes.mensagemInforma( this, "Selecione um item na lista!" );
			return;
		}
		try {
			String sSQL = "DELETE FROM ATATENDIMENTO WHERE CODATENDO=? AND CODEMP=? AND CODFILIAL=?";
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setString( 1, "" + vCodAtends.elementAt( tabCli.getLinhaSel() ) );
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

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcAtend ) {
			carregaAtendimentos();
		}
		else if ( cevt.getListaCampos() == lcCli ) {
			carregaAtendimentos();
			HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboContr( con, txtCodCli.getVlrInteger(), "<Todos>" );
			cbContr.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) { }

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btNovo ) {
			novoAtend();
		}
		else if ( evt.getSource() == btExcluir ) {
			excluiAtend();
		}
		else if ( evt.getSource() == btImprimir ) {
			try {
				FRAtendimentos tela = FRAtendimentos.class.newInstance();
				tela.setParametros( txtCodCli.getVlrInteger(), txtDataini.getVlrDate(), txtDatafim.getVlrDate() );
				Aplicativo.telaPrincipal.criatela( "", tela, con );

			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}

	private void montaComboTipo() {

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
			cbTipo.setItensGeneric( vLabsTipo, vValsTipo );
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
		lcAtend.setConexao( cn );
		lcRec.setConexao( cn );
		lcItRec.setConexao( cn );

		montaComboTipo();
	}

	public void focusGained( FocusEvent arg0 ) { }

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtDatafim ) {
			carregaAtendimentos();
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
		else if ( evt.getComboBoxPad() == cbTipo ) {
			carregaAtendimentos();
		}
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( ( kevt.getSource() == txtCodCli ) && ( txtCodCli.getVlrInteger() == 0 ) ) {
				carregaAtendimentos();
			}
			else if ( ( kevt.getSource() == txtCodAtend ) && ( txtCodAtend.getVlrInteger() == 0 ) ) {
				carregaAtendimentos();
			}
		}
	}

	public void keyReleased( KeyEvent arg0 ) { }

	public void keyTyped( KeyEvent arg0 ) { }

}
