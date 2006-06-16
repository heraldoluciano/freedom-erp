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
	public static final byte ACK = 6;
	public static final byte NAK = 21;
	public static final int TIMEOUT = 1000;
	public static final int TIMEOUT_ACK = 1500;
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
	
	public static final char ACRECIMO_PERCENTUAL = 'A';
	public static final char DESCONTO_PERCENTUAL = 'D';
	public static final char ACRECIMO_POR_VALOR = 'a';
	public static final char DESCONTO_POR_VALOR = 'd';
	
	protected int sistema = -1;
	protected byte[] bytesLidos = null;
    private InputStream entrada = null;
    private OutputStream saida = null;
	protected String porta;
	protected int portaSel = -1;
	protected boolean ativada = false;
	protected SerialPort portaSerial = null;
	
	public ECFDriver() { }
	
	public ECFDriver(int com) {
		ativaPorta(com);
	}
	
	public byte[] adicBytes(byte[] variavel, byte[] incremental) {
		byte[] retorno = new byte[variavel.length+incremental.length];
		for (int i=0; i<retorno.length; i++) {
			if (i<variavel.length)
				retorno[i] = variavel[i];
			else
				retorno[i] = incremental[i-variavel.length];
		}
		return retorno;
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

	public String convPorta(int com) {
		String porta = null;
		if (getSistema()==OS_WINDOWS) 
			porta = "COM"+(com+1);
		else
			porta = "/dev/ttyS"+com;
		return porta;
	}
	
	public boolean getAtivada() {
		return ativada;
	}

	public byte[] getBytesLidos() {
		return bytesLidos;
	}

	public int getSistema() {
		String os = null;
		if (sistema==OS_NONE) {
		  os = System.getProperty("os.name").toLowerCase();
		  if (os.indexOf("linux")>OS_NONE)
			  sistema = OS_LINUX;
		  else if (os.indexOf("windows")>OS_NONE)
			  sistema = OS_WINDOWS;
		}
		return sistema;
	}
	
	public void desativaPorta() {
		if (portaSerial==null) 
			portaSerial.close();
		portaSerial = null;
		ativada = false;
	}
	
	public byte[] enviaCmd(byte[] CMD ) {
		return enviaCmd(CMD, portaSel);
	}
	
	public byte[] enviaCmd(byte[] CMD, int com) {
		byte[] retorno = null;
		byte[] buffer = null;
		byte[] tmp = null;
		int vezes = 0;
		if (ativaPorta(com)) {
		   try {
			 // saida.
			  saida.flush();
		      saida.write(CMD);
		      Thread.sleep(TIMEOUT_ACK);
		      System.out.println(entrada.available());
		      while ( (entrada.available()<=0) && (vezes<100) ) {
		    	  System.out.println("Aguardando retorno...");
		    	  Thread.sleep(100);
		    	  vezes ++;
		      }
		      vezes = 0;
		      while ((entrada.available()>0) && (vezes<100)) {
		    	  System.out.println("Lendo retorno: "+entrada.available());
		    	  retorno = new byte[entrada.available()];
		    	  entrada.read(retorno);
		    	  if (buffer==null)
		    		  buffer = retorno;
		    	  else {
		    		  tmp = buffer;
		    		  buffer = new byte[tmp.length + retorno.length];
		    		  for (int i=0; i<buffer.length; i++) {
		    			  if (i<tmp.length) 
		    				  buffer[i] = tmp[i];
		    			  else 
		    				  buffer[i] = retorno[i-tmp.length];
		    		  }
		    	  }
	    	     Thread.sleep(100);
		      }
		   }
		   catch (IOException e) {
			   
		   }
		   catch (InterruptedException e) {
			   
		   }
		}
		System.out.println("tamanho do retorno: "+buffer.length);
		for (int i=0; i<buffer.length; i++) {
			System.out.println("Retorno "+i+" = "+buffer[i]);
		}
		return buffer;
	}
	
	public String parseParam(String param, int tamanho) {
		if(param.length() > tamanho)
			param = param.substring( tamanho );
		return param;
	}
	
	public String parseParam(int param, int tamanho) {				
		return strZero( String.valueOf(param) , tamanho);
	}
	
	public String parseParam(float param,int tamanho,int casasdec) {				
		return strDecimalToStrCurrency(param,tamanho,casasdec);
	}
    
	public String replicate(String texto, int Quant) {		
		StringBuffer sRetorno = new StringBuffer();
		sRetorno.append("");
		for (int i = 0; i < Quant; i++)
			sRetorno.append(texto);
		return sRetorno.toString();
	}

	public String strDecimalToStrCurrency(float param,int tamanho,int casasdec) {
		StringBuffer str = new StringBuffer();
		str.append( String.valueOf( param ) );
		
		char[] c = str.toString().toCharArray();
		int index = str.indexOf(".");
		int indexDesc = casasdec - ((c.length-1)-index);
		str.delete(0,c.length);
		
		for(int i=0; i < c.length; i++)
			if( i!= index )
				str.append(c[i]);
		
		for(int i=0; i < indexDesc; i++)
			str.append("0");
		
		return strZero( str.toString() , tamanho);
	}

	public String strZero(String val, int zeros) {
		if (val == null)
			return val;
		String sRetorno = replicate("0", zeros - val.trim().length());
		sRetorno += val.trim();
		return sRetorno;
	}
	
	public abstract byte[] preparaCmd(byte[] CMD);
	
	public abstract int executaCmd(byte[] CMD);
	
	public abstract int checkRetorno(byte[] bytes);
	
	////////////////////////
	/////              /////
	/////	COMANDOS   /////
	/////              /////
	////////////////////////
	
	public abstract int aberturaDeCupom();// 0
	
	public abstract int aberturaDeCupom(String cnpj);// 0
	
	public abstract int alteraSimboloMoeda(String simbolo);// 1
	
	public abstract int leituraX();// 5
	
	public abstract int reducaoZ();// 6
	
	public abstract int vendaItem(String codProd, String descProd, String sitTrib, float qtd, float valor, float desconto);// 9
	
	public abstract int cancelaItemAnterior();// 13
	
	public abstract int cancelaCupom();// 14
	
	public abstract int cancelaItemGenerico(int item);// 31
	
	public abstract int iniciaFechamentoCupom(char opt, float percentual);// 32
	
	public abstract int terminaFechamentoCupom(String menssagem);// 34
	
	//	tertar com outro papel.
	public abstract int programaUnidadeMedida(String descUnid);// 62 51
	
	public abstract int aumentaDescItem(String descricao);// 62 52
	
	// com problemas devido a falta de informação sobre os parametros.
	public abstract int vendaItemDepartamento(String sitTrib, float valor, float qtd, float desconto, float acrescimo, String departamento, String unidade, String codProd, String descProd);// 63
	
	public abstract int nomeiaDepartamento(int index, String descricao);// 65
	
	//	ver rotina de funcionamento.
	public abstract int efetuaFormaPagamento(int indice, float valor, String descForma);// 72
	//	são 50 parametros ????
	//public abstract int programaDescFormaPagamento(String descT2, String descT3);// 73
	
}
