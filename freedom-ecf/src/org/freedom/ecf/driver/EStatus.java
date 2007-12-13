
package org.freedom.ecf.driver;

public enum EStatus {

	RETORNO_OK( "RETORNO_OK" ),
	RETORNO_INDEFINIDO( "Retorno indefinido: " ),

	// Status da impressora Bematech...
	BEMA_ERRO_COMUNICACAO( 0, "Erro de comunicação física." ),
	BEMA_PARAMETRO_INVALIDO( -2, "Parâmetro inválido na função." ),
	BEMA_ALIQUOTA_NAO_PROGRAMADA( -3, "Aliquota não programada." ),
	BEMA_ARQ_INI_NAO_ENCONTRADO( -4, "O arquivo de inicialização não encontrado." ),
	BEMA_ERRO_ABRIR_PORTA( -5, "Erro ao abrir a porta de comunicação." ),
	BEMA_ERRO_GRAVAR_RETORNO( -8, "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT" ),
	BEMA_NAO_STATUS_600( -27, "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)" ),
	BEMA_FUNCAO_NAO_COMPATIVEL( -30, "Função não compatível com a impressora YANCO" ),
	BEMA_FORMA_PAGAMENTO_NAO_FINALIZADA( -31, "Forma de pagamento não finalizada" ),
	
	// Status da impressra daruma;	
	DARUMA_ERROR_01( 1, "Comando habilitado somente em modo manutenção." ),
	DARUMA_ERROR_02( 2, "Falha na gravação da Memória Fiscal." ),
	DARUMA_ERROR_03( 3, "Capaciadade de Memória Fiscal esgotada." ),
	DARUMA_ERROR_04( 4, "Data fornecida não coincide com a data interna da IF." ),
	DARUMA_ERROR_05( 5, "Impressora Fiscal bloqueada por erro fiscal." ),
	DARUMA_ERROR_06( 6, "Erro no campo de controle da Memória Fiscal." ),
	DARUMA_ERROR_07( 7, "Existem uma memória fiscal instalada." ),
	DARUMA_ERROR_08( 8, "Senha incorreta." ),
	DARUMA_ERROR_09( 9, "Comando habilitado somente em modo operação." ),
	DARUMA_ERROR_10( 10, "Documento aberto." ),
	DARUMA_ERROR_11( 11, "Documento não aberto." ),
	DARUMA_ERROR_12( 12, "Não a documento a cancelar." ),
	DARUMA_ERROR_13( 13, "Campo númerico com valores inválidos." ),
	DARUMA_ERROR_14( 14, "Capacidade máxima da memória foi atingida." ),
	DARUMA_ERROR_15( 15, "Item solicitado não foi encontrado." ),
	DARUMA_ERROR_16( 16, "Erro na sintaxe do comando." ),
	DARUMA_ERROR_17( 17, "Overflow nos cáuculos internos." ),
	DARUMA_ERROR_18( 18, "Alíquota de inposto não definida para o totalizador selecionado." ),
	DARUMA_ERROR_19( 19, "Memória fiscal vazia." ),
	DARUMA_ERROR_20( 20, "Nenhum campo requer atualização." ),
	DARUMA_ERROR_21( 21, "Repita o comando de Redução Z." ),
	DARUMA_ERROR_22( 22, "Redução Z do dia já foi executada." ),
	DARUMA_ERROR_23( 23, "Redução Z pendente." ),
	DARUMA_ERROR_24( 24, "Valor de desconto ou acrécimo inválido." ),
	DARUMA_ERROR_25( 25, "Caracteres não estampáveis foram encontrados." ),
	DARUMA_ERROR_26( 26, "Comando não pode ser executado." ),
	DARUMA_ERROR_27( 27, "Operação abortada." ),
	DARUMA_ERROR_28( 28, "Campo númerico em zero não permitido." ),
	DARUMA_ERROR_29( 29, "Documento anterior não foi cupom fiscal." ),
	DARUMA_ERROR_30( 30, "Foi selecionado um documento não fiscal inválido ou não programando." ),
	DARUMA_ERROR_31( 31, "Não pode autenticar." ),
	DARUMA_ERROR_32( 32, "Desconto de INSS não habilitado" ),
	DARUMA_ERROR_33( 33, "Emita comprovantes não fiscais vinculados pendentes." ),
	DARUMA_ERROR_34( 34, "Impressora fiscal bloqueada por erro fiscal." ),
	DARUMA_ERROR_35( 35, "Relógio interno inoperante." ),
	DARUMA_ERROR_36( 36, "Versão do firmware gravada na MF não coincide ccom a esperada." ),
	DARUMA_ERROR_38( 38, "Foi selecionada uma forma de pagamento inválida." ),
	DARUMA_ERROR_39( 39, "Erro na sequencia de fechamento do documento." ),
	DARUMA_ERROR_40( 40, "Já foi emitido algum documento após a ultíma redução Z." ),
	DARUMA_ERROR_41( 41, "Data/Hora fornecida é anterior a última gravada na Memória Fiscal." ),
	DARUMA_ERROR_42( 42, "Leitura X do início do dia ainda não foi emitida." ),
	DARUMA_ERROR_43( 43, "Não pode mais emitir CNF Vinculado solicitado." ),
	DARUMA_ERROR_44( 44, "Forma de pagamento já definida." ),
	DARUMA_ERROR_45( 45, "Campo em brando ou contendo caracter de controle." ),
	DARUMA_ERROR_47( 47, "Nenhum periférico homologado conectado a porta auxiliar." ),
	DARUMA_ERROR_48( 48, "Valor do estorno superior ao total acumulado nesta forma de pagamento." ),
	DARUMA_ERROR_49( 49, "Forma de pagamento a ser estornada não foi encontrada na memória." ),
	DARUMA_ERROR_50( 50, "Impressora sem papel." ),
	DARUMA_ERROR_61( 61, "Operação interrompida por falta de energia elétrica." ),
	DARUMA_ERROR_70( 71, "Falha na comunidação com o módulo impressor." ),
	DARUMA_ERROR_80( 81, "Periférico conectado a porta auxiliar não homologado." ),
	DARUMA_ERROR_81( 82, "Banco não cadastrado." ),
	DARUMA_ERROR_82( 83, "Nada a imprimir." ),
	DARUMA_ERROR_83( 84, "Extenso não cabe no cheque." ),
	DARUMA_ERROR_84( 85, "Leitor de CMC-7 não instalado." ),
	DARUMA_ERROR_86( 86, "Não há mais memória para o cadastro de bancos." ),
	DARUMA_ERROR_87( 87, "Impressão no verso somento após a impressão da frente do cheque." ),
	DARUMA_ERROR_88( 88, "Valor inválido para o cheque." ),	
	DARUMA_WARNING_01( 1001, "Near End foi detectado." ),
	DARUMA_WARNING_02( 1002, "Execute redução Z." ),
	DARUMA_WARNING_04( 1004, "Queda de energia." ),
	DARUMA_WARNING_10( 1010, "Bateria interna requer substituição." ),
	DARUMA_WARNING_20( 1020, "Operação habilitada somente em MIT." );
	

	private String message;
	private int code;
	

	EStatus( final String arg ) {
		this.message = arg;
	}
	
	EStatus( final int code, final String arg ) {
		this.code = code;
		this.message = arg;
	}
	
	public void setMessage( final String arg ) {
		this.message = arg;
	}

	public String getMessage() {
		return this.message;
	}

	public int getCode() {
		return this.code;
	}
}
