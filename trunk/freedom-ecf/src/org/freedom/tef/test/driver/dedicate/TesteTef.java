
package org.freedom.tef.test.driver.dedicate;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.freedom.tef.driver.dedicate.DedicatedAction;
import org.freedom.tef.driver.dedicate.DedicatedTef;
import org.freedom.tef.driver.dedicate.DedicatedTefEvent;
import org.freedom.tef.driver.dedicate.DedicatedTefListener;

public class TesteTef extends JFrame implements DedicatedTefListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel cabecalho = null;

	private JLabel operador = null;

	private JLabel cliente = null;

	private JButton action = null;

	private DedicatedTef tef;

	private JButton ler_cartao = null;

	private JButton verifica_pinpad = null;


	public TesteTef() throws Exception  {

		super();
		initialize();
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		setLocationRelativeTo( this );
		
		tef = DedicatedTef.getInstance( "totem.ini", this );		
	}

	private void initialize() {

		this.setSize( 500, 300 );
		this.setContentPane( getJContentPane() );
		this.setTitle( "JFrame" );
	}
	
	private JPanel getJContentPane() {

		if ( jContentPane == null ) {
			cliente = new JLabel();
			cliente.setText( "menssagem para cliente" );
			cliente.setBackground( Color.darkGray );
			cliente.setForeground( Color.white );
			cliente.setHorizontalAlignment( SwingConstants.CENTER );
			cliente.setOpaque( true );
			cliente.setBounds( new Rectangle( 10, 120, 470, 60 ) );
			operador = new JLabel();
			operador.setText( "menssagem para operador" );
			operador.setBackground( Color.darkGray );
			operador.setForeground( Color.white );
			operador.setHorizontalAlignment( SwingConstants.CENTER );
			operador.setOpaque( true );
			operador.setBounds( new Rectangle( 10, 50, 470, 60 ) );
			cabecalho = new JLabel();
			cabecalho.setText( "Cabeçario do Menu" );
			cabecalho.setBackground( Color.darkGray );
			cabecalho.setForeground( Color.white );
			cabecalho.setHorizontalAlignment( SwingConstants.CENTER );
			cabecalho.setOpaque( true );
			cabecalho.setBounds( new Rectangle( 10, 10, 470, 30 ) );
			jContentPane = new JPanel();
			jContentPane.setLayout( null );
			jContentPane.add( cabecalho, null );
			jContentPane.add( operador, null );
			jContentPane.add( cliente, null );
			jContentPane.add( getAction(), null );
			jContentPane.add(getLer_cartao(), null);
			jContentPane.add(getVerifica_pinpad(), null);
		}
		return jContentPane;
	}

	private JButton getAction() {

		if ( action == null ) {
			action = new JButton( " requisição de venda" );
			action.setBounds( new Rectangle( 310, 190, 170, 30 ) );
			action.addActionListener( this );
		}
		return action;
	}

	public void actionCommand( DedicatedTefEvent e ) {

		if ( e.getSource() == this ) {
			if ( e.getAction() == DedicatedAction.ERRO ) {
    			JOptionPane.showMessageDialog( this, e.getMessage(), "Erro TEF", JOptionPane.ERROR_MESSAGE );
    		}
			else if ( e.getAction() == DedicatedAction.REMOVER_CABECALHO_MENU ) {
    			cabecalho.setText( "É pra limpar este campo !!!" );
    		}
		}		
	}

	/**
	 * This method initializes ler_cartao	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLer_cartao() {
	
		if ( ler_cartao == null ) {
			ler_cartao = new JButton( "ler cartão" );
			ler_cartao.setBounds( new Rectangle( 10, 230, 170, 30 ) );
			ler_cartao.addActionListener( this );
		}
		return ler_cartao;
	}

	/**
	 * This method initializes verifica_pinpad	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getVerifica_pinpad() {
	
		if ( verifica_pinpad == null ) {
			verifica_pinpad = new JButton( "verifica pinpad" );
			verifica_pinpad.setBounds( new Rectangle( 10, 190, 170, 30 ) );
			verifica_pinpad.addActionListener( this );
		}
		return verifica_pinpad;
	}
	
	public void actionPerformed( ActionEvent e ) {
		
		if ( e.getSource() == action ) {
			tef.requestSale( new BigDecimal( "15.748" ), 123456, Calendar.getInstance().getTime(), "teste" );
		}
		else if ( e.getSource() == verifica_pinpad ) {
			tef.checkPinPad();
		}
		else if ( e.getSource() == ler_cartao ) {
			tef.readCard( "Sesc Parana - passe o cartao." );
		}
	}

	public static void main( String[] args ) {

		try {
			TesteTef teste = new TesteTef();
			teste.setVisible( true );
		}
		catch ( Exception e ) {
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, e.getMessage(), "Erro TEF", JOptionPane.ERROR_MESSAGE );
			System.exit( 0 );
		}
	}
}
