/*
 * Classe base para impressoras fiscais
 * Autor: Robson Sanchez
 * Data: 05/04/2006
 *
 */
package org.freedom.ecf.driver;

import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

public abstract class ECFDriver {
	public static final byte ESC = 27;
	public static final byte STX = 2;
	public static final int TIMEOUT = 60;
	public static final int BAUDRATE = 9600;
	public static final int DATABITS = SerialPort.DATABITS_8;
	public static final int STOPBITS = SerialPort.STOPBITS_1;
	public static final int PARITY = SerialPort.PARITY_NONE;
	public static final int COM1 = 0;
	public static final int COM2 = 1;
	public static final int COM3 = 2;
	public static final int COM4 = 3;
	public static final int COM5 = 4;
	protected String porta;
	protected boolean ativada = false;
	protected SerialPort portaSerial = null;
	public ECFDriver() {
		
	}
	public ECFDriver(String porta) {
		this.porta = porta;
		abrePorta(porta);
	}
	public boolean abrePorta(String porta) {
		boolean retorno = true;
		this.porta = porta;
		portaSerial = abreSerial(porta);
		if (portaSerial==null)
			retorno = false;
		ativada = retorno;
		return retorno;
	}
	public SerialPort abreSerial(String porta) {
		SerialPort portaSerial = null;
		Enumeration listaPortas = null;
		CommPortIdentifier ips = null;
		listaPortas = CommPortIdentifier.getPortIdentifiers();
		while (listaPortas.hasMoreElements()) {
			ips = (CommPortIdentifier) listaPortas.nextElement();
			if (ips.getName().equalsIgnoreCase(porta)) 
				break;
			else
				ips = null;
		}
		if (ips != null) {
			try {
				portaSerial = (SerialPort) ips.open("SComm",TIMEOUT);
				portaSerial.setSerialPortParams(BAUDRATE, DATABITS, STOPBITS, PARITY);
			}
			catch(PortInUseException e) {
				portaSerial = null;
			}
			catch (UnsupportedCommOperationException e) {
				
			}
		}
		return portaSerial;
	}
	public boolean getAtivada() {
		return ativada;
	}
	public abstract byte[] preparaCmd(byte[] CMD);
	
	public abstract int leituraX();
	
	
}
