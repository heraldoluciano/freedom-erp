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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.freedom.bmps.Icone;

public class FFilho extends JInternalFrame implements InternalFrameListener {
  private Component firstFocus = null;
  private Container contentFirstFocus = null;
  private boolean initFirstFocus = true; 
  public String strTemp = "";
  public Connection con = null;
  public FFilho () {
     /* Construtor da classe. */
     this("Filho01",true,true,true,true);
     addInternalFrameListener(this);
  }
  public FFilho(String arg01, boolean arg02, boolean arg03, boolean arg04, boolean arg05) {
  	super(arg01, arg02, arg03, arg04, arg05);
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
  public void internalFrameActivated(InternalFrameEvent e) {
  	//System.out.println("Teste 1");
  }
  public void internalFrameClosed(InternalFrameEvent e) {
  	//System.out.println("Teste 2");
  }
  public void internalFrameClosing(InternalFrameEvent e) {
  	//System.out.println("Teste 3");
  }
  public void internalFrameDeactivated(InternalFrameEvent e) {
  	//System.out.println("Teste 4");
  }
  public void internalFrameDeiconified(InternalFrameEvent e) {
  	//System.out.println("Teste 5");
  }
  public void internalFrameIconified(InternalFrameEvent e) {
  //	System.out.println("Teste 6");
  }
  
  public synchronized void setFirstFocus(Component firstFocus) {
  	this.firstFocus = firstFocus;
  }
  public synchronized void firstFocus() {
  	if ( (firstFocus!=null) && (firstFocus.hasFocus()) && (initFirstFocus) )
  		firstFocus.requestFocus();
  		
  		
  		
/*  	if (firstFocus!=null) { 
  		if (firstFocus.hasFocus())
  			firstFocus.requestFocus();
  		else 
  			loadFirstFocus();
  	}
  	else
  		loadFirstFocus();*/
  }
  /*public synchronized void loadFirstFocus() {
    Component cOpened = null;
  	if (contentFirstFocus == null)
       contentFirstFocus = getContentPane();
    //String nome = cpOpened.getName();
    if (contentFirstFocus != null) {
       for (int i=0 ; i<contentFirstFocus.getComponentCount() ; i++) {
         cOpened = contentFirstFocus.getComponent(i);
         if (cOpened!=null) {
            if (cOpened.hasFocus()) {
               cOpened.nextFocus();
               cOpened.requestFocus();
               break;
            }
         }
       }
    }
  } */
  
  /*public synchronized void setContentFirstFocus(Container contentFirstFocus) {
  	this.contentFirstFocus = contentFirstFocus;
  }*/
  public void internalFrameOpened(InternalFrameEvent e) {
 	firstFocus();
  }
  public void setConexao(Connection cn) {
  	con = cn;
  }
  public synchronized void execShow() {
    show();
  }
  public boolean getInitFirstFocus() {
  	return initFirstFocus;
  }
  public void setInitFirstFocus(boolean initFirstFocus) {
  	this.initFirstFocus = initFirstFocus;
  }
  
}
