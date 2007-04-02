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

		RegA( char codrem, Map map, int numReg ) {

			super( 'A' );
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
			// this.sbreg.append( "\n");

		}
		
		protected void parseLine(String line) {};
	}

	class RegB extends Reg {

		private final char COD_MOV = '2';

		RegB( char codreg, StuffCli stfCli ) {

			super( codreg );
			this.sbreg.append( format( stfCli.getCodigo(), ETipo.$9, 10, 0 ) );
			this.sbreg.append( format( "", ETipo.X, 15, 0 ) ); // Completar a identificação do cliente na empresa
			this.sbreg.append( format( stfCli.getArgs()[ EColcli.AGENCIACLI.ordinal() ], ETipo.$9, 4, 0 ) );
			this.sbreg.append( format( stfCli.getArgs()[ EColcli.IDENTCLI.ordinal() ], ETipo.X, 14, 0 ) );
			this.sbreg.append( Funcoes.dataAAAAMMDD( new Date() ) );
			this.sbreg.append( format( "", ETipo.X, 96, 0 ) ); // Reservado para o futuro
			this.sbreg.append( COD_MOV );
			this.sbreg.append( format( "", ETipo.X, 1, 0 ) ); // Reservado para o futuro
			// this.sbreg.append( "\n");

			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}

		protected void parseLine(String line) {};
		
	}

	class RegC extends Reg {

		private final char COD_MOV = '2';

		RegC( char codreg, StuffCli stfCli, int numSeq ) {

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
			// this.sbreg.append( "\n");
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}

		protected void parseLine(String line) {};

	}

	class RegD extends Reg {

		private final char COD_MOV = '0';

		RegD( char codreg, StuffCli stfCli, int numSeq ) {

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
			// this.sbreg.append( "\n");
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}

		protected void parseLine(String line) {};
		
	}

	class RegE extends Reg {

		private final char COD_MOV = '0';

		private final String COD_MOEDA = "03";
		
		private float vlrParc = 0;

		RegE( char codreg, StuffRec stfRec, int numSeq, int numAgenda ) {

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
			// this.sbreg.append( "\n");
			this.sbreg.append( (char) 13 );
			this.sbreg.append( (char) 10 );
		}
		
		float getVlrparc() {
			return this.vlrParc;
		}
		
		protected void parseLine(String line) {};
		
	}

	class RegZ extends Reg {
		RegZ( int totreg, float vlrtotal, int nroseq ) {
			super('Z');
			this.sbreg.append( format( totreg, ETipo.$9, 6, 0) );
			this.sbreg.append( format( vlrtotal, ETipo.$9, 17, 2) );
			this.sbreg.append( format( "", ETipo.X, 119, 0)); // Reservado para o futuro
			this.sbreg.append( format( nroseq, ETipo.$9, 6, 0 ));
			this.sbreg.append( format( "", ETipo.$9, 1, 0));
		}

		protected void parseLine(String line) {};

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
