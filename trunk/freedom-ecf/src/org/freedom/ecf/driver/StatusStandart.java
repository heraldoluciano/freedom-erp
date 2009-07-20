
package org.freedom.ecf.driver;

public final class StatusStandart implements Status {

	public final static StatusStandart RETORNO_OK = new StatusStandart( 0, RELEVANC_MESSAGE, "RETORNO_OK" );

	public final static StatusStandart IMPRESSORA_OK = new StatusStandart( 100, RELEVANC_ERRO, "Impressora OK" );

	public final static StatusStandart RETORNO_INDEFINIDO = new StatusStandart( 101, RELEVANC_ERRO, "Retorno indefinido: " );

	public final static StatusStandart FUNCAO_NAO_IMPLEMENTADA = new StatusStandart( -1, RELEVANC_ERRO, "Comando não implementado pelo driver de comunicação." );
	
	public final static StatusStandart IMPRESSORA_SEM_COMUNICACAO = new StatusStandart( -1, RELEVANC_ERRO, "Impressora não responde." );

	private String message;

	private int code;

	private int relevanc;

	private StatusStandart( int code, int relevanc, String message ) {

		this.code = code;
		this.relevanc = relevanc;
		this.message = message;
	}

	public int getCode() {

		return code;
	}

	public String getMessage() {

		return message;
	}

	public int getRelevanc() {

		return relevanc;
	}
}
