/**
 * @version 01/06/2001 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FRelatorio.java <BR>
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
 * Formulário modelo para relatórios.
 */

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.Painel;


public abstract class FRelatorio extends FFilho implements ActionListener,KeyListener {
  private Painel pinCli = new Painel(350,170);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JPanel pnCentRod = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
  public JPanel pnBotoes = new JPanel(new GridLayout(1,2));
  private JButton btImp = new JButton(Icone.novo("btImprime.gif"));
  private JButton btPrevimp = new JButton(Icone.novo("btPrevimp.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  boolean bSetArea = true;
  boolean bCtrl = false;
  Container c = null;
  public FRelatorio() {
    setTitulo("Requisiçao de Relatório");
    setAtribos(100,100,350,200);
    c = super.getTela();
    pnRod.setBorder(BorderFactory.createEtchedBorder());
    c.add(pnRod,BorderLayout.SOUTH);
    pnRod.setPreferredSize(new Dimension(350,32));
    btSair.setPreferredSize(new Dimension(100,30));
    pnRod.add(btSair,BorderLayout.EAST);
    pnRod.add(pnCentRod,BorderLayout.CENTER);
    pnBotoes.setPreferredSize(new Dimension(80,28));
    pnCentRod.add(pnBotoes);
    pnBotoes.add(btImp);
    pnBotoes.add(btPrevimp);
    
    btImp.setToolTipText("Imprimir (Ctrl + I)");
    btPrevimp.setToolTipText("Visualizar Impressão (Ctrl + P)");
    btSair.setToolTipText("Sair (ESC)");
    
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    btSair.addActionListener(this);
    btImp.addKeyListener(this);
    btPrevimp.addKeyListener(this);
    btSair.addKeyListener(this);
  }
  public void setPanel(JPanel pn) {
  	c.remove(pinCli);
  	c.add(pn,BorderLayout.CENTER);
    bSetArea = false;
  }
  public void setPainel(Painel pin) {
    pinCli = pin;
    bSetArea = false;
  }
  public void setAreaComp() {
    pinCli = new Painel((int)getSize().getWidth()-10,
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
  /**
   *
   * Retorna o container do rootPane.
   * Sobrepôs o getTela do FFilho por ele 'resetar' o Layout.
   * @see FFilho#getTela
   *
   */
  public Container getTela() {
          return c;
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
}

