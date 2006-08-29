package org.freedom.drivers; 

import java.math.BigDecimal;

import org.freedom.ecf.com.Serial;
import org.freedom.ecf.driver.AbstractECFDriver;
import org.freedom.ecf.driver.ECFBematech;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.telas.AplicativoPDV;

public class ECFDriver {

	private JBemaFI32 bema = null;

	private boolean bModoDemo = AplicativoPDV.bModoDemo;

	private boolean dll;

	private AbstractECFDriver ecf = null;

	private String sUserID = AplicativoPDV.strUsuario;

	public static String sMensErroLog = "";

	public ECFDriver( final boolean arg0 ) {

		this.dll = arg0;

		if ( this.dll ) {
			bema = new JBemaFI32();
		}
		else {
			ecf = new ECFBematech( Serial.COM1 );
		}

	}
	
	public int numeroReducoes() {
		
		int retorno = -1;
		
		if ( dll ) {
			retorno = bema.numeroReducoes( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {
			
			retorno = Integer.parseInt( ecf.retornoVariaveis( AbstractECFDriver.V_REDUCOES ) );
						
		}
		
		return retorno;
		
	}
	
	public int numeroCancelados() {
		
		int retorno = -1;
		
		if ( dll ) {
			retorno = bema.numeroCancelados( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {
			
			retorno = Integer.parseInt( ecf.retornoVariaveis( AbstractECFDriver.V_CUPONS_CANC ) );
			
		}
		
		return retorno;
		
	}
	
	public double cancelamentos() {
		
		double retorno = -1;
		
		if ( dll ) {
			retorno = bema.cancelamentos( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {
			
			retorno = Double.parseDouble( ecf.retornoVariaveis( AbstractECFDriver.V_CANCELAMENTOS ) );
			
		}
		
		return retorno;
		
	}
	
	public double descontos() {
		
		double retorno = -1;
		
		if ( dll ) {
			retorno = bema.descontos( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {
			
			retorno = Double.parseDouble( ecf.retornoVariaveis( AbstractECFDriver.V_CANCELAMENTOS ) );
			
		}
		
		return retorno;
		
	}
	
	public String retornoTotalizadores() {
		
		String retorno = "";
		
		if ( dll ) {
			retorno = bema.retornaTotalizadores( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {
			
			retorno = ecf.retornoTotalizadoresParciais();
			
		}
		
		return retorno;
		
	}
	
	public boolean abreComprovanteNaoFiscalVinculado( final String sFormaPagto, final BigDecimal bdValor, final int iNumCupom ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.abreComprovanteNaoFiscalVinculado( sUserID, sFormaPagto, bdValor, iNumCupom, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			bRetorno = trataRetornoFuncao( ecf.abreComprovanteNFiscalVinculado( sFormaPagto, bdValor.floatValue(), iNumCupom ) );

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_ABRE_N_FISCAL_VIN, "ERRO NO COMPROVANTE NÃO FISCAL VINCULADO: " + sFormaPagto + "|" + bdValor + "|" + iNumCupom + "|" + sMensErroLog );
			}

		}

		return bRetorno;

	}
	
	public boolean abreCupom() {
		
		return abreCupom( "" );
		
	}

	public boolean abreCupom( final String sCnpj ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.abreCupom( sCnpj, sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			if ( "".equals( sCnpj ) || sCnpj == null ) {
				int i = 1;
				while ( !trataRetornoFuncao( ecf.aberturaDeCupom() ) ) {
					Logger.gravaLogTxt( "", sUserID, Logger.LGEP_ABERT_CUPOM, sMensErroLog );
					Funcoes.espera( 2 );
					if ( i >= 5 ) {
						bRetorno = false;
						break;
					}
					i++;
				}
			}
			else {
				int i = 1;
				while ( !trataRetornoFuncao( ecf.aberturaDeCupom( sCnpj ) ) ) {
					Logger.gravaLogTxt( "", sUserID, Logger.LGEP_ABERT_CUPOM, sMensErroLog );
					Funcoes.espera( 2 );
					if ( i >= 5 ) {
						bRetorno = false;
						break;
					}
					i++;
				}
			}

		}

		return bRetorno;

	}

	public boolean abreGaveta() {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.abreGaveta( sUserID, bModoDemo );
		}/*
			 * else { if ( ! bModoDemo ) {
			 * 
			 * bRetorno = trataRetornoFuncao( ecf.acionaGavetaDinheiro( 1 ) );
			 * 
			 * if ( ! bRetorno ) { Logger.gravaLogTxt( "", sUserID, Logger.LGEP_ABERT_GAVETA, sMensErroLog ); }
			 *  } }
			 */

		return bRetorno;

	}

	public boolean cancelaCupom() {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.cancelaCupom( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			bRetorno = trataRetornoFuncao( ecf.cancelaCupom() );

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_CANC_CUPOM, sMensErroLog );
			}

		}

		return bRetorno;

	}
	
	public boolean cancelaItemAnterior() {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.cancelaItemAnterior( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			Logger.gravaLogTxt( "", sUserID, Logger.LGIF_CANC_ITEM, "TENTATIVA DE CANCELAMENTO DE ITEM ANTERIOR PELO OPERADOR " + sUserID );

			bRetorno = trataRetornoFuncao( ecf.cancelaItemAnterior() );

			if ( !sMensErroLog.trim().equals( "" ) ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_CANC_ITEM, sMensErroLog );
			}
		}

		return bRetorno;

	}

	public boolean cancelaItemGenerico( final int item ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.cancelaItemGenerico( sUserID, item, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			Logger.gravaLogTxt( "", sUserID, Logger.LGIF_CANC_ITEM, "CANCELAMENTO DE ITEM [" + item + "] PELO " + sUserID );

			bRetorno = trataRetornoFuncao( ecf.cancelaItemGenerico( item ) );

			if ( !sMensErroLog.trim().equals( "" ) ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_CANC_ITEM, sMensErroLog );
			}
		}

		return bRetorno;

	}
	
	public boolean fechaComprovanteNaoFiscalVinculado() {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.fechaComprovanteNaoFiscalVinculado( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			bRetorno = trataRetornoFuncao( ecf.fechamentoRelatorioGerencial() );

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_FECHA_N_FISCAL_VIN, "ERRO NO COMPROVANTE NÃO FISCAL VINCULADO: " + sMensErroLog );
			}

		}

		return bRetorno;

	}

	public boolean fechaCupomFiscal( final String sFormaPagto, final String sAcreDesc, final String sTipoAcreDesc, final float fVlrAcreDesc, final float fVlrPago, final String sMensagem ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.fechaCupomFiscal( sUserID, sFormaPagto, sAcreDesc, sTipoAcreDesc, fVlrAcreDesc, fVlrPago, sMensagem, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			if ( "".equals( sAcreDesc ) || "D".equals( sAcreDesc ) ) {
				if ( sTipoAcreDesc.trim().equals( "$" ) ) {
					bRetorno = trataRetornoFuncao( ecf.iniciaFechamentoCupom( AbstractECFDriver.DESCONTO_VALOR, fVlrAcreDesc ) );
				}
				else {
					bRetorno = trataRetornoFuncao( ecf.iniciaFechamentoCupom( AbstractECFDriver.DESCONTO_PERC, fVlrAcreDesc ) );
				}
			}
			else {
				if ( sTipoAcreDesc.trim().equals( "$" ) ) {
					bRetorno = trataRetornoFuncao( ecf.iniciaFechamentoCupom( AbstractECFDriver.ACRECIMO_VALOR, fVlrAcreDesc ) );
				}
				else {
					bRetorno = trataRetornoFuncao( ecf.iniciaFechamentoCupom( AbstractECFDriver.ACRECIMO_PERC, fVlrAcreDesc ) );
				}
			}

			if ( bRetorno ) {
				String formaPag = Funcoes.adicEspacosDireita( Funcoes.tiraAcentos( sFormaPagto ), 16 );
				String sCodFormaPag = Funcoes.strZero( ecf.programaFormaPagamento( formaPag ), 2 );

				bRetorno = trataRetornoFuncao( ecf.efetuaFormaPagamento( sCodFormaPag, fVlrPago, "" ) );
			}

			if ( bRetorno ) {
				ecf.terminaFechamentoCupom( sMensagem );
			}

			if ( !sMensErroLog.trim().equals( "" ) ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_FECHA_CUPOM, "Forma Pagto.: " + sFormaPagto + " - Valor Pago:" + fVlrPago + " - Msg. final de cupom: " + sMensagem + " - Erro: " + sMensErroLog );
			}

		}

		return bRetorno;

	}

	public boolean fechaRelatorioGerencial() {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.fechaRelatorioGerencial( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			bRetorno = trataRetornoFuncao( ecf.fechamentoRelatorioGerencial() );

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_FECHA_REL_REGENCIAL, "ERRO NO COMPROVANTE: " + sMensErroLog );
			}

		}

		return bRetorno;

	}

	public boolean finalizaModoTEF( String sUserID, boolean bModoDemo ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.finalizaModoTEF( sUserID, bModoDemo );
		}/*
			 * else { if ( ! bModoDemo ) {
			 * 
			 * bRetorno = trataRetornoFuncao( ecf.acionaGavetaDinheiro( 1 ) );
			 * 
			 * if ( ! bRetorno ) { Logger.gravaLogTxt( "", sUserID, Logger.LGEP_ABERT_GAVETA, sMensErroLog ); }
			 *  } }
			 */

		return bRetorno;

	}

	public boolean iniciaModoTEF( String sUserID, boolean bModoDemo ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.iniciaModoTEF( sUserID, bModoDemo );
		}/*
			 * else { if ( ! bModoDemo ) {
			 * 
			 * bRetorno = trataRetornoFuncao( ecf.acionaGavetaDinheiro( 1 ) );
			 * 
			 * if ( ! bRetorno ) { Logger.gravaLogTxt( "", sUserID, Logger.LGEP_ABERT_GAVETA, sMensErroLog ); }
			 *  } }
			 */

		return bRetorno;

	}

	public boolean leituraX() {

		return leituraX( false );

	}

	public boolean leituraX( final boolean saidaSerial ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.leituraX( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			if ( saidaSerial ) {
				bRetorno = trataRetornoFuncao( ecf.leituraXSerial() );
			}
			else {
				bRetorno = trataRetornoFuncao( ecf.leituraX() );
			}

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_LEITRA_X, sMensErroLog );
			}

		}

		return bRetorno;

	}

	public String leStatus() {

		String sMensagem = "";
		String sSep = "";

		if ( dll ) {
			sMensagem = bema.leStatus( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {
			
			String tmp = ecf.getStatus();
			
			if( tmp != null && tmp.length() > 0 ) {
				
				String[] status = tmp.split(","); 
				
				int st1 = Integer.parseInt( status[ 1 ] );
				int st2 = Integer.parseInt( status[ 2 ] );
				
				if ( st1 >= 128 ) {
					sMensagem += "Fim de papel.";
				}
				else if ( st1 >= 64 ) {
					sMensagem += "Pouco papel.";
				}
				else if ( st1 >= 32 ) {
					sMensagem += "Erro no relógio.";
				}
				else if ( st1 >= 16 ) {
					sMensagem += "Impressora em erro.";
				}
				else if ( st1 >= 8 ) {
					sMensagem += "Primeiro dado de CMD não foi ESC( 1Bh).";
				}
				else if ( st1 >= 4 ) {
					sMensagem += "Comando inexistente.";
				}
				else if ( st1 >= 2 ) {
					sMensagem += "Cupom fiscal aberto.";
				}
				else if ( st1 >= 1 ) {
					sMensagem += "Número de parâmetro de CMD inválido.";
				}
				
				if ( sMensagem.length() > 0 ) {
					sSep = "\n";					
				}
				
				if ( st2 >= 128 ) {
					sMensagem += sSep + "Tipo de parâmetro de CMD inválido.";
				}
				else if ( st2 >= 64 ) {
					sMensagem += sSep + "Memória fiscal lotada.";
				}
				else if ( st2 >= 32 ) {
					sMensagem += sSep + "Erro na memória RAM CMOS não volátil.";
				}
				else if ( st2 >= 16 ) {
					sMensagem += sSep + "Alíquota não programada.";
				}
				else if ( st2 >= 8 ) {
					sMensagem += sSep + "Capacidade de alíquotas esgotada.";
				}
				else if ( st2 >= 4 ) {
					sMensagem += sSep + "Cancelamento não permitido.";
				}
				else if ( st2 >= 2 ) {
					sMensagem += sSep + "CNPJ/IE do proprietário não programados.";
				}
				else if ( st2 >= 1 ) {
					sMensagem += sSep + "Comando não executado.";
				}
			}
			
		}

		return sMensagem;

	}

	public int numeroCupom() {

		int iRetorno = 0;

		if ( dll ) {
			iRetorno = bema.numeroCupom( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			String sNumero = ecf.retornoNumeroCupom();

			if ( sNumero == null ) {
				sNumero = "";
			}
			else {
				iRetorno = Integer.parseInt( sNumero );
			}
			/*if ( ! trataRetornoFuncao( iRetorno ) ) {
				if ( sNumero.equals( "" ) ) {
					Funcoes.mensagemErro( null, "Número do cupom nulo!\n" + "Entre em contato com departamento técnico!!!" );
					iRetorno = 999999;
				}
				else {
					sNumero = sNumero.trim();
					if ( sNumero.equals( "" ) )
						iRetorno = 0;
				}
			}
			else {
				if ( Funcoes.ehInteiro( sNumero ) ) {
					iRetorno = Integer.parseInt( sNumero );
				}
				else {
					Funcoes.mensagemErro( null, "Número do cupom não é inteiro!\n" + "Entre em contato com departamento técnico!!!" );
					iRetorno = 888888;
				}
			}*/

		}

		return iRetorno;

	}

	public boolean programaAliquotas( final String sVal, final int iModoICMISS ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.programaAliquotas( sUserID, sVal, iModoICMISS, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			char opt = iModoICMISS == 0 ? AbstractECFDriver.ICMS : AbstractECFDriver.ISS;
			bRetorno = trataRetornoFuncao( ecf.adicaoDeAliquotaTriburaria( sVal, opt ) );

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_INCL_ALIQ, sMensErroLog );
			}

		}

		return bRetorno;

	}

	public boolean programaMoeda( final String sSimb, final String sSing, final String sPlur ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.programaMoeda( sUserID, sSing, sPlur, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {
			
			String simbolo = Funcoes.tiraAcentos( sSimb );
			final String singular = Funcoes.tiraAcentos( sSing ); 
			final String plural = Funcoes.tiraAcentos( sPlur );
			
			if ( simbolo.indexOf( '$' ) > -1 ) {
				simbolo = " " + simbolo.replace( '$', ' ' );
			}
			
			System.out.println( "\nAltera simbolo moeda:\n" );
			
			bRetorno = trataRetornoFuncao( ecf.alteraSimboloMoeda( simbolo ) );			

			System.out.println( "\nDepois simbolo moeda:\n" );
			
			if ( bRetorno ) {
				
				System.out.println( "Programa moeda no singular:" );
				
				bRetorno = trataRetornoFuncao( ecf.programaMoedaSingular( singular ) );
				
				if ( bRetorno ) {
					
					System.out.println( "Programa moeda no plural:" );
					
					bRetorno = trataRetornoFuncao( ecf.programaMoedaPlural( plural ) );
					
					if ( ! bRetorno ) {
						Logger.gravaLogTxt( "", sUserID, Logger.LGEP_PROG_MOEDA, "Moeda Plural - " + sMensErroLog );
					}
					
				}
				else {
					Logger.gravaLogTxt( "", sUserID, Logger.LGEP_PROG_MOEDA, "Moeda Singular - " + sMensErroLog );
				}
				
			}
			else {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_ALT_SIMB_MOEDA, "Simbolo Moeda Corrente - " + sMensErroLog );
			}
			
		}

		return bRetorno;

	}

	public boolean reducaoZ() {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.reducaoZ( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			bRetorno = trataRetornoFuncao( ecf.reducaoZ() );

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_REDUCAO_Z, sMensErroLog );
			}

		}

		return bRetorno;

	}

	public boolean relatorioGerencialTef( final String comprovante ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.relatorioGerencialTef( sUserID, comprovante, bModoDemo );
		}

		return bRetorno;

	}

	public String retornoAliquotas() {

		String sRetorno = "";

		if ( dll ) {
			sRetorno = bema.retornaAliquotas( sUserID, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {

			try {
				
				String aliquotas = ecf.retornoAliquotas();
				
				final int tamanho = Integer.parseInt( aliquotas.substring( 0, 2 ) );
				
				sRetorno = aliquotas.substring( 2, ( tamanho * 4 ) + 2 );

				
			} catch ( Exception e ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_RET_ALIQ, sMensErroLog );
			}

		}

		return sRetorno;

	}

	public boolean sangria( final BigDecimal valor ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.sangria( sUserID, valor, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null && valor.floatValue() > 0 ) {

			bRetorno = trataRetornoFuncao( ecf.comprovanteNFiscalNVinculado( "SA", valor.floatValue(), "" ) );

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_FAZ_SANGRIA, "ERRO NA SANGRIA-VALOR: " + valor.floatValue() + "|" + sMensErroLog );
			}

		}

		return bRetorno;

	}

	public boolean suprimento( final BigDecimal valor ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.sangria( sUserID, valor, bModoDemo );
		}
		else if ( !bModoDemo && ecf != null && valor.floatValue() > 0 ) {

			bRetorno = trataRetornoFuncao( ecf.comprovanteNFiscalNVinculado( "SU", valor.floatValue(), "" ) );

			if ( !bRetorno ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_FAZ_SUPRIMENTO, "ERRO NO SUPRIMENTO-VALOR: " + valor.floatValue() + "-" + sMensErroLog );
			}

		}

		return bRetorno;

	}

	public boolean trataRetornoFuncao( final int iRetorno ) {

		boolean bRetorno = true;

		String sMensagem = "";

		sMensErroLog = "";

		switch ( iRetorno ) {

			case 0 :
				sMensagem = "Erro de comunicação física";
				break;
			case 1 :
				sMensagem = "";
				break;
			case -2 :
				sMensagem = "Parâmetro inválido na função.";
				break;
			case -3 :
				sMensagem = "Aliquota não programada";
				break;
			case -4 :
				sMensagem = "O arquivo de inicialização BEMAFI32.INI não foi encontrado no diretório de sistema do Windows";
				break;
			case -5 :
				sMensagem = "Erro ao abrir a porta de comunicação";
				break;
			case -8 :
				sMensagem = "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT";
				break;
			case -27 :
				sMensagem = "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)";
				break;
			case -30 :
				sMensagem = "Função não compatível com a impressora YANCO";
				break;
			case -31 :
				sMensagem = "Forma de pagamento não finalizada";
				break;
			default :
				sMensagem = "Retorno indefinido: " + iRetorno;
				break;

		}

		if ( !"".equals( sMensagem.trim() ) ) {

			bRetorno = false;

			sMensErroLog = sMensagem + " - STATUS: " + iRetorno;

			Funcoes.mensagemErro( null, sMensagem );

		}

		return bRetorno;
	}
	
	public boolean usaComprovanteNaoFiscalVinculadoTef( final String sTexto ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.usaComprovanteNaoFiscalVinculadoTef( sUserID, sTexto, bModoDemo );
		}/*
			 * else { if ( ! bModoDemo ) {
			 * 
			 * bRetorno = trataRetornoFuncao( ecf.acionaGavetaDinheiro( 1 ) );
			 * 
			 * if ( ! bRetorno ) { Logger.gravaLogTxt( "", sUserID, Logger.LGEP_ABERT_GAVETA, sMensErroLog ); }
			 *  } }
			 */

		return bRetorno;

	}

	public boolean vendaItem( final int iCodprod, final String sDescprod, final String sTributo, final BigDecimal bdQuant, final BigDecimal bdValor, final BigDecimal bdValDesc ) {

		boolean bRetorno = false;

		if ( dll ) {
			bRetorno = bema.vendaItem( sUserID, iCodprod, sDescprod, sTributo, bdQuant.doubleValue(), bdValor.doubleValue(), bdValDesc.doubleValue(), bModoDemo );
		}
		else if ( !bModoDemo && ecf != null ) {
			
			String sCodprod = Funcoes.strZero( String.valueOf( iCodprod ), 29 );
			String sDescricao = Funcoes.tiraAcentos( Funcoes.adicionaEspacos( sDescprod, 29 ) );
			int index = 1;

			while ( ! trataRetornoFuncao( ecf.vendaItem( sCodprod, sDescricao, sTributo, bdQuant.floatValue(), bdValor.floatValue(), bdValDesc.floatValue() ) ) ) {
				
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_IMPRES_ITEM, "Codigo: " + sCodprod + "-Descrição: " + sDescprod + "-Qtd.: " + bdQuant.floatValue() + "-Valor: " + bdValor.floatValue() + "-Desconto: " + bdValDesc.floatValue() + "-Erro: " + sMensErroLog );
				
				Funcoes.espera( 2 );
				
				if ( index++ >= 5 ) {
					bRetorno = false;
					break;
				}
				
			}

			if ( !sMensErroLog.trim().equals( "" ) ) {
				Logger.gravaLogTxt( "", sUserID, Logger.LGEP_CANC_ITEM, sMensErroLog );
			}
		}

		return bRetorno;

	}

	public boolean verificaCupomAberto() {
		
		boolean bRetorno = false;
		
		if ( dll ) {
			bRetorno = bema.verificaCupomAberto( sUserID, bModoDemo );
		}
		else if ( ! bModoDemo ) {
			
			String[] tmp = ecf.getStatus().split(",");
			
			if ( tmp != null && tmp.length > 1 ) {
			
				bRetorno = "2".equals( tmp[ 1 ] );
			
			}
			
		}
		
		return bRetorno;
		
	}
	
	public static String transStatus( char cStatus ) {

		String sRet = "";
		switch ( cStatus ) {
			case 'A' :
				sRet = "Aberto";
				break;
			case 'U' :
				sRet = "Suprimento";
				break;
			case 'S' :
				sRet = "Sangria";
				break;
			case 'V' :
				sRet = "Venda";
				break;
			case 'Z' :
				sRet = "Fechado (com LeituraZ)";
				break;
			default :
				sRet = "Não identificado";
		}
		return sRet;
	}

}
