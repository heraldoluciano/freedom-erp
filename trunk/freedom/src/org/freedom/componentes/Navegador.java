/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Navegador.java <BR>
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
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.freedom.bmps.Icone;
public class Navegador extends JPanel implements ActionListener, KeyListener {
  public  JButton btNovo = new JButton(Icone.novo("btNovo.gif"));
  public  JButton btExcluir = new JButton(Icone.novo("btExcluir.gif"));
  public  JButton btEditar = new JButton(Icone.novo("btEditar.gif"));
  public  JButton btSalvar = new JButton(Icone.novo("btSalvar.gif"));
  public  JButton btCancelar = new JButton(Icone.novo("btCancelar.gif"));
  public  JButton btPrim = null;
  public  JButton btAnt = null;
  public  JButton btProx = null;
  public  JButton btUlt = null;
  private ListaCampos lcNav = null;
  boolean Ctrl = false;
  public boolean bDet = false;
  boolean[] podeVer = new boolean[9];
  public Navegador (boolean nav) {
    bDet = nav; 
    
    for (int i=0; i<9; i++) {
      podeVer[i] = true;
    }
    btNovo.setToolTipText("Novo (Ctrl + N)");
    btExcluir.setToolTipText("Deletar (Ctrl + D)");
    btEditar.setToolTipText("Editar (Ctrl + E)");
    btSalvar.setToolTipText("Salvar (Ctrl + S)");
    btCancelar.setToolTipText("Cancelar (Ctrl + W)");

    setLayout(new GridLayout( 1, 5));
    setPreferredSize(new Dimension(150, 30));
    add(btNovo);
    add(btExcluir);
    add(btEditar);
    add(btSalvar);
    add(btCancelar);
    
    btNovo.addActionListener(this);
    btExcluir.addActionListener(this);
    btEditar.addActionListener(this);
    btSalvar.addActionListener(this);
    btCancelar.addActionListener(this);

    if (nav) {

      btPrim = new JButton(Icone.novo("btPrim.gif"));
      btAnt = new JButton(Icone.novo("btAnt.gif"));
      btProx = new JButton(Icone.novo("btProx.gif"));
      btUlt = new JButton(Icone.novo("btUlt.gif"));

      btPrim.setToolTipText("Primeiro (Ctrl + PageUp)");
      btAnt.setToolTipText("Anterior (PageUp)");
      btProx.setToolTipText("Próximo (PageDown)");
      btUlt.setToolTipText("Último (Ctrl + PageDow)");
      
      setLayout(new GridLayout( 1, 9));
      setPreferredSize(new Dimension(270, 30));
      removeAll();

      add(btPrim);      // 0
      add(btAnt);       // 1
      add(btProx);      // 2
      add(btUlt);       // 3
      add(btNovo);      // 4
      add(btExcluir);   // 5
      add(btEditar);    // 6
      add(btSalvar);    // 7
      add(btCancelar);  // 8

      btPrim.addActionListener(this);
      btAnt.addActionListener(this);
      btProx.addActionListener(this);
      btUlt.addActionListener(this);
    }
  }
  public void setListaCampos(ListaCampos lc) {
    lcNav = lc;    
  }
  public void keyPressed(KeyEvent kevt) {
//    Funcoes.mensagemInforma( null, "keyPressed");
    if (kevt.getKeyCode() == KeyEvent.VK_CONTROL) Ctrl = true;
    if ((btAnt != null) & (btProx != null) & (Ctrl == false)) {    
      if (kevt.getKeyCode() == KeyEvent.VK_PAGE_UP) btAnt.doClick();
      else if (kevt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) btProx.doClick();
    }
    if(Ctrl) {
      if (kevt.getKeyCode() == KeyEvent.VK_N) btNovo.doClick();
      else if (kevt.getKeyCode() == KeyEvent.VK_D) btExcluir.doClick();
      else if (kevt.getKeyCode() == KeyEvent.VK_E) btEditar.doClick();
      else if (kevt.getKeyCode() == KeyEvent.VK_S) btSalvar.doClick();
      else if (kevt.getKeyCode() == KeyEvent.VK_W) btCancelar.doClick();
      if ((btPrim !=null) & (btUlt != null)) {    
        if (kevt.getKeyCode() == KeyEvent.VK_PAGE_UP) btPrim.doClick();
        else if (kevt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) btUlt.doClick();
      }
    }
  }
  public void visivel(String sVal,boolean bVal) {
    int ind = 0;
    if (bDet) {
      if (sVal.compareTo("NEW") == 0) ind = 4;
      else if (sVal.compareTo("DELETE") == 0) ind = 5;
      else if (sVal.compareTo("EDIT") == 0) ind = 6;
      else if (sVal.compareTo("SAVE") == 0) ind = 7;
      else if (sVal.compareTo("CANCEL") == 0) ind = 8;
    }
    else {
      if (sVal.compareTo("NEW") == 0) ind = 0;
      else if (sVal.compareTo("DELETE") == 0) ind = 1;
      else if (sVal.compareTo("EDIT") == 0) ind = 2;
      else if (sVal.compareTo("SAVE") == 0) ind = 3;
      else if (sVal.compareTo("CANCEL") == 0) ind = 4;
    }
    if(bVal) {
      if (podeVer[ind]) {
        getComponent(ind).setEnabled(bVal);
      }
    }
    else {
      getComponent(ind).setEnabled(bVal);
    }
  }
  public void setAtivo(int ind, boolean b) {
    podeVer[ind] = b;
    getComponent(ind).setEnabled(b);
  }
  public void keyReleased(KeyEvent kevt) {
//    Funcoes.mensagemInforma( null, "keyRelease");
    if(kevt.getKeyCode() == KeyEvent.VK_CONTROL) Ctrl = false;
  }
  public void keyTyped(KeyEvent kevt) {
//    Funcoes.mensagemInforma( null, "keyTyped");
  }
  public void actionPerformed(ActionEvent evt) {
    if (lcNav != null) {
      if (evt.getSource() == btNovo) {
         lcNav.insert(true);
      }
      else if (evt.getSource() == btSalvar) {
         lcNav.post();
      }
      else if (evt.getSource() == btEditar) {
         lcNav.edit();
      }
      else if (evt.getSource() == btExcluir) {
         lcNav.delete();
      }
      else if (evt.getSource() == btCancelar) {
         lcNav.cancel(true);
      }
      if (bDet) {
        if (evt.getSource() == btPrim) {
           lcNav.first();
        }
        else if (evt.getSource() == btAnt) {
          lcNav.prior();
        }
        else if (evt.getSource() == btProx) {
          lcNav.next();
        }
        else if (evt.getSource() == btUlt) {
          lcNav.last();
        }
      }
    }
  }
}
