package org.freedom.ecf.app;

import java.math.BigDecimal;

import org.freedom.ecf.com.Serial;
import org.freedom.ecf.driver.AbstractECFDriver;
import org.freedom.ecf.driver.ECFBematech;


public class Control {
	
	private boolean modoDemostracao;	
	
	private AbstractECFDriver ecf;
	
	private String messageLog;
	
	
	public Control( final String ecfdriver ) throws IllegalArgumentException, NullPointerException {
		
		this( ecfdriver, Serial.COM1, false );
	}

	public Control( final String ecfdriver, final boolean mododemostracao ) throws IllegalArgumentException, NullPointerException {
		
		this( ecfdriver, Serial.COM1, mododemostracao );
	}
	
	public Control( final String ecfdriver, final int porta ) throws IllegalArgumentException, NullPointerException {
		
		this( ecfdriver, porta, false );
	}
	
	public Control( final String ecfdriver, final String porta ) throws IllegalArgumentException, NullPointerException {
		
		this( ecfdriver, Serial.convPorta( porta ), false );
	}
	
	public Control( final String ecfdriver, final String porta, final boolean mododemostracao ) throws IllegalArgumentException, NullPointerException {
		
		this( ecfdriver, Serial.convPorta( porta ), mododemostracao );
	}
	
	public Control( final String ecfdriver, final int porta, final boolean mododemostracao ) throws IllegalArgumentException, NullPointerException {
		
		if ( ecfdriver == null || ecfdriver.trim().length() == 0 ) {			
			throw new IllegalArgumentException( "Driver de impressora fiscal invalido." );
		}
		
        try {
        	Object obj = Class.forName( ecfdriver ).newInstance();
        	if ( obj instanceof AbstractECFDriver ) {
            	this.ecf = (AbstractECFDriver) obj;    
            	this.ecf.ativaPorta( porta > 0 ? porta : Serial.COM1 );
        	}
        } catch ( ClassNotFoundException e ) {
        	e.printStackTrace();
        } catch ( InstantiationException e ) {
        	e.printStackTrace();
        } catch ( IllegalAccessException e ) {
        	e.printStackTrace();
        }
        
        if ( this.ecf == null ) {
        	throw new NullPointerException( 
        			"Não foi possível carregar o driver da impressora.\n" +
        			ecfdriver );
        }
        
        setModoDemostracao( mododemostracao );
	}
	
	public void setModoDemostracao( final boolean modoDemostracao ) {
		this.modoDemostracao = modoDemostracao;
	}
	
	public boolean isModoDemostracao() {
		return this.modoDemostracao;
	}
	
	public boolean notIsModoDemostracao() {
		return ! isModoDemostracao();
	}
	
	public void setMessageLog( final String message ) {
		this.messageLog = message;
	}
	
	public String getMessageLog() {
		return this.messageLog;
	}

	public boolean decodeReturn( final int arg ) {

		boolean returnOfAction = true;

		setMessageLog( null );
		String str = ecf.decodeReturnECF( arg ).getMessage();

		if ( str != null ) {
			returnOfAction = false;
			setMessageLog( str );
		}

		return returnOfAction;
	}
	
	// Comandos ...
	
	public boolean leituraX() {
		
		return leituraX( false );
	}
	
	public boolean leituraX( final boolean saidaSerial ) {
		
		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			
			if ( saidaSerial ) {
				returnOfAction = decodeReturn( ecf.leituraXSerial() );
			}
			else {
				returnOfAction = decodeReturn( ecf.leituraX() );
			}
			
			if ( ! returnOfAction ) {
				// gravar log do erro.
			}
		}
		
		return returnOfAction;
	}
	
	public boolean suprimento( final BigDecimal valor ) { 
		return true;
	}
	
	public String readSerialPort() {
		
		if ( notIsModoDemostracao() ) {			
			return  new String( ecf.getBytesLidos() );
		}
		
		return null;
	}
}
