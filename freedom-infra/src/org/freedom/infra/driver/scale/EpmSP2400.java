package org.freedom.infra.driver.scale;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;

import org.freedom.infra.comm.CtrlPort;
import org.freedom.infra.comm.Serial;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;

public class EpmSP2400 extends AbstractScale  {

	private BigDecimal weight = null;
	
	private Date date = null;
	
	private Time time = null;
	
	public static final String NOME_BAL = "Rodoviária EPM SP-2400";
	
public void initialize( Integer com, Integer timeout, Integer baudrate, Integer databits, Integer stopbits, Integer parity ) {
		
		this.com = com;
		
		serialParams.setTimeout( timeout );
		serialParams.setBauderate( baudrate );
		serialParams.setDatabits( databits );
		serialParams.setStopbits( stopbits );
		serialParams.setParity( parity );

		activePort(this);
		
		readReturn();
		
		
		
	}
	
	public String getName() {
		return NOME_BAL;
	}

	public void stopRead() {
		try {
			SerialPort  porta = Serial.getInstance().getSerialPort();	
			porta.removeEventListener();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void readReturn() {
		
		try {
			
			
//			SerialPort  porta = Serial.getInstance().getSerialPort();
//			porta.addEventListener(this);
			
//			porta.removeEventListener();
			
//			porta.notifyOnDataAvailable( true );
			
			
			
//			porta.addEventListener( this );
			
			
//			SerialPort  porta = Serial.getInstance().getSerialPort();
			
//			if(porta !=null) {
//				porta.removeEventListener();
//				porta.addEventListener(this);
//			}

			
			Thread.sleep( TIMEOUT_ACK );
			
/*			if(buffer!=null) {
			
				String reading = new String( buffer );			
				
				System.out.println(reading);
				
				parseString(reading);				
				
			}
			else {
				System.out.println("Buffer is null!");
			}*/			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setDate(Date date) {
		
		this.date = date;		
	}

	
	public Date getDate() {
 
		return date;
	
	}

	private void setTime(Time time) {
		
		this.time = time; 
	
	}
	
	public Time getTime() {
		
		return time;		
	}
		
	private synchronized void parseString(String str) {
		
		String strweight = "";
		String strdate = "";
		String strtime = "";
		
		try {
			
			System.out.println("Leitura:" + str);
			
			int posicaobranca = str.indexOf( " " );
			
			// pega os ultimos 48 caracteres do buffer
			if(str.length()>48) {
				str = str.substring(str.length()-48);
			}
			
			if(posicaobranca<24 && posicaobranca>-1) {
				str = str.substring( posicaobranca );
				str = StringFunctions.alltrim( str );
			}
			
			
			
			if(str.length()>=24) {
			
				str = str.substring( 0, 24 );
				
				String validador = str.substring( 11, 12 )  + str.substring( 14, 15 ) + str.substring( 19, 20 );
				
				if("//:".equals( validador )) {
					
					
										
					System.out.println("Finalizou leitura!");
					
					System.out.println("String Limpa:" + str);
					
					strweight = str.substring( 0,  07 );
					
					System.out.println("peso lido: " + strweight);
					
					strdate = str.substring( 9,  17 );
					
					System.out.println("data lida: " + strdate);
					
					strtime = str.substring( 17, str.length()-1 );
					
					System.out.println("hora lida antes: " + strtime);
					
					strtime = StringFunctions.clearString(strtime);
					
					System.out.println("hora lida depois: " + strtime);
					
					setWeight( ConversionFunctions.stringToBigDecimal( strweight ) );
					setDate( ConversionFunctions.strDate6digToDate( strdate ) );
					setTime( ConversionFunctions.strTimetoTime( strtime ) );
					
					CtrlPort.getInstance().disablePort();

				}
				else {				
					System.out.println("String nao validada: " + str);
				}
				
				
			}
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void serialEvent( final SerialPortEvent event ) {

		byte[] result = null;
		byte[] bufferTmp = null;
		byte[] tmp = null;
		
		InputStream input = null;

		input = CtrlPort.getInstance().getInput();

		try {
			
			if ( event.getEventType() == SerialPortEvent.DATA_AVAILABLE ) {

				result = new byte[ input.available() ];
//				result = new byte[ 64 ];

				int nodeBytes = 0;
				
				if ( result != null ) { 
					
					while ( input.available() > 0 ) {
						
						nodeBytes = input.read( result );	
					
					}

					System.out.println("tamanho da cadeia de leitura:" + nodeBytes);
					
					if ( buffer == null ) {
						bufferTmp = result;
					} 
					else {
						isRead = true;
						tmp = buffer;
						bufferTmp = new byte[ tmp.length + result.length ];

						for ( int i = 0; i < bufferTmp.length; i++ ) {
							if ( i < tmp.length ) {
								bufferTmp[ i ] = tmp[ i ];
							} else {
								bufferTmp[ i ] = result[ i - tmp.length ];
							}
						}
					}
					
					buffer = bufferTmp;
					parseString( new String(buffer) );
//					buffer = null;
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void setWeight( BigDecimal weight ) {
		
		this.weight = weight;
		
	}
	
	public BigDecimal getWeight() {
		
		return weight;
	}
	

}
