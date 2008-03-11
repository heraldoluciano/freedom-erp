/**		
 * Este programa é licenciado de acordo com a LGPL (Lesser General Public License), <br>
 * versão 2.1, Fevereiro de 1999 <br>
 * A LGPL deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <br>
 * Caso uma cópia da LGPL não esteja disponível junto com este Programa, você pode contatar <br>
 * o LICENCIADOR ou então pegar uma cópia em: <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative Commons</a> <br>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este programa é preciso estar de acordo com os termos da LGPL. <br>
 * <br>
 * <br>
 * A classe TextTef para Bandeira VISA.<br>
 * <br>
 * <br>
 * @author Setpoint Informática Ltda. / Alex Rodrigues <br>
 * @version 1.0 (beta), 05/03/2008 <br>
 * <br>
 * @see org.freedom.tef.text.TextTef <br>
 * @see org.freedom.tef.Flag <br>
 */

package org.freedom.tef.driver.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class VisaTextTef extends TextTef  {
	
	private long indentification = 1;

	
	public VisaTextTef() {
		
		super();
	}
	
	public VisaTextTef( final TextTefProperties textTefProperties ) throws Exception {
		
		super( textTefProperties );
	}

	@Override
	protected void initializeTextTef() throws Exception {
		
		if ( getTextTefProperties() == null ) {
			throw new NullPointerException( "Properties para TEF não especificadas!" );
		}
		else {
			
			getTextTefProperties().setProperty( TextTefProperties.ARQ_TMP,      TextTefProperties.ARQ_TMP );
			getTextTefProperties().setProperty( TextTefProperties.ARQ_SEND,     TextTefProperties.ARQ_SEND );
			getTextTefProperties().setProperty( TextTefProperties.ARQ_RESPONSE, TextTefProperties.ARQ_RESPONSE );
			getTextTefProperties().setProperty( TextTefProperties.ARQ_ACTIVE,   TextTefProperties.ARQ_ACTIVE );
			getTextTefProperties().setProperty( TextTefProperties.ARQ_STATUS,   TextTefProperties.ARQ_STATUS );
		
        	fileTemp     = new File( getTextTefProperties().get( TextTefProperties.PATH_SEND ) + "/"
                                   + getTextTefProperties().get( TextTefProperties.ARQ_TMP ) ) ;
        	fileSend     = new File( getTextTefProperties().get( TextTefProperties.PATH_SEND ) + "/"
                                   + getTextTefProperties().get( TextTefProperties.ARQ_SEND ) ) ;
        	fileResponse = new File( getTextTefProperties().get( TextTefProperties.PATH_RESPONSE ) + "/"
                                   + getTextTefProperties().get( TextTefProperties.ARQ_RESPONSE ) ) ;
        	fileStatus   = new File( getTextTefProperties().get( TextTefProperties.PATH_RESPONSE ) + "/"
                                   + getTextTefProperties().get( TextTefProperties.ARQ_STATUS ) ) ;
        	fileActive   = new File( getTextTefProperties().get( TextTefProperties.PATH_RESPONSE ) + "/"
                                   + getTextTefProperties().get( TextTefProperties.ARQ_ACTIVE ) ) ;
        	
        	if ( ! isActive() ) {
        		throw new Exception( "Gerenciador tef não está ativo!" );
        	}
        	/*else if ( existActiveTEF( ADM, fileResponse, 1, null ) ) {
        		readReturn();
        		// nao confirmar();
        	}*/
		}
	}

	@Override
	protected boolean validateTextTefProperties( final TextTefProperties textTefProperties ) throws Exception {
		return true;
	}

	@Override
	public boolean isActive() throws Exception {
		
		boolean isActive = false;
		
		if ( fileActive == null
				|| ( ! fileActive.exists() || ! fileActive.canRead() ) ) {
			return isActive;
		}
	
		try {
			Properties properties = new Properties();
			properties.load( new FileInputStream( fileActive ) );
			if ( properties.getProperty( TextTefProperties.HEADER, "" ).equals( "TEF" ) ) {
				isActive = true;
				loadTextTefPropertie( properties );
			}
			properties.clear();
		} catch ( IOException e ) {
			throw new Exception( "Não foi possível verificar se o gerenciador TEF está ativo!", e );
		}
		
		return isActive;
	}

	@Override
	public boolean readResponseToPrint() throws Exception {
		return false;
	}

	@Override
	public boolean requestAdministrator() throws Exception {
		return false;
	}

	@Override
	public boolean requestCancel( final String nsu, 
								  final String rede, 
								  final Date data, 
								  final BigDecimal value ) throws Exception {
		return false;
	}

	@Override
	public boolean requestSale( final Integer numberDoc, 
			                    final BigDecimal value ) throws Exception {
		
		boolean actionReturn = false;		
		final long indentification = this.indentification++;
		final DecimalFormat df = new DecimalFormat( "0.00" );
		
		List<String> request = new ArrayList<String>();
		
		request.add( TextTefProperties.HEADER          + " = " + TextTef.CRT );
		request.add( TextTefProperties.INDENTIFICATION + " = " + indentification );
		request.add( TextTefProperties.DOCUMENT        + " = " + numberDoc );
		request.add( TextTefProperties.VALUE           + " = " + df.format( value ) );
		request.add( TextTefProperties.EOT             + " = " + 0 );
		
		actionReturn = send( request );

		if ( ! actionReturn
				|| ! existFileStatus( TextTef.CRT, indentification )
					|| ! existFileResponse( TextTef.CRT, indentification ) ) {
			actionReturn = false;
		}
		else {
			readReturn();
		}
		
		return actionReturn;
	}

	private void loadTextTefPropertie( final File file ) throws Exception {
		
		if ( file != null ) {
			
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream( file );
			properties.load( fis );
	        fis.close();
	        loadTextTefPropertie( properties );
		}
	}

	private void loadTextTefPropertie( final Properties properties ) {
		
		if ( properties == null ) {
			return;
		}
		else {
			for ( String key : TextTefProperties.getKeyList() ) {
				if ( TextTefProperties.PATH_SEND.equals( key ) ||
					 TextTefProperties.PATH_RESPONSE.equals( key ) ||
					 TextTefProperties.ARQ_TMP.equals( key ) ||
					 TextTefProperties.ARQ_SEND.equals( key ) ||
					 TextTefProperties.ARQ_RESPONSE.equals( key ) || 
					 TextTefProperties.ARQ_ACTIVE.equals( key ) ||
					 TextTefProperties.ARQ_STATUS.equals( key ) ) {
					continue;
				}
				else {
					getTextTefProperties().set( key, properties.getProperty( key, "" ) );
				}
			}
		}
	}
	
	private boolean send( final List<String> send ) throws Exception {
		
		if ( send == null ) {
			return false;
		}
		
	    boolean actionReturn = false;
	
		if ( fileTemp.exists() ) {
			fileTemp.delete();
		}
		try {
			
			PrintStream printStream = new PrintStream( new FileOutputStream( fileTemp ) );
			
			for ( String s : send ) {
				printStream.println( s );
			}
			
			printStream.close();						
			fileTemp.renameTo( fileSend );	
			
			actionReturn = true;
			
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return actionReturn;
	}

	//Se não existe linhas para imprimir então apaga o arquivo,
	//caso contrário só irá apagar o arquivo na confirmação.
	private void readReturn() throws Exception {
		
	    loadTextTefPropertie( fileResponse );
	    
	    if ( Integer.parseInt( 
	    		getTextTefProperties().getProperty( TextTefProperties.AMOUNT_LINES, "0") ) == 0 ) {
	    	fileResponse.delete();
	    }
	
	    fileStatus.delete();
	}

	private boolean existFile( final int changes, 
							   final File file, 
							   final String header, 
							   final long indentification ) throws Exception {
		
	    boolean existFile = false;
	    int c = changes;
	    do {			
			Thread.sleep( 1000 );
			System.out.println( "Chances = " + c );
		} while ( c-- > 0 ); {
			if ( file.exists() && file.canRead() ) {
				Properties properties = new Properties();
				FileInputStream fileInputStream = new FileInputStream( file );
				properties.load( fileInputStream );
				fileInputStream.close();
				if ( properties.getProperty( TextTefProperties.HEADER, "" ).equals( header )
						&& ( ( indentification > 0 && 
							   properties.getProperty( TextTefProperties.INDENTIFICATION, "" )
							  		.equals( String.valueOf( indentification ) ))
							 || indentification == 0 ) ) {
					existFile = true;
					c = 0;
					System.out.println( "Encontrou " + file.getName() );
				}
				properties.clear();
				System.out.println( "Não encontrou " + file.getName() );
			}
		}
	    
	    return existFile;
	}

	private boolean existFileStatus( final String header, final long indentification ) throws Exception {
		
	    return existFile( 7, fileStatus, header, indentification );
	}

	private boolean existFileResponse( final String header, final long indentification ) throws Exception {
		
		return existFile( 180, fileResponse, header, indentification );
	}
}
