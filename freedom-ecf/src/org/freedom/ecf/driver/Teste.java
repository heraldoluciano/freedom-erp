package org.freedom.ecf.driver;

//import java.util.Properties;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ECFBematech ecf = new ECFBematech(ECFBematech.COM1);
		ecf.leituraX();
		//Properties p = System.getProperties();
		
	//	System.out.println(p.getProperty("os.arch"));
		//System.out.println(p.getProperty("os.name"));
		//System.out.println(p.getProperty("os.version"));
		//os.version

	}

}
