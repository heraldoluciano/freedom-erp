package org.freedom.modulos.crm.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
//import org.freedom.modulos.crm.business.object.ContratoVW;
import org.freedom.library.functions.Funcoes;
import org.freedom.modulos.crm.business.object.ContratoVW.EColContr;


public class DAOGestaoProj extends AbstractDAO {
	
	private Object prefs[] = null;
	
	public DAOGestaoProj( DbConnection cn) {

		super( cn );

	}
	
	
	public Vector<Vector<Object>> loadContr( Date dataini, Date datafim, Integer codempct , Integer codfilialct, Integer codcontr, String conthsubcontr) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Vector<Vector<Object >> result = new Vector<Vector<Object>>();
		
		Vector<Object> row = null;
		
	
			try{
				sql = new StringBuilder( "select ct.tipo, ct.indice, " );
				sql.append( "( case " );
				sql.append( "when idx=1 and ct.tipo in ('sc','sp') then desccontrsc " );
				sql.append( "when idx=1 and ct.tipo in ('ct','pj') then desccontr " );
				sql.append( "when idx=2 then ct.descitcontr " );
				sql.append( "when idx=3 then ct.desctarefa " );
				sql.append( "when idx=4 then ct.desctarefast " );
				sql.append( "end ) descricao, " );
				sql.append( "t.totalprevgeral, t.totalgeral, t.totalcobcligeral, t.totalant, t.totalcobcliant, t.totalper, t.totalcobcliper, " );
				sql.append( "ct.idx, ct.codcontr, ct.codcontrsc, ct.coditcontr, ct.codtarefa, ct.codtarefast " );
				sql.append( "from vdcontratovw01 ct, vdcontratototsp(ct.codempct, ct.codfilialct, ct.codcontr, ct.coditcontr, " );
				sql.append( "ct.codempsc, ct.codfilialsc, ct.codcontrsc, " );
				sql.append( "ct.codempta, ct.codfilialta, ct.codtarefa, " );
				sql.append( "ct.codempst, ct.codfilialst, ct.codtarefast, " );
				sql.append( "?, ? ) t " );
				sql.append( "where ct.codempct=? and ct.codfilialct=? and ct.codcontr=? " );
				if ("S".equals(conthsubcontr)) {
					sql.append( "and ct.codcontrsc is not null " );
				} else {
					sql.append( "and ct.codcontrsc is null ");
				}
				sql.append( "order by idx01, idx02, idx03, idx04, idx05 " );

				
				ps = getConn().prepareStatement( sql.toString() );
				int param = 1;
				ps.setDate( param++, Funcoes.dateToSQLDate( dataini ) );
				ps.setDate( param++, Funcoes.dateToSQLDate( datafim ) );
				ps.setInt( param++, codempct );
				ps.setInt( param++, codfilialct );
				ps.setInt( param++, codcontr );
				rs = ps.executeQuery();
				
				while( rs.next() ){
					row = new Vector<Object>();
					row.addElement( getString(  rs.getString( EColContr.TIPO.toString() ) ) );
					row.addElement( getString( rs.getString( EColContr.INDICE.toString() ) ) );
					row.addElement( getString( rs.getString( EColContr.DESCRICAO.toString() ) ) );
					row.addElement( getBigDecimal( rs.getBigDecimal(  EColContr.TOTALPREVGERAL.toString() ) ) );
					row.addElement( getBigDecimal( rs.getBigDecimal( EColContr.TOTALGERAL.toString() ) ) );
					row.addElement( getBigDecimal( rs.getBigDecimal( EColContr.TOTALCOBCLIGERAL.toString() ) ) );
					row.addElement( getBigDecimal( rs.getBigDecimal( EColContr.TOTALANT.toString() ) ) );
					row.addElement( getBigDecimal( rs.getBigDecimal( EColContr.TOTALCOBCLIANT.toString() ) ) );
					row.addElement( getBigDecimal( rs.getBigDecimal( EColContr.TOTALPER.toString() ) ) );
					row.addElement( getBigDecimal( rs.getBigDecimal( EColContr.TOTALCOBCLIPER.toString() ) ) );
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
	
	private String getString( String value ){
		String result = null;
		
		if (value == null){
			result = "";
		} else {
			result = value;
		}
		return result;
	}	
	
	private BigDecimal getBigDecimal( BigDecimal value ) {
		BigDecimal result = null;
		
		if (value == null){
			result = new BigDecimal(0);
		} else {
			result = value;
		}
		return result;
	}
}
