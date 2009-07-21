package org.freedom.modules.nfe.bean;

public class FreedomNFEKey extends AbstractNFEKey {
	public FreedomNFEKey(Integer codemp, Integer codfilial, 
			String tipovenda, Integer codvenda ) {
		put("CODEMP", codemp);
		put("CODFILIAL", codfilial);
		put("TIPOVENDA", tipovenda);
		put("CODVENDA", codvenda);
	}
}
