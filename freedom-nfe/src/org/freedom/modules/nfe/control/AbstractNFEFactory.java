/**
 * @version 15/07/2009 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.nfe.control <BR>
 * Classe: @(#)AbstractNFEFactory <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Classe padrão para API NFE.
 */

package org.freedom.modules.nfe.control;

import java.util.ArrayList;
import java.util.List;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modules.nfe.bean.AbstractNFEKey;
import org.freedom.modules.nfe.bean.NFEInconsistency;
import org.freedom.modules.nfe.event.NFEEvent;
import org.freedom.modules.nfe.event.NFEListener;

public abstract class AbstractNFEFactory implements NFEListener{

	private boolean valid = true;

	private DbConnection conSys = null;
	
	private DbConnection conNFE = null;
	
	private List<NFEListener> listEvent = new ArrayList<NFEListener>();
	
	private List<NFEInconsistency> listInconsistency = new ArrayList<NFEInconsistency>();
	
	private AbstractNFEKey key = null;
	
	private SYSTEM sourceSystem = SYSTEM.FREEDOM;
	
	public enum SYSTEM{FREEDOM};
	
	public AbstractNFEFactory() {
		addNFEListener( this );
	}

	public void addInconsistency( String description, String correctiveAction ) {
		this.addInconsistency(new NFEInconsistency(description, correctiveAction) );
	}
	
	public void addInconsistency( NFEInconsistency inconsistency ) {
		listInconsistency.add( inconsistency );
	}
	
	private void fireValidSend() {
		NFEEvent event = new NFEEvent(this);
		for (NFEListener obj: listEvent) {
			obj.beforeValidSend(event);
		}
		for (NFEListener obj: listEvent) {
			obj.validSend(event);
		}
		for (NFEListener obj: listEvent) {
			obj.afterValidSend(event);
		}
	}
	
	private void fireRunSend() {
		NFEEvent event = new NFEEvent(this);
		for (NFEListener obj: listEvent) {
			obj.beforeRunSend(event);
		}
		for (NFEListener obj: listEvent) {
			obj.runSend(event);
		}
		for (NFEListener obj: listEvent) {
			obj.afterRunSend(event);
		}
	}
	
	public void post() {
		fireValidSend();
		fireRunSend();
	}

	public synchronized void addNFEListener(NFEListener event) {
		this.listEvent.add(event);
	}

	public void removeNFEListener(NFEListener event) {
		this.listEvent.remove(event);
	}
	
	public boolean isValid() {
	
		return valid;
	}

	
	public void setValid( boolean valid ) {
	
		this.valid = valid;
	}

	public DbConnection getConSys() {
		return conSys;
	}

	public void setConSys(DbConnection conSys) {
		this.conSys = conSys;
	}

	public DbConnection getConNFE() {
		return conNFE;
	}

	public void setConNFE(DbConnection conNFE) {
		this.conNFE = conNFE;
	}

	public List<NFEInconsistency> getListInconsistency() {
		return listInconsistency;
	}

	public void setListInconsistency(List<NFEInconsistency> listInconsistency) {
		this.listInconsistency = listInconsistency;
	}

	public void setSourceSystem(SYSTEM sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public SYSTEM getSourceSystem() {
		return sourceSystem;
	}

	public void setKey(AbstractNFEKey key) {
		this.key = key;
	}

	public AbstractNFEKey getKey() {
		return key;
	}
}
