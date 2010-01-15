package org.freedom.modulos.gms;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FRecMerc extends FDetalhe  {
	
	// *** Variáveis estáticas
	
	private static final long serialVersionUID = 1L;

	// *** Campos (Cabeçalho)
	
	private JTextFieldPad txtTicket = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	private JTextFieldPad txtPlacaVeiculo = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
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

	private JTextFieldPad txtBairro = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtNomeBairro = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );
	
	private JTextFieldFK txtCodPais = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtSiglaUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	private JTextFieldFK txtCodMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 8, 0 );
		
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

	public FRecMerc () {
		
		super();

		setTitulo( "Recepção de mercadorias" );
		setAtribos( 50, 50, 700, 480);

		montaListaCampos();
		montaTela();
		montaTab();
		ajustaTabela();
		
		setImprimir(true);		

	}

	private void buscaTransp(String placa) {
		StringBuilder sql = new StringBuilder();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			sql.append( "select first 1 tp.codtran from vdtransp tp ");
			sql.append( "where tp.codemp=? and tp.codfilial=? and tp.placatran=?");
								
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTRANSP" ) );

			if ( rs.next() ) {
				
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
		adicCampo( txtPlacaVeiculo, 80, 20, 70, 20, "PlacaVeiculo", "Placa", ListaCampos.DB_SI, true);

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
		adicCampo( txtCodMun, 314, 140, 60, 20, "CodMunic", "Município", ListaCampos.DB_SI, false );
		
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

	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}
		else if (evt.getSource() == btImp) 
			imprimir(false);
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

	}

	
}
