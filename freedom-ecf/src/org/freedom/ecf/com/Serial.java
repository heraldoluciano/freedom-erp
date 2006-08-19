package org.freedom.ecf.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

/**
 * Classe de controle da porta serial <BR>
 * Projeto: freedom-ecf <BR>
 * Pacote: org.freedom.ecf.com <BR>
 * Classe: Serial.java <BR>
 * <BR>
 * Este programa é licenciado de acordo com a LGPL (Lesser General Public
 * License), <BR>
 * versão 2.1, Fevereiro de 1999 <BR>
 * A LGPL deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LGPL não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <a
 * href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative
 * Commons</a> <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar de acordo com os termos da LGPL. <BR>
 * <BR>
 * @author Robson Sanchez/Setpoint Informática Ltda. <BR>
 * criada: 19/08/2006. <BR>
 */
public class Serial {

	public static final int TIMEOUT = 1000;
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
    private InputStream entrada = null;
    private OutputStream saida = null;
	private String porta;
	private int portaSel = -1;
	private boolean ativada = false;
	private SerialPort portaSerial = null;
	private int sistema = -1;
	
	private static Serial instance;
	private Serial() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static Serial getInstance() {
		if (instance==null) {
			instance = new Serial();
		}
		return instance;
	}
	public boolean ativaPorta( final int com, SerialPortEventListener event ) {
		
		boolean retorno = true;
		
		if ( com != portaSel || portaSerial == null ) {
			portaSel = com;
			porta = convPorta( com );
			portaSerial = ativaSerial( porta );
			
			if ( portaSerial == null ) {
				retorno = false;
			} else {
				try {
					portaSerial.addEventListener(event);
					portaSerial.notifyOnDataAvailable(true);
				}
				catch (TooManyListenersException e) {
					retorno = false;
				}
			}
			ativada = retorno;
		}
		return retorno;
	}
	
	
	
	public SerialPort ativaSerial( final String porta ) {
		
		SerialPort portaSerial = null;
		Enumeration listaPortas = null;
		CommPortIdentifier ips = null;
		listaPortas = CommPortIdentifier.getPortIdentifiers();
		
		while ( listaPortas.hasMoreElements() ) {
			
			ips = ( CommPortIdentifier ) listaPortas.nextElement();
			
			if ( ips.getName().equalsIgnoreCase( porta ) ) {
				break;
			}
			else {
				ips = null;
			}
			
		}
		
		if ( ips != null ) {
			
			try {
								
				portaSerial = ( SerialPort ) ips.open( "SComm", TIMEOUT );
				
				if ( portaSerial != null ) {
					
	            	entrada = portaSerial.getInputStream();
	            	saida = portaSerial.getOutputStream();
					portaSerial.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_OUT);
					portaSerial.setSerialPortParams(BAUDRATE, DATABITS, STOPBITS, PARITY);
					
				}
				
			} catch( PortInUseException e ) {
				e.printStackTrace();
			} catch ( UnsupportedCommOperationException e ) {
				
			} catch ( IOException e ) {
				portaSerial = null;
			}
			
		}
		
		return portaSerial;
		
	}
	
	public String convPorta( final int com ) {
		
		final StringBuffer porta = new StringBuffer();
		
		if ( getSistema() == OS_WINDOWS ) { 
			porta.append( "COM" );
			porta.append( com + 1 );
		}
		else {
			porta.append( "/dev/ttyS" );
			porta.append( com );
		}
		
		return porta.toString();
		
	}
	
	public int getSistema() {
		
		final String system = System.getProperty( "os.name" ).toLowerCase();
		
		if ( sistema == OS_NONE ) {
					  
		  if ( system.indexOf( "linux" ) > OS_NONE ) {
			  sistema = OS_LINUX;
		  }
		  else if ( system.indexOf("windows") > OS_NONE ) {
			  sistema = OS_WINDOWS;
		  }
		  
		}
		
		return sistema;
		
	}
	
	public void desativaPorta() {

		if ( portaSerial == null ) {
			portaSerial.close();
		}

		portaSerial = null;
		ativada = false;

	}
	
	public boolean getAtivada() {
		return ativada;
	}
	
    public InputStream getEntrada() {
    	return entrada;
    }
    public OutputStream getSaida() {
    	return saida;
    }

}
