/**		
 * Este programa é licenciado de acordo com a LGPL (Lesser General Public License), <br>
 * versão 2.1, Fevereiro de 1999 <br>
 * A LGPL deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <br>
 * Caso uma cópia da LGPL não esteja disponível junto com este Programa, você pode contatar <br>
 * o LICENCIADOR ou então pegar uma cópia em: <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative Commons</a> <br>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este programa é preciso estar de acordo com os termos da LGPL. <br>
 * <br>
 * <br>
 * A classe TextTef fornecerá implementações em comum para as classe de implementação de funções de TEF<br>
 * por meio de comunicação com troca de arquivos texto, que serão implementadas por bandeiras.<br>
 * <br>
 * <br>
 * @author Setpoint Informática Ltda. / Alex Rodrigues <br>
 * @version 1.0 (beta), 05/03/2008 <br>
 */

package org.freedom.tef.driver.text;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;



public abstract class TextTef {
	
	public static final String ADM = "ADM";
	
	public static final String ADR = "ADR";
	
	public static final String CHQ = "CHQ";
	
	public static final String CNC = "CNC";
	
	public static final String CRT = "CRT";
	
	public static final String PRE = "PRE";
	
	private TextTefProperties textTefProperties;

    protected File fileTemp;

    protected File fileSend;

    protected File fileResponse;

    protected File fileStatus;

    protected File fileActive;
	
	
	public TextTef() {
		super();
	}
	
	public TextTef( final TextTefProperties textTefProperties ) throws Exception {
		this();
		setTextTefProperties( textTefProperties );
		initializeTextTef();
	}
	
	public TextTefProperties getTextTefProperties() {			
		return textTefProperties;
	}

	public void setTextTefProperties( final TextTefProperties textTefProperties ) throws Exception {		
		if ( textTefProperties == null ) {
			throw new NullPointerException( "Properties para TEF não especificadas!" );
		}		
		if ( validateTextTefProperties( textTefProperties ) ) {
			this.textTefProperties = textTefProperties;
		}
	}

	public String get( String key ) throws Exception {
		return getTextTefProperties() != null ? getTextTefProperties().get( key ) : null;
	}

	public String set( String key, String value ) throws Exception {
		return getTextTefProperties() != null ? getTextTefProperties().set( key, value ) : null;
	}

	protected void initializeTextTef( final TextTefProperties textTefProperties ) throws Exception {
		setTextTefProperties( textTefProperties );
		initializeTextTef();
	}
	
	abstract protected void initializeTextTef() throws Exception ;

	abstract protected boolean validateTextTefProperties( final TextTefProperties textTefProperties )
		throws Exception;
	
	abstract public boolean isActive() throws Exception;
	
	abstract public boolean requestSale( final Integer numberDoc, 
			                             final BigDecimal value ) throws Exception;
	
	abstract public boolean requestCancel( final String nsu,
										   final String rede,
									       final Date data,
										   final BigDecimal value ) throws Exception;
	
	abstract public boolean requestAdministrator() throws Exception;
	
	abstract public boolean readResponseToPrint() throws Exception;
}
