/**
 * @version 11/07/2011 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms.view.frame.crud.detail <BR>
 *         Classe: @(#)FExpedicao.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * 
 *         Tela de cadastro de expedição de produtos.
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPassword;
import org.freedom.library.type.StringDireita;
import org.freedom.modulos.gms.business.object.RecMerc;
import org.freedom.modulos.gms.view.dialog.utility.DLPesagem;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.gms.view.frame.utility.FControleExpedicao;
import org.freedom.modulos.std.view.frame.crud.plain.FAlmox;
import org.freedom.modulos.std.view.frame.crud.tabbed.FTransp;

public class FExpedicao extends FDetalhe implements FocusListener, CarregaListener, PostListener, InsertListener, KeyListener {

	// *** Constantes

	private static final long serialVersionUID = 1L;

	// *** Variaveis

	private HashMap<String, Object> prefere = null;

	private boolean novo = true;

	private Vector<String> vValsFrete = new Vector<String>();

	private Vector<String> vLabsFrete = new Vector<String>();

	// *** Campos (Cabeçalho)

	private JTextFieldPad txtTicket = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtPlaca = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoExpedDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoExped = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRefProdCab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtRefProdDet = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDescTipoExped = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProdCab = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdCab = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProdPadrao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodMot = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeMot = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodVeic = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtModeloVeic = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtCNPJTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtVlrFrete = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, Aplicativo.casasDecFin );

	private JTextFieldPad txtCodProcExped = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtDescProcExped = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTipoProcExped = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtStatusItExped = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtPeso1 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDataPesagem = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtSaida = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtHoraPesagem = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	private JRadioGroup<String, String> rgFrete = null;

	private JTextFieldPad txtCodAlmoxProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	// *** Campos (Detalhe)

	private JTextFieldPad txtCodItExped = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdDet = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	// *** Paineis

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JPanelPad pinDetGrid = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );

	private JPanelPad pinCabObs = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	// *** Lista Campos

	private ListaCampos lcTran = new ListaCampos( this, "TN" );

	private ListaCampos lcProdCab = new ListaCampos( this, "PD" );

	private ListaCampos lcProdDet = new ListaCampos( this, "PD" );

	private ListaCampos lcTipoExped = new ListaCampos( this, "TE" );

	private ListaCampos lcProc = new ListaCampos( this, "TE" );

	private ListaCampos lcAlmox = new ListaCampos( this, "AX" );

	private ListaCampos lcMotorista = new ListaCampos( this, "MT" );

	private ListaCampos lcVeiculo = new ListaCampos( this, "VE" );

	// *** Labels

	private JLabelPad lbStatus = new JLabelPad();

	// *** Separador label

	private JLabelPad sepdet = new JLabelPad();

	// *** Botões

	private JButtonPad btPesagem = new JButtonPad( Icone.novo( "btBalanca.png" ) );

	// *** Tela do Painel de controle
	private FControleExpedicao tela_mae = null;

	// *** Tabelas

	private JTablePad tabPesagem = null;

	// *** Plugin

	private boolean atualizaPesoManual = false;

	private JTextAreaPad txaObs = new JTextAreaPad();

	private JScrollPane spnObs = new JScrollPane( txaObs );

	private JTabbedPanePad tpnCab = new JTabbedPanePad();

	private JPanelPad pinCabExpedicao = new JPanelPad();

	public FExpedicao() {

		montaTela();
	}

	private void montaTela() {

		setTitulo( "Expedição de produtos acabados" );
		setAtribos( 20, 20, 653, 550 );

		nav.setNavigation( true );

		configuraCampos();

		criaTabelas();
		montaListaCampos();
		montaPaineis();
		montaTab();
		ajustaTabela();
		adicListeners();

		setImprimir( true );

	}

	private void criaTabelas() {

		// Tabela de pesagens

		tabPesagem = new JTablePad();
		tabPesagem.setRowHeight( 21 );

		tabPesagem.adicColuna( "Seq." );
		tabPesagem.adicColuna( "Data" );
		tabPesagem.adicColuna( "Hora" );
		tabPesagem.adicColuna( "Peso 1" );

		tabPesagem.setColunaInvisivel( 0 );
		tabPesagem.setTamColuna( 60, 1 );
		tabPesagem.setTamColuna( 50, 2 );
		tabPesagem.setTamColuna( 100, 3 );

	}

	private void montaPaineis() {

		pnMaster.remove( spTab );
		pnMaster.add( pinDetGrid, BorderLayout.CENTER );

		pinDetGrid.add( spTab );
		pinDetGrid.add( new JScrollPane( tabPesagem ) );

		pnCliCab.add( tpnCab );
		tpnCab.addTab( "Geral", pinCabExpedicao );

		pinCabObs.add( spnObs );
		tpnCab.addTab( "Observações", pinCabObs );

		montaCabecalho();
		montaDetalhe();

	}

	public FExpedicao( boolean novo ) {

		super();

		this.novo = novo;

		montaTela();

	}

	private void configuraCampos() {

		txtPlaca.setUpperCase( true );
		txtPlaca.setMascara( JTextFieldPad.MC_PLACA );

		vValsFrete.addElement( "C" );
		vValsFrete.addElement( "F" );
		vLabsFrete.addElement( "CIF" );
		vLabsFrete.addElement( "FOB" );

		rgFrete = new JRadioGroup<String, String>( 1, 2, vLabsFrete, vValsFrete, -4 );

		lbStatus.setForeground( Color.WHITE );
		lbStatus.setBackground( Color.GRAY );
		lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbStatus.setOpaque( true );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
	}

	private void montaCabecalho() {

		setAltCab( 270 );

		setListaCampos( lcCampos );
		setPainel( pinCabExpedicao );

		adicCampo( txtTicket			, 7		, 20	, 70	, 20, "Ticket", "Ticket", ListaCampos.DB_PK, true );
		adic( txtPlaca					, 80	, 20	, 70	, 20 );

		adicCampo( txtCodTipoExped		, 153	, 20	, 40	, 20, "CodTipoExped", "Cód.T.", ListaCampos.DB_FK, txtDescTipoExped, true );
		adicDescFK( txtDescTipoExped	, 196	, 20	, 228	, 20, "DescTipoExped", "Tipo de expedição" );

		adicCampo( txtDtSaida			, 427	, 20	, 70	, 20, "DtSaida", "Data", ListaCampos.DB_SI, true );

		adicCampo( txtCodProdCab		, 7		, 60	, 70	, 20, "CodProd", "Cod.Pd.", ListaCampos.DB_FK, txtDescProdCab, true );
		adicDescFK( txtDescProdCab		, 80	, 60	, 200	, 20, "DescProd", "Descrição do Produto" );
		
		adicCampoInvisivel( txtRefProdCab, "RefProd", "Referência", ListaCampos.DB_SI, false );

		adicCampo( txtCodAlmox			, 283	, 60	, 50	, 20, "CodAlmox", "Almox.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescAlmox		, 336	, 60	, 160	, 20, "DescAlmox", "Descrição do almoxarifado" );

		adicCampo( txtCodVeic			, 7		, 100	, 70	, 20, "CodVeic", "Cod.Veic.", ListaCampos.DB_FK, txtModeloVeic, true );
		adicDescFK( txtModeloVeic		, 80	, 100	, 345	, 20, "Modelo", "Modelo do veículo" );
		
		adicCampo( txtCodMot			, 7		, 140	, 70	, 20, "CodMot", "Cod.Mot.", ListaCampos.DB_FK, txtNomeMot, true );
		adicDescFK( txtNomeMot			, 80	, 140	, 345	, 20, "NomeMot", "Nome do motorista" );

		adicCampo( txtCodTran			, 7		, 180	, 70	, 20, "CodTran", "Cod.Tran.", ListaCampos.DB_FK, txtNomeTran, true );
		adicDescFK( txtNomeTran			, 80	, 180	, 345	, 20, "NomeTran", "Nome da transportadora" );

		adicCampoInvisivel( txtStatus, "Status", "Status", ListaCampos.DB_SI, false );

		adicDBLiv( txaObs, "ObsExped", "Observações", false );

		adic( lbStatus					, 500	, 20	, 123	, 60 );

		setListaCampos( true, "EXPEDICAO", "EQ" );

		lcCampos.setQueryInsert( true );

	}

	private void montaDetalhe() {

		setAltDet( 70 );

		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		lcDet.setPodeIns( false );
		lcDet.setPodeExc( false );

		navRod.btNovo.setEnabled( false );
		navRod.btExcluir.setEnabled( false );

		adicCampo( txtCodItExped	, 7		, 20	, 40	, 20, "CodItExped", "Seq.", ListaCampos.DB_PK, true );
		adicCampo( txtCodProdDet		, 50	, 20	, 50	, 20, "CodProd", "Cód.Pd.", ListaCampos.DB_FK, txtDescProdDet, true );
		adicDescFK( txtDescProdDet		, 103	, 20	, 203	, 20, "DescProd", "Descrição do Produto" );
		adicCampoInvisivel( txtRefProdDet, "RefProd", "Referência", ListaCampos.DB_SI, false );

		adicCampoInvisivel( txtCodProcExped, "CodProcExped", "Cod.Proc.", ListaCampos.DB_FK, txtDescProcExped, true );
		adicDescFKInvisivel( txtDescProcExped, "DescProcExped", "Descrição do processo" );
		adicCampoInvisivel( txtCodTipoExpedDet, "CodTipoExped", "Cod.Tp.Exped.", ListaCampos.DB_SI, true );

		txtStatusItExped.setSoLeitura( true );
		adicCampoInvisivel( txtStatusItExped, "StatusItExped", "Status", ListaCampos.DB_SI, false );

		setListaCampos( true, "ITEXPEDICAO", "EQ" );
		lcDet.setQueryInsert( false );

		sepdet.setBorder( BorderFactory.createEtchedBorder() );
		adic( sepdet, 315, 4, 2, 52 );

		adic( btPesagem, 575, 5, 50, 50 );
		btPesagem.setToolTipText( "Realiza pesagem - (F12)" );
		pinDetGrid.setBackground( Color.RED );

	}

	private void carregaTipoRec() {

		txtCodTipoExped.setVlrInteger( (Integer) prefere.get( "codtipoexped" ) );
		lcTipoExped.carregaDados();

	}

	private void buscaPrefere() {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			prefere = new HashMap<String, Object>();

			sql.append( "select coalesce(pf8.codtipoexped,0) codtipoexped " );
			sql.append( "from sgprefere8 pf8 " );
			sql.append( "where pf8.codemp=? and pf8.codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE8" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				prefere.put( "codtipoexped", rs.getInt( "codtipoexped" ) );
			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void buscaVeiculo( String placa ) {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select ve.codveic from vdveiculo ve " );
			sql.append( "where ve.codemp=? and ve.codfilial=? and ve.placa=?" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setString( 3, placa );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				txtCodVeic.setVlrInteger( rs.getInt( "codveic" ) );
				lcVeiculo.carregaDados();

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void buscaMotorista( Integer codtran ) {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select first 1 tm.codmot " );
			sql.append( "from vdtranspmot tm " );
			sql.append( "where tm.codemp=? and tm.codfilial=? and tm.codtran=?" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setInt( 3, codtran );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				txtCodMot.setVlrInteger( rs.getInt( "codmot" ) );

				lcMotorista.setWhereAdic( "CODTRAN=" + codtran );

				lcMotorista.carregaDados();

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void buscaTransp( String placa ) {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select tp.codtran from vdtransp tp " );
			sql.append( "where tp.codemp=? and tp.codfilial=? and tp.placatran=?" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setString( 3, placa );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				txtCodTran.setVlrInteger( rs.getInt( "codtran" ) );
				lcTran.carregaDados();

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void ajustaTabela() {

		tab.setRowHeight( 21 );

		tab.setTamColuna( 30, 0 );
		tab.setColunaInvisivel( 1 );
		tab.setColunaInvisivel( 2 );
		tab.setTamColuna( 140, 4 );
		tab.setColunaInvisivel( 3 );
		tab.setColunaInvisivel( 5 );
		tab.setTamColuna( 70, 6 );
		tab.setTamColuna( 70, 7 );
		tab.setColunaInvisivel( 8 );

	}

	public void exec( int ticket, int tiporecmerc, FControleExpedicao tela_mae ) {

		try {
			lcCampos.edit();
			txtTicket.setVlrInteger( ticket );
			txtCodTipoExped.setVlrInteger( tiporecmerc );
			lcCampos.carregaDados();

			setTelaMae( tela_mae );

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void adicListeners() {

		txtPlaca.addFocusListener( this );

		lcCampos.addInsertListener( this );
		lcCampos.addCarregaListener( this );
		lcDet.addPostListener( this );
		lcDet.addCarregaListener( this );
		lcTipoExped.addCarregaListener( this );
		lcProdCab.addCarregaListener( this );

		btPesagem.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

	}

	private void montaListaCampos() {

		// * Tipo de Recebimento Cabeçalho

		lcTipoExped.add( new GuardaCampo( txtCodTipoExped	, "CodTipoExped"	, "Cód.Tipo.Exp."					, ListaCampos.DB_PK, false ) );
		lcTipoExped.add( new GuardaCampo( txtDescTipoExped	, "DescTipoExped"	, "Descrição do tipo de expedição"	, ListaCampos.DB_SI, false ) );
		lcTipoExped.add( new GuardaCampo( txtCodProdPadrao	, "Codprod"			, "Cód.Prod."						, ListaCampos.DB_SI, false ) );

		txtCodTipoExped.setTabelaExterna( lcTipoExped, FTipoRecMerc.class.getCanonicalName() );
		txtCodTipoExped.setNomeCampo( "CodTipoExped" );
		txtCodTipoExped.setFK( true );

		lcTipoExped.setReadOnly( true );
		lcTipoExped.montaSql( false, "TIPOEXPEDICAO", "EQ" );

		// * Produto (Cabeçalho)

		lcProdCab.add( new GuardaCampo( txtCodProdCab	, "CodProd"		, "Cód.prod."				, ListaCampos.DB_PK, false ) );
		lcProdCab.add( new GuardaCampo( txtDescProdCab	, "DescProd"	, "Descrição do produto"	, ListaCampos.DB_SI, false ) );
		lcProdCab.add( new GuardaCampo( txtRefProdCab	, "RefProd"		, "Referência"				, ListaCampos.DB_SI, false ) );
		lcProdCab.add( new GuardaCampo( txtCodAlmoxProd	, "CodAlmox"	, "Cód.Almox."				, ListaCampos.DB_SI, false ) );

		txtCodProdCab.setTabelaExterna( lcProdCab, FProduto.class.getCanonicalName() );
		txtCodProdCab.setNomeCampo( "CodProd" );
		txtCodProdCab.setFK( true );

		lcProdCab.setReadOnly( true );
		lcProdCab.montaSql( false, "PRODUTO", "EQ" );

		// * Almoxarifado (Cabeçalho)

		lcAlmox.add( new GuardaCampo( txtCodAlmox		, "CodAlmox"		, "Cód.almox."				, ListaCampos.DB_PK, true ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmox		, "DescAlmox"		, "Descrição do almoxarifado", ListaCampos.DB_SI, false ) );
		
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setReadOnly( true );
		lcAlmox.setQueryCommit( false );
		txtCodAlmox.setTabelaExterna( lcAlmox, FAlmox.class.getCanonicalName() );

		// * Transportadora

		lcTran.add( new GuardaCampo( txtCodTran			, "CodTran"			, "Cód.For."				, ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtNomeTran		, "NomeTran"		, "Nome da transportadora"	, ListaCampos.DB_SI, false ) );
		lcTran.add( new GuardaCampo( txtCNPJTran		, "CnpjTran"		, "CNPJ"					, ListaCampos.DB_SI, false ) );

		txtCodTran.setTabelaExterna( lcTran, FTransp.class.getCanonicalName() );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );

		lcTran.setReadOnly( true );
		lcTran.montaSql( false, "TRANSP", "VD" );

		// * Produto (Detalhe)

		lcProdDet.add( new GuardaCampo( txtCodProdDet	, "CodProd"			, "Cód.prod."				, ListaCampos.DB_PK, false ) );
		lcProdDet.add( new GuardaCampo( txtDescProdDet	, "DescProd"		, "Descrição do produto"	, ListaCampos.DB_SI, false ) );
		lcProdDet.add( new GuardaCampo( txtRefProdDet	, "RefProd"			, "Referência"				, ListaCampos.DB_SI, false ) );

		txtCodProdDet.setTabelaExterna( lcProdDet, FProduto.class.getCanonicalName() );
		txtCodProdDet.setNomeCampo( "CodProd" );
		txtCodProdDet.setFK( true );

		lcProdDet.setReadOnly( true );
		lcProdDet.montaSql( false, "PRODUTO", "EQ" );

		/***************
		 * PROCESSOS *
		 ***************/

		lcProc.add( new GuardaCampo( txtCodProcExped	, "CodProcExped"	, "Cód.Proc."				, ListaCampos.DB_PK, false ) );
		lcProc.add( new GuardaCampo( txtDescProcExped	, "DescProcExped"	, "Descrição do processo"	, ListaCampos.DB_SI, false ) );
		lcProc.add( new GuardaCampo( txtCodTipoExpedDet	, "CodTipoExped"	, "Cod.Tp.Exp."				, ListaCampos.DB_PF, false ) );
		lcProc.add( new GuardaCampo( txtTipoProcExped	, "TipoProcExped"	, "Tp.Proc.Exp."			, ListaCampos.DB_SI, false ) );

		txtCodProcExped.setTabelaExterna( lcProc, null );
		txtCodProcExped.setNomeCampo( "CodProcExped" );
		txtCodProcExped.setFK( true );

		lcProc.setReadOnly( true );
		lcProc.montaSql( false, "PROCEXPED", "EQ" );

		/***************
		 * MOTORISTAS *
		 ***************/

		lcMotorista.add( new GuardaCampo( txtCodMot		, "CodMot"	, "Cód.Mot."			, ListaCampos.DB_PK, false ) );
		lcMotorista.add( new GuardaCampo( txtNomeMot	, "NomeMot"	, "Nome do motorista"	, ListaCampos.DB_SI, false ) );

		txtCodMot.setTabelaExterna( lcMotorista, null );
		txtCodMot.setNomeCampo( "CodMot" );
		txtCodMot.setFK( true );

		lcMotorista.setReadOnly( true );
		lcMotorista.montaSql( false, "MOTORISTA", "VD" );

		/***************
		 * VEICULOS *
		 ***************/

		lcVeiculo.add( new GuardaCampo( txtCodVeic		, "CodVeic"	, "Cód.Veic."			, ListaCampos.DB_PK, false ) );
		lcVeiculo.add( new GuardaCampo( txtModeloVeic	, "Modelo"	, "Modelo do veículo"	, ListaCampos.DB_SI, false ) );
		lcVeiculo.add( new GuardaCampo( txtPlaca		, "Placa"	, "Placa"				, ListaCampos.DB_SI, false ) );
		
		txtCodVeic.setTabelaExterna( lcVeiculo, null );
		txtCodVeic.setNomeCampo( "CodVeic" );
		txtCodVeic.setFK( true );

		lcVeiculo.setReadOnly( true );
		lcVeiculo.montaSql( false, "VEICULO", "VD" );

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( evt.getSource() == btPesagem ) {
			if ( validaProcesso() ) {
				capturaAmostra();
			}
		}

		super.actionPerformed( evt );
	}

	private boolean validaProcesso() {

		boolean ret = false;
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			if ( "PE".equals( txtStatusItExped.getVlrString() ) ) {

				sql.append( "select first 1 statusitexped from eqitexped " );
				sql.append( "where codemp=? and codfilial=? and ticket=? and coditexped < ? " );
				sql.append( "order by codititexped desc" );

				ps = con.prepareStatement( sql.toString() );

				ps.setInt( 1, lcCampos.getCodEmp() );
				ps.setInt( 2, lcCampos.getCodFilial() );
				ps.setInt( 3, txtTicket.getVlrInteger() );
				ps.setInt( 4, txtCodItExped.getVlrInteger() );

				rs = ps.executeQuery();

				if ( rs.next() ) {

					String statusant = rs.getString( "statusitexped" );

					if ( "PE".equals( statusant ) ) {
						Funcoes.mensagemInforma( this, "Processo anterior ainda está pendente!" );
						ret = false;
					}
					else if ( "FN".equals( statusant ) ) {
						ret = true;
					}

				}
				else {
					ret = true;
				}
			}
			else {

				if ( atualizaPesoManual ) {
					AtualizaAmostra();
				}
				else {
					Funcoes.mensagemInforma( this, "Processo ja foi finalizado" );
				}
				ret = false;
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return ret;
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );

		String DataP1 = null;
		String HoraP1 = null;
		String UnidP1 = null;
		String interno = null;
		BigDecimal PesoP1 = null;

		String DataP2 = null;
		String HoraP2 = null;
		String UnidP2 = null;

		BigDecimal PesoP2 = null;
		BigDecimal PesoLiq = null;
		BigDecimal PesoTotal = null;
		BigDecimal media = null;
		BigDecimal desconto = null;

		String renda = null;

		RecMerc recmerc = new RecMerc( this, txtTicket.getVlrInteger(), con );

		try {

			try {

				HashMap<String, Object> p1 = recmerc.getPrimeirapesagem();

				PesoP1 = (BigDecimal) p1.get( "peso" );
				DataP1 = (String) p1.get( "data" );
				HoraP1 = (String) p1.get( "hora" );
				UnidP1 = (String) p1.get( "unid" );
				interno = (String) p1.get( "interno" );

				HashMap<String, Object> p2 = recmerc.getSegundapesagem();

				PesoP2 = (BigDecimal) p2.get( "peso" );
				DataP2 = (String) p2.get( "data" );
				HoraP2 = (String) p2.get( "hora" );
				UnidP2 = (String) p2.get( "unid" );

				PesoLiq = PesoP1.subtract( PesoP2 );

				HashMap<String, Object> p3 = recmerc.getRendapesagem();

				media = (BigDecimal) p3.get( "media" );
				renda = (String) p3.get( "renda" );

				desconto = recmerc.getDesconto();

				if ( desconto == null ) {
					desconto = new BigDecimal( 0 );
				}

				if ( desconto.floatValue() > 0 ) {

					BigDecimal pesodesc = PesoLiq.multiply( desconto.divide( new BigDecimal( 100 ) ) );
					PesoTotal = PesoLiq.subtract( pesodesc );

					System.out.println( "Aplicado desconto no peso de :" + pesodesc.toString() );

				}
				else {
					PesoTotal = PesoLiq;
				}

			} catch ( Exception e ) {
				System.out.println( "Erro ao buscar pesagens!" );
			}

			imp.pulaLinha( 0, imp.comprimido() + " " );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 77, txtDescAlmox.getVlrString() );

			imp.pulaLinha( 2, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PLACA:............:" );
			imp.say( imp.pRow(), 24, Funcoes.setMascara( txtPlaca.getVlrString(), JTextFieldPad.mascplaca ) );

			imp.say( imp.pRow(), 35, "FRETE:" );
			imp.say( imp.pRow(), 41, "F".equals( rgFrete.getVlrString() ) ? "FOB" : "CIF" );

			imp.say( imp.pRow(), 70, "INTERNO.:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 48, StringFunctions.strZero( txtTicket.getVlrString(), 10 ) );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRODUTO:..........:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );
			imp.say( imp.pRow(), 15, txtDescProdCab.getVlrString().trim() );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRODUTOR:.........:" );

			imp.pulaLinha( 2, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRIMEIRA PESAGEM..:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 7, 0, String.valueOf( PesoP1 ) ) );

			imp.say( imp.pRow(), 23, UnidP1 );
			imp.say( imp.pRow(), 30, DataP1 );
			imp.say( imp.pRow(), 46, HoraP1 );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "SEGUNDA PESAGEM...:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 7, 0, String.valueOf( PesoP2 ) ) );
			imp.say( imp.pRow(), 23, UnidP2 );
			imp.say( imp.pRow(), 30, DataP2 );
			imp.say( imp.pRow(), 46, HoraP2 );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "LIQUIDO...........:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 7, 0, String.valueOf( PesoLiq ) ) );
			imp.say( imp.pRow(), 23, UnidP1 );

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( imp.pRow(), 3, "DESCONTO..........:" );
			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 2, 2, String.valueOf( desconto ) ) + " %" );

			imp.say( imp.pRow(), 30, "OBS.:" + Funcoes.copy( txaObs.getVlrString(), 0, 30 ) );

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( imp.pRow(), 3, "TOTAL.............:" );

			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 7, 0, String.valueOf( PesoTotal ) ) );
			imp.say( imp.pRow(), 23, UnidP1 );

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( imp.pRow(), 3, "Renda.............:" );
			imp.say( imp.pRow(), 24, String.valueOf( media.intValue() ) );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "Renda Classif.....:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 15, renda );

			imp.pulaLinha( 3 );
			imp.fechaGravacao();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro na impressão de ticket!\n" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcProdCab.setConexao( cn );
		lcProdDet.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcTran.setConexao( cn );
		lcTipoExped.setConexao( cn );
		lcProc.setConexao( cn );
		lcMotorista.setConexao( cn );
		lcVeiculo.setConexao( cn );

		buscaPrefere();

		if ( novo ) {
			lcCampos.insert( true );
		}

	}

	private void capturaAmostra() {

		DLPesagem dl = null;

		try {

			dl = new DLPesagem( this, txtTipoProcExped.getVlrString() );

			dl.setConexao( con );
			dl.execShow();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		if ( dl.OK ) {

			txtPeso1.setVlrBigDecimal( dl.getPeso1() );

			txtDataPesagem.setVlrDate( dl.getData() );
			txtHoraPesagem.setVlrString( dl.getHora() );

			if ( txtPeso1.getVlrBigDecimal().floatValue() > 0 && txtDataPesagem.getVlrDate() != null && txtHoraPesagem.getVlrString() != null ) {

				salvaAmostra();

			}

			limpaAmostra();
			montaTabPesagem();

		}

		dl.dispose();
	}

	private void limpaAmostra() {

		txtPeso1.setVlrBigDecimal( new BigDecimal( 0 ) );
		txtDataPesagem.setVlrDate( null );
		txtHoraPesagem.setVlrString( null );
	}

	private void salvaAmostra() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		Integer codamostragem = 0;
		ResultSet rs = null;

		try {

			sql.append( "select coalesce( max(codamostragem), 0 ) " );
			sql.append( "from eqrecamostragem " );
			sql.append( "where " );
			sql.append( "codemp=? and codfilial=? and ticket=? and coditrecmerc=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, lcDet.getCodEmp() );
			ps.setInt( 2, lcDet.getCodFilial() );
			ps.setInt( 3, txtTicket.getVlrInteger() );
			ps.setInt( 4, txtCodItExped.getVlrInteger() );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codamostragem = rs.getInt( 1 );
			}

			codamostragem++;

			sql = new StringBuilder();

			sql.append( "insert into eqrecamostragem " );
			sql.append( "(codemp,codfilial,ticket,coditrecmerc,codamostragem,pesoamost,dataamost,horaamost)" );
			sql.append( "values(?, ?, ?, ?, ?, ?, ?, ?, ?)" );

			ps = con.prepareStatement( sql.toString() );

			Integer coditrecmerc = txtCodItExped.getVlrInteger();

			ps.setInt( 1, lcDet.getCodEmp() );
			ps.setInt( 2, lcDet.getCodFilial() );
			ps.setInt( 3, txtTicket.getVlrInteger() );
			ps.setInt( 4, coditrecmerc );
			ps.setInt( 5, codamostragem );
			ps.setBigDecimal( 6, txtPeso1.getVlrBigDecimal() );
			ps.setDate( 7, Funcoes.dateToSQLDate( txtDataPesagem.getVlrDate() ) );
			ps.setTime( 8, Funcoes.strTimeTosqlTime( txtHoraPesagem.getVlrString() ) );

			ps.execute();
			con.commit();

			lcDet.edit();
			lcDet.post();

			txtCodItExped.setVlrInteger( coditrecmerc );
			lcDet.carregaDados();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void AtualizaAmostra() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		Integer codamostragem = 0;
		ResultSet rs = null;

		try {

			sql = new StringBuilder();

			sql.append( "update eqexpedamostragem set pesoamost=? " );
			sql.append( "where codemp=? and codfilial=? and ticket=? and coditexped=? and codamostragem=? " );

			int i = 0;

			while ( tabPesagem.getNumLinhas() > i ) {

				codamostragem = (Integer) tabPesagem.getValor( i, 0 );

				txtPeso1.setVlrBigDecimal( ConversionFunctions.stringCurrencyToBigDecimal( ( (StringDireita) tabPesagem.getValor( i, 3 ) ).toString() ) );

				ps = con.prepareStatement( sql.toString() );

				ps.setBigDecimal( 1, txtPeso1.getVlrBigDecimal() );

				ps.setInt( 3, lcDet.getCodEmp() );
				ps.setInt( 4, lcDet.getCodFilial() );
				ps.setInt( 5, txtTicket.getVlrInteger() );
				ps.setInt( 6, txtCodItExped.getVlrInteger() );
				ps.setInt( 7, codamostragem );

				ps.execute();

				i++;
			}

			con.commit();

			// lcDet.edit();
			// lcDet.post();

			montaTabPesagem();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void focusGained( FocusEvent e ) {

		// TODO Auto-generated method stub

	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource() == txtPlaca ) {

			if ( txtCodTran.getVlrInteger() == 0 ) {
				buscaTransp( txtPlaca.getVlrString() );
				buscaVeiculo( txtPlaca.getVlrString() );
				buscaMotorista( txtCodTran.getVlrInteger() );
			}

		}

	}

	private void montaTabPesagem() {

		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select " );
			sql.append( "codamostragem, pesoamost, dataamost, horaamost " );
			sql.append( "from eqexpedamostragem " );
			sql.append( "where codemp=? and codfilial=? and ticket=? and coditexped=? " );

			StringBuffer status = new StringBuffer( "" );

			System.out.println( "SQL:" + sql.toString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, lcDet.getCodEmp() );
			ps.setInt( iparam++, lcDet.getCodFilial() );
			ps.setInt( iparam++, txtTicket.getVlrInteger() );
			ps.setInt( iparam++, txtCodItExped.getVlrInteger() );

			ResultSet rs = ps.executeQuery();

			tabPesagem.limpa();

			int row = 0;

			while ( rs.next() ) {

				tabPesagem.adicLinha();

				tabPesagem.setValor( rs.getInt( "codamostragem" ), row, 0 );
				tabPesagem.setValor( rs.getDate( "dataamost" ), row, 1 );
				tabPesagem.setValor( rs.getString( "horaamost" ), row, 2 );
				tabPesagem.setValor( Funcoes.bdToStr( rs.getBigDecimal( "pesoamost" ), 2 ), row, 3 );

				row++;

			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void atualizaStatus() {

		RecMerc.atualizaStatus( txtStatus.getVlrString(), lbStatus );

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCampos ) {
			atualizaStatus();
			// Se náo foi realizada nenhuma pesagem deve carregar a sequencia 1 para facilitar a utilizacao
			if ( txtStatus.getVlrString().equals( RecMerc.STATUS_PENDENTE.getValue() ) ) {
				carregaSequencia( 0 );
			}
			// Se ja tiver sido realizada a pesagem 1 deve carregar a sequencia 2 para facilitar a utilizacao
			if ( txtStatus.getVlrString().equals( RecMerc.STATUS_PESAGEM_1.getValue() ) ) {
				carregaSequencia( 1 );
			}
			// Se ja tiver sido realizada a pesagem 2 deve carregar a proxima sequencia
			else if ( txtStatus.getVlrString().equals( RecMerc.STATUS_DESCARREGAMENTO.getValue() ) ) {
				carregaSequencia( 2 );
			}

		}
		else if ( cevt.getListaCampos() == lcTipoExped ) {
			carregaProdutoPadrao();
		}

		else if ( cevt.getListaCampos() == lcDet ) {

			limpaAmostra();

			montaTabPesagem();

		}
		else if ( cevt.getListaCampos() == lcProdCab && lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {

			txtCodAlmox.setVlrInteger( txtCodAlmoxProd.getVlrInteger() );
			lcAlmox.carregaDados();

		}

	}

	// Futuramente deve ser implementado para buscar automaticamente o
	// coditrecmerc a partir do status e do tipo de processo.
	private void carregaSequencia( Integer proximos ) {

		try {

			if ( proximos == 0 ) {
				lcDet.carregaItem( 0 );
			}

			for ( int i = 0; i < proximos; i++ ) {
				lcDet.next( Types.INTEGER );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void carregaProdutoPadrao() {

		try {

			if ( txtCodProdPadrao.getVlrInteger() > 0 && ( ListaCampos.LCS_INSERT == lcCampos.getStatus() ) ) {
				txtCodProdCab.setVlrInteger( txtCodProdPadrao.getVlrInteger() );
				lcProdCab.carregaDados();
			}
			
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub

	}

	public void beforePost( PostEvent pevt ) {

		super.beforePost( pevt );

		if ( pevt.getListaCampos() == lcCampos ) {
			if ( "".equals( txtStatus.getVlrString() ) ) {
				txtStatus.setVlrString( "PE" );
			}
		}
	}

	public void afterPost( PostEvent pevt ) {

		super.beforePost( pevt );

		if ( pevt.getListaCampos() == lcDet ) {
			lcCampos.carregaDados();
		}
		else if ( pevt.getListaCampos() == lcCampos ) {
			lcDet.carregaDados();
			carregaSequencia( 0 );
		}

	}

	public void beforeInsert( InsertEvent ievt ) {

		// TODO Auto-generated method stub

	}

	public void afterInsert( InsertEvent ievt ) {

		if ( ievt.getListaCampos() == lcCampos ) {
			carregaTipoRec();

			txtDtSaida.setVlrDate( new Date() );

		}

	}

	public void setTelaMae( FControleExpedicao tela_mae ) {

		this.tela_mae = tela_mae;
	}

	public void dispose() {

		super.dispose();

		if ( tela_mae != null ) {
			tela_mae.montaGrid();
		}

	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_F12 ) {
			btPesagem.doClick();
		}
		if ( kevt.getKeyCode() == KeyEvent.VK_F11 ) {
			liberaCampo( true );
		}

	}

	private void liberaPeso( boolean libera ) {

		tabPesagem.setColunaEditavel( 3, libera );
		tabPesagem.setColunaEditavel( 4, libera );

		atualizaPesoManual = libera;

	}

	private void liberaCampo( boolean libera ) {

		FPassword fpw = new FPassword( this, FPassword.LIBERA_CAMPO_PESAGEM, null, con );
		fpw.execShow();

		if ( fpw.OK ) {
			liberaPeso( libera );
		}

		fpw.dispose();

	}

}
