package org.freedom.modulos.std.business.object;

import java.math.BigDecimal;


public class ItemVenda {
	
	private Integer codprod;
	private String refProd; 
	private BigDecimal qtdprod;  
	private BigDecimal precoprod; 
	private BigDecimal vlrdesc;
	private String codlote;
	
	public Integer getCodprod() {
	
		return codprod;
	}
	
	public String getRefProd() {
	
		return refProd;
	}
	
	public BigDecimal getQtdprod() {
	
		return qtdprod;
	}
	
	public BigDecimal getPrecoprod() {
	
		return precoprod;
	}
	
	public void setCodprod( Integer codprod ) {
	
		this.codprod = codprod;
	}
	
	public void setRefProd( String refProd ) {
	
		this.refProd = refProd;
	}
	
	public void setQtdprod( BigDecimal qtdprod ) {
	
		this.qtdprod = qtdprod;
	}
	
	public void setPrecoprod( BigDecimal precoprod ) {
	
		this.precoprod = precoprod;
	}
	
	public String getCodlote() {
	
		return codlote;
	}

	
	public void setCodlote( String codlote ) {
	
		this.codlote = codlote;
	}

	
	public BigDecimal getVlrdesc() {
	
		return vlrdesc;
	}

	
	public void setVlrdesc( BigDecimal vlrdesc ) {
	
		this.vlrdesc = vlrdesc;
	}
	
}
