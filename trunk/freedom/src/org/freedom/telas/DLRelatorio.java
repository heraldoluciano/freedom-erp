/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)DLRelatorio.java <BR>
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;
public abstract class DLRelatorio extends JDialog implements ActionListener,KeyListener {
  private JPanelPad pinCli = new JPanelPad(350,170);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JPanel pnCentRod = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
  private JPanel pnBotoes = new JPanel(new GridLayout(1,2));
  private JButton btImp = new JButton(Icone.novo("btImprime.gif"));
  private JButton btPrevimp = new JButton(Icone.novo("btPrevimp.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  boolean bSetArea = true;
  boolean bCtrl = false;
  public Container c = null;
  public DLRelatorio() {
    setTitulo("Requisiçao de Relatório");
    setAtribos(100,100,350,200);
    c = getContentPane();
    c.setLayout(new BorderLayout());
    pnRod.setBorder(BorderFactory.createEtchedBorder());
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(pinCli,BorderLayout.CENTER);
    pnRod.setPreferredSize(new Dimension(350,32));
    btSair.setPreferredSize(new Dimension(100,30));
    pnRod.add(btSair,BorderLayout.EAST);
    pnRod.add(pnCentRod,BorderLayout.CENTER);
    pnBotoes.setPreferredSize(new Dimension(80,28));
    pnCentRod.add(pnBotoes);
    pnBotoes.add(btImp);
    pnBotoes.add(btPrevimp);
    
    btImp.setToolTipText("Imprimir (Ctrl + I)");
    btPrevimp.setToolTipText("Previsão da impressão (Ctrl + P)");
    btSair.setToolTipText("Sair (ESC)");
    
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    btSair.addActionListener(this);
    btImp.addKeyListener(this);
    btPrevimp.addKeyListener(this);
    btSair.addKeyListener(this);
  }
  public void setPainel(JPanelPad pin) {
    pinCli = pin;
    bSetArea = false;
  }
  public void setAreaComp() {
    pinCli = new JPanelPad((int)getSize().getWidth()-10,
      (int)getSize().getHeight()-45);  
    c.add(pinCli,BorderLayout.CENTER);
    bSetArea = false;
  }
  public void adic(Component comp, int iX, int iY, int iLarg, int iAlt) {
    if (bSetArea)
      setAreaComp();
    comp.addKeyListener(this);
    pinCli.adic(comp,iX,iY,iLarg,iAlt);
  }

  public abstract void imprimir(boolean b);
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btImp) {
      imprimir(false);
    }
    else if (evt.getSource() == btPrevimp) {
      imprimir(true);
    }
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_CONTROL) {
      bCtrl = true;
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_I) {
      if (bCtrl)
        btImp.doClick();
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_P) {
      if (bCtrl)
        btPrevimp.doClick();
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_ESCAPE) {
      btSair.doClick();
    }
  }
  public void keyReleased(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_CONTROL) {
      bCtrl = false;
    }
  }
  public void keyTyped(KeyEvent kevt) { }
  public void setTitulo(String sVal) {
    setTitle(sVal);
  }
  public void setAtribos(int iX, int iY, int iLarg, int iAlt) {
    setBounds(iX,iY,iLarg,iAlt);
  }
  public void setAtribos(int Larg, int Alt) {
    setSize( Larg, Alt);
    setLocationRelativeTo(this);
  }
}

