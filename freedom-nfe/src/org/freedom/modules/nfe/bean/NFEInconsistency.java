/*
 * Projeto: Setpoint-nfe
 * Pacote: org.freedom.modules.nfe.event
 * Classe: @(#)NFEListener.java
 */
package org.freedom.modules.nfe.bean;

/**
 * Classe para definição de incosistência na NF-e.<br>
 * 
 * @see	org.freedom.modules.nfe.control.AbstractNFEFactory
 * 
 * @author Setpoint Informática Ltda./Robson Sanchez
 * @version 15/07/2009
 */
public class NFEInconsistency {
	
	private String description;
	
	private String correctiveAction;

	public NFEInconsistency( String description, String correctiveAction ) {
		this.description = description;
		this.correctiveAction = correctiveAction;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setCorrectiveAction( String correctiveAction ) {
		this.correctiveAction = correctiveAction;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}
}
