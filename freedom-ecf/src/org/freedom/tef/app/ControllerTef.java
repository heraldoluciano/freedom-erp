/**		
 * Este programa é licenciado de acordo com a LGPL (Lesser General Public License), <br>
 * versão 2.1, Fevereiro de 1999 <br>
 * A LGPL deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <br>
 * Caso uma cópia da LGPL não esteja disponível junto com este Programa, você pode contatar <br>
 * o LICENCIADOR ou então pegar uma cópia em: <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative Commons</a> <br>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este programa é preciso estar de acordo com os termos da LGPL. <br>
 * <br>
 * <br>
 * Classe para abstração da comunicação entre aplicativo e funções tef.<BR>
 * <br>
 * <br>
 * @author Setpoint Informática Ltda. / Alex Rodrigues <br>
 * @version 1.0 (beta), 05/03/2008 <br>
 * <br>
 * @see org.freedom.tef.text.TextTef <br>
 * @see org.freedom.tef.text.TextTefFactore <br>
 */

package org.freedom.tef.app;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.freedom.infra.components.LoggerManager;
import org.freedom.tef.driver.text.TextTef;
import org.freedom.tef.driver.text.TextTefFactore;
import org.freedom.tef.driver.text.TextTefProperties;

import static org.freedom.tef.driver.text.TextTefProperties.*;
import static org.freedom.tef.app.ControllerTefEvent.*;

public class ControllerTef {
	
	public static final int TEF_TEXT = 0;
	
	public static final int TEF_DEDICATED = 1;
	
	private File fileParametrosOfInitiation;
	
	private TextTefProperties defaultTextTefProperties;
	
	private int typeTef = TEF_TEXT; // default TEF text.
	
	private Logger logger;
	
	private ControllerTefListener controllerTefListeners;
	
	
	public ControllerTef() {		

		super();
		
		try {
			logger = LoggerManager.getLogger( "log/freedomTEF.log" );
		} catch ( RuntimeException e ) {
			e.printStackTrace();
		}
	}
	
	public ControllerTef( final TextTefProperties defaultTextTefProperties, 
			              final File fileParametrosOfInitiation,
			              final int typeTef ) throws Exception {
		this();
		
		initializeControllerTef( defaultTextTefProperties, fileParametrosOfInitiation, typeTef );
	}	
	
	public void initializeControllerTef( final TextTefProperties textTefProperties, 
	                                     final File fileParametrosOfInitiation,
	                                     final int typeTef ) throws Exception {
		
		setDefaultTextTefProperties( textTefProperties );
		setFileParametrosOfInitiation( fileParametrosOfInitiation );
		setTypeTef( typeTef );
	}

	private TextTefProperties getTextTefProperties() {	
		
		final TextTefProperties textTefProperties = new TextTefProperties();
		
		textTefProperties.set( PATH_SEND, getDefaultTextTefProperties().get( PATH_SEND ) );
		textTefProperties.set( PATH_RESPONSE, getDefaultTextTefProperties().get( PATH_RESPONSE ) );
		
		return textTefProperties;
	}

	private TextTefProperties getDefaultTextTefProperties() {			
		return this.defaultTextTefProperties;
	}
	
	public void setDefaultTextTefProperties( final TextTefProperties defaultTextTefProperties ) {
	
		if ( defaultTextTefProperties == null ) {
			throw new NullPointerException( "TextTefProperties não especificado!" );
		}
		
		this.defaultTextTefProperties = defaultTextTefProperties;
	}
	
	public File getFileParametrosOfInitiation() {	
		return fileParametrosOfInitiation;
	}
	
	public void setFileParametrosOfInitiation( final File fileParametrosOfInitiation ) {	
		
		if ( fileParametrosOfInitiation == null ) {
			throw new NullPointerException( "Parametros de inicialização não especificados!" );
		}
		
		this.fileParametrosOfInitiation = fileParametrosOfInitiation;
	}

	public int getTypeTef() {	
		return typeTef;
	}
	
	public void setTypeTef( final int typeTef ) throws IllegalArgumentException {
		
		if ( TEF_TEXT == typeTef || TEF_DEDICATED == typeTef ) {
			this.typeTef = typeTef;
		}
		else {
			throw new IllegalArgumentException( "Tipo de gerenciamento de TEF inválido!" );
		}		
	}
	
	public ControllerTefListener setControllerMessageListener( final ControllerTefListener listener ) {
		
		return this.controllerTefListeners = listener;
	}
	
	private boolean fireControllerTefEvent( final int option ) {
		return fireControllerTefEvent( option, null );
	}
	
	private boolean fireControllerTefEvent( final int option, 
			                                final String message ) {			
		boolean tefMessage = false;
		
		ControllerTefEvent controllerTefEvent = new ControllerTefEvent( this, option, message );
		
		if ( this.controllerTefListeners != null ) {
			tefMessage = this.controllerTefListeners.actionTef( controllerTefEvent );
		}
		
		return tefMessage;
	}

	private void whiterLogError( final String message ) {		
		if ( logger != null ) {
			logger.error( message );
		}
	}
	
	// ************************************************************************** \\
	// ***                                                                    *** \\
	// ***                   Implementação das funções de TEF                 *** \\
	// ***                                                                    *** \\	
	// ************************************************************************** \\
	
	/**
	 * Este método faz a requisição de pagamento para o gerenciador padrão TEF<br>
	 * atráves da criação do arquivo com os parametros da requisição e<br>
	 * verifica o recebimento da requisição atráves da leitura do arquivo de retorno<br>
	 * e transmite a mensagem caso haja para ControllerMessageListener com ControllerMessageEvent.<br>
	 * <br>
	 * Após a execução deste é necessária a execução da impressão do comprovante.
	 * 
	 * @param numberDoc
	 * @param value
	 * @param flagName
	 * 
	 * @return verdadeiro para envio correto da requisição e recebimento correto do retorno. 
	 */
	public boolean requestSale( final Integer numberDoc,
			                    final BigDecimal value,
			                    final String flagName ) {
		
		boolean actionReturn = false;
		
		if ( TEF_TEXT == getTypeTef() ) {
			actionReturn = requestSaleTextTef( numberDoc, value, flagName );
		}
		
		return actionReturn;
	}
	
	private boolean requestSaleTextTef( final Integer numberDoc,
                                        final BigDecimal value,
                                        final String flagName ) {
		boolean actionReturn = false;
		
		try {
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileParametrosOfInitiation() );	
			// verifica se está ativo e faz a requisição e
			// verifica se houve resposta.
			if ( textTef.isActive() 
					&& textTef.requestSale( numberDoc, value ) 
						&& textTef.readResponseSale() ) {
					
				String messageOperator = textTef.get( MESSAGE_OPERATOR, "" );
				
				// avisa para os ouvintes a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( WARNING, messageOperator );
				}
				
				// invoca método para lançar eventos de impressão do comprovante.
				if ( voucherTextTef( textTef ) ) {
					actionReturn = textTef.confirmationOfSale();
				}
			}
			else {
				fireControllerTefEvent( END_PRINT, "TEF não está ativo!" );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			String etmp = "Erro ao solicitar venda:\n" + e.getMessage();
			fireControllerTefEvent( END_PRINT, etmp );
			whiterLogError( etmp );
		}
		
		return actionReturn;
	}
	
	private boolean voucherTextTef( final TextTef textTef ) throws Exception {

		boolean voucherTextTef = false;
		
		if ( textTef != null ) {
		
    		int amountLines = Integer.parseInt( textTef.get( AMOUNT_LINES, "0" ) );
    		
    		// verifica a quantidade de linhas do comprovante tef para invocar a impressão.
    		if ( amountLines > 0 ) {
    			
    			// lança os eventos de inicio e fim da impressão do comprovante tef,
    			// invocando o método printVoucherTextTef entre tais eventos.
    			if ( fireControllerTefEvent( BEGIN_PRINT )
    						&& printVoucherTextTef( textTef ) 
    								&& fireControllerTefEvent( END_PRINT ) ) {
    				voucherTextTef = true;
    			}
    		}
		}
		
		return voucherTextTef;
	}
		
	private boolean printVoucherTextTef( final TextTef textTef ) throws Exception {

		boolean actionReturn = false;
			
		// recupera a lista de linhas do comprovante tef.
		List<String> responseToPrint = textTef.getResponseToPrint();
		
		if ( responseToPrint != null && responseToPrint.size() > 0 ) {	
			
			int numberTickets = 1; // alterar para variável parametrizavél.

			tickets : while ( numberTickets-- > 0 ) {
				for ( String message : responseToPrint ) {
					
					// aciona evento para que, a aplicação
					// envie o comando de impressão para impressora fiscal.
					actionReturn = fireControllerTefEvent( PRINT, message );
					
					// em caso de falha na impressão o processo deve ser reiniciado
					// dependendo de, confirmação do operador, requisitada pela aplicação.
					if ( ! actionReturn ) {
						if ( fireControllerTefEvent( CONFIRM, "Impressora não responde, tentar novamente?" ) ) {
							fireControllerTefEvent( RE_PRINT );
							actionReturn = printVoucherTextTef( textTef );
							break tickets;
						}
						else {
							actionReturn = textTef.notConfirmationOfSale();
							break tickets;							
						}
					}
				}
			}
		}
		
		return actionReturn;
	}
}
