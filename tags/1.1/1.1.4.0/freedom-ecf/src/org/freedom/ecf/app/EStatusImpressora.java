package org.freedom.ecf.app;


public enum EStatusImpressora {

	IMPRESSORA_OK( "Impressora OK" ),
	FIM_DE_PAPEL( "Fim de papel." ),
    POUCO_PAPEL( "Pouco papel." ),
    RELOGIO_ERROR( "Erro no relógio." ),
    IMPRESSORA_EM_ERRO( "Impressora em erro." ),
    NO_ESC( "Primeiro dado do comando não foi ESC." ),
    NO_COMMAND( "Comando inexistente." ),
    CUPOM_FISCAL_ABERTO( "Cupom fiscal aberto." ),
    NU_PARAMS_INVALIDO( "Número de parâmetro de CMD inválido." ),
    TP_PARAM_INVALIDO( "Tipo de parâmetro de CMD inválido." ),
    OUT_OF_MEMORY( "Memória fiscal lotada." ),
    MEMORY_ERROR( "Erro na memória RAM CMOS não volátil." ),
    NO_ALIQUOTA( "Alíquota não programada." ),
    OUT_OF_ALIQUOTA( "Capacidade de alíquotas esgotada." ),
    NO_ACESESS_CANCELAMENTO( "Cancelamento não permitido." ),
    NO_CNPJ_IE( "CNPJ/IE do proprietário não programados." ),
    COMMAND_NO_EXECUTE( "Comando não executado." );

	private final String mensagem;

	EStatusImpressora( String mensagem ) {

		this.mensagem = mensagem;
	}

	String getMensagem() {

		return this.mensagem;
	}
}
