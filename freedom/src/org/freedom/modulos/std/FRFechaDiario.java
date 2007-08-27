package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FRelatorio;


public class FRFechaDiario extends FRelatorio{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodCaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescCaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private ListaCampos lcCaixa = new ListaCampos( this );
	
	public FRFechaDiario(){
		
		setTitulo( "Fechamento diário" );
		setAtribos( 80, 80, 340, 170 );
		
		montaTela();
		montaListaCampos();
	}
	
	public void montaTela(){
		
		adic( new JLabelPad("Data:"), 7, 47, 50, 20 );
		adic( txtData, 7, 67, 110, 20 );
		
		adic( new JLabelPad( "Nº caixa" ), 7, 7, 80, 20 );		
		adic( txtCodCaixa, 7, 27, 70, 20 );
		adic( new JLabelPad( "Descrição do caixa" ), 80, 7, 200, 20 );
		adic( txtDescCaixa, 80, 27, 223, 20 );

		GregorianCalendar cPeriodo = new GregorianCalendar();
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) );
		txtData.setVlrDate( cPeriodo.getTime() );
	}
	
	public void montaListaCampos(){
		
		lcCaixa.add( new GuardaCampo( txtCodCaixa, "CodCaixa", "Cód.caixa", ListaCampos.DB_PK, false ) );
		lcCaixa.add( new GuardaCampo( txtDescCaixa, "DescCaixa", "Descrição do caixa", ListaCampos.DB_SI, false ) );
		lcCaixa.montaSql( false, "CAIXA", "PV" );
		lcCaixa.setReadOnly( true );
		txtCodCaixa.setTabelaExterna( lcCaixa );
		txtCodCaixa.setFK( true );
		txtCodCaixa.setNomeCampo( "CodCaixa" );
		
	}
	
	public void setConexao( Connection cn ){
		
		super.setConexao( cn );
		lcCaixa.setConexao( cn );
		
	}

	@ Override
	public void imprimir( boolean b ) {
		
		ImprimeOS imp = new ImprimeOS( "", con );
		
		StringBuilder sSQL = new StringBuilder();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			
			sSQL.append( "" );
			
			imp.setTitulo( "Fechamento diário" );
			imp.addSubTitulo( "FECHAMENTO DIÀRIO" );
		
		} catch ( Exception e ) {
			
		}
		
	}
}
