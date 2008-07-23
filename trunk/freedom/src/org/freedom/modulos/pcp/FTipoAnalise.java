package org.freedom.modulos.pcp;

import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;


public class FTipoAnalise extends FDados {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodTpAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtDescTpAnalise = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextAreaPad txaObsTpAnalise = new JTextAreaPad();
	
	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private JTextFieldFK txtDescUnid = new JTextFieldFK( JTextFieldPad.TP_STRING, 15, 0 );
	
	private JTextFieldPad txtMetodo = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private Vector<String> vLabs1 = new Vector<String>();
	
	private Vector<String> vVals1 = new Vector<String>();
	
	private JRadioGroup<?, ?> rgTipo = null;

	ListaCampos lcUnid = new ListaCampos( this, "UD" );

	public FTipoAnalise(){
	
		setTitulo( "Tipos de Analise" );
		setAtribos( 50, 50, 350, 330 );
		montaListaCampos();
		montaTela();
		
	} 
	
	private void montaTela(){
		
		vLabs1.addElement("Mínimo e Máximo");
 		vLabs1.addElement("Descritivo"); 
 		vVals1.addElement("MM");
 		vVals1.addElement("DT");
		    
 		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
 		rgTipo.setVlrString("MM");
 		
 		adicCampo( txtCodTpAnalise, 7, 20, 70, 20 , "CodTpAnalise", "Cód.Tp.An.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTpAnalise, 80, 20, 245, 20, "DescTpAnalise", "Descrição do tipo de analise", ListaCampos.DB_SI, true );
		adicCampo( txtCodUnid, 7, 65, 70, 20, "CodUnid", "Cód.Unid", ListaCampos.DB_FK, txtDescUnid, false );
		adicDescFK( txtDescUnid, 80, 65, 245, 20, "DescUnid", "Descrição da Unidade" );
		adicCampo( txtMetodo, 7, 105, 320, 20, "metodo", "Método Analítico", ListaCampos.DB_SI, true );
		adicDB( txaObsTpAnalise, 7, 200, 320, 50, "ObsTpAnalise", "Observação", false );
		adicDB( rgTipo, 7, 150, 320, 30, "TipoExpec", "Tipo de expecificação", true );
		setListaCampos( true, "TIPOANALISE", "PP" );
		
	}
	
	private void montaListaCampos(){
		
		lcUnid.add( new GuardaCampo( txtCodUnid, "CodUnid", "Cód.Unidade", ListaCampos.DB_PK, null, false ) );
		lcUnid.add( new GuardaCampo( txtDescUnid, "DescUnid", "Descrição da unidade", ListaCampos.DB_SI, false ) );
		lcUnid.montaSql( false, "UNIDADE", "EQ" );
		lcUnid.setReadOnly( true );
		lcUnid.setQueryCommit( false );
		txtCodUnid.setListaCampos( lcUnid );
		txtCodUnid.setTabelaExterna( lcUnid );
	}
	public void setConexao( Connection cn ){
		
		super.setConexao( cn );
		lcUnid.setConexao( cn );
	}
}
