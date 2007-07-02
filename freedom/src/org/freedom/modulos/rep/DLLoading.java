/**
 * @version 05/03/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)DLLoading.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Display de espera.
 */

package org.freedom.modulos.rep;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.freedom.bmps.Icone;


public class DLLoading extends JWindow implements ActionListener {

	private static final long serialVersionUID = 1;
	
	private final Color azul_claro = new Color( 0.95f, 0.95f, 1f );
	
	private final Color azul = new Color( 0.6f, 0.62f, 0.8f );
	
	private final JLabel aguarde = new JLabel( "Aguarde ...", SwingConstants.CENTER );
	
	private final JLabel loading = new JLabel( "", SwingConstants.CENTER );
	
	private final JPanel panel = new JPanel();
	
	private ImageIcon[] img = new ImageIcon[ 8 ];
	
	private Timer timer = new Timer( 120, this );
	
	private int index = 0;
	
	public DLLoading() {
		
		setSize( 200, 100 );
		setLocationRelativeTo( null );
		setBackground( azul_claro );
		
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.setBackground( azul_claro );		

		loadImages();
		
		panel.setLayout( new BorderLayout() );
		panel.setBackground( azul_claro );
		panel.setBorder( BorderFactory.createEtchedBorder( Color.GRAY, Color.GRAY ) );
		c.add( panel, BorderLayout.CENTER );
		
		loading.setIcon( img[ index ] );
		panel.add( loading, BorderLayout.CENTER );
		
		aguarde.setFont( new Font( "Heveltica", Font.BOLD, 18 ) );
		aguarde.setForeground( azul );
		aguarde.setPreferredSize( new Dimension( 200, 30 ) );
		panel.add( aguarde, BorderLayout.SOUTH );
	}
	
	private void loadImages() {
		
		for ( int i=0; i < img.length; i++ ) {
			img[ i ] = Icone.novo( "load_" + i + ".gif" );
		}
	}
	
	public void start() {

		if( ! timer.isRunning() ) {
			timer.start();
		}		
		setVisible( true );
	}
	
	public void stop() {
		
		timer.stop();
		dispose();
	}
	
	public void actionPerformed( ActionEvent e ) {

		if ( index == 8 ) {
			index = 0;
		}
		
		loading.setIcon( img[ index++ ] );
		loading.updateUI();
		panel.updateUI();
	}

	public static void main( String[] args ) {

		DLLoading load = new DLLoading();
		load.start();
	}
}
