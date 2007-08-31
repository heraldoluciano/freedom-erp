package org.freedom.funcoes;
import java.math.BigDecimal;
import java.util.Date;




public class Teste {

	/**
	 * @param args
	 */ 
	public static void main( String[] args ) {
		
        Date dtBase = Funcoes.encodeDate( 1997, 10, 7 );
        Date dtVencto = Funcoes.encodeDate( 2007, 8, 25 );
        String barra = org.freedom.funcoes.Boleto.geraCodBar( "1",  "9", "9", 
				new Long(Funcoes.getNumDiasAbs( Funcoes.encodeDate( 2007, 8, 23 ), 
						Funcoes.encodeDate( 1997, 10, 7 ) )), 
				new BigDecimal(100), 
				 "1421609", new Long(1), new Long(1),  "3275-1", "5688-X", "17", "00" );
		System.out.println(barra);
		System.out.println(barra.length());
		
		System.out.println(org.freedom.funcoes.Boleto.geraNossoNumero( "00", "1421609", new Long(1), new Long(1) ));

	}

}
