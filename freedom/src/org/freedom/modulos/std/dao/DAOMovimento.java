package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;


public class DAOMovimento extends AbstractDAO {
	
	public DAOMovimento (DbConnection cn) {
		super(cn);
	}
	
	public Integer geraSeqId (String tabela) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Integer id = 0;
		ps = getConn().prepareStatement( "select biseq from sgsequence_idsp(?)" );
		ps.setString( 1, tabela );
		
		rs = ps.executeQuery();
		if (rs.next()) {
			id = rs.getInt( "biseq" ); 
		}

		return id;
	}
	
	
	private String getString (String value) {
		String result = null;
		
		if (value == null){
			result = "";
		} else {
			result = value;
		}
		return result;
	}	
	
	private Integer getInteger (Integer value) {
		Integer result = null;
		
		if (value == null) {
			result = new Integer(0);
		} else {
			result = value;
		}
		return result;
	}
	
	private BigDecimal getBigDecimal (BigDecimal value) {
		BigDecimal result = null;
		
		if (value == null) {
			result = BigDecimal.ZERO;
		} else {
			result = value;
		}
		return result;
	}

}


