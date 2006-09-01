/**
 * @version 1.0.0 - 05/04/2006 <BR>
 * @author Robson Sanchez/Setpoint Informática Ltda. <BR>
 *         criada: 19/08/2006. <BR>
 * 
 * Projeto: Freedom-ECF <BR>
 * Pacote: org.freedom.ecf.com <BR>
 * Classe:
 * @(#)Serial.java <BR>
 * @see org.freedom.ecf.driver.AbstractECFDriver
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Esta classe é um Singleton para acesso e controle a porta serial. <BR>
 * 
 */
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


public class Serial {

	public static final int TIMEOUT = 150;

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

	private static InputStream entrada = null;

	private static OutputStream saida = null;

	private static String porta;

	private static int portaSel = -1;

	private static boolean ativada = false;

	private static SerialPort portaSerial = null;

	private static int sistema = -1;

	private static Serial instance = null;

	/*
	 *  Construtor definido como private seguindo o padrão Singleton.
	 */
	private Serial() {

		super();

	}

	public static Serial getInstance() {

		if ( instance == null ) {
			instance = new Serial();
		}
		
		return instance;
	}

	/**
	 *  Ativa a porta serial.<BR>
	 * @param com
	 * @param event Objeto ouvinte do evento de leitura dos bytes recebidos.<BR>
	 * @return Verdadeiro caso a porta esteja ativada. <BR>
	 */
	public boolean ativaPorta( final int com, final SerialPortEventListener event ) {

		boolean retorno = true;

		if ( com != portaSel || portaSerial == null ) {
			
			portaSel = com;
			porta = convPorta( com );
			portaSerial = ativaSerial( porta );

			if ( portaSerial == null ) {
				retorno = false;
			}
			else {
				
				try {
					
					portaSerial.addEventListener( event );
					portaSerial.notifyOnDataAvailable( true );
					
				} catch ( TooManyListenersException e ) {
					e.printStackTrace();
					retorno = false;
				}
				
			}
			
			ativada = retorno;
			
		}
		
		return retorno;
	}

	/**
	 * Desativa a porta serial.<BR>
	 *
	 */
	public void desativaPorta() {

		if ( portaSerial == null ) {
			portaSerial.close();
		}

		portaSerial = null;
		ativada = false;

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

	/**
	 * Captura a porta aberta de define os objetos de entrada e saida.<BR>
	 * @param porta
	 * @return
	 */
	public SerialPort ativaSerial( final String porta ) {

		SerialPort portaSerial = null;
		Enumeration listaPortas = null;
		CommPortIdentifier ips = null;
		listaPortas = CommPortIdentifier.getPortIdentifiers();

		while ( listaPortas.hasMoreElements() ) {

			ips = (CommPortIdentifier) listaPortas.nextElement();

			if ( ips.getName().equalsIgnoreCase( porta ) ) {
				break;
			}
			else {
				ips = null;
			}

		}

		if ( ips != null ) {

			try {

				portaSerial = (SerialPort) ips.open( "SComm", TIMEOUT );

				if ( portaSerial != null ) {

					entrada = portaSerial.getInputStream();
					saida = portaSerial.getOutputStream();
					try {
						portaSerial.setFlowControlMode( SerialPort.FLOWCONTROL_RTSCTS_OUT );
					} catch ( UnsupportedCommOperationException e ) {
						e.printStackTrace();
					}
					try {
						portaSerial.setSerialPortParams( BAUDRATE, DATABITS, STOPBITS, PARITY );
					} catch ( UnsupportedCommOperationException e ) {
						e.printStackTrace();
					}
					portaSerial.setDTR(true);
					portaSerial.setRTS(true);
					
/*
 *
 *http://bdn.borland.com/article/0,1410,31915,00.html
 *    public ComControl() {

        try {
            TimeStamp = new java.util.Date().toString();
            serialPort1 = (SerialPort) portId1.open("ComControl", 2000);
            System.out.println(TimeStamp + ": " + portId1.getName() + " opened for scanner input");
            serialPort2 = (SerialPort) portId2.open("ComControl", 2000);
            System.out.println(TimeStamp + ": " + portId2.getName() + " opened for diverter output");

        } catch (PortInUseException e) {}
        try {
            inputStream = serialPort1.getInputStream();
        } catch (IOException e) {}
        try {
            serialPort1.addEventListener(this);
        } catch (TooManyListenersException e) {}
        serialPort1.notifyOnDataAvailable(true);
        try {

            serialPort1.setSerialPortParams(9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

            serialPort1.setDTR(false);
            serialPort1.setRTS(false);

            serialPort2.setSerialPortParams(9600,
                SerialPort.DATABITS_7,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_EVEN);

        } catch (UnsupportedCommOperationException e) {}
 */					

				}

			} catch ( PortInUseException e ) {
				e.printStackTrace();
			} catch ( IOException e ) {
				e.printStackTrace();
				//portaSerial = null;
			}

		}

		return portaSerial;

	}

	/**
	 * 
	 * @return o nome do sistema operacional.<BR>
	 */
	public static int getSistema() {

		final String system = System.getProperty( "os.name" ).toLowerCase();

		if ( sistema == OS_NONE ) {

			if ( system.indexOf( "linux" ) > OS_NONE ) {
				sistema = OS_LINUX;
			}
			else if ( system.indexOf( "windows" ) > OS_NONE ) {
				sistema = OS_WINDOWS;
			}

		}

		return sistema;

	}

	/**
	 * 
	 * @return Verdadeiro caso a porta esteja ativada. <BR>
	 */
	public boolean isAtivada() {

		return ativada;
	}

	public InputStream getEntrada() {

		return entrada;
	}
	
	public OutputStream getSaida() {

		return saida;
	}

}
