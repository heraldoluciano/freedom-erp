package org.freedom.modulos.gms;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.cfg.FBairro;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FRecMerc extends FDetalhe implements FocusListener, JComboBoxListener, CarregaListener, PostListener, InsertListener {
	
	// *** Constantes
	
	private static final long serialVersionUID = 1L;

	// *** Variaveis
	
	private HashMap<String, Object> prefere = null;
	private boolean novo = true;
	private Vector<String> vValsFrete = new Vector<String>();
	private Vector<String> vLabsFrete = new Vector<String>();
	
	private Vector<Integer> vValsBairro = new Vector<Integer>();
	private Vector<String> vLabsBairro = new Vector<String>();

	// *** Campos (Cabeçalho)
	
	private JTextFieldPad txtTicket = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	private JTextFieldPad txtPlacaTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTipoRecMercDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodTipoRecMerc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private JTextFieldFK txtDescTipoRecMerc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
		
	private JTextFieldPad txtCodProdCab = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private JTextFieldFK txtDescProdCab = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JTextFieldFK txtCNPJTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );
	
	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtCNPJFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );
	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodBairro = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtNomeBairro = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );
	
	private JTextFieldFK txtCodPais = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtSiglaUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtCodMun = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );	
	private JTextFieldFK txtDescMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodProcRecMerc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldFK txtDescProcRecMerc = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	
	private JTextFieldPad txtTipoProcRecMerc = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
	
	private JTextFieldPad txtStatus = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0); 

	private JComboBoxPad cbBairro = new JComboBoxPad( vLabsBairro, vValsBairro, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtPeso = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, 2);
	private JTextFieldPad txtDataPesagem = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );	
	private JTextFieldPad txtHoraPesagem = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	
	private JRadioGroup<String, String> rgFrete = null;
		
	// *** Campos (Detalhe)
	
	private JTextFieldPad txtCodItRecMerc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodProdDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private JTextFieldFK txtDescProdDet = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	
	// *** Paineis
	
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();// JPanelPad.TP_JPANEL, new BorderLayout() );
//	private JPanelPad pinDetCampos = new JPanelPad(60,0);
	private JPanelPad pinDetGrid = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ));	
	
	// *** Lista Campos	
	
	private ListaCampos lcTran = new ListaCampos( this, "TN" );
	private ListaCampos lcFor = new ListaCampos( this, "FR" );
	private ListaCampos lcProdCab = new ListaCampos( this, "PD" );
	private ListaCampos lcProdDet = new ListaCampos( this, "PD" );
	private ListaCampos lcTipoRecMerc = new ListaCampos( this, "TR" );
	private ListaCampos lcBairro = new ListaCampos( this );
	private ListaCampos lcMunic = new ListaCampos( this );
	private ListaCampos lcProc = new ListaCampos( this, "TP" );
	
	// *** Labels
	
	private JLabelPad lbBairro = new JLabelPad("Bairro");
	private JLabelPad lbStatus = new JLabelPad();
	
	// *** Botões
	
	private JButtonPad btAdicBairro = new JButtonPad( Icone.novo( "btAdic2.gif" ) );
	private JButtonPad btPesagem = new JButtonPad( Icone.novo( "btBalanca.png" ) );
	
	// *** Tela do Painel de controle
	private FControleRecMerc tela_mae = null;
	
	// *** Tabelas
	
	private Tabela tabPesagem = null;
	
	
	public FRecMerc (boolean novo) {
		
		super();
		
		this.novo = novo;
		
		setTitulo( "Recepção de mercadorias" );
		setAtribos( 50, 50, 653, 480);

		configuraCampos();

		criaTabelas();
		montaListaCampos();
		montaTela();
		montaTab();
		ajustaTabela();
		adicListeners();
					
		setImprimir(true);
		
	}
	
	private void configuraCampos() {
		
		txtPlacaTran.setUpperCase( true );
		txtPlacaTran.setMascara( JTextFieldPad.MC_PLACA );
		
		txtCodMun.setAtivo( false );
		
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
		lbStatus.setText( "NÃO SALVO" );
		lbStatus.setVisible( true );
		
	}
	
	private void criaTabelas() {
		
		// Tabela de pesagens
		
		tabPesagem = new Tabela();
//		tabPesagem.setRowHeight( 21 );

		tabPesagem.adicColuna( "Data" );
		tabPesagem.adicColuna( "Hora" );
		tabPesagem.adicColuna( "Peso" );
				
		tabPesagem.setTamColuna( 70, 1 );
		tabPesagem.setTamColuna( 70, 2 );
		tabPesagem.setTamColuna( 120, 3 );
		
	}

	
	private void montaTela() {

		pinDetGrid.add( new JScrollPane( tabPesagem ) );
		
		montaCabecalho();		
		montaDetalhe();
		
		
	}
	
	public void montaTabPesagem() {
		
		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			sql.append( "pesoamost, dataamost, horaamost ");			
			sql.append( "from eqrecamostragem ");
			sql.append( "where codemp=? and codfilial=? and ticket=? and coditrecmerc=? ");
			
			StringBuffer status = new StringBuffer("");

			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, lcDet.getCodEmp() );
			ps.setInt( iparam++, lcDet.getCodFilial() );
			ps.setInt( iparam++, txtTicket.getVlrInteger() );
			ps.setInt( iparam++, txtCodItRecMerc.getVlrInteger() );
			
			ResultSet rs = ps.executeQuery();		
			
			tabPesagem.limpa();
						
			int row = 0;
			
			while ( rs.next() ) {
				
				tabPesagem.adicLinha();
				
				tabPesagem.setValor(  rs.getDate( "dataamost" ), row, 0 );
				tabPesagem.setValor( rs.getString( "horaamost" ), row, 1 );
				tabPesagem.setValor( Funcoes.bdToStr( rs.getBigDecimal( "pesoamost" ),2), row, 2 );
								
				row++;
				
			}

		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	private void montaCabecalho() {

		setAltCab( 210 );
		
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab);
		 
		adicCampo( txtTicket, 7, 20, 70, 20, "Ticket", "Ticket", ListaCampos.DB_PK, true);
		adicCampo( txtPlacaTran, 80, 20, 70, 20, "PlacaVeiculo", "Placa", ListaCampos.DB_SI, true);

		adicCampo( txtCodTipoRecMerc, 153, 20, 40, 20, "CodTipoRecMerc", "Cód.T.", ListaCampos.DB_FK, txtDescTipoRecMerc, true );
		adicDescFK( txtDescTipoRecMerc, 196, 20, 301, 20, "DescTipoRecMerc", "Tipo de recebimento" ) ;
		
		adicCampo( txtCodProdCab, 7, 60, 70, 20, "CodProd", "Cod.Pd.", ListaCampos.DB_FK, txtDescProdCab, true );
		adicDescFK( txtDescProdCab, 80, 60, 417, 20, "DescProd", "Descrição do Produto" );
		
		adicCampo( txtCodTran, 7, 100, 70, 20, "CodTran", "Cod.Tran.", ListaCampos.DB_FK, txtNomeTran, true );
		adicDescFK( txtNomeTran, 80, 100, 417, 20, "NomeTran", "Nome da transportadora" );

		adicDB( rgFrete, 500, 100, 123, 20, "TipoFrete", "Tipo de frete", false );
		
		adicCampo( txtCodFor, 7, 140, 70, 20, "CodFor", "Cod.For.", ListaCampos.DB_FK, txtRazFor, true );
		adicDescFK( txtRazFor, 80, 140, 237, 20, "RazFor", "Razão social do fornecedor" );
		
		adicCampo( txtCodPais, 320, 140, 28, 20, "CodPais", "País", ListaCampos.DB_SI, false );
		adicCampo( txtSiglaUF, 351, 140, 23, 20, "SiglaUF", "UF", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodMun, "CodMunic", "Cód.Mun.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescMun, 377, 140, 120, 20, "DescMunic", "Município" );
		
		adicCampoInvisivel( txtCodBairro, "CodBairro", "Cód.Bairro", ListaCampos.DB_FK, false );
		adicCampoInvisivel( txtStatus, "Status", "Status", ListaCampos.DB_SI, false );
		
		pinCab.adic( lbBairro, 500, 120, 100, 20 );
		pinCab.adic( cbBairro, 500, 140, 100, 20 );
		
		adic( btAdicBairro, 603, 140, 20, 20 );
		
		adic( lbStatus, 500, 20 , 123 , 60  );
		
		setListaCampos( true, "RECMERC", "EQ");
		lcCampos.setQueryInsert( true );		
	
		
	}
	
	private void montaDetalhe() {
		
		setAltDet(120);
		
		setPainel( pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

//		setPainel( pinDetCampos );
		
		

		
		
		adicCampo(txtCodItRecMerc, 7, 20, 40, 20, "CodItRecMerc","Seq.",ListaCampos.DB_PK, true);
		adicCampo( txtCodProdDet, 50, 20, 70, 20,"CodProd","Cod.Prod.",ListaCampos.DB_FK, txtDescProdDet, true );
		adicDescFK( txtDescProdDet, 123, 20, 200, 20, "DescProd", "Descrição do Produto" );		
		
		adicCampoInvisivel( txtCodProcRecMerc, "CodProcRecMerc","Cod.Proc.",ListaCampos.DB_FK, txtDescProcRecMerc, true );		
		adicDescFKInvisivel( txtDescProcRecMerc, "DescProcRecMerc", "Descrição do processo" );
		adicCampoInvisivel( txtCodTipoRecMercDet, "CodTipoRecMerc","Cod.Tp.Rec.Merc", ListaCampos.DB_SI,  true );
		
		setListaCampos( true, "ITRECMERC", "EQ");		
		lcDet.setQueryInsert( true );
		
		adic(btPesagem, 575, 5, 50, 50);
		pinDetGrid.setBackground( Color.RED );
		adic(pinDetGrid, 0, 60, 700, 50);
		
		
	}

	
	private void carregaTipoRec() {
		
		txtCodTipoRecMerc.setVlrInteger( (Integer) prefere.get( "codtiporecmerc" ) );
		lcTipoRecMerc.carregaDados();
		
	}

	private void buscaPrefere() {
		
		StringBuilder sql = new StringBuilder();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			prefere = new HashMap<String, Object>();
			
			sql.append( "select coalesce(pf8.codtiporecmerc,0) codtiporecmerc " );
			sql.append( "from sgprefere8 pf8 ");
			sql.append( "where pf8.codemp=? and pf8.codfilial=? ");
								
			ps = con.prepareStatement(sql.toString());
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE8" ) );
						
			rs = ps.executeQuery();

			if ( rs.next() ) {
				prefere.put( "codtiporecmerc", rs.getInt( "codtiporecmerc" ) );				
			}
		
			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void buscaTransp(String placa) {

		StringBuilder sql = new StringBuilder();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			sql.append( "select tp.codtran from vdtransp tp ");
			sql.append( "where tp.codemp=? and tp.codfilial=? and tp.placatran=?");
								
			ps = con.prepareStatement(sql.toString());
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setString( 3, placa );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
			
				txtCodTran.setVlrInteger( rs.getInt( "codtran" ) );
				lcTran.carregaDados();
				
			}
		
			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	private void ajustaTabela() {

		tab.setRowHeight( 21 );
		
		tab.setTamColuna(40,0); 		
		tab.setColunaInvisivel(1);
		tab.setColunaInvisivel(2);
		tab.setTamColuna(250,4);
		tab.setColunaInvisivel(3);
		tab.setColunaInvisivel(5);
		
	}
	
	public void exec( int ticket, int tiporecmerc, FControleRecMerc tela_mae ) {
		
		try {
			lcCampos.edit();
			txtTicket.setVlrInteger( ticket );
			txtCodTipoRecMerc.setVlrInteger( tiporecmerc );
			lcCampos.carregaDados();
			
			setTelaMae( tela_mae );
						
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void adicListeners() {
		
		txtPlacaTran.addFocusListener( this );
		
		cbBairro.addComboBoxListener( this );
		
		lcCampos.addInsertListener( this );				
		lcCampos.addCarregaListener( this );
		lcDet.addPostListener( this );
		lcBairro.addCarregaListener( this );
		lcMunic.addCarregaListener( this );
		lcFor.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		
		btAdicBairro.addActionListener( this );
		btPesagem.addActionListener( this );
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this); 

		
	}	
	
	private boolean validaSequenciaProcesso() {
		boolean ret = false;
		
		try {
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private void montaListaCampos() {

		//* Tipo de Recebimento Cabeçalho		
		
		lcTipoRecMerc.add( new GuardaCampo( txtCodTipoRecMerc, "CodTipoRecMerc", "Cód.Tipo.Rec.", ListaCampos.DB_PK, false ) );		
		lcTipoRecMerc.add( new GuardaCampo( txtDescTipoRecMerc, "DescTipoRecMerc", "Descrição do tipo de recepção de mercadoria", ListaCampos.DB_SI, false ) );
		
		txtCodTipoRecMerc.setTabelaExterna( lcTipoRecMerc );
		txtCodTipoRecMerc.setNomeCampo( "CodTipoRecMerc" );
		txtCodTipoRecMerc.setFK( true );
		
		lcTipoRecMerc.setReadOnly( true );
		lcTipoRecMerc.montaSql( false, "TIPORECMERC", "EQ" );
		
		//* Produto (Cabeçalho)
		
		lcProdCab.add( new GuardaCampo( txtCodProdCab, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProdCab.add( new GuardaCampo( txtDescProdCab, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		
		txtCodProdCab.setTabelaExterna( lcProdCab );		
		txtCodProdCab.setNomeCampo( "CodProd" );
		txtCodProdCab.setFK( true );
		
		lcProdCab.setReadOnly( true );
		lcProdCab.montaSql( false, "PRODUTO", "EQ" );

		//* Fornecedor		
		
		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.For.", ListaCampos.DB_PK, false ) );		
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCNPJFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCodPais, "CodPais", "Cód.País", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtSiglaUF, "SiglaUF", "UF", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCodMun, "CodMunic", "Cód.Mun.", ListaCampos.DB_SI, false ) );
		
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );
		
		//* Transportadora
		
		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.For.", ListaCampos.DB_PK, false ) );		
		lcTran.add( new GuardaCampo( txtNomeTran, "NomeTran", "Nome da transportadora", ListaCampos.DB_SI, false ) );
		lcTran.add( new GuardaCampo( txtCNPJTran, "CnpjTran", "CNPJ", ListaCampos.DB_SI, false ) );
		
		txtCodTran.setTabelaExterna( lcTran );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );
		
		lcTran.setReadOnly( true );
		lcTran.montaSql( false, "TRANSP", "VD" );
		
		//* Produto (Detalhe)
		
		lcProdDet.add( new GuardaCampo( txtCodProdDet, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProdDet.add( new GuardaCampo( txtDescProdDet, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		
		txtCodProdDet.setTabelaExterna( lcProdDet );		
		txtCodProdDet.setNomeCampo( "CodProd" );
		txtCodProdDet.setFK( true );
		
		lcProdDet.setReadOnly( true );
		lcProdDet.montaSql( false, "PRODUTO", "EQ" );
		
		/***************
		 * MUNICIPIO *
		 **************/

		lcMunic.setUsaME( false );
		lcMunic.add( new GuardaCampo( txtCodMun, "CodMunic", "Cód.Muni", ListaCampos.DB_PK, false ) );
		lcMunic.add( new GuardaCampo( txtDescMun, "NomeMunic", "Nome Muni.", ListaCampos.DB_SI, false ) );
	
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setQueryCommit( false );
		lcMunic.setReadOnly( true );
		txtCodMun.setTabelaExterna( lcMunic );
		
		
		/***************
		 * BAIRRO      *
		 ***************/

		lcBairro.setUsaME( false );
		lcBairro.add( new GuardaCampo( txtCodBairro, "CodBairro", "Cód.Bairro", ListaCampos.DB_PK, true ) );
		lcBairro.add( new GuardaCampo( txtCodMun, "CodMunic", "Cód.Munic", ListaCampos.DB_PK, true ) );
		lcBairro.add( new GuardaCampo( txtSiglaUF, "SiglaUF", "Sigla.UF", ListaCampos.DB_PK, false ) );
		lcBairro.add( new GuardaCampo( txtCodPais, "CodPais", "Cód.País", ListaCampos.DB_PK, false ) );
		lcBairro.add( new GuardaCampo( txtNomeBairro, "NomeBairro", "Nome do Bairro", ListaCampos.DB_SI, false ) ); 
		
		lcBairro.setDinWhereAdic( "CODPAIS = #N", txtCodPais );		
		lcBairro.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcBairro.setDinWhereAdic( "CODMUNIC = #S", txtCodMun );
				
		lcBairro.montaSql( false, "BAIRRO", "SG" );
		lcBairro.setQueryCommit( false );
		lcBairro.setReadOnly( true );
		txtCodBairro.setTabelaExterna( lcBairro );

		/***************
		 * PROCESSOS   *
		 ***************/
		
		lcProc.add( new GuardaCampo( txtCodProcRecMerc, "CodProcRecMerc", "Cód.Proc.", ListaCampos.DB_PK, false ) );
		lcProc.add( new GuardaCampo( txtDescProcRecMerc, "DescProcRecMerc", "Descrição do processo", ListaCampos.DB_SI, false ) );
		
		lcProc.add( new GuardaCampo( txtCodTipoRecMercDet, "CodTipoRecMerc", "Cod.Tp.Rec.Merc.", ListaCampos.DB_SI, false ) );

		txtCodProcRecMerc.setTabelaExterna( lcProc );
		txtCodProcRecMerc.setNomeCampo( "CodProcRecMerc" );
		txtCodProcRecMerc.setFK( true );
		
		lcProc.setReadOnly( true );
		lcProc.montaSql( false, "PROCRECMERC", "EQ" );
		
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}
		else if (evt.getSource() == btImp) {
			imprimir(false);
		}
		else if ( evt.getSource() == btAdicBairro ) {
			
			FBairro bairro = new FBairro( true );
			
			try {

				bairro.setConexao( con );
				
				bairro.setCodPais( txtCodPais.getVlrInteger() );
				bairro.setSiglaUF( txtSiglaUF.getVlrString() );
				bairro.setCodMunic( txtCodMun.getVlrString() );
				
				Aplicativo.telaPrincipal.criatela( "Recepção de mercadorias", bairro, con );
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if ( evt.getSource() == btPesagem ) {
			capturaAmostra();
		}
		
		super.actionPerformed(evt);
	}
	
	private void carregaStatus() {
		
		if ( "PE".equals( txtStatus.getVlrString() ) ) {
			lbStatus.setText( "PENDENTE" );
			lbStatus.setBackground( Color.ORANGE );
			lbStatus.setVisible( true );
		}
		else if ( "E1".equals( txtStatus.getVlrString() ) ) {
			lbStatus.setText( "PESAGEM 1" );
			lbStatus.setBackground( Color.BLUE );
			lbStatus.setVisible( true );
		}
		else if ( "E1".equals( txtStatus.getVlrString() ) ) {
			lbStatus.setText( "PESAGEM 2" );
			lbStatus.setBackground( Color.BLUE );
			lbStatus.setVisible( true );
		}		
		else if ( "FN".equals( txtStatus.getVlrString() ) ) {
			lbStatus.setText( "FINALIZADO" );
			lbStatus.setBackground( new Color( 45, 190, 60 ) );
			lbStatus.setVisible( true );
		}
		else if ( "NE".equals( txtStatus.getVlrString() ) ) {
			lbStatus.setText( "NOTA EMITIDA" );
			lbStatus.setBackground( Color.RED );
			lbStatus.setVisible( true );
		}
		
		else {
			lbStatus.setForeground( Color.WHITE );
			lbStatus.setBackground( Color.GRAY );
			lbStatus.setText( "INDEFINIDO" );
			lbStatus.setVisible( true );
		}	
		
	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relatório de tipo de recebimento de mercadorias");

		String sSQL = "SELECT CODTIPORECMERC,DESCTIPORECMERC FROM EQTIPORECMERC "
					+ "WHERE CODEMP=? AND CODFILIAL=? ORDER BY 1 ";

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sSQL);
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQTIPORECMERC" ) );
			
			rs = ps.executeQuery();
			imp.limpaPags();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow()+0,0,""+imp.normal());
					imp.say(imp.pRow()+0,0,"");
					imp.say(imp.pRow()+0,2,"Código");
					imp.say(imp.pRow()+0,25,"Descrição");
					imp.say(imp.pRow()+1,0,""+imp.normal());
					imp.say(imp.pRow()+0,0,Funcoes.replicate("-",79));
				}
				imp.say(imp.pRow()+1,0,""+imp.normal());
				imp.say(imp.pRow()+0,2,rs.getString("CodTipoRecMerc"));
				imp.say(imp.pRow()+0,25,rs.getString("DescTipoRecMerc"));
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow()+1,0,""+imp.normal());
			imp.say(imp.pRow()+0,0,Funcoes.replicate("=",79));
			imp.eject();

			imp.fechaGravacao();

			//      rs.close();
			//      ps.close();
			con.commit();
			
		}  
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de tipos de recebimento de mercadorias!\n"+err.getMessage(),true,con,err);      
		}

		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
	
	public void setConexao(DbConnection cn) {
		
		super.setConexao(cn);
		
		lcProdCab.setConexao( cn );
		lcProdDet.setConexao( cn );
		lcTran.setConexao( cn );
		lcFor.setConexao( cn );
		lcTipoRecMerc.setConexao( cn );
		lcMunic.setConexao( cn );
		lcBairro.setConexao( cn );
		lcProc.setConexao( cn );
		
		buscaPrefere();	
		
		if(novo) {
			lcCampos.insert( true );
		}

	}

	private void capturaAmostra() {
		DLPesagem dl = null;
				
		try {

			dl = new DLPesagem( this );

			dl.setConexao( con );
			dl.setVisible( true );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if ( dl.OK ) {
			txtPeso.setVlrBigDecimal( dl.getPeso() );
			txtDataPesagem.setVlrDate( dl.getData() );
			txtHoraPesagem.setVlrString( dl.getHora() );	
			lcDet.edit();
			
		}
		
		dl.dispose();
	}
	
	private void limpaAmostra() {
		txtPeso.setVlrBigDecimal( new BigDecimal( 0 ) );
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
			
			ps.setInt( 1, lcDet.getCodEmp());	  	
			ps.setInt( 2, lcDet.getCodFilial());
			ps.setInt( 3, txtTicket.getVlrInteger() );
			ps.setInt( 4, txtCodItRecMerc.getVlrInteger() );
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				codamostragem = rs.getInt( 1 );
			}
			
			codamostragem++;
			
			sql = new StringBuilder();
			
			sql.append( "insert into eqrecamostragem ");
			sql.append( "(codemp,codfilial,ticket,coditrecmerc,codamostragem,pesoamost,dataamost,horaamost)" );
			sql.append( "values(?, ?, ?, ?, ?, ?, ?, ?)" );
			
			ps = con.prepareStatement(sql.toString());
			
			ps.setInt( 1, lcDet.getCodEmp());	  	
			ps.setInt( 2, lcDet.getCodFilial());
			ps.setInt( 3, txtTicket.getVlrInteger() );
			ps.setInt( 4, txtCodItRecMerc.getVlrInteger() );
			ps.setInt( 5, codamostragem );
			ps.setBigDecimal( 6, txtPeso.getVlrBigDecimal() );
			ps.setDate( 7, Funcoes.dateToSQLDate( txtDataPesagem.getVlrDate()) );
			ps.setTime( 8, Funcoes.strTimeTosqlTime( txtHoraPesagem.getVlrString()) );
			
			ps.execute();			
			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void focusGained( FocusEvent e ) {

		// TODO Auto-generated method stub
		
	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource() == txtPlacaTran ) {
			
			if( txtCodTran.getVlrInteger() == 0 ) {
				buscaTransp( txtPlacaTran.getVlrString() );
			}
			
		}
		
	}
	
	private void montaComboBairro() {

		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "select codbairro, nomebairro " );
			sql.append( "from sgbairro " );
			sql.append( "where codpais=? and siglauf=? and codmunic=? " );
			sql.append( "order by nomebairro" );
			sql.append( "" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, txtCodPais.getVlrInteger() );
			ps.setString( 2, txtSiglaUF.getVlrString() );
			ps.setString( 3, txtCodMun.getVlrString() );
			
			ResultSet rs = ps.executeQuery();
			
			vValsBairro.clear();
			vLabsBairro.clear();
			
			while ( rs.next() ) {
				vValsBairro.addElement( rs.getInt( "CodBairro" ) );
				vLabsBairro.addElement( rs.getString( "NomeBairro" ) );
			}
			
			cbBairro.setItens( vLabsBairro, vValsBairro );
			
			rs.close();
			ps.close();
			
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar os bairros!\n" + e.getMessage(), true, con, e );
		} 
	}

	public void valorAlterado( JComboBoxEvent evt ) {
		if ( evt.getComboBoxPad() == cbBairro ) {
			if(txtCodBairro.getVlrInteger()!=cbBairro.getVlrInteger()) {
				txtCodBairro.setVlrInteger( cbBairro.getVlrInteger() );
			//	lcBairro.carregaDados();
			}
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if( cevt.getListaCampos() == lcFor ) {
			lcMunic.carregaDados();
		}		
		else if( cevt.getListaCampos() == lcBairro ) {
			if(txtCodBairro.getVlrInteger()!=cbBairro.getVlrInteger()) {
				cbBairro.setVlrInteger( txtCodBairro.getVlrInteger() );
				lcCampos.edit();
			}
		}		
		else if (cevt.getListaCampos() == lcMunic) {
			montaComboBairro();
		}
		else if( cevt.getListaCampos() == lcCampos ) {
			carregaStatus();
		}
		else if( cevt.getListaCampos() == lcDet ) {
			limpaAmostra();
			montaTabPesagem();
		}

		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub
		
	}
	
	public void beforePost( PostEvent pevt ) {
		super.beforePost( pevt );
		
		if ( pevt.getListaCampos() == lcCampos ) {
			carregaTipoRec();
			if("".equals( txtStatus.getVlrString())) {
				txtStatus.setVlrString( "PE" );
			}
		}
		else if ( pevt.getListaCampos() == lcDet ) {
			if(txtPeso.getVlrBigDecimal().floatValue()>0 && txtDataPesagem.getVlrDate()!=null && txtHoraPesagem.getVlrString() !=null) {
				salvaAmostra();
			}
		}
	}

	public void beforeInsert( InsertEvent ievt ) {

		// TODO Auto-generated method stub
		
	}

	public void afterInsert( InsertEvent ievt ) {
		if(ievt.getListaCampos() == lcCampos) {
			carregaTipoRec();
		}
		
	}
	
	public void setTelaMae(FControleRecMerc tela_mae) {
		this.tela_mae = tela_mae;
	}
	
    public void dispose() {     
        super.dispose();
        tela_mae.montaGrid();
    }

	
	
}
