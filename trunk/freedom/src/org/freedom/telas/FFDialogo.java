/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FFDialogo.java <BR>
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

package org.freedom.telas;
import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MenuComponent;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;

public class FFDialogo extends FFilho implements ActionListener, KeyListener {
  public  JButton btCancel = new JButton("Cancelar",Icone.novo("btCancelar.gif"));
  public  JButton btOK = new JButton("OK",Icone.novo("btOk.gif"));
  private JPanel pnBox = new JPanel();
  public  JPanel pnRodape = new JPanel(new BorderLayout());
  private JPanel pnBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER,3,3));
  private JPanel pnGrid = new JPanel(new GridLayout(1,2));
  private JPanel pnBordRodape = new JPanel(new BorderLayout());
  private JPanelPad pin = new JPanelPad();
  public  Container c = getContentPane();
  private Border br = BorderFactory.createEtchedBorder();
  private Component cPai = null;
  boolean setArea = true;
  boolean bUltimo = false;
  public boolean OK = false;
  public boolean bAguardando = false;
  private JLayeredPane pnVidro = new JLayeredPane();
  public JComponent pnPai = null;
  private Modal modal = null;
  public FFDialogo() {
  	this(Aplicativo.telaPrincipal);
  }
  public FFDialogo(Component cOrig) {
    cPai = cOrig;
	setTitle("Dialogo");
	setAtribos(50,50,500,300);
    c.setLayout(new BorderLayout());
    
//    pnGrid.setPreferredSize(new Dimension(220,30));
    btOK.setPreferredSize(new Dimension(110,30));
    btCancel.setPreferredSize(new Dimension(110,30));
    pnGrid.add(btOK);
    pnGrid.add(btCancel);
    pnBotoes.add(pnGrid);
    
    pnBox.add(pnBotoes);
    
    pnRodape.add(pnBox, BorderLayout.EAST);
    
//    pnBordRodape.setPreferredSize(new Dimension(250,30));
    pnBordRodape.setBorder(br);
    pnBordRodape.add(pnRodape);
    
    c.add(pnBordRodape, BorderLayout.SOUTH); 
    
    btCancel.addActionListener(this);
    btCancel.addKeyListener(this);
    btOK.addActionListener(this);
    btOK.addKeyListener(this);
    addKeyListener(this);
    
   
    
//Gambs para tornar o form uma modal:    

    if (cOrig instanceof JFrame)
	  pnPai = ((JFrame)cOrig).getRootPane();
    else if (cOrig instanceof JDialog)
	  pnPai = ((JDialog)cOrig).getRootPane();
    else if (cOrig instanceof JInternalFrame)
      pnPai = ((JInternalFrame)cOrig).getDesktopPane();
    else {
	  pnPai = JOptionPane.getDesktopPaneForComponent(cOrig);
	  if (pnPai==null) {
	    Window win = SwingUtilities.getWindowAncestor(cOrig);
	    if (win instanceof JDialog)
	      pnPai = ((JDialog)win).getRootPane();
	    else if (win instanceof JFrame)
	 	  pnPai = ((JFrame)win).getRootPane();
	  }
    }
    
	MouseInputAdapter adapter = 
	  new MouseInputAdapter(){};
	pnPai.addMouseListener(adapter);
	pnPai.addMouseMotionListener(adapter);
	
	

	putClientProperty("JInternalFrame.frameType", 
	  "optionDialog");
	  
	if (pnPai instanceof JRootPane) {
	  pnVidro.setOpaque(false);
	  pnVidro.add(this);
	  ((JRootPane)pnPai).setGlassPane(pnVidro);
	}
	else 
	  pnPai.add(this,JLayeredPane.MODAL_LAYER); 
  }
  public void setPainel(JPanelPad p) {
    pin = p;
    setArea = false;
  }
  public void setPanel(JComponent p) {
  	c.add(p,BorderLayout.CENTER);
    setArea = false;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK)
        ok();
    else if (evt.getSource() == btCancel)
        cancel();
  }
  public void ok() {
    OK = true;       
    setVisible(false);
  }
  public void cancel() {
    OK = false;
    setVisible(false);
  }
  public void setTitulo(String tit) {
    setTitle(tit);
  }
  public void setAtribos(int X, int Y, int Larg, int Alt) {
    setBounds( X, Y, Larg, Alt);
  }
  public void setAtribos(int Larg, int Alt) {
	setBounds((pnPai.getSize().width-Larg)/2 , (pnPai.getSize().height-Alt)/2, Larg, Alt);
  }
  public void eUltimo() {
    bUltimo = true;    
  }
  public void setToFrameLayout() {
    pnRodape.remove(0);
    pnGrid = new JPanel(new GridLayout(1,1));
    pnGrid.setPreferredSize(new Dimension(100,30));
    JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
    btSair.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          setVisible(false);
        }
      }
    );
    pnGrid.add(btSair);
    pnRodape.add(pnGrid, BorderLayout.EAST);
  }
  public void setAreaComp() {
    pin = new JPanelPad ((int)getSize().getWidth(), (int)getSize().getHeight());
    c.add(pin, BorderLayout.CENTER);
    setArea = false;
  }
  public void adic(Component comp, int X, int Y, int Larg, int Alt) {
    if (setArea)
      setAreaComp();
    comp.addKeyListener(this);
    pin.adic(comp, X, Y, Larg, Alt);
  }
  public void adicInvisivel(Component comp) {
    comp.addKeyListener(this);
  }
  public void setVisible(boolean bVal) {
//	Component fo = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
	super.setVisible(bVal);
	
  	if (bVal) {

  	  if (pnPai instanceof JRootPane)
	    pnVidro.setVisible(true);
	  try {
		setSelected(true);
	  }
	  catch(Exception e) { }
	  
	  modal = new Modal(this);
	  modal.ligaModal(); //Fica parado aki ateh o usuário clicar em OK.
	  if (pnVidro != null && pnPai instanceof JRootPane)
		pnVidro.setVisible(false);
	  if (cPai instanceof JInternalFrame) {
		try {
		  ((JInternalFrame)cPai).setSelected(true);
//		  if (fo != null && fo.isShowing())
//		  	fo.requestFocus(); 
		} 
		catch (PropertyVetoException e) { }
	  }
	  modal = null;
	}
	else {
		if (modal != null) {
			modal.paraModal();
		}
	}
  }
  public void keyPressed(KeyEvent kevt) {
    if ((bUltimo) & (kevt.getKeyCode() == KeyEvent.VK_ENTER))
      btOK.doClick();
    else if (kevt.getKeyCode() == KeyEvent.VK_ESCAPE)
      btCancel.doClick();  
  }
  public void keyTyped(KeyEvent kevt) { }
  public void keyReleased(KeyEvent kevt) { }
}
class Modal extends Component {
	private JInternalFrame jfOrig = null;
	public Modal (JInternalFrame jfO) {
		jfOrig = jfO;
	}
	public synchronized void ligaModal() {
        sligaModal();
	}
	public synchronized void paraModal() {
    	disparaProcs();
	}
	synchronized void sligaModal() {
      try {
		if (SwingUtilities.isEventDispatchThread()) {
		  EventQueue theQueue = 
			getToolkit().getSystemEventQueue();
		  while (jfOrig.isVisible()) {
			AWTEvent event = theQueue.getNextEvent();
			Object source = event.getSource();
			if (event instanceof ActiveEvent) {
			  ((ActiveEvent)event).dispatch();
			} else if (source instanceof Component) {
			  ((Component)source).dispatchEvent(
				event);
			} else if (source instanceof 
				MenuComponent) {
			  ((MenuComponent)source).dispatchEvent(
				event); 
			} else {
			  System.err.println(
				"Unable to dispatch: " + event);
			}
			//System.out.println("Source: "+source+"\nEvento: "+event+"\n\n");
		  }
//		  System.out.println("SAIU DO WHILE");
		} 
		else {
		  while (jfOrig.isVisible()) {
			wait();
		  }
		}
	  } 
	  catch (InterruptedException ignored) { }
	} 
	synchronized void disparaProcs() {
	  notifyAll();
	}
}
