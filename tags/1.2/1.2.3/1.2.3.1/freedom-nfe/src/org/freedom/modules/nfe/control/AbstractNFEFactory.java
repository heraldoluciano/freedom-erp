/*
 * Projeto: Freedom-nfe
 * Pacote: org.freedom.modules.nfe.control
 * Classe: @(#)AbstractNFEFactory.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 */

package org.freedom.modules.nfe.control;

import java.util.ArrayList;
import java.util.List;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.infra.pojos.Constant;
import org.freedom.modules.nfe.bean.AbstractNFEKey;
import org.freedom.modules.nfe.bean.NFEInconsistency;
import org.freedom.modules.nfe.event.NFEEvent;
import org.freedom.modules.nfe.event.NFEListener;

/**
 * Classe padrão para implementação de NF-e.
 * 
 * @author Setpoint Informática Ltda./Robson Sanchez
 * @version 15/07/2009 Robson Sanchez
 * @version 10/03/2010 Anderson Sanchez
 */

public abstract class AbstractNFEFactory {

	private boolean valid = true;

	private DbConnection conSys = null;

	private DbConnection conNFE = null;

	private AbstractNFEKey key = null;

	private List<NFEInconsistency> listInconsistency;

	private final List<NFEListener> listEvent = new ArrayList<NFEListener>();

	private Constant tpNF = AbstractNFEFactory.TP_NF_OUT;

	public static final Constant TP_NF_IN = new Constant("Entrada", new Integer(0));

	public static final Constant TP_NF_OUT = new Constant("Saida", new Integer(1));

	public static final Constant TP_NF_BOTH = new Constant("Ambos", new Integer(3));

	public enum SYSTEM {
		FREEDOM
	};

	public AbstractNFEFactory() {
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
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

	public void setKey(AbstractNFEKey key) {
		this.key = key;
	}

	public AbstractNFEKey getKey() {
		return key;
	}

	public void addInconsistency(String description, String correctiveAction) {
		this.addInconsistency(new NFEInconsistency(NFEInconsistency.TypeInconsistency.ERROR, description, correctiveAction));
	}

	public void addInconsistency(NFEInconsistency inconsistency) {
		if (inconsistency != null) {
			listInconsistency.add(inconsistency);
		}
	}

	public List<NFEInconsistency> getListInconsistency() {

		if (this.listInconsistency == null) {
			this.listInconsistency = new ArrayList<NFEInconsistency>();
		}

		return listInconsistency;
	}

	public void setTpNF(Constant tpNF) {
		this.tpNF = tpNF;
	}

	public Constant getTpNF() {
		return tpNF;
	}

	public void setListInconsistency(List<NFEInconsistency> listInconsistency) {
		this.listInconsistency = listInconsistency;
	}

	public synchronized void addNFEListener(NFEListener event) {
		this.listEvent.add(event);
	}

	public void removeNFEListener(NFEListener event) {
		this.listEvent.remove(event);
	}

	protected abstract void validSend();

	protected abstract void runSend();

	public void post() {

		fireBeforeValidSend();

		validSend();

		fireAfterValidSend();

		if (isValid()) {

			fireBeforeRunSend();

			runSend();

			fireAfterRunSend();
		}
	}

	private void fireBeforeValidSend() {

		NFEEvent event = new NFEEvent(this);

		for (NFEListener obj : listEvent) {
			obj.beforeValidSend(event);
		}
	}

	private void fireAfterValidSend() {

		NFEEvent event = new NFEEvent(this);

		for (NFEListener obj : listEvent) {
			obj.afterValidSend(event);
		}
	}

	private void fireBeforeRunSend() {

		NFEEvent event = new NFEEvent(this);

		for (NFEListener obj : listEvent) {
			obj.beforeValidSend(event);
		}
	}

	private void fireAfterRunSend() {

		NFEEvent event = new NFEEvent(this);

		for (NFEListener obj : listEvent) {
			obj.afterRunSend(event);
		}
	}
}
