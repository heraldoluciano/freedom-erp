package org.freedom.modulos.std.orcamento.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Cesta {
	private Integer codemp;
	private Integer codfilial;
	private Integer codcli;
	private String razcli;
	private Date datacesta;
	private BigDecimal qtdcesta;
	private BigDecimal vlrdesccesta;
	private BigDecimal vlrliqcesta;
	public Boolean sel;
	private List<Item> itens;
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
	public Integer getCodcli() {
		return codcli;
	}
	public void setCodcli(Integer codcli) {
		this.codcli = codcli;
	}
	public String getRazcli() {
		return razcli;
	}
	public void setRazcli(String razcli) {
		this.razcli = razcli;
	}
	public Date getDatacesta() {
		return datacesta;
	}
	public void setDatacesta(Date datacesta) {
		this.datacesta = datacesta;
	}
	public BigDecimal getQtdcesta() {
		return qtdcesta;
	}
	public void setQtdcesta(BigDecimal qtdcesta) {
		this.qtdcesta = qtdcesta;
	}
	public BigDecimal getVlrdesccesta() {
		return vlrdesccesta;
	}
	public void setVlrdesccesta(BigDecimal vlrdesccesta) {
		this.vlrdesccesta = vlrdesccesta;
	}
	public BigDecimal getVlrliqcesta() {
		return vlrliqcesta;
	}
	public void setVlrliqcesta(BigDecimal vlrliqcesta) {
		this.vlrliqcesta = vlrliqcesta;
	}
	public List<Item> getItens() {
		return itens;
	}
	public Boolean getSel() {
		return sel;
	}
	public void setSel(Boolean sel) {
		this.sel = sel;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

}
