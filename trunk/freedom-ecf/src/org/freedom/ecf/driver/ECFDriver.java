/*
 * Classe base para impressoras fiscais
 * Autor: Robson Sanchez
 * Data: 05/04/2006
 *
 */
package org.freedom.ecf.driver;

public abstract class ECFDriver {
	public static final byte ESC = 27;
	public static final byte STX = 2;
	protected String porta;
	protected boolean ativada = false;
	public ECFDriver() {
		
	}
	public ECFDriver(String porta) {
		this.porta = porta;
	}
	public boolean abrePorta(String porta) {
		boolean retorno = true;
		this.porta = porta;
		
		ativada = retorno;
		return retorno;
	}
	public boolean getAtivada() {
		return ativada;
	}
	public abstract byte[] preparaCmd(byte[] CMD);
	
	public abstract int leituraX();
	
	
}
