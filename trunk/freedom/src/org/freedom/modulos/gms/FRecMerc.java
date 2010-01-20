package org.freedom.modulos.gms;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

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
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
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

	private Vector<Integer> vValsBairro = new Vector<Integer>();
	private Vector<String> vLabsBairro = new Vector<String>();

	// *** Campos (Cabeçalho)
	
	private JTextFieldPad txtTicket = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	private JTextFieldPad txtPlacaTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
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

	
	private JComboBoxPad cbBairro = new JComboBoxPad( vLabsBairro, vValsBairro, JComboBoxPad.TP_INTEGER, 8, 0 );
		
	// *** Campos (Detalhe)
	
	private JTextFieldPad txtCodItRecMerc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodProdDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private JTextFieldFK txtDescProdDet = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// *** Paineis
	
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();

	// *** Lista Campos	
	
	private ListaCampos lcTran = new ListaCampos( this, "TN" );
	private ListaCampos lcFor = new ListaCampos( this, "FR" );
	private ListaCampos lcProdCab = new ListaCampos( this, "PD" );
	private ListaCampos lcProdDet = new ListaCampos( this, "PD" );
	private ListaCampos lcTipoRecMerc = new ListaCampos( this, "TR" );
	private ListaCampos lcBairro = new ListaCampos( this );
	private ListaCampos lcMunic = new ListaCampos( this );
	
	// *** Labels
	
	private JLabelPad lbBairro = new JLabelPad("Bairro");
	
	// *** Botões
	
	private JButtonPad btNovoBairro = new JButtonPad( Icone.novo( "btAdic2.gif" ) );
	
	public FRecMerc (boolean novo) {
		
		super();
		
		this.novo = novo;
		
		setTitulo( "Recepção de mercadorias" );
		setAtribos( 50, 50, 700, 480);

		montaListaCampos();
		montaTela();
		montaTab();
		ajustaTabela();
		adicListeners();
		configuraCampos();
			
		setImprimir(true);
		
	}
	
	private void configuraCampos() {
		txtPlacaTran.setUpperCase( true );
		txtPlacaTran.setMascara( JTextFieldPad.MC_PLACA );
		
		txtCodMun.setAtivo( false );
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
	
	private void montaTela() {
		
		// *** Cabeçalho *** //
		
		setAltCab( 210 );
		
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		 
		adicCampo( txtTicket, 7, 20, 70, 20, "Ticket", "Ticket", ListaCampos.DB_PK, true);
		adicCampo( txtPlacaTran, 80, 20, 70, 20, "PlacaVeiculo", "Placa", ListaCampos.DB_SI, true);

		adicCampo( txtCodTipoRecMerc, 153, 20, 70, 20, "CodTipoRecMerc", "Cod.Tp.Rec.", ListaCampos.DB_FK, txtDescProdCab, true );
		adicDescFK( txtDescTipoRecMerc, 226, 20, 370, 20, "DescTipoRecMerc", "Descrição do tipo de recebimento" ) ;
		
		adicCampo( txtCodProdCab, 7, 60, 70, 20, "CodProd", "Cod.Prod.", ListaCampos.DB_FK, txtDescProdCab, true );
		adicDescFK( txtDescProdCab, 80, 60, 370, 20, "DescProd", "Descrição do Produto" );
		
		adicCampo( txtCodTran, 7, 100, 70, 20, "CodTran", "Cod.Tran.", ListaCampos.DB_FK, txtNomeTran, true );
		adicDescFK( txtNomeTran, 80, 100, 370, 20, "NomeTran", "Nome da transportadora" );

		adicCampo( txtCodFor, 7, 140, 70, 20, "CodFor", "Cod.For.", ListaCampos.DB_FK, txtRazFor, true );
		adicDescFK( txtRazFor, 80, 140, 170, 20, "RazFor", "Razão social do fornecedor" );
		
		adicCampo( txtCodPais, 253, 140, 30, 20, "CodPais", "País", ListaCampos.DB_SI, false );
		adicCampo( txtSiglaUF, 286, 140, 25, 20, "SiglaUF", "UF", ListaCampos.DB_SI, false );
		adicCampo( txtCodMun, 314, 140, 60, 20, "CodMunic", "Cód.Mun.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescMun, 377, 140, 120, 20, "DescMunic", "Nome do município" );
		
		adicCampoInvisivel( txtCodBairro, "CodBairro", "Cód.Bairro", ListaCampos.DB_FK, false );
		
		pinCab.adic( lbBairro, 500, 120, 100, 20 );
		pinCab.adic( cbBairro, 500, 140, 100, 20 );
		
		adic( btNovoBairro, 603, 100, 20, 20 );
		
		setListaCampos( true, "RECMERC", "EQ");
		lcCampos.setQueryInsert( true );

		// *** Campos do detalhe
		
		setAltDet(120);
		
		setPainel( pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

		adicCampo(txtCodItRecMerc, 7, 20, 40, 20, "CodItRecMerc","Cod.It.",ListaCampos.DB_PK, true);

		adicCampo( txtCodProdDet, 50, 20, 70, 20,"CodProd","Cod.Prod.",ListaCampos.DB_FK, txtDescProdDet, true );
		adicDescFK( txtDescProdDet, 123, 20, 200, 20, "DescProd", "Descrição do Produto" );		
		
		setListaCampos( true, "ITRECMERC", "EQ");		
		lcDet.setQueryInsert( true );
		
	}
	
	private void ajustaTabela() {

		tab.setTamColuna(40,0); 
		tab.setTamColuna(60,1);
		tab.setTamColuna(200,2);

	}
	
	private void adicListeners() {
		
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this); 
		
		txtPlacaTran.addFocusListener( this );
		
		cbBairro.addComboBoxListener( this );
		
		lcCampos.addInsertListener( this );
		lcBairro.addCarregaListener( this );
		lcMunic.addCarregaListener( this );
		
		btNovoBairro.addActionListener( this );
		
	}	
	
	private void montaListaCampos() {

		//* Tipo de Recebimento		
		
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
		lcBairro.add( new GuardaCampo( txtCodMun, "CodMunic", "Cód.Munic", ListaCampos.DB_PK, false ) );
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

	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}
		else if (evt.getSource() == btImp) {
			imprimir(false);
		}
		else if ( evt.getSource() == btNovoBairro ) {
			
			
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
		super.actionPerformed(evt);
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
		
		buscaPrefere();	
		
		if(novo) {
			lcCampos.insert( true );
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
			
//			vValsBairro.addElement( -1 );
//			vLabsBairro.addElement( "<Selecione>" );
			
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

		if( cevt.getListaCampos() == lcBairro ) {
			if(txtCodBairro.getVlrInteger()!=cbBairro.getVlrInteger()) {
				cbBairro.setVlrInteger( txtCodBairro.getVlrInteger() );
				lcCampos.edit();
			}
		}
		else if (cevt.getListaCampos() == lcMunic) {
			montaComboBairro();
		}
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub
		
	}
	
	public void beforePost( PostEvent pevt ) {
		super.beforePost( pevt );
		
		if ( pevt.getListaCampos() == lcCampos ) {
			carregaTipoRec();	
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

	
	
}
