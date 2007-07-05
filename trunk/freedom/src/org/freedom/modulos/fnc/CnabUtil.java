package org.freedom.modulos.fnc;

import java.math.BigDecimal;
import java.util.Date;

import org.freedom.funcoes.Funcoes;


class CnabUtil extends FbnUtil {
	
	abstract class Reg {
		
		public abstract void parseLine( String line ) throws Exception;
		
		public abstract String getLine() throws Exception;
		
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
		private String nomeEmp;
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
		
		private void setRegistroHeader( final int registroHeader ) {		
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
		
		private void setTipoServico( final String tipoServico ) {		
			this.tipoServico = tipoServico;
		}
		
		public String getVersaoLayout() {		
			return versaoLayout;
		}
		
		private void setVersaoLayout( final String versaoLayout ) {		
			this.versaoLayout = versaoLayout;
		}

		@Override
		public String getLine() throws Exception {
		
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
				line.append( format( getNomeEmp(), ETipo.X, 30, 0 ) );
				line.append( format( getMsg1(), ETipo.X, 40, 0 ) );
				line.append( format( getMsg2(), ETipo.X, 40, 0 ) );
				line.append( format( getNrRemRet(), ETipo.$9, 8, 0 ) );
				line.append( dateToString( getDataRemRet() ) );
				line.append( dateToString( getDataCred() ) );
				line.append( Funcoes.replicate( " ", 33 ) );
			}
			catch ( Exception e ) {	
				throw new Exception( "CNAB registro 1.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		@Override
		public void parseLine( final String line ) throws Exception {

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
					setDigAgConta( line.substring( 72, 73 ) );
					setNomeEmp( line.substring( 74, 103 ) );
					setMsg1( line.substring( 103, 143 ) );
					setMsg2( line.substring( 143, 183 ) );
					setNrRemRet( line.substring( 183, 191 ).trim().length() > 0 ? Integer.parseInt( line.substring( 183, 191 ).trim() ) : 0 );
					setDataRemRet( Funcoes.encodeDate( Integer.parseInt( line.substring( 191, 193 ).trim() ), Integer.parseInt( line.substring( 193, 195 ).trim() ), Integer.parseInt( line.substring( 195, 199 ).trim() ) ) );
					setDataCred( Funcoes.encodeDate( Integer.parseInt( line.substring( 199, 201 ).trim() ), Integer.parseInt( line.substring( 201, 203 ).trim() ), Integer.parseInt( line.substring( 203, 207 ).trim() ) ) );
				}
			} catch ( Exception e ) {
				throw new Exception( "CNAB registro 1.\nErro ao ler registro.\n" + e.getMessage() );
			}			
		}
				
	}
	
	class Reg3 extends Reg {
		
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
		
		public void setCodMovimento( final int codMovimento ) {		
			this.codMovimento = codMovimento;
		}
		
		public int getLoteServico() {		
			return loteServico;
		}
		
		public void setLoteServico( final int loteServico ) {		
			this.loteServico = loteServico;
		}
		
		public int getRegistroDetalhe() {		
			return registroDetalhe;
		}
		
		private void setRegistroDetalhe( final int registroDetalhe ) {		
			this.registroDetalhe = registroDetalhe;
		}
		
		public char getSegmento() {		
			return segmento;
		}
		
		private void setSegmento( final char segmento ) {		
			this.segmento = segmento;
		}
		
		public int getSeqLote() {		
			return seqLote;
		}
		
		public void setSeqLote( final int seqLote ) {		
			this.seqLote = seqLote;
		}

		@Override
		public String getLine() throws Exception {

			StringBuilder line = new StringBuilder();
			
			try {
				
				line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
				line.append( format( getLoteServico(), ETipo.$9, 4, 0 ) );
				line.append( getRegistroDetalhe() );
				line.append( format( getSeqLote(), ETipo.$9, 5, 0 ) );
				line.append( getSegmento() );
				line.append( ' ' );
				line.append( format( getCodMovimento(), ETipo.$9, 2, 0 ) );
			}
			catch ( Exception e ) {	
				throw new Exception( "CNAB registro 3.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		@Override
		public void parseLine( String line ) throws Exception {

			try {

				if ( line == null ) {					
					throw new Exception( "CNAB registro 3.\nLinha nula." );
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
				throw new Exception( "CNAB registro 3.\nErro ao ler registro.\n" + e.getMessage() );
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
		
		public char getAceite() {		
			return aceite;
		}
		
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
		
		public void setCodBaixaDev( final int codBaixaDev ) {		
			this.codBaixaDev = codBaixaDev;
		}
		
		public int getCodCarteira() {		
			return codCarteira;
		}
		
		public void setCodCarteira( final int codCarteira ) {		
			this.codCarteira = codCarteira;
		}
		
		public int getCodDesc() {		
			return codDesc;
		}
		
		public void setCodDesc( final int codDesc ) {		
			this.codDesc = codDesc;
		}
		
		public int getCodJuros() {		
			return codJuros;
		}
		
		public void setCodJuros( final int codJuros ) {		
			this.codJuros = codJuros;
		}
		
		public int getCodMoeda() {		
			return codMoeda;
		}
		
		public void setCodMoeda( final int codMoeda ) {		
			this.codMoeda = codMoeda;
		}
		
		public int getCodProtesto() {		
			return codProtesto;
		}
		
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
		
		public void setDiasBaixaDevol( final int diasBaixaDevol ) {		
			this.diasBaixaDevol = diasBaixaDevol;
		}
		
		public int getDiasProtesto() {		
			return diasProtesto;
		}
		
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
		
		public void setDtJuros( final Date dtJuros ) {		
			this.dtJuros = dtJuros;
		}
		
		public Date getDtVencTitulo() {		
			return dtVencTitulo;
		}
		
		public void setDtVencTitulo( final Date dtVencTitulo ) {		
			this.dtVencTitulo = dtVencTitulo;
		}
		
		public int getEspecieTit() {		
			return especieTit;
		}
		
		public void setEspecieTit( final int especieTit ) {		
			this.especieTit = especieTit;
		}
		
		public int getFormaCadTitulo() {		
			return formaCadTitulo;
		}
		
		public void setFormaCadTitulo( final int formaCadTitulo ) {		
			this.formaCadTitulo = formaCadTitulo;
		}
		
		public int getIdentDist() {		
			return identDist;
		}
		
		public void setIdentDist( final int identDist ) {		
			this.identDist = identDist;
		}
		
		public int getIdentEmitBol() {		
			return identEmitBol;
		}
		
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
		
		public void setIdentTitulo( final String identTitulo ) {		
			this.identTitulo = identTitulo;
		}
		
		public int getTipoDoc() {		
			return tipoDoc;
		}
		
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

		@Override
		public String getLine() throws Exception {

			StringBuilder line = new StringBuilder();
			
			try {
				
				line.append( super.getLine() );
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
				line.append( format( getVlrTitulo(), ETipo.$9, 13, 2 ) );
				line.append( format( getAgenciaCob(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgenciaCob(), ETipo.X, 1, 0 ) );
				line.append( format( getEspecieTit(), ETipo.$9, 2, 0 ) );
				line.append( format( getAceite(), ETipo.X, 1, 0 ) );
				line.append( dateToString( getDtEmitTit() ) );
				line.append( format( getCodJuros(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDtJuros() ) );
				line.append( format( getVlrJurosTaxa(), ETipo.$9, 13, 2 ) ); 
				line.append( format( getCodDesc(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDtDesc() ) );
				line.append( format( getVlrpercConced(), ETipo.$9, 13, 2 ) );
				line.append( format( getVlrIOF(), ETipo.$9, 13, 2 ) );
				line.append( format( getVlrAbatimento(), ETipo.$9, 13, 2 ) );
				line.append( format( getIdentTitEmp(), ETipo.X, 25, 0 ) );
				line.append( format( getCodProtesto(), ETipo.$9, 1, 0 ) );
				line.append( format( getDiasProtesto(), ETipo.$9, 2, 0 ) );
				line.append( format( getCodBaixaDev(), ETipo.$9, 1, 0 ) );
				line.append( format( getDiasBaixaDevol(), ETipo.$9, 3, 0 ) );  
				line.append( format( getCodMoeda(), ETipo.$9, 2, 0 ) );
				line.append( format( getContrOperCred(), ETipo.$9, 10, 0 ) );
			}
			catch ( Exception e ) {	
				throw new Exception( "CNAB registro 3 segmento P.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		@Override
		public void parseLine( String line ) throws Exception {

			try {

				if ( line == null ) {					
					throw new Exception( "CNAB registro 3 segmento P.\nLinha nula." );
				}
				else {

					super.parseLine( line );
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
					setDtVencTitulo( Funcoes.encodeDate( Integer.parseInt( line.substring( 77, 79 ).trim() ), Integer.parseInt( line.substring( 79, 81 ).trim() ), Integer.parseInt( line.substring( 81, 85 ).trim() ) ) );
					setVlrTitulo( new BigDecimal( line.substring( 85, 100 ) ) );
					setAgenciaCob( line.substring( 100, 105 ) );
					setDigAgenciaCob( line.substring( 105, 106 ) );
					setEspecieTit( line.substring( 106, 108 ).trim().length() > 0 ? Integer.parseInt( line.substring( 106, 108 ).trim() ) : 0 );
					setAceite( line.substring( 108, 109 ).charAt( 0 ) );
					setDtEmitTit( Funcoes.encodeDate( Integer.parseInt( line.substring( 109, 111 ).trim() ), Integer.parseInt( line.substring( 111, 113 ).trim() ), Integer.parseInt( line.substring( 113, 117 ).trim() ) ) );
					setCodJuros( line.substring( 117, 118 ).trim().length() > 0 ? Integer.parseInt( line.substring( 117, 118 ).trim() ) : 0 );
					setDtJuros( Funcoes.encodeDate( Integer.parseInt( line.substring( 118, 120 ).trim() ), Integer.parseInt( line.substring( 120, 122 ).trim() ), Integer.parseInt( line.substring( 122, 126 ).trim() ) ) );
					setVlrJurosTaxa( strToBigDecimal( line.substring( 126, 141 ) ) ); 
					setCodDesc( line.substring( 141, 142 ).trim().length() > 0 ? Integer.parseInt( line.substring( 141, 142 ).trim() ) : 0 );
					setDtDesc( Funcoes.encodeDate( Integer.parseInt( line.substring( 142, 144 ).trim() ), Integer.parseInt( line.substring( 144, 146 ).trim() ), Integer.parseInt( line.substring( 146, 150 ).trim() ) ) );
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
				throw new Exception( "CNAB registro 3 segmento P.\nErro ao ler registro.\n" + e.getMessage() );
			}
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

	/**
	 * Converte para java.math.BigDecimal um String de inteiros sem ponto ou virgula.
	 * @param arg String de inteiros sem ponto ou virgula.
	 * @return java.math.BigDecimal com escala de 2.
	 * @throws NumberFormatException
	 */
	public static BigDecimal strToBigDecimal( final String arg ) throws NumberFormatException {
		String value = null;
		if ( arg != null ) {
			char chars[] = arg.toCharArray();
			for ( int i=0; i < chars.length; i++ ) {
				if ( '0' != chars[ i ] ) {
					value = arg.substring( i );
					break;
				}
			}
			if ( value != null ) {
				value = value.substring( 0, value.length() - 2 ) + "." + value.substring( value.length() - 2 ); 
			}
		}	
		return new BigDecimal( value );
	}
}
