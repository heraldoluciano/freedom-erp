package org.freedom.modulos.gms;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.FDetalhe;


public class FRecMerc extends FDetalhe  {
	
	private static final long serialVersionUID = 1L;

	// *** Campos
	
	private JTextFieldPad txtTicket = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtPlacaVeiculo = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);

	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);	
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	
	private JTextFieldPad txtCodTran = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);	
	private JTextFieldFK txtNomeTran = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	
	private JTextFieldPad txtBairCarga = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeBairro = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );
	
	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	//
	
	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private ListaCampos lcFor = new ListaCampos( this );


	public FRecMerc () {
		
		super();

		setTitulo("Recepção de mercadorias");
		setAtribos( 50, 50, 470, 350);

		setAltCab(90);
		
		pinCab = new JPanelPad(420,90);
		
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);

/*		vLabsTipo.addElement( "Pesagem inicial" );
		vLabsTipo.addElement( "Tiragem de renda" );
		vLabsTipo.addElement( "Pesagem final" );
		
		vValsTipo.addElement( "PI" );
		vValsTipo.addElement( "TR" );
		vValsTipo.addElement( "PF" );*/
		
//		cbTipoProcRecMerc = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_STRING, 2, 0 );
		
		adicCampo(txtTicket, 7, 20, 70, 20,"CodTipoRecMerc","Cód.Tp.Rec.",ListaCampos.DB_PK,true);
//		adicCampo(txtDescTipoRecMerc, 80, 20, 355, 20,"DescTipoRecMerc","Descrição do tipo recepção de mercadorias",ListaCampos.DB_SI,true);
		
		setListaCampos( true, "TIPORECMERC", "EQ");

		setAltDet(60);
		setPainel( pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

//***		adicCampo(txtCodProcRecMerc, 7, 20, 40, 20, "CodProcRecMerc","Cód.Et.",ListaCampos.DB_PK, true);
//		adicCampo(txtDescProcRecMerc, 50, 20, 240, 20, "DescProcRecMerc","Descrição do processo de recepção", ListaCampos.DB_SI, true);
//		adicDB( cbTipoProcRecMerc, 293, 20, 140, 24, "TipoProcRecMerc","Tipo de processo",true);		
		
		setListaCampos( true, "PROCRECMERC", "EQ");

		montaTab();    
		tab.setTamColuna(70,0); 
		tab.setTamColuna(300,1);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);  
		setImprimir(true);

	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}

	public void setConexao(DbConnection cn) {
		super.setConexao(cn);

	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relatório de tipo de recebimento de mercadorias");

		String sSQL = "SELECT CODTIPORECMERC,DESCTIPORECMERC FROM EQTIPORECMERC ORDER BY 1";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sSQL);
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
}
