/*
 * Created on 05/10/2004
 * Autor: Robson Sanchez
 * Descrição: Classe de conexão com banco de dados 
 */
package org.freedom.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;

/**
 * @author robson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DbConnectionFactory {
	//protected static ServletContext context;
	protected static DbConnectionPool pool;

	/*
	 * protected static void initPool() throws Exception { if (pool == null) {
	 * try {
	 * 
	 *  } catch (Exception ex) { System.out.println(ex.getMessage()); throw ex; } } }
	 */

	public static java.sql.Connection getConnection(ServletContext context,
			String sessionID) throws SQLException {
		java.sql.Connection conn = null;
		try {
			conn = getConnection(context, sessionID, "", "");
			//conn = dataSource.getConnection();
		} catch (SQLException esql) {
			throw esql;
		}
		return conn;
	}

	public static void recycleConnection(ServletContext context,
			String sessionID) throws ResourceException {
		ResourceKey resource = null;

		pool = (DbConnectionPool) context.getAttribute("db-connection-pool");
		try {
			resource = pool.getResourceSession(sessionID);
			if (resource != null)
				pool.recycleResource(resource);
		} catch (Exception e) {
			throw new ResourceException(e.getMessage());
		}
	}

	public static java.sql.Connection getConnection(ServletContext context,
			String sessionID, String userid, String password)
			throws SQLException {
		java.sql.Connection conn = null;
		ResourceKey resource = null;
		try {
			try {
				pool = (DbConnectionPool) context
						.getAttribute("db-connection-pool");
				resource = pool.getResourceSession(sessionID);
				if (resource == null) {
					if (userid == null)
						userid = "";
					if (password == null)
						password = "";
					if ((!userid.equals("")) && (!password.equals(""))) {
						pool.setUser(userid);
						pool.setPassword(password);
						pool.setSessionID(sessionID);
					}
					resource = pool.getResource(sessionID, userid, password);

					if (resource != null) {
						if (!resource.getPassword().equals(password)) {
							recycleConnection(context, sessionID); // Recicla a
																   // conexão e
																   // retorna
																   // erro
							throw new Exception("Senha inválida."); // Exceção
																	// de senha
																	// inválida
						} 
							conn = (java.sql.Connection) resource.getResource();
					}
				}
			} catch (Exception esql) {
				throw esql;
			}
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return conn;
	}

	public static void closeConnection(java.sql.Connection conn,
			PreparedStatement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

}