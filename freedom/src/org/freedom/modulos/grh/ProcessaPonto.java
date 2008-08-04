package org.freedom.modulos.grh;

import java.awt.Dimension;
import java.sql.Connection;

import org.freedom.infra.x.swing.JFrame;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.Login;

public class ProcessaPonto extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProcessaPonto() {
		super();
		initialize();
	}
	
	private void initialize() {		
		this.setSize( new Dimension( 400, 200 ) );
		this.setAlwaysOnTop( true );
		this.setVisible( true );
	}
	
	public static void main( String[] args ) {		
		new ProcessaPonto();
	}
	
	private static Connection getConexao( String sUsu, String sSenha ) {

		Connection con = null;
		try {
			String strBanco = Aplicativo.getParameter( "banco" );
			String strDriver = Aplicativo.getParameter( "driver" );
			Login.getConexao( strBanco, strDriver, sUsu, sSenha );
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return con;
	}
}
