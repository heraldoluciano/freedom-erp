package org.freedom.tef.driver.dedicate;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.freedom.infra.util.ini.ManagerIni;

import SoftwareExpress.SiTef.jCliSiTefI;
	

public class DedicatedTef {
	
	public static final String ENDERECO_TCP = "ENDERECO_TCP";
	
	public static final String EMPRESA = "EMPRESA";
	
	public static final String TERMINAL = "TERMINAL";
	
	private Properties properties;
	
	private jCliSiTefI clientesitef;
	
	private DedicatedTefListener dedicateTefListener;
	
	private static DedicatedTef instance;
	
	
	public static DedicatedTef getInstance( DedicatedTefListener dedicateTefListener ) throws Exception {

		if ( instance == null ) {
			instance = new DedicatedTef( dedicateTefListener );
		}
		
		return instance;
	}
	
	public static DedicatedTef getInstance( String file, DedicatedTefListener dedicateTefListener ) throws Exception {
		
		return getInstance( new File( file ), dedicateTefListener );
	}
	
	public static DedicatedTef getInstance( File file, DedicatedTefListener dedicateTefListener ) throws Exception {

		if ( file.exists() ) {
			instance = new DedicatedTef( file, dedicateTefListener );
		}
		else {
			instance = null;
		}
		
		return instance;
	}
	
	public DedicatedTefListener getDedicateTefListener() {	
		return dedicateTefListener;
	}
	
	private DedicatedTef( DedicatedTefListener dedicateTefListener ) throws Exception {

		this( new File( System.getProperty( ManagerIni.FILE_INIT_DEFAULT ) ), dedicateTefListener );
	}
	
	private DedicatedTef( File file, DedicatedTefListener dedicateTefListener ) throws Exception {
		
		if ( file == null || !file.exists() ) {
			throw new IllegalArgumentException( "Arquivo de parametros não existente." );
		}
		if ( dedicateTefListener == null ) {
			throw new IllegalArgumentException( "Ouvinte de eventos nulo." );
		}

		ManagerIni mi = ManagerIni.createManagerIniFile( file );
		properties = mi.getSession( "DedicateTef" );
		
		this.dedicateTefListener = dedicateTefListener;
		
		clientesitef = new jCliSiTefI();		  
		
		int r = clientesitef.ConfiguraIntSiTefInterativo( properties.getProperty( ENDERECO_TCP ),
				                                          properties.getProperty( EMPRESA ),
				                                          properties.getProperty( TERMINAL ) );
		
		if ( ! checkConfiguraIntSiTefInterativo( r ) ) {
			throw new Exception( "Erro ao configurar tef + [ ConfiguraIntSiTefInterativo() : " + r + " ]" );
		} 
	}	
	
	private boolean checkConfiguraIntSiTefInterativo( int result ) {
		
		boolean checked = false;

		check : {
			if ( result == 1 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Endereço IP inválido ou não resolvido." ) );
				break check;
    		} 
			else if ( result == 2 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Código da loja inválido." ) );
				break check;
    		} 
			else if ( result == 3 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Código do terminal inválido." ) );
				break check;
    		} 
			else if ( result == 6 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Erro na inicialização TCP/IP." ) );
				break check;
    		} 
			else if ( result == 7 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Falta de memória." ) );
				break check;
    		} 
			else if ( result == 8 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Não encontrou a dll CliSiTef ou ela está com problemas." ) );
				break check;
    		} 
			else {
				checked = true;
    		} 
		}
		
		if ( ! checked ) {
			System.out.println( "buffer= " + clientesitef.GetBuffer() );
		}

		return checked;
	}

	private boolean checkStandart( int result ) {
		
		boolean checked = false;
	
		check : {
			if ( result > 0 && result < 10000 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Negada pelo autorizador." ) );
				break check;
			} 
			else if ( result == -1 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Modulo não inicializado." ) );
				break check;
			} 
			else if ( result == -2 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Operação cancelada pelo operador." ) );
				break check;
			} 
			else if ( result == -3 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Fornecida uma modalidade inválida." ) );
				break check;
			} 
			else if ( result == -4 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Falta de memória para rodar a função." ) );
				break check;
			} 
			else if ( result == -5 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Sem comunicação com o SiTef." ) );
				break check;
			} 
			else if ( result < 0 ) {
				dedicateTefListener.actionCommand( 
						new DedicatedTefEvent( dedicateTefListener, DedicatedAction.ERRO,
								              "Erro interno não mapeado." ) );
				break check;
			} 
			else {
				checked = true;
			} 
		}
		
		if ( ! checked ) {
			System.out.println( "buffer= " + clientesitef.GetBuffer() );
		}
	
		return checked;
	}

	public boolean checkPinPad() {
		
		int result = clientesitef.VerificaPresencaPinPad();
		
		return result == 1;
	}
	
	public boolean readCard( String message ) {
		
		clientesitef.SetMsgDisplay( message );
		int result = clientesitef.LeCartaoInterativo();
		
		return checkStandart( result );
	}
	
	public boolean requestSale( BigDecimal value, Integer docNumber, Date dateHour, String operator ) {
		
		boolean requestsale = false;
		
		DecimalFormat df = new DecimalFormat( "0.00" );
		SimpleDateFormat sdf1 = new SimpleDateFormat( "yyyyMMdd", Locale.getDefault() );
		SimpleDateFormat sdf2 = new SimpleDateFormat( "HHmmss", Locale.getDefault() );
		String date = sdf1.format( dateHour );
		String hour = sdf2.format( dateHour );
		
		int result = clientesitef.IniciaFuncaoSiTefInterativo( Modality.DEBITO.getCode() ,
                                                        	   df.format( value.doubleValue() ) ,
                                                        	   String.valueOf( docNumber ) ,
                                                        	   date ,
                                                        	   hour ,
                                                        	   operator );
		
		if ( !checkStandart( result ) ) {
			//return requestsale;
		}
		
		while ( true ) {

			System.out.println( "Antes ..." );
			System.out.println( "ProximoComando = " + clientesitef.GetProximoComando() );
			System.out.println( "TipoCampo = " + clientesitef.GetTipoCampo() );
			System.out.println( "TamanhoMinimo = " + clientesitef.GetTamanhoMinimo() );
			System.out.println( "TamanhoMaximo = " + clientesitef.GetTamanhoMaximo() );
			System.out.println( "Buffer = " + clientesitef.GetBuffer() );
			
			result = clientesitef.ContinuaFuncaoSiTefInterativo();
			
			System.out.println( "Depois ..." );
			System.out.println( "ProximoComando = " + clientesitef.GetProximoComando() );
			System.out.println( "TipoCampo = " + clientesitef.GetTipoCampo() );
			System.out.println( "TamanhoMinimo = " + clientesitef.GetTamanhoMinimo() );
			System.out.println( "TamanhoMaximo = " + clientesitef.GetTamanhoMaximo() );
			System.out.println( "Buffer = " + clientesitef.GetBuffer() );
						
			if ( result == 0 ) {
				requestsale = true;
				break;
			}
			else if ( checkStandart( result ) ) {
				actionCommand( clientesitef.GetProximoComando() );
			}
			else {
				return false;
			}
		}

		return requestsale;
	}
	
	private void actionCommand( final int nextCommand ) {
		
		if ( dedicateTefListener != null ) {
    		if ( nextCommand == DedicatedAction.REMOVER_CABECALHO_MENU.code() ) {
    			dedicateTefListener.actionCommand( 
    					new DedicatedTefEvent( dedicateTefListener, DedicatedAction.REMOVER_CABECALHO_MENU, 
    							              clientesitef.GetBuffer().trim() ) );
    		} 				
		}
	}
}
