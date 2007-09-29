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

		getCommand().append( STX.getCommand() );
		getCommand().append( L.getCommand() );
		getCommand().append( CR.getCommand() );
	}

	public void printString( int arg0, int arg1, String arg2 ) {

		addCommand( getOrdinance() );
		addCommand( getFont() );
		addCommand( getHorizontal_multiplier() );
		addCommand( getVertical_multiplier() );
		addCommand( intToStrZero( getSubtype_font().getCommand(), 3 ) );
		addCommand( intToStrZero( arg0 ) );
		addCommand( intToStrZero( arg1 ) );
		addCommand( arg2 );
	}

	/**
	 * Comando de fechamento de script.
	 */
	private void close() {

		getCommand().append( E.getCommand() );
		getCommand().append( CR.getCommand() );
	}

	/**
	 * @return String contendo o script de comando para impressão.
	 */
	public String command() {

		// Fecha o script antes de retorna-lo.
		close();

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

		L( 0x4C ),

		ORDINANCE_DEFAULT( 0x31 ),

		ORDINANCE_ROTATION_270( 0x32 ),

		ORDINANCE_ROTATION_180( 0x33 ),

		ORDINANCE_ROTATION_90( 0x34 ),
		
		FONT_DEFAULT( 0x32 ),

		SUBTYPE_FONT_DEFAULT( 0x30 ),
		
		HORIZONTAL_MULTIPLIER_DEFAULT( 0x31 ),
		
		VERTICAL_MULTIPLIER_DEFAULT( 0x31 );

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
		public int getCommand() {

			return this.command;
		}
	}
}
