package org.freedom.tef.driver.dedicate;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.freedom.infra.util.ini.ManagerIni;

import SoftwareExpress.SiTef.jCliSiTefI;


public class DedicateTef {
	
	public static final String ENDERECO_TCP = "ENDERECO_TCP";
	
	public static final String EMPRESA = "EMPRESA";
	
	public static final String TERMINAL = "TERMINAL";
	
	private Properties properties;
	
	private jCliSiTefI clientesitef;
	
	private static DedicateTef instance;
	
	
	public static DedicateTef getInstance() {

		if ( instance == null ) {
			instance = new DedicateTef();
		}
		
		return instance;
	}
	
	public static DedicateTef getInstance( String file ) {
		
		return getInstance( new File( file ) );
	}
	
	public static DedicateTef getInstance( File file ) {

		if ( file.exists() ) {
			instance = new DedicateTef( file );
		}
		else {
			instance = null;
		}
		
		return instance;
	}
	
	private DedicateTef() {

		this( new File( System.getProperty( ManagerIni.FILE_INIT_DEFAULT ) ) );
	}
	
	private DedicateTef( File file ) throws IllegalArgumentException {
		
		if ( file == null || !file.exists() ) {
			throw new IllegalArgumentException( "Arquivo de parametros não existente." );
		}

		try {
			ManagerIni mi = ManagerIni.createManagerIniFile( file );
			properties = mi.getSession( "DedicateTef" );
			
			clientesitef = new jCliSiTefI();
					
			clientesitef.SetEnderecoSiTef( properties.getProperty( ENDERECO_TCP ) );
			clientesitef.SetCodigoLoja( properties.getProperty( EMPRESA ) );
			clientesitef.SetNumeroTerminal( properties.getProperty( TERMINAL ) );
			clientesitef.SetConfiguraResultado( 0 );			  
			
			int r = clientesitef.ConfiguraIntSiTefInterativo();
			
			if ( r != 0 ) {
				System.out.println( "ERRO " + r + " na ConfiguraIntSiTefInterativo!" );
			} 
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public long requestSale( BigDecimal value, Integer docNumber, Date dateHour, String operator ) {
		
		long requestsale = 0;
		
		DecimalFormat df = new DecimalFormat( "0.00" );
		
		SimpleDateFormat sdf1 = new SimpleDateFormat( "yyyyMMdd", Locale.getDefault() );
		SimpleDateFormat sdf2 = new SimpleDateFormat( "HHmmss", Locale.getDefault() );
		String date = sdf1.format( dateHour );
		String hour = sdf2.format( dateHour );
		
		clientesitef.SetModalidade( 0 );
		clientesitef.SetValor( df.format( value.doubleValue() ) );
		clientesitef.SetNumeroCuponFiscal( String.valueOf( docNumber ) );
		clientesitef.SetDataFiscal( date);
		clientesitef.SetHorario( hour );
		clientesitef.SetOperador( operator );
		clientesitef.SetRestricoes( "" );
		clientesitef.IniciaFuncaoSiTefInterativo();
		
		return requestsale;
	}
	
	public static void main( String[] args ) {
		
		DedicateTef tef = DedicateTef.getInstance();
		
		tef.requestSale( new BigDecimal( "15.748" ), 123456, Calendar.getInstance().getTime(), "teste" );
		
		System.exit( 0 );
	}
}
