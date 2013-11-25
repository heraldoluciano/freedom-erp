package org.freedom.infra.util.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.freedom.infra.model.jdbc.DbConnection;

/**
 * Projeto: <a href="http://freedom-erp.googlecode.com">Freedom-infra</a> <br>
 * Este programa é licenciado de acordo com a GPL <br>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada
 * pela Fundação do Software Livre (FSF); <BR>
 * <br>
 * 
 * Classe de execução de querys em banco de dados.
 * 
 * @author Robson Sanchez/Setpoint Tecnologia
 *  
 * @since 03/09/2013
 */

public class DBEngine {

	private String driver;
	private String url;
	private String user;
	private String password;
	private String job;
	private List<Variable> listVariable;
	private static String SQL_TERM = ";";
	
	private DbConnection con;
	
	/**
	 * @param args Parâmetros para execução de query
	 */
	public static void main(String[] args) {
		if (args==null || args.length<5) {
			System.out.println("Uso: [Driver] [URL] [User] [Password] [Job file]");
		} else {
			String filename = args[4];
			File file = new File(filename);
			DBEngine engine = new DBEngine(args[0], args[1], args[2], args[3], file);
			engine.execute(engine.getJob(), null);
		}
	}

	public DBEngine(DbConnection con) {
		setCon(con);
	}
	
	public DBEngine(String driver, String url, String user, String password, File file) {
		StringBuilder job = new StringBuilder();
		try {
			FileReader r = new FileReader(file);
			BufferedReader br = new BufferedReader(r);
			String line = null;
			while ((line=br.readLine())!=null) {
				job.append(line);
				job.append("\n");
			}
			br.close();
			r.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadParam(driver, url, user, password, job.toString());
	}
	
	private void loadParam(String driver, String url, String user, String password, String job) {
		setDriver(driver);
		setUrl(url);
		setUser(user);
		setPassword(password);
		setJob(job);
		con = loadConnection();
	}

	public boolean execute(String job, List<Variable> listvar) {
		return this.execute(job, listvar, null);
	}
	
	public boolean execute(String job, List<Variable> listvar, String term) {
		boolean result = false;
		if (term==null) {
			term = SQL_TERM;
		}
		String[] jobline = parseLine(job, term);
		for (int i=0; i<jobline.length; i++) {
			jobline[i] = replaceValue(jobline[i], listvar);
		}
		result = executeSQL(jobline);
		return result;
	}
	
	public String replaceValue(String line, List<Variable> listvar) {
		//StringBuilder buffer = new StringBuilder();
		String tmp = null;
		if (listvar==null) {
			tmp = line;
		} else {
			tmp = line;
			for (Variable var: listvar) {
				tmp = tmp.replace(var.getName(), var.getValue());
			}
		}
		/*String[] splitline = tmp.split("\n");
		for (String str: splitline) {
			if (!str.trim().startsWith("--")) {
				buffer.append(str);
				buffer.append(" ");
			}
		}*/
		return tmp;
				//buffer.toString();
	}
	
	public boolean executeQuery(String sql) {
		boolean result = false;
		try {
			if (sql.trim().toLowerCase().startsWith("commit")) {
				con.commit();
			} else if (!"".equals(sql.trim())) {
				System.out.println(sql);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.execute();
			}
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException err) {
				err.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean executeSQL(String[] jobline) {
		boolean result = false;
		for (String sql: jobline) {
			result = executeQuery(sql);
		}
		return result;
	}
	
	public String[] parseLine(String job, String term) {
		String[] result;
		result = job.split(term);
		return result;
	}
	
	public DBEngine(String driver, String url, String user, String password, String job) {
		loadParam(driver, url, user, password, job);
	}

	private DbConnection loadConnection() {
		DbConnection result = null;
		try {
			result = new DbConnection(getDriver(), getUrl(), getUser(), getPassword() );
			//result.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public DbConnection getCon() {
		return con;
	}

	public void setCon(DbConnection con) {
		this.con = con;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public List<Variable> getListVariable() {
		return listVariable;
	}

	public void setListVariable(List<Variable> listVariable) {
		this.listVariable = listVariable;
	}

	public class Variable {
		private String name;
		private String value;
		public Variable(String name, String value) {
			setName(name);
			setValue(value);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public boolean equals(Object obj) {
			boolean result = false;
			if (obj!=null && obj instanceof Variable && getName()!=null) {
				result = getName().equalsIgnoreCase(((Variable) obj).getName()); 
			}
			return result;
		}
		public int hashCode() {
			int result = -1;
			if (getName()!=null) {
				result = getName().toLowerCase().hashCode();
			}
			return result;
		}
	}

}
