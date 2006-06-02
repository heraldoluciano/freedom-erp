package org.freedom.ecf.driver;

import java.util.Date;

//import java.util.Properties;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date data = new Date();
		ECFBematech ecf = new ECFBematech(ECFBematech.COM1);
		System.out.println("Inicio --> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
		ecf.aberturaDeCupom ();
		data = new Date();
		System.out.println("Fim -----> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
		//Properties p = System.getProperties();
		
	//	System.out.println(p.getProperty("os.arch"));
		//System.out.println(p.getProperty("os.name"));
		//System.out.println(p.getProperty("os.version"));
		//os.version

	}

}
