/**
 * @version 29/10/2013 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.library.swing.component <BR>
 * Classe: @(#)Historico.java <BR>
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
 * Classe de armazenamento de logs. 
 */

package org.freedom.library.swing.component;

import java.util.Date;

public class Historico {
	private Date dataOperacao;
	private String TipoOperacao;
	private String historico;
	private Integer id;

	public Date getDataOperacao() {
		return dataOperacao;
	}

	public String getTipoOperacao() {
		return TipoOperacao;
	}

	public String getHistorico() {
		return historico;
	}

	public Integer getId() {
		return id;
	}

	public void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		TipoOperacao = tipoOperacao;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
