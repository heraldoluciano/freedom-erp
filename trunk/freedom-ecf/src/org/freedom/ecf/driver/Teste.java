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
		
		//ecf.vendaItem("0000000000001", "Caneta                       ", "FF", 15.001f, 0.12f, 0f);
		ecf.cancelaItemGenerico(2);
		
		data = new Date();
		System.out.println("Fim -----> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
		
		
		System.exit(0);
	}

}
