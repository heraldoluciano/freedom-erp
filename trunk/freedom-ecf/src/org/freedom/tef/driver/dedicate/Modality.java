package org.freedom.tef.driver.dedicate;


public enum Modality {
	
	CHEQUE( 0, "Cheque" ),
	DEBITO( 1, "Débito" ),
	CREDITO( 2, "Crédito" ),
	VOUCHER( 3, "Voucher" ),
	DINHEIRO( 98, "Dinheiro" ),
	OUTRA( 99, "Outra" );
	
	private Integer code;
	
	private String name;
	
	private Modality( Integer code, String name ) {
		this.code = code;
		this.name = name;
	}
	
	Integer getCode() {
		return this.code;
	}
	
	String getName() {
		return this.name;
	}
}
