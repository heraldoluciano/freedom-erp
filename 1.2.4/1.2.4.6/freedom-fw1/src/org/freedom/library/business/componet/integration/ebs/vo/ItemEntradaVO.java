package org.freedom.library.business.componet.integration.ebs.vo;

import java.math.BigDecimal;

import org.freedom.library.business.componet.integration.ebs.EbsContabil;

/**
 * 
 * @author ffrizzo
 * since 11/12/2010
 */
public class ItemEntradaVO {

	private final int tipoRegistro = 2;

	private int codigo;

	private BigDecimal quantidade;

	private BigDecimal valor;

	private BigDecimal quantidade2;

	private BigDecimal desconto;

	private BigDecimal baseICMS;

	private BigDecimal aliquotaICMS;

	private BigDecimal valorIPI;

	private BigDecimal baseICMSSubTributaria;

	private BigDecimal aliquotaIPI;

	private BigDecimal percentualReducaoBaseICMS;

	private int situacaoTributaria;

	private String indentificacao;

	private int situacaoTributariaIPI;

	private BigDecimal baseIPI;

	private int situacaoTributariaPIS;

	private BigDecimal basePIS;

	private BigDecimal aliquotaPIS;

	private BigDecimal quantidadeBasePIS;

	private BigDecimal valorAliquotaPIS;

	private BigDecimal valorPIS;

	private int situacaoTributariaCOFINS;

	private BigDecimal baseCOFINS;

	private BigDecimal aliquotaCOFINS;

	private BigDecimal quantidadeBaseCOFINS;

	private BigDecimal valorAliquotaCOFINS;

	private BigDecimal valorCOFINS;

	private BigDecimal valorICMSSubTributaria;

	private int sequencial;
	
	public ItemEntradaVO() {
	}

	public int getTipoRegistro() {
		return tipoRegistro;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getQuantidade2() {
		return quantidade2;
	}

	public void setQuantidade2(BigDecimal quantidade2) {
		this.quantidade2 = quantidade2;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getBaseICMS() {
		return baseICMS;
	}

	public void setBaseICMS(BigDecimal baseICMS) {
		this.baseICMS = baseICMS;
	}

	public BigDecimal getAliquotaICMS() {
		return aliquotaICMS;
	}

	public void setAliquotaICMS(BigDecimal aliquotaICMS) {
		this.aliquotaICMS = aliquotaICMS;
	}

	public BigDecimal getValorIPI() {
		return valorIPI;
	}

	public void setValorIPI(BigDecimal valorIPI) {
		this.valorIPI = valorIPI;
	}

	public BigDecimal getBaseICMSSubTributaria() {
		return baseICMSSubTributaria;
	}

	public void setBaseICMSSubTributaria(BigDecimal baseICMSSubTributaria) {
		this.baseICMSSubTributaria = baseICMSSubTributaria;
	}

	public BigDecimal getAliquotaIPI() {
		return aliquotaIPI;
	}

	public void setAliquotaIPI(BigDecimal aliquotaIPI) {
		this.aliquotaIPI = aliquotaIPI;
	}

	public BigDecimal getPercentualReducaoBaseICMS() {
		return percentualReducaoBaseICMS;
	}

	public void setPercentualReducaoBaseICMS(BigDecimal percentualReducaoBaseICMS) {
		this.percentualReducaoBaseICMS = percentualReducaoBaseICMS;
	}

	public int getSituacaoTributaria() {
		return situacaoTributaria;
	}

	public void setSituacaoTributaria(int situacaoTributaria) {
		this.situacaoTributaria = situacaoTributaria;
	}

	public String getIndentificacao() {
		return indentificacao;
	}

	public void setIndentificacao(String indentificacao) {
		this.indentificacao = indentificacao;
	}

	public int getSituacaoTributariaIPI() {
		return situacaoTributariaIPI;
	}

	public void setSituacaoTributariaIPI(int situacaoTributariaIPI) {
		this.situacaoTributariaIPI = situacaoTributariaIPI;
	}

	public BigDecimal getBaseIPI() {
		return baseIPI;
	}

	public void setBaseIPI(BigDecimal baseIPI) {
		this.baseIPI = baseIPI;
	}

	public int getSituacaoTributariaPIS() {
		return situacaoTributariaPIS;
	}

	public void setSituacaoTributariaPIS(int situacaoTributariaPIS) {
		this.situacaoTributariaPIS = situacaoTributariaPIS;
	}

	public BigDecimal getBasePIS() {
		return basePIS;
	}

	public void setBasePIS(BigDecimal basePIS) {
		this.basePIS = basePIS;
	}

	public BigDecimal getAliquotaPIS() {
		return aliquotaPIS;
	}

	public void setAliquotaPIS(BigDecimal aliquotaPIS) {
		this.aliquotaPIS = aliquotaPIS;
	}

	public BigDecimal getQuantidadeBasePIS() {
		return quantidadeBasePIS;
	}

	public void setQuantidadeBasePIS(BigDecimal quantidadeBasePIS) {
		this.quantidadeBasePIS = quantidadeBasePIS;
	}

	public BigDecimal getValorAliquotaPIS() {
		return valorAliquotaPIS;
	}

	public void setValorAliquotaPIS(BigDecimal valorAliquotaPIS) {
		this.valorAliquotaPIS = valorAliquotaPIS;
	}

	public BigDecimal getValorPIS() {
		return valorPIS;
	}

	public void setValorPIS(BigDecimal valorPIS) {
		this.valorPIS = valorPIS;
	}

	public int getSituacaoTributariaCOFINS() {
		return situacaoTributariaCOFINS;
	}

	public void setSituacaoTributariaCOFINS(int situacaoTributariaCOFINS) {
		this.situacaoTributariaCOFINS = situacaoTributariaCOFINS;
	}

	public BigDecimal getBaseCOFINS() {
		return baseCOFINS;
	}

	public void setBaseCOFINS(BigDecimal baseCOFINS) {
		this.baseCOFINS = baseCOFINS;
	}

	public BigDecimal getAliquotaCOFINS() {
		return aliquotaCOFINS;
	}

	public void setAliquotaCOFINS(BigDecimal aliquotaCOFINS) {
		this.aliquotaCOFINS = aliquotaCOFINS;
	}

	public BigDecimal getQuantidadeBaseCOFINS() {
		return quantidadeBaseCOFINS;
	}

	public void setQuantidadeBaseCOFINS(BigDecimal quantidadeBaseCOFINS) {
		this.quantidadeBaseCOFINS = quantidadeBaseCOFINS;
	}

	public BigDecimal getValorAliquotaCOFINS() {
		return valorAliquotaCOFINS;
	}

	public void setValorAliquotaCOFINS(BigDecimal valorAliquotaCOFINS) {
		this.valorAliquotaCOFINS = valorAliquotaCOFINS;
	}

	public BigDecimal getValorCOFINS() {
		return valorCOFINS;
	}

	public void setValorCOFINS(BigDecimal valorCOFINS) {
		this.valorCOFINS = valorCOFINS;
	}

	public BigDecimal getValorICMSSubTributaria() {
		return valorICMSSubTributaria;
	}

	public void setValorICMSSubTributaria(BigDecimal valorICMSSubTributaria) {
		this.valorICMSSubTributaria = valorICMSSubTributaria;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	@Override
	public String toString() {

		StringBuilder itemEntrada = new StringBuilder(500);

		itemEntrada.append(getTipoRegistro());
		itemEntrada.append(EbsContabil.format(getCodigo(), 10));
		itemEntrada.append(EbsContabil.format(getQuantidade(), 9, 3));
		itemEntrada.append(EbsContabil.format(getValor(), 12, 2));
		itemEntrada.append(EbsContabil.format(getQuantidade2(), 13, 3));
		itemEntrada.append(EbsContabil.format(getDesconto(), 12, 2));
		itemEntrada.append(EbsContabil.format(getBaseICMS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getAliquotaICMS(), 5, 2));
		itemEntrada.append(EbsContabil.format(getValorIPI(), 12, 2));
		itemEntrada.append(EbsContabil.format(getBaseICMSSubTributaria(), 12, 2));
		itemEntrada.append(EbsContabil.format(getAliquotaIPI(), 5, 2));
		itemEntrada.append(EbsContabil.format(getPercentualReducaoBaseICMS(), 5, 2));
		itemEntrada.append(EbsContabil.format(getSituacaoTributaria(), 3));
		itemEntrada.append(EbsContabil.format(getIndentificacao(), 15));
		itemEntrada.append(EbsContabil.format(getSituacaoTributariaIPI(), 3));
		itemEntrada.append(EbsContabil.format(getBaseIPI(), 12, 2));
		itemEntrada.append(EbsContabil.format(getSituacaoTributariaPIS(), 3));
		itemEntrada.append(EbsContabil.format(getBasePIS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getAliquotaPIS(), 5, 2));
		itemEntrada.append(EbsContabil.format(getQuantidadeBasePIS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getValorAliquotaPIS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getValorPIS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getSituacaoTributariaCOFINS(), 3));
		itemEntrada.append(EbsContabil.format(getBaseCOFINS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getAliquotaCOFINS(), 5, 2));
		itemEntrada.append(EbsContabil.format(getQuantidadeBaseCOFINS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getValorAliquotaCOFINS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getValorCOFINS(), 12, 2));
		itemEntrada.append(EbsContabil.format(getValorICMSSubTributaria(), 12, 2));
		itemEntrada.append(EbsContabil.format(" ", 224));
		itemEntrada.append(EbsContabil.format(" ", 5));
		itemEntrada.append(EbsContabil.format(getSequencial(), 6));

		return itemEntrada.toString();
	}
}
