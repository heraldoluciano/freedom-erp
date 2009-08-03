/*
 * Projeto: Setpoint-nfe
 * Pacote: org.freedom.modules.nfe.control
 * Classe: @(#)AbstractNFEFactory.java
 */
package org.freedom.modules.nfe.control;

import java.util.ArrayList;
import java.util.List;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modules.nfe.bean.AbstractNFEKey;
import org.freedom.modules.nfe.bean.NFEInconsistency;
import org.freedom.modules.nfe.event.NFEEvent;
import org.freedom.modules.nfe.event.NFEListener;

/**
 * Classe padrão para implementação de NF-e.
 * 
 * @author Setpoint Informática Ltda./Robson Sanchez
 * @version 15/07/2009
 */
public abstract class AbstractNFEFactory implements NFEListener {

	private boolean valid = true;

	private SYSTEM sourceSystem = SYSTEM.FREEDOM;

	private DbConnection conSys = null;

	private DbConnection conNFE = null;

	private AbstractNFEKey key = null;

	private final List<NFEListener> listEvent = new ArrayList<NFEListener>();

	private List<NFEInconsistency> listInconsistency = new ArrayList<NFEInconsistency>();

	public enum SYSTEM {
		FREEDOM
	};
	

	public AbstractNFEFactory() {
		addNFEListener( this );
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid( boolean valid ) {
		this.valid = valid;
	}

	public void setSourceSystem( SYSTEM sourceSystem ) {
		this.sourceSystem = sourceSystem;
	}

	public SYSTEM getSourceSystem() {
		return sourceSystem;
	}

	public DbConnection getConSys() {
		return conSys;
	}

	public void setConSys( DbConnection conSys ) {
		this.conSys = conSys;
	}

	public DbConnection getConNFE() {
		return conNFE;
	}

	public void setConNFE( DbConnection conNFE ) {
		this.conNFE = conNFE;
	}

	public void setKey( AbstractNFEKey key ) {
		this.key = key;
	}

	public AbstractNFEKey getKey() {
		return key;
	}

	public void addInconsistency( String description, String correctiveAction ) {
		this.addInconsistency( new NFEInconsistency( description, correctiveAction ) );
	}

	public void addInconsistency( NFEInconsistency inconsistency ) {
		listInconsistency.add( inconsistency );
	}

	public List<NFEInconsistency> getListInconsistency() {
		return listInconsistency;
	}

	public void setListInconsistency( List<NFEInconsistency> listInconsistency ) {
		this.listInconsistency = listInconsistency;
	}

	public synchronized void addNFEListener( NFEListener event ) {
		this.listEvent.add( event );
	}

	public void removeNFEListener( NFEListener event ) {
		this.listEvent.remove( event );
	}

	public void post() {
		
		fireValidSend();
		fireRunSend();
	}

	private void fireValidSend() {
		
		NFEEvent event = new NFEEvent( this );
		
		for ( NFEListener obj : listEvent ) {
			obj.beforeValidSend( event );
		}
		
		for ( NFEListener obj : listEvent ) {
			obj.validSend( event );
		}
		
		for ( NFEListener obj : listEvent ) {
			obj.afterValidSend( event );
		}
	}

	private void fireRunSend() {
		
		NFEEvent event = new NFEEvent( this );
		
		for ( NFEListener obj : listEvent ) {
			obj.beforeRunSend( event );
		}
		
		for ( NFEListener obj : listEvent ) {
			obj.runSend( event );
		}
		
		for ( NFEListener obj : listEvent ) {
			obj.afterRunSend( event );
		}
	}
}
