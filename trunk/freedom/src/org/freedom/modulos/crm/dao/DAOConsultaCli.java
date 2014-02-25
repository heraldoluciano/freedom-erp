/**
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sachez <BR>
 * @versao 25/02/2014
 *  
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.dao <BR>
 *         Classe: @(#)DAOConsultaCli.java <BR>
 *         
 * 
 *                    Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                    na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                    Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                    Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                    Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Classe com o objetivo de persistir os dados da class FConsultaCli
 * 
 */
package org.freedom.modulos.crm.dao;

import java.sql.SQLException;
import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;

public class DAOConsultaCli extends AbstractDAO {


	public DAOConsultaCli( DbConnection connection ) {

		super( connection );

	}
	
	public void commit() throws SQLException {
		getConn().commit();
	}


}
