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

import static org.freedom.funcoes.Command.CR;
import static org.freedom.funcoes.Command.E;
import static org.freedom.funcoes.Command.G;
import static org.freedom.funcoes.Command.L;
import static org.freedom.funcoes.Command.STX;

public class EtiquetaPPLA {

	private StringBuilder command = new StringBuilder();

	private Command ordinance = Command.ORDINANCE_DEFAULT;

	private Command font = Command.FONT_DEFAULT;

	private Command subtype_font = Command.SUBTYPE_FONT_DEFAULT;

	private Command horizontal_multiplier = Command.HORIZONTAL_MULTIPLIER_DEFAULT;

	private Command vertical_multiplier = Command.VERTICAL_MULTIPLIER_DEFAULT;
	
	private Command barcode_type = Command.BARCODE_A;
	
	private Command barcode_b_w = Command.BARCODE_WIDE_BAR_DEFAULT;
	
	private Command barcode_b_f = Command.BARCODE_FINE_BAR_DEFAULT;

	/**
	 * Contrutor padrão para EtiquetaPPLA.
	 */
	public EtiquetaPPLA() {
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

	public Command getFont() {

		return font;
	}

	public void setFont( Command font ) {

		this.font = font;
	}

	public Command getHorizontal_multiplier() {

		return horizontal_multiplier;
	}

	public void setHorizontal_multiplier( Command horizontal_multiplier ) {

		this.horizontal_multiplier = horizontal_multiplier;
	}

	public void setHorizontal_multiplier( int horizontal_multiplier ) {

		this.horizontal_multiplier = new Command( horizontal_multiplier );
	}

	public Command getOrdinance() {

		return ordinance;
	}

	public void setOrdinance( Command ordinance ) {

		this.ordinance = ordinance;
	}

	public Command getSubtype_font() {

		return subtype_font;
	}

	public void setSubtype_font( Command subtype_font ) {

		this.subtype_font = subtype_font;
	}

	public Command getVertical_multiplier() {

		return vertical_multiplier;
	}

	public void setVertical_multiplier( Command vertical_multiplier ) {

		this.vertical_multiplier = vertical_multiplier;
	}
	
	public Command getBarcode_b_f() {
	
		return barcode_b_f;
	}
	
	public void setBarcode_b_f( Command barcode_b_f ) {
	
		this.barcode_b_f = barcode_b_f;
	}
	
	public void setBarcode_b_f( int barcode_b_f ) {
	
		this.barcode_b_f = new Command( barcode_b_f );
	}
	
	public Command getBarcode_b_w() {
	
		return barcode_b_w;
	}
	
	public void setBarcode_b_w( Command barcode_b_l ) {
	
		this.barcode_b_w = barcode_b_l;
	}
	
	public void setBarcode_b_w( int barcode_b_l ) {
	
		this.barcode_b_w = new Command( barcode_b_l );
	}
	
	public Command getBarcode_type() {
	
		return barcode_type;
	}
	
	public void setBarcode_type( Command barcode_type ) {
	
		this.barcode_type = barcode_type;
	}

	/**
	 * Adiciona commando ao script.
	 * 
	 * @param arg
	 *            Comando descrito atraves da enumeração de comandos<br>
	 *            org.freedom.funcoes.EtiquetaPPLA.Command.<br>
	 * 
	 * @see org.freedom.funcoes.EtiquetaPPLA.Command
	 */
	public void addCommand( Command arg ) {

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
	public void open() {

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
	public void appendString( int y, int x, String texto ) {

		addCommand( getOrdinance() );
		addCommand( getFont() );
		addCommand( getHorizontal_multiplier() );
		addCommand( getVertical_multiplier() );
		addCommand( charToStrZero( getSubtype_font().getCommand(), 3 ) );
		addCommand( intToStrZero( y ) );
		addCommand( intToStrZero( x ) );
		addCommand( texto );		
		addCommand( CR );
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
	public void appendBarCode( int altura, int y, int x, String dados ) {

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
	
	public void appendCopy( int repeticoes ) {

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
	public void close() {

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
}
