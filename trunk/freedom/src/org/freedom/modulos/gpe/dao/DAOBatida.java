/**
 * @version 17/09/2011 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gpe.dao <BR>
 *         Classe:
 * @(#)DAOBatida.java <BR>
 * 
 *                Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *              Classe de acesso a dados para entidade PEBATIDA
 * 
 */
package org.freedom.modulos.gpe.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.gpe.business.object.Batida;
import org.freedom.modulos.gpe.business.object.Batida.PREFS;


public class DAOBatida extends AbstractDAO {
	private Object prefs[] = null;
	
	public DAOBatida( DbConnection connection ) {

		super( connection );
		
	}

	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ Batida.PREFS.values().length];
		
		try {
			sql = new StringBuilder("select lancapontoaf  " );
			sql.append( "from sgprefere3 p " );
			sql.append( "where  p.codemp=? and p.codfilial=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				prefs[ PREFS.LANCAPONTOAF.ordinal() ] = new Boolean("S".equals(rs.getString( PREFS.LANCAPONTOAF.toString() )));
			}
			rs.close();
			ps.close();
			getConn().commit();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}
	
	public boolean carregaPonto(Integer codemp, Integer codfilial, String idusu) {
		boolean result = false;
		result = (Boolean) prefs[ PREFS.LANCAPONTOAF.ordinal()];
		if ( result ) { // Verifica se o sistema está configurado para carregar tela de ponto
			
		} 
		return result;
	}
	
}
