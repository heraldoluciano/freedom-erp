/**
 * @version 1.0.0 - 05/04/2006 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez/Alex Rodrigues <BR>
 * 
 * Projeto: Freedom-ECF <BR>
 * Pacote: org.freedom.ecf.driver <BR>
 * Classe:
 * @(#)ECFBematech.java <BR>
 * @see org.freedom.ecf.driver.AbstractECFDriver
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Esta classe implementa metodos de acesso a comandos de impressão <BR>
 * para impressoras fiscais Bematech.
 * 
 */
package org.freedom.ecf.driver;

import java.util.Date;

public class ECFBematech extends AbstractECFDriver {

	/**
	 * Construtor da classe ECFBematech. <BR>
	 * Inicia a construção da classe chamando o construtor padrão da classe super <BR>
	 * e chama o metodo ativaPorta(int). <BR>
	 * 
	 * @param com
	 *            parametro para ativação da porta serial.<BR>
	 */
	public ECFBematech( final int com ) {

		super();
		ativaPorta( com );
	}

	/**
	 * Prepara o comando conforme o protocolo de comunicação com a impressora. <BR>
	 * O protocolo de comunicação com a impressora é estruturado <BR>
	 * em blocos e possui a seguinte forma: <BR>
	 * <BR>
	 * STX - byte indicativo de inicio de transmissão. <BR>
	 * NBL - byte nenos significativo, da soma do número de bytes que serão enviados. <BR>
	 * NBH - byte mais significativo, da soma do número de bytes que serão enviados. <BR>
	 * CMD - Sequência de bytes que compõe o comando e seus parâmetros. <BR>
	 * CSL - byte menos significativo, da soma dos valores dos bytes que compõem o camando e seu parâmetros(CMD). <BR>
	 * 
	 * @see org.freedom.ecf.driver.AbstractECFDriver#preparaCmd(byte[])
	 * @param CMD
	 *            comando a ser executado e seus parâmetros. <BR>
	 */
	public byte[] preparaCmd( final byte[] CMD ) {

		final int tamCMD = CMD.length;
		final int tam = tamCMD + 2;
		int soma = 0;
		byte CSL = 0;
		byte CSH = 0;
		final byte NBL = (byte) ( tam % 256 );
		final byte NBH = (byte) ( tam / 256 );
		byte[] retorno = new byte[ 5 + tamCMD ];

		retorno[ 0 ] = STX;
		retorno[ 1 ] = NBL;
		retorno[ 2 ] = NBH;

		for ( int i = 0; i < tamCMD; i++ ) {
			soma += CMD[ i ];
			retorno[ i + 3 ] = CMD[ i ];
		}

		CSL = (byte) ( soma % 256 );
		CSH = (byte) ( soma / 256 );

		retorno[ retorno.length - 2 ] = CSL;
		retorno[ retorno.length - 1 ] = CSH;

		return retorno;

	}

	/**
	 * Este metodo executa o comando chamando os metodos <BR>
	 * preparaCmd(byte[]) <BR>
	 * enviaCmd(byte[]) <BR>
	 * aguardaImpressal(byte[]) <BR>
	 * e devolve o resultado do emvio do camando. <BR>
	 * 
	 * @see org.freedom.ecf.driver.AbstractECFDriver#executaCmd(byte[])
	 * @param CMD
	 *            comando a ser executado e seus parâmetros. <BR>
	 */
	public int executaCmd( final byte[] CMD, final int tamRetorno ) {

		byte[] retorno = null;
		byte[] cmd = null;

		cmd = preparaCmd( CMD );
		retorno = enviaCmd( cmd, tamRetorno );

		// aguardaImpressao();

		return checkRetorno( retorno );

	}

	/**
	 * Converte o retorno dos comandos <BR>
	 * do formato BCD para ASCII. <BR>
	 * 
	 * @param bcdParam
	 *            Retorno a ser convertido. <BR>
	 * @return String com o resultado da converção. <BR>
	 */
	private String bcdToAsc( final byte[] bcdParam ) {

		final StringBuffer retorno = new StringBuffer();

		byte bcd = 0;
		byte byteBH = 0;
		byte byteBL = 0;

		for ( int i = 0; i < bcdParam.length; i++ ) {

			bcd = bcdParam[ i ];

			byteBH = (byte) ( (int) bcd / 16 );
			byteBL = (byte) ( (int) bcd % 16 );

			retorno.append( byteBH );
			retorno.append( byteBL );

		}

		return retorno.toString();

	}

	/**
	 * Formata os retorna enviado pela impressora <BR>
	 * separando o STATUS do estado da impressora <BR>
	 * dos dados do retorno, onde <BR>
	 * ACK (06) - byte indicativo de recebimento correto. <BR>
	 * ST1 2 ST2 - bytes de estado da impressora. <BR>
	 * NAK (15h ou 21d) - byte indicativo de recebimento incorreto. <BR>
	 * <BR>
	 * O retorno tem a seguinte sintaxe : <BR>
	 * [ACK][retorno solicitado][ST1][ST2] <BR>
	 * 
	 * @see org.freedom.ecf.driver.AbstractECFDriver#checkRetorno(byte[])
	 * @param bytes
	 *            bytes retornados pela porta serial.<BR>
	 * @return retorno indece para a mensagem.
	 */
	public int checkRetorno( final byte[] bytes ) {

		int retorno = 0;
		byte ack = 0;
		byte st1 = 0;
		byte st2 = 0;

		if ( bytes != null ) {

			ack = bytes[ 0 ];

			if ( bytes.length > 3 ) {

				st1 = bytes[ bytes.length - 2 ];
				st2 = bytes[ bytes.length - 1 ];

				final byte[] bytesLidos = new byte[ bytes.length - 3 ];
				System.arraycopy( bytes, 1, bytesLidos, 0, bytesLidos.length );
				setBytesLidos( bytesLidos );

			}

			if ( ack == ACK ) {
				retorno = 1;
			}
			else {

				retorno = -27; // Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)

				retorno = checkST1( st1 );

				retorno = checkST2( st2 );

			}

		}

		return retorno;

	}

	private int checkST1( final byte ST1 ) {

		int retorno = 0;
		byte st1 = ST1;

		if ( st1 > 127 ) {
			st1 -= 128;
		}
		if ( st1 > 63 ) {
			st1 -= 64;
		}
		if ( st1 > 31 ) {
			st1 -= 32;
		}
		if ( st1 > 15 ) {
			st1 -= 16;
		}
		if ( st1 > 7 ) {
			st1 -= 8;
		}
		if ( st1 > 3 ) {
			st1 -= 4;
		}
		if ( st1 > 1 ) {
			st1 -= 2;
		}
		if ( st1 > 0 ) {
			st1 -= 1;
			retorno = -2; // "Parâmetro inválido na função. ou Número de parâmetros inválido na funçao"
		}

		return retorno;

	}

	private int checkST2( final byte ST2 ) {

		int retorno = 0;
		byte st2 = ST2;

		if ( st2 > 127 ) {
			retorno = -2; // "Parâmetro inválido na função."
			st2 -= 128;
		}
		if ( st2 > 63 ) {
			st2 -= 64;
		}
		if ( st2 > 31 ) {
			st2 -= 32;
		}
		if ( st2 > 15 ) {
			st2 -= 16;
		}
		if ( st2 > 7 ) {
			st2 -= 8;
		}
		if ( st2 > 3 ) {
			st2 -= 4;
		}
		if ( st2 > 1 ) {
			st2 -= 2;
		}
		if ( st2 > 0 ) {
			st2 -= 1;
			retorno = -2; // "Parâmetro inválido na função. ou Número de parâmetros inválido na funçao"
		}

		return retorno;

	}

	/**
	 * Abertura de Cupom Fiscal. <BR>
	 * 
	 * @see org.freedom.ecf.driver.AbstractECFDriver#aberturaDeCupom()
	 * @return vide metodo checkRetorno(byte[]).<BR>
	 */
	public int aberturaDeCupom() {

		final byte[] CMD = { ESC, 0 };

		return executaCmd( CMD, 3 );

	}

	/**
	 * Abertura de Cupom Fiscal com CNPJ do cliente.<BR>
	 * 
	 * @see org.freedom.ecf.driver.AbstractECFDriver#aberturaDeCupom(java.lang.String)
	 * @param cnpj
	 *            CNPJ/CPF do cliente.<BR>
	 * @return vide metodo checkRetorno(byte[]).<BR>
	 */
	public int aberturaDeCupom( final String cnpj ) {

		byte[] CMD = { ESC, 0 };
		CMD = adicBytes( CMD, parseParam( cnpj, 29, false ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Altera simbolo da moeda corrente, não a nescidade de passar o $ no parametro.
	public int alteraSimboloMoeda( final String simbolo ) {

		byte[] CMD = { ESC, 1 };
		CMD = adicBytes( CMD, parseParam( simbolo, 2, false ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Executa a redução Z.
	public int reducaoZ() {

		final byte[] CMD = { ESC, 5 };

		return executaCmd( CMD, 3 );

	}

	// Executa a leitura X.
	public int leituraX() {

		final byte[] CMD = { ESC, 6 };

		return executaCmd( CMD, 3 );

	}

	// adiciona aliquotas.
	public int adicaoDeAliquotaTriburaria( final String aliq, final char opt ) {

		byte[] CMD = { ESC, 7 };

		final StringBuffer buf = new StringBuffer();
		buf.append( parseParam( aliq, 4, false ) );

		if ( ISS == opt ) {
			buf.append( opt );
		}

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Leitura da memoria fiscal por data.
	public int leituraMemoriaFiscal( final Date dataIni, final Date dataFim, final char tipo ) {

		byte[] CMD = { ESC, 8 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( dataIni ) );
		buf.append( parseParam( dataFim ) );
		buf.append( parseParam( tipo, 1 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Leitura da memoria fiscal por reuções.
	public int leituraMemoriaFiscal( final int ini, final int fim, final char tipo ) {

		byte[] CMD = { ESC, 8 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( ini, 6 ) );
		buf.append( parseParam( fim, 6 ) );
		buf.append( parseParam( tipo, 1 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Venda de item.
	public int vendaItem( final String codProd, final String descProd, final String sitTrib, final float qtd, final float valor, final float desconto ) {

		byte[] CMD = { ESC, 9 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( codProd, 13, false ) );
		buf.append( parseParam( descProd, 29, false ) );
		buf.append( parseParam( sitTrib, 2, false ) );
		buf.append( parseParam( qtd, 7, 3 ) );
		buf.append( parseParam( valor, 8, 2 ) );
		buf.append( parseParam( desconto, 8, 2 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Cancelamento do item anterior.
	public int cancelaItemAnterior() {

		final byte[] CMD = { ESC, 13 };

		return executaCmd( CMD, 3 );

	}

	// Cancelamento do cupom.
	public int cancelaCupom() {

		final byte[] CMD = { ESC, 14 };

		return executaCmd( CMD, 3 );

	}

	// Autenticação de documento.
	public int autenticacaoDeDocumento() {

		final byte[] CMD = { ESC, 16 };

		return executaCmd( CMD, 3 );

	}

	// Ativa ou desativa o horario de verão.
	public int programaHorarioVerao() {

		final byte[] CMD = { ESC, 18 };

		return executaCmd( CMD, 3 );

	}

	public String getStatus() {

		final byte[] CMD = preparaCmd( new byte[] { ESC, 19 } );

		final byte[] ret = enviaCmd( CMD, 3 );

		final StringBuffer retorno = new StringBuffer();
		retorno.append( ret[ 0 ] + "," );
		retorno.append( ret[ 1 ] + "," );
		retorno.append( ret[ 2 ] );

		return retorno.toString();

	}

	public void aguardaImpressao() {

		byte[] CMD = { ESC, 19 };
		// byte[] retorno = null;
		byte[] retorno = new byte[ 1 ];
		CMD = preparaCmd( CMD );

		while ( /* retorno == null || */retorno.length < 2 ) {

			// depois que entra do laço e ocorre algum erro no envio do comando
			// a condição de retorno == null valida o laço
			// tornando ele um laço infinito...

			retorno = enviaCmd( CMD, 3 );

			try {
				Thread.sleep( 100 );
			} catch ( InterruptedException e ) {
			}

		}

	}

	public int relatorioGerencial( final String texto ) {

		byte[] CMD = { ESC, 20 };

		CMD = adicBytes( CMD, parseParam( texto, 620, true ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	public int fechamentoRelatorioGerencial() {

		final byte[] CMD = { ESC, 21 };

		return executaCmd( CMD, 3 );

	}

	public int acionaGavetaDinheiro( final int time ) {

		byte[] CMD = { ESC, 22 };

		CMD = adicBytes( CMD, parseParam( (char) time ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	public String retornoEstadoGavetaDinheiro() {

		final byte[] CMD = { ESC, 23 };

		executaCmd( CMD, 4 );

		return bcdToAsc( getBytesLidos() );

	}

	public int comprovanteNFiscalNVinculado( final String opt, final float valor, final String formaPag ) {

		byte[] CMD = { ESC, 25 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( opt, 2, false ) );
		buf.append( parseParam( valor, 14, 2 ) );
		buf.append( parseParam( formaPag, 16, false ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	public String retornoAliquotas() {

		final byte[] CMD = { ESC, 26 };

		executaCmd( CMD, 68 );

		return bcdToAsc( getBytesLidos() );

	}

	public String retornoTotalizadoresParciais() {

		final byte[] CMD = { ESC, 27 };

		executaCmd( CMD, 222 );

		return bcdToAsc( getBytesLidos() );

	}

	public String retornoSubTotal() {

		final byte[] CMD = { ESC, 29 };

		executaCmd( CMD, 17 );

		return bcdToAsc( getBytesLidos() );

	}

	public String retornoNumeroCupom() {

		final byte[] CMD = { ESC, 30 };

		executaCmd( CMD, 9 );

		return bcdToAsc( getBytesLidos() );

	}

	// Cancelamento de item generico.
	public int cancelaItemGenerico( final int item ) {

		byte[] CMD = { ESC, 31 };

		CMD = adicBytes( CMD, parseParam( item, 4 ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Inicia o fechamento do cupom.
	public int iniciaFechamentoCupom( final char opt, final float valor ) {

		byte[] CMD = { ESC, 32 };

		int tamanho = 14;

		if ( opt == ACRECIMO_PERC || opt == DESCONTO_PERC ) {
			tamanho = 4;
		}

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( opt ) );
		buf.append( parseParam( valor, tamanho, 2 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Termina o fechamento do cupom.
	public int terminaFechamentoCupom( final String menssagem ) {

		byte[] CMD = { ESC, 34 };

		CMD = adicBytes( CMD, parseParam( menssagem, 492, true ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	public String retornoVariaveis( final char var ) {

		final byte[] CMD = { ESC, 35, (byte) var };
		/*
		 * o tamanho dos bytes de retorno varia conforme o parametro.
		 */
		executaCmd( CMD, 0 );

		String retorno = "";

		if ( var == VAR_NUM_SERIE || var == VAR_CNPJ_IE || var == VAR_CLICHE || var == VAR_MOEDA ) {

			retorno = new String( getBytesLidos() );

		}
		else {

			retorno = bcdToAsc( getBytesLidos() );

		}

		return retorno;

	}

	// Truncamento / aredondamento
	public int programaTruncamentoArredondamento( final char opt ) {

		byte[] CMD = { ESC, 39 };

		CMD = adicBytes( CMD, parseParam( opt, 1 ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	//	
	public int nomeiaTotalizadorNaoSujeitoICMS( final int indice, final String desc ) {

		byte[] CMD = { ESC, 40 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( indice, 2 ) );
		buf.append( parseParam( desc, 19, false ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Venda de item com tres casas decimais.
	public int vendaItemTresCasas( final String codProd, final String descProd, final String sitTrib, final float qtd, final float valor, final float desconto ) {

		byte[] CMD = { ESC, 56 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( codProd, 13, false ) );
		buf.append( parseParam( descProd, 29, false ) );
		buf.append( parseParam( sitTrib, 2, false ) );
		buf.append( parseParam( qtd, 7, 3 ) );
		buf.append( parseParam( valor, 8, 3 ) );
		buf.append( parseParam( desconto, 8, 2 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Programa espaço entre as linhas em dots.
	public int programarEspacoEntreLinhas( final int espaco ) {

		byte[] CMD = { ESC, 60 };

		CMD = adicBytes( CMD, parseParam( (char) espaco ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Programa espaço entre as linhas em dots.
	public int programarEspacoEntreCupons( final int espaco ) {

		byte[] CMD = { ESC, 61 };

		CMD = adicBytes( CMD, parseParam( (char) espaco ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Programa a unidade de medida
	// Valida somente para um item, depois volta ao default.
	public int programaUnidadeMedida( final String descUnid ) {

		byte[] CMD = { ESC, 62, 51 };

		CMD = adicBytes( CMD, parseParam( descUnid, 2, false ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Aumenta a descrição do item para 200 caracteres
	// Valida somente para um item, depois volta ao default.
	public int aumentaDescItem( final String descricao ) {

		byte[] CMD = { ESC, 62, 52 };

		CMD = adicBytes( CMD, parseParam( descricao, 200, true ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	public String retornoEstadoPapel() {

		final byte[] CMD = { ESC, 62, 54 };

		executaCmd( CMD, 5 );

		return bcdToAsc( getBytesLidos() );

	}

	public String retornoUltimaReducao() {

		final byte[] CMD = { ESC, 62, 55 };

		executaCmd( CMD, 308 );

		return bcdToAsc( getBytesLidos() );

	}

	// Venda de item com entrada de Departamento, Desconto e Unidade
	public int vendaItemDepartamento( final String sitTrib, final float valor, final float qtd, final float desconto, final float acrescimo, final int departamento, final String unidade, final String codProd, final String descProd ) {

		byte[] CMD = { ESC, 63 };

		final StringBuffer buf = new StringBuffer( 312 );

		buf.append( parseParam( sitTrib, 2, false ) );
		buf.append( parseParam( valor, 9, 3 ) );
		buf.append( parseParam( qtd, 7, 3 ) );
		buf.append( parseParam( desconto, 10, 2 ) );
		buf.append( parseParam( acrescimo, 10, 2 ) );
		buf.append( parseParam( departamento, 2 ) );
		buf.append( "00000000000000000000" );
		buf.append( parseParam( unidade, 2, false ) );
		buf.append( parseParam( codProd + (char) 0, 49, false ) );
		buf.append( parseParam( descProd + (char) 0, 200, false ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	public int programaCaracterParaAutenticacao( final int[] caracteres ) {

		byte[] CMD = { ESC, 64 };

		byte[] bytes = new byte[ caracteres.length ];
		for ( int i = 0; i < caracteres.length; i++ ) {
			bytes[ i ] = (byte) caracteres[ i ];
		}
		CMD = adicBytes( CMD, bytes );

		return executaCmd( CMD, 3 );

	}

	// Nomeia os departamentos.
	public int nomeiaDepartamento( final int index, final String descricao ) {

		byte[] CMD = { ESC, 65 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( index, 2 ) );
		buf.append( parseParam( descricao, 20, false ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	public int abreComprovanteNFiscalVinculado( final String formaPag, final float valor, final int doc ) {

		byte[] CMD = { ESC, 66 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( formaPag, 16, false ) );
		buf.append( parseParam( valor, 14, 2 ) );
		buf.append( parseParam( doc, 6 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	public int usaComprovanteNFiscalVinculado( final String texto ) {

		byte[] CMD = { ESC, 67 };

		CMD = adicBytes( CMD, parseParam( texto, 620, false ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	public int habilitaCupomAdicional( final char opt ) {

		byte[] CMD = { ESC, 68 };

		CMD = adicBytes( CMD, parseParam( opt, 1 ).getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Executa a leitura X.
	public int leituraXSerial() {

		final byte[] CMD = { ESC, 69 };

		return executaCmd( CMD, 3 );

	}

	// Caso a impressora estiver em erro inicialia a mesma.
	// alguns erro podem ser recuperado em modo remoto.
	public int resetErro() {

		final byte[] CMD = { ESC, 70 };

		return executaCmd( CMD, 3 );

	}

	// Programa formas de pagamentos,
	// Validas somente para o mesmo dia.
	public int programaFormaPagamento( final String descricao ) {

		byte[] CMD = { ESC, 71 };

		CMD = adicBytes( CMD, parseParam( descricao, 16, false ).getBytes() );

		executaCmd( CMD, 5 );

		final String retorno = bcdToAsc( getBytesLidos() );

		return Integer.parseInt( retorno.substring( 0, 2 ) );

	}

	// Efetua forma de pagamento.
	// tem que ter troco...
	public int efetuaFormaPagamento( final int indice, final float valor, final String descForma ) {

		byte[] CMD = { ESC, 72 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( indice, 2 ) );
		buf.append( parseParam( valor, 14, 2 ) );
		buf.append( parseParam( descForma, 80, false ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

	// Estorna de uma forma de pagamento a outra.
	// O valor não pode exceder o valor da forma de origem.
	public int estornoFormaPagamento( final String descOrigem, final String descDestino, final float valor ) {

		byte[] CMD = { ESC, 74 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( descOrigem, 16, false ) );
		buf.append( parseParam( descDestino, 16, false ) );
		buf.append( parseParam( valor, 14, 2 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );

	}

}
