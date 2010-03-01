package org.freedom.infra.driver.scale;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import javax.comm.SerialPort;


public class FilizolaBP15 extends AbstractScale  {

	public FilizolaBP15() {

		super();
		
		configSerialParams();
		activePort( 0 );
	
		byte[] comando = {ENQ};
		
		sendCmd( comando, 0, 100);
		
		readReturn();
		
	}
	
	protected void readReturn() {
		
		try {
			
			
			
			String reading = new String(buffer);
			
			System.out.println(reading);
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getWeight() {
		// TODO Auto-generated method stub
		
		System.out.println("Peso= parse de:" + new String(buffer) );
		
		return null;
	}
	
	private void configSerialParams() {

		serialParams.setTimeout( 100 );
		serialParams.setBauderate( 9600 );
		serialParams.setDatabits( SerialPort.DATABITS_8 );
		serialParams.setStopbits( SerialPort.STOPBITS_2 );
		serialParams.setParity( SerialPort.PARITY_NONE );
		
	}
	
	

}
