/*
 * Classe base para impressoras fiscais
 * Autor: Robson Sanchez
 * Data: 05/04/2006
 *
 */
package org.freedom.ecf.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

public abstract class AbstractECFDriver implements SerialPortEventListener {
	
	public static final byte ESC = 27;
	public static final byte STX = 2;
	public static final byte ACK = 6;
	public static final byte NAK = 21;
	public static final int TIMEOUT = 1000;
	public static final int TIMEOUT_ACK = 150;
	public static final int TIMEOUT_READ = 30000;
	public static final int BAUDRATE = 9600;
	public static final int DATABITS = SerialPort.DATABITS_8;
	public static final int STOPBITS = SerialPort.STOPBITS_1;
	public static final int PARITY = SerialPort.PARITY_NONE;
	public static final int COM1 = 0;
	public static final int COM2 = 1;
	public static final int COM3 = 2;
	public static final int COM4 = 3;
	public static final int COM5 = 4;
	public static final int OS_NONE = -1;
	public static final int OS_LINUX = 0;
	public static final int OS_WINDOWS = 1;
	
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
	
	public static final char VAR_NUM_SERIE = 0;
	public static final char VAR_VER_FIRMWARE = 1;
	public static final char VAR_CNPJ_IE = 2;
	public static final char VAR_GRANDE_TOTAL= 3;
	public static final char VAR_CANCELAMENTOS = 4;
	public static final char VAR_DESCONTOS = 5;
	public static final char VAR_CONT_SEQ = 6;
	public static final char VAR_OP_N_FISCAIS = 7;
	public static final char VAR_CUPONS_CANC = 8;
	public static final char VAR_REDUCOES = 9;
	public static final char VAR_NUM_INT_TEC = 10;
	public static final char VAR_NUM_SUB_PROP = 11;
	public static final char VAR_NUM_ULT_ITEM = 12;
	public static final char VAR_CLICHE = 13;
	public static final char VAR_NUM_CAIXA = 14;
	public static final char VAR_NUM_LOJA = 15;
	public static final char VAR_MOEDA = 16;
	public static final char VAR_FLAG_FISCAL = 17;
	public static final char VAR_TMP_LIGADA = 18;
	public static final char VAR_TMP_IMPRIMNDO = 19;
	public static final char VAR_FLAG_TEC = 20;
	public static final char VAR_FLAG_EPROM = 21;
	public static final char VAR_VLR_ULT_CUPOM = 22;
	public static final char VAR_DT_HORA = 23;
	public static final char VAR_TOT_NICMS = 24;
	public static final char VAR_DESC_TOT_NICMS = 25;
	public static final char VAR_DT_ULT_REDUCAO = 26;
	public static final char VAR_DT_MOVIMENTO = 27;
	public static final char VAR_FLAG_TRUNCA = 28;
	public static final char VAR_FLAG_VINC_ISS = 29;
	public static final char VAR_TOT_ACRECIMOS = 30;
	//public static final char VAR_CONT_BILHETE = 31;
	public static final char VAR_FORMAS_PAG = 32;
	public static final char VAR_CNF_NVINCULADO = 33;
	public static final char VAR_DEPARTAMENTOS = 34;
	public static final char VAR_TIPO_IMP = 253;

	private int sistema = -1;
	private byte[] bytesLidos = new byte[3];
    private InputStream entrada = null;
    private OutputStream saida = null;
	private byte[] buffer = null;
	private boolean leuEvento = false;
    
	protected String porta;
	protected int portaSel = -1;
	protected boolean ativada = false;
	protected SerialPort portaSerial = null;
	
	public AbstractECFDriver () {
		
	}
		   
	public byte[] adicBytes( final byte[] variavel, final byte[] incremental ) {
		
		byte[] retorno = new byte[ variavel.length + incremental.length ];
		
		for ( int i=0 ; i < retorno.length; i++ ) {		
			if( i < variavel.length ) {
				retorno[i] = variavel[i];
			}
			else {
				retorno[i] = incremental[ i - variavel.length];
			}
		}
		
		return retorno;
		
	}

	public boolean ativaPorta( final int com ) {
		
		boolean retorno = true;
		
		if ( com != portaSel || portaSerial == null ) {
			portaSel = com;
			porta = convPorta( com );
			portaSerial = ativaSerial( porta );
			
			if ( portaSerial == null ) {
				retorno = false;
			} else {
				try {
					portaSerial.addEventListener(this);
					portaSerial.notifyOnDataAvailable(true);
				}
				catch (TooManyListenersException e) {
					retorno = false;
				}
			}
			ativada = retorno;
		}
		return retorno;
	}
	
	
	
	public SerialPort ativaSerial( final String porta ) {
		
		SerialPort portaSerial = null;
		Enumeration listaPortas = null;
		CommPortIdentifier ips = null;
		listaPortas = CommPortIdentifier.getPortIdentifiers();
		
		while ( listaPortas.hasMoreElements() ) {
			
			ips = ( CommPortIdentifier ) listaPortas.nextElement();
			
			if ( ips.getName().equalsIgnoreCase( porta ) ) {
				break;
			}
			else {
				ips = null;
			}
			
		}
		
		if ( ips != null ) {
			
			try {
								
				portaSerial = ( SerialPort ) ips.open( "SComm", TIMEOUT );
				
				if ( portaSerial != null ) {
					
	            	entrada = portaSerial.getInputStream();
	            	saida = portaSerial.getOutputStream();
					portaSerial.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_OUT);
					portaSerial.setSerialPortParams(BAUDRATE, DATABITS, STOPBITS, PARITY);
					
				}
				
			} catch( PortInUseException e ) {
				e.printStackTrace();
			} catch ( UnsupportedCommOperationException e ) {
				
			} catch ( IOException e ) {
				portaSerial = null;
			}
			
		}
		
		return portaSerial;
		
	}
	
	public String convPorta( final int com ) {
		
		final StringBuffer porta = new StringBuffer();
		
		if ( getSistema() == OS_WINDOWS ) { 
			porta.append( "COM" );
			porta.append( com + 1 );
		}
		else {
			porta.append( "/dev/ttyS" );
			porta.append( com );
		}
		
		return porta.toString();
		
	}
	
	public boolean getAtivada() {
		return ativada;
	}
	
	public void setBytesLidos( final byte[] arg ) {
		
		bytesLidos = new byte[ arg.length ];
		System.arraycopy( arg, 0, bytesLidos, 0, bytesLidos.length );
		
	}

	public byte[] getBytesLidos() {
		
		final byte[] retorno = new byte[bytesLidos.length];
		
		System.arraycopy( bytesLidos, 0, retorno, 0, retorno.length );
		
		return retorno;
		
	}

	public int getSistema() {
		
		final String system = System.getProperty( "os.name" ).toLowerCase();
		
		if ( sistema == OS_NONE ) {
					  
		  if ( system.indexOf( "linux" ) > OS_NONE ) {
			  sistema = OS_LINUX;
		  }
		  else if ( system.indexOf("windows") > OS_NONE ) {
			  sistema = OS_WINDOWS;
		  }
		  
		}
		
		return sistema;
		
	}
	
	public void desativaPorta() {
		
		if ( portaSerial == null ) {
			portaSerial.close();
		}
		
		portaSerial = null;
		ativada = false;
		
	}
	
	public byte[] enviaCmd( final byte[] CMD, int tamEsperado ) {
		
		return enviaCmd( CMD, portaSel, tamEsperado );
		
	}

	public void serialEvent(SerialPortEvent event) {
		byte[] retorno = null;
		byte[] tmp = null;
		try {
			System.out.println("entrou no evento");
			switch (event.getEventType()) {
				case SerialPortEvent.DATA_AVAILABLE:
				   retorno = new byte[ entrada.available() ];
				   if ( retorno != null ) {
					   entrada.read( retorno );
					   if ( buffer == null ) {
						   buffer = retorno;
					   }
					   else {
						   leuEvento = true;
						   tmp = buffer;
						   buffer = new byte[tmp.length + retorno.length];
						   for ( int i=0; i < buffer.length; i++ ) {						   
							   if ( i < tmp.length ) {
								   buffer[i] = tmp[i];
							   }
							   else { 
								   buffer[i] = retorno[i-tmp.length];
							   }
							   System.out.println("lendo no evento " + buffer[i]);
						   }
					   }					   
				   } else {
					   System.out.println("Available em branco no evento");
				   }
				   break;
			default:
				break;
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public byte[] enviaCmd( final byte[] CMD, final int com, int tamRetorno ) {
		
		byte[] retorno = null;
		byte[] tmp = null;
		int vezes = 0;
		long tempo = 0;
		long tempoAtual = 0;
		buffer = null;
		leuEvento = false;
		
		if ( ativaPorta( com ) ) {
			
		   try {
			   
			   // saida.
			   saida.flush();
			   tempo = System.currentTimeMillis();
			   saida.write( CMD );
			   do {
				   Thread.sleep( TIMEOUT_ACK );
				   tempoAtual = System.currentTimeMillis();
				   System.out.println("laço de tempo " + (tempoAtual-tempo) + " - " +(TIMEOUT_READ) );
			   } 
			   while ((tempoAtual-tempo)<=(TIMEOUT_READ) && 
					(buffer==null || buffer.length <= tamRetorno || (!leuEvento)));
			   			   
/*			   while ( entrada.available() <= 0 && vezes < 100 ) {
				   
				   //System.out.println( "Aguardando retorno..." );
				   Thread.sleep(100);
				   vezes ++;
				   
			   } */
			   
			   //vezes = 0;
			   
/*			   while ( entrada.available() > 0 && vezes < 100 ) {
				   
				  // System.out.println( "Lendo retorno: " + entrada.available() );
				   retorno = new byte[ entrada.available() ];
				   entrada.read( retorno );
				   
				   if ( buffer == null ) {
					   buffer = retorno;
				   }
				   else {
					   tmp = buffer;
					   buffer = new byte[tmp.length + retorno.length];
					   
					   for ( int i=0; i < buffer.length; i++ ) {						   
						   if ( i < tmp.length ) {
							   buffer[i] = tmp[i];
						   }
						   else { 
							   buffer[i] = retorno[i-tmp.length];
						   }
					   }
					   
				   }
				   
				   Thread.sleep(100);
				   
			   } */
			   
		   	} catch (IOException e) {
		   	} catch (InterruptedException e) {
		   	}
		   	
		}
		/*
		System.out.println("tamanho do retorno: "+buffer.length);
		
		for (int i=0; i<buffer.length; i++) {
			System.out.println("Retorno "+i+" = "+buffer[i]);
		}*/
		
		return buffer;
		
	}
	
	public String parseParam( final String param, final int tamanho, final boolean terminador) {
		
		final StringBuffer tmp = new StringBuffer(); 
		
		if ( param != null ) {
			
			if( terminador ) {
				
				if( param.indexOf( String.valueOf( (char)10 ) ) <= -1 ) {
					
					if( tamanho <= param.length() ) {
						tmp.append( param.substring( 0, tamanho-1 ) );
					}
					
					tmp.append( param );
					tmp.append( (char)10 );
					
				}
				
			} else {
				
				if( tamanho < param.length() ) {
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
		
		return strZero( String.valueOf( param ) , tamanho );
		
	}
	
	public String parseParam( final char param ) {
		
		return String.valueOf( param );
		
	}
	
	public String parseParam( final float param, final int tamanho, final int casasdec ) {
		
		return strDecimalToStrCurrency( param, tamanho, casasdec );
		
	}
	
	public String parseParam( final Date param ) {
		
		final SimpleDateFormat sdf = new SimpleDateFormat( "ddMMyy" );
		
		return sdf.format( param ).trim();
		
	}
    
	public String replicate( final String texto, final int quant ) {
		
		final StringBuffer sRetorno = new StringBuffer();
		
		for ( int i=0; i < quant; i++ ) {
			sRetorno.append( texto );
		}
		
		return sRetorno.toString();
		
	}

	public String strDecimalToStrCurrency( final float param, final int tamanho, final int casasdec ) {
		
		final StringBuffer str = new StringBuffer();
		str.append( String.valueOf( param ) );
		
		final char[] strTochar = str.toString().toCharArray();
		final int index = str.indexOf(".");
		final int indexDesc = casasdec - ( ( strTochar.length - 1 ) - index );
		
		str.delete( 0, strTochar.length );
		
		for ( int i=0; i < strTochar.length; i++ ) {
			if( i!= index ) {
				str.append( strTochar[i] );
			}
		}
		
		for ( int i=0; i < indexDesc; i++ ) {
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
	
	////////////////////////
	/////              /////
	/////	COMANDOS   /////
	/////              /////
	////////////////////////
	
	public abstract int aberturaDeCupom();// 0
	
	public abstract int aberturaDeCupom(String cnpj);// 0
	
	public abstract int alteraSimboloMoeda(String simbolo);// 1
	
	public abstract int leituraX();// 5
	
	public abstract int reducaoZ();// 6
	
	public abstract int adicaoDeAliquotaTriburaria(String aliq, char opt);// 7
	
	public abstract int leituraMemoriaFiscal( Date dataIni, Date dataFim, char tipo);// 8
	
	public abstract int leituraMemoriaFiscal( int ini, int fim, char tipo);// 8
	
	public abstract int vendaItem(String codProd, String descProd, String sitTrib, float qtd, float valor, float desconto);// 9
	
	public abstract int cancelaItemAnterior();// 13
	
	public abstract int cancelaCupom();// 14
	
	public abstract int autenticacaoDeDocumento();// 16
	
	public abstract int programaHorarioVerao();// 18
	
	public abstract String getStatus();// 19;
	
	public abstract int relatorioGerencial(String texto);// 20
	
	public abstract int fechamentoRelatorioGerencial();// 21
	
	public abstract int acionaGavetaDinheiro(int time);// 22
	
	public abstract String retornoEstadoGavetaDinheiro();// 23
	
	public abstract int comprovanteNFiscalNVinculado(String opt, float valor, String formaPag);// 25
	
	public abstract String retornoAliquotas();// 26
	
	public abstract String retornoTotalizadoresParciais();// 27
	
	public abstract String retornoSubTotal();// 29
	
	public abstract String retornoNumeroCupom();// 30
	
	public abstract int cancelaItemGenerico(int item);// 31
	
	public abstract int iniciaFechamentoCupom(char opt, float percentual);// 32
	
	public abstract int terminaFechamentoCupom(String menssagem);// 34
	
	public abstract String retornoVariaveis(char var);// 35
	
	public abstract int programaTruncamentoArredondamento(char opt);// 39
	
	public abstract int nomeiaTotalizadorNaoSujeitoICMS(int indice, String desc);// 40
	
	public abstract int vendaItemTresCasas(String codProd, String descProd, String sitTrib, float qtd, float valor, float desconto);// 56
	
	public abstract int programarEspacoEntreLinhas(int espaco);// 60
	
	public abstract int programarEspacoEntreCupons(int espaco);// 61
	
	public abstract int programaUnidadeMedida(String descUnid);// 62 51
	
	public abstract int aumentaDescItem(String descricao);// 62 52
	
	public abstract String retornoEstadoPapel();// 62 54
	
	public abstract String retornoUltimaReducao();// 62 55
	
	public abstract int vendaItemDepartamento(String sitTrib, float valor, float qtd, float desconto, float acrescimo, int departamento, String unidade, String codProd, String descProd);// 63
	
	public abstract int programaCaracterParaAutenticacao(int[] caracteres);// 64
	
	public abstract int nomeiaDepartamento(int index, String descricao);// 65
	
	public abstract int abreComprovanteNFiscalVinculado(String formaPag, float valor, int doc);// 66
	
	public abstract int usaComprovanteNFiscalVinculado(String texto);// 67
	
	public abstract int habilitaCupomAdicional(char opt);// 68
	
	public abstract int leituraXSerial();// 69
	
	public abstract int resetErro();// 70
	
	public abstract int programaFormaPagamento(String descricao);// 71
	
	public abstract int efetuaFormaPagamento(int indice, float valor, String descForma);// 72
	
	public abstract int estornoFormaPagamento(String descOrigem, String descDestino, float valor);// 74
	
}
