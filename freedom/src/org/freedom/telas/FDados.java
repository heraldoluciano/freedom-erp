/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FDados.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Painel;
import org.freedom.componentes.PainelImagem;

public class FDados extends FFilho implements ActionListener, KeyListener, InternalFrameListener, PostListener{//, FocusListener {
   public Connection con = null;
   public PreparedStatement atualiza = null;
   public PreparedStatement insere = null;
   public PreparedStatement deleta = null;
   public ListaCampos lcCampos = new ListaCampos(this); 
   public ListaCampos lcSeq = null;
   private BorderLayout blDados = new BorderLayout();
   private BorderLayout blCliente = new BorderLayout();
   private BorderLayout blRodape = new BorderLayout();
   private GridLayout glImp = new GridLayout( 1, 2); 
   private FlowLayout flImp = new FlowLayout(FlowLayout.CENTER, 0, 0);
   public JPanel pnCliente = new JPanel();
   public JPanel pnRodape = new JPanel();
   public JPanel pnBordRod = new JPanel();
   public Navegador nav = new Navegador(false); 
   public Navegador navSeq = new Navegador(false); 
   public JPanel pnImp = new JPanel();
   public JPanel pnGImp = new JPanel();
   public Painel pinDados = new Painel();
   public JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
   public JButton btImp = new JButton( Icone.novo("btImprime.gif"));
   public JButton btPrevimp = new JButton( Icone.novo("btPrevimp.gif"));
   public Border br = BorderFactory.createEtchedBorder();
   public Container c = getTela();
   public JComponent primeiroCompo = null;
   boolean Shift = false;
   boolean Ctrl = false;
   boolean setArea = true;
   boolean bMostrar = false;
   public FDados () { 
//     super();
     setTitulo("Formulário de dados");
     setAtribos( 50, 50, 500, 300);

     lcSeq = lcCampos;
     navSeq = nav;

     c.setLayout(blDados);

     btSair.setToolTipText("Fecha a Tela (Shift + F4)");
     btImp.setToolTipText("Imprimir (Ctrl+P)");
     btPrevimp.setToolTipText("Visualizar Impressão (Ctrl+R)");
     pnCliente.setLayout(blCliente);
         
     c.add(pnCliente, BorderLayout.CENTER);
     
     pnRodape.setLayout(blRodape);

     pnImp.setLayout(flImp);
     pnGImp.setLayout(glImp);
     pnGImp.setPreferredSize(new Dimension( 80, 26));
     pnGImp.add(btImp);
     pnGImp.add(btPrevimp);
     pnImp.add(pnGImp);

     pnBordRod.setLayout(new GridLayout(1,1));
     pnBordRod.setPreferredSize(new Dimension(450, 30));
     pnBordRod.add(pnRodape);
     pnBordRod.setBorder(br);

     btSair.addActionListener(this);

     btSair.addKeyListener(this);
     btImp.addKeyListener(this);
     btPrevimp.addKeyListener(this);

     addKeyListener(this);
     addInternalFrameListener(this);
     
     lcCampos.addPostListener(this);

     pnRodape.add( btSair, BorderLayout.EAST);      
     pnRodape.add( nav, BorderLayout.WEST);
     pnRodape.add( pnImp, BorderLayout.CENTER);
     c.add(pnBordRod, BorderLayout.SOUTH);
     
     setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
  }  

  public void execShow(Connection cn) {
    con = cn;
    lcCampos.setConexao(cn);
    lcSeq.setConexao(cn);
    setPKFoco();
    show();
  }

  public void setPKFoco() {
    for (int i=0; i<lcCampos.getComponentCount(); i++) {
      if (((GuardaCampo)lcCampos.getComponent(i)).ehPK())
        ((GuardaCampo)lcCampos.getComponent(i)).getComponente().requestFocus();
    }
  }
  public void setConexao(Connection cn) {
     con = cn;
  }

  public void execConsultaPrep() {
/*     try {
        resultado = consultaPrep.executeQuery();
     }
     catch (SQLException e) {
        Funcoes.mensagemErro(null,e.getMessage());
     }*/
  }

  public void actionPerformed(ActionEvent evt) {
    if (evt.getActionCommand() == "Sair") 
      dispose();
  }
  public void keyPressed(KeyEvent kevt) {
    if(kevt.getKeyCode() == KeyEvent.VK_SHIFT) Shift = true;
    if(kevt.getKeyCode() == KeyEvent.VK_CONTROL) Ctrl = true;
    if(Shift) {
      if(kevt.getKeyCode() == KeyEvent.VK_F4) btSair.doClick();
    }
    if(Ctrl) {
      if (kevt.getKeyCode() == KeyEvent.VK_R) btPrevimp.doClick();
        else if (kevt.getKeyCode() == KeyEvent.VK_P) btImp.doClick();
    }
  }
  public void keyReleased(KeyEvent kevt) {
//    Funcoes.mensagemErro( null, "keyRelease");
    if(kevt.getKeyCode() == KeyEvent.VK_SHIFT) Shift = false;
    else if(kevt.getKeyCode() == KeyEvent.VK_CONTROL) Ctrl = false;
  }
  public void keyTyped(KeyEvent kevt) {  }
//  public void focusGained(FocusEvent e) { }
//  public void focusLost(FocusEvent e) { }
  public void setPainel(Painel pin, Container pn) {
    pinDados = pin;
    pn.add(pinDados);
    setArea = false;
  }
  public void setPainel(Painel pin) {
    pinDados = pin;
    setArea = false;
  }
  public Dimension getAreaComp() {
    int iLarg = (int)getSize().getWidth();
    int iAlt = (int)getSize().getHeight();
    iLarg -= 10;
    iAlt -= 35;
    Dimension dm = new Dimension(iLarg,iAlt);
    return dm;
  }
  public void setAreaComp() {
    pinDados = new Painel((int)getSize().getWidth()-10,
      (int)getSize().getHeight()-65);  
    pnCliente.add(pinDados, BorderLayout.CENTER);
    setArea = false;
  }
  public void adic(Component comp, int X, int Y, int Larg, int Alt) {
    if (setArea)
      setAreaComp();
    comp.addKeyListener(this);
    comp.addKeyListener(navSeq);
    try {
      JTextFieldPad txt = (JTextFieldPad) comp; 
      txt.addFocusListener(txt);
      comp = txt;
    }
    catch (Exception e) { }
    pinDados.adic(comp,X,Y,Larg,Alt); 
  }
  public JLabel adicCampo(JTextFieldPad comp, int X, int Y, int Larg, int Alt, String nome, 
      String label, int tipo, int tam, int dec, boolean pk, boolean fk, JTextFieldFK txtDescFK, boolean req) {
    comp.setTipo(tipo,tam,dec);
    comp.setName("txt"+nome);
    comp.setNomeCampo(nome);
    comp.setListaCampos(lcSeq);
    comp.setPKFK(pk,fk);
    lcSeq.add(new GuardaCampo( comp, X, Y, Larg, Alt, nome, label, pk, fk, txtDescFK, tipo, req),"txt"+nome);
    navSeq.setListaCampos(lcSeq);
    lcSeq.setNavegador(navSeq);
    lcSeq.setState(ListaCampos.LCS_NONE);
	JLabel lbTmp = new JLabel(label);

    adic(lbTmp, X, Y-20, Larg, 20);
    adic(comp, X, Y, Larg, Alt);
    return lbTmp;
  }
  public JLabel adicCampo(JTextFieldPad comp, int X, int Y, int Larg, int Alt, String nome, 
      String label, byte key, boolean req) {
    comp.setName("txt"+nome);
    comp.setNomeCampo(nome);
    comp.setListaCampos(lcSeq);
	comp.setChave(key);
    lcSeq.add(new GuardaCampo( comp, nome, label, key, req),"txt"+nome);
    navSeq.setListaCampos(lcSeq);
    lcSeq.setNavegador(navSeq);
    lcSeq.setState(ListaCampos.LCS_NONE);
    JLabel lbTmp = new JLabel(label);
    adic(lbTmp, X, Y-20, Larg, 20);
    adic(comp, X, Y, Larg, Alt);
    return lbTmp;
  }
  
  public void adicCampo(JPasswordFieldPad comp, int X, int Y, int Larg, int Alt, String nome, 
      String label, byte key, boolean req) {
	comp.setName("txp"+nome);
	comp.setListaCampos(lcSeq);
	lcSeq.add(new GuardaCampo( comp, nome, label, key, req),"txt"+nome);
	navSeq.setListaCampos(lcSeq);
	lcSeq.setNavegador(navSeq);
	lcSeq.setState(ListaCampos.LCS_NONE);
	adic(new JLabel(label), X, Y-20, Larg, 20);
	adic(comp, X, Y, Larg, Alt);
  }
  
  public void adicCampoInvisivel(JTextFieldPad comp, String nome, String label, 
       int tipo, int tam, int dec, boolean pk, boolean fk, JTextFieldFK txtDescFK, boolean req) {
    comp.setTipo(tipo,tam,dec);
    comp.addKeyListener(this);
    comp.addKeyListener(navSeq);
    comp.setName("txt"+nome);
    comp.setNomeCampo(nome);
    comp.setListaCampos(lcSeq);
	comp.setPKFK(pk,fk);
    lcSeq.add(new GuardaCampo( comp, 0, 0, 0, 0, nome, label, pk, fk, txtDescFK, tipo, req),"txt"+nome);
    navSeq.setListaCampos(lcSeq);
    lcSeq.setNavegador(navSeq);
    lcSeq.setState(ListaCampos.LCS_NONE);
  }
  
  public JLabel adicDescFK(JTextFieldFK comp, int X, int Y, int Larg, int Alt, String nome, String label) {
    comp.setNomeCampo(nome);
    comp.addKeyListener(this);
    comp.addKeyListener(navSeq);
    comp.setLabel(label);
    JLabel lbTmp = new JLabel(label);
    adic(lbTmp, X, Y-20, Larg, 20);
    adic(comp, X, Y, Larg, Alt);
    return lbTmp;
  }
  public JLabel adicDescFK(JTextFieldFK comp, int X, int Y, int Larg, int Alt, String nome, String label, int Tipo, int tam, int dec) {
    comp.setNomeCampo(nome);
    comp.addKeyListener(this);
    comp.addKeyListener(navSeq);
    comp.setTipo(Tipo, tam, dec);
    comp.setLabel(label);
    JLabel lbTmp = new JLabel(label);
    adic(lbTmp, X, Y-20, Larg, 20);
    adic(comp, X, Y, Larg, Alt);
    return lbTmp;
  }
  public void adicDBLiv( Component comp, String nome, String label, int tipo, boolean req) { 
    comp.setName(nome);
    comp.addKeyListener(this);
    comp.addKeyListener(navSeq);
    if (comp instanceof JRadioGroup)
      ((JRadioGroup) comp).setListaCampos(lcSeq);
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad) comp).setListaCampos(lcSeq);
    else if (comp instanceof JTextAreaPad)
      ((JTextAreaPad) comp).setListaCampos(lcSeq);
    lcSeq.add(new GuardaCampo( comp, 1, 1, 1, 1, nome, label, false, false, null, tipo, req),nome);
  }

  public void adicDB( Component comp, int X, int Y, int Larg, int Alt, String nome, String label, boolean req) {
  	boolean bScroll = false;
    comp.setName(nome);
    if (comp instanceof JRadioGroup)
      ((JRadioGroup) comp).setListaCampos(lcSeq);
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad) comp).setListaCampos(lcSeq);
	else if (comp instanceof JComboBoxPad)
	  ((JComboBoxPad) comp).setListaCampos(lcSeq);
    else if (comp instanceof PainelImagem)
      ((PainelImagem) comp).setListaCampos(lcSeq);
	else if (comp instanceof JTextAreaPad) {
	  ((JTextAreaPad) comp).setListaCampos(lcSeq);
	  bScroll = true;
	}
    lcSeq.add(new GuardaCampo( comp, nome, label, ListaCampos.DB_SI, req));
    adic(new JLabel(label), X, Y-20, Larg, 20);
    if (bScroll)
  	  adic(new JScrollPane(comp),X, Y, Larg, Alt);
    else 
	  adic(comp, X, Y, Larg, Alt);
  }
  
  // deprecated 
  public void adicDB( Component comp, int X, int Y, int Larg, int Alt, String nome, String label, int tipo, boolean req) {
  	boolean bScroll = false;
    comp.setName(nome);
    if (comp instanceof JRadioGroup)
      ((JRadioGroup) comp).setListaCampos(lcSeq);
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad) comp).setListaCampos(lcSeq);
	else if (comp instanceof JComboBoxPad)
	  ((JComboBoxPad) comp).setListaCampos(lcSeq);
    else if (comp instanceof PainelImagem)
      ((PainelImagem) comp).setListaCampos(lcSeq);
	else if (comp instanceof JTextAreaPad) {
	  ((JTextAreaPad) comp).setListaCampos(lcSeq);
	  bScroll = true;
	}
    lcSeq.add(new GuardaCampo( comp, X, Y, Larg, Alt, nome, label, false, false, null, tipo, req),nome);
    adic(new JLabel(label), X, Y-20, Larg, 20);
    if (bScroll)
  	  adic(new JScrollPane(comp),X, Y, Larg, Alt);
    else 
	  adic(comp, X, Y, Larg, Alt);
  }
  public void setNavegador(Navegador nv) {
    navSeq = nv;
  }
  public void setListaCampos(ListaCampos lc) {
    lcSeq = lc;
  }
  public void setListaCampos(boolean bAuto, String sTab, String sSigla) {
      lcSeq.montaSql(bAuto, sTab, sSigla);
  }           
  public void setBordaReq(JComponent comp) {
    comp.setBorder( 
      BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
        BorderFactory.createEtchedBorder()
      )
    );
  }
  public void setBordaPad(JComponent comp) {
    comp.setBorder( BorderFactory.createEtchedBorder());
  }
  public void internalFrameClosing(InternalFrameEvent ifevt) { 
    try {
      setClosed(false);
      setVisible(true);
    }
    catch(Exception err) { }
/*    if ((lcCampos.getStatus() == lcCampos.LCS_EDIT) | 
       (lcCampos.getStatus() == lcCampos.LCS_INSERT)) {
      if (JOptionPane.showConfirmDialog(null, "Dados não foram salvos, deseja descartar?", "Freedom", JOptionPane.YES_NO_OPTION)==0 ) {
        bMostrar = false;
      }
      else { 
        bMostrar = true;
      }
    }*/
    super.internalFrameClosing(ifevt);
  }
  public void internalFrameActivated(InternalFrameEvent ifevt) { }
  public void internalFrameClosed(InternalFrameEvent ifevt) { 
    try {
      setClosed(false);
      setVisible(true);
    }
    catch(Exception err) { }
/*    if (bMostrar) {
      setVisible(true);
      setVisible(true);
      System.out.println("Executou o show()");
    }
    else
      dispose();*/
  }
  public void beforePost(PostEvent pevt) {
    setPKFoco();
  }
  public void afterPost(PostEvent pevt) { }
  public void internalFrameDeactivated(InternalFrameEvent ifevt) { 
    super.internalFrameDeactivated(ifevt);
  }
  public void internalFrameDeiconified(InternalFrameEvent ifevt) { 
    super.internalFrameDeiconified(ifevt);
  }
  public void internalFrameIconified(InternalFrameEvent ifevt) { 
    super.internalFrameIconified(ifevt);
  }
  public void internalFrameOpened(InternalFrameEvent ifevt) { 
    super.internalFrameOpened(ifevt);
  }
}        
        
        
        
        
        
        

