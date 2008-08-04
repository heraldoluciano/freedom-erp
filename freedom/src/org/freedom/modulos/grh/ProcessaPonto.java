package org.freedom.modulos.grh;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.PainelImagem;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.x.swing.JFrame;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.Login;

public class ProcessaPonto extends JFrame implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private JPanelPad pnGeral = new JPanelPad( new BorderLayout());
	
	private JPanelPad pnLogo = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pnFoto = new JPanelPad( );
	
	private JPanelPad pnCampos = new JPanelPad();
		
	private JTextFieldPad txtCodMatricula = new JTextFieldPad( JTextFieldPad.TP_STRING , 30, 0);
	
	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE , 12, 0);
	
	private JTextFieldPad txtHorario = new JTextFieldPad( JTextFieldPad.TP_TIME , 20, 0);
		
	private JLabelPad lbStatus = null;
	
	private JLabelPad lbLogo =  new JLabelPad( Icone.novo( "bannerPonto.jpg" ) );
	
//	private JLabelPad lbFoto = null;
	
	private PainelImagem lbFoto = new PainelImagem( 260000 );
	
	private javax.swing.Timer timer;
	
	private JLabel label;
	
	private JLabel lbApelido;
	
	private static Connection con = null;
	
	public ProcessaPonto() {
		super();
		initialize();
	}
	
	private void initialize() {		
		
		this.setSize( new Dimension( 510, 200 ) );
		this.setAlwaysOnTop( true );
		this.setTitle( "Ponto eletrônico" );
		this.setLocation( 200, 200 );
		this.setVisible( true );
		this.setDefaultCloseOperation( DISPOSE_ON_CLOSE );	
		//this.setResizable( false );
		montaTela();
		disparaRelogio();
		
		txtCodMatricula.addKeyListener( this );
	}
	
	private void montaTela(){
		
		add( pnGeral );
	
		pnLogo.setPreferredSize( new Dimension( 400, 50 ) );
		pnGeral.add( pnLogo, BorderLayout.NORTH );
		pnLogo.add( lbLogo, BorderLayout.CENTER );
		pnFoto.setPreferredSize( new Dimension( 100, 50 ) );
		pnGeral.add( pnFoto, BorderLayout.EAST );
		pnCampos.setPreferredSize( new Dimension( 410, 100 ) );
		pnGeral.add( pnCampos, BorderLayout.WEST );		
	
		pnCampos.adic( new JLabelPad("Matricula"), 7, 5, 300, 20 );
		pnCampos.adic( txtCodMatricula, 7, 25, 150, 30 );
		lbApelido = new JLabel();
		pnCampos.adic( lbApelido, 7, 65, 150, 20 );
		lbApelido.setFont( new Font( "Arial", Font.BOLD, 16 ) );	
		lbApelido.setForeground( Color.BLUE );
		pnCampos.adic( txtData, 210, 25, 80, 20 );		
		txtData.setVlrDate( new Date() );
		txtData.setEditable( false );
		txtCodMatricula.requestFocus();
	
		label = new JLabel();
		label.setFont( new Font( "Arial", Font.BOLD, 16 ) );
		JPanel panel = new JPanel();
		pnCampos.adic( label,  300, 75, 90, 20  );	
		pnFoto.adic( lbFoto, 0, 0, 95, 115 );
				
	}
	
	private boolean carregaInfo(){
		
		String Sstatus = "";
		String sFoto = "";
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bRet = false;
		
		sSQL.append( "SELECT APELIDOEMPR, FOTOEMPR FROM RHEMPREGADO " );
		sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND MATEMPR=?" );
			
		try {
			
			if (con!=null) {
			
				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, 5 );
				ps.setInt( 2, 1 );
				ps.setInt( 3, txtCodMatricula.getVlrInteger() );
				
				rs = ps.executeQuery();
				
				if( rs.next() ){
					
					lbApelido.setText( rs.getString( "APELIDOEMPR") );
					
					Blob bVal = rs.getBlob(2);
					if (bVal != null) {
						lbFoto.setVlrBytes(bVal.getBinaryStream());
					}
					bRet = true;
				}
			}
			else {
				System.out.println("CONEXÃO NULA!");
			}			
		} 
		catch ( Exception e ) {
			
			e.printStackTrace();
		}
		return bRet;
		//pnCampos.adic( lbStatus, 320, 65, 50, 50 );
			
	}
		
	private  void disparaRelogio() {		
		
		if ( timer == null ) {
			timer = new javax.swing.Timer( 1000, this );
			timer.setInitialDelay( 0 );
			timer.start();
		} else if ( !timer.isRunning() ) {
			timer.restart();
		}
	}
	
	public void actionPerformed( ActionEvent e ) {
		
		if( e.getSource() == timer ) {
			GregorianCalendar calendario = new GregorianCalendar();
			int h = calendario.get( GregorianCalendar.HOUR_OF_DAY );
			int m = calendario.get( GregorianCalendar.MINUTE );
			int s = calendario.get( GregorianCalendar.SECOND );
			String hora = ( ( h < 10 ) ? "0" : "" ) + h + ":" + ( ( m < 10 ) ? "0" : "" ) + m + ":" + ( ( s < 10 ) ? "0" : "" ) + s;
			label.setText( hora );
		}
	}
	
	public static void main( String[] args ) {		
	
		try {
			ProcessaPonto pp =  new ProcessaPonto();		
			con = getConexao( "SYSDBA", "masterkey" );		
			//pp.carregaInfo();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static Connection getConexao( String sUsu, String sSenha ) {

		Connection con = null;
		try {
			Aplicativo.setLookAndFeel( null );
			String strBanco = Aplicativo.getParameter( "banco" );
			String strDriver = Aplicativo.getParameter( "driver" );
			con = Login.getConexao( strBanco, strDriver, sUsu, sSenha );
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return con;
	}
	
	private void insertPonto(){
		
		StringBuffer sInsert = new StringBuffer();
		PreparedStatement ps = null;
	}

	public void keyPressed( KeyEvent e ) {

		if( e.getSource() == txtCodMatricula ){
			if( e.getKeyCode() == KeyEvent.VK_ENTER ){
				
				if( carregaInfo() ){
					txtCodMatricula.requestFocus();
				//	insertPonto();
					
				}else{
					Funcoes.mensagemInforma( null, "Matricula não encontrada!" );
					txtCodMatricula.requestFocus();
				}
			}
		}		
	}

	public void keyReleased( KeyEvent e ) {}

	public void keyTyped( KeyEvent e ) {}
}
