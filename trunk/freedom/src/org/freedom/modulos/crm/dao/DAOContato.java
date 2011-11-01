package org.freedom.modulos.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;


public class DAOContato extends AbstractDAO {
	private Object prefs[] = null;
	public enum CONT_PREFS{ USACTOSEQ };
	
	
	public DAOContato( DbConnection cn) {

		super( cn );
	}
	
	public Integer testaCodPK( String sTabela ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer retorno = new Integer( 0 );

		try {
			ps = getConn().prepareStatement( "SELECT ISEQ FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "TKCONTATO" ) );
			ps.setString( 3, "CO" );

			rs = ps.executeQuery();
			rs.next();

			retorno = new Integer( rs.getString( 1 ) );

			rs.close();
			ps.close();

			getConn().commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
		}

		return retorno;

	}
	
	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ CONT_PREFS.values().length];
		
		try {
			sql = new StringBuilder("select p.usactoseq " );
			sql.append( "from sgprefere3 p "); 
			sql.append( "where  p.codemp=? and p.codfilial=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				
				prefs[ CONT_PREFS.USACTOSEQ.ordinal() ] = rs.getString( CONT_PREFS.USACTOSEQ.toString() );		
				
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
	
	public Object[] getPrefs() {
		return this.prefs;
	}

}
