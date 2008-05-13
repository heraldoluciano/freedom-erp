
package test;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.freedom.infra.x.UIMaker.Button;
import org.freedom.infra.x.UIMaker.Label;
import org.freedom.infra.x.UIMaker.Panel;
import org.freedom.infra.x.UIMaker.effect.Effect;
import org.freedom.infra.x.UIMaker.effect.Fade;
import org.freedom.infra.x.util.text.DecimalDocument;
import org.freedom.infra.x.util.text.IntegerDocument;
import org.freedom.infra.x.util.text.Mask;
import org.freedom.infra.x.util.text.StringDocument;

public class TestComponents extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private Button botao01 = null;

	private Label label01 = null;

	private Panel panel01 = null;

	private Panel panel02 = null;

	private JTextField textField01 = null;

	/**
	 * This is the default constructor
	 */
	public TestComponents() {

		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		this.setSize(400, 400);
		this.setContentPane(getJContentPane());
		this.setTitle( "JFrame" );
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {

		if ( jContentPane == null ) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			//jContentPane.setBackground( Color.ORANGE );
			jContentPane.add(getTextField01(), null);
			/*jContentPane.add(getBotao01(), null);
			jContentPane.add(getPanel01(), null);
			jContentPane.add(getPanel02(), null);
			jContentPane.add(getLabel01(), null);*/
		}
		return jContentPane;
	}

	private Button getBotao01() {
	
		if ( botao01 == null ) {
			botao01 = new Button();
			botao01.setBounds(new Rectangle(15, 15, 120, 69));
			botao01.setPressedIcon(new ImageIcon(getClass().getResource("/test/botao_1_press.jpg")));
			botao01.setIcon(new ImageIcon(getClass().getResource("/test/botao_1.jpg")));
			botao01.setBorderPainted( true );
			botao01.addActionListener( new java.awt.event.ActionListener() {
				public void actionPerformed( java.awt.event.ActionEvent e ) {
					/*Effect effect = new ShineInBorder( 
							panel01, ShineInBorder.SPEED_FAST, Color.WHITE, Color.ORANGE );*/
					Effect effect = new Fade( panel01 );
					((Fade)effect).setMaxAlfa( 1f );
					((Fade)effect).setColor( Color.BLACK );
					((Fade)effect).addComponent( panel02 );
					((Fade)effect).setAction( Fade.ACTION_OUT );
					effect.doStart();
				}
			} );
			
		}
		return botao01;
	}

	private Label getLabel01() {
	
		if ( label01 == null ) {
			label01 = new Label();
			label01.setDisplay( 
					"<a href=#>testes</a><BR>\"<teste 1>\"\nteste 2\nteste 3" );
			label01.setBounds(new Rectangle(15, 150, 120, 90));
			//label01.setIcon(new ImageIcon(getClass().getResource("/test/botao_1.jpg")));
			//label01.setIconTextGap( 20 );
			//label01.setLocation( 14, 105 );
		}
		return label01;
	}

	/**
	 * This method initializes panel01	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private Panel getPanel01() {
	
		if ( panel01 == null ) {
			panel01 = new Panel( );
			panel01.setBounds(new Rectangle(25, 196, 358, 134));
			Label teste = new Label( "Teste de label" );
			teste.setBorder( BorderFactory.createEtchedBorder() );
			panel01.add( teste , 10, 5, 120, 20 );
		}
		return panel01;
	}

	/**
	 * This method initializes panel02	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private Panel getPanel02() {
	
		if ( panel02 == null ) {
			panel02 = new Panel( );
			panel02.setBounds(new Rectangle(60, 100, 150, 800));
			Label teste = new Label( "TESTE DE LABEL 02" );
			teste.setBorder( BorderFactory.createEtchedBorder() );
			panel02.add( teste , 10, 5, 120, 20 );
			panel02.setArc( 25, 25 );
			panel02.setBackground( Color.BLACK );
			panel02.setOpaque( true );
		}
		return panel02;
	}

	/**
	 * This method initializes textField01	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextField01() {
	
		if ( textField01 == null ) {
			textField01 = new JTextField();
			textField01.setBounds( 50, 50, 200, 30 );
			textField01.setDocument( new IntegerDocument( 8 ) );
		}
		return textField01;
	}

}  //  @jve:decl-index=0:visual-constraint="187,10"
