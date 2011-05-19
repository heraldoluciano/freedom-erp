package org.freedom.infra.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExtractDescription {

	/**
	 * @param args
	 */
    private static int ARG_JDBC_DRIVER = 0;
    private static int ARG_URL_DATABASE = 1;
    private static int ARG_EXPORT_FILE = 2;
    private static int ARG_USER = 3;
    private static int ARG_PASSWD = 4;
    private String export_file = null;
    private Connection connection = null;
    public static void main(String[] args) {

		if (args==null || args.length<=ARG_PASSWD) {
			System.out.println("Uso: java org.freedom.infra.util.db.ExtractDescription [jdbc_driver] [url_database] [export_file] [user_database] [passwd_database]");
		} else {
			System.out.println("JDBC_DRIVER: " + args[ARG_JDBC_DRIVER]);
			System.out.println("URL_DATABASE: " + args[ARG_URL_DATABASE]);
			System.out.println("EXPORT_FILE: " + args[ARG_EXPORT_FILE]);
			ExtractDescription extract = new ExtractDescription(args[ARG_JDBC_DRIVER], args[ARG_URL_DATABASE], args[ARG_EXPORT_FILE], args[ARG_USER], args[ARG_PASSWD]);
            extract.export();
		}
	}

	public ExtractDescription(String jdbc_driver, String url_database, String export_file, String user_database, String passwd_database) {
        setExport_file(export_file);
		try {
			Class.forName(jdbc_driver);
			setConnection(DriverManager.getConnection(url_database, user_database, passwd_database));
		} catch (ClassNotFoundException e) {
			System.out.println("Erro carregando driver JDBC.\n" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Erro conectando ao banco de dados.\n" + e.getMessage());
		}
	    
	}
	
	public void export() {
		try {
			PreparedStatement ps = 
				connection.prepareStatement( "SELECT RDB$RELATION_NAME, RDB$DESCRIPTION FROM RDB$RELATIONS WHERE RDB$DESCRIPTION IS NOT NULL");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
			}
		} catch (SQLException e) {
			System.out.println("Erro executando consulta.\n" + e.getMessage());
		}
		
	}

	public void setExport_file(String export_file) {
		this.export_file = export_file;
	}

	public String getExport_file() {
		return export_file;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
		try {
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("Erro configurando auto commit.\n" + e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}
}
