/*
 * Classe base para impressoras fiscais
 * Autor: Robson Sanchez
 * Data: 05/04/2006
 *
 */
package org.freedom.ecf.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	public static final int OS_NONE = -1;
	public static final int OS_LINUX = 0;
	public static final int OS_WINDOWS = 1;
	protected int sistema = -1;
    private InputStream entrada = null;
    private OutputStream saida = null;
	
	protected String porta;
	protected int portaSel = -1;
	protected boolean ativada = false;
	protected SerialPort portaSerial = null;
	public ECFDriver() {
		
	}
	public ECFDriver(int com) {
		ativaPorta(com);
	}
	public boolean ativaPorta(int com) {
		boolean retorno = true;
		if ( (com!=portaSel) || (portaSerial==null) ) {
			portaSel = com;
			porta = convPorta(com);
			portaSerial = ativaSerial(porta);
			if (portaSerial==null)
				retorno = false;
			ativada = retorno;
		}
		return retorno;
	}
	public String convPorta(int com) {
		String porta = null;
		if (getSistema()==OS_WINDOWS) 
			porta = "COM"+(com+1);
		else
			porta = "/dev/ttyS"+com;
		return porta;
	}
	public int getSistema() {
		String os = null;
		if (sistema==OS_NONE) {
		  os = System.getProperty("os.name").toLowerCase();
		  if (os.indexOf("linux")>OS_NONE)
			  sistema = OS_LINUX;
		  else if (os.indexOf("windows")>OS_NONE)
			  sistema = OS_LINUX;
		}
		return sistema;
	}
	public SerialPort ativaSerial(String porta) {
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
				if (portaSerial!=null) {
	            	entrada = portaSerial.getInputStream();
	            	saida = portaSerial.getOutputStream();
					portaSerial.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_OUT);
					portaSerial.setSerialPortParams(BAUDRATE, DATABITS, STOPBITS, PARITY);
				}
			}
			catch(PortInUseException e) {
				portaSerial = null;
			}
			catch (UnsupportedCommOperationException e) {
				
			}
			catch (IOException e) {
				portaSerial = null;
			}
		}
		return portaSerial;
	}
	public boolean getAtivada() {
		return ativada;
	}

	public void desativaPorta() {
		if (portaSerial==null) 
			portaSerial.close();
		portaSerial = null;
		ativada = false;
	}
	
	public byte[] enviaCmd(byte[] CMD) {
		return enviaCmd(CMD, portaSel);
	}
	public byte[] enviaCmd(byte[] CMD, int com) {
		byte[] retorno = null;
		if (ativaPorta(com)) {
		   try {
		      saida.write(CMD);
		      entrada.read(retorno);
		   }
		   catch (IOException e) {
			   
		   }
		}
		return retorno;
	}
	public abstract byte[] preparaCmd(byte[] CMD);
	
	public abstract int leituraX();
	
	public abstract int checkRetorno(byte[] retorno);
	
	
}
