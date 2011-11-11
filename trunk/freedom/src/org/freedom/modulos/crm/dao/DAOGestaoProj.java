package org.freedom.modulos.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.crm.business.object.ContratoVW;
import org.freedom.modulos.crm.business.object.ContratoVW.EColContr;


public class DAOGestaoProj extends AbstractDAO {
	
	private Object prefs[] = null;
	
	public DAOGestaoProj( DbConnection cn) {

		super( cn );

	}
	
	public void setPrefs( Integer codempct , Integer codfilialct, Integer codcontr) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ ContratoVW.EColContr.values().length ];
	
			try{
				sql = new StringBuilder( "SELECT CT.INDICE, CT.IDX, " );
				sql.append( "( CASE  " );
				sql.append( "WHEN IDX=1 THEN DESCCONTR " );
				sql.append( "WHEN IDX=2 THEN DESCITCONTR " );
				sql.append( "WHEN IDX=3 THEN DESCTAREFA " );
				sql.append( "WHEN IDX=4 THEN DESCTAREFAST " );
				sql.append( "END ) DESCRICAO, " );
				sql.append( "TIPO, CODCONTR, CODITCONTR, CODTAREFA, CODTAREFAST " );
				sql.append( "FROM VDCONTRATOVW01 CT " );
				sql.append(	"WHERE CT.CODEMPCT=? AND CT.CODFILIALCT=? AND CT.CODCONTR=? ");
				sql.append( "AND TIPO IN ('SC','TA','ST') " );
				sql.append(	"ORDER BY INDICE " );
			
				
				ps = getConn().prepareStatement( sql.toString() );
				ps.setInt( 1, codempct );
				ps.setInt( 2, codfilialct );
				ps.setInt( 3, codcontr );
				rs = ps.executeQuery();
				
				if( rs.next() ){
					prefs[ EColContr.INDICE.ordinal() ] = rs.getString( EColContr.INDICE.toString() );
					prefs[ EColContr.DESCRICAO.ordinal() ] =  rs.getString( EColContr.DESCRICAO.toString() );
					prefs[ EColContr.TIPO.ordinal() ] = rs.getString( EColContr.TIPO.toString() );
					prefs[ EColContr.CODCONTR.ordinal() ] = new Integer(rs.getInt( EColContr.CODCONTR.toString() ));
					prefs[ EColContr.CODITCONTR.ordinal() ] = new Integer(rs.getInt( EColContr.CODITCONTR.toString() ));
					prefs[ EColContr.CODTAREFA.ordinal() ] = new Integer(rs.getInt( EColContr.CODTAREFA.toString() ));
					prefs[ EColContr.CODTAREFAST.ordinal() ] = new Integer(rs.getInt( EColContr.CODTAREFAST.toString() ));
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
	
	public Vector<Vector<Object>> loadContr( Integer codempct , Integer codfilialct, Integer codcontr) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Vector<Vector<Object >> result = new Vector<Vector<Object>>();
		
		Vector<Object> row = null;
		
	
			try{
				sql = new StringBuilder( "SELECT CT.INDICE, CT.IDX, " );
				sql.append( "( CASE  " );
				sql.append( "WHEN IDX=1 THEN DESCCONTR " );
				sql.append( "WHEN IDX=2 THEN DESCITCONTR " );
				sql.append( "WHEN IDX=3 THEN DESCTAREFA " );
				sql.append( "WHEN IDX=4 THEN DESCTAREFAST " );
				sql.append( "END ) DESCRICAO, " );
				sql.append( "TIPO, CODCONTR, CODITCONTR, CODTAREFA, CODTAREFAST " );
				sql.append( "FROM VDCONTRATOVW01 CT " );
				sql.append(	"WHERE CT.CODEMPCT=? AND CT.CODFILIALCT=? AND CT.CODCONTR=? ");
				sql.append( "AND TIPO IN ('SC','TA','ST') " );
				sql.append(	"ORDER BY INDICE " );
			
				
				ps = getConn().prepareStatement( sql.toString() );
				ps.setInt( 1, codempct );
				ps.setInt( 2, codfilialct );
				ps.setInt( 3, codcontr );
				rs = ps.executeQuery();
				
				while( rs.next() ){
					row = new Vector<Object>();
					row.addElement( rs.getString( EColContr.INDICE.toString() ) );
					row.addElement( rs.getString( EColContr.TIPO.toString() ) );
					row.addElement( rs.getString( EColContr.DESCRICAO.toString() ) );
					row.addElement( new Integer(rs.getInt( EColContr.CODCONTR.toString() ) ) );
					row.addElement( new Integer(rs.getInt( EColContr.CODITCONTR.toString() ) ) );
					row.addElement( new Integer(rs.getInt( EColContr.CODTAREFA.toString() ) ) );
					row.addElement( new Integer(rs.getInt( EColContr.CODTAREFAST.toString() ) ) );
					result.addElement( row );
				}
				rs.close();
				ps.close();
				getConn().commit();
			} finally {
				ps = null;
				rs = null;
				sql = null;
			}
			return result;
		}	
}
