/**
 * @version 16/07/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)StatusBar.java <BR>
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
 * Comentários da classe.....
 */

package org.freedom.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;


/**
 * @author robson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StatusBar extends JPanelPad {

	private JPanelPad pnEst = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnCentro = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnEsquerda = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnDescEst = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnFilial = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnRazFilial = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnUsuario = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnInfo = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnRelogio = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnIconFilial = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnIconEst = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnIconInfo = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnIDUSU = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnDescInfo = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnIconUsuario = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JLabelPad lFilial = new JLabelPad();
	private JLabelPad lEst = new JLabelPad();  
	private JLabelPad lUsuario = new JLabelPad();
	private JLabelPad lInfo = new JLabelPad();
	private JLabelPad lRelogio = new JLabelPad();  
	private int iNumEst = 0;
	private int iCodFilial = 0;
	private String sRazFilial = "";
	private String sDescEst = "";
	private String sIDUsu = "";
	private ImageIcon iconFilial = Icone.novo("statusbarFilial.gif");
	private ImageIcon iconUsuario = Icone.novo("statusbarUsu.gif");
	private ImageIcon iconEst = Icone.novo("statusbarPc.gif");
	private ImageIcon iconRelogio = Icone.novo("statusbarTime.gif");
	private ImageIcon iconInfo = Icone.novo("statusbarInfo.gif");
	
	
	//private Icone.novo("btCalc.gif")

	/**
	 * @param arg0
	 * @param arg1
	 */
	public StatusBar(LayoutManager arg0, boolean arg1) {
		super(JPanelPad.TP_JPANEL, arg0, arg1);
		MontaStatusBar();
	}

	/**
	 * @param arg0
	 */
	public StatusBar(LayoutManager arg0) {
		super(JPanelPad.TP_JPANEL, arg0);
		MontaStatusBar();
	}

	/**
	 * @param arg0
	 */
	public StatusBar(boolean arg0) {
		super(JPanelPad.TP_JPANEL, arg0);
		MontaStatusBar();
	}

	/**
	 * 
	 */
	public StatusBar() {
		super();
		MontaStatusBar();
	}
	private void MontaStatusBar() {
		montaStatus();
		Timer tm = new Timer(1000,
		  new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
			  upRelogio();
			}
		  }
		);
		tm.start();
	}
	
	private void montaStatus() {
		
	  this.setLayout(new BorderLayout());
	  this.setPreferredSize(new Dimension(300, 25));
	  this.setBorder(BorderFactory.createRaisedBevelBorder());

	  pnRazFilial.add(lFilial);
      pnDescEst.add(lEst);
      pnIDUSU.add(lUsuario);
      pnDescInfo.add(lInfo);
      
      pnIconFilial.add(new JLabelPad(iconFilial),BorderLayout.WEST);
      pnIconEst.add(new JLabelPad(iconEst),BorderLayout.WEST);
      pnIconUsuario.add(new JLabelPad(iconUsuario),BorderLayout.WEST);
      pnIconInfo.add(new JLabelPad(iconInfo), BorderLayout.WEST);

	  lFilial.setPreferredSize(new Dimension(260, 20));
	  lFilial.setFont(new Font("Arial", Font.PLAIN, 12));
	  lFilial.setForeground( new Color(118, 89, 170));
      
	  lEst.setPreferredSize(new Dimension(100, 20));
	  lEst.setFont(new Font("Arial", Font.PLAIN, 12));
	  lEst.setForeground( new Color(118, 89, 170));
     
	  lUsuario.setPreferredSize(new Dimension(100, 20));
	  lUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
	  lUsuario.setForeground( new Color(118, 89, 170));

      lInfo.setPreferredSize(new Dimension(180,20));
      lInfo.setFont(new Font("Arial", Font.PLAIN,12));
      lInfo.setForeground(new Color(118, 89, 170));
      
	  pnFilial.setPreferredSize(new Dimension(180,23));
	  pnFilial.setBorder(BorderFactory.createLoweredBevelBorder());

      pnEst.setPreferredSize(new Dimension(180,23));
	  pnEst.setBorder(BorderFactory.createLoweredBevelBorder());
	  //pnEst.add(lEst, BorderLayout.WEST);
	  
	  pnInfo.setBorder(BorderFactory.createLoweredBevelBorder());

      pnFilial.add(pnIconFilial,BorderLayout.WEST);
      pnFilial.add(pnRazFilial,BorderLayout.CENTER);
	  
      pnEst.add(pnIconEst,BorderLayout.WEST);
      pnEst.add(pnDescEst,BorderLayout.CENTER);
      
      pnUsuario.add(pnIconUsuario,BorderLayout.WEST);
      pnUsuario.add(pnIDUSU,BorderLayout.CENTER);
      
      pnInfo.add(pnIconInfo,BorderLayout.WEST);
      pnInfo.add(pnDescInfo,BorderLayout.CENTER);
      
      
	  pnUsuario.setBorder(BorderFactory.createLoweredBevelBorder());
	  //pnUsuario.add(lUsuario, BorderLayout.WEST);

	  pnEsquerda.add(pnFilial, BorderLayout.WEST);
	  pnEsquerda.add(pnEst, BorderLayout.CENTER);
      pnCentro.add(pnUsuario, BorderLayout.WEST);
      pnCentro.add(pnInfo, BorderLayout.CENTER);
      
      this.add(pnEsquerda, BorderLayout.WEST);
	  this.add(pnCentro, BorderLayout.CENTER);
     
	  lRelogio.setPreferredSize(new Dimension(125, 20));
	  lRelogio.setFont(new Font("Arial", Font.PLAIN, 12));
	  lRelogio.setForeground( new Color(118, 89, 170));
	  lRelogio.setHorizontalAlignment(SwingConstants.CENTER);
     
	  pnRelogio.setBorder(BorderFactory.createLoweredBevelBorder());
	  pnRelogio.add(new JLabelPad(iconRelogio),BorderLayout.WEST);
	  pnRelogio.add(lRelogio, BorderLayout.CENTER);
	  this.add(pnRelogio, BorderLayout.EAST);
     
//	  c.add(pnStatus, BorderLayout.SOUTH);
	}
	
	public void upRelogio() {
	  String hora = "", minuto = "";
	  String dia = "", mes = "", ano = "";
	  Calendar cal = Calendar.getInstance();
	  hora = ""+(cal.get(Calendar.HOUR_OF_DAY));
	  minuto = ""+(cal.get(Calendar.MINUTE));
	  minuto = minuto.length() > 1 ? minuto : "0"+minuto;
	  dia = "" + (cal.get(Calendar.DAY_OF_MONTH));
	  dia = dia.length() > 1 ? dia : "0"+dia;  
	  mes = "" + (cal.get(Calendar.MONTH) + 1);
	  mes = mes.length() > 1 ? mes : "0"+mes;  
	  ano = "" + (cal.get(Calendar.YEAR));
	  lRelogio.setText(dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto);
	  lRelogio.repaint();
	}
	
	public void setCodFilial(int iCodFilial) {
		this.iCodFilial = iCodFilial;
		ajustaFilial();
	}
	public void setUsuario(String sIDUsu) {
	   this.sIDUsu = sIDUsu.trim();
	   lUsuario.setText(sIDUsu);
	   
	}
	
	public void setNumEst(int iNumEst) {
	   this.iNumEst = iNumEst;
	   ajustaEst(); 		
	}

	public void setRazFilial(String sRazFilial) {
		this.sRazFilial = sRazFilial;
		ajustaFilial();
	}
	public void setDescEst(String sDescEst) {
	   this.sDescEst = sDescEst;
	   ajustaEst();	
	}
	
	public int getCodFilial() {
	   return iCodFilial;
	}
	
	public int getNumEst() {
	   return iNumEst;	
	}
	
	public String getDescEst() {
	   return sDescEst;
	}
	
	public String getUsuario() {
	   return sIDUsu;	
	}
	
	public String getRazFilial() {
		return sRazFilial;
	}
	
	private void ajustaEst() {
	   lEst.setText((" "+iNumEst+"-"+sDescEst.trim()));	
	}
	
	private void ajustaFilial() {
	   lFilial.setText((" "+iCodFilial+"-"+sRazFilial.trim()));
	}
	public void setInfo(String sTexto) {
	   if (sTexto==null) 
	      sTexto="";
	   else 
	      sTexto = sTexto.trim();
	   lInfo.setText(sTexto);
	}

}
