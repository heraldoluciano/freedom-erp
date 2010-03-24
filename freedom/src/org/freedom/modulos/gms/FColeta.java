package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
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
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.objetos.RecMerc;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FColeta extends FDetalhe implements FocusListener, JComboBoxListener, CarregaListener, PostListener, InsertListener {

	// *** Constantes

	private static final long serialVersionUID = 1L;

	// *** Variaveis

	private HashMap<String, Object> prefere = null;

	private boolean novo = true;

	private Vector<String> vValsFrete = new Vector<String>();

	private Vector<String> vLabsFrete = new Vector<String>();

	// *** Campos (Cabeçalho)

	private JTextFieldPad txtTicket = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodTipoRecMercDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoRecMerc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtCNPJTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtCodPais = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtSiglaUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodMun = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtStatusItRecMerc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JRadioGroup<String, String> rgFrete = null;
	
	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtDtEnt = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtPrevRet = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDocRecMerc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodProcRecMerc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtDescProcRecMerc = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTipoProcRecMerc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );


	// *** Campos (Detalhe)

	private JTextFieldPad txtCodItRecMerc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdDet = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	// *** Paineis

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();// JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinDetGrid = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );

	// *** Lista Campos

	private ListaCampos lcTran = new ListaCampos( this, "TN" );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcProdDet = new ListaCampos( this, "PD" );

	private ListaCampos lcVendedor = new ListaCampos( this, "VD" );
	
	private ListaCampos lcTipoRecMerc = new ListaCampos( this, "TR" );
	
	private ListaCampos lcProc = new ListaCampos( this, "TP" );

	// *** Labels

	private JLabelPad lbStatus = new JLabelPad();

	public FColeta( ) {

		super();

		setTitulo( "Coleta de materiais" );
		setAtribos( 50, 50, 653, 480 );

		configuraCampos();
		montaListaCampos();
		montaTela();
		montaTab();
		ajustaTabela();
		adicListeners();

		setImprimir( true );

	}

	private void configuraCampos() {

		txtCodMun.setAtivo( false );

		vValsFrete.addElement( "C" );
		vValsFrete.addElement( "F" );
		vLabsFrete.addElement( "CIF" );
		vLabsFrete.addElement( "FOB" );

		rgFrete = new JRadioGroup<String, String>( 1, 2, vLabsFrete, vValsFrete, -4 );

		lbStatus = RecMerc.getLabelStatus( null );

		lbStatus.setText( "NÃO SALVO" );
		lbStatus.setVisible( true );
		
		txtDtEnt.setVlrDate( new Date() );
		txtDtPrevRet.setVlrDate( new Date() );

	}

	private void montaTela() {

		pnMaster.remove( spTab );
		pnMaster.add( pinDetGrid, BorderLayout.CENTER );

		pinDetGrid.add( spTab );
//		pinDetGrid.add( new JScrollPane( tabPesagem ) );

		montaCabecalho();
		montaDetalhe();

	}

	private void montaCabecalho() {

		setAltCab( 210 );

		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );

		adicCampo( txtTicket, 7, 20, 70, 20, "Ticket", "Ticket", ListaCampos.DB_PK, true );

		adicCampoInvisivel( txtCodTipoRecMerc, "CodTipoRecMerc", "Cód.T.", ListaCampos.DB_FK, true );

		adicCampo( txtCodCli, 80, 20, 70, 20, "CodCli", "Cod.Cli.", ListaCampos.DB_FK, txtRazCli, true );
		adicDescFK( txtRazCli, 153, 20, 237, 20, "RazCli", "Razão social do cliente" );
		
		adicCampo( txtCodVend, 7, 60, 80, 20, "CodVend", "Cód.comis.", ListaCampos.DB_FK, txtNomeVend, true );
		adicDescFK( txtNomeVend, 90, 60, 197, 20, "NomeVend", "Nome do comissionado" );
		
		adicCampo( txtDtEnt, 7, 100, 70, 20, "DtEnt", "Dt.Entrada", ListaCampos.DB_SI, true );
		adicCampo( txtDtPrevRet, 80, 100, 70, 20, "DtPrevRet", "Dt.Prevista", ListaCampos.DB_SI, true );
		adicCampo( txtDocRecMerc, 153, 100, 70, 20, "DocRecMerc", "Documento", ListaCampos.DB_SI, true );

		adicCampoInvisivel( txtStatus, "Status", "Status", ListaCampos.DB_SI, false );

		adic( lbStatus, 500, 20, 123, 60 );

		setListaCampos( true, "RECMERC", "EQ" );
		lcCampos.setQueryInsert( true );

	}

	private void montaDetalhe() {

		setAltDet( 70 );

		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtCodItRecMerc, 7, 20, 40, 20, "CodItRecMerc", "Seq.", ListaCampos.DB_PK, true );
		adicCampo( txtCodProdDet, 50, 20, 50, 20, "CodProd", "Cód.Pd.", ListaCampos.DB_FK, txtDescProdDet, true );
		adicDescFK( txtDescProdDet, 103, 20, 203, 20, "DescProd", "Descrição do Produto" );
		
		adicCampoInvisivel( txtCodProcRecMerc, "CodProcRecMerc", "Cod.Proc.", ListaCampos.DB_FK, txtDescProcRecMerc, true );
		adicDescFKInvisivel( txtDescProcRecMerc, "DescProcRecMerc", "Descrição do processo" );
		adicCampoInvisivel( txtCodTipoRecMercDet, "CodTipoRecMerc", "Cod.Tp.Rec.Merc", ListaCampos.DB_SI, true );

		txtStatusItRecMerc.setSoLeitura( true );
		adicCampoInvisivel( txtStatusItRecMerc, "StatusItRecMerc", "Status", ListaCampos.DB_SI, false );

		setListaCampos( true, "ITRECMERC", "EQ" );
		lcDet.setQueryInsert( true );

		pinDetGrid.setBackground( Color.RED );

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

			sql.append( "select coalesce(pf8.codtiporecmerccm,0) codtiporecmerc " );
			sql.append( "from sgprefere8 pf8 " );
			sql.append( "where pf8.codemp=? and pf8.codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE8" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				prefere.put( "codtiporecmerc", rs.getInt( "codtiporecmerc" ) );
			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void ajustaTabela() {

		tab.setRowHeight( 21 );

/*		tab.setTamColuna( 30, 0 );
		tab.setColunaInvisivel( 1 );
		tab.setColunaInvisivel( 2 );
		tab.setTamColuna( 140, 4 );
		tab.setColunaInvisivel( 3 );
		tab.setColunaInvisivel( 5 );
		tab.setTamColuna( 70, 6 );
		tab.setTamColuna( 70, 7 );
		tab.setColunaInvisivel( 8 );
*/
	}

	private void adicListeners() {

		lcCampos.addInsertListener( this );
		lcCampos.addCarregaListener( this );
		lcDet.addPostListener( this );
		lcCli.addCarregaListener( this );
		lcDet.addCarregaListener( this );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

	}

	private void montaListaCampos() {

		// * Tipo de Recebimento Cabeçalho

		lcTipoRecMerc.add( new GuardaCampo( txtCodTipoRecMerc, "CodTipoRecMerc", "Cód.Tipo.Rec.", ListaCampos.DB_PK, false ) );

		txtCodTipoRecMerc.setTabelaExterna( lcTipoRecMerc );
		txtCodTipoRecMerc.setNomeCampo( "CodTipoRecMerc" );
		txtCodTipoRecMerc.setFK( true );

		lcTipoRecMerc.setReadOnly( true );
		lcTipoRecMerc.montaSql( false, "TIPORECMERC", "EQ" );

		
		// * Cliente

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.Cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCNPJCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodPais, "CodPais", "Cód.País", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtSiglaUF, "SiglaUF", "UF", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodMun, "CodMunic", "Cód.Mun.", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comis.", ListaCampos.DB_SI, false ) );

		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );

		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

		// * Transportadora

		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.For.", ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtNomeTran, "NomeTran", "Nome da transportadora", ListaCampos.DB_SI, false ) );
		lcTran.add( new GuardaCampo( txtCNPJTran, "CnpjTran", "CNPJ", ListaCampos.DB_SI, false ) );

		txtCodTran.setTabelaExterna( lcTran );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );

		lcTran.setReadOnly( true );
		lcTran.montaSql( false, "TRANSP", "VD" );

		// * Produto (Detalhe)

		lcProdDet.add( new GuardaCampo( txtCodProdDet, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProdDet.add( new GuardaCampo( txtDescProdDet, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );

		txtCodProdDet.setTabelaExterna( lcProdDet );
		txtCodProdDet.setNomeCampo( "CodProd" );
		txtCodProdDet.setFK( true );

		lcProdDet.setReadOnly( true );
		lcProdDet.montaSql( false, "PRODUTO", "EQ" );

		// FK Vendedor
		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.Venda", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.setWhereAdic( "ATIVOCOMIS='S'" );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );
		
		/***************
		 * PROCESSOS *
		 ***************/

		lcProc.add( new GuardaCampo( txtCodProcRecMerc, "CodProcRecMerc", "Cód.Proc.", ListaCampos.DB_PK, false ) );
		lcProc.add( new GuardaCampo( txtDescProcRecMerc, "DescProcRecMerc", "Descrição do processo", ListaCampos.DB_SI, false ) );
		lcProc.add( new GuardaCampo( txtCodTipoRecMercDet, "CodTipoRecMerc", "Cod.Tp.Rec.Merc.", ListaCampos.DB_SI, false ) );
		lcProc.add( new GuardaCampo( txtTipoProcRecMerc, "TipoProcRecMerc", "Tp.Proc.Rec.Merc.", ListaCampos.DB_SI, false ) );

		txtCodProcRecMerc.setTabelaExterna( lcProc );
		txtCodProcRecMerc.setNomeCampo( "CodProcRecMerc" );
		txtCodProcRecMerc.setFK( true );

		lcProc.setReadOnly( true );
		lcProc.montaSql( false, "PROCRECMERC", "EQ" );



	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}

		super.actionPerformed( evt );

	}


	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );

		try {

			try {


			} catch ( Exception e ) {
				System.out.println( "Erro ao buscar primeira pesagem" );
			}

			imp.pulaLinha( 7, imp.comprimido() );

			imp.pulaLinha( 3, imp.comprimido() );

			imp.say( imp.pRow(), 70, "INTERNO.:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );


			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRODUTO:..........:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRODUTOR:.........:" );

			imp.say( imp.pRow(), 24, txtCodCli.getVlrString().trim() + " " + txtRazCli.getVlrString().trim() );

			imp.pulaLinha( 2, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRIMEIRA PESAGEM..:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );



			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "SEGUNDA PESAGEM...:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );


			imp.pulaLinha( 2, imp.comprimido() );

			imp.say( imp.pRow(), 3, "LIQUIDO...........:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );


			imp.pulaLinha( 2, imp.comprimido() );

			imp.say( imp.pRow(), 3, "Renda.............:" );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "Renda Classif.....:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

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

		lcProdDet.setConexao( cn );
		lcTran.setConexao( cn );
		lcCli.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcTipoRecMerc.setConexao( cn );
		lcProc.setConexao( cn );

		buscaPrefere();

		if ( novo ) {
			lcCampos.insert( true );
		}

	}

	public void focusGained( FocusEvent e ) {

		// TODO Auto-generated method stub

	}

	public void focusLost( FocusEvent e ) {


	}

	public void valorAlterado( JComboBoxEvent evt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCampos ) {
			lbStatus = RecMerc.getLabelStatus( txtStatus.getVlrString() );
		}

	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub

	}

	public void beforePost( PostEvent pevt ) {

		super.beforePost( pevt );

		if ( pevt.getListaCampos() == lcCampos ) {
			carregaTipoRec();
			if ( "".equals( txtStatus.getVlrString() ) ) {
				txtStatus.setVlrString( RecMerc.STATUS_PENDENTE.getValue() );
			}
		}
		else if ( pevt.getListaCampos() == lcDet ) {
			txtCodTipoRecMercDet.setVlrInteger( txtCodTipoRecMerc.getVlrInteger() );
			txtCodProcRecMerc.setVlrInteger( 1 );
		}
	}

	public void afterPost( PostEvent pevt ) {

		super.beforePost( pevt );

		if ( pevt.getListaCampos() == lcDet ) {
			lcCampos.carregaDados();
		}
	}

	public void beforeInsert( InsertEvent ievt ) {

		// TODO Auto-generated method stub

	}

	public void afterInsert( InsertEvent ievt ) {

		if ( ievt.getListaCampos() == lcCampos ) {
			carregaTipoRec();
		}

	}

}
