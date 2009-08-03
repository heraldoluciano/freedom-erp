/*
 * Projeto: Setpoint-nfe
 * Pacote: org.freedom.modules.nfe.bean
 * Classe: @(#)AbstractNFEKey.java
 */
package org.freedom.modules.nfe.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapa de chaves utilizado pela implementação de NF-e.
 * 
 * @see	org.freedom.modules.nfe.control.AbstractNFEFactory
 * 
 * @author Setpoint Informática Ltda./Robson Sanchez
 * @version 15/07/2009
 */
public abstract class AbstractNFEKey {

	private Map<String, Object> keyMap = new HashMap<String, Object>();

	public void setKey( Map<String, Object> keyMap ) {
		this.keyMap = keyMap;
	}

	public Map<String, Object> getKey() {
		return keyMap;
	}

	public Object get( String key ) {
		return keyMap.get( key );
	}

	public void put( String key, Object value ) {
		keyMap.put( key, value );
	}

}
