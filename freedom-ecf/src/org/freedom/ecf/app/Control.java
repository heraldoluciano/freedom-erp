package org.freedom.ecf.app;

import static org.freedom.ecf.driver.EStatus.RETORNO_OK;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.freedom.ecf.com.Serial;
import org.freedom.ecf.driver.AbstractECFDriver;
import org.freedom.infra.components.LoggerManager;

public class Control {
	
	private boolean modoDemostracao;	
	
	private AbstractECFDriver ecf;
	
	private List<String> aliquotas;
	
	private String messageLog;
	
	private Logger logger;
	
	
	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * com a porta default Serial.COM1 e o estado de modo demostração false.<br>
	 * 
	 * @see org.freedom.ecf.com.Serial#COM1
	 * @param ecfdriver
	 *            nome do driver de comunicação serial.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver não consiga ser instânciado.
	 */
	public Control( final String ecfdriver ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, Serial.COM1, false );
	}

	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * com a porta default Serial.COM1<br>
	 * 
	 * @see org.freedom.ecf.com.Serial#COM1
	 * @param ecfdriver
	 *            nome do driver de comunicação serial.
	 * @param mododemostracao
	 *            estado de modo demostração.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver não consiga ser instânciado.
	 */
	public Control( final String ecfdriver, final boolean mododemostracao ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, Serial.COM1, mododemostracao );
	}

	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * com o estado de modo demostração false.<br>
	 * 
	 * @param ecfdriver
	 *            nome do driver de comunicação serial.
	 * @param porta
	 *            porta serial.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver não consiga ser instânciado.
	 */
	public Control( final String ecfdriver, final int porta ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, porta, false );
	}

	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * com o estado de modo demostração false e convertendo o nome da porta para a constante indicativa.<br>
	 * 
	 * @see org.freedom.ecf.com.Serial#convPorta(int)
	 * @param ecfdriver
	 *            nome do driver de comunicação serial.
	 * @param porta
	 *            nome da porta serial.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver não consiga ser instânciado.
	 */
	public Control( final String ecfdriver, final String porta ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, Serial.convPorta( porta ), false );
	}

	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * convertendo o nome da porta para a constante indicativa.<br>
	 * 
	 * @see org.freedom.ecf.com.Serial#convPorta(int)
	 * @param ecfdriver
	 *            nome do driver de comunicação serial.
	 * @param porta
	 *            nome da porta serial.
	 * @param mododemostracao
	 *            estado de modo demostração.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver não consiga ser instânciado.
	 */
	public Control( final String ecfdriver, final String porta, final boolean mododemostracao ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, Serial.convPorta( porta ), mododemostracao );
	}

	/**
	 * Contrutor de classe.<br>
	 * Valida o nome do driver de comunicação serial e instancia a classe com este nome<br>
	 * abrindo a porta serial e definindo o estado de modo demonstração.<br>
	 * 
	 * @param ecfdriver
	 *            nome do driver de comunicação serial.
	 * @param porta
	 *            porta serial
	 * @param mododemostracao
	 *            estado de modo demostração.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver não consiga ser instânciado.
	 */
	public Control( final String ecfdriver, final int porta, final boolean mododemostracao ) 
		throws IllegalArgumentException, NullPointerException {

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
					"Não foi possível carregar o driver da impressora.\n" 
					+ ecfdriver );
		}

		setModoDemostracao( mododemostracao );
		
		try {
			logger = LoggerManager.getLogger( "log/freedom-ecf.log" );
		} catch ( RuntimeException e ) {
			e.printStackTrace();
		}
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
	
	private void setAliquotas() {
		this.aliquotas = getAllAliquotas();
	}
	
	public List<String> getAliquotas() {
		
		List<String> returnlist = null;
		
		if ( this.aliquotas != null ) {
			returnlist = new ArrayList<String>();
			Collections.copy( this.aliquotas, returnlist );
		}
		
		return returnlist;
	}
	
	public void setMessageLog( final String message ) {
		this.messageLog = message;
	}
	
	public String getMessageLog() {
		return this.messageLog;
	}

	public boolean decodeReturn( final int arg ) {

		boolean returnOfAction = true;

		setMessageLog( RETORNO_OK.getMessage() );
		String str = ecf.decodeReturnECF( arg ).getMessage();

		if ( ! RETORNO_OK.getMessage().equals( str ) ) {
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
			returnOfAction = 
				saidaSerial ? decodeReturn( ecf.leituraXSerial() ) : decodeReturn( ecf.leituraX() );
			if ( ! returnOfAction ) {
				whiterLogError( "[LEITURA X] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean suprimento( final BigDecimal valor ) {
		
		return suprimento( valor, "Dinheiro" );
	}
	
	public boolean suprimento( final BigDecimal valor, String formaDePagamento ) { 

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			if ( valor != null && valor.floatValue() > 0f ) {
				BigDecimal valorsuprimento = valor.setScale( 2, BigDecimal.ROUND_HALF_UP );
				if ( formaDePagamento == null || formaDePagamento.trim().length() == 0 ) {
					formaDePagamento = "Dinheiro";
				}
				returnOfAction = decodeReturn( 
						ecf.comprovanteNFiscalNVinculado( 
								AbstractECFDriver.SUPRIMENTO, 
								valorsuprimento.floatValue(), 
								formatString( formaDePagamento, 16 ) ) );
			}
			if ( !returnOfAction ) {
				whiterLogError( "[SUPRIMENTO] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean abreCupom() {
		
		return abreCupom( null );
	}
	
	public boolean abreCupom( final String cnpj_cpf ) {
		
		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			returnOfAction = cnpj_cpf != null && cnpj_cpf.trim().length() > 0 
					? decodeReturn( ecf.aberturaDeCupom( cnpj_cpf ) ) 
							: decodeReturn( ecf.aberturaDeCupom() );
			if ( !returnOfAction ) {
				whiterLogError( "[ABERTURA DE CUPOM] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean unidadeMedida( final String unidade ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			if ( unidade != null && unidade.trim().length() > 0 ) {
				returnOfAction = decodeReturn( ecf.programaUnidadeMedida( unidade ) );
				if ( !returnOfAction ) {
					whiterLogError( "[UNIDADE DE MEDIDA] " );
				}
			} 
			else {
				returnOfAction = false;
				setMessageLog( "unidade de medida inválida." );
				whiterLogError( "[UNIDADE DE MEDIDA] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean aumetaDescricaoItem( final String descricao ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			if ( descricao != null && descricao.trim().length() > 0 ) {
				returnOfAction = decodeReturn( ecf.aumentaDescItem( tiraAcentos( descricao ) ) );
				if ( !returnOfAction ) {
					whiterLogError( "[AUMENTA DESCRIÇÂO] " );
				}
			} 
			else {
				returnOfAction = false;
				setMessageLog( "descrição do item inválida." );
				whiterLogError( "[AUMENTA DESCRIÇÂO] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean vendaItem (
			final String codigo, 
			final String descricao, 
			BigDecimal aliquota, 
			BigDecimal quantidade, 
			BigDecimal valor ) {
		
		return vendaItem( 
				codigo, 
				descricao, 
				aliquota, 
				quantidade,
				valor,
				new BigDecimal( "0" ) );
	}
	
	public boolean vendaItem (
			final String codigo, 
			final String descricao, 
			BigDecimal aliquota, 
			BigDecimal quantidade, 
			BigDecimal valor, 
			BigDecimal desconto ) {
		
		return vendaItem( 
				codigo, 
				descricao, 
				aliquota, 
				AbstractECFDriver.TP_QTD_DECIMAL, 
				quantidade, 
				AbstractECFDriver.DUAS_CASAS_DECIMAIS, 
				valor, 
				AbstractECFDriver.TP_DESC_VALOR, 
				desconto );
	}
	
	public boolean vendaItem( 
			final String codigo, 
			final String descricao, 
			BigDecimal aliquota, 
			final int tipoQuantidade,
			BigDecimal quantidade, 
			final int casasDecimais,
			BigDecimal valor, 
			final int tipoDesconto,
			BigDecimal desconto ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			boolean actionOK = true;
			String strAliquota = null;
			if ( codigo == null || codigo.trim().length() == 0 ) {
				setMessageLog( "Código do produto inválido.[" + codigo +"]" );
				actionOK = false;
			}
			else if ( descricao == null || descricao.trim().length() == 0 ) {
				setMessageLog( "Descrição do item inválida.[" + descricao +"]" );
				actionOK = false;
			}
			else if ( aliquota == null || aliquota.floatValue() <= 0.0f ) {
				setMessageLog( "Aliquota inválida.[" + aliquota +"]" );
				actionOK = false;
			}
			else if ( ( strAliquota = getIndexAliquota( aliquota.floatValue() ) ) == null ) {
				setMessageLog( "Aliquota não encontrada.[" + aliquota +"]" );
				actionOK = false;
			}
			else if ( quantidade == null || quantidade.floatValue() <= 0.0f 
					|| ( tipoQuantidade == AbstractECFDriver.TP_QTD_INTEIRO && quantidade.floatValue() > 9999f )
					|| ( tipoQuantidade == AbstractECFDriver.TP_QTD_DECIMAL && quantidade.floatValue() > 9999.9994f ) ) {
				setMessageLog( "Quantidade inválida.[" + quantidade +"]" );
				actionOK = false; 
			}
			else if ( valor == null || valor.floatValue() <= 0.0f
						|| valor.floatValue() > 999999.994f ) {
				setMessageLog( "Valor inválido.[" + valor +"]" );
				actionOK = false;
			}
			else if ( desconto == null || desconto.floatValue() < 0.0f
					|| ( tipoDesconto == AbstractECFDriver.TP_DESC_PERCENTUAL && desconto.floatValue() > 99.994f )
					|| ( tipoDesconto == AbstractECFDriver.TP_DESC_VALOR && desconto.floatValue() > 9999.9994f ) ) {
				setMessageLog( "Desconto inválido.[" + desconto +"]" );
				actionOK = false;
			}
			
			if ( actionOK ) {
				quantidade = tipoQuantidade == AbstractECFDriver.TP_QTD_INTEIRO 
								? new BigDecimal( quantidade.intValue() )
										: quantidade.setScale( 3, BigDecimal.ROUND_HALF_UP ) ;
				desconto = desconto.setScale( 2, BigDecimal.ROUND_HALF_UP );
				if ( casasDecimais == 3 ) {
					valor = valor.setScale( 3, BigDecimal.ROUND_HALF_UP );
					ecf.vendaItemTresCasas( 
							formatString( codigo, 13 ), 
							formatString( tiraAcentos( descricao ), 29 ), 
							strAliquota, 
							tipoQuantidade,
							quantidade.floatValue(), 
							valor.floatValue(),
							tipoDesconto,
							desconto.floatValue() );	
				}
				else {
					valor = valor.setScale( 2, BigDecimal.ROUND_HALF_UP );
					ecf.vendaItemTresCasas( 
							formatString( codigo, 13 ), 
							formatString( tiraAcentos( descricao ), 29 ), 
							strAliquota, 
							tipoQuantidade,
							quantidade.floatValue(), 
							valor.floatValue(), 
							tipoDesconto,
							desconto.floatValue() );	
				}
			}
			else {
				returnOfAction = false;
				whiterLogError( "[VENDA DE ITEM] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean sangria( final BigDecimal valor ) {
		
		return sangria( valor, "Dinheiro" );
	}
	
	public boolean sangria( final BigDecimal valor, String formaDePagamento ) { 

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			if ( valor != null && valor.floatValue() > 0f ) {
				BigDecimal valorsangria = valor.setScale( 2, BigDecimal.ROUND_HALF_UP );
				if ( formaDePagamento == null || formaDePagamento.trim().length() == 0 ) {
					formaDePagamento = "Dinheiro";
				}
				returnOfAction = decodeReturn( 
						ecf.comprovanteNFiscalNVinculado( 
								AbstractECFDriver.SANGRIA, 
								valorsangria.floatValue(), 
								formatString( formaDePagamento, 16 ) ) );
			}
			if ( !returnOfAction ) {
				logger.error( "[SANGRIA] " + getMessageLog() );
			}
		}
		
		return returnOfAction;
	}
	
	public List<String> getAllAliquotas() {

		List<String> returnOfAction = new ArrayList<String>();
		
		if ( notIsModoDemostracao() ) {	
			String sAliquotas = ( ecf.retornoAliquotas() ).trim();
			int tamanho = 0;
			if ( sAliquotas != null && sAliquotas.trim().length() > 2 ) {
				tamanho = Integer.parseInt( sAliquotas.trim().substring( 0, 2 ) );				
			}			
			String tmp = sAliquotas.trim().substring( 2 );
			for ( int i=0; i < tamanho; i++ ) {				
				returnOfAction.add( tmp.substring( i * 4, ( i * 4 ) + 4 ) );				
			}
		}
		
		return returnOfAction;
	}
	
	public String getIndexAliquota( final float arg ) {
		
		String indexAliquota = null;
		
		if ( arg > 0.0f && arg < 99.99f ) {
			if ( this.aliquotas == null ) {
				setAliquotas();
			}
			String tmp = ecf.floatToString( arg, 4, 2 );
			int index = 1;
			for ( String s : this.aliquotas ) {
				if ( s.equals( tmp ) ) {
					indexAliquota = ecf.strZero( String.valueOf( index ), 2 );
					break;
				}
				index++;
			}
		}
		
		return indexAliquota;
	}

	/**
	 * Realiza a leitura da porta serial para capturar os dados retornados na mesma.<br>
	 * 
	 * @return leitura da porta serial.
	 */
	public String readSerialPort() {

		String strReturn = null;

		if ( notIsModoDemostracao() ) {
			strReturn = new String( ecf.getBytesLidos() );
		}

		return strReturn;
	}
	
	/**
	 * Formata uma String complentando com espaços em branco até<br>
	 * que o tamanho string seja o passado por parametro.<br>
	 * 
	 * @param arg0
	 *            String a ser formatada.
	 * @param arg1
	 *            tamanho da string formatada.
	 * @return string formatada.
	 */
	private String formatString( final String arg0, final int arg1 ) {
		
		final StringBuilder buffer = new StringBuilder();
		String str = "";		
		if ( arg0 != null ) {
			str = arg0;
		}	
		final int i = arg1 - str.length();		
		buffer.append( arg0 );
		for ( int j=0; j < i; j++ ) {
			buffer.append( " " );
		}
		
		return buffer.toString();
	}
	
	private void whiterLogError( final String actionName ) {
		
		if ( logger != null ) {
			logger.error( actionName + getMessageLog() );
		}
	}
	
	// funções que devem ser esternas.

	public String tiraAcentos( String sTexto ) {

		String sRet = "";
		char cVals[] = sTexto.toCharArray();
		for ( int i = 0; i < cVals.length; i++ ) {
			cVals[ i ] = tiraAcento( cVals[ i ] );
		}
		sRet = new String( cVals );
		
		return sRet;
	}

	public char tiraAcento( char cKey ) {

		char cTmp = cKey;

		if ( contido( cTmp, "ãâáà" ) ) {
			cTmp = 'a';
		}
		else if ( contido( cTmp, "ÃÂÁÀ" ) ) {
			cTmp = 'A';
		}
		else if ( contido( cTmp, "êéè" ) ) {
			cTmp = 'e';
		}
		else if ( contido( cTmp, "ÊÉÈ" ) ) {
			cTmp = 'E';
		}
		else if ( contido( cTmp, "îíì" ) ) {
			cTmp = 'i';
		}
		else if ( contido( cTmp, "ÎÍÌ" ) ) {
			cTmp = 'I';
		}
		else if ( contido( cTmp, "õôóò" ) ) {
			cTmp = 'o';
		}
		else if ( contido( cTmp, "ÕÔÓÒ" ) ) {
			cTmp = 'O';
		}
		else if ( contido( cTmp, "ûúùü" ) ) {
			cTmp = 'u';
		}
		else if ( contido( cTmp, "ÛÚÙÜ" ) ) {
			cTmp = 'U';
		}
		else if ( contido( cTmp, "ç" ) ) {
			cTmp = 'c';
		}
		else if ( contido( cTmp, "Ç" ) ) {
			cTmp = 'C';
		}
		
		return cTmp;
	}

	public boolean contido( char cTexto, String sTexto ) {

		boolean bRetorno = false;
		
		for ( int i = 0; i < sTexto.length(); i++ ) {
			if ( cTexto == sTexto.charAt( i ) ) {
				bRetorno = true;
				break;
			}
		}
		
		return bRetorno;
	}
}
