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
		System.out.println(org.freedom.funcoes.Boleto.geraCodBar( "1",  "9", "9", 
				new Long(Funcoes.getNumDiasAbs( Funcoes.encodeDate( 2007, 8, 25 ), 
						Funcoes.encodeDate( 1997, 10, 7 ) )), 
				new BigDecimal(265), 
				 "224071", new Long(1133), new Long(1),  "3275-1", "5688-X", "18", "00" ));

	}

}
