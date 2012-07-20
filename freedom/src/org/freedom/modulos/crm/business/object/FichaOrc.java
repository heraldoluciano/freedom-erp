package org.freedom.modulos.crm.business.object;


public class FichaOrc {
	
	public static enum INSERT_ORC {
		NONE, CODEMP, CODFILIAL, TIPOORC, CODORC, DTORC, DTVENCORC, CODEMPCL, CODFILIALCL, CODCLI, CODEMPVD, 
		CODFILIALVD, CODVEND, CODEMPPG, CODFILIALPG, CODPLANOPAG, CODEMPTN, CODFILIALTN, CODTRAN, STATUSORC
	}
	
	public static enum GET_ORC { 
		CODORC, CODCLI, RAZCLI, DTEMISSAO, DTVENC, CODPAG, DESCPAG, CODITORC, QTDITORC, PRECOITORC, TIPOORC; 
	}
	
	public enum PREFS { 
		USACTOSEQ, LAYOUTFICHAAVAL, CODPLANOPAG 
	};
	
	private Integer codemp;
	
	private Integer codfilial;
	
	private Integer seqfichaaval;
	
	private Integer seqitfichaaval;
	
	private Integer codempor;
	
	private Integer codfilialor;
	
	private Integer codorc;

	
	public Integer getCodemp() {
	
		return codemp;
	}

	
	public void setCodemp( Integer codemp ) {
	
		this.codemp = codemp;
	}

	
	public Integer getCodfilial() {
	
		return codfilial;
	}

	
	public void setCodfilial( Integer codfilial ) {
	
		this.codfilial = codfilial;
	}

	
	public Integer getSeqfichaaval() {
	
		return seqfichaaval;
	}

	
	public void setSeqfichaaval( Integer seqfichaaval ) {
	
		this.seqfichaaval = seqfichaaval;
	}

	
	public Integer getSeqitfichaaval() {
	
		return seqitfichaaval;
	}

	
	public void setSeqitfichaaval( Integer seqitfichaaval ) {
	
		this.seqitfichaaval = seqitfichaaval;
	}

	
	public Integer getCodempor() {
	
		return codempor;
	}

	
	public void setCodempor( Integer codempor ) {
	
		this.codempor = codempor;
	}

	
	public Integer getCodfilialor() {
	
		return codfilialor;
	}

	
	public void setCodfilialor( Integer codfilialor ) {
	
		this.codfilialor = codfilialor;
	}

	
	public Integer getCodorc() {
	
		return codorc;
	}

	
	public void setCodorc( Integer codorc ) {
	
		this.codorc = codorc;
	}
	

}
