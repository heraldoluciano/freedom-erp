package org.freedom.modulos.grh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.PainelImagem;
import org.freedom.infra.x.swing.JFrame;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.Login;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProcessaPonto extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JPanelPad pnGeral = new JPanelPad( new BorderLayout());
	
	private JPanelPad pnLogo = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pnFoto = new JPanelPad( );
	
	private JPanelPad pnCampos = new JPanelPad();
	
	private PainelImagem ImgFoto = new PainelImagem( 65000 );
	
	private JTextFieldPad txtCodBar = new JTextFieldPad( JTextFieldPad.TP_STRING , 30, 0);
	
	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE , 12, 0);
	
	private JTextFieldPad txtHorario = new JTextFieldPad( JTextFieldPad.TP_TIME , 20, 0);
	
	private JTextFieldPad txtNome = new JTextFieldPad( JTextFieldPad.TP_STRING , 50, 0);
	
	private JLabelPad lbStatus = null;
	
	private JLabelPad lbLogo =  new JLabelPad( Icone.novo( "bannerPonto.jpg" ) );
	
	private javax.swing.Timer timer;
	
	private JLabel label;
	
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
		this.setResizable( false );
		montaTela();
		disparaRelogio();
		
	}
	
	private void montaTela(){
		
		add( pnGeral );
	
	
		pnLogo.setPreferredSize( new Dimension( 400, 50 ) );
		pnGeral.add( pnLogo, BorderLayout.NORTH );
		pnLogo.add( lbLogo, BorderLayout.CENTER );
		pnFoto.setPreferredSize( new Dimension( 100, 50 ) );
		pnGeral.add( pnFoto, BorderLayout.EAST );
		pnFoto.adic( ImgFoto, 0, 0, 95, 115 );
		pnCampos.setPreferredSize( new Dimension( 410, 100 ) );
		pnGeral.add( pnCampos, BorderLayout.WEST );		
	
		pnCampos.adic( new JLabelPad("Código de barras"), 7, 5, 300, 20 );
		pnCampos.adic( txtCodBar, 7, 25, 200, 20 );
		pnCampos.adic( new JLabelPad("Nome"), 7, 45, 300, 20 );
		txtNome.setEditable( false );
		pnCampos.adic( txtNome, 7, 65, 200, 20 );
		pnCampos.adic( new JLabelPad("Data"), 210, 5, 80, 20 );
		pnCampos.adic( txtData, 210, 25, 80, 20 );		
		txtData.setVlrDate( new Date() );
		txtData.setEditable( false );
		txtCodBar.requestFocus();
	
		pnCampos.adic( new JLabelPad("Horário"), 300, 5, 80, 20 );
		label = new JLabel();
		label.setFont( new Font( "Arial", Font.BOLD, 16 ) );
		JPanel panel = new JPanel();
		pnCampos.adic( label,  300, 25, 90, 20  );	
				
	}
	
	private void carregaInfo(){
		
		String status = null;
		
		
		lbStatus = new JLabelPad( Icone.novo( status + ".jpg" ) );
		
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
		
		if (e.getSource()==timer) {
			GregorianCalendar calendario = new GregorianCalendar();
			int h = calendario.get( GregorianCalendar.HOUR_OF_DAY );
			int m = calendario.get( GregorianCalendar.MINUTE );
			int s = calendario.get( GregorianCalendar.SECOND );
			String hora = ( ( h < 10 ) ? "0" : "" ) + h + ":" + ( ( m < 10 ) ? "0" : "" ) + m + ":" + ( ( s < 10 ) ? "0" : "" ) + s;
			label.setText( hora );
		}
		
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
