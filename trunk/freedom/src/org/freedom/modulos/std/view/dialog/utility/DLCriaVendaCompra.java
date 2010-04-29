package org.freedom.modulos.std.view.dialog.utility;

import java.awt.event.KeyEvent;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;
import org.freedom.library.swing.frame.Aplicativo;

public class DLCriaVendaCompra extends FDialogo {
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtNewCod = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldFK.TP_STRING, 40, 0);
	
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldFK.TP_STRING, 40, 0);
	
	private JTextFieldPad txtCodModNota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtDoc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldPad txtSerie = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
	
	private ListaCampos lcPlanoPag = new ListaCampos( this );
	
	private ListaCampos lcTipoMov = new ListaCampos( this );
	
	public DLCriaVendaCompra( boolean confirmacodigo, String tipo ) {
		
		setTitulo( "Confirmação" );
		
		String labeltipo = "";
		
		if( "E".equals( tipo )) { // Venda PDV/ECF
			setAtribos(235, 120);
			labeltipo = "um cupom";
			adic(new JLabelPad( "Deseja criar " + labeltipo + " agora?" ), 7, 15, 220, 20);
		}
		else if ("V".equals( tipo )) { // Venda STD
			setAtribos(235, 140);
			labeltipo = "uma venda";
		}
		else if ("C".equals( tipo )) { // Compra GMS 
			setAtribos( 400, 200 );
			labeltipo = "uma compra";
		}
		
		// Se for uma compra ou venda e deve ser confirmado o codigo
		if( ( "V".equals( tipo ) ) && confirmacodigo ) {
			
			adic(new JLabelPad( "Deseja criar " + labeltipo + " agora?" ), 7, 15, 220, 20);
			
			adic(new JLabelPad("Nº Pedido"), 7, 40, 80, 20);
			adic(txtNewCod, 87, 40, 120, 20);
		}
		
		//Se for uma compra
		if("C".equals( tipo )) {
			
			adic(new JLabelPad("Cod.Compra"), 7, 0, 80, 20);
			adic(txtNewCod, 7, 20, 80, 20);
			
			adic( new JLabelPad( "Cód.tp.mov." ), 90, 0, 80, 20);
			adic( txtCodTipoMov, 90, 20, 80, 20 );
			
			adic( new JLabelPad( "Descrição do tipo de movimento" ), 173, 0, 200, 20);
			adic( txtDescTipoMov, 173, 20, 200, 20 );
			
			adic( new JLabelPad( "Serie" ), 376, 0, 60, 20);
			adic(txtSerie, 376, 0, 60, 20);
			
			
			adic( new JLabelPad( "Nro.Doc." ), 459, 0, 80, 20 );
			adic( txtDoc, 459, 20, 80, 20);
			
			
			
			adic( new JLabelPad( "Cod.Plano.Pag" ), 7, 80, 80, 20);
			adic( txtCodPlanoPag, 7, 100, 80, 20 );
			
			adic( new JLabelPad( "Descrição do plano de pagamento" ), 90, 80, 200, 20);
			adic( txtDescPlanoPag, 90, 100, 200, 20 );
			
		}
		
		btOK.addKeyListener( this );
				
	}
	
	private void montaListaCampos() {
		
		//Lista campos para plano de pagamento
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.setWhereAdic( "ATIVOPLANOPAG='S' AND CVPLANOPAG IN ('C','A')" );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		
		//Lista campos para o tipo de movimento
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtCodModNota, "CodModNota", "Código do modelo de nota", ListaCampos.DB_FK, false ) );
		lcTipoMov.setWhereAdic( "((ESTIPOMOV = 'E') AND" + " ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU " + "WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND " + "TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS=" + Aplicativo.iCodEmp
				+ " AND TU.CODFILIALUS=" + ListaCampos.getMasterFilial( "SGUSUARIO" ) + " AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) )" );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );

	}
	
	public void setNewCodigo( int arg ) {
		txtNewCod.setVlrInteger( arg );
	}
	
	public int getNewCodigo() {
		return txtNewCod.getVlrInteger().intValue();
	}
	
	public void keyPressed(KeyEvent kevt) {
		
		if(kevt.getSource() == btOK) {
			btOK.doClick();
		}
		
		super.keyPressed(kevt);
		
	}
	
	
	public Integer getCodplanopag() {
	
		return txtCodPlanoPag.getVlrInteger();
	}

	
	public void setCodplanopag( Integer codplanopag ) {
	
		txtCodPlanoPag.setVlrInteger( codplanopag );
	}
	
	public String getSerie() {
		
		return txtSerie.getVlrString();
	}

	
	public void setSerie( String serie ) {
	
		txtSerie.setVlrString( serie );
	}

	public void setConexao(DbConnection cn) {
		
		lcPlanoPag.setConexao( cn );
		lcTipoMov.setConexao( cn );
		
		montaListaCampos();
		
		lcPlanoPag.carregaDados();
		lcTipoMov.carregaDados();
		
	}
	
	public Integer getDoc() {
		return txtDoc.getVlrInteger();
	}
	
	public Integer getCodTipoMov() {
		return txtCodTipoMov.getVlrInteger();
	}
	
	public void setCodTipoMov( Integer codtipomov ) {
		txtCodTipoMov.setVlrInteger(codtipomov);
	}


}
