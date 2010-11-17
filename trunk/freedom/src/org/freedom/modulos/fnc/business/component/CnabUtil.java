package org.freedom.modulos.fnc.business.component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.business.component.BancodoBrasil;
import org.freedom.library.business.exceptions.ExceptionCnab;
import org.freedom.library.functions.Funcoes;
import org.freedom.modulos.fnc.library.business.compoent.FbnUtil;

public class CnabUtil extends FbnUtil {

	public abstract class Reg {

		public abstract void parseLine( String line ) throws ExceptionCnab;

		public abstract String getLine( String padraocnab ) throws ExceptionCnab;

		public String format( Object obj, ETipo tipo, int tam, int dec ) {

			String retorno = null;
			String str = null;

			if ( obj == null ) {
				str = "";
			}
			else {
				str = obj.toString();
			}

			if ( tipo == ETipo.$9 ) {

				if ( dec > 0 ) {

					retorno = Funcoes.transValor( str, tam, dec, true );
				}
				else {

					retorno = StringFunctions.strZero( str, tam );
				}
			}
			else {
				retorno = Funcoes.adicionaEspacos( str, tam );
			}

			return retorno;
		}

		public static final String CNAB_240 = "240";

		public static final String CNAB_400 = "400";

		protected final String LITERAL_REM = "REMESSA";

		protected final String LITERAL_SERV = "COBRANCA";

		protected final String LITERAL_SISTEMA = "MX";

		protected final String DATA_06 = "DDMMAA";

		protected final String DATA_08 = "DDMMAAAA";

	}

	public class RegHeader extends Reg {

		private String codBanco;

		private String loteServico;

		private int registroHeader;

		private int tipoInscEmp;

		private String razEmp;

		private String cpfCnpjEmp;

		private String codConvBanco;

		private String agencia;

		private String digAgencia;

		private String conta;

		private String digConta;

		private String digAgConta;

		private String nomeBanco;

		private int tipoOperacao;

		private Date dataGeracao;

		private int horaGeracao;

		private Integer sequenciaArq;

		private String versaoLayout;

		private String densidadeArq;

		private String usoBanco;

		private String usoEmp;

		// private String COBRANÇA S/PAPEL
		private String usoVans;

		private String tipoServico;

		private String ocorrencias;

		public RegHeader() {

			setLoteServico( "0000" );
			setRegistroHeader( 0 );
			setTipoOperacao( 1 );
			setVersaoLayout( "030" );
		}

		public RegHeader( String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public String getAgencia() {

			return agencia;
		}

		public void setAgencia( final String agencia ) {

			this.agencia = agencia;
		}

		public String getCodBanco() {

			return codBanco;
		}

		public void setCodBanco( final String codBanco ) {

			this.codBanco = codBanco;
		}

		/**
		 * Indentifica a empresa no banco para determinados tipos de serviços.<br>
		 * Observar as regras de preenchimento abaixo no que se refere ao header de serviço/lote:<br>
		 * "9999999994444CCVVV " / 20 bytes / , onde:<br>
		 * 999999999 - Código do convênio.<br>
		 * 4444 - Código do produto.<br>
		 * CC - Carteira de cobrança.<br>
		 * VVV - Variação da carteira de cobrança.<br>
		 */
		public String getCodConvBanco() {

			return codConvBanco;
		}

		public void setCodConvBanco( final String codConvenio ) {

			this.codConvBanco = codConvenio;
		}

		public String getConta() {

			return conta;
		}

		public void setConta( final String conta ) {

			this.conta = conta;
		}

		/**
		 * Inscrição da empresa. Conforme o tipo da inscrição.<br>
		 * 
		 * @see org.freedom.modulos.fnc.business.component.CnabUtil.Reg1#setTipoInscEmp(int tipoInscEmp )
		 */
		public String getCpfCnpjEmp() {

			return cpfCnpjEmp;
		}

		public void setCpfCnpjEmp( final String cpfCnpjEmp ) {

			this.cpfCnpjEmp = cpfCnpjEmp;
		}

		public Date getDataGeracao() {

			return dataGeracao;
		}

		public void setDataGeracao( final Date dataGeracao ) {

			this.dataGeracao = dataGeracao;
		}

		public String getDensidadeArq() {

			return densidadeArq;
		}

		public void setDensidadeArq( final String densidadeArq ) {

			this.densidadeArq = densidadeArq;
		}

		public String getDigAgConta() {

			return digAgConta;
		}

		public void setDigAgConta( final String digAgeConta ) {

			this.digAgConta = digAgeConta;
		}

		public String getDigAgencia() {

			return digAgencia;
		}

		public void setDigAgencia( final String digAgencia ) {

			this.digAgencia = digAgencia;
		}

		public String getDigConta() {

			return digConta;
		}

		public void setDigConta( final String digConta ) {

			this.digConta = digConta;
		}

		public int getHoraGeracao() {

			return horaGeracao;
		}

		public void setHoraGeracao( final int horaGeracao ) {

			this.horaGeracao = horaGeracao;
		}

		public String getLoteServico() {

			return loteServico;
		}

		private void setLoteServico( final String loteServico ) {

			this.loteServico = loteServico;
		}

		public String getNomeBanco() {

			return nomeBanco;
		}

		public void setNomeBanco( final String nomeBanco ) {

			this.nomeBanco = nomeBanco;
		}

		public String getOcorrencias() {

			return ocorrencias;
		}

		public void setOcorrencias( final String ocorrencias ) {

			this.ocorrencias = ocorrencias;
		}

		public String getRazEmp() {

			return razEmp;
		}

		public void setRazEmp( final String razaoEmp ) {

			this.razEmp = razaoEmp;
		}

		public int getRegistroHeader() {

			return registroHeader;
		}

		private void setRegistroHeader( final int registroHeader ) {

			this.registroHeader = registroHeader;
		}

		public Integer getSequenciaArq() {

			return sequenciaArq;
		}

		public void setSequenciaArq( final Integer sequenciaArq ) {

			this.sequenciaArq = sequenciaArq;
		}

		/**
		 * Indica o tipo de inscrição da empresa.<br>
		 * 1 - CPF<br>
		 * 2 - CNPJ<br>
		 */
		public int getTipoInscEmp() {

			return tipoInscEmp;
		}

		public void setTipoInscEmp( final int tipoInscEmp ) {

			this.tipoInscEmp = tipoInscEmp;
		}

		public String getTipoServico() {

			return tipoServico;
		}

		public void setTipoServico( final String tipoServico ) {

			this.tipoServico = tipoServico;
		}

		public String getUsoBanco() {

			return usoBanco;
		}

		public void setUsoBanco( final String usoBanco ) {

			this.usoBanco = usoBanco;
		}

		public String getUsoEmp() {

			return usoEmp;
		}

		public void setUsoEmp( final String usoEmp ) {

			this.usoEmp = usoEmp;
		}

		public String getUsoVans() {

			return usoVans;
		}

		public void setUsoVans( final String usoVans ) {

			this.usoVans = usoVans;
		}

		/**
		 * Indica o tipo de operação.<br>
		 * 1 - Remessa.<br>
		 * 2 - Retorno.<br>
		 */
		public int getTipoOperacao() {

			return tipoOperacao;
		}

		private void setTipoOperacao( final int tipoOperacao ) {

			this.tipoOperacao = tipoOperacao;
		}

		public String getVersaoLayout() {

			return versaoLayout;
		}

		private void setVersaoLayout( final String versaoLayout ) {

			this.versaoLayout = versaoLayout;
		}

		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				if ( padraocnab.equals( CNAB_240 ) ) {

					line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
					line.append( getLoteServico() );
					line.append( getRegistroHeader() );
					line.append( StringFunctions.replicate( " ", 9 ) );
					line.append( format( getTipoInscEmp(), ETipo.$9, 1, 0 ) );
					line.append( format( getCpfCnpjEmp(), ETipo.$9, 14, 0 ) );
					line.append( format( getCodConvBanco(), ETipo.X, 20, 0 ) );
					line.append( format( getAgencia(), ETipo.$9, 5, 0 ) );
					line.append( format( getDigAgencia(), ETipo.X, 1, 0 ) );
					line.append( format( getConta(), ETipo.$9, 12, 0 ) );
					line.append( format( getDigConta(), ETipo.X, 1, 0 ) );
					line.append( format( getDigAgConta(), ETipo.X, 1, 0 ) );
					line.append( format( getRazEmp(), ETipo.X, 30, 0 ) );
					line.append( format( getNomeBanco(), ETipo.X, 30, 0 ) );
					line.append( StringFunctions.replicate( " ", 10 ) );
					line.append( format( getTipoOperacao(), ETipo.$9, 1, 0 ) );
					line.append( dateToString( getDataGeracao(), null ) );
					line.append( format( getHoraGeracao(), ETipo.$9, 6, 0 ) );
					line.append( format( getSequenciaArq(), ETipo.$9, 6, 0 ) );
					line.append( getVersaoLayout() );
					line.append( format( getDensidadeArq(), ETipo.$9, 5, 0 ) );
					line.append( format( getUsoBanco(), ETipo.X, 20, 0 ) );
					line.append( format( getUsoEmp(), ETipo.X, 20, 0 ) );
					line.append( StringFunctions.replicate( " ", 11 ) );
					line.append( "CSP" );// indentifica cobrança sem papel.
					line.append( format( getUsoVans(), ETipo.$9, 3, 0 ) );
					line.append( format( getTipoServico(), ETipo.X, 2, 0 ) );
					line.append( format( getOcorrencias(), ETipo.X, 10, 0 ) );
					line.append( (char) 13 );
					line.append( (char) 10 );
				}
				else if ( padraocnab.equals( CNAB_400 ) ) {

					line.append( getRegistroHeader() ); // Posição 001 a 001 - Identificação do Registro
					line.append( "1" ); // Posição 002 a 002 - Identificação do Arquvo de remessa
					line.append( format( LITERAL_REM, ETipo.X, 7, 0 ) ); // Posição 003 a 009 - Literação de remassa
					line.append( "01" ); // Posição 010 a 011 - Código do serviço (01)
					line.append( format( LITERAL_SERV, ETipo.X, 15, 0 ) );// Posição 012 a 026 - Literal Serviço
					
					// Informação personalizada para o banco do brasil
					if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {
						
						line.append( StringFunctions.strZero( getAgencia(), 4 ) );// Posição 027 a 030 - Prefixo da agencia
						line.append( getDigAgencia() );// Posição 031 a 031 - Digito verificador da agencia
						line.append( StringFunctions.strZero(getConta(),8 ));// Posição 032 a 039 - Numero da conta
						line.append( getDigConta());// Posição 040 a 040 - Digito verificador da conta
						
						if (getCodConvBanco().length()<7) {
						
							line.append( StringFunctions.strZero( getCodConvBanco(), 6 ) );// Posição 041 a 046 - Código da Empresa
						}
						else {
							line.append( StringFunctions.strZero( "", 6 ) );// Posição 041 a 046 - Preencher com zeros
						}
						
						
					}
					else {
						line.append( StringFunctions.strZero( getCodConvBanco(), 20 ) );// Posição 027 a 046 - Código da Empresa
					}
					
					line.append( format( getRazEmp().toUpperCase(), ETipo.X, 30, 0 ) );// Posição 047 a 076 - Nome da Empresa
					line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );// Posição 077 a 079 - Número do banco na câmara de compensação
					line.append( format( getNomeBanco().toUpperCase(), ETipo.X, 15, 0 ) );// Posição 080 a 094 - Nome do banco por extenso
					line.append( dateToString( getDataGeracao(), DATA_06 ) );// Posição 095 a 100 - Data da gravação do arquivo
					
					if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {
						line.append( StringFunctions.strZero( getSequenciaArq()+"", 7 ) );// Posição 101 a 107 - Sequencial da remessa
						
						if (getCodConvBanco().length()<7) {
							line.append( StringFunctions.replicate( " ", 287 ) ); // Posição 108 a 394 - Espaço em branco
						}
						else {
							line.append( StringFunctions.replicate( " ", 22 ) ); // Posição 108 a 130 - Espaço em branco
							line.append( format( getCodConvBanco(), ETipo.X, 7, 0 ) ); // Posição 130 a 136 - Codigo do convenio lider 
							line.append( StringFunctions.replicate( " ", 258 ) ); // Posição 136 a 394 - Espaço em branco
							//xxx
							
						}
					}
					else {
						line.append( StringFunctions.replicate( " ", 8 ) );// Posição 101 a 108 - Espaço em branc
						line.append( LITERAL_SISTEMA );// Posição 109 a 110 - Literal do Sistema (MX - Micro a micro)
						line.append( format( getSequenciaArq(), ETipo.$9, 7, 0 ) ); // Posição 111 a 117 - Nro sequencial da remessa
						line.append( StringFunctions.replicate( " ", 277 ) ); // Posição 118 a 394 - Espaço em branco
					}
					

					
					line.append( format( 1, ETipo.$9, 6, 0 ) ); // Sequencial do registro de um em um
					line.append( (char) 13 );
					line.append( (char) 10 );

				}

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro Header.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		@ Override
		public void parseLine( final String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "CNAB registro Header.\nLinha nula." );
				}
				else {
					if ( line.length() < 400 ) { // Padrão CNAB 240
						setCodBanco( line.substring( 0, 3 ) );
						setLoteServico( line.substring( 3, 7 ) );
						setRegistroHeader( line.substring( 7, 8 ).trim().length() > 0 ? Integer.parseInt( line.substring( 7, 8 ).trim() ) : 0 );
						setTipoInscEmp( line.substring( 17, 18 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 18 ).trim() ) : 0 );
						setCpfCnpjEmp( line.substring( 18, 32 ) );
						setCodConvBanco( line.substring( 32, 52 ) );
						setAgencia( line.substring( 52, 57 ) );
						setDigAgencia( line.substring( 57, 58 ) );
						setConta( line.substring( 58, 70 ) );
						setDigConta( line.substring( 70, 71 ) );
						setDigAgConta( line.substring( 71, 72 ) );
						setRazEmp( line.substring( 72, 102 ) );
						setNomeBanco( line.substring( 102, 132 ) );
						setTipoOperacao( line.substring( 142, 143 ).trim().length() > 0 ? Integer.parseInt( line.substring( 142, 143 ).trim() ) : 0 );
						setDataGeracao( stringDDMMAAAAToDate( line.substring( 143, 151 ).trim() ) );
						setHoraGeracao( line.substring( 151, 157 ).trim().length() > 0 ? Integer.parseInt( line.substring( 151, 157 ).trim() ) : 0 );
						setSequenciaArq( line.substring( 157, 163 ).trim().length() > 0 ? Integer.parseInt( line.substring( 157, 163 ).trim() ) : 0 );
						setVersaoLayout( line.substring( 163, 166 ) );
						setDensidadeArq( line.substring( 166, 171 ) );
						setUsoBanco( line.substring( 171, 191 ) );
						setUsoEmp( line.substring( 191, 211 ) );
						setUsoVans( line.substring( 225, 228 ) );
						setTipoServico( line.substring( 228, 230 ) );
						setOcorrencias( line.substring( 230 ) );
					}
					else { // Padrão CNAB 400
						System.out.println( "LENDO CNAB 400..." );

						// Identificador de retorno
						if ( "2".equals( line.substring( 1, 2 ) ) ) {

							setRegistroHeader( new Integer( line.substring( 0, 1 ) ).intValue() );
							setTipoOperacao( 2 );
							setCodConvBanco( line.substring( 26, 46 ) );
							setRazEmp( line.substring( 46, 76 ) );
							setCodBanco( line.substring( 76, 79 ) );
							setNomeBanco( line.substring( 79, 94 ) );
							setDataGeracao( stringDDMMAAToDate( line.substring( 94, 100 ).trim() ) );
							setSequenciaArq( Integer.parseInt( line.substring( 394, 400 ) ) );

						}
						else {
							Funcoes.mensagemInforma( null, "Arquivo informado não representa um arquivo de retorno válido no padrão CNAB 400!" );
						}

					}

				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro Header.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Reg1 extends Reg {

		private String codBanco;

		private int loteServico;

		private int registroHeader;

		private String tipoOperacao;

		private String tipoServico;

		private String formaLancamento;

		private String versaoLayout;

		private int tipoInscEmp;

		private String cpfCnpjEmp;

		private String codConvBanco;

		private String agencia;

		private String digAgencia;

		private String conta;

		private String digConta;

		private String digAgConta;

		private String razEmp;

		private String msg1;

		private String msg2;

		private int nrRemRet;

		private Date dataRemRet;

		private Date dataCred;

		public Reg1() {

			setRegistroHeader( 1 );
			setTipoServico( "01" );
			setVersaoLayout( "020" );
		}

		public Date getDataCred() {

			return dataCred;
		}

		public void setDataCred( Date dataCred ) {

			this.dataCred = dataCred;
		}

		public Reg1( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public String getAgencia() {

			return agencia;
		}

		public void setAgencia( final String agencia ) {

			this.agencia = agencia;
		}

		public String getCpfCnpjEmp() {

			return cpfCnpjEmp;
		}

		/**
		 * Inscrição da empresa. Conforme o tipo da inscrição.<br>
		 * 
		 * @see org.freedom.modulos.fnc.business.component.CnabUtil.Reg1#setTipoInscEmp(int tipoInscEmp )
		 */
		public void setCpfCnpjEmp( final String cnpjEmp ) {

			this.cpfCnpjEmp = cnpjEmp;
		}

		public String getCodBanco() {

			return codBanco;
		}

		public void setCodBanco( final String codBanco ) {

			this.codBanco = codBanco;
		}

		public String getCodConvBanco() {

			return codConvBanco;
		}

		/**
		 * Indentifica a empresa no banco para determinados tipos de serviços.<br>
		 * Observar as regras de preenchimento abaixo no que se refere ao headre de serviço/lote:<br>
		 * "9999999994444CCVVV " / 20 bytes / , onde:<br>
		 * 999999999 - Código do convênio.<br>
		 * 4444 - Código do produto.<br>
		 * CC - Carteira de cobrança.<br>
		 * VVV - Variação da carteira de cobrança.<br>
		 */
		public void setCodConvBanco( final String codConvBanco ) {

			this.codConvBanco = codConvBanco;
		}

		public String getConta() {

			return conta;
		}

		public void setConta( final String conta ) {

			this.conta = conta;
		}

		public Date getDataRemRet() {

			return dataRemRet;
		}

		public void setDataRemRet( final Date dataRemRet ) {

			this.dataRemRet = dataRemRet;
		}

		public String getDigAgConta() {

			return digAgConta;
		}

		public void setDigAgConta( final String digAgeConta ) {

			this.digAgConta = digAgeConta;
		}

		public String getDigAgencia() {

			return digAgencia;
		}

		public void setDigAgencia( final String digAgencia ) {

			this.digAgencia = digAgencia;
		}

		public String getDigConta() {

			return digConta;
		}

		public void setDigConta( final String digConta ) {

			this.digConta = digConta;
		}

		public String getFormaLancamento() {

			return formaLancamento;
		}

		/**
		 * Este campo não será utilizado para combrança.<br>
		 */
		public void setFormaLancamento( final String formaLancamento ) {

			this.formaLancamento = formaLancamento;
		}

		public int getLoteServico() {

			return loteServico;
		}

		/**
		 * Indentifica um Lote de Serviço.<br>
		 * Sequencial e não deve ser repetido dentro do arquivo.<br>
		 * As numerações 0000 e 9999 <br>
		 * são exclusivas para o Header e para o Trailer do arquivo respectivamente.<br>
		 */
		public void setLoteServico( final int loteServico ) {

			this.loteServico = loteServico;
		}

		public String getMsg1() {

			return msg1;
		}

		/**
		 * As menssagens serão impressas em todos os bloquetos referentes 1 e 2 ao mesmo lote.<br>
		 * Estes campos não serão utilizados no arquivo de retorno.<br>
		 */
		public void setMsg1( final String msg1 ) {

			this.msg1 = msg1;
		}

		/**
		 * As menssagens serão impressas em todos os bloquetos referentes 1 e 2 ao mesmo lote.<br>
		 * Estes campos não serão utilizados no arquivo de retorno.<br>
		 */
		public String getMsg2() {

			return msg2;
		}

		public void setMsg2( final String msg2 ) {

			this.msg2 = msg2;
		}

		public String getRazEmp() {

			return razEmp;
		}

		public void setRazEmp( final String nomeEmp ) {

			this.razEmp = nomeEmp;
		}

		public int getNrRemRet() {

			return nrRemRet;
		}

		public void setNrRemRet( final int nrRemRet ) {

			this.nrRemRet = nrRemRet;
		}

		public int getRegistroHeader() {

			return registroHeader;
		}

		/**
		 * Indica o tipo de registro.<br>
		 */
		private void setRegistroHeader( final int registroHeader ) {

			this.registroHeader = registroHeader;
		}

		public int getTipoInscEmp() {

			return tipoInscEmp;
		}

		/**
		 * Indica o tipo de inscrição da empresa.<br>
		 * 1 - CPF.<br>
		 * 2 - CNPJ.<br>
		 */
		public void setTipoInscEmp( final int tipoInscEmp ) {

			this.tipoInscEmp = tipoInscEmp;
		}

		public String getTipoOperacao() {

			return tipoOperacao;
		}

		/**
		 * Indica a operação que devera ser realizada com os registros Detalhe do Lote.<br>
		 * Deve constar apenas um tipo por Lote:<br>
		 * C - Lançamento a Crédito.<br>
		 * D - Lançamento a Débito.<br>
		 * E - Extrato para conciliação.<br>
		 * I - Informações de titulos capturados do próprio banco.<br>
		 * R - Arquivo de remessa.<br>
		 * T - Arquivo de retorno.<br>
		 */
		public void setTipoOperacao( final String tipoOperacao ) {

			this.tipoOperacao = tipoOperacao;
		}

		public String getTipoServico() {

			return tipoServico;
		}

		/**
		 * Indica o tipo de serviço que o lote contém.<br>
		 * 01 - Cobrança.<br>
		 * 02 - Cobrança em papel.<br>
		 * 03 - Bloqueto eletronico.<br>
		 * 04 - Conciliação bancária.<br>
		 * 05 - Débitos.<br>
		 * 10 - Pagamento dividendos.<br>
		 * 20 - Pagamento fornecedor.<br>
		 * 30 - Pagamento salários.<br>
		 * 50 - Pagamento sinistro segurados.<br>
		 * 60 - Pagamento despesa viajante em trânsito.<br>
		 */
		private void setTipoServico( final String tipoServico ) {

			this.tipoServico = tipoServico;
		}

		public String getVersaoLayout() {

			return versaoLayout;
		}

		/**
		 * Indica o número da versão do layout do lote, composto de:<br>
		 * versão : 2 digitos.<br>
		 * release: 1 digito.<br>
		 */
		private void setVersaoLayout( final String versaoLayout ) {

			this.versaoLayout = versaoLayout;
		}

		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {
				if ( padraocnab.equals( CNAB_240 ) ) {
					line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
					line.append( format( getLoteServico(), ETipo.$9, 4, 0 ) );
					line.append( getRegistroHeader() );
					line.append( format( getTipoOperacao(), ETipo.X, 1, 0 ) );
					line.append( getTipoServico() );
					line.append( format( getFormaLancamento(), ETipo.$9, 2, 0 ) );
					line.append( getVersaoLayout() );
					line.append( ' ' );
					line.append( format( getTipoInscEmp(), ETipo.$9, 1, 0 ) );
					line.append( format( getCpfCnpjEmp(), ETipo.$9, 15, 0 ) );
					line.append( format( getCodConvBanco(), ETipo.X, 20, 0 ) );
					line.append( format( getAgencia(), ETipo.$9, 5, 0 ) );
					line.append( format( getDigAgencia(), ETipo.X, 1, 0 ) );
					line.append( format( getConta(), ETipo.$9, 12, 0 ) );
					line.append( format( getDigConta(), ETipo.X, 1, 0 ) );
					line.append( format( getDigAgConta(), ETipo.X, 1, 0 ) );
					line.append( format( getRazEmp(), ETipo.X, 30, 0 ) );
					line.append( format( getMsg1(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg2(), ETipo.X, 40, 0 ) );
					line.append( format( getNrRemRet(), ETipo.$9, 8, 0 ) );
					line.append( dateToString( getDataRemRet(), null ) );
					line.append( dateToString( getDataCred(), null ) );
					line.append( StringFunctions.replicate( " ", 33 ) );
					line.append( (char) 13 );
					line.append( (char) 10 );
				}

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 1.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		@ Override
		public void parseLine( final String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "CNAB registro 1.\nLinha nula." );
				}
				else {

					setCodBanco( line.substring( 0, 3 ) );
					setLoteServico( line.substring( 3, 7 ).trim().length() > 0 ? Integer.parseInt( line.substring( 3, 7 ).trim() ) : 0 );
					setRegistroHeader( line.substring( 7, 8 ).trim().length() > 0 ? Integer.parseInt( line.substring( 7, 8 ).trim() ) : 0 );
					setTipoOperacao( line.substring( 8, 9 ) );
					setTipoServico( line.substring( 9, 11 ) );
					setFormaLancamento( line.substring( 11, 13 ) );
					setVersaoLayout( line.substring( 13, 16 ) );
					setTipoInscEmp( line.substring( 17, 18 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 18 ).trim() ) : 0 );
					setCpfCnpjEmp( line.substring( 18, 33 ) );
					setCodConvBanco( line.substring( 33, 53 ) );
					setAgencia( line.substring( 53, 58 ) );
					setDigAgencia( line.substring( 58, 59 ) );
					setConta( line.substring( 59, 71 ) );
					setDigConta( line.substring( 71, 72 ) );
					setDigAgConta( line.substring( 72, 73 ) );
					setRazEmp( line.substring( 73, 103 ) );
					setMsg1( line.substring( 103, 143 ) );
					setMsg2( line.substring( 143, 183 ) );
					setNrRemRet( line.substring( 183, 191 ).trim().length() > 0 ? Integer.parseInt( line.substring( 183, 191 ).trim() ) : 0 );
					setDataRemRet( stringDDMMAAAAToDate( line.substring( 191, 199 ).trim() ) );
					setDataCred( stringDDMMAAAAToDate( line.substring( 199, 207 ).trim() ) );
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 1.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}

	}

	public abstract class Reg3 extends Reg {

		private String codBanco;

		private int loteServico;

		private int registroDetalhe;

		private int seqLote;

		private char segmento;

		private int codMovimento;

		public Reg3( final char segmento ) {

			setRegistroDetalhe( 3 );
			setSegmento( segmento );
		}

		public String getCodBanco() {

			return codBanco;
		}

		public void setCodBanco( final String codBanco ) {

			this.codBanco = codBanco;
		}

		public int getCodMovimento() {

			return codMovimento;
		}

		/**
		 * 01 - Entrada de titulos.<br>
		 * 02 - Pedido de baixa.<br>
		 * 04 - Concessão de abatimento.<br>
		 * 05 - Cancelamento de abatimento.<br>
		 * 06 - Alteração de vencimento.<br>
		 * 07 - Concessão de desconto.<br>
		 * 08 - Cancelamento de desconto.<br>
		 * 09 - Protestar.<br>
		 * 10 - Cancela/Sustação da instrução de protesto.<br>
		 * 30 - Recusa da alegação do sacado.<br>
		 * 31 - Alteração de outros dados.<br>
		 * 40 - Alteração da modalidade.<br>
		 */
		public void setCodMovimento( final int codMovimento ) {

			this.codMovimento = codMovimento;
		}

		public int getLoteServico() {

			return loteServico;
		}

		/**
		 * Indentifica um Lote de Serviço.<br>
		 * Sequencial e nmão deve ser repetido dentro do arquivo.<br>
		 * As numerações 0000 e 9999 <br>
		 * são exclusivas para o Header e para o Trailer do arquivo respectivamente.<br>
		 */
		public void setLoteServico( final int loteServico ) {

			this.loteServico = loteServico;
		}

		public int getRegistroDetalhe() {

			return registroDetalhe;
		}

		/**
		 * Indica o tipo do registro.<br>
		 */
		private void setRegistroDetalhe( final int registroDetalhe ) {

			this.registroDetalhe = registroDetalhe;
		}

		public char getSegmento() {

			return segmento;
		}

		/**
		 * Indica o seguimento do registro.
		 */
		private void setSegmento( final char segmento ) {

			this.segmento = segmento;
		}

		public int getSeqLote() {

			return seqLote;
		}

		/**
		 * Número de sequência do registro no lote inicializado sempre em 1.<br>
		 */
		public void setSeqLote( final int seqLote ) {

			this.seqLote = seqLote;
		}

		public String getLineReg3( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {
				line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
				line.append( format( getLoteServico(), ETipo.$9, 4, 0 ) );
				line.append( getRegistroDetalhe() );
				line.append( format( getSeqLote(), ETipo.$9, 5, 0 ) );
				line.append( getSegmento() );
				line.append( ' ' );
				line.append( format( getCodMovimento(), ETipo.$9, 2, 0 ) );
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		public void parseLineReg3( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					setCodBanco( line.substring( 0, 3 ) );
					setLoteServico( line.substring( 3, 7 ).trim().length() > 0 ? Integer.parseInt( line.substring( 3, 7 ).trim() ) : 0 );
					setRegistroDetalhe( line.substring( 7, 8 ).trim().length() > 0 ? Integer.parseInt( line.substring( 7, 8 ).trim() ) : 0 );
					setSeqLote( line.substring( 8, 13 ).trim().length() > 0 ? Integer.parseInt( line.substring( 8, 13 ).trim() ) : 0 );
					setSegmento( line.substring( 13, 14 ).charAt( 0 ) );
					setCodMovimento( line.substring( 15, 17 ).trim().length() > 0 ? Integer.parseInt( line.substring( 15, 17 ).trim() ) : 0 );
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Reg3P extends Reg3 {

		private String agencia;

		private String digAgencia;

		private String conta;

		private String digConta;

		private String digAgConta;

		private String identTitulo;

		private int codCarteira;

		private int formaCadTitulo;

		private int tipoDoc;

		private int identEmitBol;

		private int identDist;

		private String docCobranca;

		private Date dtVencTitulo;

		private BigDecimal vlrTitulo;

		private String agenciaCob;

		private String digAgenciaCob;

		private int especieTit;

		private char aceite;

		private Date dtEmitTit;

		private int codJuros;

		private Date dtJuros;

		private BigDecimal vlrJurosTaxa;
		
		//private BigDecimal vlrJuros;

		private int codDesc;

		private Date dtDesc;

		private BigDecimal vlrpercConced;

		private BigDecimal vlrIOF;

		private BigDecimal vlrAbatimento;

		private String identTitEmp;

		private int codProtesto;

		private int diasProtesto;

		private int codBaixaDev;

		private int diasBaixaDevol;

		private int codMoeda;

		private String contrOperCred;

		public Reg3P() {

			super( 'P' );
		}

		public Reg3P( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public char getAceite() {

			return aceite;
		}

		/**
		 * A - Aceite.<br>
		 * N - Não aceite.<br>
		 */
		public void setAceite( final char aceite ) {

			this.aceite = aceite;
		}

		public String getAgencia() {

			return agencia;
		}

		public void setAgencia( final String agencia ) {

			this.agencia = agencia;
		}

		public String getAgenciaCob() {

			return agenciaCob;
		}

		public void setAgenciaCob( final String agenciaCob ) {

			this.agenciaCob = agenciaCob;
		}

		public int getCodBaixaDev() {

			return codBaixaDev;
		}

		/**
		 * Código para baixa/devolução.<br>
		 * 1 - Baixar/Devolver.<br>
		 * 2 - Não baixar/ Não devolver.<br>
		 */
		public void setCodBaixaDev( final int codBaixaDev ) {

			this.codBaixaDev = codBaixaDev;
		}

		public int getCodCarteira() {

			return codCarteira;
		}

		/**
		 * Carteira.<br>
		 * 1 - Cobrança simples.<br>
		 * 2 - Cobrança vinculada.<br>
		 * 3 - Cobrança caucionada.<br>
		 * 4 - Cobrança descontada.<br>
		 * 7 - Cobrança direta especial / carteira 17.
		 */
		public void setCodCarteira( final int codCarteira ) {

			this.codCarteira = codCarteira;
		}

		public int getCodDesc() {

			return codDesc;
		}

		/**
		 * Código do desconto.<br>
		 * 1 - Valor fixo até a data informada.<br>
		 * 2 - Percentual até a data informada.<br>
		 * 3 - Valor por antecipação por dia corrido.<br>
		 * 4 - Valor por antecipação por dia util.<br>
		 * 5 - Percentual sobre o valor nominal dia corrido.<br>
		 * 6 - Percentual sobre o valor nominal dia util.<br>
		 * Obs.: Para as opções 1 e 2 será obrigatório a informação da data.<br>
		 */
		public void setCodDesc( final int codDesc ) {

			this.codDesc = codDesc;
		}

		public int getCodJuros() {

			return codJuros;
		}

		/**
		 * Código do juros de mora.<br>
		 * 1 - Valor por dia.<br>
		 * 2 - Taxa mensal.<br>
		 * 3 - Isento.<br>
		 */
		public void setCodJuros( final int codJuros ) {

			this.codJuros = codJuros;
		}

		public int getCodMoeda() {

			return codMoeda;
		}

		/**
		 * Código da moeda.<br>
		 * 01 - Reservado para uso futuro.<br>
		 * 02 - Dolar americano comercial/venda.<br>
		 * 03 - Dolar americano turismo/venda.<br>
		 * 04 - ITRD.<br>
		 * 05 - IDTR.<br>
		 * 06 - UFIR diária.<br>
		 * 07 - UFIR mensal.<br>
		 * 08 - FAJ-TR.<br>
		 * 09 - Real.<br>
		 */
		public void setCodMoeda( final int codMoeda ) {

			this.codMoeda = codMoeda;
		}

		public int getCodProtesto() {

			return codProtesto;
		}

		/**
		 * Código para protesto.<br>
		 * 1 - Dias corridos.<br>
		 * 2 - Dias utéis.<br>
		 * 3 - Não protestar.<br>
		 */
		public void setCodProtesto( final int codProtesto ) {

			this.codProtesto = codProtesto;
		}

		public String getConta() {

			return conta;
		}

		public void setConta( final String conta ) {

			this.conta = conta;
		}

		public String getContrOperCred() {

			return contrOperCred;
		}

		public void setContrOperCred( final String contrOperCred ) {

			this.contrOperCred = contrOperCred;
		}

		public int getDiasBaixaDevol() {

			return diasBaixaDevol;
		}

		/**
		 * Número de dias para a Baixa / Devolução.<br>
		 */
		public void setDiasBaixaDevol( final int diasBaixaDevol ) {

			this.diasBaixaDevol = diasBaixaDevol;
		}

		public int getDiasProtesto() {

			return diasProtesto;
		}

		/**
		 * Número de dias para protesto.<br>
		 */
		public void setDiasProtesto( final int diasProtesto ) {

			this.diasProtesto = diasProtesto;
		}

		public String getDigAgConta() {

			return digAgConta;
		}

		public void setDigAgConta( final String digAgConta ) {

			this.digAgConta = digAgConta;
		}

		public String getDigAgencia() {

			return digAgencia;
		}

		public void setDigAgencia( final String digAgencia ) {

			this.digAgencia = digAgencia;
		}

		public String getDigAgenciaCob() {

			return digAgenciaCob;
		}

		public void setDigAgenciaCob( final String digAgenciaCob ) {

			this.digAgenciaCob = digAgenciaCob;
		}

		public String getDigConta() {

			return digConta;
		}

		public void setDigConta( final String digConta ) {

			this.digConta = digConta;
		}

		public String getDocCobranca() {

			return docCobranca;
		}

		/**
		 * Número utilizado pelo cliente para identificação do titulo.<br>
		 */
		public void setDocCobranca( final String docCobranca ) {

			this.docCobranca = docCobranca;
		}

		public Date getDtDesc() {

			return dtDesc;
		}

		public void setDtDesc( final Date dtDesc ) {

			this.dtDesc = dtDesc;
		}

		public Date getDtEmitTit() {

			return dtEmitTit;
		}

		public void setDtEmitTit( final Date dtEmitTit ) {

			this.dtEmitTit = dtEmitTit;
		}

		public Date getDtJuros() {

			return dtJuros;
		}

		/**
		 * Se inválida ou não informada, será assumida a data do vencimento.<br>
		 */
		public void setDtJuros( final Date dtJuros ) {

			this.dtJuros = dtJuros;
		}

		public Date getDtVencTitulo() {

			return dtVencTitulo;
		}

		/**
		 * Data de vencimento do titulo.<br>
		 * A vista - preencher com 11111111.<br>
		 * Contra-apresentação - preencher com 99999999.<br>
		 * Obs.: O prazo legal para vencimento "a vista" ou "contra apresentação"<br>
		 * é de 15 dias da data do registro no banco.<br>
		 */
		public void setDtVencTitulo( final Date dtVencTitulo ) {

			this.dtVencTitulo = dtVencTitulo;
		}

		public int getEspecieTit() {

			return especieTit;
		}

		/**
		 * Especie do titulo CB 240.<br>
		 * 01 - CH Cheque.<br>
		 * 02 - DM Duplicata mercantíl.<br>
		 * 03 - DMI Duplicata mercantíl p/ indicação.<br>
		 * 04 - DS Duplicata de serviço.<br>
		 * 05 - DSI DUplicata de serviçõ p/ indicação.<br>
		 * 06 - DR Duplicata rural.<br>
		 * 07 - LC Letra de cambio.<br>
		 * 08 - NCC Nota de crédito comercial.<br>
		 * 09 - NCE Nota de crédito a exportação.<br>
		 * 10 - NCI Nota de crédito indústria.<br>
		 * 11 - NCR Nota de crédito rural.<br>
		 * 12 - NP Nota promissória.<br>
		 * 13 - NPR Nota promissória rural.<br>
		 * 14 - TM Triplicata mercantíl.<br>
		 * 15 - TS Triplicata de serviço.<br>
		 * 16 - NS Nota de seguro.<br>
		 * 17 - RC Recibo.<br>
		 * 18 - FAT Fatura.<br>
		 * 19 - ND Nota de débito.<br>
		 * 20 - AP Apolice de seguro.<br>
		 * 21 - ME Mensalidade escolar.<br>
		 * 22 - PC Parcela de consórcio.<br>
		 * 99 - Outros.<br>
		 */
		public void setEspecieTit( final int especieTit ) {

			this.especieTit = especieTit;
		}

		public int getFormaCadTitulo() {

			return formaCadTitulo;
		}

		/**
		 * Forma de cadastramento do titulo.<br>
		 * 1 - Com cadastro.<br>
		 * 2 - Sem cadastro.<br>
		 */
		public void setFormaCadTitulo( final int formaCadTitulo ) {

			this.formaCadTitulo = formaCadTitulo;
		}

		public int getIdentDist() {

			return identDist;
		}

		/**
		 * Identificação da distribuição.<br>
		 * 1 - Banco.<br>
		 * 2 - Cliente.<br>
		 */
		public void setIdentDist( final int identDist ) {

			this.identDist = identDist;
		}

		public int getIdentEmitBol() {

			return identEmitBol;
		}

		/**
		 * Identificação da emissão de bloqueto.<br>
		 * 1 - Banco emite.<br>
		 * 2 - Cliente emite.<br>
		 * 3 - Banco pré-emite e o cliente completa.<br>
		 * 4 - Banco reemite.<br>
		 * 5 - Banco não reemite.<br>
		 * 6 - Cobrança sem papel.<br>
		 * Obs.: Os campos 4 e 5 só serão aceitos para código de movimento para remessa 31.
		 */
		public void setIdentEmitBol( final int identEmitBol ) {

			this.identEmitBol = identEmitBol;
		}

		public String getIdentTitEmp() {

			return identTitEmp;
		}

		public void setIdentTitEmp( final String identTitEmp ) {

			this.identTitEmp = identTitEmp;
		}

		public String getIdentTitulo() {

			return identTitulo;
		}

		/**
		 * Nosso número.<br>
		 */
		public void setIdentTitulo( final String identTitulo ) {

			this.identTitulo = identTitulo;
		}

		public int getTipoDoc() {

			return tipoDoc;
		}

		/**
		 * Tipo de documento.<br>
		 * 1 - Tradicional.<br>
		 * 2 - Escrutiral.<br>
		 */
		public void setTipoDoc( final int tipoDoc ) {

			this.tipoDoc = tipoDoc;
		}

		public BigDecimal getVlrAbatimento() {

			return vlrAbatimento;
		}

		public void setVlrAbatimento( final BigDecimal vlrAbatimento ) {

			this.vlrAbatimento = vlrAbatimento;
		}

		public BigDecimal getVlrIOF() {

			return vlrIOF;
		}

		public void setVlrIOF( final BigDecimal vlrIOF ) {

			this.vlrIOF = vlrIOF;
		}

		public BigDecimal getVlrJurosTaxa() {

			return vlrJurosTaxa;
		}

		public void setVlrJurosTaxa( final BigDecimal vlrJurosTaxa ) {

			this.vlrJurosTaxa = vlrJurosTaxa;
		}

/*		public BigDecimal getVlrJuros() {

			return vlrJuros;
		}

		public void setVlrJuros( final BigDecimal vlrJuros ) {

			this.vlrJuros = vlrJuros;
		} */
		
		

		public BigDecimal getVlrpercConced() {

			return vlrpercConced;
		}

		public void setVlrpercConced( final BigDecimal vlrpercConced ) {

			this.vlrpercConced = vlrpercConced;
		}

		public BigDecimal getVlrTitulo() {

			return vlrTitulo;
		}

		public void setVlrTitulo( final BigDecimal vlrTitulo ) {

			this.vlrTitulo = vlrTitulo;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#getLine()
		 */
		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {
				line.append( super.getLineReg3( padraocnab ) );
				line.append( format( getAgencia(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgencia(), ETipo.X, 1, 0 ) );
				line.append( format( getConta(), ETipo.$9, 12, 0 ) );
				line.append( format( getDigConta(), ETipo.X, 1, 0 ) );
				line.append( format( getDigAgConta(), ETipo.X, 1, 0 ) );
				line.append( format( getIdentTitulo(), ETipo.X, 20, 0 ) );
				line.append( format( getCodCarteira(), ETipo.$9, 1, 0 ) );
				line.append( format( getFormaCadTitulo(), ETipo.$9, 1, 0 ) );
				line.append( format( getTipoDoc(), ETipo.$9, 1, 0 ) );
				line.append( format( getIdentEmitBol(), ETipo.$9, 1, 0 ) );
				line.append( format( getIdentDist(), ETipo.$9, 1, 0 ) );
				line.append( format( getDocCobranca(), ETipo.X, 15, 0 ) );
				line.append( dateToString( getDtVencTitulo(), null ) );
				line.append( format( getVlrTitulo(), ETipo.$9, 15, 2 ) );
				line.append( format( getAgenciaCob(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgenciaCob(), ETipo.$9, 1, 0 ) );
				line.append( format( getEspecieTit(), ETipo.$9, 2, 0 ) );
				line.append( format( getAceite(), ETipo.X, 1, 0 ) );
				line.append( dateToString( getDtEmitTit(), null ) );
				line.append( format( getCodJuros(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDtJuros(), null ) );
				line.append( format( getVlrJurosTaxa(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodDesc(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDtDesc(), null ) );
				line.append( format( getVlrpercConced(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrIOF(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrAbatimento(), ETipo.$9, 15, 2 ) );
				line.append( format( getIdentTitEmp(), ETipo.X, 25, 0 ) );
				line.append( format( getCodProtesto(), ETipo.$9, 1, 0 ) );
				line.append( format( getDiasProtesto(), ETipo.$9, 2, 0 ) );
				line.append( format( getCodBaixaDev(), ETipo.$9, 1, 0 ) );
				line.append( format( getDiasBaixaDevol(), ETipo.$9, 3, 0 ) );
				line.append( format( getCodMoeda(), ETipo.$9, 2, 0 ) );
				line.append( format( getContrOperCred(), ETipo.$9, 10, 0 ) );
				line.append( " " );
				line.append( (char) 13 );
				line.append( (char) 10 );
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento P.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#parseLine(java.lang.String)
		 */
		@ Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					super.parseLineReg3( line );
					setAgencia( line.substring( 17, 22 ) );
					setDigAgencia( line.substring( 22, 23 ) );
					setConta( line.substring( 23, 35 ) );
					setDigConta( line.substring( 35, 36 ) );
					setDigAgConta( line.substring( 36, 37 ) );
					setIdentTitulo( line.substring( 37, 57 ) );
					setCodCarteira( line.substring( 57, 58 ).trim().length() > 0 ? Integer.parseInt( line.substring( 57, 58 ).trim() ) : 0 );
					setFormaCadTitulo( line.substring( 58, 59 ).trim().length() > 0 ? Integer.parseInt( line.substring( 58, 59 ).trim() ) : 0 );
					setTipoDoc( line.substring( 59, 60 ).trim().length() > 0 ? Integer.parseInt( line.substring( 59, 60 ).trim() ) : 0 );
					setIdentEmitBol( line.substring( 60, 61 ).trim().length() > 0 ? Integer.parseInt( line.substring( 60, 61 ).trim() ) : 0 );
					setIdentDist( line.substring( 61, 62 ).trim().length() > 0 ? Integer.parseInt( line.substring( 61, 62 ).trim() ) : 0 );
					setDocCobranca( line.substring( 62, 77 ) );
					setDtVencTitulo( stringDDMMAAAAToDate( line.substring( 77, 85 ).trim() ) );
					setVlrTitulo( strToBigDecimal( line.substring( 85, 100 ) ) );
					setAgenciaCob( line.substring( 100, 105 ) );
					setDigAgenciaCob( line.substring( 105, 106 ).trim() );
					setEspecieTit( line.substring( 106, 108 ).trim().length() > 0 ? Integer.parseInt( line.substring( 106, 108 ).trim() ) : 0 );
					setAceite( line.substring( 108, 109 ).charAt( 0 ) );
					setDtEmitTit( stringDDMMAAAAToDate( line.substring( 109, 117 ).trim() ) );
					setCodJuros( line.substring( 117, 118 ).trim().length() > 0 ? Integer.parseInt( line.substring( 117, 118 ).trim() ) : 0 );
					setDtJuros( stringDDMMAAAAToDate( line.substring( 118, 126 ).trim() ) );
					setVlrJurosTaxa( strToBigDecimal( line.substring( 126, 141 ) ) );
					setCodDesc( line.substring( 141, 142 ).trim().length() > 0 ? Integer.parseInt( line.substring( 141, 142 ).trim() ) : 0 );
					setDtDesc( stringDDMMAAAAToDate( line.substring( 142, 150 ).trim() ) );
					setVlrpercConced( strToBigDecimal( line.substring( 150, 165 ) ) );
					setVlrIOF( strToBigDecimal( line.substring( 165, 180 ) ) );
					setVlrAbatimento( strToBigDecimal( line.substring( 180, 195 ) ) );
					setIdentTitEmp( line.substring( 195, 220 ) );
					setCodProtesto( line.substring( 220, 221 ).trim().length() > 0 ? Integer.parseInt( line.substring( 220, 221 ).trim() ) : 0 );
					setDiasProtesto( line.substring( 221, 223 ).trim().length() > 0 ? Integer.parseInt( line.substring( 221, 223 ).trim() ) : 0 );
					setCodBaixaDev( line.substring( 223, 224 ).trim().length() > 0 ? Integer.parseInt( line.substring( 223, 224 ).trim() ) : 0 );
					setDiasBaixaDevol( line.substring( 224, 227 ).trim().length() > 0 ? Integer.parseInt( line.substring( 224, 227 ).trim() ) : 0 );
					setCodMoeda( line.substring( 227, 229 ).trim().length() > 0 ? Integer.parseInt( line.substring( 227, 229 ).trim() ) : 0 );
					setContrOperCred( line.substring( 229, 239 ) );
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento P.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Reg3Q extends Reg3 {

		private int tipoInscCli;

		private String cpfCnpjCli;

		private String razCli;

		private String endCli;

		private String bairCli;

		private String cepCli;

		// private String sufxCepCli;
		private String cidCli;

		private String ufCli;

		private int tipoInscAva;

		private String cpfCnpjAva;

		private String razAva;

		private int codCompensacao;

		private String nossoNumero;

		public Reg3Q() {

			super( 'Q' );
		}

		public Reg3Q( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public String getBairCli() {

			return bairCli;
		}

		public void setBairCli( final String bairCli ) {

			this.bairCli = bairCli;
		}

		public String getCepCli() {

			return cepCli;
		}

		public void setCepCli( final String cepCli ) {

			this.cepCli = cepCli;
		}

		public String getCidCli() {

			return cidCli;
		}

		public void setCidCli( final String cidCli ) {

			this.cidCli = cidCli;
		}

		public int getCodCompensacao() {

			return codCompensacao;
		}

		/**
		 * Somente para troca de arquivos entre bancos.<br>
		 */
		private void setCodCompensacao( final int codCompensacao ) {

			this.codCompensacao = codCompensacao;
		}

		public String getCpfCnpjAva() {

			return cpfCnpjAva;
		}

		/**
		 * Inscrição. Conforme o tipo da inscrição.<br>
		 * 
		 * @see org.freedom.modulos.fnc.business.component.CnabUtil.Reg3Q#setTipoInscAva(int tipoInscEmp )
		 */
		public void setCpfCnpjAva( final String cpfCnpjAva ) {

			this.cpfCnpjAva = cpfCnpjAva;
		}

		public String getCpfCnpjCli() {

			return cpfCnpjCli;
		}

		/**
		 * Inscrição. Conforme o tipo da inscrição.<br>
		 * 
		 * @see org.freedom.modulos.fnc.business.component.CnabUtil.Reg3Q#setTipoInscCli(int tipoInscEmp )
		 */
		public void setCpfCnpjCli( final String cpfCnpjCli ) {

			this.cpfCnpjCli = cpfCnpjCli;
		}

		public String getEndCli() {

			return endCli;
		}

		public void setEndCli( final String endCli ) {

			this.endCli = endCli;
		}

		public String getNossoNumero() {

			return nossoNumero;
		}

		/**
		 * Somente para troca de arquivos entre bancos.<br>
		 */
		private void setNossoNumero( final String nossoNumero ) {

			this.nossoNumero = nossoNumero;
		}

		public String getRazAva() {

			return razAva;
		}

		/**
		 * Informação obrigatória quando se tratar de titulo negociado em nome de terceiros.<br>
		 */
		public void setRazAva( final String razAva ) {

			this.razAva = razAva;
		}

		public String getRazCli() {

			return razCli;
		}

		public void setRazCli( final String razCli ) {

			this.razCli = razCli;
		}

		public int getTipoInscAva() {

			return tipoInscAva;
		}

		/**
		 * Indica o tipo de inscrição da empresa.<br>
		 * 0 - Isento / Não informado.<br>
		 * 1 - CPF.<br>
		 * 2 - CNPJ.<br>
		 */
		public void setTipoInscAva( final int tipoInscAva ) {

			this.tipoInscAva = tipoInscAva;
		}

		public int getTipoInscCli() {

			return tipoInscCli;
		}

		/**
		 * Indica o tipo de inscrição da empresa.<br>
		 * 0 - Isento / Não informado.<br>
		 * 1 - CPF.<br>
		 * 2 - CNPJ.<br>
		 */
		public void setTipoInscCli( final int tipoInscCli ) {

			this.tipoInscCli = tipoInscCli;
		}

		public String getUfCli() {

			return ufCli;
		}

		public void setUfCli( final String ufCli ) {

			this.ufCli = ufCli;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#getLine()
		 */
		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3( padraocnab ) );
				line.append( format( getTipoInscCli(), ETipo.$9, 1, 0 ) );
				line.append( format( getCpfCnpjCli(), ETipo.$9, 15, 0 ) );
				line.append( format( getRazCli(), ETipo.X, 40, 0 ) );
				line.append( format( getEndCli(), ETipo.X, 40, 0 ) );
				line.append( format( getBairCli(), ETipo.X, 15, 0 ) );
				line.append( format( getCepCli(), ETipo.$9, 8, 0 ) );
				line.append( format( getCidCli(), ETipo.X, 15, 0 ) );
				line.append( format( getUfCli(), ETipo.X, 2, 0 ) );
				line.append( format( getTipoInscAva(), ETipo.$9, 1, 0 ) );
				line.append( format( getCpfCnpjAva(), ETipo.$9, 15, 0 ) );
				line.append( format( getRazAva(), ETipo.X, 40, 0 ) );
				line.append( format( getCodCompensacao(), ETipo.$9, 3, 0 ) );
				line.append( format( getNossoNumero(), ETipo.X, 20, 0 ) );
				line.append( StringFunctions.replicate( " ", 8 ) );
				line.append( (char) 13 );
				line.append( (char) 10 );

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento Q.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#parseLine(java.lang.String)
		 */
		@ Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					super.parseLineReg3( line );
					setTipoInscCli( line.substring( 17, 18 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 18 ).trim() ) : 0 );
					setCpfCnpjCli( line.substring( 18, 33 ) );
					setRazCli( line.substring( 33, 73 ) );
					setEndCli( line.substring( 73, 113 ) );
					setBairCli( line.substring( 113, 128 ) );
					setCepCli( line.substring( 128, 136 ) );
					setCidCli( line.substring( 136, 151 ) );
					setUfCli( line.substring( 151, 153 ) );
					setTipoInscAva( line.substring( 153, 154 ).trim().length() > 0 ? Integer.parseInt( line.substring( 153, 154 ).trim() ) : 0 );
					setCpfCnpjAva( line.substring( 154, 169 ) );
					setRazAva( line.substring( 169, 209 ) );
					setCodCompensacao( line.substring( 209, 212 ).trim().length() > 0 ? Integer.parseInt( line.substring( 209, 212 ).trim() ) : 0 );
					setNossoNumero( line.substring( 212, 232 ) );
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento Q.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Reg3R extends Reg3 {

		private int codDesc2;

		private Date dataDesc2;

		private BigDecimal vlrPercConced2;

		private int codDesc3;

		private Date dataDesc3;

		private BigDecimal vlrPercConced3;

		private int codMulta;

		private Date dataMulta;

		private BigDecimal vlrPercMulta;

		private String msgSacado;

		private String msg3;

		private String msg4;

		private String codBancoDeb;

		private String agenciaDeb;

		private String contaDeb;

		private int codOcorrSacado;

		public Reg3R() {

			super( 'R' );
		}

		public Reg3R( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public String getAgenciaDeb() {

			return agenciaDeb;
		}

		public void setAgenciaDeb( final String agenciaDeb ) {

			this.agenciaDeb = agenciaDeb;
		}

		public String getCodBancoDeb() {

			return codBancoDeb;
		}

		public void setCodBancoDeb( final String codBancoDeb ) {

			this.codBancoDeb = codBancoDeb;
		}

		public int getCodDesc2() {

			return codDesc2;
		}

		/**
		 * Código do desconto.<br>
		 * 1 - Valor fixo até a data informada.<br>
		 * 2 - Percentual até a data informada.<br>
		 * 3 - Valor por antecipação por dia corrido.<br>
		 * 4 - Valor por antecipação por dia util.<br>
		 * 5 - Percentual sobre o valor nominal dia corrido.<br>
		 * 6 - Percentual sobre o valor nominal dia util.<br>
		 * Obs.: Para as opções 1 e 2 será obrigatório a informação da data.<br>
		 */
		public void setCodDesc2( final int codDesc2 ) {

			this.codDesc2 = codDesc2;
		}

		public int getCodDesc3() {

			return codDesc3;
		}

		/**
		 * Código do desconto.<br>
		 * 1 - Valor fixo até a data informada.<br>
		 * 2 - Percentual até a data informada.<br>
		 * 3 - Valor por antecipação por dia corrido.<br>
		 * 4 - Valor por antecipação por dia util.<br>
		 * 5 - Percentual sobre o valor nominal dia corrido.<br>
		 * 6 - Percentual sobre o valor nominal dia util.<br>
		 * Obs.: Para as opções 1 e 2 será obrigatório a informação da data.<br>
		 */
		public void setCodDesc3( final int codDesc3 ) {

			this.codDesc3 = codDesc3;
		}

		public int getCodMulta() {

			return codMulta;
		}

		/**
		 * Código da multa.<br>
		 * 1 - Valor fixo.<br>
		 * 2 - Percentual.<br>
		 */
		public void setCodMulta( final int codMulta ) {

			this.codMulta = codMulta;
		}

		public int getCodOcorrSacado() {

			return codOcorrSacado;
		}

		/**
		 * Deverá conter o(s) código(s) da(s) ocorrência(s) do sacado<br>
		 * a(s) qual(is) o cedente não concorda.<br>
		 * Somente será utilizado para o código de movimento 30.<br>
		 */
		public void setCodOcorrSacado( final int codOcorrSacado ) {

			this.codOcorrSacado = codOcorrSacado;
		}

		public String getContaDeb() {

			return contaDeb;
		}

		public void setContaDeb( final String contaDeb ) {

			this.contaDeb = contaDeb;
		}

		public Date getDataDesc2() {

			return dataDesc2;
		}

		public void setDataDesc2( final Date dataDesc2 ) {

			this.dataDesc2 = dataDesc2;
		}

		public Date getDataDesc3() {

			return dataDesc3;
		}

		public void setDataDesc3( final Date dataDesc3 ) {

			this.dataDesc3 = dataDesc3;
		}

		public Date getDataMulta() {

			return dataMulta;
		}

		public void setDataMulta( final Date dataMulta ) {

			this.dataMulta = dataMulta;
		}

		public String getMsg3() {

			return msg3;
		}

		/**
		 * Menssagem livre a ser impressa no campo instruções<br>
		 * da ficha de compensação do bloqueto.<br>
		 */
		public void setMsg3( final String msg3 ) {

			this.msg3 = msg3;
		}

		/**
		 * Menssagem livre a ser impressa no campo instruções<br>
		 * da ficha de compensação do bloqueto.<br>
		 */
		public String getMsg4() {

			return msg4;
		}

		public void setMsg4( final String msg4 ) {

			this.msg4 = msg4;
		}

		public String getMsgSacado() {

			return msgSacado;
		}

		/**
		 * Este campo só poderá ser utilizado,<br>
		 * cazo haja troca de arquivos magnéticos entre o banco e o sacado.<br>
		 */
		public void setMsgSacado( final String msgSacado ) {

			this.msgSacado = msgSacado;
		}

		public BigDecimal getVlrPercConced2() {

			return vlrPercConced2;
		}

		public void setVlrPercConced2( final BigDecimal vlrPercConced2 ) {

			this.vlrPercConced2 = vlrPercConced2;
		}

		public BigDecimal getVlrPercConced3() {

			return vlrPercConced3;
		}

		public void setVlrPercConced3( final BigDecimal vlrPercConced3 ) {

			this.vlrPercConced3 = vlrPercConced3;
		}

		public BigDecimal getVlrPercMulta() {

			return vlrPercMulta;
		}

		public void setVlrPercMulta( final BigDecimal vlrPercMulta ) {

			this.vlrPercMulta = vlrPercMulta;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#getLine()
		 */
		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {
				line.append( super.getLineReg3( padraocnab ) );
				line.append( format( getCodDesc2(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDataDesc2(), null ) );
				line.append( format( getVlrPercConced2(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodDesc3(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDataDesc3(), null ) );
				line.append( format( getVlrPercConced3(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodMulta(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDataMulta(), null ) );
				line.append( format( getVlrPercMulta(), ETipo.$9, 15, 2 ) );
				line.append( format( getMsgSacado(), ETipo.X, 10, 0 ) );
				line.append( format( getMsg3(), ETipo.X, 40, 0 ) );
				line.append( format( getMsg4(), ETipo.X, 40, 0 ) );
				line.append( format( getCodBancoDeb(), ETipo.$9, 3, 0 ) );
				line.append( format( getAgenciaDeb(), ETipo.$9, 4, 0 ) );
				line.append( format( getContaDeb(), ETipo.$9, 13, 0 ) );
				line.append( format( getCodOcorrSacado(), ETipo.$9, 8, 0 ) );
				line.append( (char) 13 );
				line.append( (char) 10 );
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento R.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#parseLine(java.lang.String)
		 */
		@ Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					super.parseLineReg3( line );
					setCodDesc2( line.substring( 17, 18 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 18 ).trim() ) : 0 );
					setDataDesc2( stringDDMMAAAAToDate( line.substring( 18, 26 ).trim() ) );
					setVlrPercConced2( strToBigDecimal( line.substring( 26, 41 ) ) );
					setCodDesc3( line.substring( 41, 42 ).trim().length() > 0 ? Integer.parseInt( line.substring( 41, 42 ).trim() ) : 0 );
					setDataDesc3( stringDDMMAAAAToDate( line.substring( 42, 50 ).trim() ) );
					setVlrPercConced3( strToBigDecimal( line.substring( 50, 65 ) ) );
					setCodMulta( line.substring( 65, 66 ).trim().length() > 0 ? Integer.parseInt( line.substring( 65, 66 ).trim() ) : 0 );
					setDataMulta( stringDDMMAAAAToDate( line.substring( 66, 74 ).trim() ) );
					setVlrPercMulta( strToBigDecimal( line.substring( 74, 89 ) ) );
					setMsgSacado( line.substring( 89, 99 ) );
					setMsg3( line.substring( 99, 139 ) );
					setMsg4( line.substring( 139, 179 ) );
					setCodBancoDeb( line.substring( 179, 182 ) );
					setAgenciaDeb( line.substring( 182, 186 ) );
					setContaDeb( line.substring( 186, 199 ) );
					setCodOcorrSacado( line.substring( 199, 207 ).trim().length() > 0 ? Integer.parseInt( line.substring( 199, 207 ).trim() ) : 0 );
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento R.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Reg3S extends Reg3 {

		private int tipoImpressao;

		private int linhaImp;

		private String msgImp;

		private int tipoChar;

		private String msg5;

		private String msg6;

		private String msg7;

		private String msg8;

		private String msg9;

		public Reg3S() {

			super( 'S' );
		}

		public Reg3S( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public int getLinhaImp() {

			return linhaImp;
		}

		/**
		 * Número da linha a ser impressa.<br>
		 * Frente : Poderá variar de 01 a 36.<br>
		 * Verso : Poderá variar de 01 a 24.<br>
		 * Zeros : Para envio de bloqueto por e-mail.<br>
		 */
		public void setLinhaImp( final int linhaImp ) {

			this.linhaImp = linhaImp;
		}

		public String getMsg5() {

			return msg5;
		}

		/**
		 * Menssagem livre a ser impressa no campo instruções<br>
		 * da ficha de compensação do bloqueto.<br>
		 */
		public void setMsg5( final String msg5 ) {

			this.msg5 = msg5;
		}

		public String getMsg6() {

			return msg6;
		}

		/**
		 * Menssagem livre a ser impressa no campo instruções<br>
		 * da ficha de compensação do bloqueto.<br>
		 */
		public void setMsg6( final String msg6 ) {

			this.msg6 = msg6;
		}

		public String getMsg7() {

			return msg7;
		}

		/**
		 * Menssagem livre a ser impressa no campo instruções<br>
		 * da ficha de compensação do bloqueto.<br>
		 */
		public void setMsg7( final String msg7 ) {

			this.msg7 = msg7;
		}

		public String getMsg8() {

			return msg8;
		}

		/**
		 * Menssagem livre a ser impressa no campo instruções<br>
		 * da ficha de compensação do bloqueto.<br>
		 */
		public void setMsg8( final String msg8 ) {

			this.msg8 = msg8;
		}

		public String getMsg9() {

			return msg9;
		}

		/**
		 * Menssagem livre a ser impressa no campo instruções<br>
		 * da ficha de compensação do bloqueto.<br>
		 */
		public void setMsg9( final String msg9 ) {

			this.msg9 = msg9;
		}

		public String getMsgImp() {

			return msgImp;
		}

		/**
		 * Está linha deverá ser enviada no formato imagem de impressão,<br>
		 * com tamanho maximo de 140 posições.<br>
		 * Para bloqueto por e-mail: os endereços de e-mail deverão ser separados<br>
		 * por ";" e sem espaços.<br>
		 */
		public void setMsgImp( final String msgImp ) {

			this.msgImp = msgImp;
		}

		public int getTipoChar() {

			return tipoChar;
		}

		/**
		 * Formato do caractere de impressão.<br>
		 * 01 - Normal.<br>
		 * 02 - Itálico.<br>
		 * Zeros para envio de bloqueto por e-mail.<br>
		 */
		public void setTipoChar( final int tipoChar ) {

			this.tipoChar = tipoChar;
		}

		public int getTipoImpressao() {

			return tipoImpressao;
		}

		/**
		 * Código de indentificação para impressão da menssagem.<br>
		 * 1 - Frente do bloqueto.<br>
		 * 2 - Verso do bloqueto.<br>
		 * 3 - Campo instruções da ficha de compensação do bloqueto.<br>
		 * 8 - Bloqueto por e-mail.<br>
		 */
		public void setTipoImpressao( final int tipoImpressao ) {

			this.tipoImpressao = tipoImpressao;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#getLine()
		 */
		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3( padraocnab ) );
				line.append( format( getTipoImpressao(), ETipo.$9, 1, 0 ) );

				if ( getTipoImpressao() == 1 || getTipoImpressao() == 2 ) {

					line.append( format( getLinhaImp(), ETipo.$9, 2, 0 ) );
					line.append( format( getMsgImp(), ETipo.X, 140, 0 ) );
					line.append( format( getTipoChar(), ETipo.$9, 2, 0 ) );
					line.append( StringFunctions.replicate( " ", 78 ) );
				}
				else if ( getTipoImpressao() == 3 ) {

					line.append( format( getMsg5(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg6(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg7(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg8(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg9(), ETipo.X, 40, 0 ) );
					line.append( StringFunctions.replicate( " ", 22 ) );
				}

				line.append( (char) 13 );
				line.append( (char) 10 );

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento S.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@ Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					super.parseLineReg3( line );
					setTipoImpressao( line.substring( 17, 18 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 18 ).trim() ) : 0 );

					if ( getTipoImpressao() == 1 || getTipoImpressao() == 2 ) {

						setLinhaImp( line.substring( 18, 20 ).trim().length() > 0 ? Integer.parseInt( line.substring( 18, 20 ).trim() ) : 0 );
						setMsgImp( line.substring( 20, 160 ) );
						setTipoChar( line.substring( 160, 162 ).trim().length() > 0 ? Integer.parseInt( line.substring( 160, 162 ).trim() ) : 0 );
					}
					else if ( getTipoImpressao() == 3 ) {

						setMsg5( line.substring( 18, 58 ) );
						setMsg6( line.substring( 58, 98 ) );
						setMsg7( line.substring( 98, 138 ) );
						setMsg8( line.substring( 138, 178 ) );
						setMsg9( line.substring( 178, 218 ) );
					}
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento S.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Reg3T extends Reg3 {

		private String agencia;

		private String digAgencia;

		private String conta;

		private String digConta;

		private String digAgConta;

		private String identTitBanco;

		private int carteira;

		private String docCob;

		private Date dataVencTit;

		private BigDecimal vlrTitulo;

		private String codBancoCob;

		private String agenciaCob;

		private String digAgenciaCob;

		private String identTitEmp;

		private int codMoeda;

		private int tipoInscCli;

		private String cpfCnpjCli;

		private String razCli;

		private String contratoCred;

		private BigDecimal vlrTarifa;

		private String codRejeicoes;
		
		private int diasProtesto;

		public Reg3T() {

			super( 'T' );
		}

		public Reg3T( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public String getAgencia() {

			return agencia;
		}

		public void setAgencia( final String agencia ) {

			this.agencia = agencia;
		}

		public String getAgenciaCob() {

			return agenciaCob;
		}

		public void setAgenciaCob( final String agenciaCob ) {

			this.agenciaCob = agenciaCob;
		}

		public int getCarteira() {

			return carteira;
		}

		/**
		 * Carteira.<br>
		 * 1 - Cobrança simpres.<br>
		 * 2 - Cobrança vinculada.<br>
		 * 3 - Cobrança caucionada.<br>
		 * 4 - Cobrança descontada.<br>
		 * 7 - Cobrança direta especial / carteira 17.
		 */
		public void setCarteira( final int carteira ) {

			this.carteira = carteira;
		}

		public String getCodBancoCob() {

			return codBancoCob;
		}

		/**
		 * Agencia cobradora/recebedora.<br>
		 */
		public void setCodBancoCob( final String codBanco ) {

			this.codBancoCob = codBanco;
		}

		public int getCodMoeda() {

			return codMoeda;
		}

		/**
		 * Código da moeda.<br>
		 * 01 - Reservado para uso futuro.<br>
		 * 02 - Dolar americano comercial/venda.<br>
		 * 03 - Dolar americano turismo/venda.<br>
		 * 04 - ITRD.<br>
		 * 05 - IDTR.<br>
		 * 06 - UFIR diária.<br>
		 * 07 - UFIR mensal.<br>
		 * 08 - FAJ-TR.<br>
		 * 09 - Real.<br>
		 */
		public void setCodMoeda( final int codMoeda ) {

			this.codMoeda = codMoeda;
		}

		public String getCodRejeicoes() {

			return codRejeicoes;
		}

		public void setCodRejeicoes( final String codRejeicoes ) {

			this.codRejeicoes = codRejeicoes;
		}

		public String getConta() {

			return conta;
		}

		public void setConta( final String conta ) {

			this.conta = conta;
		}

		public String getContratoCred() {

			return contratoCred;
		}

		public void setContratoCred( final String contratoCred ) {

			this.contratoCred = contratoCred;
		}

		public String getCpfCnpjCli() {

			return cpfCnpjCli;
		}

		public void setCpfCnpjCli( final String cpfCnpjCli ) {

			this.cpfCnpjCli = cpfCnpjCli;
		}

		public Date getDataVencTit() {

			return dataVencTit;
		}

		public void setDataVencTit( final Date dataVencTit ) {

			this.dataVencTit = dataVencTit;
		}

		public String getDigAgConta() {

			return digAgConta;
		}

		public void setDigAgConta( final String digAgConta ) {

			this.digAgConta = digAgConta;
		}

		public String getDigAgencia() {

			return digAgencia;
		}

		public void setDigAgencia( final String digAgencia ) {

			this.digAgencia = digAgencia;
		}

		public String getDigAgenciaCob() {

			return digAgenciaCob;
		}

		public void setDigAgenciaCob( final String digAgenciaCob ) {

			this.digAgenciaCob = digAgenciaCob;
		}

		public String getDigConta() {

			return digConta;
		}

		public void setDigConta( final String digConta ) {

			this.digConta = digConta;
		}

		public String getDocCob() {

			return docCob;
		}

		public void setDocCob( final String docCob ) {

			this.docCob = docCob;
		}

		public String getIdentTitBanco() {

			return identTitBanco;
		}

		public void setIdentTitBanco( final String identTitBanco ) {

			this.identTitBanco = identTitBanco;
		}

		public String getIdentTitEmp() {

			return identTitEmp;
		}

		public void setIdentTitEmp( final String identTitEmp ) {

			this.identTitEmp = identTitEmp;
		}

		public String getRazCli() {

			return razCli;
		}

		public void setRazCli( final String razCli ) {

			this.razCli = razCli;
		}

		public int getTipoInscCli() {

			return tipoInscCli;
		}

		public void setTipoInscCli( final int tipoInscCli ) {

			this.tipoInscCli = tipoInscCli;
		}

		public BigDecimal getVlrTarifa() {

			return vlrTarifa;
		}

		public void setVlrTarifa( final BigDecimal vlrTarifa ) {

			this.vlrTarifa = vlrTarifa;
		}

		public BigDecimal getVlrTitulo() {

			return vlrTitulo;
		}

		public void setVlrTitulo( final BigDecimal vlrTitulo ) {

			this.vlrTitulo = vlrTitulo;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#getLine()
		 */
		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3( padraocnab ) );
				line.append( format( getAgencia(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgencia(), ETipo.$9, 1, 0 ) );
				line.append( format( getConta(), ETipo.$9, 12, 0 ) );
				line.append( format( getDigConta(), ETipo.$9, 1, 0 ) );
				line.append( format( getDigAgConta(), ETipo.$9, 1, 0 ) );
				line.append( format( getIdentTitBanco(), ETipo.X, 20, 0 ) );
				line.append( format( getCarteira(), ETipo.$9, 1, 0 ) );
				line.append( format( getDocCob(), ETipo.X, 15, 0 ) );
				line.append( dateToString( getDataVencTit(), null ) );
				line.append( format( getVlrTitulo(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodBancoCob(), ETipo.$9, 3, 0 ) );
				line.append( format( getAgenciaCob(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgenciaCob(), ETipo.$9, 1, 0 ) );
				line.append( format( getIdentTitEmp(), ETipo.X, 25, 0 ) );
				line.append( format( getCodMoeda(), ETipo.$9, 2, 0 ) );
				line.append( format( getTipoInscCli(), ETipo.$9, 1, 0 ) );
				line.append( format( getCpfCnpjCli(), ETipo.$9, 15, 0 ) );
				line.append( format( getRazCli(), ETipo.X, 40, 0 ) );
				line.append( format( getContratoCred(), ETipo.$9, 10, 0 ) );
				line.append( format( getVlrTarifa(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodRejeicoes(), ETipo.$9, 10, 0 ) );
				line.append( StringFunctions.replicate( " ", 17 ) );
				line.append( (char) 13 );
				line.append( (char) 10 );

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento T.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@ Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					super.parseLineReg3( line );
					setAgencia( line.substring( 17, 22 ) );
					setDigAgencia( line.substring( 22, 23 ) );
					setConta( line.substring( 23, 35 ) );
					setDigConta( line.substring( 35, 36 ) );
					setDigAgConta( line.substring( 36, 37 ) );
					setIdentTitBanco( line.substring( 37, 57 ) );
					setCarteira( line.substring( 57, 58 ).trim().length() > 0 ? Integer.parseInt( line.substring( 57, 58 ).trim() ) : 0 );
					setDocCob( line.substring( 58, 73 ) );
					setDataVencTit( stringDDMMAAAAToDate( line.substring( 73, 81 ).trim() ) );
					setVlrTitulo( strToBigDecimal( line.substring( 81, 96 ) ) );
					setCodBancoCob( line.substring( 96, 99 ) );
					setAgenciaCob( line.substring( 99, 104 ) );
					setDigAgenciaCob( line.substring( 104, 105 ) );
					setIdentTitEmp( line.substring( 105, 130 ) );
					setCodMoeda( line.substring( 130, 132 ).trim().length() > 0 ? Integer.parseInt( line.substring( 130, 132 ).trim() ) : 0 );
					setTipoInscCli( line.substring( 132, 133 ).trim().length() > 0 ? Integer.parseInt( line.substring( 132, 133 ).trim() ) : 0 );
					setCpfCnpjCli( line.substring( 133, 148 ) );
					setRazCli( line.substring( 148, 188 ) );
					setContratoCred( line.substring( 188, 198 ) );
					setVlrTarifa( strToBigDecimal( line.substring( 198, 213 ) ) );
					setCodRejeicoes( line.substring( 213, 223 ) );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
				throw new ExceptionCnab( "CNAB registro 3 segmento T.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Reg3U extends Reg3 {

		private BigDecimal vlrJurosMulta;

		private BigDecimal vlrDesc;

		private BigDecimal vrlAbatCancel;

		private BigDecimal vlrIOF;

		private BigDecimal vlrPago;

		private BigDecimal vlrLiqCred;

		private BigDecimal vlrOutrasDesp;

		private BigDecimal vlrOutrosCred;

		private BigDecimal vlrOcorrSac;

		private Date dataOcorr;

		private Date dataEfetvCred;

		private String codOcorrSac;

		private Date dataOcorrSac;

		private String compOcorrSac;

		private String codBancoCompens;

		private String nossoNrCompens;

		public Reg3U() {

			super( 'U' );
		}

		public Reg3U( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public String getCodBancoCompens() {

			return codBancoCompens;
		}

		public void setCodBancoCompens( final String codBancoCompens ) {

			this.codBancoCompens = codBancoCompens;
		}

		public String getCodOcorrSac() {

			return codOcorrSac;
		}

		public void setCodOcorrSac( final String codOcorrSac ) {

			this.codOcorrSac = codOcorrSac;
		}

		public String getCompOcorrSac() {

			return compOcorrSac;
		}

		public void setCompOcorrSac( final String compOcorrSac ) {

			this.compOcorrSac = compOcorrSac;
		}

		public Date getDataEfetvCred() {

			return dataEfetvCred;
		}

		public void setDataEfetvCred( final Date dataEfetvCred ) {

			this.dataEfetvCred = dataEfetvCred;
		}

		public Date getDataOcorr() {

			return dataOcorr;
		}

		public void setDataOcorr( final Date dataOcorr ) {

			this.dataOcorr = dataOcorr;
		}

		public Date getDataOcorrSac() {

			return dataOcorrSac;
		}

		public void setDataOcorrSac( final Date dataOcorrSac ) {

			this.dataOcorrSac = dataOcorrSac;
		}

		public String getNossoNrCompens() {

			return nossoNrCompens;
		}

		/**
		 * Somente para troca de arquivo entre bancos.<br>
		 */
		public void setNossoNrCompens( final String nossoNrCompens ) {

			this.nossoNrCompens = nossoNrCompens;
		}

		public BigDecimal getVlrDesc() {

			return vlrDesc;
		}

		public void setVlrDesc( final BigDecimal vlrDesc ) {

			this.vlrDesc = vlrDesc;
		}

		public BigDecimal getVlrIOF() {

			return vlrIOF;
		}

		public void setVlrIOF( final BigDecimal vlrIOF ) {

			this.vlrIOF = vlrIOF;
		}

		public BigDecimal getVlrJurosMulta() {

			return vlrJurosMulta;
		}

		public void setVlrJurosMulta( final BigDecimal vlrJurosMulta ) {

			this.vlrJurosMulta = vlrJurosMulta;
		}

		public BigDecimal getVlrLiqCred() {

			return vlrLiqCred;
		}

		public void setVlrLiqCred( final BigDecimal vlrLiqCred ) {

			this.vlrLiqCred = vlrLiqCred;
		}

		public BigDecimal getVlrOcorrSac() {

			return vlrOcorrSac;
		}

		public void setVlrOcorrSac( final BigDecimal vlrOcorrSac ) {

			this.vlrOcorrSac = vlrOcorrSac;
		}

		public BigDecimal getVlrOutrasDesp() {

			return vlrOutrasDesp;
		}

		public void setVlrOutrasDesp( final BigDecimal vlrOutrasDesp ) {

			this.vlrOutrasDesp = vlrOutrasDesp;
		}

		public BigDecimal getVlrOutrosCred() {

			return vlrOutrosCred;
		}

		public void setVlrOutrosCred( final BigDecimal vlrOutrosCred ) {

			this.vlrOutrosCred = vlrOutrosCred;
		}

		public BigDecimal getVlrPago() {

			return vlrPago;
		}

		public void setVlrPago( final BigDecimal vlrPago ) {

			this.vlrPago = vlrPago;
		}

		public BigDecimal getVrlAbatCancel() {

			return vrlAbatCancel;
		}

		public void setVlrAbatCancel( final BigDecimal vrlAbatCancel ) {

			this.vrlAbatCancel = vrlAbatCancel;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#getLine()
		 */
		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3( padraocnab ) );
				line.append( format( getVlrJurosMulta(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrDesc(), ETipo.$9, 15, 2 ) );
				line.append( format( getVrlAbatCancel(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrIOF(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrPago(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrLiqCred(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrOutrasDesp(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrOutrosCred(), ETipo.$9, 15, 2 ) );
				line.append( dateToString( getDataOcorr(), null ) );
				line.append( dateToString( getDataEfetvCred(), null ) );
				line.append( format( getCodOcorrSac(), ETipo.X, 4, 0 ) );
				line.append( dateToString( getDataOcorrSac(), null ) );
				line.append( format( getVlrOcorrSac(), ETipo.$9, 15, 2 ) );
				line.append( format( getCompOcorrSac(), ETipo.X, 30, 0 ) );
				line.append( format( getCodBancoCompens(), ETipo.$9, 3, 0 ) );
				line.append( format( getNossoNrCompens(), ETipo.$9, 20, 0 ) );
				line.append( StringFunctions.replicate( " ", 7 ) );
				line.append( (char) 13 );
				line.append( (char) 10 );

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento T.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@ Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					super.parseLineReg3( line );
					setVlrJurosMulta( strToBigDecimal( line.substring( 17, 32 ) ) );
					setVlrDesc( strToBigDecimal( line.substring( 32, 47 ) ) );
					setVlrAbatCancel( strToBigDecimal( line.substring( 47, 62 ) ) );
					setVlrIOF( strToBigDecimal( line.substring( 62, 77 ) ) );
					setVlrPago( strToBigDecimal( line.substring( 77, 92 ) ) );
					setVlrLiqCred( strToBigDecimal( line.substring( 92, 107 ) ) );
					setVlrOutrasDesp( strToBigDecimal( line.substring( 107, 122 ) ) );
					setVlrOutrosCred( strToBigDecimal( line.substring( 122, 137 ) ) );
					setDataOcorr( stringDDMMAAAAToDate( line.substring( 137, 145 ).trim() ) );
					setDataEfetvCred( stringDDMMAAAAToDate( line.substring( 145, 153 ).trim() ) );
					setCodOcorrSac( line.substring( 153, 157 ) );
					setDataOcorrSac( stringDDMMAAAAToDate( line.substring( 157, 165 ).trim() ) );
					setVlrOcorrSac( strToBigDecimal( line.substring( 165, 180 ) ) );
					setCompOcorrSac( line.substring( 180, 210 ) );
					setCodBancoCompens( line.substring( 210, 213 ) );
					setNossoNrCompens( line.substring( 213, 233 ) );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
				throw new ExceptionCnab( "CNAB registro 3 segmento U.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Reg5 extends Reg {

		private String codBanco;

		private int loteServico;

		private int registroTrailer;

		private int qtdRegistros;

		private int qtdSimples;

		private BigDecimal vlrSimples;

		private int qtdVinculado;

		private BigDecimal vlrVinculado;

		private int qtdCalculado;

		private BigDecimal vlrCalculado;

		private int qtdDescontado;

		private BigDecimal vlrDescontado;

		private String avisoLanca;

		public Reg5() {

			setRegistroTrailer( 5 );
		}

		public Reg5( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}

		public String getAvisoLanca() {

			return avisoLanca;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setAvisoLanca( String avisoLanca ) {

			this.avisoLanca = avisoLanca;
		}

		public String getCodBanco() {

			return codBanco;
		}

		public void setCodBanco( String codBanco ) {

			this.codBanco = codBanco;
		}

		public int getLoteServico() {

			return loteServico;
		}

		/**
		 * Indentifica um Lote de Serviço.<br>
		 * Sequencial e nmão deve ser repetido dentro do arquivo.<br>
		 * As numerações 0000 e 9999 <br>
		 * são exclusivas para o Header e para o Trailer do arquivo respectivamente.<br>
		 */
		public void setLoteServico( int loteServico ) {

			this.loteServico = loteServico;
		}

		public int getQtdCalculado() {

			return qtdCalculado;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setQtdCalculado( int qtdCalculado ) {

			this.qtdCalculado = qtdCalculado;
		}

		public int getQtdDescontado() {

			return qtdDescontado;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setQtdDescontado( int qtdDescontado ) {

			this.qtdDescontado = qtdDescontado;
		}

		public int getQtdRegistros() {

			return qtdRegistros;
		}

		/**
		 * Quantidade de registros no lote, incluindo o Header e o Traler.<br>
		 */
		public void setQtdRegistros( int qtdRegistros ) {

			this.qtdRegistros = qtdRegistros;
		}

		public int getQtdSimples() {

			return qtdSimples;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setQtdSimples( int qtdSimples ) {

			this.qtdSimples = qtdSimples;
		}

		public int getQtdVinculado() {

			return qtdVinculado;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setQtdVinculado( int qtdVinculado ) {

			this.qtdVinculado = qtdVinculado;
		}

		public int getRegistroTrailer() {

			return registroTrailer;
		}

		private void setRegistroTrailer( int registroTraler ) {

			this.registroTrailer = registroTraler;
		}

		public BigDecimal getVlrCalculado() {

			return vlrCalculado;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setVlrCalculado( BigDecimal vlrCalculado ) {

			this.vlrCalculado = vlrCalculado;
		}

		public BigDecimal getVlrDescontado() {

			return vlrDescontado;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setVlrDescontado( BigDecimal vlrDescontado ) {

			this.vlrDescontado = vlrDescontado;
		}

		public BigDecimal getVlrSimples() {

			return vlrSimples;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setVlrSimples( BigDecimal vlrSimples ) {

			this.vlrSimples = vlrSimples;
		}

		public BigDecimal getVlrVinculado() {

			return vlrVinculado;
		}

		/**
		 * Somente será utilizado para informação do arquivo de retorno.<br>
		 */
		public void setVlrVinculado( BigDecimal vlrVinculado ) {

			this.vlrVinculado = vlrVinculado;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#getLine()
		 */
		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {
				line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
				line.append( format( getLoteServico(), ETipo.$9, 4, 0 ) );
				line.append( format( getRegistroTrailer(), ETipo.$9, 1, 0 ) );
				line.append( StringFunctions.replicate( " ", 9 ) );
				line.append( format( getQtdRegistros(), ETipo.$9, 6, 0 ) );
				line.append( format( getQtdSimples(), ETipo.$9, 6, 0 ) );
				line.append( format( getVlrSimples(), ETipo.$9, 17, 2 ) );
				line.append( format( getQtdVinculado(), ETipo.$9, 6, 0 ) );
				line.append( format( getVlrVinculado(), ETipo.$9, 17, 2 ) );
				line.append( format( getQtdCalculado(), ETipo.$9, 6, 0 ) );
				line.append( format( getVlrCalculado(), ETipo.$9, 17, 2 ) );
				line.append( format( getQtdDescontado(), ETipo.$9, 6, 0 ) );
				line.append( format( getVlrDescontado(), ETipo.$9, 17, 2 ) );
				line.append( format( getAvisoLanca(), ETipo.X, 8, 0 ) );
				line.append( StringFunctions.replicate( " ", 117 ) );
				line.append( (char) 13 );
				line.append( (char) 10 );

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 5.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@ Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					setCodBanco( line.substring( 0, 3 ) );
					setLoteServico( line.substring( 3, 7 ).trim().length() > 0 ? Integer.parseInt( line.substring( 3, 7 ).trim() ) : 0 );
					setRegistroTrailer( line.substring( 7, 8 ).trim().length() > 0 ? Integer.parseInt( line.substring( 7, 8 ).trim() ) : 0 );
					setQtdRegistros( line.substring( 17, 23 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 23 ).trim() ) : 0 );
					setQtdSimples( line.substring( 23, 29 ).trim().length() > 0 ? Integer.parseInt( line.substring( 23, 29 ).trim() ) : 0 );
					setVlrSimples( strToBigDecimal( line.substring( 29, 46 ) ) );
					setQtdVinculado( line.substring( 46, 52 ).trim().length() > 0 ? Integer.parseInt( line.substring( 46, 52 ).trim() ) : 0 );
					setVlrVinculado( strToBigDecimal( line.substring( 52, 69 ) ) );
					setQtdCalculado( line.substring( 69, 75 ).trim().length() > 0 ? Integer.parseInt( line.substring( 69, 75 ).trim() ) : 0 );
					setVlrCalculado( strToBigDecimal( line.substring( 75, 92 ) ) );
					setQtdDescontado( line.substring( 92, 98 ).trim().length() > 0 ? Integer.parseInt( line.substring( 92, 98 ).trim() ) : 0 );
					setVlrDescontado( strToBigDecimal( line.substring( 98, 115 ) ) );
					setAvisoLanca( line.substring( 115, 123 ) );
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 5.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class RegTrailer extends Reg {

		private String codBanco;

		private String loteServico;

		private String registroTrailer;

		private int qtdLotes;

		private int qtdRegistros;

		private int qtdConsilacoes;

		private int seqregistro;

		public RegTrailer() {

			setLoteServico( "9999" );
			setRegistroTrailer( "9" );
		}

		public int getSeqregistro() {

			return seqregistro;
		}

		public void setSeqregistro( int seqregistro ) {

			this.seqregistro = seqregistro;
		}

		public String getCodBanco() {

			return codBanco;
		}

		public void setCodBanco( final String codBanco ) {

			this.codBanco = codBanco;
		}

		public String getLoteServico() {

			return loteServico;
		}

		private void setLoteServico( final String loteServico ) {

			this.loteServico = loteServico;
		}

		public int getQtdConsilacoes() {

			return qtdConsilacoes;
		}

		public void setQtdConsilacoes( final int qtdConsilacoes ) {

			this.qtdConsilacoes = qtdConsilacoes;
		}

		public int getQtdLotes() {

			return qtdLotes;
		}

		public void setQtdLotes( final int qtdLotes ) {

			this.qtdLotes = qtdLotes;
		}

		public int getQtdRegistros() {

			return qtdRegistros;
		}

		public void setQtdRegistros( final int qtdRegistros ) {

			this.qtdRegistros = qtdRegistros;
		}

		public String getRegistroTrailer() {

			return registroTrailer;
		}

		private void setRegistroTrailer( final String regTrailer ) {

			this.registroTrailer = regTrailer;
		}

		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {
				if ( padraocnab.equals( CNAB_240 ) ) {

					line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
					line.append( format( getLoteServico(), ETipo.$9, 4, 0 ) );
					line.append( format( getRegistroTrailer(), ETipo.$9, 1, 0 ) );
					line.append( StringFunctions.replicate( " ", 9 ) );
					line.append( format( getQtdLotes(), ETipo.$9, 6, 0 ) );
					line.append( format( getQtdRegistros(), ETipo.$9, 6, 0 ) );
					line.append( format( getQtdConsilacoes(), ETipo.$9, 6, 0 ) );
					line.append( StringFunctions.replicate( " ", 205 ) );
					line.append( (char) 13 );
					line.append( (char) 10 );
				}
				else if ( padraocnab.equals( CNAB_400 ) ) {
					line.append( StringFunctions.replicate( "9", 1 ) ); // Posição 001 a 001 - Identificação do registro
					line.append( StringFunctions.replicate( " ", 393 ) ); // Posição 002 a 394 - Branco
					line.append( format( seqregistro, ETipo.$9, 6, 0 ) ); // Posição 395 a 400 - Nro Sequancial do ultimo registro
				}

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro trailer.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@ Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					setCodBanco( line.substring( 0, 3 ) );
					setLoteServico( line.substring( 3, 7 ) );
					setRegistroTrailer( line.substring( 7, 8 ) );
					setQtdRegistros( line.substring( 17, 23 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 23 ).trim() ) : 0 );
					setQtdLotes( line.substring( 23, 29 ).trim().length() > 0 ? Integer.parseInt( line.substring( 23, 29 ).trim() ) : 0 );
					setQtdRegistros( line.substring( 29, 35 ).trim().length() > 0 ? Integer.parseInt( line.substring( 29, 35 ).trim() ) : 0 );
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro trailer.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	public class Receber {

		private int codrec;

		private int nrparcrec;

		private String docrec;

		private BigDecimal valor;

		private BigDecimal valorApagar;

		private Date emissao;

		private Date vencimento;

		private String conta;

		private String planejamento;

		private String centrocusto;

		private String code;

		private int codcliente;

		private String razcliente;
		
		private String status;

		public Receber() {

		}

		public Receber( int arg0, int arg1 ) {

			setCodrec( arg0 );
			setNrparcrec( arg1 );
		}

		
		public String getStatus() {
		
			return status;
		}

		
		public void setStatus( String status ) {
		
			this.status = status;
		}

		public Receber( String arg ) {

			this.code = arg;
			decode();
		}

		public int getCodrec() {

			return codrec;
		}

		public void setCodrec( int codrec ) {

			this.codrec = codrec;
		}

		public int getNrparcrec() {

			return nrparcrec;
		}

		public void setNrparcrec( int nrparcrec ) {

			this.nrparcrec = nrparcrec;
		}

		public String getConta() {

			return conta;
		}

		public void setConta( String conta ) {

			this.conta = conta;
		}

		public String getDocrec() {

			return docrec;
		}

		public void setDocrec( String docrec ) {

			this.docrec = docrec;
		}

		public Date getEmissao() {

			return emissao;
		}

		public void setEmissao( Date emissao ) {

			this.emissao = emissao;
		}

		public String getPlanejamento() {

			return planejamento;
		}

		public void setPlanejamento( String planejamento ) {

			this.planejamento = planejamento;
		}

		public String getCentrocusto() {

			return centrocusto;
		}

		public void setCentrocusto( String centrocusto ) {

			this.centrocusto = centrocusto;
		}

		public BigDecimal getValor() {

			return valor;
		}

		public void setValor( BigDecimal valor ) {

			this.valor = valor;
		}

		public BigDecimal getValorApagar() {

			return valorApagar;
		}

		public void setValorApagar( BigDecimal valorApagar ) {

			this.valorApagar = valorApagar;
		}

		public Date getVencimento() {

			return vencimento;
		}

		public void setVencimento( Date vencimento ) {

			this.vencimento = vencimento;
		}

		public int getCodcliente() {

			return codcliente;
		}

		public void setCodcliente( int codcliente ) {

			this.codcliente = codcliente;
		}

		public String getRazcliente() {

			return razcliente;
		}

		public void setRazcliente( String razcliente ) {

			this.razcliente = razcliente;
		}

		public String encode() {

			this.code = StringFunctions.strZero( String.valueOf( getCodrec() ), 10 ) + StringFunctions.strZero( String.valueOf( getNrparcrec() ), 3 );

			return this.code;
		}

		public void decode() {

			if ( code != null && code.trim().length() > 10 ) {

				setCodrec( Integer.parseInt( code.substring( 0, 10 ) ) );
				setNrparcrec( Integer.parseInt( code.substring( 10 ) ) );
			}
		}

		public String toString() {

			return encode();
		}
	}

	/**
	 * Converte java.util.Date para um java.lang.String em formato DDMMAAAA.
	 * 
	 * @param arg
	 *            java.util.Date.
	 * @return java.lang.String em formato DDMMAAAA.
	 * @throws Exception
	 */
	public static String dateToString( final Date arg, String formato ) throws ExceptionCnab {

		String retorno = null;

		try {
			if ( arg != null ) {

				int[] args = Funcoes.decodeDate( arg );
				retorno = StringFunctions.strZero( String.valueOf( args[ 2 ] ), 2 ) + StringFunctions.strZero( String.valueOf( args[ 1 ] ), 2 );

				if ( "DDMMAAAA".equals( formato ) || formato == null ) {
					retorno = retorno + StringFunctions.strZero( String.valueOf( args[ 0 ] ), 4 );
				}
				else if ( "DDMMAA".equals( formato ) ) {
					retorno = retorno + String.valueOf( args[ 0 ] ).substring( 2 );
				}
			}
			else {
				retorno = "00000000";
			}
		} catch ( RuntimeException e ) {
			throw new ExceptionCnab( "Erro na função dateToString.\n" + e.getMessage() );
		}

		return retorno;
	}

	/**
	 * Converte um java.lang.String em formato AAAAMMDD para java.util.Date
	 * 
	 * @param arg
	 *            java.lang.String.
	 * @return java.util.Date.
	 * @throws Exception
	 */
	public static Date stringAAAAMMDDToDate( final String arg ) throws ExceptionCnab {

		Date retorno = null;

		try {
			if ( arg != null && arg.trim().length() > 7 ) {

				int dia = Integer.parseInt( arg.substring( 6 ) );
				int mes = Integer.parseInt( arg.substring( 4, 6 ) );
				int ano = Integer.parseInt( arg.substring( 0, 4 ) );

				if ( dia > 0 && mes > 0 && ano > 0 ) {
					retorno = Funcoes.encodeDate( ano, mes, dia );
				}
			}
		} catch ( NumberFormatException e ) {
			throw new ExceptionCnab( "Erro na função stringToDate.\n" + e.getMessage() );
		}

		return retorno;
	}

	/**
	 * Converte um java.lang.String em formato AAAAMMDD para java.util.Date
	 * 
	 * @param arg
	 *            java.lang.String.
	 * @return java.util.Date.
	 * @throws Exception
	 */
	public static Date stringDDMMAAAAToDate( final String arg ) throws ExceptionCnab {

		Date retorno = null;

		try {
			if ( arg != null && arg.trim().length() > 7 ) {

				int dia = Integer.parseInt( arg.substring( 0, 2 ) );
				int mes = Integer.parseInt( arg.substring( 2, 4 ) );
				int ano = Integer.parseInt( arg.substring( 4 ) );

				if ( dia > 0 && mes > 0 && ano > 0 ) {
					retorno = Funcoes.encodeDate( ano, mes, dia );
				}
			}
		} catch ( NumberFormatException e ) {
			throw new ExceptionCnab( "Erro na função stringToDate.\n" + e.getMessage() );
		}

		return retorno;
	}

	public static Date stringDDMMAAToDate( final String arg ) throws ExceptionCnab {

		Date retorno = null;
		Calendar cl = new GregorianCalendar();

		try {

			cl = Calendar.getInstance();

			if ( arg != null && arg.trim().length() > 5 ) {

				int dia = Integer.parseInt( arg.substring( 0, 2 ) );
				int mes = Integer.parseInt( arg.substring( 2, 4 ) );

				String anoatu = cl.get( Calendar.YEAR ) + "";

				int ano = Integer.parseInt( anoatu.substring( 0, 2 ) + arg.substring( 4 ) );

				if ( dia > 0 && mes > 0 && ano > 0 ) {
					retorno = Funcoes.encodeDate( ano, mes, dia );
				}
			}
		} catch ( NumberFormatException e ) {
			throw new ExceptionCnab( "Erro na função stringToDate.\n" + e.getMessage() );
		}

		return retorno;
	}

	/**
	 * Converte para java.math.BigDecimal um String de inteiros sem ponto ou virgula.
	 * 
	 * @param arg
	 *            String de inteiros sem ponto ou virgula.
	 * @return java.math.BigDecimal com escala de 2.
	 * @throws NumberFormatException
	 */
	public static BigDecimal strToBigDecimal( final String arg ) throws ExceptionCnab {

		BigDecimal bdReturn = null;

		try {
			if ( arg != null ) {
				StringBuilder value = new StringBuilder();
				char chars[] = arg.trim().toCharArray();
				if ( chars.length >= 3 ) {
					int indexDecimal = ( chars.length - 1 ) - 2;
					for ( int i = 0; i < chars.length; i++ ) {
						value.append( chars[ i ] );
						if ( i == indexDecimal ) {
							value.append( '.' );
						}
					}
					bdReturn = new BigDecimal( value.toString() );
				}
			}
		} catch ( RuntimeException e ) {
			throw new ExceptionCnab( "Erro na função strToBigDecimal.\n" + e.getMessage() );
		}

		return bdReturn;
	}

	// yyy

	public class RegT400 extends Reg {

		private String codBanco;

		private int loteServico;

		private int registroHeader;

		private String tipoOperacao;

		private String tipoServico;

		private String formaLancamento;

		private String versaoLayout;

		private int tipoInscEmp;

		private String cpfCnpjEmp;

		private String codConvBanco;

		private String agencia;

		private String digAgencia;

		private String conta;

		private String digConta;

		private String digAgConta;

		private String razEmp;

		private String msg1;

		private String msg2;

		private int nrRemRet;

		private Date dataRemRet;

		private Date dataCred;

		private int codCarteira;

		private String identTitulo;
		
		private String variacaocarteira;

		private String identTitEmp;

		private BigDecimal vlrPercMulta;

		private String digNossoNumero;

		private int codMovimento;

		private String docCobranca;

		private Date dtVencTitulo;

		private BigDecimal vlrTitulo;

		private int especieTit;

		private Date dtEmitTit;

		private int codJuros;

		private BigDecimal vlrJurosTaxa;

		private Date dtDesc;

		private BigDecimal vlrDesc;

		private BigDecimal vlrIOF;

		private BigDecimal vlrAbatimento;

		private int tipoInscCli;

		private String cpfCnpjCli;

		private String razCli;

		private String endCli;

		private String bairCli;

		private String cepCli;

		private int seqregistro;

		private String codRejeicoes;

		private BigDecimal vlrJurosMulta;

		private BigDecimal vrlAbatCancel;

		private BigDecimal vlrPago;

		private BigDecimal vlrLiqCred;

		private BigDecimal vlrOutrasDesp;

		private BigDecimal vlrOutrosCred;

		private BigDecimal vlrOcorrSac;

		private int identEmitBol = 2;
		
		private int codProtesto = 0;
		
		private int diasProtesto = 0;
		
		public int getCodProtesto() {

			return codProtesto;
		}

		public BigDecimal calcVlrJuros(int codjur, BigDecimal vlrapagar, BigDecimal perc) {
			/**
			 * Código do juros de mora.<br>
			 * 1 - Valor por dia.<br>
			 * 2 - Taxa mensal.<br>
			 * 3 - Isento.<br>
			 */
			BigDecimal retorno = new BigDecimal(0);
			if (vlrapagar!=null && perc!=null && 
					perc.doubleValue()>0 && vlrapagar.doubleValue()>0 && 
					(codjur==1 || codjur==2) ) {
				if (codjur==1) {
				    retorno = vlrapagar.multiply( perc ).divide( new BigDecimal(100) );
				} else if (codjur==2) {
				    retorno = vlrapagar.multiply( perc ).divide( new BigDecimal(100) ).divide( new BigDecimal(30) );
				}
			}
			return retorno;
		}
		
		/**
		 * Código para protesto.<br>
		 * 1 - Dias corridos.<br>
		 * 2 - Dias utéis.<br>
		 * 3 - Não protestar.<br>
		 */
		public void setCodProtesto( final int codProtesto ) {

			this.codProtesto = codProtesto;
		}

		public int getDiasProtesto() {

			return diasProtesto;
		}

		/**
		 * Número de dias para protesto.<br>
		 */
		public void setDiasProtesto( final int diasProtesto ) {

			this.diasProtesto = diasProtesto;
		}
		
		public String getCpfCnpjCli() {

			return cpfCnpjCli;
		}

		public String getIdentTitEmp() {

			return identTitEmp;
		}

		public void setIdentTitEmp( String identTitEmp ) {

			this.identTitEmp = identTitEmp;
		}

		public BigDecimal getVlrJurosMulta() {

			return vlrJurosMulta;
		}

		public void setVlrJurosMulta( BigDecimal vlrJurosMulta ) {

			this.vlrJurosMulta = vlrJurosMulta;
		}

		public BigDecimal getVrlAbatCancel() {

			return vrlAbatCancel;
		}

		public void setVrlAbatCancel( BigDecimal vrlAbatCancel ) {

			this.vrlAbatCancel = vrlAbatCancel;
		}

		public BigDecimal getVlrPago() {

			return vlrPago;
		}

		public void setVlrPago( BigDecimal vlrPago ) {

			this.vlrPago = vlrPago;
		}

		public BigDecimal getVlrLiqCred() {

			return vlrLiqCred;
		}

		public void setVlrLiqCred( BigDecimal vlrLiqCred ) {

			this.vlrLiqCred = vlrLiqCred;
		}

		public BigDecimal getVlrOutrasDesp() {

			return vlrOutrasDesp;
		}

		public void setVlrOutrasDesp( BigDecimal vlrOutrasDesp ) {

			this.vlrOutrasDesp = vlrOutrasDesp;
		}

		public BigDecimal getVlrOutrosCred() {

			return vlrOutrosCred;
		}

		public void setVlrOutrosCred( BigDecimal vlrOutrosCred ) {

			this.vlrOutrosCred = vlrOutrosCred;
		}

		public BigDecimal getVlrOcorrSac() {

			return vlrOcorrSac;
		}

		public void setVlrOcorrSac( BigDecimal vlrOcorrSac ) {

			this.vlrOcorrSac = vlrOcorrSac;
		}

		public String getCodRejeicoes() {

			return codRejeicoes;
		}

		public void setCodRejeicoes( String codRejeicoes ) {

			this.codRejeicoes = codRejeicoes;
		}

		public void setCpfCnpjCli( String cpfCnpjCli ) {

			this.cpfCnpjCli = cpfCnpjCli;
		}

		public int getSeqregistro() {

			return seqregistro;
		}

		public void setSeqregistro( int seqregistro ) {

			this.seqregistro = seqregistro;
		}

		public String getRazCli() {

			return razCli;
		}

		public void setRazCli( String razCli ) {

			this.razCli = razCli;
		}

		public String getEndCli() {

			return endCli;
		}

		public void setEndCli( String endCli ) {

			this.endCli = endCli;
		}

		public String getBairCli() {

			return bairCli;
		}

		public void setBairCli( String bairCli ) {

			this.bairCli = bairCli;
		}

		public String getCepCli() {

			return cepCli;
		}

		public void setCepCli( String cepCli ) {

			this.cepCli = cepCli;
		}

		public String getCidCli() {

			return cidCli;
		}

		public void setCidCli( String cidCli ) {

			this.cidCli = cidCli;
		}

		public String getUfCli() {

			return ufCli;
		}

		public void setUfCli( String ufCli ) {

			this.ufCli = ufCli;
		}

		public int getTipoInscAva() {

			return tipoInscAva;
		}

		public void setTipoInscAva( int tipoInscAva ) {

			this.tipoInscAva = tipoInscAva;
		}

		public String getCpfCnpjAva() {

			return cpfCnpjAva;
		}

		public void setCpfCnpjAva( String cpfCnpjAva ) {

			this.cpfCnpjAva = cpfCnpjAva;
		}

		public String getRazAva() {

			return razAva;
		}

		public void setRazAva( String razAva ) {

			this.razAva = razAva;
		}

		private String cidCli;

		private String ufCli;

		private int tipoInscAva;

		private String cpfCnpjAva;

		private String razAva;

		public BigDecimal getVlrJurosTaxa() {

			return vlrJurosTaxa;
		}

		public int getTipoInscCli() {

			return tipoInscCli;
		}

		public void setTipoInscCli( int tipoInscCli ) {

			this.tipoInscCli = tipoInscCli;
		}

		public void setVlrJurosTaxa( BigDecimal vlrJurosTaxa ) {

			this.vlrJurosTaxa = vlrJurosTaxa;
		}

		public BigDecimal getVlrAbatimento() {

			return vlrAbatimento;
		}

		public void setVlrAbatimento( BigDecimal vlrAbatimento ) {

			this.vlrAbatimento = vlrAbatimento;
		}

		public BigDecimal getVlrIOF() {

			return vlrIOF;
		}

		public void setVlrIOF( BigDecimal vlrIOF ) {

			this.vlrIOF = vlrIOF;
		}

		public Date getDtDesc() {

			return dtDesc;
		}

		public BigDecimal getVlrDesc() {

			return vlrDesc;
		}

		public void setVlrDesc( BigDecimal vlrDesc ) {

			this.vlrDesc = vlrDesc;
		}

		public void setDtDesc( Date dtDesc ) {

			this.dtDesc = dtDesc;
		}

		public int getCodMovimento() {

			return codMovimento;
		}

		public int getCodJuros() {

			return codJuros;
		}

		public void setCodJuros( int codJuros ) {

			this.codJuros = codJuros;
		}

		public BigDecimal getVlrTitulo() {

			return vlrTitulo;
		}

		public int getEspecieTit() {

			return especieTit;
		}

		public Date getDtEmitTit() {

			return dtEmitTit;
		}

		public void setDtEmitTit( Date dtEmitTit ) {

			this.dtEmitTit = dtEmitTit;
		}

		public void setEspecieTit( int especieTit ) {

			this.especieTit = especieTit;
		}

		public void setVlrTitulo( BigDecimal vlrTitulo ) {

			this.vlrTitulo = vlrTitulo;
		}

		public void setCodMovimento( int codMovimento ) {

			this.codMovimento = codMovimento;
		}

		public Date getDtVencTitulo() {

			return dtVencTitulo;
		}

		public void setDtVencTitulo( Date dtVencTitulo ) {

			this.dtVencTitulo = dtVencTitulo;
		}

		public String getDocCobranca() {

			return docCobranca;
		}

		public void setDocCobranca( String docCobranca ) {

			this.docCobranca = docCobranca;
		}

		public String getDigNossoNumero() {

			return digNossoNumero;
		}

		public void setDigNossoNumero( String digNossoNumero ) {

			this.digNossoNumero = digNossoNumero;
		}

		public RegT400() {

			setRegistroHeader( 1 );
			setTipoServico( "01" );
			setVersaoLayout( "020" );
		}

		public RegT400( final String line ) throws ExceptionCnab {

			this();
			parseLine( line );
		}
		
		public String getAgencia() {

			return agencia;
		}

		public void setAgencia( final String agencia ) {

			this.agencia = agencia;
		}

		public String getCpfCnpjEmp() {

			return cpfCnpjEmp;
		}

		/**
		 * Inscrição da empresa. Conforme o tipo da inscrição.<br>
		 * 
		 * @see org.freedom.modulos.fnc.business.component.CnabUtil.Reg1#setTipoInscEmp(int tipoInscEmp )
		 */
		public void setCpfCnpjEmp( final String cnpjEmp ) {

			this.cpfCnpjEmp = cnpjEmp;
		}

		public String getCodBanco() {

			return codBanco;
		}

		public void setCodBanco( final String codBanco ) {

			this.codBanco = codBanco;
		}

		public String getCodConvBanco() {

			return codConvBanco;
		}

		/**
		 * Indentifica a empresa no banco para determinados tipos de serviços.<br>
		 * Observar as regras de preenchimento abaixo no que se refere ao headre de serviço/lote:<br>
		 * "9999999994444CCVVV " / 20 bytes / , onde:<br>
		 * 999999999 - Código do convênio.<br>
		 * 4444 - Código do produto.<br>
		 * CC - Carteira de cobrança.<br>
		 * VVV - Variação da carteira de cobrança.<br>
		 */
		public void setCodConvBanco( final String codConvBanco ) {

			this.codConvBanco = codConvBanco;
		}

		public String getConta() {

			return conta;
		}

		public void setConta( final String conta ) {

			this.conta = conta;
		}

		public Date getDataCred() {

			return dataCred;
		}

		public void setDataCred( final Date dataCred ) {

			this.dataCred = dataCred;
		}

		public Date getDataRemRet() {

			return dataRemRet;
		}

		public void setDataRemRet( final Date dataRemRet ) {

			this.dataRemRet = dataRemRet;
		}

		public String getDigAgConta() {

			return digAgConta;
		}

		public void setDigAgConta( final String digAgeConta ) {

			this.digAgConta = digAgeConta;
		}

		public String getDigAgencia() {

			return digAgencia;
		}

		public void setDigAgencia( final String digAgencia ) {

			this.digAgencia = digAgencia;
		}

		public String getDigConta() {

			return digConta;
		}

		public void setDigConta( final String digConta ) {

			this.digConta = digConta;
		}

		public String getFormaLancamento() {

			return formaLancamento;
		}

		/**
		 * Este campo não será utilizado para combrança.<br>
		 */
		public void setFormaLancamento( final String formaLancamento ) {

			this.formaLancamento = formaLancamento;
		}

		public int getLoteServico() {

			return loteServico;
		}

		/**
		 * Indentifica um Lote de Serviço.<br>
		 * Sequencial e nmão deve ser repetido dentro do arquivo.<br>
		 * As numerações 0000 e 9999 <br>
		 * são exclusivas para o Header e para o Trailer do arquivo respectivamente.<br>
		 */
		public void setLoteServico( final int loteServico ) {

			this.loteServico = loteServico;
		}

		public String getMsg1() {

			return msg1;
		}

		/**
		 * As menssagens serão impressas em todos os bloquetos referentes 1 e 2 ao mesmo lote.<br>
		 * Estes campos não serão utilizados no arquivo de retorno.<br>
		 */
		public void setMsg1( final String msg1 ) {

			this.msg1 = msg1;
		}

		/**
		 * As menssagens serão impressas em todos os bloquetos referentes 1 e 2 ao mesmo lote.<br>
		 * Estes campos não serão utilizados no arquivo de retorno.<br>
		 */
		public String getMsg2() {

			return msg2;
		}

		public void setMsg2( final String msg2 ) {

			this.msg2 = msg2;
		}

		public String getRazEmp() {

			return razEmp;
		}

		public void setRazEmp( final String nomeEmp ) {

			this.razEmp = nomeEmp;
		}

		public int getNrRemRet() {

			return nrRemRet;
		}

		public void setNrRemRet( final int nrRemRet ) {

			this.nrRemRet = nrRemRet;
		}

		public int getRegistroHeader() {

			return registroHeader;
		}

		/**
		 * Indica o tipo de registro.<br>
		 */
		private void setRegistroHeader( final int registroHeader ) {

			this.registroHeader = registroHeader;
		}

		public int getTipoInscEmp() {

			return tipoInscEmp;
		}

		/**
		 * Indica o tipo de inscrição da empresa.<br>
		 * 1 - CPF.<br>
		 * 2 - CNPJ.<br>
		 */
		public void setTipoInscEmp( final int tipoInscEmp ) {

			this.tipoInscEmp = tipoInscEmp;
		}

		public String getTipoOperacao() {

			return tipoOperacao;
		}

		/**
		 * Indica a operação que devera ser realizada com os registros Detalhe do Lote.<br>
		 * Deve constar apenas um tipo por Lote:<br>
		 * C - Lançamento a Crédito.<br>
		 * D - Lançamento a Débito.<br>
		 * E - Extrato para conciliação.<br>
		 * I - Informações de titulos capturados do próprio banco.<br>
		 * R - Arquivo de remessa.<br>
		 * T - Arquivo de retorno.<br>
		 */
		public void setTipoOperacao( final String tipoOperacao ) {

			this.tipoOperacao = tipoOperacao;
		}

		public String getTipoServico() {

			return tipoServico;
		}

		public int getCodCarteira() {

			return codCarteira;
		}
		
		public String getVariacaoCarteira() {

			return variacaocarteira;
		}
		
		public void setVariacaoCarteira(String var) {

			variacaocarteira = var;
		}

		public void setCodCarteira( final int codCarteira ) {

			this.codCarteira = codCarteira;
		}

		public String getIdentTitulo() {

			return identTitulo;
		}

		/**
		 * Nosso número.<br>
		 */
		public void setIdentTitulo( final String identTitulo ) {

			this.identTitulo = identTitulo;
		}

		/**
		 * Indica o tipo de serviço que o lote contém.<br>
		 * 01 - Cobrança.<br>
		 * 02 - Cobrança em papel.<br>
		 * 03 - Bloqueto eletronico.<br>
		 * 04 - Conciliação bancária.<br>
		 * 05 - Débitos.<br>
		 * 10 - Pagamento dividendos.<br>
		 * 20 - Pagamento fornecedor.<br>
		 * 30 - Pagamento salários.<br>
		 * 50 - Pagamento sinistro segurados.<br>
		 * 60 - Pagamento despesa viajante em trânsito.<br>
		 */
		private void setTipoServico( final String tipoServico ) {

			this.tipoServico = tipoServico;
		}

		public String getVersaoLayout() {

			return versaoLayout;
		}

		public BigDecimal getVlrPercMulta() {

			return vlrPercMulta;
		}

		public void setVlrPercMulta( BigDecimal vlrPercMulta ) {

			this.vlrPercMulta = vlrPercMulta;
		}

		/**
		 * Indica o número da versão do layout do lote, composto de:<br>
		 * versão : 2 digitos.<br>
		 * release: 1 digito.<br>
		 */
		private void setVersaoLayout( final String versaoLayout ) {

			this.versaoLayout = versaoLayout;
		}

		public int getIdentEmitBol() {

			return identEmitBol;
		}

		/**
		 * Identificação da emissão de bloqueto.<br>
		 * 1 - Banco emite.<br>
		 * 2 - Cliente emite.<br>
		 * 3 - Banco pré-emite e o cliente completa.<br>
		 * 4 - Banco reemite.<br>
		 * 5 - Banco não reemite.<br>
		 * 6 - Cobrança sem papel.<br>
		 * Obs.: Os campos 4 e 5 só serão aceitos para código de movimento para remessa 31.
		 */
		public void setIdentEmitBol( final int identEmitBol ) {

			this.identEmitBol = identEmitBol;
		}

		@ Override
		public String getLine( String padraocnab ) throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				if (getCodConvBanco().length()<7) {
					// Convênios menores que 1.000.000
					line.append( "1" ); // Tipo de registro 1	
				} else {
					// Convênios maiorer que 1.000.000
					line.append( "7"); // Tipo de registro 7
				}
				

				/*************************************************/
				/**                                             **/
				/** Implementação para o banco do Brasil CBR641 **/
				/**                                             **/
				/*************************************************/
				
				if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {					
					line.append( format( getTipoInscEmp(), ETipo.$9, 2, 0 ) );// 002 a 003 - Tipo de inscrição da empresa
					line.append( format( getCpfCnpjEmp(), ETipo.$9, 14, 0 ) );//004 a 017 - Numero do CPF/CNPJ da Empresa
					line.append( format( getAgencia(), ETipo.$9, 4, 0 ) );//018 a 021 - Agencia
					line.append( format( getDigAgencia(), ETipo.X, 1, 0 ) );//022 a 022 - Digito da agencia
					line.append( format( getConta(), ETipo.$9, 8, 0 ) );//023 a 030 - Numero da conta corrente
					line.append( format( getDigConta(), ETipo.X, 1, 0 ) );//031 a 031 - Digito da conta corrente
					if (getCodConvBanco().length()<7) {
						line.append( format( getCodConvBanco(), ETipo.X, 6, 0 ) );//032 a 037 - Convenio
					} else {
						line.append( format( getCodConvBanco(), ETipo.X, 7, 0 ) );//032 a 038 - Convenio
					}
					line.append( format( getIdentTitEmp(), ETipo.X, 25, 0 ) ); // Convênio < 1.000.000 = Posição 38 a 62 - Nro de controle do participante (nosso numero)
																			    // Convênio >= 1.000.000 = Posição 39 a 63	
					if (getCodConvBanco().length()<7) {
						// Convênio < 1.000.000
						line.append( format( getIdentTitulo(), ETipo.X, 11, 0 ) ); // Convênio < 1.000.000 = Posição 063 a 073 - Nosso numero
						line.append( format( getDigNossoNumero(), ETipo.$9, 1, 0 ) ); // Posição 074 a 074 - Digito verificador do nosso numero
					} else {
						// Convênio >= 1.000.000
						line.append( format( getIdentTitulo(), ETipo.X, 17, 0 ) ); // Convênio >= 1.000.000 = Posição 064 a 080 - Nosso numero
					}
																				// Convênio >= 1.000.000 = Posição 064 a 074 - Nosso numero
					line.append( "00" ); // Conv. < 100.000.00 = Posição 075 a 076 / Conv >= 1.000.000 = Posição 081 a 082 - Numero da prestacao informar zeros
					line.append( "00" ); // Posição 077 a 078 / 083 a 084 - Grupo de valor informar zeros
					line.append( StringFunctions.replicate( " ", 3 ) );// Posição 079 a 081 / 085 a 087 - Preencher com brancos
					line.append( " " );// Posição 082 a 082 / 088 a 088 -Indicativo de mensagem Preencher com brancos
					line.append( StringFunctions.replicate( " ", 3 ) );// Posição 083 a 085 / 089 a 091 - Preencher com brancos
					line.append( format( getVariacaoCarteira(), ETipo.X, 3, 0 ) ); // Posição 086 a 088 / 092 a 094 - Variacao da carteira
					line.append( "0" ); // Posição 089 a 089 / 095 a 095 - Conta caução
					if (getCodConvBanco().length()<7) {
						line.append( "00000" ); // Posição 090 a 094 - Codigo de responsabilidade
						line.append( "0" ); // Posição 095 a 095 - Digito do codigo de responsabilidade
					} 
					line.append( "000000" ); // Posição 096 a 101 - Numero do borderô
					line.append( StringFunctions.replicate( " ", 5 ) );// Posição 102 a 106 - Tipo de cobrança Preencher com brancos
					line.append( StringFunctions.strZero( getCodCarteira() + "", 2 ) ); // Posição 107 a 108 - Código da Carteira de cobranca
					
				}
				
				/*************************************************/
				/**                                             **/
				/** Implementação para outros bancos (BRADESCO) **/
				/**                                             **/
				/*************************************************/
				
				else {
				
					line.append( StringFunctions.replicate( " ", 5 ) ); // Opcional - Agencia para debito em conta
					line.append( " " ); // Opcional - Dígito da Agencia para debito em conta
					line.append( StringFunctions.replicate( " ", 5 ) ); // Opcional - Razão da conta para debito
					line.append( StringFunctions.replicate( " ", 7 ) ); // Opcional - Conta do sacado para debito
					line.append( " " ); // Opcional - Dígito da conta para debito
					// Identificação da Empresa cedente no banco
					line.append( "0" ); // Posição 21 a 21 - Zero
					
					line.append( StringFunctions.strZero( getCodCarteira() + "", 3 ) ); // Posição 22 a 24 - Código da Carteira
					line.append( format( getAgencia(), ETipo.$9, 5, 0 ) ); // Posição 25 a 29 - Código da Agência Cedente
					line.append( format( getConta(), ETipo.$9, 7, 0 ) ); // Posição 30 a 36 - Conta Corrente
					line.append( format( getDigConta(), ETipo.X, 1, 0 ) ); // Posição 37 a 37 - Dígito da conta
					// fim da idendificação

					line.append( format( getIdentTitEmp(), ETipo.X, 25, 0 ) ); // Posição 38 a 62 - Nro de controle do participante (nosso numero)
					line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) ); // Posição 63 a 65 - Nro do banco

					if ( getVlrPercMulta().floatValue() > 0 ) {
						line.append( "2" ); // Posição 66 a 66 - Se = 2 considerar multa se = 0 sem multa.
						line.append( format( getVlrPercMulta(), ETipo.$9, 4, 2 ) ); // Posição 67 a 70 - Percentual de multa
					}
					else {
						line.append( "0" ); // Posição 66 a 66 - Se = 2 considerar multa se = 0 sem multa.
						line.append( StringFunctions.replicate( "0", 4 ) ); // Posição 67 a 70 - Percentual de multa (preenchido com zeros)
					}

					line.append( format( getIdentTitulo(), ETipo.X, 11, 0 ) ); // Posição 71 a 81 - Identificação do título no banco (nosso numero)

					line.append( format( getDigNossoNumero(), ETipo.$9, 1, 0 ) ); // Posição 82 a 82 - Digito verificador do nosso numero

					// Implementar futuramente
					line.append( format( 0, ETipo.$9, 10, 2 ) ); // Posição 83 a 92 - Valor do Desconto bonif./dia

					line.append( format( getIdentEmitBol(), ETipo.$9, 1, 0 ) ); // Posição 93 a 93 - Condição para Emissão da papeleta de cobrança - 1-Banco emite e processa, 2- Cliente emite e o Banco Processa.

					line.append( "N" ); // Posição 94 a 94 - Ident. se emite boleto para deb. automaticao

					line.append( StringFunctions.replicate( " ", 10 ) ); // Posição 95 a 104 - Identificação da operacao do banco (em branco)

					line.append( " " ); // Posição 105 a 105 - Indicador de rateio crédito 'R' = sim / " "= não
					line.append( "2" ); // Posição 106 a 106 - Endereçamento para aviso do debito autom. 1 = emite aviso / 2 =não emite

					line.append( StringFunctions.replicate( " ", 2 ) ); // Posição 107 a 108 - Branco
					
				}
				
			

				line.append( format( getCodMovimento(), ETipo.$9, 2, 0 ) ); // Posição 109 a 110 - Identificação da ocorrência

				line.append( StringFunctions.strZero( getDocCobranca(), 10 ) ); // Posição 111 a 120 - Nro do documento

				line.append( dateToString( getDtVencTitulo(), "DDMMAA" ) ); // Posição 121 a 126 - Data do vencimento do título

				line.append( format( getVlrTitulo(), ETipo.$9, 13, 2 ) ); // Posição 127 a 139 - Valor do título

				if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {	
					line.append( BancodoBrasil.BANCO_DO_BRASIL ); // Posição 140 a 142 - Banco encarregado da cobrança ( preencher com 001 )
					line.append( "0000" ); // Posição 143 a 146 - Agencia depositária ( preencher com zeros )
					line.append( " " ); // Posição 147 a 147 - Digito Verificador do Prefixo da Agencia. Preencher com branco.
				}
				else {
					line.append( "000" ); // Posição 140 a 142 - Banco encarregado da cobrança ( preencher com zeros )
					line.append( "00000" ); // Posição 143 a 147 - Agencia depositária ( preencher com zeros )
				}

				// line.append( format( getEspecieTit(), ETipo.$9, 2, 0 ) ); // Posição 148 a 149 - Espécie de Título

				/*
				 * 01-Dulicata; 02-Nota Promissoria; 03-Nota de seguro 04-Cobrança seriada 05-Recibo 10-Letras de câmbio 11-Nota de débito 12-Duplicata de serv. 99-Outros;
				 */
				line.append( format( getEspecieTit(), ETipo.$9, 2, 0 ) ); // Posição 148 a 149 - Espécie de Título (Implementada de forma fixa pois difere do código no padrão cnab 240
//				line.append( format( "01", ETipo.$9, 2, 0 ) ); // Posição 148 a 149 - Espécie de Título (Implementada de forma fixa pois difere do código no padrão cnab 240

				line.append( "N" ); // Posição 150 a 150 - Identificação (Sempre "N");

				line.append( dateToString( getDtEmitTit(), "DDMMAA" ) ); // Posição 151 a 156 - Data de emissão do título

				if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {
					
					line.append( format( getCodJuros(), ETipo.$9, 2, 0 ) ); // Posição 157 a 158 - 1° Instrução - Código para juros
					line.append( StringFunctions.strZero( getDiasProtesto() + "", 2 ) ); // Posição 159 a 160 - 2° Instrução - Numero de dias para protesto
					
				}
				else {
				
					line.append( "00" ); // Posição 157 a 158 - 1° Instrução - // Implementação futura.
					line.append( "00" ); // Posição 159 a 160 - 2° Instrução - // Implementação futura.
					
				}

				if ( (getCodJuros()==1) || (getCodJuros()==2) ) { // Se Juros/Mora diária
					line.append( format( calcVlrJuros(getCodJuros(), getVlrTitulo(), getVlrJurosTaxa()), ETipo.$9, 13, 2 ) ); // Posição 161 a 173 - (se for do tipo mora diária) Mora por dia de atraso
				}
				else {
					line.append( StringFunctions.replicate( "0", 13 ) ); // Posição 161 a 173 - (Se não for do tipo mora diária) Mora por dia de atraso
				}

				if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {
					line.append( "000000" ); // Posição 174 a 179 - Data limite para concessão de desconto (informar 000000)	
				}
				else{
					line.append( dateToString( getDtDesc(), "DDMMAA" ) ); // Posição 174 a 179 - Data limete para concessão de desconto
				}

				line.append( format( getVlrDesc(), ETipo.$9, 13, 2 ) ); // Posição 180 a 192 - Valor de desconto

				line.append( StringFunctions.replicate( "0", 13 ) ); // Posição 193 a 205 - (Valor do IOF (Apenas para empresas seguradoras))

				line.append( format( getVlrAbatimento(), ETipo.$9, 13, 2 ) ); // Posição 206 a 218 - Valor do Abatimento a ser concedido ou cancelado (no caso de transação de abatimento)

				line.append( StringFunctions.strZero( getTipoInscCli() + "", 2 ) );// Posição 219 a 220 - Identificação do tipo de inscrição do sacado -- 01:CPF, 02:CNPJ

				line.append( format( getCpfCnpjCli(), ETipo.$9, 14, 0 ) );// Posição 221 a 234 - CNPJ/CPF
				
				if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {
					line.append( format( StringFunctions.clearString( StringFunctions.clearAccents( getRazCli()), " " ).toUpperCase(), ETipo.X, 37, 0 ) );// Posição 235 a 271 - Nome do Sacado
					line.append( StringFunctions.replicate( " ", 3 ) ); // Posição 272 a 274 - Brancos
					line.append( format( StringFunctions.clearString( StringFunctions.clearAccents( getEndCli() ), " " ).toUpperCase(), ETipo.X, 52, 0 ) );// Posição 275 a 326 - Endereço Completo
				}
				else {
					line.append( format( getRazCli(), ETipo.X, 40, 0 ) );// Posição 235 a 274 - Nome do Sacado
					line.append( format( getEndCli(), ETipo.X, 40, 0 ) );// Posição 275 a 314 - Endereço Completo
					line.append( format( getMsg1(),   ETipo.X, 12, 0 ) );// Posição 315 a 326 - Mensagem 1
				}
				
				line.append( format( getCepCli(), ETipo.$9, 8, 0 ) ); // Posição 327 a 334 - Cep

				if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {
					line.append( format( StringFunctions.clearAccents( getCidCli() ).toUpperCase(), ETipo.X, 15, 0 ) );// Posição 335 a 349 - Cidade do sacado
					line.append( format( getUfCli(), ETipo.X, 2, 0 ) );// Posição 350 a 351 - UF do sacado
					line.append( format( StringFunctions.clearString( StringFunctions.clearAccents( getRazAva()), " " ).toUpperCase(), ETipo.X, 40, 0 ) );// Posição 352 a 391 - Sacado/Avalista ou Mensagem 2
					line.append( StringFunctions.replicate( " ", 2 ) ); // Posição 392 a 393 - Informar Brancos
					line.append( " " ); // Posição 394 a 394 - Brancos para completar registro
				}
				else {
					line.append( format( getRazAva(), ETipo.X, 60, 0 ) );// Posição 335 a 394 - Sacado/Avalista ou Mensagem 2
				}
				
				line.append( format( getSeqregistro(), ETipo.$9, 6, 0 ) );// Posição 395 a 400 - Não Sequencial do registro

				line.append( (char) 13 ); 

				line.append( (char) 10 );

			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 1.\nErro ao escrever registro.\n" + e.getMessage() );
			}

			return line.toString();
		}

		@ Override
		public void parseLine( final String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {
					throw new ExceptionCnab( "CNAB registro 1.\nLinha nula." );
				}
				else {

					if ( line.length() < 400 ) { // Padrão CNAB 240
						setCodBanco( line.substring( 0, 3 ) );
						setLoteServico( line.substring( 3, 7 ).trim().length() > 0 ? Integer.parseInt( line.substring( 3, 7 ).trim() ) : 0 );
						setRegistroHeader( line.substring( 7, 8 ).trim().length() > 0 ? Integer.parseInt( line.substring( 7, 8 ).trim() ) : 0 );
						setTipoOperacao( line.substring( 8, 9 ) );
						setTipoServico( line.substring( 9, 11 ) );
						setFormaLancamento( line.substring( 11, 13 ) );
						setVersaoLayout( line.substring( 13, 16 ) );
						setTipoInscEmp( line.substring( 17, 18 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 18 ).trim() ) : 0 );
						setCpfCnpjEmp( line.substring( 18, 33 ) );
						setCodConvBanco( line.substring( 33, 53 ) );
						setAgencia( line.substring( 53, 58 ) );
						setDigAgencia( line.substring( 58, 59 ) );
						setConta( line.substring( 59, 71 ) );
						setDigConta( line.substring( 71, 72 ) );
						setDigAgConta( line.substring( 72, 73 ) );
						setRazEmp( line.substring( 73, 103 ) );
						setMsg1( line.substring( 103, 143 ) );
						setMsg2( line.substring( 143, 183 ) );
						setNrRemRet( line.substring( 183, 191 ).trim().length() > 0 ? Integer.parseInt( line.substring( 183, 191 ).trim() ) : 0 );
						setDataRemRet( stringDDMMAAAAToDate( line.substring( 191, 199 ).trim() ) );
						setDataCred( stringDDMMAAAAToDate( line.substring( 199, 207 ).trim() ) );
					}
					else { // Padrão CNAB 400

						if ( "1".equals( line.substring( 0, 1 ) ) ) { // Posição 01 a 01 - Identificação do Registro DETALHE

							setCodCarteira( Integer.parseInt( line.substring( 107, 108 ) ) ); // Posição 108 a 108 - Código da carteira
							setCodRejeicoes( line.substring( 108, 110 ) );// Posição 109 a 109 - Código das ocorrências (vide pg.45)

							setIdentTitEmp( line.substring( 37, 62 ) ); // Posição 38 a 62 - Nro Controle do Participante

							setVlrOutrasDesp( strToBigDecimal( line.substring( 188, 201 ) ) );

							setVlrJurosMulta( strToBigDecimal( line.substring( 201, 214 ) ) );
							setVlrIOF( strToBigDecimal( line.substring( 214, 227 ) ) );
							setVlrAbatimento( strToBigDecimal( line.substring( 227, 240 ) ) );
							setVlrDesc( strToBigDecimal( line.substring( 240, 253 ) ) );

							setVlrPago( strToBigDecimal( line.substring( 253, 266 ) ) );
							
//							setVlrPago( strToBigDecimal( line.substring( 305, 318 ) ) );
							
							setVlrJurosTaxa( strToBigDecimal( line.substring( 266, 279 ) ) );
							setVlrOutrosCred( strToBigDecimal( line.substring( 279, 292 ) ) );
							
							if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {							
								setDataCred( stringDDMMAAToDate( line.substring( 175, 181 ).trim() ) );
							}
							else {
								setDataCred( stringDDMMAAToDate( line.substring( 295, 301 ).trim() ) );
							}
							
							System.out.println( "Rejeição...." + line.substring( 86,88 ));
							
						} else if ( "7".equals( line.substring( 0, 1 ) ) ) { // Posição 01 a 01 - Identificação do Registro DETALHE

							setIdentTitEmp( line.substring( 38, 63 ) ); // Posição 39 a 63 - Nro Controle do Participante
							setCodCarteira( Integer.parseInt( line.substring( 107, 109 ) ) ); // Posição 108 a 109 - Código da carteira
							setCodRejeicoes( line.substring( 108, 110 ) );// Posição 109 a 110 - Código das ocorrências (vide pg.45)
							setVlrOutrasDesp( strToBigDecimal( line.substring( 188, 201 ) ) ); // 189 a 201 - Outras despesas
							setVlrJurosMulta( strToBigDecimal( line.substring( 201, 214 ) ) ); // 202 a 214 - Juros do desconto
							setVlrIOF( strToBigDecimal( line.substring( 214, 227 ) ) ); // 215 a 227 - IOF do desconto
							setVlrAbatimento( strToBigDecimal( line.substring( 227, 240 ) ) ); // 228 a 240 - Valor do abatimento
							setVlrDesc( strToBigDecimal( line.substring( 240, 253 ) ) ); // 241 a 253 - Desconto concedido 
							setVlrPago( strToBigDecimal( line.substring( 253, 266 ) ) ); // 254 a 266 - Valor recebido (valor recebido parcial)
							setVlrJurosTaxa( strToBigDecimal( line.substring( 266, 279 ) ) ); // 267 a 279 - Juros de mora
							setVlrOutrosCred( strToBigDecimal( line.substring( 279, 292 ) ) ); // 280 a 292 - Outros recebimentos
							if(getCodBanco().equals( BancodoBrasil.BANCO_DO_BRASIL )) {							
								setDataCred( stringDDMMAAToDate( line.substring( 175, 181 ).trim() ) ); // 176 a 181 - Data do crédito (DDMMAAAA)
							}
							else {
								setDataCred( stringDDMMAAToDate( line.substring( 295, 301 ).trim() ) );
							}
							System.out.println( "Rejeição...." + line.substring( 86,88 ));
						}
						else {
							Funcoes.mensagemErro( null, "Erro na leitura do arquivo de retorno.\nNão é um registro de transação válido para o padrão CNAB 400!" );
						}

					}

				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 1.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}

	}

}
