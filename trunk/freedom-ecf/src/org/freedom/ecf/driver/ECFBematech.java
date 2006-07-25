/*
 * Classe de driver para impressoras Bematech
 * Autor: Robson Sanchez/Setpoint Informática Ltda.
 * Data: 05/04/2006
 */
package org.freedom.ecf.driver;

import java.util.Date;

public class ECFBematech extends AbstractECFDriver {
	
	private static final String[] ST1 = new String[] {
		"fim de papel",
		"pouco de papel",
		"erro no relogio",
		"impressora em erro",
		"primeiro dado do CMD não foi ESC",
		"comando inexistente",
		"cupom aberto",
		"número de parâmetros inválido"
	};
	private static final String[] ST2 = new String[] {
		"tipo de parâmetro do comando inválido",
		"memoria fiscal invalida",
		"erro na memória RAM CMOS são volátil",
		"alíquota não programada",
		"capacidade de alíquotas programéveis lotada",
		"cancelamento não permitido",
		"CNPJ/IE do proprietário não programados",
		"comando não executado"
	};	
	private static final int[] bytes = new int[] {128,64,32,16,8,4,2,1};
	
	public ECFBematech( final int com ) {
		super();
		ativaPorta( com );
	}
	
	public byte[] preparaCmd( final byte[] CMD ) {
		
		final int tamCMD = CMD.length;
		final int tam = tamCMD + 2;
		int soma = 0;
		byte CSL = 0;
		byte CSH = 0;
		final byte NBL = (byte)( tam % 256 );
		final byte NBH = (byte)( tam / 256 );
		byte[] retorno = new byte[ 5 + tamCMD ];
		
		retorno[0] = STX;
		retorno[1] = NBL;
		retorno[2] = NBH;
		
		for ( int i=0; i < tamCMD; i++ ) {
			soma += CMD[i];
			retorno[i+3] = CMD[i];
		}
		
		CSL = (byte)( soma % 256 );
		CSH = (byte)( soma / 256 );
		
        retorno[retorno.length-2] = CSL;
        retorno[retorno.length-1] = CSH;
        
		return retorno;
		
	}

	public int executaCmd( final byte[] CMD ) {
		
		byte[] retorno = null;
		byte[] cmd = null ;
		
		cmd = preparaCmd( CMD );
		
		retorno = enviaCmd( cmd );
		getMenssagemRetorno( retorno );
		
      if (getAguardaImp()) {
		   aguardaImpressao();
      }
		
		return checkRetorno( retorno );
		
	}
	
	private void getMenssagemRetorno( final byte[] buffer ) {
		
		if( buffer.length > 2 ) {
			
			final int st1 = buffer[buffer.length-2];
			final int st2 = buffer[buffer.length-1];
			
			for ( int i=0; i < bytes.length; i++ ) {
				if( st1 >= bytes[i] ) {
					System.out.println("ST1 = " + ST1[i]);
					break;
				}
			}
			
			for ( int i=0; i < bytes.length; i++ ) {
				if( st2 >= bytes[i] ) {
					System.out.println("ST2 = " + ST2[i]);
					break;
				}
			}
			
		}
		
	}
   
   private String bcdToAsc(final byte[] bcdParam) {
      StringBuffer retorno = new StringBuffer();
      byte bcd = 0;
      byte bh = 0;
      byte bl = 0;
      for (int i = 0; i<bcdParam.length; i++) {
         bcd = bcdParam[i];
         bh = (byte) ( (int) bcd / 16);
         bl = (byte) ( (int) bcd % 16);
         retorno.append(bh);
         retorno.append(bl);
      }
      return retorno.toString();
   }

	public int checkRetorno( final byte[] bytes ) {
		
		int retorno = 0;
		bytesLidos = null;
		//byte ack = 0;
		//byte st1 = 0;
		//byte st2 = 0;
		byte ack = 0;
		byte st1 = 0;
		byte st2 = 0;
		
		if ( bytes != null ) {
			
			ack = bytes[0];
			
		   if ( bytes.length > 3 ) {

				st1 = bytes[bytes.length-2];
				st2 = bytes[bytes.length-1];
			   
			   bytesLidos = new byte[bytes.length-3];
			   System.arraycopy( bytes, 1, bytesLidos, 0, bytesLidos.length );
			   
		   }
		   
		   
		 //  for ( int i=0; i < bytes.length-3; i++ ) {
		//	   bytesLidos[i] = bytes[i+1];
		  // }
		   
		   /*
		   if ( ack == ACK ) {
			   retorno = 1;
		   }
		   else {
			   retorno = -27; // Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)
			   if (st1>127) { st1 -= 128; }
			   if (st1>63) { st1 -= 64; }
			   if (st1>31) { st1 -= 32; }
			   if (st1>15) { st1 -= 16; }
			   if (st1>7) { st1 -= 8; }
			   if (st1>3) { st1 -= 4; }
			   if (st1>1) { st1 -= 2; }
			   if (st1>0) {
				   st1 -= 1;
				   retorno = -2; //"Parâmetro inválido na função. ou Número de parâmetros inválido na funçao"
			   }
			   if (st2>127) {
				   retorno = -2; //"Parâmetro inválido na função."
				   st2 -= 128;
			   }
			   if (st2>63) { st2 -= 64; }
			   if (st2>31) { st2 -= 32; }
			   if (st2>15) { st2 -= 16; }
			   if (st2>7) { st2 -= 8; }
			   if (st2>3) { st2 -= 4; }
			   if (st2>1) { st2 -= 2; }
			   if (st2>0) {
				   st2 -= 1;
				   retorno = -2; //"Parâmetro inválido na função. ou Número de parâmetros inválido na funçao"
			   }
			   
		   }*/
		   
		}
				
		return retorno;
		
	}
	
	//	Abre o cupom para venda.
	public int aberturaDeCupom() {
		
		final byte[] CMD = {ESC,0};
		
		return executaCmd(CMD);
		
	}
	
	//	Abre o cupom para venda passando o cnpj/cpf do cliente.
	public int aberturaDeCupom( final String cnpj ) {
		
		byte[] CMD = {ESC,0};
		CMD = adicBytes( CMD, parseParam( cnpj, 29, false ).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Altera simbolo da moeda corrente, não a nescidade de passar o $ no parametro.
	public int alteraSimboloMoeda( final String simbolo ) {
		
		byte[] CMD = {ESC,1};
		CMD = adicBytes( CMD, parseParam( simbolo, 2, false ).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Executa a redução Z.
	public int reducaoZ() {
		
		final byte[] CMD = {ESC,5};
		
		return executaCmd( CMD );
		
	}
	
	// 	Executa a leitura X.
	public int leituraX() {
		
		final byte[] CMD = {ESC,6};
		
		return executaCmd( CMD );
		
	}
	
	//	adiciona aliquotas.
	public int adicaoDeAliquotaTriburaria( final float aliq, final char opt ) {
		
		if( aliq > 99 ) {
			throw new IllegalArgumentException( "Alíquota invalida. Não pode superar 99,99 %." );
		}

		byte[] CMD = {ESC,7};
		
		final StringBuffer buf = new StringBuffer();
		buf.append( parseParam( aliq, 4, 2 ) );
		
		if( ISS == opt ) {
			buf.append(opt);
		}
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Leitura da memoria fiscal por data.
	public int leituraMemoriaFiscal( final Date dataIni, final Date dataFim, final char tipo ) {
		
		byte[] CMD = {ESC,8};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( dataIni ) );
		buf.append( parseParam( dataFim ) );
		buf.append( parseParam( tipo, 1 ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Leitura da memoria fiscal por reuções.
	public int leituraMemoriaFiscal( final int ini, final int fim, final char tipo ) {
		
		byte[] CMD = {ESC,8};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( ini, 6 ) );
		buf.append( parseParam( fim, 6 ) );
		buf.append( parseParam( tipo, 1 ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Venda de item.
	public int vendaItem( final String codProd, final String descProd, final String sitTrib, final float qtd, final float valor, final float desconto ) {
		
		byte[] CMD = {ESC,9};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( codProd, 13, false ) );
		buf.append( parseParam( descProd, 29, false ) );
		buf.append( parseParam( sitTrib, 2, false ) );
		buf.append( parseParam( qtd, 7, 3 ) );
		buf.append( parseParam( valor, 8, 2 ) );
		buf.append( parseParam( desconto, 8, 2 ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Cancelamento do item anterior.
	public int cancelaItemAnterior() {
		
		final byte[] CMD = {ESC,13};
		
		return executaCmd( CMD );
		
	}
	
	//	Cancelamento do cupom.
	public int cancelaCupom() {
		
		final byte[] CMD = {ESC,14};
		
		return executaCmd( CMD );
		
	}
	
	//	Autenticação de documento.
	public int autenticacaoDeDocumento() {
		
		final byte[] CMD = {ESC,16};
		
		return executaCmd( CMD );
		
	}
	
	//	Ativa ou desativa o horario de verão.
	public int programaHorarioVerao() {
		
		final byte[] CMD = {ESC,18};
		
		return executaCmd( CMD );
		
	}
	
	
	public int getStatus() {
		
		final byte[] CMD = {ESC,19};
		
		return executaCmd( CMD );
		
	}
	
	public void aguardaImpressao() {
		
		byte[] CMD = {ESC,19}; // status 		
		byte[] retorno = null;
		
		CMD = preparaCmd( CMD );
		
		while ( retorno == null || retorno.length < 2 ) {
			
		   retorno = enviaCmd( CMD );
		   getMenssagemRetorno( retorno );
         System.out.println("Aguardando o retorno de aguarda Impressao");
		   
		   try {
			   Thread.sleep(100);
		   } catch (InterruptedException e) { }
		   
		}
		
	}
	
	public int relatorioGerencial( final String texto ) {
		
		byte[] CMD = {ESC,20};
		
		CMD = adicBytes( CMD, parseParam( texto, 620, true ).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	public int fechamentoRelatorioGerencial() {
		
		final byte[] CMD = {ESC,21};
		
		return executaCmd( CMD );
		
	}
	
	public int comprovanteNFiscalNVinculado( final String opt, final float valor, final String formaPag ) {
		
		byte[] CMD = {ESC,25};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( opt, 2, false ) );
		buf.append( parseParam( valor, 14, 2 ) );	
		buf.append( parseParam( formaPag, 16, false ) );		
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	public String retornoAliquotas() {
		final byte[] CMD = {ESC,26};
		executaCmd( CMD );
		return bcdToAsc(getBytesLidos());
	}
	
	public int retornoTotalizadoresParciais() {

		final byte[] CMD = {ESC,27};
		
		return executaCmd( CMD );
		
	}
	
	public int retornoSubTotal() {

		final byte[] CMD = {ESC,29};
		
		return executaCmd( CMD );
		
	}	

	public int retornoNumeroCupom() {

		final byte[] CMD = {ESC,30};
		
		return executaCmd( CMD );
		
	}
	
	//	Cancelamento de item generico.
	public int cancelaItemGenerico( final int item ) {
		
		byte[] CMD = {ESC,31};
		
		CMD = adicBytes( CMD, parseParam( item, 4).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Inicia o fechamento do cupom.
	public int iniciaFechamentoCupom( final char opt, final float valor ) {

		byte[] CMD = {ESC,32};
		
		int tamanho = 14;
		
		if( opt == ACRECIMO_PERCENTUAL || opt == DESCONTO_PERCENTUAL ) { 
			tamanho = 4;			
		}
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( opt ) );
		buf.append( parseParam( valor, tamanho, 2 ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes());
		
		return executaCmd( CMD );
		
	}
	
	//	Termina o fechamento do cupom.
	public int terminaFechamentoCupom( final String menssagem ) {
		
		byte[] CMD = {ESC,34};
		
		CMD = adicBytes( CMD, parseParam( menssagem, 492, true).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	// Truncamento / aredondamento
	public int programaTruncamentoArredondamento( final char opt ) {
		
		byte[] CMD = {ESC,39};
		
		CMD = adicBytes( CMD, parseParam( opt, 1).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	
	public int nomeiaTotalizadorNaoSujeitoICMS( final int indice, final String desc ) {
		
		byte[] CMD = {ESC,40};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( indice, 2 ) );
		buf.append( parseParam( desc, 19, false ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Venda de item com tres casas decimais.
	public int vendaItemTresCasas( final String codProd, final String descProd, final String sitTrib, final float qtd, final float valor, final float desconto ) {
		
		byte[] CMD = {ESC,56};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( codProd, 13, false ) );
		buf.append( parseParam( descProd, 29, false ) );
		buf.append( parseParam( sitTrib, 2, false ) );
		buf.append( parseParam( qtd, 7, 3 ) );
		buf.append( parseParam( valor, 8, 3 ) );
		buf.append( parseParam( desconto, 8, 2 ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Programa espaço entre as linhas em dots.
	public int programarEspacoEntreLinhas( final int espaco ) {
		
		byte[] CMD = {ESC,60};
		
		CMD = adicBytes( CMD, parseParam( (char)espaco ).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Programa espaço entre as linhas em dots.
	public int programarEspacoEntreCupons( final int espaco ) {
		
		byte[] CMD = {ESC,61};
		
		CMD = adicBytes( CMD, parseParam( (char)espaco ).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Programa a unidade de medida
	//	Valida somente para um item, depois volta ao default.
	public int programaUnidadeMedida( final String descUnid ) {
		
		byte[] CMD = {ESC,62,51};
		
		CMD = adicBytes( CMD, parseParam( descUnid, 2, false).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Aumenta a descrição do item para 200 caracteres
	//	Valida somente para um item, depois volta ao default.
	public int aumentaDescItem( final String descricao ) {
		
		byte[] CMD = {ESC,62,52};
		
		CMD = adicBytes( CMD, parseParam( descricao, 200, true ).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Venda de item com entrada de Departamento, Desconto e Unidade
	public int vendaItemDepartamento( final String sitTrib, final float valor, final float qtd, final float desconto, final float acrescimo, final int departamento, final String unidade, final String codProd, final String descProd ) {
		
		byte[] CMD = {ESC,63};
		
		final StringBuffer buf = new StringBuffer(312);
		
		buf.append( parseParam( sitTrib, 2, false ) );
		buf.append( parseParam( valor, 9, 3 ) );
		buf.append( parseParam( qtd, 7, 3 ) );
		buf.append( parseParam( desconto, 10, 2 ) );
		buf.append( parseParam( acrescimo, 10, 2 ) );
		buf.append( parseParam( departamento, 2 ) );
		buf.append( "00000000000000000000" );
		buf.append( parseParam( unidade, 2, false ) );
		buf.append( parseParam( codProd + (char)0, 49, false ) );
		buf.append( parseParam( descProd + (char)0, 200, false ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	public int programaCaracterParaAutenticacao( final byte[] caracteres ) {
		
		byte[] CMD = {ESC,64};
			
		CMD = adicBytes( CMD, caracteres );
		
		return executaCmd( CMD );
		
	}
	
	//	Nomeia os departamentos.
	public int nomeiaDepartamento( final int index, final String descricao ) {
		
		byte[] CMD = {ESC,65};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( index, 2 ) );
		buf.append( parseParam( descricao, 20, false ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	public int abreComprovanteNFiscalVinculado( final String formaPag, final float valor, final int doc ) {
		
		byte[] CMD = {ESC,66};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( formaPag, 16, false ) );
		buf.append( parseParam( valor, 14, 2 ) );
		buf.append( parseParam( doc, 6 ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	public int usaComprovanteNFiscalVinculado( final String texto ) {
		
		byte[] CMD = {ESC,67};
		
		CMD = adicBytes( CMD, parseParam( texto, 620, false ).getBytes());
		
		return executaCmd( CMD );
		
	}
	
	public int habilitaCupomAdicional( final char opt ) {
		
		byte[] CMD = {ESC,68};
		
		CMD = adicBytes( CMD, parseParam( opt, 1 ).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	// 	Executa a leitura X.
	public int leituraXSerial() {
		
		final byte[] CMD = {ESC,69};
		
		return executaCmd( CMD );
		
	}
	
	//	Caso a impressora estiver em erro inicialia a mesma.
	//  alguns erro podem ser recuperado em modo remoto.
	public int resetErro() {
		
		final byte[] CMD = {ESC,70};
		
		return executaCmd( CMD );
		
	}
	
	//	Programa formas de pagamentos,
	//	Validas somente para o mesmo dia.
	public int programaFormaPagamento( final String descricao ) {
		
		byte[] CMD = {ESC,71};
		
		CMD = adicBytes( CMD, parseParam( descricao, 16, false ).getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Efetua forma de pagamento.
	//	tem que ter troco...
	public int efetuaFormaPagamento( final int indice, final float valor, final String descForma ) {
		
		byte[] CMD = {ESC,72};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( indice, 2 ) );
		buf.append( parseParam( valor, 14, 2 ) );
		buf.append( parseParam( descForma, 80, false ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
	}
	
	//	Estorna de uma forma de pagamento a outra.
	//	O valor não pode exceder o valor da forma de origem. 
	public int estornoFormaPagamento( final String descOrigem, final String descDestino, final float valor ) {
		
		byte[] CMD = {ESC,74};
		
		final StringBuffer buf = new StringBuffer();
		
		buf.append( parseParam( descOrigem, 16, false ) );
		buf.append( parseParam( descDestino, 16, false ) );
		buf.append( parseParam( valor, 14, 2 ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );
		
		return executaCmd( CMD );
		
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
