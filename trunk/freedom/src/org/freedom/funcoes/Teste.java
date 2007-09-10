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
        Long fatvenc = new Long(Funcoes.getNumDiasAbs( Funcoes.encodeDate( 2007, 8, 23 ), 
				Funcoes.encodeDate( 1997, 10, 7 ) ));
        BigDecimal vlrtit = new BigDecimal(100);
        String barra = org.freedom.funcoes.Boleto.geraCodBar( "1",  "9", "9", 
				fatvenc, 
				vlrtit, 
				 "1421609", new Long(1), new Long(1),  "3275-1", "5688-X", "17", "00" );
        String linhadig = Boleto.geraLinhaDig( barra, fatvenc, vlrtit );
		System.out.println(barra);
		System.out.println(barra.length());
		System.out.println(linhadig);
		System.out.println(linhadig.length());
		
		
		//System.out.println(org.freedom.funcoes.Boleto.geraNossoNumero( "00", "1421609", new Long(1), new Long(1) ));
        //System.out.println(Boleto.digVerif( "0019340100000000330375031750416060680935011" , 11 ));

	}

}
