/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FFilho.java <BR>
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
 * Classe com funcoes basicas para controle do um InternalFrame.
 */

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.freedom.bmps.Icone;



public class FFilho extends JInternalFrame implements InternalFrameListener {
  public String strTemp = "";

  public FFilho () { 
     /* Construtor da classe. */
     
     super("Filho01",true,true,true,true);
     setVisible(true);
     addInternalFrameListener(this);
  }
  public void setTitulo(String tit) {
  	if (getName() == null)
  	  setName(tit); 
    setTitle(tit);
  }
  public void setAtribos(int Esq, int Topo, int Larg, int Alt) { 
    setBounds(Esq,Topo,Larg,Alt); 
  }
  public void setTela(Container c) { setContentPane(c); }
  public Container getTela() { 
          Container tela = getContentPane();
          tela.setLayout(new BorderLayout());
          return tela; 
  }
  public void internalFrameActivated(InternalFrameEvent wevt) {
//    Funcoes.mensageInforma(null,"ON ACTIVATED");
  }
  public void internalFrameClosed(InternalFrameEvent wevt) {
//    Funcoes.mensagemInforma(null,"ON CLOSED");
  }
  public void internalFrameClosing(InternalFrameEvent wevt) {
//    Funcoes.mensagemInforma(null,"ON CLOSING");
/*    try {
      setClosed(false);
    }
    catch (Exception err) { }*/
  }
  public JPanel adicBotaoSair() {
    Container c = getContentPane();
    JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
    JPanel pnRod = new JPanel(new BorderLayout());
    pnRod.setPreferredSize(new Dimension(200,30));        
    btSair.setPreferredSize(new Dimension(110,30));        
    pnRod.add(btSair,BorderLayout.EAST);
    c.add(pnRod,BorderLayout.SOUTH);
    btSair.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          dispose();
        }
      }
    );
    return pnRod;
  }
  
  public void dispose() {
  	System.gc();
  	super.dispose();
  }
  /**
   *  Ajusta conexão da tela. <BR>
   *  Adiciona a conexão vigente a este formulário.
   *  Esta função é geramente chamada da classe criadora da tela, <BR>
   *  esta função será sobrescrita em cada classe filha para serem <BR>
   *  devidamente ajustada as conexões necessárias na tela.
   *
   *  @param cn: Conexao valida e ativa que será repassada e esta tela.
   */

  public void internalFrameDeactivated(InternalFrameEvent wevt) { }
  public void internalFrameDeiconified(InternalFrameEvent wevt) { }
  public void internalFrameIconified(InternalFrameEvent wevt) { }
  public void internalFrameOpened(InternalFrameEvent wevt) {
 /*   Container cpOpened = getContentPane();
    Component cOpened = null;
    if (cpOpened != null) {
       for (int i=0 ; i<cpOpened.getComponentCount() ; i++) {
         cOpened = cpOpened.getComponent(i);
         if (cOpened!=null) {
            if (cOpened.hasFocus()) {
               cOpened.nextFocus();
               cOpened.requestFocus();
               break;
            }
         }
       }
    }*/
  }
}
