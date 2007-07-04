package org.freedom.modulos.fnc;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.freedom.funcoes.Funcoes;

class SiaccUtil extends FbnUtil {
	
	abstract class Reg {
		
		private char tiporeg;

		protected StringBuilder sbreg = new StringBuilder();

		Reg( char codreg )  {

			this.sbreg.append( codreg );
			this.tiporeg = codreg;
		}

		String format( Object obj, ETipo tipo, int tam, int dec ) {

			String retorno = null;
			String str = null;
			// String formato = null;
			if ( obj == null ) {
				str = "";
			}
			else {
				str = obj.toString();
			}
			if ( tipo == ETipo.$9 ) {
				retorno = Funcoes.transValor( str, tam, dec, true );
			}
			else {
				retorno = Funcoes.adicionaEspacos( str, tam );
			}
			return retorno;
		}

		public String toString() {

			return sbreg.toString();
		}
		
		protected abstract void parseLine(String line) throws ExceptionSiacc;
		
		public char getTiporeg() {
			return this.tiporeg;
		}
	}

	class RegA extends Reg {
		
		public static final char CODREG = 'A';	// registro A.01
		private Integer codRemessa = null;		// registro A.02
		private String codConvenio = null;		// registro A.03
		private String nomeEmpresa = null;		// registro A.04
		private Integer codBanco = null;		// registro A.05
		private String nomeBanco = null;		// registro A.06
		private Integer dataMovimento = null;	// registro A.07
		private Integer seqArquivo = null;		// registro A.08
		private Integer versao = null;			// registro A.09
		private String identServ = null;		// registro A.10
		private String contaComp = null;		// registro A.11
		private String identAmbCli = null;		// registro A.12
		private String identAmbBco = null;		// registro A.13
		//private String A14 = null;			// registro A.14
		private Integer seqRegistro = null;		// registro A.15
		//private String A16 = null;			// registro A.16
		
		RegA( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}

		RegA( final char codrem, final Map map, final int numReg ) {

			super( CODREG );
			this.sbreg.append( codrem );
			this.sbreg.append( format( map.get( EPrefs.CODCONV ), ETipo.X, 20, 0 ) );
			this.sbreg.append( format( map.get( EPrefs.NOMEEMP ), ETipo.X, 20, 0 ) );
			this.sbreg.append( format( map.get( EPrefs.CODBANCO ), ETipo.$9, 3, 0 ) );
			this.sbreg.append( format( map.get( EPrefs.NOMEBANCO ), ETipo.X, 20, 0 ) );
			this.sbreg.append( Funcoes.dataAAAAMMDD( new Date() ) );
			this.sbreg.append( format( map.get( EPrefs.NROSEQ ), ETipo.$9, 6, 0 ) );
			this.sbreg.append( format( map.get( EPrefs.VERLAYOUT ), ETipo.$9, 2, 0 ) );
			this.sbreg.append( format( map.get( EPrefs.IDENTSERV ), ETipo.X, 17, 0 ) );
			this.sbreg.append( format( map.get( EPrefs.CONTACOMPR ), ETipo.$9, 16, 0 ) );
			this.sbreg.append( format( map.get( EPrefs.IDENTAMBCLI ), ETipo.X, 1, 0 ) );
			this.sbreg.append( format( map.get( EPrefs.IDENTAMBBCO ), ETipo.X, 1, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 27, 0 ) ); // Reservado para o futuro
			this.sbreg.append( format( numReg, ETipo.$9, 6, 0 ) ); // Número sequencial do registro
			this.sbreg.append( format( "", ETipo.X, 1, 0 ) ); // Reservado para o futuro
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}
		
		protected void parseLine( final String line ) throws ExceptionSiacc {
			
			try {
				setCodRemessa( line.substring( 1, 2 ).trim().length() > 0 ? new Integer( line.substring( 1, 2 ).trim() ) : null );
				setCodConvenio( line.substring( 2, 22 ) );
				setNomeEmpresa( line.substring( 22, 42 ) );
				setCodBanco( line.substring( 42, 45 ).trim().length() > 0 ? new Integer( line.substring( 42, 45 ).trim() ) : null );
				setNomeBanco( line.substring( 45, 65 ) );
				setDataMovimento( line.substring( 65, 73 ).trim().length() > 0 ? new Integer( line.substring( 65, 73 ).trim() ) : null );
				setSeqArquivo( line.substring( 73, 79 ).trim().length() > 0 ? new Integer( line.substring( 73, 79 ).trim() ) : null );
				setVersao( line.substring( 79, 81 ).trim().length() > 0 ? new Integer( line.substring( 79, 81 ).trim() ) : null );
				setIdentServ( line.substring( 81, 98 ) );
				setContaComp( line.substring( 98, 114 ) );
				setIdentAmbCli( line.substring( 114, 115 ) );
				setIdentAmbBco( line.substring( 115, 116 ) );
				//A14 = line.substring( 116, 143 );
				setSeqRegistro( line.substring( 143, 149 ).trim().length() > 0 ?  new Integer( line.substring( 143, 149 ).trim() ) : null );
				//A16 = line.substring( 149 );
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro no registro tipo A!\n"+e.getMessage());
			}
		}
		
		public Integer getCodBanco() {		
			return codBanco;
		}
		
		public void setCodBanco( final Integer codBanco ) {		
			this.codBanco = codBanco;
		}
		
		public String getCodConvenio() {		
			return codConvenio;
		}
		
		public void setCodConvenio( final String codConvenio ) {		
			this.codConvenio = codConvenio;
		}
		
		public Integer getCodRemessa() {		
			return codRemessa;
		}
		
		public void setCodRemessa( final Integer codRemessa ) {		
			this.codRemessa = codRemessa;
		}
		
		public String getContaComp() {		
			return contaComp;
		}
		
		public void setContaComp( final String contaComp ) {		
			this.contaComp = contaComp;
		}
		
		public Integer getDataMovimento() {		
			return dataMovimento;
		}
		
		public void setDataMovimento( final Integer dataMovimento ) {		
			this.dataMovimento = dataMovimento;
		}
		
		public String getIdentAmbCaixa() {		
			return identAmbBco;
		}
		
		public void setIdentAmbBco( final String identAmbCaixa ) {		
			this.identAmbBco = identAmbCaixa;
		}
		
		public String getIdentAmbCli() {		
			return identAmbCli;
		}
		
		public void setIdentAmbCli( final String identAmbCli ) {		
			this.identAmbCli = identAmbCli;
		}
		
		public String getIdentServ() {		
			return identServ;
		}
		
		public void setIdentServ( final String identServ ) {		
			this.identServ = identServ;
		}
		
		public String getNomeBanco() {		
			return nomeBanco;
		}
		
		public void setNomeBanco( final String nomeBanco ) {		
			this.nomeBanco = nomeBanco;
		}
		
		public String getNomeEmpresa() {		
			return nomeEmpresa;
		}
		
		public void setNomeEmpresa( final String nomeEmpresa ) {		
			this.nomeEmpresa = nomeEmpresa;
		}
		
		public Integer getSeqArquivo() {		
			return seqArquivo;
		}
		
		public void setSeqArquivo( final Integer seqArquivo ) {		
			this.seqArquivo = seqArquivo;
		}
		
		public Integer getSeqRegistro() {		
			return seqRegistro;
		}
		
		public void setSeqRegistro( final Integer seqRegistro ) {		
			this.seqRegistro = seqRegistro;
		}
		
		public Integer getVersao() {		
			return versao;
		}
		
		public void setVersao( final Integer versao ) {		
			this.versao = versao;
		}
	}

	class RegB extends Reg {

		private final char COD_MOV = '2';
		private static final char CODREG = 'B'; // registro B.01
		private String identCliEmp = null;		// registro B.02
		private Integer agenciaDebt = null;		// registro B.03
		private String identCliBco = null;		// registro B.04
		private Integer dataOpcao = null;		// registro B.05				
		//private String B06 = null;			// registro B.06
		private Integer codMovimento = null;	// registro B.07
		//private Integer B08 = null;			// registro B.08

		RegB( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}
		
		RegB( final char codreg, final StuffCli stfCli ) {

			super( codreg );
			this.sbreg.append( format( stfCli.getCodigo(), ETipo.$9, 10, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 15, 0 ) ); // Completar a identificação do cliente na empresa
			this.sbreg.append( format( stfCli.getArgs()[ EColcli.AGENCIACLI.ordinal() ], ETipo.$9, 4, 0 ) );
			this.sbreg.append( format( stfCli.getArgs()[ EColcli.IDENTCLI.ordinal() ], ETipo.X, 14, 0 ) );
			this.sbreg.append( Funcoes.dataAAAAMMDD( new Date() ) );
			this.sbreg.append( format( "", ETipo.X, 96, 0 ) ); // Reservado para o futuro
			this.sbreg.append( COD_MOV );
			this.sbreg.append( format( "", ETipo.X, 1, 0 ) ); // Reservado para o futuro
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}

		protected void parseLine( final String line ) throws ExceptionSiacc {
			try {
				setIdentCliEmp( line.substring( 1, 26 ) );
				setAgenciaDebt( line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ).trim() ) : null );
				setIdentCliBanco( line.substring( 30, 44 ) );
				setDataOpcao( line.substring(  44, 52  ).trim().length() > 0 ? new Integer( line.substring( 44, 52 ).trim() ) : null );
				//B06 = line.substring( 52, 148 );
				setCodMovimento( line.substring( 148, 149 ).trim().length() > 0 ? new Integer( line.substring( 148, 149 ).trim() ) : null );
				//B08 = line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ).trim() ) : null;
			}
			catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro B!\n" + e.getMessage());
			}
		}
		
		public Integer getAgenciaDebt() {		
			return agenciaDebt;
		}
		
		public void setAgenciaDebt( final Integer agenciaDebt ) {		
			this.agenciaDebt = agenciaDebt;
		}
		
		public Integer getCodMovimento() {		
			return codMovimento;
		}
		
		public void setCodMovimento( final Integer codMovimento ) {		
			this.codMovimento = codMovimento;
		}
		
		public java.util.Date getDataOpcao() throws Exception {		
			return strToDate( String.valueOf( dataOpcao ) );
		}
		
		public void setDataOpcao( final Integer dataOpcao ) {		
			this.dataOpcao = dataOpcao;
		}
		
		public String getIdentCliBco() {		
			return identCliBco;
		}
		
		public void setIdentCliBanco( final String identCliBanco ) {		
			this.identCliBco = identCliBanco;
		}
		
		public String getIdentCliEmp() {		
			return identCliEmp;
		}
		
		public void setIdentCliEmp( final String identCliEmp ) {		
			this.identCliEmp = identCliEmp;
		}		
	}

	class RegC extends Reg {

		private final char COD_MOV = '2';
		private static final char CODREG = 'C'; // registro C.01
		private String identCliEmp = null;		// registro C.02
		private Integer agenciaDeb = null;		// registro C.03
		private String identCliBanco = null;	// registro C.04
		private String ocorrencia1 = null;		// registro C.05
		private String ocorrencia2 = null;		// registro C.06
		//private String C07 = null;			// registro C.07
		private Integer seqRegistro = null;		// registro C.08
		private Integer codMovimento = null;	// registro C.09
		
		RegC( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}
		
		RegC( final char codreg, final StuffCli stfCli, final int numSeq ) {

			super( codreg );
			this.sbreg.append( format( stfCli.getCodigo(), ETipo.$9, 10, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 15, 0 ) ); // Completar a identificação do cliente na empresa
			this.sbreg.append( format( stfCli.getArgs()[ EColcli.AGENCIACLI.ordinal() ], ETipo.$9, 4, 0 ) );
			this.sbreg.append( format( stfCli.getArgs()[ EColcli.IDENTCLI.ordinal() ], ETipo.X, 14, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 40, 0 ) ); // Ocorrencia 1
			this.sbreg.append( format( "", ETipo.X, 40, 0 ) ); // Ocorrencia 2
			this.sbreg.append( format( "", ETipo.X, 19, 0 ) ); // Reservado para o futuro
			this.sbreg.append( format( numSeq, ETipo.$9, 6, 0 ) ); // Reservado para o futuro
			this.sbreg.append( COD_MOV );
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}

		protected void parseLine( final String line ) throws ExceptionSiacc {
			try {
				identCliEmp = line.substring( 1, 26 );
				agenciaDeb = line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ) ) : null;
				identCliBanco = line.substring( 30, 44 );
				ocorrencia1 = line.substring( 44, 84 );
				ocorrencia2 = line.substring( 84, 124 );
				//C07 = line.substring( 124, 143 );
				seqRegistro = line.substring( 143, 149 ).trim().length() > 0 ? new Integer( line.substring( 143, 149 ) ) : null;
				codMovimento = line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null;
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro C!\n" + e.getMessage());
			}
			
		}
		
		public Integer getAgenciaDeb() {		
			return agenciaDeb;
		}
		
		public void setAgenciaDeb( final Integer agenciaDeb ) {		
			this.agenciaDeb = agenciaDeb;
		}
		
		public Integer getCodMovimento() {		
			return codMovimento;
		}
		
		public void setCodMovimento( final Integer codMovimento ) {		
			this.codMovimento = codMovimento;
		}
		
		public String getIdentCliBanco() {		
			return identCliBanco;
		}
		
		public void setIdentCliBanco( final String identCliBanco ) {		
			this.identCliBanco = identCliBanco;
		}
		
		public String getIdentCliEmp() {		
			return identCliEmp;
		}
		
		public void setIdentCliEmp( final String identCliEmp ) {		
			this.identCliEmp = identCliEmp;
		}
		
		public String getOcorrencia1() {		
			return ocorrencia1;
		}
		
		public void setOcorrencia1( final String ocorrencia1 ) {		
			this.ocorrencia1 = ocorrencia1;
		}
		
		public String getOcorrencia2() {		
			return ocorrencia2;
		}
		
		public void setOcorrencia2( final String ocorrencia2 ) {		
			this.ocorrencia2 = ocorrencia2;
		}
		
		public Integer getSeqRegistro() {		
			return seqRegistro;
		}
		
		public void setSeqRegistro( final Integer seqRegistro ) {		
			this.seqRegistro = seqRegistro;
		}
	}

	class RegD extends Reg {

		private final char COD_MOV = '0';
		private static final char CODREG = 'D'; // registro D.01
		private String identCliEmpAnt = null;	// registro D.02
		private Integer agenciaDeb = null;		// registro D.03
		private String identCliBanco = null;	// registro D.04
		private String idetCliEmpAtual = null;	// registro D.05
		private String ocorrencia = null;		// registro D.06
		//private String C07 = null;			// registro D.07
		private Integer seqRegistro = null;		// registro D.08
		private Integer codMovimento = null;	// registro D.09
		
		RegD( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}

		RegD( final char codreg, final StuffCli stfCli, final int numSeq ) {

			super( codreg );
			this.sbreg.append( format( stfCli.getCodigo(), ETipo.$9, 10, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 15, 0 ) ); // Completar a identificação do cliente na empresa
			this.sbreg.append( format( stfCli.getArgs()[ EColcli.AGENCIACLI.ordinal() ], ETipo.$9, 4, 0 ) );
			this.sbreg.append( format( stfCli.getArgs()[ EColcli.IDENTCLI.ordinal() ], ETipo.X, 14, 0 ) );
			this.sbreg.append( format( stfCli.getCodigo(), ETipo.$9, 10, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 15, 0 ) ); // Completar a identificação do cliente na empresa
			this.sbreg.append( format( "", ETipo.X, 60, 0 ) ); // Ocorrencia
			this.sbreg.append( format( "", ETipo.X, 14, 0 ) ); // Reservado para o futuro
			this.sbreg.append( format( numSeq, ETipo.$9, 6, 0 ) ); // Reservado para o futuro
			this.sbreg.append( COD_MOV );
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}

		protected void parseLine( final String line ) throws ExceptionSiacc {


			try {
				setIdentCliEmpAnt( line.substring( 1, 26 ) );
				setAgenciaDeb( line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ) ) : null );
				setIdentCliBanco( line.substring( 30, 44 ) );
				setIdetCliEmpAtual( line.substring( 44, 69 ) );
				setOcorrencia( line.substring( 69, 129 ) );
				//C07 = line.substring( 124, 143 );
				setSeqRegistro( line.substring( 143, 149 ).trim().length() > 0 ? new Integer( line.substring( 143, 149 ) ) : null );
				setCodMovimento( line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null );
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro D!\n " + e.getMessage());
			}

		}
		
		public Integer getAgenciaDeb() {		
			return agenciaDeb;
		}
		
		public void setAgenciaDeb( final Integer agenciaDeb ) {		
			this.agenciaDeb = agenciaDeb;
		}
		
		public Integer getCodMovimento() {		
			return codMovimento;
		}
		
		public void setCodMovimento( final Integer codMovimento ) {		
			this.codMovimento = codMovimento;
		}
		
		public String getIdentCliBanco() {		
			return identCliBanco;
		}
		
		public void setIdentCliBanco( final String identCliBanco ) {		
			this.identCliBanco = identCliBanco;
		}
		
		public String getIdentCliEmpAnt() {		
			return identCliEmpAnt;
		}
		
		public void setIdentCliEmpAnt( final String identCliEmpAnt ) {		
			this.identCliEmpAnt = identCliEmpAnt;
		}
		
		public String getIdetCliEmpAtual() {		
			return idetCliEmpAtual;
		}
		
		public void setIdetCliEmpAtual( final String idetCliEmpAtual ) {		
			this.idetCliEmpAtual = idetCliEmpAtual;
		}
		
		public String getOcorrencia() {		
			return ocorrencia;
		}
		
		public void setOcorrencia( final String ocorrencia ) {		
			this.ocorrencia = ocorrencia;
		}
		
		public Integer getSeqRegistro() {		
			return seqRegistro;
		}
		
		public void setSeqRegistro( final Integer seqRegistro ) {		
			this.seqRegistro = seqRegistro;
		}		
	}

	class RegE extends Reg {

		private final char COD_MOV = '0';
		private final String COD_MOEDA = "03";		
		private static final char CODREG = 'E'; // registro E.01
		private String identCliEmp = null;		// registro E.02
		private Integer agenciaDebCred = null;	// registro E.03
		private String identCliBanco = null;	// registro E.04
		private Integer dataVenc = null;		// registro E.05
		private Long valorDebCred = null;		// registro E.06
		private String codMoeda = null;			// registro E.07
		private String usoEmp = null;			// registro E.08
		private Integer numAgendCli = null;		// registro E.09
		//private Integer E10 = null;			// registro E.10
		private Integer seqRegistro = null;		// registro E.11
		private Integer codMovimento = null;	// registro E.12
		private BigDecimal vlrParc = null;
		private Integer codRec = null;
		private Integer nparcItRec = null;
		
		RegE( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}

		RegE( final char codreg, final StuffRec stfRec, final int numSeq, final int numAgenda ) {

			super( codreg );
			setCodRec(stfRec.getCodrec());
			setNparcItRec(stfRec.getNParcitrec());
			setUsoEmp(format(getCodRec(),ETipo.$9,6,0)+format(getNparcItRec(),ETipo.$9,4,0));
			setVlrParc( new BigDecimal( stfRec.getArgs()[ EColrec.VLRAPAG.ordinal() ] ));
			setIdentCliEmp( stfRec.getArgs()[ EColrec.PESSOACLI.ordinal() ], 
					stfRec.getArgs()[ EColrec.CPFCLI.ordinal() ],
					stfRec.getArgs()[ EColrec.CNPJCLI.ordinal() ]);
			this.sbreg.append( getIdentCliEmp() );
			this.sbreg.append( format( stfRec.getArgs()[ EColrec.AGENCIACLI.ordinal() ], ETipo.$9, 4, 0 ) );
			this.sbreg.append( format( stfRec.getArgs()[ EColrec.IDENTCLI.ordinal() ], ETipo.X, 14, 0 ) );
			this.sbreg.append( format( stfRec.getArgs()[ EColrec.DTVENC.ordinal() ], ETipo.$9, 8, 0 ) );
			this.sbreg.append( format( Funcoes.transValor(vlrParc, 15, 2, true), ETipo.$9, 15, 0 ) );
			this.sbreg.append( COD_MOEDA );
			this.sbreg.append( format( getUsoEmp(), ETipo.X, 60, 0 ) ); // Uso da empresa
			this.sbreg.append( format( numAgenda, ETipo.$9, 6, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 8, 0 ) ); // Reservado para o futuro
			this.sbreg.append( format( numSeq, ETipo.$9, 6, 0 ) ); // Reservado para o futuro
			this.sbreg.append( COD_MOV );
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}
		
		protected void parseLine( final String line ) throws ExceptionSiacc {
			
			try {
				setIdentCliEmp( line.substring( 1, 26 ) );
				setAgenciaDebCred( line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ) ) : null );
				setIdentCliBanco( line.substring( 30, 44 ) );
				setDataVenc( line.substring( 44, 52 ).trim().length() > 0 ? new Integer( line.substring( 44, 52 ) ) : null );
				setValorDebCred( line.substring( 52, 67 ).trim().length() > 0 ? new Long( line.substring( 52, 67 ) ) : null );
				setCodMoeda( line.substring( 67, 69 ) );
				setUsoEmp( line.substring( 69, 129 ) );
				setNumAgendCli( line.substring( 129, 135 ).trim().length() > 0 ? new Integer( line.substring( 129, 135 ) ) : null );
				//E10( line.substring( 135, 143 ) );
				setSeqRegistro( line.substring( 143, 149 ).trim().length() > 0 ? new Integer( line.substring( 143, 149 ) ) : null );
				setCodMovimento( line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null );
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro E!\n" + e.getMessage());
			}
		}
		
		public Integer getAgenciaDebCred() {		
			return agenciaDebCred;
		}
		
		public void setAgenciaDebCred( final Integer agenciaDebCred ) {		
			this.agenciaDebCred = agenciaDebCred;
		}
		
		public String getCodMoeda() {		
			return codMoeda;
		}
		
		public void setCodMoeda( final String codMoeda ) {		
			this.codMoeda = codMoeda;
		}
		
		public Integer getCodMovimento() {		
			return codMovimento;
		}
		
		public void setCodMovimento( final Integer codMovimento ) {		
			this.codMovimento = codMovimento;
		}
		
		public Integer getDataVenc() {		
			return dataVenc;
		}
		
		public void setDataVenc( final Integer dataVenc ) {		
			this.dataVenc = dataVenc;
		}
		
		public String getIdentCliBanco() {		
			return identCliBanco;
		}
		
		public void setIdentCliBanco( final String identCliBanco ) {		
			this.identCliBanco = identCliBanco;
		}
		
		public String getIdentCliEmp() {		
			return identCliEmp;
		}
		
	    public void setIdentCliEmp(final String pessoaCli, final String cpfCli, final String cnpjCli) {
	    	String tmpIdent = null;
	    	if ("F".equals( pessoaCli )) {
	    		tmpIdent = format( cpfCli, ETipo.$9, 11, 0 );
	    	} else {
	    		tmpIdent = format( cnpjCli, ETipo.$9, 14, 0 );
	    	}
	    	tmpIdent += format("0", ETipo.$9, 25 - tmpIdent.length(), 0);
	    	setIdentCliEmp( tmpIdent );
	    }
		
		public void setIdentCliEmp( final String identCliEmp ) {		
			this.identCliEmp = identCliEmp;
		}
		
		public Integer getNumAgendCli() {		
			return numAgendCli;
		}
		
		public void setNumAgendCli( final Integer numAgendCli ) {		
			this.numAgendCli = numAgendCli;
		}
		
		public Integer getSeqRegistro() {		
			return seqRegistro;
		}
		
		public void setSeqRegistro( final Integer seqRegistro ) {		
			this.seqRegistro = seqRegistro;
		}
		
		public String getUsoEmp() {		
			return usoEmp;
		}
		
		public void setUsoEmp( final String usoEmp ) {
			this.usoEmp = usoEmp;
			String codrec = this.usoEmp.substring( 0, 6 );
			String numparcrec = this.usoEmp.substring( 6, 10 );
			if ( codrec != null && codrec.trim().length() > 0 ) {
				setCodRec( Integer.parseInt( codrec.trim() ) );
			}
			if ( numparcrec != null && numparcrec.trim().length() > 0 ) {			
				setNparcItRec( Integer.parseInt( numparcrec.trim() ) );
			}
		}
		
		public Long getValorDebCred() {		
			return valorDebCred;
		}
		
		public void setValorDebCred( final Long valorDebCred ) {		
			this.valorDebCred = valorDebCred;
		}
		
		public BigDecimal getVlrParc() {		
			return vlrParc;
		}
		
		public void setVlrParc( BigDecimal vlrParc ) {		
			this.vlrParc = vlrParc;
		}
		
		public Integer getCodRec() {		
			return codRec;
		}
		
		public void setCodRec( final Integer codRec ) {		
			this.codRec = codRec;
		}
		
		public Integer getNparcItRec() {		
			return nparcItRec;
		}
		
		public void setNparcItRec( final Integer nparcItRec ) {		
			this.nparcItRec = nparcItRec;
		}		
	}

	class RegF extends Reg {
		
		private static final char CODREG = 'F'; // registro F.01
		private String identCliEmp = null;		// registro F.02
		private Integer agencia = null;			// registro F.03
		private String identCliBanco = null;	// registro F.04
		private Integer dataVenc = null;		// registro F.05
		private String valorDebCred = null;		// registro F.06
		private String codRetorno = null;		// registro F.07
		private String usoEmp = null;			// registro F.08
		//private String F09 = null;			// registro F.09
		private Integer codMovimento = null;	// registro F.10
		private Integer codRec = null;
		private Integer nparcItRec = null;
		
		RegF( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}
		
		protected void parseLine( final String line ) throws ExceptionSiacc {
			
			try {
				setIdentCliEmp( line.substring( 1, 26 ) );
				setAgencia( line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ) ) : null );
				setIdentCliBanco( line.substring( 30, 44 ) );
				setDataVenc( line.substring( 44, 52 ).trim().length() > 0 ? new Integer( line.substring( 44, 52 ) ) : null );
				setValorDebCred( line.substring( 52, 67 ) );
				setCodRetorno( line.substring( 67, 69 ) );
				setUsoEmp( line.substring( 69, 129 ) );
				//	F09 line.substring( 129, 149 ) );
				setCodMovimento( line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null );
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro F!\n" + e.getMessage());
			}
		}
		
		public Integer getAgencia() {		
			return agencia;
		}
		
		public void setAgencia( final Integer agencia ) {		
			this.agencia = agencia;
		}
		
		public Integer getCodMovimento() {		
			return codMovimento;
		}
		
		public void setCodMovimento( final Integer codMovimento ) {		
			this.codMovimento = codMovimento;
		}
		
		public String getCodRetorno() {		
			return codRetorno;
		}
		
		public void setCodRetorno( final String codRet ) {		
			this.codRetorno = codRet;
		}
		
		public java.util.Date getDataVenc() throws Exception {		
			return strToDate( String.valueOf( dataVenc ) );
		}
		
		public void setDataVenc( final Integer dataVenc ) {		
			this.dataVenc = dataVenc;
		}
		
		public String getIdentCliBanco() {		
			return identCliBanco;
		}
		
		public void setIdentCliBanco( final String identCliBanco ) {		
			this.identCliBanco = identCliBanco;
		}
		
		public String getIdentCliEmp() {		
			return identCliEmp;
		}
		
		public void setIdentCliEmp( final String identCliEmp ) {		
			this.identCliEmp = identCliEmp;
		}
		
		public String getUsoEmp() {		
			return usoEmp;
		}
		
		public void setUsoEmp( final String usoEmp ) {		
			this.usoEmp = usoEmp;		
			String codrec = this.usoEmp.substring( 0, 6 );
			String numparcrec = this.usoEmp.substring( 6, 10 );
			if ( codrec != null && codrec.trim().length() > 0 ) {
				setCodRec( Integer.parseInt( codrec.trim() ) );
			}
			if ( numparcrec != null && numparcrec.trim().length() > 0 ) {			
				setNparcItRec( Integer.parseInt( numparcrec.trim() ) );
			}
		}
		
		public BigDecimal getValorDebCred() {
			return strToBigDecimal( valorDebCred );
		}
		
		public void setValorDebCred( final String valor ) {		
			this.valorDebCred = valor;
		}
		
		public Integer getCodRec() {		
			return codRec;
		}
		
		public void setCodRec( final Integer codRec ) {		
			this.codRec = codRec;
		}
		
		public Integer getNparcItRec() {		
			return nparcItRec;
		}
		
		public void setNparcItRec( final Integer nparcItRec ) {		
			this.nparcItRec = nparcItRec;
		}		
	}

	class RegH extends Reg {
		
		private static final char CODREG = 'H'; // registro H.01
		private String identCliEmpAnt = null;	// registro H.02
		private Integer agencia = null;			// registro H.03
		private String identCliBanco = null;	// registro H.04
		private String identCliEmpAtual = null;	// registro H.05
		private String ocorrencia = null;		// registro H.06
		//private String H07 = null;			// registro H.07
		private Integer codMovimento = null;	// registro H.08
		
		RegH( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}
		
		protected void parseLine( final String line ) throws ExceptionSiacc {
			try {
				setIdentCliEmpAnt( line.substring( 1, 26 ) );
				setAgencia( line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ) ) : null );
				setIdentCliBanco( line.substring( 30, 44 ) );
				setIdentCliEmpAtual( line.substring( 44, 69 ) );
				setOcorrencia( line.substring( 69, 127 ) );
				//H07( line.substring( 127, 149 ) );
				setCodMovimento( line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null );
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro H!/n" + e.getMessage());
			}

		}
		
		public Integer getAgencia() {		
			return agencia;
		}
		
		public void setAgencia( final Integer agencia ) {		
			this.agencia = agencia;
		}
		
		public Integer getCodMovimento() {		
			return codMovimento;
		}
		
		public void setCodMovimento( final Integer codMovimento ) {		
			this.codMovimento = codMovimento;
		}
		
		public String getIdentCliBanco() {		
			return identCliBanco;
		}
		
		public void setIdentCliBanco( final String identCliBanco ) {		
			this.identCliBanco = identCliBanco;
		}
		
		public String getIdentCliEmpAnt() {		
			return identCliEmpAnt;
		}
		
		public void setIdentCliEmpAnt( final String identCliEmpAnt ) {		
			this.identCliEmpAnt = identCliEmpAnt;
		}
		
		public String getIdentCliEmpAtual() {		
			return identCliEmpAtual;
		}
		
		public void setIdentCliEmpAtual( final String identCliEmpAtual ) {		
			this.identCliEmpAtual = identCliEmpAtual;
		}
		
		public String getOcorrencia() {		
			return ocorrencia;
		}
		
		public void setOcorrencia( final String ocorrencia ) {		
			this.ocorrencia = ocorrencia;
		}
	}

	class RegJ extends Reg {
		
		private static final char CODREG = 'J'; // registro J.01
		private String menssagemInfo = null;	// registro J.02
		private String filler = null;			// registro J.03
		
		RegJ( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}
		
		protected void parseLine( final String line ) throws ExceptionSiacc {
			try {
				setMenssagemInfo( line.substring( 1, 27 ) );
				setFiller( line.substring( 27 ) );
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro J!" + e.getMessage());
			}
		}
		
		public String getFiller() {		
			return filler;
		}
		
		public void setFiller( final String filler ) {		
			this.filler = filler;
		}
		
		public String getMenssagemInfo() {		
			return menssagemInfo;
		}
		
		public void setMenssagemInfo( final String menssagemInfo ) {		
			this.menssagemInfo = menssagemInfo;
		}
	}

	class RegX extends Reg {
	
		private static final char CODREG = 'X'; // registro X.01
		private String codAgencia = null;		// registro X.02
		private String nomeAgencia = null;		// registro X.03
		private String endAgencia = null;		// registro X.04
		private String numAgencia = null;		// registro X.05
		private String cepAgencia = null;		// registro X.06
		private String sufixoCepAgencia = null;	// registro X.07
		private String cidadeAgencia = null;	// registro X.08
		private String ufAgencia = null;		// registro X.09
		private String sitAgencia = null;		// registro X.10
		//private String X11 = null;			// registro X.11
		
		RegX( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}
		
		protected void parseLine( final String line ) throws ExceptionSiacc {
			try {
				setCodAgencia( line.substring( 1, 5 ) );
				setNomeAgencia( line.substring( 5, 35 ) );
				setEndAgencia( line.substring( 35, 65 ) );
				setNumAgencia( line.substring( 65, 70 ) );
				setCepAgencia( line.substring( 70, 75 ) );
				setSufixoCepAgencia( line.substring( 75, 78 ) );
				setCidadeAgencia( line.substring( 78, 98 ) );
				setUfAgencia( line.substring( 98, 100 ) );
				setSitAgencia( line.substring( 100, 101 ) );
				//X11( line.substring( 101 ) );
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro X!\n" + e.getMessage());
			}
		}
		
		public String getCodAgencia() {		
			return codAgencia;
		}
		
		public void setCodAgencia( final String agencia ) {		
			this.codAgencia = agencia;
		}
		
		public String getCepAgencia() {		
			return cepAgencia;
		}
		
		public void setCepAgencia( final String cepAgencia ) {		
			this.cepAgencia = cepAgencia;
		}
		
		public String getCidadeAgencia() {		
			return cidadeAgencia;
		}
		
		public void setCidadeAgencia( final String cidadeAgencia ) {		
			this.cidadeAgencia = cidadeAgencia;
		}
		
		public String getEndAgencia() {		
			return endAgencia;
		}
		
		public void setEndAgencia( final String endAgencia ) {		
			this.endAgencia = endAgencia;
		}
		
		public String getNomeAgencia() {		
			return nomeAgencia;
		}
		
		public void setNomeAgencia( final String nomeAgencia ) {		
			this.nomeAgencia = nomeAgencia;
		}
		
		public String getNumAgencia() {		
			return numAgencia;
		}
		
		public void setNumAgencia( final String numAgencia ) {		
			this.numAgencia = numAgencia;
		}
		
		public String getSitAgencia() {		
			return sitAgencia;
		}
		
		public void setSitAgencia( final String sitAgencia ) {		
			this.sitAgencia = sitAgencia;
		}
		
		public String getSufixoCepAgencia() {		
			return sufixoCepAgencia;
		}
		
		public void setSufixoCepAgencia( final String sufixoCepAgencia ) {		
			this.sufixoCepAgencia = sufixoCepAgencia;
		}
		
		public String getUfAgencia() {		
			return ufAgencia;
		}
		
		public void setUfAgencia( final String ufAgencia ) {		
			this.ufAgencia = ufAgencia;
		}
	}
	
	class RegZ extends Reg {
		
		private static final char CODREG = 'Z'; // registro Z.01
		private Integer totalRegistros = null;	// registro Z.02
		private Long valorTotal = null;			// registro Z.03
		//private String Z04 = null;			// registro Z.04
		private Integer seqRegistro = null;		// registro Z.05
		private Integer codMovimento = null;	// registro Z.06
		
		RegZ( final String line ) throws ExceptionSiacc {
			
			super( CODREG );
			parseLine( line );
		}
		
		RegZ( final int totreg, final float vlrtotal, final int nroseq ) {
			
			super('Z');
			this.sbreg.append( format( totreg, ETipo.$9, 6, 0) );
			this.sbreg.append( format( vlrtotal, ETipo.$9, 17, 2) );
			this.sbreg.append( format( "", ETipo.X, 119, 0)); // Reservado para o futuro
			this.sbreg.append( format( nroseq, ETipo.$9, 6, 0 ));
			this.sbreg.append( format( "", ETipo.$9, 1, 0));
		}

		protected void parseLine( final String line ) throws ExceptionSiacc {
			try {
				setTotalRegistros( line.substring( 1, 7 ).trim().length() > 0 ? new Integer( line.substring( 1, 7 ) ) : null );
				setValorTotal( line.substring( 7, 24 ).trim().length() > 0 ? new Long( line.substring( 7, 24 ) ) : null );
				//Z04( line.substring( 24, 143 ) );
				setSeqRegistro( line.substring( 143, 149 ).trim().length() > 0 ? new Integer( line.substring( 143, 149 ) ) : null );
				setCodMovimento( line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null );
			} catch (Exception e) {
				throw new ExceptionSiacc("Erro na leitura do registro Z!\n" + e.getMessage());
			}
		}
		
		public Integer getCodMovimento() {		
			return codMovimento;
		}
		
		public void setCodMovimento( final Integer codMovimento ) {		
			this.codMovimento = codMovimento;
		}
		
		public Integer getSeqRegistro() {		
			return seqRegistro;
		}
		
		public void setSeqRegistro( final Integer seqRegistro ) {		
			this.seqRegistro = seqRegistro;
		}
		
		public Integer getTotalRegistros() {		
			return totalRegistros;
		}
		
		public void setTotalRegistros( final Integer totalRegistros ) {		
			this.totalRegistros = totalRegistros;
		}
		
		public Long getValorTotal() {		
			return valorTotal;
		}
		
		public void setValorTotal( final Long valorTotal ) {		
			this.valorTotal = valorTotal;
		}
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
	
	/**
	 * Converte para java.util.Date um String em formato AAAAMMDD.
	 * @param arg String de data no formato AAAAMMDD.
	 * @return java.util.Date.
	 * @throws Exception
	 */
	public static java.util.Date strToDate( final String arg ) throws Exception {
		Date retorno = null;
		if ( arg != null ) {
			Integer ano = Integer.parseInt( arg.substring( 0, 4 ) );
			Integer mes = Integer.parseInt( arg.substring( 4, 6 ) );
			Integer dia = Integer.parseInt( arg.substring( 6 ) );
			Calendar cal = Calendar.getInstance();
			cal.set( ano, mes - 1, dia );
			retorno = cal.getTime();
		}		
		return retorno;
	}
}
