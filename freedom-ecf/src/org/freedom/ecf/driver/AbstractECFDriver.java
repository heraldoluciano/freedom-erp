/*
 * Classe base para impressoras fiscais Autor: Robson Sanchez Data: 05/04/2006
 * 
 */
package org.freedom.ecf.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import org.freedom.ecf.com.Serial;

public abstract class AbstractECFDriver implements SerialPortEventListener {

	public static final byte ESC = 27;

	public static final byte STX = 2;

	public static final byte ACK = 6;

	public static final byte NAK = 21;

	public static final int TIMEOUT_ACK = 300;

	public static final int TIMEOUT_READ = 30000;

	public static final char ACRECIMO_PERC = 'A';

	public static final char DESCONTO_PERC = 'D';

	public static final char ACRECIMO_VALOR = 'a';

	public static final char DESCONTO_VALOR = 'd';

	public static final char IMPRESSAO = 'I';

	public static final char RETORNO = 'R';

	public static final char ICMS = '0';

	public static final char ISS = '1';

	public static final char TRUNCA = '0';

	public static final char ARREDONDA = '1';

	public static final char DES_CUPOM_ADIC = '0';

	public static final char HAB_CUPOM_ADIC = '1';

	public static final char V_NUM_SERIE = 0;

	public static final char V_VER_FIRMWARE = 1;

	public static final char V_CNPJ_IE = 2;

	public static final char V_GRANDE_TOTAL = 3;

	public static final char V_CANCELAMENTOS = 4;

	public static final char V_DESCONTOS = 5;

	public static final char V_CONT_SEQ = 6;

	public static final char V_OP_N_FISCAIS = 7;

	public static final char V_CUPONS_CANC = 8;

	public static final char V_REDUCOES = 9;

	public static final char V_NUM_INT_TEC = 10;

	public static final char V_NUM_SUB_PROP = 11;

	public static final char V_NUM_ULT_ITEM = 12;

	public static final char V_CLICHE = 13;

	public static final char V_NUM_CAIXA = 14;

	public static final char V_NUM_LOJA = 15;

	public static final char V_MOEDA = 16;

	public static final char V_FLAG_FISCAL = 17;

	public static final char V_TMP_LIGADA = 18;

	public static final char V_TMP_IMPRIMNDO = 19;

	public static final char V_FLAG_TEC = 20;

	public static final char V_FLAG_EPROM = 21;

	public static final char V_VLR_ULT_CUPOM = 22;

	public static final char V_DT_HORA = 23;

	public static final char V_TOT_NICMS = 24;

	public static final char V_DESC_TOT_NICMS = 25;

	public static final char V_DT_ULT_REDUCAO = 26;

	public static final char V_DT_MOVIMENTO = 27;

	public static final char V_FLAG_TRUNCA = 28;

	public static final char V_FLAG_VINC_ISS = 29;

	public static final char V_TOT_ACRECIMOS = 30;

	public static final char V_CONT_BILHETE = 31;

	public static final char V_FORMAS_PAG = 32;

	public static final char V_CNF_NVINCULADO = 33;

	public static final char V_DEPARTAMENTOS = 34;

	public static final char V_TIPO_IMP = 253;

	private byte[] bytesLidos = new byte[ 3 ];

	private static byte[] buffer = null;

	private static boolean leuEvento = false;

	protected String porta;

	protected int portaSel = -1;

	protected boolean ativada = false;

	protected SerialPort portaSerial = null;

	public AbstractECFDriver() {

		Locale.setDefault( new Locale( "pt", "BR" ) );

	}

	public byte[] adicBytes( final byte[] variavel, final byte[] incremental ) {

		byte[] retorno = new byte[ variavel.length + incremental.length ];

		for ( int i = 0; i < retorno.length; i++ ) {
			if ( i < variavel.length ) {
				retorno[ i ] = variavel[ i ];
			}
			else {
				retorno[ i ] = incremental[ i - variavel.length ];
			}
		}

		return retorno;

	}

	public boolean ativaPorta( final int com ) {

		boolean retorno = true;

		if ( !Serial.getInstance().isAtivada() ) {
			retorno = Serial.getInstance().ativaPorta( com, this );
		}

		return retorno;
	}

	public void setBytesLidos( final byte[] arg ) {

		bytesLidos = new byte[ arg.length ];
		System.arraycopy( arg, 0, bytesLidos, 0, bytesLidos.length );

	}

	public byte[] getBytesLidos() {

		final byte[] retorno = new byte[ bytesLidos.length ];

		System.arraycopy( bytesLidos, 0, retorno, 0, retorno.length );

		return retorno;

	}

	public void serialEvent( final SerialPortEvent event ) {

		byte[] retorno = null;
		byte[] bufferTmp = null;
		byte[] tmp = null;
		InputStream entrada = null;

		entrada = Serial.getInstance().getEntrada();

		try {

			if ( event.getEventType() == SerialPortEvent.DATA_AVAILABLE ) {

				retorno = new byte[ entrada.available() ];

				if ( retorno != null ) {

					if ( buffer == null ) {
						bufferTmp = retorno;
					}
					else {

						leuEvento = true;
						tmp = buffer;
						bufferTmp = new byte[ tmp.length + retorno.length ];

						for ( int i = 0; i < bufferTmp.length; i++ ) {

							if ( i < tmp.length ) {
								bufferTmp[ i ] = tmp[ i ];
							}
							else {
								bufferTmp[ i ] = retorno[ i - tmp.length ];
							}

						}
					}

					buffer = bufferTmp;

				}

			}

		} catch ( IOException e ) {
			e.printStackTrace();
		}

	}

	public byte[] enviaCmd( final byte[] CMD, final int tamEsperado ) {

		return enviaCmd( CMD, portaSel, tamEsperado );

	}

	public byte[] enviaCmd( final byte[] CMD, final int com, final int tamRetorno ) {

		long tempo = 0;
		long tempoAtual = 0;
		leuEvento = false;
		buffer = null;
		final OutputStream saida = Serial.getInstance().getSaida();

		if ( ativaPorta( com ) ) {

			try {

				// saida.
				saida.flush();
				tempo = System.currentTimeMillis();
				saida.write( CMD );

				do {

					Thread.sleep( TIMEOUT_ACK );
					tempoAtual = System.currentTimeMillis();

				} while ( ( tempoAtual - tempo ) < ( TIMEOUT_READ ) && ( buffer == null || buffer.length < tamRetorno || !leuEvento ) );

			} catch ( IOException e ) {
				e.printStackTrace();
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}

		}

		return buffer;

	}

	public String parseParam( final String param, final int tamanho, final boolean terminador ) {

		final StringBuffer tmp = new StringBuffer();

		if ( param != null ) {

			if ( terminador ) {

				if ( param.indexOf( String.valueOf( (char) 10 ) ) <= -1 ) {

					if ( tamanho <= param.length() ) {
						tmp.append( param.substring( 0, tamanho - 1 ) );
					}

					tmp.append( param );
					tmp.append( (char) 10 );

				}

			}
			else {

				if ( tamanho < param.length() ) {
					tmp.append( param.substring( 0, tamanho ) );
				}
				else {
					tmp.append( param );
				}
			}

		}

		return tmp.toString();

	}

	public String parseParam( final int param, final int tamanho ) {

		return strZero( String.valueOf( param ), tamanho );

	}

	public String parseParam( final char param ) {

		return String.valueOf( param );

	}

	public String parseParam( final float param, final int tamanho, final int casasdec ) {

		return strDecimalToStrCurrency( param, tamanho, casasdec );

	}

	public String parseParam( final Date param ) {

		final SimpleDateFormat sdf = new SimpleDateFormat( "ddMMyy", Locale.getDefault() );

		return sdf.format( param ).trim();

	}

	public String replicate( final String texto, final int quant ) {

		final StringBuffer sRetorno = new StringBuffer();

		for ( int i = 0; i < quant; i++ ) {
			sRetorno.append( texto );
		}

		return sRetorno.toString();

	}

	public String strDecimalToStrCurrency( final float param, final int tamanho, final int casasdec ) {

		final StringBuffer str = new StringBuffer();
		str.append( String.valueOf( param ) );

		final char[] strTochar = str.toString().toCharArray();
		final int index = str.indexOf( "." );
		final int indexDesc = casasdec - ( ( strTochar.length - 1 ) - index );

		str.delete( 0, strTochar.length );

		for ( int i = 0; i < strTochar.length; i++ ) {
			if ( i != index ) {
				str.append( strTochar[ i ] );
			}
		}

		for ( int i = 0; i < indexDesc; i++ ) {
			str.append( 0 );
		}

		return strZero( str.toString(), tamanho );

	}

	public String strZero( final String val, final int zeros ) {

		String str = val;

		if ( str == null ) {
			str = "";
		}

		final StringBuffer sRetorno = new StringBuffer();

		sRetorno.append( replicate( "0", zeros - str.trim().length() ) );
		sRetorno.append( str.trim() );

		return sRetorno.toString();

	}

	public abstract byte[] preparaCmd( byte[] CMD );

	public abstract int executaCmd( byte[] CMD, int tamRetorno );

	public abstract int checkRetorno( byte[] bytes );

	// //////////////////////
	// /// /////
	// /// COMANDOS /////
	// /// /////
	// //////////////////////

	public abstract int aberturaDeCupom();// 0

	public abstract int aberturaDeCupom( String cnpj );// 0

	public abstract int alteraSimboloMoeda( String simbolo );// 1

	public abstract int leituraX();// 5

	public abstract int reducaoZ();// 6

	public abstract int adicaoDeAliquotaTriburaria( String aliq, char opt );// 7

	public abstract int leituraMemoriaFiscal( Date dataIni, Date dataFim, char tipo );// 8

	public abstract int leituraMemoriaFiscal( int ini, int fim, char tipo );// 8

	public abstract int vendaItem( String codProd, String descProd, String sitTrib, float qtd, float valor, float desconto );// 9

	public abstract int cancelaItemAnterior();// 13

	public abstract int cancelaCupom();// 14

	public abstract int autenticacaoDeDocumento();// 16

	public abstract int programaHorarioVerao();// 18

	public abstract String getStatus();// 19;

	public abstract int relatorioGerencial( String texto );// 20

	public abstract int fechamentoRelatorioGerencial();// 21

	public abstract int acionaGavetaDinheiro( int time );// 22

	public abstract String retornoEstadoGavetaDinheiro();// 23

	public abstract int comprovanteNFiscalNVinculado( String opt, float valor, String formaPag );// 25

	public abstract String retornoAliquotas();// 26

	public abstract String retornoTotalizadoresParciais();// 27

	public abstract String retornoSubTotal();// 29

	public abstract String retornoNumeroCupom();// 30

	public abstract int cancelaItemGenerico( int item );// 31

	public abstract int iniciaFechamentoCupom( char opt, float percentual );// 32

	public abstract int terminaFechamentoCupom( String menssagem );// 34

	public abstract String retornoVariaveis( char var );// 35

	public abstract int programaTruncamentoArredondamento( char opt );// 39

	public abstract int nomeiaTotalizadorNaoSujeitoICMS( int indice, String desc );// 40

	public abstract int vendaItemTresCasas( String codProd, String descProd, String sitTrib, float qtd, float valor, float desconto );// 56

	public abstract int programaMoedaSingular( String nomeSingular );// 58

	public abstract int programaMoedaPlural( String nomePlurar );// 59

	public abstract int programarEspacoEntreLinhas( int espaco );// 60

	public abstract int programarEspacoEntreCupons( int espaco );// 61

	public abstract int programaUnidadeMedida( String descUnid );// 62 51

	public abstract int aumentaDescItem( String descricao );// 62 52

	public abstract String retornoEstadoPapel();// 62 54

	public abstract String retornoUltimaReducao();// 62 55

	public abstract int vendaItemDepartamento( String sitTrib, float valor, float qtd, float desconto, float acrescimo, int departamento, String unidade, String codProd, String descProd );// 63

	public abstract int programaCaracterParaAutenticacao( int[] caracteres );// 64

	public abstract int nomeiaDepartamento( int index, String descricao );// 65

	public abstract int abreComprovanteNFiscalVinculado( String formaPag, float valor, int doc );// 66

	public abstract int usaComprovanteNFiscalVinculado( String texto );// 67

	public abstract int habilitaCupomAdicional( char opt );// 68

	public abstract int leituraXSerial();// 69

	public abstract int resetErro();// 70

	public abstract String programaFormaPagamento( String descricao );// 71

	public abstract int efetuaFormaPagamento( String indice, float valor, String descForma );// 72

	public abstract int estornoFormaPagamento( String descOrigem, String descDestino, float valor );// 74

}
