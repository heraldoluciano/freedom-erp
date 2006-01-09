/*
 * Created on 05/10/2004
 * Autor: robson 
 * Descrição: Pool de conexões JDBC
 */
package org.freedom.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;
import org.freedom.util.resource.ResourcePool;

/**
 * @author robson
 * 
 * Pool de conexões JDBC
 */
public class DbConnectionPool extends ResourcePool {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.elog.system.util.ResourcePool#createResource()
	 */

	private final String driver, url;

	private final int initialConnections = 0;

	private boolean driverLoaded = false;

	private String user, password;

	private String userweb, passwordweb; // User name e password configurados em
										 // web.xml

	private String sessionID;

	public DbConnectionPool(String driver, String url) {
		this(driver, url, null, null);
	}

	public DbConnectionPool(String driver, String url, String user,
			String password) {
		this.driver = driver;
		this.url = url;
		if ((user != null) || (password != null)) { // se o usuário e senha
													// estiverem definidos no
													// web xml
			this.userweb = user;
			this.passwordweb = password;
			try {
				for (int i = 0; i < initialConnections; i++) {
					ResourceKey resource = createResource();
					availableResources.put(resource.getHashKey(), resource);
				}
			} catch (Exception ex) {
			}
		}
	}

	public ResourceKey createResource() throws ResourceException {
		Connection connection = null;
		ResourceKey resource = null;
		String key = null;
		String pwd = null;
		try {
			if (!driverLoaded) {
				Class.forName(driver);
				driverLoaded = true;
			}
			if ((user == null) || (password == null)) { // se o username ou a
														// password informada
														// estiverem nulos
				// conectará com as informações de web xml
				connection = DriverManager.getConnection(url, userweb,
						passwordweb);
				key = userweb;
				pwd = passwordweb;
			} else {
				connection = DriverManager.getConnection(url, user, password);
				key = user;
				pwd = password;
			}
			resource = new ResourceKey(sessionID, key, pwd, connection);
		} catch (Exception ex) {
			// ClassNotFoundException ou SQLException
			throw new ResourceException(ex.getMessage());
		} finally {
			key = null;
		}

		return resource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.elog.system.util.ResourcePool#closeResource(java.lang.Object)
	 */
	public void closeResource(ResourceKey resource) {
		java.sql.Connection connection = null;
		try {
			connection = (Connection) resource.getResource();
			connection.close();
			resource = null;
		} catch (SQLException ex) {
			// ignora exceção
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.elog.system.util.ResourcePool#isResourceValid(java.lang.Object)
	 */
	public boolean isResourceValid(ResourceKey resource) {
		Connection connection = null;
		boolean valid = false;
		try {
			connection = (Connection) resource.getResource();
			valid = !connection.isClosed();
		} catch (SQLException ex) {
			valid = false;
		}
		return valid;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

}