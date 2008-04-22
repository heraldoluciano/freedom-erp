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

import static org.freedom.tef.driver.text.TextTef.*;
import static org.freedom.tef.driver.text.TextTefProperties.*;
import static org.freedom.tef.app.ControllerTefEvent.*;

public class ControllerTef {
	
	public static final int TEF_TEXT = 0;
	
	public static final int TEF_DEDICATED = 1;
	
	private static final int VOUCHER_ERROR = -1;

	private static final int VOUCHER_NO = 0;

	private static final int VOUCHER_OK = 1;
	
	private File fileParametrosOfInitiation;
	
	private TextTefProperties defaultTextTefProperties;
	
	private int typeTef = TEF_TEXT; // default TEF text.
	
	private int numberOfTickets = 1;
	
	private Logger logger;
	
	private ControllerTefListener controllerTefListener;
	
	
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
	
	/**
	 * Inicializa as propriedades do objeto.
	 * 
	 * @param 	textTefProperties			Lista de propriedades
	 * @param 	fileParametrosOfInitiation	Arquivo de mapeamento de bandeiras.
	 * @param 	typeTef						Tipo de comunicação ( Texto ou Dedicado )
	 * 
	 * @throws 	Exception
	 */
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
			throw new NullPointerException( "Arquivo de parametros de inicialização inválido!" );
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
		
		return this.controllerTefListener = listener;
	}
	
	private boolean fireControllerTefEvent( final int option ) {
		return fireControllerTefEvent( option, null );
	}
	
	private boolean fireControllerTefEvent( final int option, 
			                                final String message ) {			
		boolean tefMessage = false;
		
		ControllerTefEvent controllerTefEvent = new ControllerTefEvent( this, option, message );
		
		if ( this.controllerTefListener != null ) {
			tefMessage = this.controllerTefListener.actionTef( controllerTefEvent );
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
	 * Este método faz a requisição de pagamento para o gerenciador padrão TEF. <br>
	 * 
	 * @param numberDoc		Número do documento.
	 * @param value			Valor da pagamento.
	 * @param flagName		Nome da bandeira.
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

	/**
	 * Este método faz a requisição das funções administrativas do gerenciador padrão TEF. <br>
	 * 
	 * @param flagName		Nome da bandeira.
	 * 
	 * @return verdadeiro para envio correto da requisição e recebimento correto do retorno. 
	 */
	public boolean requestAdministrator( final String flagName ) {
		
		boolean actionReturn = false;
		
		if ( TEF_TEXT == getTypeTef() ) {
			actionReturn = requestAdministratorTextTef( flagName );
		}
		
		return actionReturn;
	}
	
	/**
	 * Efetua a requisição de venda para TEF Texto,<br>
	 * atráves da criação do arquivo com os parâmetros da requisição e<br>
	 * verifica o recebimento da requisição atráves da leitura do arquivo de retorno<br>
	 * e transmite a mensagem caso haja para ControllerMessageListener com ControllerMessageEvent.<br>
	 * <br>
	 * 
	 * @see	#requestSale(Integer, BigDecimal, String)
	 * 
	 * @param numberDoc		Número do documento.
	 * @param value			Valor da pagamento.
	 * @param flagName		Nome da bandeira.
	 * 
	 * @return verdadeiro para envio correto da requisição e recebimento correto do retorno.
	 */
	private boolean requestSaleTextTef( final Integer numberDoc,
                                        final BigDecimal value,
                                        final String flagName ) {
		boolean actionReturn = false;
		
		try {
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileParametrosOfInitiation() );	
			if ( ! standardManagerActive( textTef ) ) {
				return actionReturn;
			}
						
			if ( textTef.requestSale( numberDoc, value ) 
					&& textTef.readResponse( CRT ) ) {
					
				String messageOperator = textTef.get( MESSAGE_OPERATOR, "" );
				
				// avisa para os ouvintes a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( WARNING, messageOperator );
				}
				
				// invoca método para lançar eventos de impressão do comprovante.
				if ( voucherTextTef( textTef ) == VOUCHER_OK ) {
					actionReturn = textTef.confirmation();
				}
				else {
					actionReturn = noConfirmation( textTef );					
				}
			}
			
		} catch ( Exception e ) {
			String etmp = "Erro ao solicitar venda:\n" + e.getMessage();
			fireControllerTefEvent( ERROR, etmp );
			whiterLogError( etmp );
		}
		
		return actionReturn;
	}
	
	/**
	 * Efetua a requisição das funções administrativas do gerenciador padrão de TEF.<br>
	 * 
	 * @see	#requestAdministrator(String)
	 * 
	 * @param flagName		Nome da bandeira.
	 * 
	 * @return verdadeiro para envio correto da requisição e recebimento correto do retorno.
	 */
	private boolean requestAdministratorTextTef( final String flagName ) {
		
		boolean actionReturn = false;
		
		try {
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileParametrosOfInitiation() );	
			if ( ! standardManagerActive( textTef ) ) {
				return actionReturn;
			}
						
			if ( textTef.requestAdministrator() 
					&& textTef.readResponse( ADM ) ) {
					
				String messageOperator = textTef.get( MESSAGE_OPERATOR, "" );
				
				// avisa para o ouvinte a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( WARNING, messageOperator );
				}
				
				// invoca método para lançar eventos de impressão do comprovante.
				int voucher = voucherTextTef( textTef );
				if ( voucher == VOUCHER_OK ) {
					actionReturn = textTef.confirmation();
				}
				else if ( voucher == VOUCHER_ERROR ) {
					actionReturn = noConfirmation( textTef );					
				}
			}
			
		} catch ( Exception e ) {
			String etmp = "Erro ao acionar ADM:\n" + e.getMessage();
			fireControllerTefEvent( ERROR, etmp );
			whiterLogError( etmp );
		}
		
		return actionReturn;
	}
	
	/**
	 * Verifica se o gerenciador padrão de TEF está ativo,<br>
	 * do contrário envia menssagem para ouvinte.<br>
	 * 
	 * @param 	textTef		Objeto de TEF.
	 * 
	 * @return	Verdadeiro para gerenciador padrão ativo.
	 * 
	 * @throws 	Exception
	 */
	private boolean standardManagerActive( final TextTef textTef ) throws Exception {
		
		boolean active = true;
		
		if ( textTef != null && ! textTef.standardManagerActive() ) {
			active = false;
			fireControllerTefEvent( ERROR, "TEF não está ativo!" );
		}
		
		return active;
	}
	
	/**
	 * Envia comando de não confirmação da transação TEF, e envia menssagem da não confirmação para o ouvinte.<br>
	 * O não envio do comando de confirmação ou não confirmação deixa a transação em estado pendente.<br>
	 * 
	 * @param 	textTef		Objeto de TEF.
	 * 
	 * @return	Verdadeiro para envio correto da não confirmação.
	 * 
	 * @throws Exception
	 */
	private boolean noConfirmation( final TextTef textTef ) throws Exception {
		
		boolean active = false;
		
		if ( textTef != null ) {
			fireControllerTefEvent( WARNING, "Transação não efetuada. Favor reter o Cupom." );
			active = textTef.noConfirmation();
		}
		
		return active;
	}
	
	/**
	 * Verifica as condições para impressão do comprovante de TEF.
	 * 
	 * @param textTef	Objeto de TEF.
	 * 
	 * @return 	Indice da situação do comprovante:<br>
	 * 			VOUCHER_NO : 	caso não ouver menssagem para impressão ( AMOUNT_LINES == 0 )<br>
	 * 			VOUCHER_OK : 	voucherTextTefAux(TextTef) verdadeiro<br>
	 * 			VOUCHER_ERROR : voucherTextTefAux(TextTef) false<br>
	 * 
	 * @see		#voucherTextTefAux(TextTef)
	 * 
	 * @throws 	Exception
	 */
	private int voucherTextTef( final TextTef textTef ) throws Exception {

		int voucherTextTef = VOUCHER_NO;
		
		if ( textTef != null ) {
    		
        	int amountLines = Integer.parseInt( textTef.get( AMOUNT_LINES, "0" ) );
        		
    		// verifica a quantidade de linhas do comprovante tef para invocar a impressão.
    		if ( amountLines > 0 ) {
    			voucherTextTef = voucherTextTefAux( textTef ) ? VOUCHER_OK : VOUCHER_ERROR;
    		}
		}    			
		
		return voucherTextTef;
	}
	
	/**
	 * Auxiliar a {@link #voucherTextTef(TextTef)},<br>
	 * tratando da lógica de acionamento de eventos de impressão.<br>
	 * 
	 * @param textTef	Objeto TEF.
	 * 
	 * @return 	Verdadeiro para correta impressão do comprovante,<br>
	 * 			esta condição de verdadeira é devolvida, pela aplicação, através de eventos.<br>
	 * 
	 * @see		#voucherTextTef(TextTef)
	 * @see		#printVoucherTextTef(TextTef)
	 * @see		#fireControllerTefEvent(int)
	 * @see		#fireControllerTefEvent(int, String)
	 * 
	 * @throws 	Exception
	 */
	private boolean voucherTextTefAux( final TextTef textTef ) throws Exception {
	
		// Testa começo da impressão até o sucesso,
		// e tenta a retomado do processo, ápos confirmação da aplicação,
		// do contrário para o processo.
		boolean beginPrint = fireControllerTefEvent( BEGIN_PRINT );
		while ( !beginPrint ) {
			if ( fireControllerTefEvent( CONFIRM, "Impressora não responde, tentar novamente?" ) ) {
				beginPrint = fireControllerTefEvent( BEGIN_PRINT );
			} else {
				return false;
			}
		}

		// Testa impressão até o sucesso,
		// e tenta a retomado do processo, ápos confirmação da aplicação,
		// do contrário para o processo.
		boolean printVoucher = printVoucherTextTef( textTef );
		while ( !printVoucher ) {
			if ( fireControllerTefEvent( CONFIRM, "Impressora não responde, tentar novamente?" ) ) {
				if ( fireControllerTefEvent( RE_PRINT ) ) {
					printVoucher = printVoucherTextTef( textTef );
				}
			} else {
				return false;
			}
		}

		// Testa final impressão até o sucesso,
		// e tenta a retomado do processo, ápos confirmação da aplicação,
		// do contrário para o processo.
		boolean endPrint = fireControllerTefEvent( END_PRINT );
		while ( !endPrint ) {
			if ( fireControllerTefEvent( CONFIRM, "Impressora não responde, tentar novamente?" ) ) {
				if ( fireControllerTefEvent( RE_PRINT ) && 
						printVoucherTextTef( textTef ) ) {
					endPrint = fireControllerTefEvent( END_PRINT );
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
		
	/**
	 * Executa o acinamento da impressão do conteudo do comprovante TEF.
	 * 
	 * @param textTef	Objetc TEF
	 * 
	 * @return 	Verdadeiro para correta impressão do comprovante,<br>
	 * 			esta condição de verdadeira é devolvida, pela aplicação, através de eventos.<br>
	 * 
	 * @see		#voucherTextTefAux(TextTef)
	 * @see		#fireControllerTefEvent(int)
	 * @see		#fireControllerTefEvent(int, String)
	 * 
	 * @throws 	Exception
	 */
	private boolean printVoucherTextTef( final TextTef textTef ) throws Exception {

		boolean actionReturn = false;
			
		// recupera a lista de linhas do comprovante tef.
		List<String> responseToPrint = textTef.getResponseToPrint();
		
		if ( responseToPrint != null && responseToPrint.size() > 0 ) {	
			
			int nt = this.numberOfTickets;

			tickets : while ( nt-- > 0 ) {
				for ( String message : responseToPrint ) {
					
					// aciona evento para que, a aplicação
					// envie o comando de impressão para impressora fiscal.
					actionReturn = fireControllerTefEvent( PRINT, message );
					
					if ( ! actionReturn ) {
						break tickets;	
					}
				}
			}
		}
		
		return actionReturn;
	}
}
