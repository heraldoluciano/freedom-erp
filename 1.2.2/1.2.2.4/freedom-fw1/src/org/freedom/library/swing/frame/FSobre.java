/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FLogin.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários para a classe...
 */

package org.freedom.library.swing.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.SystemFunctions;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.util.SwingParams;

public class FSobre extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JButtonPad btMemoria = new JButtonPad( "Reciclar", Icone.novo( "btExecuta.gif" ) );

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
	
	private Font font = SwingParams.getFontboldmed();
	
	public FSobre() {

		super( Aplicativo.telaPrincipal );
		
		setTitulo( "Sobre" );
		setAtribos( 330, 300 );
		
		setToFrameLayout();
		
		btMemoria.addActionListener( this );
		c.add( tpnSobre, BorderLayout.CENTER );
		tpnSobre.addTab( "Sobre", pnSobre );

		JLabelPad lbImg = new JLabelPad( img );
		lbImg.setPreferredSize( new Dimension( img.getIconWidth(), img.getIconHeight() ) );
		String sdatacompilacao = "";

		try {
			
			Date datacompilacao = SystemFunctions.getClassDateCompilation(this.getClass());	
			
			sdatacompilacao = Funcoes.dateToStrDataHora( datacompilacao ) ;
			
		} 
		catch ( Exception err ) {
			err.printStackTrace();
		}

		pnSobre.add( lbImg, BorderLayout.NORTH );
		
		StringBuilder htmlversao = new StringBuilder();
		htmlversao.append("<HTML>");
		
		htmlversao.append("<BR>");
		htmlversao.append(" Versão: ");
		
		htmlversao.append( SystemFunctions.getVersionSis(this.getClass()) );
		
		htmlversao.append("</BR>");	

		htmlversao.append("<BR>");
		htmlversao.append(" Compilação: ");	
		
		htmlversao.append( sdatacompilacao );
		
		htmlversao.append("</BR>");	

		htmlversao.append("</HTML>");
		 
		JLabelPad pnversao = new JLabelPad(htmlversao.toString());
		
		pnSobre.add(pnversao, BorderLayout.CENTER);
		
		tpnSobre.addTab( "Equipe", pnEquipe );
		
		StringBuffer sHtmlEquipe = new StringBuffer();
		
		sHtmlEquipe.append( "<HTML>" );
		sHtmlEquipe.append( "<TABLE width='300' border=\"1\">" );
		
		Vector<String> equipesis = Aplicativo.getEquipeSis();
		
		for ( int i = 0; equipesis.size() > i; i++ ) {
			
			sHtmlEquipe.append( "<TR><CENTER>" );
			sHtmlEquipe.append( equipesis.elementAt( i ).toString() );
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
		
		buf.append( "<TABLE width='300' border=\"1\"><CENTER>" );
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
