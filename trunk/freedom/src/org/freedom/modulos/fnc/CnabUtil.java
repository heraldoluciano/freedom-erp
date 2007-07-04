package org.freedom.modulos.fnc;

import java.util.Date;

import org.freedom.funcoes.Funcoes;


class CnabUtil extends FbnUtil {
	
	abstract class Reg {
		
		protected abstract void parseLine( String line ) throws Exception;
		
		protected abstract String getLine() throws Exception;
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
		private String digAgeConta;
		private String nomeEmp;
		private String msg1;
		private String msg2;
		private int nrRemRet;
		private Date dataRemRet;
		private Date dataCred;
		
		public String getAgencia() {		
			return agencia;
		}
		
		public void setAgencia( final String agencia ) {		
			this.agencia = agencia;
		}
		
		public String getCpfCnpjEmp() {		
			return cpfCnpjEmp;
		}
		
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
		
		public String getDigAgeConta() {		
			return digAgeConta;
		}
		
		public void setDigAgeConta( final String digAgeConta ) {		
			this.digAgeConta = digAgeConta;
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
		
		public void setFormaLancamento( final String formaLancamento ) {		
			this.formaLancamento = formaLancamento;
		}
		
		public int getLoteServico() {		
			return loteServico;
		}
		
		public void setLoteServico( final int loteServico ) {		
			this.loteServico = loteServico;
		}
		
		public String getMsg1() {		
			return msg1;
		}
		
		public void setMsg1( final String msg1 ) {		
			this.msg1 = msg1;
		}
		
		public String getMsg2() {		
			return msg2;
		}
		
		public void setMsg2( final String msg2 ) {		
			this.msg2 = msg2;
		}
		
		public String getNomeEmp() {		
			return nomeEmp;
		}
		
		public void setNomeEmp( final String nomeEmp ) {		
			this.nomeEmp = nomeEmp;
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
		
		public void setRegistroHeader( final int registroHeader ) {		
			this.registroHeader = registroHeader;
		}
		
		public int getTipoInscEmp() {		
			return tipoInscEmp;
		}
		
		public void setTipoInscEmp( final int tipoInscEmp ) {		
			this.tipoInscEmp = tipoInscEmp;
		}
		
		public String getTipoOperacao() {		
			return tipoOperacao;
		}
		
		public void setTipoOperacao( final String tipoOperacao ) {		
			this.tipoOperacao = tipoOperacao;
		}
		
		public String getTipoServico() {		
			return tipoServico;
		}
		
		public void setTipoServico( final String tipoServico ) {		
			this.tipoServico = tipoServico;
		}
		
		public String getVersaoLayout() {		
			return versaoLayout;
		}
		
		public void setVersaoLayout( final String versaoLayout ) {		
			this.versaoLayout = versaoLayout;
		}

		@Override
		protected void parseLine( final String line ) throws Exception {

			try {

				if ( line == null ) {
					
					throw new Exception( "CNAB registro 1.\nLinha nula." );
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
					setDigAgeConta( line.substring( 72, 73 ) );
					setNomeEmp( line.substring( 74, 103 ) );
					setMsg1( line.substring( 103, 143 ) );
					setMsg2( line.substring( 143, 183 ) );
					setNrRemRet( line.substring( 183, 191 ).trim().length() > 0 ? Integer.parseInt( line.substring( 183, 191 ).trim() ) : 0 );
					setDataRemRet( Funcoes.encodeDate( Integer.parseInt( line.substring( 191, 193 ).trim() ), Integer.parseInt( line.substring( 193, 195 ).trim() ), Integer.parseInt( line.substring( 195, 199 ).trim() ) ) );
					setDataCred( Funcoes.encodeDate( Integer.parseInt( line.substring( 199, 201 ).trim() ), Integer.parseInt( line.substring( 201, 203 ).trim() ), Integer.parseInt( line.substring( 203, 207 ).trim() ) ) );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
				throw new Exception( "CNAB registro 1.\nErro ao ler registro.\n" + e.getMessage() );
			}			
		}

		@Override
		protected String getLine() throws Exception {

			StringBuilder line = new StringBuilder();
			
			try {
				
				line.append( getCodBanco() );
				line.append( getLoteServico() );
				line.append( getRegistroHeader() );
				line.append( getTipoOperacao() );
				line.append( getTipoServico() );
				line.append( getFormaLancamento() );
				line.append( getVersaoLayout() );
				line.append( ' ' );
				line.append( getTipoInscEmp() );
				line.append( getCpfCnpjEmp() );
				line.append( getCodConvBanco() );
				line.append( getAgencia() );
				line.append( getDigAgencia() );
				line.append( getConta() );
				line.append( getDigConta() );
				line.append( getDigAgeConta() );
				line.append( getNomeEmp() );
				line.append( getMsg1() );
				line.append( getMsg2() );
				line.append( getNrRemRet() );
				line.append( dateToString( getDataRemRet() ) );
				line.append( dateToString( getDataCred() ) );
				line.append( Funcoes.replicate( " ", 33 ) );
			}
			catch ( Exception e ) {				
				e.printStackTrace();
				throw new Exception( "CNAB registro 1.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}
				
	}
	
	/**
	 * Converte java.util.Date para um String em formato DDMMAAAA.
	 * @param arg java.util.Date.
	 * @return String em formato DDMMAAAA.
	 * @throws Exception
	 */
	public static String dateToString( final Date arg ) throws Exception {
		
		String retorno = null;
		int[] args = Funcoes.decodeDate( arg );		
		retorno = String.valueOf( args[2] ) + String.valueOf( args[1] ) + String.valueOf( args[0] );
		
		return retorno;
	}
}
