package org.freedom.modulos.std.orcamento.bean;

import java.math.BigDecimal;

public class Item {
	Integer codemp;
	Integer codfilial;
	Integer codprod;
	String descprod;
	BigDecimal qtd;
	BigDecimal preco;
	BigDecimal percdesc;
	BigDecimal vlrdesc;
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
		return vlrdesc;
	}
	public void setVlrdesc(BigDecimal vlrdesc) {
		this.vlrdesc = vlrdesc;
	}
}
