
package org.freedom.ecf.driver;

public enum EStatus {

	RETORNO_OK( "RETORNO_OK" ),

	// Status da impressora Bematech...
	ERRO_COMUNICACAO( "Erro de comunicação física." ),
	PARAMETRO_INVALIDO( "Parâmetro inválido na função." ),
	ALIQUOTA_NAO_PROGRAMADA( "Aliquota não programada." ),
	ARQ_INI_NAO_ENCONTRADO( "O arquivo de inicialização não encontrado." ),
	ERRO_ABRIR_PORTA( "Erro ao abrir a porta de comunicação." ),
	ERRO_GRAVAR_RETORNO( "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT" ),
	NAO_STATUS_600( "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)" ),
	FUNCAO_NAO_COMPATIVEL( "Função não compatível com a impressora YANCO" ),
	FORMA_PAGAMENTO_NAO_FINALIZADA( "Forma de pagamento não finalizada" ),
	RETORNO_INDEFINIDO( "Retorno indefinido: " ),
	
	// Status da impressra daruma;
	
	ERROR_01( "Comando habilitado somente em modo manutenção." ),
	ERROR_02( "Falha na gravação da Memória Fiscal." ),
	ERROR_03( "Capaciadade de Memória Fiscal esgotada." ),
	ERROR_04( "Data fornecida não coincide com a data interna da IF." ),
	ERROR_05( "Impressora Fiscal bloqueada por erro fiscal." ),
	ERROR_06( "Erro no campo de controle da Memória Fiscal." ),
	ERROR_07( "Existem uma memória fiscal instalada." ),
	ERROR_08( "Senha incorreta." ),
	ERROR_09( "Comando habilitado somente em modo operação." ),
	ERROR_10( "Documento aberto." ),
	ERROR_11( "Documento não aberto." ),
	ERROR_12( "Não a documento a cancelar." ),
	ERROR_13( "Campo númerico com valores inválidos." ),
	ERROR_14( "Capacidade máxima da memória foi atingida." ),
	ERROR_15( "Item solicitado não foi encontrado." ),
	ERROR_16( "Erro na sintaxe do comando." ),
	ERROR_17( "Overflow nos cáuculos internos." ),
	ERROR_18( "Alíquota de inposto não definida para o totalizador selecionado." ),
	ERROR_19( "Memória fiscal vazia." ),
	ERROR_20( "Nenhum campo requer atualização." ),
	ERROR_21( "Repita o comando de Redução Z." ),
	ERROR_22( "Redução Z do dia já foi executada." ),
	ERROR_23( "Redução Z pendente." ),
	ERROR_24( "Valor de desconto ou acrécimo inválido." ),
	ERROR_25( "Caracteres não estampáveis foram encontrados." ),
	ERROR_26( "Comando não pode ser executado." ),
	ERROR_27( "Operação abortada." ),
	ERROR_28( "Campo númerico em zero não permitido." ),
	ERROR_29( "Documento anterior não foi cupom fiscal." ),
	ERROR_30( "Foi selecionado um documento não fiscal inválido ou não programando." ),
	ERROR_31( "Não pode autenticar." ),
	ERROR_32( "Desconto de INSS não habilitado" ),
	ERROR_33( "Emita comprovantes não fiscais vinculados pendentes." ),
	ERROR_34( "Impressora fiscal bloqueada por erro fiscal." ),
	ERROR_35( "Relógio interno inoperante." ),
	ERROR_36( "Versão do firmware gravada na MF não coincide ccom a esperada." ),
	ERROR_38( "Foi selecionada uma forma de pagamento inválida." ),
	ERROR_39( "Erro na sequencia de fechamento do documento." ),
	ERROR_40( "Já foi emitido algum documento após a ultíma redução Z." ),
	ERROR_41( "Data/Hora fornecida é anterior a última gravada na Memória Fiscal." ),
	ERROR_42( "Leitura X do início do dia ainda não foi emitida." ),
	ERROR_43( "Não pode mais emitir CNF Vinculado solicitado." ),
	ERROR_44( "Forma de pagamento já definida." ),
	ERROR_45( "Campo em brando ou contendo caracter de controle." ),
	ERROR_47( "Nenhum periférico homologado conectado a porta auxiliar." ),
	ERROR_48( "Valor do estorno superior ao total acumulado nesta forma de pagamento." ),
	ERROR_49( "Forma de pagamento a ser estornada não foi encontrada na memória." ),
	ERROR_50( "Impressora sem papel." ),
	ERROR_61( "Operação interrompida por falta de energia elétrica." ),
	ERROR_70( "Falha na comunidação com o módulo impressor." ),
	ERROR_80( "Periférico conectado a porta auxiliar não homologado." ),
	ERROR_81( "" ),
	ERROR_82( "" ),
	ERROR_83( "" ),
	ERROR_84( "" ),
	ERROR_85( "" ),
	ERROR_86( "" ),
	ERROR_87( "" ),
	ERROR_88( "" );
	

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
