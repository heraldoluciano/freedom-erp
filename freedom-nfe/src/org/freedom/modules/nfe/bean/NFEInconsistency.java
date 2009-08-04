/*
 * Projeto: Freedom-nfe
 * Pacote: org.freedom.modules.nfe.event
 * Classe: @(#)NFEListener.java
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
