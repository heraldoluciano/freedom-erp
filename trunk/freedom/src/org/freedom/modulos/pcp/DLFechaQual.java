package org.freedom.modulos.pcp;

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
	
	String tipo = "";
	
	public DLFechaQual( String sDescAnalise, String sTipo ){
		
		setTitulo( "Qualidade" );
		setAtribos( 350, 200 );
		
		adic( new JLabelPad("Descrição da analise"), 7, 5, 200, 20 );
		adic( txtDescEst, 7, 25, 310, 20 );
		adic( new JLabelPad("Aferição"), 7, 45, 200, 20 );
		
		tipo = sTipo;
		
		if( "DT".equals( sTipo )){
			
			adic( txtDescAfer, 7, 65, 200, 20 );
			txtDescAfer.setRequerido( true );
		}
		else if( "MM".equals( sTipo ) ){
			
			adic( txtVlrAfer, 7, 65, 70, 20 );
			txtVlrAfer.setRequerido( true );
		}
		
		txtDescEst.setVlrString( sDescAnalise );
		
		txtDescEst.setAtivo( false );
	
	
	}

	private void getValorTeste() {
		Funcoes.mensagemInforma( null, "Valor do campo:" + txtVlrAfer.getVlrInteger() );
		
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
	//	getValorTeste();
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
