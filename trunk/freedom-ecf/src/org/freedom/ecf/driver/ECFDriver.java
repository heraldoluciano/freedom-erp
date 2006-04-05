package org.freedom.ecf.driver;

public abstract class ECFDriver {
	public static final byte ESC = 27;
	protected String porta;
	public ECFDriver() {
		
	}
	public ECFDriver(String porta) {
		this.porta = porta;
	}
	public boolean abrePorta(String porta) {
		boolean retorno = true;
		this.porta = porta;
		return retorno;
	}
}
