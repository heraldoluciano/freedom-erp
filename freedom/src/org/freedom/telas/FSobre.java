/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FLogin.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Comentários para a classe...
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import org.freedom.componentes.JLabelPad;
import javax.swing.JPanel;
import org.freedom.componentes.JTabbedPanePad;

import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;


public class FSobre extends FFDialogo {
  private JTabbedPanePad tpnSobre = new JTabbedPanePad();
  private JPanel pnSobre = new JPanel(new BorderLayout());
  private JPanel pnEquipe = new JPanel(new BorderLayout());
  ImageIcon img = Icone.novo(Aplicativo.strSplash);
  public FSobre () {
  	super(Aplicativo.telaPrincipal);
    setTitulo("Sobre...");
	setAtribos(312,330);
	
	setToFrameLayout();

	c.add(tpnSobre,BorderLayout.CENTER);
	tpnSobre.addTab("Sobre",pnSobre);
	
	JLabelPad lbImg = new JLabelPad(img);
	lbImg.setPreferredSize(new Dimension(img.getIconWidth(),img.getIconHeight()));
    String sVersao = "";
	try {
      URL uPath = getClass().getResource("FSobre.class");
      JarURLConnection juc = (JarURLConnection)uPath.openConnection();
      sVersao = Funcoes.dateToStrDataHora(new Date(juc.getJarEntry().getTime()));
	}
	catch(Exception err) { };
	
	pnSobre.add(lbImg,BorderLayout.NORTH);
	pnSobre.add(new JLabelPad ("<HTML><BR>"+
	        " Freedom<BR>"+
	        " Versão do jar: "+sVersao+"<BR>"+
			" Setpoint Informática Ltda.   1994 - "+
			(new GregorianCalendar().get(Calendar.YEAR))+"<BR></HTML>"),BorderLayout.CENTER);

  
	tpnSobre.addTab("Equipe freedom",pnEquipe);
	pnEquipe.add(new JLabelPad ("<HTML><BR><CENTER>"+
        " Robson Sanchez - Supervisor/Analista<BR>"+
        " Anderson Sanchez - Supervisor/Programador<BR>"+
        " Fernando Oliveira - Programador<BR>"+
        " Moyzes Braz - Arte gráfica<BR>"+
        " Marco Antonio - Testes/Implantação</CENTER><BR>"));
  }
}    
