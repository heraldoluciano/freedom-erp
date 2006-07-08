/*
 * Classe de driver para impressoras Bematech
 * Autor: Robson Sanchez/Setpoint Informática Ltda.
 * Data: 05/04/2006
 */
package org.freedom.ecf.driver;

import java.util.Date;

public class ECFBematech extends ECFDriver {
	
	public ECFBematech(int com) {
		super(com);
	}
	
	public byte[] preparaCmd(byte[] CMD) {
		int tamCMD = CMD.length;
		int tam = tamCMD + 2;
		byte NBL = (byte) (tam % 256);
		byte NBH = (byte) (tam / 256);
		byte CSL = 0;
		byte CSH = 0;
		int soma = 0;
		byte[] retorno = new byte[5 + tamCMD];
		retorno[0] = STX;
		retorno[1] = NBL;
		retorno[2] = NBH;
		for (int i=0; i<tamCMD; i++) {
			soma += CMD[i];
			retorno[i+3] = CMD[i];
		}
		CSL = (byte) (soma % 256);
		CSH = (byte) (soma / 256);
        retorno[retorno.length-2] = CSL;
        retorno[retorno.length-1] = CSH;
		return retorno;
	}

	public int executaCmd(byte[] CMD) {
		byte[] retorno = null;
		CMD = preparaCmd(CMD);
		retorno = enviaCmd(CMD);
		aguardaImpressao();
		return checkRetorno(retorno);
	}
	
	public void aguardaImpressao() {
		byte[] CMD = {ESC,19}; // status 
		byte[] retorno = null;
		CMD = preparaCmd(CMD);
		while ((retorno==null) || (retorno.length<2)) {
		   retorno = enviaCmd(CMD);
		   try {
			   Thread.sleep(3000);
		   }
		   catch (InterruptedException e) {
			   
		   }
		}
	}
	
	public int checkRetorno(byte[] bytes) {
		int retorno = 0;
		bytesLidos = null;
		//byte[] flags = new byte[3];
		byte ack = 0;
		byte st1 = 0;
		byte st2 = 0;
		if (bytes!=null) {
		   if (bytes.length>3) 
			   bytesLidos = new byte[bytes.length-3];
		   for (int i=0; i<bytes.length; i++) {
			   if (i==0)
			       ack = bytes[i];
			   else if (i==1)
			       st1 = bytes[i];
			   else if (i==2)
				   st2 = bytes[i];
			   else
				   bytesLidos[i-3] = bytes[i];
		   }
		   if (ack==ACK)
			   retorno = 1;
		   else {
			   retorno = -27; // Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)
			   if (st1>127) st1 -= 128;
			   if (st1>63) st1 -= 64;
			   if (st1>31) st1 -= 32;
			   if (st1>15) st1 -= 16;
			   if (st1>7) st1 -= 8;
			   if (st1>3) st1 -= 4;
			   if (st1>1) st1 -= 2;
			   if (st1>0) {
				   st1 -= 1;
				   retorno = -2; //"Parâmetro inválido na função. ou Número de parâmetros inválido na funçao"
			   }
			   if (st2>127) {
				   retorno = -2; //"Parâmetro inválido na função."
				   st2 -= 128;
			   }
			   if (st2>63) st2 -= 64;
			   if (st2>31) st2 -= 32;
			   if (st2>15) st2 -= 16;
			   if (st2>7) st2 -= 8;
			   if (st2>3) st2 -= 4;
			   if (st2>1) st2 -= 2;
			   if (st2>0) {
				   st2 -= 1;
				   retorno = -2; //"Parâmetro inválido na função. ou Número de parâmetros inválido na funçao"
			   }
		   }
		}
		return retorno;
	}
	
	//	Abre o cupom para venda.
	public int aberturaDeCupom() {
		byte[] CMD = {ESC,0};
		return executaCmd(CMD);
	}
	
	//	Abre o cupom para venda passando o cnpj/cpf do cliente.
	public int aberturaDeCupom(String cnpj) {
		byte[] CMD = {ESC,0};
		CMD = adicBytes(CMD,parseParam(cnpj,29).getBytes());
		return executaCmd(CMD);
	}
	
	//	Altera simbolo da moeda corrente, não a nescidade de passar o $ no parametro.
	public int alteraSimboloMoeda(String simbolo) {
		byte[] CMD = {ESC,1};
		CMD = adicBytes(CMD,parseParam(simbolo,2).getBytes());
		return executaCmd(CMD);
	}
	
	//	Executa a redução Z.
	public int reducaoZ() {
		byte[] CMD = {ESC,5};
		return executaCmd(CMD);
	}
	
	// 	Executa a leitura X.
	public int leituraX() {
		byte[] CMD = {ESC,6};
		return executaCmd(CMD);
	}
	
	//	adiciona aliquotas.
	public int adicaoDeAliquotaTriburaria(float aliq, char opt) {
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(aliq,4,2) );
		if(ISS == opt)
			buf.append(opt);
		byte[] CMD = {ESC,7};
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Leitura da memoria fiscal por data.
	public int leituraMemoriaFiscal( Date dataIni, Date dataFim, char tipo) {
		byte[] CMD = {ESC,8};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(dataIni) );
		buf.append( parseParam(dataFim) );
		buf.append( parseParam(tipo,1) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Leitura da memoria fiscal por reuções.
	public int leituraMemoriaFiscal( int ini, int fim, char tipo) {
		byte[] CMD = {ESC,8};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(ini,6) );
		buf.append( parseParam(fim,6) );
		buf.append( parseParam(tipo,1) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Venda de item.
	public int vendaItem(String codProd, String descProd, String sitTrib, float qtd, float valor, float desconto) {
		byte[] CMD = {ESC,9};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(codProd,13) );
		buf.append( parseParam(descProd,29) );
		buf.append( parseParam(sitTrib,2) );
		buf.append( parseParam(qtd,7,3) );
		buf.append( parseParam(valor,8,2) );
		buf.append( parseParam(desconto,8,2) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Cancelamento do item anterior.
	public int cancelaItemAnterior() {
		byte[] CMD = {ESC,13};
		return executaCmd(CMD);
	}
	
	//	Cancelamento do cupom.
	public int cancelaCupom() {
		byte[] CMD = {ESC,14};
		return executaCmd(CMD);
	}
	
	//	Ativa ou desativa o horario de verão.
	public int programaHorarioVerao() {
		byte[] CMD = {ESC,18};
		return executaCmd(CMD);
	}
	
	//	Cancelamento de item generico.
	public int cancelaItemGenerico(int item) {
		byte[] CMD = {ESC,31};
		CMD = adicBytes(CMD,parseParam(item,4).getBytes());
		return executaCmd(CMD);
	}
	
	//	Inicia o fechamento do cupom.
	public int iniciaFechamentoCupom(char opt, float valor) {
		int tamanho = 14;
		if( opt == ACRECIMO_PERCENTUAL || opt == DESCONTO_PERCENTUAL ) 
			tamanho = 4;			
		byte[] CMD = {ESC,32};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(String.valueOf(opt),1) );
		buf.append( parseParam(valor,tamanho,2) );		
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Termina o fechamento do cupom.
	public int terminaFechamentoCupom(String menssagem) {
		byte[] CMD = {ESC,34};
		CMD = adicBytes(CMD,parseParam(menssagem,492).getBytes());
		return executaCmd(CMD);
	}
	
	// Truncamento / aredondamento
	public int programaTruncamentoArredondamento(char opt) {
		byte[] CMD = {ESC,39};
		CMD = adicBytes(CMD,parseParam(opt,1).getBytes());
		return executaCmd(CMD);
	}
	
	//	
	public int nomeiaTotalizadorNaoSujeitoICMS(int indice, String desc) {
		byte[] CMD = {ESC,40};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(indice,2) );
		buf.append( parseParam(desc,19) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Programa espaço entre as linhas em dots.
	public int programarEspacoEntreLinhas(int espaco) {
		byte[] CMD = {ESC,60};
		CMD = adicBytes(CMD,parseParam(espaco,1).getBytes());
		return executaCmd(CMD);
	}
	
	//	Programa espaço entre as linhas em dots.
	public int programarEspacoEntreCupons(int espaco) {
		byte[] CMD = {ESC,61};
		CMD = adicBytes(CMD,parseParam(espaco,1).getBytes());
		return executaCmd(CMD);
	}
	
	//	Programa a unidade de medida
	//	Valida somente para um item, depois volta ao default.
	public int programaUnidadeMedida(String descUnid) {
		byte[] CMD = {ESC,62,51};
		CMD = adicBytes(CMD,parseParam(descUnid,2).getBytes());
		return executaCmd(CMD);
	}
	
	//	Aumenta a descrição do item para 200 caracteres
	//	Valida somente para um item, depois volta ao default.
	public int aumentaDescItem(String descricao) {
		byte[] CMD = {ESC,62,52};
		CMD = adicBytes(CMD,parseParam(descricao,200).getBytes());
		return executaCmd(CMD);
	}
	
	//	Venda de item com entrada de Departamento, Desconto e Unidade
	public int vendaItemDepartamento(String sitTrib, float valor, float qtd, float desconto, float acrescimo, int departamento, String unidade, String codProd, String descProd) {
		byte[] CMD = {ESC,63};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(sitTrib,2) );
		buf.append( parseParam(valor,9,3) );
		buf.append( parseParam(qtd,7,3) );
		buf.append( parseParam(desconto,10,2) );
		buf.append( parseParam(acrescimo,10,2) );
		buf.append( parseParam(departamento,2) );
		buf.append( parseParam("00000000000000000000",20) );
		buf.append( parseParam(unidade,2) );
		buf.append( parseParam(codProd,49) );
		buf.append( parseParam(descProd,201) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Nomeia os departamentos.
	public int nomeiaDepartamento(int index, String descricao) {
		byte[] CMD = {ESC,65};
		CMD = adicBytes(CMD,parseParam(descricao,20).getBytes());
		return executaCmd(CMD);
	}
	
	public int habilitaCupomAdicional(char opt) {
		byte[] CMD = {ESC,68};
		CMD = adicBytes(CMD,parseParam(opt,1).getBytes());
		return executaCmd(CMD);
	}
	
	//	Caso a impressora estiver em erro inicialia a mesma.
	//  alguns erro podem ser recuperado em modo remoto.
	public int resetErro() {
		byte[] CMD = {ESC,70};
		return executaCmd(CMD);
	}
	
	//	Programa formas de pagamentos,
	//	Validas somente para o mesmo dia.
	public int programaFormaPagamento(String[] descricoes) {
		byte[] CMD = {ESC,71};
		StringBuffer buf = new StringBuffer();
		int size = descricoes.length < 48 ? descricoes.length : 48; 
		for(int i=0; i<size; i++) {
			buf.append( parseParam(descricoes[i],16) );
		}
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Efetua forma de pagamento.
	public int efetuaFormaPagamento(int indice, float valor, String descForma) {
		byte[] CMD = {ESC,72};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(indice,2) );
		buf.append( parseParam(valor,14,2) );
		buf.append( parseParam(descForma,80) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Estorna de uma forma de pagamento a outra.
	//	O valor não pode exceder o valor da forma de origem. 
	public int estornoFormaPagamento(String descOrigem, String descDestino, float valor) {		
		byte[] CMD = {ESC,74};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(descOrigem,16) );
		buf.append( parseParam(descDestino,16) );
		buf.append( parseParam(valor,14,2) );
		CMD = adicBytes(CMD,buf.toString().getBytes());		
		return executaCmd(CMD);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 *        case 0: sMensagem = "Erro de comunicação física"; break;
       case 1: sMensagem = ""; break;
       case -2: sMensagem = "Parâmetro inválido na função."; break;
       case -3: sMensagem = "Aliquota não programada"; break;
       case -4: sMensagem = "O arquivo de inicialização BEMAFI32.INI não foi encontrado no diretório de sistema do Windows"; break;
       case -5: sMensagem = "Erro ao abrir a porta de comunicação"; break;
       case -8: sMensagem = "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT"; break;
       case -27: sMensagem = "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)"; break;
       case -30: sMensagem = "Função não compatível com a impressora YANCO"; break;
       case -31: sMensagem = "Forma de pagamento não finalizada"; break;
       default : sMensagem = "Retorno indefinido: "+iRetorno; break;
	 */
}
