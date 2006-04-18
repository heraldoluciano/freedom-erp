/*
 * Classe de driver para impressoras Bematech
 * Autor: Robson Sanchez/Setpoint Informática Ltda.
 * Data: 05/04/2006
 */
package org.freedom.ecf.driver;

public class ECFBematech extends ECFDriver {
	
	public ECFBematech(int com) {
		super(com);
	}
	public byte[] preparaCmd(byte[] CMD) {
		int tamCMD = CMD.length;
		int tam = tamCMD + 2;
		byte NBL = (byte) (tam % 256);
		byte NBH = (byte) (tam / 256);
		byte CSL = 0;
		byte CSH = 0;
		int soma = 0;
		byte[] retorno = new byte[5 + tamCMD];
		retorno[0] = STX;
		retorno[1] = NBL;
		retorno[2] = NBH;
		for (int i=0; i<tamCMD; i++) {
			soma += CMD[i];
			retorno[i+3] = CMD[i];
		}
		CSL = (byte) (soma % 256);
		CSH = (byte) (soma / 256);
        retorno[retorno.length-2] = CSL;
        retorno[retorno.length-1] = CSH;
		return retorno;
	}
	
	public int leituraX() {
		byte[] CMD = {ESC,6};
		byte[] retorno = null;
		CMD = preparaCmd(CMD);
		retorno = enviaCmd(CMD);
		return checkRetorno(retorno);
	}

	public int checkRetorno(byte[] retorno) {
		return 0;
	}
	
}
