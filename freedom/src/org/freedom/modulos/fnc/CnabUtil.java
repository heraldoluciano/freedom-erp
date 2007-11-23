package org.freedom.modulos.fnc;

import java.math.BigDecimal;
import java.util.Date;

import org.freedom.funcoes.Funcoes;

public class CnabUtil extends FbnUtil {

	abstract class Reg {

		public abstract void parseLine( String line ) throws ExceptionCnab;

		public abstract String getLine() throws ExceptionCnab;

		protected String format( Object obj, ETipo tipo, int tam, int dec ) {

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

					retorno = Funcoes.strZero( str, tam );
				}
			}
			else {
				retorno = Funcoes.adicionaEspacos( str, tam );
			}

			return retorno;
		}
	}

	class RegHeader extends Reg {

		private String codBanco;

		private String loteServico;

		private int registroHeader;

		private int tipoInscEmp;

		private String cpfCnpjEmp;

		private String codConvBanco;

		private String agencia;

		private String digAgencia;

		private String conta;

		private String digConta;

		private String digAgConta;

		private String razEmp;

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
		 * Observar as regras de preenchimento abaixo no que se refere ao headre de serviço/lote:<br>
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
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg1#setTipoInscEmp( int tipoInscEmp )
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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
				line.append( getLoteServico() );
				line.append( getRegistroHeader() );
				line.append( Funcoes.replicate( " ", 9 ) );
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
				line.append( Funcoes.replicate( " ", 10 ) );
				line.append( format( getTipoOperacao(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDataGeracao() ) );
				line.append( format( getHoraGeracao(), ETipo.$9, 6, 0 ) );
				line.append( format( getSequenciaArq(), ETipo.$9, 6, 0 ) );
				line.append( getVersaoLayout() );
				line.append( format( getDensidadeArq(), ETipo.$9, 5, 0 ) );
				line.append( format( getUsoBanco(), ETipo.X, 20, 0 ) );
				line.append( format( getUsoEmp(), ETipo.X, 20, 0 ) );
				line.append( Funcoes.replicate( " ", 11 ) );
				line.append( "CSP" );// indentifica cobrança sem papel.
				line.append( format( getUsoVans(), ETipo.$9, 3, 0 ) );
				line.append( format( getTipoServico(), ETipo.X, 2, 0 ) );
				line.append( format( getOcorrencias(), ETipo.X, 10, 0 ) );
				line.append( (char) 13 );
				line.append( (char) 10 );
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
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro Header.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}

	class Reg1 extends Reg {

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
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg1#setTipoInscEmp( int tipoInscEmp )
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

		/**
		 * Indica o tipo de serviço que o lote contém.<br>
		 * 01 - Cobramça.<br>
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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

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
				line.append( dateToString( getDataRemRet() ) );
				line.append( dateToString( getDataCred() ) );
				line.append( Funcoes.replicate( " ", 33 ) );
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

	abstract class Reg3 extends Reg {

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
		 * 04 - Comcessão de abatimento.<br>
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

		public String getLineReg3() throws ExceptionCnab {

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

	class Reg3P extends Reg3 {

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

		private int codDesc;

		private Date dtDesc;

		private BigDecimal vlrpercConced;

		private BigDecimal vlrIOF;

		private BigDecimal vlrAbatimento;

		private Receber identTitEmp;

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
		 * 1 - Cobrança simpres.<br>
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
		 * 2 - Taxa nensal.<br>
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
		 * Especie do titulo.<br>
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

		public Receber getIdentTitEmp() {

			return identTitEmp;
		}

		public void setIdentTitEmp( final Receber identTitEmp ) {

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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3() );
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
				line.append( dateToString( getDtVencTitulo() ) );
				line.append( format( getVlrTitulo(), ETipo.$9, 15, 2 ) );
				line.append( format( getAgenciaCob(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgenciaCob(), ETipo.$9, 1, 0 ) );
				line.append( format( getEspecieTit(), ETipo.$9, 2, 0 ) );
				line.append( format( getAceite(), ETipo.X, 1, 0 ) );
				line.append( dateToString( getDtEmitTit() ) );
				line.append( format( getCodJuros(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDtJuros() ) );
				line.append( format( getVlrJurosTaxa(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodDesc(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDtDesc() ) );
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
					setIdentTitEmp( new Receber( line.substring( 195, 220 ) ) );
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

	class Reg3Q extends Reg3 {

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
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3Q#setTipoInscAva( int tipoInscEmp )
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
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3Q#setTipoInscCli( int tipoInscEmp )
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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3() );
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
				line.append( Funcoes.replicate( " ", 8 ) );
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

	class Reg3R extends Reg3 {

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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3() );
				line.append( format( getCodDesc2(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDataDesc2() ) );
				line.append( format( getVlrPercConced2(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodDesc3(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDataDesc3() ) );
				line.append( format( getVlrPercConced3(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodMulta(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDataMulta() ) );
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

	class Reg3S extends Reg3 {

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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3() );
				line.append( format( getTipoImpressao(), ETipo.$9, 1, 0 ) );

				if ( getTipoImpressao() == 1 || getTipoImpressao() == 2 ) {

					line.append( format( getLinhaImp(), ETipo.$9, 2, 0 ) );
					line.append( format( getMsgImp(), ETipo.X, 140, 0 ) );
					line.append( format( getTipoChar(), ETipo.$9, 2, 0 ) );
					line.append( Funcoes.replicate( " ", 78 ) );
				}
				else if ( getTipoImpressao() == 3 ) {

					line.append( format( getMsg5(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg6(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg7(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg8(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg9(), ETipo.X, 40, 0 ) );
					line.append( Funcoes.replicate( " ", 22 ) );
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

	class Reg3T extends Reg3 {

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

		private String codBanco;

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

		public String getCodBanco() {

			return codBanco;
		}

		/**
		 * Agencia cobradora/recebedora.<br>
		 */
		public void setCodBanco( final String codBanco ) {

			this.codBanco = codBanco;
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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3() );
				line.append( format( getAgencia(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgencia(), ETipo.$9, 1, 0 ) );
				line.append( format( getConta(), ETipo.$9, 12, 0 ) );
				line.append( format( getDigConta(), ETipo.$9, 1, 0 ) );
				line.append( format( getDigAgConta(), ETipo.$9, 1, 0 ) );
				line.append( format( getIdentTitBanco(), ETipo.X, 20, 0 ) );
				line.append( format( getCarteira(), ETipo.$9, 1, 0 ) );
				line.append( format( getDocCob(), ETipo.X, 15, 0 ) );
				line.append( dateToString( getDataVencTit() ) );
				line.append( format( getVlrTitulo(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
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
				line.append( Funcoes.replicate( " ", 17 ) );
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
					setCodBanco( line.substring( 96, 99 ) );
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

	class Reg3U extends Reg3 {

		private BigDecimal vlrJurosMulta;

		private BigDecimal vlrDesc;

		private BigDecimal vrlAbatCancel;

		private BigDecimal vlrIOF;

		private BigDecimal vlrPago;

		private BigDecimal vlrLiqCred;

		private BigDecimal vlrOutrasDesp;

		private BigDecimal vlrOutrosCred;

		private Date dataOcorr;

		private Date dataEfetvCred;

		private String codOcorrSac;

		private Date dataOcorrSac;

		private BigDecimal vlrOcorrSac;

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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( super.getLineReg3() );
				line.append( format( getVlrJurosMulta(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrDesc(), ETipo.$9, 15, 2 ) );
				line.append( format( getVrlAbatCancel(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrIOF(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrPago(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrLiqCred(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrOutrasDesp(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrOutrosCred(), ETipo.$9, 15, 2 ) );
				line.append( dateToString( getDataOcorr() ) );
				line.append( dateToString( getDataEfetvCred() ) );
				line.append( format( getCodOcorrSac(), ETipo.X, 4, 0 ) );
				line.append( dateToString( getDataOcorrSac() ) );
				line.append( format( getVlrOcorrSac(), ETipo.$9, 15, 2 ) );
				line.append( format( getCompOcorrSac(), ETipo.X, 30, 0 ) );
				line.append( format( getCodBancoCompens(), ETipo.$9, 3, 0 ) );
				line.append( format( getNossoNrCompens(), ETipo.$9, 20, 0 ) );
				line.append( Funcoes.replicate( " ", 7 ) );
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

	class Reg5 extends Reg {

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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
				line.append( format( getLoteServico(), ETipo.$9, 4, 0 ) );
				line.append( format( getRegistroTrailer(), ETipo.$9, 1, 0 ) );
				line.append( Funcoes.replicate( " ", 9 ) );
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
				line.append( Funcoes.replicate( " ", 117 ) );
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

	class RegTrailer extends Reg {

		private String codBanco;

		private String loteServico;

		private String registroTrailer;

		private int qtdLotes;

		private int qtdRegistros;

		private int qtdConsilacoes;

		public RegTrailer() {

			setLoteServico( "9999" );
			setRegistroTrailer( "9" );
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
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();

			try {

				line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
				line.append( format( getLoteServico(), ETipo.$9, 4, 0 ) );
				line.append( format( getRegistroTrailer(), ETipo.$9, 1, 0 ) );
				line.append( Funcoes.replicate( " ", 9 ) );
				line.append( format( getQtdLotes(), ETipo.$9, 6, 0 ) );
				line.append( format( getQtdRegistros(), ETipo.$9, 6, 0 ) );
				line.append( format( getQtdConsilacoes(), ETipo.$9, 6, 0 ) );
				line.append( Funcoes.replicate( " ", 205 ) );
				line.append( (char) 13 );
				line.append( (char) 10 );
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
	
	class Receber {
		
		private int codrec;
		
		private int nrparcrec;
		
		private String docrec;
		
		private BigDecimal valorApagar;
		
		private Date emissao;
		
		private Date vencimento;
		
		private String conta;
		
		private String planejamento;
		
		private String code;
		
		private int codcliente;
		
		private String razcliente;
		
		
		public Receber() {
			
		}
		
		public Receber( int arg0, int arg1 ) { 
			
			setCodrec( arg0 );
			setNrparcrec( arg1 );
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
			
			this.code = Funcoes.strZero( String.valueOf( getCodrec() ), 10 ) 
						+ Funcoes.strZero( String.valueOf( getNrparcrec() ), 3 );
			
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
	public static String dateToString( final Date arg ) throws ExceptionCnab {

		String retorno = null;

		try {
			if ( arg != null ) {

				int[] args = Funcoes.decodeDate( arg );
				retorno = Funcoes.strZero( 
						String.valueOf( args[ 2 ] ), 2 ) + 
						Funcoes.strZero( String.valueOf( args[ 1 ] ), 2 ) + 
						Funcoes.strZero( String.valueOf( args[ 0 ] ), 4 );
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
				String value = null;

				char chars[] = arg.toCharArray();

				for ( int i = 0; i < chars.length; i++ ) {
					if ( '0' != chars[ i ] ) {
						value = arg.substring( i );
						break;
					}
				}
				if ( value != null ) {
					value = value.substring( 0, value.length() - 2 ) + "." + value.substring( value.length() - 2 );
				}
				bdReturn = new BigDecimal( value != null ? value : "0" );
			}
		} catch ( RuntimeException e ) {
			throw new ExceptionCnab( "Erro na função strToBigDecimal.\n" + e.getMessage() );
		}

		return bdReturn;
	}
}
