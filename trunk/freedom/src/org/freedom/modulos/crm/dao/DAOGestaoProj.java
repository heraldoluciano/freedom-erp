package org.freedom.modulos.crm.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
//import org.freedom.modulos.crm.business.object.ContratoVW;
import org.freedom.modulos.crm.business.object.ContratoVW.EColContr;


public class DAOGestaoProj extends AbstractDAO {
	
	private Object prefs[] = null;
	
	public DAOGestaoProj( DbConnection cn) {

		super( cn );

	}
	
	
	public Vector<Vector<Object>> loadContr( Integer codempct , Integer codfilialct, Integer codcontr, String conthsubcontr) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Vector<Vector<Object >> result = new Vector<Vector<Object>>();
		
		Vector<Object> row = null;
		
	
			try{
				sql = new StringBuilder( "SELECT CT.INDICE, " );
				sql.append( "( CASE  " );
				sql.append( "WHEN IDX=1 AND TIPO IN ('SC','SP') THEN DESCCONTRSC " );
				sql.append( "WHEN IDX=1 AND TIPO IN ('CT','PJ') THEN DESCCONTR " );
				sql.append( "WHEN IDX=2 THEN DESCITCONTR " );
				sql.append( "WHEN IDX=3 THEN DESCTAREFA " );
				sql.append( "WHEN IDX=4 THEN DESCTAREFAST " );
				sql.append( "END ) DESCRICAO, " );
				sql.append( " TIPO, IDX, CODCONTR, CODCONTRSC, CODITCONTR, CODTAREFA, CODTAREFAST " );
				sql.append( "FROM VDCONTRATOVW01 CT " );
				sql.append(	"WHERE CT.CODEMPCT=? AND CT.CODFILIALCT=? AND CT.CODCONTR=? ");
				if ("S".equals(conthsubcontr)) {
					sql.append( "AND CT.CODCONTRSC IS NOT NULL " );
				} else {
					sql.append( "AND CT.CODCONTRSC IS NULL ");
				}
				sql.append(	"ORDER BY IDX01, IDX02, IDX03, IDX04, IDX05 " );
			
				
				ps = getConn().prepareStatement( sql.toString() );
				ps.setInt( 1, codempct );
				ps.setInt( 2, codfilialct );
				ps.setInt( 3, codcontr );
				rs = ps.executeQuery();
				
				while( rs.next() ){
					row = new Vector<Object>();
					row.addElement( rs.getString( EColContr.INDICE.toString() ) );
					row.addElement( rs.getString( EColContr.DESCRICAO.toString() ) );
					row.addElement( rs.getBigDecimal(  EColContr.PREV_TOTAL.toString() ) );
					row.addElement( rs.getBigDecimal( EColContr.REAL_ANT.toString() ) ) ;
					row.addElement( rs.getBigDecimal( EColContr.SALDO_ANT.toString() ) );
					row.addElement( rs.getBigDecimal(rs.getInt( EColContr.PREVISAO.toString() ) ) );
					row.addElement( rs.getBigDecimal(rs.getInt( EColContr.REALIZADO.toString() ) ) );
					row.addElement( rs.getBigDecimal(rs.getInt( EColContr.SALDO.toString() ) ) );
					row.addElement( rs.getBigDecimal(rs.getInt( EColContr.SALDO_COB.toString() ) ) );
					row.addElement( rs.getString( EColContr.TIPO.toString() ) );
					row.addElement( new Integer(rs.getInt( EColContr.IDX.toString() ) ) );
					row.addElement( new Integer(rs.getInt( EColContr.CODCONTR.toString() ) ) );
					row.addElement( new Integer(rs.getInt( EColContr.CODCONTRSC.toString() ) ) );
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
	
public Integer getNewIndiceContr(Integer codemp, Integer codfilial, Integer codcli) throws SQLException	{
		
		Integer result = null;
		StringBuilder sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			sql = new StringBuilder();
			sql.append( "SELECT COALESCE(MAX(CO.INDEXCONTR),0)+1 INDEXCONTR " );
			sql.append( "FROM VDCONTRATO CO " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=?" );

			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setInt( 3, codcli );
			rs = ps.executeQuery();
			
			if( rs.next() ){
				result = new Integer( rs.getInt( "INDEXCONTR" ) );		
			} else {
				result = 1;
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
	
	public Integer getNewIndiceItemContr(Integer codemp, Integer codfilial, Integer codcontr) throws SQLException	{
		
		Integer result = null;
		StringBuilder sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			sql = new StringBuilder();
			sql.append( "SELECT COALESCE(MAX(IC.INDEXITCONTR),0)+1 INDEXITCONTR " );
			sql.append( "FROM VDITCONTRATO IC " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCONTR=?" );

			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setInt( 3, codcontr );
			rs = ps.executeQuery();
			
			if( rs.next() ){
				result = new Integer( rs.getInt( "INDEXITCONTR" ) );		
			} else {
				result = 1;
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
	


	
	
	public Integer getNewIndiceItemTarefa(Integer codempct, Integer codfilialct, Integer codcontr, Integer coditcontr) throws SQLException	{
		
		Integer result = null;
		StringBuilder sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{

			sql = new StringBuilder();
			sql.append( "SELECT COALESCE(MAX(TA.INDEXTAREFA),0)+1 INDEXTAREFA " );
			sql.append( "FROM CRTAREFA TA " );
			sql.append( "WHERE CODEMPCT=? AND CODFILIALCT=? AND CODCONTR=?  AND CODITCONTR=? AND TIPOTAREFA=?" );

			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codempct );
			ps.setInt( 2, codfilialct );
			ps.setInt( 3, codcontr );
			ps.setInt( 4, coditcontr );
			ps.setString( 5, "T" );
			rs = ps.executeQuery();
			
			if( rs.next() ){
				result = new Integer( rs.getInt( "INDEXTAREFA" ) );		
			} else {
				result = 1;
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
	
	public Integer getNewIndiceItemSubTarefa(Integer codempta, Integer codfilialta, Integer codtarefa) throws SQLException	{
		
		Integer result = null;
		StringBuilder sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{

			sql = new StringBuilder();
			sql.append( "SELECT COALESCE(MAX(TA.INDEXTAREFA),0)+1 INDEXTAREFA " );
			sql.append( "FROM CRTAREFA TA " );
			sql.append( "WHERE CODEMPTA=? AND CODFILIALTA=? AND CODTAREFATA=?" );

			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codempta );
			ps.setInt( 2, codfilialta );
			ps.setInt( 3, codtarefa );
			//ps.setString( 4, "S" );
			rs = ps.executeQuery();
			
			if( rs.next() ){
				result = new Integer( rs.getInt( "INDEXTAREFA" ) );		
			} else {
				result = 1;
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
	
	public Integer getCodContr(Integer codemp, Integer codfilial, Integer codtarefa) throws SQLException{
		Integer result = null;
		StringBuilder sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{

			sql = new StringBuilder();
			sql.append( "SELECT CODCONTR " );
			sql.append( "FROM CRTAREFA TA " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODTAREFA=?" );

			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setInt( 3, codtarefa );
			rs = ps.executeQuery();

			if( rs.next() ){
				result = new Integer( rs.getInt( "CODCONTR" ) );		
			} else {
				result = null;
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
	public Integer getCodItContr(Integer codemp, Integer codfilial, Integer codtarefa) throws SQLException{
		Integer result = null;
		StringBuilder sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{

			sql = new StringBuilder();
			sql.append( "SELECT CODITCONTR " );
			sql.append( "FROM CRTAREFA TA " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODTAREFA=?" );

			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setInt( 3, codtarefa );
			rs = ps.executeQuery();

			if( rs.next() ){
				result = new Integer( rs.getInt( "CODITCONTR" ) );		
			} else {
				result = null;
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
