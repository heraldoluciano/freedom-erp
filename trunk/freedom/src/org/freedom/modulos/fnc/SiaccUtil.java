package org.freedom.modulos.fnc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.freedom.funcoes.Funcoes;

class SiaccUtil {
	enum EColcli {
		CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, AGENCIACLI, IDENTCLI, TIPOREMCLI
	}

	enum EColrec {
		CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, SITREMESSA, CODCLI, AGENCIACLI, IDENTCLI, DTVENC, VLRAPAG
	}

	enum EPrefs {
		CODBANCO, NOMEBANCO, CODCONV, NOMEEMP, VERLAYOUT, IDENTSERV, CONTACOMPR, IDENTAMBCLI, IDENTAMBBCO, NROSEQ
	}
	
	enum ETipo {
		X, $9
	}
	
	abstract class Reg {

		protected StringBuilder sbreg = new StringBuilder();

		Reg( char codreg ) {

			this.sbreg.append( codreg );
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
		
		protected abstract void parseLine(String line);
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
		
		RegA( final String line ) {
			
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
		
		protected void parseLine( final String line ) {
			
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

		RegB( final String line ) {
			
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

		protected void parseLine( final String line ) {

			setIdentCliEmp( line.substring( 1, 26 ) );
			setAgenciaDebt( line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ).trim() ) : null );
			setIdentCliBanco( line.substring( 30, 44 ) );
			setDataOpcao( line.substring(  44, 52  ).trim().length() > 0 ? new Integer( line.substring( 44, 52 ).trim() ) : null );
			//B06 = line.substring( 52, 148 );
			setCodMovimento( line.substring( 148, 149 ).trim().length() > 0 ? new Integer( line.substring( 148, 149 ).trim() ) : null );
			//B08 = line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ).trim() ) : null;
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
		
		public Integer getDataOpcao() {		
			return dataOpcao;
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
		
		RegC( final String line ) {
			
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

		protected void parseLine( final String line ) {
			
			identCliEmp = line.substring( 1, 26 );
			agenciaDeb = line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ) ) : null;
			identCliBanco = line.substring( 30, 44 );
			ocorrencia1 = line.substring( 44, 84 );
			ocorrencia2 = line.substring( 84, 124 );
			//C07 = line.substring( 124, 143 );
			seqRegistro = line.substring( 143, 149 ).trim().length() > 0 ? new Integer( line.substring( 143, 149 ) ) : null;
			codMovimento = line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null;
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
		
		RegD( final String line ) {
			
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

		protected void parseLine( final String line ) {
			
			setIdentCliEmpAnt( line.substring( 1, 26 ) );
			setAgenciaDeb( line.substring( 26, 30 ).trim().length() > 0 ? new Integer( line.substring( 26, 30 ) ) : null );
			setIdentCliBanco( line.substring( 30, 44 ) );
			setIdetCliEmpAtual( line.substring( 44, 69 ) );
			setOcorrencia( line.substring( 69, 129 ) );
			//C07 = line.substring( 124, 143 );
			setSeqRegistro( line.substring( 143, 149 ).trim().length() > 0 ? new Integer( line.substring( 143, 149 ) ) : null );
			setCodMovimento( line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null );
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
		private float vlrParc = 0;
		
		RegE( final String line ) {
			
			super( CODREG );
			parseLine( line );
		}

		RegE( final char codreg, final StuffRec stfRec, final int numSeq, final int numAgenda ) {

			super( codreg );
			this.vlrParc =  Float.valueOf(stfRec.getArgs()[ EColrec.VLRAPAG.ordinal() ]);
			this.sbreg.append( format( stfRec.getArgs()[ EColrec.CODCLI.ordinal() ], ETipo.$9, 10, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 15, 0 ) ); // Completar a identificação do cliente na empresa
			this.sbreg.append( format( stfRec.getArgs()[ EColrec.AGENCIACLI.ordinal() ], ETipo.$9, 4, 0 ) );
			this.sbreg.append( format( stfRec.getArgs()[ EColrec.IDENTCLI.ordinal() ], ETipo.X, 14, 0 ) );
			this.sbreg.append( format( stfRec.getArgs()[ EColrec.DTVENC.ordinal() ], ETipo.$9, 8, 0 ) );
			this.sbreg.append( format( Funcoes.transValor(new BigDecimal(vlrParc), 15, 2, true), ETipo.$9, 15, 0 ) );
			this.sbreg.append( COD_MOEDA );
			this.sbreg.append( format( "", ETipo.X, 60, 0 ) ); // Uso da empresa
			this.sbreg.append( format( numAgenda, ETipo.$9, 6, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 8, 0 ) ); // Reservado para o futuro
			this.sbreg.append( format( numSeq, ETipo.$9, 6, 0 ) ); // Reservado para o futuro
			this.sbreg.append( COD_MOV );
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}
		
		protected void parseLine( final String line ) {
			
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
		}
		
		public Long getValorDebCred() {		
			return valorDebCred;
		}
		
		public void setValorDebCred( final Long valorDebCred ) {		
			this.valorDebCred = valorDebCred;
		}
		
		public float getVlrParc() {		
			return vlrParc;
		}
		
		public void setVlrParc( float vlrParc ) {		
			this.vlrParc = vlrParc;
		}		
	}

	class RegF extends Reg {
		
		private static final char CODREG = 'Z'; // registro Z.01
		private String identCliEmp = null;	// registro Z.02
		private Long valorTotal = null;			// registro Z.03
		//private String E04 = null;			// registro Z.04
		private Integer seqRegistro = null;		// registro Z.05
		private Integer codMovimento = null;	// registro Z.06
		
		RegF( final String line ) {
			
			super( CODREG );
			parseLine( line );
		}
		
		protected void parseLine( final String line ) {
			
		}
	}
	
	class RegZ extends Reg {
		
		private static final char CODREG = 'Z'; // registro Z.01
		private Integer totalRegistros = null;	// registro Z.02
		private Long valorTotal = null;			// registro Z.03
		//private String E04 = null;			// registro Z.04
		private Integer seqRegistro = null;		// registro Z.05
		private Integer codMovimento = null;	// registro Z.06
		
		RegZ( final String line ) {
			
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

		protected void parseLine( final String line ) {
			
			setTotalRegistros( line.substring( 1, 7 ).trim().length() > 0 ? new Integer( line.substring( 1, 7 ) ) : null );
			setValorTotal( line.substring( 7, 24 ).trim().length() > 0 ? new Long( line.substring( 7, 24 ) ) : null );
			//Z04( line.substring( 24, 143 ) );
			setSeqRegistro( line.substring( 143, 149 ).trim().length() > 0 ? new Integer( line.substring( 143, 149 ) ) : null );
			setCodMovimento( line.substring( 149 ).trim().length() > 0 ? new Integer( line.substring( 149 ) ) : null );
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
	
	class StuffCli {
		
	
		private String[] stfArgs = null;
	
		private Integer codigo = null;
	
		StuffCli( Integer codCli, String[] args ) {
	
			// System.out.println(args.length);
			this.codigo = codCli;
			this.stfArgs = args;
		}
	
		public String[] getArgs() {
	
			return this.stfArgs;
		}
	
		public Integer getCodigo() {
	
			return this.codigo;
		}
	
		public boolean equals( Object obj ) {
	
			if ( obj instanceof StuffCli )
				return codigo.equals( ( (StuffCli) obj ).getCodigo() );
			else
				return false;
		}
	
		public int hashCode() {
	
			return this.codigo.hashCode();
		}
	}

	class StuffRec {
	
		private String[] stfArgs = null;
	
		private Integer chave1 = null;
	
		private Integer chave2 = null;
	
		private Integer[] chaveComp = new Integer[ 2 ];
	
		StuffRec( Integer codRec, Integer nParcItRec, String[] args ) {
	
			// System.out.println(args.length);
			this.chave1 = codRec;
			this.chave2 = nParcItRec;
			this.chaveComp[ 0 ] = codRec;
			this.chaveComp[ 1 ] = nParcItRec;
			this.stfArgs = args;
		}
	
		public String[] getArgs() {
	
			return this.stfArgs;
		}
	
		public Integer getCodrec() {
	
			return this.chave1;
		}
	
		public Integer getNParcitrec() {
	
			return this.chave2;
		}

		public void setSitremessa(String sit) {
			this.stfArgs[EColrec.SITREMESSA.ordinal()] = sit;
		}
		
		public boolean equals( Object obj ) {
	
			if ( obj instanceof StuffRec )
				return ( ( chave1.equals( ( (StuffRec) obj ).getCodrec() ) ) && ( chave2.equals( ( (StuffRec) obj ).getNParcitrec() ) ) );
			else
				return false;
		}
	
		public int hashCode() {
	
			return chaveComp.hashCode();
		}
	}

}
