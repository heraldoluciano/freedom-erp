package org.freedom.ecf.driver;

import java.util.Calendar;
import java.util.Date;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ECFBematech ecf = new ECFBematech(ECFBematech.COM1);
		
		Date data = new Date();
		System.out.println("Inicio --> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
		
		/*
		ecf.aberturaDeCupom();
		
		//ecf.programaUnidadeMedida("KG");
		//ecf.aumentaDescItem("Caneta                       0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" );
		ecf.vendaItem("0000000000001", "Caneta                       ", "FF", 1f, 0.12f, 0f);
		//ecf.vendaItemDepartamento("FF", 15.001f, 1f, 0.001f, 0.001f, "01", "UN", "0000000000001", "Caneta                       ");
		
		ecf.iniciaFechamentoCupom(ECFBematech.DESCONTO_PERCENTUAL,0);
		ecf.efetuaFormaPagamento(2,0.12f,"");
		ecf.terminaFechamentoCupom("Obrigado pela Preferencia!!!");
		*/
		
		//ecf.programaFormaPagamento(new String[]{"Cartao Credito  "});
		
		//ecf.estornoFormaPagamento("Cartao Credito  ", "Dinheiro        ", 0.40f );
		
		Date dt = Calendar.getInstance().getTime();
		Date dt2 = Calendar.getInstance().getTime();
		dt.setDate(1);
		dt.setMonth( dt.getMonth() - 1 );
		dt2.setDate(31);
		dt2.setMonth( dt2.getMonth() - 1 );
		
		ecf.leituraMemoriaFiscal( 1, 10, 'I');
		
		data = new Date();
		System.out.println("Fim -----> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
		
		
		System.exit(0);
	}

}
