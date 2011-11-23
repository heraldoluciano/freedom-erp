package org.freedom.modulos.fnc.view.frame.report;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FRelatorio;


public class FRFluxoCaixaPeriodo extends FRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	private final JTextFieldPad txtDataIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDataFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	//RadioGroup
	private Vector<String> vLabsTipo = new Vector<String>();
	
	private Vector<String> vValsTipo = new Vector<String>();
	
	private JRadioGroup	<String, String> rgTipoRel = null;
	
	private Vector<String> vLabsGraf = new Vector<String>();
	
	private Vector<String> vValsGraf = new Vector<String>();
	
	private JRadioGroup	<String, String> rgTipo = null;
	
	private Vector<String> vLabsOrdem = new Vector<String>();
	
	private Vector<String> vValsOrdem = new Vector<String>();
	
	private JRadioGroup	<String, String> rgOrdem = null;
	
	public FRFluxoCaixaPeriodo() {
		super();

		setTitulo( "Fluxo de caixa por período" );
		setAtribos( 80, 80, 350, 260 );
		montaRadioGroup();
		montaTela();
	}
	
	private void montaTela(){

	
		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
		JLabel periodo = new JLabel( "Período", SwingConstants.CENTER );
		periodo.setOpaque( true );

		adic( periodo, 20, 0, 80, 20 );
		adic( txtDataIni, 30, 20, 100, 20 );
		adic( new JLabel( "até", SwingConstants.CENTER ), 140, 20, 40, 20 );
		adic( txtDataFim, 190, 20, 100, 20 );
		adic( bordaData, 10, 10, 300, 40 );
		
		adic ( rgTipoRel, 10, 57, 300, 30 );
		//adic ( rgTipo, 10, 93, 300, 30 );
		adic ( rgOrdem, 10, 109, 300, 30, "Ordenar por: " );
		
		Calendar cal = Calendar.getInstance();
		txtDataFim.setVlrDate( cal.getTime() );
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 );
		txtDataIni.setVlrDate( cal.getTime() );
	}
	
	private void montaRadioGroup(){
		
		//Relatório Resumido ou Detalhado
		vLabsTipo .addElement( "Resumido" );
		vLabsTipo.addElement( "Detalhado" );
		vValsTipo.addElement( "R" );
		vValsTipo.addElement( "D" );
		
		rgTipoRel = new JRadioGroup<String, String>( 1, 2, vLabsTipo, vValsTipo );
		rgTipoRel.setVlrString( "D" );
		
		//Relatório Gráfico ou Texto
		vLabsGraf .addElement( "Gráfico" );
		vLabsGraf.addElement( "Texto" );
		vValsGraf.addElement( "G" );
		vValsGraf.addElement( "T" );
		
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabsGraf, vValsGraf );
		rgTipo.setVlrString( "G" );
		
		//Ordem do Relatório por emissão ou vencimento ou pagamento;
		vLabsOrdem .addElement( "Emissão" );
		vLabsOrdem.addElement( "Vencimento" );
		vLabsOrdem.addElement( "Pagamento" );
		vValsOrdem.addElement( "E" );
		vValsOrdem.addElement( "V" );
		vValsOrdem.addElement( "P" );
		
		rgOrdem = new JRadioGroup<String, String>( 1, 3, vLabsOrdem, vValsOrdem );
		rgOrdem.setVlrString( "E" );
	}

	public void imprimir( boolean bVisualizar ) {
		Blob fotoemp = null;
		StringBuilder sql = null;
		String sCab = null;
		String sOrdem = null;
		String sWhere = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
	}
	
	private void imprimiGrafico(boolean bVisualizar, String sCab, ResultSet rs, Blob fotoemp){
		
	}
}
