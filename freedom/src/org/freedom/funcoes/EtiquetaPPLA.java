/**
 * @version 28/09/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.componentes <BR>
 *         Classe:
 * @(#)EtiquetaPPLA.java <BR>
 *                       Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 *                       versão 2.1.0 ou qualquer versão posterior. <BR>
 *                       A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 *                       Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 *                       o LICENCIADOR ou então pegar uma cópia em: <BR>
 *                       Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                       Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 *                       de acordo com os termos da LPG-PC <BR>
 *                       <BR>
 *                       Comentários da classe.....
 */
package org.freedom.funcoes;

import static org.freedom.funcoes.EtiquetaPPLA.CMD.*;

public class EtiquetaPPLA {

	private StringBuilder command = new StringBuilder();

	private CMD ordinance = ORDINANCE_DEFAULT;

	private CMD font = FONT_DEFAULT;

	private CMD subtype_font = SUBTYPE_FONT_DEFAULT;

	private CMD horizontal_multiplier = HORIZONTAL_MULTIPLIER_DEFAULT;

	private CMD vertical_multiplier = VERTICAL_MULTIPLIER_DEFAULT;
	
	private CMD barcode_type = BARCODE_A;
	
	private CMD barcode_b_w = BARCODE_WIDE_BAR_DEFAULT;
	
	private CMD barcode_b_f = BARCODE_FINE_BAR_DEFAULT;

	/**
	 * Contrutor padrão para EtiquetaPPLA.
	 */
	public EtiquetaPPLA() {

		// Inicia o script na criação do objeto.
		open();
	}

	/**
	 * @return StringBuilder que armazena o script.
	 */
	private StringBuilder getCommand() {

		if ( this.command == null ) {

			this.command = new StringBuilder();
		}

		return this.command;
	}

	public CMD getFont() {

		return font;
	}

	public void setFont( CMD font ) {

		this.font = font;
	}

	public CMD getHorizontal_multiplier() {

		return horizontal_multiplier;
	}

	public void setHorizontal_multiplier( CMD horizontal_multiplier ) {

		this.horizontal_multiplier = horizontal_multiplier;
	}

	public CMD getOrdinance() {

		return ordinance;
	}

	public void setOrdinance( CMD ordinance ) {

		this.ordinance = ordinance;
	}

	public CMD getSubtype_font() {

		return subtype_font;
	}

	public void setSubtype_font( CMD subtype_font ) {

		this.subtype_font = subtype_font;
	}

	public CMD getVertical_multiplier() {

		return vertical_multiplier;
	}

	public void setVertical_multiplier( CMD vertical_multiplier ) {

		this.vertical_multiplier = vertical_multiplier;
	}
	
	public CMD getBarcode_b_f() {
	
		return barcode_b_f;
	}
	
	public void setBarcode_b_f( CMD barcode_b_f ) {
	
		this.barcode_b_f = barcode_b_f;
	}
	
	public CMD getBarcode_b_w() {
	
		return barcode_b_w;
	}
	
	public void setBarcode_b_w( CMD barcode_b_l ) {
	
		this.barcode_b_w = barcode_b_l;
	}
	
	public CMD getBarcode_type() {
	
		return barcode_type;
	}
	
	public void setBarcode_type( CMD barcode_type ) {
	
		this.barcode_type = barcode_type;
	}

	/**
	 * Adiciona commando ao script.
	 * 
	 * @param arg
	 *            Comando descrito atraves da enumeração de comandos<br>
	 *            org.freedom.funcoes.EtiquetaPPLA.CMD.<br>
	 * 
	 * @see org.freedom.funcoes.EtiquetaPPLA.CMD
	 */
	public void addCommand( CMD arg ) {

		getCommand().append( arg.getCommand() );
	}

	/**
	 * Adiciona commando ao script.
	 * 
	 * @param arg
	 *            Comando.
	 */
	public void addCommand( Object arg ) {

		getCommand().append( arg );
	}

	/**
	 * Comando de abertura de script.
	 */
	private void open() {

		addCommand( STX );
		addCommand( L );
		addCommand( CR );
	}

	/**
	 * Armazena no buffer de comandos o comando de escrita de texto<br>
	 * formatando o comando com parametros previamente definidos<br> 
	 * <br> 
	 * <li>Ordinance</li>
	 * <li>Font</li>
	 * <li>Horizontal multiplier </li>
	 * <li>Vertical multiplier</li>
	 * <li>Subtype font</li>
	 * <br><br>
	 * e os passados por parametro na assinatura do metodo.<br>
	 * 
	 * @param y
	 *            posição Y
	 * @param x
	 *            posição X
	 * @param texto
	 *            texto
	 */
	public void printString( int y, int x, String texto ) {

		addCommand( getOrdinance() );
		addCommand( getFont() );
		addCommand( getHorizontal_multiplier() );
		addCommand( getVertical_multiplier() );
		addCommand( charToStrZero( getSubtype_font().getCommand(), 3 ) );
		addCommand( intToStrZero( y ) );
		addCommand( intToStrZero( x ) );
		addCommand( texto );
		addCommand( CR );
		close();
	}

	/**
	 * * Armazena no buffer de comandos o comando de impressão de código de barras<br>
	 * formatando o comando com parametros previamente definidos<br>
	 * <br> 
	 * - Ordinance<br> 
	 * - Barcode type<br> 
	 * - Barcode wide bar<br> 
	 * - Barcode fine bar<br>
	 * <br>
	 * e os passados por parametro na assinatura do metodo.<br>
	 * 
	 * @param altura
	 *            Altura do código de barras.
	 * @param y
	 *            posição Y
	 * @param x
	 *            posição X
	 * @param dados
	 *            dados para o código.
	 */
	public void printBarCode( int altura, int y, int x, String dados ) {

		addCommand( getOrdinance() );
		addCommand( getBarcode_type() );
		addCommand( getBarcode_b_w() );
		addCommand( getBarcode_b_f() );
		addCommand( intToStrZero( altura, 3 ) );
		addCommand( intToStrZero( y ) );
		addCommand( intToStrZero( x ) );
		addCommand( dados );
		close();
	}
	
	public void printCopy( int repeticoes ) {

		if ( repeticoes > 0 ) {
			addCommand( STX );
			addCommand( E );
			addCommand( intToStrZero( repeticoes - 1 , 3 ) );
			addCommand( CR );
			addCommand( STX );
			addCommand( G );
			addCommand( CR );
		}
	}

	/**
	 * Comando de fechamento de script.
	 */
	private void close() {

		addCommand( E );
		addCommand( CR );
	}

	/**
	 * @return String contendo o script de comando para impressão.
	 */
	public String command() {

		return this.command.toString();
	}

	/**
	 * Sobrecarrega o metodo intToStrZero( final int arg, final int size )<br>
	 * definindo um valor padrão de tamanho em 4 caracteres.<br>
	 * 
	 * @param arg
	 *            inteiro a ser formatado.
	 * @return String formatada com zeros a esquerda.
	 * 
	 * @see org.freedom.funcoes.EtiquetaPPLA@intToStrZero( int, int )
	 */
	private String intToStrZero( final int arg ) {

		return intToStrZero( arg, 4 );
	}

	/**
	 * Converte um inteiro em uma String adicioanda de zeros a esquerda<br>
	 * completando o tamanho especificado.
	 * 
	 * @param arg
	 *            inteiro a ser formatado.
	 * @param size
	 *            tamanho do retorno.
	 * @return String formatada com zeros a esquerda.
	 */
	private String intToStrZero( final int arg, final int size ) {

		String value = String.valueOf( arg );
		StringBuffer buffer = new StringBuffer();
		int strsize = size - value.length();

		for ( int i = 0; i < strsize; i++ ) {
			buffer.append( 0 );
		}

		buffer.append( arg );

		return buffer.toString();
	}

	/**
	 * Converte um character em uma String adicioanda de zeros a esquerda<br>
	 * completando o tamanho especificado.
	 * 
	 * @param arg
	 *            inteiro a ser formatado.
	 * @param size
	 *            tamanho do retorno.
	 * @return String formatada com zeros a esquerda.
	 */
	private String charToStrZero( final char arg, final int size ) {

		String value = String.valueOf( arg );
		StringBuffer buffer = new StringBuffer();
		int strsize = size - value.length();

		for ( int i = 0; i < strsize; i++ ) {
			buffer.append( 0 );
		}

		buffer.append( arg );

		return buffer.toString();
	}

	/**
	 * Enumera os comandos utilizados na criação de script para<br>
	 * impressão de etiquetas utilizando a linguagem PPLA.
	 * 
	 * @author Alex Rodrigues
	 * @version 0.0.1.0
	 */
	public enum CMD {

		STX( 0x02 ),

		CR( 0x0D ),

		E( 0x45 ),

		G( 0x47 ),

		L( 0x4C ),

		ORDINANCE_DEFAULT( 0x31 ),

		ORDINANCE_ROTATION_270( 0x32 ),

		ORDINANCE_ROTATION_180( 0x33 ),

		ORDINANCE_ROTATION_90( 0x34 ),
		
		// Fonts ...

		FONT_DEFAULT( 0x32 ),

		SUBTYPE_FONT_DEFAULT( 0x30 ),

		HORIZONTAL_MULTIPLIER_DEFAULT( 0x31 ),

		VERTICAL_MULTIPLIER_DEFAULT( 0x31 ),
		
		// Barcode ...
		
		BARCODE_A( 'A' ),
		
		BARCODE_WIDE_BAR_DEFAULT( 0x35 ),
		
		BARCODE_FINE_BAR_DEFAULT( 0x32 );

		/**
		 * Armazena o valor hexadecimal do comando.
		 */
		private int command;

		private CMD( int command ) {

			this.command = command;
		}

		/**
		 * 
		 * @return inteiro com o valor hexadecimal do comando.
		 */
		public char getCommand() {

			return (char) this.command;
		}
	}
}
