package org.freedom.modules.nfe.bean;


public class ReturnMessageKey {
	
	private Integer codeReturn;
	private String message;
	
	public ReturnMessageKey(Integer codereturn, String message) {
		this.codeReturn = codereturn;
		this.message = message;
	}

	public Integer getCodeReturn() {
		return codeReturn;
	}
	public String getMessage() {
		return message;
	}
	
}
