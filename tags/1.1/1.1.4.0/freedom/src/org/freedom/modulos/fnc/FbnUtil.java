package org.freedom.modulos.fnc;


class FbnUtil {
	enum EColcli {
		CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, AGENCIACLI, IDENTCLI, TIPOREMCLI,
	    CODEMPPF,CODFILIALPF,NUMCONTA,CODPLAN, RAZCLI
	}

	enum EColrec {
		CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, SITREMESSA, CODCLI, AGENCIACLI, 
		IDENTCLI, DTVENC, VLRAPAG, PESSOACLI, CPFCLI, CNPJCLI, CODMOVIMENTO,
		DOCREC, DTREC, NRPARCPAG
	}
	
	enum EParcela {
		CODREC,NPARCITREC,VLRITREC,VLRDESCITREC,VLRMULTAITREC,VLRJUROSITREC,
	    VLRPARCITREC,VLRPAGOITREC,VLRAPAGITREC,DTITREC,DTVENCITREC,DTPAGOITREC,
	    STATUSITREC,CODPLAN,OBSITREC,NUMCONTA,CODBANCO,CODTIPOCOB,DOCLANCAITREC,
	    FLAG,ANOCC,CODCC,VLRCOMIITREC;
	}

	enum EPrefs {
		CODBANCO, NOMEBANCO, CODCONV, NOMEEMP, NOMEEMPCNAB, VERLAYOUT, IDENTSERV, CONTACOMPR, IDENTAMBCLI, 
		IDENTAMBBCO, NROSEQ, AGENCIA, DIGAGENCIA, NUMCONTA, DIGCONTA, DIGAGCONTA ,CNPFEMP,
		FORCADTIT, TIPODOC, IDENTEMITBOL , IDENTDISTBOL, ESPECTIT, CODJUROS, VLRPERCJUROS,
		CODDESC, VLRPERCDESC, CODPROT, DIASPROT, CODBAIXADEV, DIASBAIXADEV, MDECOB, CONVCOB, ACEITE ;
	}
	
	enum ETipo {
		X, $9
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
		
		private Integer docrec = null;
	
		private String chaveComp = null;
	
		StuffRec( Integer codRec, Integer nParcItRec, String[] args ) {
	
			// System.out.println(args.length);
			this.chave1 = codRec;
			this.chave2 = nParcItRec;
			this.chaveComp = "[" + String.valueOf( codRec ) + "][" +String.valueOf( nParcItRec ) +"]";
			this.stfArgs = args;
			if (args.length>EColrec.DOCREC.ordinal()) {
				docrec = new Integer(args[EColrec.DOCREC.ordinal()]);
			}
		}
	
		public String[] getArgs() {
	
			return this.stfArgs;
		}
	
		public Integer getCodrec() {
	
			return this.chave1;
		}
	
		public Integer getDocrec() {
			return this.docrec;
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
	
	class StuffParcela {
		
		private Integer codrec = null;
		private Integer numparcrec = null;
		private Object[] args = null;
		
		private String chaveComp = null;
		
		StuffParcela( final Integer codrec, final Integer numparcrec, final Object[] args ) {
			
			this.codrec = codrec;
			this.numparcrec = numparcrec;
			this.args = args;
			this.chaveComp = "[" + String.valueOf( codrec ) + "][" +String.valueOf( numparcrec ) +"]";
		}		
		
		public Object[] getArgs() {		
			return args;
		}
		
		public Integer getCodrec() {		
			return codrec;
		}
		
		public Integer getNumparcrec() {		
			return numparcrec;
		}

		public boolean equals( Object obj ) {
	
			if ( obj instanceof StuffParcela )
				return ( ( codrec.equals( ( (StuffParcela)obj ).getCodrec() ) ) && ( numparcrec.equals( ( (StuffParcela)obj ).getNumparcrec() ) ) );
			else
				return false;
		}
	
		public int hashCode() {
			return chaveComp.hashCode();
		}		
	}
}
