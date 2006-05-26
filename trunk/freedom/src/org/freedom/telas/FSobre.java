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
import java.awt.event.ActionEvent;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;

public class FSobre extends FFDialogo {
	private static final long serialVersionUID = 1L;
	private JButton btMemoria = new JButton("RMA",Icone.novo("btExecutar.gif"));
    private JTabbedPanePad tpnSobre = new JTabbedPanePad();
    private JPanelPad pnSobre = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
    private JPanelPad pnEquipe = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
    private JPanelPad pnSistema = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
    private ImageIcon img = Icone.novo(Aplicativo.strSplash);
	private long lMemLivre;
  	private long lMemTotal;
  	private long lMemUtilizada;
  	private long lMemMaxima;

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
  		pnSobre.add(new JLabelPad ("<HTML><BR> Versão do jar: "+sVersao+"<BR> "+ Aplicativo.sEmpSis+"<BR>" +
  				(new GregorianCalendar().get(Calendar.YEAR))+"<BR></HTML>"),BorderLayout.CENTER);
	
  		
	
  //  System.gc();
	
	
	tpnSobre.addTab("Equipe "+Aplicativo.sNomeSis ,pnEquipe);
    String sHtmlEquipe = "<HTML><BR><CENTER>";
    for (int i = 0;Aplicativo.vEquipeSis.size()>i;i++){
        sHtmlEquipe += Aplicativo.vEquipeSis.elementAt(i).toString()+"<BR>";
    }
    
	pnEquipe.add(new JLabelPad (sHtmlEquipe+"</CENTER><BR>"));
	
	
	tpnSobre.addTab("Sistema" ,pnSistema);
	carregaInfoSis();
	pnSistema.add(new JLabelPad("<HTML>" +
 				"Memória maxima:"+lMemMaxima+" MB" + "<BR>" +
 				"Memória total:"+lMemTotal+" MB" + "<BR>" +
 				"Memória utilizada:"+lMemUtilizada+" MB" + "<BR>" +
 				"Memória livre:"+lMemLivre+" MB"+"<HTML>"),BorderLayout.NORTH);
    pnSistema.add(btMemoria,BorderLayout.SOUTH);
	
  }
  public void carregaInfoSis() {
		lMemLivre =((Runtime.getRuntime().freeMemory() / 1024) / 1024);
  		lMemTotal = ((Runtime.getRuntime().totalMemory() / 1024) / 1024);
  		lMemUtilizada = lMemTotal - lMemLivre;
  		lMemMaxima = ((Runtime.getRuntime().maxMemory() /1024) / 1024);
  		System.out.println("Carregando informações de memória..");
  }
   public void actionPerformed(ActionEvent evt) {
        super.actionPerformed(evt);    
    	if (evt.getSource() == btMemoria) 
    		carregaInfoSis();
        }	
  	
}    
