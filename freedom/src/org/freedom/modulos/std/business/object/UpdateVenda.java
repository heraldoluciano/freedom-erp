package org.freedom.modulos.std.business.object;

import java.math.BigDecimal;


public class UpdateVenda {

	private BigDecimal vlrprodvenda;
	private BigDecimal vlrliqprodvenda;
	private BigDecimal vlradicvenda;
	private BigDecimal Vlrdescvenda;
	private BigDecimal vlrdescitvenda;
	
	public BigDecimal getVlrprodvenda() {
	
		return vlrprodvenda;
	}
	
	public BigDecimal getVlrliqprodvenda() {
	
		return vlrliqprodvenda;
	}
	
	public BigDecimal getVlradicvenda() {
	
		return vlradicvenda;
	}
	
	public BigDecimal getVlrdescvenda() {
	
		return Vlrdescvenda;
	}
	
	public void setVlrprodvenda( BigDecimal vlrprodvenda ) {
	
		this.vlrprodvenda = vlrprodvenda;
	}
	
	public void setVlrliqprodvenda( BigDecimal vlrliqprodvenda ) {
	
		this.vlrliqprodvenda = vlrliqprodvenda;
	}
	
	public void setVlradicvenda( BigDecimal vlradicvenda ) {
	
		this.vlradicvenda = vlradicvenda;
	}
	
	public void setVlrdescvenda( BigDecimal vlrdescvenda ) {
	
		Vlrdescvenda = vlrdescvenda;
	}

	
	public BigDecimal getVlrdescitvenda() {
	
		return vlrdescitvenda;
	}

	
	public void setVlrdescitvenda( BigDecimal vlrdescitvenda ) {
	
		this.vlrdescitvenda = vlrdescitvenda;
	}

}
