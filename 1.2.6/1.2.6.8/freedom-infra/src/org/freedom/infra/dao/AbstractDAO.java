/**
 * @version 02/08/2011 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.infra.dao <BR>
 * Classe: @(#)AbstractDAO.java <BR>
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
 * Classe base para implementações de métodos de acesso a dados
 */

package org.freedom.infra.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.freedom.infra.model.jdbc.DbConnection;

public abstract class AbstractDAO {
	private DbConnection conn;
	Integer codemp = null;
	Integer codfilial = null;

	public AbstractDAO(DbConnection connection) {
		setConn(connection);
	}

	public AbstractDAO(DbConnection connection, Integer codemp,
			Integer codfilial) {
		this(connection);
		setCodemp(codemp);
		setCodfilial(codfilial);
	}

	public DbConnection getConn() {
		return conn;
	}

	public void setConn(DbConnection conn) {
		this.conn = conn;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public Integer getCodfilial() {
		return codfilial;
	}

	public void setCodfilial(Integer codfilial) {
		this.codfilial = codfilial;
	}

	protected String getString(String value) {
		String result = null;

		if (value == null) {
			result = "";
		} else {
			result = value;
		}
		return result;
	}

	protected Integer getInteger(Integer value) {
		Integer result = null;

		if (value == null) {
			result = new Integer(0);
		} else {
			result = value;
		}
		return result;
	}

	protected BigDecimal getBigDecimal(BigDecimal value) {
		BigDecimal result = null;

		if (value == null) {
			result = BigDecimal.ZERO;
		} else {
			result = value;
		}
		return result;
	}

	public Integer writePK(String prefix) throws Exception {
		Integer result = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select iseq from spgeranum(?,?,?)");
			PreparedStatement ps = getConn().prepareStatement(sql.toString());
			int param = 1;
			ps.setInt(param++, getCodemp());
			ps.setInt(param++, getCodfilial());
			ps.setString(param++, prefix);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("iseq");
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch (SQLException err) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception("Erro gravando chave primária\n"+err.getMessage());
		}
		return result;
	}
	
	public Integer gerarSeqId(String tabela, boolean execCommit)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = 0;
		ps = getConn().prepareStatement("select biseq from sgsequence_idsp(?)");
		int param = 1;
		ps.setString(param, tabela.toLowerCase());
		rs = ps.executeQuery();
		if (rs.next()) {
			id = rs.getInt("biseq");
		}
		rs.close();
		ps.close();
		if (execCommit) {
			getConn().commit();
		}
		return id;
	}

	public void rollback() throws SQLException {
		if (getConn()!=null && getConn().isConnected() && getConn().isTransaction() ) {
			getConn().rollback();
		}
	}
	
	public void commit() throws SQLException {
		if (getConn()!=null && getConn().isConnected() && getConn().isTransaction() ) {
			getConn().commit();
		}
	}
	
	public void setValue( Object value, Vector<Object> row, int pos ) {
		if (row!=null) {
			for (int i=row.size(); i<=pos; i++) {
				row.addElement(null);
			}
			row.setElementAt(value, pos);
		}
	}
	
	public Map<String, Object> getMapFields(Object bean) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		if (bean!=null) {
			Method methods[] = bean.getClass().getDeclaredMethods();
			for (Method method: methods ) {
				if (method.getName().startsWith("get")) {
					try {
						Object[] args = new Object[0];
						Object value = method.invoke(bean, args);
						if (value!=null) {
							String field = method.getName().substring(3).toLowerCase();
							result.put(field, value);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		return result;
	}
	
	public StringBuilder getQueryInsert(String tablename, Map<String, Object> mapFields) {
		StringBuilder result = new StringBuilder();
		result.append( "insert into " );
		result.append( tablename );
		result.append( " ( " );
		Object[] fields = mapFields.keySet().toArray();
		for (int i=0; i< fields.length; i++) {
			if (i>0) {
				result.append(", ");
			} 
			result.append(fields[i]);
		}
		result.append( " ) values ( " );
		for (int i=0; i< fields.length; i++) {
			if (i>0) {
				result.append(", ");
			} 
			result.append("?");
		}
		result.append( " ) " );
		return result;
	}
	
	public void setParamsInsert(PreparedStatement ps, Map<String, Object> mapFields) throws SQLException {
		Object[] fields = mapFields.keySet().toArray();
		for (int i=1; i<=fields.length; i++) {
			Object value = mapFields.get(fields[i-1]);
			setParam(ps, i, value);
		}
	}
	
	public void setParam(PreparedStatement ps, int position, Object value) throws SQLException {
		if (value instanceof Short) {
			ps.setInt(position, (Short) value);
		} else if (value instanceof Integer) {
			ps.setInt(position, (Integer) value);
		} else if (value instanceof String) {
			ps.setString(position, (String) value);
		} else if (value instanceof BigDecimal) {
			ps.setBigDecimal(position, (BigDecimal) value);
		} else if (value instanceof Date) {
			ps.setDate(position, dateToSqlDate((Date) value));
		}  
	}
	
	public static java.sql.Date dateToSqlDate(Date date) {
		java.sql.Date result = new java.sql.Date(date.getTime());
		return result;
	}

}
