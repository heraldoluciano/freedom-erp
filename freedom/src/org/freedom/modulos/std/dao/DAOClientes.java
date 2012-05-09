package org.freedom.modulos.std.dao;

import java.math.BigDecimal;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;


public class DAOClientes extends AbstractDAO {
	
	public DAOClientes( DbConnection cn) {

		super( cn );

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
			result = BigDecimal.ZERO;
		} else {
			result = value;
		}
		return result;
	}


}


