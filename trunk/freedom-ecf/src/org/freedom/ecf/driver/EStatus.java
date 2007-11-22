
package org.freedom.ecf.driver;

public enum EStatus {

	RETORNO_OK( "RETORNO_OK" ),
	ERRO_COMUNICACAO( "Erro de comunicação física." ),
	PARAMETRO_INVALIDO( "Parâmetro inválido na função." ),
	ALIQUOTA_NAO_PROGRAMADA( "Aliquota não programada." ),
	ARQ_INI_NAO_ENCONTRADO( "O arquivo de inicialização não encontrado." ),
	ERRO_ABRIR_PORTA( "Erro ao abrir a porta de comunicação." ),
	ERRO_GRAVAR_RETORNO( "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT" ),
	NAO_STATUS_600( "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)" ),
	FUNCAO_NAO_COMPATIVEL( "Função não compatível com a impressora YANCO" ),
	FORMA_PAGAMENTO_NAO_FINALIZADA( "Forma de pagamento não finalizada" ),
	RETORNO_INDEFINIDO( "Retorno indefinido: " );

	private String message;

	EStatus( final String arg ) {
		this.message = arg;
	}
	
	public void setMessage( final String arg ) {
		this.message = arg;
	}

	public String getMessage() {
		return this.message;
	}
}
