/**
 * @version 22/04/2013 <BR>
 * @author Setpoint Informática Ltda./Bruno Nascimento<BR>
 *  
 *          Projeto: Freedom <BR>
 * 
 *          Pacote: org.freedom.modulos.pcp.dao <BR>
 *          Classe: @(#)DAOPull.java <BR>
 * 
 *                  Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                  modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                  na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                  Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                  sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                  Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                  Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                  de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                  DAO que possui a responsabilidade sobre o Sistema de produção Puxada ( Pull System ).
 * 
 */


package org.freedom.modulos.pcp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;

public class DAOPrevEstoq extends AbstractDAO {

	public DAOPrevEstoq( DbConnection cn) {
		super( cn );
	}


	public boolean comRef() throws SQLException {

		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;


		ps = Aplicativo.getInstace().getConexao().prepareStatement( sSQL );

		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

		rs = ps.executeQuery();

		if ( rs.next() )
			if ( rs.getString( "UsaRefProd" ).trim().equals( "S" ) )
				bRetorno = true;

		ps.close();
		rs.close();
		return bRetorno;
	}

	
}


