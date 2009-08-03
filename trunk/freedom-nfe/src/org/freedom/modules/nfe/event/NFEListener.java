/*
 * Projeto: Setpoint-nfe
 * Pacote: org.freedom.modules.nfe.event
 * Classe: @(#)NFEListener.java
 */
package org.freedom.modules.nfe.event;

/**
 * Ouvinte de Evento de NF-e.<br>
 * 
 * Esta interface prepara a classe que a implementar, para receber ações do evento de NF-e.<br>
 * 
 * @see	org.freedom.modules.nfe.event.NFEEvent
 * 
 * @author Setpoint Informática Ltda./Robson Sanchez
 * @version 03/08/2009
 */
public interface NFEListener {
	
	public void beforeValidSend( NFEEvent e );

	public void validSend( NFEEvent e );

	public void afterValidSend( NFEEvent e );

	public void beforeRunSend( NFEEvent e );

	public void runSend( NFEEvent e );

	public void afterRunSend( NFEEvent e );

	//public boolean isValid();
}
