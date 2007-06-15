package org.freedom.funcoes;


public class Boleto {
	
	private static String codBar = "";
	
	public static String geraCodBar(boolean isNum, String codbanco, String codmoeda, String dvbanco, String fatVenc, 
			String vlrTitulo, String campLivre  ){
		
		String barcode = null;
		
		if (isNum) {
			barcode = "00192240701002023275600005688189834870000005000";
		} else {
			barcode = Funcoes.strZero( codbanco,3 ) + Funcoes.strZero( codmoeda, 1 ) + 
				Funcoes.strZero( digVerif (dvbanco), 1 ) + Funcoes.strZero( fatVenc, 4 ) + Funcoes.strZero( vlrTitulo, 9 ) +
				Funcoes.strZero( campLivre, 25 );
			
			codBar = barcode;
		}
		return barcode;
	}
	public static String digVerif(String digito){
		
		String digCodBar = null;
		
		if(codBar.length() == 43 ){
			
		}else{
			
		}
		return digCodBar;
	}
}
