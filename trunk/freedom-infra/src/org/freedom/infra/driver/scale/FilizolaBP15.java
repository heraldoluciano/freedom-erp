package org.freedom.infra.driver.scale;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import javax.comm.SerialPort;
import org.freedom.infra.functions.StrFunctions;

public class FilizolaBP15 extends AbstractScale  {

	public static final byte ENQ = 5;
	
	public static final byte STX = 2;
	
	public static final byte ETX = 3;
	
	public static final String INSTABLE = "|||||";
	
	public static final String NEGATIVE = "NNNNN";
	
	public static final String OVERFLOW = "SSSSS";
	
	public FilizolaBP15( int port ) {
		
		super();
		
		configSerialParams();
		
		activePort( port );
	
		byte[] comando = { ENQ };
		
		sendCmd( comando, 0, 100 );
		
		readReturn();
		
	}
	
	protected void readReturn() {
		
		try {
			
			if(buffer!=null) {
			
				String reading = new String( buffer );			
				System.out.println(reading);
				
			}
			else {
				System.out.println("Buffer is null!");
			}			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Date getDate() {
 
		return new Date();
	}

	public Time getTime() {
		
		Time tm = null;		
		tm = new Time(getDate().getTime());
		
		return tm;
		
	}

	public BigDecimal getWeight() {
		
		BigDecimal weight = new BigDecimal(0);
		
		if( buffer!=null && buffer.length>0 ) {
		
			if( buffer[0] == STX && buffer[6] == ETX ) {

				String strweight = new String(buffer);
				
				strweight = strweight.substring(1,strweight.length()-1);
				
				strweight = StrFunctions.alltrim(strweight);
				
				System.out.print( strweight );
			
				if( FilizolaBP15.INSTABLE.equals( strweight ) ) {
					System.out.println("Escale is unstable, try again!" );
					return weight;
				}
				else if ( FilizolaBP15.NEGATIVE.equals( strweight ) ) {
					System.out.println("Escale return a negative value, try again!" );
					return weight;					
				}
				else if ( FilizolaBP15.OVERFLOW.equals( strweight ) ) {
					System.out.println("Escale return a weight overflow, try again!" );
					return weight;
				}
				
				weight = new BigDecimal(String.valueOf(strweight));

			}
			else { 
				System.out.println("Invalid return!");
			}			
									
		}		
		
		return weight;
	}
	
	private void configSerialParams() {

		serialParams.setTimeout( 100 );
		serialParams.setBauderate( 9600 );
		serialParams.setDatabits( SerialPort.DATABITS_8 );
		serialParams.setStopbits( SerialPort.STOPBITS_1 );
		serialParams.setParity( SerialPort.PARITY_NONE );
		
	}
	
	

}
