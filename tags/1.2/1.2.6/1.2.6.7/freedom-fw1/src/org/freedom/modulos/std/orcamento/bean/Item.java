package org.freedom.modulos.std.orcamento.bean;

import java.math.BigDecimal;

public class Item {
	private Integer codemp;
	private Integer codfilial;
	private Integer codprod;
	private String descprod;
	private BigDecimal qtd;
	private BigDecimal preco;
	private BigDecimal percdesc;
	private BigDecimal vlrdesc;
	private BigDecimal vlrliq;
	private Boolean sel = new Boolean(false);
	public Item() {
		setQtd(BigDecimal.ZERO);
		setPreco(BigDecimal.ZERO);
		setPercdesc(BigDecimal.ZERO);
		setVlrdesc(BigDecimal.ZERO);
		setVlrliq(BigDecimal.ZERO);
	}
	public Item(Integer codemp, Integer codfilial, Integer codprod, String descprod) {
		setCodemp(codemp);
		setCodfilial(codfilial);
		setCodprod(codprod);
		setDescprod(descprod);
	}
	public Integer getCodemp() {
		return codemp;
	}
	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}
	public Integer getCodfilial() {
		return codfilial;
	}
	public void setCodfilial(Integer codfilial) {
		this.codfilial = codfilial;
	}
	public Integer getCodprod() {
		return codprod;
	}
	public void setCodprod(Integer codprod) {
		this.codprod = codprod;
	}
	public String getDescprod() {
		return descprod;
	}
	public void setDescprod(String descprod) {
		this.descprod = descprod;
	}
	public BigDecimal getQtd() {
		if (qtd==null) {
			qtd = new BigDecimal(0);
		}
		return qtd;
	}
	public void setQtd(BigDecimal qtd) {
		this.qtd = qtd;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public BigDecimal getPercdesc() {
		return percdesc;
	}
	public void setPercdesc(BigDecimal percdesc) {
		this.percdesc = percdesc;
	}
	public BigDecimal getVlrdesc() {
		if (vlrdesc==null) {
			vlrdesc = new BigDecimal(0);
		}
		return vlrdesc;
	}
	public void setVlrdesc(BigDecimal vlrdesc) {
		this.vlrdesc = vlrdesc;
	}
	public BigDecimal getVlrliq() {
		if (vlrliq==null) {
			vlrliq = new BigDecimal(0);
		}
		return vlrliq;
	}
	public void setVlrliq(BigDecimal vlrliq) {
		this.vlrliq = vlrliq;
	}
	public Boolean getSel() {
		return sel;
	}
	public void setSel(Boolean sel) {
		this.sel = sel;
	}
}
