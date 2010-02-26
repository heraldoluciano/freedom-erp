package org.freedom.infra.driver.scale;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import org.freedom.infra.comm.AbstractPort;
import org.freedom.infra.comm.CtrlPort;
import org.freedom.infra.comm.SerialParams;



public abstract class AbstractScale implements SerialPortEventListener {

	protected SerialParams serialParams = new SerialParams();
	
	protected static byte[] buffer = null;
	
	public abstract BigDecimal getWeight();

	public abstract Date getDate();

	public abstract Time getTime();
	
	protected abstract void readReturn();
	
	public boolean isRead = false;
	
	private boolean outputWrite;
	
	protected static int TIMEOUT_READ = 30000;
	
	public static final int TIMEOUT_ACK = 500;
	
	public static final byte ENQ = 5;
	
	
	
	public boolean activePort( final int com ) {

		boolean result = CtrlPort.getInstance().isActive();

		if ( !result ) {
			result = CtrlPort.getInstance().activePort( com, serialParams, this );
		}

		return result;
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

				if ( result != null ) {

					input.read( result );

					if ( buffer == null ) {
						bufferTmp = result;
					} else {
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
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void writeOutput( final byte[] CMD ) {
		
		try {
			final OutputStream output = CtrlPort.getInstance().getOutput();
			output.flush();
			output.write( CMD );		
			closeOutput();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void closeOutput() {
		
		try {
			final OutputStream output = CtrlPort.getInstance().getOutput();			
			output.close();	
			outputWrite = true;
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public byte[] sendCmd( final byte[] CMD, final int com, final int tamresult ) {

		long tempo = 0;
		long tempoAtual = 0;
		boolean isserial = AbstractPort.isSerial( com );
		isRead = false;
		buffer = null;

		if ( activePort( com ) ) {

			try {

				tempo = System.currentTimeMillis();
				outputWrite = false;
				
				Thread tee = new Thread( new Runnable() {
					public void run() {
						writeOutput( CMD );
					}				
				});
				
				tee.start();
				tee.join( TIMEOUT_READ );
				
				if ( !outputWrite ) {
					tee.interrupt();
					return null;
				}

				if ( isserial ) {
					do {
						Thread.sleep( TIMEOUT_ACK );
						tempoAtual = System.currentTimeMillis();
					} while ( (tempoAtual - tempo) < (TIMEOUT_READ) 
								&& (buffer == null || buffer.length < tamresult || !isRead ) );
				}

			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}

		return buffer;
	}
	

	
}
