package org.freedom.modulos.pcp;

import java.math.BigDecimal;
import java.util.HashMap;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


public class DLFechaQual extends FFDialogo {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtDescAfer = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldPad txtVlrAfer = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtVlrMin = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
		
	private JTextFieldPad txtVlrMax = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
	
	String tipo = "";
	
	public DLFechaQual( String sDescAnalise, String sTipo, BigDecimal bVlrMin, BigDecimal bVlrMax, BigDecimal vlrAfer, String sAfer ){
		
		setTitulo( "Qualidade" );
		setAtribos( 350, 220 );
		
		adic( new JLabelPad("Descrição da analise"), 7, 5, 200, 20 );
		adic( txtDescEst, 7, 25, 310, 20 );
		
		tipo = sTipo;
		
		if( "DT".equals( sTipo )){
			
			adic( new JLabelPad("Aferição"), 7, 45, 200, 20 );
			adic( txtDescAfer, 7, 65, 200, 20 );
			txtDescAfer.setRequerido( true );
			txtDescAfer.setVlrString( sAfer );
		}
		else if( "MM".equals( sTipo ) ){
			
			adic( new JLabelPad("Vlr.Mín."), 7, 45, 70, 20 );
			adic( txtVlrMin, 7, 65, 70, 20 );
			adic( new JLabelPad("Vlr.Máx."), 80, 45, 70, 20 );
			adic( txtVlrMax , 80, 65, 70, 20 );
			adic( new JLabelPad("Aferição"), 7, 85, 200, 20 );
			adic( txtVlrAfer, 7, 105, 70, 20 );
			
			txtVlrMin.setSoLeitura( true );
			txtVlrMax.setSoLeitura( true );
			txtVlrAfer.setRequerido( true );
			
			txtVlrMin.setVlrBigDecimal( bVlrMin );
			txtVlrMax.setVlrBigDecimal( bVlrMax );
			txtVlrAfer.setVlrBigDecimal( vlrAfer == null ? new BigDecimal(0) : vlrAfer);
		}
		
		txtDescEst.setVlrString( sDescAnalise );
		txtDescEst.setAtivo( false );
	
	}
	public HashMap<String, Object> getValor(){
			
		HashMap<String, Object> hRet = new HashMap<String, Object>();
		
		try {
				
			hRet.put( "DESCAFER", txtDescAfer.getVlrString() );
			hRet.put( "VLRAFER", txtVlrAfer.getVlrBigDecimal() );
		
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
		}
		
		return hRet;
		
	}
	
	public void ok() {
	
		 if( "DT".equals( tipo )){
			
			 if ( ( txtDescAfer.getVlrString().equals( "" ) ) ) {
					Funcoes.mensagemInforma( this, "Informe a descrição!" );
					return;
			}
			 else{
				super.ok();
			}
		}
		 else if( "MM".equals( tipo ) ){				
			
			if ( ( txtVlrAfer.getVlrString().equals( "" ) ) ) {
				Funcoes.mensagemInforma( this, "Informe o valor!" );
				return;
			}			
			else{
				super.ok();
			}	
		}
	}
}
