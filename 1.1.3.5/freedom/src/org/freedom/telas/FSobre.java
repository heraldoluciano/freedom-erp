/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FLogin.java <BR>
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
 * Comentários para a classe...
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;

public class FSobre extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JButton btMemoria = new JButton( "Reciclar", Icone.novo( "btExecuta.gif" ) );

	private JTabbedPanePad tpnSobre = new JTabbedPanePad();

	private JPanelPad pnSobre = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnEquipe = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnSistema = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private ImageIcon img = Icone.novo( Aplicativo.strSplash );

	private long lMemLivre;

	private long lMemTotal;

	private long lMemUtilizada;

	private long lMemMaxima;

	private JLabelPad labelEquipe = null;
	
	private JLabelPad labelSistema = new JLabelPad();
	
	private Font font = new Font( "Verdana", Font.BOLD + Font.ITALIC, 13 );

	public FSobre() {

		super( Aplicativo.telaPrincipal );
		setTitulo( "Sobre" );
		setAtribos( 312, 330 );
		setToFrameLayout();
		btMemoria.addActionListener( this );
		c.add( tpnSobre, BorderLayout.CENTER );
		tpnSobre.addTab( "Sobre", pnSobre );

		JLabelPad lbImg = new JLabelPad( img );
		lbImg.setPreferredSize( new Dimension( img.getIconWidth(), img.getIconHeight() ) );
		String sVersao = "";

		try {
			URL uPath = getClass().getResource( "FSobre.class" );
			JarURLConnection juc = (JarURLConnection) uPath.openConnection();
			sVersao = Funcoes.dateToStrDataHora( new Date( juc.getJarEntry().getTime() ) );
		} catch ( Exception err ) {
		}

		pnSobre.add( lbImg, BorderLayout.NORTH );
		pnSobre.add( new JLabelPad( "<HTML><BR> Versão do jar: " + sVersao + "<BR> " + Aplicativo.sEmpSis + "<BR>" + ( new GregorianCalendar().get( Calendar.YEAR ) ) + "<BR></HTML>" ), BorderLayout.CENTER );

		tpnSobre.addTab( "Equipe", pnEquipe );
		
		StringBuffer sHtmlEquipe = new StringBuffer();
		
		sHtmlEquipe.append( "<HTML>" );
		sHtmlEquipe.append( "<TABLE width='280' border=\"1\">" );
		
		for ( int i = 0; Aplicativo.vEquipeSis.size() > i; i++ ) {
			
			sHtmlEquipe.append( "<TR><CENTER>" );
			sHtmlEquipe.append( Aplicativo.vEquipeSis.elementAt( i ).toString() );
			sHtmlEquipe.append( "</CENTER></TR>" );
		}
		
		sHtmlEquipe.append( "</TABLE></HTML>" );
		
		labelEquipe = new JLabelPad( sHtmlEquipe.toString() );
		labelEquipe.setFont( font );

		pnEquipe.add( new JScrollPane( labelEquipe ) , BorderLayout.CENTER );

		tpnSobre.addTab( "Sistema", pnSistema );

		labelSistema.setFont( font );
		labelSistema.setForeground( Color.BLUE );

		pnSistema.add( labelSistema, BorderLayout.CENTER );
		pnSistema.add( btMemoria, BorderLayout.SOUTH );

		carregaInfoSis();
	}

	public void carregaInfoSis() {

		Runtime.getRuntime().gc();
		System.gc();
		
		lMemLivre = ( ( Runtime.getRuntime().freeMemory() / 1024 ) / 1024 );
		lMemTotal = ( ( Runtime.getRuntime().totalMemory() / 1024 ) / 1024 );
		lMemUtilizada = lMemTotal - lMemLivre;
		lMemMaxima = ( ( Runtime.getRuntime().maxMemory() / 1024 ) / 1024 );
		
		StringBuffer buf = new StringBuffer();
		
		buf.append( "<HTML>" );
		
		buf.append( "<TABLE width='290' border=\"1\"><CENTER>" );
		buf.append( "<TR>" );
		buf.append( "<TD>Memória maxima:</TD>" );
		buf.append( "<TD>" + lMemMaxima + " MB" + "</TD>" );
		buf.append( "</TR>" );
		buf.append( "<TR>" );
		buf.append( "<TD>Memória total:</TD>" );
		buf.append( "<TD>" + lMemTotal + " MB" + "</TD>" );
		buf.append( "</TR>" );
		buf.append( "<TR>" );
		buf.append( "<TD>Memória utilizada:</TD>" );
		buf.append( "<TD>" + lMemUtilizada + " MB" + "</TD>" );
		buf.append( "</TR>" );
		buf.append( "<TR>" );
		buf.append( "<TD>Memória livre:</TD>" );
		buf.append( "<TD>" + lMemLivre + " MB" + "</TD>" );
		buf.append( "</TR>" );
		buf.append( "</CENTER></TABLE>" );
		
		buf.append( "</HTML>" );
		
		labelSistema.setText( buf.toString() );	
		
		repaint();
	}

	public void actionPerformed( ActionEvent evt ) {
		
		if ( evt.getSource() == btMemoria ) {
		
			carregaInfoSis();
		}
		
		super.actionPerformed( evt );
	}

}
