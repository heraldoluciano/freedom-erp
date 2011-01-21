package org.freedom.library.business.componet.integration.ebs.vo;

import java.math.BigDecimal;

import org.freedom.library.business.componet.integration.ebs.EbsContabil;

/**
 * 
 * @author ffrizzo
 * since 11/12/2010
 */
public class ItemVO {
	
	private int codigo;

	private String descricao;

	private String ncm;

	private String unidade;

	private BigDecimal peso;

	private String referencia;

	private int tipoProduto;

	private int sequencial;

	public ItemVO() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(int tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	@Override
	public String toString() {

		StringBuilder item = new StringBuilder(100);

		item.append(EbsContabil.format(getCodigo(), 10));
		item.append(EbsContabil.format(getDescricao(), 40));
		item.append(EbsContabil.format(getNcm(), 8));
		item.append(EbsContabil.format(getUnidade(), 4));
		item.append(EbsContabil.format(getPeso(), 9, 3));
		item.append(EbsContabil.format(getReferencia(), 15));
		item.append(EbsContabil.format(getTipoProduto(), 2));
		item.append(EbsContabil.format(" ", 6));
		item.append(EbsContabil.format(getSequencial(), 6));

		return item.toString();
	}
}
