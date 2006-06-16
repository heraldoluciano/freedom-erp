package org.freedom.ecf.driver;

import java.util.Date;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ECFBematech ecf = new ECFBematech(ECFBematech.COM1);
		
		Date data = new Date();
		System.out.println("Inicio --> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
		
		//ecf.aberturaDeCupom();
		//ecf.programaUnidadeMedida("999");
		ecf.vendaItem("0000000000001", "Caneta                       ", "FF", 1f, 0.12f, 0f);
		//ecf.vendaItemDepartamento("FF", 15.001f, 1f, 0.001f, 0.001f, "01", "UN", "0000000000001", "Caneta                       ");
		
		//ecf.efetuaFormaPagamento(1,1,"Dinheiro");
		ecf.iniciaFechamentoCupom(ECFBematech.DESCONTO_PERCENTUAL,0);
		ecf.terminaFechamentoCupom("Volte sempre");
		
		data = new Date();
		System.out.println("Fim -----> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
		
		
		System.exit(0);
	}

}
