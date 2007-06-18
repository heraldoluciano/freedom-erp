package org.freedom.funcoes;
import java.math.BigDecimal;
import java.util.Date;




public class Teste {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		
        Date dtBase = Funcoes.encodeDate( 1997, 10, 7 );
        Date dtVencto = Funcoes.encodeDate( 2007, 06, 20 );
		System.out.println(Boleto.geraCodBar( "1", "9", "9", new Long(Funcoes.getNumDiasAbs( dtBase, dtVencto )) , new BigDecimal(100),
				"1", new Long(46), new Short("1"), "3275-1", "5688-X", "17" ));

	}

}
