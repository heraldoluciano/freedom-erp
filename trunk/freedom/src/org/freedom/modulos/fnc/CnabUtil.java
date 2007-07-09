package org.freedom.modulos.fnc;

import java.math.BigDecimal;
import java.util.Date;

import org.freedom.funcoes.Funcoes;


class CnabUtil extends FbnUtil {
	
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
			}
			catch ( Exception e ) {	
				throw new ExceptionCnab( "CNAB registro 1.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		@Override
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
					setRazEmp( line.substring( 74, 103 ) );
					setMsg1( line.substring( 103, 143 ) );
					setMsg2( line.substring( 143, 183 ) );
					setNrRemRet( line.substring( 183, 191 ).trim().length() > 0 ? Integer.parseInt( line.substring( 183, 191 ).trim() ) : 0 );
					setDataRemRet( Funcoes.encodeDate( Integer.parseInt( line.substring( 191, 193 ).trim() ), Integer.parseInt( line.substring( 193, 195 ).trim() ), Integer.parseInt( line.substring( 195, 199 ).trim() ) ) );
					setDataCred( Funcoes.encodeDate( Integer.parseInt( line.substring( 199, 201 ).trim() ), Integer.parseInt( line.substring( 201, 203 ).trim() ), Integer.parseInt( line.substring( 203, 207 ).trim() ) ) );
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
			}
			catch ( Exception e ) {	
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

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#getLine()
		 */
		@Override
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
				line.append( format( getDigAgenciaCob(), ETipo.X, 1, 0 ) );
				line.append( format( getEspecieTit(), ETipo.$9, 2, 0 ) );
				line.append( format( getAceite(), ETipo.X, 1, 0 ) );
				line.append( dateToString( getDtEmitTit() ) );
				line.append( format( getCodJuros(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDtJuros() ) );
				line.append( format( getVlrJurosTaxa(), ETipo.$9, 15, 2 ) ); 
				line.append( format( getCodDesc(), ETipo.$9, 1, 0 ) );
				line.append( dateToString( getDtDesc() ) );
				line.append( format( getVlrpercConced(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrIOF(), ETipo.$9, 13, 2 ) );
				line.append( format( getVlrAbatimento(), ETipo.$9, 15, 2 ) );
				line.append( format( getIdentTitEmp(), ETipo.X, 25, 0 ) );
				line.append( format( getCodProtesto(), ETipo.$9, 1, 0 ) );
				line.append( format( getDiasProtesto(), ETipo.$9, 2, 0 ) );
				line.append( format( getCodBaixaDev(), ETipo.$9, 1, 0 ) );
				line.append( format( getDiasBaixaDevol(), ETipo.$9, 3, 0 ) );  
				line.append( format( getCodMoeda(), ETipo.$9, 2, 0 ) );
				line.append( format( getContrOperCred(), ETipo.$9, 10, 0 ) );
			}
			catch ( Exception e ) {	
				throw new ExceptionCnab( "CNAB registro 3 segmento P.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}
		
		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#parseLine(java.lang.String)
		 */
		@Override
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
					setDtVencTitulo( Funcoes.encodeDate( Integer.parseInt( line.substring( 77, 79 ).trim() ), Integer.parseInt( line.substring( 79, 81 ).trim() ), Integer.parseInt( line.substring( 81, 85 ).trim() ) ) );
					setVlrTitulo( strToBigDecimal( line.substring( 85, 100 ) ) );
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
		//private String sufxCepCli;
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
		
		public void setCodCompensacao( final int codCompensacao ) {		
			this.codCompensacao = codCompensacao;
		}
		
		public String getCpfCnpjAva() {		
			return cpfCnpjAva;
		}
		
		public void setCpfCnpjAva( final String cpfCnpjAva ) {		
			this.cpfCnpjAva = cpfCnpjAva;
		}
		
		public String getCpfCnpjCli() {		
			return cpfCnpjCli;
		}
		
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
		
		public void setNossoNumero( final String nossoNumero ) {		
			this.nossoNumero = nossoNumero;
		}
		
		public String getRazAva() {		
			return razAva;
		}
		
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
		
		public void setTipoInscAva( final int tipoInscAva ) {		
			this.tipoInscAva = tipoInscAva;
		}
		
		public int getTipoInscCli() {		
			return tipoInscCli;
		}
		
		public void setTipoInscCli( final int tipoInscCli ) {		
			this.tipoInscCli = tipoInscCli;
		}
		
		public String getUfCli() {		
			return ufCli;
		}
		
		public void setUfCli( final String ufCli ) {		
			this.ufCli = ufCli;
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#getLine()
		 */
		@Override
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();
			
			try {
				
				line.append( super.getLineReg3() );
				line.append( format( getTipoInscCli(), ETipo.$9, 1, 0 ) );
				line.append( format( getCpfCnpjCli(), ETipo.X, 15, 0 ) );
				line.append( format( getRazCli(), ETipo.X, 40, 0 ) );
				line.append( format( getEndCli(), ETipo.X, 40, 0 ) );
				line.append( format( getBairCli(), ETipo.X, 15, 0 ) );
				line.append( format( getCepCli(), ETipo.$9, 8, 0 ) );
				line.append( format( getCidCli(), ETipo.X, 15, 0 ) );
				line.append( format( getUfCli(), ETipo.X, 2, 0 ) );
				line.append( format( getTipoInscAva(), ETipo.$9, 1, 0 ) );
				line.append( format( getCpfCnpjAva(), ETipo.X, 15, 0 ) );
				line.append( format( getRazAva(), ETipo.X, 40, 0 ) );
				line.append( format( getCodCompensacao(), ETipo.$9, 3, 0 ) );
				line.append( format( getNossoNumero(), ETipo.X, 20, 0 ) );
				line.append( Funcoes.replicate( " ", 8 ) );
			}
			catch ( Exception e ) {	
				throw new ExceptionCnab( "CNAB registro 3 segmento Q.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#parseLine(java.lang.String)
		 */
		@Override
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
		
		public void setCodDesc2( final int codDesc2 ) {		
			this.codDesc2 = codDesc2;
		}
		
		public int getCodDesc3() {		
			return codDesc3;
		}
		
		public void setCodDesc3( final int codDesc3 ) {		
			this.codDesc3 = codDesc3;
		}
		
		public int getCodMulta() {		
			return codMulta;
		}
		
		public void setCodMulta( final int codMulta ) {		
			this.codMulta = codMulta;
		}
		
		public int getCodOcorrSacado() {		
			return codOcorrSacado;
		}
		
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
		
		public void setMsg3( final String msg3 ) {		
			this.msg3 = msg3;
		}
		
		public String getMsg4() {		
			return msg4;
		}
		
		public void setMsg4( final String msg4 ) {		
			this.msg4 = msg4;
		}
		
		public String getMsgSacado() {		
			return msgSacado;
		}
		
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

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#getLine()
		 */
		@Override
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
			}
			catch ( Exception e ) {	
				throw new ExceptionCnab( "CNAB registro 3 segmento R.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#parseLine(java.lang.String)
		 */
		@Override
		public void parseLine( String line ) throws ExceptionCnab {
			
			try {

				if ( line == null ) {					
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					super.parseLineReg3( line );
					setCodDesc2( line.substring( 17, 18 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 18 ).trim() ) : 0 );
					setDataDesc2( Funcoes.encodeDate( Integer.parseInt( line.substring( 18, 20 ).trim() ), Integer.parseInt( line.substring( 20, 22 ).trim() ), Integer.parseInt( line.substring( 22, 26 ).trim() ) ) );
					setVlrPercConced2( strToBigDecimal( line.substring( 26, 41 ) ) );
					setCodDesc3( line.substring( 41, 42 ).trim().length() > 0 ? Integer.parseInt( line.substring( 41, 42 ).trim() ) : 0 );
					setDataDesc3( Funcoes.encodeDate( Integer.parseInt( line.substring( 42, 44 ).trim() ), Integer.parseInt( line.substring( 44, 46 ).trim() ), Integer.parseInt( line.substring( 46, 50 ).trim() ) ) );
					setVlrPercConced3( strToBigDecimal( line.substring( 50, 65 ) ) );
					setCodMulta( line.substring( 65, 66 ).trim().length() > 0 ? Integer.parseInt( line.substring( 65, 66 ).trim() ) : 0 );
					setDataMulta( Funcoes.encodeDate( Integer.parseInt( line.substring( 66, 68 ).trim() ), Integer.parseInt( line.substring( 68, 70 ).trim() ), Integer.parseInt( line.substring( 70, 74 ).trim() ) ) );
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
		
		public int getLinhaImp() {		
			return linhaImp;
		}
		
		public void setLinhaImp( final int linhaImp ) {		
			this.linhaImp = linhaImp;
		}
		
		public String getMsg5() {		
			return msg5;
		}
		
		public void setMsg5( final String msg5 ) {		
			this.msg5 = msg5;
		}
		
		public String getMsg6() {		
			return msg6;
		}
		
		public void setMsg6( final String msg6 ) {		
			this.msg6 = msg6;
		}
		
		public String getMsg7() {		
			return msg7;
		}
		
		public void setMsg7( final String msg7 ) {		
			this.msg7 = msg7;
		}
		
		public String getMsg8() {		
			return msg8;
		}
		
		public void setMsg8( final String msg8 ) {		
			this.msg8 = msg8;
		}
		
		public String getMsg9() {		
			return msg9;
		}
		
		public void setMsg9( final String msg9 ) {		
			this.msg9 = msg9;
		}
		
		public String getMsgImp() {		
			return msgImp;
		}
		
		public void setMsgImp( final String msgImp ) {		
			this.msgImp = msgImp;
		}
		
		public int getTipoChar() {		
			return tipoChar;
		}
		
		public void setTipoChar( final int tipoChar ) {		
			this.tipoChar = tipoChar;
		}
		
		public int getTipoImpressao() {		
			return tipoImpressao;
		}
		
		public void setTipoImpressao( final int tipoImpressao ) {		
			this.tipoImpressao = tipoImpressao;
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#getLine()
		 */
		@Override
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
				else if ( getTipoImpressao() == 3 )  {
				
					line.append( format( getMsg5(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg6(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg7(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg8(), ETipo.X, 40, 0 ) );
					line.append( format( getMsg9(), ETipo.X, 40, 0 ) );
					line.append( Funcoes.replicate( " ", 22 ) );
				}
			}
			catch ( Exception e ) {	
				throw new ExceptionCnab( "CNAB registro 3 segmento S.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@Override
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
					else if ( getTipoImpressao() == 3 )  {
					
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
		
		public void setCarteira( final int carteira ) {		
			this.carteira = carteira;
		}
		
		public String getCodBanco() {		
			return codBanco;
		}
		
		public void setCodBanco( final String codBanco ) {		
			this.codBanco = codBanco;
		}
		
		public int getCodMoeda() {		
			return codMoeda;
		}
		
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

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#getLine()
		 */
		@Override
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
			}
			catch ( Exception e ) {	
				throw new ExceptionCnab( "CNAB registro 3 segmento T.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@Override
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
					setDataVencTit( Funcoes.encodeDate( Integer.parseInt( line.substring( 73, 75 ).trim() ), Integer.parseInt( line.substring( 75, 77 ).trim() ), Integer.parseInt( line.substring( 77, 81 ).trim() ) ) );
					setVlrTitulo( strToBigDecimal( line.substring( 81, 96 ) ) );
					setCodBanco( line.substring( 96, 99 ) );
					setAgenciaCob( line.substring( 99, 104 ) );
					setDigAgenciaCob( line.substring( 104, 105 ) );
					setIdentTitEmp( line.substring( 150, 130 ) );
					setCodMoeda( line.substring( 130, 132 ).trim().length() > 0 ? Integer.parseInt( line.substring( 130, 132 ).trim() ) : 0 );
					setTipoInscCli( line.substring( 132, 133 ).trim().length() > 0 ? Integer.parseInt( line.substring( 132, 133 ).trim() ) : 0 );
					setCpfCnpjCli( line.substring( 133, 148 ) );
					setRazCli( line.substring( 148, 188 ) );
					setContratoCred( line.substring( 188, 198 ) );
					setVlrTarifa( strToBigDecimal( line.substring( 198, 213 ) ) ); 
					setCodRejeicoes( line.substring( 213, 223 ) );
				}
			} catch ( Exception e ) {
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
		
		public void setVrlAbatCancel( final BigDecimal vrlAbatCancel ) {		
			this.vrlAbatCancel = vrlAbatCancel;
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#getLine()
		 */
		@Override
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
			}
			catch ( Exception e ) {	
				throw new ExceptionCnab( "CNAB registro 3 segmento T.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {					
					throw new ExceptionCnab( "Linha nula." );
				}
				else {

					super.parseLineReg3( line );
					setVlrJurosMulta( strToBigDecimal( line.substring( 17, 32 ) ) );
					setVlrDesc( strToBigDecimal( line.substring( 32, 47 ) ) );
					setVrlAbatCancel( strToBigDecimal( line.substring( 47, 62 ) ) );
					setVlrIOF( strToBigDecimal( line.substring( 62, 77 ) ) );
					setVlrPago( strToBigDecimal( line.substring( 77, 92 ) ) );
					setVlrLiqCred( strToBigDecimal( line.substring( 92, 107 ) ) );
					setVlrOutrasDesp( strToBigDecimal( line.substring( 107, 122 ) ) );
					setVlrOutrosCred( strToBigDecimal( line.substring( 122, 137 ) ) );
					setDataOcorr( Funcoes.encodeDate( Integer.parseInt( line.substring( 137, 139 ).trim() ), Integer.parseInt( line.substring( 139, 141 ).trim() ), Integer.parseInt( line.substring( 141, 145 ).trim() ) ) );
					setDataEfetvCred( Funcoes.encodeDate( Integer.parseInt( line.substring( 145, 147 ).trim() ), Integer.parseInt( line.substring( 147, 149 ).trim() ), Integer.parseInt( line.substring( 149, 153 ).trim() ) ) );
					setCodOcorrSac( line.substring( 153, 157 ) );
					setDataOcorrSac( Funcoes.encodeDate( Integer.parseInt( line.substring( 157, 159 ).trim() ), Integer.parseInt( line.substring( 159, 161 ).trim() ), Integer.parseInt( line.substring( 161, 165 ).trim() ) ) );
					setVlrOcorrSac( strToBigDecimal( line.substring( 165, 180 ) ) );
					setCompOcorrSac( line.substring( 180, 210 ) );
					setCodBancoCompens( line.substring( 210, 213 ) );
					setNossoNrCompens( line.substring( 213, 233 ) );
				}
			} catch ( Exception e ) {
				throw new ExceptionCnab( "CNAB registro 3 segmento U.\nErro ao ler registro.\n" + e.getMessage() );
			}
		}
	}
		
	class Reg5 extends Reg {
		
		private String codBanco;
		private int loteServico;
		private int registroTraler;
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
			
			setRegistroTraler( 5 );
		}
				
		public String getAvisoLanca() {		
			return avisoLanca;
		}
		
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
		
		public void setLoteServico( int loteServico ) {		
			this.loteServico = loteServico;
		}
		
		public int getQtdCalculado() {		
			return qtdCalculado;
		}
		
		public void setQtdCalculado( int qtdCalculado ) {		
			this.qtdCalculado = qtdCalculado;
		}
		
		public int getQtdDescontado() {		
			return qtdDescontado;
		}
		
		public void setQtdDescontado( int qtdDescontado ) {		
			this.qtdDescontado = qtdDescontado;
		}
		
		public int getQtdRegistros() {		
			return qtdRegistros;
		}
		
		public void setQtdRegistros( int qtdRegistros ) {		
			this.qtdRegistros = qtdRegistros;
		}
		
		public int getQtdSimples() {		
			return qtdSimples;
		}
		
		public void setQtdSimples( int qtdSimples ) {		
			this.qtdSimples = qtdSimples;
		}
		
		public int getQtdVinculado() {		
			return qtdVinculado;
		}
		
		public void setQtdVinculado( int qtdVinculado ) {		
			this.qtdVinculado = qtdVinculado;
		}
		
		public int getRegistroTraler() {		
			return registroTraler;
		}
		
		private void setRegistroTraler( int registroTraler ) {		
			this.registroTraler = registroTraler;
		}
		
		public BigDecimal getVlrCalculado() {		
			return vlrCalculado;
		}
		
		public void setVlrCalculado( BigDecimal vlrCalculado ) {		
			this.vlrCalculado = vlrCalculado;
		}
		
		public BigDecimal getVlrDescontado() {		
			return vlrDescontado;
		}
		
		public void setVlrDescontado( BigDecimal vlrDescontado ) {		
			this.vlrDescontado = vlrDescontado;
		}
		
		public BigDecimal getVlrSimples() {		
			return vlrSimples;
		}
		
		public void setVlrSimples( BigDecimal vlrSimples ) {		
			this.vlrSimples = vlrSimples;
		}
		
		public BigDecimal getVlrVinculado() {		
			return vlrVinculado;
		}
		
		public void setVlrVinculado( BigDecimal vlrVinculado ) {		
			this.vlrVinculado = vlrVinculado;
		}
		
		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#getLine()
		 */
		@Override
		public String getLine() throws ExceptionCnab {

			StringBuilder line = new StringBuilder();
			
			try {
				
				line.append( format( getCodBanco(), ETipo.$9, 3, 0 ) );
				line.append( format( getLoteServico(), ETipo.$9, 4, 0 ) );
				line.append( format( getRegistroTraler(), ETipo.$9, 1, 0 ) );
				line.append( Funcoes.replicate( " ", 9 ) );
				line.append( format( getQtdRegistros(), ETipo.$9, 6, 0 ) );
				line.append( format( getQtdSimples(), ETipo.$9, 6, 0 ) );
				line.append( format( getVlrSimples(), ETipo.$9, 15, 2 ) );
				line.append( format( getQtdVinculado(), ETipo.$9, 6, 0 ) );
				line.append( format( getVlrVinculado(), ETipo.$9, 15, 2 ) );
				line.append( format( getQtdCalculado(), ETipo.$9, 6, 0 ) );
				line.append( format( getVlrCalculado(), ETipo.$9, 15, 2 ) );
				line.append( format( getQtdDescontado(), ETipo.$9, 6, 0 ) );
				line.append( format( getVlrDescontado(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrDescontado(), ETipo.X, 8, 0 ) );
				line.append( Funcoes.replicate( " ", 117 ) );
			}
			catch ( Exception e ) {	
				throw new ExceptionCnab( "CNAB registro 5.\nErro ao escrever registro.\n" + e.getMessage() );
			}
			
			return line.toString();
		}

		/* (non-Javadoc)
		 * @see org.freedom.modulos.fnc.CnabUtil.Reg#parseLine(java.lang.String)
		 */
		@Override
		public void parseLine( String line ) throws ExceptionCnab {

			try {

				if ( line == null ) {					
					throw new ExceptionCnab( "Linha nula." );
				}
				else {
					
					setCodBanco( line.substring( 0, 3 ) );
					setLoteServico( line.substring( 3, 7 ).trim().length() > 0 ? Integer.parseInt( line.substring( 3, 7 ).trim() ) : 0 );
					setRegistroTraler( line.substring( 7, 8 ).trim().length() > 0 ? Integer.parseInt( line.substring( 7, 8 ).trim() ) : 0 );
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
