package org.freedom.modulos.pcp.business.object;

import java.math.BigDecimal;
import java.util.Date;



public class PPGeraOP {

	public enum PROCEDUREOP {
		NONE, TIPOPROCESS, CODEMPOP, CODFILIALOP, CODOP, SEQOP, CODEMPPD, CODFILIALPD, CODPROD, CODEMPOC, CODFILIALOC, CODORC, TIPOORC, CODITORC, QTDSUGPRODOP, DTFABROP, SEQEST, CODEMPET, CODFILIALET, CODEST, AGRUPDATAAPROV, AGRUPDTFABROP, AGRUPCODCLI, CODEMPCL, CODFILIALCL, CODCLI, DATAAPROV, CODEMPCP, CODFILIALCP, CODCOMPRA, CODITCOMPRA, JUSTFICQTDPROD, CODEMPPDENTRADA, CODFILIALPDENTRADA, CODPRODENTRADA, QTDENTRADA
	}

	private Integer codempop;
	private Integer codfilialop;
	private Integer codemppd;
	private Integer codfilialpd;
	private Integer codprod;
	private BigDecimal qtdSugProdOp;
	private Date dtFabOp;
	private Integer seqest;
	
	public Integer getCodempop() {
	
		return codempop;
	}
	
	public void setCodempop( Integer codempop ) {
	
		this.codempop = codempop;
	}
	
	public Integer getCodfilialop() {
	
		return codfilialop;
	}
	
	public void setCodfilialop( Integer codfilialop ) {
	
		this.codfilialop = codfilialop;
	}
	
	public Integer getCodemppd() {
	
		return codemppd;
	}
	
	public void setCodemppd( Integer codemppd ) {
	
		this.codemppd = codemppd;
	}
	
	public Integer getCodfilialpd() {
	
		return codfilialpd;
	}
	
	public void setCodfilialpd( Integer codfilialpd ) {
	
		this.codfilialpd = codfilialpd;
	}
	
	public Integer getCodprod() {
	
		return codprod;
	}
	
	public void setCodprod( Integer codprod ) {
	
		this.codprod = codprod;
	}
	
	public BigDecimal getQtdSugProdOp() {
	
		return qtdSugProdOp;
	}
	
	public void setQtdSugProdOp( BigDecimal qtdSugProdOp ) {
	
		this.qtdSugProdOp = qtdSugProdOp;
	}
	
	public Date getDtFabOp() {
	
		return dtFabOp;
	}
	
	public void setDtFabOp( Date dtFabOp ) {
	
		this.dtFabOp = dtFabOp;
	}
	
	public Integer getSeqest() {
	
		return seqest;
	}
	
	public void setSeqest( Integer seqest ) {
	
		this.seqest = seqest;
	}
	
	
	
}
